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
 * Provides a wrapper for {@link AssetCategoryLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategoryLocalService
 * @generated
 */
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
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategory addAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.addAssetCategory(assetCategory);
	}

	/**
	* Creates a new asset category with the primary key. Does not add the asset category to the database.
	*
	* @param categoryId the primary key for the new asset category
	* @return the new asset category
	*/
	public com.liferay.portlet.asset.model.AssetCategory createAssetCategory(
		long categoryId) {
		return _assetCategoryLocalService.createAssetCategory(categoryId);
	}

	/**
	* Deletes the asset category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryId the primary key of the asset category
	* @return the asset category that was removed
	* @throws PortalException if a asset category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategory deleteAssetCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.deleteAssetCategory(categoryId);
	}

	/**
	* Deletes the asset category from the database. Also notifies the appropriate model listeners.
	*
	* @param assetCategory the asset category
	* @return the asset category that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategory deleteAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.deleteAssetCategory(assetCategory);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetCategoryLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.asset.model.AssetCategory fetchAssetCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.fetchAssetCategory(categoryId);
	}

	/**
	* Returns the asset category with the primary key.
	*
	* @param categoryId the primary key of the asset category
	* @return the asset category
	* @throws PortalException if a asset category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategory getAssetCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getAssetCategory(categoryId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the asset category matching the UUID and group.
	*
	* @param uuid the asset category's UUID
	* @param groupId the primary key of the group
	* @return the matching asset category
	* @throws PortalException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategory getAssetCategoryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getAssetCategoryByUuidAndGroupId(uuid,
			groupId);
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
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getAssetCategories(start, end);
	}

	/**
	* Returns the number of asset categories.
	*
	* @return the number of asset categories
	* @throws SystemException if a system exception occurred
	*/
	public int getAssetCategoriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getAssetCategoriesCount();
	}

	/**
	* Updates the asset category in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetCategory the asset category
	* @return the asset category that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategory updateAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.updateAssetCategory(assetCategory);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetEntryAssetCategory(long entryId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.addAssetEntryAssetCategory(entryId,
			categoryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetEntryAssetCategory(long entryId,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.addAssetEntryAssetCategory(entryId,
			assetCategory);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetEntryAssetCategories(long entryId, long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.addAssetEntryAssetCategories(entryId,
			categoryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetEntryAssetCategories(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> AssetCategories)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.addAssetEntryAssetCategories(entryId,
			AssetCategories);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void clearAssetEntryAssetCategories(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.clearAssetEntryAssetCategories(entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteAssetEntryAssetCategory(long entryId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.deleteAssetEntryAssetCategory(entryId,
			categoryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteAssetEntryAssetCategory(long entryId,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.deleteAssetEntryAssetCategory(entryId,
			assetCategory);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteAssetEntryAssetCategories(long entryId, long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.deleteAssetEntryAssetCategories(entryId,
			categoryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void deleteAssetEntryAssetCategories(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> AssetCategories)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.deleteAssetEntryAssetCategories(entryId,
			AssetCategories);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetEntryAssetCategories(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getAssetEntryAssetCategories(entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetEntryAssetCategories(
		long entryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getAssetEntryAssetCategories(entryId,
			start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetEntryAssetCategories(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getAssetEntryAssetCategories(entryId,
			start, end, orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public int getAssetEntryAssetCategoriesCount(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getAssetEntryAssetCategoriesCount(entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasAssetEntryAssetCategory(long entryId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.hasAssetEntryAssetCategory(entryId,
			categoryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasAssetEntryAssetCategories(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.hasAssetEntryAssetCategories(entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public void setAssetEntryAssetCategories(long entryId, long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.setAssetEntryAssetCategories(entryId,
			categoryIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _assetCategoryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_assetCategoryLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.asset.model.AssetCategory addCategory(
		long userId, long parentCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.addCategory(userId, parentCategoryId,
			titleMap, descriptionMap, vocabularyId, categoryProperties,
			serviceContext);
	}

	public com.liferay.portlet.asset.model.AssetCategory addCategory(
		long userId, java.lang.String title, long vocabularyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.addCategory(userId, title,
			vocabularyId, serviceContext);
	}

	public void addCategoryResources(
		com.liferay.portlet.asset.model.AssetCategory category,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.addCategoryResources(category,
			addGroupPermissions, addGuestPermissions);
	}

	public void addCategoryResources(
		com.liferay.portlet.asset.model.AssetCategory category,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.addCategoryResources(category,
			groupPermissions, guestPermissions);
	}

	public void deleteCategory(
		com.liferay.portlet.asset.model.AssetCategory category)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.deleteCategory(category);
	}

	public void deleteCategory(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.deleteCategory(categoryId);
	}

	public void deleteVocabularyCategories(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.deleteVocabularyCategories(vocabularyId);
	}

	public com.liferay.portlet.asset.model.AssetCategory fetchCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.fetchCategory(categoryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getCategories();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getCategories(classNameId, classPK);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getCategories(className, classPK);
	}

	public com.liferay.portlet.asset.model.AssetCategory getCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getCategory(categoryId);
	}

	public com.liferay.portlet.asset.model.AssetCategory getCategory(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getCategory(uuid, groupId);
	}

	public long[] getCategoryIds(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getCategoryIds(className, classPK);
	}

	public java.lang.String[] getCategoryNames()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getCategoryNames();
	}

	public java.lang.String[] getCategoryNames(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getCategoryNames(classNameId, classPK);
	}

	public java.lang.String[] getCategoryNames(java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getCategoryNames(className, classPK);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getChildCategories(parentCategoryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getChildCategories(parentCategoryId,
			start, end, obc);
	}

	public int getChildCategoriesCount(long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getChildCategoriesCount(parentCategoryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getEntryCategories(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getEntryCategories(entryId);
	}

	public java.util.List<java.lang.Long> getSubcategoryIds(
		long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getSubcategoryIds(parentCategoryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getVocabularyCategories(vocabularyId,
			start, end, obc);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long parentCategoryId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getVocabularyCategories(parentCategoryId,
			vocabularyId, start, end, obc);
	}

	public int getVocabularyCategoriesCount(long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getVocabularyCategoriesCount(vocabularyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyRootCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getVocabularyRootCategories(vocabularyId,
			start, end, obc);
	}

	public int getVocabularyRootCategoriesCount(long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.getVocabularyRootCategoriesCount(vocabularyId);
	}

	public void mergeCategories(long fromCategoryId, long toCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.mergeCategories(fromCategoryId, toCategoryId);
	}

	public com.liferay.portlet.asset.model.AssetCategory moveCategory(
		long categoryId, long parentCategoryId, long vocabularyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.moveCategory(categoryId,
			parentCategoryId, vocabularyId, serviceContext);
	}

	public void rebuildTree(long groupId, boolean force)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetCategoryLocalService.rebuildTree(groupId, force);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> search(
		long groupId, java.lang.String name,
		java.lang.String[] categoryProperties, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.search(groupId, name,
			categoryProperties, start, end);
	}

	public com.liferay.portlet.asset.model.AssetCategory updateCategory(
		long userId, long categoryId, long parentCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetCategoryLocalService.updateCategory(userId, categoryId,
			parentCategoryId, titleMap, descriptionMap, vocabularyId,
			categoryProperties, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public AssetCategoryLocalService getWrappedAssetCategoryLocalService() {
		return _assetCategoryLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {
		_assetCategoryLocalService = assetCategoryLocalService;
	}

	public AssetCategoryLocalService getWrappedService() {
		return _assetCategoryLocalService;
	}

	public void setWrappedService(
		AssetCategoryLocalService assetCategoryLocalService) {
		_assetCategoryLocalService = assetCategoryLocalService;
	}

	private AssetCategoryLocalService _assetCategoryLocalService;
}