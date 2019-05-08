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

package com.liferay.portal.search.elasticsearch6.internal.facet;

import com.liferay.portal.search.elasticsearch6.internal.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.internal.facet.ModifiedFacetImpl;
import com.liferay.portal.search.test.util.facet.BaseAggregationFilteringTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import java.util.Collections;

/**
 * @author André de Oliveira
 */
public class AggregationFilteringTest extends BaseAggregationFilteringTestCase {

	protected static FacetProcessor createFacetProcessor() {
		return new CompositeFacetProcessor() {
			{
				defaultFacetProcessor = new DefaultFacetProcessor();

				setFacetProcessor(
					new ModifiedFacetProcessor(),
					Collections.singletonMap(
						"class.name", ModifiedFacetImpl.class.getName()));
			}
		};
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayElasticsearchIndexingFixtureFactory.builder(
		).facetProcessor(
			createFacetProcessor()
		).build();
	}

}