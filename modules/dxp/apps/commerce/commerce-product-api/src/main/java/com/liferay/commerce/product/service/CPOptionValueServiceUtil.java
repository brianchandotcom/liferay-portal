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
 * Provides the remote service utility for CPOptionValue. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPOptionValueServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPOptionValueService
 * @see com.liferay.commerce.product.service.base.CPOptionValueServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPOptionValueServiceImpl
 * @generated
 */
@ProviderType
public class CPOptionValueServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPOptionValueServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPOptionValue addCPOptionValue(
		long cpOptionId, java.util.Map<java.util.Locale, String> titleMap,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPOptionValue(cpOptionId, titleMap, priority, key,
			serviceContext);
	}

	public static void deleteCPOptionValue(long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPOptionValue(cpOptionValueId);
	}

	public static com.liferay.commerce.product.model.CPOptionValue fetchCPOptionValue(
		long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPOptionValue(cpOptionValueId);
	}

	public static com.liferay.commerce.product.model.CPOptionValue getCPOptionValue(
		long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPOptionValue(cpOptionValueId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.product.model.CPOptionValue updateCPOptionValue(
		long cpOptionValueId, java.util.Map<java.util.Locale, String> titleMap,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPOptionValue(cpOptionValueId, titleMap, priority,
			key, serviceContext);
	}

	public static CPOptionValueService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPOptionValueService, CPOptionValueService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPOptionValueService.class);

		ServiceTracker<CPOptionValueService, CPOptionValueService> serviceTracker =
			new ServiceTracker<CPOptionValueService, CPOptionValueService>(bundle.getBundleContext(),
				CPOptionValueService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}