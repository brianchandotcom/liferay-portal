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

package com.liferay.password.policies.admin.web.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.permission.PasswordPolicyPermissionUtil;

import javax.portlet.RenderResponse;

/**
 * @author Pei-Jung Lan
 */
public class PasswordPolicyChecker extends EmptyOnClickRowChecker {

	public PasswordPolicyChecker(RenderResponse renderResponse) {
		super(renderResponse);
	}

	@Override
	public boolean isDisabled(Object obj) {
		PasswordPolicy passwordPolicy = (PasswordPolicy)obj;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (passwordPolicy.getDefaultPolicy() ||
			!PasswordPolicyPermissionUtil.contains(
				permissionChecker, passwordPolicy.getPasswordPolicyId(),
				ActionKeys.DELETE)) {

			return true;
		}

		return super.isDisabled(obj);
	}

}