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
 * The implementation of the changeset collection local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the {@link
 * com.liferay.changeset.service.ChangesetCollectionLocalService} interface.
 * <p> This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    ChangesetCollectionLocalServiceBaseImpl
 * @see    com.liferay.changeset.service.ChangesetCollectionLocalServiceUtil
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