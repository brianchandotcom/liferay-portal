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

package com.liferay.depot.service.impl;

import com.liferay.depot.exception.DepotEntryNameException;
import com.liferay.depot.internal.constants.DepotRolesConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotAppCustomizationLocalService;
import com.liferay.depot.service.base.DepotEntryLocalServiceBaseImpl;
import com.liferay.depot.service.persistence.DepotEntryGroupRelPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.depot.model.DepotEntry",
	service = AopService.class
)
public class DepotEntryLocalServiceImpl extends DepotEntryLocalServiceBaseImpl {

	@Override
	public DepotEntry addDepotEntry(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException {

		_validateNameMap(nameMap, LocaleUtil.getDefault());

		DepotEntry depotEntry = depotEntryPersistence.create(
			counterLocalService.increment());

		depotEntry.setUuid(serviceContext.getUuid());

		Group group = _groupLocalService.addGroup(
			serviceContext.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			DepotEntry.class.getName(), depotEntry.getDepotEntryId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, descriptionMap,
			GroupConstants.TYPE_DEPOT, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, false,
			true, serviceContext);

		_userLocalService.addGroupUsers(
			group.getGroupId(), new long[] {serviceContext.getUserId()});

		User user = _userLocalService.getUser(serviceContext.getUserId());

		if (!user.isDefaultUser()) {
			Role role = _roleLocalService.getRole(
				group.getCompanyId(), DepotRolesConstants.DEPOT_OWNER);

			_userGroupRoleLocalService.addUserGroupRoles(
				user.getUserId(), group.getGroupId(),
				new long[] {role.getRoleId()});

			_userLocalService.addGroupUsers(
				group.getGroupId(), new long[] {user.getUserId()});
		}

		depotEntry.setGroupId(group.getGroupId());

		depotEntry.setCompanyId(serviceContext.getCompanyId());
		depotEntry.setUserId(serviceContext.getUserId());

		depotEntry = depotEntryPersistence.update(depotEntry);

		resourceLocalService.addResources(
			serviceContext.getCompanyId(), 0, serviceContext.getUserId(),
			DepotEntry.class.getName(), depotEntry.getDepotEntryId(), false,
			false, false);

		return depotEntry;
	}

	@Override
	public DepotEntry fetchGroupDepotEntry(long groupId) {
		return depotEntryPersistence.fetchByGroupId(groupId);
	}

	@Override
	public List<DepotEntry> getGroupConnectedDepotEntries(
			long groupId, int start, int end)
		throws PortalException {

		List<DepotEntry> depotEntries = new ArrayList<>();

		List<DepotEntryGroupRel> depotEntryGroupRels =
			_depotEntryGroupRelPersistence.findByToGroupId(groupId, start, end);

		for (DepotEntryGroupRel depotEntryGroupRel : depotEntryGroupRels) {
			DepotEntry depotEntry = depotEntryLocalService.getDepotEntry(
				depotEntryGroupRel.getDepotEntryId());

			depotEntries.add(depotEntry);
		}

		return depotEntries;
	}

	@Override
	public int getGroupConnectedDepotEntriesCount(long groupId) {
		return _depotEntryGroupRelPersistence.countByToGroupId(groupId);
	}

	@Override
	public DepotEntry getGroupDepotEntry(long groupId) throws PortalException {
		return depotEntryPersistence.findByGroupId(groupId);
	}

	@Override
	public DepotEntry updateDepotEntry(
			long depotEntryId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap,
			Map<String, Boolean> depotAppCustomizationMap,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException {

		DepotEntry depotEntry = getDepotEntry(depotEntryId);

		depotEntry.setModifiedDate(serviceContext.getModifiedDate());

		depotEntry = depotEntryPersistence.update(depotEntry);

		for (Map.Entry<String, Boolean> entry :
				depotAppCustomizationMap.entrySet()) {

			_depotAppCustomizationLocalService.updateDepotAppCustomization(
				depotEntryId, entry.getValue(), entry.getKey());
		}

		_validateTypeSettingsProperties(depotEntry, typeSettingsProperties);

		Group group = _groupLocalService.getGroup(depotEntry.getGroupId());

		UnicodeProperties currentTypeSettingsProperties =
			group.getTypeSettingsProperties();

		boolean inheritLocales = GetterUtil.getBoolean(
			currentTypeSettingsProperties.getProperty("inheritLocales"), true);

		inheritLocales = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("inheritLocales"),
			inheritLocales);

		if (inheritLocales) {
			typeSettingsProperties.setProperty(
				PropsKeys.LOCALES,
				StringUtil.merge(
					LocaleUtil.toLanguageIds(
						LanguageUtil.getAvailableLocales())));
		}

		currentTypeSettingsProperties.putAll(typeSettingsProperties);

		Locale locale = LocaleUtil.fromLanguageId(
			currentTypeSettingsProperties.getProperty("languageId"));

		Optional<String> defaultNameOptional = _getDefaultNameOptional(
			nameMap, locale);

		defaultNameOptional.ifPresent(
			defaultName -> nameMap.put(locale, defaultName));

		group = _groupLocalService.updateGroup(
			depotEntry.getGroupId(), group.getParentGroupId(), nameMap,
			descriptionMap, group.getType(), group.isManualMembership(),
			group.getMembershipRestriction(), group.getFriendlyURL(),
			group.isInheritContent(), group.isActive(), serviceContext);

		_groupLocalService.updateGroup(
			group.getGroupId(), currentTypeSettingsProperties.toString());

		return depotEntry;
	}

	private Optional<String> _getDefaultNameOptional(
		Map<Locale, String> nameMap, Locale defaultLocale) {

		if (Validator.isNotNull(nameMap.get(defaultLocale))) {
			return Optional.empty();
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			defaultLocale, DepotEntryLocalServiceImpl.class);

		return Optional.of(_language.get(resourceBundle, "unnamed-repository"));
	}

	private void _validateNameMap(
			Map<Locale, String> nameMap, Locale defaultLocale)
		throws DepotEntryNameException {

		if (MapUtil.isEmpty(nameMap) ||
			Validator.isNull(nameMap.get(defaultLocale))) {

			throw new DepotEntryNameException();
		}
	}

	private void _validateTypeSettingsProperties(
			DepotEntry depotEntry, UnicodeProperties typeSettingsProperties)
		throws LocaleException {

		if (!typeSettingsProperties.containsKey("inheritLocales")) {
			return;
		}

		if (typeSettingsProperties.containsKey(PropsKeys.LOCALES) &&
			Validator.isNull(
				typeSettingsProperties.getProperty(PropsKeys.LOCALES))) {

			throw new LocaleException(
				LocaleException.TYPE_DEFAULT,
				"Must have at least one valid locale for repository " +
					depotEntry.getGroupId());
		}

		boolean inheritLocales = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("inheritLocales"));

		if (!inheritLocales &&
			!typeSettingsProperties.containsKey(PropsKeys.LOCALES)) {

			throw new LocaleException(
				LocaleException.TYPE_DEFAULT,
				"Must have at least one valid locale for repository " +
					depotEntry.getGroupId());
		}
	}

	@Reference
	private DepotAppCustomizationLocalService
		_depotAppCustomizationLocalService;

	@Reference
	private DepotEntryGroupRelPersistence _depotEntryGroupRelPersistence;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}