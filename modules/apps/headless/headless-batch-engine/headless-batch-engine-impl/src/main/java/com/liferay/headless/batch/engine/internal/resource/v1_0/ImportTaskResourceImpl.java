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

package com.liferay.headless.batch.engine.internal.resource.v1_0;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.ItemClassRegistry;
import com.liferay.batch.engine.configuration.BatchEngineTaskConfiguration;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.model.BatchEngineImportTaskError;
import com.liferay.batch.engine.service.BatchEngineImportTaskErrorLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.headless.batch.engine.dto.v1_0.FailedItem;
import com.liferay.headless.batch.engine.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.internal.resource.v1_0.util.ParametersUtil;
import com.liferay.headless.batch.engine.resource.v1_0.ImportTaskResource;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Ivica Cardic
 */
@Component(
	configurationPid = "com.liferay.batch.engine.configuration.BatchEngineTaskConfiguration",
	properties = "OSGI-INF/liferay/rest/v1_0/import-task.properties",
	property = "batch.engine=true", scope = ServiceScope.PROTOTYPE,
	service = ImportTaskResource.class
)
public class ImportTaskResourceImpl extends BaseImportTaskResourceImpl {

	@Override
	public ImportTask deleteImportTask(
			String className, String callbackURL, String importStrategy,
			String taskItemDelegateName, MultipartBody multipartBody)
		throws Exception {

		return _importFile(
			BatchEngineTaskOperation.DELETE,
			multipartBody.getBinaryFile("file"), callbackURL, className, null,
			importStrategy, taskItemDelegateName);
	}

	@Override
	public ImportTask deleteImportTask(
			String className, String callbackURL, String importStrategy,
			String taskItemDelegateName, Object object)
		throws Exception {

		String contentType = contextHttpServletRequest.getHeader(
			HttpHeaders.CONTENT_TYPE);

		return _importFile(
			BatchEngineTaskOperation.DELETE, _getBytes(object, contentType),
			callbackURL, className, _getBatchEngineTaskContentType(contentType),
			taskItemDelegateName, importStrategy, null);
	}

	@Override
	public ImportTask getImportTask(Long importTaskId) throws Exception {
		return _toImportTask(
			_batchEngineImportTaskLocalService.getBatchEngineImportTask(
				importTaskId));
	}

	@Override
	public Response getImportTaskContent(Long importTaskId) throws Exception {
		BatchEngineImportTask batchEngineImportTask =
			_batchEngineImportTaskLocalService.getBatchEngineImportTask(
				importTaskId);

		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus =
			BatchEngineTaskExecuteStatus.valueOf(
				batchEngineImportTask.getExecuteStatus());

		if (batchEngineTaskExecuteStatus !=
				BatchEngineTaskExecuteStatus.COMPLETED) {

			return Response.status(
				Response.Status.NOT_FOUND
			).build();
		}

		StreamingOutput streamingOutput = outputStream -> StreamUtil.transfer(
			_batchEngineImportTaskLocalService.openContentInputStream(
				importTaskId),
			outputStream);

		return Response.ok(
			streamingOutput
		).header(
			"content-disposition", "attachment; filename=import.zip"
		).build();
	}

	@Override
	public Response getImportTaskFailedItemReport(Long importTaskId)
		throws Exception {

		StreamingOutput streamingOutput = outputStream -> {
			try (BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(outputStream))) {

				bufferedWriter.write("item, itemIndex, message");

				bufferedWriter.newLine();

				for (BatchEngineImportTaskError batchEngineImportTaskError :
						_batchEngineImportTaskErrorLocalService.
							getBatchEngineImportTaskErrors(importTaskId)) {

					bufferedWriter.write(
						StringBundler.concat(
							batchEngineImportTaskError.getItem(),
							StringPool.COMMA_AND_SPACE,
							batchEngineImportTaskError.getItemIndex(),
							StringPool.COMMA_AND_SPACE,
							batchEngineImportTaskError.getMessage()));

					bufferedWriter.newLine();
				}
			}
		};

