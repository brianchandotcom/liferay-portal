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

package com.liferay.document.library.sync.service.persistence;

import com.liferay.document.library.sync.model.DLSyncEvent;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the dl sync event service. This utility wraps <code>com.liferay.document.library.sync.service.persistence.impl.DLSyncEventPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncEventPersistence
 * @generated
 */
public class DLSyncEventUtil {

	/**
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
	public static void clearCache(DLSyncEvent dlSyncEvent) {
		getPersistence().clearCache(dlSyncEvent);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, DLSyncEvent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DLSyncEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLSyncEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLSyncEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DLSyncEvent> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DLSyncEvent update(DLSyncEvent dlSyncEvent) {
		return getPersistence().update(dlSyncEvent);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DLSyncEvent update(
		DLSyncEvent dlSyncEvent, ServiceContext serviceContext) {

		return getPersistence().update(dlSyncEvent, serviceContext);
	}

	/**
	 * Returns all the dl sync events where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @return the matching dl sync events
	 */
	public static List<DLSyncEvent> findByModifiedTime(long modifiedTime) {
		return getPersistence().findByModifiedTime(modifiedTime);
	}

	/**
	 * Returns a range of all the dl sync events where modifiedTime &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @return the range of matching dl sync events
	 */
	public static List<DLSyncEvent> findByModifiedTime(
		long modifiedTime, int start, int end) {

		return getPersistence().findByModifiedTime(modifiedTime, start, end);
	}

