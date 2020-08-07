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

import com.liferay.commerce.frontend.clay.data.set.ClayAutocompleteDataSetFilter;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetFilter;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetFilterContextContributor;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = "commerce.data.set.filter.type=autocomplete",
	service = ClayDataSetFilterContextContributor.class
)
public class ClayAutocompleteDataSetFilterContextContributor
	implements ClayDataSetFilterContextContributor {

	public Map<String, Object> getClayDataSetFilterContext(
		ClayDataSetFilter clayDataSetFilter, Locale locale) {

		if (clayDataSetFilter instanceof ClayAutocompleteDataSetFilter) {
			return _serialize(
				(ClayAutocompleteDataSetFilter)clayDataSetFilter, locale);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		ClayAutocompleteDataSetFilter clayAutocompleteDataSetFilter,
		Locale locale) {

		return HashMapBuilder.<String, Object>put(
			"inputPlaceholder",
			ResourceBundleUtil.getString(
				clayAutocompleteDataSetFilter.getResourceBundle(locale),
				clayAutocompleteDataSetFilter.getPlaceholder())
		).put(
			"itemKey", clayAutocompleteDataSetFilter.getItemKey()
		).put(
			"itemLabel", clayAutocompleteDataSetFilter.getItemLabel()
		).put(
			"userGroup",
			() -> {
				String selectionType = "single";

				if (clayAutocompleteDataSetFilter.isMultipleSelection()) {
					selectionType = "multiple";
				}

				return selectionType;
			}
		).build();
	}

}