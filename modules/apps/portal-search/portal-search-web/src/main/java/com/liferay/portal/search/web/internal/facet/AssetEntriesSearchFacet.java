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

package com.liferay.portal.search.web.internal.facet;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.FacetFactory;
import com.liferay.portal.search.facet.type.AssetEntriesFacetFactory;
import com.liferay.portal.search.web.facet.BaseJSPSearchFacet;
import com.liferay.portal.search.web.facet.SearchFacet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = SearchFacet.class)
public class AssetEntriesSearchFacet extends BaseJSPSearchFacet {

	public static String[] getEntryClassNames(String configuration) {
		if (Validator.isNull(configuration)) {
			return null;
		}

		JSONObject configurationJSONObject;

		try {
			configurationJSONObject = JSONFactoryUtil.createJSONObject(
				configuration);
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to parse configuration", jsone.getCause());
			}

			return null;
		}

		JSONArray jsonArray = configurationJSONObject.getJSONArray("facets");

		if (jsonArray == null) {
			return null;
		}

		String id = AssetEntriesSearchFacet.class.getName();

		IntStream intStream = IntStream.range(0, jsonArray.length());

		Stream<JSONObject> jsonObjectsStream = intStream.mapToObj(
			jsonArray::getJSONObject);

		return jsonObjectsStream.filter(
			jsonObject -> id.equals(jsonObject.getString("id"))
		).map(
			jsonObject -> jsonObject.getJSONObject("data")
		).filter(
			Objects::nonNull
		).map(
			jsonObject -> jsonObject.getJSONArray("values")
		).filter(
			Objects::nonNull
		).map(
			ArrayUtil::toStringArray
		).findAny(
		).orElse(
			null
		);
	}

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

		JSONObject jsonObject = JSONUtil.put("frequencyThreshold", 1);

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
		return assetEntriesFacetFactory.getFacetClassName();
	}

	@Override
	public String getFieldName() {
		Facet facet = assetEntriesFacetFactory.newInstance(null);

		return facet.getFieldName();
	}

	@Override
	public JSONObject getJSONData(ActionRequest actionRequest) {
		int frequencyThreshold = ParamUtil.getInteger(
			actionRequest, getClassName() + "frequencyThreshold", 1);

		JSONObject jsonObject = JSONUtil.put(
			"frequencyThreshold", frequencyThreshold);

		String[] assetTypes = StringUtil.split(
			ParamUtil.getString(actionRequest, getClassName() + "assetTypes"));

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (ArrayUtil.isEmpty(assetTypes)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

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
		target = "(osgi.web.symbolicname=com.liferay.portal.search.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	protected String[] getAssetTypes(long companyId) {
		List<String> assetTypes = new ArrayList<>();

		List<AssetRendererFactory<?>> assetRendererFactories =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				companyId);

		for (AssetRendererFactory<?> assetRendererFactory :
				assetRendererFactories) {

			if (!assetRendererFactory.isSearchable()) {
				continue;
			}

			assetTypes.add(assetRendererFactory.getClassName());
		}

		return ArrayUtil.toStringArray(assetTypes);
	}

	@Override
	protected FacetFactory getFacetFactory() {
		return assetEntriesFacetFactory;
	}

	@Reference
	protected AssetEntriesFacetFactory assetEntriesFacetFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntriesSearchFacet.class);

}