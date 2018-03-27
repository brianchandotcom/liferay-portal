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

package com.liferay.sync.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for SyncDevice. This utility wraps
 * {@link com.liferay.sync.service.impl.SyncDeviceServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SyncDeviceService
 * @see com.liferay.sync.service.base.SyncDeviceServiceBaseImpl
 * @see com.liferay.sync.service.impl.SyncDeviceServiceImpl
 * @generated
 */
@ProviderType
public class SyncDeviceServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.sync.service.impl.SyncDeviceServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.sync.model.SyncDevice registerSyncDevice(
		java.lang.String type, long buildNumber, int featureSet,
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .registerSyncDevice(type, buildNumber, featureSet, uuid);
	}

	public static void unregisterSyncDevice(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unregisterSyncDevice(uuid);
	}

	public static SyncDeviceService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SyncDeviceService, SyncDeviceService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SyncDeviceService.class);

		ServiceTracker<SyncDeviceService, SyncDeviceService> serviceTracker = new ServiceTracker<SyncDeviceService, SyncDeviceService>(bundle.getBundleContext(),
				SyncDeviceService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}