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

package com.liferay.portal.search.elasticsearch6.internal;

/**
 * @author André de Oliveira
 */
public class LiferayElasticsearchIndexingFixtureFactory {

	public static ElasticsearchIndexingFixtureBuilder builder() {
		return ElasticsearchIndexingFixtureFactory.builder(
		).liferayMappingsAddedToIndex(
			true
		);
	}

	public static ElasticsearchIndexingFixture getInstance() {
		return _elasticsearchIndexingFixture;
	}

	private static ElasticsearchIndexingFixture _buildInstance() {
		ElasticsearchIndexingFixtureBuilder
			elasticsearchIndexingFixtureBuilder = builder();

		return elasticsearchIndexingFixtureBuilder.build();
	}

	private static final ElasticsearchIndexingFixture
		_elasticsearchIndexingFixture = _buildInstance();

}