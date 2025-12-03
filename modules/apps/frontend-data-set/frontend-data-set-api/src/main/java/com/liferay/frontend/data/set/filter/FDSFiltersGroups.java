/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.filter;

import com.liferay.frontend.data.set.FDSEntryItemImportPolicy;

import jakarta.servlet.http.HttpServletRequest;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Daniel Sanz
 */
public interface FDSFiltersGroups {

	public default FDSEntryItemImportPolicy getFDSEntryItemImportPolicy() {
		return FDSEntryItemImportPolicy.ITEM_PROXY;
	}

	public LinkedHashMap<String, List<FDSFilter>> getFiltersGroups(
		HttpServletRequest httpServletRequest);

}