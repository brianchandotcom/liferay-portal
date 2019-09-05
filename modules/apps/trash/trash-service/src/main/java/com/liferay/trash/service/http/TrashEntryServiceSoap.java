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

package com.liferay.trash.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.trash.service.TrashEntryServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>TrashEntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.trash.model.TrashEntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.trash.model.TrashEntry</code>, that is translated to a
 * <code>com.liferay.trash.model.TrashEntrySoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntryServiceHttp
 * @generated
 */
public class TrashEntryServiceSoap {

	/**
	 * Deletes the trash entries with the matching group ID considering
	 * permissions.
	 *
	 * @param groupId the primary key of the group
	 */
	public static void deleteEntries(long groupId) throws RemoteException {
		try {
			TrashEntryServiceUtil.deleteEntries(groupId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Deletes the trash entries with the primary keys.
	 *
	 * @param entryIds the primary keys of the trash entries
	 */
	public static void deleteEntries(long[] entryIds) throws RemoteException {
		try {
			TrashEntryServiceUtil.deleteEntries(entryIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
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
	 * @param entryId the primary key of the trash entry
	 */
	public static void deleteEntry(long entryId) throws RemoteException {
		try {
			TrashEntryServiceUtil.deleteEntry(entryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
		throws RemoteException {

		try {
			TrashEntryServiceUtil.deleteEntry(className, classPK);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the trash entries with the matching group ID.
	 *
	 * @param groupId the primary key of the group
	 * @return the matching trash entries
	 */
	public static com.liferay.trash.model.TrashEntryList getEntries(
			long groupId)
		throws RemoteException {

		try {
			com.liferay.trash.model.TrashEntryList returnValue =
				TrashEntryServiceUtil.getEntries(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns a range of all the trash entries matching the group ID.
	 *
	 * @param groupId the primary key of the group
	 * @param start the lower bound of the range of trash entries to return
	 * @param end the upper bound of the range of trash entries to return (not
	 inclusive)
	 * @param obc the comparator to order the trash entries (optionally
	 <code>null</code>)
	 * @return the range of matching trash entries ordered by comparator
	 <code>obc</code>
	 */
	public static com.liferay.trash.model.TrashEntryList getEntries(
			long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.trash.model.TrashEntry> obc)
		throws RemoteException {

		try {
			com.liferay.trash.model.TrashEntryList returnValue =
				TrashEntryServiceUtil.getEntries(groupId, start, end, obc);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.trash.model.TrashEntrySoap[] getEntries(
			long groupId, String className)
		throws RemoteException {

		try {
			java.util.List<com.liferay.trash.model.TrashEntry> returnValue =
				TrashEntryServiceUtil.getEntries(groupId, className);

			return com.liferay.trash.model.TrashEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns a range of all the trash entries matching the group ID.
	 *
	 * @param groupId the primary key of the group
	 * @param className the class name of the entity
	 * @param start the lower bound of the range of trash entries to return
	 * @param end the upper bound of the range of trash entries to return (not
	 inclusive)
	 * @param obc the comparator to order the trash entries (optionally
	 <code>null</code>)
	 * @return the range of matching trash entries ordered by comparator
	 <code>obc</code>
	 */
	public static com.liferay.trash.model.TrashEntryList getEntries(
			long groupId, String className, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.trash.model.TrashEntry> obc)
		throws RemoteException {

		try {
			com.liferay.trash.model.TrashEntryList returnValue =
				TrashEntryServiceUtil.getEntries(
					groupId, className, start, end, obc);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
		throws RemoteException {

		try {
			TrashEntryServiceUtil.moveEntry(
				className, classPK, destinationContainerModelId,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.trash.model.TrashEntrySoap restoreEntry(
			long entryId)
		throws RemoteException {

		try {
			com.liferay.trash.model.TrashEntry returnValue =
				TrashEntryServiceUtil.restoreEntry(entryId);

			return com.liferay.trash.model.TrashEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static com.liferay.trash.model.TrashEntrySoap restoreEntry(
			long entryId, long overrideClassPK, String name)
		throws RemoteException {

		try {
			com.liferay.trash.model.TrashEntry returnValue =
				TrashEntryServiceUtil.restoreEntry(
					entryId, overrideClassPK, name);

			return com.liferay.trash.model.TrashEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.trash.model.TrashEntrySoap restoreEntry(
			String className, long classPK)
		throws RemoteException {

		try {
			com.liferay.trash.model.TrashEntry returnValue =
				TrashEntryServiceUtil.restoreEntry(className, classPK);

			return com.liferay.trash.model.TrashEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.trash.model.TrashEntrySoap restoreEntry(
			String className, long classPK, long overrideClassPK, String name)
		throws RemoteException {

		try {
			com.liferay.trash.model.TrashEntry returnValue =
				TrashEntryServiceUtil.restoreEntry(
					className, classPK, overrideClassPK, name);

			return com.liferay.trash.model.TrashEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		TrashEntryServiceSoap.class);

}