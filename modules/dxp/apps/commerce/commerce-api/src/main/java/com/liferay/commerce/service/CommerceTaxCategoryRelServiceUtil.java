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
 * Provides the remote service utility for CommerceTaxCategoryRel. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceTaxCategoryRelServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryRelService
 * @see com.liferay.commerce.service.base.CommerceTaxCategoryRelServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceTaxCategoryRelServiceImpl
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryRelServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceTaxCategoryRelServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteCommerceTaxCategoryRel(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceTaxCategoryRel(className, classPK);
	}

	public static com.liferay.commerce.model.CommerceTaxCategoryRel fetchCommerceTaxCategoryRel(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCommerceTaxCategoryRel(className, classPK);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommerceTaxCategoryRel updateCommerceTaxCategoryRel(
		long commerceTaxCategoryId, java.lang.String className, long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceTaxCategoryRel(commerceTaxCategoryId,
			className, classPK, serviceContext);
	}

	public static CommerceTaxCategoryRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTaxCategoryRelService, CommerceTaxCategoryRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceTaxCategoryRelService.class);

		ServiceTracker<CommerceTaxCategoryRelService, CommerceTaxCategoryRelService> serviceTracker =
			new ServiceTracker<CommerceTaxCategoryRelService, CommerceTaxCategoryRelService>(bundle.getBundleContext(),
				CommerceTaxCategoryRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}