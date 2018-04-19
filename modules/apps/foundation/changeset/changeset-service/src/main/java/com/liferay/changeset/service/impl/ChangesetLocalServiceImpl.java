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

import com.liferay.changeset.exception.NoSuchChangesetException;
import com.liferay.changeset.model.Changeset;
import com.liferay.changeset.service.base.ChangesetLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

/**
 * The implementation of the changeset local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.changeset.service.ChangesetLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetLocalServiceBaseImpl
 * @see com.liferay.changeset.service.ChangesetLocalServiceUtil
 */
public class ChangesetLocalServiceImpl extends ChangesetLocalServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.liferay.changeset.service.ChangesetLocalServiceUtil} to access the changeset local service.
	 */
	@Override
	public Changeset addChangeset(
			long userId, long groupId, String name, String description)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long changesetId = counterLocalService.increment();

		Changeset changeset = changesetPersistence.create(changesetId);

		changeset.setGroupId(groupId);
		changeset.setCompanyId(user.getCompanyId());
		changeset.setUserId(user.getUserId());
		changeset.setUserName(user.getFullName());
		changeset.setName(name);
		changeset.setDescription(description);

		return changesetPersistence.update(changeset);
	}

	@Override
	public Changeset fetchChangeset(long groupId, String name) {
		return changesetPersistence.fetchByG_N(groupId, name);
	}

	@Override
	public Changeset fetchOrAddChangeset(long groupId, String name)
		throws PortalException {

		Changeset changeset = changesetLocalService.fetchChangeset(
			groupId, name);

		if (changeset != null) {
			return changeset;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		return changesetLocalService.addChangeset(
			user.getUserId(), groupId, name, StringPool.BLANK);
	}

	@Override
	public Changeset getChangeset(long groupId, String name)
		throws NoSuchChangesetException {

		return changesetPersistence.findByG_N(groupId, name);
	}

}