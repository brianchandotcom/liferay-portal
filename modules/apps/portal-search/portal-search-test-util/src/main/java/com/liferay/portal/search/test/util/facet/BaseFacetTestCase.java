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

package com.liferay.portal.search.test.util.facet;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.search.test.util.FacetsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.QueryContributor;

import java.io.Serializable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public abstract class BaseFacetTestCase extends BaseIndexingTestCase {

	protected void addDocument(String... values) throws Exception {
		addDocument(DocumentCreationHelpers.singleText(getField(), values));
	}

	protected void addDocuments(int count, String... values) throws Exception {
		for (int i = 0; i < count; i++) {
			addDocument(values);
		}
	}

	protected void assertSearchFacet(Consumer<FacetTestHelper> consumer)
		throws Exception {

		assertSearch(
			indexingTestHelper -> consumer.accept(
				new FacetTestHelper(indexingTestHelper)));
	}

	protected abstract String getField();

	protected Facet initFacet(Facet facet) {
		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		facetConfiguration.setDataJSONObject(jsonFactory.createJSONObject());

		return facet;
	}

	protected final JSONFactory jsonFactory = new JSONFactoryImpl();

	protected class FacetTestHelper {

		public FacetTestHelper(IndexingTestHelper indexingTestHelper) {
			_indexingTestHelper = indexingTestHelper;
		}

		public <T extends Facet> T addFacet(
			Function<SearchContext, ? extends T> function) {

			SearchContext searchContext = getSearchContext();

			T facet = function.apply(searchContext);

			searchContext.addFacet(facet);

			return facet;
		}

		public void assertFrequencies(Facet facet, List<String> expected) {
			FacetsAssert.assertFrequencies(
				facet.getFieldName(), getSearchContext(), expected);
		}

		public void assertResultCount(int expected) {
			_indexingTestHelper.assertResultCount(expected);
		}

		public void assertValues(
			String fieldName, List<String> expectedValues) {

			_indexingTestHelper.assertValues(fieldName, expectedValues);
		}

		public SearchContext getSearchContext() {
			return _indexingTestHelper.getSearchContext();
		}

		public void search() {
			_indexingTestHelper.search();
		}

		public void setPostFilter(Filter postFilter) {
			_indexingTestHelper.setPostFilter(postFilter);
		}

		public void setQueryContributor(QueryContributor queryContributor) {
			_indexingTestHelper.setQueryContributor(queryContributor);
		}

		public void setSearchContextAttribute(String name, Serializable value) {
			_indexingTestHelper.setSearchContextAttribute(name, value);
		}

		private final IndexingTestHelper _indexingTestHelper;

	}

}