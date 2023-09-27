/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		private static final PermissionChecker _permissionChecker =
			_getPermissionChecker();

	}

}