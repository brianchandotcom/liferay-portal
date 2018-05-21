/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.headless.user.apio.internal.security;

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
@Component(immediate = true, service = RolePermissionChecker.class)
public class RolePermissionChecker {

    public Boolean forAdding(Credentials credentials) {

        Try<PermissionChecker> permissionCheckerTry =
            _getPermissionCheckerTry(credentials);

        return permissionCheckerTry.map(
            permissionChecker -> PortalPermissionUtil.contains(
                permissionChecker, ActionKeys.ADD_ROLE)
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
    private OrganizationService _organizationService;

    @Reference
    private UserService _userService;

    @Reference
    private HasPermission _hasPermission;

}
