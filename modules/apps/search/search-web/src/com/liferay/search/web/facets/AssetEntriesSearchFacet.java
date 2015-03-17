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
import com.liferay.search.web.util.SearchFacet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, service = SearchFacet.class
)
public class AssetEntriesSearchFacet extends BaseSearchFacet {

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

		jsonArray.put("com.liferay.portal.model.User");
		jsonArray.put("com.liferay.portlet.blogs.model.BlogsEntry");
		jsonArray.put("com.liferay.portlet.documentlibrary.model.DLFileEntry");
		jsonArray.put("com.liferay.portlet.documentlibrary.model.DLFolder");
		jsonArray.put("com.liferay.portlet.journal.model.JournalArticle");
		jsonArray.put("com.liferay.portlet.journal.model.JournalFolder");
		jsonArray.put("com.liferay.portlet.messageboards.model.MBMessage");
		jsonArray.put("com.liferay.portlet.wiki.model.WikiPage");

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

}