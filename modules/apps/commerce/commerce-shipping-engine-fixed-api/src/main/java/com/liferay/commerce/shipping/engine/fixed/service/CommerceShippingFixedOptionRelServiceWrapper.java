/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceShippingFixedOptionRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRelService
 * @generated
 */
public class CommerceShippingFixedOptionRelServiceWrapper
	implements CommerceShippingFixedOptionRelService,
			   ServiceWrapper<CommerceShippingFixedOptionRelService> {

	public CommerceShippingFixedOptionRelServiceWrapper() {
		this(null);
	}

	public CommerceShippingFixedOptionRelServiceWrapper(
		CommerceShippingFixedOptionRelService
			commerceShippingFixedOptionRelService) {

		_commerceShippingFixedOptionRelService =
			commerceShippingFixedOptionRelService;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel(
				long commerceInventoryWarehouseId,
				long commerceShippingFixedOptionId,
				long commerceShippingMethodId, long countryId, long regionId,
				java.math.BigDecimal fixedPrice, double ratePercentage,
				java.math.BigDecimal rateUnitWeightPrice, double weightFrom,
				double weightTo, String zip,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionRelService.
			addCommerceShippingFixedOptionRel(
				commerceInventoryWarehouseId, commerceShippingFixedOptionId,
				commerceShippingMethodId, countryId, regionId, fixedPrice,
				ratePercentage, rateUnitWeightPrice, weightFrom, weightTo, zip,
				serviceContext);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel(
				long groupId, long commerceInventoryWarehouseId,
				long commerceShippingFixedOptionId,
				long commerceShippingMethodId, long countryId, long regionId,
				java.math.BigDecimal fixedPrice, double ratePercentage,
				java.math.BigDecimal rateUnitWeightPrice, double weightFrom,
				double weightTo, String zip)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionRelService.
			addCommerceShippingFixedOptionRel(
				groupId, commerceInventoryWarehouseId,
				commerceShippingFixedOptionId, commerceShippingMethodId,
				countryId, regionId, fixedPrice, ratePercentage,
				rateUnitWeightPrice, weightFrom, weightTo, zip);
	}

	@Override
	public void deleteCommerceShippingFixedOptionRel(
			long commerceShippingFixedOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceShippingFixedOptionRelService.
			deleteCommerceShippingFixedOptionRel(
				commerceShippingFixedOptionRelId);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionRel fetchCommerceShippingFixedOptionRel(
				long commerceShippingFixedOptionRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionRelService.
			fetchCommerceShippingFixedOptionRel(
				commerceShippingFixedOptionRelId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOptionRel> getCommerceShippingFixedOptionRels(
					long commerceShippingFixedOptionId,
					long commerceShippingMethodId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.shipping.engine.fixed.model.
							CommerceShippingFixedOptionRel> orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionRelService.
			getCommerceShippingFixedOptionRels(
				commerceShippingFixedOptionId, commerceShippingMethodId, start,
				end, orderByComparator);
	}

	@Override
	public int getCommerceShippingFixedOptionRelsCount(
			long commerceShippingFixedOptionId, long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionRelService.
			getCommerceShippingFixedOptionRelsCount(
				commerceShippingFixedOptionId, commerceShippingMethodId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOptionRel>
					getCommerceShippingMethodFixedOptionRels(
						long commerceShippingMethodId, int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.shipping.engine.fixed.model.
								CommerceShippingFixedOptionRel>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionRelService.
			getCommerceShippingMethodFixedOptionRels(
				commerceShippingMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingMethodFixedOptionRelsCount(
			long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionRelService.
			getCommerceShippingMethodFixedOptionRelsCount(
				commerceShippingMethodId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceShippingFixedOptionRelService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionRel updateCommerceShippingFixedOptionRel(
				long commerceShippingFixedOptionRelId,
				long commerceInventoryWarehouseId, long countryId,
				long regionId, java.math.BigDecimal fixedPrice,
				double ratePercentage, java.math.BigDecimal rateUnitWeightPrice,
				double weightFrom, double weightTo, String zip)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingFixedOptionRelService.
			updateCommerceShippingFixedOptionRel(
				commerceShippingFixedOptionRelId, commerceInventoryWarehouseId,
				countryId, regionId, fixedPrice, ratePercentage,
				rateUnitWeightPrice, weightFrom, weightTo, zip);
	}

	@Override
	public CommerceShippingFixedOptionRelService getWrappedService() {
		return _commerceShippingFixedOptionRelService;
	}

	@Override
	public void setWrappedService(
		CommerceShippingFixedOptionRelService
			commerceShippingFixedOptionRelService) {

		_commerceShippingFixedOptionRelService =
			commerceShippingFixedOptionRelService;
	}

	private CommerceShippingFixedOptionRelService
		_commerceShippingFixedOptionRelService;

}