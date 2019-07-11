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

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.facet.FacetProcessor;

import org.elasticsearch.action.search.SearchRequestBuilder;

/**
 * @author André de Oliveira
 */
public class ElasticsearchIndexingFixtureBuilder {

	public ElasticsearchIndexingFixture build() {
		ElasticsearchIndexingFixture elasticsearchIndexingFixture =
			new ElasticsearchIndexingFixture();

		elasticsearchIndexingFixture.setElasticsearchFixture(
			getElasticsearchFixture());
		elasticsearchIndexingFixture.setFacetProcessor(_facetProcessor);
		elasticsearchIndexingFixture.setLiferayMappingsAddedToIndex(
			_liferayMappingsAddedToIndex);

		return elasticsearchIndexingFixture;
	}

	public ElasticsearchIndexingFixtureBuilder elasticsearchFixture(
		ElasticsearchFixture elasticsearchFixture) {

		_elasticsearchFixture = elasticsearchFixture;

		return this;
	}

	public ElasticsearchIndexingFixtureBuilder facetProcessor(
		FacetProcessor<SearchRequestBuilder> facetProcessor) {

		_facetProcessor = facetProcessor;

		return this;
	}

	public ElasticsearchIndexingFixtureBuilder liferayMappingsAddedToIndex(
		boolean liferayMappingsAddedToIndex) {

		_liferayMappingsAddedToIndex = liferayMappingsAddedToIndex;

		return this;
	}

	protected ElasticsearchFixture getElasticsearchFixture() {
		if (_elasticsearchFixture != null) {
			return _elasticsearchFixture;
		}

		return new ElasticsearchFixture(RandomTestUtil.randomString());
	}

	private ElasticsearchFixture _elasticsearchFixture;
	private FacetProcessor<SearchRequestBuilder> _facetProcessor;
	private boolean _liferayMappingsAddedToIndex;

}