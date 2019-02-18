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

package com.liferay.site.teams.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseUserCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.model.User;
import com.liferay.site.teams.web.internal.constants.SiteTeamsWebKeys;
import com.liferay.site.teams.web.internal.servlet.taglib.util.UserActionDropdownItemsProvider;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
public class UserUserCard extends BaseUserCard {

	public UserUserCard(
		User user, long teamId, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		super(user, renderRequest, rowChecker);

		_teamId = teamId;
		_renderResponse = renderResponse;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		UserActionDropdownItemsProvider userActionDropdownItemsProvider =
			new UserActionDropdownItemsProvider(
				user, _teamId, renderRequest, _renderResponse);

		return userActionDropdownItemsProvider.getActionDropdownItems();
	}

	@Override
	public String getDefaultEventHandler() {
		return SiteTeamsWebKeys.USER_DROPDOWN_DEFAULT_EVENT_HANDLER;
	}

	private final RenderResponse _renderResponse;
	private final long _teamId;

}