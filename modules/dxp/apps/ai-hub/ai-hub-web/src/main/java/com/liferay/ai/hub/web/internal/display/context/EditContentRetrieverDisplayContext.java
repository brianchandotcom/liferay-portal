/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Davyson Melo
 */
public class EditContentRetrieverDisplayContext {

	public EditContentRetrieverDisplayContext(
		GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest) {

		_groupLocalService = groupLocalService;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getReactData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"backURL",
			() -> {
				Company company = _themeDisplay.getCompany();
				Group group = _groupLocalService.getGroup(
					_themeDisplay.getScopeGroupId());

				return StringBundler.concat(
					company.getPortalURL(
						GroupConstants.DEFAULT_PARENT_GROUP_ID),
					"/web", group.getFriendlyURL(), "/agent-builder");
			}
		).build();
	}

	private final GroupLocalService _groupLocalService;
	private final ThemeDisplay _themeDisplay;

}