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
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ScopeFacet;
import com.liferay.portal.kernel.search.facet.ScopeFacetFactory;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Arrays;

/**
 * @author André de Oliveira
 */
public class ScopeFacetBuilder {

	public ScopeFacetBuilder(ScopeFacetFactory scopeFacetFactory) {
		_scopeFacetFactory = scopeFacetFactory;
	}

	public Facet build() {
		Facet facet = _scopeFacetFactory.newInstance(_searchContext);

		facet.setFacetConfiguration(buildFacetConfiguration(facet));

		if (_selectedSites != null) {
			ScopeFacet scopeFacet = (ScopeFacet)facet;

			scopeFacet.setValues(
				ListUtil.toLongArray(
					Arrays.asList(_selectedSites), GetterUtil::getLong));
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

	public void setSelectedSites(String... selectedSites) {
		_selectedSites = selectedSites;
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

	private int _frequencyThreshold;
	private int _maxTerms;
	private final ScopeFacetFactory _scopeFacetFactory;
	private SearchContext _searchContext;
	private String[] _selectedSites;

}