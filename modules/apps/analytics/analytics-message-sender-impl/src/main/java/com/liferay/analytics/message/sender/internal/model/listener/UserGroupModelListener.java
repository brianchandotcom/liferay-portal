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

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;

import java.util.ArrayList;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = ModelListener.class)
public class UserGroupModelListener extends BaseEntityModelListener<UserGroup> {

	@Override
	public String getObjectType() {
		return "usergroup";
	}

	@Override
	public void onAfterCreate(UserGroup userGroup)
		throws ModelListenerException {

		send("add", userGroup);
	}

	@Override
	public void onBeforeRemove(UserGroup userGroup)
		throws ModelListenerException {

		send("delete", userGroup);
	}

	@Override
	public void onBeforeUpdate(UserGroup newUserGroup)
		throws ModelListenerException {

		try {
			UserGroup oldUserGroup = _userGroupLocalService.getUserGroup(
				newUserGroup.getUserGroupId());

			if (_equals(newUserGroup, oldUserGroup)) {
				return;
			}

			send("update", newUserGroup);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	private boolean _equals(UserGroup newUserGroup, UserGroup oldUserGroup) {
		Set<String> modifiedAttributes = getModifiedAttributes(
			new ArrayList<String>() {
				{
					add("description");
					add("name");
				}
			},
			newUserGroup, oldUserGroup);

		if (!modifiedAttributes.isEmpty()) {
			return false;
		}

		return true;
	}

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}