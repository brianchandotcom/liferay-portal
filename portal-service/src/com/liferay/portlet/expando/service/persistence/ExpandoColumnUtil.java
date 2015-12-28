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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoColumn;

import java.util.List;

/**
 * The persistence utility for the expando column service. This utility wraps {@link com.liferay.portlet.expando.service.persistence.impl.ExpandoColumnPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoColumnPersistence
 * @see com.liferay.portlet.expando.service.persistence.impl.ExpandoColumnPersistenceImpl
 * @generated
 */
@ProviderType
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
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ExpandoColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ExpandoColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ExpandoColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ExpandoColumn> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ExpandoColumn update(ExpandoColumn expandoColumn) {
		return getPersistence().update(expandoColumn);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ExpandoColumn update(ExpandoColumn expandoColumn,
		ServiceContext serviceContext) {
		return getPersistence().update(expandoColumn, serviceContext);
	}

	/**
	* Returns all the expando columns where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the matching expando columns
	*/
	public static List<ExpandoColumn> findByTableId(long tableId) {
		return getPersistence().findByTableId(tableId);
	}

	/**
	* Returns a range of all the expando columns where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of matching expando columns
	*/
	public static List<ExpandoColumn> findByTableId(long tableId, int start,
		int end) {
		return getPersistence().findByTableId(tableId, start, end);
	}

	/**
	* Returns an ordered range of all the expando columns where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando columns
	*/
	public static List<ExpandoColumn> findByTableId(long tableId, int start,
		int end, OrderByComparator<ExpandoColumn> orderByComparator) {
		return getPersistence()
				   .findByTableId(tableId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the expando columns where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching expando columns
	*/
	public static List<ExpandoColumn> findByTableId(long tableId, int start,
		int end, OrderByComparator<ExpandoColumn> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByTableId(tableId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first expando column in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando column
	* @throws NoSuchColumnException if a matching expando column could not be found
	*/
	public static ExpandoColumn findByTableId_First(long tableId,
		OrderByComparator<ExpandoColumn> orderByComparator)
		throws com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().findByTableId_First(tableId, orderByComparator);
	}

	/**
	* Returns the first expando column in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando column, or <code>null</code> if a matching expando column could not be found
	*/
	public static ExpandoColumn fetchByTableId_First(long tableId,
		OrderByComparator<ExpandoColumn> orderByComparator) {
		return getPersistence().fetchByTableId_First(tableId, orderByComparator);
	}

	/**
	* Returns the last expando column in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando column
	* @throws NoSuchColumnException if a matching expando column could not be found
	*/
	public static ExpandoColumn findByTableId_Last(long tableId,
		OrderByComparator<ExpandoColumn> orderByComparator)
		throws com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().findByTableId_Last(tableId, orderByComparator);
	}

	/**
	* Returns the last expando column in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando column, or <code>null</code> if a matching expando column could not be found
	*/
	public static ExpandoColumn fetchByTableId_Last(long tableId,
		OrderByComparator<ExpandoColumn> orderByComparator) {
		return getPersistence().fetchByTableId_Last(tableId, orderByComparator);
	}

	/**
	* Returns the expando columns before and after the current expando column in the ordered set where tableId = &#63;.
	*
	* @param columnId the primary key of the current expando column
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando column
	* @throws NoSuchColumnException if a expando column with the primary key could not be found
	*/
	public static ExpandoColumn[] findByTableId_PrevAndNext(long columnId,
		long tableId, OrderByComparator<ExpandoColumn> orderByComparator)
		throws com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence()
				   .findByTableId_PrevAndNext(columnId, tableId,
			orderByComparator);
	}

	/**
	* Returns all the expando columns that the user has permission to view where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the matching expando columns that the user has permission to view
	*/
	public static List<ExpandoColumn> filterFindByTableId(long tableId) {
		return getPersistence().filterFindByTableId(tableId);
	}

	/**
	* Returns a range of all the expando columns that the user has permission to view where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of matching expando columns that the user has permission to view
	*/
	public static List<ExpandoColumn> filterFindByTableId(long tableId,
		int start, int end) {
		return getPersistence().filterFindByTableId(tableId, start, end);
	}

	/**
	* Returns an ordered range of all the expando columns that the user has permissions to view where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando columns that the user has permission to view
	*/
	public static List<ExpandoColumn> filterFindByTableId(long tableId,
		int start, int end, OrderByComparator<ExpandoColumn> orderByComparator) {
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
	* @throws NoSuchColumnException if a expando column with the primary key could not be found
	*/
	public static ExpandoColumn[] filterFindByTableId_PrevAndNext(
		long columnId, long tableId,
		OrderByComparator<ExpandoColumn> orderByComparator)
		throws com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence()
				   .filterFindByTableId_PrevAndNext(columnId, tableId,
			orderByComparator);
	}

	/**
	* Removes all the expando columns where tableId = &#63; from the database.
	*
	* @param tableId the table ID
	*/
	public static void removeByTableId(long tableId) {
		getPersistence().removeByTableId(tableId);
	}

	/**
	* Returns the number of expando columns where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the number of matching expando columns
	*/
	public static int countByTableId(long tableId) {
		return getPersistence().countByTableId(tableId);
	}

	/**
	* Returns the number of expando columns that the user has permission to view where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the number of matching expando columns that the user has permission to view
	*/
	public static int filterCountByTableId(long tableId) {
		return getPersistence().filterCountByTableId(tableId);
	}

	/**
	* Returns all the expando columns where tableId = &#63; and name = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param names the names
	* @return the matching expando columns
	*/
	public static List<ExpandoColumn> findByT_N(long tableId,
		java.lang.String[] names) {
		return getPersistence().findByT_N(tableId, names);
	}

	/**
	* Returns a range of all the expando columns where tableId = &#63; and name = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param names the names
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of matching expando columns
	*/
	public static List<ExpandoColumn> findByT_N(long tableId,
		java.lang.String[] names, int start, int end) {
		return getPersistence().findByT_N(tableId, names, start, end);
	}

	/**
	* Returns an ordered range of all the expando columns where tableId = &#63; and name = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param names the names
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando columns
	*/
	public static List<ExpandoColumn> findByT_N(long tableId,
		java.lang.String[] names, int start, int end,
		OrderByComparator<ExpandoColumn> orderByComparator) {
		return getPersistence()
				   .findByT_N(tableId, names, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the expando columns where tableId = &#63; and name = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param name the name
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching expando columns
	*/
	public static List<ExpandoColumn> findByT_N(long tableId,
		java.lang.String[] names, int start, int end,
		OrderByComparator<ExpandoColumn> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByT_N(tableId, names, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the expando column where tableId = &#63; and name = &#63; or throws a {@link NoSuchColumnException} if it could not be found.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the matching expando column
	* @throws NoSuchColumnException if a matching expando column could not be found
	*/
	public static ExpandoColumn findByT_N(long tableId, java.lang.String name)
		throws com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().findByT_N(tableId, name);
	}

	/**
	* Returns the expando column where tableId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the matching expando column, or <code>null</code> if a matching expando column could not be found
	*/
	public static ExpandoColumn fetchByT_N(long tableId, java.lang.String name) {
		return getPersistence().fetchByT_N(tableId, name);
	}

	/**
	* Returns the expando column where tableId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param tableId the table ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching expando column, or <code>null</code> if a matching expando column could not be found
	*/
	public static ExpandoColumn fetchByT_N(long tableId, java.lang.String name,
		boolean retrieveFromCache) {
		return getPersistence().fetchByT_N(tableId, name, retrieveFromCache);
	}

	/**
	* Removes the expando column where tableId = &#63; and name = &#63; from the database.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the expando column that was removed
	*/
	public static ExpandoColumn removeByT_N(long tableId, java.lang.String name)
		throws com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().removeByT_N(tableId, name);
	}

	/**
	* Returns the number of expando columns where tableId = &#63; and name = &#63;.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the number of matching expando columns
	*/
	public static int countByT_N(long tableId, java.lang.String name) {
		return getPersistence().countByT_N(tableId, name);
	}

	/**
	* Returns the number of expando columns where tableId = &#63; and name = any &#63;.
	*
	* @param tableId the table ID
	* @param names the names
	* @return the number of matching expando columns
	*/
	public static int countByT_N(long tableId, java.lang.String[] names) {
		return getPersistence().countByT_N(tableId, names);
	}

	/**
	* Returns the number of expando columns that the user has permission to view where tableId = &#63; and name = &#63;.
	*
	* @param tableId the table ID
	* @param name the name
	* @return the number of matching expando columns that the user has permission to view
	*/
	public static int filterCountByT_N(long tableId, java.lang.String name) {
		return getPersistence().filterCountByT_N(tableId, name);
	}

	/**
	* Returns the number of expando columns that the user has permission to view where tableId = &#63; and name = any &#63;.
	*
	* @param tableId the table ID
	* @param names the names
	* @return the number of matching expando columns that the user has permission to view
	*/
	public static int filterCountByT_N(long tableId, java.lang.String[] names) {
		return getPersistence().filterCountByT_N(tableId, names);
	}

	/**
	* Caches the expando column in the entity cache if it is enabled.
	*
	* @param expandoColumn the expando column
	*/
	public static void cacheResult(ExpandoColumn expandoColumn) {
		getPersistence().cacheResult(expandoColumn);
	}

	/**
	* Caches the expando columns in the entity cache if it is enabled.
	*
	* @param expandoColumns the expando columns
	*/
	public static void cacheResult(List<ExpandoColumn> expandoColumns) {
		getPersistence().cacheResult(expandoColumns);
	}

	/**
	* Creates a new expando column with the primary key. Does not add the expando column to the database.
	*
	* @param columnId the primary key for the new expando column
	* @return the new expando column
	*/
	public static ExpandoColumn create(long columnId) {
		return getPersistence().create(columnId);
	}

	/**
	* Removes the expando column with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column that was removed
	* @throws NoSuchColumnException if a expando column with the primary key could not be found
	*/
	public static ExpandoColumn remove(long columnId)
		throws com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().remove(columnId);
	}

	public static ExpandoColumn updateImpl(ExpandoColumn expandoColumn) {
		return getPersistence().updateImpl(expandoColumn);
	}

	/**
	* Returns the expando column with the primary key or throws a {@link NoSuchColumnException} if it could not be found.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column
	* @throws NoSuchColumnException if a expando column with the primary key could not be found
	*/
	public static ExpandoColumn findByPrimaryKey(long columnId)
		throws com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().findByPrimaryKey(columnId);
	}

	/**
	* Returns the expando column with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param columnId the primary key of the expando column
	* @return the expando column, or <code>null</code> if a expando column with the primary key could not be found
	*/
	public static ExpandoColumn fetchByPrimaryKey(long columnId) {
		return getPersistence().fetchByPrimaryKey(columnId);
	}

	public static java.util.Map<java.io.Serializable, ExpandoColumn> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the expando columns.
	*
	* @return the expando columns
	*/
	public static List<ExpandoColumn> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the expando columns.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @return the range of expando columns
	*/
	public static List<ExpandoColumn> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the expando columns.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of expando columns
	*/
	public static List<ExpandoColumn> findAll(int start, int end,
		OrderByComparator<ExpandoColumn> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the expando columns.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoColumnModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando columns
	* @param end the upper bound of the range of expando columns (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of expando columns
	*/
	public static List<ExpandoColumn> findAll(int start, int end,
		OrderByComparator<ExpandoColumn> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the expando columns from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of expando columns.
	*
	* @return the number of expando columns
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static ExpandoColumnPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ExpandoColumnPersistence)PortalBeanLocatorUtil.locate(ExpandoColumnPersistence.class.getName());

			ReferenceRegistry.registerReference(ExpandoColumnUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static ExpandoColumnPersistence _persistence;
}