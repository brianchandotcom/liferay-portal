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

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.search.facet.nested.NestedFacetFactory;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.internal.facet.NestedFacetImpl;
import com.liferay.portal.search.internal.facet.nested.NestedFacetFactoryImpl;
import com.liferay.portal.search.internal.filter.ComplexQueryPartBuilderFactoryImpl;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

/**
 * @author Jorge Díaz
 */
public abstract class BaseNestedFieldsFacetTestCase
	extends BaseSimpleFacetTestCase {

	@Override
	protected void addDocument(String... values) throws Exception {
		addDocument(
			DocumentCreationHelpers.oneDDMField(
				getField(), getNestedDocumentValueFieldName(), values));
	}

	protected Facet createFacet(SearchContext searchContext) {
		NestedFacetImpl nestedFacetImpl =
			(NestedFacetImpl)nestedFacetFactory.newInstance(searchContext);

		initFacet(nestedFacetImpl);

		nestedFacetImpl.setFieldName(getValueFieldName());
		nestedFacetImpl.setFilterField(getFilterFieldName());
		nestedFacetImpl.setFilterValue(getField());
		nestedFacetImpl.setPath(getPath());

		return nestedFacetImpl;
	}

	@Override
	protected void filterUnmatched(
		FacetTestHelper facetTestHelper, String presentButUnmatched) {

		facetTestHelper.defineRequest(
			searchRequestBuilder -> {
				BooleanQuery booleanQuery = queries.booleanQuery();

				booleanQuery.addMustNotQueryClauses(
					queries.term(getValueFieldName(), presentButUnmatched));
				booleanQuery.addMustQueryClauses(
					queries.term(getFilterFieldName(), getField()));

				searchRequestBuilder.addComplexQueryPart(
					_complexQueryPartBuilderFactory.builder(
					).query(
						queries.nested(getPath(), booleanQuery)
					).build());
			});
	}

	@Override
	protected String getField() {
		return "ddm__keyword__40635__Field14510226_en_US";
	}

	protected String getFilterFieldName() {
		return "ddmFieldArray.ddmFieldName";
	}

	protected String getNestedDocumentValueFieldName() {
		return "ddmFieldValueKeyword_en_US";
	}

	protected String getPath() {
		return "ddmFieldArray";
	}

	protected String getValueFieldName() {
		return "ddmFieldArray.ddmFieldValueKeyword_en_US";
	}

	protected void select(
		Facet facet, String value, FacetTestHelper facetTestHelper) {

		com.liferay.portal.search.facet.Facet osgiFacet =
			(com.liferay.portal.search.facet.Facet)facet;

		osgiFacet.select(value);
	}

	protected NestedFacetFactory nestedFacetFactory =
		new NestedFacetFactoryImpl();

	private final ComplexQueryPartBuilderFactory
		_complexQueryPartBuilderFactory =
			new ComplexQueryPartBuilderFactoryImpl();

}