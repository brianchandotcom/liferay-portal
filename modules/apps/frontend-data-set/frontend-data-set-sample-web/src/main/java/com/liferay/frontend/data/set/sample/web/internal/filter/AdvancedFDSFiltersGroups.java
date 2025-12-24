/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.filter;

import com.liferay.frontend.data.set.filter.FDSFilterRegistry;
import com.liferay.frontend.data.set.filter.FDSFiltersGroups;
import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
@Component(
	property = "frontend.data.set.name=" + FDSSampleFDSNames.ADVANCED,
	service = FDSFiltersGroups.class
)
public class AdvancedFDSFiltersGroups implements FDSFiltersGroups {

	@Override
	public JSONArray getGroupedFDSFiltersJSONArray(
		HttpServletRequest httpServletRequest) {

		return JSONUtil.putAll(
			JSONUtil.put("Empty Group", JSONUtil.putAll()),
			JSONUtil.put("Group 1", JSONUtil.putAll("date", "color")),
			JSONUtil.put("Group 2", JSONUtil.putAll(null, "size")),
			JSONUtil.put("Group 3", JSONUtil.putAll("status", "title")),
			JSONUtil.put(
				"Group with not registered filter",
				JSONUtil.putAll("notRegistered")));
	}

	@Reference
	private FDSFilterRegistry _fdsFilterRegistry;

}