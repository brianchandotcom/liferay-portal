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

package com.liferay.sharing.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for SharingEntry. This utility wraps
 * {@link com.liferay.sharing.service.impl.SharingEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntryService
 * @see com.liferay.sharing.service.base.SharingEntryServiceBaseImpl
 * @see com.liferay.sharing.service.impl.SharingEntryServiceImpl
 * @generated
 */
@ProviderType
public class SharingEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.sharing.service.impl.SharingEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds a sharing entry in the database if it does not exist or it updates
	* it if it exists.
	*
	* @param toUserId the user id whose resource was shared
	* @param classNameId the class name ID of the resource being shared
	* @param classPK the primary key of the resource being shared
	* @param groupId the primary key of the group containing the resource
	being shared
	* @param shareable whether the to user id can share the resource as well
	* @param sharingEntryActions the sharing entry actions
	* @param expirationDate the date when the sharing entry expires
	* @return the sharing entry
	* @param serviceContext the service context to be applied
	* @throws PortalException if the user does not have permission to share the
	resource or sharing entry actions are invalid (it is empty, it
	doesn't contain {@link SharingEntryAction#VIEW,} or it
	contains a <code>null</code> value) or from user id and to user
	id are the same or the expiration date is a value in the past.
	*/
	public static com.liferay.sharing.model.SharingEntry addOrUpdateSharingEntry(
		long toUserId, long classNameId, long classPK, long groupId,
		boolean shareable,
		java.util.Collection<com.liferay.sharing.security.permission.SharingEntryAction> sharingEntryActions,
		java.util.Date expirationDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addOrUpdateSharingEntry(toUserId, classNameId, classPK,
			groupId, shareable, sharingEntryActions, expirationDate,
			serviceContext);
	}

	/**
	* Adds a sharing entry in the database.
	*
	* @param toUserId the user id whose resource was shared
	* @param classNameId the class name ID of the resource being shared
	* @param classPK the primary key of the resource being shared
	* @param groupId the primary key of the group containing the resource
	being shared
	* @param shareable whether the to user id can share the resource as well
	* @param sharingEntryActions the sharing entry actions
	* @param expirationDate the date when the sharing entry expires
	* @return the sharing entry
	* @param serviceContext the service context to be applied
	* @throws PortalException if the user does not have permission to share the
	resource or there is already a sharing entry for the same from
	user id, to user id and resource or the sharing entry actions are
	invalid (it is empty, it doesn't contain
	{@link SharingEntryAction#VIEW,} or it contains a
	<code>null</code> value) or from user id and to user id are the
	same or the expiration date is a value in the past.
	*/
	public static com.liferay.sharing.model.SharingEntry addSharingEntry(
		long toUserId, long classNameId, long classPK, long groupId,
		boolean shareable,
		java.util.Collection<com.liferay.sharing.security.permission.SharingEntryAction> sharingEntryActions,
		java.util.Date expirationDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSharingEntry(toUserId, classNameId, classPK, groupId,
			shareable, sharingEntryActions, expirationDate, serviceContext);
	}

	public static com.liferay.sharing.model.SharingEntry deleteSharingEntry(
		long sharingEntryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSharingEntry(sharingEntryId, serviceContext);
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
	* Updates a sharing entry in the database.
	*
	* @param sharingEntryId the primary key of the sharing entry
	* @param sharingEntryActions the sharing entry actions
	* @param shareable whether the to user id can share the resource as well
	* @param expirationDate the date when the sharing entry expires
	* @return the sharing entry
	* @param serviceContext the service context to be applied
	* @throws PortalException if the sharing entry does not exist or sharing
	entry actions are invalid (it is empty, it doesn't contain
	{@link SharingEntryAction#VIEW,} or it contains a
	<code>null</code> value) or the expiration date is a value in the
	past.
	*/
	public static com.liferay.sharing.model.SharingEntry updateSharingEntry(
		long sharingEntryId,
		java.util.Collection<com.liferay.sharing.security.permission.SharingEntryAction> sharingEntryActions,
		boolean shareable, java.util.Date expirationDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateSharingEntry(sharingEntryId, sharingEntryActions,
			shareable, expirationDate, serviceContext);
	}

	public static SharingEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SharingEntryService, SharingEntryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SharingEntryService.class);

		ServiceTracker<SharingEntryService, SharingEntryService> serviceTracker = new ServiceTracker<SharingEntryService, SharingEntryService>(bundle.getBundleContext(),
				SharingEntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}