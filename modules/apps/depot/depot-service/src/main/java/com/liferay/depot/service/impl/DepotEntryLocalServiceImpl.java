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
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.base.DepotEntryLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.DuplicateGroupException;
import com.liferay.portal.kernel.exception.GroupKeyException;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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

	public static final String ORGANIZATION_NAME_SUFFIX = " LFR_ORGANIZATION";

	@Override
	public DepotEntry addDepotEntry(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException {

		_validateNameMap(nameMap, LocaleUtil.getDefault());

		DepotEntry depotEntry = depotEntryPersistence.create(
			counterLocalService.increment());

		String groupKey = _getGroupKey(nameMap, serviceContext);

		_validateGroupKey(
			depotEntry.getGroupId(), depotEntry.getCompanyId(), groupKey);

		depotEntry.setUuid(serviceContext.getUuid());

		Group group = _groupLocalService.addGroup(
			serviceContext.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			DepotEntry.class.getName(), depotEntry.getDepotEntryId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, descriptionMap,
			GroupConstants.TYPE_DEPOT, false,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, false,
			true, serviceContext);

		group.setGroupKey(groupKey);

		group = _groupLocalService.updateGroup(group);

		depotEntry.setGroupId(group.getGroupId());

		depotEntry.setCompanyId(serviceContext.getCompanyId());
		depotEntry.setUserId(serviceContext.getUserId());

		return depotEntryPersistence.update(depotEntry);
	}

	@Override
	public DepotEntry updateDepotEntry(
			long depotEntryId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException {

		DepotEntry depotEntry = getDepotEntry(depotEntryId);

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

		_validateGroupKeyChange(
			group.getGroupId(), nameMap,
			currentTypeSettingsProperties.toString());

		Locale locale = LocaleUtil.fromLanguageId(
			currentTypeSettingsProperties.getProperty("languageId"));

		Optional<String> defaultNameOptional = _getDefaultNameOptional(
			nameMap, locale);

		defaultNameOptional.ifPresent(
			defaultName -> nameMap.put(locale, defaultName));

		_groupLocalService.updateGroup(
			depotEntry.getGroupId(), group.getParentGroupId(), nameMap,
			descriptionMap, group.getType(), group.isManualMembership(),
			group.getMembershipRestriction(), group.getFriendlyURL(),
			group.isInheritContent(), group.isActive(), serviceContext);

		_groupLocalService.updateGroup(
			group.getGroupId(), currentTypeSettingsProperties.toString());

		return depotEntryPersistence.update(depotEntry);
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

	private String _getGroupKey(
			Map<Locale, String> nameMap, ServiceContext serviceContext)
		throws PortalException {

		if (nameMap == null) {
			return StringPool.BLANK;
		}

		String groupKey = nameMap.get(LocaleUtil.getDefault());

		if (Validator.isNull(groupKey)) {
			User user = _userLocalService.getUser(serviceContext.getUserId());

			Locale userLocale = user.getLocale();

			if (userLocale != null) {
				groupKey = nameMap.get(userLocale);
			}
		}

		if (Validator.isNull(groupKey)) {
			Locale mostRelevantLocale = LocaleUtil.getMostRelevantLocale();

			if (mostRelevantLocale != null) {
				groupKey = nameMap.get(mostRelevantLocale);
			}
		}

		if (Validator.isNull(groupKey)) {
			groupKey = nameMap.get(LocaleUtil.US);
		}

		return groupKey;
	}

	private void _validateGroupKey(
			long groupId, long companyId, String groupKey)
		throws PortalException {

		int groupKeyMaxLength = ModelHintsUtil.getMaxLength(
			Group.class.getName(), "groupKey");

		if (Validator.isNull(groupKey) || Validator.isNumber(groupKey) ||
			groupKey.contains(StringPool.STAR) ||
			groupKey.contains(ORGANIZATION_NAME_SUFFIX) ||
			(groupKey.length() > groupKeyMaxLength)) {

			throw new GroupKeyException();
		}

		try {
			Group group = _groupLocalService.getGroup(companyId, groupKey);

			if ((groupId <= 0) || (group.getGroupId() != groupId)) {
				throw new DuplicateGroupException("{groupId=" + groupId + "}");
			}
		}
		catch (NoSuchGroupException nsge) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsge, nsge);
			}
		}

		try {
			Company company = _companyLocalService.getCompany(companyId);

			if (groupKey.equals(company.getName())) {
				throw new DuplicateGroupException();
			}
		}
		catch (NoSuchCompanyException nsce) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsce, nsce);
			}
		}
	}

	private void _validateGroupKeyChange(
			long groupId, Map<Locale, String> nameMap, String typeSettings)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		if (!Objects.equals(group.getClassName(), DepotEntry.class.getName())) {
			return;
		}

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		typeSettingsProperties.fastLoad(typeSettings);

		String defaultLanguageId = typeSettingsProperties.getProperty(
			"languageId", LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		if ((nameMap != null) &&
			Validator.isNotNull(nameMap.get(defaultLocale)) &&
			(group.getCompanyId() > 0)) {

			_validateGroupKey(
				group.getGroupId(), group.getCompanyId(),
				nameMap.get(defaultLocale));
		}
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

		if (typeSettingsProperties.isEmpty()) {
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

	private static final Log _log = LogFactoryUtil.getLog(
		DepotEntryLocalServiceImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private UserLocalService _userLocalService;

}