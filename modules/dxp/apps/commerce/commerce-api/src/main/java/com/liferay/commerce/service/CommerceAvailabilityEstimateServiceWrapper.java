/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceAvailabilityEstimateService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityEstimateService
 * @generated
 */
@ProviderType
public class CommerceAvailabilityEstimateServiceWrapper
	implements CommerceAvailabilityEstimateService,
		ServiceWrapper<CommerceAvailabilityEstimateService> {
	public CommerceAvailabilityEstimateServiceWrapper(
		CommerceAvailabilityEstimateService commerceAvailabilityEstimateService) {
		_commerceAvailabilityEstimateService = commerceAvailabilityEstimateService;
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate addCommerceAvailabilityEstimate(
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityEstimateService.addCommerceAvailabilityEstimate(titleMap,
			priority, serviceContext);
	}

	@Override
	public void deleteCommerceAvailabilityEstimate(
		long commerceAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceAvailabilityEstimateService.deleteCommerceAvailabilityEstimate(commerceAvailabilityEstimateId);
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate getCommerceAvailabilityEstimate(
		long commerceAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityEstimateService.getCommerceAvailabilityEstimate(commerceAvailabilityEstimateId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAvailabilityEstimate> getCommerceAvailabilityEstimates(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAvailabilityEstimate> orderByComparator) {
		return _commerceAvailabilityEstimateService.getCommerceAvailabilityEstimates(groupId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceAvailabilityEstimatesCount(long groupId) {
		return _commerceAvailabilityEstimateService.getCommerceAvailabilityEstimatesCount(groupId);
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
	public com.liferay.commerce.model.CommerceAvailabilityEstimate updateCommerceAvailabilityEstimate(
		long commerceAvailabilityEstimateId,
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityEstimateService.updateCommerceAvailabilityEstimate(commerceAvailabilityEstimateId,
			titleMap, priority, serviceContext);
	}

	@Override
	public CommerceAvailabilityEstimateService getWrappedService() {
		return _commerceAvailabilityEstimateService;
	}

	@Override
	public void setWrappedService(
		CommerceAvailabilityEstimateService commerceAvailabilityEstimateService) {
		_commerceAvailabilityEstimateService = commerceAvailabilityEstimateService;
	}

	private CommerceAvailabilityEstimateService _commerceAvailabilityEstimateService;
}