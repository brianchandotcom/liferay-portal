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

package com.liferay.portal.vulcan.internal.graphql.validation;

import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOContributor;
import com.liferay.portal.vulcan.graphql.validation.GraphQLRequestContext;

import java.lang.reflect.Method;

/**
 * @author Carlos Correa
 */
public class GraphQLDTOContributorRequestContext
	implements GraphQLRequestContext {

	public GraphQLDTOContributorRequestContext(
		long companyId, GraphQLDTOContributor graphQLDTOContributor,
		GraphQLDTOContributor.Operation operation) {

		_companyId = companyId;
		_graphQLDTOContributor = graphQLDTOContributor;
		_operation = operation;
	}

	@Override
	public String getApplicationName() {
		return _graphQLDTOContributor.getApplicationName();
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public Method getMethod() {
		return null;
	}

	@Override
	public String getNamespace() {
		return null;
	}

	@Override
	public Class<?> getResourceClass() {
		return _graphQLDTOContributor.getResourceClass();
	}

	@Override
	public Method getResourceMethod() {
		return _graphQLDTOContributor.getResourceMethod(_operation);
	}

	@Override
	public boolean isValidationRequired() {
		return true;
	}

	private final long _companyId;
	private final GraphQLDTOContributor _graphQLDTOContributor;
	private final GraphQLDTOContributor.Operation _operation;

}