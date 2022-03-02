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

package com.liferay.commerce.payment.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePaymentMethodGroupRelQualifierService}.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRelQualifierService
 * @generated
 */
public class CommercePaymentMethodGroupRelQualifierServiceWrapper
	implements CommercePaymentMethodGroupRelQualifierService,
			   ServiceWrapper<CommercePaymentMethodGroupRelQualifierService> {

	public CommercePaymentMethodGroupRelQualifierServiceWrapper() {
		this(null);
	}

	public CommercePaymentMethodGroupRelQualifierServiceWrapper(
		CommercePaymentMethodGroupRelQualifierService
			commercePaymentMethodGroupRelQualifierService) {

		_commercePaymentMethodGroupRelQualifierService =
			commercePaymentMethodGroupRelQualifierService;
	}

	@Override
	public
		com.liferay.commerce.payment.model.
			CommercePaymentMethodGroupRelQualifier
					addCommercePaymentMethodGroupRelQualifier(
						String className, long classPK,
						long commercePaymentMethodGroupRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelQualifierService.
			addCommercePaymentMethodGroupRelQualifier(
				className, classPK, commercePaymentMethodGroupRelId);
	}

	@Override
	public void deleteCommercePaymentMethodGroupRelQualifier(
			long commercePaymentMethodGroupRelQualifierId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePaymentMethodGroupRelQualifierService.
			deleteCommercePaymentMethodGroupRelQualifier(
				commercePaymentMethodGroupRelQualifierId);
	}

	@Override
	public void deleteCommercePaymentMethodGroupRelQualifiers(
			String className, long commercePaymentMethodGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePaymentMethodGroupRelQualifierService.
			deleteCommercePaymentMethodGroupRelQualifiers(
				className, commercePaymentMethodGroupRelId);
	}

	@Override
	public void
			deleteCommercePaymentMethodGroupRelQualifiersByCommercePaymentMethodGroupRelId(
				long commercePaymentMethodGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePaymentMethodGroupRelQualifierService.
			deleteCommercePaymentMethodGroupRelQualifiersByCommercePaymentMethodGroupRelId(
				commercePaymentMethodGroupRelId);
	}

	@Override
	public
		com.liferay.commerce.payment.model.
			CommercePaymentMethodGroupRelQualifier
					fetchCommercePaymentMethodGroupRelQualifier(
						String className, long classPK,
						long commercePaymentMethodGroupRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelQualifierService.
			fetchCommercePaymentMethodGroupRelQualifier(
				className, classPK, commercePaymentMethodGroupRelId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.
			CommercePaymentMethodGroupRelQualifier>
					getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiers(
						long commercePaymentMethodGroupRelId, String keywords,
						int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelQualifierService.
			getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiers(
				commercePaymentMethodGroupRelId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiersCount(
			long commercePaymentMethodGroupRelId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelQualifierService.
			getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiersCount(
				commercePaymentMethodGroupRelId, keywords);
	}

	@Override
	public
		com.liferay.commerce.payment.model.
			CommercePaymentMethodGroupRelQualifier
					getCommercePaymentMethodGroupRelQualifier(
						long commercePaymentMethodGroupRelQualifierId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelQualifierService.
			getCommercePaymentMethodGroupRelQualifier(
				commercePaymentMethodGroupRelQualifierId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.
			CommercePaymentMethodGroupRelQualifier>
					getCommercePaymentMethodGroupRelQualifiers(
						long commercePaymentMethodGroupRelId, int start,
						int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.payment.model.
								CommercePaymentMethodGroupRelQualifier>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelQualifierService.
			getCommercePaymentMethodGroupRelQualifiers(
				commercePaymentMethodGroupRelId, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.
			CommercePaymentMethodGroupRelQualifier>
					getCommercePaymentMethodGroupRelQualifiers(
						String className, long commercePaymentMethodGroupRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelQualifierService.
			getCommercePaymentMethodGroupRelQualifiers(
				className, commercePaymentMethodGroupRelId);
	}

	@Override
	public int getCommercePaymentMethodGroupRelQualifiersCount(
			long commercePaymentMethodGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelQualifierService.
			getCommercePaymentMethodGroupRelQualifiersCount(
				commercePaymentMethodGroupRelId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.
			CommercePaymentMethodGroupRelQualifier>
					getCommerceTermEntryCommercePaymentMethodGroupRelQualifiers(
						long commercePaymentMethodGroupRelId, String keywords,
						int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelQualifierService.
			getCommerceTermEntryCommercePaymentMethodGroupRelQualifiers(
				commercePaymentMethodGroupRelId, keywords, start, end);
	}

	@Override
	public int getCommerceTermEntryCommercePaymentMethodGroupRelQualifiersCount(
			long commercePaymentMethodGroupRelId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelQualifierService.
			getCommerceTermEntryCommercePaymentMethodGroupRelQualifiersCount(
				commercePaymentMethodGroupRelId, keywords);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePaymentMethodGroupRelQualifierService.
			getOSGiServiceIdentifier();
	}

	@Override
	public CommercePaymentMethodGroupRelQualifierService getWrappedService() {
		return _commercePaymentMethodGroupRelQualifierService;
	}

	@Override
	public void setWrappedService(
		CommercePaymentMethodGroupRelQualifierService
			commercePaymentMethodGroupRelQualifierService) {

		_commercePaymentMethodGroupRelQualifierService =
			commercePaymentMethodGroupRelQualifierService;
	}

	private CommercePaymentMethodGroupRelQualifierService
		_commercePaymentMethodGroupRelQualifierService;

}