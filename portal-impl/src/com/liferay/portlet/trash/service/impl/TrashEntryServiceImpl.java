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
import com.liferay.portal.kernel.exception.TrashPermissionException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.trash.service.base.TrashEntryServiceBaseImpl;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.trash.kernel.model.TrashEntryList;

import java.util.List;

/**
 * The trash entry remote service is responsible for returning trash entries.
 * For more information on trash entries services and TrashEntry, see {@link
 * TrashEntryLocalServiceImpl}.
 *
 * @author Julio Camarero
 * @author Zsolt Berentey
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.trash.service.impl.TrashEntryServiceImpl}
 */
@Deprecated
public class TrashEntryServiceImpl extends TrashEntryServiceBaseImpl {

	/**
	 * Deletes the trash entries with the matching group ID considering
	 * permissions.
	 *
	 * @param groupId the primary key of the group
	 */
	@Override
	@Transactional(noRollbackFor = {TrashPermissionException.class})
	public void deleteEntries(long groupId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryServiceImpl");
	}

	/**
	 * Deletes the trash entries with the primary keys.
	 *
	 * @param entryIds the primary keys of the trash entries
	 */
	@Override
	@Transactional(noRollbackFor = {TrashPermissionException.class})
	public void deleteEntries(long[] entryIds) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryServiceImpl");
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
	 * @param entryId the primary key of the trash entry
	 */
	@Override
	public void deleteEntry(long entryId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryServiceImpl");
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
	 * @param className the class name of the entity
	 * @param classPK the primary key of the entity
	 */
	@Override
	public void deleteEntry(String className, long classPK)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryServiceImpl");
	}

	/**
	 * Returns the trash entries with the matching group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @return the matching trash entries
	 */
	@Override
	public TrashEntryList getEntries(long groupId) throws PrincipalException {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryServiceImpl");
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
	public TrashEntryList getEntries(
			long groupId, int start, int end, OrderByComparator<TrashEntry> obc)
		throws PrincipalException {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryServiceImpl");
	}

	@Override
	public List<TrashEntry> getEntries(long groupId, String className)
		throws PrincipalException {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryServiceImpl");
	}

	/**
	 * Returns a range of all the trash entries matching the group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @param  className the class name of the entity
	 * @param  start the lower bound of the range of trash entries to return
	 * @param  end the upper bound of the range of trash entries to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the trash entries (optionally
	 *         <code>null</code>)
	 * @return the range of matching trash entries ordered by comparator
	 *         <code>obc</code>
	 */
	@Override
	public TrashEntryList getEntries(
			long groupId, String className, int start, int end,
			OrderByComparator<TrashEntry> obc)
		throws PrincipalException {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryServiceImpl");
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
	 * @param className the class name of the entity
	 * @param classPK the primary key of the entity
	 * @param destinationContainerModelId the primary key of the new location
	 * @param serviceContext the service context to be applied (optionally
	 *        <code>null</code>)
	 */
	@Override
	public void moveEntry(
			String className, long classPK, long destinationContainerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryServiceImpl");
	}

	@Override
	public TrashEntry restoreEntry(long entryId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryServiceImpl");
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
	 */
	@Override
	public TrashEntry restoreEntry(
			long entryId, long overrideClassPK, String name)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryServiceImpl");
	}

	@Override
	public TrashEntry restoreEntry(String className, long classPK)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryServiceImpl");
	}

	@Override
	public TrashEntry restoreEntry(
			String className, long classPK, long overrideClassPK, String name)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryServiceImpl");
	}

	protected void deleteEntry(TrashEntry entry) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryServiceImpl");
	}

	protected List<TrashEntry> filterEntries(List<TrashEntry> entries)
		throws PrincipalException {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashEntryServiceImpl");
	}

}