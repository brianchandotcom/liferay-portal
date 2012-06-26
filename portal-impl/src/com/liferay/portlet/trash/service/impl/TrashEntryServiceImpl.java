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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.base.TrashEntryServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The trash entry remote service is responsible for returning trash entries.
 * For more information on trash entries services and TrashEntry, see {@link
 * com.liferay.portlet.trash.service.impl.TrashEntryLocalServiceImpl}.
 *
 * @author Julio Camarero
 */
public class TrashEntryServiceImpl extends TrashEntryServiceBaseImpl {

	/**
	 * Deletes the trash entries with the matching group ID considering
	 * permissions.
	 *
	 * @param  groupId the primary key of the group
	 * @throws SystemException if a system exception occurred
	 * @throws PrincipalException if a principal exception occurred
	 */
	public void deleteEntries(long groupId)
		throws SystemException, PrincipalException {

		List<TrashEntry> entries = trashEntryLocalService.getEntries(groupId);

		PermissionChecker permissionChecker = getPermissionChecker();

		for (TrashEntry entry : entries) {
			String className = entry.getClassName();
			long classPK = entry.getClassPK();

			try {
				TrashHandler trashHandler =
					TrashHandlerRegistryUtil.getTrashHandler(className);

				TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
					classPK);

				if (trashRenderer.hasDeletePermission(permissionChecker)) {
					trashHandler.deleteTrashEntry(classPK);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	/**
	 * Returns the trash entries with the matching group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @return the matching trash entries
	 * @throws SystemException if a system exception occurred
	 * @throws PrincipalException if a principal exception occurred
	 */
	public Object[] getEntries(long groupId)
		throws SystemException, PrincipalException {

		return getEntries(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	 * @throws PrincipalException if a system exception occurred
	 */
	public Object[] getEntries(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException, PrincipalException {

		int entriesCount = trashEntryLocalService.getEntriesCount(groupId);

		List<TrashEntry> entries = trashEntryLocalService.getEntries(
			groupId, 0, end + PropsValues.TRASH_SEARCH_LIMIT, obc);

		List<TrashEntry> filteredEntries = new ArrayList<TrashEntry>();

		PermissionChecker permissionChecker = getPermissionChecker();

		for (TrashEntry entry : entries) {
			String className = entry.getClassName();
			long classPK = entry.getClassPK();

			try {
				TrashHandler trashHandler =
					TrashHandlerRegistryUtil.getTrashHandler(className);

				TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
					classPK);

				if (trashRenderer.hasViewPermission(permissionChecker)) {
					filteredEntries.add(entry);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		int filteredEntriesCount = filteredEntries.size();

		if ((end != QueryUtil.ALL_POS) && (start != QueryUtil.ALL_POS)) {
			if (end > filteredEntriesCount) {
				end = filteredEntriesCount;
			}

			if (start > filteredEntriesCount) {
				start = filteredEntriesCount;
			}

			filteredEntries = filteredEntries.subList(start, end);
		}

		boolean approximate = entriesCount > PropsValues.TRASH_SEARCH_LIMIT;

		return new Object[] {
			filteredEntries, filteredEntriesCount, approximate};
	}

	/**
	 * Returns an ordered range of all the trash entries which match the
	 * keywords and the group ID, using the indexer. It is preferable to use
	 * this method instead of the non-indexed version whenever possible for
	 * performance reasons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  groupId the primary key of the group
	 * @param  userId the primary key of the user
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         user's first name, middle name, last name, screen name, or email
	 *         address
	 * @param  start the lower bound of the range of users
	 * @param  end the upper bound of the range of users (not inclusive)
	 * @param  sort the field and direction to sort by (optionally
	 *         <code>null</code>)
	 * @return the matching users
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portlet.usersadmin.util.UserIndexer
	 */
	public Hits search(
			long companyId, long groupId, long userId, String keywords,
			int start, int end, Sort sort)
		throws SystemException {

		try {
			SearchContext searchContext = new SearchContext();

			searchContext.setEnd(end);
			searchContext.setStart(start);

			searchContext.setCompanyId(companyId);
			searchContext.setUserId(userId);
			searchContext.setGroupIds(new long[] {groupId});

			searchContext.setKeywords(keywords);

			if (sort != null) {
				searchContext.setSorts(new Sort[] {sort});
			}

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				TrashEntry.class);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		TrashEntryServiceImpl.class);

}