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

package com.liferay.portal.search.web.internal.folder.facet.portlet;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.folder.FolderFacetFactory;

/**
 * @author Lino Alves
 */
public class FolderFacetBuilder {

	public FolderFacetBuilder(FolderFacetFactory folderFacetFactory) {
		_folderFacetFactory = folderFacetFactory;
	}

	public Facet build() {
		Facet facet = _folderFacetFactory.newInstance(_searchContext);

		facet.setFacetConfiguration(buildFacetConfiguration(facet));

		if (_selectedFolderIds != null) {
			facet.select(ArrayUtil.toStringArray(_selectedFolderIds));
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

	public void setSelectedFolderIds(long... selectedFolderIds) {
		_selectedFolderIds = selectedFolderIds;
	}

	protected FacetConfiguration buildFacetConfiguration(Facet facet) {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setFieldName(facet.getFieldName());
		facetConfiguration.setLabel("any-folder");
		facetConfiguration.setOrder("OrderHitsDesc");
		facetConfiguration.setStatic(false);
		facetConfiguration.setWeight(1.4);

		FolderFacetConfiguration folderFacetConfiguration =
			new FolderFacetConfigurationImpl(facetConfiguration);

		folderFacetConfiguration.setFrequencyThreshold(_frequencyThreshold);
		folderFacetConfiguration.setMaxTerms(_maxTerms);

		return facetConfiguration;
	}

	private final FolderFacetFactory _folderFacetFactory;
	private int _frequencyThreshold;
	private int _maxTerms;
	private SearchContext _searchContext;
	private long[] _selectedFolderIds;

}