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
 * Provides a wrapper for {@link CommerceAvailabilityEstimateService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityEstimateService
 * @generated
 */
public class CommerceAvailabilityEstimateServiceWrapper
	implements CommerceAvailabilityEstimateService,
			   ServiceWrapper<CommerceAvailabilityEstimateService> {

	public CommerceAvailabilityEstimateServiceWrapper() {
		this(null);
	}

	public CommerceAvailabilityEstimateServiceWrapper(
		CommerceAvailabilityEstimateService
			commerceAvailabilityEstimateService) {

		_commerceAvailabilityEstimateService =
			commerceAvailabilityEstimateService;
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate
			addCommerceAvailabilityEstimate(
				java.util.Map<java.util.Locale, String> titleMap,
				double priority,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAvailabilityEstimateService.
			addCommerceAvailabilityEstimate(titleMap, priority, serviceContext);
	}

	@Override
	public void deleteCommerceAvailabilityEstimate(
			long commerceAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAvailabilityEstimateService.deleteCommerceAvailabilityEstimate(
			commerceAvailabilityEstimateId);
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate
			getCommerceAvailabilityEstimate(long commerceAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAvailabilityEstimateService.
			getCommerceAvailabilityEstimate(commerceAvailabilityEstimateId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.model.CommerceAvailabilityEstimate>
				getCommerceAvailabilityEstimates(
					long companyId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.model.
							CommerceAvailabilityEstimate> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAvailabilityEstimateService.
			getCommerceAvailabilityEstimates(
				companyId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAvailabilityEstimatesCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAvailabilityEstimateService.
			getCommerceAvailabilityEstimatesCount(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAvailabilityEstimateService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate
			updateCommerceAvailabilityEstimate(
				long commerceAvailabilityEstimateId,
				java.util.Map<java.util.Locale, String> titleMap,
				double priority,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAvailabilityEstimateService.
			updateCommerceAvailabilityEstimate(
				commerceAvailabilityEstimateId, titleMap, priority,
				serviceContext);
	}

	@Override
	public CommerceAvailabilityEstimateService getWrappedService() {
		return _commerceAvailabilityEstimateService;
	}

	@Override
	public void setWrappedService(
		CommerceAvailabilityEstimateService
			commerceAvailabilityEstimateService) {

		_commerceAvailabilityEstimateService =
			commerceAvailabilityEstimateService;
	}

	private CommerceAvailabilityEstimateService
		_commerceAvailabilityEstimateService;

}