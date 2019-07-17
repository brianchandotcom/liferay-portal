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

import com.liferay.asset.kernel.exception.AssetCategoryNameException;
import com.liferay.asset.kernel.exception.DuplicateCategoryException;
import com.liferay.asset.kernel.exception.InvalidAssetCategoryException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCachable;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelHintsUtil;
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
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.service.base.AssetCategoryLocalServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;
import com.liferay.portlet.asset.util.comparator.AssetCategoryLeftCategoryIdComparator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Provides the local service for accessing, adding, deleting, merging, moving,
 * and updating asset categories.
 *
 * @author Brian Wing Shun Chan
 * @author Alvaro del Castillo
 * @author Jorge Ferrer
 * @author Bruno Farache
 */
public class AssetCategoryLocalServiceImpl
	extends AssetCategoryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetCategory addCategory(
			long userId, long groupId, long parentCategoryId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			long vocabularyId, String[] categoryProperties,
			ServiceContext serviceContext)
		throws PortalException {

		// Category

		User user = userLocalService.getUser(userId);

		String name = titleMap.get(LocaleUtil.getSiteDefault());

		name = ModelHintsUtil.trimString(
			AssetCategory.class.getName(), "name", name);

		if (categoryProperties == null) {
			categoryProperties = new String[0];
		}

		validate(0, parentCategoryId, name, vocabularyId);

		if (parentCategoryId > 0) {
			assetCategoryPersistence.findByPrimaryKey(parentCategoryId);
		}

		assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

		long categoryId = counterLocalService.increment();

		AssetCategory category = assetCategoryPersistence.create(categoryId);

		category.setUuid(serviceContext.getUuid());
		category.setGroupId(groupId);
		category.setCompanyId(user.getCompanyId());
		category.setUserId(user.getUserId());
		category.setUserName(user.getFullName());
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setTitleMap(titleMap);
		category.setDescriptionMap(descriptionMap);
		category.setVocabularyId(vocabularyId);

		assetCategoryPersistence.update(category);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addCategoryResources(
				category, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addCategoryResources(
				category, serviceContext.getModelPermissions());
		}

		return category;
	}

	@Override
	public AssetCategory addCategory(
			long userId, long groupId, String title, long vocabularyId,
			ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> titleMap = new HashMap<>();

		Locale locale = LocaleUtil.getSiteDefault();

		titleMap.put(locale, title);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(locale, StringPool.BLANK);

		return assetCategoryLocalService.addCategory(
			userId, groupId, AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			titleMap, descriptionMap, vocabularyId, null, serviceContext);
	}

	@Override
	public void addCategoryResources(
			AssetCategory category, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), AssetCategory.class.getName(),
			category.getCategoryId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addCategoryResources(
			AssetCategory category, ModelPermissions modelPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), AssetCategory.class.getName(),
			category.getCategoryId(), modelPermissions);
	}

	@Override
	public void deleteCategories(List<AssetCategory> categories)
		throws PortalException {

		List<Long> rebuildTreeGroupIds = new ArrayList<>();

		for (AssetCategory category : categories) {
			if (!rebuildTreeGroupIds.contains(category.getGroupId()) &&
				(getChildCategoriesCount(category.getCategoryId()) > 0)) {

				final long groupId = category.getGroupId();

				TransactionCommitCallbackUtil.registerCallback(
					new Callable<Void>() {

						@Override
						public Void call() throws Exception {
							assetCategoryLocalService.rebuildTree(
								groupId, true);

							return null;
						}

					});

				rebuildTreeGroupIds.add(groupId);
			}

			assetCategoryLocalService.deleteCategory(category, true);
		}
	}

	@Override
	public void deleteCategories(long[] categoryIds) throws PortalException {
		List<AssetCategory> categories = new ArrayList<>();

		for (long categoryId : categoryIds) {
			AssetCategory category = assetCategoryPersistence.findByPrimaryKey(
				categoryId);

			categories.add(category);
		}

		deleteCategories(categories);
	}

	@Override
	public AssetCategory deleteCategory(AssetCategory category)
		throws PortalException {

		return assetCategoryLocalService.deleteCategory(category, false);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public AssetCategory deleteCategory(
			AssetCategory category, boolean skipRebuildTree)
		throws PortalException {

		// Categories

		List<AssetCategory> categories =
			assetCategoryPersistence.findByParentCategoryId(
				category.getCategoryId());

		for (AssetCategory curCategory : categories) {
			assetCategoryLocalService.deleteCategory(curCategory, true);
		}

		if (!categories.isEmpty() && !skipRebuildTree) {
			final long groupId = category.getGroupId();

			TransactionCommitCallbackUtil.registerCallback(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						assetCategoryLocalService.rebuildTree(groupId, true);

						return null;
					}

				});
		}

		// Category

		assetCategoryPersistence.remove(category);

		// Resources

		resourceLocalService.deleteResource(
			category.getCompanyId(), AssetCategory.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, category.getCategoryId());

		return category;
	}

	@Override
	public AssetCategory deleteCategory(long categoryId)
		throws PortalException {

		AssetCategory category = assetCategoryPersistence.findByPrimaryKey(
			categoryId);

		return assetCategoryLocalService.deleteCategory(category);
	}

	@Override
	public void deleteVocabularyCategories(long vocabularyId)
		throws PortalException {

		List<AssetCategory> categories = assetCategoryPersistence.findByP_V(
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, vocabularyId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new AssetCategoryLeftCategoryIdComparator(false));

		assetCategoryLocalService.deleteCategories(categories);
	}

	@Override
	public AssetCategory fetchCategory(long categoryId) {
		return assetCategoryPersistence.fetchByPrimaryKey(categoryId);
	}

	@Override
	public AssetCategory fetchCategory(
		long groupId, long parentCategoryId, String name, long vocabularyId) {

		return assetCategoryPersistence.fetchByG_P_N_V_First(
			groupId, parentCategoryId, name, vocabularyId, null);
	}

	@Override
	public List<AssetCategory> getCategories() {
		return assetCategoryPersistence.findAll();
	}

	@Override
	public List<AssetCategory> getCategories(Hits hits) throws PortalException {
		List<Document> documents = hits.toList();

		List<AssetCategory> categories = new ArrayList<>(documents.size());

		for (Document document : documents) {
			long categoryId = GetterUtil.getLong(
				document.get(Field.ASSET_CATEGORY_ID));

			AssetCategory category = fetchCategory(categoryId);

			if (category == null) {
				categories = null;

				Indexer<AssetCategory> indexer = IndexerRegistryUtil.getIndexer(
					AssetCategory.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (categories != null) {
				categories.add(category);
			}
		}

		return categories;
	}

	@Override
	@ThreadLocalCachable
	public List<AssetCategory> getCategories(long classNameId, long classPK) {
		return Collections.emptyList();
	}

	@Override
	public List<AssetCategory> getCategories(String className, long classPK) {
		return assetCategoryLocalService.getCategories(
			classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public AssetCategory getCategory(long categoryId) throws PortalException {
		return assetCategoryPersistence.findByPrimaryKey(categoryId);
	}

	@Override
	public AssetCategory getCategory(String uuid, long groupId)
		throws PortalException {

		return assetCategoryPersistence.findByUUID_G(uuid, groupId);
	}

	@Override
	public long[] getCategoryIds(String className, long classPK) {
		return getCategoryIds(getCategories(className, classPK));
	}

	@Override
	public String[] getCategoryNames() {
		return getCategoryNames(getCategories());
	}

	@Override
	public String[] getCategoryNames(long classNameId, long classPK) {
		return getCategoryNames(getCategories(classNameId, classPK));
	}

	@Override
	public String[] getCategoryNames(String className, long classPK) {
		return getCategoryNames(getCategories(className, classPK));
	}

	@Override
	public List<AssetCategory> getChildCategories(long parentCategoryId) {
		return assetCategoryPersistence.findByParentCategoryId(
			parentCategoryId);
	}

	@Override
	public List<AssetCategory> getChildCategories(
		long parentCategoryId, int start, int end,
		OrderByComparator<AssetCategory> obc) {

		return assetCategoryPersistence.findByParentCategoryId(
			parentCategoryId, start, end, obc);
	}

	@Override
	public int getChildCategoriesCount(long parentCategoryId) {
		return assetCategoryPersistence.countByParentCategoryId(
			parentCategoryId);
	}

	@Override
	public List<AssetCategory> getDescendantCategories(AssetCategory category) {
		return assetCategoryPersistence.getDescendants(category);
	}

	@Override
	public List<AssetCategory> getEntryCategories(long entryId) {
		return Collections.emptyList();
	}

	@Override
	public List<Long> getSubcategoryIds(long parentCategoryId) {
		AssetCategory parentAssetCategory =
			assetCategoryPersistence.fetchByPrimaryKey(parentCategoryId);

		if (parentAssetCategory == null) {
			return Collections.emptyList();
		}

		return ListUtil.toList(
			assetCategoryPersistence.getDescendants(parentAssetCategory),
			AssetCategory.CATEGORY_ID_ACCESSOR);
	}

	@Override
	public long[] getViewableCategoryIds(
			String className, long classPK, long[] categoryIds)
		throws PortalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			return categoryIds;
		}

		List<AssetCategory> oldCategories =
			assetCategoryLocalService.getCategories(className, classPK);

		for (AssetCategory category : oldCategories) {
			if (!ArrayUtil.contains(categoryIds, category.getCategoryId()) &&
				!AssetCategoryPermission.contains(
					permissionChecker, category, ActionKeys.VIEW)) {

				categoryIds = ArrayUtil.append(
					categoryIds, category.getCategoryId());
			}
		}

		return categoryIds;
	}

	@Override
	public List<AssetCategory> getVocabularyCategories(
		long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> obc) {

		return assetCategoryPersistence.findByVocabularyId(
			vocabularyId, start, end, obc);
	}

	@Override
	public List<AssetCategory> getVocabularyCategories(
		long parentCategoryId, long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> obc) {

		return assetCategoryPersistence.findByP_V(
			parentCategoryId, vocabularyId, start, end, obc);
	}

	@Override
	public int getVocabularyCategoriesCount(long vocabularyId) {
		return assetCategoryPersistence.countByVocabularyId(vocabularyId);
	}

	@Override
	public List<AssetCategory> getVocabularyRootCategories(
		long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> obc) {

		return getVocabularyCategories(
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, vocabularyId,
			start, end, obc);
	}

	@Override
	public int getVocabularyRootCategoriesCount(long vocabularyId) {
		return assetCategoryPersistence.countByP_V(
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, vocabularyId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetCategory mergeCategories(long fromCategoryId, long toCategoryId)
		throws PortalException {

		assetCategoryLocalService.deleteCategory(fromCategoryId);

		return getCategory(toCategoryId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetCategory moveCategory(
			long categoryId, long parentCategoryId, long vocabularyId,
			ServiceContext serviceContext)
		throws PortalException {

		AssetCategory category = assetCategoryPersistence.findByPrimaryKey(
			categoryId);

		validate(
			categoryId, parentCategoryId, category.getName(), vocabularyId);

		if (categoryId == parentCategoryId) {
			throw new InvalidAssetCategoryException(parentCategoryId, 2);
		}

		if (parentCategoryId > 0) {
			AssetCategory parentCategory =
				assetCategoryPersistence.findByPrimaryKey(parentCategoryId);

			List<AssetCategory> childrenCategories =
				_getNestedChildrenCategories(categoryId);

			if (childrenCategories.contains(parentCategory)) {
				throw new InvalidAssetCategoryException(categoryId, 1);
			}
		}

		if (vocabularyId != category.getVocabularyId()) {
			assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

			category.setVocabularyId(vocabularyId);

			updateChildrenVocabularyId(category, vocabularyId);
		}

		category.setParentCategoryId(parentCategoryId);

		assetCategoryPersistence.update(category);

		return category;
	}

	@Override
	public void rebuildTree(long groupId, boolean force) {
		assetCategoryPersistence.rebuildTree(groupId, force);
	}

	@Override
	public List<AssetCategory> search(
		long groupId, String name, String[] categoryProperties, int start,
		int end) {

		return assetCategoryFinder.findByG_N_P(
			groupId, name, categoryProperties, start, end);
	}

	@Override
	public BaseModelSearchResult<AssetCategory> searchCategories(
			long companyId, long groupIds, String title, long vocabularyId,
			int start, int end)
		throws PortalException {

		return searchCategories(
			companyId, new long[] {groupIds}, title, new long[] {vocabularyId},
			start, end);
	}

	@Override
	public BaseModelSearchResult<AssetCategory> searchCategories(
			long companyId, long[] groupIds, String title, long[] vocabularyIds,
			int start, int end)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupIds, title, new long[0], vocabularyIds, start, end,
			null);

		return searchCategories(searchContext);
	}

	@Override
	public BaseModelSearchResult<AssetCategory> searchCategories(
			long companyId, long[] groupIds, String title,
			long[] parentCategoryIds, long[] vocabularyIds, int start, int end)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupIds, title, parentCategoryIds, vocabularyIds, start,
			end, null);

		return searchCategories(searchContext);
	}

	@Override
	public BaseModelSearchResult<AssetCategory> searchCategories(
			long companyId, long[] groupIds, String title, long[] vocabularyIds,
			long[] parentCategoryIds, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupIds, title, parentCategoryIds, vocabularyIds, start,
			end, sort);

		return searchCategories(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetCategory updateCategory(
			long userId, long categoryId, long parentCategoryId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			long vocabularyId, String[] categoryProperties,
			ServiceContext serviceContext)
		throws PortalException {

		// Category

		String name = titleMap.get(LocaleUtil.getSiteDefault());

		name = ModelHintsUtil.trimString(
			AssetCategory.class.getName(), "name", name);

		if (categoryProperties == null) {
			categoryProperties = new String[0];
		}

		validate(categoryId, parentCategoryId, name, vocabularyId);

		if (parentCategoryId > 0) {
			assetCategoryPersistence.findByPrimaryKey(parentCategoryId);
		}

		AssetCategory category = assetCategoryPersistence.findByPrimaryKey(
			categoryId);

		if (vocabularyId != category.getVocabularyId()) {
			assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

			parentCategoryId =
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;

			category.setVocabularyId(vocabularyId);

			updateChildrenVocabularyId(category, vocabularyId);
		}

		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setTitleMap(titleMap);
		category.setDescriptionMap(descriptionMap);

		assetCategoryPersistence.update(category);

		return category;
	}

	protected SearchContext buildSearchContext(
		long companyId, long[] groupIds, String title, long[] parentCategoryIds,
		long[] vocabularyIds, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.ASSET_PARENT_CATEGORY_IDS, parentCategoryIds);
		attributes.put(Field.ASSET_VOCABULARY_IDS, vocabularyIds);
		attributes.put(Field.TITLE, title);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(groupIds);
		searchContext.setKeywords(title);
		searchContext.setSorts(sort);
		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected long[] getCategoryIds(List<AssetCategory> categories) {
		return ListUtil.toLongArray(
			categories, AssetCategory.CATEGORY_ID_ACCESSOR);
	}

	protected String[] getCategoryNames(List<AssetCategory> categories) {
		return ListUtil.toArray(categories, AssetCategory.NAME_ACCESSOR);
	}

	protected BaseModelSearchResult<AssetCategory> searchCategories(
			SearchContext searchContext)
		throws PortalException {

		Indexer<AssetCategory> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			AssetCategory.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<AssetCategory> categories = getCategories(hits);

			if (categories != null) {
				return new BaseModelSearchResult<>(
					categories, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected void updateChildrenVocabularyId(
		AssetCategory category, long vocabularyId) {

		List<AssetCategory> childrenCategories =
			assetCategoryPersistence.findByParentCategoryId(
				category.getCategoryId());

		if (!childrenCategories.isEmpty()) {
			for (AssetCategory childCategory : childrenCategories) {
				childCategory.setVocabularyId(vocabularyId);

				assetCategoryPersistence.update(childCategory);

				updateChildrenVocabularyId(childCategory, vocabularyId);
			}
		}
	}

	protected void validate(
			long categoryId, long parentCategoryId, String name,
			long vocabularyId)
		throws PortalException {

		if (Validator.isNull(name)) {
			StringBundler sb = new StringBundler(5);

			sb.append(
				"Asset category name cannot be null for key {categoryId=");
			sb.append(categoryId);
			sb.append(", vocabularyId=");
			sb.append(vocabularyId);
			sb.append("}");

			throw new AssetCategoryNameException(
				StringBundler.concat(
					"Category name cannot be null for category ", categoryId,
					" and vocabulary ", vocabularyId));
		}

		AssetCategory category = assetCategoryPersistence.fetchByP_N_V(
			parentCategoryId, name, vocabularyId);

		if ((category != null) && (category.getCategoryId() != categoryId)) {
			StringBundler sb = new StringBundler(4);

			sb.append("There is another category named ");
			sb.append(name);
			sb.append(" as a child of category ");
			sb.append(parentCategoryId);

			throw new DuplicateCategoryException(sb.toString());
		}
	}

	private List<AssetCategory> _getNestedChildrenCategories(long categoryId) {
		List<AssetCategory> categories = new ArrayList<>();

		List<AssetCategory> childrenCategories =
			assetCategoryPersistence.findByParentCategoryId(categoryId);

		if (!childrenCategories.isEmpty()) {
			for (AssetCategory childCategory : childrenCategories) {
				categories.add(childCategory);

				List<AssetCategory> nestedChildrenCategories =
					_getNestedChildrenCategories(childCategory.getCategoryId());

				if (!nestedChildrenCategories.isEmpty()) {
					categories.addAll(nestedChildrenCategories);
				}
			}
		}

		return categories;
	}

}