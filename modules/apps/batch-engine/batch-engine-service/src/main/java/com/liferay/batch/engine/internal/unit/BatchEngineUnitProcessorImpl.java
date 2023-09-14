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
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitThreadLocal;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
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
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegateAdaptorFactory;

import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

		List<CompletableFuture<Void>> completableFutures = new ArrayList<>();

		for (BatchEngineUnit batchEngineUnit : batchEngineUnits) {
			try {
				CompletableFuture<Void> completableFuture =
					_processBatchEngineUnit(batchEngineUnit);

				if (completableFuture != null) {
					completableFutures.add(completableFuture);
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

		return CompletableFuture.allOf(
			completableFutures.toArray(new CompletableFuture[0]));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private CompletableFuture<Void> _execute(
			BatchEngineUnit batchEngineUnit,
			BatchEngineUnitConfiguration batchEngineUnitConfiguration,
			byte[] content, String contentType)
		throws Exception {

		CompletableFuture<Void> completableFuture = new CompletableFuture<>();

		ServiceTracker<Object, Object> serviceTracker =
			new ServiceTracker<Object, Object>(
				_bundleContext,
				_bundleContext.createFilter(
					StringBundler.concat(
						"(|(&(batch.engine.entity.class.name=",
						batchEngineUnitConfiguration.getClassName(), ")",
						"(!(batch.engine.task.item.delegate.name=*)))",
						"(&(batch.engine.entity.class.name=",
						_getObjectEntryClassName(batchEngineUnitConfiguration),
						")(batch.engine.task.item.delegate.name=",
						batchEngineUnitConfiguration.getTaskItemDelegateName(),
						"))(&(batch.engine.entity.class.name=",
						batchEngineUnitConfiguration.getClassName(),
						")(batch.engine.task.item.delegate.name=",
						batchEngineUnitConfiguration.getTaskItemDelegateName(),
						")))")),
				null) {

				@Override
				public Object addingService(
					ServiceReference<Object> serviceReference) {

					Object service = _bundleContext.getService(
						serviceReference);

					ExecutorService executorService =
						_portalExecutorManager.getPortalExecutor(
							BatchEngineUnitProcessorImpl.class.getName());

					executorService.submit(
						() -> {
							try {
								_execute(
									batchEngineUnit,
									batchEngineUnitConfiguration, content,
									contentType, service, this);
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
						});

					return null;
				}

			};

		serviceTracker.open();

		return completableFuture;
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

	private CompletableFuture<Void> _processBatchEngineUnit(
			BatchEngineUnit batchEngineUnit)
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

		Map<String, Serializable> parameters =
			batchEngineUnitConfiguration.getParameters();

		String featureFlag = (String)parameters.get("featureFlag");

		if (Validator.isNotNull(featureFlag) &&
			!FeatureFlagManagerUtil.isEnabled(featureFlag)) {

			return null;
		}

		return _execute(
			batchEngineUnit, batchEngineUnitConfiguration, content,
			contentType);
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
	private File _file;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private VulcanBatchEngineTaskItemDelegateAdaptorFactory
		_vulcanBatchEngineTaskItemDelegateAdaptorFactory;

}