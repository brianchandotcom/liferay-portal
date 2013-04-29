/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.DuplicateVocabularyException;
import com.liferay.portlet.asset.VocabularyNameException;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.base.AssetVocabularyLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service for accessing, adding, deleting, and updating
 * asset vocabularies.
 *
 * @author Alvaro del Castillo
 * @author Eduardo Lundgren
 * @author Jorge Ferrer
 * @author Juan Fernández
 */
public class AssetVocabularyLocalServiceImpl
	extends AssetVocabularyLocalServiceBaseImpl {

	/**
	 * @deprecated As of 6.1.0
	 */
	public AssetVocabulary addVocabulary(
			long userId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addVocabulary(
			userId, StringPool.BLANK, titleMap, descriptionMap, settings,
			serviceContext);
	}

	public AssetVocabulary addVocabulary(
			long userId, String title, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Vocabulary

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		String name = titleMap.get(LocaleUtil.getDefault());

		Date now = new Date();

		validate(groupId, name);

		long vocabularyId = counterLocalService.increment();

		AssetVocabulary vocabulary = assetVocabularyPersistence.create(
			vocabularyId);

		vocabulary.setUuid(serviceContext.getUuid());
		vocabulary.setGroupId(groupId);
		vocabulary.setCompanyId(user.getCompanyId());
		vocabulary.setUserId(user.getUserId());
		vocabulary.setUserName(user.getFullName());
		vocabulary.setCreateDate(now);
		vocabulary.setModifiedDate(now);
		vocabulary.setName(name);

		if (Validator.isNotNull(title)) {
			vocabulary.setTitle(title);
		}
		else {
			vocabulary.setTitleMap(titleMap);
		}

		vocabulary.setDescriptionMap(descriptionMap);
		vocabulary.setSettings(settings);

		assetVocabularyPersistence.update(vocabulary);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addVocabularyResources(
				vocabulary, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addVocabularyResources(
				vocabulary, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		return vocabulary;
	}

	public AssetVocabulary addVocabulary(
			long userId, String title, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		Locale locale = LocaleUtil.getDefault();

		titleMap.put(locale, title);

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(locale, StringPool.BLANK);

		return addVocabulary(
			userId, title, titleMap, descriptionMap, null, serviceContext);
	}

	public void addVocabularyResources(
			AssetVocabulary vocabulary, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			vocabulary.getCompanyId(), vocabulary.getGroupId(),
			vocabulary.getUserId(), AssetVocabulary.class.getName(),
			vocabulary.getVocabularyId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	public void addVocabularyResources(
			AssetVocabulary vocabulary, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			vocabulary.getCompanyId(), vocabulary.getGroupId(),
			vocabulary.getUserId(), AssetVocabulary.class.getName(),
			vocabulary.getVocabularyId(), groupPermissions, guestPermissions);
	}

	public AssetVocabulary createDefaultVocabulary(long groupId)
		throws PortalException, SystemException {

		Group group = groupLocalService.getGroup(groupId);

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(
			LocaleUtil.getDefault(), PropsValues.ASSET_VOCABULARY_DEFAULT);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		return assetVocabularyLocalService.addVocabulary(
			defaultUserId, StringPool.BLANK, titleMap, null, StringPool.BLANK,
			serviceContext);
	}

	public void deleteVocabularies(long groupId)
		throws PortalException, SystemException {

		List<AssetVocabulary> vocabularies =
			assetVocabularyPersistence.findByGroupId(groupId);

		for (AssetVocabulary vocabulary : vocabularies) {
			deleteVocabulary(vocabulary);
		}
	}

	public void deleteVocabulary(AssetVocabulary vocabulary)
		throws PortalException, SystemException {

		// Vocabulary

		assetVocabularyPersistence.remove(vocabulary);

		// Resources

		resourceLocalService.deleteResource(
			vocabulary.getCompanyId(), AssetVocabulary.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, vocabulary.getVocabularyId());

		// Categories

		assetCategoryLocalService.deleteVocabularyCategories(
			vocabulary.getVocabularyId());
	}

	public void deleteVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		AssetVocabulary vocabulary =
			assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

		deleteVocabulary(vocabulary);
	}

	public List<AssetVocabulary> getCompanyVocabularies(long companyId)
		throws SystemException {

		return assetVocabularyPersistence.findByCompanyId(companyId);
	}

	public List<AssetVocabulary> getGroupsVocabularies(long[] groupIds)
		throws PortalException, SystemException {

		return getGroupsVocabularies(groupIds, null);
	}

	public List<AssetVocabulary> getGroupsVocabularies(
			long[] groupIds, String className)
		throws PortalException, SystemException {

		List<AssetVocabulary> vocabularies = new ArrayList<AssetVocabulary>();

		groupIds = ArrayUtil.unique(groupIds);

		for (long groupId : groupIds) {
			List<AssetVocabulary> groupVocabularies = getGroupVocabularies(
				groupId);

			if (Validator.isNull(className)) {
				vocabularies.addAll(groupVocabularies);

				continue;
			}

			for (AssetVocabulary groupVocabulary : groupVocabularies) {
				UnicodeProperties settingsProperties =
					groupVocabulary.getSettingsProperties();

				long[] selectedClassNameIds = StringUtil.split(
					settingsProperties.getProperty("selectedClassNameIds"), 0L);
				long classNameId = PortalUtil.getClassNameId(className);

				if ((selectedClassNameIds.length == 0) ||
					(selectedClassNameIds[0] == 0) ||
					ArrayUtil.contains(selectedClassNameIds, classNameId)) {

					vocabularies.add(groupVocabulary);
				}
			}
		}

		return vocabularies;
	}

	public List<AssetVocabulary> getGroupVocabularies(long groupId)
		throws PortalException, SystemException {

		return getGroupVocabularies(groupId, true);
	}

	public List<AssetVocabulary> getGroupVocabularies(
			long groupId, boolean createDefaultVocabulary)
		throws PortalException, SystemException {

		List<AssetVocabulary> vocabularies =
			assetVocabularyPersistence.findByGroupId(groupId);

		if (!vocabularies.isEmpty() || !createDefaultVocabulary) {
			return vocabularies;
		}

		Group group = groupLocalService.getGroup(groupId);

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(
			LocaleUtil.getDefault(), PropsValues.ASSET_VOCABULARY_DEFAULT);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		AssetVocabulary vocabulary = assetVocabularyLocalService.addVocabulary(
			defaultUserId, StringPool.BLANK, titleMap, null, StringPool.BLANK,
			serviceContext);

		vocabularies = new ArrayList<AssetVocabulary>();

		vocabularies.add(vocabulary);

		return vocabularies;
	}

	public List<AssetVocabulary> getGroupVocabularies(
			long groupId, String name, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return assetVocabularyFinder.findByG_N(groupId, name, start, end, obc);
	}

	public AssetVocabulary getGroupVocabulary(long groupId, String name)
		throws PortalException, SystemException {

		return assetVocabularyPersistence.findByG_N(groupId, name);
	}

	public List<AssetVocabulary> getVocabularies(long[] vocabularyIds)
		throws PortalException, SystemException {

		List<AssetVocabulary> vocabularies = new ArrayList<AssetVocabulary>();

		for (long vocabularyId : vocabularyIds) {
			AssetVocabulary vocabulary = getVocabulary(vocabularyId);

			vocabularies.add(vocabulary);
		}

		return vocabularies;
	}

	public AssetVocabulary getVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		return assetVocabularyPersistence.findByPrimaryKey(vocabularyId);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public AssetVocabulary updateVocabulary(
			long vocabularyId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return updateVocabulary(
			vocabularyId, StringPool.BLANK, titleMap, descriptionMap, settings,
			serviceContext);
	}

	public AssetVocabulary updateVocabulary(
			long vocabularyId, String title, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		long groupId = serviceContext.getScopeGroupId();
		String name = titleMap.get(LocaleUtil.getDefault());

		AssetVocabulary vocabulary =
			assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

		if (!vocabulary.getName().equals(name)) {
			validate(groupId, name);
		}

		vocabulary.setModifiedDate(new Date());
		vocabulary.setName(name);
		vocabulary.setTitleMap(titleMap);

		if (Validator.isNotNull(title)) {
			vocabulary.setTitle(title);
		}

		vocabulary.setDescriptionMap(descriptionMap);
		vocabulary.setSettings(settings);

		assetVocabularyPersistence.update(vocabulary);

		return vocabulary;
	}

	protected boolean hasVocabulary(long groupId, String name)
		throws SystemException {

		if (assetVocabularyPersistence.countByG_N(groupId, name) == 0) {
			return false;
		}
		else {
			return true;
		}
	}

	protected void validate(long groupId, String name)
		throws PortalException, SystemException {

		if (Validator.isNull(name)) {
			throw new VocabularyNameException();
		}

		if (hasVocabulary(groupId, name)) {
			throw new DuplicateVocabularyException(
				"A category vocabulary with the name " + name +
					" already exists");
		}
	}

}