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
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marko Cikos
 */
@Component(
	property = "frontend.data.set.name=" + FDSSampleFDSNames.CUSTOMIZED,
	service = FDSFilter.class
)
public class SizeSelectionFDSFilter extends BaseSelectionFDSFilter {

	@Override
	public String getId() {
		return "size";
	}

	@Override
	public String getLabel() {
		return "size";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		return ListUtil.fromArray(
			new SelectionFDSFilterItem("Tiny", "Tiny"),
			new SelectionFDSFilterItem("Small", "Small"),
			new SelectionFDSFilterItem("Medium", "Medium"),
			new SelectionFDSFilterItem("Large", "Large"),
			new SelectionFDSFilterItem("Huge", "Huge"),
			new SelectionFDSFilterItem("Gargantuan", "Gargantuan"));
	}

	@Override
	public boolean isMultiple() {
		return false;
	}

}