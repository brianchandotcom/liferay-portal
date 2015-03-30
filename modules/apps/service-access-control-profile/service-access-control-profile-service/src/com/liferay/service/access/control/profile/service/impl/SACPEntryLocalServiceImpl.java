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
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.service.access.control.profile.exception.DuplicateSACPEntryNameException;
import com.liferay.service.access.control.profile.model.SACPEntry;
import com.liferay.service.access.control.profile.service.base.SACPEntryLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class SACPEntryLocalServiceImpl extends SACPEntryLocalServiceBaseImpl {

	@Override
	public SACPEntry addSACPEntry(
			long companyId, long userId, String allowedServices, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		if (sacpEntryPersistence.fetchByC_N(companyId, name) != null) {
			throw new DuplicateSACPEntryNameException();
		}

		User user = userLocalService.getUserById(userId);

		Date now = new Date();

		long sacpEntryId = counterLocalService.increment();

		SACPEntry sacpEntry = sacpEntryPersistence.create(sacpEntryId);

		sacpEntry.setAllowedServices(allowedServices);
		sacpEntry.setCompanyId(companyId);
		sacpEntry.setCreateDate(now);
		sacpEntry.setModifiedDate(now);
		sacpEntry.setName(name);
		sacpEntry.setTitleMap(titleMap);
		sacpEntry.setUserId(userId);
		sacpEntry.setUserName(user.getFullName());
		sacpEntry.setUuid(user.getUserUuid());

		sacpEntryPersistence.update(sacpEntry, serviceContext);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addSACPEntryResources(
				sacpEntry, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addSACPEntryResources(
				sacpEntry, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		return sacpEntry;
	}

	@Override
	public void addSACPEntryResources(
			SACPEntry sacpEntry, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			sacpEntry.getCompanyId(), 0, sacpEntry.getUserId(),
			SACPEntry.class.getName(), sacpEntry.getSacpEntryId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addSACPEntryResources(
			SACPEntry sacpEntry, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			sacpEntry.getCompanyId(), 0, sacpEntry.getUserId(),
			SACPEntry.class.getName(), sacpEntry.getSacpEntryId(),
			groupPermissions, guestPermissions);
	}

	@Override
	public SACPEntry deleteSACPEntry(long sacpEntryId) throws PortalException {
		SACPEntry sacpEntry = sacpEntryPersistence.findByPrimaryKey(
			sacpEntryId);

		return deleteSACPEntry(sacpEntry);
	}

	public SACPEntry delteSACPEntry(SACPEntry sacpEntry)
		throws PortalException {

		sacpEntry = sacpEntryPersistence.remove(sacpEntry);

		resourceLocalService.deleteResource(
			sacpEntry.getCompanyId(), SACPEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, sacpEntry.getSacpEntryId());

		return sacpEntry;
	}

	@Override
	public List<SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end) {

		return sacpEntryPersistence.findByCompanyId(companyId, start, end);
	}

	@Override
	public List<SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end, OrderByComparator<SACPEntry> obc) {

		return sacpEntryPersistence.findByCompanyId(companyId, start, end, obc);
	}

	@Override
	public int getCompanySACPEntryCount(long companyId) {
		return sacpEntryPersistence.countByCompanyId(companyId);
	}

	@Override
	public SACPEntry getSACPEntry(long companyId, String name)
		throws PortalException {

		return sacpEntryPersistence.findByC_N(companyId, name);
	}

	@Override
	public SACPEntry updateSACPEntry(
			long sacpEntryId, String allowedServices, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		SACPEntry sacpEntry = sacpEntryPersistence.findByPrimaryKey(
			sacpEntryId);

		sacpEntry.setAllowedServices(allowedServices);
		sacpEntry.setName(name);
		sacpEntry.setTitleMap(titleMap);

		sacpEntry = sacpEntryPersistence.update(sacpEntry, serviceContext);

		// Resources

		if ((serviceContext.getGroupPermissions() != null) ||
			(serviceContext.getGuestPermissions() != null)) {

			updateSACPEntryResources(
				sacpEntry, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		return sacpEntry;
	}

	@Override
	public void updateSACPEntryResources(
			SACPEntry sacpEntry, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.updateResources(
			sacpEntry.getCompanyId(), 0, SACPEntry.class.getName(),
			sacpEntry.getSacpEntryId(), groupPermissions, guestPermissions);
	}

}