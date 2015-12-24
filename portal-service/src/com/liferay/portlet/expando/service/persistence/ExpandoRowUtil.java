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

import com.liferay.portlet.expando.model.ExpandoRow;

import java.util.List;

/**
 * The persistence utility for the expando row service. This utility wraps {@link com.liferay.portlet.expando.service.persistence.impl.ExpandoRowPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoRowPersistence
 * @see com.liferay.portlet.expando.service.persistence.impl.ExpandoRowPersistenceImpl
 * @generated
 */
@ProviderType
public class ExpandoRowUtil {
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
	public static void clearCache(ExpandoRow expandoRow) {
		getPersistence().clearCache(expandoRow);
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
	public static List<ExpandoRow> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ExpandoRow> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ExpandoRow> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ExpandoRow> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ExpandoRow update(ExpandoRow expandoRow) {
		return getPersistence().update(expandoRow);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ExpandoRow update(ExpandoRow expandoRow,
		ServiceContext serviceContext) {
		return getPersistence().update(expandoRow, serviceContext);
	}

	/**
	* Returns all the expando rows where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the matching expando rows
	*/
	public static List<ExpandoRow> findByTableId(long tableId) {
		return getPersistence().findByTableId(tableId);
	}

	/**
	* Returns a range of all the expando rows where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoRowModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando rows
	* @param end the upper bound of the range of expando rows (not inclusive)
	* @return the range of matching expando rows
	*/
	public static List<ExpandoRow> findByTableId(long tableId, int start,
		int end) {
		return getPersistence().findByTableId(tableId, start, end);
	}

	/**
	* Returns an ordered range of all the expando rows where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoRowModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando rows
	* @param end the upper bound of the range of expando rows (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando rows
	*/
	public static List<ExpandoRow> findByTableId(long tableId, int start,
		int end, OrderByComparator<ExpandoRow> orderByComparator) {
		return getPersistence()
				   .findByTableId(tableId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the expando rows where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoRowModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando rows
	* @param end the upper bound of the range of expando rows (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching expando rows
	*/
	public static List<ExpandoRow> findByTableId(long tableId, int start,
		int end, OrderByComparator<ExpandoRow> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByTableId(tableId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first expando row in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando row
	* @throws NoSuchRowException if a matching expando row could not be found
	*/
	public static ExpandoRow findByTableId_First(long tableId,
		OrderByComparator<ExpandoRow> orderByComparator)
		throws com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence().findByTableId_First(tableId, orderByComparator);
	}

	/**
	* Returns the first expando row in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando row, or <code>null</code> if a matching expando row could not be found
	*/
	public static ExpandoRow fetchByTableId_First(long tableId,
		OrderByComparator<ExpandoRow> orderByComparator) {
		return getPersistence().fetchByTableId_First(tableId, orderByComparator);
	}

	/**
	* Returns the last expando row in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando row
	* @throws NoSuchRowException if a matching expando row could not be found
	*/
	public static ExpandoRow findByTableId_Last(long tableId,
		OrderByComparator<ExpandoRow> orderByComparator)
		throws com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence().findByTableId_Last(tableId, orderByComparator);
	}

	/**
	* Returns the last expando row in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando row, or <code>null</code> if a matching expando row could not be found
	*/
	public static ExpandoRow fetchByTableId_Last(long tableId,
		OrderByComparator<ExpandoRow> orderByComparator) {
		return getPersistence().fetchByTableId_Last(tableId, orderByComparator);
	}

	/**
	* Returns the expando rows before and after the current expando row in the ordered set where tableId = &#63;.
	*
	* @param rowId the primary key of the current expando row
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando row
	* @throws NoSuchRowException if a expando row with the primary key could not be found
	*/
	public static ExpandoRow[] findByTableId_PrevAndNext(long rowId,
		long tableId, OrderByComparator<ExpandoRow> orderByComparator)
		throws com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence()
				   .findByTableId_PrevAndNext(rowId, tableId, orderByComparator);
	}

	/**
	* Removes all the expando rows where tableId = &#63; from the database.
	*
	* @param tableId the table ID
	*/
	public static void removeByTableId(long tableId) {
		getPersistence().removeByTableId(tableId);
	}

	/**
	* Returns the number of expando rows where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the number of matching expando rows
	*/
	public static int countByTableId(long tableId) {
		return getPersistence().countByTableId(tableId);
	}

	/**
	* Returns all the expando rows where classPK = &#63;.
	*
	* @param classPK the class p k
	* @return the matching expando rows
	*/
	public static List<ExpandoRow> findByClassPK(long classPK) {
		return getPersistence().findByClassPK(classPK);
	}

	/**
	* Returns a range of all the expando rows where classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoRowModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classPK the class p k
	* @param start the lower bound of the range of expando rows
	* @param end the upper bound of the range of expando rows (not inclusive)
	* @return the range of matching expando rows
	*/
	public static List<ExpandoRow> findByClassPK(long classPK, int start,
		int end) {
		return getPersistence().findByClassPK(classPK, start, end);
	}

	/**
	* Returns an ordered range of all the expando rows where classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoRowModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classPK the class p k
	* @param start the lower bound of the range of expando rows
	* @param end the upper bound of the range of expando rows (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando rows
	*/
	public static List<ExpandoRow> findByClassPK(long classPK, int start,
		int end, OrderByComparator<ExpandoRow> orderByComparator) {
		return getPersistence()
				   .findByClassPK(classPK, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the expando rows where classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoRowModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classPK the class p k
	* @param start the lower bound of the range of expando rows
	* @param end the upper bound of the range of expando rows (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching expando rows
	*/
	public static List<ExpandoRow> findByClassPK(long classPK, int start,
		int end, OrderByComparator<ExpandoRow> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByClassPK(classPK, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first expando row in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando row
	* @throws NoSuchRowException if a matching expando row could not be found
	*/
	public static ExpandoRow findByClassPK_First(long classPK,
		OrderByComparator<ExpandoRow> orderByComparator)
		throws com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence().findByClassPK_First(classPK, orderByComparator);
	}

	/**
	* Returns the first expando row in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando row, or <code>null</code> if a matching expando row could not be found
	*/
	public static ExpandoRow fetchByClassPK_First(long classPK,
		OrderByComparator<ExpandoRow> orderByComparator) {
		return getPersistence().fetchByClassPK_First(classPK, orderByComparator);
	}

	/**
	* Returns the last expando row in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando row
	* @throws NoSuchRowException if a matching expando row could not be found
	*/
	public static ExpandoRow findByClassPK_Last(long classPK,
		OrderByComparator<ExpandoRow> orderByComparator)
		throws com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence().findByClassPK_Last(classPK, orderByComparator);
	}

	/**
	* Returns the last expando row in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando row, or <code>null</code> if a matching expando row could not be found
	*/
	public static ExpandoRow fetchByClassPK_Last(long classPK,
		OrderByComparator<ExpandoRow> orderByComparator) {
		return getPersistence().fetchByClassPK_Last(classPK, orderByComparator);
	}

	/**
	* Returns the expando rows before and after the current expando row in the ordered set where classPK = &#63;.
	*
	* @param rowId the primary key of the current expando row
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando row
	* @throws NoSuchRowException if a expando row with the primary key could not be found
	*/
	public static ExpandoRow[] findByClassPK_PrevAndNext(long rowId,
		long classPK, OrderByComparator<ExpandoRow> orderByComparator)
		throws com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence()
				   .findByClassPK_PrevAndNext(rowId, classPK, orderByComparator);
	}

	/**
	* Removes all the expando rows where classPK = &#63; from the database.
	*
	* @param classPK the class p k
	*/
	public static void removeByClassPK(long classPK) {
		getPersistence().removeByClassPK(classPK);
	}

	/**
	* Returns the number of expando rows where classPK = &#63;.
	*
	* @param classPK the class p k
	* @return the number of matching expando rows
	*/
	public static int countByClassPK(long classPK) {
		return getPersistence().countByClassPK(classPK);
	}

	/**
	* Returns the expando row where tableId = &#63; and classPK = &#63; or throws a {@link NoSuchRowException} if it could not be found.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @return the matching expando row
	* @throws NoSuchRowException if a matching expando row could not be found
	*/
	public static ExpandoRow findByT_C(long tableId, long classPK)
		throws com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence().findByT_C(tableId, classPK);
	}

	/**
	* Returns the expando row where tableId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @return the matching expando row, or <code>null</code> if a matching expando row could not be found
	*/
	public static ExpandoRow fetchByT_C(long tableId, long classPK) {
		return getPersistence().fetchByT_C(tableId, classPK);
	}

	/**
	* Returns the expando row where tableId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching expando row, or <code>null</code> if a matching expando row could not be found
	*/
	public static ExpandoRow fetchByT_C(long tableId, long classPK,
		boolean retrieveFromCache) {
		return getPersistence().fetchByT_C(tableId, classPK, retrieveFromCache);
	}

	/**
	* Removes the expando row where tableId = &#63; and classPK = &#63; from the database.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @return the expando row that was removed
	*/
	public static ExpandoRow removeByT_C(long tableId, long classPK)
		throws com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence().removeByT_C(tableId, classPK);
	}

	/**
	* Returns the number of expando rows where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @return the number of matching expando rows
	*/
	public static int countByT_C(long tableId, long classPK) {
		return getPersistence().countByT_C(tableId, classPK);
	}

	/**
	* Caches the expando row in the entity cache if it is enabled.
	*
	* @param expandoRow the expando row
	*/
	public static void cacheResult(ExpandoRow expandoRow) {
		getPersistence().cacheResult(expandoRow);
	}

	/**
	* Caches the expando rows in the entity cache if it is enabled.
	*
	* @param expandoRows the expando rows
	*/
	public static void cacheResult(List<ExpandoRow> expandoRows) {
		getPersistence().cacheResult(expandoRows);
	}

	/**
	* Creates a new expando row with the primary key. Does not add the expando row to the database.
	*
	* @param rowId the primary key for the new expando row
	* @return the new expando row
	*/
	public static ExpandoRow create(long rowId) {
		return getPersistence().create(rowId);
	}

	/**
	* Removes the expando row with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param rowId the primary key of the expando row
	* @return the expando row that was removed
	* @throws NoSuchRowException if a expando row with the primary key could not be found
	*/
	public static ExpandoRow remove(long rowId)
		throws com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence().remove(rowId);
	}

	public static ExpandoRow updateImpl(ExpandoRow expandoRow) {
		return getPersistence().updateImpl(expandoRow);
	}

	/**
	* Returns the expando row with the primary key or throws a {@link NoSuchRowException} if it could not be found.
	*
	* @param rowId the primary key of the expando row
	* @return the expando row
	* @throws NoSuchRowException if a expando row with the primary key could not be found
	*/
	public static ExpandoRow findByPrimaryKey(long rowId)
		throws com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence().findByPrimaryKey(rowId);
	}

	/**
	* Returns the expando row with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param rowId the primary key of the expando row
	* @return the expando row, or <code>null</code> if a expando row with the primary key could not be found
	*/
	public static ExpandoRow fetchByPrimaryKey(long rowId) {
		return getPersistence().fetchByPrimaryKey(rowId);
	}

	public static java.util.Map<java.io.Serializable, ExpandoRow> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the expando rows.
	*
	* @return the expando rows
	*/
	public static List<ExpandoRow> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the expando rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoRowModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando rows
	* @param end the upper bound of the range of expando rows (not inclusive)
	* @return the range of expando rows
	*/
	public static List<ExpandoRow> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the expando rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoRowModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando rows
	* @param end the upper bound of the range of expando rows (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of expando rows
	*/
	public static List<ExpandoRow> findAll(int start, int end,
		OrderByComparator<ExpandoRow> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the expando rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoRowModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando rows
	* @param end the upper bound of the range of expando rows (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of expando rows
	*/
	public static List<ExpandoRow> findAll(int start, int end,
		OrderByComparator<ExpandoRow> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the expando rows from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of expando rows.
	*
	* @return the number of expando rows
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static ExpandoRowPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ExpandoRowPersistence)PortalBeanLocatorUtil.locate(ExpandoRowPersistence.class.getName());

			ReferenceRegistry.registerReference(ExpandoRowUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static ExpandoRowPersistence _persistence;
}