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

package com.liferay.portal.search.solr7.internal.facet;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.search.solr7.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.facet.BaseAggregationFilteringTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import java.util.Collections;

import org.apache.solr.client.solrj.SolrQuery;

/**
 * @author André de Oliveira
 */
public class AggregationFilteringTest extends BaseAggregationFilteringTestCase {

	protected void addFacetProcessor(
		String className, FacetProcessor<SolrQuery> facetProcessor,
		CompositeFacetProcessor compositeFacetProcessor) {

		compositeFacetProcessor.setFacetProcessor(
			facetProcessor, Collections.singletonMap("class.name", className));
	}

	protected FacetProcessor createFacetProcessor() {
		CompositeFacetProcessor compositeFacetProcessor =
			new CompositeFacetProcessor();

		compositeFacetProcessor.setDefaultFacetProcessor(
			new DefaultFacetProcessor() {
				{
					jsonFactory = _jsonFactory;
				}
			});

		addFacetProcessor(
			"com.liferay.portal.search.internal.facet.ModifiedFacetImpl",
			new ModifiedFacetProcessor() {
				{
					jsonFactory = _jsonFactory;
				}
			},
			compositeFacetProcessor);

		return compositeFacetProcessor;
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		SolrIndexingFixture solrIndexingFixture = new SolrIndexingFixture();

		solrIndexingFixture.setFacetProcessor(createFacetProcessor());

		return solrIndexingFixture;
	}

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}