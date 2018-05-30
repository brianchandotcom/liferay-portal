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

package com.liferay.commerce.user.segment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceUserSegmentCriterionService}.
 *
 * @author Marco Leo
 * @see CommerceUserSegmentCriterionService
 * @generated
 */
@ProviderType
public class CommerceUserSegmentCriterionServiceWrapper
	implements CommerceUserSegmentCriterionService,
		ServiceWrapper<CommerceUserSegmentCriterionService> {
	public CommerceUserSegmentCriterionServiceWrapper(
		CommerceUserSegmentCriterionService commerceUserSegmentCriterionService) {
		_commerceUserSegmentCriterionService = commerceUserSegmentCriterionService;
	}

	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion addCommerceUserSegmentCriterion(
		long commerceUserSegmentEntryId, String type, String typeSettings,
		double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentCriterionService.addCommerceUserSegmentCriterion(commerceUserSegmentEntryId,
			type, typeSettings, priority, serviceContext);
	}

	@Override
	public void deleteCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceUserSegmentCriterionService.deleteCommerceUserSegmentCriterion(commerceUserSegmentCriterionId);
	}

	@Override
	public java.util.List<com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion> getCommerceUserSegmentCriteria(
		long commerceUserSegmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentCriterionService.getCommerceUserSegmentCriteria(commerceUserSegmentEntryId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceUserSegmentCriteriaCount(
		long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentCriterionService.getCommerceUserSegmentCriteriaCount(commerceUserSegmentEntryId);
	}

	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion getCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentCriterionService.getCommerceUserSegmentCriterion(commerceUserSegmentCriterionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceUserSegmentCriterionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion updateCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId, String type, String typeSettings,
		double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentCriterionService.updateCommerceUserSegmentCriterion(commerceUserSegmentCriterionId,
			type, typeSettings, priority, serviceContext);
	}

	@Override
	public CommerceUserSegmentCriterionService getWrappedService() {
		return _commerceUserSegmentCriterionService;
	}

	@Override
	public void setWrappedService(
		CommerceUserSegmentCriterionService commerceUserSegmentCriterionService) {
		_commerceUserSegmentCriterionService = commerceUserSegmentCriterionService;
	}

	private CommerceUserSegmentCriterionService _commerceUserSegmentCriterionService;
}