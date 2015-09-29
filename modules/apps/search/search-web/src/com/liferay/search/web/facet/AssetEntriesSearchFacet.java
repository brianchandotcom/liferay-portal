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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.search.facets.api.facet.BaseJSPSearchFacet;
import com.liferay.search.facets.api.facet.SearchFacet;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = SearchFacet.class)
public class AssetEntriesSearchFacet extends BaseJSPSearchFacet {

	public List<AssetRendererFactory<?>> getAssetRendererFactories(
		long companyId) {

		return AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
			companyId);
	}

	@Override
	public String getConfigurationJspPath() {
		return "/facets/configuration/asset_entries.jsp";
	}

	@Override
	public FacetConfiguration getDefaultConfiguration(long companyId) {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setClassName(getFacetClassName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("frequencyThreshold", 1);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String assetType : getAssetTypes(companyId)) {
			jsonArray.put(assetType);
		}

		jsonObject.put("values", jsonArray);

		facetConfiguration.setDataJSONObject(jsonObject);

		facetConfiguration.setFieldName(getFieldName());
		facetConfiguration.setLabel(getLabel());
		facetConfiguration.setOrder(getOrder());
		facetConfiguration.setStatic(false);
		facetConfiguration.setWeight(1.5);

		return facetConfiguration;
	}

	@Override
	public String getDisplayJspPath() {
		return "/facets/view/asset_entries.jsp";
	}

	@Override
	public String getFacetClassName() {
		return AssetEntriesFacet.class.getName();
	}

	@Override
	public String getFieldName() {
		return Field.ENTRY_CLASS_NAME;
	}

	@Override
	public JSONObject getJSONData(ActionRequest actionRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		int frequencyThreshold = ParamUtil.getInteger(
			actionRequest, getClassName() + "frequencyThreshold", 1);

		jsonObject.put("frequencyThreshold", frequencyThreshold);

		String[] assetTypes = StringUtil.split(
			ParamUtil.getString(actionRequest, getClassName() + "assetTypes"));

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (ArrayUtil.isEmpty(assetTypes)) {
			assetTypes = getAssetTypes(themeDisplay.getCompanyId());
		}

		for (String assetType : assetTypes) {
			jsonArray.put(assetType);
		}

		jsonObject.put("values", jsonArray);

		return jsonObject;
	}

	@Override
	public String getLabel() {
		return "any-asset";
	}

	@Override
	public String getTitle() {
		return "asset-type";
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.search.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
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

}