/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.serializer;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Daniel Sanz
 */
public interface FDSSerializer {

	public static final String TYPE_CUSTOM = "custom";

	public static final String TYPE_SYSTEM = "system";

	public String serializeAPIURL(
		String fdsName, HttpServletRequest httpServletRequest);

	public List<FDSActionDropdownItem> serializeBulkActions(
		String fdsName, HttpServletRequest httpServletRequest);

	public CreationMenu serializeCreationMenu(
		String fdsName, HttpServletRequest httpServletRequest);

	public List<FDSActionDropdownItem> serializeItemsActions(
		String fdsName, HttpServletRequest httpServletRequest);

}