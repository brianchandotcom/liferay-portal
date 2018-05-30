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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceShippingMethod. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceShippingMethodServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingMethodService
 * @see com.liferay.commerce.service.base.CommerceShippingMethodServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceShippingMethodServiceImpl
 * @generated
 */
@ProviderType
public class CommerceShippingMethodServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceShippingMethodServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceShippingMethod addCommerceShippingMethod(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.io.File imageFile, String engineKey, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceShippingMethod(nameMap, descriptionMap,
			imageFile, engineKey, priority, active, serviceContext);
	}

	public static com.liferay.commerce.model.CommerceShippingMethod createCommerceShippingMethod(
		long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .createCommerceShippingMethod(commerceShippingMethodId);
	}

	public static void deleteCommerceShippingMethod(
		long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceShippingMethod(commerceShippingMethodId);
	}

	public static com.liferay.commerce.model.CommerceShippingMethod fetchCommerceShippingMethod(
		long groupId, String engineKey) {
		return getService().fetchCommerceShippingMethod(groupId, engineKey);
	}

	public static com.liferay.commerce.model.CommerceShippingMethod getCommerceShippingMethod(
		long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceShippingMethod(commerceShippingMethodId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceShippingMethod> getCommerceShippingMethods(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceShippingMethods(groupId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceShippingMethod> getCommerceShippingMethods(
		long groupId, boolean active) {
		return getService().getCommerceShippingMethods(groupId, active);
	}

	public static int getCommerceShippingMethodsCount(long groupId,
		boolean active) {
		return getService().getCommerceShippingMethodsCount(groupId, active);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommerceShippingMethod setActive(
		long commerceShippingMethodId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().setActive(commerceShippingMethodId, active);
	}

	public static com.liferay.commerce.model.CommerceShippingMethod updateCommerceShippingMethod(
		long commerceShippingMethodId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.io.File imageFile, double priority, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceShippingMethod(commerceShippingMethodId,
			nameMap, descriptionMap, imageFile, priority, active);
	}

	public static CommerceShippingMethodService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceShippingMethodService, CommerceShippingMethodService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceShippingMethodService.class);

		ServiceTracker<CommerceShippingMethodService, CommerceShippingMethodService> serviceTracker =
			new ServiceTracker<CommerceShippingMethodService, CommerceShippingMethodService>(bundle.getBundleContext(),
				CommerceShippingMethodService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}