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

import com.liferay.portal.TrashPermissionException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Transactional;
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
import com.liferay.portlet.trash.model.impl.TrashEntryImpl;
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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	@Transactional(noRollbackFor = {TrashPermissionException.class})
	public void deleteEntries(long groupId) throws PortalException {
		boolean throwTrashPermissionException = false;

		List<TrashEntry> entries = trashEntryPersistence.findByGroupId(groupId);

		PermissionChecker permissionChecker = getPermissionChecker();

		for (TrashEntry entry : entries) {
			entry = trashEntryPersistence.fetchByPrimaryKey(entry.getEntryId());

			if (entry == null) {
				continue;
			}

			try {
				TrashHandler trashHandler =
					TrashHandlerRegistryUtil.getTrashHandler(
						entry.getClassName());

				if (!trashHandler.hasTrashPermission(
						permissionChecker, 0, entry.getClassPK(),
						ActionKeys.VIEW)) {

					continue;
				}

				deleteEntry(entry);
			}
			catch (TrashPermissionException tpe) {
				throwTrashPermissionException = true;
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (throwTrashPermissionException) {
			throw new TrashPermissionException(
				TrashPermissionException.EMPTY_TRASH);
		}
	}

	/**
	 * Deletes the trash entries with the primary keys.
	 *
	 * @param  entryIds the primary keys of the trash entries
	 * @throws PortalException if a trash entry with the primary key could not
	 *         be found or if the user did not have permission to delete any one
	 *         of the trash entries
	 */
	@Override
	@Transactional(noRollbackFor = {TrashPermissionException.class})
	public void deleteEntries(long[] entryIds) throws PortalException {
		boolean throwTrashPermissionException = false;

		for (long entryId : entryIds) {
			try {
				deleteEntry(entryId);
			}
			catch (TrashPermissionException tpe) {
				throwTrashPermissionException = true;
			}
		}

		if (throwTrashPermissionException) {
			throw new TrashPermissionException(
				TrashPermissionException.EMPTY_TRASH);
		}
	}

	/**
	 * Deletes the trash entry with the primary key.
	 *
	 * <p>
	 * This method throws a {@link TrashPermissionException} with type {@link
	 * TrashPermissionException#DELETE} if the user did not have permission to
	 * delete the trash entry.
	 * </p>
	 *
	 * @param  entryId the primary key of the trash entry
	 * @throws PortalException if a trash entry with the primary key could not
	 *         be found or if the user did not have permission to delete the
	 *         trash entry
	 */
	@Override
	public void deleteEntry(long entryId) throws PortalException {
		TrashEntry entry = trashEntryPersistence.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	/**
	 * Deletes the trash entry with the entity class name and class primary key.
	 *
	 * <p>
	 * This method throws a {@link TrashPermissionException} with type {@link
	 * TrashPermissionException#DELETE} if the user did not have permission to
	 * delete the trash entry.
	 * </p>
	 *
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @throws PortalException if a trash entry with the entity class name and
	 *         primary key could not be found or if the user did not have
	 *         permission to delete the entry
	 */
	@Override
	public void deleteEntry(String className, long classPK)
		throws PortalException {

		TrashEntry entry = trashEntryLocalService.fetchEntry(
			className, classPK);

		if (entry == null) {
			entry = new TrashEntryImpl();

			entry.setClassName(className);
			entry.setClassPK(classPK);
		}

		deleteEntry(entry);
	}

	/**
	 * Returns the trash entries with the matching group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @return the matching trash entries
	 * @throws PrincipalException if a principal exception occurred
	 */
	@Override
	public TrashEntryList getEntries(long groupId) throws PrincipalException {
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
	 */
	@Override
	public TrashEntryList getEntries(
			long groupId, int start, int end, OrderByComparator<TrashEntry> obc)
		throws PrincipalException {

		TrashEntryList trashEntriesList = new TrashEntryList();

		int entriesCount = trashEntryPersistence.countByGroupId(groupId);

		boolean approximate = entriesCount > PropsValues.TRASH_SEARCH_LIMIT;

		trashEntriesList.setApproximate(approximate);

		List<TrashEntry> entries = trashEntryPersistence.findByGroupId(
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

		int total = filteredEntries.size();

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS)) {
			start = 0;
			end = total;
		}

		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
			start, end, total);

		start = startAndEnd[0];
		end = startAndEnd[1];

		filteredEntries = filteredEntries.subList(start, end);

		trashEntriesList.setArray(TrashEntrySoap.toSoapModels(filteredEntries));
		trashEntriesList.setCount(total);

		return trashEntriesList;
	}

	/**
	 * Moves the trash entry with the entity class name and primary key,
	 * restoring it to a new location identified by the destination container
	 * model ID.
	 *
	 * <p>
	 * This method throws a {@link TrashPermissionException} if the user did not
	 * have the permission to perform one of the necessary operations. The
	 * exception is created with a type specific to the operation:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * {@link TrashPermissionException#MOVE} - if the user did not have
	 * permission to move the trash entry to the new
	 * destination
	 * </li>
	 * <li>
	 * {@link TrashPermissionException#RESTORE} - if the user did not have
	 * permission to restore the trash entry
	 * </li>
	 * </ul>
	 *
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @param  destinationContainerModelId the primary key of the new location
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>)
	 * @throws PortalException if a matching trash entry could not be found, if
	 *         the user did not have permission to move the trash entry to the
	 *         new location, if the user did not have permission to restore the
	 *         trash entry, if a duplicate trash entry exists at the new
	 *         location, or if a portal exception occurred
	 */
	@Override
	public void moveEntry(
			String className, long classPK, long destinationContainerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		long scopeGroupId = 0;

		if (serviceContext != null) {
			scopeGroupId = serviceContext.getScopeGroupId();
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		destinationContainerModelId =
			trashHandler.getDestinationContainerModelId(
				classPK, destinationContainerModelId);

		if (!trashHandler.hasTrashPermission(
				permissionChecker, scopeGroupId, destinationContainerModelId,
				TrashActionKeys.MOVE)) {

			throw new TrashPermissionException(TrashPermissionException.MOVE);
		}

		if (trashHandler.isInTrash(classPK) &&
			!trashHandler.hasTrashPermission(
				permissionChecker, 0, classPK, TrashActionKeys.RESTORE)) {

			throw new TrashPermissionException(
				TrashPermissionException.RESTORE);
		}

		TrashEntry trashEntry = trashHandler.getTrashEntry(classPK);

		if (trashEntry.isTrashEntry(className, classPK)) {
			trashHandler.checkRestorableEntry(
				trashEntry, destinationContainerModelId, StringPool.BLANK);
		}
		else {
			trashHandler.checkRestorableEntry(
				classPK, destinationContainerModelId, StringPool.BLANK);
		}

		trashHandler.moveTrashEntry(
			getUserId(), classPK, destinationContainerModelId, serviceContext);
	}

	@Override
	public TrashEntry restoreEntry(long entryId) throws PortalException {
		return restoreEntry(entryId, 0, null);
	}

	/**
	 * Restores the trash entry to its original location. In order to handle a
	 * duplicate trash entry already existing at the original location, either
	 * pass in the primary key of the existing trash entry's entity to overwrite
	 * or pass in a new name to give to the trash entry being restored.
	 *
	 * <p>
	 * This method throws a {@link TrashPermissionException} if the user did not
	 * have the permission to perform one of the necessary operations. The
	 * exception is created with a type specific to the operation:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * {@link TrashPermissionException#RESTORE} - if the user did not have
	 * permission to restore the trash entry
	 * </li>
	 * <li>
	 * {@link TrashPermissionException#RESTORE_OVERWRITE} - if the user did not
	 * have permission to delete the existing trash entry
	 * </li>
	 * <li>
	 * {@link TrashPermissionException#RESTORE_RENAME} - if the user did not
	 * have permission to rename the trash entry
	 * </li>
	 * </ul>
	 *
	 * @param  entryId the primary key of the trash entry to restore
	 * @param  overrideClassPK the primary key of the entity to overwrite
	 *         (optionally <code>0</code>)
	 * @param  name a new name to give to the trash entry being restored
	 *         (optionally <code>null</code>)
	 * @return the restored trash entry
	 * @throws PortalException if a matching trash entry could not be found, if
	 *         the user did not have permission to overwrite an existing trash
	 *         entry, to rename the trash entry being restored, or to restore
	 *         the trash entry in general
	 */
	@Override
	public TrashEntry restoreEntry(
			long entryId, long overrideClassPK, String name)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		TrashEntry entry = trashEntryPersistence.findByPrimaryKey(entryId);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			entry.getClassName());

		if (!trashHandler.hasTrashPermission(
				permissionChecker, 0, entry.getClassPK(),
				TrashActionKeys.RESTORE)) {

			throw new TrashPermissionException(
				TrashPermissionException.RESTORE);
		}

		if (overrideClassPK > 0) {
			if (!trashHandler.hasTrashPermission(
					permissionChecker, 0, overrideClassPK,
					TrashActionKeys.OVERWRITE)) {

				throw new TrashPermissionException(
					TrashPermissionException.RESTORE_OVERWRITE);
			}

			trashHandler.deleteTrashEntry(overrideClassPK);

			trashHandler.checkRestorableEntry(
				entry, TrashEntryConstants.DEFAULT_CONTAINER_ID, null);
		}
		else if (name != null) {
			if (!trashHandler.hasTrashPermission(
					permissionChecker, 0, entry.getClassPK(),
					TrashActionKeys.RENAME)) {

				throw new TrashPermissionException(
					TrashPermissionException.RESTORE_RENAME);
			}

			trashHandler.checkRestorableEntry(
				entry, TrashEntryConstants.DEFAULT_CONTAINER_ID, name);

			trashHandler.updateTitle(entry.getClassPK(), name);
		}

		trashHandler.restoreTrashEntry(getUserId(), entry.getClassPK());

		return entry;
	}

	@Override
	public TrashEntry restoreEntry(String className, long classPK)
		throws PortalException {

		return restoreEntry(className, classPK, 0, null);
	}

	@Override
	public TrashEntry restoreEntry(
			String className, long classPK, long overrideClassPK, String name)
		throws PortalException {

		TrashEntry trashEntry = trashEntryPersistence.fetchByC_C(
			classNameLocalService.getClassNameId(className), classPK);

		if (trashEntry != null) {
			return restoreEntry(trashEntry.getEntryId(), overrideClassPK, name);
		}

		return null;
	}

	protected void deleteEntry(TrashEntry entry) throws PortalException {
		PermissionChecker permissionChecker = getPermissionChecker();

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			entry.getClassName());

		if (!trashHandler.hasTrashPermission(
				permissionChecker, 0, entry.getClassPK(), ActionKeys.DELETE)) {

			throw new TrashPermissionException(TrashPermissionException.DELETE);
		}

		trashHandler.deleteTrashEntry(entry.getClassPK());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TrashEntryServiceImpl.class);

}