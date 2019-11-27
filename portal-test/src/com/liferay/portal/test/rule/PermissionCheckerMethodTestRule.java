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

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.MethodTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.util.Objects;

import org.junit.runner.Description;

/**
 * @author Tom Wang
 */
public class PermissionCheckerMethodTestRule extends MethodTestRule<Void> {

	public static final PermissionCheckerMethodTestRule INSTANCE =
		new PermissionCheckerMethodTestRule();

	@Override
	public void afterMethod(Description description, Void c, Object target)
		throws Throwable {

		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Override
	public Void beforeMethod(Description description, Object target)
		throws Exception {

		setUpPermissionThreadLocal();
		setUpPrincipalThreadLocal();

		return null;
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionChecker permissionChecker =
			PermissionCheckerHolder._permissionChecker;

		PermissionChecker clonedPermissionChecker = permissionChecker.clone();

		clonedPermissionChecker.init(TestPropsValues.getUser());

		PermissionThreadLocal.setPermissionChecker(
			(PermissionChecker)ProxyUtil.newProxyInstance(
				PermissionChecker.class.getClassLoader(),
				new Class<?>[] {PermissionChecker.class},
				(proxy, method, args) -> {
					if (Objects.equals(
							method.getName(), "hasOwnerPermission")) {

						return true;
					}

					return method.invoke(clonedPermissionChecker, args);
				}));
	}

	protected void setUpPrincipalThreadLocal() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	private PermissionCheckerMethodTestRule() {
	}

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;

	private static class PermissionCheckerHolder {

		private static PermissionChecker _getPermissionChecker() {
			try {
				ClassLoader portalClassLoader =
					PortalClassLoaderUtil.getClassLoader();

				Class<PermissionChecker> clazz =
					(Class<PermissionChecker>)portalClassLoader.loadClass(
						"com.liferay.portal.security.permission." +
							"SimplePermissionChecker");

				return clazz.newInstance();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		private static final PermissionChecker _permissionChecker =
			_getPermissionChecker();

	}

}