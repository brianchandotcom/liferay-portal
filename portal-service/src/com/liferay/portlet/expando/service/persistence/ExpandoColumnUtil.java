/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoColumn;

import java.util.List;

/**
 * The persistence utility for the expando column service. This utility wraps {@link ExpandoColumnPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoColumnPersistence
 * @see ExpandoColumnPersistenceImpl
 * @generated
 */
public class ExpandoColumnUtil {
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
	public static void clearCache(ExpandoColumn expandoColumn) {
		getPersistence().clearCache(expandoColumn);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ExpandoColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ExpandoColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ExpandoColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static ExpandoColumn remove(ExpandoColumn expandoColumn)
		throws SystemException {
		return getPersistence().remove(expandoColumn);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ExpandoColumn update(ExpandoColumn expandoColumn,
		boolean merge) throws SystemException {
		return getPersistence().update(expandoColumn, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static ExpandoColumn update(ExpandoColumn expandoColumn,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(expandoColumn, merge, serviceContext);
	}

	/**
	* Caches the expando column in the entity cache if it is enabled.
	*
	* @param expandoColumn the expando column
	*/
	public static void cacheResult(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn) {
		getPersistence().cacheResult(expandoColumn);
	}

	/**
	* Caches the expando columns in the entity cache if it is enabled.
	*
	* @param expandoColumns the expando columns
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> expandoColumns) {
		getPersistence().cacheResult(expandoColumns);
	}

	/**
	* Creates a new expando column with the primary key. Does not add the expando column to the database.
	*
	* @param columnId the primary key for the new expando column
	* @return the new expando column
	*/
	public static com.liferay.portlet.expando.model.ExpandoColumn create(
		long columnId) {
		return getPersistence().create(columnId);
	}

	/**
	* Removes the expando column with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column that was removed
	* @throws com.liferay.portlet.expando.NoSuchColumnException if a expando column with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoColumn remove(
		long columnId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().remove(columnId);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateImpl(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(expandoColumn, merge);
	}

	/**
	* Returns the expando column with the primary key or throws a {@link com.liferay.portlet.expando.NoSuchColumnException} if it could not be found.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column
	* @throws com.liferay.portlet.expando.NoSuchColumnException if a expando column with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoColumn findByPrimaryKey(
		long columnId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().findByPrimaryKey(columnId);
	}

	/**
	* Returns the expando column with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column, or <code>null</code> if a expando column with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoColumn fetchByPrimaryKey(
		long columnId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(columnId);
	}

	/**
	* Returns all the expando columns where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByTableId(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTableId(tableId);
	}

	/**
	* Returns a range of all the expando columns where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByTableId(
		long tableId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTableId(tableId, start, end);
	}

	/**
	* Returns an ordered range of all the expando columns where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByTableId(
		long tableId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByTableId(tableId, start, end, orderByComparator);
	}

	/**
	* Returns the first expando column in the ordered set where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando column
	* @throws com.liferay.portlet.expando.NoSuchColumnException if a matching expando column could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoColumn findByTableId_First(
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().findByTableId_First(tableId, orderByComparator);
	}

	/**
	* Returns the last expando column in the ordered set where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando column
	* @throws com.liferay.portlet.expando.NoSuchColumnException if a matching expando column could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoColumn findByTableId_Last(
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().findByTableId_Last(tableId, orderByComparator);
	}

	/**
	* Returns the expando columns before and after the current expando column in the ordered set where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param columnId the primary key of the current expando column
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando column
	* @throws com.liferay.portlet.expando.NoSuchColumnException if a expando column with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoColumn[] findByTableId_PrevAndNext(
		long columnId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence()
				   .findByTableId_PrevAndNext(columnId, tableId,
			orderByComparator);
	}

	/**
	* Returns all the expando columns that the user has permission to view where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByTableId(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByTableId(tableId);
	}

	/**
	* Returns a range of all the expando columns that the user has permission to view where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByTableId(
		long tableId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByTableId(tableId, start, end);
	}

	/**
	* Returns an ordered range of all the expando columns that the user has permissions to view where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByTableId(
		long tableId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByTableId(tableId, start, end, orderByComparator);
	}

	/**
	* Returns the expando columns before and after the current expando column in the ordered set of expando columns that the user has permission to view where tableId = &#63;.
	*
	* @param columnId the primary key of the current expando column
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando column
	* @throws com.liferay.portlet.expando.NoSuchColumnException if a expando column with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoColumn[] filterFindByTableId_PrevAndNext(
		long columnId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence()
				   .filterFindByTableId_PrevAndNext(columnId, tableId,
			orderByComparator);
	}

	/**
	* Returns all the expando columns where tableId = &#63; and name = &#63;.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByT_N(
		long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_N(tableId, name);
	}

	/**
	* Returns a range of all the expando columns where tableId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param name the name
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByT_N(
		long tableId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_N(tableId, name, start, end);
	}

	/**
	* Returns an ordered range of all the expando columns where tableId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param name the name
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByT_N(
		long tableId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByT_N(tableId, name, start, end, orderByComparator);
	}

	/**
	* Returns the first expando column in the ordered set where tableId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando column
	* @throws com.liferay.portlet.expando.NoSuchColumnException if a matching expando column could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoColumn findByT_N_First(
		long tableId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().findByT_N_First(tableId, name, orderByComparator);
	}

	/**
	* Returns the last expando column in the ordered set where tableId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando column
	* @throws com.liferay.portlet.expando.NoSuchColumnException if a matching expando column could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoColumn findByT_N_Last(
		long tableId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().findByT_N_Last(tableId, name, orderByComparator);
	}

	/**
	* Returns the expando columns before and after the current expando column in the ordered set where tableId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param columnId the primary key of the current expando column
	* @param tableId the table ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando column
	* @throws com.liferay.portlet.expando.NoSuchColumnException if a expando column with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoColumn[] findByT_N_PrevAndNext(
		long columnId, long tableId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence()
				   .findByT_N_PrevAndNext(columnId, tableId, name,
			orderByComparator);
	}

	/**
	* Returns all the expando columns where tableId = &#63; and name = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param names the names
	* @return the matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByT_N(
		long tableId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_N(tableId, names);
	}

	/**
	* Returns a range of all the expando columns where tableId = &#63; and name = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param names the names
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByT_N(
		long tableId, java.lang.String[] names, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_N(tableId, names, start, end);
	}

	/**
	* Returns an ordered range of all the expando columns where tableId = &#63; and name = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param names the names
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByT_N(
		long tableId, java.lang.String[] names, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByT_N(tableId, names, start, end, orderByComparator);
	}

	/**
	* Returns all the expando columns that the user has permission to view where tableId = &#63; and name = &#63;.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByT_N(
		long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByT_N(tableId, name);
	}

	/**
	* Returns a range of all the expando columns that the user has permission to view where tableId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param name the name
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByT_N(
		long tableId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByT_N(tableId, name, start, end);
	}

	/**
	* Returns an ordered range of all the expando columns that the user has permissions to view where tableId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param name the name
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByT_N(
		long tableId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByT_N(tableId, name, start, end, orderByComparator);
	}

	/**
	* Returns the expando columns before and after the current expando column in the ordered set of expando columns that the user has permission to view where tableId = &#63; and name = &#63;.
	*
	* @param columnId the primary key of the current expando column
	* @param tableId the table ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando column
	* @throws com.liferay.portlet.expando.NoSuchColumnException if a expando column with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoColumn[] filterFindByT_N_PrevAndNext(
		long columnId, long tableId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence()
				   .filterFindByT_N_PrevAndNext(columnId, tableId, name,
			orderByComparator);
	}

	/**
	* Returns all the expando columns that the user has permission to view where tableId = &#63; and name = any &#63;.
	*
	* @param tableId the table ID
	* @param names the names
	* @return the matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByT_N(
		long tableId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByT_N(tableId, names);
	}

	/**
	* Returns a range of all the expando columns that the user has permission to view where tableId = &#63; and name = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param names the names
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByT_N(
		long tableId, java.lang.String[] names, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByT_N(tableId, names, start, end);
	}

	/**
	* Returns an ordered range of all the expando columns that the user has permission to view where tableId = &#63; and name = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param names the names
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByT_N(
		long tableId, java.lang.String[] names, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByT_N(tableId, names, start, end,
			orderByComparator);
	}

	/**
	* Returns all the expando columns.
	*
	* @return the expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the expando columns.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the expando columns.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the expando columns where tableId = &#63; from the database.
	*
	* @param tableId the table ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByTableId(tableId);
	}

	/**
	* Removes all the expando columns where tableId = &#63; and name = &#63; from the database.
	*
	* @param tableId the table ID
	* @param name the name
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByT_N(long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByT_N(tableId, name);
	}

	/**
	* Removes all the expando columns from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of expando columns where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the number of matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static int countByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByTableId(tableId);
	}

	/**
	* Returns the number of expando columns that the user has permission to view where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the number of matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByTableId(tableId);
	}

	/**
	* Returns the number of expando columns where tableId = &#63; and name = &#63;.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the number of matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static int countByT_N(long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_N(tableId, name);
	}

	/**
	* Returns the number of expando columns where tableId = &#63; and name = any &#63;.
	*
	* @param tableId the table ID
	* @param names the names
	* @return the number of matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static int countByT_N(long tableId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_N(tableId, names);
	}

	/**
	* Returns the number of expando columns that the user has permission to view where tableId = &#63; and name = &#63;.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the number of matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByT_N(long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByT_N(tableId, name);
	}

	/**
	* Returns the number of expando columns that the user has permission to view where tableId = &#63; and name = any &#63;.
	*
	* @param tableId the table ID
	* @param names the names
	* @return the number of matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByT_N(long tableId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByT_N(tableId, names);
	}

	/**
	* Returns the number of expando columns.
	*
	* @return the number of expando columns
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ExpandoColumnPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ExpandoColumnPersistence)PortalBeanLocatorUtil.locate(ExpandoColumnPersistence.class.getName());

			ReferenceRegistry.registerReference(ExpandoColumnUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(ExpandoColumnPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(ExpandoColumnUtil.class,
			"_persistence");
	}

	private static ExpandoColumnPersistence _persistence;
}