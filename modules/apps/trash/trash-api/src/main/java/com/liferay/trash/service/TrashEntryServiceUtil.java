/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.trash.model.TrashEntry;

import java.util.List;

/**
 * Provides the remote service utility for TrashEntry. This utility wraps
 * <code>com.liferay.trash.service.impl.TrashEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntryService
 * @generated
 */
public class TrashEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.trash.service.impl.TrashEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Deletes the trash entries with the matching group ID considering
	 * permissions.
	 *
	 * @param groupId the primary key of the group
	 */
	public static void deleteEntries(long groupId) throws PortalException {
		getService().deleteEntries(groupId);
	}

	/**
	 * Deletes the trash entries with the primary keys.
	 *
	 * @param entryIds the primary keys of the trash entries
	 */
	public static void deleteEntries(long[] entryIds) throws PortalException {
		getService().deleteEntries(entryIds);
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
	public static void deleteEntry(long entryId) throws PortalException {
		getService().deleteEntry(entryId);
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
	public static void deleteEntry(String className, long classPK)
		throws PortalException {

		getService().deleteEntry(className, classPK);
	}

	/**
	 * Returns the trash entries with the matching group ID.
	 *
	 * @param groupId the primary key of the group
	 * @return the matching trash entries
	 */
	public static com.liferay.trash.model.TrashEntryList getEntries(
			long groupId)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return getService().getEntries(groupId);
	}

	/**
	 * Returns a range of all the trash entries matching the group ID.
	 *
	 * @param groupId the primary key of the group
	 * @param start the lower bound of the range of trash entries to return
	 * @param end the upper bound of the range of trash entries to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the trash entries
	 (optionally <code>null</code>)
	 * @return the range of matching trash entries ordered by comparator
	 <code>orderByComparator</code>
	 */
	public static com.liferay.trash.model.TrashEntryList getEntries(
			long groupId, int start, int end,
			OrderByComparator<TrashEntry> orderByComparator)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return getService().getEntries(groupId, start, end, orderByComparator);
	}

	public static List<TrashEntry> getEntries(long groupId, String className)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return getService().getEntries(groupId, className);
	}

	/**
	 * Returns a range of all the trash entries matching the group ID.
	 *
	 * @param groupId the primary key of the group
	 * @param className the class name of the entity
	 * @param start the lower bound of the range of trash entries to return
	 * @param end the upper bound of the range of trash entries to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the trash entries
	 (optionally <code>null</code>)
	 * @return the range of matching trash entries ordered by comparator
	 <code>orderByComparator</code>
	 */
	public static com.liferay.trash.model.TrashEntryList getEntries(
			long groupId, String className, int start, int end,
			OrderByComparator<TrashEntry> orderByComparator)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return getService().getEntries(
			groupId, className, start, end, orderByComparator);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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
	 <code>null</code>)
	 */
	public static void moveEntry(
			String className, long classPK, long destinationContainerModelId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().moveEntry(
			className, classPK, destinationContainerModelId, serviceContext);
	}

	public static TrashEntry restoreEntry(long entryId) throws PortalException {
		return getService().restoreEntry(entryId);
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
	 * @param entryId the primary key of the trash entry to restore
	 * @param overrideClassPK the primary key of the entity to overwrite
	 (optionally <code>0</code>)
	 * @param name a new name to give to the trash entry being restored
	 (optionally <code>null</code>)
	 * @return the restored trash entry
	 */
	public static TrashEntry restoreEntry(
			long entryId, long overrideClassPK, String name)
		throws PortalException {

		return getService().restoreEntry(entryId, overrideClassPK, name);
	}

	public static TrashEntry restoreEntry(String className, long classPK)
		throws PortalException {

		return getService().restoreEntry(className, classPK);
	}

	public static TrashEntry restoreEntry(
			String className, long classPK, long overrideClassPK, String name)
		throws PortalException {

		return getService().restoreEntry(
			className, classPK, overrideClassPK, name);
	}

	public static TrashEntryService getService() {
		return _service;
	}

	public static void setService(TrashEntryService service) {
		_service = service;
	}

	private static volatile TrashEntryService _service;

}