		return Response.ok(
			streamingOutput
		).header(
			"Content-Disposition",
			"attachment; filename=" + StringUtil.randomString() + ".csv"
		).build();
	}

	@Override
	public ImportTask postImportTask(
			String className, String callbackURL, String fieldNameMapping,
			String importStrategy, String taskItemDelegateName,
			MultipartBody multipartBody)
		throws Exception {

		return _importFile(
			BatchEngineTaskOperation.CREATE,
			multipartBody.getBinaryFile("file"), callbackURL, className,
			fieldNameMapping, importStrategy, taskItemDelegateName);
	}

	@Override
	public ImportTask postImportTask(
			String className, String callbackURL, String fieldNameMapping,
			String importStrategy, String taskItemDelegateName, Object object)
		throws Exception {

		String contentType = contextHttpServletRequest.getHeader(
			HttpHeaders.CONTENT_TYPE);

		return _importFile(
			BatchEngineTaskOperation.CREATE, _getBytes(object, contentType),
			callbackURL, className, _getBatchEngineTaskContentType(contentType),
			fieldNameMapping, importStrategy, taskItemDelegateName);
	}

	@Override
	public ImportTask putImportTask(
			String className, String callbackURL, String importStrategy,
			String taskItemDelegateName, MultipartBody multipartBody)
		throws Exception {

		return _importFile(
			BatchEngineTaskOperation.UPDATE,
			multipartBody.getBinaryFile("file"), callbackURL, className, null,
			importStrategy, taskItemDelegateName);
	}

	@Override
	public ImportTask putImportTask(
			String className, String callbackURL, String importStrategy,
			String taskItemDelegateName, Object object)
		throws Exception {

		String contentType = contextHttpServletRequest.getHeader(
			HttpHeaders.CONTENT_TYPE);

		return _importFile(
			BatchEngineTaskOperation.UPDATE, _getBytes(object, contentType),
			callbackURL, className, _getBatchEngineTaskContentType(contentType),
			null, importStrategy, taskItemDelegateName);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		BatchEngineTaskConfiguration batchEngineTaskConfiguration =
			ConfigurableUtil.createConfigurable(
				BatchEngineTaskConfiguration.class, properties);

		_batchSize = batchEngineTaskConfiguration.importBatchSize();

		if (_batchSize <= 0) {
			_batchSize = 1;
		}

		Properties batchSizeProperties = PropsUtil.getProperties(
			"batch.size.", true);

		for (Map.Entry<Object, Object> entry : batchSizeProperties.entrySet()) {
			_itemClassBatchSizeMap.put(
				String.valueOf(entry.getKey()),
				GetterUtil.getInteger(entry.getValue()));
		}
	}

	private String _getBatchEngineTaskContentType(String contentType) {
		if (contentType.equals(MediaType.APPLICATION_JSON)) {
			return String.valueOf(BatchEngineTaskContentType.JSON);
		}
		else if (contentType.equals("application/x-ndjson")) {
			return String.valueOf(BatchEngineTaskContentType.JSONL);
		}
		else if (contentType.equals("text/csv")) {
			return String.valueOf(BatchEngineTaskContentType.CSV);
		}

		return contentType;
	}

	private byte[] _getBytes(Object object, String contentType)
		throws Exception {

		byte[] bytes = null;

		if (contentType.equals(MediaType.APPLICATION_JSON)) {
			ObjectMapper objectMapper = new ObjectMapper();

			bytes = objectMapper.writeValueAsBytes(object);
		}
		else {
			String content = (String)object;

			bytes = content.getBytes();
		}

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			_getUnsyncByteArrayOutputStream(
				"fileName", new ByteArrayInputStream(bytes));

		return unsyncByteArrayOutputStream.toByteArray();
	}

	private Map.Entry<byte[], String> _getContentAndExtensionFromCompressedFile(
			InputStream inputStream)
		throws Exception {

		byte[] content = StreamUtil.toByteArray(inputStream);

		String fileName = null;

		try (ZipInputStream zipInputStream = new ZipInputStream(
				new UnsyncByteArrayInputStream(content))) {

			ZipEntry zipEntry = zipInputStream.getNextEntry();

			fileName = zipEntry.getName();
		}

		return new AbstractMap.SimpleImmutableEntry<>(
			content, _file.getExtension(fileName));
	}

	private Map.Entry<byte[], String>
			_getContentAndExtensionFromUncompressedFile(
				String fileName, InputStream inputStream)
		throws Exception {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			_getUnsyncByteArrayOutputStream(fileName, inputStream);

		return new AbstractMap.SimpleImmutableEntry<>(
			unsyncByteArrayOutputStream.toByteArray(),
			_file.getExtension(fileName));
	}

	private UnsyncByteArrayOutputStream _getUnsyncByteArrayOutputStream(
			String fileName, InputStream inputStream)
		throws Exception {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				unsyncByteArrayOutputStream)) {

			ZipEntry zipEntry = new ZipEntry(fileName);

			zipOutputStream.putNextEntry(zipEntry);

			StreamUtil.transfer(inputStream, zipOutputStream, false);
		}

		return unsyncByteArrayOutputStream;
	}

	private ImportTask _importFile(
			BatchEngineTaskOperation batchEngineTaskOperation,
			BinaryFile binaryFile, String callbackURL, String className,
			String fieldNameMappingString, String importStrategy,
			String taskItemDelegateName)
		throws Exception {

		Map.Entry<byte[], String> entry = null;

		if (StringUtil.endsWith(binaryFile.getFileName(), "zip")) {
			entry = _getContentAndExtensionFromCompressedFile(
				binaryFile.getInputStream());
		}
		else {
			entry = _getContentAndExtensionFromUncompressedFile(
				binaryFile.getFileName(), binaryFile.getInputStream());
		}

		return _importFile(
			batchEngineTaskOperation, entry.getKey(), callbackURL, className,
			entry.getValue(), fieldNameMappingString, importStrategy,
			taskItemDelegateName);
	}

	private ImportTask _importFile(
			BatchEngineTaskOperation batchEngineTaskOperation, byte[] bytes,
			String callbackURL, String className,
			String batchEngineTaskContentType, String fieldNameMappingString,
			String importStrategy, String taskItemDelegateName)
		throws Exception {

		Class<?> clazz = _itemClassRegistry.getItemClass(className);

		if (clazz == null) {
			throw new IllegalArgumentException(
				"Unknown class name: " + className);
		}

		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				ImportTaskResourceImpl.class.getName());

		BatchEngineImportTask batchEngineImportTask =
			_batchEngineImportTaskLocalService.addBatchEngineImportTask(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				_itemClassBatchSizeMap.getOrDefault(className, _batchSize),
				callbackURL, className, bytes,
				StringUtil.upperCase(batchEngineTaskContentType),
				BatchEngineTaskExecuteStatus.INITIAL.name(),
				_toMap(fieldNameMappingString),
				_toImportStrategy(importStrategy),
				batchEngineTaskOperation.name(),
				ParametersUtil.toParameters(contextUriInfo, _ignoredParameters),
				taskItemDelegateName);

		executorService.submit(
			() -> _batchEngineImportTaskExecutor.execute(
				batchEngineImportTask));

		return _toImportTask(batchEngineImportTask);
	}

	private FailedItem _toFailedItem(
		BatchEngineImportTaskError batchEngineImportTaskError) {

		return new FailedItem() {
			{
				item = batchEngineImportTaskError.getItem();
				itemIndex = batchEngineImportTaskError.getItemIndex();
				message = batchEngineImportTaskError.getMessage();
			}
		};
	}

	private int _toImportStrategy(String importStrategy) {
		if ((importStrategy == null) ||
			importStrategy.equals(
				BatchEngineImportTaskConstants.
					IMPORT_STRATEGY_STRING_ON_ERROR_FAIL)) {

			return BatchEngineImportTaskConstants.IMPORT_STRATEGY_ON_ERROR_FAIL;
		}

		return BatchEngineImportTaskConstants.IMPORT_STRATEGY_ON_ERROR_CONTINUE;
	}

	private ImportTask _toImportTask(
		BatchEngineImportTask batchEngineImportTask) {

		return new ImportTask() {
			{
				className = batchEngineImportTask.getClassName();
				contentType = batchEngineImportTask.getContentType();
				endTime = batchEngineImportTask.getEndTime();
				errorMessage = batchEngineImportTask.getErrorMessage();
				executeStatus = ImportTask.ExecuteStatus.create(
					batchEngineImportTask.getExecuteStatus());
				failedItems = TransformUtil.transformToArray(
					batchEngineImportTask.getBatchEngineImportTaskErrors(),
					batchEngineImportTaskError -> _toFailedItem(
						batchEngineImportTaskError),
					FailedItem.class);
				id = batchEngineImportTask.getBatchEngineImportTaskId();
				importStrategy = ImportTask.ImportStrategy.create(
					BatchEngineImportTaskConstants.getImportStrategyString(
						batchEngineImportTask.getImportStrategy()));
				operation = ImportTask.Operation.create(
					batchEngineImportTask.getOperation());
				processedItemsCount =
					batchEngineImportTask.getProcessedItemsCount();
				startTime = batchEngineImportTask.getStartTime();
				totalItemsCount = batchEngineImportTask.getTotalItemsCount();
			}
		};
	}

	private Map<String, String> _toMap(String fieldNameMappingString) {
		if (Validator.isNull(fieldNameMappingString)) {
			return Collections.emptyMap();
		}

		Map<String, String> fieldNameMappingMap = new HashMap<>();

		String[] fieldNameMappings = StringUtil.split(
			fieldNameMappingString, ',');

		for (String fieldNameMapping : fieldNameMappings) {
			String[] fieldNames = StringUtil.split(fieldNameMapping, '=');

			fieldNameMappingMap.put(fieldNames[0], fieldNames[1]);
		}

		return fieldNameMappingMap;
	}

	private static final Set<String> _ignoredParameters = new HashSet<>(
		Arrays.asList("callbackURL", "fieldNameMapping"));

	@Reference
	private BatchEngineImportTaskErrorLocalService
		_batchEngineImportTaskErrorLocalService;

	@Reference
	private BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;

	@Reference
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	private int _batchSize;

	@Reference
	private File _file;

	private final Map<String, Integer> _itemClassBatchSizeMap = new HashMap<>();

	@Reference
	private ItemClassRegistry _itemClassRegistry;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

}