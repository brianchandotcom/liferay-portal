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

package com.liferay.friendly.url.service.impl;

import com.liferay.friendly.url.exception.DuplicateFriendlyURLEntryException;
import com.liferay.friendly.url.exception.FriendlyURLLengthException;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.model.FriendlyURLEntryMapping;
import com.liferay.friendly.url.service.base.FriendlyURLEntryLocalServiceBaseImpl;
import com.liferay.friendly.url.util.comparator.FriendlyURLEntryCreateDateComparator;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.friendly.url.model.FriendlyURLEntry",
	service = AopService.class
)
public class FriendlyURLEntryLocalServiceImpl
	extends FriendlyURLEntryLocalServiceBaseImpl {

	@Override
	public FriendlyURLEntry addFriendlyURLEntry(
			long groupId, Class<?> clazz, long classPK, String urlTitle,
			ServiceContext serviceContext)
		throws PortalException {

		return addFriendlyURLEntry(
			groupId, classNameLocalService.getClassNameId(clazz), classPK,
			urlTitle, serviceContext);
	}

	@Override
	public FriendlyURLEntry addFriendlyURLEntry(
			long groupId, long classNameId, long classPK,
			Map<String, String> urlTitleMap, ServiceContext serviceContext)
		throws PortalException {

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		return addFriendlyURLEntry(
			groupId, classNameId, classPK, defaultLanguageId, urlTitleMap,
			serviceContext);
	}

	@Override
	public FriendlyURLEntry addFriendlyURLEntry(
			long groupId, long classNameId, long classPK,
			String defaultLanguageId, Map<String, String> urlTitleMap,
			ServiceContext serviceContext)
		throws PortalException {

		validate(groupId, classNameId, classPK, urlTitleMap);

		FriendlyURLEntryMapping friendlyURLEntryMapping =
			friendlyURLEntryMappingPersistence.fetchByC_C(classNameId, classPK);

		if (friendlyURLEntryMapping == null) {
			long friendlyURLMappingId = counterLocalService.increment();

			friendlyURLEntryMapping = friendlyURLEntryMappingPersistence.create(
				friendlyURLMappingId);

			friendlyURLEntryMapping.setClassNameId(classNameId);
			friendlyURLEntryMapping.setClassPK(classPK);
		}
		else if (_containsAllURLTitles(friendlyURLEntryMapping, urlTitleMap)) {
			return friendlyURLEntryPersistence.fetchByPrimaryKey(
				friendlyURLEntryMapping.getFriendlyURLEntryId());
		}

		long friendlyURLEntryId = counterLocalService.increment();

		FriendlyURLEntry friendlyURLEntry = friendlyURLEntryPersistence.create(
			friendlyURLEntryId);

		friendlyURLEntry.setUuid(serviceContext.getUuid());

		Group group = _groupLocalService.getGroup(groupId);

		friendlyURLEntry.setCompanyId(group.getCompanyId());

		friendlyURLEntry.setGroupId(groupId);
		friendlyURLEntry.setClassNameId(classNameId);
		friendlyURLEntry.setClassPK(classPK);
		friendlyURLEntry.setDefaultLanguageId(defaultLanguageId);

		friendlyURLEntryMapping.setFriendlyURLEntryId(friendlyURLEntryId);

		friendlyURLEntryMappingPersistence.update(friendlyURLEntryMapping);

		friendlyURLEntryPersistence.update(friendlyURLEntry);

		_updateFriendlyURLEntryLocalizations(
			friendlyURLEntry, classNameId, classPK, urlTitleMap);

		return friendlyURLEntry;
	}

	@Override
	public FriendlyURLEntry addFriendlyURLEntry(
			long groupId, long classNameId, long classPK, String urlTitle,
			ServiceContext serviceContext)
		throws PortalException {

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		return addFriendlyURLEntry(
			groupId, classNameId, classPK, defaultLanguageId,
			Collections.singletonMap(defaultLanguageId, urlTitle),
			serviceContext);
	}

	@Override
	public FriendlyURLEntry deleteFriendlyURLEntry(
		FriendlyURLEntry friendlyURLEntry) {

		friendlyURLEntryLocalizationPersistence.removeByFriendlyURLEntryId(
			friendlyURLEntry.getFriendlyURLEntryId());

		FriendlyURLEntry deletedFriendlyURLEntry =
			friendlyURLEntryPersistence.remove(friendlyURLEntry);

		FriendlyURLEntryMapping friendlyURLEntryMapping =
			friendlyURLEntryMappingPersistence.fetchByC_C(
				friendlyURLEntry.getClassNameId(),
				friendlyURLEntry.getClassPK());

		if ((friendlyURLEntryMapping != null) &&
			(friendlyURLEntryMapping.getFriendlyURLEntryId() ==
				friendlyURLEntry.getFriendlyURLEntryId())) {

			friendlyURLEntry = friendlyURLEntryPersistence.fetchByG_C_C_Last(
				friendlyURLEntry.getGroupId(),
				friendlyURLEntry.getClassNameId(),
				friendlyURLEntry.getClassPK(),
				new FriendlyURLEntryCreateDateComparator());

			if (friendlyURLEntry == null) {
				friendlyURLEntryMappingPersistence.remove(
					friendlyURLEntryMapping);
			}
			else {
				friendlyURLEntryMapping.setFriendlyURLEntryId(
					friendlyURLEntry.getFriendlyURLEntryId());

				friendlyURLEntryMappingPersistence.update(
					friendlyURLEntryMapping);
			}
		}

		return deletedFriendlyURLEntry;
	}

	@Override
	public FriendlyURLEntry deleteFriendlyURLEntry(long friendlyURLEntryId)
		throws PortalException {

		return deleteFriendlyURLEntry(
			friendlyURLEntryPersistence.findByPrimaryKey(friendlyURLEntryId));
	}

	@Override
	public void deleteFriendlyURLEntry(
			long groupId, Class<?> clazz, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		List<FriendlyURLEntry> friendlyURLEntries =
			friendlyURLEntryPersistence.findByG_C_C(
				groupId, classNameId, classPK);

		for (FriendlyURLEntry friendlyURLEntry : friendlyURLEntries) {
			friendlyURLEntryLocalizationPersistence.removeByFriendlyURLEntryId(
				friendlyURLEntry.getFriendlyURLEntryId());

			friendlyURLEntryPersistence.remove(friendlyURLEntry);
		}

		friendlyURLEntryMappingPersistence.removeByC_C(classNameId, classPK);
	}

	@Override
	public void deleteGroupFriendlyURLEntries(
		final long groupId, final long classNameId) {

		ActionableDynamicQuery actionableDynamicQuery =
			getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property property = PropertyFactoryUtil.forName("classNameId");

				dynamicQuery.add(property.eq(classNameId));
			});
		actionableDynamicQuery.setGroupId(groupId);
		actionableDynamicQuery.setPerformActionMethod(
			(FriendlyURLEntry friendlyURLEntry) -> {
				friendlyURLEntryLocalizationPersistence.
					removeByFriendlyURLEntryId(
						friendlyURLEntry.getFriendlyURLEntryId());

				friendlyURLEntryPersistence.remove(friendlyURLEntry);

				FriendlyURLEntryMapping friendlyURLEntryMapping =
					friendlyURLEntryMappingPersistence.fetchByC_C(
						classNameId, friendlyURLEntry.getClassPK());

				if ((friendlyURLEntryMapping != null) &&
					(friendlyURLEntryMapping.getFriendlyURLEntryId() ==
						friendlyURLEntry.getFriendlyURLEntryId())) {

					friendlyURLEntryMappingPersistence.remove(
						friendlyURLEntryMapping);
				}
			});

		try {
			actionableDynamicQuery.performActions();
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	@Override
	public FriendlyURLEntry fetchFriendlyURLEntry(
		long groupId, Class<?> clazz, String urlTitle) {

		return fetchFriendlyURLEntry(
			groupId, classNameLocalService.getClassNameId(clazz), urlTitle);
	}

	@Override
	public FriendlyURLEntry fetchFriendlyURLEntry(
		long groupId, long classNameId, String urlTitle) {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			friendlyURLEntryLocalizationPersistence.fetchByG_C_U(
				groupId, classNameId,
				FriendlyURLNormalizerUtil.normalizeWithEncoding(urlTitle));

		if (friendlyURLEntryLocalization != null) {
			return friendlyURLEntryPersistence.fetchByPrimaryKey(
				friendlyURLEntryLocalization.getFriendlyURLEntryId());
		}

		return null;
	}

	@Override
	public FriendlyURLEntryLocalization fetchFriendlyURLEntryLocalization(
		long groupId, long classNameId, String urlTitle) {

		return friendlyURLEntryLocalizationPersistence.fetchByG_C_U(
			groupId, classNameId,
			FriendlyURLNormalizerUtil.normalizeWithEncoding(urlTitle));
	}

	@Override
	public List<FriendlyURLEntry> getFriendlyURLEntries(
		long groupId, long classNameId, long classPK) {

		return friendlyURLEntryPersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public FriendlyURLEntry getMainFriendlyURLEntry(
			Class<?> clazz, long classPK)
		throws PortalException {

		return getMainFriendlyURLEntry(
			classNameLocalService.getClassNameId(clazz), classPK);
	}

	@Override
	public FriendlyURLEntry getMainFriendlyURLEntry(
			long classNameId, long classPK)
		throws PortalException {

		FriendlyURLEntryMapping friendlyURLEntryMapping =
			friendlyURLEntryMappingPersistence.findByC_C(classNameId, classPK);

		return friendlyURLEntryPersistence.findByPrimaryKey(
			friendlyURLEntryMapping.getFriendlyURLEntryId());
	}

	@Override
	public String getUniqueUrlTitle(
		long groupId, long classNameId, long classPK, String urlTitle) {

		String normalizedUrlTitle =
			FriendlyURLNormalizerUtil.normalizeWithEncoding(urlTitle);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntryLocalization.class.getName(), "urlTitle");

		String prefix = normalizedUrlTitle;
		int endPos = urlTitle.length();

		while (prefix.length() > maxLength) {
			endPos = _getNewEndPos(urlTitle, endPos);

			prefix = FriendlyURLNormalizerUtil.normalizeWithEncoding(
				urlTitle.substring(0, endPos));
		}

		String curUrlTitle = prefix;

		for (int i = 1;; i++) {
			FriendlyURLEntryLocalization friendlyURLEntryLocalization =
				friendlyURLEntryLocalizationPersistence.fetchByG_C_U(
					groupId, classNameId, curUrlTitle);

			if ((friendlyURLEntryLocalization == null) ||
				(friendlyURLEntryLocalization.getClassPK() == classPK)) {

				break;
			}

			String suffix = StringPool.DASH + i;

			while ((prefix.length() + suffix.length()) > maxLength) {
				endPos = _getNewEndPos(urlTitle, endPos);

				prefix = FriendlyURLNormalizerUtil.normalizeWithEncoding(
					urlTitle.substring(0, endPos));
			}

			curUrlTitle = prefix + suffix;
		}

		return curUrlTitle;
	}

	@Override
	public void setMainFriendlyURLEntry(FriendlyURLEntry friendlyURLEntry) {
		FriendlyURLEntryMapping friendlyURLEntryMapping =
			friendlyURLEntryMappingPersistence.fetchByC_C(
				friendlyURLEntry.getClassNameId(),
				friendlyURLEntry.getClassPK());

		if (friendlyURLEntryMapping == null) {
			long friendlyURLEntryMappingId = counterLocalService.increment();

			friendlyURLEntryMapping = friendlyURLEntryMappingPersistence.create(
				friendlyURLEntryMappingId);

			friendlyURLEntryMapping.setClassNameId(
				friendlyURLEntry.getClassNameId());
			friendlyURLEntryMapping.setClassPK(friendlyURLEntry.getClassPK());
		}

		friendlyURLEntryMapping.setFriendlyURLEntryId(
			friendlyURLEntry.getFriendlyURLEntryId());

		friendlyURLEntryMappingPersistence.update(friendlyURLEntryMapping);
	}

	@Override
	public FriendlyURLEntry updateFriendlyURLEntry(
			long friendlyURLEntryId, long classNameId, long classPK,
			String defaultLanguageId, Map<String, String> urlTitleMap)
		throws PortalException {

		FriendlyURLEntry friendlyURLEntry =
			friendlyURLEntryPersistence.findByPrimaryKey(friendlyURLEntryId);

		validate(
			friendlyURLEntry.getGroupId(), classNameId, classPK, urlTitleMap);

		friendlyURLEntry.setClassNameId(classNameId);
		friendlyURLEntry.setClassPK(classPK);
		friendlyURLEntry.setDefaultLanguageId(defaultLanguageId);

		friendlyURLEntryPersistence.update(friendlyURLEntry);

		_updateFriendlyURLEntryLocalizations(
			friendlyURLEntry, classNameId, classPK, urlTitleMap);

		return friendlyURLEntry;
	}

	@Override
	public FriendlyURLEntryLocalization updateFriendlyURLLocalization(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {

		return friendlyURLEntryLocalizationPersistence.update(
			friendlyURLEntryLocalization);
	}

	@Override
	public FriendlyURLEntryLocalization updateFriendlyURLLocalization(
			long friendlyURLLocalizationId, String urlTitle)
		throws PortalException {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			friendlyURLEntryLocalizationPersistence.fetchByPrimaryKey(
				friendlyURLLocalizationId);

		if (friendlyURLEntryLocalization != null) {
			friendlyURLEntryLocalization.setUrlTitle(urlTitle);

			return friendlyURLEntryLocalizationPersistence.update(
				friendlyURLEntryLocalization);
		}

		return null;
	}

	@Override
	public void validate(
			long groupId, long classNameId, long classPK,
			Map<String, String> urlTitleMap)
		throws PortalException {

		for (String urlTitle : urlTitleMap.values()) {
			validate(groupId, classNameId, classPK, urlTitle);
		}
	}

	@Override
	public void validate(
			long groupId, long classNameId, long classPK, String urlTitle)
		throws PortalException {

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntryLocalization.class.getName(), "urlTitle");

		String normalizedUrlTitle =
			FriendlyURLNormalizerUtil.normalizeWithEncoding(urlTitle);

		if (normalizedUrlTitle.length() > maxLength) {
			throw new FriendlyURLLengthException(
				urlTitle + " is longer than " + maxLength);
		}

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			friendlyURLEntryLocalizationPersistence.fetchByG_C_U(
				groupId, classNameId, normalizedUrlTitle);

		if (friendlyURLEntryLocalization == null) {
			return;
		}

		if ((classPK <= 0) ||
			(friendlyURLEntryLocalization.getClassPK() != classPK)) {

			throw new DuplicateFriendlyURLEntryException(
				friendlyURLEntryLocalization.toString());
		}
	}

	@Override
	public void validate(long groupId, long classNameId, String urlTitle)
		throws PortalException {

		validate(groupId, classNameId, 0, urlTitle);
	}

	private boolean _containsAllURLTitles(
		FriendlyURLEntryMapping friendlyURLEntryMapping,
		Map<String, String> urlTitleMap) {

		Map<String, String> existUrlTitleMap = new HashMap<>();

		for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
				friendlyURLEntryLocalizationPersistence.
					findByFriendlyURLEntryId(
						friendlyURLEntryMapping.getFriendlyURLEntryId())) {

			existUrlTitleMap.put(
				friendlyURLEntryLocalization.getLanguageId(),
				friendlyURLEntryLocalization.getUrlTitle());
		}

		for (Map.Entry<String, String> entry : urlTitleMap.entrySet()) {
			String urlTitle = FriendlyURLNormalizerUtil.normalizeWithEncoding(
				entry.getValue());

			if (!urlTitle.equals(existUrlTitleMap.get(entry.getKey()))) {
				return false;
			}
		}

		return true;
	}

	private int _getNewEndPos(String urlTitle, int endPos) {
		if (!Character.isLowSurrogate(urlTitle.charAt(endPos - 1)) ||
			(endPos < 2)) {

			return endPos - 1;
		}

		if (Character.isHighSurrogate(urlTitle.charAt(endPos - 2))) {
			return endPos - 2;
		}

		return endPos - 1;
	}

	private void _updateFriendlyURLEntryLocalizations(
			FriendlyURLEntry friendlyURLEntry, long classNameId, long classPK,
			Map<String, String> urlTitleMap)
		throws PortalException {

		for (Map.Entry<String, String> entry : urlTitleMap.entrySet()) {
			String normalizedUrlTitle =
				FriendlyURLNormalizerUtil.normalizeWithEncoding(
					entry.getValue());

			if (Validator.isNull(normalizedUrlTitle)) {
				continue;
			}

			FriendlyURLEntryLocalization existingFriendlyURLEntryLocalization =
				friendlyURLEntryLocalizationPersistence.fetchByG_C_U(
					friendlyURLEntry.getGroupId(), classNameId,
					normalizedUrlTitle);

			if ((existingFriendlyURLEntryLocalization != null) &&
				(existingFriendlyURLEntryLocalization.getClassPK() ==
					classPK)) {

				String existingLanguageId =
					existingFriendlyURLEntryLocalization.getLanguageId();

				if (!existingLanguageId.equals(entry.getKey())) {
					String existingUrlTitle =
						existingFriendlyURLEntryLocalization.getUrlTitle();

					if (existingUrlTitle.equals(
							urlTitleMap.get(existingLanguageId))) {

						continue;
					}

					existingFriendlyURLEntryLocalization.setLanguageId(
						entry.getKey());
				}

				existingFriendlyURLEntryLocalization.setFriendlyURLEntryId(
					friendlyURLEntry.getFriendlyURLEntryId());

				updateFriendlyURLLocalization(
					existingFriendlyURLEntryLocalization);

				continue;
			}

			updateFriendlyURLEntryLocalization(
				friendlyURLEntry, entry.getKey(), normalizedUrlTitle);
		}
	}

	@Reference
	private GroupLocalService _groupLocalService;

}