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

import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.person.apio.architect.identifier.MyUserAccountIdentifier;
import com.liferay.portal.kernel.model.Role;
import com.liferay.role.apio.identifier.RoleIdentifier;
import com.liferay.role.apio.internal.router.base.BaseUserAccountRoleNestedCollectionRouter;

import org.osgi.service.component.annotations.Component;

/**
 * Provides the information necessary to expose the {@code Role} resources of a
 * {@code MyUserAccount} through a web API. The resources are mapped from the
 * internal model {@link Role}.
 *
 * @author Eduardo Pérez
 */
@Component(immediate = true, service = NestedCollectionRouter.class)
public class MyUserAcountRoleNestedCollectionRouter
	extends BaseUserAccountRoleNestedCollectionRouter<MyUserAccountIdentifier>
	implements NestedCollectionRouter
		<Role, Long, RoleIdentifier, Long, MyUserAccountIdentifier> {
}