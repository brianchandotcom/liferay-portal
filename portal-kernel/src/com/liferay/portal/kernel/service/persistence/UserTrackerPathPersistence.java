/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchUserTrackerPathException;
import com.liferay.portal.kernel.model.UserTrackerPath;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the user tracker path service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserTrackerPathUtil
 * @generated
 */
@ProviderType
public interface UserTrackerPathPersistence
	extends BasePersistence<UserTrackerPath> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserTrackerPathUtil} to access the user tracker path persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the user tracker paths where userTrackerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserTrackerPathModelImpl</code>.
	 * </p>
	 *
	 * @param userTrackerId the user tracker ID
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user tracker paths
	 */
	public java.util.List<UserTrackerPath> findByUserTrackerId(
		long userTrackerId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserTrackerPath>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first user tracker path in the ordered set where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker path
	 * @throws NoSuchUserTrackerPathException if a matching user tracker path could not be found
	 */
	public UserTrackerPath findByUserTrackerId_First(
			long userTrackerId,
			com.liferay.portal.kernel.util.OrderByComparator<UserTrackerPath>
				orderByComparator)
		throws NoSuchUserTrackerPathException;

	/**
	 * Returns the first user tracker path in the ordered set where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker path, or <code>null</code> if a matching user tracker path could not be found
	 */
	public UserTrackerPath fetchByUserTrackerId_First(
		long userTrackerId,
		com.liferay.portal.kernel.util.OrderByComparator<UserTrackerPath>
			orderByComparator);

	/**
	 * Removes all the user tracker paths where userTrackerId = &#63; from the database.
	 *
	 * @param userTrackerId the user tracker ID
	 */
	public void removeByUserTrackerId(long userTrackerId);

	/**
	 * Returns the number of user tracker paths where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @return the number of matching user tracker paths
	 */
	public int countByUserTrackerId(long userTrackerId);

	/**
	 * Creates a new user tracker path with the primary key. Does not add the user tracker path to the database.
	 *
	 * @param userTrackerPathId the primary key for the new user tracker path
	 * @return the new user tracker path
	 */
	public UserTrackerPath create(long userTrackerPathId);

	/**
	 * Removes the user tracker path with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userTrackerPathId the primary key of the user tracker path
	 * @return the user tracker path that was removed
	 * @throws NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	 */
	public UserTrackerPath remove(long userTrackerPathId)
		throws NoSuchUserTrackerPathException;

	public UserTrackerPath updateImpl(UserTrackerPath userTrackerPath);

	/**
	 * Returns the user tracker path with the primary key or throws a <code>NoSuchUserTrackerPathException</code> if it could not be found.
	 *
	 * @param userTrackerPathId the primary key of the user tracker path
	 * @return the user tracker path
	 * @throws NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	 */
	public UserTrackerPath findByPrimaryKey(long userTrackerPathId)
		throws NoSuchUserTrackerPathException;

	/**
	 * Returns the user tracker path with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userTrackerPathId the primary key of the user tracker path
	 * @return the user tracker path, or <code>null</code> if a user tracker path with the primary key could not be found
	 */
	public UserTrackerPath fetchByPrimaryKey(long userTrackerPathId);

	/**
	 * Returns all the user tracker paths where userTrackerId = &#63;.
	 *
	 * @param userTrackerId the user tracker ID
	 * @return the matching user tracker paths
	 */
	public default java.util.List<UserTrackerPath> findByUserTrackerId(
		long userTrackerId) {

		return findByUserTrackerId(
			userTrackerId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the user tracker paths where userTrackerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserTrackerPathModelImpl</code>.
	 * </p>
	 *
	 * @param userTrackerId the user tracker ID
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @return the range of matching user tracker paths
	 */
	public default java.util.List<UserTrackerPath> findByUserTrackerId(
		long userTrackerId, int start, int end) {

		return findByUserTrackerId(userTrackerId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the user tracker paths where userTrackerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserTrackerPathModelImpl</code>.
	 * </p>
	 *
	 * @param userTrackerId the user tracker ID
	 * @param start the lower bound of the range of user tracker paths
	 * @param end the upper bound of the range of user tracker paths (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user tracker paths
	 */
	public default java.util.List<UserTrackerPath> findByUserTrackerId(
		long userTrackerId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserTrackerPath>
			orderByComparator) {

		return findByUserTrackerId(
			userTrackerId, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-271931048