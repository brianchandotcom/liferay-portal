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
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
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
public class BatchEngineImportAutoDeployListener implements AutoDeployListener {

	@Override
	public int deploy(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		File file = autoDeploymentContext.getFile();

		if (_log.isInfoEnabled()) {
			_log.info("Importing batch file " + file.getPath());
		}

		try (ZipFile zipFile = new ZipFile(file)) {
			Enumeration<? extends ZipEntry> iterator = zipFile.entries();

			BatchImportConfiguration batchImportConfiguration = null;
			byte[] content = null;
			String contentType = null;

			while (iterator.hasMoreElements()) {
				ZipEntry zipEntry = iterator.nextElement();

				if (Objects.equals(zipEntry.getName(), "batch-import.json")) {
					batchImportConfiguration = _objectMapper.readValue(
						zipFile.getInputStream(zipEntry),
						BatchImportConfiguration.class);
				}
				else {
					UnsyncByteArrayOutputStream
						uncompressedUnsyncByteArrayOutputStream =
							new UnsyncByteArrayOutputStream();

					StreamUtil.transfer(
						zipFile.getInputStream(zipEntry),
						uncompressedUnsyncByteArrayOutputStream);

					UnsyncByteArrayOutputStream
						compressedUnsyncByteArrayOutputStream =
							new UnsyncByteArrayOutputStream();

					try (ZipOutputStream zipOutputStream = new ZipOutputStream(
							compressedUnsyncByteArrayOutputStream)) {

						zipOutputStream.putNextEntry(
							new ZipEntry(zipEntry.getName()));

						StreamUtil.transfer(
							new UnsyncByteArrayInputStream(
								uncompressedUnsyncByteArrayOutputStream.
									toByteArray()),
							zipOutputStream, false);
					}

					content =
						compressedUnsyncByteArrayOutputStream.toByteArray();

					contentType = _file.getExtension(zipEntry.getName());
				}
			}

			if ((batchImportConfiguration == null) || (content == null) ||
				Validator.isNull(contentType)) {

				throw new IllegalStateException(
					"Batch file " + file.getName() + " is invalid");
			}

			ExecutorService executorService =
				_portalExecutorManager.getPortalExecutor(
					BatchEngineImportAutoDeployListener.class.getName());

			BatchEngineImportTask batchEngineImportTask =
				_batchEngineImportTaskLocalService.addBatchEngineImportTask(
					batchImportConfiguration.companyId,
					batchImportConfiguration.userId, 100,
					batchImportConfiguration.callbackURL,
					batchImportConfiguration.className, content,
					StringUtil.toUpperCase(contentType),
					BatchEngineTaskExecuteStatus.INITIAL.name(),
					batchImportConfiguration.fieldNameMappingMap,
					BatchEngineTaskOperation.CREATE.name(),
					batchImportConfiguration.parameters,
					batchImportConfiguration.version);

			executorService.submit(
				() -> {
					_batchEngineImportTaskExecutor.execute(
						batchEngineImportTask);

					if (_log.isInfoEnabled()) {
						_log.info(
							"Importing batch file " + file.getPath() +
								" has been finished");
					}
				});
		}
		catch (Exception e) {
			throw new AutoDeployException(e);
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

			ZipEntry zipEntry = zipFile.getEntry("batch-import.json");

			if (zipEntry == null) {
				return false;
			}

			BatchImportConfiguration batchImportConfiguration =
				_objectMapper.readValue(
					zipFile.getInputStream(zipEntry),
					BatchImportConfiguration.class);

			if (batchImportConfiguration == null) {
				return false;
			}

			if ((batchImportConfiguration.companyId == 0) ||
				(batchImportConfiguration.userId == 0) ||
				Validator.isNull(batchImportConfiguration.className) ||
				Validator.isNull(batchImportConfiguration.version)) {

				return false;
			}
		}
		catch (Exception e) {
			throw new AutoDeployException(e);
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineImportAutoDeployListener.class);

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

	private static final class BatchImportConfiguration {

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