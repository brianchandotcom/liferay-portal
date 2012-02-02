/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search.facet.config;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raymond Augé
 */
public class FacetConfigurationUtil {

	public static List<FacetConfiguration> load(String configuration) {
		List<FacetConfiguration> facetConfigurations =
			new ArrayList<FacetConfiguration>();

		try {
			if (Validator.isNull(configuration)) {
				return facetConfigurations;
			}

			JSONObject configurationJSONObject =
				JSONFactoryUtil.createJSONObject(configuration);

			JSONArray facetsJSONArray = configurationJSONObject.getJSONArray(
				"facets");

			if (facetsJSONArray == null) {
				return facetConfigurations;
			}

			for (int i = 0; i < facetsJSONArray.length(); i++) {
				JSONObject facetJSONObject = facetsJSONArray.getJSONObject(i);

				FacetConfiguration facetConfiguration = _toFacetConfiguration(
					facetJSONObject);

				facetConfigurations.add(facetConfiguration);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return facetConfigurations;
	}

	private static FacetConfiguration _toFacetConfiguration(
		JSONObject facetJSONObject) {

		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setClassName(facetJSONObject.getString("className"));

		if (facetJSONObject.has("data")) {
			facetConfiguration.setDataJSONObject(
				facetJSONObject.getJSONObject("data"));
		}

		facetConfiguration.setDisplayStyle(
			facetJSONObject.getString("displayStyle"));
		facetConfiguration.setFieldName(facetJSONObject.getString("fieldName"));
		facetConfiguration.setLabel(facetJSONObject.getString("label"));
		facetConfiguration.setOrder(facetJSONObject.getString("order"));
		facetConfiguration.setStatic(facetJSONObject.getBoolean("static"));
		facetConfiguration.setWeight(facetJSONObject.getDouble("weight"));

		return facetConfiguration;
	}

	private static Log _log = LogFactoryUtil.getLog(
		FacetConfigurationUtil.class);

}