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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.TrashVersion;
import com.liferay.portlet.trash.service.base.TrashEntryLocalServiceBaseImpl;

import java.util.Calendar;
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
	 *         com.liferay.portlet.documentlibrary.model.DLFileVersion})
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

	public void cleanUpEntries() throws PortalException, SystemException {
		List<Company> companies = CompanyLocalServiceUtil.getCompanies();

		for (Company company : companies) {
			List<Group> groups = GroupLocalServiceUtil.search(
				company.getCompanyId(), null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			for (Group group : groups) {
				cleanUpEntries(group.getCompanyId(), group.getGroupId());
			}
		}
	}

	public void cleanUpEntries(long companyId, long groupId)
		throws PortalException, SystemException {

		Date date = getCleanUpLimitDate(companyId, groupId);

		List<TrashEntry> trashEntries = trashEntryPersistence.findByG_C_CD(
			groupId, companyId, date);

		for (TrashEntry trashEntry : trashEntries) {
			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					trashEntry.getClassName());

			trashHandler.deleteTrashEntry(trashEntry.getClassPK(), false);
		}
	}

	/**
	 * Deletes the trash entry with the primary key.
	 *
	 * @param  entryId the primary key of the trash entry
	 * @throws PortalException if a trash entry with the primary key could not
	 *         be found
	 * @throws SystemException if a system exception occurred
	 */
	public TrashEntry deleteEntry(long entryId)
		throws PortalException, SystemException {

		TrashEntry entry = trashEntryPersistence.fetchByPrimaryKey(entryId);

		return deleteEntry(entry);
	}

	/**
	 * Deletes the trash entry with the entity class name and primary key.
	 *
	 * @param  className the class name of entity
	 * @param  classPK the primary key of the entry
	 * @throws PortalException if a trash entry with the primary key could not
	 *         be found
	 * @throws SystemException if a system exception occurred
	 */
	public TrashEntry deleteEntry(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		TrashEntry entry = trashEntryPersistence.fetchByC_C(
			classNameId, classPK);

		return deleteEntry(entry);
	}

	@Indexable(type = IndexableType.DELETE)
	public TrashEntry deleteEntry(TrashEntry trashEntry)
		throws SystemException {

		if (trashEntry != null) {
			trashVersionPersistence.removeByEntryId(trashEntry.getEntryId());

			trashEntry = trashEntryPersistence.remove(trashEntry);
		}

		return trashEntry;
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
	 * Returns a range of all the trash entries matching the group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @param  start the lower bound of the range of trash entries to return
	 * @param  end the upper bound of the range of trash entries to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the trash entries (optionally
	 *         <code>null</code>)
	 * @return the range of matching trash entries ordered by comparator
	 *         <code>obc</code>
	 * @throws SystemException if a system exception occurred
	 */
	public List<TrashEntry> getEntries(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return trashEntryPersistence.findByGroupId(groupId, start, end, obc);
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
	 * @throws PortalException if a trash entry with the primary key could not
	 *         be found
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

	/**
	 * Returns all the trash versions associated with the trash entry.
	 *
	 * @param  className the class name of the trash entity
	 * @param  classPK the primary key of the trash entity
	 * @return all the trash versions associated with the trash entry
	 * @throws SystemException if a system exception occurred
	 */
	public List<TrashVersion> getVersions(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return trashVersionPersistence.findByC_C(classNameId, classPK);
	}

	protected Date getCleanUpLimitDate(long companyId, long groupId)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		int trashEntriesMaxAge = PrefsPropsUtil.getInteger(
			companyId, PropsKeys.TRASH_ENTRIES_MAX_AGE,
			PropsValues.TRASH_ENTRIES_MAX_AGE);

		UnicodeProperties groupTypeSettings = group.getTypeSettingsProperties();

		trashEntriesMaxAge = GetterUtil.getInteger(
			groupTypeSettings.getProperty("trashEntriesMaxAge"),
			trashEntriesMaxAge);

		Calendar cal = Calendar.getInstance();

		cal.setTime(new Date());

		cal.add(Calendar.DATE, -trashEntriesMaxAge);

		return cal.getTime();
	}

}