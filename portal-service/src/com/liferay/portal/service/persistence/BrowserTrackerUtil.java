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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.BrowserTracker;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the browser tracker service. This utility wraps {@link BrowserTrackerPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BrowserTrackerPersistence
 * @see BrowserTrackerPersistenceImpl
 * @generated
 */
@ProviderType
public class BrowserTrackerUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(BrowserTracker browserTracker) {
		getPersistence().clearCache(browserTracker);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BrowserTracker> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BrowserTracker> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BrowserTracker> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static BrowserTracker update(BrowserTracker browserTracker) {
		return getPersistence().update(browserTracker);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static BrowserTracker update(BrowserTracker browserTracker,
		ServiceContext serviceContext) {
		return getPersistence().update(browserTracker, serviceContext);
	}

	/**
	* Returns the browser tracker where userId = &#63; or throws a {@link com.liferay.portal.NoSuchBrowserTrackerException} if it could not be found.
	*
	* @param userId the user ID
	* @return the matching browser tracker
	* @throws com.liferay.portal.NoSuchBrowserTrackerException if a matching browser tracker could not be found
	*/
	public static com.liferay.portal.model.BrowserTracker findByUserId(
		long userId) throws com.liferay.portal.NoSuchBrowserTrackerException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns the browser tracker where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @return the matching browser tracker, or <code>null</code> if a matching browser tracker could not be found
	*/
	public static com.liferay.portal.model.BrowserTracker fetchByUserId(
		long userId) {
		return getPersistence().fetchByUserId(userId);
	}

	/**
	* Returns the browser tracker where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching browser tracker, or <code>null</code> if a matching browser tracker could not be found
	*/
	public static com.liferay.portal.model.BrowserTracker fetchByUserId(
		long userId, boolean retrieveFromCache) {
		return getPersistence().fetchByUserId(userId, retrieveFromCache);
	}

	/**
	* Removes the browser tracker where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @return the browser tracker that was removed
	*/
	public static com.liferay.portal.model.BrowserTracker removeByUserId(
		long userId) throws com.liferay.portal.NoSuchBrowserTrackerException {
		return getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of browser trackers where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching browser trackers
	*/
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Caches the browser tracker in the entity cache if it is enabled.
	*
	* @param browserTracker the browser tracker
	*/
	public static void cacheResult(
		com.liferay.portal.model.BrowserTracker browserTracker) {
		getPersistence().cacheResult(browserTracker);
	}

	/**
	* Caches the browser trackers in the entity cache if it is enabled.
	*
	* @param browserTrackers the browser trackers
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.BrowserTracker> browserTrackers) {
		getPersistence().cacheResult(browserTrackers);
	}

	/**
	* Creates a new browser tracker with the primary key. Does not add the browser tracker to the database.
	*
	* @param browserTrackerId the primary key for the new browser tracker
	* @return the new browser tracker
	*/
	public static com.liferay.portal.model.BrowserTracker create(
		long browserTrackerId) {
		return getPersistence().create(browserTrackerId);
	}

	/**
	* Removes the browser tracker with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param browserTrackerId the primary key of the browser tracker
	* @return the browser tracker that was removed
	* @throws com.liferay.portal.NoSuchBrowserTrackerException if a browser tracker with the primary key could not be found
	*/
	public static com.liferay.portal.model.BrowserTracker remove(
		long browserTrackerId)
		throws com.liferay.portal.NoSuchBrowserTrackerException {
		return getPersistence().remove(browserTrackerId);
	}

	public static com.liferay.portal.model.BrowserTracker updateImpl(
		com.liferay.portal.model.BrowserTracker browserTracker) {
		return getPersistence().updateImpl(browserTracker);
	}

	/**
	* Returns the browser tracker with the primary key or throws a {@link com.liferay.portal.NoSuchBrowserTrackerException} if it could not be found.
	*
	* @param browserTrackerId the primary key of the browser tracker
	* @return the browser tracker
	* @throws com.liferay.portal.NoSuchBrowserTrackerException if a browser tracker with the primary key could not be found
	*/
	public static com.liferay.portal.model.BrowserTracker findByPrimaryKey(
		long browserTrackerId)
		throws com.liferay.portal.NoSuchBrowserTrackerException {
		return getPersistence().findByPrimaryKey(browserTrackerId);
	}

	/**
	* Returns the browser tracker with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param browserTrackerId the primary key of the browser tracker
	* @return the browser tracker, or <code>null</code> if a browser tracker with the primary key could not be found
	*/
	public static com.liferay.portal.model.BrowserTracker fetchByPrimaryKey(
		long browserTrackerId) {
		return getPersistence().fetchByPrimaryKey(browserTrackerId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.BrowserTracker> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the browser trackers.
	*
	* @return the browser trackers
	*/
	public static java.util.List<com.liferay.portal.model.BrowserTracker> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the browser trackers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BrowserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of browser trackers
	* @param end the upper bound of the range of browser trackers (not inclusive)
	* @return the range of browser trackers
	*/
	public static java.util.List<com.liferay.portal.model.BrowserTracker> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the browser trackers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BrowserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of browser trackers
	* @param end the upper bound of the range of browser trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of browser trackers
	*/
	public static java.util.List<com.liferay.portal.model.BrowserTracker> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the browser trackers from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of browser trackers.
	*
	* @return the number of browser trackers
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static BrowserTrackerPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (BrowserTrackerPersistence)PortalBeanLocatorUtil.locate(BrowserTrackerPersistence.class.getName());

			ReferenceRegistry.registerReference(BrowserTrackerUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(BrowserTrackerPersistence persistence) {
	}

	private static BrowserTrackerPersistence _persistence;
}