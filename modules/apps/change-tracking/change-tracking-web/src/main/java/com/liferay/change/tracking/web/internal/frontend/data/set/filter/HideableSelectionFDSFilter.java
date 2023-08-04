/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Noor Najjar
 */
@Component(service = FDSFilter.class)
public class HideableSelectionFDSFilter extends BaseSelectionFDSFilter {

	@Override
	public String getId() {
		return "hideable";
	}

	@Override
	public String getLabel() {
		return "System items";
	}

	@Override
	public Map<String, Object> getPreloadedData() {
		return HashMapBuilder.<String, Object>put(
			"exclude", false
		).put(
			"selectedItems",
			ListUtil.fromArray(
				new SelectionFDSFilterItem("Do not show system items", false))
		).build();
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		return ListUtil.fromArray(
			new SelectionFDSFilterItem("Do not show system items", false));
	}

}