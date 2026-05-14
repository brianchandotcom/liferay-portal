/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.push.notifications.exception.NoSuchDeviceException;
import com.liferay.push.notifications.model.PushNotificationsDevice;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the push notifications device service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Bruno Farache
 * @see PushNotificationsDeviceUtil
 * @generated
 */
@ProviderType
public interface PushNotificationsDevicePersistence
	extends BasePersistence<PushNotificationsDevice> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PushNotificationsDeviceUtil} to access the push notifications device persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the push notifications device where token = &#63; or throws a <code>NoSuchDeviceException</code> if it could not be found.
	 *
	 * @param token the token
	 * @return the matching push notifications device
	 * @throws NoSuchDeviceException if a matching push notifications device could not be found
	 */
	public PushNotificationsDevice findByToken(String token)
		throws NoSuchDeviceException;

	/**
	 * Returns the push notifications device where token = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param token the token
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching push notifications device, or <code>null</code> if a matching push notifications device could not be found
	 */
	public PushNotificationsDevice fetchByToken(
		String token, boolean useFinderCache);

	/**
	 * Removes the push notifications device where token = &#63; from the database.
	 *
	 * @param token the token
	 * @return the push notifications device that was removed
	 */
	public PushNotificationsDevice removeByToken(String token)
		throws NoSuchDeviceException;

	/**
	 * Returns the number of push notifications devices where token = &#63;.
	 *
	 * @param token the token
	 * @return the number of matching push notifications devices
	 */
	public int countByToken(String token);

	/**
	 * Returns an ordered range of all the push notifications devices where userId = &#63; and platform = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.push.notifications.model.impl.PushNotificationsDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 * @param start the lower bound of the range of push notifications devices
	 * @param end the upper bound of the range of push notifications devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching push notifications devices
	 */
	public java.util.List<PushNotificationsDevice> findByU_P(
		long userId, String platform, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<PushNotificationsDevice> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first push notifications device in the ordered set where userId = &#63; and platform = &#63;.
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching push notifications device
	 * @throws NoSuchDeviceException if a matching push notifications device could not be found
	 */
	public PushNotificationsDevice findByU_P_First(
			long userId, String platform,
			com.liferay.portal.kernel.util.OrderByComparator
				<PushNotificationsDevice> orderByComparator)
		throws NoSuchDeviceException;

	/**
	 * Returns the first push notifications device in the ordered set where userId = &#63; and platform = &#63;.
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching push notifications device, or <code>null</code> if a matching push notifications device could not be found
	 */
	public PushNotificationsDevice fetchByU_P_First(
		long userId, String platform,
		com.liferay.portal.kernel.util.OrderByComparator
			<PushNotificationsDevice> orderByComparator);

	/**
	 * Returns an ordered range of all the push notifications devices where userId = &#63; and platform = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.push.notifications.model.impl.PushNotificationsDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param userIds the user IDs
	 * @param platform the platform
	 * @param start the lower bound of the range of push notifications devices
	 * @param end the upper bound of the range of push notifications devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching push notifications devices
	 */
	public java.util.List<PushNotificationsDevice> findByU_P(
		long[] userIds, String platform, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<PushNotificationsDevice> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the push notifications devices where userId = &#63; and platform = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 */
	public void removeByU_P(long userId, String platform);

	/**
	 * Returns the number of push notifications devices where userId = &#63; and platform = &#63;.
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 * @return the number of matching push notifications devices
	 */
	public int countByU_P(long userId, String platform);

	/**
	 * Returns the number of push notifications devices where userId = any &#63; and platform = &#63;.
	 *
	 * @param userIds the user IDs
	 * @param platform the platform
	 * @return the number of matching push notifications devices
	 */
	public int countByU_P(long[] userIds, String platform);

	/**
	 * Creates a new push notifications device with the primary key. Does not add the push notifications device to the database.
	 *
	 * @param pushNotificationsDeviceId the primary key for the new push notifications device
	 * @return the new push notifications device
	 */
	public PushNotificationsDevice create(long pushNotificationsDeviceId);

	/**
	 * Removes the push notifications device with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param pushNotificationsDeviceId the primary key of the push notifications device
	 * @return the push notifications device that was removed
	 * @throws NoSuchDeviceException if a push notifications device with the primary key could not be found
	 */
	public PushNotificationsDevice remove(long pushNotificationsDeviceId)
		throws NoSuchDeviceException;

	public PushNotificationsDevice updateImpl(
		PushNotificationsDevice pushNotificationsDevice);

	/**
	 * Returns the push notifications device with the primary key or throws a <code>NoSuchDeviceException</code> if it could not be found.
	 *
	 * @param pushNotificationsDeviceId the primary key of the push notifications device
	 * @return the push notifications device
	 * @throws NoSuchDeviceException if a push notifications device with the primary key could not be found
	 */
	public PushNotificationsDevice findByPrimaryKey(
			long pushNotificationsDeviceId)
		throws NoSuchDeviceException;

	/**
	 * Returns the push notifications device with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param pushNotificationsDeviceId the primary key of the push notifications device
	 * @return the push notifications device, or <code>null</code> if a push notifications device with the primary key could not be found
	 */
	public PushNotificationsDevice fetchByPrimaryKey(
		long pushNotificationsDeviceId);

	/**
	 * Returns the push notifications device where token = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param token the token
	 * @return the matching push notifications device, or <code>null</code> if a matching push notifications device could not be found
	 */
	public default PushNotificationsDevice fetchByToken(String token) {
		return fetchByToken(token, true);
	}

	/**
	 * Returns all the push notifications devices where userId = &#63; and platform = &#63;.
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 * @return the matching push notifications devices
	 */
	public default java.util.List<PushNotificationsDevice> findByU_P(
		long userId, String platform) {

		return findByU_P(
			userId, platform,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the push notifications devices where userId = &#63; and platform = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.push.notifications.model.impl.PushNotificationsDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 * @param start the lower bound of the range of push notifications devices
	 * @param end the upper bound of the range of push notifications devices (not inclusive)
	 * @return the range of matching push notifications devices
	 */
	public default java.util.List<PushNotificationsDevice> findByU_P(
		long userId, String platform, int start, int end) {

		return findByU_P(userId, platform, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the push notifications devices where userId = &#63; and platform = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.push.notifications.model.impl.PushNotificationsDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 * @param start the lower bound of the range of push notifications devices
	 * @param end the upper bound of the range of push notifications devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching push notifications devices
	 */
	public default java.util.List<PushNotificationsDevice> findByU_P(
		long userId, String platform, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<PushNotificationsDevice> orderByComparator) {

		return findByU_P(userId, platform, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the push notifications devices where userId = any &#63; and platform = &#63;.
	 *
	 * @param userIds the user IDs
	 * @param platform the platform
	 * @return the matching push notifications devices
	 */
	public default java.util.List<PushNotificationsDevice> findByU_P(
		long[] userIds, String platform) {

		return findByU_P(
			userIds, platform,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the push notifications devices where userId = any &#63; and platform = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.push.notifications.model.impl.PushNotificationsDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param userIds the user IDs
	 * @param platform the platform
	 * @param start the lower bound of the range of push notifications devices
	 * @param end the upper bound of the range of push notifications devices (not inclusive)
	 * @return the range of matching push notifications devices
	 */
	public default java.util.List<PushNotificationsDevice> findByU_P(
		long[] userIds, String platform, int start, int end) {

		return findByU_P(userIds, platform, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the push notifications devices where userId = any &#63; and platform = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.push.notifications.model.impl.PushNotificationsDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param userIds the user IDs
	 * @param platform the platform
	 * @param start the lower bound of the range of push notifications devices
	 * @param end the upper bound of the range of push notifications devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching push notifications devices
	 */
	public default java.util.List<PushNotificationsDevice> findByU_P(
		long[] userIds, String platform, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<PushNotificationsDevice> orderByComparator) {

		return findByU_P(
			userIds, platform, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1437669335