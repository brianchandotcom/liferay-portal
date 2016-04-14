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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.shopping.model.ShoppingItem;
import com.liferay.shopping.model.ShoppingItemField;
import com.liferay.shopping.model.ShoppingItemPrice;

import java.io.File;
import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for ShoppingItem. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemLocalServiceUtil
 * @see com.liferay.shopping.service.base.ShoppingItemLocalServiceBaseImpl
 * @see com.liferay.shopping.service.impl.ShoppingItemLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ShoppingItemLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingItemLocalServiceUtil} to access the shopping item local service. Add custom service methods to {@link com.liferay.shopping.service.impl.ShoppingItemLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	public ShoppingItem addItem(long userId, long groupId, long categoryId,
		java.lang.String sku, java.lang.String name,
		java.lang.String description, java.lang.String properties,
		java.lang.String fieldsQuantities, boolean requiresShipping,
		int stockQuantity, boolean featured, java.lang.Boolean sale,
		boolean smallImage, java.lang.String smallImageURL,
		File smallImageFile, boolean mediumImage,
		java.lang.String mediumImageURL, File mediumImageFile,
		boolean largeImage, java.lang.String largeImageURL,
		File largeImageFile, List<ShoppingItemField> itemFields,
		List<ShoppingItemPrice> itemPrices, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Adds the shopping item to the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingItem the shopping item
	* @return the shopping item that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ShoppingItem addShoppingItem(ShoppingItem shoppingItem);

	/**
	* Creates a new shopping item with the primary key. Does not add the shopping item to the database.
	*
	* @param itemId the primary key for the new shopping item
	* @return the new shopping item
	*/
	public ShoppingItem createShoppingItem(long itemId);

	/**
	* Deletes the shopping item from the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingItem the shopping item
	* @return the shopping item that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public ShoppingItem deleteShoppingItem(ShoppingItem shoppingItem);

	/**
	* Deletes the shopping item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemId the primary key of the shopping item
	* @return the shopping item that was removed
	* @throws PortalException if a shopping item with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public ShoppingItem deleteShoppingItem(long itemId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingItem fetchShoppingItem(long itemId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingItem getItem(long companyId, java.lang.String sku)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingItem getItem(long itemId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingItem getItemByLargeImageId(long largeImageId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingItem getItemByMediumImageId(long mediumImageId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingItem getItemBySmallImageId(long smallImageId)
		throws PortalException;

	/**
	* Returns the shopping item with the primary key.
	*
	* @param itemId the primary key of the shopping item
	* @return the shopping item
	* @throws PortalException if a shopping item with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingItem getShoppingItem(long itemId) throws PortalException;

	public ShoppingItem updateItem(long userId, long itemId, long groupId,
		long categoryId, java.lang.String sku, java.lang.String name,
		java.lang.String description, java.lang.String properties,
		java.lang.String fieldsQuantities, boolean requiresShipping,
		int stockQuantity, boolean featured, java.lang.Boolean sale,
		boolean smallImage, java.lang.String smallImageURL,
		File smallImageFile, boolean mediumImage,
		java.lang.String mediumImageURL, File mediumImageFile,
		boolean largeImage, java.lang.String largeImageURL,
		File largeImageFile, List<ShoppingItemField> itemFields,
		List<ShoppingItemPrice> itemPrices, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Updates the shopping item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param shoppingItem the shopping item
	* @return the shopping item that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ShoppingItem updateShoppingItem(ShoppingItem shoppingItem);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingItem[] getItemsPrevAndNext(long itemId,
		OrderByComparator<ShoppingItem> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCategoriesItemsCount(long groupId,
		List<java.lang.Long> categoryIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getItemsCount(long groupId, long categoryId);

	/**
	* Returns the number of shopping items.
	*
	* @return the number of shopping items
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getShoppingItemsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long groupId, long[] categoryIds,
		java.lang.String keywords);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long groupId, long[] categoryIds,
		java.lang.String keywords, OrderByComparator<ShoppingItem> obc);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingItem> getFeaturedItems(long groupId, long categoryId,
		int numOfItems);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingItem> getItems(long groupId, long categoryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingItem> getItems(long groupId, long categoryId,
		int start, int end, OrderByComparator<ShoppingItem> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingItem> getSaleItems(long groupId, long categoryId,
		int numOfItems);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingItem> getShoppingItems(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingItem> search(long groupId, long[] categoryIds,
		java.lang.String keywords, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingItem> search(long groupId, long[] categoryIds,
		java.lang.String keywords, int start, int end,
		OrderByComparator<ShoppingItem> obc);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	public void addItemResources(ShoppingItem item,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addItemResources(ShoppingItem item,
		ModelPermissions modelPermissions) throws PortalException;

	public void addItemResources(long itemId, boolean addGroupPermissions,
		boolean addGuestPermissions) throws PortalException;

	public void addItemResources(long itemId, ModelPermissions modelPermissions)
		throws PortalException;

	public void deleteItem(ShoppingItem item) throws PortalException;

	public void deleteItem(long itemId) throws PortalException;

	public void deleteItems(long groupId, long categoryId)
		throws PortalException;
}