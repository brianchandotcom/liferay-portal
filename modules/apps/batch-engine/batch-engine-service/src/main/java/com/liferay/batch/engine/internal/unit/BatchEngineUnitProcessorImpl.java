/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.unit;

import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.internal.writer.BatchEngineTaskItemDelegateProvider;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
import com.liferay.batch.engine.unit.BatchEngineUnitMetaInfo;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitThreadLocal;
import com.liferay.batch.engine.unit.BundleBatchEngineUnit;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Matija Petanjek
 */
@Component(service = BatchEngineUnitProcessor.class)
public class BatchEngineUnitProcessorImpl implements BatchEngineUnitProcessor {

	@Override
	public CompletableFuture<Void> processBatchEngineUnits(
		Collection<BatchEngineUnit> batchEngineUnits) {

		List<Runnable> runnables = new ArrayList<>();

		for (BatchEngineUnit batchEngineUnit : batchEngineUnits) {
			try {
				BatchEngineUnitMetaInfo batchEngineUnitMetaInfo =
					batchEngineUnit.getBatchEngineUnitMetaInfo();

				String featureFlag = batchEngineUnitMetaInfo.getFeatureFlag();

				if (_isFeatureFlagDisabled(featureFlag)) {
					_featureFlagBatchEngineUnitProcessor.
						registerBatchEngineUnit(
							batchEngineUnitMetaInfo.getCompanyId(), featureFlag,
							() -> CompletableFuture.runAsync(
								_getRunnable(batchEngineUnit)));

					continue;
				}

				Runnable runnable = _getRunnable(batchEngineUnit);

				if (runnable != null) {
					runnables.add(runnable);
				}

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Successfully enqueued batch file ",
							batchEngineUnit.getFileName(), " ",
							batchEngineUnit.getDataFileName()));
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
		}

		if (runnables.isEmpty()) {
			return CompletableFuture.completedFuture(null);
		}

		CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(
			runnables.get(0));

		for (int i = 1; i < runnables.size(); i++) {
			Runnable runnable = runnables.get(i);

			completableFuture = completableFuture.thenAccept(
				result -> runnable.run());
		}

