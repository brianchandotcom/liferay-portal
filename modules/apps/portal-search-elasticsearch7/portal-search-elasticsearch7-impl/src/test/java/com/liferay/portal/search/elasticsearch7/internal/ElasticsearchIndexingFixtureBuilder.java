/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			_getElasticsearchFixture());
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

	private ElasticsearchFixture _getElasticsearchFixture() {
		if (_elasticsearchFixture != null) {
			return _elasticsearchFixture;
		}

		return new ElasticsearchFixture(RandomTestUtil.randomString());
	}

	private ElasticsearchFixture _elasticsearchFixture;
	private FacetProcessor<SearchRequestBuilder> _facetProcessor;
	private boolean _liferayMappingsAddedToIndex;

}