/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.design.library.web.internal.display.context;

import com.liferay.design.library.web.internal.constants.DesignLibraryAdminPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Gabriel Prates
 */
public class DesignLibraryDashboardDisplayContext {

	public DesignLibraryDashboardDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public Map<String, Object> getHeaderProps(long designLibraryEntryId) {
		return HashMapBuilder.<String, Object>put(
			"actionItems", () -> {
				JSONArray jsonArray = JSONUtil.putAll();

				jsonArray.put(
					JSONUtil.put(
						"href", "#settings"
					).put(
						"title",
						LanguageUtil.get(_httpServletRequest, "settings")
					).put(
						"symbolLeft", "cog"
					)
				).put(
					JSONUtil.put(
						"href", "#connected-sites"
					).put(
						"title",
						LanguageUtil.get(_httpServletRequest, "connected-sites")
					).put(
						"symbolLeft", "globe"
					)
				).put(
					JSONUtil.put(
						"href", "#manage-members"
					).put(
						"title",
						LanguageUtil.get(_httpServletRequest, "manage-members")
					).put(
						"symbolLeft", "users"
					)
				).put(
					JSONUtil.put(
						"href", "#import"
					).put(
						"title",
						LanguageUtil.get(_httpServletRequest, "import")
					).put(
						"symbolLeft", "import"
					)
				).put(
					JSONUtil.put(
						"href", "#export"
					).put(
						"title",
						LanguageUtil.get(_httpServletRequest, "export")
					).put(
						"symbolLeft", "export"
					)
				).put(
					JSONUtil.put(
						"href", "#delete"
					).put(
						"title",
						LanguageUtil.get(_httpServletRequest, "delete")
					).put(
						"symbolLeft", "trash"
					)
				);

				return jsonArray;
			}
		).put(
			"portletRoot", DesignLibraryAdminPortletKeys.DESIGN_LIBRARY_ADMIN
		).put(
			"title", "A Design Library " + (designLibraryEntryId)
		).build();
	}

	private final HttpServletRequest _httpServletRequest;

}