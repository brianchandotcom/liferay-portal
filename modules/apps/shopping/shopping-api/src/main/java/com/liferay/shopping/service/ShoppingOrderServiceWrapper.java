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

package com.liferay.shopping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ShoppingOrderService}.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderService
 * @generated
 */
@ProviderType
public class ShoppingOrderServiceWrapper implements ShoppingOrderService,
	ServiceWrapper<ShoppingOrderService> {
	public ShoppingOrderServiceWrapper(
		ShoppingOrderService shoppingOrderService) {
		_shoppingOrderService = shoppingOrderService;
	}

	@Override
	public void completeOrder(long groupId, String number, String ppTxnId,
		String ppPaymentStatus, double ppPaymentGross, String ppReceiverEmail,
		String ppPayerEmail,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingOrderService.completeOrder(groupId, number, ppTxnId,
			ppPaymentStatus, ppPaymentGross, ppReceiverEmail, ppPayerEmail,
			serviceContext);
	}

	@Override
	public void deleteOrder(long groupId, long orderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingOrderService.deleteOrder(groupId, orderId);
	}

	@Override
	public com.liferay.shopping.model.ShoppingOrder getOrder(long groupId,
		long orderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderService.getOrder(groupId, orderId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _shoppingOrderService.getOSGiServiceIdentifier();
	}

	@Override
	public void sendEmail(long groupId, long orderId, String emailType,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingOrderService.sendEmail(groupId, orderId, emailType,
			serviceContext);
	}

	@Override
	public com.liferay.shopping.model.ShoppingOrder updateOrder(long groupId,
		long orderId, String ppTxnId, String ppPaymentStatus,
		double ppPaymentGross, String ppReceiverEmail, String ppPayerEmail)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderService.updateOrder(groupId, orderId, ppTxnId,
			ppPaymentStatus, ppPaymentGross, ppReceiverEmail, ppPayerEmail);
	}

	@Override
	public com.liferay.shopping.model.ShoppingOrder updateOrder(long groupId,
		long orderId, String billingFirstName, String billingLastName,
		String billingEmailAddress, String billingCompany,
		String billingStreet, String billingCity, String billingState,
		String billingZip, String billingCountry, String billingPhone,
		boolean shipToBilling, String shippingFirstName,
		String shippingLastName, String shippingEmailAddress,
		String shippingCompany, String shippingStreet, String shippingCity,
		String shippingState, String shippingZip, String shippingCountry,
		String shippingPhone, String ccName, String ccType, String ccNumber,
		int ccExpMonth, int ccExpYear, String ccVerNumber, String comments)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderService.updateOrder(groupId, orderId,
			billingFirstName, billingLastName, billingEmailAddress,
			billingCompany, billingStreet, billingCity, billingState,
			billingZip, billingCountry, billingPhone, shipToBilling,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			shippingCompany, shippingStreet, shippingCity, shippingState,
			shippingZip, shippingCountry, shippingPhone, ccName, ccType,
			ccNumber, ccExpMonth, ccExpYear, ccVerNumber, comments);
	}

	@Override
	public ShoppingOrderService getWrappedService() {
		return _shoppingOrderService;
	}

	@Override
	public void setWrappedService(ShoppingOrderService shoppingOrderService) {
		_shoppingOrderService = shoppingOrderService;
	}

	private ShoppingOrderService _shoppingOrderService;
}