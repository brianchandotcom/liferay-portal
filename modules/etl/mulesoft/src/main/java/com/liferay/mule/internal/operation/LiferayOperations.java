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

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.MetadataKeyId;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.metadata.TypeResolver;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.NullSafe;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

/**
 * @author Matija Petanjek
 */
@Throws(LiferayResponseErrorProvider.class)
public class LiferayOperations {

	@MediaType(MediaType.APPLICATION_JSON)
	@OutputResolver(output = DELETEEndpointOutputTypeResolver.class)
	public Result<InputStream, Void> delete(
			@Connection LiferayConnection connection,
			@MetadataKeyId(DELETEEndpointTypeKeysResolver.class)
				String endpoint)
		throws IOException, TimeoutException {

		HttpResponse httpResponse = connection.delete(
			_pathParams, _queryParams, endpoint);

		_liferayResponseValidator.validate(httpResponse);

		HttpEntity httpEntity = httpResponse.getEntity();

		InputStream inputStream = httpEntity.getContent();

		return Result.<InputStream, Void>builder(
		).output(
			inputStream
		).build();
	}

	@MediaType(MediaType.APPLICATION_JSON)
	@OutputResolver(output = GETEndpointOutputTypeResolver.class)
	public Result<InputStream, Void> get(
			@Connection LiferayConnection connection,
			@MetadataKeyId(GETEndpointTypeKeysResolver.class) String endpoint)
		throws Exception {

		HttpResponse httpResponse = connection.get(
			_pathParams, _queryParams, endpoint);

		_liferayResponseValidator.validate(httpResponse);

		HttpEntity httpEntity = httpResponse.getEntity();

		InputStream inputStream = httpEntity.getContent();

		return Result.<InputStream, Void>builder(
		).output(
			inputStream
		).build();
	}

	@MediaType(MediaType.APPLICATION_JSON)
	@OutputResolver(output = PATCHEndpointOutputTypeResolver.class)
	public Result<InputStream, Void> patch(
			@Connection LiferayConnection connection,
			@MetadataKeyId(PATCHEndpointTypeKeysResolver.class) String endpoint,
			@Content @DisplayName("Records")
			@TypeResolver(value = PATCHEndpointInputTypeResolver.class)
				InputStream inputStream)
		throws IOException, TimeoutException {

		HttpResponse httpResponse = connection.patch(
			inputStream, _pathParams, _queryParams, endpoint);

		_liferayResponseValidator.validate(httpResponse);

		HttpEntity httpEntity = httpResponse.getEntity();

		return Result.<InputStream, Void>builder(
		).output(
			httpEntity.getContent()
		).build();
	}

	@MediaType(MediaType.APPLICATION_JSON)
	@OutputResolver(output = POSTEndpointOutputTypeResolver.class)
	public Result<InputStream, Void> post(
			@Connection LiferayConnection connection,
			@MetadataKeyId(POSTEndpointTypeKeysResolver.class) String endpoint,
			@Content @DisplayName("Records")
			@TypeResolver(value = POSTEndpointInputTypeResolver.class)
				InputStream inputStream)
		throws IOException, TimeoutException {

		HttpResponse httpResponse = connection.post(
			inputStream, _pathParams, _queryParams, endpoint);

		_liferayResponseValidator.validate(httpResponse);

		HttpEntity httpEntity = httpResponse.getEntity();

		return Result.<InputStream, Void>builder(
		).output(
			httpEntity.getContent()
		).build();
	}

	private final LiferayResponseValidator _liferayResponseValidator =
		new LiferayResponseValidator();

	@DisplayName("Path Parameters")
	@Expression(ExpressionSupport.NOT_SUPPORTED)
	@NullSafe
	@Optional
	@Parameter
	private Map<String, String> _pathParams;

	@DisplayName("Query Parameters")
	@Expression(ExpressionSupport.NOT_SUPPORTED)
	@NullSafe
	@Optional
	@Parameter
	private MultiMap<String, String> _queryParams;

}