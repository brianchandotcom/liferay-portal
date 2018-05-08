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

package com.liferay.portal.search.web.internal.type.facet.portlet;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.type.AssetEntriesFacetFactory;

/**
 * @author Lino Alves
 */
public class AssetEntriesFacetBuilder {

	public AssetEntriesFacetBuilder(
		AssetEntriesFacetFactory assetEntriesFacetFactory) {

		_assetEntriesFacetFactory = assetEntriesFacetFactory;
	}

	public Facet build() {
		Facet facet = _assetEntriesFacetFactory.newInstance(_searchContext);

		facet.setFacetConfiguration(buildFacetConfiguration(facet));

		facet.select(_selectedTypes);

		return facet;
	}

	public void setFrequencyThreshold(int frequencyThreshold) {
		_frequencyThreshold = frequencyThreshold;
	}

	public void setSearchContext(SearchContext searchContext) {
		_searchContext = searchContext;
	}

	public void setSelectedTypes(String... selectedTypes) {
		_selectedTypes = selectedTypes;
	}

	protected FacetConfiguration buildFacetConfiguration(Facet facet) {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setFieldName(facet.getFieldName());
		facetConfiguration.setLabel("any-asset");
		facetConfiguration.setOrder("OrderHitsDesc");
		facetConfiguration.setStatic(false);
		facetConfiguration.setWeight(1.6);

		AssetEntriesFacetConfiguration assetEntriesFacetConfiguration =
			new AssetEntriesFacetConfigurationImpl(facetConfiguration);

		assetEntriesFacetConfiguration.setFrequencyThreshold(
			_frequencyThreshold);

		return facetConfiguration;
	}

	private final AssetEntriesFacetFactory _assetEntriesFacetFactory;
	private int _frequencyThreshold;
	private SearchContext _searchContext;
	private String[] _selectedTypes;

}