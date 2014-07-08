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
import com.liferay.portal.model.PasswordTracker;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the password tracker service. This utility wraps {@link PasswordTrackerPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordTrackerPersistence
 * @see PasswordTrackerPersistenceImpl
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
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(PasswordTracker passwordTracker) {
		getPersistence().clearCache(passwordTracker);
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
	public static List<PasswordTracker> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PasswordTracker> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PasswordTracker> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static PasswordTracker update(PasswordTracker passwordTracker) {
		return getPersistence().update(passwordTracker);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
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
	public static java.util.List<com.liferay.portal.model.PasswordTracker> findByUserId(
		long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the password trackers where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of password trackers
	* @param end the upper bound of the range of password trackers (not inclusive)
	* @return the range of matching password trackers
	*/
	public static java.util.List<com.liferay.portal.model.PasswordTracker> findByUserId(
		long userId, int start, int end) {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the password trackers where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of password trackers
	* @param end the upper bound of the range of password trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching password trackers
	*/
	public static java.util.List<com.liferay.portal.model.PasswordTracker> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first password tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password tracker
	* @throws com.liferay.portal.NoSuchPasswordTrackerException if a matching password tracker could not be found
	*/
	public static com.liferay.portal.model.PasswordTracker findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPasswordTrackerException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first password tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password tracker, or <code>null</code> if a matching password tracker could not be found
	*/
	public static com.liferay.portal.model.PasswordTracker fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last password tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password tracker
	* @throws com.liferay.portal.NoSuchPasswordTrackerException if a matching password tracker could not be found
	*/
	public static com.liferay.portal.model.PasswordTracker findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPasswordTrackerException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last password tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password tracker, or <code>null</code> if a matching password tracker could not be found
	*/
	public static com.liferay.portal.model.PasswordTracker fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the password trackers before and after the current password tracker in the ordered set where userId = &#63;.
	*
	* @param passwordTrackerId the primary key of the current password tracker
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next password tracker
	* @throws com.liferay.portal.NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	*/
	public static com.liferay.portal.model.PasswordTracker[] findByUserId_PrevAndNext(
		long passwordTrackerId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPasswordTrackerException {
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
	public static void cacheResult(
		com.liferay.portal.model.PasswordTracker passwordTracker) {
		getPersistence().cacheResult(passwordTracker);
	}

	/**
	* Caches the password trackers in the entity cache if it is enabled.
	*
	* @param passwordTrackers the password trackers
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.PasswordTracker> passwordTrackers) {
		getPersistence().cacheResult(passwordTrackers);
	}

	/**
	* Creates a new password tracker with the primary key. Does not add the password tracker to the database.
	*
	* @param passwordTrackerId the primary key for the new password tracker
	* @return the new password tracker
	*/
	public static com.liferay.portal.model.PasswordTracker create(
		long passwordTrackerId) {
		return getPersistence().create(passwordTrackerId);
	}

	/**
	* Removes the password tracker with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param passwordTrackerId the primary key of the password tracker
	* @return the password tracker that was removed
	* @throws com.liferay.portal.NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	*/
	public static com.liferay.portal.model.PasswordTracker remove(
		long passwordTrackerId)
		throws com.liferay.portal.NoSuchPasswordTrackerException {
		return getPersistence().remove(passwordTrackerId);
	}

	public static com.liferay.portal.model.PasswordTracker updateImpl(
		com.liferay.portal.model.PasswordTracker passwordTracker) {
		return getPersistence().updateImpl(passwordTracker);
	}

	/**
	* Returns the password tracker with the primary key or throws a {@link com.liferay.portal.NoSuchPasswordTrackerException} if it could not be found.
	*
	* @param passwordTrackerId the primary key of the password tracker
	* @return the password tracker
	* @throws com.liferay.portal.NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	*/
	public static com.liferay.portal.model.PasswordTracker findByPrimaryKey(
		long passwordTrackerId)
		throws com.liferay.portal.NoSuchPasswordTrackerException {
		return getPersistence().findByPrimaryKey(passwordTrackerId);
	}

	/**
	* Returns the password tracker with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param passwordTrackerId the primary key of the password tracker
	* @return the password tracker, or <code>null</code> if a password tracker with the primary key could not be found
	*/
	public static com.liferay.portal.model.PasswordTracker fetchByPrimaryKey(
		long passwordTrackerId) {
		return getPersistence().fetchByPrimaryKey(passwordTrackerId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.PasswordTracker> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the password trackers.
	*
	* @return the password trackers
	*/
	public static java.util.List<com.liferay.portal.model.PasswordTracker> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the password trackers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of password trackers
	* @param end the upper bound of the range of password trackers (not inclusive)
	* @return the range of password trackers
	*/
	public static java.util.List<com.liferay.portal.model.PasswordTracker> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the password trackers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of password trackers
	* @param end the upper bound of the range of password trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of password trackers
	*/
	public static java.util.List<com.liferay.portal.model.PasswordTracker> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
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

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(PasswordTrackerPersistence persistence) {
	}

	private static PasswordTrackerPersistence _persistence;
}