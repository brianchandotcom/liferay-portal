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

package com.liferay.commerce.tax.engine.fixed.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceTaxFixedRate. This utility wraps
 * {@link com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateService
 * @see com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateServiceBaseImpl
 * @see com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateServiceImpl
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate addCommerceTaxFixedRate(
		long commerceTaxMethodId, long cpTaxCategoryId, double rate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceTaxFixedRate(commerceTaxMethodId,
			cpTaxCategoryId, rate, serviceContext);
	}

	public static void deleteCommerceTaxFixedRate(long commerceTaxFixedRateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceTaxFixedRate(commerceTaxFixedRateId);
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate fetchCommerceTaxFixedRate(
		long commerceTaxFixedRateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCommerceTaxFixedRate(commerceTaxFixedRateId);
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate fetchCommerceTaxFixedRate(
		long cpTaxCategoryId, long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchCommerceTaxFixedRate(cpTaxCategoryId,
			commerceTaxMethodId);
	}

	public static java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate> getCommerceTaxFixedRates(
		long groupId, long commerceTaxMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceTaxFixedRates(groupId, commerceTaxMethodId,
			start, end, orderByComparator);
	}

	public static int getCommerceTaxFixedRatesCount(long groupId,
		long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceTaxFixedRatesCount(groupId, commerceTaxMethodId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate updateCommerceTaxFixedRate(
		long commerceTaxFixedRateId, double rate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceTaxFixedRate(commerceTaxFixedRateId, rate);
	}

	public static CommerceTaxFixedRateService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTaxFixedRateService, CommerceTaxFixedRateService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceTaxFixedRateService.class);

		ServiceTracker<CommerceTaxFixedRateService, CommerceTaxFixedRateService> serviceTracker =
			new ServiceTracker<CommerceTaxFixedRateService, CommerceTaxFixedRateService>(bundle.getBundleContext(),
				CommerceTaxFixedRateService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}