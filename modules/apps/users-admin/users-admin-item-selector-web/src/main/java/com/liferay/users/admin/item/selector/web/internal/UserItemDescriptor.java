/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;

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
		return _user.getScreenName();
	}

	@Override
	public String getTitle(Locale locale) {
		return _user.getFullName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserItemDescriptor.class);

	private final ThemeDisplay _themeDisplay;
	private final User _user;

}