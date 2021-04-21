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
import com.liferay.mule.internal.connection.ResourceContext;
import com.liferay.mule.internal.error.LiferayError;
import com.liferay.mule.internal.error.LiferayResponseValidator;
import com.liferay.mule.internal.error.provider.LiferayResponseErrorProvider;
import com.liferay.mule.internal.metadata.input.BatchImportInputTypeResolver;
import com.liferay.mule.internal.metadata.key.ClassNameTypeKeysResolver;
import com.liferay.mule.internal.metadata.output.BatchExportOutputTypeResolver;
import com.liferay.mule.internal.util.IOUtil;
import com.liferay.mule.internal.util.JsonNodeReader;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipInputStream;

import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.MetadataKeyId;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.metadata.TypeResolver;
import org.mule.runtime.extension.api.annotation.param.ConfigOverride;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.NullSafe;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

/**
 * @author Matija Petanjek
 */
@Throws(LiferayResponseErrorProvider.class)
public class LiferayBatchOperations {

	@DisplayName("Batch - Import Records - Create")
	@MediaType(MediaType.APPLICATION_JSON)
	public void executeCreateImportTask(
			@Connection LiferayConnection connection,
			@MetadataKeyId(ClassNameTypeKeysResolver.class) String className,
			@NullSafe @Optional Map<String, String> fieldNameMappings,
			@Content @DisplayName("Records")
			@TypeResolver(value = BatchImportInputTypeResolver.class)
			InputStream inputStream,
			@ConfigOverride @DisplayName("Connection Timeout") @Optional
			@Placement(order = 1, tab = Placement.ADVANCED_TAB)
			@Summary("Socket connection timeout value")
			int connectionTimeout,
			@ConfigOverride @DisplayName("Connection Timeout Unit") @Optional
			@Placement(order = 2, tab = Placement.ADVANCED_TAB)
			@Summary("Time unit to be used in the timeout configurations")
			TimeUnit connectionTimeoutTimeUnit)
		throws IOException {

		long connectionTimeoutMillis = connectionTimeoutTimeUnit.toMillis(
			connectionTimeout);

		submitImportTask(
			connection, inputStream, className, fieldNameMappings,
			connectionTimeoutMillis);
	}

	@DisplayName("Batch - Import Records - Delete")
	@MediaType(MediaType.APPLICATION_JSON)
	public Result<String, Void> executeDeleteImportTask() {
		return null;
	}

	@DisplayName("Batch - Export Records")
	@MediaType(MediaType.APPLICATION_JSON)
	@OutputResolver(output = BatchExportOutputTypeResolver.class)
	public Result<InputStream, Void> executeExportTask(
			@Connection LiferayConnection connection,
			@MetadataKeyId(ClassNameTypeKeysResolver.class) String className,
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
			className, connection, fieldNames, siteId, connectionTimeoutMillis);

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

