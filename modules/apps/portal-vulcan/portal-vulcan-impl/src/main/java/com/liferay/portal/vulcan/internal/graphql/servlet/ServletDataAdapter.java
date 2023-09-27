/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.servlet;

import com.liferay.portal.vulcan.graphql.contributor.GraphQLContributor;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

/**
 * @author Javier de Arcos
 */
public class ServletDataAdapter implements ServletData {

	public static ServletData of(GraphQLContributor graphQLContributor) {
		return new ServletDataAdapter(graphQLContributor);
	}

	@Override
	public String getGraphQLNamespace() {
		return _graphQLContributor.getGraphQLNamespace();
	}

	@Override
	public Object getMutation() {
		return _graphQLContributor.getMutation();
	}

	@Override
	public String getPath() {
		return _graphQLContributor.getPath();
	}

	@Override
	public Object getQuery() {
		return _graphQLContributor.getQuery();
	}

	@Override
	public boolean isJaxRsResourceInvocation() {
		return _graphQLContributor.isJaxRsResourceInvocation();
	}

	private ServletDataAdapter(GraphQLContributor graphQLContributor) {
		_graphQLContributor = graphQLContributor;
	}

	private final GraphQLContributor _graphQLContributor;

}