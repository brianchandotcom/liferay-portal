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

package com.liferay.users.admin.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.Date;
import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class UserItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public UserItemDescriptor(ThemeDisplay themeDisplay, User user) {
		_themeDisplay = themeDisplay;
		_user = user;
	}

	@Override
	public String getIcon() {
		return null;
	}

	@Override
	public String getImageURL() {
		try {
			return _user.getPortraitURL(_themeDisplay);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	@Override
	public Date getModifiedDate() {
		return _user.getModifiedDate();
	}

	@Override
	public String getPayload() {
		return JSONUtil.put(
			"id", _user.getUserId()
		).put(
			"name", _user.getFullName()
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return HtmlUtil.escape(_user.getScreenName());
	}

	@Override
	public String getTitle(Locale locale) {
		return HtmlUtil.escape(_user.getFullName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserItemDescriptor.class);

	private final ThemeDisplay _themeDisplay;
	private final User _user;

}