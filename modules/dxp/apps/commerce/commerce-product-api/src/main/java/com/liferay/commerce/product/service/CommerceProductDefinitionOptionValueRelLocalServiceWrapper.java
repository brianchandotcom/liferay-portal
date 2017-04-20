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
 * Provides a wrapper for {@link CommerceProductDefinitionOptionValueRelLocalService}.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionValueRelLocalService
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionValueRelLocalServiceWrapper
	implements CommerceProductDefinitionOptionValueRelLocalService,
		ServiceWrapper<CommerceProductDefinitionOptionValueRelLocalService> {
	public CommerceProductDefinitionOptionValueRelLocalServiceWrapper(
		CommerceProductDefinitionOptionValueRelLocalService commerceProductDefinitionOptionValueRelLocalService) {
		_commerceProductDefinitionOptionValueRelLocalService = commerceProductDefinitionOptionValueRelLocalService;
	}

	/**
	* Adds the commerce product definition option value rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionValueRel the commerce product definition option value rel
	* @return the commerce product definition option value rel that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel addCommerceProductDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
		return _commerceProductDefinitionOptionValueRelLocalService.addCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRel);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel addCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionRelId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionValueRelLocalService.addCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionRelId,
			titleMap, priority, serviceContext);
	}

	/**
	* Creates a new commerce product definition option value rel with the primary key. Does not add the commerce product definition option value rel to the database.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key for the new commerce product definition option value rel
	* @return the new commerce product definition option value rel
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel createCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId) {
		return _commerceProductDefinitionOptionValueRelLocalService.createCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId);
	}

	/**
	* Deletes the commerce product definition option value rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionValueRel the commerce product definition option value rel
	* @return the commerce product definition option value rel that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel deleteCommerceProductDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionValueRelLocalService.deleteCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRel);
	}

	/**
	* Deletes the commerce product definition option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the commerce product definition option value rel
	* @return the commerce product definition option value rel that was removed
	* @throws PortalException if a commerce product definition option value rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel deleteCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionValueRelLocalService.deleteCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel fetchCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId) {
		return _commerceProductDefinitionOptionValueRelLocalService.fetchCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId);
	}

	/**
	* Returns the commerce product definition option value rel with the primary key.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the commerce product definition option value rel
	* @return the commerce product definition option value rel
	* @throws PortalException if a commerce product definition option value rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel getCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionValueRelLocalService.getCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId);
	}

	/**
	* Updates the commerce product definition option value rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionValueRel the commerce product definition option value rel
	* @return the commerce product definition option value rel that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel updateCommerceProductDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
		return _commerceProductDefinitionOptionValueRelLocalService.updateCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRel);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel updateCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionValueRelLocalService.updateCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId,
			titleMap, priority, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceProductDefinitionOptionValueRelLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceProductDefinitionOptionValueRelLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceProductDefinitionOptionValueRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionValueRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionValueRelLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of commerce product definition option value rels.
	*
	* @return the number of commerce product definition option value rels
	*/
	@Override
	public int getCommerceProductDefinitionOptionValueRelsCount() {
		return _commerceProductDefinitionOptionValueRelLocalService.getCommerceProductDefinitionOptionValueRelsCount();
	}

	@Override
	public int getCommerceProductDefinitionOptionValueRelsCount(
		long commerceProductDefinitionOptionRelId) {
		return _commerceProductDefinitionOptionValueRelLocalService.getCommerceProductDefinitionOptionValueRelsCount(commerceProductDefinitionOptionRelId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceProductDefinitionOptionValueRelLocalService.getOSGiServiceIdentifier();
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
		return _commerceProductDefinitionOptionValueRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceProductDefinitionOptionValueRelLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceProductDefinitionOptionValueRelLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns a range of all the commerce product definition option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @return the range of commerce product definition option value rels
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRels(
		int start, int end) {
		return _commerceProductDefinitionOptionValueRelLocalService.getCommerceProductDefinitionOptionValueRels(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRels(
		long commerceProductDefinitionOptionRelId, int start, int end) {
		return _commerceProductDefinitionOptionValueRelLocalService.getCommerceProductDefinitionOptionValueRels(commerceProductDefinitionOptionRelId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRels(
		long commerceProductDefinitionOptionRelId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> orderByComparator) {
		return _commerceProductDefinitionOptionValueRelLocalService.getCommerceProductDefinitionOptionValueRels(commerceProductDefinitionOptionRelId,
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
		return _commerceProductDefinitionOptionValueRelLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceProductDefinitionOptionValueRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public CommerceProductDefinitionOptionValueRelLocalService getWrappedService() {
		return _commerceProductDefinitionOptionValueRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceProductDefinitionOptionValueRelLocalService commerceProductDefinitionOptionValueRelLocalService) {
		_commerceProductDefinitionOptionValueRelLocalService = commerceProductDefinitionOptionValueRelLocalService;
	}

	private CommerceProductDefinitionOptionValueRelLocalService _commerceProductDefinitionOptionValueRelLocalService;
}