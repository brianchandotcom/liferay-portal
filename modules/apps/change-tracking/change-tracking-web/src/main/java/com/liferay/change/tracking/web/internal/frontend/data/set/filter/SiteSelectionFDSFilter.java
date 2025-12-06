/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Noor Najjar
 */
@Component(service = FDSFilter.class)
public class SiteSelectionFDSFilter extends BaseSelectionFDSFilter {

	public SiteSelectionFDSFilter(
		long selectedSiteName, Map<Long, String> siteNamesMap) {

		_selectedSiteName = selectedSiteName;
		_siteNamesMap = siteNamesMap;
	}

	@Override
	public String getId() {
		return "siteId";
	}

	@Override
	public String getLabel() {
		return "sites";
	}

	@Override
	public Map<String, Object> getPreloadedData() {
		if (_selectedSiteName == 0) {
			return null;
		}

		return HashMapBuilder.<String, Object>put(
			"selectedItems",
			TransformUtil.transform(
				_siteNamesMap.entrySet(),
				entry -> {
					if (entry.getKey() == _selectedSiteName) {
						return new SelectionFDSFilterItem(
							entry.getValue(), String.valueOf(entry.getKey()));
					}

					return null;
				})
		).build();
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		return TransformUtil.transform(
			_siteNamesMap.entrySet(),
			entry -> new SelectionFDSFilterItem(
				entry.getValue(), String.valueOf(entry.getKey())));
	}

	private final long _selectedSiteName;
	private final Map<Long, String> _siteNamesMap;

}