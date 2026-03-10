/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.design.library.web.internal.display.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Mario Leandro
 */
public class ViewDesignLibraryAdminDisplayContext {

	public ViewDesignLibraryAdminDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public String getAPIURL() {
		return "/o/headless-asset-library/v1.0/asset-libraries?filter=type " +
			"eq 'DesignLibrary'";
	}

	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			LanguageUtil.get(
				_httpServletRequest,
				"click-new-to-create-your-first-design-library")
		).put(
			"image", "/states/design_library_empty_state.svg"
		).put(
			"title",
			LanguageUtil.get(_httpServletRequest, "no-design-libraries-yet")
		).build();
	}

	private final HttpServletRequest _httpServletRequest;

}