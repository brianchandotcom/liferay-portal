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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceListQualificationTypeRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListQualificationTypeRelService
 * @generated
 */
@ProviderType
public class CommercePriceListQualificationTypeRelServiceWrapper
	implements CommercePriceListQualificationTypeRelService,
		ServiceWrapper<CommercePriceListQualificationTypeRelService> {
	public CommercePriceListQualificationTypeRelServiceWrapper(
		CommercePriceListQualificationTypeRelService commercePriceListQualificationTypeRelService) {
		_commercePriceListQualificationTypeRelService = commercePriceListQualificationTypeRelService;
	}

	@Override
	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel addCommercePriceListQualificationTypeRel(
		long commercePriceListId,
		java.lang.String commercePriceListQualificationType, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListQualificationTypeRelService.addCommercePriceListQualificationTypeRel(commercePriceListId,
			commercePriceListQualificationType, order, serviceContext);
	}

	@Override
	public void deleteCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commercePriceListQualificationTypeRelService.deleteCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId);
	}

	@Override
	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel fetchCommercePriceListQualificationTypeRel(
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId) {
		return _commercePriceListQualificationTypeRelService.fetchCommercePriceListQualificationTypeRel(commercePriceListQualificationType,
			commercePriceListId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRels(
		long commercePriceListId) {
		return _commercePriceListQualificationTypeRelService.getCommercePriceListQualificationTypeRels(commercePriceListId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRels(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> orderByComparator) {
		return _commercePriceListQualificationTypeRelService.getCommercePriceListQualificationTypeRels(commercePriceListId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListQualificationTypeRelsCount(
		long commercePriceListId) {
		return _commercePriceListQualificationTypeRelService.getCommercePriceListQualificationTypeRelsCount(commercePriceListId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commercePriceListQualificationTypeRelService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel updateCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListQualificationTypeRelService.updateCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId,
			order, serviceContext);
	}

	@Override
	public CommercePriceListQualificationTypeRelService getWrappedService() {
		return _commercePriceListQualificationTypeRelService;
	}

	@Override
	public void setWrappedService(
		CommercePriceListQualificationTypeRelService commercePriceListQualificationTypeRelService) {
		_commercePriceListQualificationTypeRelService = commercePriceListQualificationTypeRelService;
	}

	private CommercePriceListQualificationTypeRelService _commercePriceListQualificationTypeRelService;
}