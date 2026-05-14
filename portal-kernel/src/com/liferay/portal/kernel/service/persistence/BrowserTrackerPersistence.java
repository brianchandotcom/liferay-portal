/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchBrowserTrackerException;
import com.liferay.portal.kernel.model.BrowserTracker;

import org.osgi.annotation.versioning.ProviderType;

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
public interface BrowserTrackerPersistence
	extends BasePersistence<BrowserTracker> {

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
	 * Returns the browser tracker where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching browser tracker, or <code>null</code> if a matching browser tracker could not be found
	 */
	public BrowserTracker fetchByUserId(long userId, boolean useFinderCache);

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
	 * Returns the browser tracker where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching browser tracker, or <code>null</code> if a matching browser tracker could not be found
	 */
	public default BrowserTracker fetchByUserId(long userId) {
		return fetchByUserId(userId, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-680003162