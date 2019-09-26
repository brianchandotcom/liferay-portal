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

package com.liferay.my.subscriptions.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.subscription.service.SubscriptionLocalServiceUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class MySubscriptionsManagementToolbarDisplayContext {

	public MySubscriptionsManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse, User user) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_user = user;

		_totalItems = SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
			_user.getUserId());
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "unsubscribe");
						dropdownItem.setIcon("times");
						dropdownItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "unsubscribe"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public int getTotalItems() {
		return _totalItems;
	}

	public boolean isDisabled() {
		if (_totalItems <= 0) {
			return true;
		}

		return false;
	}

	public boolean isSelectable() {
		return true;
	}

	public boolean isShowSearch() {
		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final int _totalItems;
	private final User _user;

}