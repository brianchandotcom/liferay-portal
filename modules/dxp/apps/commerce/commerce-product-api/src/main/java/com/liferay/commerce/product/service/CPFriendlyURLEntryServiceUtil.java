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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPFriendlyURLEntry. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPFriendlyURLEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPFriendlyURLEntryService
 * @see com.liferay.commerce.product.service.base.CPFriendlyURLEntryServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPFriendlyURLEntryServiceImpl
 * @generated
 */
@ProviderType
public class CPFriendlyURLEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPFriendlyURLEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPFriendlyURLEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPFriendlyURLEntryService, CPFriendlyURLEntryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPFriendlyURLEntryService.class);

		ServiceTracker<CPFriendlyURLEntryService, CPFriendlyURLEntryService> serviceTracker =
			new ServiceTracker<CPFriendlyURLEntryService, CPFriendlyURLEntryService>(bundle.getBundleContext(),
				CPFriendlyURLEntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}