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

package com.liferay.portal.vulcan.internal.graphql.servlet.data.fetcher;

import com.liferay.portal.vulcan.internal.graphql.servlet.data.processor.LiferayMethodDataFetchingProcessor;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;

import graphql.servlet.GraphQLContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Carlos Correa
 */
public class LiferayMethodDataFetcher implements DataFetcher<Object> {

	public LiferayMethodDataFetcher(
		LiferayMethodDataFetchingProcessor liferayMethodDataFetchingProcessor,
		Method method) {

		_liferayMethodDataFetchingProcessor =
			liferayMethodDataFetchingProcessor;
		_method = method;
	}

	@Override
	public Object get(DataFetchingEnvironment dataFetchingEnvironment) {
		try {
			GraphQLFieldDefinition graphQLFieldDefinition =
				dataFetchingEnvironment.getFieldDefinition();

			return _liferayMethodDataFetchingProcessor.process(
				dataFetchingEnvironment.getArguments(),
				graphQLFieldDefinition.getName(),
				_getHttpServletRequest(dataFetchingEnvironment),
				_getHttpServletResponse(dataFetchingEnvironment), _method,
				dataFetchingEnvironment.getRoot(),
				dataFetchingEnvironment.getSource());
		}
		catch (InvocationTargetException invocationTargetException) {
			throw new RuntimeException(
				invocationTargetException.getTargetException());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private HttpServletRequest _getHttpServletRequest(
		DataFetchingEnvironment dataFetchingEnvironment) {

		GraphQLContext graphQLContext = dataFetchingEnvironment.getContext();

		Optional<HttpServletRequest> httpServletRequestOptional =
			graphQLContext.getHttpServletRequest();

		return httpServletRequestOptional.orElse(null);
	}

	private HttpServletResponse _getHttpServletResponse(
		DataFetchingEnvironment dataFetchingEnvironment) {

		GraphQLContext graphQLContext = dataFetchingEnvironment.getContext();

		Optional<HttpServletResponse> httpServletResponseOptional =
			graphQLContext.getHttpServletResponse();

		return httpServletResponseOptional.orElse(null);
	}

	private final LiferayMethodDataFetchingProcessor
		_liferayMethodDataFetchingProcessor;
	private final Method _method;

}