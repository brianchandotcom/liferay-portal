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
 * Provides a wrapper for {@link CommerceProductOptionLocalService}.
 *
 * @author Marco Leo
 * @see CommerceProductOptionLocalService
 * @generated
 */
@ProviderType
public class CommerceProductOptionLocalServiceWrapper
	implements CommerceProductOptionLocalService,
		ServiceWrapper<CommerceProductOptionLocalService> {
	public CommerceProductOptionLocalServiceWrapper(
		CommerceProductOptionLocalService commerceProductOptionLocalService) {
		_commerceProductOptionLocalService = commerceProductOptionLocalService;
	}

	/**
	* Adds the commerce product option to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOption the commerce product option
	* @return the commerce product option that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductOption addCommerceProductOption(
		com.liferay.commerce.product.model.CommerceProductOption commerceProductOption) {
		return _commerceProductOptionLocalService.addCommerceProductOption(commerceProductOption);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductOption addCommerceProductOption(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductOptionLocalService.addCommerceProductOption(nameMap,
			descriptionMap, ddmFormFieldTypeName, serviceContext);
	}

	/**
	* Creates a new commerce product option with the primary key. Does not add the commerce product option to the database.
	*
	* @param commerceProductOptionId the primary key for the new commerce product option
	* @return the new commerce product option
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductOption createCommerceProductOption(
		long commerceProductOptionId) {
		return _commerceProductOptionLocalService.createCommerceProductOption(commerceProductOptionId);
	}

	/**
	* Deletes the commerce product option from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOption the commerce product option
	* @return the commerce product option that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductOption deleteCommerceProductOption(
		com.liferay.commerce.product.model.CommerceProductOption commerceProductOption)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductOptionLocalService.deleteCommerceProductOption(commerceProductOption);
	}

	/**
	* Deletes the commerce product option with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOptionId the primary key of the commerce product option
	* @return the commerce product option that was removed
	* @throws PortalException if a commerce product option with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductOption deleteCommerceProductOption(
		long commerceProductOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductOptionLocalService.deleteCommerceProductOption(commerceProductOptionId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductOption fetchCommerceProductOption(
		long commerceProductOptionId) {
		return _commerceProductOptionLocalService.fetchCommerceProductOption(commerceProductOptionId);
	}

	/**
	* Returns the commerce product option with the primary key.
	*
	* @param commerceProductOptionId the primary key of the commerce product option
	* @return the commerce product option
	* @throws PortalException if a commerce product option with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductOption getCommerceProductOption(
		long commerceProductOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductOptionLocalService.getCommerceProductOption(commerceProductOptionId);
	}

	/**
	* Updates the commerce product option in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOption the commerce product option
	* @return the commerce product option that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductOption updateCommerceProductOption(
		com.liferay.commerce.product.model.CommerceProductOption commerceProductOption) {
		return _commerceProductOptionLocalService.updateCommerceProductOption(commerceProductOption);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductOption updateCommerceProductOption(
		long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductOptionLocalService.updateCommerceProductOption(commerceProductOptionId,
			nameMap, descriptionMap, ddmFormFieldTypeName, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceProductOptionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceProductOptionLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceProductOptionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductOptionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductOptionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of commerce product options.
	*
	* @return the number of commerce product options
	*/
	@Override
	public int getCommerceProductOptionsCount() {
		return _commerceProductOptionLocalService.getCommerceProductOptionsCount();
	}

	@Override
	public int getCommerceProductOptionsCount(long groupId) {
		return _commerceProductOptionLocalService.getCommerceProductOptionsCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceProductOptionLocalService.getOSGiServiceIdentifier();
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
		return _commerceProductOptionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceProductOptionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceProductOptionLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns a range of all the commerce product options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @return the range of commerce product options
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductOption> getCommerceProductOptions(
		int start, int end) {
		return _commerceProductOptionLocalService.getCommerceProductOptions(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductOption> getCommerceProductOptions(
		long groupId, int start, int end) {
		return _commerceProductOptionLocalService.getCommerceProductOptions(groupId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductOption> getCommerceProductOptions(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductOption> orderByComparator) {
		return _commerceProductOptionLocalService.getCommerceProductOptions(groupId,
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
		return _commerceProductOptionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceProductOptionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public CommerceProductOptionLocalService getWrappedService() {
		return _commerceProductOptionLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceProductOptionLocalService commerceProductOptionLocalService) {
		_commerceProductOptionLocalService = commerceProductOptionLocalService;
	}

	private CommerceProductOptionLocalService _commerceProductOptionLocalService;
}