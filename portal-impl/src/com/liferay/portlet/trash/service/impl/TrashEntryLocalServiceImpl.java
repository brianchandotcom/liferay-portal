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

package com.liferay.portlet.trash.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.trash.service.base.TrashEntryLocalServiceBaseImpl;
import com.liferay.trash.kernel.model.TrashEntry;

import java.util.Date;
import java.util.List;

/**
 * Provides the local service for accessing, adding, checking, and deleting
 * trash entries in the Recycle Bin.
 *
 * @author Zsolt Berentey
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.trash.service.impl.TrashEntryLocalServiceImpl}
 */
@Deprecated
public class TrashEntryLocalServiceImpl extends TrashEntryLocalServiceBaseImpl {

	/**
	 * Moves an entry to trash.
	 *
	 * @param  userId the primary key of the user removing the entity
	 * @param  groupId the primary key of the entry's group
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @param  classUuid the UUID of the entity's class
	 * @param  referrerClassName the referrer class name used to add a deletion
	 *         {@link SystemEvent}
	 * @param  status the status of the entity prior to being moved to trash
	 * @param  statusOVPs the primary keys and statuses of any of the entry's
	 *         versions (e.g., {@link
	 *         com.liferay.portlet.documentlibrary.model.DLFileVersion})
	 * @param  typeSettingsProperties the type settings properties
	 * @return the trashEntry
	 */
	@Override
	public TrashEntry addTrashEntry(
			long userId, long groupId, String className, long classPK,
			String classUuid, String referrerClassName, int status,
			List<ObjectValuePair<Long, Integer>> statusOVPs,
			UnicodeProperties typeSettingsProperties)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	@Override
	public void checkEntries() throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	@Override
	public void deleteEntries(long groupId) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	/**
	 * Deletes the trash entry with the primary key.
	 *
	 * @param  entryId the primary key of the trash entry
	 * @return the trash entry with the primary key
	 */
	@Override
	public TrashEntry deleteEntry(long entryId) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	/**
	 * Deletes the trash entry with the entity class name and primary key.
	 *
	 * @param  className the class name of entity
	 * @param  classPK the primary key of the entry
	 * @return the trash entry with the entity class name and primary key
	 */
	@Override
	public TrashEntry deleteEntry(String className, long classPK) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public TrashEntry deleteEntry(TrashEntry trashEntry) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	/**
	 * Returns the trash entry with the primary key.
	 *
	 * @param  entryId the primary key of the entry
	 * @return the trash entry with the primary key
	 */
	@Override
	public TrashEntry fetchEntry(long entryId) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	/**
	 * Returns the trash entry with the entity class name and primary key.
	 *
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @return the trash entry with the entity class name and primary key
	 */
	@Override
	public TrashEntry fetchEntry(String className, long classPK) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	/**
	 * Returns the trash entries with the matching group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @return the trash entries with the group ID
	 */
	@Override
	public List<TrashEntry> getEntries(long groupId) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	/**
	 * Returns a range of all the trash entries matching the group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @param  start the lower bound of the range of trash entries to return
	 * @param  end the upper bound of the range of trash entries to return (not
	 *         inclusive)
	 * @return the range of matching trash entries
	 */
	@Override
	public List<TrashEntry> getEntries(long groupId, int start, int end) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
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
	 */
	@Override
	public List<TrashEntry> getEntries(
		long groupId, int start, int end, OrderByComparator<TrashEntry> obc) {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	@Override
	public List<TrashEntry> getEntries(long groupId, String className) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	/**
	 * Returns the number of trash entries with the group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @return the number of matching trash entries
	 */
	@Override
	public int getEntriesCount(long groupId) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	/**
	 * Returns the trash entry with the primary key.
	 *
	 * @param  entryId the primary key of the trash entry
	 * @return the trash entry with the primary key
	 */
	@Override
	public TrashEntry getEntry(long entryId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	/**
	 * Returns the entry with the entity class name and primary key.
	 *
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @return the trash entry with the entity class name and primary key
	 */
	@Override
	public TrashEntry getEntry(String className, long classPK)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	@Override
	public Hits search(
		long companyId, long groupId, long userId, String keywords, int start,
		int end, Sort sort) {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	@Override
	public BaseModelSearchResult<TrashEntry> searchTrashEntries(
		long companyId, long groupId, long userId, String keywords, int start,
		int end, Sort sort) {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, long userId, String keywords, int start,
		int end, Sort sort) {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

	protected Date getMaxAge(Group group) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryLocalServiceImpl");
	}

}