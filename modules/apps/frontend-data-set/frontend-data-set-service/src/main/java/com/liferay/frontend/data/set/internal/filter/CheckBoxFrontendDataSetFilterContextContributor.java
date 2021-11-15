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

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.frontend.data.set.filter.BaseCheckBoxFrontendDataSetFilter;
import com.liferay.frontend.data.set.filter.CheckBoxFrontendDataSetFilterItem;
import com.liferay.frontend.data.set.filter.FrontendDataSetFilter;
import com.liferay.frontend.data.set.filter.FrontendDataSetFilterContextContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Collections;
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
	property = "frontend.data.set.filter.type=checkbox",
	service = FrontendDataSetFilterContextContributor.class
)
public class CheckBoxFrontendDataSetFilterContextContributor
	implements FrontendDataSetFilterContextContributor {

	@Override
	public Map<String, Object> getFrontendDataSetFilterContext(
		FrontendDataSetFilter frontendDataSetFilter, Locale locale) {

		if (frontendDataSetFilter instanceof
				BaseCheckBoxFrontendDataSetFilter) {

			return _serialize(
				(BaseCheckBoxFrontendDataSetFilter)frontendDataSetFilter,
				locale);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseCheckBoxFrontendDataSetFilter baseCheckBoxFrontendDataSetFilter,
		Locale locale) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		List<CheckBoxFrontendDataSetFilterItem>
			checkBoxFrontendDataSetFilterItems =
				baseCheckBoxFrontendDataSetFilter.
					getCheckBoxFrontendDataSetFilterItems(locale);

		for (CheckBoxFrontendDataSetFilterItem
				checkBoxFrontendDataSetFilterItem :
					checkBoxFrontendDataSetFilterItems) {

			jsonArray.put(
				JSONUtil.put(
					"label",
					LanguageUtil.get(
						resourceBundle,
						checkBoxFrontendDataSetFilterItem.getLabel())
				).put(
					"value", checkBoxFrontendDataSetFilterItem.getValue()
				));
		}

		return HashMapBuilder.<String, Object>put(
			"items", jsonArray
		).build();
	}

	@Reference
	private JSONFactory _jsonFactory;

}