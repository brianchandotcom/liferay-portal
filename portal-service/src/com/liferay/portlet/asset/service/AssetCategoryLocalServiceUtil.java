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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory addAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addAssetCategory(assetCategory);
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
	* Deletes the asset category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryId the primary key of the asset category
	* @return the asset category that was removed
	* @throws PortalException if a asset category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory deleteAssetCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteAssetCategory(categoryId);
	}

	/**
	* Deletes the asset category from the database. Also notifies the appropriate model listeners.
	*
	* @param assetCategory the asset category
	* @return the asset category that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory deleteAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteAssetCategory(assetCategory);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.asset.model.AssetCategory fetchAssetCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchAssetCategory(categoryId);
	}

	/**
	* Returns the asset category with the primary key.
	*
	* @param categoryId the primary key of the asset category
	* @return the asset category
	* @throws PortalException if a asset category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory getAssetCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetCategory(categoryId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static com.liferay.portlet.asset.model.AssetCategory getAssetCategoryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetCategoryByUuidAndGroupId(uuid, groupId);
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
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetCategories(start, end);
	}

	/**
	* Returns the number of asset categories.
	*
	* @return the number of asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static int getAssetCategoriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetCategoriesCount();
	}

	/**
	* Updates the asset category in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetCategory the asset category
	* @return the asset category that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory updateAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAssetCategory(assetCategory);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetEntryAssetCategory(long entryId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addAssetEntryAssetCategory(entryId, categoryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetEntryAssetCategory(long entryId,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addAssetEntryAssetCategory(entryId, assetCategory);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetEntryAssetCategories(long entryId,
		long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addAssetEntryAssetCategories(entryId, categoryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetEntryAssetCategories(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> AssetCategories)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addAssetEntryAssetCategories(entryId, AssetCategories);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void clearAssetEntryAssetCategories(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().clearAssetEntryAssetCategories(entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetEntryAssetCategory(long entryId,
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetEntryAssetCategory(entryId, categoryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetEntryAssetCategory(long entryId,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetEntryAssetCategory(entryId, assetCategory);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetEntryAssetCategories(long entryId,
		long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetEntryAssetCategories(entryId, categoryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetEntryAssetCategories(long entryId,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> AssetCategories)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetEntryAssetCategories(entryId, AssetCategories);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetEntryAssetCategories(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetEntryAssetCategories(entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetEntryAssetCategories(
		long entryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetEntryAssetCategories(entryId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetEntryAssetCategories(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getAssetEntryAssetCategories(entryId, start, end,
			orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static int getAssetEntryAssetCategoriesCount(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetEntryAssetCategoriesCount(entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasAssetEntryAssetCategory(long entryId,
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasAssetEntryAssetCategory(entryId, categoryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasAssetEntryAssetCategories(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasAssetEntryAssetCategories(entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void setAssetEntryAssetCategories(long entryId,
		long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().setAssetEntryAssetCategories(entryId, categoryIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.asset.model.AssetCategory addCategory(
		long userId, long parentCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addCategory(userId, parentCategoryId, titleMap,
			descriptionMap, vocabularyId, categoryProperties, serviceContext);
	}

	public static com.liferay.portlet.asset.model.AssetCategory addCategory(
		long userId, java.lang.String title, long vocabularyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addCategory(userId, title, vocabularyId, serviceContext);
	}

	public static void addCategoryResources(
		com.liferay.portlet.asset.model.AssetCategory category,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addCategoryResources(category, addGroupPermissions,
			addGuestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.asset.model.AssetCategory category,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addCategoryResources(category, groupPermissions, guestPermissions);
	}

	public static void deleteCategory(
		com.liferay.portlet.asset.model.AssetCategory category)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategory(category);
	}

	public static void deleteCategory(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategory(categoryId);
	}

	public static void deleteVocabularyCategories(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteVocabularyCategories(vocabularyId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory fetchCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchCategory(categoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategories();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategories(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategories(className, classPK);
	}

	public static com.liferay.portlet.asset.model.AssetCategory getCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategory(categoryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory getCategory(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategory(uuid, groupId);
	}

	public static long[] getCategoryIds(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryIds(className, classPK);
	}

	public static java.lang.String[] getCategoryNames()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryNames();
	}

	public static java.lang.String[] getCategoryNames(long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryNames(classNameId, classPK);
	}

	public static java.lang.String[] getCategoryNames(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryNames(className, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getChildCategories(parentCategoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getChildCategories(parentCategoryId, start, end, obc);
	}

	public static int getChildCategoriesCount(long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getChildCategoriesCount(parentCategoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getEntryCategories(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntryCategories(entryId);
	}

	public static java.util.List<java.lang.Long> getSubcategoryIds(
		long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSubcategoryIds(parentCategoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getVocabularyCategories(vocabularyId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long parentCategoryId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getVocabularyCategories(parentCategoryId, vocabularyId,
			start, end, obc);
	}

	public static int getVocabularyCategoriesCount(long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getVocabularyCategoriesCount(vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyRootCategories(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getVocabularyRootCategories(vocabularyId, start, end, obc);
	}

	public static int getVocabularyRootCategoriesCount(long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getVocabularyRootCategoriesCount(vocabularyId);
	}

	public static void mergeCategories(long fromCategoryId, long toCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().mergeCategories(fromCategoryId, toCategoryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory moveCategory(
		long categoryId, long parentCategoryId, long vocabularyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .moveCategory(categoryId, parentCategoryId, vocabularyId,
			serviceContext);
	}

	public static void rebuildTree(long groupId, boolean force)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().rebuildTree(groupId, force);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> search(
		long groupId, java.lang.String name,
		java.lang.String[] categoryProperties, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().search(groupId, name, categoryProperties, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetCategory updateCategory(
		long userId, long categoryId, long parentCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
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

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(AssetCategoryLocalService service) {
	}

	private static AssetCategoryLocalService _service;
}