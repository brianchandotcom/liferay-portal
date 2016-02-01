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

import com.liferay.portlet.expando.exception.NoSuchValueException;
import com.liferay.portlet.expando.model.ExpandoValue;

/**
 * The persistence interface for the expando value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.expando.service.persistence.impl.ExpandoValuePersistenceImpl
 * @see ExpandoValueUtil
 * @generated
 */
@ProviderType
public interface ExpandoValuePersistence extends BasePersistence<ExpandoValue> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExpandoValueUtil} to access the expando value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the expando values where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the matching expando values
	*/
	public java.util.List<ExpandoValue> findByTableId(long tableId);

	/**
	* Returns a range of all the expando values where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByTableId(long tableId, int start,
		int end);

	/**
	* Returns an ordered range of all the expando values where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByTableId(long tableId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns an ordered range of all the expando values where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByTableId(long tableId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first expando value in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByTableId_First(long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the first expando value in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByTableId_First(long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the last expando value in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByTableId_Last(long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where tableId = &#63;.
	*
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByTableId_Last(long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the expando values before and after the current expando value in the ordered set where tableId = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param tableId the table ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws NoSuchValueException if a expando value with the primary key could not be found
	*/
	public ExpandoValue[] findByTableId_PrevAndNext(long valueId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Removes all the expando values where tableId = &#63; from the database.
	*
	* @param tableId the table ID
	*/
	public void removeByTableId(long tableId);

	/**
	* Returns the number of expando values where tableId = &#63;.
	*
	* @param tableId the table ID
	* @return the number of matching expando values
	*/
	public int countByTableId(long tableId);

	/**
	* Returns all the expando values where columnId = &#63;.
	*
	* @param columnId the column ID
	* @return the matching expando values
	*/
	public java.util.List<ExpandoValue> findByColumnId(long columnId);

	/**
	* Returns a range of all the expando values where columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param columnId the column ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByColumnId(long columnId,
		int start, int end);

	/**
	* Returns an ordered range of all the expando values where columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param columnId the column ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByColumnId(long columnId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns an ordered range of all the expando values where columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param columnId the column ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByColumnId(long columnId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first expando value in the ordered set where columnId = &#63;.
	*
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByColumnId_First(long columnId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the first expando value in the ordered set where columnId = &#63;.
	*
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByColumnId_First(long columnId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the last expando value in the ordered set where columnId = &#63;.
	*
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByColumnId_Last(long columnId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where columnId = &#63;.
	*
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByColumnId_Last(long columnId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the expando values before and after the current expando value in the ordered set where columnId = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws NoSuchValueException if a expando value with the primary key could not be found
	*/
	public ExpandoValue[] findByColumnId_PrevAndNext(long valueId,
		long columnId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Removes all the expando values where columnId = &#63; from the database.
	*
	* @param columnId the column ID
	*/
	public void removeByColumnId(long columnId);

	/**
	* Returns the number of expando values where columnId = &#63;.
	*
	* @param columnId the column ID
	* @return the number of matching expando values
	*/
	public int countByColumnId(long columnId);

	/**
	* Returns all the expando values where rowId = &#63;.
	*
	* @param rowId the row ID
	* @return the matching expando values
	*/
	public java.util.List<ExpandoValue> findByRowId(long rowId);

	/**
	* Returns a range of all the expando values where rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param rowId the row ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByRowId(long rowId, int start,
		int end);

	/**
	* Returns an ordered range of all the expando values where rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param rowId the row ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByRowId(long rowId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns an ordered range of all the expando values where rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param rowId the row ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByRowId(long rowId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first expando value in the ordered set where rowId = &#63;.
	*
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByRowId_First(long rowId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the first expando value in the ordered set where rowId = &#63;.
	*
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByRowId_First(long rowId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the last expando value in the ordered set where rowId = &#63;.
	*
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByRowId_Last(long rowId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where rowId = &#63;.
	*
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByRowId_Last(long rowId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the expando values before and after the current expando value in the ordered set where rowId = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws NoSuchValueException if a expando value with the primary key could not be found
	*/
	public ExpandoValue[] findByRowId_PrevAndNext(long valueId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Removes all the expando values where rowId = &#63; from the database.
	*
	* @param rowId the row ID
	*/
	public void removeByRowId(long rowId);

	/**
	* Returns the number of expando values where rowId = &#63;.
	*
	* @param rowId the row ID
	* @return the number of matching expando values
	*/
	public int countByRowId(long rowId);

	/**
	* Returns all the expando values where tableId = &#63; and columnId = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @return the matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_C(long tableId, long columnId);

	/**
	* Returns a range of all the expando values where tableId = &#63; and columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_C(long tableId, long columnId,
		int start, int end);

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_C(long tableId, long columnId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and columnId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_C(long tableId, long columnId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByT_C_First(long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByT_C_First(long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByT_C_Last(long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByT_C_Last(long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param tableId the table ID
	* @param columnId the column ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws NoSuchValueException if a expando value with the primary key could not be found
	*/
	public ExpandoValue[] findByT_C_PrevAndNext(long valueId, long tableId,
		long columnId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Removes all the expando values where tableId = &#63; and columnId = &#63; from the database.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	*/
	public void removeByT_C(long tableId, long columnId);

	/**
	* Returns the number of expando values where tableId = &#63; and columnId = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @return the number of matching expando values
	*/
	public int countByT_C(long tableId, long columnId);

	/**
	* Returns all the expando values where tableId = &#63; and rowId = &#63;.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @return the matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_R(long tableId, long rowId);

	/**
	* Returns a range of all the expando values where tableId = &#63; and rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_R(long tableId, long rowId,
		int start, int end);

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_R(long tableId, long rowId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and rowId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_R(long tableId, long rowId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByT_R_First(long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByT_R_First(long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByT_R_Last(long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByT_R_Last(long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param tableId the table ID
	* @param rowId the row ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws NoSuchValueException if a expando value with the primary key could not be found
	*/
	public ExpandoValue[] findByT_R_PrevAndNext(long valueId, long tableId,
		long rowId,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Removes all the expando values where tableId = &#63; and rowId = &#63; from the database.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	*/
	public void removeByT_R(long tableId, long rowId);

	/**
	* Returns the number of expando values where tableId = &#63; and rowId = &#63;.
	*
	* @param tableId the table ID
	* @param rowId the row ID
	* @return the number of matching expando values
	*/
	public int countByT_R(long tableId, long rowId);

	/**
	* Returns all the expando values where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @return the matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_CPK(long tableId, long classPK);

	/**
	* Returns a range of all the expando values where tableId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_CPK(long tableId, long classPK,
		int start, int end);

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_CPK(long tableId, long classPK,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_CPK(long tableId, long classPK,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByT_CPK_First(long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByT_CPK_First(long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByT_CPK_Last(long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByT_CPK_Last(long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param tableId the table ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws NoSuchValueException if a expando value with the primary key could not be found
	*/
	public ExpandoValue[] findByT_CPK_PrevAndNext(long valueId, long tableId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Removes all the expando values where tableId = &#63; and classPK = &#63; from the database.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	*/
	public void removeByT_CPK(long tableId, long classPK);

	/**
	* Returns the number of expando values where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param classPK the class p k
	* @return the number of matching expando values
	*/
	public int countByT_CPK(long tableId, long classPK);

	/**
	* Returns the expando value where columnId = &#63; and rowId = &#63; or throws a {@link NoSuchValueException} if it could not be found.
	*
	* @param columnId the column ID
	* @param rowId the row ID
	* @return the matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByC_R(long columnId, long rowId)
		throws NoSuchValueException;

	/**
	* Returns the expando value where columnId = &#63; and rowId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param columnId the column ID
	* @param rowId the row ID
	* @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByC_R(long columnId, long rowId);

	/**
	* Returns the expando value where columnId = &#63; and rowId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param columnId the column ID
	* @param rowId the row ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByC_R(long columnId, long rowId,
		boolean retrieveFromCache);

	/**
	* Removes the expando value where columnId = &#63; and rowId = &#63; from the database.
	*
	* @param columnId the column ID
	* @param rowId the row ID
	* @return the expando value that was removed
	*/
	public ExpandoValue removeByC_R(long columnId, long rowId)
		throws NoSuchValueException;

	/**
	* Returns the number of expando values where columnId = &#63; and rowId = &#63;.
	*
	* @param columnId the column ID
	* @param rowId the row ID
	* @return the number of matching expando values
	*/
	public int countByC_R(long columnId, long rowId);

	/**
	* Returns all the expando values where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching expando values
	*/
	public java.util.List<ExpandoValue> findByC_C(long classNameId, long classPK);

	/**
	* Returns a range of all the expando values where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByC_C(long classNameId,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the expando values where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns an ordered range of all the expando values where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the first expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the last expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the expando values before and after the current expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws NoSuchValueException if a expando value with the primary key could not be found
	*/
	public ExpandoValue[] findByC_C_PrevAndNext(long valueId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Removes all the expando values where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByC_C(long classNameId, long classPK);

	/**
	* Returns the number of expando values where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching expando values
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Returns the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; or throws a {@link NoSuchValueException} if it could not be found.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param classPK the class p k
	* @return the matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByT_C_C(long tableId, long columnId, long classPK)
		throws NoSuchValueException;

	/**
	* Returns the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param classPK the class p k
	* @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByT_C_C(long tableId, long columnId, long classPK);

	/**
	* Returns the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByT_C_C(long tableId, long columnId, long classPK,
		boolean retrieveFromCache);

	/**
	* Removes the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; from the database.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param classPK the class p k
	* @return the expando value that was removed
	*/
	public ExpandoValue removeByT_C_C(long tableId, long columnId, long classPK)
		throws NoSuchValueException;

	/**
	* Returns the number of expando values where tableId = &#63; and columnId = &#63; and classPK = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param classPK the class p k
	* @return the number of matching expando values
	*/
	public int countByT_C_C(long tableId, long columnId, long classPK);

	/**
	* Returns all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @return the matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_C_D(long tableId,
		long columnId, java.lang.String data);

	/**
	* Returns a range of all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_C_D(long tableId,
		long columnId, java.lang.String data, int start, int end);

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_C_D(long tableId,
		long columnId, java.lang.String data, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns an ordered range of all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching expando values
	*/
	public java.util.List<ExpandoValue> findByT_C_D(long tableId,
		long columnId, java.lang.String data, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByT_C_D_First(long tableId, long columnId,
		java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByT_C_D_First(long tableId, long columnId,
		java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value
	* @throws NoSuchValueException if a matching expando value could not be found
	*/
	public ExpandoValue findByT_C_D_Last(long tableId, long columnId,
		java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	*/
	public ExpandoValue fetchByT_C_D_Last(long tableId, long columnId,
		java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param valueId the primary key of the current expando value
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next expando value
	* @throws NoSuchValueException if a expando value with the primary key could not be found
	*/
	public ExpandoValue[] findByT_C_D_PrevAndNext(long valueId, long tableId,
		long columnId, java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator)
		throws NoSuchValueException;

	/**
	* Removes all the expando values where tableId = &#63; and columnId = &#63; and data = &#63; from the database.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	*/
	public void removeByT_C_D(long tableId, long columnId, java.lang.String data);

	/**
	* Returns the number of expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	*
	* @param tableId the table ID
	* @param columnId the column ID
	* @param data the data
	* @return the number of matching expando values
	*/
	public int countByT_C_D(long tableId, long columnId, java.lang.String data);

	/**
	* Caches the expando value in the entity cache if it is enabled.
	*
	* @param expandoValue the expando value
	*/
	public void cacheResult(ExpandoValue expandoValue);

	/**
	* Caches the expando values in the entity cache if it is enabled.
	*
	* @param expandoValues the expando values
	*/
	public void cacheResult(java.util.List<ExpandoValue> expandoValues);

	/**
	* Creates a new expando value with the primary key. Does not add the expando value to the database.
	*
	* @param valueId the primary key for the new expando value
	* @return the new expando value
	*/
	public ExpandoValue create(long valueId);

	/**
	* Removes the expando value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param valueId the primary key of the expando value
	* @return the expando value that was removed
	* @throws NoSuchValueException if a expando value with the primary key could not be found
	*/
	public ExpandoValue remove(long valueId) throws NoSuchValueException;

	public ExpandoValue updateImpl(ExpandoValue expandoValue);

	/**
	* Returns the expando value with the primary key or throws a {@link NoSuchValueException} if it could not be found.
	*
	* @param valueId the primary key of the expando value
	* @return the expando value
	* @throws NoSuchValueException if a expando value with the primary key could not be found
	*/
	public ExpandoValue findByPrimaryKey(long valueId)
		throws NoSuchValueException;

	/**
	* Returns the expando value with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param valueId the primary key of the expando value
	* @return the expando value, or <code>null</code> if a expando value with the primary key could not be found
	*/
	public ExpandoValue fetchByPrimaryKey(long valueId);

	@Override
	public java.util.Map<java.io.Serializable, ExpandoValue> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the expando values.
	*
	* @return the expando values
	*/
	public java.util.List<ExpandoValue> findAll();

	/**
	* Returns a range of all the expando values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @return the range of expando values
	*/
	public java.util.List<ExpandoValue> findAll(int start, int end);

	/**
	* Returns an ordered range of all the expando values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of expando values
	*/
	public java.util.List<ExpandoValue> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator);

	/**
	* Returns an ordered range of all the expando values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of expando values
	* @param end the upper bound of the range of expando values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of expando values
	*/
	public java.util.List<ExpandoValue> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ExpandoValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the expando values from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of expando values.
	*
	* @return the number of expando values
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}