	/**
	 * Returns an ordered range of all the dl sync events where modifiedTime &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dl sync events
	 */
	public static List<DLSyncEvent> findByModifiedTime(
		long modifiedTime, int start, int end,
		OrderByComparator<DLSyncEvent> orderByComparator) {

		return getPersistence().findByModifiedTime(
			modifiedTime, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the dl sync events where modifiedTime &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dl sync events
	 */
	public static List<DLSyncEvent> findByModifiedTime(
		long modifiedTime, int start, int end,
		OrderByComparator<DLSyncEvent> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByModifiedTime(
			modifiedTime, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first dl sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dl sync event
	 * @throws NoSuchEventException if a matching dl sync event could not be found
	 */
	public static DLSyncEvent findByModifiedTime_First(
			long modifiedTime, OrderByComparator<DLSyncEvent> orderByComparator)
		throws com.liferay.document.library.sync.exception.
			NoSuchEventException {

		return getPersistence().findByModifiedTime_First(
			modifiedTime, orderByComparator);
	}

	/**
	 * Returns the first dl sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dl sync event, or <code>null</code> if a matching dl sync event could not be found
	 */
	public static DLSyncEvent fetchByModifiedTime_First(
		long modifiedTime, OrderByComparator<DLSyncEvent> orderByComparator) {

		return getPersistence().fetchByModifiedTime_First(
			modifiedTime, orderByComparator);
	}

	/**
	 * Returns the last dl sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dl sync event
	 * @throws NoSuchEventException if a matching dl sync event could not be found
	 */
	public static DLSyncEvent findByModifiedTime_Last(
			long modifiedTime, OrderByComparator<DLSyncEvent> orderByComparator)
		throws com.liferay.document.library.sync.exception.
			NoSuchEventException {

		return getPersistence().findByModifiedTime_Last(
			modifiedTime, orderByComparator);
	}

	/**
	 * Returns the last dl sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dl sync event, or <code>null</code> if a matching dl sync event could not be found
	 */
	public static DLSyncEvent fetchByModifiedTime_Last(
		long modifiedTime, OrderByComparator<DLSyncEvent> orderByComparator) {

		return getPersistence().fetchByModifiedTime_Last(
			modifiedTime, orderByComparator);
	}

	/**
	 * Returns the dl sync events before and after the current dl sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param syncEventId the primary key of the current dl sync event
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dl sync event
	 * @throws NoSuchEventException if a dl sync event with the primary key could not be found
	 */
	public static DLSyncEvent[] findByModifiedTime_PrevAndNext(
			long syncEventId, long modifiedTime,
			OrderByComparator<DLSyncEvent> orderByComparator)
		throws com.liferay.document.library.sync.exception.
			NoSuchEventException {

		return getPersistence().findByModifiedTime_PrevAndNext(
			syncEventId, modifiedTime, orderByComparator);
	}

	/**
	 * Removes all the dl sync events where modifiedTime &gt; &#63; from the database.
	 *
	 * @param modifiedTime the modified time
	 */
	public static void removeByModifiedTime(long modifiedTime) {
		getPersistence().removeByModifiedTime(modifiedTime);
	}

	/**
	 * Returns the number of dl sync events where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @return the number of matching dl sync events
	 */
	public static int countByModifiedTime(long modifiedTime) {
		return getPersistence().countByModifiedTime(modifiedTime);
	}

	/**
	 * Returns the dl sync event where typePK = &#63; or throws a <code>NoSuchEventException</code> if it could not be found.
	 *
	 * @param typePK the type pk
	 * @return the matching dl sync event
	 * @throws NoSuchEventException if a matching dl sync event could not be found
	 */
	public static DLSyncEvent findByTypePK(long typePK)
		throws com.liferay.document.library.sync.exception.
			NoSuchEventException {

		return getPersistence().findByTypePK(typePK);
	}

	/**
	 * Returns the dl sync event where typePK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param typePK the type pk
	 * @return the matching dl sync event, or <code>null</code> if a matching dl sync event could not be found
	 */
	public static DLSyncEvent fetchByTypePK(long typePK) {
		return getPersistence().fetchByTypePK(typePK);
	}

	/**
	 * Returns the dl sync event where typePK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param typePK the type pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dl sync event, or <code>null</code> if a matching dl sync event could not be found
	 */
	public static DLSyncEvent fetchByTypePK(
		long typePK, boolean useFinderCache) {

		return getPersistence().fetchByTypePK(typePK, useFinderCache);
	}

	/**
	 * Removes the dl sync event where typePK = &#63; from the database.
	 *
	 * @param typePK the type pk
	 * @return the dl sync event that was removed
	 */
	public static DLSyncEvent removeByTypePK(long typePK)
		throws com.liferay.document.library.sync.exception.
			NoSuchEventException {

		return getPersistence().removeByTypePK(typePK);
	}

	/**
	 * Returns the number of dl sync events where typePK = &#63;.
	 *
	 * @param typePK the type pk
	 * @return the number of matching dl sync events
	 */
	public static int countByTypePK(long typePK) {
		return getPersistence().countByTypePK(typePK);
	}

	/**
	 * Caches the dl sync event in the entity cache if it is enabled.
	 *
	 * @param dlSyncEvent the dl sync event
	 */
	public static void cacheResult(DLSyncEvent dlSyncEvent) {
		getPersistence().cacheResult(dlSyncEvent);
	}

	/**
	 * Caches the dl sync events in the entity cache if it is enabled.
	 *
	 * @param dlSyncEvents the dl sync events
	 */
	public static void cacheResult(List<DLSyncEvent> dlSyncEvents) {
		getPersistence().cacheResult(dlSyncEvents);
	}

	/**
	 * Creates a new dl sync event with the primary key. Does not add the dl sync event to the database.
	 *
	 * @param syncEventId the primary key for the new dl sync event
	 * @return the new dl sync event
	 */
	public static DLSyncEvent create(long syncEventId) {
		return getPersistence().create(syncEventId);
	}

	/**
	 * Removes the dl sync event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncEventId the primary key of the dl sync event
	 * @return the dl sync event that was removed
	 * @throws NoSuchEventException if a dl sync event with the primary key could not be found
	 */
	public static DLSyncEvent remove(long syncEventId)
		throws com.liferay.document.library.sync.exception.
			NoSuchEventException {

		return getPersistence().remove(syncEventId);
	}

	public static DLSyncEvent updateImpl(DLSyncEvent dlSyncEvent) {
		return getPersistence().updateImpl(dlSyncEvent);
	}

	/**
	 * Returns the dl sync event with the primary key or throws a <code>NoSuchEventException</code> if it could not be found.
	 *
	 * @param syncEventId the primary key of the dl sync event
	 * @return the dl sync event
	 * @throws NoSuchEventException if a dl sync event with the primary key could not be found
	 */
	public static DLSyncEvent findByPrimaryKey(long syncEventId)
		throws com.liferay.document.library.sync.exception.
			NoSuchEventException {

		return getPersistence().findByPrimaryKey(syncEventId);
	}

	/**
	 * Returns the dl sync event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param syncEventId the primary key of the dl sync event
	 * @return the dl sync event, or <code>null</code> if a dl sync event with the primary key could not be found
	 */
	public static DLSyncEvent fetchByPrimaryKey(long syncEventId) {
		return getPersistence().fetchByPrimaryKey(syncEventId);
	}

	/**
	 * Returns all the dl sync events.
	 *
	 * @return the dl sync events
	 */
	public static List<DLSyncEvent> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the dl sync events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @return the range of dl sync events
	 */
	public static List<DLSyncEvent> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the dl sync events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dl sync events
	 */
	public static List<DLSyncEvent> findAll(
		int start, int end, OrderByComparator<DLSyncEvent> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the dl sync events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dl sync events
	 */
	public static List<DLSyncEvent> findAll(
		int start, int end, OrderByComparator<DLSyncEvent> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the dl sync events from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of dl sync events.
	 *
	 * @return the number of dl sync events
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DLSyncEventPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DLSyncEventPersistence, DLSyncEventPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DLSyncEventPersistence.class);

		ServiceTracker<DLSyncEventPersistence, DLSyncEventPersistence>
			serviceTracker =
				new ServiceTracker
					<DLSyncEventPersistence, DLSyncEventPersistence>(
						bundle.getBundleContext(), DLSyncEventPersistence.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}