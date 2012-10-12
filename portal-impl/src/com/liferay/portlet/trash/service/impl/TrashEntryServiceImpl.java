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
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.TrashEntryList;
import com.liferay.portlet.trash.model.TrashEntrySoap;
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
	 * @throws PrincipalException if a principal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteEntries(long groupId)
		throws PrincipalException, SystemException {

		List<TrashEntry> entries = trashEntryLocalService.getEntries(groupId);

		PermissionChecker permissionChecker = getPermissionChecker();

		for (TrashEntry entry : entries) {
			String className = entry.getClassName();
			long classPK = entry.getClassPK();

			try {
				TrashHandler trashHandler =
					TrashHandlerRegistryUtil.getTrashHandler(className);

				if (trashHandler.hasTrashPermission(
						permissionChecker, 0, classPK, ActionKeys.DELETE)) {

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
	 * @throws PrincipalException if a principal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public TrashEntryList getEntries(long groupId)
		throws PrincipalException, SystemException {

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
	 * @throws PrincipalException if a system exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public TrashEntryList getEntries(
			long groupId, int start, int end, OrderByComparator obc)
		throws PrincipalException, SystemException {

		TrashEntryList trashEntriesList = new TrashEntryList();

		int entriesCount = trashEntryLocalService.getEntriesCount(groupId);

		boolean approximate = entriesCount > PropsValues.TRASH_SEARCH_LIMIT;

		trashEntriesList.setApproximate(approximate);

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

				if (trashHandler.hasTrashPermission(
						permissionChecker, 0, classPK, ActionKeys.VIEW)) {

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

		trashEntriesList.setArray(TrashEntrySoap.toSoapModels(filteredEntries));
		trashEntriesList.setCount(filteredEntriesCount);

		return trashEntriesList;
	}

	private static Log _log = LogFactoryUtil.getLog(
		TrashEntryServiceImpl.class);

}