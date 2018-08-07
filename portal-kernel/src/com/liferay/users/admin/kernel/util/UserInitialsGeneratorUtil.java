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

package com.liferay.users.admin.kernel.util;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.Locale;

/**
 * @author Drew Brokke
 */
public class UserInitialsGeneratorUtil {

	public static String getInitials(
		Locale locale, String firstName, String middleName, String lastName) {

		return _userInitialsGenerator.getInitials(
			locale, firstName, middleName, lastName);
	}

	public static String getInitials(User user) {
		return _userInitialsGenerator.getInitials(user);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static UserInitialsGenerator getUserInitialsGenerator() {
		return _userInitialsGenerator;
	}

	private static volatile UserInitialsGenerator _userInitialsGenerator =
		ServiceProxyFactory.newServiceTrackedInstance(
			UserInitialsGenerator.class, UserInitialsGeneratorUtil.class,
			"_userInitialsGenerator", false);

}