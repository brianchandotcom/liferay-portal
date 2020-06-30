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

package com.liferay.commerce.pricing.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePricingClassCPDefinitionRelService}.
 *
 * @author Riccardo Alberti
 * @see CommercePricingClassCPDefinitionRelService
 * @generated
 */
public class CommercePricingClassCPDefinitionRelServiceWrapper
	implements CommercePricingClassCPDefinitionRelService,
			   ServiceWrapper<CommercePricingClassCPDefinitionRelService> {

	public CommercePricingClassCPDefinitionRelServiceWrapper(
		CommercePricingClassCPDefinitionRelService
			commercePricingClassCPDefinitionRelService) {

		_commercePricingClassCPDefinitionRelService =
			commercePricingClassCPDefinitionRelService;
	}

	@Override
	public
		com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel
				addCommercePricingClassCPDefinitionRel(
					long commercePricingClassId, long cpDefinitionId,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePricingClassCPDefinitionRelService.
			addCommercePricingClassCPDefinitionRel(
				commercePricingClassId, cpDefinitionId, serviceContext);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel>
				getCommercePricingClassCPDefinitionRelByClassId(
					long commercePricingClassId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePricingClassCPDefinitionRelService.
			getCommercePricingClassCPDefinitionRelByClassId(
				commercePricingClassId);
	}

	@Override
	public long[] getCPDefinitionIds(long commercePricingClassId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePricingClassCPDefinitionRelService.getCPDefinitionIds(
			commercePricingClassId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePricingClassCPDefinitionRelService.
			getOSGiServiceIdentifier();
	}

	@Override
	public CommercePricingClassCPDefinitionRelService getWrappedService() {
		return _commercePricingClassCPDefinitionRelService;
	}

	@Override
	public void setWrappedService(
		CommercePricingClassCPDefinitionRelService
			commercePricingClassCPDefinitionRelService) {

		_commercePricingClassCPDefinitionRelService =
			commercePricingClassCPDefinitionRelService;
	}

	private CommercePricingClassCPDefinitionRelService
		_commercePricingClassCPDefinitionRelService;

}