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

import com.liferay.portlet.expando.model.ExpandoValue;

import java.util.List;

/**
 * The persistence utility for the expando value service. This utility wraps {@link ExpandoValuePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValuePersistence
 * @see ExpandoValuePersistenceImpl
 * @generated
 */
public class ExpandoValueUtil {
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
	public static void clearCache(ExpandoValue expandoValue) {
		getPersistence().clearCache(expandoValue);
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
	public static List<ExpandoValue> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ExpandoValue> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ExpandoValue> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ExpandoValue update(ExpandoValue expandoValue)
		throws SystemException {
		return getPersistence().update(expandoValue);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ExpandoValue update(ExpandoValue expandoValue,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(expandoValue, serviceContext);
	}

	/**
	* Returns all the expando values where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByTableId(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTableId(tableId);
	}

	/**
	* Returns a range of all the expando values where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByTableId(
		long tableId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTableId(tableId, start, end);
	}

	/**
	* Returns an ordered range of all the expando values where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByTableId(
		long tableId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByTableId(tableId, start, end, orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByTableId_First(
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByTableId_First(tableId, orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByTableId_First(
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByTableId_First(tableId, orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByTableId_Last(
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByTableId_Last(tableId, orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByTableId_Last(
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByTableId_Last(tableId, orderByComparator);
	}

	/**
	* Returns the expando values before and after the current expando value in the ordered set where tableId = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue[] findByTableId_PrevAndNext(
		long valueId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByTableId_PrevAndNext(valueId, tableId,
			orderByComparator);
	}

	/**
	* Removes all the expando values where tableId = &#63; from the database.
	*
	* @param tableId the table ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByTableId(tableId);
	}

	/**
	* Returns the number of expando values where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static int countByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByTableId(tableId);
	}

	/**
	* Returns all the expando values where columnId = &#63;.
	*
	* @param columnId the column ID
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByColumnId(
		long columnId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByColumnId(columnId);
	}

	/**
	* Returns a range of all the expando values where columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param columnId the column ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByColumnId(
		long columnId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByColumnId(columnId, start, end);
	}

	/**
	* Returns an ordered range of all the expando values where columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param columnId the column ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByColumnId(
		long columnId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByColumnId(columnId, start, end, orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where columnId = &#63;.
	*
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByColumnId_First(
		long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByColumnId_First(columnId, orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where columnId = &#63;.
	*
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByColumnId_First(
		long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByColumnId_First(columnId, orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where columnId = &#63;.
	*
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByColumnId_Last(
		long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByColumnId_Last(columnId, orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where columnId = &#63;.
	*
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByColumnId_Last(
		long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByColumnId_Last(columnId, orderByComparator);
	}

	/**
	* Returns the expando values before and after the current expando value in the ordered set where columnId = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue[] findByColumnId_PrevAndNext(
		long valueId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByColumnId_PrevAndNext(valueId, columnId,
			orderByComparator);
	}

	/**
	* Removes all the expando values where columnId = &#63; from the database.
	*
	* @param columnId the column ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByColumnId(long columnId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByColumnId(columnId);
	}

	/**
	* Returns the number of expando values where columnId = &#63;.
	*
	* @param columnId the column ID
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static int countByColumnId(long columnId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByColumnId(columnId);
	}

	/**
	* Returns all the expando values where rowId = &#63;.
	*
	* @param rowId the row ID
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByRowId(
		long rowId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRowId(rowId);
	}

	/**
	* Returns a range of all the expando values where rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param rowId the row ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByRowId(
		long rowId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRowId(rowId, start, end);
	}

	/**
	* Returns an ordered range of all the expando values where rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param rowId the row ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByRowId(
		long rowId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRowId(rowId, start, end, orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where rowId = &#63;.
	*
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByRowId_First(
		long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByRowId_First(rowId, orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where rowId = &#63;.
	*
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByRowId_First(
		long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByRowId_First(rowId, orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where rowId = &#63;.
	*
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByRowId_Last(
		long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByRowId_Last(rowId, orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where rowId = &#63;.
	*
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByRowId_Last(
		long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByRowId_Last(rowId, orderByComparator);
	}

	/**
	* Returns the expando values before and after the current expando value in the ordered set where rowId = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue[] findByRowId_PrevAndNext(
		long valueId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByRowId_PrevAndNext(valueId, rowId, orderByComparator);
	}

	/**
	* Removes all the expando values where rowId = &#63; from the database.
	*
	* @param rowId the row ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByRowId(long rowId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByRowId(rowId);
	}

	/**
	* Returns the number of expando values where rowId = &#63;.
	*
	* @param rowId the row ID
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static int countByRowId(long rowId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByRowId(rowId);
	}

	/**
	* Returns all the expando values where tableId = &#63; and columnId = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C(
		long tableId, long columnId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_C(tableId, columnId);
	}

	/**
	* Returns a range of all the expando values where tableId = &#63; and columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C(
		long tableId, long columnId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_C(tableId, columnId, start, end);
	}

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C(
		long tableId, long columnId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByT_C(tableId, columnId, start, end, orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByT_C_First(
		long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByT_C_First(tableId, columnId, orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByT_C_First(
		long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByT_C_First(tableId, columnId, orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByT_C_Last(
		long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByT_C_Last(tableId, columnId, orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByT_C_Last(
		long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByT_C_Last(tableId, columnId, orderByComparator);
	}

	/**
	* Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param tableId the table ID
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue[] findByT_C_PrevAndNext(
		long valueId, long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByT_C_PrevAndNext(valueId, tableId, columnId,
			orderByComparator);
	}

	/**
	* Removes all the expando values where tableId = &#63; and columnId = &#63; from the database.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByT_C(long tableId, long columnId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByT_C(tableId, columnId);
	}

	/**
	* Returns the number of expando values where tableId = &#63; and columnId = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static int countByT_C(long tableId, long columnId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_C(tableId, columnId);
	}

	/**
	* Returns all the expando values where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_CPK(
		long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_CPK(tableId, classPK);
	}

	/**
	* Returns a range of all the expando values where tableId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_CPK(
		long tableId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_CPK(tableId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_CPK(
		long tableId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByT_CPK(tableId, classPK, start, end, orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByT_CPK_First(
		long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByT_CPK_First(tableId, classPK, orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByT_CPK_First(
		long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByT_CPK_First(tableId, classPK, orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByT_CPK_Last(
		long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByT_CPK_Last(tableId, classPK, orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByT_CPK_Last(
		long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByT_CPK_Last(tableId, classPK, orderByComparator);
	}

	/**
	* Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param tableId the table ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue[] findByT_CPK_PrevAndNext(
		long valueId, long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByT_CPK_PrevAndNext(valueId, tableId, classPK,
			orderByComparator);
	}

	/**
	* Removes all the expando values where tableId = &#63; and classPK = &#63; from the database.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByT_CPK(long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByT_CPK(tableId, classPK);
	}

	/**
	* Returns the number of expando values where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static int countByT_CPK(long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_CPK(tableId, classPK);
	}

	/**
	* Returns all the expando values where tableId = &#63; and rowId = &#63;.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_R(
		long tableId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_R(tableId, rowId);
	}

	/**
	* Returns a range of all the expando values where tableId = &#63; and rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_R(
		long tableId, long rowId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_R(tableId, rowId, start, end);
	}

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_R(
		long tableId, long rowId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByT_R(tableId, rowId, start, end, orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByT_R_First(
		long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByT_R_First(tableId, rowId, orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByT_R_First(
		long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByT_R_First(tableId, rowId, orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByT_R_Last(
		long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByT_R_Last(tableId, rowId, orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByT_R_Last(
		long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByT_R_Last(tableId, rowId, orderByComparator);
	}

	/**
	* Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param tableId the table ID
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue[] findByT_R_PrevAndNext(
		long valueId, long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByT_R_PrevAndNext(valueId, tableId, rowId,
			orderByComparator);
	}

	/**
	* Removes all the expando values where tableId = &#63; and rowId = &#63; from the database.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByT_R(long tableId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByT_R(tableId, rowId);
	}

	/**
	* Returns the number of expando values where tableId = &#63; and rowId = &#63;.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static int countByT_R(long tableId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_R(tableId, rowId);
	}

	/**
	* Returns the expando value where columnId = &#63; and rowId = &#63; or throws a {@link com.liferay.portlet.expando.NoSuchValueException} if it could not be found.
	*
	* @param columnId the column ID
	* @param rowId the row ID
	* @return the matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByC_R(
		long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByC_R(columnId, rowId);
	}

	/**
	* Returns the expando value where columnId = &#63; and rowId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param columnId the column ID
	* @param rowId the row ID
	* @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByC_R(
		long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_R(columnId, rowId);
	}

	/**
	* Returns the expando value where columnId = &#63; and rowId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param columnId the column ID
	* @param rowId the row ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByC_R(
		long columnId, long rowId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_R(columnId, rowId, retrieveFromCache);
	}

	/**
	* Removes the expando value where columnId = &#63; and rowId = &#63; from the database.
	*
	* @param columnId the column ID
	* @param rowId the row ID
	* @return the expando value that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue removeByC_R(
		long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().removeByC_R(columnId, rowId);
	}

	/**
	* Returns the number of expando values where columnId = &#63; and rowId = &#63;.
	*
	* @param columnId the column ID
	* @param rowId the row ID
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_R(long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_R(columnId, rowId);
	}

	/**
	* Returns all the expando values where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the expando values where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the expando values where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the expando values before and after the current expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue[] findByC_C_PrevAndNext(
		long valueId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByC_C_PrevAndNext(valueId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Removes all the expando values where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of expando values where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.expando.NoSuchValueException} if it could not be found.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param classPK the class p k
	* @return the matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByT_C_C(
		long tableId, long columnId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByT_C_C(tableId, columnId, classPK);
	}

	/**
	* Returns the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param classPK the class p k
	* @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByT_C_C(
		long tableId, long columnId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByT_C_C(tableId, columnId, classPK);
	}

	/**
	* Returns the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByT_C_C(
		long tableId, long columnId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByT_C_C(tableId, columnId, classPK, retrieveFromCache);
	}

	/**
	* Removes the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; from the database.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param classPK the class p k
	* @return the expando value that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue removeByT_C_C(
		long tableId, long columnId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().removeByT_C_C(tableId, columnId, classPK);
	}

	/**
	* Returns the number of expando values where tableId = &#63; and columnId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param classPK the class p k
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static int countByT_C_C(long tableId, long columnId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_C_C(tableId, columnId, classPK);
	}

	/**
	* Returns all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C_D(
		long tableId, long columnId, java.lang.String data)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_C_D(tableId, columnId, data);
	}

	/**
	* Returns a range of all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C_D(
		long tableId, long columnId, java.lang.String data, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_C_D(tableId, columnId, data, start, end);
	}

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C_D(
		long tableId, long columnId, java.lang.String data, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByT_C_D(tableId, columnId, data, start, end,
			orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByT_C_D_First(
		long tableId, long columnId, java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByT_C_D_First(tableId, columnId, data, orderByComparator);
	}

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByT_C_D_First(
		long tableId, long columnId, java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByT_C_D_First(tableId, columnId, data,
			orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByT_C_D_Last(
		long tableId, long columnId, java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByT_C_D_Last(tableId, columnId, data, orderByComparator);
	}

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByT_C_D_Last(
		long tableId, long columnId, java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByT_C_D_Last(tableId, columnId, data, orderByComparator);
	}

	/**
	* Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue[] findByT_C_D_PrevAndNext(
		long valueId, long tableId, long columnId, java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByT_C_D_PrevAndNext(valueId, tableId, columnId, data,
			orderByComparator);
	}

	/**
	* Removes all the expando values where tableId = &#63; and columnId = &#63; and data = &#63; from the database.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByT_C_D(long tableId, long columnId,
		java.lang.String data)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByT_C_D(tableId, columnId, data);
	}

	/**
	* Returns the number of expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public static int countByT_C_D(long tableId, long columnId,
		java.lang.String data)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_C_D(tableId, columnId, data);
	}

	/**
	* Caches the expando value in the entity cache if it is enabled.
	*
	* @param expandoValue the expando value
	*/
	public static void cacheResult(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue) {
		getPersistence().cacheResult(expandoValue);
	}

	/**
	* Caches the expando values in the entity cache if it is enabled.
	*
	* @param expandoValues the expando values
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.expando.model.ExpandoValue> expandoValues) {
		getPersistence().cacheResult(expandoValues);
	}

	/**
	* Creates a new expando value with the primary key. Does not add the expando value to the database.
	*
	* @param valueId the primary key for the new expando value
	* @return the new expando value
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue create(
		long valueId) {
		return getPersistence().create(valueId);
	}

	/**
	* Removes the expando value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param valueId the primary key of the expando value
	* @return the expando value that was removed
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue remove(
		long valueId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().remove(valueId);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue updateImpl(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(expandoValue);
	}

	/**
	* Returns the expando value with the primary key or throws a {@link com.liferay.portlet.expando.NoSuchValueException} if it could not be found.
	*
	* @param valueId the primary key of the expando value
	* @return the expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue findByPrimaryKey(
		long valueId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByPrimaryKey(valueId);
	}

	/**
	* Returns the expando value with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param valueId the primary key of the expando value
	* @return the expando value, or <code>null</code> if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoValue fetchByPrimaryKey(
		long valueId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(valueId);
	}

	/**
	* Returns all the expando values.
	*
	* @return the expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the expando values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the expando values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of expando values
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the expando values from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of expando values.
	*
	* @return the number of expando values
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ExpandoValuePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ExpandoValuePersistence)PortalBeanLocatorUtil.locate(ExpandoValuePersistence.class.getName());

			ReferenceRegistry.registerReference(ExpandoValueUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(ExpandoValuePersistence persistence) {
	}

	private static ExpandoValuePersistence _persistence;
}