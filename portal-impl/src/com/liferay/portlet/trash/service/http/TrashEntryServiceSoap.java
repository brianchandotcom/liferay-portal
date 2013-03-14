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

package com.liferay.portlet.trash.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.trash.service.TrashEntryServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.trash.service.TrashEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.trash.model.TrashEntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.trash.model.TrashEntry}, that is translated to a
 * {@link com.liferay.portlet.trash.model.TrashEntrySoap}. Methods that SOAP cannot
 * safely wire are skipped.
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
 * @author    Brian Wing Shun Chan
 * @see       TrashEntryServiceHttp
 * @see       com.liferay.portlet.trash.model.TrashEntrySoap
 * @see       com.liferay.portlet.trash.service.TrashEntryServiceUtil
 * @generated
 */
public class TrashEntryServiceSoap {
	/**
	* Deletes the trash entries with the matching group ID considering
	* permissions.
	*
	* @param groupId the primary key of the group
	* @throws PrincipalException if a principal exception occurred
	* @throws SystemException if a system exception occurred
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
	* @throws PortalException if the user didn't have permission to delete one
	or more entries
	* @throws SystemException if a system exception occurred
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
	* @param entryId the primary key of the trash entry
	* @throws PortalException if the user didn't have permission to delete the
	entry
	* @throws SystemException if a system exception occurred
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
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @throws PortalException if the user didn't have permission to delete the
	entry
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteEntry(java.lang.String className, long classPK)
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
	* @throws PrincipalException if a principal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntryList getEntries(
		long groupId) throws RemoteException {
		try {
			com.liferay.portlet.trash.model.TrashEntryList returnValue = TrashEntryServiceUtil.getEntries(groupId);

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
	* @throws PrincipalException if a system exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntryList getEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			com.liferay.portlet.trash.model.TrashEntryList returnValue = TrashEntryServiceUtil.getEntries(groupId,
					start, end, obc);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @param destinationContainerModelId the primary key of the new location
	* @param serviceContext the service context (optionally <code>null</code>)
	* @throws PortalException if the user didn't have permission to add the
	entry to its new location or to restore it from the trash in
	general
	* @throws SystemException if a system exception occurred
	*/
	public static void moveEntry(java.lang.String className, long classPK,
		long destinationContainerModelId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			TrashEntryServiceUtil.moveEntry(className, classPK,
				destinationContainerModelId, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.trash.model.TrashEntrySoap restoreEntry(
		long entryId) throws RemoteException {
		try {
			com.liferay.portlet.trash.model.TrashEntry returnValue = TrashEntryServiceUtil.restoreEntry(entryId);

			return com.liferay.portlet.trash.model.TrashEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
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
	* @param entryId the primary key of the trash entry
	* @param overrideClassPK the primary key of the item to overwrite
	* @param name the new name of the entry (optionally <code>null</code>)
	* @return the trash entry that was restored
	* @throws PortalException if the user didn't have permission to overwrite
	an existing item, to rename the entry or to restore the entry
	from the trash in general
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntrySoap restoreEntry(
		long entryId, long overrideClassPK, java.lang.String name)
		throws RemoteException {
		try {
			com.liferay.portlet.trash.model.TrashEntry returnValue = TrashEntryServiceUtil.restoreEntry(entryId,
					overrideClassPK, name);

			return com.liferay.portlet.trash.model.TrashEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(TrashEntryServiceSoap.class);
}