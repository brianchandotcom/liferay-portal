/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.filter;

import com.liferay.frontend.data.set.FDSEntryItemImportPolicy;
import com.liferay.portal.kernel.json.JSONArray;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Daniel Sanz
 */
public interface GroupedFDSFilters {

	public default FDSEntryItemImportPolicy getFDSEntryItemImportPolicy() {
		return FDSEntryItemImportPolicy.ITEM_PROXY;
	}

	public JSONArray getGroupedFDSFiltersJSONArray(
		HttpServletRequest httpServletRequest);

}