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

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetFilterContextContributor;
import com.liferay.frontend.taglib.clay.data.set.filter.AutocompleteClayDataSetFilter;
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

		if (clayDataSetFilter instanceof AutocompleteClayDataSetFilter) {
			return _serialize((AutocompleteClayDataSetFilter)clayDataSetFilter);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		AutocompleteClayDataSetFilter autocompleteClayDataSetFilter) {

		String selectionType = "single";

		if (autocompleteClayDataSetFilter.isMultipleSelection()) {
			selectionType = "multiple";
		}

		return HashMapBuilder.<String, Object>put(
			"apiURL", autocompleteClayDataSetFilter.getAPIURL()
		).put(
			"inputPlaceholder", autocompleteClayDataSetFilter.getPlaceholder()
		).put(
			"itemKey", autocompleteClayDataSetFilter.getItemKey()
		).put(
			"itemLabel", autocompleteClayDataSetFilter.getItemLabel()
		).put(
			"selectionType", selectionType
		).build();
	}

}