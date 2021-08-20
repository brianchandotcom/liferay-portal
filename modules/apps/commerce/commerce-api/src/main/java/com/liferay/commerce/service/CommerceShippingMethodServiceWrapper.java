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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceShippingMethodService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingMethodService
 * @generated
 */
public class CommerceShippingMethodServiceWrapper
	implements CommerceShippingMethodService,
			   ServiceWrapper<CommerceShippingMethodService> {

	public CommerceShippingMethodServiceWrapper(
		CommerceShippingMethodService commerceShippingMethodService) {

		_commerceShippingMethodService = commerceShippingMethodService;
	}

	@Override
	public com.liferay.commerce.model.CommerceAddressRestriction
			addCommerceAddressRestriction(
				long groupId, long commerceShippingMethodId, long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingMethodService.addCommerceAddressRestriction(
			groupId, commerceShippingMethodId, countryId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.model.CommerceAddressRestriction
			addCommerceAddressRestriction(
				long commerceShippingMethodId, long countryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingMethodService.addCommerceAddressRestriction(
			commerceShippingMethodId, countryId, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingMethod
			addCommerceShippingMethod(
				long groupId, java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				java.io.File imageFile, String engineKey, double priority,
				boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingMethodService.addCommerceShippingMethod(
			groupId, nameMap, descriptionMap, imageFile, engineKey, priority,
			active);
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingMethod
			createCommerceShippingMethod(long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingMethodService.createCommerceShippingMethod(
			commerceShippingMethodId);
	}

	@Override
	public void deleteCommerceAddressRestriction(
			long commerceAddressRestrictionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceShippingMethodService.deleteCommerceAddressRestriction(
			commerceAddressRestrictionId);
	}

	@Override
	public void deleteCommerceAddressRestrictions(long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceShippingMethodService.deleteCommerceAddressRestrictions(
			commerceShippingMethodId);
	}

	@Override
	public void deleteCommerceShippingMethod(long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceShippingMethodService.deleteCommerceShippingMethod(
			commerceShippingMethodId);
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingMethod
			fetchCommerceShippingMethod(long groupId, String engineKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingMethodService.fetchCommerceShippingMethod(
			groupId, engineKey);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddressRestriction>
			getCommerceAddressRestrictions(
				long commerceShippingMethodId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceAddressRestriction>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingMethodService.getCommerceAddressRestrictions(
			commerceShippingMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAddressRestrictionsCount(
			long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingMethodService.
			getCommerceAddressRestrictionsCount(commerceShippingMethodId);
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingMethod
			getCommerceShippingMethod(long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingMethodService.getCommerceShippingMethod(
			commerceShippingMethodId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShippingMethod>
			getCommerceShippingMethods(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingMethodService.getCommerceShippingMethods(
			groupId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShippingMethod>
			getCommerceShippingMethods(long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingMethodService.getCommerceShippingMethods(
			groupId, active);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShippingMethod>
			getCommerceShippingMethods(
				long groupId, long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingMethodService.getCommerceShippingMethods(
			groupId, countryId, active);
	}

	@Override
	public int getCommerceShippingMethodsCount(long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingMethodService.getCommerceShippingMethodsCount(
			groupId, active);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceShippingMethodService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingMethod setActive(
			long commerceShippingMethodId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingMethodService.setActive(
			commerceShippingMethodId, active);
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingMethod
			updateCommerceShippingMethod(
				long commerceShippingMethodId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				java.io.File imageFile, double priority, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingMethodService.updateCommerceShippingMethod(
			commerceShippingMethodId, nameMap, descriptionMap, imageFile,
			priority, active);
	}

	@Override
	public CommerceShippingMethodService getWrappedService() {
		return _commerceShippingMethodService;
	}

	@Override
	public void setWrappedService(
		CommerceShippingMethodService commerceShippingMethodService) {

		_commerceShippingMethodService = commerceShippingMethodService;
	}

	private CommerceShippingMethodService _commerceShippingMethodService;

}