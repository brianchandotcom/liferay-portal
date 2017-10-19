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
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.category.CategoryFacetFactory;

/**
 * @author Lino Alves
 */
public class AssetCategoriesFacetBuilder {

	public AssetCategoriesFacetBuilder(
		CategoryFacetFactory categoryFacetFactory) {

		_categoryFacetFactory = categoryFacetFactory;
	}

	public Facet build() {
		Facet facet = _categoryFacetFactory.newInstance(_searchContext);

		facet.setFacetConfiguration(buildFacetConfiguration(facet));

		if (_selectedCategoryIds != null) {
			facet.select(ArrayUtil.toStringArray(_selectedCategoryIds));
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

	private final CategoryFacetFactory _categoryFacetFactory;
	private int _frequencyThreshold;
	private int _maxTerms;
	private SearchContext _searchContext;
	private long[] _selectedCategoryIds;

}