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

package com.liferay.batch.engine.internal.installer;

import com.fasterxml.jackson.annotation.JsonProperty;

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
import com.liferay.portal.file.install.FileInstaller;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
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
import java.io.Serializable;

import java.net.URL;

import java.util.Collection;
import java.util.Collections;
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
 */
@Component(service = FileInstaller.class)
public class BatchEngineFileInstaller implements FileInstaller {

	@Override
	public boolean canTransformURL(File file) {
		String fileName = file.getName();

		if (!StringUtil.endsWith(fileName, ".zip")) {
			return false;
		}

		try (ZipFile zipFile = new ZipFile(file)) {
			for (BatchEngineZipUnit batchEngineZipUnit :
					_getBatchEngineZipUnits(zipFile)) {

				if (!batchEngineZipUnit.isValid()) {
					continue;
				}

				BatchEngineImportConfiguration batchEngineImportConfiguration =
					_getBatchEngineImportConfiguration(batchEngineZipUnit);

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
			if (_log.isDebugEnabled()) {
				_log.debug(new AutoDeployException(exception));
			}
		}

		return false;
	}

	public boolean isBatchEngineTechnical(String zipEntryName) {
		if (zipEntryName.endsWith("jsont")) {
			return true;
		}

		return false;
	}

	@Override
	public URL transformURL(File file) throws AutoDeployException {
		try (ZipFile zipFile = new ZipFile(file)) {
			_deploy(zipFile);
		}
		catch (Exception exception) {
			throw new AutoDeployException(exception);
		}

		return null;
	}

	@Override
	public void uninstall(File file) {
	}

	public static final class BatchEngineImportConfiguration {

		public String getCallbackURL() {
			return callbackURL;
		}

		public String getClassName() {
			return className;
		}

		public long getCompanyId() {
			return companyId;
		}

		public Map<String, String> getFieldNameMappingMap() {
			return fieldNameMappingMap;
		}

		public Map<String, Serializable> getParameters() {
			return parameters;
		}

		public String getTaskItemDelegateName() {
			return taskItemDelegateName;
		}

		public long getUserId() {
			return userId;
		}

		public String getVersion() {
			return version;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public void setFieldNameMappingMap(
			Map<String, String> fieldNameMappingMap) {

			if (fieldNameMappingMap == null) {
				fieldNameMappingMap = Collections.emptyMap();
			}

			this.fieldNameMappingMap = new HashMap<>(fieldNameMappingMap);
		}

		public void setParameters(Map<String, Serializable> parameters) {
			if (parameters == null) {
				parameters = Collections.emptyMap();
			}

			this.parameters = new HashMap<>(parameters);
		}

		public void setTaskItemDelegateName(String taskItemDelegateName) {
			this.taskItemDelegateName = taskItemDelegateName;
		}

		public void setVersion(String version) {
			this.version = version;
		}

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

		for (BatchEngineZipUnit batchEngineZipUnit :
				_getBatchEngineZipUnits(zipFile)) {

			try {
				_processBatchEngineZipUnit(batchEngineZipUnit);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Successfully enqueued batch file " +
							batchEngineZipUnit);
				}
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Ignoring invalid batch file " + batchEngineZipUnit,
						exception);
				}
			}
		}
	}

	private BatchEngineImportConfiguration _getBatchEngineImportConfiguration(
			BatchEngineZipUnit<BatchEngineImportConfiguration>
				batchEngineZipUnit)
		throws IOException {

		BatchEngineImportConfiguration batchEngineConfiguration =
			batchEngineZipUnit.getBatchEngineConfiguration(
				BatchEngineImportConfiguration.class);

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

	private Iterable<BatchEngineZipUnit> _getBatchEngineZipUnits(
		ZipFile zipFile) {

		return new Iterable<BatchEngineZipUnit>() {

			@Override
			public Iterator<BatchEngineZipUnit> iterator() {
				return new BatchEngineZipUnitIterator(zipFile);
			}

		};
	}

	private Collection<BatchEngineZipUnit> _getBatchEngineZipUnitsCollection(
		ZipFile zipFile) {

		Map<String, ZipEntry> batchEngineZipEntries = new HashMap<>();
		Map<String, BatchEngineZipUnit> batchEngineZipUnits = new HashMap<>();
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

				batchEngineZipUnits.put(
					key, new AdvancedBatchEngineZipUnitImpl(zipFile, zipEntry));

				continue;
			}

			batchEngineZipUnits.put(
				key,
				new ClassicBatchEngineZipUnitImpl(
					zipFile, zipEntry, complementZipEntry));

			batchEngineZipEntries.remove(key);
		}

		return batchEngineZipUnits.values();
	}

	private void _processBatchEngineZipUnit(
			BatchEngineZipUnit<BatchEngineImportConfiguration>
				batchEngineZipUnit)
		throws Exception {

		BatchEngineImportConfiguration batchEngineImportConfiguration = null;
		byte[] content = null;
		String contentType = null;

		if (batchEngineZipUnit.isValid()) {
			batchEngineImportConfiguration = _getBatchEngineImportConfiguration(
				batchEngineZipUnit);

			UnsyncByteArrayOutputStream compressedUnsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			try (InputStream inputStream =
					batchEngineZipUnit.getDataInputStream();
				ZipOutputStream zipOutputStream = new ZipOutputStream(
					compressedUnsyncByteArrayOutputStream)) {

				zipOutputStream.putNextEntry(
					new ZipEntry(batchEngineZipUnit.getDataFileName()));

				StreamUtil.transfer(inputStream, zipOutputStream, false);
			}

			content = compressedUnsyncByteArrayOutputStream.toByteArray();

			contentType = _file.getExtension(
				batchEngineZipUnit.getDataFileName());
		}

		if ((batchEngineImportConfiguration == null) || (content == null) ||
			Validator.isNull(contentType)) {

			throw new IllegalStateException(
				"Invalid batch engine file " +
					batchEngineZipUnit.getZipFileName());
		}

		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				BatchEngineFileInstaller.class.getName());

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
							batchEngineZipUnit.getZipFileName());
				}
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineFileInstaller.class);

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

	private class BatchEngineZipUnitIterator
		implements Iterator<BatchEngineZipUnit> {

		public BatchEngineZipUnitIterator(ZipFile zipFile) {
			Collection<BatchEngineZipUnit> batchEngineZipUnits =
				_getBatchEngineZipUnitsCollection(zipFile);

			_iterator = batchEngineZipUnits.iterator();
		}

		@Override
		public boolean hasNext() {
			return _iterator.hasNext();
		}

		@Override
		public BatchEngineZipUnit next() {
			return _iterator.next();
		}

		private final Iterator<BatchEngineZipUnit> _iterator;

	}

}