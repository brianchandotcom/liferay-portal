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

import com.liferay.change.tracking.engine.exception.NoSuchChangeEntryException;
import com.liferay.change.tracking.engine.model.ChangeEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the change entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.change.tracking.engine.service.persistence.impl.ChangeEntryPersistenceImpl
 * @see ChangeEntryUtil
 * @generated
 */
@ProviderType
public interface ChangeEntryPersistence extends BasePersistence<ChangeEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ChangeEntryUtil} to access the change entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the change entries where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the matching change entries
	*/
	public java.util.List<ChangeEntry> findByResourcePrimKey(
		long resourcePrimKey);

	/**
	* Returns a range of all the change entries where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param start the lower bound of the range of change entries
	* @param end the upper bound of the range of change entries (not inclusive)
	* @return the range of matching change entries
	*/
	public java.util.List<ChangeEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end);

	/**
	* Returns an ordered range of all the change entries where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param start the lower bound of the range of change entries
	* @param end the upper bound of the range of change entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching change entries
	*/
	public java.util.List<ChangeEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeEntry> orderByComparator);

	/**
	* Returns an ordered range of all the change entries where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param start the lower bound of the range of change entries
	* @param end the upper bound of the range of change entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching change entries
	*/
	public java.util.List<ChangeEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first change entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching change entry
	* @throws NoSuchChangeEntryException if a matching change entry could not be found
	*/
	public ChangeEntry findByResourcePrimKey_First(long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeEntry> orderByComparator)
		throws NoSuchChangeEntryException;

	/**
	* Returns the first change entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching change entry, or <code>null</code> if a matching change entry could not be found
	*/
	public ChangeEntry fetchByResourcePrimKey_First(long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeEntry> orderByComparator);

	/**
	* Returns the last change entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching change entry
	* @throws NoSuchChangeEntryException if a matching change entry could not be found
	*/
	public ChangeEntry findByResourcePrimKey_Last(long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeEntry> orderByComparator)
		throws NoSuchChangeEntryException;

	/**
	* Returns the last change entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching change entry, or <code>null</code> if a matching change entry could not be found
	*/
	public ChangeEntry fetchByResourcePrimKey_Last(long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeEntry> orderByComparator);

	/**
	* Returns the change entries before and after the current change entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param changeEntryId the primary key of the current change entry
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next change entry
	* @throws NoSuchChangeEntryException if a change entry with the primary key could not be found
	*/
	public ChangeEntry[] findByResourcePrimKey_PrevAndNext(long changeEntryId,
		long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeEntry> orderByComparator)
		throws NoSuchChangeEntryException;

	/**
	* Removes all the change entries where resourcePrimKey = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	*/
	public void removeByResourcePrimKey(long resourcePrimKey);

	/**
	* Returns the number of change entries where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the number of matching change entries
	*/
	public int countByResourcePrimKey(long resourcePrimKey);

	/**
	* Caches the change entry in the entity cache if it is enabled.
	*
	* @param changeEntry the change entry
	*/
	public void cacheResult(ChangeEntry changeEntry);

	/**
	* Caches the change entries in the entity cache if it is enabled.
	*
	* @param changeEntries the change entries
	*/
	public void cacheResult(java.util.List<ChangeEntry> changeEntries);

	/**
	* Creates a new change entry with the primary key. Does not add the change entry to the database.
	*
	* @param changeEntryId the primary key for the new change entry
	* @return the new change entry
	*/
	public ChangeEntry create(long changeEntryId);

	/**
	* Removes the change entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeEntryId the primary key of the change entry
	* @return the change entry that was removed
	* @throws NoSuchChangeEntryException if a change entry with the primary key could not be found
	*/
	public ChangeEntry remove(long changeEntryId)
		throws NoSuchChangeEntryException;

	public ChangeEntry updateImpl(ChangeEntry changeEntry);

	/**
	* Returns the change entry with the primary key or throws a {@link NoSuchChangeEntryException} if it could not be found.
	*
	* @param changeEntryId the primary key of the change entry
	* @return the change entry
	* @throws NoSuchChangeEntryException if a change entry with the primary key could not be found
	*/
	public ChangeEntry findByPrimaryKey(long changeEntryId)
		throws NoSuchChangeEntryException;

	/**
	* Returns the change entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param changeEntryId the primary key of the change entry
	* @return the change entry, or <code>null</code> if a change entry with the primary key could not be found
	*/
	public ChangeEntry fetchByPrimaryKey(long changeEntryId);

	@Override
	public java.util.Map<java.io.Serializable, ChangeEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the change entries.
	*
	* @return the change entries
	*/
	public java.util.List<ChangeEntry> findAll();

	/**
	* Returns a range of all the change entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change entries
	* @param end the upper bound of the range of change entries (not inclusive)
	* @return the range of change entries
	*/
	public java.util.List<ChangeEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the change entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change entries
	* @param end the upper bound of the range of change entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of change entries
	*/
	public java.util.List<ChangeEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeEntry> orderByComparator);

	/**
	* Returns an ordered range of all the change entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change entries
	* @param end the upper bound of the range of change entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of change entries
	*/
	public java.util.List<ChangeEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangeEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the change entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of change entries.
	*
	* @return the number of change entries
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of change collections associated with the change entry.
	*
	* @param pk the primary key of the change entry
	* @return long[] of the primaryKeys of change collections associated with the change entry
	*/
	public long[] getChangeCollectionPrimaryKeys(long pk);

	/**
	* Returns all the change collections associated with the change entry.
	*
	* @param pk the primary key of the change entry
	* @return the change collections associated with the change entry
	*/
	public java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeCollections(
		long pk);

	/**
	* Returns a range of all the change collections associated with the change entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the change entry
	* @param start the lower bound of the range of change entries
	* @param end the upper bound of the range of change entries (not inclusive)
	* @return the range of change collections associated with the change entry
	*/
	public java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeCollections(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the change collections associated with the change entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the change entry
	* @param start the lower bound of the range of change entries
	* @param end the upper bound of the range of change entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of change collections associated with the change entry
	*/
	public java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeCollections(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.engine.model.ChangeCollection> orderByComparator);

	/**
	* Returns the number of change collections associated with the change entry.
	*
	* @param pk the primary key of the change entry
	* @return the number of change collections associated with the change entry
	*/
	public int getChangeCollectionsSize(long pk);

	/**
	* Returns <code>true</code> if the change collection is associated with the change entry.
	*
	* @param pk the primary key of the change entry
	* @param changeCollectionPK the primary key of the change collection
	* @return <code>true</code> if the change collection is associated with the change entry; <code>false</code> otherwise
	*/
	public boolean containsChangeCollection(long pk, long changeCollectionPK);

	/**
	* Returns <code>true</code> if the change entry has any change collections associated with it.
	*
	* @param pk the primary key of the change entry to check for associations with change collections
	* @return <code>true</code> if the change entry has any change collections associated with it; <code>false</code> otherwise
	*/
	public boolean containsChangeCollections(long pk);

	/**
	* Adds an association between the change entry and the change collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollectionPK the primary key of the change collection
	*/
	public void addChangeCollection(long pk, long changeCollectionPK);

	/**
	* Adds an association between the change entry and the change collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollection the change collection
	*/
	public void addChangeCollection(long pk,
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection);

	/**
	* Adds an association between the change entry and the change collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollectionPKs the primary keys of the change collections
	*/
	public void addChangeCollections(long pk, long[] changeCollectionPKs);

	/**
	* Adds an association between the change entry and the change collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollections the change collections
	*/
	public void addChangeCollections(long pk,
		java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> changeCollections);

	/**
	* Clears all associations between the change entry and its change collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry to clear the associated change collections from
	*/
	public void clearChangeCollections(long pk);

	/**
	* Removes the association between the change entry and the change collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollectionPK the primary key of the change collection
	*/
	public void removeChangeCollection(long pk, long changeCollectionPK);

	/**
	* Removes the association between the change entry and the change collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollection the change collection
	*/
	public void removeChangeCollection(long pk,
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection);

	/**
	* Removes the association between the change entry and the change collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollectionPKs the primary keys of the change collections
	*/
	public void removeChangeCollections(long pk, long[] changeCollectionPKs);

	/**
	* Removes the association between the change entry and the change collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollections the change collections
	*/
	public void removeChangeCollections(long pk,
		java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> changeCollections);

	/**
	* Sets the change collections associated with the change entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollectionPKs the primary keys of the change collections to be associated with the change entry
	*/
	public void setChangeCollections(long pk, long[] changeCollectionPKs);

	/**
	* Sets the change collections associated with the change entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollections the change collections to be associated with the change entry
	*/
	public void setChangeCollections(long pk,
		java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> changeCollections);
}