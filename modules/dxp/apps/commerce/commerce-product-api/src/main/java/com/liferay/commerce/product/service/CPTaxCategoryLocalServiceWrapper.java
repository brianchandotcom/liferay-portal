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
 * Provides a wrapper for {@link CPTaxCategoryLocalService}.
 *
 * @author Marco Leo
 * @see CPTaxCategoryLocalService
 * @generated
 */
@ProviderType
public class CPTaxCategoryLocalServiceWrapper
	implements CPTaxCategoryLocalService,
		ServiceWrapper<CPTaxCategoryLocalService> {
	public CPTaxCategoryLocalServiceWrapper(
		CPTaxCategoryLocalService cpTaxCategoryLocalService) {
		_cpTaxCategoryLocalService = cpTaxCategoryLocalService;
	}

	/**
	* Adds the cp tax category to the database. Also notifies the appropriate model listeners.
	*
	* @param cpTaxCategory the cp tax category
	* @return the cp tax category that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPTaxCategory addCPTaxCategory(
		com.liferay.commerce.product.model.CPTaxCategory cpTaxCategory) {
		return _cpTaxCategoryLocalService.addCPTaxCategory(cpTaxCategory);
	}

	@Override
	public com.liferay.commerce.product.model.CPTaxCategory addCPTaxCategory(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTaxCategoryLocalService.addCPTaxCategory(nameMap,
			descriptionMap, serviceContext);
	}

	/**
	* Creates a new cp tax category with the primary key. Does not add the cp tax category to the database.
	*
	* @param CPTaxCategoryId the primary key for the new cp tax category
	* @return the new cp tax category
	*/
	@Override
	public com.liferay.commerce.product.model.CPTaxCategory createCPTaxCategory(
		long CPTaxCategoryId) {
		return _cpTaxCategoryLocalService.createCPTaxCategory(CPTaxCategoryId);
	}

	@Override
	public void deleteCPTaxCategories(long groupId) {
		_cpTaxCategoryLocalService.deleteCPTaxCategories(groupId);
	}

	/**
	* Deletes the cp tax category from the database. Also notifies the appropriate model listeners.
	*
	* @param cpTaxCategory the cp tax category
	* @return the cp tax category that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CPTaxCategory deleteCPTaxCategory(
		com.liferay.commerce.product.model.CPTaxCategory cpTaxCategory)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTaxCategoryLocalService.deleteCPTaxCategory(cpTaxCategory);
	}

	/**
	* Deletes the cp tax category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPTaxCategoryId the primary key of the cp tax category
	* @return the cp tax category that was removed
	* @throws PortalException if a cp tax category with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPTaxCategory deleteCPTaxCategory(
		long CPTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTaxCategoryLocalService.deleteCPTaxCategory(CPTaxCategoryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTaxCategoryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpTaxCategoryLocalService.dynamicQuery();
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
		return _cpTaxCategoryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpTaxCategoryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpTaxCategoryLocalService.dynamicQuery(dynamicQuery, start,
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
		return _cpTaxCategoryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpTaxCategoryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.product.model.CPTaxCategory fetchCPTaxCategory(
		long CPTaxCategoryId) {
		return _cpTaxCategoryLocalService.fetchCPTaxCategory(CPTaxCategoryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpTaxCategoryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the cp tax categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp tax categories
	* @param end the upper bound of the range of cp tax categories (not inclusive)
	* @return the range of cp tax categories
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPTaxCategory> getCPTaxCategories(
		int start, int end) {
		return _cpTaxCategoryLocalService.getCPTaxCategories(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPTaxCategory> getCPTaxCategories(
		long groupId) {
		return _cpTaxCategoryLocalService.getCPTaxCategories(groupId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPTaxCategory> getCPTaxCategories(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPTaxCategory> orderByComparator) {
		return _cpTaxCategoryLocalService.getCPTaxCategories(groupId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of cp tax categories.
	*
	* @return the number of cp tax categories
	*/
	@Override
	public int getCPTaxCategoriesCount() {
		return _cpTaxCategoryLocalService.getCPTaxCategoriesCount();
	}

	@Override
	public int getCPTaxCategoriesCount(long groupId) {
		return _cpTaxCategoryLocalService.getCPTaxCategoriesCount(groupId);
	}

	/**
	* Returns the cp tax category with the primary key.
	*
	* @param CPTaxCategoryId the primary key of the cp tax category
	* @return the cp tax category
	* @throws PortalException if a cp tax category with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPTaxCategory getCPTaxCategory(
		long CPTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTaxCategoryLocalService.getCPTaxCategory(CPTaxCategoryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpTaxCategoryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpTaxCategoryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTaxCategoryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the cp tax category in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpTaxCategory the cp tax category
	* @return the cp tax category that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPTaxCategory updateCPTaxCategory(
		com.liferay.commerce.product.model.CPTaxCategory cpTaxCategory) {
		return _cpTaxCategoryLocalService.updateCPTaxCategory(cpTaxCategory);
	}

	@Override
	public com.liferay.commerce.product.model.CPTaxCategory updateCPTaxCategory(
		long cpTaxCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTaxCategoryLocalService.updateCPTaxCategory(cpTaxCategoryId,
			nameMap, descriptionMap);
	}

	@Override
	public CPTaxCategoryLocalService getWrappedService() {
		return _cpTaxCategoryLocalService;
	}

	@Override
	public void setWrappedService(
		CPTaxCategoryLocalService cpTaxCategoryLocalService) {
		_cpTaxCategoryLocalService = cpTaxCategoryLocalService;
	}

	private CPTaxCategoryLocalService _cpTaxCategoryLocalService;
}