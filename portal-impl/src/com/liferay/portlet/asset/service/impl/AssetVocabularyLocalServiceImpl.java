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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.DuplicateVocabularyException;
import com.liferay.portlet.asset.VocabularyNameException;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.base.AssetVocabularyLocalServiceBaseImpl;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.asset.util.AssetVocabularyUtil;

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

	@Override
	public AssetVocabulary addDefaultVocabulary(long groupId)
		throws PortalException {

		Group group = groupLocalService.getGroup(groupId);

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(
			LocaleUtil.getSiteDefault(), PropsValues.ASSET_VOCABULARY_DEFAULT);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		return assetVocabularyLocalService.addVocabulary(
			defaultUserId, StringPool.BLANK, titleMap, null, StringPool.BLANK,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	@Override
	public AssetVocabulary addVocabulary(
			long userId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException {

		return assetVocabularyLocalService.addVocabulary(
			userId, StringPool.BLANK, titleMap, descriptionMap, settings,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetVocabulary addVocabulary(
			long userId, String title, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException {

		// Vocabulary

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		String name = titleMap.get(LocaleUtil.getSiteDefault());

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

	@Override
	public AssetVocabulary addVocabulary(
			long userId, String title, ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		Locale locale = LocaleUtil.getSiteDefault();

		titleMap.put(locale, title);

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(locale, StringPool.BLANK);

		return assetVocabularyLocalService.addVocabulary(
			userId, title, titleMap, descriptionMap, null, serviceContext);
	}

	@Override
	public void addVocabularyResources(
			AssetVocabulary vocabulary, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			vocabulary.getCompanyId(), vocabulary.getGroupId(),
			vocabulary.getUserId(), AssetVocabulary.class.getName(),
			vocabulary.getVocabularyId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addVocabularyResources(
			AssetVocabulary vocabulary, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			vocabulary.getCompanyId(), vocabulary.getGroupId(),
			vocabulary.getUserId(), AssetVocabulary.class.getName(),
			vocabulary.getVocabularyId(), groupPermissions, guestPermissions);
	}

	@Override
	public void deleteVocabularies(long groupId) throws PortalException {
		List<AssetVocabulary> vocabularies =
			assetVocabularyPersistence.findByGroupId(groupId);

		for (AssetVocabulary vocabulary : vocabularies) {
			assetVocabularyLocalService.deleteVocabulary(vocabulary);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE)
	public void deleteVocabulary(AssetVocabulary vocabulary)
		throws PortalException {

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

	@Override
	public void deleteVocabulary(long vocabularyId) throws PortalException {
		AssetVocabulary vocabulary =
			assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

		assetVocabularyLocalService.deleteVocabulary(vocabulary);
	}

	@Override
	public List<AssetVocabulary> getCompanyVocabularies(long companyId) {
		return assetVocabularyPersistence.findByCompanyId(companyId);
	}

	@Override
	public List<AssetVocabulary> getGroupsVocabularies(long[] groupIds) {
		return getGroupsVocabularies(groupIds, null);
	}

	@Override
	public List<AssetVocabulary> getGroupsVocabularies(
		long[] groupIds, String className) {

		return getGroupsVocabularies(
			groupIds, className, AssetCategoryConstants.ALL_CLASS_TYPE_PK);
	}

	@Override
	public List<AssetVocabulary> getGroupsVocabularies(
		long[] groupIds, String className, long classTypePK) {

		List<AssetVocabulary> vocabularies =
			assetVocabularyPersistence.findByGroupId(groupIds);

		if (Validator.isNull(className)) {
			return vocabularies;
		}

		return AssetUtil.filterVocabularies(
			vocabularies, className, classTypePK);
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(long groupId)
		throws PortalException {

		return getGroupVocabularies(groupId, true);
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(
			long groupId, boolean addDefaultVocabulary)
		throws PortalException {

		List<AssetVocabulary> vocabularies =
			assetVocabularyPersistence.findByGroupId(groupId);

		if (!vocabularies.isEmpty() || !addDefaultVocabulary) {
			return vocabularies;
		}

		AssetVocabulary vocabulary = addDefaultVocabulary(groupId);

		vocabularies = new ArrayList<AssetVocabulary>();

		vocabularies.add(vocabulary);

		return vocabularies;
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(
		long groupId, String name, int start, int end, OrderByComparator obc) {

		return assetVocabularyFinder.findByG_N(groupId, name, start, end, obc);
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(long[] groupIds) {
		return assetVocabularyPersistence.findByGroupId(groupIds);
	}

	@Override
	public int getGroupVocabulariesCount(long[] groupIds) {
		return assetVocabularyPersistence.countByGroupId(groupIds);
	}

	@Override
	public AssetVocabulary getGroupVocabulary(long groupId, String name)
		throws PortalException {

		return assetVocabularyPersistence.findByG_N(groupId, name);
	}

	@Override
	public List<AssetVocabulary> getVocabularies(long[] vocabularyIds)
		throws PortalException {

		List<AssetVocabulary> vocabularies = new ArrayList<AssetVocabulary>();

		for (long vocabularyId : vocabularyIds) {
			AssetVocabulary vocabulary = getVocabulary(vocabularyId);

			vocabularies.add(vocabulary);
		}

		return vocabularies;
	}

	@Override
	public AssetVocabulary getVocabulary(long vocabularyId)
		throws PortalException {

		return assetVocabularyPersistence.findByPrimaryKey(vocabularyId);
	}

	@Override
	public BaseModelSearchResult<AssetVocabulary> searchVocabularies(
			long companyId, long groupId, String title, int start, int end)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, title, start, end);

		return searchVocabularies(searchContext);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	@Override
	public AssetVocabulary updateVocabulary(
			long vocabularyId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException {

		return assetVocabularyLocalService.updateVocabulary(
			vocabularyId, StringPool.BLANK, titleMap, descriptionMap, settings,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetVocabulary updateVocabulary(
			long vocabularyId, String title, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException {

		long groupId = serviceContext.getScopeGroupId();
		String name = titleMap.get(LocaleUtil.getSiteDefault());

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

	protected SearchContext buildSearchContext(
		long companyId, long groupId, String title, int start, int end) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(Field.TITLE, title);
		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[]{groupId});
		searchContext.setKeywords(title);
		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected boolean hasVocabulary(long groupId, String name) {
		if (assetVocabularyPersistence.countByG_N(groupId, name) == 0) {
			return false;
		}
		else {
			return true;
		}
	}

	protected BaseModelSearchResult<AssetVocabulary> searchVocabularies(
			SearchContext searchContext)
		throws PortalException {

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			AssetVocabulary.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<AssetVocabulary> vocabularies =
				AssetVocabularyUtil.getVocabularies(hits);

			if (vocabularies != null) {
				return new BaseModelSearchResult<AssetVocabulary>(
					vocabularies, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected void validate(long groupId, String name) throws PortalException {
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