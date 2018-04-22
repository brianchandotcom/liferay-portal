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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceCalculationLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceCalculationLocalService
 * @generated
 */
@ProviderType
public class CommercePriceCalculationLocalServiceWrapper
	implements CommercePriceCalculationLocalService,
		ServiceWrapper<CommercePriceCalculationLocalService> {
	public CommercePriceCalculationLocalServiceWrapper(
		CommercePriceCalculationLocalService commercePriceCalculationLocalService) {
		_commercePriceCalculationLocalService = commercePriceCalculationLocalService;
	}

	@Override
	public java.lang.String formatPrice(long groupId, java.math.BigDecimal price)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceCalculationLocalService.formatPrice(groupId, price);
	}

	@Override
	public java.lang.String formatPriceWithCurrency(long commerceCurrencyId,
		java.math.BigDecimal price)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceCalculationLocalService.formatPriceWithCurrency(commerceCurrencyId,
			price);
	}

	@Override
	public java.math.BigDecimal getFinalPrice(long groupId, long userId,
		long cpInstanceId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceCalculationLocalService.getFinalPrice(groupId,
			userId, cpInstanceId, quantity);
	}

	@Override
	public java.math.BigDecimal getFinalPrice(long groupId,
		long commerceCurrencyId, long userId, long cpInstanceId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceCalculationLocalService.getFinalPrice(groupId,
			commerceCurrencyId, userId, cpInstanceId, quantity);
	}

	@Override
	public java.lang.String getFormattedFinalPrice(long groupId, long userId,
		long cpInstanceId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceCalculationLocalService.getFormattedFinalPrice(groupId,
			userId, cpInstanceId, quantity);
	}

	@Override
	public java.lang.String getFormattedFinalPrice(long groupId,
		long commerceCurrencyId, long userId, long cpInstanceId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceCalculationLocalService.getFormattedFinalPrice(groupId,
			commerceCurrencyId, userId, cpInstanceId, quantity);
	}

	@Override
	public java.lang.String getFormattedOrderSubtotal(
		com.liferay.commerce.model.CommerceOrder commerceOrder)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceCalculationLocalService.getFormattedOrderSubtotal(commerceOrder);
	}

	@Override
	public java.math.BigDecimal getOrderSubtotal(
		com.liferay.commerce.model.CommerceOrder commerceOrder)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceCalculationLocalService.getOrderSubtotal(commerceOrder);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commercePriceCalculationLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.math.BigDecimal getUnitPrice(long groupId, long userId,
		long cpInstanceId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceCalculationLocalService.getUnitPrice(groupId,
			userId, cpInstanceId, quantity);
	}

	@Override
	public java.math.BigDecimal getUnitPrice(long groupId,
		long commerceCurrencyId, long userId, long cpInstanceId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceCalculationLocalService.getUnitPrice(groupId,
			commerceCurrencyId, userId, cpInstanceId, quantity);
	}

	@Override
	public CommercePriceCalculationLocalService getWrappedService() {
		return _commercePriceCalculationLocalService;
	}

	@Override
	public void setWrappedService(
		CommercePriceCalculationLocalService commercePriceCalculationLocalService) {
		_commercePriceCalculationLocalService = commercePriceCalculationLocalService;
	}

	private CommercePriceCalculationLocalService _commercePriceCalculationLocalService;
}