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

package com.liferay.password.policies.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseUserCard;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.model.User;
import com.liferay.taglib.util.LexiconUtil;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class UserVerticalCard extends BaseUserCard {

	public UserVerticalCard(
		RenderRequest renderRequest, RowChecker rowChecker, User user) {

		super(user, renderRequest, rowChecker);
	}

	@Override
	public String getUserColorClass() {
		return "sticker-user-icon " + LexiconUtil.getUserColorCssClass(user);
	}

}