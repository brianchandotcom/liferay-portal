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

package com.liferay.commerce.data.integration.apio.internal.security.permission;

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.functional.Try;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.function.BiFunction;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true, service = AccountPermissionChecker.class)
public class AccountPermissionChecker {

	public Boolean forAdding(Credentials credentials) {
		Try<PermissionChecker> permissionCheckerTry = _getPermissionCheckerTry(
			credentials);

		return permissionCheckerTry.map(
			permissionChecker -> PortalPermissionUtil.contains(
				permissionChecker, ActionKeys.ADD_ORGANIZATION)
		).orElse(
			false
		);
	}

	public BiFunction<Credentials, Long, Boolean> forDeleting() {
		return _hasPermission.forDeleting(Organization.class);
	}

	public BiFunction<Credentials, Long, Boolean> forUpdating() {
		return _hasPermission.forUpdating(Organization.class);
	}

	private Try<PermissionChecker> _getPermissionCheckerTry(
		Credentials credentials) {

		return Try.success(
			credentials.get()
		).map(
			Object::toString
		).map(
			Long::valueOf
		).map(
			_userService::getUserById
		).map(
			PermissionCheckerFactoryUtil::create
		).recoverWith(
			__ -> Try.fromFallible(PermissionThreadLocal::getPermissionChecker)
		);
	}

	@Reference
	private HasPermission _hasPermission;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private UserService _userService;

}