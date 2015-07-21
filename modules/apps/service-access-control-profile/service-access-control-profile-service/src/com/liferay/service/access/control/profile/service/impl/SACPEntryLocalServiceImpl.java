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
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.service.access.control.profile.configuration.SACPConfiguration;
import com.liferay.service.access.control.profile.constants.SACPConstants;
import com.liferay.service.access.control.profile.exception.DuplicateSACPEntryNameException;
import com.liferay.service.access.control.profile.exception.RequiredSACPEntryException;
import com.liferay.service.access.control.profile.exception.SACPEntryNameException;
import com.liferay.service.access.control.profile.exception.SACPEntryTitleException;
import com.liferay.service.access.control.profile.model.SACPEntry;
import com.liferay.service.access.control.profile.model.SACPEntryConstants;
import com.liferay.service.access.control.profile.service.base.SACPEntryLocalServiceBaseImpl;

import java.util.HashMap;
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
			long userId, String allowedServiceSignatures,
			boolean defaultSACPEntry, String name, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws PortalException {

		// Service access control profile entry

		User user = userPersistence.findByPrimaryKey(userId);
		name = StringUtil.trim(name);

		validate(name, titleMap);

		if (sacpEntryPersistence.fetchByC_N(user.getCompanyId(), name) !=
				null) {

			throw new DuplicateSACPEntryNameException();
		}

		long sacpEntryId = counterLocalService.increment();

		SACPEntry sacpEntry = sacpEntryPersistence.create(sacpEntryId);

		sacpEntry.setUuid(serviceContext.getUuid());
		sacpEntry.setCompanyId(user.getCompanyId());
		sacpEntry.setUserId(userId);
		sacpEntry.setUserName(user.getFullName());
		sacpEntry.setAllowedServiceSignatures(allowedServiceSignatures);
		sacpEntry.setDefaultSACPEntry(defaultSACPEntry);
		sacpEntry.setName(name);
		sacpEntry.setTitleMap(titleMap);

		sacpEntryPersistence.update(sacpEntry, serviceContext);

		// Resources

		resourceLocalService.addResources(
			sacpEntry.getCompanyId(), 0, userId, SACPEntry.class.getName(),
			sacpEntry.getSacpEntryId(), false, false, false);

		return sacpEntry;
	}

	public void checkDefaultSACPEntry(long companyId) throws PortalException {
		SACPConfiguration sacpConfiguration = settingsFactory.getSettings(
			SACPConfiguration.class,
			new CompanyServiceSettingsLocator(
				companyId, SACPConstants.SERVICE_NAME));

		SACPEntry applicationSACPEntry = sacpEntryPersistence.fetchByC_N(
			companyId, sacpConfiguration.defaultApplicationSACPEntryName());
		SACPEntry userSACPEntry = sacpEntryPersistence.fetchByC_N(
			companyId, sacpConfiguration.defaultUserSACPEntryName());

		if ((applicationSACPEntry != null) && (userSACPEntry != null)) {
			return;
		}

		long defaultUserId = userLocalService.getDefaultUserId(companyId);
		Role guestRole = roleLocalService.getRole(
			companyId, RoleConstants.GUEST);

		if (applicationSACPEntry == null) {
			Map<Locale, String> titleMap = new HashMap<>();

			titleMap.put(
				LocaleUtil.getDefault(),
				sacpConfiguration.defaultApplicationSACPEntryDescription());

			applicationSACPEntry = addSACPEntry(
				defaultUserId,
				sacpConfiguration.
					defaultApplicationSACPEntryServiceSignatures(),
				true, sacpConfiguration.defaultApplicationSACPEntryName(),
				titleMap, new ServiceContext());

			resourcePermissionLocalService.setResourcePermissions(
				applicationSACPEntry.getCompanyId(), SACPEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(applicationSACPEntry.getSacpEntryId()),
				guestRole.getRoleId(), new String[] {ActionKeys.VIEW});
		}

		if (userSACPEntry == null) {
			Map<Locale, String> titleMap = new HashMap<>();

			titleMap.put(
				LocaleUtil.getDefault(),
				sacpConfiguration.defaultUserSACPEntryDescription());

			userSACPEntry = addSACPEntry(
				defaultUserId,
				sacpConfiguration.defaultUserSACPEntryServiceSignatures(), true,
				sacpConfiguration.defaultUserSACPEntryName(), titleMap,
				new ServiceContext());

			resourcePermissionLocalService.setResourcePermissions(
				userSACPEntry.getCompanyId(), SACPEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(userSACPEntry.getSacpEntryId()),
				guestRole.getRoleId(), new String[] {ActionKeys.VIEW});
		}
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

		if (sacpEntry.isDefaultSACPEntry() &&
			!CompanyThreadLocal.isDeleteInProcess()) {

			throw new RequiredSACPEntryException();
		}

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
			long sacpEntryId, String allowedServiceSignatures, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		// Service access control profile entry

		SACPEntry sacpEntry = sacpEntryPersistence.findByPrimaryKey(
			sacpEntryId);

		SACPEntry existingSACPEntry = sacpEntryPersistence.fetchByC_N(
			sacpEntry.getCompanyId(), name);

		if ((existingSACPEntry != null) &&
			(existingSACPEntry.getSacpEntryId() != sacpEntryId)) {

			throw new DuplicateSACPEntryNameException();
		}

		if (sacpEntry.isDefaultSACPEntry()) {
			name = sacpEntry.getName();
		}

		name = StringUtil.trim(name);

		validate(name, titleMap);

		sacpEntry.setAllowedServiceSignatures(allowedServiceSignatures);
		sacpEntry.setName(name);
		sacpEntry.setTitleMap(titleMap);

		sacpEntry = sacpEntryPersistence.update(sacpEntry, serviceContext);

		return sacpEntry;
	}

	protected void validate(String name, Map<Locale, String> titleMap)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new SACPEntryNameException();
		}

		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);

			if (SACPEntryConstants.NAME_ALLOWED_CHARACTERS.indexOf(c) < 0) {
				throw new SACPEntryNameException("Invalid character " + c);
			}
		}

		boolean titleExists = false;

		if (titleMap != null) {
			Locale defaultLocale = LocaleUtil.getDefault();
			String defaultTitle = titleMap.get(defaultLocale);

			if (Validator.isNotNull(defaultTitle)) {
				titleExists = true;
			}
		}

		if (!titleExists) {
			throw new SACPEntryTitleException();
		}
	}

	@ServiceReference(type = SettingsFactory.class)
	protected SettingsFactory settingsFactory;

}