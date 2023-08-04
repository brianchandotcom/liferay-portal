/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Noor Najjar
 */
@Component(service = FDSFilter.class)
public class TypeNameSelectionFDSFilter extends BaseSelectionFDSFilter {

	public TypeNameSelectionFDSFilter(Map<Long, String> typeNamesMap) {
		_typeNamesMap = typeNamesMap;
	}

	@Override
	public String getId() {
		return "modelClassNameId";
	}

	@Override
	public String getLabel() {
		return "types";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		List<SelectionFDSFilterItem> selectionFDSFilterItems =
			new ArrayList<>();

		for (Map.Entry<Long, String> entry : _typeNamesMap.entrySet()) {
			selectionFDSFilterItems.add(
				new SelectionFDSFilterItem(
					entry.getValue(), String.valueOf(entry.getKey())));
		}

		return selectionFDSFilterItems;
	}

	private final Map<Long, String> _typeNamesMap;

}