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

package com.liferay.portlet.messageboards.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MBCategoryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see MBCategoryLocalService
 * @generated
 */
@ProviderType
public class MBCategoryLocalServiceWrapper implements MBCategoryLocalService,
	ServiceWrapper<MBCategoryLocalService> {
	public MBCategoryLocalServiceWrapper(
		MBCategoryLocalService mbCategoryLocalService) {
		_mbCategoryLocalService = mbCategoryLocalService;
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String displayStyle,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, int inServerPort, boolean inUseSSL,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, java.lang.String outEmailAddress,
		boolean outCustom, java.lang.String outServerName, int outServerPort,
		boolean outUseSSL, java.lang.String outUserName,
		java.lang.String outPassword, boolean allowAnonymous,
		boolean mailingListActive,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbCategoryLocalService.addCategory(userId, parentCategoryId,
			name, description, displayStyle, emailAddress, inProtocol,
			inServerName, inServerPort, inUseSSL, inUserName, inPassword,
			inReadInterval, outEmailAddress, outCustom, outServerName,
			outServerPort, outUseSSL, outUserName, outPassword, allowAnonymous,
			mailingListActive, serviceContext);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbCategoryLocalService.addCategory(userId, parentCategoryId,
			name, description, serviceContext);
	}

	@Override
	public void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbCategoryLocalService.addCategoryResources(category,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbCategoryLocalService.addCategoryResources(category, modelPermissions);
	}

	@Override
	public void addCategoryResources(long categoryId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbCategoryLocalService.addCategoryResources(categoryId,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addCategoryResources(long categoryId,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbCategoryLocalService.addCategoryResources(categoryId,
			modelPermissions);
	}

	/**
	* Adds the message boards category to the database. Also notifies the appropriate model listeners.
	*
	* @param mbCategory the message boards category
	* @return the message boards category that was added
	*/
	@Override
	public com.liferay.portlet.messageboards.model.MBCategory addMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory) {
		return _mbCategoryLocalService.addMBCategory(mbCategory);
	}

	/**
	* Creates a new message boards category with the primary key. Does not add the message boards category to the database.
	*
	* @param categoryId the primary key for the new message boards category
	* @return the new message boards category
	*/
	@Override
	public com.liferay.portlet.messageboards.model.MBCategory createMBCategory(
		long categoryId) {
		return _mbCategoryLocalService.createMBCategory(categoryId);
	}

	@Override
	public void deleteCategories(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbCategoryLocalService.deleteCategories(groupId);
	}

	@Override
	public void deleteCategory(
		com.liferay.portlet.messageboards.model.MBCategory category)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbCategoryLocalService.deleteCategory(category);
	}

	@Override
	public void deleteCategory(
		com.liferay.portlet.messageboards.model.MBCategory category,
		boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbCategoryLocalService.deleteCategory(category, includeTrashedEntries);
	}

	@Override
	public void deleteCategory(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbCategoryLocalService.deleteCategory(categoryId);
	}

	/**
	* Deletes the message boards category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryId the primary key of the message boards category
	* @return the message boards category that was removed
	* @throws PortalException if a message boards category with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.messageboards.model.MBCategory deleteMBCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbCategoryLocalService.deleteMBCategory(categoryId);
	}

	/**
	* Deletes the message boards category from the database. Also notifies the appropriate model listeners.
	*
	* @param mbCategory the message boards category
	* @return the message boards category that was removed
	*/
	@Override
	public com.liferay.portlet.messageboards.model.MBCategory deleteMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory) {
		return _mbCategoryLocalService.deleteMBCategory(mbCategory);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbCategoryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mbCategoryLocalService.dynamicQuery();
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
		return _mbCategoryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _mbCategoryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _mbCategoryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _mbCategoryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _mbCategoryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory fetchMBCategory(
		long categoryId) {
		return _mbCategoryLocalService.fetchMBCategory(categoryId);
	}

	/**
	* Returns the message boards category matching the UUID and group.
	*
	* @param uuid the message boards category's UUID
	* @param groupId the primary key of the group
	* @return the matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	@Override
	public com.liferay.portlet.messageboards.model.MBCategory fetchMBCategoryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _mbCategoryLocalService.fetchMBCategoryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _mbCategoryLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId) {
		return _mbCategoryLocalService.getCategories(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long excludedCategoryId, long parentCategoryId,
		int status, int start, int end) {
		return _mbCategoryLocalService.getCategories(groupId,
			excludedCategoryId, parentCategoryId, status, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long[] excludedCategoryIds, long[] parentCategoryIds,
		int status, int start, int end) {
		return _mbCategoryLocalService.getCategories(groupId,
			excludedCategoryIds, parentCategoryIds, status, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end) {
		return _mbCategoryLocalService.getCategories(groupId, parentCategoryId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId, int status, int start, int end) {
		return _mbCategoryLocalService.getCategories(groupId, parentCategoryId,
			status, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int start, int end) {
		return _mbCategoryLocalService.getCategories(groupId,
			parentCategoryIds, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int status, int start, int end) {
		return _mbCategoryLocalService.getCategories(groupId,
			parentCategoryIds, status, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, int status) {
		return _mbCategoryLocalService.getCategories(groupId, status);
	}

	@Override
	public java.util.List<java.lang.Object> getCategoriesAndThreads(
		long groupId, long categoryId) {
		return _mbCategoryLocalService.getCategoriesAndThreads(groupId,
			categoryId);
	}

	@Override
	public java.util.List<java.lang.Object> getCategoriesAndThreads(
		long groupId, long categoryId, int status) {
		return _mbCategoryLocalService.getCategoriesAndThreads(groupId,
			categoryId, status);
	}

	@Override
	public java.util.List<java.lang.Object> getCategoriesAndThreads(
		long groupId, long categoryId, int status, int start, int end) {
		return _mbCategoryLocalService.getCategoriesAndThreads(groupId,
			categoryId, status, start, end);
	}

	@Override
	public int getCategoriesAndThreadsCount(long groupId, long categoryId) {
		return _mbCategoryLocalService.getCategoriesAndThreadsCount(groupId,
			categoryId);
	}

	@Override
	public int getCategoriesAndThreadsCount(long groupId, long categoryId,
		int status) {
		return _mbCategoryLocalService.getCategoriesAndThreadsCount(groupId,
			categoryId, status);
	}

	@Override
	public int getCategoriesCount(long groupId) {
		return _mbCategoryLocalService.getCategoriesCount(groupId);
	}

	@Override
	public int getCategoriesCount(long groupId, long excludedCategoryId,
		long parentCategoryId, int status) {
		return _mbCategoryLocalService.getCategoriesCount(groupId,
			excludedCategoryId, parentCategoryId, status);
	}

	@Override
	public int getCategoriesCount(long groupId, long[] excludedCategoryIds,
		long[] parentCategoryIds, int status) {
		return _mbCategoryLocalService.getCategoriesCount(groupId,
			excludedCategoryIds, parentCategoryIds, status);
	}

	@Override
	public int getCategoriesCount(long groupId, long parentCategoryId) {
		return _mbCategoryLocalService.getCategoriesCount(groupId,
			parentCategoryId);
	}

	@Override
	public int getCategoriesCount(long groupId, long parentCategoryId,
		int status) {
		return _mbCategoryLocalService.getCategoriesCount(groupId,
			parentCategoryId, status);
	}

	@Override
	public int getCategoriesCount(long groupId, long[] parentCategoryIds) {
		return _mbCategoryLocalService.getCategoriesCount(groupId,
			parentCategoryIds);
	}

	@Override
	public int getCategoriesCount(long groupId, long[] parentCategoryIds,
		int status) {
		return _mbCategoryLocalService.getCategoriesCount(groupId,
			parentCategoryIds, status);
	}

	@Override
	public int getCategoriesCount(long groupId, int status) {
		return _mbCategoryLocalService.getCategoriesCount(groupId, status);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory getCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbCategoryLocalService.getCategory(categoryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCompanyCategories(
		long companyId, int start, int end) {
		return _mbCategoryLocalService.getCompanyCategories(companyId, start,
			end);
	}

	@Override
	public int getCompanyCategoriesCount(long companyId) {
		return _mbCategoryLocalService.getCompanyCategoriesCount(companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _mbCategoryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _mbCategoryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns a range of all the message boards categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of message boards categories
	*/
	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getMBCategories(
		int start, int end) {
		return _mbCategoryLocalService.getMBCategories(start, end);
	}

	/**
	* Returns all the message boards categories matching the UUID and company.
	*
	* @param uuid the UUID of the message boards categories
	* @param companyId the primary key of the company
	* @return the matching message boards categories, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getMBCategoriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _mbCategoryLocalService.getMBCategoriesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of message boards categories matching the UUID and company.
	*
	* @param uuid the UUID of the message boards categories
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching message boards categories, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getMBCategoriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.messageboards.model.MBCategory> orderByComparator) {
		return _mbCategoryLocalService.getMBCategoriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of message boards categories.
	*
	* @return the number of message boards categories
	*/
	@Override
	public int getMBCategoriesCount() {
		return _mbCategoryLocalService.getMBCategoriesCount();
	}

	/**
	* Returns the message boards category with the primary key.
	*
	* @param categoryId the primary key of the message boards category
	* @return the message boards category
	* @throws PortalException if a message boards category with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.messageboards.model.MBCategory getMBCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbCategoryLocalService.getMBCategory(categoryId);
	}

	/**
	* Returns the message boards category matching the UUID and group.
	*
	* @param uuid the message boards category's UUID
	* @param groupId the primary key of the group
	* @return the matching message boards category
	* @throws PortalException if a matching message boards category could not be found
	*/
	@Override
	public com.liferay.portlet.messageboards.model.MBCategory getMBCategoryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbCategoryLocalService.getMBCategoryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _mbCategoryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbCategoryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<java.lang.Long> getSubcategoryIds(
		java.util.List<java.lang.Long> categoryIds, long groupId,
		long categoryId) {
		return _mbCategoryLocalService.getSubcategoryIds(categoryIds, groupId,
			categoryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getSubscribedCategories(
		long groupId, long userId, int start, int end) {
		return _mbCategoryLocalService.getSubscribedCategories(groupId, userId,
			start, end);
	}

	@Override
	public int getSubscribedCategoriesCount(long groupId, long userId) {
		return _mbCategoryLocalService.getSubscribedCategoriesCount(groupId,
			userId);
	}

	@Override
	public void moveCategoriesToTrash(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbCategoryLocalService.moveCategoriesToTrash(groupId, userId);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory moveCategory(
		long categoryId, long parentCategoryId, boolean mergeWithParentCategory)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbCategoryLocalService.moveCategory(categoryId,
			parentCategoryId, mergeWithParentCategory);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory moveCategoryFromTrash(
		long userId, long categoryId, long newCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbCategoryLocalService.moveCategoryFromTrash(userId,
			categoryId, newCategoryId);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory moveCategoryToTrash(
		long userId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbCategoryLocalService.moveCategoryToTrash(userId, categoryId);
	}

	@Override
	public void restoreCategoryFromTrash(long userId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbCategoryLocalService.restoreCategoryFromTrash(userId, categoryId);
	}

	@Override
	public void subscribeCategory(long userId, long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbCategoryLocalService.subscribeCategory(userId, groupId, categoryId);
	}

	@Override
	public void unsubscribeCategory(long userId, long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbCategoryLocalService.unsubscribeCategory(userId, groupId, categoryId);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String displayStyle,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, int inServerPort, boolean inUseSSL,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, java.lang.String outEmailAddress,
		boolean outCustom, java.lang.String outServerName, int outServerPort,
		boolean outUseSSL, java.lang.String outUserName,
		java.lang.String outPassword, boolean allowAnonymous,
		boolean mailingListActive, boolean mergeWithParentCategory,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbCategoryLocalService.updateCategory(categoryId,
			parentCategoryId, name, description, displayStyle, emailAddress,
			inProtocol, inServerName, inServerPort, inUseSSL, inUserName,
			inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			allowAnonymous, mailingListActive, mergeWithParentCategory,
			serviceContext);
	}

	/**
	* Updates the message boards category in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mbCategory the message boards category
	* @return the message boards category that was updated
	*/
	@Override
	public com.liferay.portlet.messageboards.model.MBCategory updateMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory) {
		return _mbCategoryLocalService.updateMBCategory(mbCategory);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory updateMessageCount(
		long categoryId) {
		return _mbCategoryLocalService.updateMessageCount(categoryId);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory updateStatistics(
		long categoryId) {
		return _mbCategoryLocalService.updateStatistics(categoryId);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory updateStatus(
		long userId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbCategoryLocalService.updateStatus(userId, categoryId, status);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory updateThreadCount(
		long categoryId) {
		return _mbCategoryLocalService.updateThreadCount(categoryId);
	}

	@Override
	public MBCategoryLocalService getWrappedService() {
		return _mbCategoryLocalService;
	}

	@Override
	public void setWrappedService(MBCategoryLocalService mbCategoryLocalService) {
		_mbCategoryLocalService = mbCategoryLocalService;
	}

	private MBCategoryLocalService _mbCategoryLocalService;
}