		return getExportTaskResult(
			getExportTaskContentZipInputStream(
				connection, exportTaskId, connectionTimeoutMillis));
	}

	@DisplayName("Batch - Import Records - Update")
	@MediaType(MediaType.APPLICATION_JSON)
	public Result<String, Void> executeUpdateImportTask() {
		return null;
	}

	private ZipInputStream getExportTaskContentZipInputStream(
		LiferayConnection connection, String exportTaskId,
		long connectionTimeout) {

		Map<String, String> pathParams = new HashMap<>();

		pathParams.put("exportTaskId", exportTaskId);

		ResourceContext.Builder builder = new ResourceContext.Builder();

		HttpResponse httpResponse = connection.get(
			builder.connectionTimeout(
				connectionTimeout
			).endpoint(
				"/v1.0/export-task/{exportTaskId}/content"
			).jaxRSAppBase(
				"/headless-batch-engine"
			).pathParams(
				pathParams
			).build());

		liferayResponseValidator.validate(httpResponse);

		HttpEntity httpEntity = httpResponse.getEntity();

		return new ZipInputStream(httpEntity.getContent());
	}

	private Result<InputStream, Void> getExportTaskResult(
			ZipInputStream zipInputStream)
		throws IOException {

		zipInputStream.getNextEntry();

		return Result.<InputStream, Void>builder(
		).output(
			zipInputStream
		).build();
	}

	private String getExportTaskStatus(
		LiferayConnection connection, String exportTaskId,
		long connectionTimeout) {

		Map<String, String> pathParams = new HashMap<>();

		pathParams.put("exportTaskId", exportTaskId);

		ResourceContext.Builder builder = new ResourceContext.Builder();

		HttpResponse httpResponse = connection.get(
			builder.connectionTimeout(
				connectionTimeout
			).endpoint(
				"/v1.0/export-task/{exportTaskId}"
			).jaxRSAppBase(
				"/headless-batch-engine"
			).pathParams(
				pathParams
			).build());

		liferayResponseValidator.validate(httpResponse);

		JsonNode payloadJsonNode = jsonNodeReader.fromHttpResponse(
			httpResponse);

		JsonNode exportTaskStatusJsonNode = payloadJsonNode.get(
			"executeStatus");

		return exportTaskStatusJsonNode.textValue();
	}

	private String submitExportTask(
		String className, LiferayConnection connection, String fieldNames,
		String siteId, long connectionTimeout) {

		Map<String, String> pathParams = new HashMap<>();

		pathParams.put("className", className);
		pathParams.put("contentType", BatchExportContentType.JSON.toString());

		MultiMap<String, String> queryParams = new MultiMap<>();

		if (fieldNames != null) {
			queryParams.put("fieldNames", fieldNames);
		}

		if (siteId != null) {
			queryParams.put("siteId", siteId);
		}

		ResourceContext.Builder builder = new ResourceContext.Builder();

		HttpResponse httpResponse = connection.post(
			builder.connectionTimeout(
				connectionTimeout
			).endpoint(
				"/v1.0/export-task/{className}/{contentType}"
			).jaxRSAppBase(
				"/headless-batch-engine"
			).pathParams(
				pathParams
			).queryParams(
				queryParams
			).build());

		JsonNode payloadJsonNode = jsonNodeReader.fromHttpResponse(
			httpResponse);

		JsonNode idJsonNode = payloadJsonNode.get("id");

		return String.valueOf(idJsonNode.longValue());
	}

	private String submitImportTask(
			LiferayConnection connection, InputStream inputStream,
			String className, Map<String, String> fieldNameMappings,
			long connectionTimeout)
		throws IOException {

		Map<String, String> pathParams = new HashMap<>();

		pathParams.put("className", className);

		MultiMap<String, String> queryParams = new MultiMap<>();

		if (!fieldNameMappings.isEmpty()) {
			Set<Map.Entry<String, String>> fieldNameMappingsEntries =
				fieldNameMappings.entrySet();

			Stream<Map.Entry<String, String>> fieldNameMappingsStream =
				fieldNameMappingsEntries.stream();

			queryParams.put(
				"fieldNameMappings",
				fieldNameMappingsStream.map(
					entry -> entry.getKey() + "=" + entry.getValue()
				).collect(
					Collectors.joining(",")
				));
		}

		ResourceContext.Builder builder = new ResourceContext.Builder();

		HttpResponse httpResponse = connection.post(
			builder.bytes(
				IOUtil.getBytes(inputStream)
			).connectionTimeout(
				connectionTimeout
			).endpoint(
				"/v1.0/import-task/{className}"
			).jaxRSAppBase(
				"/headless-batch-engine"
			).pathParams(
				pathParams
			).queryParams(
				queryParams
			).build());

		JsonNode payloadJsonNode = jsonNodeReader.fromHttpResponse(
			httpResponse);

		JsonNode idJsonNode = payloadJsonNode.get("id");

		return String.valueOf(idJsonNode.longValue());
	}

	private final JsonNodeReader jsonNodeReader = new JsonNodeReader();
	private final LiferayResponseValidator liferayResponseValidator =
		new LiferayResponseValidator();

}