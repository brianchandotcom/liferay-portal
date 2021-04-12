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

package com.liferay.mule.internal.operation;

import com.fasterxml.jackson.databind.JsonNode;

import com.liferay.mule.internal.connection.LiferayConnection;
import com.liferay.mule.internal.error.LiferayError;
import com.liferay.mule.internal.error.LiferayResponseValidator;
import com.liferay.mule.internal.error.provider.LiferayResponseErrorProvider;
import com.liferay.mule.internal.util.JsonNodeReader;
import com.liferay.mule.internal.values.ClassNameValueProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.mule.runtime.api.meta.model.display.PathModel;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.param.ConfigOverride;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Path;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.annotation.values.OfValues;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

/**
 * @author Matija Petanjek
 */
@Throws(LiferayResponseErrorProvider.class)
public class LiferayBatchOperations {

	@DisplayName("Batch - Import records - Create")
	@MediaType(MediaType.APPLICATION_JSON)
	public Result<String, Void> executeCreateImportTask() {
		return null;
	}

	@DisplayName("Batch - Import records - Delete")
	@MediaType(MediaType.APPLICATION_JSON)
	public Result<String, Void> executeDeleteImportTask() {
		return null;
	}

	@DisplayName("Batch - Export records")
	@MediaType(MediaType.APPLICATION_OCTET_STREAM)
	public void executeExportTask(
			@Connection LiferayConnection connection,
			@OfValues(ClassNameValueProvider.class) String className,
			BatchExportContentType batchExportContentType,
			@Path(type = PathModel.Type.DIRECTORY) String directoryPath,
			@Optional String siteId,
			@Optional @Summary("Comma-separated list") String fieldNames,
			@ConfigOverride @DisplayName("Connection Timeout") @Optional
			@Placement(order = 1, tab = Placement.ADVANCED_TAB)
			@Summary("Socket connection timeout value")
				int connectionTimeout,
			@ConfigOverride @DisplayName("Connection Timeout Unit") @Optional
			@Placement(order = 2, tab = Placement.ADVANCED_TAB)
			@Summary("Time unit to be used in the timeout configurations")
				TimeUnit connectionTimeoutTimeUnit)
		throws InterruptedException, IOException {

		long connectionTimeoutMillis = connectionTimeoutTimeUnit.toMillis(
			connectionTimeout);

		String exportTaskId = submitExportTask(
			batchExportContentType, className, connection, fieldNames, siteId,
			connectionTimeoutMillis);

		while (true) {
			String exportTaskStatus = getExportTaskStatus(
				connection, exportTaskId, connectionTimeoutMillis);

			if (exportTaskStatus.equalsIgnoreCase("completed")) {
				break;
			}
			else if (exportTaskStatus.equalsIgnoreCase("failed")) {
				throw new ModuleException(
					"Batch export failed", LiferayError.BATCH_EXPORT_FAILED);
			}

			Thread.sleep(1000);
		}

		extractExportTaskContent(
			getExportTaskContentZipInputStream(
				connection, exportTaskId, connectionTimeoutMillis),
			directoryPath);
	}

	@DisplayName("Batch - Import records - Update")
	@MediaType(MediaType.APPLICATION_JSON)
	public Result<String, Void> executeUpdateImportTask() {
		return null;
	}

	private void extractExportTaskContent(
			ZipInputStream zipInputStream, String directoryPath)
		throws IOException {

		ZipEntry zipEntry = zipInputStream.getNextEntry();

		try (FileOutputStream fileOutputStream = new FileOutputStream(
				directoryPath + File.separator + zipEntry.getName())) {

			byte[] buffer = new byte[1024];
			int length;

			while ((length = zipInputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, length);
			}
		}
	}

	private ZipInputStream getExportTaskContentZipInputStream(
		LiferayConnection connection, String exportTaskId,
		long connectionTimeout) {

		Map<String, String> pathParams = new HashMap<>();

		pathParams.put("exportTaskId", exportTaskId);

		HttpResponse httpResponse = connection.get(
			"/headless-batch-engine",
			"/v1.0/export-task/{exportTaskId}/content", pathParams,
			new MultiMap<>(), connectionTimeout);

		liferayResponseValidator.validate(httpResponse);

		HttpEntity httpEntity = httpResponse.getEntity();

		return new ZipInputStream(httpEntity.getContent());
	}

	private String getExportTaskStatus(
		LiferayConnection connection, String exportTaskId,
		long connectionTimeout) {

		Map<String, String> pathParams = new HashMap<>();

		pathParams.put("exportTaskId", exportTaskId);

		HttpResponse httpResponse = connection.get(
			"/headless-batch-engine", "/v1.0/export-task/{exportTaskId}",
			pathParams, new MultiMap<>(), connectionTimeout);

		liferayResponseValidator.validate(httpResponse);

		JsonNode payloadJsonNode = jsonNodeReader.fromHttpResponse(
			httpResponse);

		JsonNode exportTaskStatusJsonNode = payloadJsonNode.get(
			"executeStatus");

		return exportTaskStatusJsonNode.textValue();
	}

	private String submitExportTask(
		BatchExportContentType batchExportContentType, String className,
		LiferayConnection connection, String fieldNames, String siteId,
		long connectionTimeout) {

		Map<String, String> pathParams = new HashMap<>();

		pathParams.put("className", className);
		pathParams.put("contentType", batchExportContentType.toString());

		MultiMap<String, String> queryParams = new MultiMap<>();

		if (fieldNames != null) {
			queryParams.put("fieldNames", fieldNames);
		}

		if (siteId != null) {
			queryParams.put("siteId", siteId);
		}

		HttpResponse httpResponse = connection.post(
			"/headless-batch-engine",
			"/v1.0/export-task/{className}/{contentType}", null, pathParams,
			queryParams, connectionTimeout);

		JsonNode payloadJsonNode = jsonNodeReader.fromHttpResponse(
			httpResponse);

		JsonNode idJsonNode = payloadJsonNode.get("id");

		return String.valueOf(idJsonNode.longValue());
	}

	private final JsonNodeReader jsonNodeReader = new JsonNodeReader();
	private final LiferayResponseValidator liferayResponseValidator =
		new LiferayResponseValidator();

}