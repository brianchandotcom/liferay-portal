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
 * Provides a wrapper for {@link ShoppingItemLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemLocalService
 * @generated
 */
@ProviderType
public class ShoppingItemLocalServiceWrapper implements ShoppingItemLocalService,
	ServiceWrapper<ShoppingItemLocalService> {
	public ShoppingItemLocalServiceWrapper(
		ShoppingItemLocalService shoppingItemLocalService) {
		_shoppingItemLocalService = shoppingItemLocalService;
	}

	@Override
	public com.liferay.shopping.model.ShoppingItem addItem(long userId,
		long groupId, long categoryId, java.lang.String sku,
		java.lang.String name, java.lang.String description,
		java.lang.String properties, java.lang.String fieldsQuantities,
		boolean requiresShipping, int stockQuantity, boolean featured,
		java.lang.Boolean sale, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallImageFile,
		boolean mediumImage, java.lang.String mediumImageURL,
		java.io.File mediumImageFile, boolean largeImage,
		java.lang.String largeImageURL, java.io.File largeImageFile,
		java.util.List<com.liferay.shopping.model.ShoppingItemField> itemFields,
		java.util.List<com.liferay.shopping.model.ShoppingItemPrice> itemPrices,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemLocalService.addItem(userId, groupId, categoryId,
			sku, name, description, properties, fieldsQuantities,
			requiresShipping, stockQuantity, featured, sale, smallImage,
			smallImageURL, smallImageFile, mediumImage, mediumImageURL,
			mediumImageFile, largeImage, largeImageURL, largeImageFile,
			itemFields, itemPrices, serviceContext);
	}

	@Override
	public void addItemResources(com.liferay.shopping.model.ShoppingItem item,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingItemLocalService.addItemResources(item, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addItemResources(com.liferay.shopping.model.ShoppingItem item,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingItemLocalService.addItemResources(item, modelPermissions);
	}

	@Override
	public void addItemResources(long itemId, boolean addGroupPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingItemLocalService.addItemResources(itemId, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addItemResources(long itemId,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingItemLocalService.addItemResources(itemId, modelPermissions);
	}

	/**
	* Adds the shopping item to the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingItem the shopping item
	* @return the shopping item that was added
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItem addShoppingItem(
		com.liferay.shopping.model.ShoppingItem shoppingItem) {
		return _shoppingItemLocalService.addShoppingItem(shoppingItem);
	}

	/**
	* Creates a new shopping item with the primary key. Does not add the shopping item to the database.
	*
	* @param itemId the primary key for the new shopping item
	* @return the new shopping item
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItem createShoppingItem(
		long itemId) {
		return _shoppingItemLocalService.createShoppingItem(itemId);
	}

	@Override
	public void deleteItem(com.liferay.shopping.model.ShoppingItem item)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingItemLocalService.deleteItem(item);
	}

	@Override
	public void deleteItem(long itemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingItemLocalService.deleteItem(itemId);
	}

	@Override
	public void deleteItems(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingItemLocalService.deleteItems(groupId, categoryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the shopping item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemId the primary key of the shopping item
	* @return the shopping item that was removed
	* @throws PortalException if a shopping item with the primary key could not be found
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItem deleteShoppingItem(
		long itemId) throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemLocalService.deleteShoppingItem(itemId);
	}

	/**
	* Deletes the shopping item from the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingItem the shopping item
	* @return the shopping item that was removed
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItem deleteShoppingItem(
		com.liferay.shopping.model.ShoppingItem shoppingItem) {
		return _shoppingItemLocalService.deleteShoppingItem(shoppingItem);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _shoppingItemLocalService.dynamicQuery();
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
		return _shoppingItemLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _shoppingItemLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _shoppingItemLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _shoppingItemLocalService.dynamicQueryCount(dynamicQuery);
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
		return _shoppingItemLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.shopping.model.ShoppingItem fetchShoppingItem(
		long itemId) {
		return _shoppingItemLocalService.fetchShoppingItem(itemId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _shoppingItemLocalService.getActionableDynamicQuery();
	}

	@Override
	public int getCategoriesItemsCount(long groupId,
		java.util.List<java.lang.Long> categoryIds) {
		return _shoppingItemLocalService.getCategoriesItemsCount(groupId,
			categoryIds);
	}

	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingItem> getFeaturedItems(
		long groupId, long categoryId, int numOfItems) {
		return _shoppingItemLocalService.getFeaturedItems(groupId, categoryId,
			numOfItems);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _shoppingItemLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.shopping.model.ShoppingItem getItem(long companyId,
		java.lang.String sku)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemLocalService.getItem(companyId, sku);
	}

	@Override
	public com.liferay.shopping.model.ShoppingItem getItem(long itemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemLocalService.getItem(itemId);
	}

	@Override
	public com.liferay.shopping.model.ShoppingItem getItemByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemLocalService.getItemByLargeImageId(largeImageId);
	}

	@Override
	public com.liferay.shopping.model.ShoppingItem getItemByMediumImageId(
		long mediumImageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemLocalService.getItemByMediumImageId(mediumImageId);
	}

	@Override
	public com.liferay.shopping.model.ShoppingItem getItemBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemLocalService.getItemBySmallImageId(smallImageId);
	}

	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingItem> getItems(
		long groupId, long categoryId) {
		return _shoppingItemLocalService.getItems(groupId, categoryId);
	}

	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingItem> getItems(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.shopping.model.ShoppingItem> obc) {
		return _shoppingItemLocalService.getItems(groupId, categoryId, start,
			end, obc);
	}

	@Override
	public int getItemsCount(long groupId, long categoryId) {
		return _shoppingItemLocalService.getItemsCount(groupId, categoryId);
	}

	@Override
	public com.liferay.shopping.model.ShoppingItem[] getItemsPrevAndNext(
		long itemId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.shopping.model.ShoppingItem> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemLocalService.getItemsPrevAndNext(itemId, obc);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _shoppingItemLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingItem> getSaleItems(
		long groupId, long categoryId, int numOfItems) {
		return _shoppingItemLocalService.getSaleItems(groupId, categoryId,
			numOfItems);
	}

	/**
	* Returns the shopping item with the primary key.
	*
	* @param itemId the primary key of the shopping item
	* @return the shopping item
	* @throws PortalException if a shopping item with the primary key could not be found
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItem getShoppingItem(long itemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemLocalService.getShoppingItem(itemId);
	}

	/**
	* Returns a range of all the shopping items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping items
	* @param end the upper bound of the range of shopping items (not inclusive)
	* @return the range of shopping items
	*/
	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingItem> getShoppingItems(
		int start, int end) {
		return _shoppingItemLocalService.getShoppingItems(start, end);
	}

	/**
	* Returns the number of shopping items.
	*
	* @return the number of shopping items
	*/
	@Override
	public int getShoppingItemsCount() {
		return _shoppingItemLocalService.getShoppingItemsCount();
	}

	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingItem> search(
		long groupId, long[] categoryIds, java.lang.String keywords, int start,
		int end) {
		return _shoppingItemLocalService.search(groupId, categoryIds, keywords,
			start, end);
	}

	@Override
	public int searchCount(long groupId, long[] categoryIds,
		java.lang.String keywords) {
		return _shoppingItemLocalService.searchCount(groupId, categoryIds,
			keywords);
	}

	@Override
	public com.liferay.shopping.model.ShoppingItem updateItem(long userId,
		long itemId, long groupId, long categoryId, java.lang.String sku,
		java.lang.String name, java.lang.String description,
		java.lang.String properties, java.lang.String fieldsQuantities,
		boolean requiresShipping, int stockQuantity, boolean featured,
		java.lang.Boolean sale, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallImageFile,
		boolean mediumImage, java.lang.String mediumImageURL,
		java.io.File mediumImageFile, boolean largeImage,
		java.lang.String largeImageURL, java.io.File largeImageFile,
		java.util.List<com.liferay.shopping.model.ShoppingItemField> itemFields,
		java.util.List<com.liferay.shopping.model.ShoppingItemPrice> itemPrices,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemLocalService.updateItem(userId, itemId, groupId,
			categoryId, sku, name, description, properties, fieldsQuantities,
			requiresShipping, stockQuantity, featured, sale, smallImage,
			smallImageURL, smallImageFile, mediumImage, mediumImageURL,
			mediumImageFile, largeImage, largeImageURL, largeImageFile,
			itemFields, itemPrices, serviceContext);
	}

	/**
	* Updates the shopping item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param shoppingItem the shopping item
	* @return the shopping item that was updated
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItem updateShoppingItem(
		com.liferay.shopping.model.ShoppingItem shoppingItem) {
		return _shoppingItemLocalService.updateShoppingItem(shoppingItem);
	}

	@Override
	public ShoppingItemLocalService getWrappedService() {
		return _shoppingItemLocalService;
	}

	@Override
	public void setWrappedService(
		ShoppingItemLocalService shoppingItemLocalService) {
		_shoppingItemLocalService = shoppingItemLocalService;
	}

	private ShoppingItemLocalService _shoppingItemLocalService;
}