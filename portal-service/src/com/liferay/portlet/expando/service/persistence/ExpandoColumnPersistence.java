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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.expando.model.ExpandoColumn;

/**
 * The persistence interface for the expando column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoColumnPersistenceImpl
 * @see ExpandoColumnUtil
 * @generated
 */
public interface ExpandoColumnPersistence extends BasePersistence<ExpandoColumn> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExpandoColumnUtil} to access the expando column persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the expando column in the entity cache if it is enabled.
	*
	* @param expandoColumn the expando column
	*/
	public void cacheResult(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn);

	/**
	* Caches the expando columns in the entity cache if it is enabled.
	*
	* @param expandoColumns the expando columns
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> expandoColumns);

	/**
	* Creates a new expando column with the primary key. Does not add the expando column to the database.
	*
	* @param columnId the primary key for the new expando column
	* @return the new expando column
	*/
	public com.liferay.portlet.expando.model.ExpandoColumn create(long columnId);

	/**
	* Removes the expando column with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column that was removed
	* @throws com.liferay.portlet.expando.NoSuchColumnException if a expando column with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoColumn remove(long columnId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException;

	public com.liferay.portlet.expando.model.ExpandoColumn updateImpl(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the expando column with the primary key or throws a {@link com.liferay.portlet.expando.NoSuchColumnException} if it could not be found.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column
	* @throws com.liferay.portlet.expando.NoSuchColumnException if a expando column with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoColumn findByPrimaryKey(
		long columnId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException;

	/**
	* Returns the expando column with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column, or <code>null</code> if a expando column with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoColumn fetchByPrimaryKey(
		long columnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the expando columns where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByTableId(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByTableId(
		long tableId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByTableId(
		long tableId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.expando.model.ExpandoColumn findByTableId_First(
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException;

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
	public com.liferay.portlet.expando.model.ExpandoColumn findByTableId_Last(
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException;

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
	public com.liferay.portlet.expando.model.ExpandoColumn[] findByTableId_PrevAndNext(
		long columnId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException;

	/**
	* Returns all the expando columns that the user has permission to view where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByTableId(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByTableId(
		long tableId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByTableId(
		long tableId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.expando.model.ExpandoColumn[] filterFindByTableId_PrevAndNext(
		long columnId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException;

	/**
	* Returns all the expando columns where tableId = &#63; and name = &#63;.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByT_N(
		long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByT_N(
		long tableId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByT_N(
		long tableId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.expando.model.ExpandoColumn findByT_N_First(
		long tableId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException;

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
	public com.liferay.portlet.expando.model.ExpandoColumn findByT_N_Last(
		long tableId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException;

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
	public com.liferay.portlet.expando.model.ExpandoColumn[] findByT_N_PrevAndNext(
		long columnId, long tableId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException;

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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByT_N(
		long tableId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByT_N(
		long tableId, java.lang.String[] names, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByT_N(
		long tableId, java.lang.String[] names, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the expando columns that the user has permission to view where tableId = &#63; and name = &#63;.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByT_N(
		long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByT_N(
		long tableId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByT_N(
		long tableId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.expando.model.ExpandoColumn[] filterFindByT_N_PrevAndNext(
		long columnId, long tableId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException;

	/**
	* Returns all the expando columns that the user has permission to view where tableId = &#63; and name = any &#63;.
	*
	* @param tableId the table ID
	* @param names the names
	* @return the matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByT_N(
		long tableId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByT_N(
		long tableId, java.lang.String[] names, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> filterFindByT_N(
		long tableId, java.lang.String[] names, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the expando columns.
	*
	* @return the expando columns
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the expando columns where tableId = &#63; from the database.
	*
	* @param tableId the table ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the expando columns where tableId = &#63; and name = &#63; from the database.
	*
	* @param tableId the table ID
	* @param name the name
	* @throws SystemException if a system exception occurred
	*/
	public void removeByT_N(long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the expando columns from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando columns where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the number of matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public int countByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando columns that the user has permission to view where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the number of matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando columns where tableId = &#63; and name = &#63;.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the number of matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public int countByT_N(long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando columns where tableId = &#63; and name = any &#63;.
	*
	* @param tableId the table ID
	* @param names the names
	* @return the number of matching expando columns
	* @throws SystemException if a system exception occurred
	*/
	public int countByT_N(long tableId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando columns that the user has permission to view where tableId = &#63; and name = &#63;.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the number of matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByT_N(long tableId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando columns that the user has permission to view where tableId = &#63; and name = any &#63;.
	*
	* @param tableId the table ID
	* @param names the names
	* @return the number of matching expando columns that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByT_N(long tableId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando columns.
	*
	* @return the number of expando columns
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public ExpandoColumn remove(ExpandoColumn expandoColumn)
		throws SystemException;
}