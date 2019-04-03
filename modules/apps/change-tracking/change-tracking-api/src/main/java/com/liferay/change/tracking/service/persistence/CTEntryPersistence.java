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

package com.liferay.change.tracking.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.exception.NoSuchEntryException;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the ct entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryUtil
 * @generated
 */
@ProviderType
public interface CTEntryPersistence extends BasePersistence<CTEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTEntryUtil} to access the ct entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ct entries where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @return the matching ct entries
	 */
	public java.util.List<CTEntry> findByModelClassNameId(
		long modelClassNameId);

	/**
	 * Returns a range of all the ct entries where modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public java.util.List<CTEntry> findByModelClassNameId(
		long modelClassNameId, int start, int end);

	/**
	 * Returns an ordered range of all the ct entries where modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public java.util.List<CTEntry> findByModelClassNameId(
		long modelClassNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct entries where modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public java.util.List<CTEntry> findByModelClassNameId(
		long modelClassNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public CTEntry findByModelClassNameId_First(
			long modelClassNameId,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByModelClassNameId_First(
		long modelClassNameId,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns the last ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public CTEntry findByModelClassNameId_Last(
			long modelClassNameId,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByModelClassNameId_Last(
		long modelClassNameId,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public CTEntry[] findByModelClassNameId_PrevAndNext(
			long ctEntryId, long modelClassNameId,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the ct entries where modelClassNameId = &#63; from the database.
	 *
	 * @param modelClassNameId the model class name ID
	 */
	public void removeByModelClassNameId(long modelClassNameId);

	/**
	 * Returns the number of ct entries where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @return the number of matching ct entries
	 */
	public int countByModelClassNameId(long modelClassNameId);

	/**
	 * Returns the ct entry where modelClassNameId = &#63; and modelClassPK = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public CTEntry findByC_C(long modelClassNameId, long modelClassPK)
		throws NoSuchEntryException;

	/**
	 * Returns the ct entry where modelClassNameId = &#63; and modelClassPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByC_C(long modelClassNameId, long modelClassPK);

	/**
	 * Returns the ct entry where modelClassNameId = &#63; and modelClassPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByC_C(
		long modelClassNameId, long modelClassPK, boolean retrieveFromCache);

	/**
	 * Removes the ct entry where modelClassNameId = &#63; and modelClassPK = &#63; from the database.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the ct entry that was removed
	 */
	public CTEntry removeByC_C(long modelClassNameId, long modelClassPK)
		throws NoSuchEntryException;

	/**
	 * Returns the number of ct entries where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the number of matching ct entries
	 */
	public int countByC_C(long modelClassNameId, long modelClassPK);

	/**
	 * Caches the ct entry in the entity cache if it is enabled.
	 *
	 * @param ctEntry the ct entry
	 */
	public void cacheResult(CTEntry ctEntry);

	/**
	 * Caches the ct entries in the entity cache if it is enabled.
	 *
	 * @param ctEntries the ct entries
	 */
	public void cacheResult(java.util.List<CTEntry> ctEntries);

	/**
	 * Creates a new ct entry with the primary key. Does not add the ct entry to the database.
	 *
	 * @param ctEntryId the primary key for the new ct entry
	 * @return the new ct entry
	 */
	public CTEntry create(long ctEntryId);

	/**
	 * Removes the ct entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry that was removed
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public CTEntry remove(long ctEntryId) throws NoSuchEntryException;

	public CTEntry updateImpl(CTEntry ctEntry);

	/**
	 * Returns the ct entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public CTEntry findByPrimaryKey(long ctEntryId) throws NoSuchEntryException;

	/**
	 * Returns the ct entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry, or <code>null</code> if a ct entry with the primary key could not be found
	 */
	public CTEntry fetchByPrimaryKey(long ctEntryId);

	/**
	 * Returns all the ct entries.
	 *
	 * @return the ct entries
	 */
	public java.util.List<CTEntry> findAll();

	/**
	 * Returns a range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of ct entries
	 */
	public java.util.List<CTEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries
	 */
	public java.util.List<CTEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ct entries
	 */
	public java.util.List<CTEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the ct entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ct entries.
	 *
	 * @return the number of ct entries
	 */
	public int countAll();

	/**
	 * Returns the primaryKeys of ct entry aggregates associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return long[] of the primaryKeys of ct entry aggregates associated with the ct entry
	 */
	public long[] getCTEntryAggregatePrimaryKeys(long pk);

	/**
	 * Returns all the ct entry associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return the ct entries associated with the ct entry aggregate
	 */
	public java.util.List<CTEntry> getCTEntryAggregateCTEntries(long pk);

	/**
	 * Returns all the ct entry associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of ct entries associated with the ct entry aggregate
	 */
	public java.util.List<CTEntry> getCTEntryAggregateCTEntries(
		long pk, int start, int end);

	/**
	 * Returns all the ct entry associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries associated with the ct entry aggregate
	 */
	public java.util.List<CTEntry> getCTEntryAggregateCTEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns the number of ct entry aggregates associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return the number of ct entry aggregates associated with the ct entry
	 */
	public int getCTEntryAggregatesSize(long pk);

	/**
	 * Returns <code>true</code> if the ct entry aggregate is associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 * @return <code>true</code> if the ct entry aggregate is associated with the ct entry; <code>false</code> otherwise
	 */
	public boolean containsCTEntryAggregate(long pk, long ctEntryAggregatePK);

	/**
	 * Returns <code>true</code> if the ct entry has any ct entry aggregates associated with it.
	 *
	 * @param pk the primary key of the ct entry to check for associations with ct entry aggregates
	 * @return <code>true</code> if the ct entry has any ct entry aggregates associated with it; <code>false</code> otherwise
	 */
	public boolean containsCTEntryAggregates(long pk);

	/**
	 * Adds an association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 */
	public void addCTEntryAggregate(long pk, long ctEntryAggregatePK);

	/**
	 * Adds an association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregate the ct entry aggregate
	 */
	public void addCTEntryAggregate(
		long pk,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate);

	/**
	 * Adds an association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates
	 */
	public void addCTEntryAggregates(long pk, long[] ctEntryAggregatePKs);

	/**
	 * Adds an association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregates the ct entry aggregates
	 */
	public void addCTEntryAggregates(
		long pk,
		java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates);

	/**
	 * Clears all associations between the ct entry and its ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry to clear the associated ct entry aggregates from
	 */
	public void clearCTEntryAggregates(long pk);

	/**
	 * Removes the association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 */
	public void removeCTEntryAggregate(long pk, long ctEntryAggregatePK);

	/**
	 * Removes the association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregate the ct entry aggregate
	 */
	public void removeCTEntryAggregate(
		long pk,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate);

	/**
	 * Removes the association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates
	 */
	public void removeCTEntryAggregates(long pk, long[] ctEntryAggregatePKs);

	/**
	 * Removes the association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregates the ct entry aggregates
	 */
	public void removeCTEntryAggregates(
		long pk,
		java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates);

	/**
	 * Sets the ct entry aggregates associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates to be associated with the ct entry
	 */
	public void setCTEntryAggregates(long pk, long[] ctEntryAggregatePKs);

	/**
	 * Sets the ct entry aggregates associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregates the ct entry aggregates to be associated with the ct entry
	 */
	public void setCTEntryAggregates(
		long pk,
		java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates);

	/**
	 * Returns the primaryKeys of ct collections associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return long[] of the primaryKeys of ct collections associated with the ct entry
	 */
	public long[] getCTCollectionPrimaryKeys(long pk);

	/**
	 * Returns all the ct entry associated with the ct collection.
	 *
	 * @param pk the primary key of the ct collection
	 * @return the ct entries associated with the ct collection
	 */
	public java.util.List<CTEntry> getCTCollectionCTEntries(long pk);

	/**
	 * Returns all the ct entry associated with the ct collection.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct collection
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @return the range of ct entries associated with the ct collection
	 */
	public java.util.List<CTEntry> getCTCollectionCTEntries(
		long pk, int start, int end);

	/**
	 * Returns all the ct entry associated with the ct collection.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct collection
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries associated with the ct collection
	 */
	public java.util.List<CTEntry> getCTCollectionCTEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns the number of ct collections associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return the number of ct collections associated with the ct entry
	 */
	public int getCTCollectionsSize(long pk);

	/**
	 * Returns <code>true</code> if the ct collection is associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPK the primary key of the ct collection
	 * @return <code>true</code> if the ct collection is associated with the ct entry; <code>false</code> otherwise
	 */
	public boolean containsCTCollection(long pk, long ctCollectionPK);

	/**
	 * Returns <code>true</code> if the ct entry has any ct collections associated with it.
	 *
	 * @param pk the primary key of the ct entry to check for associations with ct collections
	 * @return <code>true</code> if the ct entry has any ct collections associated with it; <code>false</code> otherwise
	 */
	public boolean containsCTCollections(long pk);

	/**
	 * Adds an association between the ct entry and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPK the primary key of the ct collection
	 */
	public void addCTCollection(long pk, long ctCollectionPK);

	/**
	 * Adds an association between the ct entry and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollection the ct collection
	 */
	public void addCTCollection(
		long pk, com.liferay.change.tracking.model.CTCollection ctCollection);

	/**
	 * Adds an association between the ct entry and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPKs the primary keys of the ct collections
	 */
	public void addCTCollections(long pk, long[] ctCollectionPKs);

	/**
	 * Adds an association between the ct entry and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollections the ct collections
	 */
	public void addCTCollections(
		long pk,
		java.util.List<com.liferay.change.tracking.model.CTCollection>
			ctCollections);

	/**
	 * Clears all associations between the ct entry and its ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry to clear the associated ct collections from
	 */
	public void clearCTCollections(long pk);

	/**
	 * Removes the association between the ct entry and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPK the primary key of the ct collection
	 */
	public void removeCTCollection(long pk, long ctCollectionPK);

	/**
	 * Removes the association between the ct entry and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollection the ct collection
	 */
	public void removeCTCollection(
		long pk, com.liferay.change.tracking.model.CTCollection ctCollection);

	/**
	 * Removes the association between the ct entry and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPKs the primary keys of the ct collections
	 */
	public void removeCTCollections(long pk, long[] ctCollectionPKs);

	/**
	 * Removes the association between the ct entry and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollections the ct collections
	 */
	public void removeCTCollections(
		long pk,
		java.util.List<com.liferay.change.tracking.model.CTCollection>
			ctCollections);

	/**
	 * Sets the ct collections associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPKs the primary keys of the ct collections to be associated with the ct entry
	 */
	public void setCTCollections(long pk, long[] ctCollectionPKs);

	/**
	 * Sets the ct collections associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollections the ct collections to be associated with the ct entry
	 */
	public void setCTCollections(
		long pk,
		java.util.List<com.liferay.change.tracking.model.CTCollection>
			ctCollections);

}