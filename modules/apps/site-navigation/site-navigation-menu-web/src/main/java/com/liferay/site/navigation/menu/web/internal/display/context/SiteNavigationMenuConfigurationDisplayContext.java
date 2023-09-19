/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.web.internal.display.context;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Michael Bowerman
 */
public class SiteNavigationMenuConfigurationDisplayContext {

	public SiteNavigationMenuConfigurationDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public boolean hasLayoutPageTemplateEntry() {
		if (_hasLayoutPageTemplateEntry != null) {
			return _hasLayoutPageTemplateEntry;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		long plid = layout.getPlid();

		if (layout.isDraftLayout()) {
			plid = layout.getClassPK();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(plid);

		if (layoutPageTemplateEntry != null) {
			_hasLayoutPageTemplateEntry = true;
		}
		else {
			_hasLayoutPageTemplateEntry = false;
		}

		return _hasLayoutPageTemplateEntry;
	}

	private Boolean _hasLayoutPageTemplateEntry;
	private final HttpServletRequest _httpServletRequest;

}