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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.search.web.util.SearchFacet;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, service = SearchFacet.class
)
public class AssetEntriesSearchFacet extends BaseSearchFacet {

	public List<AssetRendererFactory> getAssetRendererFactories() {
		return _assetRendererFactories;
	}

	@Override
	public String getClassName() {
		return AssetEntriesSearchFacet.class.getName();
	}

	@Override
	public FacetConfiguration getDefaultConfiguration() {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setClassName(AssetEntriesFacet.class.getName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("frequencyThreshold", 1);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (AssetRendererFactory assetRendererFactory :
				_assetRendererFactories) {

			jsonArray.put(assetRendererFactory.getClassName());
		}

		jsonObject.put("values", jsonArray);

		facetConfiguration.setDataJSONObject(jsonObject);
		facetConfiguration.setDisplayStyle("asset_entries");
		facetConfiguration.setFieldName(Field.ENTRY_CLASS_NAME);
		facetConfiguration.setLabel("asset-type");
		facetConfiguration.setOrder("OrderHitsDesc");
		facetConfiguration.setStatic(false);
		facetConfiguration.setWeight(1.5);

		return facetConfiguration;
	}

	@Override
	public String getTitle() {
		return "asset-type";
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(search.asset.type=*)", unbind = "removeAssetRendererFactory"
	)
	protected void addAssetRendererFactory(
		AssetRendererFactory assetRendererFactory) {

		_assetRendererFactories.add(assetRendererFactory);
	}

	protected void removeAssetRendererFactory(
		AssetRendererFactory assetRendererFactory) {

		_assetRendererFactories.remove(assetRendererFactory);
	}

	private final List<AssetRendererFactory> _assetRendererFactories =
		new CopyOnWriteArrayList<>();

}