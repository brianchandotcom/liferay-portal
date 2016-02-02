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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.exception.NoSuchListTypeException;
import com.liferay.portal.model.ListType;

/**
 * The persistence interface for the list type service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.ListTypePersistenceImpl
 * @see ListTypeUtil
 * @generated
 */
@ProviderType
public interface ListTypePersistence extends BasePersistence<ListType> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ListTypeUtil} to access the list type persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the list types where type = &#63;.
	*
	* @param type the type
	* @return the matching list types
	*/
	public java.util.List<ListType> findByType(java.lang.String type);

	/**
	* Returns a range of all the list types where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ListTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of list types
	* @param end the upper bound of the range of list types (not inclusive)
	* @return the range of matching list types
	*/
	public java.util.List<ListType> findByType(java.lang.String type,
		int start, int end);

	/**
	* Returns an ordered range of all the list types where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ListTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of list types
	* @param end the upper bound of the range of list types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching list types
	*/
	public java.util.List<ListType> findByType(java.lang.String type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListType> orderByComparator);

	/**
	* Returns an ordered range of all the list types where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ListTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of list types
	* @param end the upper bound of the range of list types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching list types
	*/
	public java.util.List<ListType> findByType(java.lang.String type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListType> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first list type in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching list type
	* @throws NoSuchListTypeException if a matching list type could not be found
	*/
	public ListType findByType_First(java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<ListType> orderByComparator)
		throws NoSuchListTypeException;

	/**
	* Returns the first list type in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching list type, or <code>null</code> if a matching list type could not be found
	*/
	public ListType fetchByType_First(java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<ListType> orderByComparator);

	/**
	* Returns the last list type in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching list type
	* @throws NoSuchListTypeException if a matching list type could not be found
	*/
	public ListType findByType_Last(java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<ListType> orderByComparator)
		throws NoSuchListTypeException;

	/**
	* Returns the last list type in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching list type, or <code>null</code> if a matching list type could not be found
	*/
	public ListType fetchByType_Last(java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<ListType> orderByComparator);

	/**
	* Returns the list types before and after the current list type in the ordered set where type = &#63;.
	*
	* @param listTypeId the primary key of the current list type
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next list type
	* @throws NoSuchListTypeException if a list type with the primary key could not be found
	*/
	public ListType[] findByType_PrevAndNext(long listTypeId,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<ListType> orderByComparator)
		throws NoSuchListTypeException;

	/**
	* Removes all the list types where type = &#63; from the database.
	*
	* @param type the type
	*/
	public void removeByType(java.lang.String type);

	/**
	* Returns the number of list types where type = &#63;.
	*
	* @param type the type
	* @return the number of matching list types
	*/
	public int countByType(java.lang.String type);

	/**
	* Returns the list type where name = &#63; and type = &#63; or throws a {@link NoSuchListTypeException} if it could not be found.
	*
	* @param name the name
	* @param type the type
	* @return the matching list type
	* @throws NoSuchListTypeException if a matching list type could not be found
	*/
	public ListType findByN_T(java.lang.String name, java.lang.String type)
		throws NoSuchListTypeException;

	/**
	* Returns the list type where name = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param name the name
	* @param type the type
	* @return the matching list type, or <code>null</code> if a matching list type could not be found
	*/
	public ListType fetchByN_T(java.lang.String name, java.lang.String type);

	/**
	* Returns the list type where name = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param name the name
	* @param type the type
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching list type, or <code>null</code> if a matching list type could not be found
	*/
	public ListType fetchByN_T(java.lang.String name, java.lang.String type,
		boolean retrieveFromCache);

	/**
	* Removes the list type where name = &#63; and type = &#63; from the database.
	*
	* @param name the name
	* @param type the type
	* @return the list type that was removed
	*/
	public ListType removeByN_T(java.lang.String name, java.lang.String type)
		throws NoSuchListTypeException;

	/**
	* Returns the number of list types where name = &#63; and type = &#63;.
	*
	* @param name the name
	* @param type the type
	* @return the number of matching list types
	*/
	public int countByN_T(java.lang.String name, java.lang.String type);

	/**
	* Caches the list type in the entity cache if it is enabled.
	*
	* @param listType the list type
	*/
	public void cacheResult(ListType listType);

	/**
	* Caches the list types in the entity cache if it is enabled.
	*
	* @param listTypes the list types
	*/
	public void cacheResult(java.util.List<ListType> listTypes);

	/**
	* Creates a new list type with the primary key. Does not add the list type to the database.
	*
	* @param listTypeId the primary key for the new list type
	* @return the new list type
	*/
	public ListType create(long listTypeId);

	/**
	* Removes the list type with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param listTypeId the primary key of the list type
	* @return the list type that was removed
	* @throws NoSuchListTypeException if a list type with the primary key could not be found
	*/
	public ListType remove(long listTypeId) throws NoSuchListTypeException;

	public ListType updateImpl(ListType listType);

	/**
	* Returns the list type with the primary key or throws a {@link NoSuchListTypeException} if it could not be found.
	*
	* @param listTypeId the primary key of the list type
	* @return the list type
	* @throws NoSuchListTypeException if a list type with the primary key could not be found
	*/
	public ListType findByPrimaryKey(long listTypeId)
		throws NoSuchListTypeException;

	/**
	* Returns the list type with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param listTypeId the primary key of the list type
	* @return the list type, or <code>null</code> if a list type with the primary key could not be found
	*/
	public ListType fetchByPrimaryKey(long listTypeId);

	@Override
	public java.util.Map<java.io.Serializable, ListType> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the list types.
	*
	* @return the list types
	*/
	public java.util.List<ListType> findAll();

	/**
	* Returns a range of all the list types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ListTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of list types
	* @param end the upper bound of the range of list types (not inclusive)
	* @return the range of list types
	*/
	public java.util.List<ListType> findAll(int start, int end);

	/**
	* Returns an ordered range of all the list types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ListTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of list types
	* @param end the upper bound of the range of list types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of list types
	*/
	public java.util.List<ListType> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListType> orderByComparator);

	/**
	* Returns an ordered range of all the list types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ListTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of list types
	* @param end the upper bound of the range of list types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of list types
	*/
	public java.util.List<ListType> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListType> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the list types from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of list types.
	*
	* @return the number of list types
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}