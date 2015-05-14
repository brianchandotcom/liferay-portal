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
import com.liferay.service.access.control.profile.constants.SACPConstants;
import com.liferay.service.access.control.profile.model.SACPEntry;
import com.liferay.service.access.control.profile.service.base.SACPEntryServiceBaseImpl;
import com.liferay.service.access.control.profile.service.permission.SACPEntryPermission;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The implementation of the s a c p entry remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.service.access.control.profile.service.SACPEntryService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SACPEntryServiceBaseImpl
 * @see com.liferay.service.access.control.profile.service.SACPEntryServiceUtil
 */
@ProviderType
public class SACPEntryServiceImpl extends SACPEntryServiceBaseImpl {

	@Override
	public SACPEntry addSACPEntry(
			long companyId, String allowedServices, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		PortletPermissionUtil.check(
			getPermissionChecker(), SACPConstants.PORTLET_ID,
			SACPConstants.ACTION_ADD_SACP_ENTRY);

		return sacpEntryLocalService.addSACPEntry(
			getUserId(), allowedServices, name, titleMap, serviceContext);
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

		return sacpEntryLocalService.getSACPEntry(sacpEntryId);
	}

	@Override
	public SACPEntry getSACPEntry(long companyId, String name)
		throws PortalException {

		SACPEntry sacpEntry = sacpEntryLocalService.getSACPEntry(
			companyId, name);

		SACPEntryPermission.check(
			getPermissionChecker(), sacpEntry, ActionKeys.VIEW);

		return sacpEntry;
	}

	@Override
	public SACPEntry updateSACPEntry(
			long sacpEntryId, String allowedServices, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		SACPEntryPermission.check(
			getPermissionChecker(), sacpEntryId, ActionKeys.UPDATE);

		return sacpEntryLocalService.updateSACPEntry(
			sacpEntryId, allowedServices, name, titleMap, serviceContext);
	}

}