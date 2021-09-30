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
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
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
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.service.base.AssetVocabularyLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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

		Group group = _groupLocalService.getGroup(groupId);

		long defaultUserId = _userLocalService.getDefaultUserId(
			group.getCompanyId());

		Map<Locale, String> titleMap = new HashMap<>();

		for (Locale locale : LanguageUtil.getAvailableLocales(groupId)) {
			titleMap.put(
				locale,
				LanguageUtil.get(locale, PropsValues.ASSET_VOCABULARY_DEFAULT));
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		return assetVocabularyLocalService.addVocabulary(
			defaultUserId, groupId, StringPool.BLANK, titleMap, null,
			StringPool.BLANK, serviceContext);
	}

	@Override
	public AssetVocabulary addVocabulary(
			long userId, long groupId, String title,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String settings, int visibilityType, ServiceContext serviceContext)
		throws PortalException {

		return assetVocabularyLocalService.addVocabulary(
			userId, groupId, titleMap.get(LocaleUtil.getSiteDefault()), title,
			titleMap, descriptionMap, settings, visibilityType, serviceContext);
	}

	@Override
	public AssetVocabulary addVocabulary(
			long userId, long groupId, String title,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String settings, ServiceContext serviceContext)
		throws PortalException {

		return assetVocabularyLocalService.addVocabulary(
			userId, groupId, titleMap.get(LocaleUtil.getSiteDefault()), title,
			titleMap, descriptionMap, settings, serviceContext);
	}

	@Override
	public AssetVocabulary addVocabulary(
			long userId, long groupId, String title,
			ServiceContext serviceContext)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		return assetVocabularyLocalService.addVocabulary(
			userId, groupId, title,
			HashMapBuilder.put(
				locale, title
			).build(),
			HashMapBuilder.put(
				locale, StringPool.BLANK
			).build(),
			null, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetVocabulary addVocabulary(
			long userId, long groupId, String name, String title,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String settings, int visibilityType, ServiceContext serviceContext)
		throws PortalException {

		// Vocabulary

		User user = _userLocalService.getUser(userId);

		if (Validator.isNull(name)) {
			name = _generateVocabularyName(
				groupId, titleMap.get(LocaleUtil.getSiteDefault()));
		}

		name = _getVocabularyName(name);

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
		vocabulary.setVisibilityType(visibilityType);

		vocabulary = assetVocabularyPersistence.update(vocabulary);

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
			long userId, long groupId, String name, String title,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String settings, ServiceContext serviceContext)
		throws PortalException {

		return addVocabulary(
			userId, groupId, name, title, titleMap, descriptionMap, settings,
			AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC, serviceContext);
	}

	@Override
	public void addVocabularyResources(
			AssetVocabulary vocabulary, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		_resourceLocalService.addResources(
			vocabulary.getCompanyId(), vocabulary.getGroupId(),
			vocabulary.getUserId(), AssetVocabulary.class.getName(),
			vocabulary.getVocabularyId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addVocabularyResources(
			AssetVocabulary vocabulary, ModelPermissions modelPermissions)
		throws PortalException {

		_resourceLocalService.addModelResources(
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

		_resourceLocalService.deleteResource(
			vocabulary.getCompanyId(), AssetVocabulary.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, vocabulary.getVocabularyId());

		// Categories

		_assetCategoryLocalService.deleteVocabularyCategories(
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

		long classNameId = _classNameLocalService.getClassNameId(className);

		return ListUtil.filter(
			vocabularies,
			assetVocabulary ->
				assetVocabulary.isAssociatedToClassNameIdAndClassTypePK(
					classNameId, classTypePK));
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(long groupId)
		throws PortalException {

		return getGroupVocabularies(groupId, false);
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

		return ListUtil.fromArray(vocabulary);
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(
		long groupId, int visibilityType) {

		return assetVocabularyPersistence.findByG_V(groupId, visibilityType);
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(
		long groupId, String name, int start, int end,
		OrderByComparator<AssetVocabulary> orderByComparator) {

		return assetVocabularyFinder.findByG_N(
			groupId, name, start, end, orderByComparator);
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(long[] groupIds) {
		return assetVocabularyPersistence.findByGroupId(groupIds);
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(
		long[] groupIds, int[] visibilityTypes) {

		return assetVocabularyPersistence.findByG_V(groupIds, visibilityTypes);
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

	@Override
	public AssetVocabulary updateVocabulary(
			long vocabularyId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings)
		throws PortalException {

		return assetVocabularyLocalService.updateVocabulary(
			vocabularyId, titleMap, descriptionMap, settings,
			AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetVocabulary updateVocabulary(
			long vocabularyId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			int visibilityType)
		throws PortalException {

		AssetVocabulary vocabulary =
			assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

		vocabulary.setTitleMap(titleMap);
		vocabulary.setDescriptionMap(descriptionMap);
		vocabulary.setSettings(settings);
		vocabulary.setVisibilityType(visibilityType);

		return assetVocabularyPersistence.update(vocabulary);
	}

	@Override
	public AssetVocabulary updateVocabulary(
			long vocabularyId, String title, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException {

		return assetVocabularyLocalService.updateVocabulary(
			vocabularyId, titleMap.get(LocaleUtil.getSiteDefault()), title,
			titleMap, descriptionMap, settings, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetVocabulary updateVocabulary(
			long vocabularyId, String name, String title,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String settings, ServiceContext serviceContext)
		throws PortalException {

		AssetVocabulary vocabulary =
			assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

		name = _getVocabularyName(name);

		if (!Objects.equals(vocabulary.getName(), name)) {
			validate(vocabulary.getGroupId(), name);
		}

		vocabulary.setName(name);
		vocabulary.setTitleMap(titleMap);

		if (Validator.isNotNull(title)) {
			vocabulary.setTitle(title);
		}

		vocabulary.setDescriptionMap(descriptionMap);
		vocabulary.setSettings(settings);

		return assetVocabularyPersistence.update(vocabulary);
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

	private String _generateVocabularyName(long groupId, String name) {
		String vocabularyName = _getVocabularyName(name);

		vocabularyName = StringUtil.replace(
			vocabularyName, CharPool.SPACE, CharPool.DASH);

		String curVocabularyName = vocabularyName;

		int count = 0;

		while (true) {
			AssetVocabulary vocabulary = assetVocabularyPersistence.fetchByG_N(
				groupId, curVocabularyName);

			if (vocabulary == null) {
				return curVocabularyName;
			}

			curVocabularyName = curVocabularyName + CharPool.DASH + count++;
		}
	}

	private String _getVocabularyName(String vocabularyName) {
		if (vocabularyName != null) {
			vocabularyName = vocabularyName.trim();

			return StringUtil.toLowerCase(vocabularyName);
		}

		return StringPool.BLANK;
	}

	@BeanReference(type = AssetCategoryLocalService.class)
	private AssetCategoryLocalService _assetCategoryLocalService;

	@BeanReference(type = ClassNameLocalService.class)
	private ClassNameLocalService _classNameLocalService;

	@BeanReference(type = GroupLocalService.class)
	private GroupLocalService _groupLocalService;

	@BeanReference(type = ResourceLocalService.class)
	private ResourceLocalService _resourceLocalService;

	@BeanReference(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}