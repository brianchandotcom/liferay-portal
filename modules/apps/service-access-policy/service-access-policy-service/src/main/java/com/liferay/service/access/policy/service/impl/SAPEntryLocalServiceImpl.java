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

package com.liferay.service.access.policy.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
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
import com.liferay.service.access.policy.configuration.SAPConfiguration;
import com.liferay.service.access.policy.constants.SAPConstants;
import com.liferay.service.access.policy.exception.DuplicateSAPEntryNameException;
import com.liferay.service.access.policy.exception.RequiredSAPEntryException;
import com.liferay.service.access.policy.exception.SAPEntryNameException;
import com.liferay.service.access.policy.exception.SAPEntryTitleException;
import com.liferay.service.access.policy.model.SAPEntry;
import com.liferay.service.access.policy.model.SAPEntryConstants;
import com.liferay.service.access.policy.service.base.SAPEntryLocalServiceBaseImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class SAPEntryLocalServiceImpl extends SAPEntryLocalServiceBaseImpl {

	@Override
	public SAPEntry addSAPEntry(
			long userId, String allowedServiceSignatures,
			boolean defaultSAPEntry, boolean enabled, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		// Service access policy entry

		User user = userPersistence.findByPrimaryKey(userId);
		allowedServiceSignatures = normalizeServiceSignatures(
			allowedServiceSignatures);
		name = StringUtil.trim(name);

		validate(name, titleMap);

		if (sapEntryPersistence.fetchByC_N(user.getCompanyId(), name) != null) {
			throw new DuplicateSAPEntryNameException();
		}

		long sapEntryId = counterLocalService.increment();

		SAPEntry sapEntry = sapEntryPersistence.create(sapEntryId);

		sapEntry.setUuid(serviceContext.getUuid());
		sapEntry.setCompanyId(user.getCompanyId());
		sapEntry.setUserId(userId);
		sapEntry.setUserName(user.getFullName());
		sapEntry.setAllowedServiceSignatures(allowedServiceSignatures);
		sapEntry.setDefaultSAPEntry(defaultSAPEntry);
		sapEntry.setEnabled(enabled);
		sapEntry.setName(name);
		sapEntry.setTitleMap(titleMap);

		sapEntryPersistence.update(sapEntry, serviceContext);

		// Resources

		resourceLocalService.addResources(
			sapEntry.getCompanyId(), 0, userId, SAPEntry.class.getName(),
			sapEntry.getSapEntryId(), false, false, false);

		return sapEntry;
	}

	@Override
	public void checkSystemSAPEntries(long companyId) throws PortalException {
		SAPConfiguration sapConfiguration =
			configurationFactory.getConfiguration(
				SAPConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, SAPConstants.SERVICE_NAME));

		SAPEntry systemDefaultSAPEntry = sapEntryPersistence.fetchByC_N(
			companyId, sapConfiguration.systemDefaultSAPEntryName());
		SAPEntry systemUserPasswordSAPEntry = sapEntryPersistence.fetchByC_N(
			companyId, sapConfiguration.systemUserPasswordSAPEntryName());

		if ((systemDefaultSAPEntry != null) &&
			(systemUserPasswordSAPEntry != null)) {

			return;
		}

		long defaultUserId = userLocalService.getDefaultUserId(companyId);
		Role guestRole = roleLocalService.getRole(
			companyId, RoleConstants.GUEST);

		if (systemDefaultSAPEntry == null) {
			Map<Locale, String> titleMap = new HashMap<>();

			titleMap.put(
				LocaleUtil.getDefault(),
				sapConfiguration.systemDefaultSAPEntryDescription());

			systemDefaultSAPEntry = addSAPEntry(
				defaultUserId,
				sapConfiguration.systemDefaultSAPEntryServiceSignatures(), true,
				true, sapConfiguration.systemDefaultSAPEntryName(), titleMap,
				new ServiceContext());

			resourcePermissionLocalService.setResourcePermissions(
				systemDefaultSAPEntry.getCompanyId(), SAPEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(systemDefaultSAPEntry.getSapEntryId()),
				guestRole.getRoleId(), new String[] {ActionKeys.VIEW});
		}

		if (systemUserPasswordSAPEntry == null) {
			Map<Locale, String> titleMap = new HashMap<>();

			titleMap.put(
				LocaleUtil.getDefault(),
				sapConfiguration.systemUserPasswordSAPEntryDescription());

			systemUserPasswordSAPEntry = addSAPEntry(
				defaultUserId,
				sapConfiguration.systemUserPasswordSAPEntryServiceSignatures(),
				false, true, sapConfiguration.systemUserPasswordSAPEntryName(),
				titleMap, new ServiceContext());

			resourcePermissionLocalService.setResourcePermissions(
				systemUserPasswordSAPEntry.getCompanyId(),
				SAPEntry.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(systemUserPasswordSAPEntry.getSapEntryId()),
				guestRole.getRoleId(), new String[] {ActionKeys.VIEW});
		}
	}

	@Override
	public SAPEntry deleteSAPEntry(long sapEntryId) throws PortalException {
		SAPEntry sapEntry = sapEntryPersistence.findByPrimaryKey(sapEntryId);

		return deleteSAPEntry(sapEntry);
	}

	@Override
	public SAPEntry deleteSAPEntry(SAPEntry sapEntry) throws PortalException {
		if (sapEntry.isSystem() && !CompanyThreadLocal.isDeleteInProcess()) {
			throw new RequiredSAPEntryException();
		}

		sapEntry = super.deleteSAPEntry(sapEntry);

		resourceLocalService.deleteResource(
			sapEntry.getCompanyId(), SAPEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, sapEntry.getSapEntryId());

		return sapEntry;
	}

	@Override
	public SAPEntry fetchSAPEntry(long companyId, String name)
		throws PortalException {

		return sapEntryPersistence.fetchByC_N(companyId, name);
	}

	@Override
	public List<SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end) {

		return sapEntryPersistence.findByCompanyId(companyId, start, end);
	}

	@Override
	public List<SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end, OrderByComparator<SAPEntry> obc) {

		return sapEntryPersistence.findByCompanyId(companyId, start, end, obc);
	}

	@Override
	public int getCompanySAPEntriesCount(long companyId) {
		return sapEntryPersistence.countByCompanyId(companyId);
	}

	@Override
	public List<SAPEntry> getDefaultSAPEntries(
		long companyId, boolean defaultSAPEntry) {

		return sapEntryPersistence.findByC_D(companyId, defaultSAPEntry);
	}

	@Override
	public SAPEntry getSAPEntry(long companyId, String name)
		throws PortalException {

		return sapEntryPersistence.findByC_N(companyId, name);
	}

	@Override
	public SAPEntry updateSAPEntry(
			long sapEntryId, String allowedServiceSignatures,
			boolean defaultSAPEntry, boolean enabled, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		SAPEntry sapEntry = sapEntryPersistence.findByPrimaryKey(sapEntryId);

		SAPEntry existingSAPEntry = sapEntryPersistence.fetchByC_N(
			sapEntry.getCompanyId(), name);

		if ((existingSAPEntry != null) &&
			(existingSAPEntry.getSapEntryId() != sapEntryId)) {

			throw new DuplicateSAPEntryNameException();
		}

		allowedServiceSignatures = normalizeServiceSignatures(
			allowedServiceSignatures);

		if (sapEntry.isSystem()) {
			defaultSAPEntry = sapEntry.getDefaultSAPEntry();
			name = sapEntry.getName();
		}

		name = StringUtil.trim(name);

		validate(name, titleMap);

		sapEntry.setAllowedServiceSignatures(allowedServiceSignatures);
		sapEntry.setDefaultSAPEntry(defaultSAPEntry);
		sapEntry.setEnabled(enabled);
		sapEntry.setName(name);
		sapEntry.setTitleMap(titleMap);

		sapEntry = sapEntryPersistence.update(sapEntry, serviceContext);

		return sapEntry;
	}

	protected String normalizeServiceSignatures(String serviceSignatures) {
		String[] serviceSignaturesArray = serviceSignatures.split(
			StringPool.NEW_LINE);

		Set<String> sortedServiceSignatures = new TreeSet<>();

		for (String serviceSignature : serviceSignaturesArray) {
			String[] serviceSignatureArray = serviceSignature.split(
				StringPool.POUND);

			StringBundler sb = new StringBundler(
				serviceSignatureArray.length * 2);

			boolean empty = true;

			for (int i = 0; i < serviceSignatureArray.length; i++) {
				serviceSignatureArray[i] = StringUtil.trim(
					serviceSignatureArray[i]);

				if (serviceSignatureArray[i].length() > 0) {
					empty = false;
				}

				sb.append(serviceSignatureArray[i]);
				sb.append(StringPool.POUND);
			}

			if (!empty) {
				sb.setIndex(sb.index() - 1);

				sortedServiceSignatures.add(sb.toString());
			}
		}

		StringBundler sb = new StringBundler(
			sortedServiceSignatures.size() * 2);

		for (String sortedServiceSignature : sortedServiceSignatures) {
			sb.append(sortedServiceSignature);
			sb.append(StringPool.NEW_LINE);
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	protected void validate(String name, Map<Locale, String> titleMap)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new SAPEntryNameException();
		}

		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);

			if (SAPEntryConstants.NAME_ALLOWED_CHARACTERS.indexOf(c) < 0) {
				throw new SAPEntryNameException("Invalid character " + c);
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
			throw new SAPEntryTitleException();
		}
	}

	@ServiceReference(type = ConfigurationFactory.class)
	protected ConfigurationFactory configurationFactory;

}