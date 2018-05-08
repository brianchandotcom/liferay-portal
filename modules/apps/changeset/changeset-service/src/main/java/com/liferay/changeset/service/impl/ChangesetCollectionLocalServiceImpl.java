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

package com.liferay.changeset.service.impl;

import com.liferay.changeset.exception.NoSuchCollectionException;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.service.base.ChangesetCollectionLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

/**
 * @author Brian Wing Shun Chan
 */
public class ChangesetCollectionLocalServiceImpl
	extends ChangesetCollectionLocalServiceBaseImpl {

	@Override
	public ChangesetCollection addChangesetCollection(
			long userId, long groupId, String name, String description)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long changesetCollectionId = counterLocalService.increment();

		ChangesetCollection changesetCollection =
			changesetCollectionPersistence.create(changesetCollectionId);

		changesetCollection.setGroupId(groupId);
		changesetCollection.setCompanyId(user.getCompanyId());
		changesetCollection.setUserId(user.getUserId());
		changesetCollection.setUserName(user.getFullName());
		changesetCollection.setName(name);
		changesetCollection.setDescription(description);

		return changesetCollectionPersistence.update(changesetCollection);
	}

	@Override
	public ChangesetCollection fetchChangesetCollection(
		long groupId, String name) {

		return changesetCollectionPersistence.fetchByG_N(groupId, name);
	}

	@Override
	public ChangesetCollection fetchOrAddChangesetCollection(
			long groupId, String name)
		throws PortalException {

		ChangesetCollection changesetCollection =
			changesetCollectionLocalService.fetchChangesetCollection(
				groupId, name);

		if (changesetCollection != null) {
			return changesetCollection;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		return changesetCollectionLocalService.addChangesetCollection(
			user.getUserId(), groupId, name, StringPool.BLANK);
	}

	@Override
	public ChangesetCollection getChangesetCollection(long groupId, String name)
		throws NoSuchCollectionException {

		return changesetCollectionPersistence.findByG_N(groupId, name);
	}

}