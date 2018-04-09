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
	public void deleteCommerceTaxCategoryRel(java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceTaxCategoryRelService.deleteCommerceTaxCategoryRel(className,
			classPK);
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxCategoryRel fetchCommerceTaxCategoryRel(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryRelService.fetchCommerceTaxCategoryRel(className,
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
	public com.liferay.commerce.model.CommerceTaxCategoryRel updateCommerceTaxCategoryRel(
		long commerceTaxCategoryId, java.lang.String className, long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryRelService.updateCommerceTaxCategoryRel(commerceTaxCategoryId,
			className, classPK, serviceContext);
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