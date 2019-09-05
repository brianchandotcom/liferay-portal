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

package com.liferay.sharing.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.sharing.service.SharingEntryServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>SharingEntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.sharing.model.SharingEntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.sharing.model.SharingEntry</code>, that is translated to a
 * <code>com.liferay.sharing.model.SharingEntrySoap</code>. Methods that SOAP
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
 * @see SharingEntryServiceHttp
 * @generated
 */
public class SharingEntryServiceSoap {

	/**
	 * Adds a new sharing entry in the database or updates an existing one.
	 *
	 * @param toUserId the ID of the user the resource is shared with
	 * @param classNameId the resource's class name ID
	 * @param classPK the primary key of the resource
	 * @param groupId the primary key of the resource's group
	 * @param shareable whether the user specified by {@code toUserId} can
	 share the resource
	 * @param sharingEntryActions the sharing entry actions
	 * @param expirationDate the date when the sharing entry expires
	 * @param serviceContext the service context
	 * @return the sharing entry
	 * @throws PortalException if the user does not have permission to share the
	 resource, if the sharing entry actions are invalid (e.g., empty
	 don't contain {@code SharingEntryAction#VIEW}, or contain a
	 {@code null} value), if the to/from user IDs are the same, or if
	 the expiration date is a past value
	 */
	public static com.liferay.sharing.model.SharingEntrySoap
			addOrUpdateSharingEntry(
				long toUserId, long classNameId, long classPK, long groupId,
				boolean shareable,
				java.util.Collection
					<com.liferay.sharing.security.permission.SharingEntryAction>
						sharingEntryActions,
				java.util.Date expirationDate,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.sharing.model.SharingEntry returnValue =
				SharingEntryServiceUtil.addOrUpdateSharingEntry(
					toUserId, classNameId, classPK, groupId, shareable,
					sharingEntryActions, expirationDate, serviceContext);

			return com.liferay.sharing.model.SharingEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Adds a new sharing entry in the database.
	 *
	 * @param toUserId the ID of the user the resource is shared with
	 * @param classNameId the resource's class name ID
	 * @param classPK the primary key of the resource
	 * @param groupId the primary key of the resource's group
	 * @param shareable whether the user specified by {@code toUserId} can
	 share the resource
	 * @param sharingEntryActions the sharing entry actions
	 * @param expirationDate the date when the sharing entry expires
	 * @param serviceContext the service context
	 * @return the sharing entry
	 * @throws PortalException if the user does not have permission to share the
	 resource, if a sharing entry already exists for the to/from user
	 IDs, if the sharing entry actions are invalid (e.g., empty, do
	 not contain {@code SharingEntryAction#VIEW}, or contain a {@code
	 null} value), if the to/from user IDs are the same, or if the
	 expiration date is a past value
	 */
	public static com.liferay.sharing.model.SharingEntrySoap addSharingEntry(
			long toUserId, long classNameId, long classPK, long groupId,
			boolean shareable,
			java.util.Collection
				<com.liferay.sharing.security.permission.SharingEntryAction>
					sharingEntryActions,
			java.util.Date expirationDate,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.sharing.model.SharingEntry returnValue =
				SharingEntryServiceUtil.addSharingEntry(
					toUserId, classNameId, classPK, groupId, shareable,
					sharingEntryActions, expirationDate, serviceContext);

			return com.liferay.sharing.model.SharingEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.sharing.model.SharingEntrySoap deleteSharingEntry(
			long sharingEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.sharing.model.SharingEntry returnValue =
				SharingEntryServiceUtil.deleteSharingEntry(
					sharingEntryId, serviceContext);

			return com.liferay.sharing.model.SharingEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Updates the sharing entry in the database.
	 *
	 * @param sharingEntryId the primary key of the sharing entry
	 * @param sharingEntryActions the sharing entry actions
	 * @param shareable whether the user the resource is shared with can also
	 share it
	 * @param expirationDate the date when the sharing entry expires
	 * @param serviceContext the service context
	 * @return the sharing entry
	 * @throws PortalException if the sharing entry does not exist, if the
	 sharing entry actions are invalid (e.g., empty, don't contain
	 {@code SharingEntryAction#VIEW}, or contain a {@code null}
	 value), or if the expiration date is a past value
	 */
	public static com.liferay.sharing.model.SharingEntrySoap updateSharingEntry(
			long sharingEntryId,
			java.util.Collection
				<com.liferay.sharing.security.permission.SharingEntryAction>
					sharingEntryActions,
			boolean shareable, java.util.Date expirationDate,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.sharing.model.SharingEntry returnValue =
				SharingEntryServiceUtil.updateSharingEntry(
					sharingEntryId, sharingEntryActions, shareable,
					expirationDate, serviceContext);

			return com.liferay.sharing.model.SharingEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SharingEntryServiceSoap.class);

}