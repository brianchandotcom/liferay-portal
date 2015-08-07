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
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.search.web.util.SearchFacet;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.portlet.ActionRequest;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = SearchFacet.class)
public class AssetEntriesSearchFacet extends BaseJSPSearchFacet {

	public List<AssetRendererFactory<?>> getAssetRendererFactories() {
		return _assetRendererFactories;
	}

	@Override
	public String getConfigurationJspPath() {
		return "/META-INF/resources/facets/configuration/asset_entries.jsp";
	}

	@Override
	public FacetConfiguration getDefaultConfiguration() {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setClassName(getFacetClassName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("frequencyThreshold", 1);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String assetType : getAssetTypes()) {
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
		return "/META-INF/resources/facets/view/asset_entries.jsp";
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
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		int frequencyThreshold = ParamUtil.getInteger(
			actionRequest, getClassName() + "frequencyThreshold", 1);

		jsonObject.put("frequencyThreshold", frequencyThreshold);

		String[] assetTypes = StringUtil.split(
			ParamUtil.getString(actionRequest, getClassName() + "assetTypes"));

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (ArrayUtil.isEmpty(assetTypes)) {
			assetTypes = getAssetTypes();
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
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.search.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(search.asset.type=*)", unbind = "removeAssetRendererFactory"
	)
	protected void addAssetRendererFactory(
		AssetRendererFactory<?> assetRendererFactory) {

		_assetRendererFactories.add(assetRendererFactory);
	}

	protected String[] getAssetTypes() {
		String[] assetTypes = new String[_assetRendererFactories.size()];

		for (int i = 0; i < _assetRendererFactories.size(); i++) {
			AssetRendererFactory<?> assetRendererFactory =
				_assetRendererFactories.get(i);

			assetTypes[i] = assetRendererFactory.getClassName();
		}

		return assetTypes;
	}

	protected void removeAssetRendererFactory(
		AssetRendererFactory<?> assetRendererFactory) {

		_assetRendererFactories.remove(assetRendererFactory);
	}

	private final List<AssetRendererFactory<?>> _assetRendererFactories =
		new CopyOnWriteArrayList<>();

}