		return completableFuture;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private void _execute(
			BatchEngineUnit batchEngineUnit,
			BatchEngineUnitConfiguration batchEngineUnitConfiguration,
			byte[] content, String contentType, Object service,
			ServiceTracker<Object, Object> serviceTracker)
		throws Exception {

		BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate =
			_batchEngineTaskItemDelegateProvider.toBatchEngineTaskItemDelegate(
				service);

		BatchEngineImportTask batchEngineImportTask =
			_batchEngineImportTaskLocalService.addBatchEngineImportTask(
				null, batchEngineUnitConfiguration.getCompanyId(),
				batchEngineUnitConfiguration.getUserId(), 100,
				batchEngineUnitConfiguration.getCallbackURL(),
				batchEngineUnitConfiguration.getClassName(), content,
				StringUtil.toUpperCase(contentType),
				BatchEngineTaskExecuteStatus.INITIAL.name(),
				batchEngineUnitConfiguration.getFieldNameMappingMap(),
				BatchEngineImportTaskConstants.IMPORT_STRATEGY_ON_ERROR_FAIL,
				BatchEngineTaskOperation.CREATE.name(),
				batchEngineUnitConfiguration.getParameters(),
				batchEngineUnitConfiguration.getTaskItemDelegateName(),
				batchEngineTaskItemDelegate);

		try {
			BatchEngineUnitThreadLocal.setFileName(
				batchEngineUnit.getFileName());

			_batchEngineImportTaskExecutor.execute(
				batchEngineImportTask, batchEngineTaskItemDelegate,
				batchEngineUnitConfiguration.isCheckPermissions());
		}
		finally {
			BatchEngineUnitThreadLocal.setFileName(StringPool.BLANK);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Successfully deployed batch engine file ",
					batchEngineUnit.getFileName(), " ",
					batchEngineUnit.getDataFileName()));
		}

		serviceTracker.close();
	}

	private Bundle _getBundle(BatchEngineUnit batchEngineUnit) {
		if (!(batchEngineUnit instanceof BundleBatchEngineUnit)) {
			return null;
		}

		BundleBatchEngineUnit bundleBatchEngineUnit =
			(BundleBatchEngineUnit)batchEngineUnit;

		return bundleBatchEngineUnit.getBundle();
	}

	private String _getObjectEntryClassName(
		BatchEngineUnitConfiguration batchEngineUnitConfiguration) {

		String className = batchEngineUnitConfiguration.getClassName();

		String taskItemDelegateName =
			batchEngineUnitConfiguration.getTaskItemDelegateName();

		if (Validator.isNotNull(taskItemDelegateName)) {
			className = StringBundler.concat(
				className, StringPool.POUND, taskItemDelegateName);
		}

		return className;
	}

	private Runnable _getRunnable(BatchEngineUnit batchEngineUnit)
		throws Exception {

		BatchEngineUnitConfiguration batchEngineUnitConfiguration = null;
		byte[] content = null;
		String contentType = null;

		if (batchEngineUnit.isValid()) {
			batchEngineUnitConfiguration = _updateBatchEngineUnitConfiguration(
				batchEngineUnit.getBatchEngineUnitConfiguration());

			UnsyncByteArrayOutputStream compressedUnsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			try (InputStream inputStream = batchEngineUnit.getDataInputStream();
				ZipOutputStream zipOutputStream = new ZipOutputStream(
					compressedUnsyncByteArrayOutputStream)) {

				zipOutputStream.putNextEntry(
					new ZipEntry(batchEngineUnit.getDataFileName()));

				StreamUtil.transfer(inputStream, zipOutputStream, false);
			}

			content = compressedUnsyncByteArrayOutputStream.toByteArray();

			contentType = _file.getExtension(batchEngineUnit.getDataFileName());
		}

		if ((batchEngineUnitConfiguration == null) || (content == null) ||
			Validator.isNull(contentType)) {

			throw new IllegalStateException(
				StringBundler.concat(
					"Invalid batch engine file ", batchEngineUnit.getFileName(),
					" ", batchEngineUnit.getDataFileName()));
		}

		if (_isProcessed(batchEngineUnit)) {
			return null;
		}

		return _getRunnable(
			batchEngineUnit, batchEngineUnitConfiguration, content,
			contentType);
	}

	private Runnable _getRunnable(
		BatchEngineUnit batchEngineUnit,
		BatchEngineUnitConfiguration batchEngineUnitConfiguration,
		byte[] content, String contentType) {

		return () -> {
			CompletableFuture<Void> completableFuture =
				new CompletableFuture<>();

			ServiceTracker<Object, Object> serviceTracker = null;

			try {
				serviceTracker = new ServiceTracker<Object, Object>(
					_bundleContext,
					_bundleContext.createFilter(
						StringBundler.concat(
							"(|(&(batch.engine.entity.class.name=",
							batchEngineUnitConfiguration.getClassName(), ")",
							"(!(batch.engine.task.item.delegate.name=*)))",
							"(&(batch.engine.entity.class.name=",
							_getObjectEntryClassName(
								batchEngineUnitConfiguration),
							")(batch.engine.task.item.delegate.name=",
							batchEngineUnitConfiguration.
								getTaskItemDelegateName(),
							"))(&(batch.engine.entity.class.name=",
							batchEngineUnitConfiguration.getClassName(),
							")(batch.engine.task.item.delegate.name=",
							batchEngineUnitConfiguration.
								getTaskItemDelegateName(),
							")))")),
					null) {

					@Override
					public Object addingService(
						ServiceReference<Object> serviceReference) {

						Object service = _bundleContext.getService(
							serviceReference);

						try {
							_execute(
								batchEngineUnit, batchEngineUnitConfiguration,
								content, contentType, service, this);
						}
						catch (Exception exception) {
							if (_log.isWarnEnabled()) {
								_log.warn(exception);
							}
						}
						finally {
							completableFuture.complete(null);
						}

						_bundleContext.ungetService(serviceReference);

						return null;
					}

				};

				if (serviceTracker != null) {
					serviceTracker.open();

					completableFuture.get();
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
		};
	}

	private boolean _isFeatureFlagDisabled(String featureFlagKey) {
		if (Validator.isNotNull(featureFlagKey) &&
			!FeatureFlagManagerUtil.isEnabled(featureFlagKey)) {

			return true;
		}

		return false;
	}

	private boolean _isProcessed(BatchEngineUnit batchEngineUnit) {
		Bundle bundle = _getBundle(batchEngineUnit);

		if (bundle == null) {
			return false;
		}

		try {
			BatchEngineUnitConfiguration batchEngineUnitConfiguration =
				batchEngineUnit.getBatchEngineUnitConfiguration();

			String dataFileName = batchEngineUnit.getDataFileName();

			java.io.File processedFile = bundle.getDataFile(
				com.liferay.petra.string.StringUtil.merge(
					Arrays.asList(
						dataFileName.replaceAll("\\W+", "."),
						batchEngineUnitConfiguration.getCompanyId(),
						"processed"),
					"."));

			if (processedFile == null) {
				return false;
			}

			String lastModifiedString = String.valueOf(
				bundle.getLastModified());

			if (processedFile.exists() &&
				Objects.equals(_file.read(processedFile), lastModifiedString)) {

				return true;
			}

			if (!processedFile.exists()) {
				processedFile.createNewFile();
			}

			_file.write(processedFile, lastModifiedString, true);

			return false;
		}
		catch (IOException ioException) {
			ReflectionUtil.throwException(ioException);
		}

		return false;
	}

	private BatchEngineUnitConfiguration _updateBatchEngineUnitConfiguration(
		BatchEngineUnitConfiguration batchEngineUnitConfiguration) {

		if ((batchEngineUnitConfiguration.getUserId() == 0) &&
			batchEngineUnitConfiguration.isCheckPermissions() &&
			batchEngineUnitConfiguration.isMultiCompany()) {

			batchEngineUnitConfiguration.setCheckPermissions(false);
		}

		if (batchEngineUnitConfiguration.getCompanyId() == 0) {
			if (_log.isInfoEnabled()) {
				_log.info("Using default company ID for this batch process");
			}

			try {
				Company company = _companyLocalService.getCompanyByWebId(
					PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

				batchEngineUnitConfiguration.setCompanyId(
					company.getCompanyId());
			}
			catch (PortalException portalException) {
				_log.error("Unable to get default company ID", portalException);
			}
		}

		if (batchEngineUnitConfiguration.getUserId() == 0) {
			if (_log.isInfoEnabled()) {
				_log.info("Using default user ID for this batch process");
			}

			try {
				batchEngineUnitConfiguration.setUserId(
					_userLocalService.getUserIdByScreenName(
						batchEngineUnitConfiguration.getCompanyId(),
						PropsUtil.get(PropsKeys.DEFAULT_ADMIN_SCREEN_NAME)));
			}
			catch (PortalException portalException) {
				_log.error("Unable to get default user ID", portalException);
			}
		}

		return batchEngineUnitConfiguration;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineUnitProcessorImpl.class);

	@Reference
	private BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;

	@Reference
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	@Reference
	private BatchEngineTaskItemDelegateProvider
		_batchEngineTaskItemDelegateProvider;

	private BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private FeatureFlagBatchEngineUnitProcessor
		_featureFlagBatchEngineUnitProcessor;

	@Reference
	private File _file;

	@Reference
	private UserLocalService _userLocalService;

}