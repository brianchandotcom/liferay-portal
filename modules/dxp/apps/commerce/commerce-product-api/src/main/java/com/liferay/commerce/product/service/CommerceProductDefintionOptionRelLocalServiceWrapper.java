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
 * Provides a wrapper for {@link CommerceProductDefintionOptionRelLocalService}.
 *
 * @author Marco Leo
 * @see CommerceProductDefintionOptionRelLocalService
 * @generated
 */
@ProviderType
public class CommerceProductDefintionOptionRelLocalServiceWrapper
	implements CommerceProductDefintionOptionRelLocalService,
		ServiceWrapper<CommerceProductDefintionOptionRelLocalService> {
	public CommerceProductDefintionOptionRelLocalServiceWrapper(
		CommerceProductDefintionOptionRelLocalService commerceProductDefintionOptionRelLocalService) {
		_commerceProductDefintionOptionRelLocalService = commerceProductDefintionOptionRelLocalService;
	}

	/**
	* Adds the commerce product defintion option rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefintionOptionRel the commerce product defintion option rel
	* @return the commerce product defintion option rel that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefintionOptionRel addCommerceProductDefintionOptionRel(
		com.liferay.commerce.product.model.CommerceProductDefintionOptionRel commerceProductDefintionOptionRel) {
		return _commerceProductDefintionOptionRelLocalService.addCommerceProductDefintionOptionRel(commerceProductDefintionOptionRel);
	}

	/**
	* Creates a new commerce product defintion option rel with the primary key. Does not add the commerce product defintion option rel to the database.
	*
	* @param commerceProductDefintionOptionRelId the primary key for the new commerce product defintion option rel
	* @return the new commerce product defintion option rel
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefintionOptionRel createCommerceProductDefintionOptionRel(
		long commerceProductDefintionOptionRelId) {
		return _commerceProductDefintionOptionRelLocalService.createCommerceProductDefintionOptionRel(commerceProductDefintionOptionRelId);
	}

	/**
	* Deletes the commerce product defintion option rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefintionOptionRel the commerce product defintion option rel
	* @return the commerce product defintion option rel that was removed
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefintionOptionRel deleteCommerceProductDefintionOptionRel(
		com.liferay.commerce.product.model.CommerceProductDefintionOptionRel commerceProductDefintionOptionRel) {
		return _commerceProductDefintionOptionRelLocalService.deleteCommerceProductDefintionOptionRel(commerceProductDefintionOptionRel);
	}

	/**
	* Deletes the commerce product defintion option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefintionOptionRelId the primary key of the commerce product defintion option rel
	* @return the commerce product defintion option rel that was removed
	* @throws PortalException if a commerce product defintion option rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefintionOptionRel deleteCommerceProductDefintionOptionRel(
		long commerceProductDefintionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefintionOptionRelLocalService.deleteCommerceProductDefintionOptionRel(commerceProductDefintionOptionRelId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefintionOptionRel fetchCommerceProductDefintionOptionRel(
		long commerceProductDefintionOptionRelId) {
		return _commerceProductDefintionOptionRelLocalService.fetchCommerceProductDefintionOptionRel(commerceProductDefintionOptionRelId);
	}

	/**
	* Returns the commerce product defintion option rel with the primary key.
	*
	* @param commerceProductDefintionOptionRelId the primary key of the commerce product defintion option rel
	* @return the commerce product defintion option rel
	* @throws PortalException if a commerce product defintion option rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefintionOptionRel getCommerceProductDefintionOptionRel(
		long commerceProductDefintionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefintionOptionRelLocalService.getCommerceProductDefintionOptionRel(commerceProductDefintionOptionRelId);
	}

	/**
	* Updates the commerce product defintion option rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefintionOptionRel the commerce product defintion option rel
	* @return the commerce product defintion option rel that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefintionOptionRel updateCommerceProductDefintionOptionRel(
		com.liferay.commerce.product.model.CommerceProductDefintionOptionRel commerceProductDefintionOptionRel) {
		return _commerceProductDefintionOptionRelLocalService.updateCommerceProductDefintionOptionRel(commerceProductDefintionOptionRel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceProductDefintionOptionRelLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceProductDefintionOptionRelLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceProductDefintionOptionRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefintionOptionRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefintionOptionRelLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of commerce product defintion option rels.
	*
	* @return the number of commerce product defintion option rels
	*/
	@Override
	public int getCommerceProductDefintionOptionRelsCount() {
		return _commerceProductDefintionOptionRelLocalService.getCommerceProductDefintionOptionRelsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceProductDefintionOptionRelLocalService.getOSGiServiceIdentifier();
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
		return _commerceProductDefintionOptionRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceProductDefintionOptionRelLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceProductDefintionOptionRelLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns a range of all the commerce product defintion option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefintionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product defintion option rels
	* @param end the upper bound of the range of commerce product defintion option rels (not inclusive)
	* @return the range of commerce product defintion option rels
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefintionOptionRel> getCommerceProductDefintionOptionRels(
		int start, int end) {
		return _commerceProductDefintionOptionRelLocalService.getCommerceProductDefintionOptionRels(start,
			end);
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
		return _commerceProductDefintionOptionRelLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceProductDefintionOptionRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public CommerceProductDefintionOptionRelLocalService getWrappedService() {
		return _commerceProductDefintionOptionRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceProductDefintionOptionRelLocalService commerceProductDefintionOptionRelLocalService) {
		_commerceProductDefintionOptionRelLocalService = commerceProductDefintionOptionRelLocalService;
	}

	private CommerceProductDefintionOptionRelLocalService _commerceProductDefintionOptionRelLocalService;
}