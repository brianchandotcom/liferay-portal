/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.expando.model.ExpandoTable;

/**
 * The persistence interface for the expando table service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoTablePersistenceImpl
 * @see ExpandoTableUtil
 * @generated
 */
public interface ExpandoTablePersistence extends BasePersistence<ExpandoTable> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExpandoTableUtil} to access the expando table persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the expando table in the entity cache if it is enabled.
	*
	* @param expandoTable the expando table
	*/
	public void cacheResult(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable);

	/**
	* Caches the expando tables in the entity cache if it is enabled.
	*
	* @param expandoTables the expando tables
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.expando.model.ExpandoTable> expandoTables);

	/**
	* Creates a new expando table with the primary key. Does not add the expando table to the database.
	*
	* @param tableId the primary key for the new expando table
	* @return the new expando table
	*/
	public com.liferay.portlet.expando.model.ExpandoTable create(long tableId);

	/**
	* Removes the expando table with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tableId the primary key of the expando table
	* @return the expando table that was removed
	* @throws com.liferay.portlet.expando.NoSuchTableException if a expando table with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoTable remove(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException;

	public com.liferay.portlet.expando.model.ExpandoTable updateImpl(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the expando table with the primary key or throws a {@link com.liferay.portlet.expando.NoSuchTableException} if it could not be found.
	*
	* @param tableId the primary key of the expando table
	* @return the expando table
	* @throws com.liferay.portlet.expando.NoSuchTableException if a expando table with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoTable findByPrimaryKey(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException;

	/**
	* Returns the expando table with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param tableId the primary key of the expando table
	* @return the expando table, or <code>null</code> if a expando table with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoTable fetchByPrimaryKey(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the matching expando tables
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findByC_C(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of expando tables
	* @param end the upper bound of the range of expando tables (not inclusive)
	* @return the range of matching expando tables
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of expando tables
	* @param end the upper bound of the range of expando tables (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando tables
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando table
	* @throws com.liferay.portlet.expando.NoSuchTableException if a matching expando table could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoTable findByC_C_First(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException;

	/**
	* Returns the last expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando table
	* @throws com.liferay.portlet.expando.NoSuchTableException if a matching expando table could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoTable findByC_C_Last(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException;

	/**
	* Returns the expando tables before and after the current expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the primary key of the current expando table
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando table
	* @throws com.liferay.portlet.expando.NoSuchTableException if a expando table with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoTable[] findByC_C_PrevAndNext(
		long tableId, long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException;

	/**
	* Returns the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.expando.NoSuchTableException} if it could not be found.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param name the name
	* @return the matching expando table
	* @throws com.liferay.portlet.expando.NoSuchTableException if a matching expando table could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoTable findByC_C_N(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException;

	/**
	* Returns the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param name the name
	* @return the matching expando table, or <code>null</code> if a matching expando table could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoTable fetchByC_C_N(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching expando table, or <code>null</code> if a matching expando table could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoTable fetchByC_C_N(
		long companyId, long classNameId, java.lang.String name,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the expando tables.
	*
	* @return the expando tables
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the expando tables.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of expando tables
	* @param end the upper bound of the range of expando tables (not inclusive)
	* @return the range of expando tables
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the expando tables.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of expando tables
	* @param end the upper bound of the range of expando tables (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of expando tables
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the expando tables where companyId = &#63; and classNameId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_C(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param name the name
	* @return the expando table that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoTable removeByC_C_N(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException;

	/**
	* Removes all the expando tables from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the number of matching expando tables
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando tables where companyId = &#63; and classNameId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param name the name
	* @return the number of matching expando tables
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C_N(long companyId, long classNameId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando tables.
	*
	* @return the number of expando tables
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}