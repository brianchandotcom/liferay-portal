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

package com.liferay.shopping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import com.liferay.shopping.model.ShoppingItemField;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the shopping item field service. This utility wraps {@link com.liferay.shopping.service.persistence.impl.ShoppingItemFieldPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemFieldPersistence
 * @see com.liferay.shopping.service.persistence.impl.ShoppingItemFieldPersistenceImpl
 * @generated
 */
@ProviderType
public class ShoppingItemFieldUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(ShoppingItemField shoppingItemField) {
		getPersistence().clearCache(shoppingItemField);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ShoppingItemField> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ShoppingItemField> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ShoppingItemField> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ShoppingItemField> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ShoppingItemField update(ShoppingItemField shoppingItemField) {
		return getPersistence().update(shoppingItemField);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ShoppingItemField update(
		ShoppingItemField shoppingItemField, ServiceContext serviceContext) {
		return getPersistence().update(shoppingItemField, serviceContext);
	}

	/**
	* Returns all the shopping item fields where itemId = &#63;.
	*
	* @param itemId the item ID
	* @return the matching shopping item fields
	*/
	public static List<ShoppingItemField> findByItemId(long itemId) {
		return getPersistence().findByItemId(itemId);
	}

	/**
	* Returns a range of all the shopping item fields where itemId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param itemId the item ID
	* @param start the lower bound of the range of shopping item fields
	* @param end the upper bound of the range of shopping item fields (not inclusive)
	* @return the range of matching shopping item fields
	*/
	public static List<ShoppingItemField> findByItemId(long itemId, int start,
		int end) {
		return getPersistence().findByItemId(itemId, start, end);
	}

	/**
	* Returns an ordered range of all the shopping item fields where itemId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param itemId the item ID
	* @param start the lower bound of the range of shopping item fields
	* @param end the upper bound of the range of shopping item fields (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping item fields
	*/
	public static List<ShoppingItemField> findByItemId(long itemId, int start,
		int end, OrderByComparator<ShoppingItemField> orderByComparator) {
		return getPersistence()
				   .findByItemId(itemId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the shopping item fields where itemId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param itemId the item ID
	* @param start the lower bound of the range of shopping item fields
	* @param end the upper bound of the range of shopping item fields (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching shopping item fields
	*/
	public static List<ShoppingItemField> findByItemId(long itemId, int start,
		int end, OrderByComparator<ShoppingItemField> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByItemId(itemId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first shopping item field in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping item field
	* @throws com.liferay.shopping.NoSuchItemFieldException if a matching shopping item field could not be found
	*/
	public static ShoppingItemField findByItemId_First(long itemId,
		OrderByComparator<ShoppingItemField> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchItemFieldException {
		return getPersistence().findByItemId_First(itemId, orderByComparator);
	}

	/**
	* Returns the first shopping item field in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping item field, or <code>null</code> if a matching shopping item field could not be found
	*/
	public static ShoppingItemField fetchByItemId_First(long itemId,
		OrderByComparator<ShoppingItemField> orderByComparator) {
		return getPersistence().fetchByItemId_First(itemId, orderByComparator);
	}

	/**
	* Returns the last shopping item field in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping item field
	* @throws com.liferay.shopping.NoSuchItemFieldException if a matching shopping item field could not be found
	*/
	public static ShoppingItemField findByItemId_Last(long itemId,
		OrderByComparator<ShoppingItemField> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchItemFieldException {
		return getPersistence().findByItemId_Last(itemId, orderByComparator);
	}

	/**
	* Returns the last shopping item field in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping item field, or <code>null</code> if a matching shopping item field could not be found
	*/
	public static ShoppingItemField fetchByItemId_Last(long itemId,
		OrderByComparator<ShoppingItemField> orderByComparator) {
		return getPersistence().fetchByItemId_Last(itemId, orderByComparator);
	}

	/**
	* Returns the shopping item fields before and after the current shopping item field in the ordered set where itemId = &#63;.
	*
	* @param itemFieldId the primary key of the current shopping item field
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping item field
	* @throws com.liferay.shopping.NoSuchItemFieldException if a shopping item field with the primary key could not be found
	*/
	public static ShoppingItemField[] findByItemId_PrevAndNext(
		long itemFieldId, long itemId,
		OrderByComparator<ShoppingItemField> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchItemFieldException {
		return getPersistence()
				   .findByItemId_PrevAndNext(itemFieldId, itemId,
			orderByComparator);
	}

	/**
	* Removes all the shopping item fields where itemId = &#63; from the database.
	*
	* @param itemId the item ID
	*/
	public static void removeByItemId(long itemId) {
		getPersistence().removeByItemId(itemId);
	}

	/**
	* Returns the number of shopping item fields where itemId = &#63;.
	*
	* @param itemId the item ID
	* @return the number of matching shopping item fields
	*/
	public static int countByItemId(long itemId) {
		return getPersistence().countByItemId(itemId);
	}

	/**
	* Caches the shopping item field in the entity cache if it is enabled.
	*
	* @param shoppingItemField the shopping item field
	*/
	public static void cacheResult(ShoppingItemField shoppingItemField) {
		getPersistence().cacheResult(shoppingItemField);
	}

	/**
	* Caches the shopping item fields in the entity cache if it is enabled.
	*
	* @param shoppingItemFields the shopping item fields
	*/
	public static void cacheResult(List<ShoppingItemField> shoppingItemFields) {
		getPersistence().cacheResult(shoppingItemFields);
	}

	/**
	* Creates a new shopping item field with the primary key. Does not add the shopping item field to the database.
	*
	* @param itemFieldId the primary key for the new shopping item field
	* @return the new shopping item field
	*/
	public static ShoppingItemField create(long itemFieldId) {
		return getPersistence().create(itemFieldId);
	}

	/**
	* Removes the shopping item field with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemFieldId the primary key of the shopping item field
	* @return the shopping item field that was removed
	* @throws com.liferay.shopping.NoSuchItemFieldException if a shopping item field with the primary key could not be found
	*/
	public static ShoppingItemField remove(long itemFieldId)
		throws com.liferay.shopping.exception.NoSuchItemFieldException {
		return getPersistence().remove(itemFieldId);
	}

	public static ShoppingItemField updateImpl(
		ShoppingItemField shoppingItemField) {
		return getPersistence().updateImpl(shoppingItemField);
	}

	/**
	* Returns the shopping item field with the primary key or throws a {@link com.liferay.shopping.NoSuchItemFieldException} if it could not be found.
	*
	* @param itemFieldId the primary key of the shopping item field
	* @return the shopping item field
	* @throws com.liferay.shopping.NoSuchItemFieldException if a shopping item field with the primary key could not be found
	*/
	public static ShoppingItemField findByPrimaryKey(long itemFieldId)
		throws com.liferay.shopping.exception.NoSuchItemFieldException {
		return getPersistence().findByPrimaryKey(itemFieldId);
	}

	/**
	* Returns the shopping item field with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param itemFieldId the primary key of the shopping item field
	* @return the shopping item field, or <code>null</code> if a shopping item field with the primary key could not be found
	*/
	public static ShoppingItemField fetchByPrimaryKey(long itemFieldId) {
		return getPersistence().fetchByPrimaryKey(itemFieldId);
	}

	public static java.util.Map<java.io.Serializable, ShoppingItemField> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the shopping item fields.
	*
	* @return the shopping item fields
	*/
	public static List<ShoppingItemField> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the shopping item fields.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping item fields
	* @param end the upper bound of the range of shopping item fields (not inclusive)
	* @return the range of shopping item fields
	*/
	public static List<ShoppingItemField> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the shopping item fields.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping item fields
	* @param end the upper bound of the range of shopping item fields (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shopping item fields
	*/
	public static List<ShoppingItemField> findAll(int start, int end,
		OrderByComparator<ShoppingItemField> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the shopping item fields.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping item fields
	* @param end the upper bound of the range of shopping item fields (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of shopping item fields
	*/
	public static List<ShoppingItemField> findAll(int start, int end,
		OrderByComparator<ShoppingItemField> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the shopping item fields from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of shopping item fields.
	*
	* @return the number of shopping item fields
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static ShoppingItemFieldPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ShoppingItemFieldPersistence, ShoppingItemFieldPersistence> _serviceTracker =
		ServiceTrackerFactory.open(ShoppingItemFieldPersistence.class);
}