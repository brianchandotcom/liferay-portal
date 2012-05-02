/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.TrashVersion;
import com.liferay.portlet.trash.service.base.TrashEntryLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * The trash local service is responsible for accessing, creating, modifying and
 * deleting trash entries in the Recycle Bin.
 *
 * @author Zsolt Berentey
 */
public class TrashEntryLocalServiceImpl extends TrashEntryLocalServiceBaseImpl {

	/**
	 * Moves an entry to trash.
	 *
	 * @param  userId the primary key of the user removing the entity
	 * @param  groupId the primary key of the entry's group
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @param  status the status of the entity prior to being moved to trash
	 * @param  versions the primary keys and statuses of any of the entry's
	 *         versions (e.g., {@link
	 *         com.liferay.portlet.documentlibrary.model.DLFileVerison})
	 * @param  typeSettingsProperties the type settings properties
	 * @return the trashEntry
	 * @throws SystemException if a system exception occurred
	 */
	public TrashEntry addTrashEntry(
			long userId, long groupId, String className, long classPK,
			int status, List<ObjectValuePair<Long, Integer>> versions,
			UnicodeProperties typeSettingsProperties)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);

		long entryId = counterLocalService.increment();

		TrashEntry trashEntry = trashEntryPersistence.create(entryId);

		trashEntry.setGroupId(groupId);
		trashEntry.setCompanyId(user.getCompanyId());
		trashEntry.setUserId(user.getUserId());
		trashEntry.setUserName(user.getFullName());
		trashEntry.setCreateDate(new Date());
		trashEntry.setClassNameId(classNameId);
		trashEntry.setClassPK(classPK);

		if (typeSettingsProperties != null) {
			trashEntry.setTypeSettingsProperties(typeSettingsProperties);
		}

		trashEntry.setStatus(status);

		trashEntryPersistence.update(trashEntry, false);

		if (versions != null) {
			for (ObjectValuePair<Long, Integer> version : versions) {
				long versionClassPK = version.getKey();
				int versionStatus = version.getValue();

				long versionId = counterLocalService.increment();

				TrashVersion trashVersion = trashVersionPersistence.create(
					versionId);

				trashVersion.setEntryId(entryId);
				trashVersion.setClassNameId(classNameId);
				trashVersion.setClassPK(versionClassPK);
				trashVersion.setStatus(versionStatus);

				trashVersionPersistence.update(trashVersion, false);
			}
		}

		return trashEntry;
	}

	/**
	 * Deletes the trash entry with the entity class name and primary key.
	 *
	 * @param  className the class name of entity
	 * @param  classPK the primary key of the entry
	 * @throws PortalException if the user did not have permission to delete the
	 *         entry
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteEntry(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		TrashEntry trashEntry = trashEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (trashEntry != null) {
			trashVersionPersistence.removeByEntryId(trashEntry.getEntryId());

			trashEntryPersistence.remove(trashEntry);
		}
	}

	/**
	 * Returns the trash entry with the primary key.
	 *
	 * @param  entryId the primary key of the entry
	 * @return the trash entry with the primary key
	 * @throws SystemException if a system exception occurred
	 */
	public TrashEntry fetchEntry(long entryId) throws SystemException {
		return trashEntryPersistence.fetchByPrimaryKey(entryId);
	}

	/**
	 * Returns the trash entry with the entity class name and primary key.
	 *
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @return the trash entry with the entity class name and primary key
	 * @throws SystemException if a system exception occurred
	 */
	public TrashEntry fetchEntry(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return trashEntryPersistence.fetchByC_C(classNameId, classPK);
	}

	/**
	 * Returns the trash entries with the matching group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @return the trash entries with the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public List<TrashEntry> getEntries(long groupId) throws SystemException {
		return trashEntryPersistence.findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the trash entries matching the group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @param  start the lower bound of the range of trash entries to return
	 * @param  end the upper bound of the range of trash entries to return (not
	 *         inclusive)
	 * @return the range of matching trash entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<TrashEntry> getEntries(long groupId, int start, int end)
		throws SystemException {

		return trashEntryPersistence.findByGroupId(groupId, start, end);
	}

	/**
	 * Returns the number of trash entries with the group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @return the number of matching trash entries
	 * @throws SystemException if a system exception occurred
	 */
	public int getEntriesCount(long groupId) throws SystemException {
		return trashEntryPersistence.countByGroupId(groupId);
	}

	/**
	 * Returns the trash entry with the primary key.
	 *
	 * @param  entryId the primary key of the trash entry
	 * @return the trash entry with the primary key
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public TrashEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return trashEntryPersistence.findByPrimaryKey(entryId);
	}

	/**
	 * Returns the entry with the entity class name and primary key.
	 *
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @return the trash entry with the entity class name and primary key
	 * @throws PortalException if a trash entry with the primary key could not
	 *         be found
	 * @throws SystemException if a system exception occurred
	 */
	public TrashEntry getEntry(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return trashEntryPersistence.findByC_C(classNameId, classPK);
	}

	/**
	 * Returns all the trash versions associated with the trash entry.
	 *
	 * @param  entryId the primary key of the trash entry
	 * @return all the trash versions associated with the trash entry
	 * @throws SystemException if a system exception occurred
	 */
	public List<TrashVersion> getVersions(long entryId) throws SystemException {
		return trashEntryPersistence.getTrashVersions(entryId);
	}

}