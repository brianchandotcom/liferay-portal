/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testhook.hook.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceWrapper;
import com.liferay.testhook.hook.model.impl.TestHookUserImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class TestHookUserLocalServiceImpl extends UserLocalServiceWrapper {

	public TestHookUserLocalServiceImpl(UserLocalService userLocalService) {
		super(userLocalService);
	}

	@Override
	public User getUserByEmailAddress(long companyId, String emailAddress)
		throws PortalException {

		System.out.println(
			"Called TestHookUserLocalServiceImpl.getUserByEmailAddress(" +
				companyId + ", " + emailAddress + ")");

		User user = super.getUserByEmailAddress(companyId, emailAddress);

		return new TestHookUserImpl(user);
	}

	@Override
	public User getUserById(long userId) throws PortalException {
		System.out.println(
			"Called TestHookUserLocalServiceImpl.getUserById(" + userId + ")");

		User user = super.getUserById(userId);

		return new TestHookUserImpl(user);
	}

}