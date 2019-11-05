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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.util.ArrayList;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = ModelListener.class)
public class GroupModelListener extends BaseEntityModelListener<Group> {

	@Override
	public String getObjectType() {
		return "group";
	}

	@Override
	public void onAfterCreate(Group group) throws ModelListenerException {
		send("add", group);
	}

	@Override
	public void onBeforeRemove(Group group) throws ModelListenerException {
		send("delete", group);
	}

	@Override
	public void onBeforeUpdate(Group newGroup) throws ModelListenerException {
		try {
			Group oldGroup = _groupLocalService.getGroup(newGroup.getGroupId());

			if (_equals(newGroup, oldGroup)) {
				return;
			}

			send("update", newGroup);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	private boolean _equals(Group newGroup, Group oldGroup) {
		Set<String> modifiedAttributes = getModifiedAttributes(
			new ArrayList<String>() {
				{
					add("description");
					add("descriptiveName");
					add("friendlyURL");
					add("name");
				}
			},
			newGroup, oldGroup);

		if (!modifiedAttributes.isEmpty()) {
			return false;
		}

		return true;
	}

	@Reference
	private GroupLocalService _groupLocalService;

}