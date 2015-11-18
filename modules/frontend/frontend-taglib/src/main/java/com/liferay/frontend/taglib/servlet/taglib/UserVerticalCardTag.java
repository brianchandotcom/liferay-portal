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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.util.LexiconUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class UserVerticalCardTag extends VerticalCardTag {

	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	protected void cleanUp() {
		_userId = 0;
	}

	@Override
	protected String getPage() {
		return "/card/user_vertical_card/page.jsp";
	}

	protected User getUser() {
		return UserLocalServiceUtil.fetchUser(_userId);
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return true;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		User user = getUser();

		request.setAttribute(
			"liferay-frontend:card:colorCssClass",
			LexiconUtil.getUserColorCssClass(user));

		if ((user != null) && (user.getPortraitId() > 0)) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			try {
				request.setAttribute(
					"liferay-frontend:card:portraitURL",
					user.getPortraitURL(themeDisplay));
			}
			catch (PortalException pe) {
			}
		}

		String initials = StringPool.BLANK;

		if (user != null) {
			initials = user.getInitials();
		}

		request.setAttribute("liferay-frontend:card:userInitials", initials);
	}

	private long _userId;

}