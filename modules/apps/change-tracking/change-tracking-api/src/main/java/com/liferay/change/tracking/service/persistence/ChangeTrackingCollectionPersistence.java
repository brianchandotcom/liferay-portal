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

import com.liferay.change.tracking.exception.NoSuchCollectionException;
import com.liferay.change.tracking.model.ChangeTrackingCollection;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the change tracking collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.change.tracking.service.persistence.impl.ChangeTrackingCollectionPersistenceImpl
 * @see ChangeTrackingCollectionUtil
 * @generated
 */
@ProviderType
public interface ChangeTrackingCollectionPersistence extends BasePersistence<ChangeTrackingCollection> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ChangeTrackingCollectionUtil} to access the change tracking collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the change tracking collection in the entity cache if it is enabled.
	*
	* @param changeTrackingCollection the change tracking collection
	*/
	public void cacheResult(ChangeTrackingCollection changeTrackingCollection);

	/**
	* Caches the change tracking collections in the entity cache if it is enabled.
	*
	* @param changeTrackingCollections the change tracking collections
	*/
	public void cacheResult(
		java.util.List<ChangeTrackingCollection> changeTrackingCollections);

	/**
	* Creates a new change tracking collection with the primary key. Does not add the change tracking collection to the database.
	*
	* @param changeTrackingCollectionId the primary key for the new change tracking collection
	* @return the new change tracking collection
	*/
	public ChangeTrackingCollection create(long changeTrackingCollectionId);

	/**
	* Removes the change tracking collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingCollectionId the primary key of the change tracking collection
	* @return the change tracking collection that was removed
	* @throws NoSuchCollectionException if a change tracking collection with the primary key could not be found
	*/
	public ChangeTrackingCollection remove(long changeTrackingCollectionId)
		throws NoSuchCollectionException;

	public ChangeTrackingCollection updateImpl(
		ChangeTrackingCollection changeTrackingCollection);

	/**
	* Returns the change tracking collection with the primary key or throws a {@link NoSuchCollectionException} if it could not be found.
	*
	* @param changeTrackingCollectionId the primary key of the change tracking collection
	* @return the change tracking collection
	* @throws NoSuchCollectionException if a change tracking collection with the primary key could not be found
	*/
	public ChangeTrackingCollection findByPrimaryKey(
		long changeTrackingCollectionId) throws NoSuchCollectionException;

	/**
	* Returns the change tracking collection with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param changeTrackingCollectionId the primary key of the change tracking collection
	* @return the change tracking collection, or <code>null</code> if a change tracking collection with the primary key could not be found
	*/
	public ChangeTrackingCollection fetchByPrimaryKey(
		long changeTrackingCollectionId);

	@Override
	public java.util.Map<java.io.Serializable, ChangeTrackingCollection> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the change tracking collections.
	*
	* @return the change tracking collections
	*/
	public java.util.List<ChangeTrackingCollection> findAll();

	/**
	* Returns a range of all the change tracking collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change tracking collections
	* @param end the upper bound of the range of change tracking collections (not inclusive)
	* @return the range of change tracking collections
	*/
	public java.util.List<ChangeTrackingCollection> findAll(int start, int end);

	/**
	* Returns an ordered range of all the change tracking collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change tracking collections
	* @param end the upper bound of the range of change tracking collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of change tracking collections
	*/
	public java.util.List<ChangeTrackingCollection> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeTrackingCollection> orderByComparator);

	/**
	* Returns an ordered range of all the change tracking collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change tracking collections
	* @param end the upper bound of the range of change tracking collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of change tracking collections
	*/
	public java.util.List<ChangeTrackingCollection> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeTrackingCollection> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the change tracking collections from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of change tracking collections.
	*
	* @return the number of change tracking collections
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of change tracking entries associated with the change tracking collection.
	*
	* @param pk the primary key of the change tracking collection
	* @return long[] of the primaryKeys of change tracking entries associated with the change tracking collection
	*/
	public long[] getChangeTrackingEntryPrimaryKeys(long pk);

	/**
	* Returns all the change tracking entries associated with the change tracking collection.
	*
	* @param pk the primary key of the change tracking collection
	* @return the change tracking entries associated with the change tracking collection
	*/
	public java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingEntries(
		long pk);

	/**
	* Returns a range of all the change tracking entries associated with the change tracking collection.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the change tracking collection
	* @param start the lower bound of the range of change tracking collections
	* @param end the upper bound of the range of change tracking collections (not inclusive)
	* @return the range of change tracking entries associated with the change tracking collection
	*/
	public java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingEntries(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the change tracking entries associated with the change tracking collection.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the change tracking collection
	* @param start the lower bound of the range of change tracking collections
	* @param end the upper bound of the range of change tracking collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of change tracking entries associated with the change tracking collection
	*/
	public java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.model.ChangeTrackingEntry> orderByComparator);

	/**
	* Returns the number of change tracking entries associated with the change tracking collection.
	*
	* @param pk the primary key of the change tracking collection
	* @return the number of change tracking entries associated with the change tracking collection
	*/
	public int getChangeTrackingEntriesSize(long pk);

	/**
	* Returns <code>true</code> if the change tracking entry is associated with the change tracking collection.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntryPK the primary key of the change tracking entry
	* @return <code>true</code> if the change tracking entry is associated with the change tracking collection; <code>false</code> otherwise
	*/
	public boolean containsChangeTrackingEntry(long pk,
		long changeTrackingEntryPK);

	/**
	* Returns <code>true</code> if the change tracking collection has any change tracking entries associated with it.
	*
	* @param pk the primary key of the change tracking collection to check for associations with change tracking entries
	* @return <code>true</code> if the change tracking collection has any change tracking entries associated with it; <code>false</code> otherwise
	*/
	public boolean containsChangeTrackingEntries(long pk);

	/**
	* Adds an association between the change tracking collection and the change tracking entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntryPK the primary key of the change tracking entry
	*/
	public void addChangeTrackingEntry(long pk, long changeTrackingEntryPK);

	/**
	* Adds an association between the change tracking collection and the change tracking entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntry the change tracking entry
	*/
	public void addChangeTrackingEntry(long pk,
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry);

	/**
	* Adds an association between the change tracking collection and the change tracking entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntryPKs the primary keys of the change tracking entries
	*/
	public void addChangeTrackingEntries(long pk, long[] changeTrackingEntryPKs);

	/**
	* Adds an association between the change tracking collection and the change tracking entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntries the change tracking entries
	*/
	public void addChangeTrackingEntries(long pk,
		java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> changeTrackingEntries);

	/**
	* Clears all associations between the change tracking collection and its change tracking entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection to clear the associated change tracking entries from
	*/
	public void clearChangeTrackingEntries(long pk);

	/**
	* Removes the association between the change tracking collection and the change tracking entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntryPK the primary key of the change tracking entry
	*/
	public void removeChangeTrackingEntry(long pk, long changeTrackingEntryPK);

	/**
	* Removes the association between the change tracking collection and the change tracking entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntry the change tracking entry
	*/
	public void removeChangeTrackingEntry(long pk,
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry);

	/**
	* Removes the association between the change tracking collection and the change tracking entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntryPKs the primary keys of the change tracking entries
	*/
	public void removeChangeTrackingEntries(long pk,
		long[] changeTrackingEntryPKs);

	/**
	* Removes the association between the change tracking collection and the change tracking entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntries the change tracking entries
	*/
	public void removeChangeTrackingEntries(long pk,
		java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> changeTrackingEntries);

	/**
	* Sets the change tracking entries associated with the change tracking collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntryPKs the primary keys of the change tracking entries to be associated with the change tracking collection
	*/
	public void setChangeTrackingEntries(long pk, long[] changeTrackingEntryPKs);

	/**
	* Sets the change tracking entries associated with the change tracking collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntries the change tracking entries to be associated with the change tracking collection
	*/
	public void setChangeTrackingEntries(long pk,
		java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> changeTrackingEntries);
}