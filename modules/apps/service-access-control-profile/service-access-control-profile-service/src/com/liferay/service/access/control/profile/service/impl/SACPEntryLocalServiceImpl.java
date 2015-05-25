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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.service.access.control.profile.constants.SACPEntryConstants;
import com.liferay.service.access.control.profile.exception.DuplicateSACPEntryNameException;
import com.liferay.service.access.control.profile.exception.SACPEntryNameException;
import com.liferay.service.access.control.profile.exception.SACPEntryTitleException;
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
			long userId, String allowedServices, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		if (name != null) {
			name = name.trim();
		}

		validate(name, titleMap);

		// Service access control profile entry

		User user = userLocalService.getUserById(userId);

		long companyId = user.getCompanyId();

		if (sacpEntryPersistence.fetchByC_N(companyId, name) != null) {
			throw new DuplicateSACPEntryNameException();
		}

		long sacpEntryId = counterLocalService.increment();

		SACPEntry sacpEntry = sacpEntryPersistence.create(sacpEntryId);

		sacpEntry.setUuid(serviceContext.getUuid());
		sacpEntry.setCompanyId(companyId);
		sacpEntry.setUserId(userId);
		sacpEntry.setUserName(user.getFullName());
		sacpEntry.setAllowedServices(allowedServices);
		sacpEntry.setName(name);
		sacpEntry.setTitleMap(titleMap);

		sacpEntryPersistence.update(sacpEntry, serviceContext);

		// Resources

		resourceLocalService.addResources(
			sacpEntry.getCompanyId(), 0, userId, SACPEntry.class.getName(),
			sacpEntry.getSacpEntryId(), false, false, false);

		return sacpEntry;
	}

	@Override
	public SACPEntry deleteSACPEntry(long sacpEntryId) throws PortalException {
		SACPEntry sacpEntry = sacpEntryPersistence.findByPrimaryKey(
			sacpEntryId);

		return deleteSACPEntry(sacpEntry);
	}

	@Override
	public SACPEntry deleteSACPEntry(SACPEntry sacpEntry)
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
	public int getCompanySACPEntriesCount(long companyId) {
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

		// Service access control profile entry

		if (name != null) {
			name = name.trim();
		}

		validate(name, titleMap);

		SACPEntry sacpEntry = sacpEntryPersistence.findByPrimaryKey(
			sacpEntryId);

		long companyId = sacpEntry.getCompanyId();

		SACPEntry duplicateEntry = sacpEntryPersistence.fetchByC_N(
			companyId, name);

		if ((duplicateEntry != null) &&
			(duplicateEntry.getSacpEntryId() != sacpEntryId)) {

			throw new DuplicateSACPEntryNameException();
		}

		Date now = new Date();

		sacpEntry.setAllowedServices(allowedServices);
		sacpEntry.setName(name);
		sacpEntry.setTitleMap(titleMap);
		sacpEntry.setModifiedDate(now);

		sacpEntry = sacpEntryPersistence.update(sacpEntry, serviceContext);

		return sacpEntry;
	}

	protected void validate(String name, Map<Locale, String> titleMap)
		throws PortalException {

		if (Validator.isNull(name) || (name.trim().length() == 0)) {
			throw new SACPEntryNameException("SACPEntry name is required");
		}

		for (int i = 0; i < name.length(); i++) {
			char character = name.charAt(i);

			if (SACPEntryConstants.NAME_ALLOWED_CHARACTERS.indexOf(
					character) < 0) {

				throw new SACPEntryNameException(
					"SACPEntry name contains disallowed character");
			}
		}

		boolean titleExists = false;

		if (titleMap != null) {
			Locale defaultLocale = LocaleUtil.getDefault();
			String defaultTitle = titleMap.get(defaultLocale);

			if ((defaultTitle != null) && (defaultTitle.trim().length() > 0)) {
				titleExists = true;
			}
		}

		if (!titleExists) {
			throw new SACPEntryTitleException("SACPEntry title is required");
		}
	}

}