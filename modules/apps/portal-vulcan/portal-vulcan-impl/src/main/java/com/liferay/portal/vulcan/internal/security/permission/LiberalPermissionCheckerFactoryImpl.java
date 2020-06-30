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

package com.liferay.portal.vulcan.internal.security.permission;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Tambara
 */
@Component(
	property = {
		"permission.checker.type=liberal", "service.ranking:Integer=-100"
	},
	service = PermissionCheckerFactory.class
)
public class LiberalPermissionCheckerFactoryImpl
	implements PermissionCheckerFactory {

	@Override
	public PermissionChecker create(User user) {
		PermissionChecker permissionChecker = new LiberalPermissionChecker();

		permissionChecker.init(user);

		return permissionChecker;
	}

}