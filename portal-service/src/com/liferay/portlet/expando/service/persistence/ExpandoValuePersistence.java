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

import com.liferay.portlet.expando.model.ExpandoValue;

/**
 * The persistence interface for the expando value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValuePersistenceImpl
 * @see ExpandoValueUtil
 * @generated
 */
public interface ExpandoValuePersistence extends BasePersistence<ExpandoValue> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExpandoValueUtil} to access the expando value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the expando value in the entity cache if it is enabled.
	*
	* @param expandoValue the expando value
	*/
	public void cacheResult(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue);

	/**
	* Caches the expando values in the entity cache if it is enabled.
	*
	* @param expandoValues the expando values
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.expando.model.ExpandoValue> expandoValues);

	/**
	* Creates a new expando value with the primary key. Does not add the expando value to the database.
	*
	* @param valueId the primary key for the new expando value
	* @return the new expando value
	*/
	public com.liferay.portlet.expando.model.ExpandoValue create(long valueId);

	/**
	* Removes the expando value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param valueId the primary key of the expando value
	* @return the expando value that was removed
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue remove(long valueId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue updateImpl(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the expando value with the primary key or throws a {@link com.liferay.portlet.expando.NoSuchValueException} if it could not be found.
	*
	* @param valueId the primary key of the expando value
	* @return the expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByPrimaryKey(
		long valueId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the expando value with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param valueId the primary key of the expando value
	* @return the expando value, or <code>null</code> if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue fetchByPrimaryKey(
		long valueId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the expando values where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByTableId(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the expando values where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByTableId(
		long tableId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the expando values where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByTableId(
		long tableId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first expando value in the ordered set where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByTableId_First(
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByTableId_Last(
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the expando values before and after the current expando value in the ordered set where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param valueId the primary key of the current expando value
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue[] findByTableId_PrevAndNext(
		long valueId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns all the expando values where columnId = &#63;.
	*
	* @param columnId the column ID
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByColumnId(
		long columnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the expando values where columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param columnId the column ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByColumnId(
		long columnId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the expando values where columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param columnId the column ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByColumnId(
		long columnId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first expando value in the ordered set where columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByColumnId_First(
		long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByColumnId_Last(
		long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the expando values before and after the current expando value in the ordered set where columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param valueId the primary key of the current expando value
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue[] findByColumnId_PrevAndNext(
		long valueId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns all the expando values where rowId = &#63;.
	*
	* @param rowId the row ID
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByRowId(
		long rowId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the expando values where rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param rowId the row ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByRowId(
		long rowId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the expando values where rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param rowId the row ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByRowId(
		long rowId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first expando value in the ordered set where rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByRowId_First(
		long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByRowId_Last(
		long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the expando values before and after the current expando value in the ordered set where rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param valueId the primary key of the current expando value
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue[] findByRowId_PrevAndNext(
		long valueId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns all the expando values where tableId = &#63; and columnId = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C(
		long tableId, long columnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the expando values where tableId = &#63; and columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C(
		long tableId, long columnId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C(
		long tableId, long columnId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByT_C_First(
		long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByT_C_Last(
		long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param valueId the primary key of the current expando value
	* @param tableId the table ID
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue[] findByT_C_PrevAndNext(
		long valueId, long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns all the expando values where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_CPK(
		long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the expando values where tableId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_CPK(
		long tableId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_CPK(
		long tableId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByT_CPK_First(
		long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByT_CPK_Last(
		long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param valueId the primary key of the current expando value
	* @param tableId the table ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue[] findByT_CPK_PrevAndNext(
		long valueId, long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns all the expando values where tableId = &#63; and rowId = &#63;.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_R(
		long tableId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the expando values where tableId = &#63; and rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_R(
		long tableId, long rowId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_R(
		long tableId, long rowId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByT_R_First(
		long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByT_R_Last(
		long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param valueId the primary key of the current expando value
	* @param tableId the table ID
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue[] findByT_R_PrevAndNext(
		long valueId, long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the expando value where columnId = &#63; and rowId = &#63; or throws a {@link com.liferay.portlet.expando.NoSuchValueException} if it could not be found.
	*
	* @param columnId the column ID
	* @param rowId the row ID
	* @return the matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByC_R(
		long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the expando value where columnId = &#63; and rowId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param columnId the column ID
	* @param rowId the row ID
	* @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue fetchByC_R(
		long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the expando value where columnId = &#63; and rowId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param columnId the column ID
	* @param rowId the row ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue fetchByC_R(
		long columnId, long rowId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the expando values where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the expando values where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the expando values where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the expando values before and after the current expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param valueId the primary key of the current expando value
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue[] findByC_C_PrevAndNext(
		long valueId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

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
	public com.liferay.portlet.expando.model.ExpandoValue findByT_C_C(
		long tableId, long columnId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param classPK the class p k
	* @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue fetchByT_C_C(
		long tableId, long columnId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.expando.model.ExpandoValue fetchByT_C_C(
		long tableId, long columnId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @return the matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C_D(
		long tableId, long columnId, java.lang.String data)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C_D(
		long tableId, long columnId, java.lang.String data, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C_D(
		long tableId, long columnId, java.lang.String data, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByT_C_D_First(
		long tableId, long columnId, java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue findByT_C_D_Last(
		long tableId, long columnId, java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
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
	public com.liferay.portlet.expando.model.ExpandoValue[] findByT_C_D_PrevAndNext(
		long valueId, long tableId, long columnId, java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Returns all the expando values.
	*
	* @return the expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the expando values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the expando values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of expando values
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the expando values where tableId = &#63; from the database.
	*
	* @param tableId the table ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the expando values where columnId = &#63; from the database.
	*
	* @param columnId the column ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByColumnId(long columnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the expando values where rowId = &#63; from the database.
	*
	* @param rowId the row ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByRowId(long rowId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the expando values where tableId = &#63; and columnId = &#63; from the database.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByT_C(long tableId, long columnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the expando values where tableId = &#63; and classPK = &#63; from the database.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public void removeByT_CPK(long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the expando values where tableId = &#63; and rowId = &#63; from the database.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByT_R(long tableId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the expando value where columnId = &#63; and rowId = &#63; from the database.
	*
	* @param columnId the column ID
	* @param rowId the row ID
	* @return the expando value that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue removeByC_R(
		long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Removes all the expando values where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; from the database.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param classPK the class p k
	* @return the expando value that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoValue removeByT_C_C(
		long tableId, long columnId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	/**
	* Removes all the expando values where tableId = &#63; and columnId = &#63; and data = &#63; from the database.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @throws SystemException if a system exception occurred
	*/
	public void removeByT_C_D(long tableId, long columnId, java.lang.String data)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the expando values from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando values where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public int countByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando values where columnId = &#63;.
	*
	* @param columnId the column ID
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public int countByColumnId(long columnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando values where rowId = &#63;.
	*
	* @param rowId the row ID
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public int countByRowId(long rowId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando values where tableId = &#63; and columnId = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public int countByT_C(long tableId, long columnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando values where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public int countByT_CPK(long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando values where tableId = &#63; and rowId = &#63;.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public int countByT_R(long tableId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando values where columnId = &#63; and rowId = &#63;.
	*
	* @param columnId the column ID
	* @param rowId the row ID
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_R(long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando values where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando values where tableId = &#63; and columnId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param classPK the class p k
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public int countByT_C_C(long tableId, long columnId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @return the number of matching expando values
	* @throws SystemException if a system exception occurred
	*/
	public int countByT_C_D(long tableId, long columnId, java.lang.String data)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of expando values.
	*
	* @return the number of expando values
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}