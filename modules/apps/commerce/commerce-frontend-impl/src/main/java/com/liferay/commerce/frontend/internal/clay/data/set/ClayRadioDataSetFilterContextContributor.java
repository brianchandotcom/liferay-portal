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

package com.liferay.commerce.frontend.internal.clay.data.set;

import com.liferay.commerce.frontend.clay.data.set.ClayDataSetFilter;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetFilterContextContributor;
import com.liferay.commerce.frontend.clay.data.set.ClayRadioDataSetFilter;
import com.liferay.commerce.frontend.clay.data.set.ClayRadioDataSetFilterItem;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "commerce.data.set.filter.type=radio",
	service = ClayDataSetFilterContextContributor.class
)
public class ClayRadioDataSetFilterContextContributor
	implements ClayDataSetFilterContextContributor {

	public Map<String, Object> getClayDataSetFilterContext(
		ClayDataSetFilter clayDataSetFilter, Locale locale) {

		if (clayDataSetFilter instanceof ClayRadioDataSetFilter) {
			return _serialize(
				(ClayRadioDataSetFilter)clayDataSetFilter, locale);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		ClayRadioDataSetFilter clayRadioDataSetFilter, Locale locale) {

		Map<String, Object> context = new HashMap<>();

		List<ClayRadioDataSetFilterItem> clayRadioDataSetFilterItems =
			clayRadioDataSetFilter.getClayRadioDataSetFilterItems(locale);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (ClayRadioDataSetFilterItem clayRadioDataSetFilterIte :
				clayRadioDataSetFilterItems) {

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			String label = LanguageUtil.get(
				resourceBundle, clayRadioDataSetFilterIte.getLabel());

			jsonObject.put("label", label);

			jsonObject.put("value", clayRadioDataSetFilterIte.getValue());

			jsonArray.put(jsonObject);
		}

		context.put("items", jsonArray);

		return context;
	}

	@Reference
	private JSONFactory _jsonFactory;

}