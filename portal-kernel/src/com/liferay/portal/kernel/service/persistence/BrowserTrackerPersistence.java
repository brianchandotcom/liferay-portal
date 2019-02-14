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

import com.liferay.portal.kernel.exception.NoSuchBrowserTrackerException;
import com.liferay.portal.kernel.model.BrowserTracker;

/**
 * The persistence interface for the browser tracker service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BrowserTrackerUtil
 * @generated
 */
@ProviderType
public interface BrowserTrackerPersistence extends BasePersistence<BrowserTracker> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BrowserTrackerUtil} to access the browser tracker persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the browser tracker where userId = &#63; or throws a <code>NoSuchBrowserTrackerException</code> if it could not be found.
	*
	* @param userId the user ID
	* @return the matching browser tracker
	* @throws NoSuchBrowserTrackerException if a matching browser tracker could not be found
	*/
	public BrowserTracker findByUserId(long userId)
		throws NoSuchBrowserTrackerException;

	/**
	* Returns the browser tracker where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @return the matching browser tracker, or <code>null</code> if a matching browser tracker could not be found
	*/
	public BrowserTracker fetchByUserId(long userId);

	/**
	* Returns the browser tracker where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching browser tracker, or <code>null</code> if a matching browser tracker could not be found
	*/
	public BrowserTracker fetchByUserId(long userId, boolean retrieveFromCache);

	/**
	* Removes the browser tracker where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @return the browser tracker that was removed
	*/
	public BrowserTracker removeByUserId(long userId)
		throws NoSuchBrowserTrackerException;

	/**
	* Returns the number of browser trackers where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching browser trackers
	*/
	public int countByUserId(long userId);

	/**
	* Caches the browser tracker in the entity cache if it is enabled.
	*
	* @param browserTracker the browser tracker
	*/
	public void cacheResult(BrowserTracker browserTracker);

	/**
	* Caches the browser trackers in the entity cache if it is enabled.
	*
	* @param browserTrackers the browser trackers
	*/
	public void cacheResult(java.util.List<BrowserTracker> browserTrackers);

	/**
	* Creates a new browser tracker with the primary key. Does not add the browser tracker to the database.
	*
	* @param browserTrackerId the primary key for the new browser tracker
	* @return the new browser tracker
	*/
	public BrowserTracker create(long browserTrackerId);

	/**
	* Removes the browser tracker with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param browserTrackerId the primary key of the browser tracker
	* @return the browser tracker that was removed
	* @throws NoSuchBrowserTrackerException if a browser tracker with the primary key could not be found
	*/
	public BrowserTracker remove(long browserTrackerId)
		throws NoSuchBrowserTrackerException;

	public BrowserTracker updateImpl(BrowserTracker browserTracker);

	/**
	* Returns the browser tracker with the primary key or throws a <code>NoSuchBrowserTrackerException</code> if it could not be found.
	*
	* @param browserTrackerId the primary key of the browser tracker
	* @return the browser tracker
	* @throws NoSuchBrowserTrackerException if a browser tracker with the primary key could not be found
	*/
	public BrowserTracker findByPrimaryKey(long browserTrackerId)
		throws NoSuchBrowserTrackerException;

	/**
	* Returns the browser tracker with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param browserTrackerId the primary key of the browser tracker
	* @return the browser tracker, or <code>null</code> if a browser tracker with the primary key could not be found
	*/
	public BrowserTracker fetchByPrimaryKey(long browserTrackerId);

	/**
	* Returns all the browser trackers.
	*
	* @return the browser trackers
	*/
	public java.util.List<BrowserTracker> findAll();

	/**
	* Returns a range of all the browser trackers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BrowserTrackerModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of browser trackers
	* @param end the upper bound of the range of browser trackers (not inclusive)
	* @return the range of browser trackers
	*/
	public java.util.List<BrowserTracker> findAll(int start, int end);

	/**
	* Returns an ordered range of all the browser trackers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BrowserTrackerModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of browser trackers
	* @param end the upper bound of the range of browser trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of browser trackers
	*/
	public java.util.List<BrowserTracker> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BrowserTracker> orderByComparator);

	/**
	* Returns an ordered range of all the browser trackers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BrowserTrackerModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of browser trackers
	* @param end the upper bound of the range of browser trackers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of browser trackers
	*/
	public java.util.List<BrowserTracker> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BrowserTracker> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the browser trackers from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of browser trackers.
	*
	* @return the number of browser trackers
	*/
	public int countAll();
}