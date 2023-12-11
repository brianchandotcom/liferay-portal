/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Tomas Polesovsky
 */
public class OAuth2ConnectedApplicationsManagementToolbarDisplayContext
	extends BaseOAuth2ManagementToolbarDisplayContext {

	public OAuth2ConnectedApplicationsManagementToolbarDisplayContext(
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
				dropdownItem.putData("action", "removeAccess");
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "remove-access"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@Override
	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"revokeOauthAuthorizationsURL",
			() -> PortletURLBuilder.createActionURL(
				liferayPortletResponse
			).setActionName(
				"/connected_applications/revoke_oauth2_authorizations"
			).buildString()
		).build();
	}

	public OrderByComparator<OAuth2Authorization> getOrderByComparator() {
		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		String columnName = "createDate";

		if (orderByCol.equals("createDate")) {
			columnName = "createDate";
		}
		else if (orderByCol.equals("oAuth2ApplicationId")) {
			columnName = "oAuth2ApplicationId";
		}

		return OrderByComparatorFactoryUtil.create(
			"OAuth2Authorization", columnName, orderByType.equals("asc"));
	}

	@Override
	public List<DropdownItem> getOrderByDropdownItems() {
		return getOrderByDropdownItems(
			HashMapBuilder.put(
				"createDate", "authorization"
			).put(
				"oAuth2ApplicationId", "application-id"
			).build());
	}

}