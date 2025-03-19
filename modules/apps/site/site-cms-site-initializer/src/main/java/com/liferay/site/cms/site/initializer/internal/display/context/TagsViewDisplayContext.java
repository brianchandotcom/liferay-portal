/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.site.cms.site.initializer.internal.configuration.CMSSiteInitializerConfiguration;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Noor Najjar
 */
public class TagsViewDisplayContext {

	public TagsViewDisplayContext(
		CMSSiteInitializerConfiguration cmsSiteInitializerConfiguration,
		HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay) {

		_cmsSiteInitializerConfiguration = cmsSiteInitializerConfiguration;
		_httpServletRequest = httpServletRequest;
		_themeDisplay = themeDisplay;
	}

	public Map<String, Object> getReactData() throws PortalException {
		return HashMapBuilder.<String, Object>put(
			"tagsURL",
			PortalUtil.getLayoutFullURL(
				LayoutLocalServiceUtil.getLayoutByFriendlyURL(
					_themeDisplay.getScopeGroupId(), false,
					"/categorization/view_tags"),
				_themeDisplay)
		).put(
			"vocabularyURL",
			PortalUtil.getLayoutFullURL(
				LayoutLocalServiceUtil.getLayoutByFriendlyURL(
					_themeDisplay.getScopeGroupId(), false,
					"/categorization/view_vocabularies"),
				_themeDisplay)
		).build();
	}

	private final CMSSiteInitializerConfiguration
		_cmsSiteInitializerConfiguration;
	private final HttpServletRequest _httpServletRequest;
	private final ThemeDisplay _themeDisplay;

}