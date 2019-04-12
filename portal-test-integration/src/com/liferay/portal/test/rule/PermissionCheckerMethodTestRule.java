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
import com.liferay.portal.security.permission.SimplePermissionChecker;

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

		PermissionThreadLocal.setPermissionChecker(
			new SimplePermissionChecker() {
				{
					init(TestPropsValues.getUser());
				}

				@Override
				public boolean hasOwnerPermission(
					long companyId, String name, String primKey, long ownerId,
					String actionId) {

					return true;
				}

			});
	}

	protected void setUpPrincipalThreadLocal() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	private PermissionCheckerMethodTestRule() {
	}

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;

}