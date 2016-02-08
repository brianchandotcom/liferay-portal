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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.GroupServiceUtil;

/**
 * @author Iván Zaera
 */
public class GroupServiceAdapter {

	public static GroupServiceAdapter create(
		DocumentRepository documentRepository) {

		if (documentRepository instanceof LocalRepository) {
			return new GroupServiceAdapter(GroupLocalServiceUtil.getService());
		}

		return new GroupServiceAdapter(
			GroupLocalServiceUtil.getService(), GroupServiceUtil.getService());
	}

	public GroupServiceAdapter(GroupLocalService groupLocalService) {
		this(groupLocalService, null);
	}

	public GroupServiceAdapter(
		GroupLocalService groupLocalService, GroupService groupService) {

		_groupLocalService = groupLocalService;
		_groupService = groupService;
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