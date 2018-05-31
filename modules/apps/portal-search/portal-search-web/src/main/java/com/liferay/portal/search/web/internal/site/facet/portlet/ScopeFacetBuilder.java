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

package com.liferay.portal.search.web.internal.site.facet.portlet;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.FacetFactory;

/**
 * @author André de Oliveira
 */
public class ScopeFacetBuilder {

	public ScopeFacetBuilder(FacetFactory facetFactory) {
		_facetFactory = facetFactory;
	}

	public Facet build() {
		Facet facet = _facetFactory.newInstance(_searchContext);

		facet.setFacetConfiguration(buildFacetConfiguration(facet));

		if (ArrayUtil.isNotEmpty(_selectedGroupIds)) {
			facet.select(_selectedGroupIds);
		}

		return facet;
	}

	public void setFrequencyThreshold(int frequencyThreshold) {
		_frequencyThreshold = frequencyThreshold;
	}

	public void setMaxTerms(int maxTerms) {
		_maxTerms = maxTerms;
	}

	public void setSearchContext(SearchContext searchContext) {
		_searchContext = searchContext;
	}

	public void setSelectedGroupIds(String... selectedGroupIds) {
		_selectedGroupIds = selectedGroupIds;
	}

	protected FacetConfiguration buildFacetConfiguration(Facet facet) {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setFieldName(facet.getFieldName());
		facetConfiguration.setLabel("any-site");
		facetConfiguration.setOrder("OrderHitsDesc");
		facetConfiguration.setStatic(false);
		facetConfiguration.setWeight(1.6);

		ScopeFacetConfiguration scopeFacetConfiguration =
			new ScopeFacetConfigurationImpl(facetConfiguration);

		scopeFacetConfiguration.setFrequencyThreshold(_frequencyThreshold);
		scopeFacetConfiguration.setMaxTerms(_maxTerms);

		return facetConfiguration;
	}

	private final FacetFactory _facetFactory;
	private int _frequencyThreshold;
	private int _maxTerms;
	private SearchContext _searchContext;
	private String[] _selectedGroupIds;

}