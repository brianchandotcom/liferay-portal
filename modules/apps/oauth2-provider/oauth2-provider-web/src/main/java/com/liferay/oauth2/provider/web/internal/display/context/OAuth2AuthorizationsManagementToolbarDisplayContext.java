/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.List;

/**
 * @author Tomas Polesovsky
 */
public class OAuth2AuthorizationsManagementToolbarDisplayContext
	extends BaseOAuth2ManagementToolbarDisplayContext {

	public OAuth2AuthorizationsManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<?> searchContainer) {

		super(
			liferayPortletRequest.getHttpServletRequest(),
			liferayPortletRequest, liferayPortletResponse, searchContainer);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "revokeOAuth2Authorizations");
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "revoke-authorizations"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@Override
	public List<DropdownItem> getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				for (String orderByCol : _ORDER_BY_COLUMNS) {
					add(
						dropdownItem -> {
							dropdownItem.setActive(
								orderByCol.equals(getOrderByCol()));
							dropdownItem.setHref(
								getCurrentSortingURL(), "orderByCol",
								orderByCol);
							dropdownItem.setLabel(
								LanguageUtil.get(
									httpServletRequest, orderByCol));
						});
				}
			}
		};
	}

	private static final String[] _ORDER_BY_COLUMNS = {
		"createDate", "userId", "userName", "accessTokenCreateDate",
		"accessTokenExpirationDate", "refreshTokenCreateDate",
		"refreshTokenExpirationDate", "remoteIPInfo"
	};

}