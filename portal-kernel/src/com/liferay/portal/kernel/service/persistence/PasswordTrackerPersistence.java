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

import com.liferay.portal.kernel.exception.NoSuchPasswordTrackerException;
import com.liferay.portal.kernel.model.PasswordTracker;

/**
 * The persistence interface for the password tracker service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordTrackerUtil
 * @generated
 */
@ProviderType
public interface PasswordTrackerPersistence extends BasePersistence<PasswordTracker> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PasswordTrackerUtil} to access the password tracker persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the password trackers where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching password trackers
	*/
	public java.util.List<PasswordTracker> findByUserId(long userId);

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
	public java.util.List<PasswordTracker> findByUserId(long userId, int start,
		int end);

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
	public java.util.List<PasswordTracker> findByUserId(long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordTracker> orderByComparator);

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
	public java.util.List<PasswordTracker> findByUserId(long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordTracker> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first password tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password tracker
	* @throws NoSuchPasswordTrackerException if a matching password tracker could not be found
	*/
	public PasswordTracker findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordTracker> orderByComparator)
		throws NoSuchPasswordTrackerException;

	/**
	* Returns the first password tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password tracker, or <code>null</code> if a matching password tracker could not be found
	*/
	public PasswordTracker fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordTracker> orderByComparator);

	/**
	* Returns the last password tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password tracker
	* @throws NoSuchPasswordTrackerException if a matching password tracker could not be found
	*/
	public PasswordTracker findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordTracker> orderByComparator)
		throws NoSuchPasswordTrackerException;

	/**
	* Returns the last password tracker in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password tracker, or <code>null</code> if a matching password tracker could not be found
	*/
	public PasswordTracker fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordTracker> orderByComparator);

	/**
	* Returns the password trackers before and after the current password tracker in the ordered set where userId = &#63;.
	*
	* @param passwordTrackerId the primary key of the current password tracker
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next password tracker
	* @throws NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	*/
	public PasswordTracker[] findByUserId_PrevAndNext(long passwordTrackerId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordTracker> orderByComparator)
		throws NoSuchPasswordTrackerException;

	/**
	* Removes all the password trackers where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of password trackers where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching password trackers
	*/
	public int countByUserId(long userId);

	/**
	* Caches the password tracker in the entity cache if it is enabled.
	*
	* @param passwordTracker the password tracker
	*/
	public void cacheResult(PasswordTracker passwordTracker);

	/**
	* Caches the password trackers in the entity cache if it is enabled.
	*
	* @param passwordTrackers the password trackers
	*/
	public void cacheResult(java.util.List<PasswordTracker> passwordTrackers);

	/**
	* Creates a new password tracker with the primary key. Does not add the password tracker to the database.
	*
	* @param passwordTrackerId the primary key for the new password tracker
	* @return the new password tracker
	*/
	public PasswordTracker create(long passwordTrackerId);

	/**
	* Removes the password tracker with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param passwordTrackerId the primary key of the password tracker
	* @return the password tracker that was removed
	* @throws NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	*/
	public PasswordTracker remove(long passwordTrackerId)
		throws NoSuchPasswordTrackerException;

	public PasswordTracker updateImpl(PasswordTracker passwordTracker);

	/**
	* Returns the password tracker with the primary key or throws a <code>NoSuchPasswordTrackerException</code> if it could not be found.
	*
	* @param passwordTrackerId the primary key of the password tracker
	* @return the password tracker
	* @throws NoSuchPasswordTrackerException if a password tracker with the primary key could not be found
	*/
	public PasswordTracker findByPrimaryKey(long passwordTrackerId)
		throws NoSuchPasswordTrackerException;

	/**
	* Returns the password tracker with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param passwordTrackerId the primary key of the password tracker
	* @return the password tracker, or <code>null</code> if a password tracker with the primary key could not be found
	*/
	public PasswordTracker fetchByPrimaryKey(long passwordTrackerId);

	/**
	* Returns all the password trackers.
	*
	* @return the password trackers
	*/
	public java.util.List<PasswordTracker> findAll();

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
	public java.util.List<PasswordTracker> findAll(int start, int end);

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
	public java.util.List<PasswordTracker> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordTracker> orderByComparator);

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
	public java.util.List<PasswordTracker> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PasswordTracker> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the password trackers from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of password trackers.
	*
	* @return the number of password trackers
	*/
	public int countAll();
}