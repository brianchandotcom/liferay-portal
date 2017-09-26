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

package com.liferay.sync.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.UserGroupRole;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sherly Liu
 */
@Component(immediate = true, service = ModelListener.class)
public class UserGroupRoleModelListener
	extends SyncBaseModelListener<UserGroupRole> {

	@Override
	public void onAfterCreate(UserGroupRole userGroupRole)
		throws ModelListenerException {

		onAddRoleAssociation(userGroupRole.getRoleId());
	}

	@Override
	public void onAfterRemove(UserGroupRole userGroupRole)
		throws ModelListenerException {

		onRemoveRoleAssociation(userGroupRole.getRoleId());
	}

}