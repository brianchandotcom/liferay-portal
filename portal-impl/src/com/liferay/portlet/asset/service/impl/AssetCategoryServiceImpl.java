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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetCategoryDisplay;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.base.AssetCategoryServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;
import com.liferay.util.Autocomplete;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service for accessing, adding, deleting, moving, and
 * updating asset categories. Its methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Alvaro del Castillo
 * @author Eduardo Lundgren
 * @author Bruno Farache
 */
public class AssetCategoryServiceImpl extends AssetCategoryServiceBaseImpl {

	@Override
	public AssetCategory addCategory(
			long parentCategoryId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, long vocabularyId,
			String[] categoryProperties, ServiceContext serviceContext)
		throws PortalException, SystemException {

		AssetCategoryPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			parentCategoryId, ActionKeys.ADD_CATEGORY);

		return assetCategoryLocalService.addCategory(
			getUserId(), parentCategoryId, titleMap, descriptionMap,
			vocabularyId, categoryProperties, serviceContext);
	}

	@Override
	public AssetCategory addCategory(
			String title, long vocabularyId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		AssetCategoryPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			ActionKeys.ADD_CATEGORY);

		return assetCategoryLocalService.addCategory(
			getUserId(), title, vocabularyId, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, Replaced by {@link #deleteCategories(long[],
	 *             ServiceContext)}
	 */
	public void deleteCategories(long[] categoryIds)
		throws PortalException, SystemException {

		deleteCategories(categoryIds, null);
	}

	@Override
	public List<AssetCategory> deleteCategories(
			long[] categoryIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<AssetCategory> failedCategories = new ArrayList<AssetCategory>();

		for (long categoryId : categoryIds) {
			try {
				AssetCategoryPermission.check(
					getPermissionChecker(), categoryId, ActionKeys.DELETE);

				assetCategoryLocalService.deleteCategory(categoryId);
			}
			catch (PortalException pe) {
				if (serviceContext == null) {
					return null;
				}

				if (serviceContext.isFailOnPortalException()) {
					throw pe;
				}

				AssetCategory category =
					assetCategoryPersistence.fetchByPrimaryKey(categoryId);

				if (category == null) {
					category = assetCategoryPersistence.create(categoryId);
				}

				failedCategories.add(category);
			}
		}

		return failedCategories;
	}

	@Override
	public void deleteCategory(long categoryId)
		throws PortalException, SystemException {

		AssetCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.DELETE);

		assetCategoryLocalService.deleteCategory(categoryId);
	}

	@Override
	public List<AssetCategory> getCategories(String className, long classPK)
		throws PortalException, SystemException {

		return filterCategories(
			assetCategoryLocalService.getCategories(className, classPK));
	}

	@Override
	public AssetCategory getCategory(long categoryId)
		throws PortalException, SystemException {

		AssetCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.VIEW);

		return assetCategoryLocalService.getCategory(categoryId);
	}

	@Override
	public List<AssetCategory> getChildCategories(long parentCategoryId)
		throws PortalException, SystemException {

		return filterCategories(
			assetCategoryLocalService.getChildCategories(parentCategoryId));
	}

	@Override
	public List<AssetCategory> getChildCategories(
			long parentCategoryId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		return filterCategories(
			assetCategoryLocalService.getChildCategories(
				parentCategoryId, start, end, obc));
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #search(long[], String,
	 *             long[], int, int)}
	 */
	@Override
	public JSONArray getJSONSearch(
			long groupId, String name, long[] vocabularyIds, int start, int end)
		throws PortalException, SystemException {

		return search(new long[] {groupId}, name, vocabularyIds, start, end);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getVocabularyCategoriesDisplay(long, int, int,
	 *             OrderByComparator)}
	 */
	@Override
	public JSONObject getJSONVocabularyCategories(
			long vocabularyId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		List<AssetCategory> categories = filterCategories(
			assetCategoryLocalService.getVocabularyCategories(
				vocabularyId, start, end, obc));

		jsonObject.put("categories", toJSONArray(categories));
		jsonObject.put("total", categories.size());

		return jsonObject;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getVocabularyCategoriesDisplay(long, String, long, int, int,
	 *             OrderByComparator)}
	 */
	@Override
	public JSONObject getJSONVocabularyCategories(
			long groupId, String name, long vocabularyId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		int page = 0;

		if ((end > 0) && (start > 0)) {
			page = end / (end - start);
		}

		jsonObject.put("page", page);

		List<AssetCategory> categories;
		int total = 0;

		if (Validator.isNotNull(name)) {
			name = (CustomSQLUtil.keywords(name))[0];

			categories = getVocabularyCategories(
				groupId, name, vocabularyId, start, end, obc);
			total = getVocabularyCategoriesCount(groupId, name, vocabularyId);
		}
		else {
			categories = getVocabularyCategories(vocabularyId, start, end, obc);
			total = getVocabularyCategoriesCount(groupId, vocabularyId);
		}

		jsonObject.put("categories", toJSONArray(categories));
		jsonObject.put("total", total);

		return jsonObject;
	}

	@Override
	public List<AssetCategory> getVocabularyCategories(
			long vocabularyId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		return filterCategories(
			assetCategoryLocalService.getVocabularyCategories(
				vocabularyId, start, end, obc));
	}

	@Override
	public List<AssetCategory> getVocabularyCategories(
			long parentCategoryId, long vocabularyId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		return filterCategories(
			assetCategoryLocalService.getVocabularyCategories(
				parentCategoryId, vocabularyId, start, end, obc));
	}

	@Override
	public List<AssetCategory> getVocabularyCategories(
			long groupId, String name, long vocabularyId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (Validator.isNull(name)) {
			return assetCategoryPersistence.filterFindByG_V(
				groupId, vocabularyId, start, end, obc);
		}
		else {
			return assetCategoryPersistence.filterFindByG_LikeN_V(
				groupId, name, vocabularyId, start, end, obc);
		}
	}

	@Override
	public int getVocabularyCategoriesCount(long groupId, long vocabularyId)
		throws SystemException {

		return assetCategoryPersistence.filterCountByG_V(groupId, vocabularyId);
	}

	@Override
	public int getVocabularyCategoriesCount(
			long groupId, String name, long vocabularyId)
		throws SystemException {

		if (Validator.isNull(name)) {
			return assetCategoryPersistence.filterCountByG_V(
				groupId, vocabularyId);
		}
		else {
			return assetCategoryPersistence.filterCountByG_LikeN_V(
				groupId, name, vocabularyId);
		}
	}

	@Override
	public AssetCategoryDisplay getVocabularyCategoriesDisplay(
			long vocabularyId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		List<AssetCategory> categories = filterCategories(
			assetCategoryLocalService.getVocabularyCategories(
				vocabularyId, start, end, obc));

		return new AssetCategoryDisplay(
			categories, categories.size(), start, end);
	}

	@Override
	public AssetCategoryDisplay getVocabularyCategoriesDisplay(
			long groupId, String name, long vocabularyId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		List<AssetCategory> categories = null;
		int total = 0;

		if (Validator.isNotNull(name)) {
			name = (CustomSQLUtil.keywords(name))[0];

			categories = getVocabularyCategories(
				groupId, name, vocabularyId, start, end, obc);
			total = getVocabularyCategoriesCount(groupId, name, vocabularyId);
		}
		else {
			categories = getVocabularyCategories(vocabularyId, start, end, obc);
			total = getVocabularyCategoriesCount(groupId, vocabularyId);
		}

		return new AssetCategoryDisplay(categories, total, start, end);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #
	 *             getVocabularyRootCategories(long, long, int, int,
	 *             OrderByComparator)}
	 */
	@Override
	public List<AssetCategory> getVocabularyRootCategories(
			long vocabularyId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		AssetVocabulary vocabulary = assetVocabularyLocalService.getVocabulary(
			vocabularyId);

		return getVocabularyRootCategories(
			vocabulary.getGroupId(), vocabularyId, start, end, obc);
	}

	@Override
	public List<AssetCategory> getVocabularyRootCategories(
			long groupId, long vocabularyId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return assetCategoryPersistence.filterFindByG_P_V(
			groupId, AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			vocabularyId, start, end, obc);
	}

	@Override
	public int getVocabularyRootCategoriesCount(long groupId, long vocabularyId)
		throws SystemException {

		return assetCategoryPersistence.filterCountByG_P_V(
			groupId, AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			vocabularyId);
	}

	@Override
	public AssetCategory moveCategory(
			long categoryId, long parentCategoryId, long vocabularyId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		AssetCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.UPDATE);

		return assetCategoryLocalService.moveCategory(
			categoryId, parentCategoryId, vocabularyId, serviceContext);
	}

	@Override
	public List<AssetCategory> search(
			long groupId, String keywords, long vocabularyId, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		String name = CustomSQLUtil.keywords(keywords)[0];

		if (Validator.isNull(name)) {
			return assetCategoryPersistence.filterFindByG_V(
				groupId, vocabularyId, start, end, obc);
		}
		else {
			return assetCategoryPersistence.filterFindByG_LikeN_V(
				groupId, name, vocabularyId, start, end, obc);
		}
	}

	@Override
	public JSONArray search(
			long groupId, String name, String[] categoryProperties, int start,
			int end)
		throws PortalException, SystemException {

		List<AssetCategory> categories = assetCategoryLocalService.search(
			groupId, name, categoryProperties, start, end);

		categories = filterCategories(categories);

		return Autocomplete.listToJson(categories, "name", "name");
	}

	@Override
	public JSONArray search(
			long[] groupIds, String name, long[] vocabularyIds, int start,
			int end)
		throws PortalException, SystemException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (long groupId : groupIds) {
			JSONArray categoriesJSONArray = null;

			if (Validator.isNull(name)) {
				categoriesJSONArray = toJSONArray(
					assetCategoryPersistence.filterFindByG_V(
						groupId, vocabularyIds));
			}
			else {
				categoriesJSONArray = toJSONArray(
					assetCategoryPersistence.filterFindByG_LikeN_V(
						groupId, name, vocabularyIds));
			}

			for (int j = 0; j < categoriesJSONArray.length(); j++) {
				jsonArray.put(categoriesJSONArray.getJSONObject(j));
			}
		}

		return jsonArray;
	}

	@Override
	public AssetCategory updateCategory(
			long categoryId, long parentCategoryId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			long vocabularyId, String[] categoryProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		AssetCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.UPDATE);

		return assetCategoryLocalService.updateCategory(
			getUserId(), categoryId, parentCategoryId, titleMap, descriptionMap,
			vocabularyId, categoryProperties, serviceContext);
	}

	protected List<AssetCategory> filterCategories(
			List<AssetCategory> categories)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		categories = ListUtil.copy(categories);

		Iterator<AssetCategory> itr = categories.iterator();

		while (itr.hasNext()) {
			AssetCategory category = itr.next();

			if (!AssetCategoryPermission.contains(
					permissionChecker, category, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return categories;
	}

	protected JSONArray toJSONArray(List<AssetCategory> categories)
		throws PortalException, SystemException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (AssetCategory category : categories) {
			String categoryJSON = JSONFactoryUtil.looseSerialize(category);

			JSONObject categoryJSONObject = JSONFactoryUtil.createJSONObject(
				categoryJSON);

			List<String> names = new ArrayList<String>();

			AssetCategory curCategory = category;

			while (curCategory.getParentCategoryId() > 0) {
				AssetCategory parentCategory = getCategory(
					curCategory.getParentCategoryId());

				names.add(parentCategory.getName());
				names.add(
					StringPool.SPACE + StringPool.GREATER_THAN +
						StringPool.SPACE);

				curCategory = parentCategory;
			}

			Collections.reverse(names);

			AssetVocabulary vocabulary = assetVocabularyService.getVocabulary(
				category.getVocabularyId());

			StringBundler sb = new StringBundler(1 + names.size());

			sb.append(vocabulary.getName());
			sb.append(names.toArray(new String[names.size()]));

			categoryJSONObject.put("path", sb.toString());

			jsonArray.put(categoryJSONObject);
		}

		return jsonArray;
	}

}