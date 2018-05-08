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

import com.liferay.changeset.exception.NoSuchEntryException;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.changeset.service.base.ChangesetEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

/**
 * @author Brian Wing Shun Chan
 */
public class ChangesetEntryLocalServiceImpl
	extends ChangesetEntryLocalServiceBaseImpl {

	@Override
	public ChangesetEntry addChangesetEntry(
			long userId, long changesetCollectionId, long classNameId,
			long classPK)
		throws PortalException {

		User user = userLocalService.getUser(userId);
		ChangesetCollection changesetCollection =
			changesetCollectionPersistence.fetchByPrimaryKey(
				changesetCollectionId);

		long changesetEntryId = counterLocalService.increment();

		ChangesetEntry changesetEntry = changesetEntryPersistence.create(
			changesetEntryId);

		changesetEntry.setGroupId(changesetCollection.getGroupId());
		changesetEntry.setCompanyId(user.getCompanyId());
		changesetEntry.setUserId(user.getUserId());
		changesetEntry.setUserName(user.getFullName());
		changesetEntry.setChangesetCollectionId(changesetCollectionId);
		changesetEntry.setClassNameId(classNameId);
		changesetEntry.setClassPK(classPK);

		return changesetEntryPersistence.update(changesetEntry);
	}

	@Override
	public ChangesetEntry fetchChangesetEntry(
		long changesetCollectionId, long classNameId, long classPK) {

		return changesetEntryPersistence.fetchByC_C_C(
			changesetCollectionId, classNameId, classPK);
	}

	@Override
	public ChangesetEntry fetchOrAddChangesetEntry(
			long changesetCollectionId, long classNameId, long classPK)
		throws PortalException {

		ChangesetEntry changesetEntry =
			changesetEntryLocalService.fetchChangesetEntry(
				changesetCollectionId, classNameId, classPK);

		if (changesetEntry != null) {
			return changesetEntry;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		return changesetEntryLocalService.addChangesetEntry(
			user.getUserId(), changesetCollectionId, classNameId, classPK);
	}

	@Override
	public long getChangesetEntriesCount(long changesetCollectionId) {
		return changesetEntryPersistence.countByChangesetCollectionId(
			changesetCollectionId);
	}

	@Override
	public long getChangesetEntriesCount(
		long changesetCollectionId, long classNameId) {

		return changesetEntryPersistence.countByC_C(
			changesetCollectionId, classNameId);
	}

	@Override
	public ChangesetEntry getChangesetEntry(
			long changesetCollectionId, long classNameId, long classPK)
		throws NoSuchEntryException {

		return changesetEntryPersistence.findByC_C_C(
			changesetCollectionId, classNameId, classPK);
	}

}