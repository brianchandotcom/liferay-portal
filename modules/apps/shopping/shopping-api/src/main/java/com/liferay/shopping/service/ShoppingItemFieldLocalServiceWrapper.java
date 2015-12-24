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

package com.liferay.shopping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ShoppingItemFieldLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemFieldLocalService
 * @generated
 */
@ProviderType
public class ShoppingItemFieldLocalServiceWrapper
	implements ShoppingItemFieldLocalService,
		ServiceWrapper<ShoppingItemFieldLocalService> {
	public ShoppingItemFieldLocalServiceWrapper(
		ShoppingItemFieldLocalService shoppingItemFieldLocalService) {
		_shoppingItemFieldLocalService = shoppingItemFieldLocalService;
	}

	/**
	* Adds the shopping item field to the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingItemField the shopping item field
	* @return the shopping item field that was added
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItemField addShoppingItemField(
		com.liferay.shopping.model.ShoppingItemField shoppingItemField) {
		return _shoppingItemFieldLocalService.addShoppingItemField(shoppingItemField);
	}

	/**
	* Creates a new shopping item field with the primary key. Does not add the shopping item field to the database.
	*
	* @param itemFieldId the primary key for the new shopping item field
	* @return the new shopping item field
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItemField createShoppingItemField(
		long itemFieldId) {
		return _shoppingItemFieldLocalService.createShoppingItemField(itemFieldId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemFieldLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the shopping item field with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemFieldId the primary key of the shopping item field
	* @return the shopping item field that was removed
	* @throws PortalException if a shopping item field with the primary key could not be found
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItemField deleteShoppingItemField(
		long itemFieldId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemFieldLocalService.deleteShoppingItemField(itemFieldId);
	}

	/**
	* Deletes the shopping item field from the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingItemField the shopping item field
	* @return the shopping item field that was removed
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItemField deleteShoppingItemField(
		com.liferay.shopping.model.ShoppingItemField shoppingItemField) {
		return _shoppingItemFieldLocalService.deleteShoppingItemField(shoppingItemField);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _shoppingItemFieldLocalService.dynamicQuery();
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
		return _shoppingItemFieldLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _shoppingItemFieldLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _shoppingItemFieldLocalService.dynamicQuery(dynamicQuery, start,
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
		return _shoppingItemFieldLocalService.dynamicQueryCount(dynamicQuery);
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
		return _shoppingItemFieldLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.shopping.model.ShoppingItemField fetchShoppingItemField(
		long itemFieldId) {
		return _shoppingItemFieldLocalService.fetchShoppingItemField(itemFieldId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _shoppingItemFieldLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _shoppingItemFieldLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingItemField> getItemFields(
		long itemId) {
		return _shoppingItemFieldLocalService.getItemFields(itemId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _shoppingItemFieldLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemFieldLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the shopping item field with the primary key.
	*
	* @param itemFieldId the primary key of the shopping item field
	* @return the shopping item field
	* @throws PortalException if a shopping item field with the primary key could not be found
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItemField getShoppingItemField(
		long itemFieldId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemFieldLocalService.getShoppingItemField(itemFieldId);
	}

	/**
	* Returns a range of all the shopping item fields.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping item fields
	* @param end the upper bound of the range of shopping item fields (not inclusive)
	* @return the range of shopping item fields
	*/
	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingItemField> getShoppingItemFields(
		int start, int end) {
		return _shoppingItemFieldLocalService.getShoppingItemFields(start, end);
	}

	/**
	* Returns the number of shopping item fields.
	*
	* @return the number of shopping item fields
	*/
	@Override
	public int getShoppingItemFieldsCount() {
		return _shoppingItemFieldLocalService.getShoppingItemFieldsCount();
	}

	/**
	* Updates the shopping item field in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param shoppingItemField the shopping item field
	* @return the shopping item field that was updated
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItemField updateShoppingItemField(
		com.liferay.shopping.model.ShoppingItemField shoppingItemField) {
		return _shoppingItemFieldLocalService.updateShoppingItemField(shoppingItemField);
	}

	@Override
	public ShoppingItemFieldLocalService getWrappedService() {
		return _shoppingItemFieldLocalService;
	}

	@Override
	public void setWrappedService(
		ShoppingItemFieldLocalService shoppingItemFieldLocalService) {
		_shoppingItemFieldLocalService = shoppingItemFieldLocalService;
	}

	private ShoppingItemFieldLocalService _shoppingItemFieldLocalService;
}