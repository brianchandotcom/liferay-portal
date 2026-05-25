/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.display.context;

import com.liferay.ai.hub.web.internal.util.DisplayContextUtil;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author Carolina Barbosa
 */
public class ViewInstructionDefinitionsDisplayContext {

	public ViewInstructionDefinitionsDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAPIURL() {
		return _BASE_API_URL + "?sort=system:desc,dateCreated:asc";
	}

	public CreationMenu getCreationMenu() throws Exception {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					DisplayContextUtil.getAIHubURL(_themeDisplay) +
						"/instruction");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "new-instruction"));
			}
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		return List.of(
			new FDSActionDropdownItem(
				StringBundler.concat(
					DisplayContextUtil.getAIHubURL(_themeDisplay),
					"/instruction",
					"?externalReferenceCode={externalReferenceCode}"),
				"view", "view", LanguageUtil.get(_httpServletRequest, "view"),
				"get", null, null),
			new FDSActionDropdownItem(
				StringBundler.concat(
					_BASE_API_URL, "/by-external-reference-code",
					"/{externalReferenceCode}"),
				"trash", "delete",
				LanguageUtil.get(_httpServletRequest, "delete"), "delete",
				"delete", "async",
				HashMapBuilder.<String, Object>put(
					"system", false
				).build()),
			new FDSActionDropdownItem(
				DisplayContextUtil.getPermissionsURL(
					"L_AI_HUB_INSTRUCTION_DEFINITION", _httpServletRequest),
				"password-policies", "permissions",
				LanguageUtil.get(_httpServletRequest, "permissions"), "get",
				"permissions", "modal-permissions"));
	}

	private static final String _BASE_API_URL =
		"/o/ai-hub/instruction-definitions";

	private final HttpServletRequest _httpServletRequest;
	private final ThemeDisplay _themeDisplay;

}