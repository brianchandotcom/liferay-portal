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

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchBigDecimalEntryException;
import com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry;

import java.math.BigDecimal;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the big decimal entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BigDecimalEntryUtil
 * @generated
 */
@ProviderType
public interface BigDecimalEntryPersistence
	extends BasePersistence<BigDecimalEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BigDecimalEntryUtil} to access the big decimal entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
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
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the big decimal entries where bigDecimalValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching big decimal entries
	 */
	public java.util.List<BigDecimalEntry> findByBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
			orderByComparator,
		boolean useFinderCache);

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
			com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
				orderByComparator)
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
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
			orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
				orderByComparator)
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
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
			orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
				orderByComparator)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
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
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the big decimal entries where bigDecimalValue &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching big decimal entries
	 */
	public java.util.List<BigDecimalEntry> findByGtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
			orderByComparator,
		boolean useFinderCache);

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
			com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
				orderByComparator)
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
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
			orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
				orderByComparator)
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
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
			orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
				orderByComparator)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
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
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the big decimal entries where bigDecimalValue &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching big decimal entries
	 */
	public java.util.List<BigDecimalEntry> findByLtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
			orderByComparator,
		boolean useFinderCache);

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
			com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
				orderByComparator)
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
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
			orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
				orderByComparator)
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
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
			orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
				orderByComparator)
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
	 * Returns the big decimal entry with the primary key or throws a <code>NoSuchBigDecimalEntryException</code> if it could not be found.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of big decimal entries
	 */
	public java.util.List<BigDecimalEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the big decimal entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of big decimal entries
	 */
	public java.util.List<BigDecimalEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BigDecimalEntry>
			orderByComparator,
		boolean useFinderCache);

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

	/**
	 * Returns the primaryKeys of lv entries associated with the big decimal entry.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @return long[] of the primaryKeys of lv entries associated with the big decimal entry
	 */
	public long[] getLVEntryPrimaryKeys(long pk);

	/**
	 * Returns all the lv entries associated with the big decimal entry.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @return the lv entries associated with the big decimal entry
	 */
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.LVEntry>
			getLVEntries(long pk);

	/**
	 * Returns a range of all the lv entries associated with the big decimal entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @return the range of lv entries associated with the big decimal entry
	 */
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.LVEntry>
			getLVEntries(long pk, int start, int end);

	/**
	 * Returns an ordered range of all the lv entries associated with the big decimal entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lv entries associated with the big decimal entry
	 */
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.LVEntry>
			getLVEntries(
				long pk, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.tools.service.builder.test.model.
						LVEntry> orderByComparator);

	/**
	 * Returns the number of lv entries associated with the big decimal entry.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @return the number of lv entries associated with the big decimal entry
	 */
	public int getLVEntriesSize(long pk);

	/**
	 * Returns <code>true</code> if the lv entry is associated with the big decimal entry.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntryPK the primary key of the lv entry
	 * @return <code>true</code> if the lv entry is associated with the big decimal entry; <code>false</code> otherwise
	 */
	public boolean containsLVEntry(long pk, long lvEntryPK);

	/**
	 * Returns <code>true</code> if the big decimal entry has any lv entries associated with it.
	 *
	 * @param pk the primary key of the big decimal entry to check for associations with lv entries
	 * @return <code>true</code> if the big decimal entry has any lv entries associated with it; <code>false</code> otherwise
	 */
	public boolean containsLVEntries(long pk);

	/**
	 * Adds an association between the big decimal entry and the lv entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntryPK the primary key of the lv entry
	 */
	public void addLVEntry(long pk, long lvEntryPK);

	/**
	 * Adds an association between the big decimal entry and the lv entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntry the lv entry
	 */
	public void addLVEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.LVEntry lvEntry);

	/**
	 * Adds an association between the big decimal entry and the lv entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntryPKs the primary keys of the lv entries
	 */
	public void addLVEntries(long pk, long[] lvEntryPKs);

	/**
	 * Adds an association between the big decimal entry and the lv entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntries the lv entries
	 */
	public void addLVEntries(
		long pk,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.LVEntry>
				lvEntries);

	/**
	 * Clears all associations between the big decimal entry and its lv entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry to clear the associated lv entries from
	 */
	public void clearLVEntries(long pk);

	/**
	 * Removes the association between the big decimal entry and the lv entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntryPK the primary key of the lv entry
	 */
	public void removeLVEntry(long pk, long lvEntryPK);

	/**
	 * Removes the association between the big decimal entry and the lv entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntry the lv entry
	 */
	public void removeLVEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.LVEntry lvEntry);

	/**
	 * Removes the association between the big decimal entry and the lv entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntryPKs the primary keys of the lv entries
	 */
	public void removeLVEntries(long pk, long[] lvEntryPKs);

	/**
	 * Removes the association between the big decimal entry and the lv entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntries the lv entries
	 */
	public void removeLVEntries(
		long pk,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.LVEntry>
				lvEntries);

	/**
	 * Sets the lv entries associated with the big decimal entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntryPKs the primary keys of the lv entries to be associated with the big decimal entry
	 */
	public void setLVEntries(long pk, long[] lvEntryPKs);

	/**
	 * Sets the lv entries associated with the big decimal entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntries the lv entries to be associated with the big decimal entry
	 */
	public void setLVEntries(
		long pk,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.LVEntry>
				lvEntries);

}