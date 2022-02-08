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

package com.liferay.commerce.shipping.engine.fixed.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceShippingFixedOptionQualifierService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionQualifierService
 * @generated
 */
public class CommerceShippingFixedOptionQualifierServiceWrapper
	implements CommerceShippingFixedOptionQualifierService,
			   ServiceWrapper<CommerceShippingFixedOptionQualifierService> {

	public CommerceShippingFixedOptionQualifierServiceWrapper() {
		this(null);
	}

	public CommerceShippingFixedOptionQualifierServiceWrapper(
		CommerceShippingFixedOptionQualifierService
			commerceShippingFixedOptionQualifierService) {

		_commerceShippingFixedOptionQualifierService =
			commerceShippingFixedOptionQualifierService;
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionQualifier
				addCommerceShippingFixedOptionQualifier(
					String className, long classPK,
					long commerceShippingFixedOptionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionQualifierService.
			addCommerceShippingFixedOptionQualifier(
				className, classPK, commerceShippingFixedOptionId);
	}

	@Override
	public void deleteCommerceShippingFixedOptionQualifier(
			long commerceShippingFixedOptionQualifierId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceShippingFixedOptionQualifierService.
			deleteCommerceShippingFixedOptionQualifier(
				commerceShippingFixedOptionQualifierId);
	}

	@Override
	public void deleteCommerceShippingFixedOptionQualifiers(
			long commerceShippingFixedOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceShippingFixedOptionQualifierService.
			deleteCommerceShippingFixedOptionQualifiers(
				commerceShippingFixedOptionId);
	}

	@Override
	public void deleteCommerceShippingFixedOptionQualifiers(
			String className, long commerceShippingFixedOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceShippingFixedOptionQualifierService.
			deleteCommerceShippingFixedOptionQualifiers(
				className, commerceShippingFixedOptionId);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionQualifier
				fetchCommerceShippingFixedOptionQualifier(
					String className, long classPK,
					long commerceShippingFixedOptionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionQualifierService.
			fetchCommerceShippingFixedOptionQualifier(
				className, classPK, commerceShippingFixedOptionId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOptionQualifier>
					getCommerceOrderTypeCommerceShippingFixedOptionQualifiers(
						long commerceShippingFixedOptionId, String keywords,
						int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionQualifierService.
			getCommerceOrderTypeCommerceShippingFixedOptionQualifiers(
				commerceShippingFixedOptionId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderTypeCommerceShippingFixedOptionQualifiersCount(
			long commerceShippingFixedOptionId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionQualifierService.
			getCommerceOrderTypeCommerceShippingFixedOptionQualifiersCount(
				commerceShippingFixedOptionId, keywords);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionQualifier
				getCommerceShippingFixedOptionQualifier(
					long commerceShippingFixedOptionQualifierId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionQualifierService.
			getCommerceShippingFixedOptionQualifier(
				commerceShippingFixedOptionQualifierId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOptionQualifier>
					getCommerceShippingFixedOptionQualifiers(
						long commerceShippingFixedOptionId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionQualifierService.
			getCommerceShippingFixedOptionQualifiers(
				commerceShippingFixedOptionId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOptionQualifier>
					getCommerceShippingFixedOptionQualifiers(
						long commerceShippingFixedOptionId, int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.shipping.engine.fixed.model.
								CommerceShippingFixedOptionQualifier>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionQualifierService.
			getCommerceShippingFixedOptionQualifiers(
				commerceShippingFixedOptionId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingFixedOptionQualifiersCount(
			long commerceShippingFixedOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionQualifierService.
			getCommerceShippingFixedOptionQualifiersCount(
				commerceShippingFixedOptionId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOptionQualifier>
					getCommerceTermEntryCommerceShippingFixedOptionQualifiers(
						long commerceShippingFixedOptionId, String keywords,
						int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionQualifierService.
			getCommerceTermEntryCommerceShippingFixedOptionQualifiers(
				commerceShippingFixedOptionId, keywords, start, end);
	}

	@Override
	public int getCommerceTermEntryCommerceShippingFixedOptionQualifiersCount(
			long commerceShippingFixedOptionId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionQualifierService.
			getCommerceTermEntryCommerceShippingFixedOptionQualifiersCount(
				commerceShippingFixedOptionId, keywords);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceShippingFixedOptionQualifierService.
			getOSGiServiceIdentifier();
	}

	@Override
	public CommerceShippingFixedOptionQualifierService getWrappedService() {
		return _commerceShippingFixedOptionQualifierService;
	}

	@Override
	public void setWrappedService(
		CommerceShippingFixedOptionQualifierService
			commerceShippingFixedOptionQualifierService) {

		_commerceShippingFixedOptionQualifierService =
			commerceShippingFixedOptionQualifierService;
	}

	private CommerceShippingFixedOptionQualifierService
		_commerceShippingFixedOptionQualifierService;

}