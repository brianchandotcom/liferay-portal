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
	public static com.liferay.sharing.model.SharingEntry addSharingEntry(
		long toUserId, long classNameId, long classPK, long groupId,
		java.util.Collection<com.liferay.sharing.constants.SharingEntryActionKey> sharingEntryActionKeys,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSharingEntry(toUserId, classNameId, classPK, groupId,
			sharingEntryActionKeys, serviceContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.sharing.model.SharingEntry updateSharingEntry(
		long sharingEntryId,
		java.util.Collection<com.liferay.sharing.constants.SharingEntryActionKey> sharingEntryActionKeys)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateSharingEntry(sharingEntryId, sharingEntryActionKeys);
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