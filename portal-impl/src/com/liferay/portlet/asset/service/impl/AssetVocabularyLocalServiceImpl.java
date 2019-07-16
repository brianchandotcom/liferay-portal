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

import com.liferay.asset.kernel.exception.DuplicateVocabularyException;
import com.liferay.asset.kernel.exception.VocabularyNameException;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.service.base.AssetVocabularyLocalServiceBaseImpl;
import com.liferay.portlet.asset.util.AssetUtil;

import java.util.ArrayList;
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

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(
			LocaleUtil.getSiteDefault(), PropsValues.ASSET_VOCABULARY_DEFAULT);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		return assetVocabularyLocalService.addVocabulary(
			defaultUserId, groupId, StringPool.BLANK, titleMap, null,
			StringPool.BLANK, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetVocabulary addVocabulary(
			long userId, long groupId, String title,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String settings, ServiceContext serviceContext)
		throws PortalException {

		// Vocabulary

		User user = userLocalService.getUser(userId);

		String name = titleMap.get(LocaleUtil.getSiteDefault());

		validate(groupId, name);

		long vocabularyId = counterLocalService.increment();

		AssetVocabulary vocabulary = assetVocabularyPersistence.create(
			vocabularyId);

		vocabulary.setUuid(serviceContext.getUuid());
		vocabulary.setGroupId(groupId);
		vocabulary.setCompanyId(user.getCompanyId());
		vocabulary.setUserId(user.getUserId());
		vocabulary.setUserName(user.getFullName());
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
				vocabulary, serviceContext.getModelPermissions());
		}

		return vocabulary;
	}

	@Override
	public AssetVocabulary addVocabulary(
			long userId, long groupId, String title,
			ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> titleMap = new HashMap<>();

		Locale locale = LocaleUtil.getSiteDefault();

		titleMap.put(locale, title);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(locale, StringPool.BLANK);

		return assetVocabularyLocalService.addVocabulary(
			userId, groupId, title, titleMap, descriptionMap, null,
			serviceContext);
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
			AssetVocabulary vocabulary, ModelPermissions modelPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			vocabulary.getCompanyId(), vocabulary.getGroupId(),
			vocabulary.getUserId(), AssetVocabulary.class.getName(),
			vocabulary.getVocabularyId(), modelPermissions);
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
		type = SystemEventConstants.TYPE_DELETE
	)
	public AssetVocabulary deleteVocabulary(AssetVocabulary vocabulary)
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

		return vocabulary;
	}

	@Override
	public void deleteVocabulary(long vocabularyId) throws PortalException {
		AssetVocabulary vocabulary =
			assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

		assetVocabularyLocalService.deleteVocabulary(vocabulary);
	}

	@Override
	public AssetVocabulary fetchGroupVocabulary(long groupId, String name) {
		return assetVocabularyPersistence.fetchByG_N(groupId, name);
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

		vocabularies = new ArrayList<>();

		vocabularies.add(vocabulary);

		return vocabularies;
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(
		long groupId, String name, int start, int end,
		OrderByComparator<AssetVocabulary> obc) {

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
	public List<AssetVocabulary> getVocabularies(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<AssetVocabulary> vocabularies = new ArrayList<>(documents.size());

		for (Document document : documents) {
			long vocabularyId = GetterUtil.getLong(
				document.get(Field.ASSET_VOCABULARY_ID));

			AssetVocabulary vocabulary = fetchAssetVocabulary(vocabularyId);

			if (vocabulary == null) {
				vocabularies = null;

				Indexer<AssetVocabulary> indexer =
					IndexerRegistryUtil.getIndexer(AssetVocabulary.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (vocabularies != null) {
				vocabularies.add(vocabulary);
			}
		}

		return vocabularies;
	}

	@Override
	public List<AssetVocabulary> getVocabularies(long[] vocabularyIds)
		throws PortalException {

		List<AssetVocabulary> vocabularies = new ArrayList<>();

		for (long vocabularyId : vocabularyIds) {
			vocabularies.add(getVocabulary(vocabularyId));
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

		return searchVocabularies(companyId, groupId, title, start, end, null);
	}

	@Override
	public BaseModelSearchResult<AssetVocabulary> searchVocabularies(
			long companyId, long groupId, String title, int start, int end,
			Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, title, start, end, sort);

		return searchVocabularies(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetVocabulary updateVocabulary(
			long vocabularyId, String title, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException {

		String name = titleMap.get(LocaleUtil.getSiteDefault());

		AssetVocabulary vocabulary =
			assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

		String vocabularyName = vocabulary.getName();

		if (!vocabularyName.equals(name)) {
			validate(vocabulary.getGroupId(), name);
		}

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
		long companyId, long groupId, String title, int start, int end,
		Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(Field.TITLE, title);
		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setKeywords(title);
		searchContext.setSorts(sort);
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

		return true;
	}

	protected BaseModelSearchResult<AssetVocabulary> searchVocabularies(
			SearchContext searchContext)
		throws PortalException {

		Indexer<AssetVocabulary> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(AssetVocabulary.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<AssetVocabulary> vocabularies = getVocabularies(hits);

			if (vocabularies != null) {
				return new BaseModelSearchResult<>(
					vocabularies, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected void validate(long groupId, String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new VocabularyNameException(
				"Category vocabulary name cannot be null for group " + groupId);
		}

		if (hasVocabulary(groupId, name)) {
			throw new DuplicateVocabularyException(
				"A category vocabulary with the name " + name +
					" already exists");
		}
	}

}