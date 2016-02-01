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

package com.liferay.portlet.expando.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.expando.exception.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoTable;

/**
 * The persistence interface for the expando table service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.expando.service.persistence.impl.ExpandoTablePersistenceImpl
 * @see ExpandoTableUtil
 * @generated
 */
@ProviderType
public interface ExpandoTablePersistence extends BasePersistence<ExpandoTable> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExpandoTableUtil} to access the expando table persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the matching expando tables
	*/
	public java.util.List<ExpandoTable> findByC_C(long companyId,
		long classNameId);

	/**
	* Returns a range of all the expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoTableModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of expando tables
	* @param end the upper bound of the range of expando tables (not inclusive)
	* @return the range of matching expando tables
	*/
	public java.util.List<ExpandoTable> findByC_C(long companyId,
		long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoTableModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of expando tables
	* @param end the upper bound of the range of expando tables (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando tables
	*/
	public java.util.List<ExpandoTable> findByC_C(long companyId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable> orderByComparator);

	/**
	* Returns an ordered range of all the expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoTableModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of expando tables
	* @param end the upper bound of the range of expando tables (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching expando tables
	*/
	public java.util.List<ExpandoTable> findByC_C(long companyId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando table
	* @throws NoSuchTableException if a matching expando table could not be found
	*/
	public ExpandoTable findByC_C_First(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable> orderByComparator)
		throws NoSuchTableException;

	/**
	* Returns the first expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando table, or <code>null</code> if a matching expando table could not be found
	*/
	public ExpandoTable fetchByC_C_First(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable> orderByComparator);

	/**
	* Returns the last expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando table
	* @throws NoSuchTableException if a matching expando table could not be found
	*/
	public ExpandoTable findByC_C_Last(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable> orderByComparator)
		throws NoSuchTableException;

	/**
	* Returns the last expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando table, or <code>null</code> if a matching expando table could not be found
	*/
	public ExpandoTable fetchByC_C_Last(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable> orderByComparator);

	/**
	* Returns the expando tables before and after the current expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param tableId the primary key of the current expando table
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando table
	* @throws NoSuchTableException if a expando table with the primary key could not be found
	*/
	public ExpandoTable[] findByC_C_PrevAndNext(long tableId, long companyId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable> orderByComparator)
		throws NoSuchTableException;

	/**
	* Removes all the expando tables where companyId = &#63; and classNameId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	*/
	public void removeByC_C(long companyId, long classNameId);

	/**
	* Returns the number of expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the number of matching expando tables
	*/
	public int countByC_C(long companyId, long classNameId);

	/**
	* Returns the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or throws a {@link NoSuchTableException} if it could not be found.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param name the name
	* @return the matching expando table
	* @throws NoSuchTableException if a matching expando table could not be found
	*/
	public ExpandoTable findByC_C_N(long companyId, long classNameId,
		java.lang.String name) throws NoSuchTableException;

	/**
	* Returns the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param name the name
	* @return the matching expando table, or <code>null</code> if a matching expando table could not be found
	*/
	public ExpandoTable fetchByC_C_N(long companyId, long classNameId,
		java.lang.String name);

	/**
	* Returns the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching expando table, or <code>null</code> if a matching expando table could not be found
	*/
	public ExpandoTable fetchByC_C_N(long companyId, long classNameId,
		java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param name the name
	* @return the expando table that was removed
	*/
	public ExpandoTable removeByC_C_N(long companyId, long classNameId,
		java.lang.String name) throws NoSuchTableException;

	/**
	* Returns the number of expando tables where companyId = &#63; and classNameId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param name the name
	* @return the number of matching expando tables
	*/
	public int countByC_C_N(long companyId, long classNameId,
		java.lang.String name);

	/**
	* Caches the expando table in the entity cache if it is enabled.
	*
	* @param expandoTable the expando table
	*/
	public void cacheResult(ExpandoTable expandoTable);

	/**
	* Caches the expando tables in the entity cache if it is enabled.
	*
	* @param expandoTables the expando tables
	*/
	public void cacheResult(java.util.List<ExpandoTable> expandoTables);

	/**
	* Creates a new expando table with the primary key. Does not add the expando table to the database.
	*
	* @param tableId the primary key for the new expando table
	* @return the new expando table
	*/
	public ExpandoTable create(long tableId);

	/**
	* Removes the expando table with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tableId the primary key of the expando table
	* @return the expando table that was removed
	* @throws NoSuchTableException if a expando table with the primary key could not be found
	*/
	public ExpandoTable remove(long tableId) throws NoSuchTableException;

	public ExpandoTable updateImpl(ExpandoTable expandoTable);

	/**
	* Returns the expando table with the primary key or throws a {@link NoSuchTableException} if it could not be found.
	*
	* @param tableId the primary key of the expando table
	* @return the expando table
	* @throws NoSuchTableException if a expando table with the primary key could not be found
	*/
	public ExpandoTable findByPrimaryKey(long tableId)
		throws NoSuchTableException;

	/**
	* Returns the expando table with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param tableId the primary key of the expando table
	* @return the expando table, or <code>null</code> if a expando table with the primary key could not be found
	*/
	public ExpandoTable fetchByPrimaryKey(long tableId);

	@Override
	public java.util.Map<java.io.Serializable, ExpandoTable> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the expando tables.
	*
	* @return the expando tables
	*/
	public java.util.List<ExpandoTable> findAll();

	/**
	* Returns a range of all the expando tables.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoTableModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando tables
	* @param end the upper bound of the range of expando tables (not inclusive)
	* @return the range of expando tables
	*/
	public java.util.List<ExpandoTable> findAll(int start, int end);

	/**
	* Returns an ordered range of all the expando tables.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoTableModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando tables
	* @param end the upper bound of the range of expando tables (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of expando tables
	*/
	public java.util.List<ExpandoTable> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable> orderByComparator);

	/**
	* Returns an ordered range of all the expando tables.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoTableModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando tables
	* @param end the upper bound of the range of expando tables (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of expando tables
	*/
	public java.util.List<ExpandoTable> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoTable> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the expando tables from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of expando tables.
	*
	* @return the number of expando tables
	*/
	public int countAll();
}