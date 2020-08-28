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

package com.liferay.translation.test.util;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Alejandro Tardín
 */
public class TranslationTestUtil {

	public static InputStream readFileToInputStream(String fileName)
		throws Exception {

		return new ByteArrayInputStream(_getBytes(fileName));
	}

	public static String readFileToString(String fileName) throws Exception {
		return new String(_getBytes(fileName));
	}

	public static void withRegularUser(
			UnsafeBiConsumer<User, Role, Exception> unsafeBiConsumer)
		throws Exception {

		_withUser(unsafeBiConsumer, RoleConstants.TYPE_REGULAR);
	}

	private static byte[] _getBytes(String fileName) throws Exception {
		return FileUtil.getBytes(
			TranslationTestUtil.class,
			"/com/liferay/translation/dependencies/" + fileName);
	}

	private static void _withUser(
			UnsafeBiConsumer<User, Role, Exception> unsafeBiConsumer,
			int roleType)
		throws Exception {

		Role role = RoleTestUtil.addRole(roleType);
		User user = UserTestUtil.addUser();

		UserLocalServiceUtil.addRoleUser(role.getRoleId(), user);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			unsafeBiConsumer.accept(user, role);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			RoleLocalServiceUtil.deleteRole(role);
			UserLocalServiceUtil.deleteUser(user);
		}
	}

}