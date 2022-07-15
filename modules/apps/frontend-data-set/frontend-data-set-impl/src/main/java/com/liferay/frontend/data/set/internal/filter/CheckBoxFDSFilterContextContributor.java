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

import com.liferay.frontend.data.set.filter.BaseCheckBoxFDSFilter;
import com.liferay.frontend.data.set.filter.CheckBoxFDSFilterItem;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilterContextContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
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
	service = FDSFilterContextContributor.class
)
public class CheckBoxFDSFilterContextContributor
	implements FDSFilterContextContributor {

	@Override
	public Map<String, Object> getFDSFilterContext(
		FDSFilter fdsFilter, Locale locale) {

		if (fdsFilter instanceof BaseCheckBoxFDSFilter) {
			return _serialize((BaseCheckBoxFDSFilter)fdsFilter, locale);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseCheckBoxFDSFilter baseCheckBoxFDSFilter, Locale locale) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		List<CheckBoxFDSFilterItem> checkBoxFDSFilterItems =
			baseCheckBoxFDSFilter.getCheckBoxFDSFilterItems(locale);

		for (CheckBoxFDSFilterItem checkBoxFDSFilterItem :
				checkBoxFDSFilterItems) {

			jsonArray.put(
				JSONUtil.put(
					"label",
					_language.get(
						resourceBundle, checkBoxFDSFilterItem.getLabel())
				).put(
					"value", checkBoxFDSFilterItem.getValue()
				));
		}

		return HashMapBuilder.<String, Object>put(
			"items", jsonArray
		).build();
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}