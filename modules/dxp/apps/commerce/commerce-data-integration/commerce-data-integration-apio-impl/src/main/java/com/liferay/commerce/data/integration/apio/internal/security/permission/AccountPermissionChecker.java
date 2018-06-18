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
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.UserService;

import java.util.function.BiFunction;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true, service = AccountPermissionChecker.class)
public class AccountPermissionChecker {

	public BiFunction<Credentials, Long, Boolean> forAdding() {
		return (credentials, organizationId) -> {
			Try<PermissionChecker> permissionCheckerTry =
				_getPermissionCheckerTry(credentials);

			Try<Organization> accountTry = Try.fromFallible(
				() -> _organizationService.fetchOrganization(organizationId));

			return permissionCheckerTry.map(
				permissionChecker -> _portletResourcePermission.contains(
					permissionChecker,
					accountTry.map(
						Organization::getGroupId
					).orElseThrow(
						() -> new NotFoundException()
					),
					ActionKeys.ADD_ORGANIZATION)
			).orElse(
				false
			);
		};
	}

	public BiFunction<Credentials, Long, Boolean> forDeleting() {
		return _permissionBridge();
	}

	public BiFunction<Credentials, Long, Boolean> forUpdating() {
		return (credentials, organizationId) -> {
			Try<PermissionChecker> permissionCheckerTry =
				_getPermissionCheckerTry(credentials);

			Try<Organization> organizationTry = Try.fromFallible(
				() -> _organizationService.fetchOrganization(organizationId));

			return permissionCheckerTry.map(
				permissionChecker -> _portletResourcePermission.contains(
					permissionChecker,
					organizationTry.map(
						Organization::getGroupId
					).orElseThrow(
						() -> new NotFoundException()
					),
					ActionKeys.UPDATE)
			).orElse(
				false
			);
		};
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

	private BiFunction<Credentials, Long, Boolean> _permissionBridge() {
		return forUpdating();
	}

	@Reference
	private HasPermission _hasPermission;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private UserService _userService;

}