/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.trash.TrashEntryConstants;
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
 * @author Zsolt Berentey
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

		boolean error = false;

		List<TrashEntry> entries = trashEntryLocalService.getEntries(groupId);

		for (TrashEntry entry : entries) {
			try {
				deleteEntry(entry);
			}
			catch (Exception e) {
				error = true;

				_log.error(e);
			}
		}

		if (error) {
			throw new PrincipalException("trash.empty.error");
		}
	}

	/**
	 * Deletes the trash entries with the primary keys.
	 *
	 * @param  entryIds the primary keys of the trash entries
	 * @throws PortalException if the user didn't have permission to delete one
	 *         or more entries
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteEntries(long[] entryIds)
		throws PortalException, SystemException {

		String errorKey = null;

		for (long entryId : entryIds) {
			try {
				deleteEntry(entryId);
			}
			catch (PrincipalException pe) {
				errorKey = pe.getMessage();
			}
		}

		if (errorKey != null) {
			throw new PrincipalException(errorKey);
		}
	}

	/**
	 * Deletes the trash entry with the primary key.
	 *
	 * <p>
	 * This method throws a PrincipalException if the user didn't have the
	 * permissions to perform the necessary operation. The exception is created
	 * with different messages for different operations:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * trash.delete.error - if the permission to delete the item was missing
	 * </li>
	 * </ul>
	 *
	 * @param  entryId the primary key of the trash entry
	 * @throws PortalException if the user didn't have permission to delete the
	 *         entry
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		TrashEntry entry = trashEntryLocalService.getEntry(entryId);

		deleteEntry(entry);
	}

	/**
	 * Deletes the trash entry with the entity class name and primary key.
	 *
	 * <p>
	 * This method throws a PrincipalException if the user didn't have the
	 * permissions to perform the necessary operation. The exception is created
	 * with different messages for different operations:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * trash.delete.error - if the permission to delete the item was missing
	 * </li>
	 * </ul>
	 *
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @throws PortalException if the user didn't have permission to delete the
	 *         entry
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteEntry(String className, long classPK)
		throws PortalException, SystemException {

		TrashEntry entry = trashEntryLocalService.getEntry(className, classPK);

		deleteEntry(entry);
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

	/**
	 * Restores the trash entry with the primary key by moving it to a new
	 * location identified by destination container model ID.
	 *
	 * <p>
	 * This method throws a PrincipalException if the user didn't have the
	 * permissions to perform one of the necessary operations. The exception is
	 * created with different messages for different operations:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * trash.move.error - if the permission to add the item to the new
	 * destination was missing
	 * </li>
	 * <li>
	 * trash.restore.error - if the permission to restore the item from trash
	 * was missing
	 * </li>
	 * </ul>
	 *
	 * @param  groupId the primary key of the group
	 * @param  entryId the primary key of the trash entry
	 * @param  destinationContainerModelId the primary key of the new location
	 * @param  serviceContext the service context (optionally <code>null</code>)
	 * @throws PortalException if the user didn't have permission to add the
	 *         entry to its new location or to restore it from the trash in
	 *         general
	 * @throws SystemException if a system exception occurred
	 */
	public void moveEntry(
			String className, long classPK, long destinationContainerModelId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		TrashEntry entry = trashEntryLocalService.getEntry(className, classPK);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		if (!trashHandler.hasTrashPermission(
				permissionChecker, entry.getGroupId(),
				destinationContainerModelId, TrashActionKeys.MOVE)) {

			throw new PrincipalException("trash.move.error");
		}

		if (trashHandler.isInTrash(classPK) &&
			!trashHandler.hasTrashPermission(
				permissionChecker, 0, classPK, TrashActionKeys.RESTORE)) {

			throw new PrincipalException("trash.restore.error");
		}

		trashHandler.checkDuplicateTrashEntry(
			entry, destinationContainerModelId, StringPool.BLANK);

		if (trashHandler.isInTrash(classPK)) {
			trashHandler.moveTrashEntry(
				classPK, destinationContainerModelId, serviceContext);
		}
		else {
			trashHandler.moveEntry(
				classPK, destinationContainerModelId, serviceContext);
		}
	}

	/**
	 * Restores to trash entry to its original location. In case of duplication,
	 * on of the optional parameters are set indicating either to overwrite the
	 * existing item or to rename the entry before restore.
	 *
	 * <p>
	 * This method throws a PrincipalException if the user didn't have the
	 * permissions to perform one of the necessary operations. The exception is
	 * created with different messages for different operations:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * trash.restore.error - if the permission to restore the item from trash
	 * was missing
	 * </li>
	 * <li>
	 * trash.restore.overwrite.error - if the permission to delete the existing
	 * item was missing
	 * </li>
	 * <li>
	 * trash.restore.rename.error - if the permission to rename the entry was
	 * missing
	 * </li>
	 * </ul>
	 *
	 * @param  entryId the primary key of the trash entry
	 * @param  overrideClassPK the primary key of the item to overwrite
	 * @param  name the new name of the entry (optionally <code>null</code>)
	 * @return the trash entry that was restored
	 * @throws PortalException if the user didn't have permission to overwrite
	 *         an existing item, to rename the entry or to restore the entry
	 *         from the trash in general
	 * @throws SystemException if a system exception occurred
	 */
	public TrashEntry restoreEntry(
			long entryId, long overrideClassPK, String name)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		TrashEntry entry = trashEntryLocalService.getTrashEntry(entryId);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			entry.getClassName());

		if (!trashHandler.hasTrashPermission(
				permissionChecker, 0, entry.getClassPK(),
				TrashActionKeys.RESTORE)) {

			throw new PrincipalException("trash.restore.error");
		}

		if (overrideClassPK > 0) {
			if (!trashHandler.hasTrashPermission(
					permissionChecker, 0, overrideClassPK,
					TrashActionKeys.OVERWRITE)) {

				throw new PrincipalException("trash.restore.overwrite.error");
			}

			trashHandler.deleteTrashEntry(overrideClassPK);
		}
		else if (name != null) {
			if (!trashHandler.hasTrashPermission(
					permissionChecker, 0, entry.getClassPK(),
					TrashActionKeys.RENAME)) {

				throw new PrincipalException("trash.restore.rename.error");
			}

			trashHandler.updateTitle(entry.getClassPK(), name);
		}

		trashHandler.checkDuplicateTrashEntry(
			entry, TrashEntryConstants.DEFAULT_CONTAINER_ID, null);

		trashHandler.restoreTrashEntry(getUserId(), entry.getClassPK());

		return entry;
	}

	protected void deleteEntry(TrashEntry entry)
		throws PortalException, SystemException {

		String className = entry.getClassName();
		long classPK = entry.getClassPK();

		PermissionChecker permissionChecker = getPermissionChecker();

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		if (!trashHandler.hasTrashPermission(
				permissionChecker, 0, classPK, ActionKeys.DELETE)) {

			throw new PrincipalException("trash.delete.error");
		}

		trashHandler.deleteTrashEntry(classPK);
	}

	private static Log _log = LogFactoryUtil.getLog(
		TrashEntryServiceImpl.class);

}