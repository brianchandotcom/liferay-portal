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
import com.liferay.portal.kernel.search.facet.ModifiedFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.search.web.util.SearchFacet;

import javax.portlet.ActionRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, service = SearchFacet.class
)
public class ModifiedSearchFacet extends BaseSearchFacet {

	@Override
	public String getClassName() {
		return ModifiedFacet.class.getName();
	}

	@Override
	public String getConfigurationView() {
		return "/facets/configuration/modified.jsp";
	}

	@Override
	public FacetConfiguration getDefaultConfiguration() {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setClassName(getClassName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("frequencyThreshold", 0);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < _labels.length; i++) {
			JSONObject range = JSONFactoryUtil.createJSONObject();

			range.put("label", _labels[i]);
			range.put("range", _ranges[i]);

			jsonArray.put(range);
		}

		jsonObject.put("ranges", jsonArray);

		facetConfiguration.setDataJSONObject(jsonObject);
		facetConfiguration.setFieldName(getFieldName());
		facetConfiguration.setLabel(getLabel());
		facetConfiguration.setOrder(getOrder());
		facetConfiguration.setStatic(false);
		facetConfiguration.setWeight(1.0);

		return facetConfiguration;
	}

	@Override
		public String getId() {
			return ModifiedSearchFacet.class.getName();
		}

	public String getDisplayView() {
		return "/facets/view/modified.jsp";
	}

	@Override
	public String getFieldName() {
		return Field.MODIFIED_DATE;
	}

	@Override
	public JSONObject getJSONData(ActionRequest actionRequest) {
		JSONObject data = JSONFactoryUtil.createJSONObject();

		int frequencyThreshold = ParamUtil.getInteger(
			actionRequest, getClassName() + "frequencyThreshold", 1);

		data.put("frequencyThreshold", frequencyThreshold);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		String[] rangesIndexes = StringUtil.split(
			ParamUtil.getString(
				actionRequest, getClassName() + "rangesIndexes"));

		for (String rangesIndex : rangesIndexes) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			String label = ParamUtil.getString(
				actionRequest, getClassName() + "label_" + rangesIndex);
			String range = ParamUtil.getString(
				actionRequest, getClassName() + "range_" + rangesIndex);

			jsonObject.put("label", label);
			jsonObject.put("range", range);

			jsonArray.put(jsonObject);
		}

		data.put("ranges", jsonArray);

		return data;
	}

	@Override
	public String getLabel() {
		return "modified";
	}

	@Override
	public String getTitle() {
		return "modified-date";
	}

	private final String[] _labels = new String[] {
		"past-hour", "past-24-hours", "past-week", "past-month", "past-year"
	};
	private final String[] _ranges = new String[] {
		"[past-hour TO *]", "[past-24-hours TO *]", "[past-week TO *]",
		"[past-month TO *]", "[past-year TO *]"
	};

}