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
import com.liferay.mule.internal.metadata.GETEndpointTypeKeysResolver;
import com.liferay.mule.internal.metadata.LiferayOutputTypeResolver;

import java.io.InputStream;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.metadata.MetadataKeyId;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.NullSafe;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

/**
 * @author Matija Petanjek
 */
public class LiferayOperations {

	@MediaType(strict = false, value = MediaType.ANY)
	public String delete() {
		throw new UnsupportedOperationException();
	}

	@MediaType(strict = false, value = MediaType.APPLICATION_JSON)
	@OutputResolver(output = LiferayOutputTypeResolver.class)
	public Result<InputStream, Void> get(
			@Connection LiferayConnection connection,
			@MetadataKeyId(GETEndpointTypeKeysResolver.class) String endpoint)
		throws Exception {

		HttpResponse httpResponse = connection.get(
			_pathParams, _queryParams, endpoint);

		HttpEntity httpEntity = httpResponse.getEntity();

		InputStream inputStream = httpEntity.getContent();

		return Result.<InputStream, Void>builder(
		).output(
			inputStream
		).build();
	}

	@MediaType(strict = false, value = MediaType.ANY)
	public String patch() {
		throw new UnsupportedOperationException();
	}

	@MediaType(strict = false, value = MediaType.ANY)
	public String post() {
		throw new UnsupportedOperationException();
	}

	@Expression(ExpressionSupport.NOT_SUPPORTED)
	@NullSafe
	@Optional
	@Parameter
	private MultiMap<String, String> _pathParams;

	@Expression(ExpressionSupport.NOT_SUPPORTED)
	@NullSafe
	@Optional
	@Parameter
	private MultiMap<String, String> _queryParams;

}