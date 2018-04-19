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
import com.liferay.changeset.model.Changeset;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.changeset.service.base.ChangesetEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

/**
 * The implementation of the changeset entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.changeset.service.ChangesetEntryLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetEntryLocalServiceBaseImpl
 * @see com.liferay.changeset.service.ChangesetEntryLocalServiceUtil
 */
public class ChangesetEntryLocalServiceImpl
	extends ChangesetEntryLocalServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.liferay.changeset.service.ChangesetEntryLocalServiceUtil} to access the changeset entry local service.
	 */
	@Override
	public ChangesetEntry addEntry(
			long userId, long changesetId, long classNameId, long classPK)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		Changeset changeset = changesetPersistence.fetchByPrimaryKey(
			changesetId);

		long entryId = counterLocalService.increment();

		ChangesetEntry entry = changesetEntryPersistence.create(entryId);

		entry.setGroupId(changeset.getGroupId());
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setChangesetId(changesetId);
		entry.setClassNameId(classNameId);
		entry.setClassPK(classPK);

		return changesetEntryPersistence.update(entry);
	}

	@Override
	public ChangesetEntry addIfNotExists(
			long changesetId, long classNameId, long classPK)
		throws PortalException {

		ChangesetEntry entry = changesetEntryLocalService.fetchEntry(
			changesetId, classNameId, classPK);

		if (entry != null) {
			return entry;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		return changesetEntryLocalService.addEntry(
			user.getUserId(), changesetId, classNameId, classPK);
	}

	@Override
	public ChangesetEntry fetchEntry(
		long changesetId, long classNameId, long classPK) {

		return changesetEntryPersistence.fetchByC_C_C(
			changesetId, classNameId, classPK);
	}

	@Override
	public long getEntriesCount(long changesetId) {
		return changesetEntryPersistence.countByChangesetId(changesetId);
	}

	@Override
	public long getEntriesCount(long changesetId, long classNameId) {
		return changesetEntryPersistence.countByC_C(changesetId, classNameId);
	}

	@Override
	public ChangesetEntry getEntry(
			long changesetId, long classNameId, long classPK)
		throws NoSuchEntryException {

		return changesetEntryPersistence.findByC_C_C(
			changesetId, classNameId, classPK);
	}

}