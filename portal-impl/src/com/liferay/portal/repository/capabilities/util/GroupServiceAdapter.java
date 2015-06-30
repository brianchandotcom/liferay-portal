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

package com.liferay.portal.repository.capabilities.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;

/**
 * @author Iván Zaera
 */
public class GroupServiceAdapter {

	public GroupServiceAdapter(GroupLocalService repositoryLocalService) {
		this(repositoryLocalService, null);
	}

	public GroupServiceAdapter(
		GroupLocalService repositoryLocalService,
		GroupService repositoryService) {

		_groupLocalService = repositoryLocalService;
		_groupService = repositoryService;
	}

	public Group getGroup(long groupId) throws PortalException {
		Group group = null;

		if (_groupService != null) {
			group = _groupService.getGroup(groupId);
		}
		else {
			group = _groupLocalService.getGroup(groupId);
		}

		return group;
	}

	private final GroupLocalService _groupLocalService;
	private final GroupService _groupService;

}