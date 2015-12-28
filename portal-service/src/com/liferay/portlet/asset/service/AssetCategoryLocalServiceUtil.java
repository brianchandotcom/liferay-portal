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
 * Provides the local service utility for AssetCategory. This utility wraps
 * {@link com.liferay.portlet.asset.service.impl.AssetCategoryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryLocalService
 * @see com.liferay.portlet.asset.service.base.AssetCategoryLocalServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetCategoryLocalServiceImpl
 * @generated
 */
@ProviderType
public class AssetCategoryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetCategoryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the asset category to the database. Also notifies the appropriate model listeners.
	*
	* @param assetCategory the asset category
	* @return the asset category that was added
	*/
	public static com.liferay.portlet.asset.model.AssetCategory addAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		return getService().addAssetCategory(assetCategory);
	}

	public static void addAssetEntryAssetCategories(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> AssetCategories) {
		getService().addAssetEntryAssetCategories(entryId, AssetCategories);
	}

	public static void addAssetEntryAssetCategories(long entryId,
		long[] categoryIds) {
		getService().addAssetEntryAssetCategories(entryId, categoryIds);
	}

	public static void addAssetEntryAssetCategory(long entryId,
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		getService().addAssetEntryAssetCategory(entryId, assetCategory);
	}

	public static void addAssetEntryAssetCategory(long entryId, long categoryId) {
		getService().addAssetEntryAssetCategory(entryId, categoryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory addCategory(
		long userId, long groupId, long parentCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCategory(userId, groupId, parentCategoryId, titleMap,
			descriptionMap, vocabularyId, categoryProperties, serviceContext);
	}

	public static com.liferay.portlet.asset.model.AssetCategory addCategory(
		long userId, long groupId, java.lang.String title, long vocabularyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCategory(userId, groupId, title, vocabularyId,
			serviceContext);
	}

	public static void addCategoryResources(
		com.liferay.portlet.asset.model.AssetCategory category,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addCategoryResources(category, addGroupPermissions,
			addGuestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.asset.model.AssetCategory category,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addCategoryResources(category, modelPermissions);
	}

	public static void clearAssetEntryAssetCategories(long entryId) {
		getService().clearAssetEntryAssetCategories(entryId);
	}

	/**
	* Creates a new asset category with the primary key. Does not add the asset category to the database.
	*
	* @param categoryId the primary key for the new asset category
	* @return the new asset category
	*/
	public static com.liferay.portlet.asset.model.AssetCategory createAssetCategory(
		long categoryId) {
		return getService().createAssetCategory(categoryId);
	}

	/**
	* Deletes the asset category from the database. Also notifies the appropriate model listeners.
	*
	* @param assetCategory the asset category
	* @return the asset category that was removed
	*/
	public static com.liferay.portlet.asset.model.AssetCategory deleteAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		return getService().deleteAssetCategory(assetCategory);
	}

	/**
	* Deletes the asset category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryId the primary key of the asset category
	* @return the asset category that was removed
	* @throws PortalException if a asset category with the primary key could not be found
	*/
	public static com.liferay.portlet.asset.model.AssetCategory deleteAssetCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteAssetCategory(categoryId);
	}

	public static void deleteAssetEntryAssetCategories(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> AssetCategories) {
		getService().deleteAssetEntryAssetCategories(entryId, AssetCategories);
	}

	public static void deleteAssetEntryAssetCategories(long entryId,
		long[] categoryIds) {
		getService().deleteAssetEntryAssetCategories(entryId, categoryIds);
	}

	public static void deleteAssetEntryAssetCategory(long entryId,
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		getService().deleteAssetEntryAssetCategory(entryId, assetCategory);
	}

	public static void deleteAssetEntryAssetCategory(long entryId,
		long categoryId) {
		getService().deleteAssetEntryAssetCategory(entryId, categoryId);
	}

	public static void deleteCategories(
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> categories)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCategories(categories);
	}

	public static void deleteCategories(long[] categoryIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCategories(categoryIds);
	}

	public static com.liferay.portlet.asset.model.AssetCategory deleteCategory(
		com.liferay.portlet.asset.model.AssetCategory category)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCategory(category);
	}

	public static com.liferay.portlet.asset.model.AssetCategory deleteCategory(
		com.liferay.portlet.asset.model.AssetCategory category,
		boolean skipRebuildTree)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCategory(category, skipRebuildTree);
	}

	public static com.liferay.portlet.asset.model.AssetCategory deleteCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCategory(categoryId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteVocabularyCategories(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteVocabularyCategories(vocabularyId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portlet.asset.model.AssetCategory fetchAssetCategory(
		long categoryId) {
		return getService().fetchAssetCategory(categoryId);
	}

	/**
	* Returns the asset category matching the UUID and group.
	*
	* @param uuid the asset category's UUID
	* @param groupId the primary key of the group
	* @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static com.liferay.portlet.asset.model.AssetCategory fetchAssetCategoryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchAssetCategoryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory fetchCategory(
		long categoryId) {
		return getService().fetchCategory(categoryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the asset categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of asset categories
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		int start, int end) {
		return getService().getAssetCategories(start, end);
	}

	/**
	* Returns all the asset categories matching the UUID and company.
	*
	* @param uuid the UUID of the asset categories
	* @param companyId the primary key of the company
	* @return the matching asset categories, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategoriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getAssetCategoriesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of asset categories matching the UUID and company.
	*
	* @param uuid the UUID of the asset categories
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching asset categories, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategoriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> orderByComparator) {
		return getService()
				   .getAssetCategoriesByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of asset categories.
	*
	* @return the number of asset categories
	*/
	public static int getAssetCategoriesCount() {
		return getService().getAssetCategoriesCount();
	}

	/**
	* Returns the asset category with the primary key.
	*
	* @param categoryId the primary key of the asset category
	* @return the asset category
	* @throws PortalException if a asset category with the primary key could not be found
	*/
	public static com.liferay.portlet.asset.model.AssetCategory getAssetCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAssetCategory(categoryId);
	}

	/**
	* Returns the asset category matching the UUID and group.
	*
	* @param uuid the asset category's UUID
	* @param groupId the primary key of the group
	* @return the matching asset category
	* @throws PortalException if a matching asset category could not be found
	*/
	public static com.liferay.portlet.asset.model.AssetCategory getAssetCategoryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAssetCategoryByUuidAndGroupId(uuid, groupId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetEntryAssetCategories(
		long entryId) {
		return getService().getAssetEntryAssetCategories(entryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetEntryAssetCategories(
		long entryId, int start, int end) {
		return getService().getAssetEntryAssetCategories(entryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetEntryAssetCategories(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> orderByComparator) {
		return getService()
				   .getAssetEntryAssetCategories(entryId, start, end,
			orderByComparator);
	}

	public static int getAssetEntryAssetCategoriesCount(long entryId) {
		return getService().getAssetEntryAssetCategoriesCount(entryId);
	}

	/**
	* Returns the entryIds of the asset entries associated with the asset category.
	*
	* @param categoryId the categoryId of the asset category
	* @return long[] the entryIds of asset entries associated with the asset category
	*/
	public static long[] getAssetEntryPrimaryKeys(long categoryId) {
		return getService().getAssetEntryPrimaryKeys(categoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories() {
		return getService().getCategories();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		java.lang.String className, long classPK) {
		return getService().getCategories(className, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		long classNameId, long classPK) {
		return getService().getCategories(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		com.liferay.portal.kernel.search.Hits hits)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCategories(hits);
	}

	public static com.liferay.portlet.asset.model.AssetCategory getCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCategory(categoryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory getCategory(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCategory(uuid, groupId);
	}

	public static long[] getCategoryIds(java.lang.String className, long classPK) {
		return getService().getCategoryIds(className, classPK);
	}

	public static java.lang.String[] getCategoryNames() {
		return getService().getCategoryNames();
	}

	public static java.lang.String[] getCategoryNames(
		java.lang.String className, long classPK) {
		return getService().getCategoryNames(className, classPK);
	}

	public static java.lang.String[] getCategoryNames(long classNameId,
		long classPK) {
		return getService().getCategoryNames(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId) {
		return getService().getChildCategories(parentCategoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return getService().getChildCategories(parentCategoryId, start, end, obc);
	}

	public static int getChildCategoriesCount(long parentCategoryId) {
		return getService().getChildCategoriesCount(parentCategoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getEntryCategories(
		long entryId) {
		return getService().getEntryCategories(entryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List<java.lang.Long> getSubcategoryIds(
		long parentCategoryId) {
		return getService().getSubcategoryIds(parentCategoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long parentCategoryId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return getService()
				   .getVocabularyCategories(parentCategoryId, vocabularyId,
			start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return getService()
				   .getVocabularyCategories(vocabularyId, start, end, obc);
	}

	public static int getVocabularyCategoriesCount(long vocabularyId) {
		return getService().getVocabularyCategoriesCount(vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyRootCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return getService()
				   .getVocabularyRootCategories(vocabularyId, start, end, obc);
	}

	public static int getVocabularyRootCategoriesCount(long vocabularyId) {
		return getService().getVocabularyRootCategoriesCount(vocabularyId);
	}

	public static boolean hasAssetEntryAssetCategories(long entryId) {
		return getService().hasAssetEntryAssetCategories(entryId);
	}

	public static boolean hasAssetEntryAssetCategory(long entryId,
		long categoryId) {
		return getService().hasAssetEntryAssetCategory(entryId, categoryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory mergeCategories(
		long fromCategoryId, long toCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().mergeCategories(fromCategoryId, toCategoryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory moveCategory(
		long categoryId, long parentCategoryId, long vocabularyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .moveCategory(categoryId, parentCategoryId, vocabularyId,
			serviceContext);
	}

	public static void rebuildTree(long groupId, boolean force) {
		getService().rebuildTree(groupId, force);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> search(
		long groupId, java.lang.String name,
		java.lang.String[] categoryProperties, int start, int end) {
		return getService().search(groupId, name, categoryProperties, start, end);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portlet.asset.model.AssetCategory> searchCategories(
		long companyId, long[] groupIds, java.lang.String title,
		long[] parentCategoryIds, long[] vocabularyIds, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCategories(companyId, groupIds, title,
			parentCategoryIds, vocabularyIds, start, end);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portlet.asset.model.AssetCategory> searchCategories(
		long companyId, long groupIds, java.lang.String title,
		long vocabularyId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCategories(companyId, groupIds, title, vocabularyId,
			start, end);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portlet.asset.model.AssetCategory> searchCategories(
		long companyId, long[] groupIds, java.lang.String title,
		long[] vocabularyIds, long[] parentCategoryIds, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCategories(companyId, groupIds, title, vocabularyIds,
			parentCategoryIds, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portlet.asset.model.AssetCategory> searchCategories(
		long companyId, long[] groupIds, java.lang.String title,
		long[] vocabularyIds, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCategories(companyId, groupIds, title, vocabularyIds,
			start, end);
	}

	public static void setAssetEntryAssetCategories(long entryId,
		long[] categoryIds) {
		getService().setAssetEntryAssetCategories(entryId, categoryIds);
	}

	/**
	* Updates the asset category in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetCategory the asset category
	* @return the asset category that was updated
	*/
	public static com.liferay.portlet.asset.model.AssetCategory updateAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		return getService().updateAssetCategory(assetCategory);
	}

	public static com.liferay.portlet.asset.model.AssetCategory updateCategory(
		long userId, long categoryId, long parentCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCategory(userId, categoryId, parentCategoryId,
			titleMap, descriptionMap, vocabularyId, categoryProperties,
			serviceContext);
	}

	public static AssetCategoryLocalService getService() {
		if (_service == null) {
			_service = (AssetCategoryLocalService)PortalBeanLocatorUtil.locate(AssetCategoryLocalService.class.getName());

			ReferenceRegistry.registerReference(AssetCategoryLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static AssetCategoryLocalService _service;
}