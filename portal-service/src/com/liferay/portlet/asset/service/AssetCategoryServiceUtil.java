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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for AssetCategory. This utility wraps
 * {@link com.liferay.portlet.asset.service.impl.AssetCategoryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryService
 * @see com.liferay.portlet.asset.service.base.AssetCategoryServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetCategoryServiceImpl
 * @generated
 */
@ProviderType
public class AssetCategoryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetCategoryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.asset.model.AssetCategory addCategory(
		long parentCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCategory(parentCategoryId, titleMap, descriptionMap,
			vocabularyId, categoryProperties, serviceContext);
	}

	public static com.liferay.portlet.asset.model.AssetCategory addCategory(
		java.lang.String title, long vocabularyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addCategory(title, vocabularyId, serviceContext);
	}

	public static void deleteCategories(long[] categoryIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCategories(categoryIds);
	}

	/**
	* @deprecated As of 7.0.0, Replaced by {@link #deleteCategories(long[])}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> deleteCategories(
		long[] categoryIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCategories(categoryIds, serviceContext);
	}

	public static void deleteCategory(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCategory(categoryId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCategories(className, classPK);
	}

	public static com.liferay.portlet.asset.model.AssetCategory getCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCategory(categoryId);
	}

	public static java.lang.String getCategoryPath(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCategoryPath(categoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getChildCategories(parentCategoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getChildCategories(parentCategoryId, start, end, obc);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #search(long[], String,
	long[], int, int)}
	*/
	@Deprecated
	public static com.liferay.portal.kernel.json.JSONArray getJSONSearch(
		long groupId, java.lang.String name, long[] vocabularyIds, int start,
		int end) throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getJSONSearch(groupId, name, vocabularyIds, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getVocabularyCategoriesDisplay(long, String, long, int, int,
	OrderByComparator)}
	*/
	@Deprecated
	public static com.liferay.portal.kernel.json.JSONObject getJSONVocabularyCategories(
		long groupId, java.lang.String name, long vocabularyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getJSONVocabularyCategories(groupId, name, vocabularyId,
			start, end, obc);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getVocabularyCategoriesDisplay(long, int, int,
	OrderByComparator)}
	*/
	@Deprecated
	public static com.liferay.portal.kernel.json.JSONObject getJSONVocabularyCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getJSONVocabularyCategories(vocabularyId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long groupId, java.lang.String name, long vocabularyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return getService()
				   .getVocabularyCategories(groupId, name, vocabularyId, start,
			end, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long groupId, long parentCategoryId, long vocabularyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return getService()
				   .getVocabularyCategories(groupId, parentCategoryId,
			vocabularyId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long parentCategoryId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getVocabularyCategories(parentCategoryId, vocabularyId,
			start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getVocabularyCategories(vocabularyId, start, end, obc);
	}

	public static int getVocabularyCategoriesCount(long groupId,
		java.lang.String name, long vocabularyId) {
		return getService()
				   .getVocabularyCategoriesCount(groupId, name, vocabularyId);
	}

	public static int getVocabularyCategoriesCount(long groupId,
		long parentCategory, long vocabularyId) {
		return getService()
				   .getVocabularyCategoriesCount(groupId, parentCategory,
			vocabularyId);
	}

	public static int getVocabularyCategoriesCount(long groupId,
		long vocabularyId) {
		return getService().getVocabularyCategoriesCount(groupId, vocabularyId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryDisplay getVocabularyCategoriesDisplay(
		long groupId, java.lang.String name, long vocabularyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getVocabularyCategoriesDisplay(groupId, name, vocabularyId,
			start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryDisplay getVocabularyCategoriesDisplay(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getVocabularyCategoriesDisplay(vocabularyId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyRootCategories(
		long groupId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return getService()
				   .getVocabularyRootCategories(groupId, vocabularyId, start,
			end, obc);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getVocabularyRootCategories(long, long, int, int,
	OrderByComparator)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyRootCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getVocabularyRootCategories(vocabularyId, start, end, obc);
	}

	public static int getVocabularyRootCategoriesCount(long groupId,
		long vocabularyId) {
		return getService()
				   .getVocabularyRootCategoriesCount(groupId, vocabularyId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory moveCategory(
		long categoryId, long parentCategoryId, long vocabularyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .moveCategory(categoryId, parentCategoryId, vocabularyId,
			serviceContext);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> search(
		long groupId, java.lang.String keywords, long vocabularyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return getService()
				   .search(groupId, keywords, vocabularyId, start, end, obc);
	}

	public static com.liferay.portal.kernel.json.JSONArray search(
		long groupId, java.lang.String name,
		java.lang.String[] categoryProperties, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().search(groupId, name, categoryProperties, start, end);
	}

	public static com.liferay.portal.kernel.json.JSONArray search(
		long[] groupIds, java.lang.String name, long[] vocabularyIds,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().search(groupIds, name, vocabularyIds, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryDisplay searchCategoriesDisplay(
		long groupId, java.lang.String title, long parentCategoryId,
		long vocabularyId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCategoriesDisplay(groupId, title, parentCategoryId,
			vocabularyId, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryDisplay searchCategoriesDisplay(
		long groupId, java.lang.String title, long vocabularyId, int start,
		int end) throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCategoriesDisplay(groupId, title, vocabularyId,
			start, end);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryDisplay searchCategoriesDisplay(
		long[] groupIds, java.lang.String title, long[] parentCategoryIds,
		long[] vocabularyIds, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCategoriesDisplay(groupIds, title, parentCategoryIds,
			vocabularyIds, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryDisplay searchCategoriesDisplay(
		long[] groupIds, java.lang.String title, long[] vocabularyIds,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCategoriesDisplay(groupIds, title, vocabularyIds,
			start, end);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.asset.model.AssetCategory updateCategory(
		long categoryId, long parentCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCategory(categoryId, parentCategoryId, titleMap,
			descriptionMap, vocabularyId, categoryProperties, serviceContext);
	}

	public static AssetCategoryService getService() {
		if (_service == null) {
			_service = (AssetCategoryService)PortalBeanLocatorUtil.locate(AssetCategoryService.class.getName());

			ReferenceRegistry.registerReference(AssetCategoryServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(AssetCategoryService service) {
	}

	private static AssetCategoryService _service;
}