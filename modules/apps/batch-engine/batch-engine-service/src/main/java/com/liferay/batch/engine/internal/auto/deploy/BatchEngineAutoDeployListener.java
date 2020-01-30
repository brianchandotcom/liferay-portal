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
import java.util.Objects;
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
			if (zipFile.size() != 2) {
				return false;
			}

			ZipEntry zipEntry = zipFile.getEntry("batch-engine.json");

			if (zipEntry == null) {
				return false;
			}

			try (InputStream inputStream = zipFile.getInputStream(zipEntry)) {
				BatchEngineImportConfiguration batchEngineImportConfiguration =
					_objectMapper.readValue(
						inputStream, BatchEngineImportConfiguration.class);

				if (batchEngineImportConfiguration == null) {
					return false;
				}

				if ((batchEngineImportConfiguration.companyId == 0) ||
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

		Enumeration<? extends ZipEntry> iterator = zipFile.entries();

		BatchEngineImportConfiguration batchEngineImportConfiguration = null;
		byte[] content = null;
		String contentType = null;

		while (iterator.hasMoreElements()) {
			ZipEntry zipEntry = iterator.nextElement();

			if (Objects.equals(zipEntry.getName(), "batch-engine.json")) {
				try (InputStream inputStream = zipFile.getInputStream(
						zipEntry)) {

					batchEngineImportConfiguration = _objectMapper.readValue(
						inputStream, BatchEngineImportConfiguration.class);
				}

				continue;
			}

			UnsyncByteArrayOutputStream compressedUnsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			try (InputStream inputStream = zipFile.getInputStream(zipEntry);
				ZipOutputStream zipOutputStream = new ZipOutputStream(
					compressedUnsyncByteArrayOutputStream)) {

				zipOutputStream.putNextEntry(new ZipEntry(zipEntry.getName()));

				StreamUtil.transfer(inputStream, zipOutputStream, false);
			}

			content = compressedUnsyncByteArrayOutputStream.toByteArray();

			contentType = _file.getExtension(zipEntry.getName());
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
				batchEngineImportConfiguration.parameters);

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
		protected long userId;

		@JsonProperty
		protected String version;

	}

}