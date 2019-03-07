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

import com.liferay.change.tracking.exception.NoSuchEntryAggregateException;
import com.liferay.change.tracking.model.CTEntryAggregate;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the ct entry aggregate service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryAggregateUtil
 * @generated
 */
@ProviderType
public interface CTEntryAggregatePersistence
	extends BasePersistence<CTEntryAggregate> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTEntryAggregateUtil} to access the ct entry aggregate persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ct entry aggregates where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @return the matching ct entry aggregates
	 */
	public java.util.List<CTEntryAggregate> findByOwnerCTEntryID(
		long ownerCTEntryId);

	/**
	 * Returns a range of all the ct entry aggregates where ownerCTEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of matching ct entry aggregates
	 */
	public java.util.List<CTEntryAggregate> findByOwnerCTEntryID(
		long ownerCTEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the ct entry aggregates where ownerCTEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entry aggregates
	 */
	public java.util.List<CTEntryAggregate> findByOwnerCTEntryID(
		long ownerCTEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntryAggregate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct entry aggregates where ownerCTEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entry aggregates
	 */
	public java.util.List<CTEntryAggregate> findByOwnerCTEntryID(
		long ownerCTEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntryAggregate>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first ct entry aggregate in the ordered set where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a matching ct entry aggregate could not be found
	 */
	public CTEntryAggregate findByOwnerCTEntryID_First(
			long ownerCTEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntryAggregate>
				orderByComparator)
		throws NoSuchEntryAggregateException;

	/**
	 * Returns the first ct entry aggregate in the ordered set where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry aggregate, or <code>null</code> if a matching ct entry aggregate could not be found
	 */
	public CTEntryAggregate fetchByOwnerCTEntryID_First(
		long ownerCTEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntryAggregate>
			orderByComparator);

	/**
	 * Returns the last ct entry aggregate in the ordered set where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a matching ct entry aggregate could not be found
	 */
	public CTEntryAggregate findByOwnerCTEntryID_Last(
			long ownerCTEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntryAggregate>
				orderByComparator)
		throws NoSuchEntryAggregateException;

	/**
	 * Returns the last ct entry aggregate in the ordered set where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry aggregate, or <code>null</code> if a matching ct entry aggregate could not be found
	 */
	public CTEntryAggregate fetchByOwnerCTEntryID_Last(
		long ownerCTEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntryAggregate>
			orderByComparator);

	/**
	 * Returns the ct entry aggregates before and after the current ct entry aggregate in the ordered set where ownerCTEntryId = &#63;.
	 *
	 * @param ctEntryAggregateId the primary key of the current ct entry aggregate
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a ct entry aggregate with the primary key could not be found
	 */
	public CTEntryAggregate[] findByOwnerCTEntryID_PrevAndNext(
			long ctEntryAggregateId, long ownerCTEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntryAggregate>
				orderByComparator)
		throws NoSuchEntryAggregateException;

	/**
	 * Removes all the ct entry aggregates where ownerCTEntryId = &#63; from the database.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 */
	public void removeByOwnerCTEntryID(long ownerCTEntryId);

	/**
	 * Returns the number of ct entry aggregates where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @return the number of matching ct entry aggregates
	 */
	public int countByOwnerCTEntryID(long ownerCTEntryId);

	/**
	 * Caches the ct entry aggregate in the entity cache if it is enabled.
	 *
	 * @param ctEntryAggregate the ct entry aggregate
	 */
	public void cacheResult(CTEntryAggregate ctEntryAggregate);

	/**
	 * Caches the ct entry aggregates in the entity cache if it is enabled.
	 *
	 * @param ctEntryAggregates the ct entry aggregates
	 */
	public void cacheResult(java.util.List<CTEntryAggregate> ctEntryAggregates);

	/**
	 * Creates a new ct entry aggregate with the primary key. Does not add the ct entry aggregate to the database.
	 *
	 * @param ctEntryAggregateId the primary key for the new ct entry aggregate
	 * @return the new ct entry aggregate
	 */
	public CTEntryAggregate create(long ctEntryAggregateId);

	/**
	 * Removes the ct entry aggregate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryAggregateId the primary key of the ct entry aggregate
	 * @return the ct entry aggregate that was removed
	 * @throws NoSuchEntryAggregateException if a ct entry aggregate with the primary key could not be found
	 */
	public CTEntryAggregate remove(long ctEntryAggregateId)
		throws NoSuchEntryAggregateException;

	public CTEntryAggregate updateImpl(CTEntryAggregate ctEntryAggregate);

	/**
	 * Returns the ct entry aggregate with the primary key or throws a <code>NoSuchEntryAggregateException</code> if it could not be found.
	 *
	 * @param ctEntryAggregateId the primary key of the ct entry aggregate
	 * @return the ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a ct entry aggregate with the primary key could not be found
	 */
	public CTEntryAggregate findByPrimaryKey(long ctEntryAggregateId)
		throws NoSuchEntryAggregateException;

	/**
	 * Returns the ct entry aggregate with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctEntryAggregateId the primary key of the ct entry aggregate
	 * @return the ct entry aggregate, or <code>null</code> if a ct entry aggregate with the primary key could not be found
	 */
	public CTEntryAggregate fetchByPrimaryKey(long ctEntryAggregateId);

	/**
	 * Returns all the ct entry aggregates.
	 *
	 * @return the ct entry aggregates
	 */
	public java.util.List<CTEntryAggregate> findAll();

	/**
	 * Returns a range of all the ct entry aggregates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of ct entry aggregates
	 */
	public java.util.List<CTEntryAggregate> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ct entry aggregates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entry aggregates
	 */
	public java.util.List<CTEntryAggregate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntryAggregate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct entry aggregates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ct entry aggregates
	 */
	public java.util.List<CTEntryAggregate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntryAggregate>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the ct entry aggregates from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ct entry aggregates.
	 *
	 * @return the number of ct entry aggregates
	 */
	public int countAll();

	/**
	 * Returns the primaryKeys of ct collections associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return long[] of the primaryKeys of ct collections associated with the ct entry aggregate
	 */
	public long[] getCTCollectionPrimaryKeys(long pk);

	/**
	 * Returns all the ct collections associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return the ct collections associated with the ct entry aggregate
	 */
	public java.util.List<com.liferay.change.tracking.model.CTCollection>
		getCTCollections(long pk);

	/**
	 * Returns a range of all the ct collections associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of ct collections associated with the ct entry aggregate
	 */
	public java.util.List<com.liferay.change.tracking.model.CTCollection>
		getCTCollections(long pk, int start, int end);

	/**
	 * Returns an ordered range of all the ct collections associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct collections associated with the ct entry aggregate
	 */
	public java.util.List<com.liferay.change.tracking.model.CTCollection>
		getCTCollections(
			long pk, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.change.tracking.model.CTCollection>
					orderByComparator);

	/**
	 * Returns the number of ct collections associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return the number of ct collections associated with the ct entry aggregate
	 */
	public int getCTCollectionsSize(long pk);

	/**
	 * Returns <code>true</code> if the ct collection is associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollectionPK the primary key of the ct collection
	 * @return <code>true</code> if the ct collection is associated with the ct entry aggregate; <code>false</code> otherwise
	 */
	public boolean containsCTCollection(long pk, long ctCollectionPK);

	/**
	 * Returns <code>true</code> if the ct entry aggregate has any ct collections associated with it.
	 *
	 * @param pk the primary key of the ct entry aggregate to check for associations with ct collections
	 * @return <code>true</code> if the ct entry aggregate has any ct collections associated with it; <code>false</code> otherwise
	 */
	public boolean containsCTCollections(long pk);

	/**
	 * Adds an association between the ct entry aggregate and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollectionPK the primary key of the ct collection
	 */
	public void addCTCollection(long pk, long ctCollectionPK);

	/**
	 * Adds an association between the ct entry aggregate and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollection the ct collection
	 */
	public void addCTCollection(
		long pk, com.liferay.change.tracking.model.CTCollection ctCollection);

	/**
	 * Adds an association between the ct entry aggregate and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollectionPKs the primary keys of the ct collections
	 */
	public void addCTCollections(long pk, long[] ctCollectionPKs);

	/**
	 * Adds an association between the ct entry aggregate and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollections the ct collections
	 */
	public void addCTCollections(
		long pk,
		java.util.List<com.liferay.change.tracking.model.CTCollection>
			ctCollections);

	/**
	 * Clears all associations between the ct entry aggregate and its ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate to clear the associated ct collections from
	 */
	public void clearCTCollections(long pk);

	/**
	 * Removes the association between the ct entry aggregate and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollectionPK the primary key of the ct collection
	 */
	public void removeCTCollection(long pk, long ctCollectionPK);

	/**
	 * Removes the association between the ct entry aggregate and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollection the ct collection
	 */
	public void removeCTCollection(
		long pk, com.liferay.change.tracking.model.CTCollection ctCollection);

	/**
	 * Removes the association between the ct entry aggregate and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollectionPKs the primary keys of the ct collections
	 */
	public void removeCTCollections(long pk, long[] ctCollectionPKs);

	/**
	 * Removes the association between the ct entry aggregate and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollections the ct collections
	 */
	public void removeCTCollections(
		long pk,
		java.util.List<com.liferay.change.tracking.model.CTCollection>
			ctCollections);

	/**
	 * Sets the ct collections associated with the ct entry aggregate, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollectionPKs the primary keys of the ct collections to be associated with the ct entry aggregate
	 */
	public void setCTCollections(long pk, long[] ctCollectionPKs);

	/**
	 * Sets the ct collections associated with the ct entry aggregate, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollections the ct collections to be associated with the ct entry aggregate
	 */
	public void setCTCollections(
		long pk,
		java.util.List<com.liferay.change.tracking.model.CTCollection>
			ctCollections);

	/**
	 * Returns the primaryKeys of ct entries associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return long[] of the primaryKeys of ct entries associated with the ct entry aggregate
	 */
	public long[] getCTEntryPrimaryKeys(long pk);

	/**
	 * Returns all the ct entries associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return the ct entries associated with the ct entry aggregate
	 */
	public java.util.List<com.liferay.change.tracking.model.CTEntry>
		getCTEntries(long pk);

	/**
	 * Returns a range of all the ct entries associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of ct entries associated with the ct entry aggregate
	 */
	public java.util.List<com.liferay.change.tracking.model.CTEntry>
		getCTEntries(long pk, int start, int end);

	/**
	 * Returns an ordered range of all the ct entries associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries associated with the ct entry aggregate
	 */
	public java.util.List<com.liferay.change.tracking.model.CTEntry>
		getCTEntries(
			long pk, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.change.tracking.model.CTEntry> orderByComparator);

	/**
	 * Returns the number of ct entries associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return the number of ct entries associated with the ct entry aggregate
	 */
	public int getCTEntriesSize(long pk);

	/**
	 * Returns <code>true</code> if the ct entry is associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPK the primary key of the ct entry
	 * @return <code>true</code> if the ct entry is associated with the ct entry aggregate; <code>false</code> otherwise
	 */
	public boolean containsCTEntry(long pk, long ctEntryPK);

	/**
	 * Returns <code>true</code> if the ct entry aggregate has any ct entries associated with it.
	 *
	 * @param pk the primary key of the ct entry aggregate to check for associations with ct entries
	 * @return <code>true</code> if the ct entry aggregate has any ct entries associated with it; <code>false</code> otherwise
	 */
	public boolean containsCTEntries(long pk);

	/**
	 * Adds an association between the ct entry aggregate and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPK the primary key of the ct entry
	 */
	public void addCTEntry(long pk, long ctEntryPK);

	/**
	 * Adds an association between the ct entry aggregate and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntry the ct entry
	 */
	public void addCTEntry(
		long pk, com.liferay.change.tracking.model.CTEntry ctEntry);

	/**
	 * Adds an association between the ct entry aggregate and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPKs the primary keys of the ct entries
	 */
	public void addCTEntries(long pk, long[] ctEntryPKs);

	/**
	 * Adds an association between the ct entry aggregate and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntries the ct entries
	 */
	public void addCTEntries(
		long pk,
		java.util.List<com.liferay.change.tracking.model.CTEntry> ctEntries);

	/**
	 * Clears all associations between the ct entry aggregate and its ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate to clear the associated ct entries from
	 */
	public void clearCTEntries(long pk);

	/**
	 * Removes the association between the ct entry aggregate and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPK the primary key of the ct entry
	 */
	public void removeCTEntry(long pk, long ctEntryPK);

	/**
	 * Removes the association between the ct entry aggregate and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntry the ct entry
	 */
	public void removeCTEntry(
		long pk, com.liferay.change.tracking.model.CTEntry ctEntry);

	/**
	 * Removes the association between the ct entry aggregate and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPKs the primary keys of the ct entries
	 */
	public void removeCTEntries(long pk, long[] ctEntryPKs);

	/**
	 * Removes the association between the ct entry aggregate and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntries the ct entries
	 */
	public void removeCTEntries(
		long pk,
		java.util.List<com.liferay.change.tracking.model.CTEntry> ctEntries);

	/**
	 * Sets the ct entries associated with the ct entry aggregate, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPKs the primary keys of the ct entries to be associated with the ct entry aggregate
	 */
	public void setCTEntries(long pk, long[] ctEntryPKs);

	/**
	 * Sets the ct entries associated with the ct entry aggregate, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntries the ct entries to be associated with the ct entry aggregate
	 */
	public void setCTEntries(
		long pk,
		java.util.List<com.liferay.change.tracking.model.CTEntry> ctEntries);

}