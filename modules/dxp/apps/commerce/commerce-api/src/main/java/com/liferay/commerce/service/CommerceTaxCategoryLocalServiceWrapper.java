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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceTaxCategoryLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryLocalService
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryLocalServiceWrapper
	implements CommerceTaxCategoryLocalService,
		ServiceWrapper<CommerceTaxCategoryLocalService> {
	public CommerceTaxCategoryLocalServiceWrapper(
		CommerceTaxCategoryLocalService commerceTaxCategoryLocalService) {
		_commerceTaxCategoryLocalService = commerceTaxCategoryLocalService;
	}

	/**
	* Adds the commerce tax category to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategory the commerce tax category
	* @return the commerce tax category that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceTaxCategory addCommerceTaxCategory(
		com.liferay.commerce.model.CommerceTaxCategory commerceTaxCategory) {
		return _commerceTaxCategoryLocalService.addCommerceTaxCategory(commerceTaxCategory);
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxCategory addCommerceTaxCategory(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryLocalService.addCommerceTaxCategory(nameMap,
			descriptionMap, serviceContext);
	}

	/**
	* Creates a new commerce tax category with the primary key. Does not add the commerce tax category to the database.
	*
	* @param commerceTaxCategoryId the primary key for the new commerce tax category
	* @return the new commerce tax category
	*/
	@Override
	public com.liferay.commerce.model.CommerceTaxCategory createCommerceTaxCategory(
		long commerceTaxCategoryId) {
		return _commerceTaxCategoryLocalService.createCommerceTaxCategory(commerceTaxCategoryId);
	}

	@Override
	public void deleteCommerceTaxCategories(long groupId) {
		_commerceTaxCategoryLocalService.deleteCommerceTaxCategories(groupId);
	}

	/**
	* Deletes the commerce tax category from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategory the commerce tax category
	* @return the commerce tax category that was removed
	*/
	@Override
	public com.liferay.commerce.model.CommerceTaxCategory deleteCommerceTaxCategory(
		com.liferay.commerce.model.CommerceTaxCategory commerceTaxCategory) {
		return _commerceTaxCategoryLocalService.deleteCommerceTaxCategory(commerceTaxCategory);
	}

	/**
	* Deletes the commerce tax category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategoryId the primary key of the commerce tax category
	* @return the commerce tax category that was removed
	* @throws PortalException if a commerce tax category with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceTaxCategory deleteCommerceTaxCategory(
		long commerceTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryLocalService.deleteCommerceTaxCategory(commerceTaxCategoryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceTaxCategoryLocalService.dynamicQuery();
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
		return _commerceTaxCategoryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceTaxCategoryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceTaxCategoryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _commerceTaxCategoryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceTaxCategoryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxCategory fetchCommerceTaxCategory(
		long commerceTaxCategoryId) {
		return _commerceTaxCategoryLocalService.fetchCommerceTaxCategory(commerceTaxCategoryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceTaxCategoryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the commerce tax categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax categories
	* @param end the upper bound of the range of commerce tax categories (not inclusive)
	* @return the range of commerce tax categories
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTaxCategory> getCommerceTaxCategories(
		int start, int end) {
		return _commerceTaxCategoryLocalService.getCommerceTaxCategories(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTaxCategory> getCommerceTaxCategories(
		long groupId) {
		return _commerceTaxCategoryLocalService.getCommerceTaxCategories(groupId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTaxCategory> getCommerceTaxCategories(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceTaxCategory> orderByComparator) {
		return _commerceTaxCategoryLocalService.getCommerceTaxCategories(groupId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce tax categories.
	*
	* @return the number of commerce tax categories
	*/
	@Override
	public int getCommerceTaxCategoriesCount() {
		return _commerceTaxCategoryLocalService.getCommerceTaxCategoriesCount();
	}

	@Override
	public int getCommerceTaxCategoriesCount(long groupId) {
		return _commerceTaxCategoryLocalService.getCommerceTaxCategoriesCount(groupId);
	}

	/**
	* Returns the commerce tax category with the primary key.
	*
	* @param commerceTaxCategoryId the primary key of the commerce tax category
	* @return the commerce tax category
	* @throws PortalException if a commerce tax category with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceTaxCategory getCommerceTaxCategory(
		long commerceTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryLocalService.getCommerceTaxCategory(commerceTaxCategoryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceTaxCategoryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceTaxCategoryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce tax category in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategory the commerce tax category
	* @return the commerce tax category that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceTaxCategory updateCommerceTaxCategory(
		com.liferay.commerce.model.CommerceTaxCategory commerceTaxCategory) {
		return _commerceTaxCategoryLocalService.updateCommerceTaxCategory(commerceTaxCategory);
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxCategory updateCommerceTaxCategory(
		long commerceTaxCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryLocalService.updateCommerceTaxCategory(commerceTaxCategoryId,
			nameMap, descriptionMap);
	}

	@Override
	public CommerceTaxCategoryLocalService getWrappedService() {
		return _commerceTaxCategoryLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceTaxCategoryLocalService commerceTaxCategoryLocalService) {
		_commerceTaxCategoryLocalService = commerceTaxCategoryLocalService;
	}

	private CommerceTaxCategoryLocalService _commerceTaxCategoryLocalService;
}