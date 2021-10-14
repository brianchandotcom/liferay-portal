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

package com.liferay.commerce.order.rule.service.impl;

import com.liferay.commerce.order.rule.constants.COREntryActionKeys;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.service.base.COREntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=COREntry"
	},
	service = AopService.class
)
public class COREntryServiceImpl extends COREntryServiceBaseImpl {

	@Override
	public COREntry addCOREntry(
			String externalReferenceCode, boolean active, String description,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String name, int priority, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_corEntryModelResourcePermission.getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null, COREntryActionKeys.ADD_COR_ENTRY);

		return corEntryLocalService.addCOREntry(
			externalReferenceCode, getUserId(), active, description,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, name, priority, type, typeSettings, serviceContext);
	}

	@Override
	public COREntry deleteCOREntry(long corEntryId) throws PortalException {
		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.DELETE);

		return corEntryLocalService.deleteCOREntry(corEntryId);
	}

	@Override
	public COREntry fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		COREntry corEntry =
			corEntryLocalService.fetchCOREntryByExternalReferenceCode(
				companyId, externalReferenceCode);

		if (corEntry != null) {
			_corEntryModelResourcePermission.check(
				getPermissionChecker(), corEntry, ActionKeys.VIEW);
		}

		return corEntry;
	}

	@Override
	public COREntry fetchCOREntry(long corEntryId) throws PortalException {
		COREntry corEntry = corEntryLocalService.fetchCOREntry(corEntryId);

		if (corEntry != null) {
			_corEntryModelResourcePermission.check(
				getPermissionChecker(), corEntry, ActionKeys.VIEW);
		}

		return corEntry;
	}

	@Override
	public List<COREntry> getCOREntries(
			long companyId, boolean active, int start, int end)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_corEntryModelResourcePermission.getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null, COREntryActionKeys.VIEW_COR_ENTRIES);

		return corEntryLocalService.getCOREntries(
			companyId, active, start, end);
	}

	@Override
	public List<COREntry> getCOREntries(
			long companyId, boolean active, String type, int start, int end)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_corEntryModelResourcePermission.getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null, COREntryActionKeys.VIEW_COR_ENTRIES);

		return corEntryLocalService.getCOREntries(
			companyId, active, type, start, end);
	}

	@Override
	public List<COREntry> getCOREntries(
			long companyId, String type, int start, int end)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_corEntryModelResourcePermission.getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null, COREntryActionKeys.VIEW_COR_ENTRIES);

		return corEntryLocalService.getCOREntries(companyId, type, start, end);
	}

	@Override
	public COREntry getCOREntry(long corEntryId) throws PortalException {
		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.VIEW);

		return corEntryLocalService.getCOREntry(corEntryId);
	}

	@Override
	public COREntry updateCOREntry(
			long corEntryId, boolean active, String description,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String name, int priority, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.UPDATE);

		return corEntryLocalService.updateCOREntry(
			getUserId(), corEntryId, active, description, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, name,
			priority, typeSettings, serviceContext);
	}

	@Override
	public COREntry updateCOREntryExternalReferenceCode(
			String externalReferenceCode, long corEntryId)
		throws PortalException {

		_corEntryModelResourcePermission.check(
			getPermissionChecker(), corEntryId, ActionKeys.UPDATE);

		return corEntryLocalService.updateCOREntryExternalReferenceCode(
			externalReferenceCode, corEntryId);
	}

	private static volatile ModelResourcePermission<COREntry>
		_corEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				COREntryServiceImpl.class, "_corEntryModelResourcePermission",
				COREntry.class);

}