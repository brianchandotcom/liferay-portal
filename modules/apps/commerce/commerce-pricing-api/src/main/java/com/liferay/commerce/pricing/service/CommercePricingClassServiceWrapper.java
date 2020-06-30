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
 * Provides a wrapper for {@link CommercePricingClassService}.
 *
 * @author Riccardo Alberti
 * @see CommercePricingClassService
 * @generated
 */
public class CommercePricingClassServiceWrapper
	implements CommercePricingClassService,
			   ServiceWrapper<CommercePricingClassService> {

	public CommercePricingClassServiceWrapper(
		CommercePricingClassService commercePricingClassService) {

		_commercePricingClassService = commercePricingClassService;
	}

	@Override
	public com.liferay.commerce.pricing.model.CommercePricingClass
			addCommercePricingClass(
				long userId, long groupId, String title, String description,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePricingClassService.addCommercePricingClass(
			userId, groupId, title, description, serviceContext);
	}

	@Override
	public com.liferay.commerce.pricing.model.CommercePricingClass
			deleteCommercePricingClass(long commercePricingClassId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePricingClassService.deleteCommercePricingClass(
			commercePricingClassId);
	}

	@Override
	public com.liferay.commerce.pricing.model.CommercePricingClass
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePricingClassService.fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.pricing.model.CommercePricingClass
			fetchCommercePricingClass(long commercePricingClassId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePricingClassService.fetchCommercePricingClass(
			commercePricingClassId);
	}

	@Override
	public com.liferay.commerce.pricing.model.CommercePricingClass
			getCommercePricingClass(long commercePricingClassId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePricingClassService.getCommercePricingClass(
			commercePricingClassId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.pricing.model.CommercePricingClass>
				getCommercePricingClasses(
					long companyId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.pricing.model.
							CommercePricingClass> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePricingClassService.getCommercePricingClasses(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCommercePricingClassesCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePricingClassService.getCommercePricingClassesCount(
			companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePricingClassService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.pricing.model.CommercePricingClass
			updateCommercePricingClass(
				long commercePricingClassId, long userId, long groupId,
				String title, String description,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePricingClassService.updateCommercePricingClass(
			commercePricingClassId, userId, groupId, title, description,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.pricing.model.CommercePricingClass
			upsertCommercePricingClass(
				long commercePricingClassId, long userId, long groupId,
				String title, String description, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePricingClassService.upsertCommercePricingClass(
			commercePricingClassId, userId, groupId, title, description,
			externalReferenceCode, serviceContext);
	}

	@Override
	public CommercePricingClassService getWrappedService() {
		return _commercePricingClassService;
	}

	@Override
	public void setWrappedService(
		CommercePricingClassService commercePricingClassService) {

		_commercePricingClassService = commercePricingClassService;
	}

	private CommercePricingClassService _commercePricingClassService;

}