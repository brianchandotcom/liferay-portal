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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.SystemEvent;
import com.liferay.portal.model.User;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.TrashVersion;
import com.liferay.portlet.trash.service.base.TrashEntryLocalServiceBaseImpl;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Provides the local service for accessing, adding, checking, and deleting
 * trash entries in the Recycle Bin.
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

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = classNameLocalService.getClassNameId(className);

		TrashEntry trashEntry = trashEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (trashEntry != null) {
			return trashEntry;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		SystemEvent systemEvent = trashHandler.addDeletionSystemEvent(
			userId, groupId, classPK, classUuid, referrerClassName);

		long entryId = counterLocalService.increment();

		trashEntry = trashEntryPersistence.create(entryId);

		trashEntry.setGroupId(groupId);
		trashEntry.setCompanyId(user.getCompanyId());
		trashEntry.setUserId(user.getUserId());
		trashEntry.setUserName(user.getFullName());
		trashEntry.setCreateDate(new Date());
		trashEntry.setClassNameId(classNameId);
		trashEntry.setClassPK(classPK);
		trashEntry.setSystemEventSetKey(systemEvent.getSystemEventSetKey());

		if (typeSettingsProperties != null) {
			trashEntry.setTypeSettingsProperties(typeSettingsProperties);
		}

		trashEntry.setStatus(status);

		trashEntryPersistence.update(trashEntry);

		if (statusOVPs != null) {
			for (ObjectValuePair<Long, Integer> statusOVP : statusOVPs) {
				long versionId = counterLocalService.increment();

				TrashVersion trashVersion = trashVersionPersistence.create(
					versionId);

				trashVersion.setEntryId(entryId);
				trashVersion.setClassNameId(classNameId);
				trashVersion.setClassPK(statusOVP.getKey());
				trashVersion.setStatus(statusOVP.getValue());

				trashVersionPersistence.update(trashVersion);
			}
		}

		return trashEntry;
	}

	@Override
	public void checkEntries() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			trashEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<TrashEntry>() {

				@Override
				public void performAction(TrashEntry trashEntry)
					throws PortalException {

					Date createDate = trashEntry.getCreateDate();

					Group group = groupPersistence.fetchByPrimaryKey(
						trashEntry.getGroupId());

					if (group == null) {
						return;
					}

					Date date = getMaxAge(group);

					if (createDate.before(date) ||
						!TrashUtil.isTrashEnabled(group)) {

						TrashHandler trashHandler =
							TrashHandlerRegistryUtil.getTrashHandler(
								trashEntry.getClassName());

						trashHandler.deleteTrashEntry(trashEntry.getClassPK());
					}
				}

			});
		actionableDynamicQuery.setTransactionAttribute(
			DefaultActionableDynamicQuery.REQUIRES_NEW_TRANSACTION_ATTRIBUTE);

		actionableDynamicQuery.performActions();
	}

	@Override
	public void deleteEntries(long groupId) {
		List<TrashEntry> entries = getEntries(groupId);

		for (TrashEntry entry : entries) {
			deleteEntry(entry);
		}
	}

	/**
	 * Deletes the trash entry with the primary key.
	 *
	 * @param  entryId the primary key of the trash entry
	 * @return the trash entry with the primary key
	 */
	@Override
	public TrashEntry deleteEntry(long entryId) {
		TrashEntry entry = trashEntryPersistence.fetchByPrimaryKey(entryId);

		return deleteEntry(entry);
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
		long classNameId = classNameLocalService.getClassNameId(className);

		TrashEntry entry = trashEntryPersistence.fetchByC_C(
			classNameId, classPK);

		return deleteEntry(entry);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public TrashEntry deleteEntry(TrashEntry trashEntry) {
		if (trashEntry != null) {
			trashVersionPersistence.removeByEntryId(trashEntry.getEntryId());

			trashEntry = trashEntryPersistence.remove(trashEntry);

			systemEventLocalService.deleteSystemEvents(
				trashEntry.getGroupId(), trashEntry.getSystemEventSetKey());
		}

		return trashEntry;
	}

	/**
	 * Returns the trash entry with the primary key.
	 *
	 * @param  entryId the primary key of the entry
	 * @return the trash entry with the primary key
	 */
	@Override
	public TrashEntry fetchEntry(long entryId) {
		return trashEntryPersistence.fetchByPrimaryKey(entryId);
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
		long classNameId = classNameLocalService.getClassNameId(className);

		return trashEntryPersistence.fetchByC_C(classNameId, classPK);
	}

	/**
	 * Returns the trash entries with the matching group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @return the trash entries with the group ID
	 */
	@Override
	public List<TrashEntry> getEntries(long groupId) {
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
	 */
	@Override
	public List<TrashEntry> getEntries(long groupId, int start, int end) {
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
	 */
	@Override
	public List<TrashEntry> getEntries(
		long groupId, int start, int end, OrderByComparator<TrashEntry> obc) {

		return trashEntryPersistence.findByGroupId(groupId, start, end, obc);
	}

	@Override
	public List<TrashEntry> getEntries(long groupId, String className) {
		long classNameId = classNameLocalService.getClassNameId(className);

		return trashEntryPersistence.findByG_C(groupId, classNameId);
	}

	/**
	 * Returns the number of trash entries with the group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @return the number of matching trash entries
	 */
	@Override
	public int getEntriesCount(long groupId) {
		return trashEntryPersistence.countByGroupId(groupId);
	}

	/**
	 * Returns the trash entry with the primary key.
	 *
	 * @param  entryId the primary key of the trash entry
	 * @return the trash entry with the primary key
	 */
	@Override
	public TrashEntry getEntry(long entryId) throws PortalException {
		return trashEntryPersistence.findByPrimaryKey(entryId);
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

		long classNameId = classNameLocalService.getClassNameId(className);

		return trashEntryPersistence.findByC_C(classNameId, classPK);
	}

	@Override
	public Hits search(
		long companyId, long groupId, long userId, String keywords, int start,
		int end, Sort sort) {

		try {
			Indexer<TrashEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(TrashEntry.class);

			SearchContext searchContext = buildSearchContext(
				companyId, groupId, userId, keywords, start, end, sort);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public BaseModelSearchResult<TrashEntry> searchTrashEntries(
		long companyId, long groupId, long userId, String keywords, int start,
		int end, Sort sort) {

		try {
			Indexer<TrashEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(TrashEntry.class);

			SearchContext searchContext = buildSearchContext(
				companyId, groupId, userId, keywords, start, end, sort);

			Hits hits = indexer.search(searchContext);

			List<TrashEntry> trashEntries = TrashUtil.getEntries(hits);

			return new BaseModelSearchResult<>(trashEntries, hits.getLength());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, long userId, String keywords, int start,
		int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setKeywords(keywords);
		searchContext.setGroupIds(new long[] {groupId});

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);
		searchContext.setUserId(userId);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected Date getMaxAge(Group group) throws PortalException {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(new Date());

		int maxAge = TrashUtil.getMaxAge(group);

		calendar.add(Calendar.MINUTE, -maxAge);

		return calendar.getTime();
	}

}