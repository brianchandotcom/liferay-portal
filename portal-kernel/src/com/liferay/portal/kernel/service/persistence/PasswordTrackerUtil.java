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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.PasswordTracker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the password tracker service. This utility wraps <code>com.liferay.portal.service.persistence.impl.PasswordTrackerPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordTrackerPersistence
 * @generated
 */
@ProviderType
public class PasswordTrackerUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(PasswordTracker passwordTracker) {
		getPersistence().clearCache(passwordTracker);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, PasswordTracker> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<PasswordTracker> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PasswordTracker> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PasswordTracker> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<PasswordTracker> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static PasswordTracker update(PasswordTracker passwordTracker) {
		return getPersistence().update(passwordTracker);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static PasswordTracker update(PasswordTracker passwordTracker,
		ServiceContext serviceContext) {
		return getPersistence().update(passwordTracker, serviceContext);
	}

	/**
	* Returns all the password trackers where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching password trackers
	*/
	public static List<PasswordTracker> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the password trackers where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PasswordTrackerModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of password trackers
	* @param end the upper bound of the range of password trackers (not inclusive)
	* @return the range of matching password trackers
	*/
	public static List<PasswordTracker> findByUserId(long userId, int start,
		int end) {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the password trackers where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PasswordTrackerModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of password trackers
	* @param end the upper bound of the range of password trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching password trackers
	*/
	public static List<PasswordTracker> findByUserId(long userId, int start,
		int end, OrderByComparator<PasswordTracker> orderByComparator) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the password trackers where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PasswordTrackerModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of password trackers
	* @param end the upper bound of the range of password trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching password trackers
	*/
	public static List<PasswordTracker> findByUserId(long userId, int start,
		int end, OrderByComparator<PasswordTracker> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first password tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password tracker
	* @throws NoSuchPasswordTrackerException if a matching password tracker could not be found
	*/
	public static PasswordTracker findByUserId_First(long userId,
		OrderByComparator<PasswordTracker> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchPasswordTrackerException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first password tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password tracker, or <code>null</code> if a matching password tracker could not be found
	*/
	public static PasswordTracker fetchByUserId_First(long userId,
		OrderByComparator<PasswordTracker> orderByComparator) {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last password tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password tracker
	* @throws NoSuchPasswordTrackerException if a matching password tracker could not be found
	*/
	public static PasswordTracker findByUserId_Last(long userId,
		OrderByComparator<PasswordTracker> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchPasswordTrackerException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last password tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password tracker, or <code>null</code> if a matching password tracker could not be found
	*/
	public static PasswordTracker fetchByUserId_Last(long userId,
		OrderByComparator<PasswordTracker> orderByComparator) {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the password trackers before and after the current password tracker in the ordered set where userId = &#63;.
	*
	* @param passwordTrackerId the primary key of the current password tracker
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next password tracker
	* @throws NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	*/
	public static PasswordTracker[] findByUserId_PrevAndNext(
		long passwordTrackerId, long userId,
		OrderByComparator<PasswordTracker> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchPasswordTrackerException {
		return getPersistence()
				   .findByUserId_PrevAndNext(passwordTrackerId, userId,
			orderByComparator);
	}

	/**
	* Removes all the password trackers where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of password trackers where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching password trackers
	*/
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Caches the password tracker in the entity cache if it is enabled.
	*
	* @param passwordTracker the password tracker
	*/
	public static void cacheResult(PasswordTracker passwordTracker) {
		getPersistence().cacheResult(passwordTracker);
	}

	/**
	* Caches the password trackers in the entity cache if it is enabled.
	*
	* @param passwordTrackers the password trackers
	*/
	public static void cacheResult(List<PasswordTracker> passwordTrackers) {
		getPersistence().cacheResult(passwordTrackers);
	}

	/**
	* Creates a new password tracker with the primary key. Does not add the password tracker to the database.
	*
	* @param passwordTrackerId the primary key for the new password tracker
	* @return the new password tracker
	*/
	public static PasswordTracker create(long passwordTrackerId) {
		return getPersistence().create(passwordTrackerId);
	}

	/**
	* Removes the password tracker with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param passwordTrackerId the primary key of the password tracker
	* @return the password tracker that was removed
	* @throws NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	*/
	public static PasswordTracker remove(long passwordTrackerId)
		throws com.liferay.portal.kernel.exception.NoSuchPasswordTrackerException {
		return getPersistence().remove(passwordTrackerId);
	}

	public static PasswordTracker updateImpl(PasswordTracker passwordTracker) {
		return getPersistence().updateImpl(passwordTracker);
	}

	/**
	* Returns the password tracker with the primary key or throws a <code>NoSuchPasswordTrackerException</code> if it could not be found.
	*
	* @param passwordTrackerId the primary key of the password tracker
	* @return the password tracker
	* @throws NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	*/
	public static PasswordTracker findByPrimaryKey(long passwordTrackerId)
		throws com.liferay.portal.kernel.exception.NoSuchPasswordTrackerException {
		return getPersistence().findByPrimaryKey(passwordTrackerId);
	}

	/**
	* Returns the password tracker with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param passwordTrackerId the primary key of the password tracker
	* @return the password tracker, or <code>null</code> if a password tracker with the primary key could not be found
	*/
	public static PasswordTracker fetchByPrimaryKey(long passwordTrackerId) {
		return getPersistence().fetchByPrimaryKey(passwordTrackerId);
	}

	/**
	* Returns all the password trackers.
	*
	* @return the password trackers
	*/
	public static List<PasswordTracker> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the password trackers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PasswordTrackerModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of password trackers
	* @param end the upper bound of the range of password trackers (not inclusive)
	* @return the range of password trackers
	*/
	public static List<PasswordTracker> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the password trackers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PasswordTrackerModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of password trackers
	* @param end the upper bound of the range of password trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of password trackers
	*/
	public static List<PasswordTracker> findAll(int start, int end,
		OrderByComparator<PasswordTracker> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the password trackers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PasswordTrackerModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of password trackers
	* @param end the upper bound of the range of password trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of password trackers
	*/
	public static List<PasswordTracker> findAll(int start, int end,
		OrderByComparator<PasswordTracker> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the password trackers from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of password trackers.
	*
	* @return the number of password trackers
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static PasswordTrackerPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PasswordTrackerPersistence)PortalBeanLocatorUtil.locate(PasswordTrackerPersistence.class.getName());

			ReferenceRegistry.registerReference(PasswordTrackerUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static PasswordTrackerPersistence _persistence;
}