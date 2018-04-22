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
 * Provides the local service utility for CommercePriceCalculation. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommercePriceCalculationLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceCalculationLocalService
 * @see com.liferay.commerce.service.base.CommercePriceCalculationLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommercePriceCalculationLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommercePriceCalculationLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommercePriceCalculationLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static java.lang.String formatPrice(long groupId,
		java.math.BigDecimal price)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().formatPrice(groupId, price);
	}

	public static java.lang.String formatPriceWithCurrency(
		long commerceCurrencyId, java.math.BigDecimal price)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().formatPriceWithCurrency(commerceCurrencyId, price);
	}

	public static java.math.BigDecimal getFinalPrice(long groupId, long userId,
		long cpInstanceId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFinalPrice(groupId, userId, cpInstanceId, quantity);
	}

	public static java.math.BigDecimal getFinalPrice(long groupId,
		long commerceCurrencyId, long userId, long cpInstanceId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFinalPrice(groupId, commerceCurrencyId, userId,
			cpInstanceId, quantity);
	}

	public static java.lang.String getFormattedFinalPrice(long groupId,
		long userId, long cpInstanceId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFormattedFinalPrice(groupId, userId, cpInstanceId,
			quantity);
	}

	public static java.lang.String getFormattedFinalPrice(long groupId,
		long commerceCurrencyId, long userId, long cpInstanceId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFormattedFinalPrice(groupId, commerceCurrencyId, userId,
			cpInstanceId, quantity);
	}

	public static java.lang.String getFormattedOrderSubtotal(
		com.liferay.commerce.model.CommerceOrder commerceOrder)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFormattedOrderSubtotal(commerceOrder);
	}

	public static java.math.BigDecimal getOrderSubtotal(
		com.liferay.commerce.model.CommerceOrder commerceOrder)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOrderSubtotal(commerceOrder);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.math.BigDecimal getUnitPrice(long groupId, long userId,
		long cpInstanceId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUnitPrice(groupId, userId, cpInstanceId, quantity);
	}

	public static java.math.BigDecimal getUnitPrice(long groupId,
		long commerceCurrencyId, long userId, long cpInstanceId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getUnitPrice(groupId, commerceCurrencyId, userId,
			cpInstanceId, quantity);
	}

	public static CommercePriceCalculationLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePriceCalculationLocalService, CommercePriceCalculationLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommercePriceCalculationLocalService.class);

		ServiceTracker<CommercePriceCalculationLocalService, CommercePriceCalculationLocalService> serviceTracker =
			new ServiceTracker<CommercePriceCalculationLocalService, CommercePriceCalculationLocalService>(bundle.getBundleContext(),
				CommercePriceCalculationLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}