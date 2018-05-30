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
 * Provides a wrapper for {@link CommerceAvailabilityRangeService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityRangeService
 * @generated
 */
@ProviderType
public class CommerceAvailabilityRangeServiceWrapper
	implements CommerceAvailabilityRangeService,
		ServiceWrapper<CommerceAvailabilityRangeService> {
	public CommerceAvailabilityRangeServiceWrapper(
		CommerceAvailabilityRangeService commerceAvailabilityRangeService) {
		_commerceAvailabilityRangeService = commerceAvailabilityRangeService;
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange addCommerceAvailabilityRange(
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityRangeService.addCommerceAvailabilityRange(titleMap,
			priority, serviceContext);
	}

	@Override
	public void deleteCommerceAvailabilityRange(
		long commerceAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceAvailabilityRangeService.deleteCommerceAvailabilityRange(commerceAvailabilityRangeId);
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange getCommerceAvailabilityRange(
		long commerceAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityRangeService.getCommerceAvailabilityRange(commerceAvailabilityRangeId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAvailabilityRange> getCommerceAvailabilityRanges(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAvailabilityRange> orderByComparator) {
		return _commerceAvailabilityRangeService.getCommerceAvailabilityRanges(groupId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceAvailabilityRangesCount(long groupId) {
		return _commerceAvailabilityRangeService.getCommerceAvailabilityRangesCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAvailabilityRangeService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange updateCommerceAvailabilityRange(
		long commerceAvailabilityRangeId,
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityRangeService.updateCommerceAvailabilityRange(commerceAvailabilityRangeId,
			titleMap, priority, serviceContext);
	}

	@Override
	public CommerceAvailabilityRangeService getWrappedService() {
		return _commerceAvailabilityRangeService;
	}

	@Override
	public void setWrappedService(
		CommerceAvailabilityRangeService commerceAvailabilityRangeService) {
		_commerceAvailabilityRangeService = commerceAvailabilityRangeService;
	}

	private CommerceAvailabilityRangeService _commerceAvailabilityRangeService;
}