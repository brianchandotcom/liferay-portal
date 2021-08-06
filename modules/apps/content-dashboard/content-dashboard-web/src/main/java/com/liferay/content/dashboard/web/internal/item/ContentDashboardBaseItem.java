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

package com.liferay.content.dashboard.web.internal.item;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yurena Cabrera
 */
public abstract class ContentDashboardBaseItem<T>
	implements ContentDashboardItem<T> {

	@Override
	public String getUserAvatarURL(HttpServletRequest httpServletRequest) {
		StringBundler sb = new StringBundler(5);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		sb.append(themeDisplay.getPathImage());

		try {
			User user = _userService.getUserById(getUserId());

			sb.append("/user_portrait?screenName=");
			sb.append(user.getScreenName());
			sb.append("&amp;companyId=");
			sb.append(user.getCompanyId());

			return sb.toString();
		}
		catch (PortalException portalException) {
			portalException.printStackTrace();
		}

		return null;
	}

	private UserService _userService;

}