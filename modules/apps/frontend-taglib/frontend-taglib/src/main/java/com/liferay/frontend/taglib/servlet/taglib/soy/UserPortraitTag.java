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

package com.liferay.frontend.taglib.servlet.taglib.soy;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.StickerTag;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author Alejandro Tardín
 * @author Jorge González
 */
public class UserPortraitTag extends StickerTag {

	@Override
	public int doStartTag() {
		putValue("shape", "circle");
		putValue("style", "unstyled");

		return super.doStartTag();
	}

	public void setUser(User user) {
		putValue("imageSrc", _getPortraitURL(user));
		putValue("title", PortalUtil.getUserName(user));
	}

	public void setUserId(long userId) {
		setUser(UserLocalServiceUtil.fetchUser(userId));
	}

	private String _getPortraitURL(User user) {
		String portraitURL = null;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (user != null) {
			try {
				portraitURL = user.getPortraitURL(themeDisplay);
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}
		else {
			portraitURL = UserConstants.getPortraitURL(
				themeDisplay.getPathImage(), true, 0, StringPool.BLANK);
		}

		return portraitURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserPortraitTag.class);

}