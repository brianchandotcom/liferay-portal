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

package com.liferay.search.web.facet;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.search.web.util.SearchFacet;

import javax.portlet.ActionRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = SearchFacet.class)
public class AssetCategoriesSearchFacet extends BaseSearchFacet {

	@Override
	public String getClassName() {
		return AssetCategoriesSearchFacet.class.getName();
	}

	@Override
	public String getConfigurationView() {
		return "/facets/configuration/asset_categories.jsp";
	}

	@Override
	public FacetConfiguration getDefaultConfiguration() {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setClassName(getFacetClassName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("displayStyle", "list");
		jsonObject.put("frequencyThreshold", 1);
		jsonObject.put("maxTerms", 10);
		jsonObject.put("showAssetCount", true);

		facetConfiguration.setDataJSONObject(jsonObject);

		facetConfiguration.setFieldName(getFieldName());
		facetConfiguration.setLabel(getLabel());
		facetConfiguration.setOrder(getOrder());
		facetConfiguration.setStatic(false);
		facetConfiguration.setWeight(1.3);

		return facetConfiguration;
	}

	@Override
	public String getDisplayView() {
		return "/facets/view/asset_categories.jsp";
	}

	public String getFacetClassName() {
		return MultiValueFacet.class.getName();
	}

	@Override
	public String getFieldName() {
		return Field.ASSET_CATEGORY_IDS;
	}

	@Override
	public String getId() {
		return AssetCategoriesSearchFacet.class.getName();
	}

	@Override
	public JSONObject getJSONData(ActionRequest actionRequest) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String displayStyleFacet = ParamUtil.getString(
			actionRequest, getClassName() + "displayStyleFacet", "list");
		int frequencyThreshold = ParamUtil.getInteger(
			actionRequest, getClassName() + "frequencyThreshold", 1);
		int maxTerms = ParamUtil.getInteger(
			actionRequest, getClassName() + "maxTerms", 10);
		boolean showAssetCount = ParamUtil.getBoolean(
			actionRequest, getClassName() + "showAssetCount", true);

		jsonObject.put("displayStyle", displayStyleFacet);
		jsonObject.put("frequencyThreshold", frequencyThreshold);
		jsonObject.put("maxTerms", maxTerms);
		jsonObject.put("showAssetCount", showAssetCount);

		return jsonObject;
	}

	@Override
	public String getLabel() {
		return "category";
	}

	@Override
	public String getTitle() {
		return "category";
	}

}