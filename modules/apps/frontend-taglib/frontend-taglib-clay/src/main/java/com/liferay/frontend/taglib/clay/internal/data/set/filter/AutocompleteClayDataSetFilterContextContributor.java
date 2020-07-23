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

import com.liferay.frontend.taglib.clay.data.set.filter.BaseAutocompleteClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilterContextContributor;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = "clay.data.set.filter.type=autocomplete",
	service = ClayDataSetFilterContextContributor.class
)
public class AutocompleteClayDataSetFilterContextContributor
	implements ClayDataSetFilterContextContributor {

	public Map<String, Object> getClayDataSetFilterContext(
		ClayDataSetFilter clayDataSetFilter, Locale locale) {

		if (clayDataSetFilter instanceof BaseAutocompleteClayDataSetFilter) {
			return _serialize(
				(BaseAutocompleteClayDataSetFilter)clayDataSetFilter);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseAutocompleteClayDataSetFilter baseAutocompleteClayDataSetFilter) {

		String selectionType = "single";

		if (baseAutocompleteClayDataSetFilter.isMultipleSelection()) {
			selectionType = "multiple";
		}

		return HashMapBuilder.<String, Object>put(
			"apiURL", baseAutocompleteClayDataSetFilter.getAPIURL()
		).put(
			"inputPlaceholder",
			baseAutocompleteClayDataSetFilter.getPlaceholder()
		).put(
			"itemKey", baseAutocompleteClayDataSetFilter.getItemKey()
		).put(
			"itemLabel", baseAutocompleteClayDataSetFilter.getItemLabel()
		).put(
			"selectionType", selectionType
		).build();
	}

}