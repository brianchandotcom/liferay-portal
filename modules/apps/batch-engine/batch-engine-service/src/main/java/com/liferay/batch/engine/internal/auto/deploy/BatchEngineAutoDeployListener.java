/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.batch.engine.internal.auto.deploy;

import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 * @author Raymond Augé
 */
@Component(service = {AutoDeployListener.class, BatchEngineUnitProcessor.class})
public class BatchEngineAutoDeployListener
	implements AutoDeployListener, BatchEngineUnitProcessor {

	@Override
	public int deploy(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		try (ZipFile zipFile = new ZipFile(autoDeploymentContext.getFile())) {
			_deploy(zipFile);
		}
		catch (Exception exception) {
			throw new AutoDeployException(exception);
		}

		return AutoDeployer.CODE_DEFAULT;
	}

	public boolean isBatchEngineTechnical(String zipEntryName) {
		if (zipEntryName.endsWith("jsont")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isDeployable(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		File file = autoDeploymentContext.getFile();

		String fileName = file.getName();

		if (!StringUtil.endsWith(fileName, ".zip")) {
			return false;
		}

		try (ZipFile zipFile = new ZipFile(file)) {
			for (BatchEngineUnit batchEngineUnit :
					_getBatchEngineUnits(zipFile)) {

				if (!batchEngineUnit.isValid()) {
					continue;
				}

				BatchEngineUnitConfiguration batchEngineUnitConfiguration =
					_getBatchEngineUnitConfiguration(batchEngineUnit);

				if ((batchEngineUnitConfiguration != null) &&
					(batchEngineUnitConfiguration.companyId > 0) &&
					(batchEngineUnitConfiguration.userId > 0) &&
					Validator.isNotNull(
						batchEngineUnitConfiguration.className) &&
					Validator.isNotNull(batchEngineUnitConfiguration.version)) {

					return true;
				}
			}
		}
		catch (Exception exception) {
			throw new AutoDeployException(exception);
		}

		return false;
	}

	@Override
	public void processBatchEngineUnits(
		Iterable<BatchEngineUnit> batchEngineUnits) {

		for (BatchEngineUnit batchEngineUnit : batchEngineUnits) {
			try {
				_processBatchEngineUnit(batchEngineUnit);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Successfully enqueued batch file " +
							batchEngineUnit.getFileName());
				}
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Ignoring invalid batch file " +
							batchEngineUnit.getFileName(),
						exception);
				}
			}
		}
	}

	private void _deploy(ZipFile zipFile) throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info("Deploying batch engine file " + zipFile.getName());
		}

		processBatchEngineUnits(_getBatchEngineUnits(zipFile));
	}

	private BatchEngineUnitConfiguration _getBatchEngineUnitConfiguration(
			BatchEngineUnit batchEngineUnit)
		throws IOException {

		BatchEngineUnitConfiguration batchEngineConfiguration =
			batchEngineUnit.getBatchEngineUnitConfiguration();

		if (batchEngineConfiguration.companyId == 0) {
			if (_log.isWarnEnabled()) {
				_log.warn("Using default company ID for this batch process");
			}

			try {
				Company company = _companyLocalService.getCompanyByWebId(
					PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

				batchEngineConfiguration.companyId = company.getCompanyId();
			}
			catch (PortalException portalException) {
				_log.error("Unable to get default company ID", portalException);
			}
		}

		if (batchEngineConfiguration.userId == 0) {
			if (_log.isWarnEnabled()) {
				_log.warn("Using default user ID for this batch process");
			}

			try {
				batchEngineConfiguration.userId =
					_userLocalService.getUserIdByScreenName(
						batchEngineConfiguration.companyId,
						PropsUtil.get(PropsKeys.DEFAULT_ADMIN_SCREEN_NAME));
			}
			catch (PortalException portalException) {
				_log.error("Unable to get default user ID", portalException);
			}
		}

		return batchEngineConfiguration;
	}

	private Iterable<BatchEngineUnit> _getBatchEngineUnits(ZipFile zipFile) {
		return new Iterable<BatchEngineUnit>() {

			@Override
			public Iterator<BatchEngineUnit> iterator() {
				return new BatchEngineUnitIterator(zipFile);
			}

		};
	}

	private String _getBatchEngineZipEntryKey(ZipEntry zipEntry) {
		String zipEntryName = zipEntry.getName();

		if (isBatchEngineTechnical(zipEntryName)) {
			return zipEntryName;
		}

		if (!zipEntryName.contains(StringPool.SLASH)) {
			return StringPool.BLANK;
		}

		return zipEntryName.substring(
			0, zipEntryName.lastIndexOf(StringPool.SLASH));
	}

	private Collection<BatchEngineUnit> _getBatchEngineZipUnitsCollection(
		ZipFile zipFile) {

		Map<String, ZipEntry> batchEngineZipEntries = new HashMap<>();
		Map<String, BatchEngineUnit> batchEngineUnits = new HashMap<>();
		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if (zipEntry.isDirectory()) {
				continue;
			}

			String key = _getBatchEngineZipEntryKey(zipEntry);

			ZipEntry complementZipEntry = batchEngineZipEntries.get(key);

			if (complementZipEntry == null) {
				batchEngineZipEntries.put(key, zipEntry);

				batchEngineUnits.put(
					key, new AdvancedBatchEngineZipUnitImpl(zipFile, zipEntry));

				continue;
			}

			batchEngineUnits.put(
				key,
				new ClassicBatchEngineZipUnitImpl(
					zipFile, zipEntry, complementZipEntry));

			batchEngineZipEntries.remove(key);
		}

		return batchEngineUnits.values();
	}

	private void _processBatchEngineUnit(BatchEngineUnit batchEngineUnit)
		throws Exception {

		BatchEngineUnitConfiguration batchEngineUnitConfiguration = null;
		byte[] content = null;
		String contentType = null;

		if (batchEngineUnit.isValid()) {
			batchEngineUnitConfiguration = _getBatchEngineUnitConfiguration(
				batchEngineUnit);

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
				"Invalid batch engine file " + batchEngineUnit.getFileName());
		}

		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				BatchEngineAutoDeployListener.class.getName());

		BatchEngineImportTask batchEngineImportTask =
			_batchEngineImportTaskLocalService.addBatchEngineImportTask(
				null, batchEngineUnitConfiguration.companyId,
				batchEngineUnitConfiguration.userId, 100,
				batchEngineUnitConfiguration.callbackURL,
				batchEngineUnitConfiguration.className, content,
				StringUtil.toUpperCase(contentType),
				BatchEngineTaskExecuteStatus.INITIAL.name(),
				batchEngineUnitConfiguration.fieldNameMappingMap,
				BatchEngineImportTaskConstants.IMPORT_STRATEGY_ON_ERROR_FAIL,
				BatchEngineTaskOperation.CREATE.name(),
				batchEngineUnitConfiguration.parameters,
				batchEngineUnitConfiguration.taskItemDelegateName);

		executorService.submit(
			() -> {
				_batchEngineImportTaskExecutor.execute(batchEngineImportTask);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Successfully deployed batch engine file " +
							batchEngineUnit.getFileName());
				}
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineAutoDeployListener.class);

	@Reference
	private BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;

	@Reference
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private com.liferay.portal.kernel.util.File _file;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	@Reference
	private UserLocalService _userLocalService;

	private class BatchEngineUnitIterator implements Iterator<BatchEngineUnit> {

		public BatchEngineUnitIterator(ZipFile zipFile) {
			Collection<BatchEngineUnit> batchEngineUnits =
				_getBatchEngineZipUnitsCollection(zipFile);

			_iterator = batchEngineUnits.iterator();
		}

		@Override
		public boolean hasNext() {
			return _iterator.hasNext();
		}

		@Override
		public BatchEngineUnit next() {
			return _iterator.next();
		}

		private final Iterator<BatchEngineUnit> _iterator;

	}

}