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

package com.liferay.search.web.facets;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.search.web.util.SearchFacet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, service = SearchFacet.class
)
public class AssetCategoriesSearchFacet extends BaseSearchFacet {

	@Override
	public String getClassName() {
		return AssetCategoriesSearchFacet.class.getName();
	}

	@Override
	public FacetConfiguration getDefaultConfiguration() {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setClassName(MultiValueFacet.class.getName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("displayStyle", "list");
		jsonObject.put("frequencyThreshold", 1);
		jsonObject.put("maxTerms", 10);
		jsonObject.put("showAssetCount", true);

		facetConfiguration.setDataJSONObject(jsonObject);
		facetConfiguration.setDisplayStyle("asset_categories");
		facetConfiguration.setFieldName(Field.ASSET_CATEGORY_IDS);
		facetConfiguration.setLabel("category");
		facetConfiguration.setOrder("OrderHitsDesc");
		facetConfiguration.setStatic(false);
		facetConfiguration.setWeight(1.3);

		return facetConfiguration;
	}

	@Override
	public String getDisplayView() {
		return "/facets/asset_categories.jsp";
	}

	@Override
	public String getTitle() {
		return "category";
	}

}