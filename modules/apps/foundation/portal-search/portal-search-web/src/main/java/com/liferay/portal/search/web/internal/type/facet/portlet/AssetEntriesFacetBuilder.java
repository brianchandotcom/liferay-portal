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

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacetFactory;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

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

		return facet;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
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

		assetEntriesFacetConfiguration.setClassNames(getAssetTypes(_companyId));
		assetEntriesFacetConfiguration.setFrequencyThreshold(
			_frequencyThreshold);

		if (_selectedTypes != null) {
			assetEntriesFacetConfiguration.setSelectedTypes(_selectedTypes);
		}

		return facetConfiguration;
	}

	protected String[] getAssetTypes(long companyId) {
		List<String> assetTypes = new ArrayList<>();

		List<AssetRendererFactory<?>> assetRendererFactories =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				companyId);

		for (int i = 0; i < assetRendererFactories.size(); i++) {
			AssetRendererFactory<?> assetRendererFactory =
				assetRendererFactories.get(i);

			if (!assetRendererFactory.isSearchable()) {
				continue;
			}

			assetTypes.add(assetRendererFactory.getClassName());
		}

		return ArrayUtil.toStringArray(assetTypes);
	}

	private final AssetEntriesFacetFactory _assetEntriesFacetFactory;
	private long _companyId;
	private int _frequencyThreshold;
	private SearchContext _searchContext;
	private String[] _selectedTypes;

}