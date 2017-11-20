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

package com.liferay.commerce.price.list.qualification.type.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceListUserRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserRelService
 * @generated
 */
@ProviderType
public class CommercePriceListUserRelServiceWrapper
	implements CommercePriceListUserRelService,
		ServiceWrapper<CommercePriceListUserRelService> {
	public CommercePriceListUserRelServiceWrapper(
		CommercePriceListUserRelService commercePriceListUserRelService) {
		_commercePriceListUserRelService = commercePriceListUserRelService;
	}

	@Override
	public com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel addCommercePriceListUserRel(
		long commercePriceListQualificationTypeRelId,
		java.lang.String className, long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserRelService.addCommercePriceListUserRel(commercePriceListQualificationTypeRelId,
			className, classPK, serviceContext);
	}

	@Override
	public void deleteCommercePriceListUserRel(long commercePriceListUserRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commercePriceListUserRelService.deleteCommercePriceListUserRel(commercePriceListUserRelId);
	}

	@Override
	public void deleteCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId,
		java.lang.String className, long classPK) {
		_commercePriceListUserRelService.deleteCommercePriceListUserRels(commercePriceListQualificationTypeRelId,
			className, classPK);
	}

	@Override
	public com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel getCommercePriceListUserRel(
		long commercePriceListUserRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserRelService.getCommercePriceListUserRel(commercePriceListUserRelId);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> getCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId, java.lang.String className) {
		return _commercePriceListUserRelService.getCommercePriceListUserRels(commercePriceListQualificationTypeRelId,
			className);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> getCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId,
		java.lang.String className, int start, int end) {
		return _commercePriceListUserRelService.getCommercePriceListUserRels(commercePriceListQualificationTypeRelId,
			className, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> getCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId,
		java.lang.String className, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> orderByComparator) {
		return _commercePriceListUserRelService.getCommercePriceListUserRels(commercePriceListQualificationTypeRelId,
			className, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListUserRelsCount(
		long commercePriceListQualificationTypeRelId, java.lang.String className) {
		return _commercePriceListUserRelService.getCommercePriceListUserRelsCount(commercePriceListQualificationTypeRelId,
			className);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commercePriceListUserRelService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel updateCommercePriceListUserRel(
		long commercePriceListUserRelId,
		long commercePriceListQualificationTypeRelId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserRelService.updateCommercePriceListUserRel(commercePriceListUserRelId,
			commercePriceListQualificationTypeRelId, serviceContext);
	}

	@Override
	public CommercePriceListUserRelService getWrappedService() {
		return _commercePriceListUserRelService;
	}

	@Override
	public void setWrappedService(
		CommercePriceListUserRelService commercePriceListUserRelService) {
		_commercePriceListUserRelService = commercePriceListUserRelService;
	}

	private CommercePriceListUserRelService _commercePriceListUserRelService;
}