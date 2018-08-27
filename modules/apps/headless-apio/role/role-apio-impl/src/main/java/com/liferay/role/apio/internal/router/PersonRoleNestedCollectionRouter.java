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

package com.liferay.role.apio.internal.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.role.apio.identifier.RoleIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the Role resources contained
 * inside a Person through a web API. The resources are mapped from the internal
 * model {@link Role}.
 *
 * @author Javier Gamarra
 * @review
 */
@Component(immediate = true)
public class PersonRoleNestedCollectionRouter
	implements NestedCollectionRouter
		<Role, Long, RoleIdentifier, Long, PersonIdentifier> {

	@Override
	public NestedCollectionRoutes<Role, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Role, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	private PageItems<Role> _getPageItems(Pagination pagination, long userId)
		throws PortalException {

		List<Role> roles = _roleService.getUserRoles(userId);

		return new PageItems<>(roles, roles.size());
	}

	@Reference
	private RoleService _roleService;

}