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

package com.liferay.portlet.asset.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetCategoryService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryService
 * @generated
 */
@ProviderType
public class AssetCategoryServiceWrapper implements AssetCategoryService,
	ServiceWrapper<AssetCategoryService> {
	public AssetCategoryServiceWrapper(
		AssetCategoryService assetCategoryService) {
		_assetCategoryService = assetCategoryService;
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory addCategory(
		long groupId, long parentCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.addCategory(groupId, parentCategoryId,
			titleMap, descriptionMap, vocabularyId, categoryProperties,
			serviceContext);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory addCategory(
		long groupId, java.lang.String title, long vocabularyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.addCategory(groupId, title, vocabularyId,
			serviceContext);
	}

	@Override
	public void deleteCategories(long[] categoryIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetCategoryService.deleteCategories(categoryIds);
	}

	/**
	* @deprecated As of 7.0.0, Replaced by {@link #deleteCategories(long[])}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> deleteCategories(
		long[] categoryIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.deleteCategories(categoryIds,
			serviceContext);
	}

	@Override
	public void deleteCategory(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetCategoryService.deleteCategory(categoryId);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory fetchCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.fetchCategory(categoryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.getCategories(className, classPK);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory getCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.getCategory(categoryId);
	}

	@Override
	public java.lang.String getCategoryPath(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.getCategoryPath(categoryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.getChildCategories(parentCategoryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.getChildCategories(parentCategoryId,
			start, end, obc);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #search(long[], String,
	long[], int, int)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.json.JSONArray getJSONSearch(
		long groupId, java.lang.String name, long[] vocabularyIds, int start,
		int end) throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.getJSONSearch(groupId, name,
			vocabularyIds, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getVocabularyCategoriesDisplay(long, String, long, int, int,
	OrderByComparator)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.json.JSONObject getJSONVocabularyCategories(
		long groupId, java.lang.String name, long vocabularyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.getJSONVocabularyCategories(groupId, name,
			vocabularyId, start, end, obc);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getVocabularyCategoriesDisplay(long, int, int,
	OrderByComparator)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.json.JSONObject getJSONVocabularyCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.getJSONVocabularyCategories(vocabularyId,
			start, end, obc);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _assetCategoryService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long groupId, java.lang.String name, long vocabularyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return _assetCategoryService.getVocabularyCategories(groupId, name,
			vocabularyId, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long groupId, long parentCategoryId, long vocabularyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return _assetCategoryService.getVocabularyCategories(groupId,
			parentCategoryId, vocabularyId, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long parentCategoryId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.getVocabularyCategories(parentCategoryId,
			vocabularyId, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.getVocabularyCategories(vocabularyId,
			start, end, obc);
	}

	@Override
	public int getVocabularyCategoriesCount(long groupId,
		java.lang.String name, long vocabularyId) {
		return _assetCategoryService.getVocabularyCategoriesCount(groupId,
			name, vocabularyId);
	}

	@Override
	public int getVocabularyCategoriesCount(long groupId, long parentCategory,
		long vocabularyId) {
		return _assetCategoryService.getVocabularyCategoriesCount(groupId,
			parentCategory, vocabularyId);
	}

	@Override
	public int getVocabularyCategoriesCount(long groupId, long vocabularyId) {
		return _assetCategoryService.getVocabularyCategoriesCount(groupId,
			vocabularyId);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategoryDisplay getVocabularyCategoriesDisplay(
		long groupId, java.lang.String name, long vocabularyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.getVocabularyCategoriesDisplay(groupId,
			name, vocabularyId, start, end, obc);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategoryDisplay getVocabularyCategoriesDisplay(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.getVocabularyCategoriesDisplay(vocabularyId,
			start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyRootCategories(
		long groupId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return _assetCategoryService.getVocabularyRootCategories(groupId,
			vocabularyId, start, end, obc);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getVocabularyRootCategories(long, long, int, int,
	OrderByComparator)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyRootCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.getVocabularyRootCategories(vocabularyId,
			start, end, obc);
	}

	@Override
	public int getVocabularyRootCategoriesCount(long groupId, long vocabularyId) {
		return _assetCategoryService.getVocabularyRootCategoriesCount(groupId,
			vocabularyId);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory moveCategory(
		long categoryId, long parentCategoryId, long vocabularyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.moveCategory(categoryId, parentCategoryId,
			vocabularyId, serviceContext);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> search(
		long groupId, java.lang.String keywords, long vocabularyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return _assetCategoryService.search(groupId, keywords, vocabularyId,
			start, end, obc);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray search(long groupId,
		java.lang.String name, java.lang.String[] categoryProperties,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.search(groupId, name, categoryProperties,
			start, end);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray search(long[] groupIds,
		java.lang.String name, long[] vocabularyIds, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.search(groupIds, name, vocabularyIds,
			start, end);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategoryDisplay searchCategoriesDisplay(
		long groupId, java.lang.String title, long parentCategoryId,
		long vocabularyId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.searchCategoriesDisplay(groupId, title,
			parentCategoryId, vocabularyId, start, end);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategoryDisplay searchCategoriesDisplay(
		long groupId, java.lang.String title, long vocabularyId,
		long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.searchCategoriesDisplay(groupId, title,
			vocabularyId, parentCategoryId, start, end, sort);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategoryDisplay searchCategoriesDisplay(
		long groupId, java.lang.String title, long vocabularyId, int start,
		int end) throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.searchCategoriesDisplay(groupId, title,
			vocabularyId, start, end);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategoryDisplay searchCategoriesDisplay(
		long[] groupIds, java.lang.String title, long[] parentCategoryIds,
		long[] vocabularyIds, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.searchCategoriesDisplay(groupIds, title,
			parentCategoryIds, vocabularyIds, start, end);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategoryDisplay searchCategoriesDisplay(
		long[] groupIds, java.lang.String title, long[] vocabularyIds,
		long[] parentCategoryIds, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.searchCategoriesDisplay(groupIds, title,
			vocabularyIds, parentCategoryIds, start, end, sort);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategoryDisplay searchCategoriesDisplay(
		long[] groupIds, java.lang.String title, long[] vocabularyIds,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.searchCategoriesDisplay(groupIds, title,
			vocabularyIds, start, end);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory updateCategory(
		long categoryId, long parentCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryService.updateCategory(categoryId,
			parentCategoryId, titleMap, descriptionMap, vocabularyId,
			categoryProperties, serviceContext);
	}

	@Override
	public AssetCategoryService getWrappedService() {
		return _assetCategoryService;
	}

	@Override
	public void setWrappedService(AssetCategoryService assetCategoryService) {
		_assetCategoryService = assetCategoryService;
	}

	private AssetCategoryService _assetCategoryService;
}