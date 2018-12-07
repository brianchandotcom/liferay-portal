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
import com.liferay.change.tracking.model.ChangeTrackingEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the change tracking entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.change.tracking.service.persistence.impl.ChangeTrackingEntryPersistenceImpl
 * @see ChangeTrackingEntryUtil
 * @generated
 */
@ProviderType
public interface ChangeTrackingEntryPersistence extends BasePersistence<ChangeTrackingEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ChangeTrackingEntryUtil} to access the change tracking entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the change tracking entries where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the matching change tracking entries
	*/
	public java.util.List<ChangeTrackingEntry> findByResourcePrimKey(
		long resourcePrimKey);

	/**
	* Returns a range of all the change tracking entries where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param start the lower bound of the range of change tracking entries
	* @param end the upper bound of the range of change tracking entries (not inclusive)
	* @return the range of matching change tracking entries
	*/
	public java.util.List<ChangeTrackingEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end);

	/**
	* Returns an ordered range of all the change tracking entries where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param start the lower bound of the range of change tracking entries
	* @param end the upper bound of the range of change tracking entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching change tracking entries
	*/
	public java.util.List<ChangeTrackingEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeTrackingEntry> orderByComparator);

	/**
	* Returns an ordered range of all the change tracking entries where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param start the lower bound of the range of change tracking entries
	* @param end the upper bound of the range of change tracking entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching change tracking entries
	*/
	public java.util.List<ChangeTrackingEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeTrackingEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first change tracking entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching change tracking entry
	* @throws NoSuchEntryException if a matching change tracking entry could not be found
	*/
	public ChangeTrackingEntry findByResourcePrimKey_First(
		long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeTrackingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first change tracking entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching change tracking entry, or <code>null</code> if a matching change tracking entry could not be found
	*/
	public ChangeTrackingEntry fetchByResourcePrimKey_First(
		long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeTrackingEntry> orderByComparator);

	/**
	* Returns the last change tracking entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching change tracking entry
	* @throws NoSuchEntryException if a matching change tracking entry could not be found
	*/
	public ChangeTrackingEntry findByResourcePrimKey_Last(
		long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeTrackingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last change tracking entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching change tracking entry, or <code>null</code> if a matching change tracking entry could not be found
	*/
	public ChangeTrackingEntry fetchByResourcePrimKey_Last(
		long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeTrackingEntry> orderByComparator);

	/**
	* Returns the change tracking entries before and after the current change tracking entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param changeTrackingEntryId the primary key of the current change tracking entry
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next change tracking entry
	* @throws NoSuchEntryException if a change tracking entry with the primary key could not be found
	*/
	public ChangeTrackingEntry[] findByResourcePrimKey_PrevAndNext(
		long changeTrackingEntryId, long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeTrackingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the change tracking entries where resourcePrimKey = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	*/
	public void removeByResourcePrimKey(long resourcePrimKey);

	/**
	* Returns the number of change tracking entries where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the number of matching change tracking entries
	*/
	public int countByResourcePrimKey(long resourcePrimKey);

	/**
	* Caches the change tracking entry in the entity cache if it is enabled.
	*
	* @param changeTrackingEntry the change tracking entry
	*/
	public void cacheResult(ChangeTrackingEntry changeTrackingEntry);

	/**
	* Caches the change tracking entries in the entity cache if it is enabled.
	*
	* @param changeTrackingEntries the change tracking entries
	*/
	public void cacheResult(
		java.util.List<ChangeTrackingEntry> changeTrackingEntries);

	/**
	* Creates a new change tracking entry with the primary key. Does not add the change tracking entry to the database.
	*
	* @param changeTrackingEntryId the primary key for the new change tracking entry
	* @return the new change tracking entry
	*/
	public ChangeTrackingEntry create(long changeTrackingEntryId);

	/**
	* Removes the change tracking entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingEntryId the primary key of the change tracking entry
	* @return the change tracking entry that was removed
	* @throws NoSuchEntryException if a change tracking entry with the primary key could not be found
	*/
	public ChangeTrackingEntry remove(long changeTrackingEntryId)
		throws NoSuchEntryException;

	public ChangeTrackingEntry updateImpl(
		ChangeTrackingEntry changeTrackingEntry);

	/**
	* Returns the change tracking entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param changeTrackingEntryId the primary key of the change tracking entry
	* @return the change tracking entry
	* @throws NoSuchEntryException if a change tracking entry with the primary key could not be found
	*/
	public ChangeTrackingEntry findByPrimaryKey(long changeTrackingEntryId)
		throws NoSuchEntryException;

	/**
	* Returns the change tracking entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param changeTrackingEntryId the primary key of the change tracking entry
	* @return the change tracking entry, or <code>null</code> if a change tracking entry with the primary key could not be found
	*/
	public ChangeTrackingEntry fetchByPrimaryKey(long changeTrackingEntryId);

	@Override
	public java.util.Map<java.io.Serializable, ChangeTrackingEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the change tracking entries.
	*
	* @return the change tracking entries
	*/
	public java.util.List<ChangeTrackingEntry> findAll();

	/**
	* Returns a range of all the change tracking entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change tracking entries
	* @param end the upper bound of the range of change tracking entries (not inclusive)
	* @return the range of change tracking entries
	*/
	public java.util.List<ChangeTrackingEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the change tracking entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change tracking entries
	* @param end the upper bound of the range of change tracking entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of change tracking entries
	*/
	public java.util.List<ChangeTrackingEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeTrackingEntry> orderByComparator);

	/**
	* Returns an ordered range of all the change tracking entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change tracking entries
	* @param end the upper bound of the range of change tracking entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of change tracking entries
	*/
	public java.util.List<ChangeTrackingEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeTrackingEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the change tracking entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of change tracking entries.
	*
	* @return the number of change tracking entries
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of change tracking collections associated with the change tracking entry.
	*
	* @param pk the primary key of the change tracking entry
	* @return long[] of the primaryKeys of change tracking collections associated with the change tracking entry
	*/
	public long[] getChangeTrackingCollectionPrimaryKeys(long pk);

	/**
	* Returns all the change tracking collections associated with the change tracking entry.
	*
	* @param pk the primary key of the change tracking entry
	* @return the change tracking collections associated with the change tracking entry
	*/
	public java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingCollections(
		long pk);

	/**
	* Returns a range of all the change tracking collections associated with the change tracking entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the change tracking entry
	* @param start the lower bound of the range of change tracking entries
	* @param end the upper bound of the range of change tracking entries (not inclusive)
	* @return the range of change tracking collections associated with the change tracking entry
	*/
	public java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingCollections(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the change tracking collections associated with the change tracking entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the change tracking entry
	* @param start the lower bound of the range of change tracking entries
	* @param end the upper bound of the range of change tracking entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of change tracking collections associated with the change tracking entry
	*/
	public java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingCollections(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.model.ChangeTrackingCollection> orderByComparator);

	/**
	* Returns the number of change tracking collections associated with the change tracking entry.
	*
	* @param pk the primary key of the change tracking entry
	* @return the number of change tracking collections associated with the change tracking entry
	*/
	public int getChangeTrackingCollectionsSize(long pk);

	/**
	* Returns <code>true</code> if the change tracking collection is associated with the change tracking entry.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollectionPK the primary key of the change tracking collection
	* @return <code>true</code> if the change tracking collection is associated with the change tracking entry; <code>false</code> otherwise
	*/
	public boolean containsChangeTrackingCollection(long pk,
		long changeTrackingCollectionPK);

	/**
	* Returns <code>true</code> if the change tracking entry has any change tracking collections associated with it.
	*
	* @param pk the primary key of the change tracking entry to check for associations with change tracking collections
	* @return <code>true</code> if the change tracking entry has any change tracking collections associated with it; <code>false</code> otherwise
	*/
	public boolean containsChangeTrackingCollections(long pk);

	/**
	* Adds an association between the change tracking entry and the change tracking collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollectionPK the primary key of the change tracking collection
	*/
	public void addChangeTrackingCollection(long pk,
		long changeTrackingCollectionPK);

	/**
	* Adds an association between the change tracking entry and the change tracking collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollection the change tracking collection
	*/
	public void addChangeTrackingCollection(long pk,
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection);

	/**
	* Adds an association between the change tracking entry and the change tracking collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollectionPKs the primary keys of the change tracking collections
	*/
	public void addChangeTrackingCollections(long pk,
		long[] changeTrackingCollectionPKs);

	/**
	* Adds an association between the change tracking entry and the change tracking collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollections the change tracking collections
	*/
	public void addChangeTrackingCollections(long pk,
		java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> changeTrackingCollections);

	/**
	* Clears all associations between the change tracking entry and its change tracking collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry to clear the associated change tracking collections from
	*/
	public void clearChangeTrackingCollections(long pk);

	/**
	* Removes the association between the change tracking entry and the change tracking collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollectionPK the primary key of the change tracking collection
	*/
	public void removeChangeTrackingCollection(long pk,
		long changeTrackingCollectionPK);

	/**
	* Removes the association between the change tracking entry and the change tracking collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollection the change tracking collection
	*/
	public void removeChangeTrackingCollection(long pk,
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection);

	/**
	* Removes the association between the change tracking entry and the change tracking collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollectionPKs the primary keys of the change tracking collections
	*/
	public void removeChangeTrackingCollections(long pk,
		long[] changeTrackingCollectionPKs);

	/**
	* Removes the association between the change tracking entry and the change tracking collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollections the change tracking collections
	*/
	public void removeChangeTrackingCollections(long pk,
		java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> changeTrackingCollections);

	/**
	* Sets the change tracking collections associated with the change tracking entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollectionPKs the primary keys of the change tracking collections to be associated with the change tracking entry
	*/
	public void setChangeTrackingCollections(long pk,
		long[] changeTrackingCollectionPKs);

	/**
	* Sets the change tracking collections associated with the change tracking entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollections the change tracking collections to be associated with the change tracking entry
	*/
	public void setChangeTrackingCollections(long pk,
		java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> changeTrackingCollections);
}