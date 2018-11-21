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

import com.liferay.change.tracking.engine.model.ChangeCollection;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the change collection service. This utility wraps {@link com.liferay.change.tracking.engine.service.persistence.impl.ChangeCollectionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangeCollectionPersistence
 * @see com.liferay.change.tracking.engine.service.persistence.impl.ChangeCollectionPersistenceImpl
 * @generated
 */
@ProviderType
public class ChangeCollectionUtil {
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
	public static void clearCache(ChangeCollection changeCollection) {
		getPersistence().clearCache(changeCollection);
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
	public static List<ChangeCollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ChangeCollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ChangeCollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ChangeCollection> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ChangeCollection update(ChangeCollection changeCollection) {
		return getPersistence().update(changeCollection);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ChangeCollection update(ChangeCollection changeCollection,
		ServiceContext serviceContext) {
		return getPersistence().update(changeCollection, serviceContext);
	}

	/**
	* Caches the change collection in the entity cache if it is enabled.
	*
	* @param changeCollection the change collection
	*/
	public static void cacheResult(ChangeCollection changeCollection) {
		getPersistence().cacheResult(changeCollection);
	}

	/**
	* Caches the change collections in the entity cache if it is enabled.
	*
	* @param changeCollections the change collections
	*/
	public static void cacheResult(List<ChangeCollection> changeCollections) {
		getPersistence().cacheResult(changeCollections);
	}

	/**
	* Creates a new change collection with the primary key. Does not add the change collection to the database.
	*
	* @param changeCollectionId the primary key for the new change collection
	* @return the new change collection
	*/
	public static ChangeCollection create(long changeCollectionId) {
		return getPersistence().create(changeCollectionId);
	}

	/**
	* Removes the change collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeCollectionId the primary key of the change collection
	* @return the change collection that was removed
	* @throws NoSuchChangeCollectionException if a change collection with the primary key could not be found
	*/
	public static ChangeCollection remove(long changeCollectionId)
		throws com.liferay.change.tracking.engine.exception.NoSuchChangeCollectionException {
		return getPersistence().remove(changeCollectionId);
	}

	public static ChangeCollection updateImpl(ChangeCollection changeCollection) {
		return getPersistence().updateImpl(changeCollection);
	}

	/**
	* Returns the change collection with the primary key or throws a {@link NoSuchChangeCollectionException} if it could not be found.
	*
	* @param changeCollectionId the primary key of the change collection
	* @return the change collection
	* @throws NoSuchChangeCollectionException if a change collection with the primary key could not be found
	*/
	public static ChangeCollection findByPrimaryKey(long changeCollectionId)
		throws com.liferay.change.tracking.engine.exception.NoSuchChangeCollectionException {
		return getPersistence().findByPrimaryKey(changeCollectionId);
	}

	/**
	* Returns the change collection with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param changeCollectionId the primary key of the change collection
	* @return the change collection, or <code>null</code> if a change collection with the primary key could not be found
	*/
	public static ChangeCollection fetchByPrimaryKey(long changeCollectionId) {
		return getPersistence().fetchByPrimaryKey(changeCollectionId);
	}

	public static java.util.Map<java.io.Serializable, ChangeCollection> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the change collections.
	*
	* @return the change collections
	*/
	public static List<ChangeCollection> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<ChangeCollection> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<ChangeCollection> findAll(int start, int end,
		OrderByComparator<ChangeCollection> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<ChangeCollection> findAll(int start, int end,
		OrderByComparator<ChangeCollection> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the change collections from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of change collections.
	*
	* @return the number of change collections
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of change entries associated with the change collection.
	*
	* @param pk the primary key of the change collection
	* @return long[] of the primaryKeys of change entries associated with the change collection
	*/
	public static long[] getChangeEntryPrimaryKeys(long pk) {
		return getPersistence().getChangeEntryPrimaryKeys(pk);
	}

	/**
	* Returns all the change entries associated with the change collection.
	*
	* @param pk the primary key of the change collection
	* @return the change entries associated with the change collection
	*/
	public static List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeEntries(
		long pk) {
		return getPersistence().getChangeEntries(pk);
	}

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
	public static List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeEntries(
		long pk, int start, int end) {
		return getPersistence().getChangeEntries(pk, start, end);
	}

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
	public static List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeEntries(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.engine.model.ChangeEntry> orderByComparator) {
		return getPersistence()
				   .getChangeEntries(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of change entries associated with the change collection.
	*
	* @param pk the primary key of the change collection
	* @return the number of change entries associated with the change collection
	*/
	public static int getChangeEntriesSize(long pk) {
		return getPersistence().getChangeEntriesSize(pk);
	}

	/**
	* Returns <code>true</code> if the change entry is associated with the change collection.
	*
	* @param pk the primary key of the change collection
	* @param changeEntryPK the primary key of the change entry
	* @return <code>true</code> if the change entry is associated with the change collection; <code>false</code> otherwise
	*/
	public static boolean containsChangeEntry(long pk, long changeEntryPK) {
		return getPersistence().containsChangeEntry(pk, changeEntryPK);
	}

	/**
	* Returns <code>true</code> if the change collection has any change entries associated with it.
	*
	* @param pk the primary key of the change collection to check for associations with change entries
	* @return <code>true</code> if the change collection has any change entries associated with it; <code>false</code> otherwise
	*/
	public static boolean containsChangeEntries(long pk) {
		return getPersistence().containsChangeEntries(pk);
	}

	/**
	* Adds an association between the change collection and the change entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntryPK the primary key of the change entry
	*/
	public static void addChangeEntry(long pk, long changeEntryPK) {
		getPersistence().addChangeEntry(pk, changeEntryPK);
	}

	/**
	* Adds an association between the change collection and the change entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntry the change entry
	*/
	public static void addChangeEntry(long pk,
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry) {
		getPersistence().addChangeEntry(pk, changeEntry);
	}

	/**
	* Adds an association between the change collection and the change entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntryPKs the primary keys of the change entries
	*/
	public static void addChangeEntries(long pk, long[] changeEntryPKs) {
		getPersistence().addChangeEntries(pk, changeEntryPKs);
	}

	/**
	* Adds an association between the change collection and the change entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntries the change entries
	*/
	public static void addChangeEntries(long pk,
		List<com.liferay.change.tracking.engine.model.ChangeEntry> changeEntries) {
		getPersistence().addChangeEntries(pk, changeEntries);
	}

	/**
	* Clears all associations between the change collection and its change entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection to clear the associated change entries from
	*/
	public static void clearChangeEntries(long pk) {
		getPersistence().clearChangeEntries(pk);
	}

	/**
	* Removes the association between the change collection and the change entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntryPK the primary key of the change entry
	*/
	public static void removeChangeEntry(long pk, long changeEntryPK) {
		getPersistence().removeChangeEntry(pk, changeEntryPK);
	}

	/**
	* Removes the association between the change collection and the change entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntry the change entry
	*/
	public static void removeChangeEntry(long pk,
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry) {
		getPersistence().removeChangeEntry(pk, changeEntry);
	}

	/**
	* Removes the association between the change collection and the change entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntryPKs the primary keys of the change entries
	*/
	public static void removeChangeEntries(long pk, long[] changeEntryPKs) {
		getPersistence().removeChangeEntries(pk, changeEntryPKs);
	}

	/**
	* Removes the association between the change collection and the change entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntries the change entries
	*/
	public static void removeChangeEntries(long pk,
		List<com.liferay.change.tracking.engine.model.ChangeEntry> changeEntries) {
		getPersistence().removeChangeEntries(pk, changeEntries);
	}

	/**
	* Sets the change entries associated with the change collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntryPKs the primary keys of the change entries to be associated with the change collection
	*/
	public static void setChangeEntries(long pk, long[] changeEntryPKs) {
		getPersistence().setChangeEntries(pk, changeEntryPKs);
	}

	/**
	* Sets the change entries associated with the change collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the change collection
	* @param changeEntries the change entries to be associated with the change collection
	*/
	public static void setChangeEntries(long pk,
		List<com.liferay.change.tracking.engine.model.ChangeEntry> changeEntries) {
		getPersistence().setChangeEntries(pk, changeEntries);
	}

	public static ChangeCollectionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ChangeCollectionPersistence, ChangeCollectionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ChangeCollectionPersistence.class);

		ServiceTracker<ChangeCollectionPersistence, ChangeCollectionPersistence> serviceTracker =
			new ServiceTracker<ChangeCollectionPersistence, ChangeCollectionPersistence>(bundle.getBundleContext(),
				ChangeCollectionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}