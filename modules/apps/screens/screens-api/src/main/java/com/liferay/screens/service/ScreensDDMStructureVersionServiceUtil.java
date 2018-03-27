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

package com.liferay.screens.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for ScreensDDMStructureVersion. This utility wraps
 * {@link com.liferay.screens.service.impl.ScreensDDMStructureVersionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author José Manuel Navarro
 * @see ScreensDDMStructureVersionService
 * @see com.liferay.screens.service.base.ScreensDDMStructureVersionServiceBaseImpl
 * @see com.liferay.screens.service.impl.ScreensDDMStructureVersionServiceImpl
 * @generated
 */
@ProviderType
public class ScreensDDMStructureVersionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.screens.service.impl.ScreensDDMStructureVersionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.json.JSONObject getDDMStructureVersion(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDMStructureVersion(structureId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ScreensDDMStructureVersionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ScreensDDMStructureVersionService, ScreensDDMStructureVersionService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ScreensDDMStructureVersionService.class);

		ServiceTracker<ScreensDDMStructureVersionService, ScreensDDMStructureVersionService> serviceTracker =
			new ServiceTracker<ScreensDDMStructureVersionService, ScreensDDMStructureVersionService>(bundle.getBundleContext(),
				ScreensDDMStructureVersionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}