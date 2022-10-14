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
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
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
			for (BatchEngineZipEntryPair batchEngineZipEntryPair :
					_getBatchEngineZipEntryPairs(zipFile)) {

				if (!batchEngineZipEntryPair.isValid()) {
					continue;
				}

				BatchEngineImportConfiguration batchEngineImportConfiguration =
					_getBatchEngineImportConfiguration(batchEngineZipEntryPair);

				if ((batchEngineImportConfiguration != null) &&
					(batchEngineImportConfiguration.companyId > 0) &&
					(batchEngineImportConfiguration.userId > 0) &&
					Validator.isNotNull(
						batchEngineImportConfiguration.className) &&
					Validator.isNotNull(
						batchEngineImportConfiguration.version)) {

					return true;
				}
			}
		}
		catch (Exception exception) {
			throw new AutoDeployException(exception);
		}

		return false;
	}

	public static final class BatchEngineImportConfiguration {

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

	private void _deploy(ZipFile zipFile) throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info("Deploying batch engine file " + zipFile.getName());
		}

		for (BatchEngineZipEntryPair batchEngineZipEntryPair :
				_getBatchEngineZipEntryPairs(zipFile)) {

			_processBatchEngineZipEntryPair(batchEngineZipEntryPair);
		}
	}

	private BatchEngineImportConfiguration _getBatchEngineImportConfiguration(
			BatchEngineZipEntryPair batchEngineZipEntryPair)
		throws IOException {

		if (batchEngineZipEntryPair._dataZipEntry != null) {
			try (InputStream inputStream =
					batchEngineZipEntryPair.getConfigurationInputStream()) {

				return _objectMapper.readValue(
					inputStream, BatchEngineImportConfiguration.class);
			}
		}

		try (InputStream inputStream =
				batchEngineZipEntryPair.getConfigurationInputStream()) {

			BatchEngineTechnicalJsonParser batchEngineTechnicalJsonParser =
				new BatchEngineTechnicalJsonParser(inputStream);

			return batchEngineTechnicalJsonParser.
				getBatchEngineImportConfiguration(_objectMapper);
		}
	}

	private String _getBatchEngineZipEntryKey(ZipEntry zipEntry) {
		String zipEntryName = zipEntry.getName();

		if (_isBatchEngineTechnical(zipEntryName)) {
			return zipEntryName;
		}

		if (!zipEntryName.contains(StringPool.SLASH)) {
			return StringPool.BLANK;
		}

		return zipEntryName.substring(
			0, zipEntryName.lastIndexOf(StringPool.SLASH));
	}

	private Iterable<BatchEngineZipEntryPair> _getBatchEngineZipEntryPairs(
		ZipFile zipFile) {

		return new Iterable<BatchEngineZipEntryPair>() {

			@Override
			public Iterator<BatchEngineZipEntryPair> iterator() {
				return new BatchEngineZipEntryPairIterator(zipFile);
			}

		};
	}

	private Collection<BatchEngineZipEntryPair>
		_getBatchEngineZipEntryPairsCollection(ZipFile zipFile) {

		Map<String, BatchEngineZipEntryPair> batchEngineZipEntryPairs =
			new HashMap<>();
		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if (zipEntry.isDirectory()) {
				continue;
			}

			String key = _getBatchEngineZipEntryKey(zipEntry);

			BatchEngineZipEntryPair batchEngineZipEntryPair =
				batchEngineZipEntryPairs.get(key);

			if (batchEngineZipEntryPair == null) {
				batchEngineZipEntryPair = new BatchEngineZipEntryPair(zipFile);

				batchEngineZipEntryPairs.put(key, batchEngineZipEntryPair);
			}

			if (_isBatchEngineConfiguration(zipEntry.getName())) {
				batchEngineZipEntryPair.setConfigurationZipEntry(zipEntry);
			}
			else {
				batchEngineZipEntryPair.setDataZipEntry(zipEntry);
			}
		}

		return batchEngineZipEntryPairs.values();
	}

	private boolean _isBatchEngineConfiguration(String zipEntryName) {
		if (Objects.equals(zipEntryName, "batch-engine.json") ||
			zipEntryName.endsWith("/batch-engine.json") ||
			_isBatchEngineTechnical(zipEntryName)) {

			return true;
		}

		return false;
	}

	private boolean _isBatchEngineTechnical(String zipEntryName) {
		if (zipEntryName.endsWith("jsont")) {
			return true;
		}

		return false;
	}

	private void _processBatchEngineZipEntryPair(
			BatchEngineZipEntryPair batchEngineZipEntryPair)
		throws Exception {

		BatchEngineImportConfiguration batchEngineImportConfiguration = null;
		byte[] content = null;
		String contentType = null;

		if (batchEngineZipEntryPair.isValid()) {
			batchEngineImportConfiguration = _getBatchEngineImportConfiguration(
				batchEngineZipEntryPair);
			UnsyncByteArrayOutputStream compressedUnsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			try (InputStream inputStream =
					batchEngineZipEntryPair.getDataInputStream();
				ZipOutputStream zipOutputStream = new ZipOutputStream(
					compressedUnsyncByteArrayOutputStream)) {

				zipOutputStream.putNextEntry(
					new ZipEntry(batchEngineZipEntryPair.getDataFileName()));

				StreamUtil.transfer(inputStream, zipOutputStream, false);
			}

			content = compressedUnsyncByteArrayOutputStream.toByteArray();

			contentType = _file.getExtension(
				batchEngineZipEntryPair.getDataFileName());
		}

		if ((batchEngineImportConfiguration == null) || (content == null) ||
			Validator.isNull(contentType)) {

			throw new IllegalStateException(
				"Invalid batch engine file " +
					batchEngineZipEntryPair.getZipFileName());
		}

		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				BatchEngineAutoDeployListener.class.getName());

		BatchEngineImportTask batchEngineImportTask =
			_batchEngineImportTaskLocalService.addBatchEngineImportTask(
				null, batchEngineImportConfiguration.companyId,
				batchEngineImportConfiguration.userId, 100,
				batchEngineImportConfiguration.callbackURL,
				batchEngineImportConfiguration.className, content,
				StringUtil.toUpperCase(contentType),
				BatchEngineTaskExecuteStatus.INITIAL.name(),
				batchEngineImportConfiguration.fieldNameMappingMap,
				BatchEngineImportTaskConstants.IMPORT_STRATEGY_ON_ERROR_FAIL,
				BatchEngineTaskOperation.CREATE.name(),
				batchEngineImportConfiguration.parameters,
				batchEngineImportConfiguration.taskItemDelegateName);

		executorService.submit(
			() -> {
				_batchEngineImportTaskExecutor.execute(batchEngineImportTask);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Successfully deployed batch engine file " +
							batchEngineZipEntryPair.getZipFileName());
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

	private class BatchEngineZipEntryPair {

		public BatchEngineZipEntryPair(ZipFile zipFile) {
			_zipFile = zipFile;
		}

		public InputStream getConfigurationInputStream() throws IOException {
			return _zipFile.getInputStream(_configurationZipEntry);
		}

		public String getDataFileName() {
			return _dataZipEntry.getName();
		}

		public InputStream getDataInputStream() throws IOException {
			return _zipFile.getInputStream(_dataZipEntry);
		}

		public String getZipFileName() {
			return _zipFile.getName();
		}

		protected boolean isValid() {
			if ((_configurationZipEntry == null) ||
				((_dataZipEntry == null) &&
				 !_isBatchEngineTechnical(_configurationZipEntry.getName()))) {

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
		private final ZipFile _zipFile;

	}

	private class BatchEngineZipEntryPairIterator
		implements Iterator<BatchEngineZipEntryPair> {

		public BatchEngineZipEntryPairIterator(ZipFile zipFile) {
			Collection<BatchEngineZipEntryPair> batchEngineZipEntryPairs =
				_getBatchEngineZipEntryPairsCollection(zipFile);

			_iterator = batchEngineZipEntryPairs.iterator();
		}

		@Override
		public boolean hasNext() {
			return _iterator.hasNext();
		}

		@Override
		public BatchEngineZipEntryPair next() {
			return _iterator.next();
		}

		private final Iterator<BatchEngineZipEntryPair> _iterator;

	}

}