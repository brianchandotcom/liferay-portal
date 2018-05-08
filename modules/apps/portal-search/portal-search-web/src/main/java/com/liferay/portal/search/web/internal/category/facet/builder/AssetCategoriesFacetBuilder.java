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

package com.liferay.portal.search.web.internal.category.facet.builder;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Lino Alves
 */
public class AssetCategoriesFacetBuilder {

	public AssetCategoriesFacetBuilder(
		AssetCategoriesFacetFactory assetCategoriesFacetFactory) {

		_assetCategoriesFacetFactory = assetCategoriesFacetFactory;
	}

	public Facet build() {
		MultiValueFacet multiValueFacet =
			_assetCategoriesFacetFactory.newInstance(_searchContext);

		multiValueFacet.setFacetConfiguration(
			buildFacetConfiguration(multiValueFacet));

		if (_selectedCategoryIds != null) {
			multiValueFacet.setValues(_selectedCategoryIds);

			_searchContext.setAttribute(
				multiValueFacet.getFieldName(),
				StringUtil.merge(_selectedCategoryIds));
		}

		return multiValueFacet;
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

	public void setSelectedCategoryIds(long... selectedCategoryIds) {
		_selectedCategoryIds = selectedCategoryIds;
	}

	protected FacetConfiguration buildFacetConfiguration(Facet facet) {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setFieldName(facet.getFieldName());
		facetConfiguration.setLabel("any-category");
		facetConfiguration.setOrder("OrderHitsDesc");
		facetConfiguration.setStatic(false);
		facetConfiguration.setWeight(1.6);

		AssetCategoriesFacetConfiguration assetCategoriesFacetConfiguration =
			new AssetCategoriesFacetConfigurationImpl(facetConfiguration);

		assetCategoriesFacetConfiguration.setFrequencyThreshold(
			_frequencyThreshold);
		assetCategoriesFacetConfiguration.setMaxTerms(_maxTerms);

		return facetConfiguration;
	}

	private final AssetCategoriesFacetFactory _assetCategoriesFacetFactory;
	private int _frequencyThreshold;
	private int _maxTerms;
	private SearchContext _searchContext;
	private long[] _selectedCategoryIds;

}