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

package com.liferay.service.access.control.profile.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.service.access.control.profile.constants.SACPActionKeys;
import com.liferay.service.access.control.profile.constants.SACPConstants;
import com.liferay.service.access.control.profile.model.SACPEntry;
import com.liferay.service.access.control.profile.service.base.SACPEntryServiceBaseImpl;
import com.liferay.service.access.control.profile.service.permission.SACPEntryPermission;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class SACPEntryServiceImpl extends SACPEntryServiceBaseImpl {

	@Override
	public SACPEntry addSACPEntry(
			String allowedServiceSignatures, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		PortletPermissionUtil.check(
			getPermissionChecker(), SACPConstants.SERVICE_NAME,
			SACPActionKeys.ACTION_ADD_SACP_ENTRY);

		return sacpEntryLocalService.addSACPEntry(
			getUserId(), allowedServiceSignatures, false, name, titleMap,
			serviceContext);
	}

	@Override
	public SACPEntry deleteSACPEntry(long sacpEntryId) throws PortalException {
		SACPEntryPermission.check(
			getPermissionChecker(), sacpEntryId, ActionKeys.DELETE);

		return sacpEntryLocalService.deleteSACPEntry(sacpEntryId);
	}

	@Override
	public SACPEntry deleteSACPEntry(SACPEntry sacpEntry)
		throws PortalException {

		SACPEntryPermission.check(
			getPermissionChecker(), sacpEntry, ActionKeys.DELETE);

		return sacpEntryLocalService.deleteSACPEntry(sacpEntry);
	}

	@Override
	public List<SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end) {

		return sacpEntryPersistence.filterFindByCompanyId(
			companyId, start, end);
	}

	@Override
	public List<SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end, OrderByComparator<SACPEntry> obc) {

		return sacpEntryPersistence.filterFindByCompanyId(
			companyId, start, end, obc);
	}

	@Override
	public int getCompanySACPEntriesCount(long companyId) {
		return sacpEntryPersistence.filterCountByCompanyId(companyId);
	}

	@Override
	public SACPEntry getSACPEntry(long sacpEntryId) throws PortalException {
		SACPEntryPermission.check(
			getPermissionChecker(), sacpEntryId, ActionKeys.VIEW);

		return sacpEntryPersistence.findByPrimaryKey(sacpEntryId);
	}

	@Override
	public SACPEntry getSACPEntry(long companyId, String name)
		throws PortalException {

		SACPEntry sacpEntry = sacpEntryPersistence.findByC_N(companyId, name);

		SACPEntryPermission.check(
			getPermissionChecker(), sacpEntry, ActionKeys.VIEW);

		return sacpEntry;
	}

	@Override
	public SACPEntry updateSACPEntry(
			long sacpEntryId, String allowedServiceSignatures, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		SACPEntryPermission.check(
			getPermissionChecker(), sacpEntryId, ActionKeys.UPDATE);

		return sacpEntryLocalService.updateSACPEntry(
			sacpEntryId, allowedServiceSignatures, name, titleMap,
			serviceContext);
	}

}