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

package com.liferay.frontend.data.set.sample.web.internal.filter;

import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marko Cikos
 */
@Component(
	property = "frontend.data.set.name=" + FDSSampleFDSNames.CUSTOMIZED,
	service = FDSFilter.class
)
public class ColorSelectionFDSFilter extends BaseSelectionFDSFilter {

	@Override
	public String getId() {
		return "color";
	}

	@Override
	public String getLabel() {
		return "color";
	}

	@Override
	public Map<String, Object> getPreloadedData() {
		return HashMapBuilder.<String, Object>put(
			"exclude", false
		).put(
			"itemsValues", new String[] {"Blue", "Green", "Yellow"}
		).build();
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		return ListUtil.fromArray(
			new SelectionFDSFilterItem("Blue", "Blue"),
			new SelectionFDSFilterItem("Green", "Green"),
			new SelectionFDSFilterItem("Red", "Red"),
			new SelectionFDSFilterItem("Yellow", "Yellow"));
	}

}