/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author Mylena Monte
 */
public class ViewContentRetrieversDisplayContext {

	public ViewContentRetrieversDisplayContext(
		GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest) {

		_groupLocalService = groupLocalService;
		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAPIURL() {
		return "/o/ai-hub/content-retrievers";
	}

	public CreationMenu getCreationMenu() throws Exception {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(_getContentRetrieverURL());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "new-data-source"));
			}
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		return List.of(
			new FDSActionDropdownItem(
				HttpComponentsUtil.addParameters(
					_getContentRetrieverURL(), "externalReferenceCode",
					"{externalReferenceCode}"),
				"view", "view", LanguageUtil.get(_httpServletRequest, "view"),
				"get", null, null),
			new FDSActionDropdownItem(
				getAPIURL() +
					"/by-external-reference-code/{externalReferenceCode}" +
						"/object-actions/crawl",
				"reload", "put",
				LanguageUtil.get(_httpServletRequest, "sync-now"), "put", null,
				"async"),
			new FDSActionDropdownItem(
				getAPIURL() +
					"/by-external-reference-code/{externalReferenceCode}",
				"trash", "delete",
				LanguageUtil.get(_httpServletRequest, "delete"), "delete",
				"delete", "async"));
	}

	private String _getContentRetrieverURL() throws Exception {
		Company company = _themeDisplay.getCompany();
		Group group = _groupLocalService.getGroup(
			_themeDisplay.getScopeGroupId());

		return StringBundler.concat(
			company.getPortalURL(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			"/web", group.getFriendlyURL(), "/content-retriever");
	}

	private final GroupLocalService _groupLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final ThemeDisplay _themeDisplay;

}