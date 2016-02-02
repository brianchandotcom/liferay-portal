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

import com.liferay.portlet.expando.exception.NoSuchRowException;
import com.liferay.portlet.expando.model.ExpandoRow;

/**
 * The persistence interface for the expando row service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.expando.service.persistence.impl.ExpandoRowPersistenceImpl
 * @see ExpandoRowUtil
 * @generated
 */
@ProviderType
public interface ExpandoRowPersistence extends BasePersistence<ExpandoRow> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExpandoRowUtil} to access the expando row persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the expando rows where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the matching expando rows
	*/
	public java.util.List<ExpandoRow> findByTableId(long tableId);

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
	public java.util.List<ExpandoRow> findByTableId(long tableId, int start,
		int end);

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
	public java.util.List<ExpandoRow> findByTableId(long tableId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator);

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
	public java.util.List<ExpandoRow> findByTableId(long tableId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first expando row in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando row
	* @throws NoSuchRowException if a matching expando row could not be found
	*/
	public ExpandoRow findByTableId_First(long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator)
		throws NoSuchRowException;

	/**
	* Returns the first expando row in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando row, or <code>null</code> if a matching expando row could not be found
	*/
	public ExpandoRow fetchByTableId_First(long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator);

	/**
	* Returns the last expando row in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando row
	* @throws NoSuchRowException if a matching expando row could not be found
	*/
	public ExpandoRow findByTableId_Last(long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator)
		throws NoSuchRowException;

	/**
	* Returns the last expando row in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando row, or <code>null</code> if a matching expando row could not be found
	*/
	public ExpandoRow fetchByTableId_Last(long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator);

	/**
	* Returns the expando rows before and after the current expando row in the ordered set where tableId = &#63;.
	*
	* @param rowId the primary key of the current expando row
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando row
	* @throws NoSuchRowException if a expando row with the primary key could not be found
	*/
	public ExpandoRow[] findByTableId_PrevAndNext(long rowId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator)
		throws NoSuchRowException;

	/**
	* Removes all the expando rows where tableId = &#63; from the database.
	*
	* @param tableId the table ID
	*/
	public void removeByTableId(long tableId);

	/**
	* Returns the number of expando rows where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the number of matching expando rows
	*/
	public int countByTableId(long tableId);

	/**
	* Returns all the expando rows where classPK = &#63;.
	*
	* @param classPK the class p k
	* @return the matching expando rows
	*/
	public java.util.List<ExpandoRow> findByClassPK(long classPK);

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
	public java.util.List<ExpandoRow> findByClassPK(long classPK, int start,
		int end);

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
	public java.util.List<ExpandoRow> findByClassPK(long classPK, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator);

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
	public java.util.List<ExpandoRow> findByClassPK(long classPK, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first expando row in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando row
	* @throws NoSuchRowException if a matching expando row could not be found
	*/
	public ExpandoRow findByClassPK_First(long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator)
		throws NoSuchRowException;

	/**
	* Returns the first expando row in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando row, or <code>null</code> if a matching expando row could not be found
	*/
	public ExpandoRow fetchByClassPK_First(long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator);

	/**
	* Returns the last expando row in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando row
	* @throws NoSuchRowException if a matching expando row could not be found
	*/
	public ExpandoRow findByClassPK_Last(long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator)
		throws NoSuchRowException;

	/**
	* Returns the last expando row in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando row, or <code>null</code> if a matching expando row could not be found
	*/
	public ExpandoRow fetchByClassPK_Last(long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator);

	/**
	* Returns the expando rows before and after the current expando row in the ordered set where classPK = &#63;.
	*
	* @param rowId the primary key of the current expando row
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando row
	* @throws NoSuchRowException if a expando row with the primary key could not be found
	*/
	public ExpandoRow[] findByClassPK_PrevAndNext(long rowId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator)
		throws NoSuchRowException;

	/**
	* Removes all the expando rows where classPK = &#63; from the database.
	*
	* @param classPK the class p k
	*/
	public void removeByClassPK(long classPK);

	/**
	* Returns the number of expando rows where classPK = &#63;.
	*
	* @param classPK the class p k
	* @return the number of matching expando rows
	*/
	public int countByClassPK(long classPK);

	/**
	* Returns the expando row where tableId = &#63; and classPK = &#63; or throws a {@link NoSuchRowException} if it could not be found.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @return the matching expando row
	* @throws NoSuchRowException if a matching expando row could not be found
	*/
	public ExpandoRow findByT_C(long tableId, long classPK)
		throws NoSuchRowException;

	/**
	* Returns the expando row where tableId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @return the matching expando row, or <code>null</code> if a matching expando row could not be found
	*/
	public ExpandoRow fetchByT_C(long tableId, long classPK);

	/**
	* Returns the expando row where tableId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching expando row, or <code>null</code> if a matching expando row could not be found
	*/
	public ExpandoRow fetchByT_C(long tableId, long classPK,
		boolean retrieveFromCache);

	/**
	* Removes the expando row where tableId = &#63; and classPK = &#63; from the database.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @return the expando row that was removed
	*/
	public ExpandoRow removeByT_C(long tableId, long classPK)
		throws NoSuchRowException;

	/**
	* Returns the number of expando rows where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @return the number of matching expando rows
	*/
	public int countByT_C(long tableId, long classPK);

	/**
	* Caches the expando row in the entity cache if it is enabled.
	*
	* @param expandoRow the expando row
	*/
	public void cacheResult(ExpandoRow expandoRow);

	/**
	* Caches the expando rows in the entity cache if it is enabled.
	*
	* @param expandoRows the expando rows
	*/
	public void cacheResult(java.util.List<ExpandoRow> expandoRows);

	/**
	* Creates a new expando row with the primary key. Does not add the expando row to the database.
	*
	* @param rowId the primary key for the new expando row
	* @return the new expando row
	*/
	public ExpandoRow create(long rowId);

	/**
	* Removes the expando row with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param rowId the primary key of the expando row
	* @return the expando row that was removed
	* @throws NoSuchRowException if a expando row with the primary key could not be found
	*/
	public ExpandoRow remove(long rowId) throws NoSuchRowException;

	public ExpandoRow updateImpl(ExpandoRow expandoRow);

	/**
	* Returns the expando row with the primary key or throws a {@link NoSuchRowException} if it could not be found.
	*
	* @param rowId the primary key of the expando row
	* @return the expando row
	* @throws NoSuchRowException if a expando row with the primary key could not be found
	*/
	public ExpandoRow findByPrimaryKey(long rowId) throws NoSuchRowException;

	/**
	* Returns the expando row with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param rowId the primary key of the expando row
	* @return the expando row, or <code>null</code> if a expando row with the primary key could not be found
	*/
	public ExpandoRow fetchByPrimaryKey(long rowId);

	@Override
	public java.util.Map<java.io.Serializable, ExpandoRow> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the expando rows.
	*
	* @return the expando rows
	*/
	public java.util.List<ExpandoRow> findAll();

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
	public java.util.List<ExpandoRow> findAll(int start, int end);

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
	public java.util.List<ExpandoRow> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator);

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
	public java.util.List<ExpandoRow> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoRow> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the expando rows from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of expando rows.
	*
	* @return the number of expando rows
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}