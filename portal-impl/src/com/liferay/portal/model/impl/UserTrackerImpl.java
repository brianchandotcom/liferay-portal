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

package com.liferay.portal.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTracker;
import com.liferay.portal.kernel.model.UserTrackerPath;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UserTrackerImpl extends UserTrackerBaseImpl {

	@Override
	public void addPath(UserTrackerPath path) {
		try {
			_paths.add(path);
		}
		catch (ArrayIndexOutOfBoundsException aioobe) {
			if (_log.isWarnEnabled()) {
				_log.warn(aioobe, aioobe);
			}
		}

		setModifiedDate(path.getPathDate());
	}

	@Override
	public int compareTo(UserTracker userTracker) {
		String userName1 = StringUtil.toLowerCase(getFullName());
		String userName2 = StringUtil.toLowerCase(userTracker.getFullName());

		int value = userName1.compareTo(userName2);

		if (value == 0) {
			value = getModifiedDate().compareTo(userTracker.getModifiedDate());
		}

		return value;
	}

	@Override
	public String getEmailAddress() {
		if (_emailAddress == null) {
			try {
				if (_user == null) {
					_user = UserLocalServiceUtil.getUserById(getUserId());
				}

				_emailAddress = _user.getEmailAddress();
			}
			catch (Exception e) {
			}
		}

		if (_emailAddress == null) {
			_emailAddress = StringPool.BLANK;
		}

		return _emailAddress;
	}

	@Override
	public String getFullName() {
		if (_fullName == null) {
			try {
				if (_user == null) {
					_user = UserLocalServiceUtil.getUserById(getUserId());
				}

				_fullName = _user.getFullName();
			}
			catch (Exception e) {
			}
		}

		if (_fullName == null) {
			_fullName = StringPool.BLANK;
		}

		return _fullName;
	}

	@Override
	public int getHits() {
		return _paths.size();
	}

	@Override
	public List<UserTrackerPath> getPaths() {
		return _paths;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserTrackerImpl.class);

	private String _emailAddress;
	private String _fullName;
	private final List<UserTrackerPath> _paths = new ArrayList<>();
	private User _user;

}