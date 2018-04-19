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
		long commerceUserSegmentEntryId, java.lang.String type,
		java.lang.String typeSettings, double priority,
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
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceUserSegmentCriterionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion updateCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId, java.lang.String type,
		java.lang.String typeSettings, double priority,
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