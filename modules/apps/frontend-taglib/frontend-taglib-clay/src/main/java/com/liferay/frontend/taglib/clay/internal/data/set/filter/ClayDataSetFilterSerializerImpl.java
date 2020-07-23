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

package com.liferay.frontend.taglib.clay.internal.data.set.filter;

import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilterContextContributor;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilterContextContributorRegistry;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilterRegistry;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilterSerializer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ClayDataSetFilterSerializer.class)
public class ClayDataSetFilterSerializerImpl
	implements ClayDataSetFilterSerializer {

	@Override
	public JSONArray serialize(String clayDataSetDisplayName, Locale locale) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		List<ClayDataSetFilter> clayDataSetFilters =
			_clayDataSetFilterRegistry.getClayDataSetFilters(
				clayDataSetDisplayName);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		for (ClayDataSetFilter clayDataSetFilter : clayDataSetFilters) {
			String label = LanguageUtil.get(
				resourceBundle, clayDataSetFilter.getLabel());

			JSONObject jsonObject = JSONUtil.put(
				"id", clayDataSetFilter.getId()
			).put(
				"label", label
			).put(
				"type", clayDataSetFilter.getType()
			);

			List<ClayDataSetFilterContextContributor>
				clayDataSetFilterContextContributors =
					_clayDataSetFilterContextContributorRegistry.
						getClayDataSetFilterContextContributors(
							clayDataSetFilter.getType());

			for (ClayDataSetFilterContextContributor
					clayDataSetFilterContextContributor :
						clayDataSetFilterContextContributors) {

				Map<String, Object> filterContext =
					clayDataSetFilterContextContributor.
						getClayDataSetFilterContext(clayDataSetFilter, locale);

				if (filterContext == null) {
					continue;
				}

				for (Map.Entry<String, Object> filterContextEntry :
						filterContext.entrySet()) {

					jsonObject.put(
						filterContextEntry.getKey(),
						filterContextEntry.getValue());
				}
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Reference
	private ClayDataSetFilterContextContributorRegistry
		_clayDataSetFilterContextContributorRegistry;

	@Reference
	private ClayDataSetFilterRegistry _clayDataSetFilterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

}