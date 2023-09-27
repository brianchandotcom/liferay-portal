/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.item.filter;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;

import java.util.List;
import java.util.Locale;

/**
 * @author Cristina González
 */
public interface ContentDashboardItemFilter {

	public DropdownItem getDropdownItem();

	public String getIcon();

	public String getLabel(Locale locale);

	public String getName();

	public String getParameterLabel(Locale locale);

	public String getParameterName();

	public List<String> getParameterValues();

	public Type getType();

	public String getURL();

	public enum Type {

		ITEM_SELECTOR

	}

}