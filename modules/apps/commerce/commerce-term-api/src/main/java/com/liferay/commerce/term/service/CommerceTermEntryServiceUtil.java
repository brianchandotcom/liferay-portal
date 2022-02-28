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

import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for CommerceTermEntry. This utility wraps
 * <code>com.liferay.commerce.term.service.impl.CommerceTermEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommerceTermEntryService
 * @generated
 */
public class CommerceTermEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.term.service.impl.CommerceTermEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceTermEntry addCommerceTermEntry(
			String externalReferenceCode, boolean active,
			Map<java.util.Locale, String> descriptionMap, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, Map<java.util.Locale, String> labelMap,
			String name, double priority, String type, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceTermEntry(
			externalReferenceCode, active, descriptionMap, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, labelMap,
			name, priority, type, typeSettings, serviceContext);
	}

	public static CommerceTermEntry deleteCommerceTermEntry(
			long commerceTermEntryId)
		throws PortalException {

		return getService().deleteCommerceTermEntry(commerceTermEntryId);
	}

	public static CommerceTermEntry fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static CommerceTermEntry fetchCommerceTermEntry(
			long commerceTermEntryId)
		throws PortalException {

		return getService().fetchCommerceTermEntry(commerceTermEntryId);
	}

	public static List<CommerceTermEntry> getCommerceTermEntries(
			long groupId, long companyId, String type)
		throws PortalException {

		return getService().getCommerceTermEntries(groupId, companyId, type);
	}

	public static CommerceTermEntry getCommerceTermEntry(
			long commerceTermEntryId)
		throws PortalException {

		return getService().getCommerceTermEntry(commerceTermEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<CommerceTermEntry> getPaymentCommerceTermEntries(
			long groupId, long companyId, long commerceOrderTypeId,
			long commercePaymentMethodGroupRelId)
		throws PortalException {

		return getService().getPaymentCommerceTermEntries(
			groupId, companyId, commerceOrderTypeId,
			commercePaymentMethodGroupRelId);
	}

	public static CommerceTermEntry updateCommerceTermEntry(
			long commerceTermEntryId, boolean active,
			Map<java.util.Locale, String> descriptionMap, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, Map<java.util.Locale, String> labelMap,
			String name, double priority, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceTermEntry(
			commerceTermEntryId, active, descriptionMap, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, labelMap,
			name, priority, typeSettings, serviceContext);
	}

	public static CommerceTermEntry
			updateCommerceTermEntryExternalReferenceCode(
				String externalReferenceCode, long commerceTermEntryId)
		throws PortalException {

		return getService().updateCommerceTermEntryExternalReferenceCode(
			externalReferenceCode, commerceTermEntryId);
	}

	public static CommerceTermEntryService getService() {
		return _service;
	}

	private static volatile CommerceTermEntryService _service;

}