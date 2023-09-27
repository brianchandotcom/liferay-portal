/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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