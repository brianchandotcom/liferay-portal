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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPOptionCategoryLocalService}.
 *
 * @author Marco Leo
 * @see CPOptionCategoryLocalService
 * @generated
 */
@ProviderType
public class CPOptionCategoryLocalServiceWrapper
	implements CPOptionCategoryLocalService,
		ServiceWrapper<CPOptionCategoryLocalService> {
	public CPOptionCategoryLocalServiceWrapper(
		CPOptionCategoryLocalService cpOptionCategoryLocalService) {
		_cpOptionCategoryLocalService = cpOptionCategoryLocalService;
	}

	/**
	* Adds the cp option category to the database. Also notifies the appropriate model listeners.
	*
	* @param cpOptionCategory the cp option category
	* @return the cp option category that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionCategory addCPOptionCategory(
		com.liferay.commerce.product.model.CPOptionCategory cpOptionCategory) {
		return _cpOptionCategoryLocalService.addCPOptionCategory(cpOptionCategory);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionCategory addCPOptionCategory(
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionCategoryLocalService.addCPOptionCategory(titleMap,
			descriptionMap, priority, key, serviceContext);
	}

	/**
	* Creates a new cp option category with the primary key. Does not add the cp option category to the database.
	*
	* @param CPOptionCategoryId the primary key for the new cp option category
	* @return the new cp option category
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionCategory createCPOptionCategory(
		long CPOptionCategoryId) {
		return _cpOptionCategoryLocalService.createCPOptionCategory(CPOptionCategoryId);
	}

	@Override
	public void deleteCPOptionCategories(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpOptionCategoryLocalService.deleteCPOptionCategories(groupId);
	}

	/**
	* Deletes the cp option category from the database. Also notifies the appropriate model listeners.
	*
	* @param cpOptionCategory the cp option category
	* @return the cp option category that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionCategory deleteCPOptionCategory(
		com.liferay.commerce.product.model.CPOptionCategory cpOptionCategory)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionCategoryLocalService.deleteCPOptionCategory(cpOptionCategory);
	}

	/**
	* Deletes the cp option category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPOptionCategoryId the primary key of the cp option category
	* @return the cp option category that was removed
	* @throws PortalException if a cp option category with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionCategory deleteCPOptionCategory(
		long CPOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionCategoryLocalService.deleteCPOptionCategory(CPOptionCategoryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionCategoryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpOptionCategoryLocalService.dynamicQuery();
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
		return _cpOptionCategoryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpOptionCategoryLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpOptionCategoryLocalService.dynamicQuery(dynamicQuery, start,
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
		return _cpOptionCategoryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpOptionCategoryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionCategory fetchCPOptionCategory(
		long CPOptionCategoryId) {
		return _cpOptionCategoryLocalService.fetchCPOptionCategory(CPOptionCategoryId);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionCategory fetchCPOptionCategory(
		long groupId, String key) {
		return _cpOptionCategoryLocalService.fetchCPOptionCategory(groupId, key);
	}

	/**
	* Returns the cp option category matching the UUID and group.
	*
	* @param uuid the cp option category's UUID
	* @param groupId the primary key of the group
	* @return the matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionCategory fetchCPOptionCategoryByUuidAndGroupId(
		String uuid, long groupId) {
		return _cpOptionCategoryLocalService.fetchCPOptionCategoryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpOptionCategoryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the cp option categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @return the range of cp option categories
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOptionCategory> getCPOptionCategories(
		int start, int end) {
		return _cpOptionCategoryLocalService.getCPOptionCategories(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOptionCategory> getCPOptionCategories(
		long groupId, int start, int end) {
		return _cpOptionCategoryLocalService.getCPOptionCategories(groupId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOptionCategory> getCPOptionCategories(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOptionCategory> orderByComparator) {
		return _cpOptionCategoryLocalService.getCPOptionCategories(groupId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the cp option categories matching the UUID and company.
	*
	* @param uuid the UUID of the cp option categories
	* @param companyId the primary key of the company
	* @return the matching cp option categories, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOptionCategory> getCPOptionCategoriesByUuidAndCompanyId(
		String uuid, long companyId) {
		return _cpOptionCategoryLocalService.getCPOptionCategoriesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp option categories matching the UUID and company.
	*
	* @param uuid the UUID of the cp option categories
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp option categories, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOptionCategory> getCPOptionCategoriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOptionCategory> orderByComparator) {
		return _cpOptionCategoryLocalService.getCPOptionCategoriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp option categories.
	*
	* @return the number of cp option categories
	*/
	@Override
	public int getCPOptionCategoriesCount() {
		return _cpOptionCategoryLocalService.getCPOptionCategoriesCount();
	}

	@Override
	public int getCPOptionCategoriesCount(long groupId) {
		return _cpOptionCategoryLocalService.getCPOptionCategoriesCount(groupId);
	}

	/**
	* Returns the cp option category with the primary key.
	*
	* @param CPOptionCategoryId the primary key of the cp option category
	* @return the cp option category
	* @throws PortalException if a cp option category with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionCategory getCPOptionCategory(
		long CPOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionCategoryLocalService.getCPOptionCategory(CPOptionCategoryId);
	}

	/**
	* Returns the cp option category matching the UUID and group.
	*
	* @param uuid the cp option category's UUID
	* @param groupId the primary key of the group
	* @return the matching cp option category
	* @throws PortalException if a matching cp option category could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionCategory getCPOptionCategoryByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionCategoryLocalService.getCPOptionCategoryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpOptionCategoryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpOptionCategoryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpOptionCategoryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionCategoryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the cp option category in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpOptionCategory the cp option category
	* @return the cp option category that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionCategory updateCPOptionCategory(
		com.liferay.commerce.product.model.CPOptionCategory cpOptionCategory) {
		return _cpOptionCategoryLocalService.updateCPOptionCategory(cpOptionCategory);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionCategory updateCPOptionCategory(
		long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionCategoryLocalService.updateCPOptionCategory(cpOptionCategoryId,
			titleMap, descriptionMap, priority, key, serviceContext);
	}

	@Override
	public CPOptionCategoryLocalService getWrappedService() {
		return _cpOptionCategoryLocalService;
	}

	@Override
	public void setWrappedService(
		CPOptionCategoryLocalService cpOptionCategoryLocalService) {
		_cpOptionCategoryLocalService = cpOptionCategoryLocalService;
	}

	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;
}