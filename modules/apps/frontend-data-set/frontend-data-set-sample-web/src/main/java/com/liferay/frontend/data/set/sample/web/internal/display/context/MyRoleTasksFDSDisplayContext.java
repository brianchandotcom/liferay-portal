/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.display.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Juanjo Fernández
 */
public class MyRoleTasksFDSDisplayContext {

	public MyRoleTasksFDSDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public String getAPIURL() {
		return "/o/headless-admin-workflow/v1.0/workflow-tasks" +
			"/assigned-to-my-roles";
	}

	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			LanguageUtil.get(
				_httpServletRequest, "start-creating-one-to-show-your-data")
		).put(
			"image", "/states/empty_state.svg"
		).put(
			"title",
			LanguageUtil.get(_httpServletRequest, "no-data-sets-created")
		).build();
	}

	private final HttpServletRequest _httpServletRequest;

}