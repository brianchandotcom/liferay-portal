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

import com.liferay.portal.kernel.cache.ThreadLocalCachable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.AssetCategoryNameException;
import com.liferay.portlet.asset.DuplicateCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.base.AssetCategoryLocalServiceBaseImpl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

	@Override
	public AssetCategory addCategory(
			long userId, long parentCategoryId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, long vocabularyId,
			String[] categoryProperties, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Category

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();

		String name = titleMap.get(LocaleUtil.getSiteDefault());

		name = ModelHintsUtil.trimString(
			AssetCategory.class.getName(), "name", name);

		if (categoryProperties == null) {
			categoryProperties = new String[0];
		}

		Date now = new Date();

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
		category.setCreateDate(now);
		category.setModifiedDate(now);
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
				category, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Properties

		for (int i = 0; i < categoryProperties.length; i++) {
			String[] categoryProperty = StringUtil.split(
				categoryProperties[i],
				AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR);

			if (categoryProperty.length <= 1) {
				categoryProperty = StringUtil.split(
					categoryProperties[i], CharPool.COLON);
			}

			String key = StringPool.BLANK;
			String value = StringPool.BLANK;

			if (categoryProperty.length > 1) {
				key = GetterUtil.getString(categoryProperty[0]);
				value = GetterUtil.getString(categoryProperty[1]);
			}

			if (Validator.isNotNull(key)) {
				assetCategoryPropertyLocalService.addCategoryProperty(
					userId, categoryId, key, value);
			}
		}

		return category;
	}

	@Override
	public AssetCategory addCategory(
			long userId, String title, long vocabularyId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		Locale locale = LocaleUtil.getSiteDefault();

		titleMap.put(locale, title);

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(locale, StringPool.BLANK);

		return addCategory(
			userId, AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, titleMap,
			descriptionMap, vocabularyId, null, serviceContext);
	}

	@Override
	public void addCategoryResources(
			AssetCategory category, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), AssetCategory.class.getName(),
			category.getCategoryId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addCategoryResources(
			AssetCategory category, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), AssetCategory.class.getName(),
			category.getCategoryId(), groupPermissions, guestPermissions);
	}

	@Override
	public void deleteCategory(AssetCategory category)
		throws PortalException, SystemException {

		deleteCategory(category, false);
	}

	@Override
	public void deleteCategory(long categoryId)
		throws PortalException, SystemException {

		AssetCategory category = assetCategoryPersistence.findByPrimaryKey(
			categoryId);

		deleteCategory(category);
	}

	@Override
	public void deleteVocabularyCategories(long vocabularyId)
		throws PortalException, SystemException {

		List<AssetCategory> categories =
			assetCategoryPersistence.findByVocabularyId(vocabularyId);

		for (AssetCategory category : categories) {
			if (category.getParentCategoryId() ==
					AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				deleteCategory(category.getCategoryId());
			}
		}
	}

	@Override
	public AssetCategory fetchCategory(long categoryId) throws SystemException {
		return assetCategoryPersistence.fetchByPrimaryKey(categoryId);
	}

	@Override
	public List<AssetCategory> getCategories() throws SystemException {
		return assetCategoryPersistence.findAll();
	}

	@Override
	@ThreadLocalCachable
	public List<AssetCategory> getCategories(long classNameId, long classPK)
		throws SystemException {

		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (entry == null) {
			return Collections.emptyList();
		}

		return assetEntryPersistence.getAssetCategories(entry.getEntryId());
	}

	@Override
	public List<AssetCategory> getCategories(String className, long classPK)
		throws SystemException {

		long classNameId = classNameLocalService.getClassNameId(className);

		return getCategories(classNameId, classPK);
	}

	@Override
	public AssetCategory getCategory(long categoryId)
		throws PortalException, SystemException {

		return assetCategoryPersistence.findByPrimaryKey(categoryId);
	}

	@Override
	public AssetCategory getCategory(String uuid, long groupId)
		throws PortalException, SystemException {

		return assetCategoryPersistence.findByUUID_G(uuid, groupId);
	}

	@Override
	public long[] getCategoryIds(String className, long classPK)
		throws SystemException {

		return getCategoryIds(getCategories(className, classPK));
	}

	@Override
	public String[] getCategoryNames() throws SystemException {
		return getCategoryNames(getCategories());
	}

	@Override
	public String[] getCategoryNames(long classNameId, long classPK)
		throws SystemException {

		return getCategoryNames(getCategories(classNameId, classPK));
	}

	@Override
	public String[] getCategoryNames(String className, long classPK)
		throws SystemException {

		return getCategoryNames(getCategories(className, classPK));
	}

	@Override
	public List<AssetCategory> getChildCategories(long parentCategoryId)
		throws SystemException {

		return assetCategoryPersistence.findByParentCategoryId(
			parentCategoryId);
	}

	@Override
	public List<AssetCategory> getChildCategories(
			long parentCategoryId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return assetCategoryPersistence.findByParentCategoryId(
			parentCategoryId, start, end, obc);
	}

	@Override
	public int getChildCategoriesCount(long parentCategoryId)
		throws SystemException {

		return assetCategoryPersistence.countByParentCategoryId(
			parentCategoryId);
	}

	@Override
	public List<AssetCategory> getEntryCategories(long entryId)
		throws SystemException {

		return assetEntryPersistence.getAssetCategories(entryId);
	}

	@Override
	public List<Long> getSubcategoryIds(long parentCategoryId)
		throws SystemException {

		return assetCategoryFinder.findByG_L(parentCategoryId);
	}

	@Override
	public List<AssetCategory> getVocabularyCategories(
			long vocabularyId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return assetCategoryPersistence.findByVocabularyId(
			vocabularyId, start, end, obc);
	}

	@Override
	public List<AssetCategory> getVocabularyCategories(
			long parentCategoryId, long vocabularyId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return assetCategoryPersistence.findByP_V(
			parentCategoryId, vocabularyId, start, end, obc);
	}

	@Override
	public int getVocabularyCategoriesCount(long vocabularyId)
		throws SystemException {

		return assetCategoryPersistence.countByVocabularyId(vocabularyId);
	}

	@Override
	public List<AssetCategory> getVocabularyRootCategories(
			long vocabularyId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return getVocabularyCategories(
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, vocabularyId,
			start, end, obc);
	}

	@Override
	public int getVocabularyRootCategoriesCount(long vocabularyId)
		throws SystemException {

		return assetCategoryPersistence.countByP_V(
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, vocabularyId);
	}

	@Override
	public void mergeCategories(long fromCategoryId, long toCategoryId)
		throws PortalException, SystemException {

		List<AssetEntry> entries = assetCategoryPersistence.getAssetEntries(
			fromCategoryId);

		assetCategoryPersistence.addAssetEntries(toCategoryId, entries);

		List<AssetCategoryProperty> categoryProperties =
			assetCategoryPropertyPersistence.findByCategoryId(fromCategoryId);

		for (AssetCategoryProperty fromCategoryProperty : categoryProperties) {
			AssetCategoryProperty toCategoryProperty =
				assetCategoryPropertyPersistence.fetchByCA_K(
					toCategoryId, fromCategoryProperty.getKey());

			if (toCategoryProperty == null) {
				fromCategoryProperty.setCategoryId(toCategoryId);

				assetCategoryPropertyPersistence.update(fromCategoryProperty);
			}
		}

		deleteCategory(fromCategoryId);
	}

	@Override
	public AssetCategory moveCategory(
			long categoryId, long parentCategoryId, long vocabularyId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		AssetCategory category = assetCategoryPersistence.findByPrimaryKey(
			categoryId);

		validate(
			categoryId, parentCategoryId, category.getName(), vocabularyId);

		if (parentCategoryId > 0) {
			assetCategoryPersistence.findByPrimaryKey(parentCategoryId);
		}

		if (vocabularyId != category.getVocabularyId()) {
			assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

			category.setVocabularyId(vocabularyId);

			updateChildrenVocabularyId(category, vocabularyId);
		}

		category.setModifiedDate(new Date());
		category.setParentCategoryId(parentCategoryId);

		assetCategoryPersistence.update(category);

		return category;
	}

	@Override
	public void rebuildTree(long groupId, boolean force)
		throws SystemException {

		assetCategoryPersistence.rebuildTree(groupId, force);
	}

	@Override
	public List<AssetCategory> search(
			long groupId, String name, String[] categoryProperties, int start,
			int end)
		throws SystemException {

		return assetCategoryFinder.findByG_N_P(
			groupId, name, categoryProperties, start, end);
	}

	@Override
	public AssetCategory updateCategory(
			long userId, long categoryId, long parentCategoryId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			long vocabularyId, String[] categoryProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

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

		String oldName = category.getName();

		if (vocabularyId != category.getVocabularyId()) {
			assetVocabularyPersistence.findByPrimaryKey(vocabularyId);

			parentCategoryId =
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;

			category.setVocabularyId(vocabularyId);

			updateChildrenVocabularyId(category, vocabularyId);
		}

		category.setModifiedDate(new Date());
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setTitleMap(titleMap);
		category.setDescriptionMap(descriptionMap);

		assetCategoryPersistence.update(category);

		// Properties

		List<AssetCategoryProperty> oldCategoryProperties =
			assetCategoryPropertyPersistence.findByCategoryId(categoryId);

		oldCategoryProperties = ListUtil.copy(oldCategoryProperties);

		for (int i = 0; i < categoryProperties.length; i++) {
			String[] categoryProperty = StringUtil.split(
				categoryProperties[i],
				AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR);

			if (categoryProperty.length <= 1) {
				categoryProperty = StringUtil.split(
					categoryProperties[i], CharPool.COLON);
			}

			String key = StringPool.BLANK;

			if (categoryProperty.length > 0) {
				key = GetterUtil.getString(categoryProperty[0]);
			}

			String value = StringPool.BLANK;

			if (categoryProperty.length > 1) {
				value = GetterUtil.getString(categoryProperty[1]);
			}

			if (Validator.isNotNull(key)) {
				boolean addCategoryProperty = true;
				boolean updateCategoryProperty = false;

				AssetCategoryProperty oldCategoryProperty = null;

				Iterator<AssetCategoryProperty> iterator =
					oldCategoryProperties.iterator();

				while (iterator.hasNext()) {
					oldCategoryProperty = iterator.next();

					if ((categoryId == oldCategoryProperty.getCategoryId()) &&
						key.equals(oldCategoryProperty.getKey())) {

						addCategoryProperty = false;

						if (!value.equals(oldCategoryProperty.getValue())) {
							updateCategoryProperty = true;
						}

						iterator.remove();

						break;
					}
				}

				if (addCategoryProperty) {
					assetCategoryPropertyLocalService.addCategoryProperty(
						userId, categoryId, key, value);
				}
				else if (updateCategoryProperty) {
					assetCategoryPropertyLocalService.updateCategoryProperty(
						userId, oldCategoryProperty.getCategoryPropertyId(),
						key, value);
				}
			}
		}

		for (AssetCategoryProperty categoryProperty : oldCategoryProperties) {
			assetCategoryPropertyLocalService.deleteAssetCategoryProperty(
				categoryProperty);
		}

		// Indexer

		if (!oldName.equals(name)) {
			List<AssetEntry> entries = assetCategoryPersistence.getAssetEntries(
				category.getCategoryId());

			assetEntryLocalService.reindex(entries);
		}

		return category;
	}

	protected void deleteCategory(AssetCategory category, boolean childCategory)
		throws PortalException, SystemException {

		// Categories

		List<AssetCategory> categories =
			assetCategoryPersistence.findByParentCategoryId(
				category.getCategoryId());

		for (AssetCategory curCategory : categories) {
			deleteCategory(curCategory, true);
		}

		if (!categories.isEmpty() && !childCategory) {
			final long groupId = category.getGroupId();

			TransactionCommitCallbackRegistryUtil.registerCallback(
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

		// Entries

		List<AssetEntry> entries = assetTagPersistence.getAssetEntries(
			category.getCategoryId());

		// Properties

		assetCategoryPropertyLocalService.deleteCategoryProperties(
			category.getCategoryId());

		// Indexer

		assetEntryLocalService.reindex(entries);
	}

	protected long[] getCategoryIds(List<AssetCategory> categories) {
		return StringUtil.split(
			ListUtil.toString(categories, AssetCategory.CATEGORY_ID_ACCESSOR),
			0L);
	}

	protected String[] getCategoryNames(List<AssetCategory> categories) {
		return StringUtil.split(
			ListUtil.toString(categories, AssetCategory.NAME_ACCESSOR));
	}

	protected void updateChildrenVocabularyId(
			AssetCategory category, long vocabularyId)
		throws SystemException {

		List<AssetCategory> childrenCategories =
			assetCategoryPersistence.findByParentCategoryId(
				category.getCategoryId());

		if (!childrenCategories.isEmpty()) {
			for (AssetCategory childCategory : childrenCategories) {
				childCategory.setVocabularyId(vocabularyId);
				childCategory.setModifiedDate(new Date());

				assetCategoryPersistence.update(childCategory);

				updateChildrenVocabularyId (childCategory, vocabularyId);
			}
		}
	}

	protected void validate(
			long categoryId, long parentCategoryId, String name,
			long vocabularyId)
		throws PortalException, SystemException {

		if (Validator.isNull(name)) {
			throw new AssetCategoryNameException();
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

}