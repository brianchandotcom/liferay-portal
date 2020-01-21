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

import static org.mule.runtime.http.api.HttpConstants.Method;

import com.liferay.mule.internal.connection.LiferayConnection;
import com.liferay.mule.internal.error.LiferayResponseErrorProvider;
import com.liferay.mule.internal.error.LiferayResponseValidator;
import com.liferay.mule.internal.metadata.input.PATCHEndpointInputTypeResolver;
import com.liferay.mule.internal.metadata.input.POSTEndpointInputTypeResolver;
import com.liferay.mule.internal.metadata.key.DELETEEndpointTypeKeysResolver;
import com.liferay.mule.internal.metadata.key.GETEndpointTypeKeysResolver;
import com.liferay.mule.internal.metadata.key.PATCHEndpointTypeKeysResolver;
import com.liferay.mule.internal.metadata.key.POSTEndpointTypeKeysResolver;
import com.liferay.mule.internal.metadata.output.DELETEEndpointOutputTypeResolver;
import com.liferay.mule.internal.metadata.output.GETEndpointOutputTypeResolver;
import com.liferay.mule.internal.metadata.output.PATCHEndpointOutputTypeResolver;
import com.liferay.mule.internal.metadata.output.POSTEndpointOutputTypeResolver;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.core.api.util.IOUtils;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.MetadataKeyId;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.metadata.TypeResolver;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.NullSafe;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matija Petanjek
 */
@Throws(LiferayResponseErrorProvider.class)
public class LiferayOperations {

	@DisplayName("Delete Record")
	@MediaType(MediaType.APPLICATION_JSON)
	@OutputResolver(output = DELETEEndpointOutputTypeResolver.class)
	public Result<String, Void> delete(
			@Connection LiferayConnection connection,
			@MetadataKeyId(DELETEEndpointTypeKeysResolver.class)
				String endpoint,
			@DisplayName("Path Parameters") @NullSafe @Optional
				Map<String, String> pathParams,
			@DisplayName("Query Parameters") @NullSafe @Optional
				MultiMap<String, String> queryParams)
		throws IOException, TimeoutException {

		_logEndpointParams(Method.DELETE, endpoint, pathParams, queryParams);

		HttpResponse httpResponse = connection.delete(
			pathParams, queryParams, endpoint);

		_liferayResponseValidator.validate(httpResponse);

		HttpEntity httpEntity = httpResponse.getEntity();

		InputStream inputStream = httpEntity.getContent();

		return Result.<String, Void>builder(
		).output(
			IOUtils.toString(inputStream)
		).build();
	}

	@DisplayName("Get Records")
	@MediaType(MediaType.APPLICATION_JSON)
	@OutputResolver(output = GETEndpointOutputTypeResolver.class)
	public Result<String, Void> get(
			@Connection LiferayConnection connection,
			@MetadataKeyId(GETEndpointTypeKeysResolver.class) String endpoint,
			@DisplayName("Path Parameters") @NullSafe @Optional
				Map<String, String> pathParams,
			@DisplayName("Query Parameters") @NullSafe @Optional
				MultiMap<String, String> queryParams)
		throws Exception {

		_logEndpointParams(Method.GET, endpoint, pathParams, queryParams);

		HttpResponse httpResponse = connection.get(
			pathParams, queryParams, endpoint);

		_liferayResponseValidator.validate(httpResponse);

		HttpEntity httpEntity = httpResponse.getEntity();

		InputStream inputStream = httpEntity.getContent();

		return Result.<String, Void>builder(
		).output(
			IOUtils.toString(inputStream)
		).build();
	}

	@DisplayName("Update Record")
	@MediaType(MediaType.APPLICATION_JSON)
	@OutputResolver(output = PATCHEndpointOutputTypeResolver.class)
	public Result<String, Void> patch(
			@Connection LiferayConnection connection,
			@MetadataKeyId(PATCHEndpointTypeKeysResolver.class) String endpoint,
			@Content @DisplayName("Record")
			@TypeResolver(value = PATCHEndpointInputTypeResolver.class)
				InputStream inputStream,
			@DisplayName("Path Parameters") @NullSafe @Optional
				Map<String, String> pathParams,
			@DisplayName("Query Parameters") @NullSafe @Optional
				MultiMap<String, String> queryParams)
		throws IOException, TimeoutException {

		_logEndpointParams(Method.PATCH, endpoint, pathParams, queryParams);

		HttpResponse httpResponse = connection.patch(
			inputStream, pathParams, queryParams, endpoint);

		_liferayResponseValidator.validate(httpResponse);

		HttpEntity httpEntity = httpResponse.getEntity();

		return Result.<String, Void>builder(
		).output(
			IOUtils.toString(httpEntity.getContent())
		).build();
	}

	@DisplayName("Create Record")
	@MediaType(MediaType.APPLICATION_JSON)
	@OutputResolver(output = POSTEndpointOutputTypeResolver.class)
	public Result<String, Void> post(
			@Connection LiferayConnection connection,
			@MetadataKeyId(POSTEndpointTypeKeysResolver.class) String endpoint,
			@Content @DisplayName("Record")
			@TypeResolver(value = POSTEndpointInputTypeResolver.class)
				InputStream inputStream,
			@DisplayName("Path Parameters") @NullSafe @Optional
				Map<String, String> pathParams,
			@DisplayName("Query Parameters") @NullSafe @Optional
				MultiMap<String, String> queryParams)
		throws IOException, TimeoutException {

		_logEndpointParams(Method.POST, endpoint, pathParams, queryParams);

		HttpResponse httpResponse = connection.post(
			inputStream, pathParams, queryParams, endpoint);

		_liferayResponseValidator.validate(httpResponse);

		HttpEntity httpEntity = httpResponse.getEntity();

		return Result.<String, Void>builder(
		).output(
			IOUtils.toString(httpEntity.getContent())
		).build();
	}

	private void _logEndpointParams(
		Method method, String endpoint, Map<String, String> pathParams,
		Map<String, String> queryParams) {

		_logger.debug(
			"Send {} request to endpoint {}, with path parameters {} and " +
				"query parameters {}",
			method, endpoint, pathParams, queryParams);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LiferayOperations.class);

	private final LiferayResponseValidator _liferayResponseValidator =
		new LiferayResponseValidator();

}