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
 * Provides a wrapper for {@link CommercePaymentMethodGroupRelService}.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRelService
 * @generated
 */
public class CommercePaymentMethodGroupRelServiceWrapper
	implements CommercePaymentMethodGroupRelService,
			   ServiceWrapper<CommercePaymentMethodGroupRelService> {

	public CommercePaymentMethodGroupRelServiceWrapper() {
		this(null);
	}

	public CommercePaymentMethodGroupRelServiceWrapper(
		CommercePaymentMethodGroupRelService
			commercePaymentMethodGroupRelService) {

		_commercePaymentMethodGroupRelService =
			commercePaymentMethodGroupRelService;
	}

	@Override
	public com.liferay.commerce.model.CommerceAddressRestriction
			addCommerceAddressRestriction(
				long groupId, long classPK, long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			addCommerceAddressRestriction(groupId, classPK, countryId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.model.CommerceAddressRestriction
			addCommerceAddressRestriction(
				long classPK, long countryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			addCommerceAddressRestriction(classPK, countryId, serviceContext);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel
			addCommercePaymentMethodGroupRel(
				long groupId, java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				java.io.File imageFile, String engineKey, double priority,
				boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			addCommercePaymentMethodGroupRel(
				groupId, nameMap, descriptionMap, imageFile, engineKey,
				priority, active);
	}

	@Override
	public void deleteCommerceAddressRestriction(
			long commerceAddressRestrictionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePaymentMethodGroupRelService.deleteCommerceAddressRestriction(
			commerceAddressRestrictionId);
	}

	@Override
	public void deleteCommerceAddressRestrictions(
			long commercePaymentMethodGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePaymentMethodGroupRelService.deleteCommerceAddressRestrictions(
			commercePaymentMethodGroupRelId);
	}

	@Override
	public void deleteCommercePaymentMethodGroupRel(
			long commercePaymentMethodGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePaymentMethodGroupRelService.
			deleteCommercePaymentMethodGroupRel(
				commercePaymentMethodGroupRelId);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel
			fetchCommercePaymentMethodGroupRel(
				long commercePaymentMethodGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			fetchCommercePaymentMethodGroupRel(commercePaymentMethodGroupRelId);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel
			fetchCommercePaymentMethodGroupRel(long groupId, String engineKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			fetchCommercePaymentMethodGroupRel(groupId, engineKey);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddressRestriction>
			getCommerceAddressRestrictions(
				long classPK, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceAddressRestriction>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			getCommerceAddressRestrictions(
				classPK, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAddressRestrictionsCount(long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			getCommerceAddressRestrictionsCount(classPK);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel
			getCommercePaymentMethodGroupRel(
				long commercePaymentMethodGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			getCommercePaymentMethodGroupRel(commercePaymentMethodGroupRelId);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel
			getCommercePaymentMethodGroupRel(long groupId, String engineKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			getCommercePaymentMethodGroupRel(groupId, engineKey);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel>
				getCommercePaymentMethodGroupRels(long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			getCommercePaymentMethodGroupRels(groupId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel>
				getCommercePaymentMethodGroupRels(long groupId, boolean active)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			getCommercePaymentMethodGroupRels(groupId, active);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel>
				getCommercePaymentMethodGroupRels(
					long groupId, boolean active, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			getCommercePaymentMethodGroupRels(groupId, active, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel>
				getCommercePaymentMethodGroupRels(
					long groupId, boolean active, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.payment.model.
							CommercePaymentMethodGroupRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			getCommercePaymentMethodGroupRels(
				groupId, active, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel>
				getCommercePaymentMethodGroupRels(
					long groupId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.payment.model.
							CommercePaymentMethodGroupRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			getCommercePaymentMethodGroupRels(
				groupId, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel>
				getCommercePaymentMethodGroupRels(
					long groupId, long countryId, boolean active)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			getCommercePaymentMethodGroupRels(groupId, countryId, active);
	}

	@Override
	public int getCommercePaymentMethodGroupRelsCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			getCommercePaymentMethodGroupRelsCount(groupId);
	}

	@Override
	public int getCommercePaymentMethodGroupRelsCount(
			long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			getCommercePaymentMethodGroupRelsCount(groupId, active);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePaymentMethodGroupRelService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel
			setActive(long commercePaymentMethodGroupRelId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.setActive(
			commercePaymentMethodGroupRelId, active);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel
			updateCommercePaymentMethodGroupRel(
				long commercePaymentMethodGroupRelId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				java.io.File imageFile, double priority, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentMethodGroupRelService.
			updateCommercePaymentMethodGroupRel(
				commercePaymentMethodGroupRelId, nameMap, descriptionMap,
				imageFile, priority, active);
	}

	@Override
	public CommercePaymentMethodGroupRelService getWrappedService() {
		return _commercePaymentMethodGroupRelService;
	}

	@Override
	public void setWrappedService(
		CommercePaymentMethodGroupRelService
			commercePaymentMethodGroupRelService) {

		_commercePaymentMethodGroupRelService =
			commercePaymentMethodGroupRelService;
	}

	private CommercePaymentMethodGroupRelService
		_commercePaymentMethodGroupRelService;

}