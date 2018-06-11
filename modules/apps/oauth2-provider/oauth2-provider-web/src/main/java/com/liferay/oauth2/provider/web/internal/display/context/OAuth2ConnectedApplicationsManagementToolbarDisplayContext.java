/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.oauth2.provider.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;

import java.util.HashMap;
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
		PortletURL currentURLObj) {

		super(
			liferayPortletRequest.getHttpServletRequest(),
			liferayPortletRequest, liferayPortletResponse, currentURLObj);
	}

	public List<DropdownItem> getActionDropdownItems() {
		DropdownItemList dropdownItems = new DropdownItemList();

		dropdownItems.add(
			dropdownItem -> {
				dropdownItem.setHref(
					StringBundler.concat(
						"javascript:", liferayPortletResponse.getNamespace(),
						"removeAccess();"));
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "remove-access"));
				dropdownItem.setQuickAction(true);
			});

		return dropdownItems;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				Map<String, String> orderColumnsMap = new HashMap<>();

				orderColumnsMap.put("createDate", "authorization");
				orderColumnsMap.put("oAuth2ApplicationId", "application-id");

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getOrderByDropdownItems(orderColumnsMap));
						dropdownGroupItem.setLabel(
							LanguageUtil.get(httpServletRequest, "order-by"));
					});
			}
		};
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

}