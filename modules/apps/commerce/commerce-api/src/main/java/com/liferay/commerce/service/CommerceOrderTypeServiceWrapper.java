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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceOrderTypeService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderTypeService
 * @generated
 */
public class CommerceOrderTypeServiceWrapper
	implements CommerceOrderTypeService,
			   ServiceWrapper<CommerceOrderTypeService> {

	public CommerceOrderTypeServiceWrapper(
		CommerceOrderTypeService commerceOrderTypeService) {

		_commerceOrderTypeService = commerceOrderTypeService;
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderType addCommerceOrderType(
			String externalReferenceCode,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			boolean active, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int displayOrder, int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeService.addCommerceOrderType(
			externalReferenceCode, nameMap, descriptionMap, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, displayOrder, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderType deleteCommerceOrderType(
			long commerceOrderTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeService.deleteCommerceOrderType(
			commerceOrderTypeId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderType
			fetchByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeService.fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderType fetchCommerceOrderType(
			long commerceOrderTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeService.fetchCommerceOrderType(
			commerceOrderTypeId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderType getCommerceOrderType(
			long commerceOrderTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeService.getCommerceOrderType(
			commerceOrderTypeId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderType>
			getCommerceOrderTypes(
				String className, long classPK, boolean active, int start,
				int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeService.getCommerceOrderTypes(
			className, classPK, active, start, end);
	}

	@Override
	public int getCommerceOrderTypesCount(
			String className, long classPK, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeService.getCommerceOrderTypesCount(
			className, classPK, active);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceOrderTypeService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderType updateCommerceOrderType(
			String externalReferenceCode, long commerceOrderTypeId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			boolean active, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int displayOrder, int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeService.updateCommerceOrderType(
			externalReferenceCode, commerceOrderTypeId, nameMap, descriptionMap,
			active, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, displayOrder,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderType
			updateCommerceOrderTypeExternalReferenceCode(
				String externalReferenceCode, long commerceOrderTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeService.
			updateCommerceOrderTypeExternalReferenceCode(
				externalReferenceCode, commerceOrderTypeId);
	}

	@Override
	public CommerceOrderTypeService getWrappedService() {
		return _commerceOrderTypeService;
	}

	@Override
	public void setWrappedService(
		CommerceOrderTypeService commerceOrderTypeService) {

		_commerceOrderTypeService = commerceOrderTypeService;
	}

	private CommerceOrderTypeService _commerceOrderTypeService;

}