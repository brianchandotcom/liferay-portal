/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.filter;

import com.liferay.frontend.data.set.filter.GroupedFDSFilters;
import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Sanz
 */
@Component(
	property = "frontend.data.set.name=" + FDSSampleFDSNames.ADVANCED,
	service = GroupedFDSFilters.class
)
public class AdvancedGroupedFDSFilters implements GroupedFDSFilters {

	@Override
	public JSONArray getGroupedFDSFiltersJSONArray(
		HttpServletRequest httpServletRequest) {

		return JSONUtil.putAll(
			JSONUtil.put("Empty Group", JSONUtil.putAll()),
			JSONUtil.put("Group 1", JSONUtil.putAll("date", "color")),
			JSONUtil.put(
				"Group 2", JSONUtil.putAll("clientExtension", null, "size")),
			JSONUtil.put("Group 3", JSONUtil.putAll("status", "title")),
			JSONUtil.put(
				"Group With Unregistered Filter",
				JSONUtil.putAll(StringUtil.randomString())));
	}

}