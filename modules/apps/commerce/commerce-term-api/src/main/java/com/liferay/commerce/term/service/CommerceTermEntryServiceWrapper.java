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

package com.liferay.commerce.term.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceTermEntryService}.
 *
 * @author Luca Pellizzon
 * @see CommerceTermEntryService
 * @generated
 */
public class CommerceTermEntryServiceWrapper
	implements CommerceTermEntryService,
			   ServiceWrapper<CommerceTermEntryService> {

	public CommerceTermEntryServiceWrapper() {
		this(null);
	}

	public CommerceTermEntryServiceWrapper(
		CommerceTermEntryService commerceTermEntryService) {

		_commerceTermEntryService = commerceTermEntryService;
	}

	@Override
	public com.liferay.commerce.term.model.CommerceTermEntry
			addCommerceTermEntry(
				String externalReferenceCode, boolean active,
				java.util.Map<java.util.Locale, String> descriptionMap,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				java.util.Map<java.util.Locale, String> labelMap, String name,
				double priority, String type, String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryService.addCommerceTermEntry(
			externalReferenceCode, active, descriptionMap, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, labelMap,
			name, priority, type, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.commerce.term.model.CommerceTermEntry
			deleteCommerceTermEntry(long commerceTermEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryService.deleteCommerceTermEntry(
			commerceTermEntryId);
	}

	@Override
	public com.liferay.commerce.term.model.CommerceTermEntry
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryService.fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.term.model.CommerceTermEntry
			fetchCommerceTermEntry(long commerceTermEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryService.fetchCommerceTermEntry(
			commerceTermEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.term.model.CommerceTermEntry>
			getCommerceTermEntries(long groupId, long companyId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryService.getCommerceTermEntries(
			groupId, companyId, type);
	}

	@Override
	public com.liferay.commerce.term.model.CommerceTermEntry
			getCommerceTermEntry(long commerceTermEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryService.getCommerceTermEntry(
			commerceTermEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceTermEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.term.model.CommerceTermEntry>
			getPaymentCommerceTermEntries(
				long groupId, long companyId, long commerceOrderTypeId,
				long commercePaymentMethodGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryService.getPaymentCommerceTermEntries(
			groupId, companyId, commerceOrderTypeId,
			commercePaymentMethodGroupRelId);
	}

	@Override
	public com.liferay.commerce.term.model.CommerceTermEntry
			updateCommerceTermEntry(
				long commerceTermEntryId, boolean active,
				java.util.Map<java.util.Locale, String> descriptionMap,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				java.util.Map<java.util.Locale, String> labelMap, String name,
				double priority, String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryService.updateCommerceTermEntry(
			commerceTermEntryId, active, descriptionMap, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, labelMap,
			name, priority, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.commerce.term.model.CommerceTermEntry
			updateCommerceTermEntryExternalReferenceCode(
				String externalReferenceCode, long commerceTermEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermEntryService.
			updateCommerceTermEntryExternalReferenceCode(
				externalReferenceCode, commerceTermEntryId);
	}

	@Override
	public CommerceTermEntryService getWrappedService() {
		return _commerceTermEntryService;
	}

	@Override
	public void setWrappedService(
		CommerceTermEntryService commerceTermEntryService) {

		_commerceTermEntryService = commerceTermEntryService;
	}

	private CommerceTermEntryService _commerceTermEntryService;

}