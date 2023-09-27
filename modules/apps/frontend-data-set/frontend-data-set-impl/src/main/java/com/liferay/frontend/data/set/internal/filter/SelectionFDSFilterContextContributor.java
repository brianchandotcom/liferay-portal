/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilterContextContributor;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
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
 * @author Marko Cikos
 */
@Component(
	property = "frontend.data.set.filter.type=selection",
	service = FDSFilterContextContributor.class
)
public class SelectionFDSFilterContextContributor
	implements FDSFilterContextContributor {

	@Override
	public Map<String, Object> getFDSFilterContext(
		FDSFilter fdsFilter, Locale locale) {

		if (fdsFilter instanceof BaseSelectionFDSFilter) {
			return _serialize((BaseSelectionFDSFilter)fdsFilter, locale);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseSelectionFDSFilter baseSelectionFDSFilter, Locale locale) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		List<SelectionFDSFilterItem> selectionFDSFilterItems =
			baseSelectionFDSFilter.getSelectionFDSFilterItems(locale);

		for (SelectionFDSFilterItem selectionFDSFilterItem :
				selectionFDSFilterItems) {

			jsonArray.put(
				JSONUtil.put(
					"label",
					_language.get(
						resourceBundle, selectionFDSFilterItem.getLabel())
				).put(
					"value", selectionFDSFilterItem.getValue()
				));
		}

		HashMapBuilder.HashMapWrapper<String, Object> builder =
			HashMapBuilder.<String, Object>put(
				"autocompleteEnabled",
				baseSelectionFDSFilter.isAutocompleteEnabled()
			).put(
				"items", jsonArray
			).put(
				"multiple", baseSelectionFDSFilter.isMultiple()
			);

		if (baseSelectionFDSFilter.isAutocompleteEnabled()) {
			builder.put(
				"apiURL", baseSelectionFDSFilter.getAPIURL()
			).put(
				"inputPlaceholder",
				_language.get(locale, baseSelectionFDSFilter.getPlaceholder())
			).put(
				"itemKey", baseSelectionFDSFilter.getItemKey()
			).put(
				"itemLabel", baseSelectionFDSFilter.getItemLabel()
			);
		}

		return builder.build();
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}