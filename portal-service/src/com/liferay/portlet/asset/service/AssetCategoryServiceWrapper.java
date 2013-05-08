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

package com.liferay.portlet.asset.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetCategoryService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategoryService
 * @generated
 */
public class AssetCategoryServiceWrapper implements AssetCategoryService,
	ServiceWrapper<AssetCategoryService> {
	public AssetCategoryServiceWrapper(
		AssetCategoryService assetCategoryService) {
		_assetCategoryService = assetCategoryService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _assetCategoryService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_assetCategoryService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.asset.model.AssetCategory addCategory(
		long parentCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.addCategory(parentCategoryId, titleMap,
			descriptionMap, vocabularyId, categoryProperties, serviceContext);
	}

	public com.liferay.portlet.asset.model.AssetCategory addCategory(
		java.lang.String title, long vocabularyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.addCategory(title, vocabularyId,
			serviceContext);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> deleteCategories(
		long[] categoryIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.deleteCategories(categoryIds,
			serviceContext);
	}

	public void deleteCategory(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryService.deleteCategory(categoryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getCategories(className, classPK);
	}

	public com.liferay.portlet.asset.model.AssetCategory getCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getCategory(categoryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getChildCategories(parentCategoryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getChildCategories(parentCategoryId,
			start, end, obc);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #search(long[], String,
	long[], int, int)}
	*/
	public com.liferay.portal.kernel.json.JSONArray getJSONSearch(
		long groupId, java.lang.String name, long[] vocabularyIds, int start,
		int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getJSONSearch(groupId, name,
			vocabularyIds, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getVocabularyCategoriesDisplay(long, int, int,
	OrderByComparator)}
	*/
	public com.liferay.portal.kernel.json.JSONObject getJSONVocabularyCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getJSONVocabularyCategories(vocabularyId,
			start, end, obc);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getVocabularyCategoriesDisplay(long, String, long, int, int,
	OrderByComparator)}
	*/
	public com.liferay.portal.kernel.json.JSONObject getJSONVocabularyCategories(
		long groupId, java.lang.String name, long vocabularyId, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getJSONVocabularyCategories(groupId, name,
			vocabularyId, start, end, obc);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getVocabularyCategories(vocabularyId,
			start, end, obc);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long parentCategoryId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getVocabularyCategories(parentCategoryId,
			vocabularyId, start, end, obc);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long groupId, java.lang.String name, long vocabularyId, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getVocabularyCategories(groupId, name,
			vocabularyId, start, end, obc);
	}

	public int getVocabularyCategoriesCount(long groupId, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getVocabularyCategoriesCount(groupId,
			vocabularyId);
	}

	public int getVocabularyCategoriesCount(long groupId,
		java.lang.String name, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getVocabularyCategoriesCount(groupId,
			name, vocabularyId);
	}

	public com.liferay.portlet.asset.model.AssetCategoryDisplay getVocabularyCategoriesDisplay(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getVocabularyCategoriesDisplay(vocabularyId,
			start, end, obc);
	}

	public com.liferay.portlet.asset.model.AssetCategoryDisplay getVocabularyCategoriesDisplay(
		long groupId, java.lang.String name, long vocabularyId, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getVocabularyCategoriesDisplay(groupId,
			name, vocabularyId, start, end, obc);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #
	getVocabularyRootCategories(long, long, int, int,
	OrderByComparator)}
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyRootCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getVocabularyRootCategories(vocabularyId,
			start, end, obc);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyRootCategories(
		long groupId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getVocabularyRootCategories(groupId,
			vocabularyId, start, end, obc);
	}

	public int getVocabularyRootCategoriesCount(long groupId, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.getVocabularyRootCategoriesCount(groupId,
			vocabularyId);
	}

	public com.liferay.portlet.asset.model.AssetCategory moveCategory(
		long categoryId, long parentCategoryId, long vocabularyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.moveCategory(categoryId, parentCategoryId,
			vocabularyId, serviceContext);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> search(
		long groupId, java.lang.String keywords, long vocabularyId, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.search(groupId, keywords, vocabularyId,
			start, end, obc);
	}

	public com.liferay.portal.kernel.json.JSONArray search(long groupId,
		java.lang.String name, java.lang.String[] categoryProperties,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.search(groupId, name, categoryProperties,
			start, end);
	}

	public com.liferay.portal.kernel.json.JSONArray search(long[] groupIds,
		java.lang.String name, long[] vocabularyIds, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.search(groupIds, name, vocabularyIds,
			start, end);
	}

	public com.liferay.portlet.asset.model.AssetCategory updateCategory(
		long categoryId, long parentCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryService.updateCategory(categoryId,
			parentCategoryId, titleMap, descriptionMap, vocabularyId,
			categoryProperties, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public AssetCategoryService getWrappedAssetCategoryService() {
		return _assetCategoryService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedAssetCategoryService(
		AssetCategoryService assetCategoryService) {
		_assetCategoryService = assetCategoryService;
	}

	public AssetCategoryService getWrappedService() {
		return _assetCategoryService;
	}

	public void setWrappedService(AssetCategoryService assetCategoryService) {
		_assetCategoryService = assetCategoryService;
	}

	private AssetCategoryService _assetCategoryService;
}