/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.util;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class UsersLoader {

	public UsersLoader(long[] userIds, UserLocalService userLocalService) {
		_userIds = userIds;
		_userLocalService = userLocalService;
	}

	public User getUser(long userId) {
		Map<Serializable, User> usersMap = _getUsersMap();

		User user = usersMap.get(userId);

		if (user != null) {
			return user;
		}

		return _userLocalService.fetchUser(userId);
	}

	private Map<Serializable, User> _getUsersMap() {
		if (_usersMap != null) {
			return _usersMap;
		}

		Set<Serializable> userIds = new HashSet<>();

		for (long userId : _userIds) {
			userIds.add(userId);
		}

		_usersMap = _userLocalService.fetchPersistedModels(userIds);

		return _usersMap;
	}

	private final long[] _userIds;
	private final UserLocalService _userLocalService;
	private Map<Serializable, User> _usersMap;

}