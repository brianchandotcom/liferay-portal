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

import com.liferay.apio.architect.alias.routes.permission.HasNestedAddingPermissionFunction;
import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.function.throwable.ThrowableTriFunction;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.commerce.data.integration.apio.identifiers.AccountIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.OrganizationService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.Organization"
)
public class AccountPermissionImpl implements HasPermission<Long> {

	@Override
	public <S> HasNestedAddingPermissionFunction<S> forAddingIn(
			Class<? extends Identifier<S>> identifierClass) {

		if (identifierClass.equals(AccountIdentifier.class)) {
			return (credentials, groupId) ->
				_portletResourcePermission.contains(
					(PermissionChecker)credentials.get(), (Long)groupId,
					ActionKeys.ADD_ORGANIZATION);
		}

		return (credentials, s) -> false;
	}

	@Override
	public Boolean forDeleting(Credentials credentials, Long entryId)
		throws Exception {

		return _forItemRoutesOperations().apply(credentials, entryId, ActionKeys.DELETE);
	}

	@Override
	public Boolean forUpdating(Credentials credentials, Long entryId)
		throws Exception {

		return _forItemRoutesOperations().apply(credentials, entryId, ActionKeys.UPDATE);
	}

	private ThrowableTriFunction<Credentials, Long, String, Boolean>
	_forItemRoutesOperations() {

		return (credentials, organizationId, actionId) -> {
			Organization organization =
				_organizationService.fetchOrganization(
					organizationId);

			return _portletResourcePermission.contains(
				(PermissionChecker)credentials.get(),
				organization.getGroupId(),
				actionId);
		};
	}

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private PortletResourcePermission _portletResourcePermission;

}