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
 * Provides a wrapper for {@link AssetCategoryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryLocalService
 * @generated
 */
@ProviderType
public class AssetCategoryLocalServiceWrapper
	implements AssetCategoryLocalService,
		ServiceWrapper<AssetCategoryLocalService> {
	public AssetCategoryLocalServiceWrapper(
		AssetCategoryLocalService assetCategoryLocalService) {
		_assetCategoryLocalService = assetCategoryLocalService;
	}

	/**
	* Adds the asset category to the database. Also notifies the appropriate model listeners.
	*
	* @param assetCategory the asset category
	* @return the asset category that was added
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetCategory addAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		return _assetCategoryLocalService.addAssetCategory(assetCategory);
	}

	@Override
	public void addAssetEntryAssetCategories(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> AssetCategories) {
		_assetCategoryLocalService.addAssetEntryAssetCategories(entryId,
			AssetCategories);
	}

	@Override
	public void addAssetEntryAssetCategories(long entryId, long[] categoryIds) {
		_assetCategoryLocalService.addAssetEntryAssetCategories(entryId,
			categoryIds);
	}

	@Override
	public void addAssetEntryAssetCategory(long entryId,
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		_assetCategoryLocalService.addAssetEntryAssetCategory(entryId,
			assetCategory);
	}

	@Override
	public void addAssetEntryAssetCategory(long entryId, long categoryId) {
		_assetCategoryLocalService.addAssetEntryAssetCategory(entryId,
			categoryId);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory addCategory(
		long userId, long groupId, long parentCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.addCategory(userId, groupId,
			parentCategoryId, titleMap, descriptionMap, vocabularyId,
			categoryProperties, serviceContext);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory addCategory(
		long userId, long groupId, java.lang.String title, long vocabularyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.addCategory(userId, groupId, title,
			vocabularyId, serviceContext);
	}

	@Override
	public void addCategoryResources(
		com.liferay.portlet.asset.model.AssetCategory category,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetCategoryLocalService.addCategoryResources(category,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addCategoryResources(
		com.liferay.portlet.asset.model.AssetCategory category,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetCategoryLocalService.addCategoryResources(category,
			modelPermissions);
	}

	@Override
	public void clearAssetEntryAssetCategories(long entryId) {
		_assetCategoryLocalService.clearAssetEntryAssetCategories(entryId);
	}

	/**
	* Creates a new asset category with the primary key. Does not add the asset category to the database.
	*
	* @param categoryId the primary key for the new asset category
	* @return the new asset category
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetCategory createAssetCategory(
		long categoryId) {
		return _assetCategoryLocalService.createAssetCategory(categoryId);
	}

	/**
	* Deletes the asset category from the database. Also notifies the appropriate model listeners.
	*
	* @param assetCategory the asset category
	* @return the asset category that was removed
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetCategory deleteAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		return _assetCategoryLocalService.deleteAssetCategory(assetCategory);
	}

	/**
	* Deletes the asset category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryId the primary key of the asset category
	* @return the asset category that was removed
	* @throws PortalException if a asset category with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetCategory deleteAssetCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.deleteAssetCategory(categoryId);
	}

	@Override
	public void deleteAssetEntryAssetCategories(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> AssetCategories) {
		_assetCategoryLocalService.deleteAssetEntryAssetCategories(entryId,
			AssetCategories);
	}

	@Override
	public void deleteAssetEntryAssetCategories(long entryId, long[] categoryIds) {
		_assetCategoryLocalService.deleteAssetEntryAssetCategories(entryId,
			categoryIds);
	}

	@Override
	public void deleteAssetEntryAssetCategory(long entryId,
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		_assetCategoryLocalService.deleteAssetEntryAssetCategory(entryId,
			assetCategory);
	}

	@Override
	public void deleteAssetEntryAssetCategory(long entryId, long categoryId) {
		_assetCategoryLocalService.deleteAssetEntryAssetCategory(entryId,
			categoryId);
	}

	@Override
	public void deleteCategories(
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> categories)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetCategoryLocalService.deleteCategories(categories);
	}

	@Override
	public void deleteCategories(long[] categoryIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetCategoryLocalService.deleteCategories(categoryIds);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory deleteCategory(
		com.liferay.portlet.asset.model.AssetCategory category)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.deleteCategory(category);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory deleteCategory(
		com.liferay.portlet.asset.model.AssetCategory category,
		boolean skipRebuildTree)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.deleteCategory(category,
			skipRebuildTree);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory deleteCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.deleteCategory(categoryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteVocabularyCategories(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetCategoryLocalService.deleteVocabularyCategories(vocabularyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetCategoryLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _assetCategoryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _assetCategoryLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _assetCategoryLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _assetCategoryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _assetCategoryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory fetchAssetCategory(
		long categoryId) {
		return _assetCategoryLocalService.fetchAssetCategory(categoryId);
	}

	/**
	* Returns the asset category matching the UUID and group.
	*
	* @param uuid the asset category's UUID
	* @param groupId the primary key of the group
	* @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetCategory fetchAssetCategoryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _assetCategoryLocalService.fetchAssetCategoryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory fetchCategory(
		long categoryId) {
		return _assetCategoryLocalService.fetchCategory(categoryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _assetCategoryLocalService.getActionableDynamicQuery();
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
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		int start, int end) {
		return _assetCategoryLocalService.getAssetCategories(start, end);
	}

	/**
	* Returns all the asset categories matching the UUID and company.
	*
	* @param uuid the UUID of the asset categories
	* @param companyId the primary key of the company
	* @return the matching asset categories, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategoriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _assetCategoryLocalService.getAssetCategoriesByUuidAndCompanyId(uuid,
			companyId);
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
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategoriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> orderByComparator) {
		return _assetCategoryLocalService.getAssetCategoriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of asset categories.
	*
	* @return the number of asset categories
	*/
	@Override
	public int getAssetCategoriesCount() {
		return _assetCategoryLocalService.getAssetCategoriesCount();
	}

	/**
	* Returns the asset category with the primary key.
	*
	* @param categoryId the primary key of the asset category
	* @return the asset category
	* @throws PortalException if a asset category with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetCategory getAssetCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.getAssetCategory(categoryId);
	}

	/**
	* Returns the asset category matching the UUID and group.
	*
	* @param uuid the asset category's UUID
	* @param groupId the primary key of the group
	* @return the matching asset category
	* @throws PortalException if a matching asset category could not be found
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetCategory getAssetCategoryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.getAssetCategoryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetEntryAssetCategories(
		long entryId) {
		return _assetCategoryLocalService.getAssetEntryAssetCategories(entryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetEntryAssetCategories(
		long entryId, int start, int end) {
		return _assetCategoryLocalService.getAssetEntryAssetCategories(entryId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetEntryAssetCategories(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> orderByComparator) {
		return _assetCategoryLocalService.getAssetEntryAssetCategories(entryId,
			start, end, orderByComparator);
	}

	@Override
	public int getAssetEntryAssetCategoriesCount(long entryId) {
		return _assetCategoryLocalService.getAssetEntryAssetCategoriesCount(entryId);
	}

	/**
	* Returns the entryIds of the asset entries associated with the asset category.
	*
	* @param categoryId the categoryId of the asset category
	* @return long[] the entryIds of asset entries associated with the asset category
	*/
	@Override
	public long[] getAssetEntryPrimaryKeys(long categoryId) {
		return _assetCategoryLocalService.getAssetEntryPrimaryKeys(categoryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories() {
		return _assetCategoryLocalService.getCategories();
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		java.lang.String className, long classPK) {
		return _assetCategoryLocalService.getCategories(className, classPK);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		long classNameId, long classPK) {
		return _assetCategoryLocalService.getCategories(classNameId, classPK);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		com.liferay.portal.kernel.search.Hits hits)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.getCategories(hits);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory getCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.getCategory(categoryId);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory getCategory(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.getCategory(uuid, groupId);
	}

	@Override
	public long[] getCategoryIds(java.lang.String className, long classPK) {
		return _assetCategoryLocalService.getCategoryIds(className, classPK);
	}

	@Override
	public java.lang.String[] getCategoryNames() {
		return _assetCategoryLocalService.getCategoryNames();
	}

	@Override
	public java.lang.String[] getCategoryNames(java.lang.String className,
		long classPK) {
		return _assetCategoryLocalService.getCategoryNames(className, classPK);
	}

	@Override
	public java.lang.String[] getCategoryNames(long classNameId, long classPK) {
		return _assetCategoryLocalService.getCategoryNames(classNameId, classPK);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId) {
		return _assetCategoryLocalService.getChildCategories(parentCategoryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return _assetCategoryLocalService.getChildCategories(parentCategoryId,
			start, end, obc);
	}

	@Override
	public int getChildCategoriesCount(long parentCategoryId) {
		return _assetCategoryLocalService.getChildCategoriesCount(parentCategoryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getEntryCategories(
		long entryId) {
		return _assetCategoryLocalService.getEntryCategories(entryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _assetCategoryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _assetCategoryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _assetCategoryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<java.lang.Long> getSubcategoryIds(
		long parentCategoryId) {
		return _assetCategoryLocalService.getSubcategoryIds(parentCategoryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long parentCategoryId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return _assetCategoryLocalService.getVocabularyCategories(parentCategoryId,
			vocabularyId, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return _assetCategoryLocalService.getVocabularyCategories(vocabularyId,
			start, end, obc);
	}

	@Override
	public int getVocabularyCategoriesCount(long vocabularyId) {
		return _assetCategoryLocalService.getVocabularyCategoriesCount(vocabularyId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyRootCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> obc) {
		return _assetCategoryLocalService.getVocabularyRootCategories(vocabularyId,
			start, end, obc);
	}

	@Override
	public int getVocabularyRootCategoriesCount(long vocabularyId) {
		return _assetCategoryLocalService.getVocabularyRootCategoriesCount(vocabularyId);
	}

	@Override
	public boolean hasAssetEntryAssetCategories(long entryId) {
		return _assetCategoryLocalService.hasAssetEntryAssetCategories(entryId);
	}

	@Override
	public boolean hasAssetEntryAssetCategory(long entryId, long categoryId) {
		return _assetCategoryLocalService.hasAssetEntryAssetCategory(entryId,
			categoryId);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory mergeCategories(
		long fromCategoryId, long toCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.mergeCategories(fromCategoryId,
			toCategoryId);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory moveCategory(
		long categoryId, long parentCategoryId, long vocabularyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.moveCategory(categoryId,
			parentCategoryId, vocabularyId, serviceContext);
	}

	@Override
	public void rebuildTree(long groupId, boolean force) {
		_assetCategoryLocalService.rebuildTree(groupId, force);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> search(
		long groupId, java.lang.String name,
		java.lang.String[] categoryProperties, int start, int end) {
		return _assetCategoryLocalService.search(groupId, name,
			categoryProperties, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portlet.asset.model.AssetCategory> searchCategories(
		long companyId, long[] groupIds, java.lang.String title,
		long[] parentCategoryIds, long[] vocabularyIds, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.searchCategories(companyId, groupIds,
			title, parentCategoryIds, vocabularyIds, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portlet.asset.model.AssetCategory> searchCategories(
		long companyId, long groupIds, java.lang.String title,
		long vocabularyId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.searchCategories(companyId, groupIds,
			title, vocabularyId, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portlet.asset.model.AssetCategory> searchCategories(
		long companyId, long[] groupIds, java.lang.String title,
		long[] vocabularyIds, long[] parentCategoryIds, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.searchCategories(companyId, groupIds,
			title, vocabularyIds, parentCategoryIds, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portlet.asset.model.AssetCategory> searchCategories(
		long companyId, long[] groupIds, java.lang.String title,
		long[] vocabularyIds, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.searchCategories(companyId, groupIds,
			title, vocabularyIds, start, end);
	}

	@Override
	public void setAssetEntryAssetCategories(long entryId, long[] categoryIds) {
		_assetCategoryLocalService.setAssetEntryAssetCategories(entryId,
			categoryIds);
	}

	/**
	* Updates the asset category in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetCategory the asset category
	* @return the asset category that was updated
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetCategory updateAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		return _assetCategoryLocalService.updateAssetCategory(assetCategory);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategory updateCategory(
		long userId, long categoryId, long parentCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetCategoryLocalService.updateCategory(userId, categoryId,
			parentCategoryId, titleMap, descriptionMap, vocabularyId,
			categoryProperties, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public AssetCategoryLocalService getWrappedAssetCategoryLocalService() {
		return _assetCategoryLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {
		_assetCategoryLocalService = assetCategoryLocalService;
	}

	@Override
	public AssetCategoryLocalService getWrappedService() {
		return _assetCategoryLocalService;
	}

	@Override
	public void setWrappedService(
		AssetCategoryLocalService assetCategoryLocalService) {
		_assetCategoryLocalService = assetCategoryLocalService;
	}

	private AssetCategoryLocalService _assetCategoryLocalService;
}