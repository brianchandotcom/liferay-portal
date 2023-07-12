/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Preston Crary
 */
public class UserBagFactoryUtil {

	public static UserBag create(long userId) throws PortalException {
		return _userBagFactory.create(userId);
	}

	public void setUserBagFactory(UserBagFactory userBagFactory) {
		_userBagFactory = userBagFactory;
	}

	private static UserBagFactory _userBagFactory;

}