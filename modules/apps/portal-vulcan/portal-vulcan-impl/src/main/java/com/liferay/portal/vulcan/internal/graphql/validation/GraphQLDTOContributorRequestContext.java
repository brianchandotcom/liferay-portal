/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public String getNamespace() {
		return null;
	}

	@Override
	public Class<?> getResourceClass() {
		return _graphQLDTOContributor.getResourceClass(_operation);
	}

	@Override
	public Method getResourceMethod() {
		return _graphQLDTOContributor.getResourceMethod(_operation);
	}

	@Override
	public boolean isJaxRsResourceInvocation() {
		return true;
	}

	private final long _companyId;
	private final GraphQLDTOContributor _graphQLDTOContributor;
	private final GraphQLDTOContributor.Operation _operation;

}