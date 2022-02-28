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

package com.liferay.commerce.term.service.impl;

import com.liferay.commerce.term.constants.CommerceTermEntryActionKeys;
import com.liferay.commerce.term.constants.CommerceTermEntryConstants;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.base.CommerceTermEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceTermEntry"
	},
	service = AopService.class
)
public class CommerceTermEntryServiceImpl
	extends CommerceTermEntryServiceBaseImpl {

	@Override
	public CommerceTermEntry addCommerceTermEntry(
			String externalReferenceCode, boolean active,
			Map<Locale, String> descriptionMap, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, Map<Locale, String> labelMap, String name,
			double priority, String type, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceTermEntryModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceTermEntryActionKeys.ADD_COMMERCE_TERM_ENTRY);

		return commerceTermEntryLocalService.addCommerceTermEntry(
			externalReferenceCode, getUserId(), active, descriptionMap,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, labelMap, name, priority, type, typeSettings,
			serviceContext);
	}

	@Override
	public CommerceTermEntry deleteCommerceTermEntry(long commerceTermEntryId)
		throws PortalException {

		_commerceTermEntryModelResourcePermission.check(
			getPermissionChecker(), commerceTermEntryId, ActionKeys.DELETE);

		return commerceTermEntryLocalService.deleteCommerceTermEntry(
			commerceTermEntryId);
	}

	@Override
	public CommerceTermEntry fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		CommerceTermEntry commerceTermEntry =
			commerceTermEntryLocalService.
				fetchCommerceTermEntryByExternalReferenceCode(
					companyId, externalReferenceCode);

		if (commerceTermEntry != null) {
			_commerceTermEntryModelResourcePermission.check(
				getPermissionChecker(), commerceTermEntry, ActionKeys.VIEW);
		}

		return commerceTermEntry;
	}

	@Override
	public CommerceTermEntry fetchCommerceTermEntry(long commerceTermEntryId)
		throws PortalException {

		CommerceTermEntry commerceTermEntry =
			commerceTermEntryLocalService.fetchCommerceTermEntry(
				commerceTermEntryId);

		if (commerceTermEntry != null) {
			_commerceTermEntryModelResourcePermission.check(
				getPermissionChecker(), commerceTermEntry, ActionKeys.VIEW);
		}

		return commerceTermEntry;
	}

	@Override
	public List<CommerceTermEntry> getCommerceTermEntries(
			long groupId, long companyId, String type)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceTermEntryActionKeys.VIEW_COMMERCE_TERM_ENTRY);

		return commerceTermEntryLocalService.getCommerceTermEntries(
			companyId, type);
	}

	@Override
	public CommerceTermEntry getCommerceTermEntry(long commerceTermEntryId)
		throws PortalException {

		_commerceTermEntryModelResourcePermission.check(
			getPermissionChecker(), commerceTermEntryId, ActionKeys.VIEW);

		return commerceTermEntryLocalService.getCommerceTermEntry(
			commerceTermEntryId);
	}

	@Override
	public List<CommerceTermEntry> getPaymentCommerceTermEntries(
			long groupId, long companyId, long commerceOrderTypeId,
			long commercePaymentMethodGroupRelId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceTermEntryActionKeys.VIEW_COMMERCE_TERM_ENTRY);

		return commerceTermEntryLocalService.getPaymentCommerceTermEntries(
			companyId, commerceOrderTypeId, commercePaymentMethodGroupRelId);
	}

	@Override
	public CommerceTermEntry updateCommerceTermEntry(
			long commerceTermEntryId, boolean active,
			Map<Locale, String> descriptionMap, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, Map<Locale, String> labelMap, String name,
			double priority, String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		_commerceTermEntryModelResourcePermission.check(
			getPermissionChecker(), commerceTermEntryId, ActionKeys.UPDATE);

		return commerceTermEntryLocalService.updateCommerceTermEntry(
			getUserId(), commerceTermEntryId, active, descriptionMap,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, labelMap, name, priority, typeSettings,
			serviceContext);
	}

	@Override
	public CommerceTermEntry updateCommerceTermEntryExternalReferenceCode(
			String externalReferenceCode, long commerceTermEntryId)
		throws PortalException {

		_commerceTermEntryModelResourcePermission.check(
			getPermissionChecker(), commerceTermEntryId, ActionKeys.UPDATE);

		return commerceTermEntryLocalService.
			updateCommerceTermEntryExternalReferenceCode(
				externalReferenceCode, commerceTermEntryId);
	}

	private static volatile ModelResourcePermission<CommerceTermEntry>
		_commerceTermEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceTermEntryServiceImpl.class,
				"_commerceTermEntryModelResourcePermission",
				CommerceTermEntry.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommerceTermEntryServiceImpl.class,
				"_portletResourcePermission",
				CommerceTermEntryConstants.RESOURCE_NAME);

}