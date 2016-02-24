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

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.shopping.exception.NoSuchItemFieldException;
import com.liferay.shopping.model.ShoppingItemField;

/**
 * The persistence interface for the shopping item field service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.shopping.service.persistence.impl.ShoppingItemFieldPersistenceImpl
 * @see ShoppingItemFieldUtil
 * @generated
 */
@ProviderType
public interface ShoppingItemFieldPersistence extends BasePersistence<ShoppingItemField> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingItemFieldUtil} to access the shopping item field persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the shopping item fields where itemId = &#63;.
	*
	* @param itemId the item ID
	* @return the matching shopping item fields
	*/
	public java.util.List<ShoppingItemField> findByItemId(long itemId);

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
	public java.util.List<ShoppingItemField> findByItemId(long itemId,
		int start, int end);

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
	public java.util.List<ShoppingItemField> findByItemId(long itemId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemField> orderByComparator);

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
	public java.util.List<ShoppingItemField> findByItemId(long itemId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemField> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first shopping item field in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping item field
	* @throws NoSuchItemFieldException if a matching shopping item field could not be found
	*/
	public ShoppingItemField findByItemId_First(long itemId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemField> orderByComparator)
		throws NoSuchItemFieldException;

	/**
	* Returns the first shopping item field in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping item field, or <code>null</code> if a matching shopping item field could not be found
	*/
	public ShoppingItemField fetchByItemId_First(long itemId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemField> orderByComparator);

	/**
	* Returns the last shopping item field in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping item field
	* @throws NoSuchItemFieldException if a matching shopping item field could not be found
	*/
	public ShoppingItemField findByItemId_Last(long itemId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemField> orderByComparator)
		throws NoSuchItemFieldException;

	/**
	* Returns the last shopping item field in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping item field, or <code>null</code> if a matching shopping item field could not be found
	*/
	public ShoppingItemField fetchByItemId_Last(long itemId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemField> orderByComparator);

	/**
	* Returns the shopping item fields before and after the current shopping item field in the ordered set where itemId = &#63;.
	*
	* @param itemFieldId the primary key of the current shopping item field
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping item field
	* @throws NoSuchItemFieldException if a shopping item field with the primary key could not be found
	*/
	public ShoppingItemField[] findByItemId_PrevAndNext(long itemFieldId,
		long itemId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemField> orderByComparator)
		throws NoSuchItemFieldException;

	/**
	* Removes all the shopping item fields where itemId = &#63; from the database.
	*
	* @param itemId the item ID
	*/
	public void removeByItemId(long itemId);

	/**
	* Returns the number of shopping item fields where itemId = &#63;.
	*
	* @param itemId the item ID
	* @return the number of matching shopping item fields
	*/
	public int countByItemId(long itemId);

	/**
	* Caches the shopping item field in the entity cache if it is enabled.
	*
	* @param shoppingItemField the shopping item field
	*/
	public void cacheResult(ShoppingItemField shoppingItemField);

	/**
	* Caches the shopping item fields in the entity cache if it is enabled.
	*
	* @param shoppingItemFields the shopping item fields
	*/
	public void cacheResult(
		java.util.List<ShoppingItemField> shoppingItemFields);

	/**
	* Creates a new shopping item field with the primary key. Does not add the shopping item field to the database.
	*
	* @param itemFieldId the primary key for the new shopping item field
	* @return the new shopping item field
	*/
	public ShoppingItemField create(long itemFieldId);

	/**
	* Removes the shopping item field with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemFieldId the primary key of the shopping item field
	* @return the shopping item field that was removed
	* @throws NoSuchItemFieldException if a shopping item field with the primary key could not be found
	*/
	public ShoppingItemField remove(long itemFieldId)
		throws NoSuchItemFieldException;

	public ShoppingItemField updateImpl(ShoppingItemField shoppingItemField);

	/**
	* Returns the shopping item field with the primary key or throws a {@link NoSuchItemFieldException} if it could not be found.
	*
	* @param itemFieldId the primary key of the shopping item field
	* @return the shopping item field
	* @throws NoSuchItemFieldException if a shopping item field with the primary key could not be found
	*/
	public ShoppingItemField findByPrimaryKey(long itemFieldId)
		throws NoSuchItemFieldException;

	/**
	* Returns the shopping item field with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param itemFieldId the primary key of the shopping item field
	* @return the shopping item field, or <code>null</code> if a shopping item field with the primary key could not be found
	*/
	public ShoppingItemField fetchByPrimaryKey(long itemFieldId);

	@Override
	public java.util.Map<java.io.Serializable, ShoppingItemField> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the shopping item fields.
	*
	* @return the shopping item fields
	*/
	public java.util.List<ShoppingItemField> findAll();

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
	public java.util.List<ShoppingItemField> findAll(int start, int end);

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
	public java.util.List<ShoppingItemField> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemField> orderByComparator);

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
	public java.util.List<ShoppingItemField> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemField> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the shopping item fields from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of shopping item fields.
	*
	* @return the number of shopping item fields
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}