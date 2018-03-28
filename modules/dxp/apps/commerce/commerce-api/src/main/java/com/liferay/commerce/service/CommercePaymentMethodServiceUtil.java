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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommercePaymentMethod. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommercePaymentMethodServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePaymentMethodService
 * @see com.liferay.commerce.service.base.CommercePaymentMethodServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommercePaymentMethodServiceImpl
 * @generated
 */
@ProviderType
public class CommercePaymentMethodServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommercePaymentMethodServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommercePaymentMethod addCommercePaymentMethod(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.io.File imageFile, java.lang.String engineKey,
		java.util.Map<java.lang.String, java.lang.String> engineParameterMap,
		double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommercePaymentMethod(nameMap, descriptionMap,
			imageFile, engineKey, engineParameterMap, priority, active,
			serviceContext);
	}

	public static com.liferay.commerce.model.CommercePaymentMethod createCommercePaymentMethod(
		long commercePaymentMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().createCommercePaymentMethod(commercePaymentMethodId);
	}

	public static void deleteCommercePaymentMethod(long commercePaymentMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommercePaymentMethod(commercePaymentMethodId);
	}

	public static com.liferay.commerce.model.CommercePaymentMethod getCommercePaymentMethod(
		long commercePaymentMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommercePaymentMethod(commercePaymentMethodId);
	}

	public static java.util.List<com.liferay.commerce.model.CommercePaymentMethod> getCommercePaymentMethods(
		long groupId) {
		return getService().getCommercePaymentMethods(groupId);
	}

	public static java.util.List<com.liferay.commerce.model.CommercePaymentMethod> getCommercePaymentMethods(
		long groupId, boolean active) {
		return getService().getCommercePaymentMethods(groupId, active);
	}

	public static java.util.List<com.liferay.commerce.model.CommercePaymentMethod> getCommercePaymentMethods(
		long groupId, long commerceCountryId, boolean active) {
		return getService()
				   .getCommercePaymentMethods(groupId, commerceCountryId, active);
	}

	public static int getCommercePaymentMethodsCount(long groupId,
		boolean active) {
		return getService().getCommercePaymentMethodsCount(groupId, active);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommercePaymentMethod setActive(
		long commercePaymentMethodId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().setActive(commercePaymentMethodId, active);
	}

	public static com.liferay.commerce.model.CommercePaymentMethod updateCommercePaymentMethod(
		long commercePaymentMethodId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.io.File imageFile,
		java.util.Map<java.lang.String, java.lang.String> engineParameterMap,
		double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommercePaymentMethod(commercePaymentMethodId,
			nameMap, descriptionMap, imageFile, engineParameterMap, priority,
			active, serviceContext);
	}

	public static CommercePaymentMethodService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePaymentMethodService, CommercePaymentMethodService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommercePaymentMethodService.class);

		ServiceTracker<CommercePaymentMethodService, CommercePaymentMethodService> serviceTracker =
			new ServiceTracker<CommercePaymentMethodService, CommercePaymentMethodService>(bundle.getBundleContext(),
				CommercePaymentMethodService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}