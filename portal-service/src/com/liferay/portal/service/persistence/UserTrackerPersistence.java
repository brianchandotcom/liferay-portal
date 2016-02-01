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

import com.liferay.portal.exception.NoSuchUserTrackerException;
import com.liferay.portal.model.UserTracker;

/**
 * The persistence interface for the user tracker service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.UserTrackerPersistenceImpl
 * @see UserTrackerUtil
 * @generated
 */
@ProviderType
public interface UserTrackerPersistence extends BasePersistence<UserTracker> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserTrackerUtil} to access the user tracker persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the user trackers where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching user trackers
	*/
	public java.util.List<UserTracker> findByCompanyId(long companyId);

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
	public java.util.List<UserTracker> findByCompanyId(long companyId,
		int start, int end);

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
	public java.util.List<UserTracker> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator);

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
	public java.util.List<UserTracker> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first user tracker in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker
	* @throws NoSuchUserTrackerException if a matching user tracker could not be found
	*/
	public UserTracker findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException;

	/**
	* Returns the first user tracker in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker, or <code>null</code> if a matching user tracker could not be found
	*/
	public UserTracker fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator);

	/**
	* Returns the last user tracker in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker
	* @throws NoSuchUserTrackerException if a matching user tracker could not be found
	*/
	public UserTracker findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException;

	/**
	* Returns the last user tracker in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker, or <code>null</code> if a matching user tracker could not be found
	*/
	public UserTracker fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator);

	/**
	* Returns the user trackers before and after the current user tracker in the ordered set where companyId = &#63;.
	*
	* @param userTrackerId the primary key of the current user tracker
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user tracker
	* @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	*/
	public UserTracker[] findByCompanyId_PrevAndNext(long userTrackerId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException;

	/**
	* Removes all the user trackers where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of user trackers where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching user trackers
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the user trackers where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching user trackers
	*/
	public java.util.List<UserTracker> findByUserId(long userId);

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
	public java.util.List<UserTracker> findByUserId(long userId, int start,
		int end);

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
	public java.util.List<UserTracker> findByUserId(long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator);

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
	public java.util.List<UserTracker> findByUserId(long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first user tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker
	* @throws NoSuchUserTrackerException if a matching user tracker could not be found
	*/
	public UserTracker findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException;

	/**
	* Returns the first user tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker, or <code>null</code> if a matching user tracker could not be found
	*/
	public UserTracker fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator);

	/**
	* Returns the last user tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker
	* @throws NoSuchUserTrackerException if a matching user tracker could not be found
	*/
	public UserTracker findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException;

	/**
	* Returns the last user tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker, or <code>null</code> if a matching user tracker could not be found
	*/
	public UserTracker fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator);

	/**
	* Returns the user trackers before and after the current user tracker in the ordered set where userId = &#63;.
	*
	* @param userTrackerId the primary key of the current user tracker
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user tracker
	* @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	*/
	public UserTracker[] findByUserId_PrevAndNext(long userTrackerId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException;

	/**
	* Removes all the user trackers where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of user trackers where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching user trackers
	*/
	public int countByUserId(long userId);

	/**
	* Returns all the user trackers where sessionId = &#63;.
	*
	* @param sessionId the session ID
	* @return the matching user trackers
	*/
	public java.util.List<UserTracker> findBySessionId(
		java.lang.String sessionId);

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
	public java.util.List<UserTracker> findBySessionId(
		java.lang.String sessionId, int start, int end);

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
	public java.util.List<UserTracker> findBySessionId(
		java.lang.String sessionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator);

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
	public java.util.List<UserTracker> findBySessionId(
		java.lang.String sessionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first user tracker in the ordered set where sessionId = &#63;.
	*
	* @param sessionId the session ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker
	* @throws NoSuchUserTrackerException if a matching user tracker could not be found
	*/
	public UserTracker findBySessionId_First(java.lang.String sessionId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException;

	/**
	* Returns the first user tracker in the ordered set where sessionId = &#63;.
	*
	* @param sessionId the session ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker, or <code>null</code> if a matching user tracker could not be found
	*/
	public UserTracker fetchBySessionId_First(java.lang.String sessionId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator);

	/**
	* Returns the last user tracker in the ordered set where sessionId = &#63;.
	*
	* @param sessionId the session ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker
	* @throws NoSuchUserTrackerException if a matching user tracker could not be found
	*/
	public UserTracker findBySessionId_Last(java.lang.String sessionId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException;

	/**
	* Returns the last user tracker in the ordered set where sessionId = &#63;.
	*
	* @param sessionId the session ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker, or <code>null</code> if a matching user tracker could not be found
	*/
	public UserTracker fetchBySessionId_Last(java.lang.String sessionId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator);

	/**
	* Returns the user trackers before and after the current user tracker in the ordered set where sessionId = &#63;.
	*
	* @param userTrackerId the primary key of the current user tracker
	* @param sessionId the session ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user tracker
	* @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	*/
	public UserTracker[] findBySessionId_PrevAndNext(long userTrackerId,
		java.lang.String sessionId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException;

	/**
	* Removes all the user trackers where sessionId = &#63; from the database.
	*
	* @param sessionId the session ID
	*/
	public void removeBySessionId(java.lang.String sessionId);

	/**
	* Returns the number of user trackers where sessionId = &#63;.
	*
	* @param sessionId the session ID
	* @return the number of matching user trackers
	*/
	public int countBySessionId(java.lang.String sessionId);

	/**
	* Caches the user tracker in the entity cache if it is enabled.
	*
	* @param userTracker the user tracker
	*/
	public void cacheResult(UserTracker userTracker);

	/**
	* Caches the user trackers in the entity cache if it is enabled.
	*
	* @param userTrackers the user trackers
	*/
	public void cacheResult(java.util.List<UserTracker> userTrackers);

	/**
	* Creates a new user tracker with the primary key. Does not add the user tracker to the database.
	*
	* @param userTrackerId the primary key for the new user tracker
	* @return the new user tracker
	*/
	public UserTracker create(long userTrackerId);

	/**
	* Removes the user tracker with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userTrackerId the primary key of the user tracker
	* @return the user tracker that was removed
	* @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	*/
	public UserTracker remove(long userTrackerId)
		throws NoSuchUserTrackerException;

	public UserTracker updateImpl(UserTracker userTracker);

	/**
	* Returns the user tracker with the primary key or throws a {@link NoSuchUserTrackerException} if it could not be found.
	*
	* @param userTrackerId the primary key of the user tracker
	* @return the user tracker
	* @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	*/
	public UserTracker findByPrimaryKey(long userTrackerId)
		throws NoSuchUserTrackerException;

	/**
	* Returns the user tracker with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userTrackerId the primary key of the user tracker
	* @return the user tracker, or <code>null</code> if a user tracker with the primary key could not be found
	*/
	public UserTracker fetchByPrimaryKey(long userTrackerId);

	@Override
	public java.util.Map<java.io.Serializable, UserTracker> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the user trackers.
	*
	* @return the user trackers
	*/
	public java.util.List<UserTracker> findAll();

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
	public java.util.List<UserTracker> findAll(int start, int end);

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
	public java.util.List<UserTracker> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator);

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
	public java.util.List<UserTracker> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserTracker> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the user trackers from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of user trackers.
	*
	* @return the number of user trackers
	*/
	public int countAll();
}