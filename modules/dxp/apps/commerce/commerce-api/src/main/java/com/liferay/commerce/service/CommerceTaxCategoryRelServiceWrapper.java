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
 * Provides a wrapper for {@link CommerceTaxCategoryRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryRelService
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryRelServiceWrapper
	implements CommerceTaxCategoryRelService,
		ServiceWrapper<CommerceTaxCategoryRelService> {
	public CommerceTaxCategoryRelServiceWrapper(
		CommerceTaxCategoryRelService commerceTaxCategoryRelService) {
		_commerceTaxCategoryRelService = commerceTaxCategoryRelService;
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxCategoryRel addCommerceTaxCategoryRel(
		long commerceTaxCategoryId, java.lang.String className, long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryRelService.addCommerceTaxCategoryRel(commerceTaxCategoryId,
			className, classPK, serviceContext);
	}

	@Override
	public void deleteCommerceTaxCategoryRel(long commercetaxCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceTaxCategoryRelService.deleteCommerceTaxCategoryRel(commercetaxCategoryRelId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTaxCategoryRel> getCommerceTaxCategoryRels(
		java.lang.String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceTaxCategoryRel> orderByComparator) {
		return _commerceTaxCategoryRelService.getCommerceTaxCategoryRels(className,
			classPK, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxCategoryRelsCount(java.lang.String className,
		long classPK) {
		return _commerceTaxCategoryRelService.getCommerceTaxCategoryRelsCount(className,
			classPK);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceTaxCategoryRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceTaxCategoryRelService getWrappedService() {
		return _commerceTaxCategoryRelService;
	}

	@Override
	public void setWrappedService(
		CommerceTaxCategoryRelService commerceTaxCategoryRelService) {
		_commerceTaxCategoryRelService = commerceTaxCategoryRelService;
	}

	private CommerceTaxCategoryRelService _commerceTaxCategoryRelService;
}