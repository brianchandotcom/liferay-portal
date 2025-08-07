/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Alicia García
 */
public class ViewSharedWithMeSectionDisplayContext {

	public ViewSharedWithMeSectionDisplayContext(
		HttpServletRequest httpServletRequest, Portal portal) {

		_httpServletRequest = httpServletRequest;
		_portal = portal;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAPIURL() {
		return "/o/headless-admin-user/v1.0/my-user-account/shared-assets" +
			"/shared-with-me?filter=spaceDepotEntry eq true";
	}

	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			LanguageUtil.get(
				_httpServletRequest,
				"items-shared-with-you-by-other-users-will-appear-here")
		).put(
			"image", "/states/empty-state-shared-with-me.svg"
		).put(
			"title",
			LanguageUtil.get(
				_httpServletRequest, "no-items-shared-with-you-yet")
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws PortalException {

		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				StringBundler.concat(
					_themeDisplay.getPortalURL(), _themeDisplay.getPathMain(),
					GroupConstants.CMS_FRIENDLY_URL,
					"/edit_content_item?objectEntryId={classPK}",
					"&p_l_mode=read&p_p_state=", LiferayWindowState.POP_UP,
					"&redirect=", _themeDisplay.getURLCurrent()),
				"view", "actionLink",
				LanguageUtil.get(_httpServletRequest, "view"), "get", null,
				"modal"),
			new FDSActionDropdownItem(
				StringBundler.concat(
					_themeDisplay.getPortalURL(), _themeDisplay.getPathMain(),
					GroupConstants.CMS_FRIENDLY_URL,
					"/edit_content_item?objectEntryId={classPK}&redirect=",
					_themeDisplay.getURLCurrent()),
				"pencil", "actionLinkEdit",
				LanguageUtil.get(_httpServletRequest, "edit"), "get", null,
				null)
		);
	}

	private final HttpServletRequest _httpServletRequest;
	private final Portal _portal;
	private final ThemeDisplay _themeDisplay;

}