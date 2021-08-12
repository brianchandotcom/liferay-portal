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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(service = AutoDeployListener.class)
public class BatchEngineAutoDeployListener implements AutoDeployListener {

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

	@Override
	public boolean isDeployable(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		File file = autoDeploymentContext.getFile();

		String fileName = file.getName();

		if (!StringUtil.endsWith(fileName, ".zip")) {
			return false;
		}

		try (ZipFile zipFile = new ZipFile(file)) {
			if (!_isValidZipEntryCount(zipFile)) {
				return false;
			}

			BatchEngineZipEntryPair batchEngineZipEntryPair =
				_getBatchEngineZipEntryPair(zipFile);

			if (!batchEngineZipEntryPair.isValid()) {
				return false;
			}

			try (InputStream inputStream = zipFile.getInputStream(
					batchEngineZipEntryPair._configurationZipEntry)) {

				BatchEngineImportConfiguration batchEngineImportConfiguration =
					_objectMapper.readValue(
						inputStream, BatchEngineImportConfiguration.class);

				if ((batchEngineImportConfiguration == null) ||
					(batchEngineImportConfiguration.companyId == 0) ||
					(batchEngineImportConfiguration.userId == 0) ||
					Validator.isNull(
						batchEngineImportConfiguration.className) ||
					Validator.isNull(batchEngineImportConfiguration.version)) {

					return false;
				}
			}
		}
		catch (Exception exception) {
			throw new AutoDeployException(exception);
		}

		return true;
	}

	private void _deploy(ZipFile zipFile) throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info("Deploying batch engine file " + zipFile.getName());
		}

		BatchEngineZipEntryPair batchEngineZipEntryPair =
			_getBatchEngineZipEntryPair(zipFile);

		BatchEngineImportConfiguration batchEngineImportConfiguration = null;
		byte[] content = null;
		String contentType = null;

		if (batchEngineZipEntryPair.isValid()) {
			try (InputStream inputStream = zipFile.getInputStream(
					batchEngineZipEntryPair._configurationZipEntry)) {

				batchEngineImportConfiguration = _objectMapper.readValue(
					inputStream, BatchEngineImportConfiguration.class);
			}

			UnsyncByteArrayOutputStream compressedUnsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			ZipEntry dataZipEntry = batchEngineZipEntryPair._dataZipEntry;

			try (InputStream inputStream = zipFile.getInputStream(dataZipEntry);
				ZipOutputStream zipOutputStream = new ZipOutputStream(
					compressedUnsyncByteArrayOutputStream)) {

				zipOutputStream.putNextEntry(
					new ZipEntry(dataZipEntry.getName()));

				StreamUtil.transfer(inputStream, zipOutputStream, false);
			}

			content = compressedUnsyncByteArrayOutputStream.toByteArray();

			contentType = _file.getExtension(dataZipEntry.getName());
		}

		if ((batchEngineImportConfiguration == null) || (content == null) ||
			Validator.isNull(contentType)) {

			throw new IllegalStateException(
				"Invalid batch engine file " + zipFile.getName());
		}

		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				BatchEngineAutoDeployListener.class.getName());

		BatchEngineImportTask batchEngineImportTask =
			_batchEngineImportTaskLocalService.addBatchEngineImportTask(
				batchEngineImportConfiguration.companyId,
				batchEngineImportConfiguration.userId, 100,
				batchEngineImportConfiguration.callbackURL,
				batchEngineImportConfiguration.className, content,
				StringUtil.toUpperCase(contentType),
				BatchEngineTaskExecuteStatus.INITIAL.name(),
				batchEngineImportConfiguration.fieldNameMappingMap,
				BatchEngineTaskOperation.CREATE.name(),
				batchEngineImportConfiguration.parameters,
				batchEngineImportConfiguration.taskItemDelegateName);

		executorService.submit(
			() -> {
				_batchEngineImportTaskExecutor.execute(batchEngineImportTask);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Successfully deployed batch engine file " +
							zipFile.getName());
				}
			});
	}

	private BatchEngineZipEntryPair _getBatchEngineZipEntryPair(
		ZipFile zipFile) {

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		BatchEngineZipEntryPair batchEngineZipEntryPair =
			new BatchEngineZipEntryPair();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if (zipEntry.isDirectory()) {
				continue;
			}

			String zipEntryName = zipEntry.getName();

			if (zipEntryName.contains("batch-engine.json")) {
				batchEngineZipEntryPair.setConfigurationZipEntry(zipEntry);

				continue;
			}

			batchEngineZipEntryPair.setDataZipEntry(zipEntry);
		}

		return batchEngineZipEntryPair;
	}

	private boolean _isValidZipEntryCount(ZipFile zipFile) {
		int count = 0;

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if (zipEntry.isDirectory()) {
				continue;
			}

			count++;
		}

		if (count != 2) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unsupported file count " + count);
			}

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineAutoDeployListener.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper();

	@Reference
	private BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;

	@Reference
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	@Reference
	private com.liferay.portal.kernel.util.File _file;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	private static final class BatchEngineImportConfiguration {

		@JsonProperty
		protected String callbackURL;

		@JsonProperty
		protected String className;

		@JsonProperty
		protected long companyId;

		@JsonProperty
		protected Map<String, String> fieldNameMappingMap;

		@JsonProperty
		protected Map<String, Serializable> parameters;

		@JsonProperty
		protected String taskItemDelegateName;

		@JsonProperty
		protected long userId;

		@JsonProperty
		protected String version;

	}

	private class BatchEngineZipEntryPair {

		protected boolean isValid() {
			if ((_configurationZipEntry == null) || (_dataZipEntry == null)) {
				return false;
			}

			return true;
		}

		protected void setConfigurationZipEntry(ZipEntry zipEntry) {
			if ((_dataZipEntry != null) &&
				!_parentDirectoryMatches(zipEntry, _dataZipEntry)) {

				return;
			}

			_configurationZipEntry = zipEntry;
		}

		protected void setDataZipEntry(ZipEntry zipEntry) {
			if ((_configurationZipEntry != null) &&
				!_parentDirectoryMatches(zipEntry, _configurationZipEntry)) {

				return;
			}

			_dataZipEntry = zipEntry;
		}

		private boolean _parentDirectoryMatches(
			ZipEntry zipEntry1, ZipEntry zipEntry2) {

			String name1 = zipEntry1.getName();
			String name2 = zipEntry2.getName();

			if (!name1.contains(StringPool.SLASH) &&
				!name2.contains(StringPool.SLASH)) {

				return true;
			}

			if (name1.startsWith(
					name2.substring(0, name2.lastIndexOf(StringPool.SLASH))) &&
				name2.startsWith(
					name1.substring(0, name1.lastIndexOf(StringPool.SLASH)))) {

				return true;
			}

			return false;
		}

		private ZipEntry _configurationZipEntry;
		private ZipEntry _dataZipEntry;

	}

}