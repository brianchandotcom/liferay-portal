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

package com.liferay.batch.engine.fileimport.internal;

import com.liferay.batch.engine.BatchFileImportOperation;
import com.liferay.batch.engine.BatchStatus;
import com.liferay.batch.engine.core.BatchJob;
import com.liferay.batch.engine.core.configuration.BatchJobRegistry;
import com.liferay.batch.engine.core.launch.BatchJobLauncher;
import com.liferay.batch.engine.fileimport.BatchFileImportHelper;
import com.liferay.batch.engine.fileimport.BatchFileImportType;
import com.liferay.batch.engine.fileimport.configuration.BatchFileImportJobNameMapper;
import com.liferay.batch.engine.fileimport.internal.util.JobSettingsProperties;
import com.liferay.batch.engine.model.BatchFileImport;
import com.liferay.batch.engine.service.BatchFileImportLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;

import java.io.BufferedInputStream;
import java.io.InputStream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	property = "compression.types=zip,batch",
	service = BatchFileImportHelper.class
)
public class BatchFileImportHelperImpl implements BatchFileImportHelper {

	@Override
	public BatchFileImport process(
			String fileName, InputStream inputStream, String domainName,
			String version, BatchFileImportOperation batchFileImportOperation,
			String callbackURL, String columnNames)
		throws Exception {

		_validate(
			fileName, inputStream, domainName, version,
			batchFileImportOperation);

		if (_log.isDebugEnabled()) {
			_log.debug("Importing file " + fileName);
		}

		FileEntry fileEntry = _addFileEntry(fileName, inputStream);

		BatchFileImport batchFileImport =
			_batchFileImportLocalService.addBatchFileImport(
				fileEntry.getFileEntryId(), domainName, version,
				batchFileImportOperation, callbackURL, columnNames,
				BatchStatus.UNKNOWN);

		BatchJob batchJob = _batchJobRegistry.getBatchJob(
			_batchFileImportJobNameMapper.getJobName(
				batchFileImport.getDomainName(), batchFileImport.getVersion(),
				BatchFileImportType.valueOf(
					StringUtil.upperCase(fileEntry.getExtension())),
				BatchFileImportOperation.valueOf(
					batchFileImport.getOperation())));

		try {
			_batchJobLauncher.run(
				batchJob,
				JobSettingsProperties.getJobSettingsProperties(
					batchFileImport, fileEntry));
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
		}

		return batchFileImport;
	}

	@Override
	public BatchFileImport queue(
			String fileName, InputStream inputStream, String domainName,
			String version, BatchFileImportOperation batchFileImportOperation,
			String callbackURL, String columnNames)
		throws Exception {

		_validate(
			fileName, inputStream, domainName, version,
			batchFileImportOperation);

		if (_log.isDebugEnabled()) {
			_log.debug(String.format("Queueing file %s for import", fileName));
		}

		FileEntry fileEntry = _addFileEntry(fileName, inputStream);

		return _batchFileImportLocalService.addBatchFileImport(
			fileEntry.getFileEntryId(), domainName, version,
			batchFileImportOperation, callbackURL, columnNames,
			BatchStatus.UNKNOWN);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_compressionTypes = Arrays.asList(
			StringUtil.split(
				GetterUtil.getString(properties.get("compression.types"))));
	}

	private FileEntry _addFileEntry(String fileName, InputStream inputStream)
		throws Exception {

		if (_isCompressedFile(FileUtil.getExtension(fileName))) {
			ZipInputStream zipInputStream = new ZipInputStream(
				new BufferedInputStream(inputStream));

			ZipEntry zipEntry = zipInputStream.getNextEntry();

			fileName = zipEntry.getName();

			inputStream = zipInputStream;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		return TempFileEntryUtil.addTempFileEntry(
			serviceContext.getScopeGroupId(), serviceContext.getUserId(),
			"batch-file-import", _getUniqueFileName(fileName), inputStream,
			MimeTypesUtil.getContentType(fileName));
	}

	private String _getUniqueFileName(String fileName) {
		return System.currentTimeMillis() + "_" + fileName;
	}

	private boolean _isCompressedFile(String extension) {
		return _compressionTypes.contains(extension);
	}

	private void _validate(
		String fileName, InputStream inputStream, String domainName,
		String version, BatchFileImportOperation batchFileImportOperation) {

		Objects.requireNonNull(fileName);
		Objects.requireNonNull(inputStream);
		Objects.requireNonNull(domainName);
		Objects.requireNonNull(version);
		Objects.requireNonNull(batchFileImportOperation);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchFileImportHelperImpl.class);

	@Reference
	private BatchFileImportJobNameMapper _batchFileImportJobNameMapper;

	@Reference
	private BatchFileImportLocalService _batchFileImportLocalService;

	@Reference
	private BatchJobLauncher _batchJobLauncher;

	@Reference
	private BatchJobRegistry _batchJobRegistry;

	private List<String> _compressionTypes;

}