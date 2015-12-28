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
import com.liferay.portal.model.UserTracker;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the user tracker service. This utility wraps {@link com.liferay.portal.service.persistence.impl.UserTrackerPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserTrackerPersistence
 * @see com.liferay.portal.service.persistence.impl.UserTrackerPersistenceImpl
 * @generated
 */
@ProviderType
public class UserTrackerUtil {
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
	public static void clearCache(UserTracker userTracker) {
		getPersistence().clearCache(userTracker);
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
	public static List<UserTracker> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<UserTracker> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<UserTracker> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<UserTracker> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static UserTracker update(UserTracker userTracker) {
		return getPersistence().update(userTracker);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static UserTracker update(UserTracker userTracker,
		ServiceContext serviceContext) {
		return getPersistence().update(userTracker, serviceContext);
	}

	/**
	* Returns all the user trackers where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching user trackers
	*/
	public static List<UserTracker> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the user trackers where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of user trackers
	* @param end the upper bound of the range of user trackers (not inclusive)
	* @return the range of matching user trackers
	*/
	public static List<UserTracker> findByCompanyId(long companyId, int start,
		int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the user trackers where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of user trackers
	* @param end the upper bound of the range of user trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user trackers
	*/
	public static List<UserTracker> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<UserTracker> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the user trackers where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of user trackers
	* @param end the upper bound of the range of user trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user trackers
	*/
	public static List<UserTracker> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<UserTracker> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first user tracker in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker
	* @throws NoSuchUserTrackerException if a matching user tracker could not be found
	*/
	public static UserTracker findByCompanyId_First(long companyId,
		OrderByComparator<UserTracker> orderByComparator)
		throws com.liferay.portal.NoSuchUserTrackerException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first user tracker in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker, or <code>null</code> if a matching user tracker could not be found
	*/
	public static UserTracker fetchByCompanyId_First(long companyId,
		OrderByComparator<UserTracker> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last user tracker in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker
	* @throws NoSuchUserTrackerException if a matching user tracker could not be found
	*/
	public static UserTracker findByCompanyId_Last(long companyId,
		OrderByComparator<UserTracker> orderByComparator)
		throws com.liferay.portal.NoSuchUserTrackerException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last user tracker in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker, or <code>null</code> if a matching user tracker could not be found
	*/
	public static UserTracker fetchByCompanyId_Last(long companyId,
		OrderByComparator<UserTracker> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the user trackers before and after the current user tracker in the ordered set where companyId = &#63;.
	*
	* @param userTrackerId the primary key of the current user tracker
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user tracker
	* @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	*/
	public static UserTracker[] findByCompanyId_PrevAndNext(
		long userTrackerId, long companyId,
		OrderByComparator<UserTracker> orderByComparator)
		throws com.liferay.portal.NoSuchUserTrackerException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(userTrackerId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the user trackers where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of user trackers where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching user trackers
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the user trackers where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching user trackers
	*/
	public static List<UserTracker> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the user trackers where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user trackers
	* @param end the upper bound of the range of user trackers (not inclusive)
	* @return the range of matching user trackers
	*/
	public static List<UserTracker> findByUserId(long userId, int start, int end) {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the user trackers where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user trackers
	* @param end the upper bound of the range of user trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user trackers
	*/
	public static List<UserTracker> findByUserId(long userId, int start,
		int end, OrderByComparator<UserTracker> orderByComparator) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the user trackers where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user trackers
	* @param end the upper bound of the range of user trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user trackers
	*/
	public static List<UserTracker> findByUserId(long userId, int start,
		int end, OrderByComparator<UserTracker> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first user tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker
	* @throws NoSuchUserTrackerException if a matching user tracker could not be found
	*/
	public static UserTracker findByUserId_First(long userId,
		OrderByComparator<UserTracker> orderByComparator)
		throws com.liferay.portal.NoSuchUserTrackerException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first user tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker, or <code>null</code> if a matching user tracker could not be found
	*/
	public static UserTracker fetchByUserId_First(long userId,
		OrderByComparator<UserTracker> orderByComparator) {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last user tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker
	* @throws NoSuchUserTrackerException if a matching user tracker could not be found
	*/
	public static UserTracker findByUserId_Last(long userId,
		OrderByComparator<UserTracker> orderByComparator)
		throws com.liferay.portal.NoSuchUserTrackerException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last user tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker, or <code>null</code> if a matching user tracker could not be found
	*/
	public static UserTracker fetchByUserId_Last(long userId,
		OrderByComparator<UserTracker> orderByComparator) {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the user trackers before and after the current user tracker in the ordered set where userId = &#63;.
	*
	* @param userTrackerId the primary key of the current user tracker
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user tracker
	* @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	*/
	public static UserTracker[] findByUserId_PrevAndNext(long userTrackerId,
		long userId, OrderByComparator<UserTracker> orderByComparator)
		throws com.liferay.portal.NoSuchUserTrackerException {
		return getPersistence()
				   .findByUserId_PrevAndNext(userTrackerId, userId,
			orderByComparator);
	}

	/**
	* Removes all the user trackers where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of user trackers where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching user trackers
	*/
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns all the user trackers where sessionId = &#63;.
	*
	* @param sessionId the session ID
	* @return the matching user trackers
	*/
	public static List<UserTracker> findBySessionId(java.lang.String sessionId) {
		return getPersistence().findBySessionId(sessionId);
	}

	/**
	* Returns a range of all the user trackers where sessionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param sessionId the session ID
	* @param start the lower bound of the range of user trackers
	* @param end the upper bound of the range of user trackers (not inclusive)
	* @return the range of matching user trackers
	*/
	public static List<UserTracker> findBySessionId(
		java.lang.String sessionId, int start, int end) {
		return getPersistence().findBySessionId(sessionId, start, end);
	}

	/**
	* Returns an ordered range of all the user trackers where sessionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param sessionId the session ID
	* @param start the lower bound of the range of user trackers
	* @param end the upper bound of the range of user trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user trackers
	*/
	public static List<UserTracker> findBySessionId(
		java.lang.String sessionId, int start, int end,
		OrderByComparator<UserTracker> orderByComparator) {
		return getPersistence()
				   .findBySessionId(sessionId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the user trackers where sessionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param sessionId the session ID
	* @param start the lower bound of the range of user trackers
	* @param end the upper bound of the range of user trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user trackers
	*/
	public static List<UserTracker> findBySessionId(
		java.lang.String sessionId, int start, int end,
		OrderByComparator<UserTracker> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findBySessionId(sessionId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first user tracker in the ordered set where sessionId = &#63;.
	*
	* @param sessionId the session ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker
	* @throws NoSuchUserTrackerException if a matching user tracker could not be found
	*/
	public static UserTracker findBySessionId_First(
		java.lang.String sessionId,
		OrderByComparator<UserTracker> orderByComparator)
		throws com.liferay.portal.NoSuchUserTrackerException {
		return getPersistence()
				   .findBySessionId_First(sessionId, orderByComparator);
	}

	/**
	* Returns the first user tracker in the ordered set where sessionId = &#63;.
	*
	* @param sessionId the session ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker, or <code>null</code> if a matching user tracker could not be found
	*/
	public static UserTracker fetchBySessionId_First(
		java.lang.String sessionId,
		OrderByComparator<UserTracker> orderByComparator) {
		return getPersistence()
				   .fetchBySessionId_First(sessionId, orderByComparator);
	}

	/**
	* Returns the last user tracker in the ordered set where sessionId = &#63;.
	*
	* @param sessionId the session ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker
	* @throws NoSuchUserTrackerException if a matching user tracker could not be found
	*/
	public static UserTracker findBySessionId_Last(java.lang.String sessionId,
		OrderByComparator<UserTracker> orderByComparator)
		throws com.liferay.portal.NoSuchUserTrackerException {
		return getPersistence()
				   .findBySessionId_Last(sessionId, orderByComparator);
	}

	/**
	* Returns the last user tracker in the ordered set where sessionId = &#63;.
	*
	* @param sessionId the session ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker, or <code>null</code> if a matching user tracker could not be found
	*/
	public static UserTracker fetchBySessionId_Last(
		java.lang.String sessionId,
		OrderByComparator<UserTracker> orderByComparator) {
		return getPersistence()
				   .fetchBySessionId_Last(sessionId, orderByComparator);
	}

	/**
	* Returns the user trackers before and after the current user tracker in the ordered set where sessionId = &#63;.
	*
	* @param userTrackerId the primary key of the current user tracker
	* @param sessionId the session ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user tracker
	* @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	*/
	public static UserTracker[] findBySessionId_PrevAndNext(
		long userTrackerId, java.lang.String sessionId,
		OrderByComparator<UserTracker> orderByComparator)
		throws com.liferay.portal.NoSuchUserTrackerException {
		return getPersistence()
				   .findBySessionId_PrevAndNext(userTrackerId, sessionId,
			orderByComparator);
	}

	/**
	* Removes all the user trackers where sessionId = &#63; from the database.
	*
	* @param sessionId the session ID
	*/
	public static void removeBySessionId(java.lang.String sessionId) {
		getPersistence().removeBySessionId(sessionId);
	}

	/**
	* Returns the number of user trackers where sessionId = &#63;.
	*
	* @param sessionId the session ID
	* @return the number of matching user trackers
	*/
	public static int countBySessionId(java.lang.String sessionId) {
		return getPersistence().countBySessionId(sessionId);
	}

	/**
	* Caches the user tracker in the entity cache if it is enabled.
	*
	* @param userTracker the user tracker
	*/
	public static void cacheResult(UserTracker userTracker) {
		getPersistence().cacheResult(userTracker);
	}

	/**
	* Caches the user trackers in the entity cache if it is enabled.
	*
	* @param userTrackers the user trackers
	*/
	public static void cacheResult(List<UserTracker> userTrackers) {
		getPersistence().cacheResult(userTrackers);
	}

	/**
	* Creates a new user tracker with the primary key. Does not add the user tracker to the database.
	*
	* @param userTrackerId the primary key for the new user tracker
	* @return the new user tracker
	*/
	public static UserTracker create(long userTrackerId) {
		return getPersistence().create(userTrackerId);
	}

	/**
	* Removes the user tracker with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userTrackerId the primary key of the user tracker
	* @return the user tracker that was removed
	* @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	*/
	public static UserTracker remove(long userTrackerId)
		throws com.liferay.portal.NoSuchUserTrackerException {
		return getPersistence().remove(userTrackerId);
	}

	public static UserTracker updateImpl(UserTracker userTracker) {
		return getPersistence().updateImpl(userTracker);
	}

	/**
	* Returns the user tracker with the primary key or throws a {@link NoSuchUserTrackerException} if it could not be found.
	*
	* @param userTrackerId the primary key of the user tracker
	* @return the user tracker
	* @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	*/
	public static UserTracker findByPrimaryKey(long userTrackerId)
		throws com.liferay.portal.NoSuchUserTrackerException {
		return getPersistence().findByPrimaryKey(userTrackerId);
	}

	/**
	* Returns the user tracker with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userTrackerId the primary key of the user tracker
	* @return the user tracker, or <code>null</code> if a user tracker with the primary key could not be found
	*/
	public static UserTracker fetchByPrimaryKey(long userTrackerId) {
		return getPersistence().fetchByPrimaryKey(userTrackerId);
	}

	public static java.util.Map<java.io.Serializable, UserTracker> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the user trackers.
	*
	* @return the user trackers
	*/
	public static List<UserTracker> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the user trackers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user trackers
	* @param end the upper bound of the range of user trackers (not inclusive)
	* @return the range of user trackers
	*/
	public static List<UserTracker> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the user trackers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user trackers
	* @param end the upper bound of the range of user trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user trackers
	*/
	public static List<UserTracker> findAll(int start, int end,
		OrderByComparator<UserTracker> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the user trackers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user trackers
	* @param end the upper bound of the range of user trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of user trackers
	*/
	public static List<UserTracker> findAll(int start, int end,
		OrderByComparator<UserTracker> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the user trackers from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of user trackers.
	*
	* @return the number of user trackers
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static UserTrackerPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (UserTrackerPersistence)PortalBeanLocatorUtil.locate(UserTrackerPersistence.class.getName());

			ReferenceRegistry.registerReference(UserTrackerUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static UserTrackerPersistence _persistence;
}