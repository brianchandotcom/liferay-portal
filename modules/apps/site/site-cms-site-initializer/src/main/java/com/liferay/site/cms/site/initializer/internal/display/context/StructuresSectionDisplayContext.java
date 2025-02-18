/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cms.site.initializer.internal.configuration.CMSSiteInitializerConfiguration;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sam Ziemer
 */
public class StructuresSectionDisplayContext {

	public StructuresSectionDisplayContext(
		CMSSiteInitializerConfiguration cmsSiteInitializerConfiguration,
		HttpServletRequest httpServletRequest) {

		_cmsSiteInitializerConfiguration = cmsSiteInitializerConfiguration;
		_httpServletRequest = httpServletRequest;
	}

	public String getAPIURL() {
		StringBundler sb = new StringBundler(3);

		sb.append("/o/search/v1.0/search?emptySearch=true");
		sb.append("&nestedFields=embedded&entryClassNames=");
		sb.append(
			ArrayUtil.toString(
				_cmsSiteInitializerConfiguration.structuresClassNames(),
				StringPool.BLANK));

		return sb.toString();
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return new ArrayList<>();
	}

	public CreationMenu getCreationMenu() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = LayoutLocalServiceUtil.getLayoutByFriendlyURL(
			themeDisplay.getScopeGroupId(), false, "/structure-builder");

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			layout, themeDisplay);

		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(layoutFullURL);
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "content"));
			}
		).addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(layoutFullURL);
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "file"));
			}
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return new ArrayList<>();
	}

	private final CMSSiteInitializerConfiguration
		_cmsSiteInitializerConfiguration;
	private final HttpServletRequest _httpServletRequest;

}