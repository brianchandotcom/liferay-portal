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

	private ServletDataAdapter(GraphQLContributor graphQLContributor) {
		_graphQLContributor = graphQLContributor;
	}

	private final GraphQLContributor _graphQLContributor;

}