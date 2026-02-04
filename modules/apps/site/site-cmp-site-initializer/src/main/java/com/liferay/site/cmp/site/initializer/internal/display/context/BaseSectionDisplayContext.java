/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Pedro Leite
 */
public abstract class BaseSectionDisplayContext {

	public BaseSectionDisplayContext(
		HttpServletRequest httpServletRequest,
		ObjectDefinition objectDefinition) {

		this.httpServletRequest = httpServletRequest;
		this.objectDefinition = objectDefinition;

		themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public abstract String getAPIURL();

	public List<DropdownItem> getBulkActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, "date-time", "update-due-date",
				LanguageUtil.get(httpServletRequest, "update-due-date"), "post",
				"update-due-date", null),
			new FDSActionDropdownItem(
				null, "arrow-start", "update-state",
				LanguageUtil.get(httpServletRequest, "update-state"), "post",
				"update-state", null),
			new FDSActionDropdownItem(
				"#", "trash", "delete",
				LanguageUtil.get(httpServletRequest, "delete"), null, null,
				null));
	}

	public abstract CreationMenu getCreationMenu();

	public abstract Map<String, Object> getEmptyState();

	public abstract List<FDSActionDropdownItem> getFDSActionDropdownItems();

	protected final HttpServletRequest httpServletRequest;
	protected final ObjectDefinition objectDefinition;
	protected final ThemeDisplay themeDisplay;

}