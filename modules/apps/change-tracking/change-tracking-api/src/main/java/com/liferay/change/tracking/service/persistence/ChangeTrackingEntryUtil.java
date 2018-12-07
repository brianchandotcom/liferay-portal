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

import com.liferay.change.tracking.model.ChangeTrackingEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the change tracking entry service. This utility wraps {@link com.liferay.change.tracking.service.persistence.impl.ChangeTrackingEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangeTrackingEntryPersistence
 * @see com.liferay.change.tracking.service.persistence.impl.ChangeTrackingEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class ChangeTrackingEntryUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(ChangeTrackingEntry changeTrackingEntry) {
		getPersistence().clearCache(changeTrackingEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ChangeTrackingEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ChangeTrackingEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ChangeTrackingEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ChangeTrackingEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ChangeTrackingEntry update(
		ChangeTrackingEntry changeTrackingEntry) {
		return getPersistence().update(changeTrackingEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ChangeTrackingEntry update(
		ChangeTrackingEntry changeTrackingEntry, ServiceContext serviceContext) {
		return getPersistence().update(changeTrackingEntry, serviceContext);
	}

	/**
	* Returns all the change tracking entries where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the matching change tracking entries
	*/
	public static List<ChangeTrackingEntry> findByResourcePrimKey(
		long resourcePrimKey) {
		return getPersistence().findByResourcePrimKey(resourcePrimKey);
	}

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
	public static List<ChangeTrackingEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end) {
		return getPersistence()
				   .findByResourcePrimKey(resourcePrimKey, start, end);
	}

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
	public static List<ChangeTrackingEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		OrderByComparator<ChangeTrackingEntry> orderByComparator) {
		return getPersistence()
				   .findByResourcePrimKey(resourcePrimKey, start, end,
			orderByComparator);
	}

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
	public static List<ChangeTrackingEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		OrderByComparator<ChangeTrackingEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByResourcePrimKey(resourcePrimKey, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first change tracking entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching change tracking entry
	* @throws NoSuchEntryException if a matching change tracking entry could not be found
	*/
	public static ChangeTrackingEntry findByResourcePrimKey_First(
		long resourcePrimKey,
		OrderByComparator<ChangeTrackingEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {
		return getPersistence()
				   .findByResourcePrimKey_First(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Returns the first change tracking entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching change tracking entry, or <code>null</code> if a matching change tracking entry could not be found
	*/
	public static ChangeTrackingEntry fetchByResourcePrimKey_First(
		long resourcePrimKey,
		OrderByComparator<ChangeTrackingEntry> orderByComparator) {
		return getPersistence()
				   .fetchByResourcePrimKey_First(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Returns the last change tracking entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching change tracking entry
	* @throws NoSuchEntryException if a matching change tracking entry could not be found
	*/
	public static ChangeTrackingEntry findByResourcePrimKey_Last(
		long resourcePrimKey,
		OrderByComparator<ChangeTrackingEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {
		return getPersistence()
				   .findByResourcePrimKey_Last(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Returns the last change tracking entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching change tracking entry, or <code>null</code> if a matching change tracking entry could not be found
	*/
	public static ChangeTrackingEntry fetchByResourcePrimKey_Last(
		long resourcePrimKey,
		OrderByComparator<ChangeTrackingEntry> orderByComparator) {
		return getPersistence()
				   .fetchByResourcePrimKey_Last(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Returns the change tracking entries before and after the current change tracking entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param changeTrackingEntryId the primary key of the current change tracking entry
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next change tracking entry
	* @throws NoSuchEntryException if a change tracking entry with the primary key could not be found
	*/
	public static ChangeTrackingEntry[] findByResourcePrimKey_PrevAndNext(
		long changeTrackingEntryId, long resourcePrimKey,
		OrderByComparator<ChangeTrackingEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {
		return getPersistence()
				   .findByResourcePrimKey_PrevAndNext(changeTrackingEntryId,
			resourcePrimKey, orderByComparator);
	}

	/**
	* Removes all the change tracking entries where resourcePrimKey = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	*/
	public static void removeByResourcePrimKey(long resourcePrimKey) {
		getPersistence().removeByResourcePrimKey(resourcePrimKey);
	}

	/**
	* Returns the number of change tracking entries where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the number of matching change tracking entries
	*/
	public static int countByResourcePrimKey(long resourcePrimKey) {
		return getPersistence().countByResourcePrimKey(resourcePrimKey);
	}

	/**
	* Caches the change tracking entry in the entity cache if it is enabled.
	*
	* @param changeTrackingEntry the change tracking entry
	*/
	public static void cacheResult(ChangeTrackingEntry changeTrackingEntry) {
		getPersistence().cacheResult(changeTrackingEntry);
	}

	/**
	* Caches the change tracking entries in the entity cache if it is enabled.
	*
	* @param changeTrackingEntries the change tracking entries
	*/
	public static void cacheResult(
		List<ChangeTrackingEntry> changeTrackingEntries) {
		getPersistence().cacheResult(changeTrackingEntries);
	}

	/**
	* Creates a new change tracking entry with the primary key. Does not add the change tracking entry to the database.
	*
	* @param changeTrackingEntryId the primary key for the new change tracking entry
	* @return the new change tracking entry
	*/
	public static ChangeTrackingEntry create(long changeTrackingEntryId) {
		return getPersistence().create(changeTrackingEntryId);
	}

	/**
	* Removes the change tracking entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingEntryId the primary key of the change tracking entry
	* @return the change tracking entry that was removed
	* @throws NoSuchEntryException if a change tracking entry with the primary key could not be found
	*/
	public static ChangeTrackingEntry remove(long changeTrackingEntryId)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {
		return getPersistence().remove(changeTrackingEntryId);
	}

	public static ChangeTrackingEntry updateImpl(
		ChangeTrackingEntry changeTrackingEntry) {
		return getPersistence().updateImpl(changeTrackingEntry);
	}

	/**
	* Returns the change tracking entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param changeTrackingEntryId the primary key of the change tracking entry
	* @return the change tracking entry
	* @throws NoSuchEntryException if a change tracking entry with the primary key could not be found
	*/
	public static ChangeTrackingEntry findByPrimaryKey(
		long changeTrackingEntryId)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(changeTrackingEntryId);
	}

	/**
	* Returns the change tracking entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param changeTrackingEntryId the primary key of the change tracking entry
	* @return the change tracking entry, or <code>null</code> if a change tracking entry with the primary key could not be found
	*/
	public static ChangeTrackingEntry fetchByPrimaryKey(
		long changeTrackingEntryId) {
		return getPersistence().fetchByPrimaryKey(changeTrackingEntryId);
	}

	public static java.util.Map<java.io.Serializable, ChangeTrackingEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the change tracking entries.
	*
	* @return the change tracking entries
	*/
	public static List<ChangeTrackingEntry> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<ChangeTrackingEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<ChangeTrackingEntry> findAll(int start, int end,
		OrderByComparator<ChangeTrackingEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<ChangeTrackingEntry> findAll(int start, int end,
		OrderByComparator<ChangeTrackingEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the change tracking entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of change tracking entries.
	*
	* @return the number of change tracking entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of change tracking collections associated with the change tracking entry.
	*
	* @param pk the primary key of the change tracking entry
	* @return long[] of the primaryKeys of change tracking collections associated with the change tracking entry
	*/
	public static long[] getChangeTrackingCollectionPrimaryKeys(long pk) {
		return getPersistence().getChangeTrackingCollectionPrimaryKeys(pk);
	}

	/**
	* Returns all the change tracking collections associated with the change tracking entry.
	*
	* @param pk the primary key of the change tracking entry
	* @return the change tracking collections associated with the change tracking entry
	*/
	public static List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingCollections(
		long pk) {
		return getPersistence().getChangeTrackingCollections(pk);
	}

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
	public static List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingCollections(
		long pk, int start, int end) {
		return getPersistence().getChangeTrackingCollections(pk, start, end);
	}

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
	public static List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingCollections(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.model.ChangeTrackingCollection> orderByComparator) {
		return getPersistence()
				   .getChangeTrackingCollections(pk, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of change tracking collections associated with the change tracking entry.
	*
	* @param pk the primary key of the change tracking entry
	* @return the number of change tracking collections associated with the change tracking entry
	*/
	public static int getChangeTrackingCollectionsSize(long pk) {
		return getPersistence().getChangeTrackingCollectionsSize(pk);
	}

	/**
	* Returns <code>true</code> if the change tracking collection is associated with the change tracking entry.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollectionPK the primary key of the change tracking collection
	* @return <code>true</code> if the change tracking collection is associated with the change tracking entry; <code>false</code> otherwise
	*/
	public static boolean containsChangeTrackingCollection(long pk,
		long changeTrackingCollectionPK) {
		return getPersistence()
				   .containsChangeTrackingCollection(pk,
			changeTrackingCollectionPK);
	}

	/**
	* Returns <code>true</code> if the change tracking entry has any change tracking collections associated with it.
	*
	* @param pk the primary key of the change tracking entry to check for associations with change tracking collections
	* @return <code>true</code> if the change tracking entry has any change tracking collections associated with it; <code>false</code> otherwise
	*/
	public static boolean containsChangeTrackingCollections(long pk) {
		return getPersistence().containsChangeTrackingCollections(pk);
	}

	/**
	* Adds an association between the change tracking entry and the change tracking collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollectionPK the primary key of the change tracking collection
	*/
	public static void addChangeTrackingCollection(long pk,
		long changeTrackingCollectionPK) {
		getPersistence()
			.addChangeTrackingCollection(pk, changeTrackingCollectionPK);
	}

	/**
	* Adds an association between the change tracking entry and the change tracking collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollection the change tracking collection
	*/
	public static void addChangeTrackingCollection(long pk,
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection) {
		getPersistence()
			.addChangeTrackingCollection(pk, changeTrackingCollection);
	}

	/**
	* Adds an association between the change tracking entry and the change tracking collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollectionPKs the primary keys of the change tracking collections
	*/
	public static void addChangeTrackingCollections(long pk,
		long[] changeTrackingCollectionPKs) {
		getPersistence()
			.addChangeTrackingCollections(pk, changeTrackingCollectionPKs);
	}

	/**
	* Adds an association between the change tracking entry and the change tracking collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollections the change tracking collections
	*/
	public static void addChangeTrackingCollections(long pk,
		List<com.liferay.change.tracking.model.ChangeTrackingCollection> changeTrackingCollections) {
		getPersistence()
			.addChangeTrackingCollections(pk, changeTrackingCollections);
	}

	/**
	* Clears all associations between the change tracking entry and its change tracking collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry to clear the associated change tracking collections from
	*/
	public static void clearChangeTrackingCollections(long pk) {
		getPersistence().clearChangeTrackingCollections(pk);
	}

	/**
	* Removes the association between the change tracking entry and the change tracking collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollectionPK the primary key of the change tracking collection
	*/
	public static void removeChangeTrackingCollection(long pk,
		long changeTrackingCollectionPK) {
		getPersistence()
			.removeChangeTrackingCollection(pk, changeTrackingCollectionPK);
	}

	/**
	* Removes the association between the change tracking entry and the change tracking collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollection the change tracking collection
	*/
	public static void removeChangeTrackingCollection(long pk,
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection) {
		getPersistence()
			.removeChangeTrackingCollection(pk, changeTrackingCollection);
	}

	/**
	* Removes the association between the change tracking entry and the change tracking collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollectionPKs the primary keys of the change tracking collections
	*/
	public static void removeChangeTrackingCollections(long pk,
		long[] changeTrackingCollectionPKs) {
		getPersistence()
			.removeChangeTrackingCollections(pk, changeTrackingCollectionPKs);
	}

	/**
	* Removes the association between the change tracking entry and the change tracking collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollections the change tracking collections
	*/
	public static void removeChangeTrackingCollections(long pk,
		List<com.liferay.change.tracking.model.ChangeTrackingCollection> changeTrackingCollections) {
		getPersistence()
			.removeChangeTrackingCollections(pk, changeTrackingCollections);
	}

	/**
	* Sets the change tracking collections associated with the change tracking entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollectionPKs the primary keys of the change tracking collections to be associated with the change tracking entry
	*/
	public static void setChangeTrackingCollections(long pk,
		long[] changeTrackingCollectionPKs) {
		getPersistence()
			.setChangeTrackingCollections(pk, changeTrackingCollectionPKs);
	}

	/**
	* Sets the change tracking collections associated with the change tracking entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking entry
	* @param changeTrackingCollections the change tracking collections to be associated with the change tracking entry
	*/
	public static void setChangeTrackingCollections(long pk,
		List<com.liferay.change.tracking.model.ChangeTrackingCollection> changeTrackingCollections) {
		getPersistence()
			.setChangeTrackingCollections(pk, changeTrackingCollections);
	}

	public static ChangeTrackingEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ChangeTrackingEntryPersistence, ChangeTrackingEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ChangeTrackingEntryPersistence.class);

		ServiceTracker<ChangeTrackingEntryPersistence, ChangeTrackingEntryPersistence> serviceTracker =
			new ServiceTracker<ChangeTrackingEntryPersistence, ChangeTrackingEntryPersistence>(bundle.getBundleContext(),
				ChangeTrackingEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}