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

package com.liferay.blogs.internal.model.listener;

import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcell Gyöpös
 */
@Component(service = ModelListener.class)
public class UserGroupModelListener extends BaseModelListener<UserGroup> {

	@Override
	public void onAfterRemoveAssociation(
			Object userGroupId, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			if (associationClassName.equals(User.class.getName())) {
				_unsubscribeUserFromUserGroupGroups(
					(long)associationClassPK, (long)userGroupId);
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}

		try {
			if (associationClassName.equals(Group.class.getName())) {
				_unsubscribeUserGroupUsersFromGroup(
					(long)associationClassPK, (long)userGroupId);
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private void _unsubscribeUserFromUserGroupGroups(
			long userId, long userGroupId)
		throws PortalException {

		List<Group> userGroupGroups = _groupLocalService.getUserGroupGroups(
			userGroupId);

		userGroupGroups.removeAll(
			_groupLocalService.getUserGroups(userId, true));

		for (Group group : userGroupGroups) {
			_blogsEntryLocalService.unsubscribe(userId, group.getGroupId());
		}
	}

	private void _unsubscribeUserGroupUsersFromGroup(
			long groupId, long userGroupId)
		throws PortalException {

		for (long userId :
				_userGroupLocalService.getUserPrimaryKeys(userGroupId)) {

			if (!_groupLocalService.hasUserGroup(userId, groupId)) {
				_blogsEntryLocalService.unsubscribe(userId, groupId);
			}
		}
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}