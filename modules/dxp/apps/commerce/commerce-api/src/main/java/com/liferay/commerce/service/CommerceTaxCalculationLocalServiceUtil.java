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
 * Provides the local service utility for CommerceTaxCalculation. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceTaxCalculationLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCalculationLocalService
 * @see com.liferay.commerce.service.base.CommerceTaxCalculationLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceTaxCalculationLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceTaxCalculationLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceTaxCalculationLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static java.util.List<com.liferay.commerce.model.CommerceTaxRate> getCommerceTaxRates(
		long siteGroupId, long userId, long commerceTaxCategoryId) {
		return getService()
				   .getCommerceTaxRates(siteGroupId, userId,
			commerceTaxCategoryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceTaxCalculationLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTaxCalculationLocalService, CommerceTaxCalculationLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceTaxCalculationLocalService.class);

		ServiceTracker<CommerceTaxCalculationLocalService, CommerceTaxCalculationLocalService> serviceTracker =
			new ServiceTracker<CommerceTaxCalculationLocalService, CommerceTaxCalculationLocalService>(bundle.getBundleContext(),
				CommerceTaxCalculationLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}