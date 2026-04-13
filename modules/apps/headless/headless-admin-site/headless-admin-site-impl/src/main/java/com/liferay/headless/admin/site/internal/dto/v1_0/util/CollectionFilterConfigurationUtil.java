/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.fragment.collection.filter.FragmentCollectionFilter;
import com.liferay.fragment.collection.filter.FragmentCollectionFilterRegistry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Javier Moral
 */
public class CollectionFilterConfigurationUtil {

	public static final String RENDERER_KEY =
		"com.liferay.fragment.renderer.collection.filter.internal." +
			"CollectionFilterFragmentRenderer";

	public static JSONObject getConfigurationJSONObject(
		FragmentCollectionFilterRegistry fragmentCollectionFilterRegistry,
		String filterKey) {

		JSONArray fieldSetsJSONArray = JSONUtil.put(
			JSONUtil.put(
				"fields",
				JSONUtil.putAll(
					JSONUtil.put(
						"dataType", "array"
					).put(
						"defaultValue", JSONFactoryUtil.createJSONArray()
					).put(
						"name", "targetCollections"
					).put(
						"type", "targetCollectionDisplay"
					),
					JSONUtil.put(
						"defaultValue", ""
					).put(
						"name", "filterKey"
					).put(
						"type", "text"
					))));

		if (Validator.isNull(filterKey) ||
			(fragmentCollectionFilterRegistry == null)) {

			return JSONUtil.put("fieldSets", fieldSetsJSONArray);
		}

		FragmentCollectionFilter fragmentCollectionFilter =
			fragmentCollectionFilterRegistry.getFragmentCollectionFilter(
				filterKey);

		if (fragmentCollectionFilter == null) {
			return JSONUtil.put("fieldSets", fieldSetsJSONArray);
		}

		JSONObject filterConfigurationJSONObject =
			fragmentCollectionFilter.getConfigurationJSONObject();

		if (filterConfigurationJSONObject == null) {
			return JSONUtil.put("fieldSets", fieldSetsJSONArray);
		}

		JSONArray filterFieldSetsJSONArray =
			filterConfigurationJSONObject.getJSONArray("fieldSets");

		if (filterFieldSetsJSONArray == null) {
			return JSONUtil.put("fieldSets", fieldSetsJSONArray);
		}

		for (int i = 0; i < filterFieldSetsJSONArray.length(); i++) {
			fieldSetsJSONArray.put(filterFieldSetsJSONArray.getJSONObject(i));
		}

		return JSONUtil.put("fieldSets", fieldSetsJSONArray);
	}

}