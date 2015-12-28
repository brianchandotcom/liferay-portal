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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.ListType;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the list type service. This utility wraps {@link com.liferay.portal.service.persistence.impl.ListTypePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ListTypePersistence
 * @see com.liferay.portal.service.persistence.impl.ListTypePersistenceImpl
 * @generated
 */
@ProviderType
public class ListTypeUtil {
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
	public static void clearCache(ListType listType) {
		getPersistence().clearCache(listType);
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
	public static List<ListType> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ListType> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ListType> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ListType> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ListType update(ListType listType) {
		return getPersistence().update(listType);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ListType update(ListType listType,
		ServiceContext serviceContext) {
		return getPersistence().update(listType, serviceContext);
	}

	/**
	* Returns all the list types where type = &#63;.
	*
	* @param type the type
	* @return the matching list types
	*/
	public static List<ListType> findByType(java.lang.String type) {
		return getPersistence().findByType(type);
	}

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
	public static List<ListType> findByType(java.lang.String type, int start,
		int end) {
		return getPersistence().findByType(type, start, end);
	}

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
	public static List<ListType> findByType(java.lang.String type, int start,
		int end, OrderByComparator<ListType> orderByComparator) {
		return getPersistence().findByType(type, start, end, orderByComparator);
	}

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
	public static List<ListType> findByType(java.lang.String type, int start,
		int end, OrderByComparator<ListType> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByType(type, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first list type in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching list type
	* @throws NoSuchListTypeException if a matching list type could not be found
	*/
	public static ListType findByType_First(java.lang.String type,
		OrderByComparator<ListType> orderByComparator)
		throws com.liferay.portal.NoSuchListTypeException {
		return getPersistence().findByType_First(type, orderByComparator);
	}

	/**
	* Returns the first list type in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching list type, or <code>null</code> if a matching list type could not be found
	*/
	public static ListType fetchByType_First(java.lang.String type,
		OrderByComparator<ListType> orderByComparator) {
		return getPersistence().fetchByType_First(type, orderByComparator);
	}

	/**
	* Returns the last list type in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching list type
	* @throws NoSuchListTypeException if a matching list type could not be found
	*/
	public static ListType findByType_Last(java.lang.String type,
		OrderByComparator<ListType> orderByComparator)
		throws com.liferay.portal.NoSuchListTypeException {
		return getPersistence().findByType_Last(type, orderByComparator);
	}

	/**
	* Returns the last list type in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching list type, or <code>null</code> if a matching list type could not be found
	*/
	public static ListType fetchByType_Last(java.lang.String type,
		OrderByComparator<ListType> orderByComparator) {
		return getPersistence().fetchByType_Last(type, orderByComparator);
	}

	/**
	* Returns the list types before and after the current list type in the ordered set where type = &#63;.
	*
	* @param listTypeId the primary key of the current list type
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next list type
	* @throws NoSuchListTypeException if a list type with the primary key could not be found
	*/
	public static ListType[] findByType_PrevAndNext(long listTypeId,
		java.lang.String type, OrderByComparator<ListType> orderByComparator)
		throws com.liferay.portal.NoSuchListTypeException {
		return getPersistence()
				   .findByType_PrevAndNext(listTypeId, type, orderByComparator);
	}

	/**
	* Removes all the list types where type = &#63; from the database.
	*
	* @param type the type
	*/
	public static void removeByType(java.lang.String type) {
		getPersistence().removeByType(type);
	}

	/**
	* Returns the number of list types where type = &#63;.
	*
	* @param type the type
	* @return the number of matching list types
	*/
	public static int countByType(java.lang.String type) {
		return getPersistence().countByType(type);
	}

	/**
	* Returns the list type where name = &#63; and type = &#63; or throws a {@link NoSuchListTypeException} if it could not be found.
	*
	* @param name the name
	* @param type the type
	* @return the matching list type
	* @throws NoSuchListTypeException if a matching list type could not be found
	*/
	public static ListType findByN_T(java.lang.String name,
		java.lang.String type)
		throws com.liferay.portal.NoSuchListTypeException {
		return getPersistence().findByN_T(name, type);
	}

	/**
	* Returns the list type where name = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param name the name
	* @param type the type
	* @return the matching list type, or <code>null</code> if a matching list type could not be found
	*/
	public static ListType fetchByN_T(java.lang.String name,
		java.lang.String type) {
		return getPersistence().fetchByN_T(name, type);
	}

	/**
	* Returns the list type where name = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param name the name
	* @param type the type
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching list type, or <code>null</code> if a matching list type could not be found
	*/
	public static ListType fetchByN_T(java.lang.String name,
		java.lang.String type, boolean retrieveFromCache) {
		return getPersistence().fetchByN_T(name, type, retrieveFromCache);
	}

	/**
	* Removes the list type where name = &#63; and type = &#63; from the database.
	*
	* @param name the name
	* @param type the type
	* @return the list type that was removed
	*/
	public static ListType removeByN_T(java.lang.String name,
		java.lang.String type)
		throws com.liferay.portal.NoSuchListTypeException {
		return getPersistence().removeByN_T(name, type);
	}

	/**
	* Returns the number of list types where name = &#63; and type = &#63;.
	*
	* @param name the name
	* @param type the type
	* @return the number of matching list types
	*/
	public static int countByN_T(java.lang.String name, java.lang.String type) {
		return getPersistence().countByN_T(name, type);
	}

	/**
	* Caches the list type in the entity cache if it is enabled.
	*
	* @param listType the list type
	*/
	public static void cacheResult(ListType listType) {
		getPersistence().cacheResult(listType);
	}

	/**
	* Caches the list types in the entity cache if it is enabled.
	*
	* @param listTypes the list types
	*/
	public static void cacheResult(List<ListType> listTypes) {
		getPersistence().cacheResult(listTypes);
	}

	/**
	* Creates a new list type with the primary key. Does not add the list type to the database.
	*
	* @param listTypeId the primary key for the new list type
	* @return the new list type
	*/
	public static ListType create(long listTypeId) {
		return getPersistence().create(listTypeId);
	}

	/**
	* Removes the list type with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param listTypeId the primary key of the list type
	* @return the list type that was removed
	* @throws NoSuchListTypeException if a list type with the primary key could not be found
	*/
	public static ListType remove(long listTypeId)
		throws com.liferay.portal.NoSuchListTypeException {
		return getPersistence().remove(listTypeId);
	}

	public static ListType updateImpl(ListType listType) {
		return getPersistence().updateImpl(listType);
	}

	/**
	* Returns the list type with the primary key or throws a {@link NoSuchListTypeException} if it could not be found.
	*
	* @param listTypeId the primary key of the list type
	* @return the list type
	* @throws NoSuchListTypeException if a list type with the primary key could not be found
	*/
	public static ListType findByPrimaryKey(long listTypeId)
		throws com.liferay.portal.NoSuchListTypeException {
		return getPersistence().findByPrimaryKey(listTypeId);
	}

	/**
	* Returns the list type with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param listTypeId the primary key of the list type
	* @return the list type, or <code>null</code> if a list type with the primary key could not be found
	*/
	public static ListType fetchByPrimaryKey(long listTypeId) {
		return getPersistence().fetchByPrimaryKey(listTypeId);
	}

	public static java.util.Map<java.io.Serializable, ListType> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the list types.
	*
	* @return the list types
	*/
	public static List<ListType> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<ListType> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<ListType> findAll(int start, int end,
		OrderByComparator<ListType> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<ListType> findAll(int start, int end,
		OrderByComparator<ListType> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the list types from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of list types.
	*
	* @return the number of list types
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static ListTypePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ListTypePersistence)PortalBeanLocatorUtil.locate(ListTypePersistence.class.getName());

			ReferenceRegistry.registerReference(ListTypeUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static ListTypePersistence _persistence;
}