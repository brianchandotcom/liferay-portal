/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.filter;

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilterRegistry;
import com.liferay.frontend.data.set.filter.FDSFiltersGroups;
import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
@Component(
	property = "frontend.data.set.name=" + FDSSampleFDSNames.ADVANCED,
	service = FDSFiltersGroups.class
)
public class AdvancedFiltersGroups implements FDSFiltersGroups {

	@Override
	public LinkedHashMap<String, List<FDSFilter>> getFiltersGroups(
		HttpServletRequest httpServletRequest) {

		Map<String, FDSFilter> filtersMap = new HashMap<>();

		for (FDSFilter filter :
				_fdsFilterRegistry.getFDSFilters(FDSSampleFDSNames.ADVANCED)) {

			filtersMap.put(filter.getId(), filter);
		}

		return LinkedHashMapBuilder.<String, List<FDSFilter>>put(
			"Group 1",
			Arrays.asList(filtersMap.get("date"), filtersMap.get("color"))
		).put(
			"Group 2",
			Arrays.asList(filtersMap.get("invalid"), filtersMap.get("size"))
		).put(
			"Group 3",
			Arrays.asList(filtersMap.get("status"), filtersMap.get("title"))
		).build();
	}

	@Reference
	private FDSFilterRegistry _fdsFilterRegistry;

}