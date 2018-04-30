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

package com.liferay.portal.search.web.internal.custom.facet.builder;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.custom.CustomFacetFactory;

/**
 * @author Wade Cao
 */
public class CustomFacetBuilder {

	public CustomFacetBuilder(CustomFacetFactory customFacetFactory) {
		_customFacetFactory = customFacetFactory;
	}

	public Facet build() {
		Facet facet = _customFacetFactory.newInstance(_searchContext);

		facet.setFieldName(_fieldToAggregate);

		facet.setFacetConfiguration(buildFacetConfiguration(facet));

		facet.select(_selectedFields);

		facet.setAggregationName(_aggregationName);

		return facet;
	}

	public void setAggregationName(String aggregationName) {
		_aggregationName = aggregationName;
	}

	public void setFieldToAggregate(String fieldToAggregate) {
		_fieldToAggregate = fieldToAggregate;
	}

	public void setSearchContext(SearchContext searchContext) {
		_searchContext = searchContext;
	}

	public void setSelectedFields(String... selectedFields) {
		_selectedFields = selectedFields;
	}

	protected FacetConfiguration buildFacetConfiguration(Facet facet) {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setFieldName(facet.getFieldName());
		facetConfiguration.setOrder("OrderHitsDesc");
		facetConfiguration.setStatic(false);
		facetConfiguration.setWeight(1.1);

		return facetConfiguration;
	}

	private String _aggregationName;
	private final CustomFacetFactory _customFacetFactory;
	private String _fieldToAggregate;
	private SearchContext _searchContext;
	private String[] _selectedFields;

}