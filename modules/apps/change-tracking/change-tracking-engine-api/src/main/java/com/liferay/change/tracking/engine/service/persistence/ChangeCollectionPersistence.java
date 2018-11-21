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

package com.liferay.change.tracking.engine.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.engine.exception.NoSuchChangeCollectionException;
import com.liferay.change.tracking.engine.model.ChangeCollection;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the change collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.change.tracking.engine.service.persistence.impl.ChangeCollectionPersistenceImpl
 * @see ChangeCollectionUtil
 * @generated
 */
@ProviderType
public interface ChangeCollectionPersistence extends BasePersistence<ChangeCollection> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ChangeCollectionUtil} to access the change collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the change collection in the entity cache if it is enabled.
	*
	* @param changeCollection the change collection
	*/
	public void cacheResult(ChangeCollection changeCollection);

	/**
	* Caches the change collections in the entity cache if it is enabled.
	*
	* @param changeCollections the change collections
	*/
	public void cacheResult(java.util.List<ChangeCollection> changeCollections);

	/**
	* Creates a new change collection with the primary key. Does not add the change collection to the database.
	*
	* @param changeCollectionId the primary key for the new change collection
	* @return the new change collection
	*/
	public ChangeCollection create(long changeCollectionId);

	/**
	* Removes the change collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeCollectionId the primary key of the change collection
	* @return the change collection that was removed
	* @throws NoSuchChangeCollectionException if a change collection with the primary key could not be found
	*/
	public ChangeCollection remove(long changeCollectionId)
		throws NoSuchChangeCollectionException;

	public ChangeCollection updateImpl(ChangeCollection changeCollection);

	/**
	* Returns the change collection with the primary key or throws a {@link NoSuchChangeCollectionException} if it could not be found.
	*
	* @param changeCollectionId the primary key of the change collection
	* @return the change collection
	* @throws NoSuchChangeCollectionException if a change collection with the primary key could not be found
	*/
	public ChangeCollection findByPrimaryKey(long changeCollectionId)
		throws NoSuchChangeCollectionException;

	/**
	* Returns the change collection with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param changeCollectionId the primary key of the change collection
	* @return the change collection, or <code>null</code> if a change collection with the primary key could not be found
	*/
	public ChangeCollection fetchByPrimaryKey(long changeCollectionId);

	@Override
	public java.util.Map<java.io.Serializable, ChangeCollection> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the change collections.
	*
	* @return the change collections
	*/
	public java.util.List<ChangeCollection> findAll();

	/**
	* Returns a range of all the change collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change collections
	* @param end the upper bound of the range of change collections (not inclusive)
	* @return the range of change collections
	*/
	public java.util.List<ChangeCollection> findAll(int start, int end);

	/**
	* Returns an ordered range of all the change collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change collections
	* @param end the upper bound of the range of change collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of change collections
	*/
	public java.util.List<ChangeCollection> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeCollection> orderByComparator);

	/**
	* Returns an ordered range of all the change collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change collections
	* @param end the upper bound of the range of change collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of change collections
	*/
	public java.util.List<ChangeCollection> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeCollection> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the change collections from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of change collections.
	*
	* @return the number of change collections
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of change entries associated with the change collection.
	*
	* @param pk the primary key of the change collection
	* @return long[] of the primaryKeys of change entries associated with the change collection
	*/
	public long[] getChangeEntryPrimaryKeys(long pk);

	/**
	* Returns all the change entries associated with the change collection.
	*
	* @param pk the primary key of the change collection
	* @return the change entries associated with the change collection
	*/
	public java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeEntries(
		long pk);

	/**
	* Returns a range of all the change entries associated with the change collection.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the change collection
	* @param start the lower bound of the range of change collections
	* @param end the upper bound of the range of change collections (not inclusive)
	* @return the range of change entries associated with the change collection
	*/
	public java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeEntries(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the change entries associated with the change collection.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the change collection
	* @param start the lower bound of the range of change collections
	* @param end the upper bound of the range of change collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of change entries associated with the change collection
	*/
	public java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.engine.model.ChangeEntry> orderByComparator);

	/**
	* Returns the number of change entries associated with the change collection.
	*
	* @param pk the primary key of the change collection
	* @return the number of change entries associated with the change collection
	*/
	public int getChangeEntriesSize(long pk);

	/**
	* Returns <code>true</code> if the change entry is associated with the change collection.
	*
	* @param pk the primary key of the change collection
	* @param changeEntryPK the primary key of the change entry
	* @return <code>true</code> if the change entry is associated with the change collection; <code>false</code> otherwise
	*/
	public boolean containsChangeEntry(long pk, long changeEntryPK);

	/**
	* Returns <code>true</code> if the change collection has any change entries associated with it.
	*
	* @param pk the primary key of the change collection to check for associations with change entries
	* @return <code>true</code> if the change collection has any change entries associated with it; <code>false</code> otherwise
	*/
	public boolean containsChangeEntries(long pk);

	/**
	* Adds an association between the change collection and the change entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntryPK the primary key of the change entry
	*/
	public void addChangeEntry(long pk, long changeEntryPK);

	/**
	* Adds an association between the change collection and the change entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntry the change entry
	*/
	public void addChangeEntry(long pk,
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry);

	/**
	* Adds an association between the change collection and the change entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntryPKs the primary keys of the change entries
	*/
	public void addChangeEntries(long pk, long[] changeEntryPKs);

	/**
	* Adds an association between the change collection and the change entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntries the change entries
	*/
	public void addChangeEntries(long pk,
		java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> changeEntries);

	/**
	* Clears all associations between the change collection and its change entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection to clear the associated change entries from
	*/
	public void clearChangeEntries(long pk);

	/**
	* Removes the association between the change collection and the change entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntryPK the primary key of the change entry
	*/
	public void removeChangeEntry(long pk, long changeEntryPK);

	/**
	* Removes the association between the change collection and the change entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntry the change entry
	*/
	public void removeChangeEntry(long pk,
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry);

	/**
	* Removes the association between the change collection and the change entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntryPKs the primary keys of the change entries
	*/
	public void removeChangeEntries(long pk, long[] changeEntryPKs);

	/**
	* Removes the association between the change collection and the change entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntries the change entries
	*/
	public void removeChangeEntries(long pk,
		java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> changeEntries);

	/**
	* Sets the change entries associated with the change collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntryPKs the primary keys of the change entries to be associated with the change collection
	*/
	public void setChangeEntries(long pk, long[] changeEntryPKs);

	/**
	* Sets the change entries associated with the change collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntries the change entries to be associated with the change collection
	*/
	public void setChangeEntries(long pk,
		java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> changeEntries);
}