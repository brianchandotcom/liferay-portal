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
 * Provides the remote service utility for CommerceTaxMethod. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceTaxMethodServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxMethodService
 * @see com.liferay.commerce.service.base.CommerceTaxMethodServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceTaxMethodServiceImpl
 * @generated
 */
@ProviderType
public class CommerceTaxMethodServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceTaxMethodServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceTaxMethod addCommerceTaxMethod(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String engineKey, boolean percentage, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceTaxMethod(nameMap, descriptionMap, engineKey,
			percentage, active, serviceContext);
	}

	public static com.liferay.commerce.model.CommerceTaxMethod createCommerceTaxMethod(
		long commerceTaxMethodId) {
		return getService().createCommerceTaxMethod(commerceTaxMethodId);
	}

	public static void deleteCommerceTaxMethod(long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceTaxMethod(commerceTaxMethodId);
	}

	public static com.liferay.commerce.model.CommerceTaxMethod fetchCommerceTaxMethod(
		long groupId, java.lang.String engineKey) {
		return getService().fetchCommerceTaxMethod(groupId, engineKey);
	}

	public static com.liferay.commerce.model.CommerceTaxMethod getCommerceTaxMethod(
		long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceTaxMethod(commerceTaxMethodId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceTaxMethod> getCommerceTaxMethods(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceTaxMethods(groupId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceTaxMethod> getCommerceTaxMethods(
		long groupId, boolean active) {
		return getService().getCommerceTaxMethods(groupId, active);
	}

	public static int getCommerceTaxMethodsCount(long groupId, boolean active) {
		return getService().getCommerceTaxMethodsCount(groupId, active);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommerceTaxMethod setActive(
		long commerceTaxMethodId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().setActive(commerceTaxMethodId, active);
	}

	public static com.liferay.commerce.model.CommerceTaxMethod updateCommerceTaxMethod(
		long commerceTaxMethodId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		boolean percentage, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceTaxMethod(commerceTaxMethodId, nameMap,
			descriptionMap, percentage, active);
	}

	public static CommerceTaxMethodService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTaxMethodService, CommerceTaxMethodService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceTaxMethodService.class);

		ServiceTracker<CommerceTaxMethodService, CommerceTaxMethodService> serviceTracker =
			new ServiceTracker<CommerceTaxMethodService, CommerceTaxMethodService>(bundle.getBundleContext(),
				CommerceTaxMethodService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}