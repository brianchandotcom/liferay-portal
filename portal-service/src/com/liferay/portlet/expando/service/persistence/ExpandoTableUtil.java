/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portlet.expando.model.ExpandoTable;

import java.util.List;

/**
 * The persistence utility for the expando table service. This utility wraps {@link ExpandoTablePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoTablePersistence
 * @see ExpandoTablePersistenceImpl
 * @generated
 */
public class ExpandoTableUtil {
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
	public static void clearCache(ExpandoTable expandoTable) {
		getPersistence().clearCache(expandoTable);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ExpandoTable> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ExpandoTable> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ExpandoTable> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ExpandoTable update(ExpandoTable expandoTable)
		throws SystemException {
		return getPersistence().update(expandoTable);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ExpandoTable update(ExpandoTable expandoTable,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(expandoTable, serviceContext);
	}

	/**
	* Returns all the expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the matching expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findByC_C(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(companyId, classNameId);
	}

	/**
	* Returns a range of all the expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of expando tables
	* @param end the upper bound of the range of expando tables (not inclusive)
	* @return the range of matching expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(companyId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C(companyId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando table
	* @throws com.liferay.portlet.expando.NoSuchTableException if a matching expando table could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable findByC_C_First(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence()
				   .findByC_C_First(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the first expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando table, or <code>null</code> if a matching expando table could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable fetchByC_C_First(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_First(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the last expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando table
	* @throws com.liferay.portlet.expando.NoSuchTableException if a matching expando table could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable findByC_C_Last(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence()
				   .findByC_C_Last(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the last expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando table, or <code>null</code> if a matching expando table could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable fetchByC_C_Last(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_Last(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the expando tables before and after the current expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param tableId the primary key of the current expando table
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando table
	* @throws com.liferay.portlet.expando.NoSuchTableException if a expando table with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable[] findByC_C_PrevAndNext(
		long tableId, long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence()
				   .findByC_C_PrevAndNext(tableId, companyId, classNameId,
			orderByComparator);
	}

	/**
	* Removes all the expando tables where companyId = &#63; and classNameId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_C(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C(companyId, classNameId);
	}

	/**
	* Returns the number of expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the number of matching expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(companyId, classNameId);
	}

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
	public static com.liferay.portlet.expando.model.ExpandoTable findByC_C_N(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence().findByC_C_N(companyId, classNameId, name);
	}

	/**
	* Returns the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param name the name
	* @return the matching expando table, or <code>null</code> if a matching expando table could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable fetchByC_C_N(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_C_N(companyId, classNameId, name);
	}

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
	public static com.liferay.portlet.expando.model.ExpandoTable fetchByC_C_N(
		long companyId, long classNameId, java.lang.String name,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_N(companyId, classNameId, name, retrieveFromCache);
	}

	/**
	* Removes the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param name the name
	* @return the expando table that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable removeByC_C_N(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence().removeByC_C_N(companyId, classNameId, name);
	}

	/**
	* Returns the number of expando tables where companyId = &#63; and classNameId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param name the name
	* @return the number of matching expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C_N(long companyId, long classNameId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C_N(companyId, classNameId, name);
	}

	/**
	* Caches the expando table in the entity cache if it is enabled.
	*
	* @param expandoTable the expando table
	*/
	public static void cacheResult(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable) {
		getPersistence().cacheResult(expandoTable);
	}

	/**
	* Caches the expando tables in the entity cache if it is enabled.
	*
	* @param expandoTables the expando tables
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.expando.model.ExpandoTable> expandoTables) {
		getPersistence().cacheResult(expandoTables);
	}

	/**
	* Creates a new expando table with the primary key. Does not add the expando table to the database.
	*
	* @param tableId the primary key for the new expando table
	* @return the new expando table
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable create(
		long tableId) {
		return getPersistence().create(tableId);
	}

	/**
	* Removes the expando table with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tableId the primary key of the expando table
	* @return the expando table that was removed
	* @throws com.liferay.portlet.expando.NoSuchTableException if a expando table with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable remove(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence().remove(tableId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable updateImpl(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(expandoTable);
	}

	/**
	* Returns the expando table with the primary key or throws a {@link com.liferay.portlet.expando.NoSuchTableException} if it could not be found.
	*
	* @param tableId the primary key of the expando table
	* @return the expando table
	* @throws com.liferay.portlet.expando.NoSuchTableException if a expando table with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable findByPrimaryKey(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence().findByPrimaryKey(tableId);
	}

	/**
	* Returns the expando table with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param tableId the primary key of the expando table
	* @return the expando table, or <code>null</code> if a expando table with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable fetchByPrimaryKey(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(tableId);
	}

	/**
	* Returns all the expando tables.
	*
	* @return the expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the expando tables.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando tables
	* @param end the upper bound of the range of expando tables (not inclusive)
	* @return the range of expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the expando tables.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando tables
	* @param end the upper bound of the range of expando tables (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the expando tables from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of expando tables.
	*
	* @return the number of expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ExpandoTablePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ExpandoTablePersistence)PortalBeanLocatorUtil.locate(ExpandoTablePersistence.class.getName());

			ReferenceRegistry.registerReference(ExpandoTableUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(ExpandoTablePersistence persistence) {
	}

	private static ExpandoTablePersistence _persistence;
}