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

package com.liferay.site.memberships.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseUserCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.model.User;
import com.liferay.site.memberships.web.internal.constants.SiteMembershipWebKeys;
import com.liferay.site.memberships.web.internal.servlet.taglib.util.UserActionDropdownItemsProvider;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
public class UsersUserCard extends BaseUserCard {

	public UsersUserCard(
		User user, boolean showActions, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		super(user, renderRequest, rowChecker);

		_showActions = showActions;
		_renderResponse = renderResponse;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		if (!_showActions) {
			return null;
		}

		try {
			UserActionDropdownItemsProvider userActionDropdownItemsProvider =
				new UserActionDropdownItemsProvider(
					user, renderRequest, _renderResponse);

			return userActionDropdownItemsProvider.getActionDropdownItems();
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getDefaultEventHandler() {
		return SiteMembershipWebKeys.USER_DROPDOWN_DEFAULT_EVENT_HANDLER;
	}

	private final RenderResponse _renderResponse;
	private final boolean _showActions;

}