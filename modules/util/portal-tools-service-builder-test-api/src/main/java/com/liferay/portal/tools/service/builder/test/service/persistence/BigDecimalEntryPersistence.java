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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchBigDecimalEntryException;
import com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Map;
import java.util.Set;

/**
 * The persistence interface for the big decimal entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.impl.BigDecimalEntryPersistenceImpl
 * @see BigDecimalEntryUtil
 * @generated
 */
@ProviderType
public interface BigDecimalEntryPersistence extends BasePersistence<BigDecimalEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BigDecimalEntryUtil} to access the big decimal entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	@Override
	public Map<Serializable, BigDecimalEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys);

	/**
	* Returns all the big decimal entries where bigDecimalValue = &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @return the matching big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findByBigDecimalValue(
		BigDecimal bigDecimalValue);

	/**
	* Returns a range of all the big decimal entries where bigDecimalValue = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param bigDecimalValue the big decimal value
	* @param start the lower bound of the range of big decimal entries
	* @param end the upper bound of the range of big decimal entries (not inclusive)
	* @return the range of matching big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findByBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end);

	/**
	* Returns an ordered range of all the big decimal entries where bigDecimalValue = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param bigDecimalValue the big decimal value
	* @param start the lower bound of the range of big decimal entries
	* @param end the upper bound of the range of big decimal entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findByBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator);

	/**
	* Returns an ordered range of all the big decimal entries where bigDecimalValue = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param bigDecimalValue the big decimal value
	* @param start the lower bound of the range of big decimal entries
	* @param end the upper bound of the range of big decimal entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findByBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first big decimal entry in the ordered set where bigDecimalValue = &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching big decimal entry
	* @throws NoSuchBigDecimalEntryException if a matching big decimal entry could not be found
	*/
	public BigDecimalEntry findByBigDecimalValue_First(
		BigDecimal bigDecimalValue,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException;

	/**
	* Returns the first big decimal entry in the ordered set where bigDecimalValue = &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching big decimal entry, or <code>null</code> if a matching big decimal entry could not be found
	*/
	public BigDecimalEntry fetchByBigDecimalValue_First(
		BigDecimal bigDecimalValue,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator);

	/**
	* Returns the last big decimal entry in the ordered set where bigDecimalValue = &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching big decimal entry
	* @throws NoSuchBigDecimalEntryException if a matching big decimal entry could not be found
	*/
	public BigDecimalEntry findByBigDecimalValue_Last(
		BigDecimal bigDecimalValue,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException;

	/**
	* Returns the last big decimal entry in the ordered set where bigDecimalValue = &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching big decimal entry, or <code>null</code> if a matching big decimal entry could not be found
	*/
	public BigDecimalEntry fetchByBigDecimalValue_Last(
		BigDecimal bigDecimalValue,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator);

	/**
	* Returns the big decimal entries before and after the current big decimal entry in the ordered set where bigDecimalValue = &#63;.
	*
	* @param bigDecimalEntryId the primary key of the current big decimal entry
	* @param bigDecimalValue the big decimal value
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next big decimal entry
	* @throws NoSuchBigDecimalEntryException if a big decimal entry with the primary key could not be found
	*/
	public BigDecimalEntry[] findByBigDecimalValue_PrevAndNext(
		long bigDecimalEntryId, BigDecimal bigDecimalValue,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException;

	/**
	* Removes all the big decimal entries where bigDecimalValue = &#63; from the database.
	*
	* @param bigDecimalValue the big decimal value
	*/
	public void removeByBigDecimalValue(BigDecimal bigDecimalValue);

	/**
	* Returns the number of big decimal entries where bigDecimalValue = &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @return the number of matching big decimal entries
	*/
	public int countByBigDecimalValue(BigDecimal bigDecimalValue);

	/**
	* Returns all the big decimal entries where bigDecimalValue &gt; &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @return the matching big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findByGtBigDecimalValue(
		BigDecimal bigDecimalValue);

	/**
	* Returns a range of all the big decimal entries where bigDecimalValue &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param bigDecimalValue the big decimal value
	* @param start the lower bound of the range of big decimal entries
	* @param end the upper bound of the range of big decimal entries (not inclusive)
	* @return the range of matching big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findByGtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end);

	/**
	* Returns an ordered range of all the big decimal entries where bigDecimalValue &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param bigDecimalValue the big decimal value
	* @param start the lower bound of the range of big decimal entries
	* @param end the upper bound of the range of big decimal entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findByGtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator);

	/**
	* Returns an ordered range of all the big decimal entries where bigDecimalValue &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param bigDecimalValue the big decimal value
	* @param start the lower bound of the range of big decimal entries
	* @param end the upper bound of the range of big decimal entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findByGtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first big decimal entry in the ordered set where bigDecimalValue &gt; &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching big decimal entry
	* @throws NoSuchBigDecimalEntryException if a matching big decimal entry could not be found
	*/
	public BigDecimalEntry findByGtBigDecimalValue_First(
		BigDecimal bigDecimalValue,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException;

	/**
	* Returns the first big decimal entry in the ordered set where bigDecimalValue &gt; &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching big decimal entry, or <code>null</code> if a matching big decimal entry could not be found
	*/
	public BigDecimalEntry fetchByGtBigDecimalValue_First(
		BigDecimal bigDecimalValue,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator);

	/**
	* Returns the last big decimal entry in the ordered set where bigDecimalValue &gt; &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching big decimal entry
	* @throws NoSuchBigDecimalEntryException if a matching big decimal entry could not be found
	*/
	public BigDecimalEntry findByGtBigDecimalValue_Last(
		BigDecimal bigDecimalValue,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException;

	/**
	* Returns the last big decimal entry in the ordered set where bigDecimalValue &gt; &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching big decimal entry, or <code>null</code> if a matching big decimal entry could not be found
	*/
	public BigDecimalEntry fetchByGtBigDecimalValue_Last(
		BigDecimal bigDecimalValue,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator);

	/**
	* Returns the big decimal entries before and after the current big decimal entry in the ordered set where bigDecimalValue &gt; &#63;.
	*
	* @param bigDecimalEntryId the primary key of the current big decimal entry
	* @param bigDecimalValue the big decimal value
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next big decimal entry
	* @throws NoSuchBigDecimalEntryException if a big decimal entry with the primary key could not be found
	*/
	public BigDecimalEntry[] findByGtBigDecimalValue_PrevAndNext(
		long bigDecimalEntryId, BigDecimal bigDecimalValue,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException;

	/**
	* Removes all the big decimal entries where bigDecimalValue &gt; &#63; from the database.
	*
	* @param bigDecimalValue the big decimal value
	*/
	public void removeByGtBigDecimalValue(BigDecimal bigDecimalValue);

	/**
	* Returns the number of big decimal entries where bigDecimalValue &gt; &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @return the number of matching big decimal entries
	*/
	public int countByGtBigDecimalValue(BigDecimal bigDecimalValue);

	/**
	* Returns all the big decimal entries where bigDecimalValue &lt; &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @return the matching big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findByLtBigDecimalValue(
		BigDecimal bigDecimalValue);

	/**
	* Returns a range of all the big decimal entries where bigDecimalValue &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param bigDecimalValue the big decimal value
	* @param start the lower bound of the range of big decimal entries
	* @param end the upper bound of the range of big decimal entries (not inclusive)
	* @return the range of matching big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findByLtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end);

	/**
	* Returns an ordered range of all the big decimal entries where bigDecimalValue &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param bigDecimalValue the big decimal value
	* @param start the lower bound of the range of big decimal entries
	* @param end the upper bound of the range of big decimal entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findByLtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator);

	/**
	* Returns an ordered range of all the big decimal entries where bigDecimalValue &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param bigDecimalValue the big decimal value
	* @param start the lower bound of the range of big decimal entries
	* @param end the upper bound of the range of big decimal entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findByLtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first big decimal entry in the ordered set where bigDecimalValue &lt; &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching big decimal entry
	* @throws NoSuchBigDecimalEntryException if a matching big decimal entry could not be found
	*/
	public BigDecimalEntry findByLtBigDecimalValue_First(
		BigDecimal bigDecimalValue,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException;

	/**
	* Returns the first big decimal entry in the ordered set where bigDecimalValue &lt; &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching big decimal entry, or <code>null</code> if a matching big decimal entry could not be found
	*/
	public BigDecimalEntry fetchByLtBigDecimalValue_First(
		BigDecimal bigDecimalValue,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator);

	/**
	* Returns the last big decimal entry in the ordered set where bigDecimalValue &lt; &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching big decimal entry
	* @throws NoSuchBigDecimalEntryException if a matching big decimal entry could not be found
	*/
	public BigDecimalEntry findByLtBigDecimalValue_Last(
		BigDecimal bigDecimalValue,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException;

	/**
	* Returns the last big decimal entry in the ordered set where bigDecimalValue &lt; &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching big decimal entry, or <code>null</code> if a matching big decimal entry could not be found
	*/
	public BigDecimalEntry fetchByLtBigDecimalValue_Last(
		BigDecimal bigDecimalValue,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator);

	/**
	* Returns the big decimal entries before and after the current big decimal entry in the ordered set where bigDecimalValue &lt; &#63;.
	*
	* @param bigDecimalEntryId the primary key of the current big decimal entry
	* @param bigDecimalValue the big decimal value
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next big decimal entry
	* @throws NoSuchBigDecimalEntryException if a big decimal entry with the primary key could not be found
	*/
	public BigDecimalEntry[] findByLtBigDecimalValue_PrevAndNext(
		long bigDecimalEntryId, BigDecimal bigDecimalValue,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException;

	/**
	* Removes all the big decimal entries where bigDecimalValue &lt; &#63; from the database.
	*
	* @param bigDecimalValue the big decimal value
	*/
	public void removeByLtBigDecimalValue(BigDecimal bigDecimalValue);

	/**
	* Returns the number of big decimal entries where bigDecimalValue &lt; &#63;.
	*
	* @param bigDecimalValue the big decimal value
	* @return the number of matching big decimal entries
	*/
	public int countByLtBigDecimalValue(BigDecimal bigDecimalValue);

	/**
	* Caches the big decimal entry in the entity cache if it is enabled.
	*
	* @param bigDecimalEntry the big decimal entry
	*/
	public void cacheResult(BigDecimalEntry bigDecimalEntry);

	/**
	* Caches the big decimal entries in the entity cache if it is enabled.
	*
	* @param bigDecimalEntries the big decimal entries
	*/
	public void cacheResult(java.util.List<BigDecimalEntry> bigDecimalEntries);

	/**
	* Creates a new big decimal entry with the primary key. Does not add the big decimal entry to the database.
	*
	* @param bigDecimalEntryId the primary key for the new big decimal entry
	* @return the new big decimal entry
	*/
	public BigDecimalEntry create(long bigDecimalEntryId);

	/**
	* Removes the big decimal entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param bigDecimalEntryId the primary key of the big decimal entry
	* @return the big decimal entry that was removed
	* @throws NoSuchBigDecimalEntryException if a big decimal entry with the primary key could not be found
	*/
	public BigDecimalEntry remove(long bigDecimalEntryId)
		throws NoSuchBigDecimalEntryException;

	public BigDecimalEntry updateImpl(BigDecimalEntry bigDecimalEntry);

	/**
	* Returns the big decimal entry with the primary key or throws a {@link NoSuchBigDecimalEntryException} if it could not be found.
	*
	* @param bigDecimalEntryId the primary key of the big decimal entry
	* @return the big decimal entry
	* @throws NoSuchBigDecimalEntryException if a big decimal entry with the primary key could not be found
	*/
	public BigDecimalEntry findByPrimaryKey(long bigDecimalEntryId)
		throws NoSuchBigDecimalEntryException;

	/**
	* Returns the big decimal entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param bigDecimalEntryId the primary key of the big decimal entry
	* @return the big decimal entry, or <code>null</code> if a big decimal entry with the primary key could not be found
	*/
	public BigDecimalEntry fetchByPrimaryKey(long bigDecimalEntryId);

	/**
	* Returns all the big decimal entries.
	*
	* @return the big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findAll();

	/**
	* Returns a range of all the big decimal entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of big decimal entries
	* @param end the upper bound of the range of big decimal entries (not inclusive)
	* @return the range of big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the big decimal entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of big decimal entries
	* @param end the upper bound of the range of big decimal entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator);

	/**
	* Returns an ordered range of all the big decimal entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of big decimal entries
	* @param end the upper bound of the range of big decimal entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of big decimal entries
	*/
	public java.util.List<BigDecimalEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the big decimal entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of big decimal entries.
	*
	* @return the number of big decimal entries
	*/
	public int countAll();
}