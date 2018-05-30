/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPDisplayLayout. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPDisplayLayoutServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPDisplayLayoutService
 * @see com.liferay.commerce.product.service.base.CPDisplayLayoutServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDisplayLayoutServiceImpl
 * @generated
 */
@ProviderType
public class CPDisplayLayoutServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDisplayLayoutServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPDisplayLayoutService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDisplayLayoutService, CPDisplayLayoutService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDisplayLayoutService.class);

		ServiceTracker<CPDisplayLayoutService, CPDisplayLayoutService> serviceTracker =
			new ServiceTracker<CPDisplayLayoutService, CPDisplayLayoutService>(bundle.getBundleContext(),
				CPDisplayLayoutService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}