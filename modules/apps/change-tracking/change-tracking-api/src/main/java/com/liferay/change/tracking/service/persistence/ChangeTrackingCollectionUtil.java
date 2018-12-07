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

import com.liferay.change.tracking.model.ChangeTrackingCollection;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the change tracking collection service. This utility wraps {@link com.liferay.change.tracking.service.persistence.impl.ChangeTrackingCollectionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangeTrackingCollectionPersistence
 * @see com.liferay.change.tracking.service.persistence.impl.ChangeTrackingCollectionPersistenceImpl
 * @generated
 */
@ProviderType
public class ChangeTrackingCollectionUtil {
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
	public static void clearCache(
		ChangeTrackingCollection changeTrackingCollection) {
		getPersistence().clearCache(changeTrackingCollection);
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
	public static List<ChangeTrackingCollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ChangeTrackingCollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ChangeTrackingCollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ChangeTrackingCollection> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ChangeTrackingCollection update(
		ChangeTrackingCollection changeTrackingCollection) {
		return getPersistence().update(changeTrackingCollection);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ChangeTrackingCollection update(
		ChangeTrackingCollection changeTrackingCollection,
		ServiceContext serviceContext) {
		return getPersistence().update(changeTrackingCollection, serviceContext);
	}

	/**
	* Caches the change tracking collection in the entity cache if it is enabled.
	*
	* @param changeTrackingCollection the change tracking collection
	*/
	public static void cacheResult(
		ChangeTrackingCollection changeTrackingCollection) {
		getPersistence().cacheResult(changeTrackingCollection);
	}

	/**
	* Caches the change tracking collections in the entity cache if it is enabled.
	*
	* @param changeTrackingCollections the change tracking collections
	*/
	public static void cacheResult(
		List<ChangeTrackingCollection> changeTrackingCollections) {
		getPersistence().cacheResult(changeTrackingCollections);
	}

	/**
	* Creates a new change tracking collection with the primary key. Does not add the change tracking collection to the database.
	*
	* @param changeTrackingCollectionId the primary key for the new change tracking collection
	* @return the new change tracking collection
	*/
	public static ChangeTrackingCollection create(
		long changeTrackingCollectionId) {
		return getPersistence().create(changeTrackingCollectionId);
	}

	/**
	* Removes the change tracking collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingCollectionId the primary key of the change tracking collection
	* @return the change tracking collection that was removed
	* @throws NoSuchCollectionException if a change tracking collection with the primary key could not be found
	*/
	public static ChangeTrackingCollection remove(
		long changeTrackingCollectionId)
		throws com.liferay.change.tracking.exception.NoSuchCollectionException {
		return getPersistence().remove(changeTrackingCollectionId);
	}

	public static ChangeTrackingCollection updateImpl(
		ChangeTrackingCollection changeTrackingCollection) {
		return getPersistence().updateImpl(changeTrackingCollection);
	}

	/**
	* Returns the change tracking collection with the primary key or throws a {@link NoSuchCollectionException} if it could not be found.
	*
	* @param changeTrackingCollectionId the primary key of the change tracking collection
	* @return the change tracking collection
	* @throws NoSuchCollectionException if a change tracking collection with the primary key could not be found
	*/
	public static ChangeTrackingCollection findByPrimaryKey(
		long changeTrackingCollectionId)
		throws com.liferay.change.tracking.exception.NoSuchCollectionException {
		return getPersistence().findByPrimaryKey(changeTrackingCollectionId);
	}

	/**
	* Returns the change tracking collection with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param changeTrackingCollectionId the primary key of the change tracking collection
	* @return the change tracking collection, or <code>null</code> if a change tracking collection with the primary key could not be found
	*/
	public static ChangeTrackingCollection fetchByPrimaryKey(
		long changeTrackingCollectionId) {
		return getPersistence().fetchByPrimaryKey(changeTrackingCollectionId);
	}

	public static java.util.Map<java.io.Serializable, ChangeTrackingCollection> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the change tracking collections.
	*
	* @return the change tracking collections
	*/
	public static List<ChangeTrackingCollection> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<ChangeTrackingCollection> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<ChangeTrackingCollection> findAll(int start, int end,
		OrderByComparator<ChangeTrackingCollection> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<ChangeTrackingCollection> findAll(int start, int end,
		OrderByComparator<ChangeTrackingCollection> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the change tracking collections from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of change tracking collections.
	*
	* @return the number of change tracking collections
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of change tracking entries associated with the change tracking collection.
	*
	* @param pk the primary key of the change tracking collection
	* @return long[] of the primaryKeys of change tracking entries associated with the change tracking collection
	*/
	public static long[] getChangeTrackingEntryPrimaryKeys(long pk) {
		return getPersistence().getChangeTrackingEntryPrimaryKeys(pk);
	}

	/**
	* Returns all the change tracking entries associated with the change tracking collection.
	*
	* @param pk the primary key of the change tracking collection
	* @return the change tracking entries associated with the change tracking collection
	*/
	public static List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingEntries(
		long pk) {
		return getPersistence().getChangeTrackingEntries(pk);
	}

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
	public static List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingEntries(
		long pk, int start, int end) {
		return getPersistence().getChangeTrackingEntries(pk, start, end);
	}

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
	public static List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingEntries(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.model.ChangeTrackingEntry> orderByComparator) {
		return getPersistence()
				   .getChangeTrackingEntries(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of change tracking entries associated with the change tracking collection.
	*
	* @param pk the primary key of the change tracking collection
	* @return the number of change tracking entries associated with the change tracking collection
	*/
	public static int getChangeTrackingEntriesSize(long pk) {
		return getPersistence().getChangeTrackingEntriesSize(pk);
	}

	/**
	* Returns <code>true</code> if the change tracking entry is associated with the change tracking collection.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntryPK the primary key of the change tracking entry
	* @return <code>true</code> if the change tracking entry is associated with the change tracking collection; <code>false</code> otherwise
	*/
	public static boolean containsChangeTrackingEntry(long pk,
		long changeTrackingEntryPK) {
		return getPersistence()
				   .containsChangeTrackingEntry(pk, changeTrackingEntryPK);
	}

	/**
	* Returns <code>true</code> if the change tracking collection has any change tracking entries associated with it.
	*
	* @param pk the primary key of the change tracking collection to check for associations with change tracking entries
	* @return <code>true</code> if the change tracking collection has any change tracking entries associated with it; <code>false</code> otherwise
	*/
	public static boolean containsChangeTrackingEntries(long pk) {
		return getPersistence().containsChangeTrackingEntries(pk);
	}

	/**
	* Adds an association between the change tracking collection and the change tracking entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntryPK the primary key of the change tracking entry
	*/
	public static void addChangeTrackingEntry(long pk,
		long changeTrackingEntryPK) {
		getPersistence().addChangeTrackingEntry(pk, changeTrackingEntryPK);
	}

	/**
	* Adds an association between the change tracking collection and the change tracking entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntry the change tracking entry
	*/
	public static void addChangeTrackingEntry(long pk,
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry) {
		getPersistence().addChangeTrackingEntry(pk, changeTrackingEntry);
	}

	/**
	* Adds an association between the change tracking collection and the change tracking entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntryPKs the primary keys of the change tracking entries
	*/
	public static void addChangeTrackingEntries(long pk,
		long[] changeTrackingEntryPKs) {
		getPersistence().addChangeTrackingEntries(pk, changeTrackingEntryPKs);
	}

	/**
	* Adds an association between the change tracking collection and the change tracking entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntries the change tracking entries
	*/
	public static void addChangeTrackingEntries(long pk,
		List<com.liferay.change.tracking.model.ChangeTrackingEntry> changeTrackingEntries) {
		getPersistence().addChangeTrackingEntries(pk, changeTrackingEntries);
	}

	/**
	* Clears all associations between the change tracking collection and its change tracking entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection to clear the associated change tracking entries from
	*/
	public static void clearChangeTrackingEntries(long pk) {
		getPersistence().clearChangeTrackingEntries(pk);
	}

	/**
	* Removes the association between the change tracking collection and the change tracking entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntryPK the primary key of the change tracking entry
	*/
	public static void removeChangeTrackingEntry(long pk,
		long changeTrackingEntryPK) {
		getPersistence().removeChangeTrackingEntry(pk, changeTrackingEntryPK);
	}

	/**
	* Removes the association between the change tracking collection and the change tracking entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntry the change tracking entry
	*/
	public static void removeChangeTrackingEntry(long pk,
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry) {
		getPersistence().removeChangeTrackingEntry(pk, changeTrackingEntry);
	}

	/**
	* Removes the association between the change tracking collection and the change tracking entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntryPKs the primary keys of the change tracking entries
	*/
	public static void removeChangeTrackingEntries(long pk,
		long[] changeTrackingEntryPKs) {
		getPersistence().removeChangeTrackingEntries(pk, changeTrackingEntryPKs);
	}

	/**
	* Removes the association between the change tracking collection and the change tracking entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntries the change tracking entries
	*/
	public static void removeChangeTrackingEntries(long pk,
		List<com.liferay.change.tracking.model.ChangeTrackingEntry> changeTrackingEntries) {
		getPersistence().removeChangeTrackingEntries(pk, changeTrackingEntries);
	}

	/**
	* Sets the change tracking entries associated with the change tracking collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntryPKs the primary keys of the change tracking entries to be associated with the change tracking collection
	*/
	public static void setChangeTrackingEntries(long pk,
		long[] changeTrackingEntryPKs) {
		getPersistence().setChangeTrackingEntries(pk, changeTrackingEntryPKs);
	}

	/**
	* Sets the change tracking entries associated with the change tracking collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change tracking collection
	* @param changeTrackingEntries the change tracking entries to be associated with the change tracking collection
	*/
	public static void setChangeTrackingEntries(long pk,
		List<com.liferay.change.tracking.model.ChangeTrackingEntry> changeTrackingEntries) {
		getPersistence().setChangeTrackingEntries(pk, changeTrackingEntries);
	}

	public static ChangeTrackingCollectionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ChangeTrackingCollectionPersistence, ChangeTrackingCollectionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ChangeTrackingCollectionPersistence.class);

		ServiceTracker<ChangeTrackingCollectionPersistence, ChangeTrackingCollectionPersistence> serviceTracker =
			new ServiceTracker<ChangeTrackingCollectionPersistence, ChangeTrackingCollectionPersistence>(bundle.getBundleContext(),
				ChangeTrackingCollectionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}