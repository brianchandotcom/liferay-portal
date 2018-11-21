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

import com.liferay.change.tracking.engine.model.ChangeEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the change entry service. This utility wraps {@link com.liferay.change.tracking.engine.service.persistence.impl.ChangeEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangeEntryPersistence
 * @see com.liferay.change.tracking.engine.service.persistence.impl.ChangeEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class ChangeEntryUtil {
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
	public static void clearCache(ChangeEntry changeEntry) {
		getPersistence().clearCache(changeEntry);
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
	public static List<ChangeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ChangeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ChangeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ChangeEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ChangeEntry update(ChangeEntry changeEntry) {
		return getPersistence().update(changeEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ChangeEntry update(ChangeEntry changeEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(changeEntry, serviceContext);
	}

	/**
	* Returns all the change entries where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the matching change entries
	*/
	public static List<ChangeEntry> findByResourcePrimKey(long resourcePrimKey) {
		return getPersistence().findByResourcePrimKey(resourcePrimKey);
	}

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
	public static List<ChangeEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end) {
		return getPersistence()
				   .findByResourcePrimKey(resourcePrimKey, start, end);
	}

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
	public static List<ChangeEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		OrderByComparator<ChangeEntry> orderByComparator) {
		return getPersistence()
				   .findByResourcePrimKey(resourcePrimKey, start, end,
			orderByComparator);
	}

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
	public static List<ChangeEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		OrderByComparator<ChangeEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByResourcePrimKey(resourcePrimKey, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first change entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching change entry
	* @throws NoSuchChangeEntryException if a matching change entry could not be found
	*/
	public static ChangeEntry findByResourcePrimKey_First(
		long resourcePrimKey, OrderByComparator<ChangeEntry> orderByComparator)
		throws com.liferay.change.tracking.engine.exception.NoSuchChangeEntryException {
		return getPersistence()
				   .findByResourcePrimKey_First(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Returns the first change entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching change entry, or <code>null</code> if a matching change entry could not be found
	*/
	public static ChangeEntry fetchByResourcePrimKey_First(
		long resourcePrimKey, OrderByComparator<ChangeEntry> orderByComparator) {
		return getPersistence()
				   .fetchByResourcePrimKey_First(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Returns the last change entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching change entry
	* @throws NoSuchChangeEntryException if a matching change entry could not be found
	*/
	public static ChangeEntry findByResourcePrimKey_Last(long resourcePrimKey,
		OrderByComparator<ChangeEntry> orderByComparator)
		throws com.liferay.change.tracking.engine.exception.NoSuchChangeEntryException {
		return getPersistence()
				   .findByResourcePrimKey_Last(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Returns the last change entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching change entry, or <code>null</code> if a matching change entry could not be found
	*/
	public static ChangeEntry fetchByResourcePrimKey_Last(
		long resourcePrimKey, OrderByComparator<ChangeEntry> orderByComparator) {
		return getPersistence()
				   .fetchByResourcePrimKey_Last(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Returns the change entries before and after the current change entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param changeEntryId the primary key of the current change entry
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next change entry
	* @throws NoSuchChangeEntryException if a change entry with the primary key could not be found
	*/
	public static ChangeEntry[] findByResourcePrimKey_PrevAndNext(
		long changeEntryId, long resourcePrimKey,
		OrderByComparator<ChangeEntry> orderByComparator)
		throws com.liferay.change.tracking.engine.exception.NoSuchChangeEntryException {
		return getPersistence()
				   .findByResourcePrimKey_PrevAndNext(changeEntryId,
			resourcePrimKey, orderByComparator);
	}

	/**
	* Removes all the change entries where resourcePrimKey = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	*/
	public static void removeByResourcePrimKey(long resourcePrimKey) {
		getPersistence().removeByResourcePrimKey(resourcePrimKey);
	}

	/**
	* Returns the number of change entries where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the number of matching change entries
	*/
	public static int countByResourcePrimKey(long resourcePrimKey) {
		return getPersistence().countByResourcePrimKey(resourcePrimKey);
	}

	/**
	* Caches the change entry in the entity cache if it is enabled.
	*
	* @param changeEntry the change entry
	*/
	public static void cacheResult(ChangeEntry changeEntry) {
		getPersistence().cacheResult(changeEntry);
	}

	/**
	* Caches the change entries in the entity cache if it is enabled.
	*
	* @param changeEntries the change entries
	*/
	public static void cacheResult(List<ChangeEntry> changeEntries) {
		getPersistence().cacheResult(changeEntries);
	}

	/**
	* Creates a new change entry with the primary key. Does not add the change entry to the database.
	*
	* @param changeEntryId the primary key for the new change entry
	* @return the new change entry
	*/
	public static ChangeEntry create(long changeEntryId) {
		return getPersistence().create(changeEntryId);
	}

	/**
	* Removes the change entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeEntryId the primary key of the change entry
	* @return the change entry that was removed
	* @throws NoSuchChangeEntryException if a change entry with the primary key could not be found
	*/
	public static ChangeEntry remove(long changeEntryId)
		throws com.liferay.change.tracking.engine.exception.NoSuchChangeEntryException {
		return getPersistence().remove(changeEntryId);
	}

	public static ChangeEntry updateImpl(ChangeEntry changeEntry) {
		return getPersistence().updateImpl(changeEntry);
	}

	/**
	* Returns the change entry with the primary key or throws a {@link NoSuchChangeEntryException} if it could not be found.
	*
	* @param changeEntryId the primary key of the change entry
	* @return the change entry
	* @throws NoSuchChangeEntryException if a change entry with the primary key could not be found
	*/
	public static ChangeEntry findByPrimaryKey(long changeEntryId)
		throws com.liferay.change.tracking.engine.exception.NoSuchChangeEntryException {
		return getPersistence().findByPrimaryKey(changeEntryId);
	}

	/**
	* Returns the change entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param changeEntryId the primary key of the change entry
	* @return the change entry, or <code>null</code> if a change entry with the primary key could not be found
	*/
	public static ChangeEntry fetchByPrimaryKey(long changeEntryId) {
		return getPersistence().fetchByPrimaryKey(changeEntryId);
	}

	public static java.util.Map<java.io.Serializable, ChangeEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the change entries.
	*
	* @return the change entries
	*/
	public static List<ChangeEntry> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<ChangeEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<ChangeEntry> findAll(int start, int end,
		OrderByComparator<ChangeEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<ChangeEntry> findAll(int start, int end,
		OrderByComparator<ChangeEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the change entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of change entries.
	*
	* @return the number of change entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of change collections associated with the change entry.
	*
	* @param pk the primary key of the change entry
	* @return long[] of the primaryKeys of change collections associated with the change entry
	*/
	public static long[] getChangeCollectionPrimaryKeys(long pk) {
		return getPersistence().getChangeCollectionPrimaryKeys(pk);
	}

	/**
	* Returns all the change collections associated with the change entry.
	*
	* @param pk the primary key of the change entry
	* @return the change collections associated with the change entry
	*/
	public static List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeCollections(
		long pk) {
		return getPersistence().getChangeCollections(pk);
	}

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
	public static List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeCollections(
		long pk, int start, int end) {
		return getPersistence().getChangeCollections(pk, start, end);
	}

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
	public static List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeCollections(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.engine.model.ChangeCollection> orderByComparator) {
		return getPersistence()
				   .getChangeCollections(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of change collections associated with the change entry.
	*
	* @param pk the primary key of the change entry
	* @return the number of change collections associated with the change entry
	*/
	public static int getChangeCollectionsSize(long pk) {
		return getPersistence().getChangeCollectionsSize(pk);
	}

	/**
	* Returns <code>true</code> if the change collection is associated with the change entry.
	*
	* @param pk the primary key of the change entry
	* @param changeCollectionPK the primary key of the change collection
	* @return <code>true</code> if the change collection is associated with the change entry; <code>false</code> otherwise
	*/
	public static boolean containsChangeCollection(long pk,
		long changeCollectionPK) {
		return getPersistence().containsChangeCollection(pk, changeCollectionPK);
	}

	/**
	* Returns <code>true</code> if the change entry has any change collections associated with it.
	*
	* @param pk the primary key of the change entry to check for associations with change collections
	* @return <code>true</code> if the change entry has any change collections associated with it; <code>false</code> otherwise
	*/
	public static boolean containsChangeCollections(long pk) {
		return getPersistence().containsChangeCollections(pk);
	}

	/**
	* Adds an association between the change entry and the change collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollectionPK the primary key of the change collection
	*/
	public static void addChangeCollection(long pk, long changeCollectionPK) {
		getPersistence().addChangeCollection(pk, changeCollectionPK);
	}

	/**
	* Adds an association between the change entry and the change collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollection the change collection
	*/
	public static void addChangeCollection(long pk,
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection) {
		getPersistence().addChangeCollection(pk, changeCollection);
	}

	/**
	* Adds an association between the change entry and the change collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollectionPKs the primary keys of the change collections
	*/
	public static void addChangeCollections(long pk, long[] changeCollectionPKs) {
		getPersistence().addChangeCollections(pk, changeCollectionPKs);
	}

	/**
	* Adds an association between the change entry and the change collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollections the change collections
	*/
	public static void addChangeCollections(long pk,
		List<com.liferay.change.tracking.engine.model.ChangeCollection> changeCollections) {
		getPersistence().addChangeCollections(pk, changeCollections);
	}

	/**
	* Clears all associations between the change entry and its change collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry to clear the associated change collections from
	*/
	public static void clearChangeCollections(long pk) {
		getPersistence().clearChangeCollections(pk);
	}

	/**
	* Removes the association between the change entry and the change collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollectionPK the primary key of the change collection
	*/
	public static void removeChangeCollection(long pk, long changeCollectionPK) {
		getPersistence().removeChangeCollection(pk, changeCollectionPK);
	}

	/**
	* Removes the association between the change entry and the change collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollection the change collection
	*/
	public static void removeChangeCollection(long pk,
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection) {
		getPersistence().removeChangeCollection(pk, changeCollection);
	}

	/**
	* Removes the association between the change entry and the change collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollectionPKs the primary keys of the change collections
	*/
	public static void removeChangeCollections(long pk,
		long[] changeCollectionPKs) {
		getPersistence().removeChangeCollections(pk, changeCollectionPKs);
	}

	/**
	* Removes the association between the change entry and the change collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollections the change collections
	*/
	public static void removeChangeCollections(long pk,
		List<com.liferay.change.tracking.engine.model.ChangeCollection> changeCollections) {
		getPersistence().removeChangeCollections(pk, changeCollections);
	}

	/**
	* Sets the change collections associated with the change entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollectionPKs the primary keys of the change collections to be associated with the change entry
	*/
	public static void setChangeCollections(long pk, long[] changeCollectionPKs) {
		getPersistence().setChangeCollections(pk, changeCollectionPKs);
	}

	/**
	* Sets the change collections associated with the change entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change entry
	* @param changeCollections the change collections to be associated with the change entry
	*/
	public static void setChangeCollections(long pk,
		List<com.liferay.change.tracking.engine.model.ChangeCollection> changeCollections) {
		getPersistence().setChangeCollections(pk, changeCollections);
	}

	public static ChangeEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ChangeEntryPersistence, ChangeEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ChangeEntryPersistence.class);

		ServiceTracker<ChangeEntryPersistence, ChangeEntryPersistence> serviceTracker =
			new ServiceTracker<ChangeEntryPersistence, ChangeEntryPersistence>(bundle.getBundleContext(),
				ChangeEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}