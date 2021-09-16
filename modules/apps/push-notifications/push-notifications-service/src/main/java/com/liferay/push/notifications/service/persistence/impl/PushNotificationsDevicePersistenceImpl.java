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

package com.liferay.push.notifications.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.push.notifications.exception.NoSuchDeviceException;
import com.liferay.push.notifications.model.PushNotificationsDevice;
import com.liferay.push.notifications.model.PushNotificationsDeviceTable;
import com.liferay.push.notifications.model.impl.PushNotificationsDeviceImpl;
import com.liferay.push.notifications.model.impl.PushNotificationsDeviceModelImpl;
import com.liferay.push.notifications.service.persistence.PushNotificationsDevicePersistence;
import com.liferay.push.notifications.service.persistence.impl.constants.PushNotificationsPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the push notifications device service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Bruno Farache
 * @generated
 */
@Component(
	service = {PushNotificationsDevicePersistence.class, BasePersistence.class}
)
public class PushNotificationsDevicePersistenceImpl
	extends BasePersistenceImpl<PushNotificationsDevice>
	implements PushNotificationsDevicePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PushNotificationsDeviceUtil</code> to access the push notifications device persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PushNotificationsDeviceImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByToken;
	private FinderPath _finderPathCountByToken;

	/**
	 * Returns the push notifications device where token = &#63; or throws a <code>NoSuchDeviceException</code> if it could not be found.
	 *
	 * @param token the token
	 * @return the matching push notifications device
	 * @throws NoSuchDeviceException if a matching push notifications device could not be found
	 */
	@Override
	public PushNotificationsDevice findByToken(String token)
		throws NoSuchDeviceException {

		PushNotificationsDevice pushNotificationsDevice = fetchByToken(token);

		if (pushNotificationsDevice == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("token=");
			sb.append(token);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDeviceException(sb.toString());
		}

		return pushNotificationsDevice;
	}

	/**
	 * Returns the push notifications device where token = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param token the token
	 * @return the matching push notifications device, or <code>null</code> if a matching push notifications device could not be found
	 */
	@Override
	public PushNotificationsDevice fetchByToken(String token) {
		return fetchByToken(token, true);
	}

	/**
	 * Returns the push notifications device where token = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param token the token
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching push notifications device, or <code>null</code> if a matching push notifications device could not be found
	 */
	@Override
	public PushNotificationsDevice fetchByToken(
		String token, boolean useFinderCache) {

		token = Objects.toString(token, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {token};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByToken, finderArgs);
		}

		if (result instanceof PushNotificationsDevice) {
			PushNotificationsDevice pushNotificationsDevice =
				(PushNotificationsDevice)result;

			if (!Objects.equals(token, pushNotificationsDevice.getToken())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_PUSHNOTIFICATIONSDEVICE_WHERE);

			boolean bindToken = false;

			if (token.isEmpty()) {
				sb.append(_FINDER_COLUMN_TOKEN_TOKEN_3);
			}
			else {
				bindToken = true;

				sb.append(_FINDER_COLUMN_TOKEN_TOKEN_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindToken) {
					queryPos.add(token);
				}

				List<PushNotificationsDevice> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByToken, finderArgs, list);
					}
				}
				else {
					PushNotificationsDevice pushNotificationsDevice = list.get(
						0);

					result = pushNotificationsDevice;

					cacheResult(pushNotificationsDevice);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (PushNotificationsDevice)result;
		}
	}

	/**
	 * Removes the push notifications device where token = &#63; from the database.
	 *
	 * @param token the token
	 * @return the push notifications device that was removed
	 */
	@Override
	public PushNotificationsDevice removeByToken(String token)
		throws NoSuchDeviceException {

		PushNotificationsDevice pushNotificationsDevice = findByToken(token);

		return remove(pushNotificationsDevice);
	}

	/**
	 * Returns the number of push notifications devices where token = &#63;.
	 *
	 * @param token the token
	 * @return the number of matching push notifications devices
	 */
	@Override
	public int countByToken(String token) {
		token = Objects.toString(token, "");

		FinderPath finderPath = _finderPathCountByToken;

		Object[] finderArgs = new Object[] {token};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_PUSHNOTIFICATIONSDEVICE_WHERE);

			boolean bindToken = false;

			if (token.isEmpty()) {
				sb.append(_FINDER_COLUMN_TOKEN_TOKEN_3);
			}
			else {
				bindToken = true;

				sb.append(_FINDER_COLUMN_TOKEN_TOKEN_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindToken) {
					queryPos.add(token);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_TOKEN_TOKEN_2 =
		"pushNotificationsDevice.token = ?";

	private static final String _FINDER_COLUMN_TOKEN_TOKEN_3 =
		"(pushNotificationsDevice.token IS NULL OR pushNotificationsDevice.token = '')";

	private FinderPath _finderPathWithPaginationFindByU_P;
	private FinderPath _finderPathWithoutPaginationFindByU_P;
	private FinderPath _finderPathCountByU_P;
	private FinderPath _finderPathWithPaginationCountByU_P;

	/**
	 * Returns all the push notifications devices where userId = &#63; and platform = &#63;.
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 * @return the matching push notifications devices
	 */
	@Override
	public List<PushNotificationsDevice> findByU_P(
		long userId, String platform) {

		return findByU_P(
			userId, platform, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the push notifications devices where userId = &#63; and platform = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PushNotificationsDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 * @param start the lower bound of the range of push notifications devices
	 * @param end the upper bound of the range of push notifications devices (not inclusive)
	 * @return the range of matching push notifications devices
	 */
	@Override
	public List<PushNotificationsDevice> findByU_P(
		long userId, String platform, int start, int end) {

		return findByU_P(userId, platform, start, end, null);
	}

	/**
	 * Returns an ordered range of all the push notifications devices where userId = &#63; and platform = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PushNotificationsDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 * @param start the lower bound of the range of push notifications devices
	 * @param end the upper bound of the range of push notifications devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching push notifications devices
	 */
	@Override
	public List<PushNotificationsDevice> findByU_P(
		long userId, String platform, int start, int end,
		OrderByComparator<PushNotificationsDevice> orderByComparator) {

		return findByU_P(userId, platform, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the push notifications devices where userId = &#63; and platform = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PushNotificationsDeviceModelImpl</code>.
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
	@Override
	public List<PushNotificationsDevice> findByU_P(
		long userId, String platform, int start, int end,
		OrderByComparator<PushNotificationsDevice> orderByComparator,
		boolean useFinderCache) {

		platform = Objects.toString(platform, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_P;
				finderArgs = new Object[] {userId, platform};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_P;
			finderArgs = new Object[] {
				userId, platform, start, end, orderByComparator
			};
		}

		List<PushNotificationsDevice> list = null;

		if (useFinderCache) {
			list = (List<PushNotificationsDevice>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (PushNotificationsDevice pushNotificationsDevice : list) {
					if ((userId != pushNotificationsDevice.getUserId()) ||
						!platform.equals(
							pushNotificationsDevice.getPlatform())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_PUSHNOTIFICATIONSDEVICE_WHERE);

			sb.append(_FINDER_COLUMN_U_P_USERID_2);

			boolean bindPlatform = false;

			if (platform.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_P_PLATFORM_3);
			}
			else {
				bindPlatform = true;

				sb.append(_FINDER_COLUMN_U_P_PLATFORM_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PushNotificationsDeviceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindPlatform) {
					queryPos.add(platform);
				}

				list = (List<PushNotificationsDevice>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first push notifications device in the ordered set where userId = &#63; and platform = &#63;.
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching push notifications device
	 * @throws NoSuchDeviceException if a matching push notifications device could not be found
	 */
	@Override
	public PushNotificationsDevice findByU_P_First(
			long userId, String platform,
			OrderByComparator<PushNotificationsDevice> orderByComparator)
		throws NoSuchDeviceException {

		PushNotificationsDevice pushNotificationsDevice = fetchByU_P_First(
			userId, platform, orderByComparator);

		if (pushNotificationsDevice != null) {
			return pushNotificationsDevice;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", platform=");
		sb.append(platform);

		sb.append("}");

		throw new NoSuchDeviceException(sb.toString());
	}

	/**
	 * Returns the first push notifications device in the ordered set where userId = &#63; and platform = &#63;.
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching push notifications device, or <code>null</code> if a matching push notifications device could not be found
	 */
	@Override
	public PushNotificationsDevice fetchByU_P_First(
		long userId, String platform,
		OrderByComparator<PushNotificationsDevice> orderByComparator) {

		List<PushNotificationsDevice> list = findByU_P(
			userId, platform, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last push notifications device in the ordered set where userId = &#63; and platform = &#63;.
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching push notifications device
	 * @throws NoSuchDeviceException if a matching push notifications device could not be found
	 */
	@Override
	public PushNotificationsDevice findByU_P_Last(
			long userId, String platform,
			OrderByComparator<PushNotificationsDevice> orderByComparator)
		throws NoSuchDeviceException {

		PushNotificationsDevice pushNotificationsDevice = fetchByU_P_Last(
			userId, platform, orderByComparator);

		if (pushNotificationsDevice != null) {
			return pushNotificationsDevice;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", platform=");
		sb.append(platform);

		sb.append("}");

		throw new NoSuchDeviceException(sb.toString());
	}

	/**
	 * Returns the last push notifications device in the ordered set where userId = &#63; and platform = &#63;.
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching push notifications device, or <code>null</code> if a matching push notifications device could not be found
	 */
	@Override
	public PushNotificationsDevice fetchByU_P_Last(
		long userId, String platform,
		OrderByComparator<PushNotificationsDevice> orderByComparator) {

		int count = countByU_P(userId, platform);

		if (count == 0) {
			return null;
		}

		List<PushNotificationsDevice> list = findByU_P(
			userId, platform, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the push notifications devices before and after the current push notifications device in the ordered set where userId = &#63; and platform = &#63;.
	 *
	 * @param pushNotificationsDeviceId the primary key of the current push notifications device
	 * @param userId the user ID
	 * @param platform the platform
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next push notifications device
	 * @throws NoSuchDeviceException if a push notifications device with the primary key could not be found
	 */
	@Override
	public PushNotificationsDevice[] findByU_P_PrevAndNext(
			long pushNotificationsDeviceId, long userId, String platform,
			OrderByComparator<PushNotificationsDevice> orderByComparator)
		throws NoSuchDeviceException {

		platform = Objects.toString(platform, "");

		PushNotificationsDevice pushNotificationsDevice = findByPrimaryKey(
			pushNotificationsDeviceId);

		Session session = null;

		try {
			session = openSession();

			PushNotificationsDevice[] array =
				new PushNotificationsDeviceImpl[3];

			array[0] = getByU_P_PrevAndNext(
				session, pushNotificationsDevice, userId, platform,
				orderByComparator, true);

			array[1] = pushNotificationsDevice;

			array[2] = getByU_P_PrevAndNext(
				session, pushNotificationsDevice, userId, platform,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected PushNotificationsDevice getByU_P_PrevAndNext(
		Session session, PushNotificationsDevice pushNotificationsDevice,
		long userId, String platform,
		OrderByComparator<PushNotificationsDevice> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_PUSHNOTIFICATIONSDEVICE_WHERE);

		sb.append(_FINDER_COLUMN_U_P_USERID_2);

		boolean bindPlatform = false;

		if (platform.isEmpty()) {
			sb.append(_FINDER_COLUMN_U_P_PLATFORM_3);
		}
		else {
			bindPlatform = true;

			sb.append(_FINDER_COLUMN_U_P_PLATFORM_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(PushNotificationsDeviceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (bindPlatform) {
			queryPos.add(platform);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						pushNotificationsDevice)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PushNotificationsDevice> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the push notifications devices where userId = any &#63; and platform = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PushNotificationsDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param userIds the user IDs
	 * @param platform the platform
	 * @return the matching push notifications devices
	 */
	@Override
	public List<PushNotificationsDevice> findByU_P(
		long[] userIds, String platform) {

		return findByU_P(
			userIds, platform, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the push notifications devices where userId = any &#63; and platform = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PushNotificationsDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param userIds the user IDs
	 * @param platform the platform
	 * @param start the lower bound of the range of push notifications devices
	 * @param end the upper bound of the range of push notifications devices (not inclusive)
	 * @return the range of matching push notifications devices
	 */
	@Override
	public List<PushNotificationsDevice> findByU_P(
		long[] userIds, String platform, int start, int end) {

		return findByU_P(userIds, platform, start, end, null);
	}

	/**
	 * Returns an ordered range of all the push notifications devices where userId = any &#63; and platform = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PushNotificationsDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param userIds the user IDs
	 * @param platform the platform
	 * @param start the lower bound of the range of push notifications devices
	 * @param end the upper bound of the range of push notifications devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching push notifications devices
	 */
	@Override
	public List<PushNotificationsDevice> findByU_P(
		long[] userIds, String platform, int start, int end,
		OrderByComparator<PushNotificationsDevice> orderByComparator) {

		return findByU_P(
			userIds, platform, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the push notifications devices where userId = &#63; and platform = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PushNotificationsDeviceModelImpl</code>.
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
	@Override
	public List<PushNotificationsDevice> findByU_P(
		long[] userIds, String platform, int start, int end,
		OrderByComparator<PushNotificationsDevice> orderByComparator,
		boolean useFinderCache) {

		if (userIds == null) {
			userIds = new long[0];
		}
		else if (userIds.length > 1) {
			userIds = ArrayUtil.sortedUnique(userIds);
		}

		platform = Objects.toString(platform, "");

		if (userIds.length == 1) {
			return findByU_P(
				userIds[0], platform, start, end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {StringUtil.merge(userIds), platform};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(userIds), platform, start, end,
				orderByComparator
			};
		}

		List<PushNotificationsDevice> list = null;

		if (useFinderCache) {
			list = (List<PushNotificationsDevice>)finderCache.getResult(
				_finderPathWithPaginationFindByU_P, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (PushNotificationsDevice pushNotificationsDevice : list) {
					if (!ArrayUtil.contains(
							userIds, pushNotificationsDevice.getUserId()) ||
						!platform.equals(
							pushNotificationsDevice.getPlatform())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_PUSHNOTIFICATIONSDEVICE_WHERE);

			if (userIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_U_P_USERID_7);

				sb.append(StringUtil.merge(userIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindPlatform = false;

			if (platform.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_P_PLATFORM_3);
			}
			else {
				bindPlatform = true;

				sb.append(_FINDER_COLUMN_U_P_PLATFORM_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PushNotificationsDeviceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindPlatform) {
					queryPos.add(platform);
				}

				list = (List<PushNotificationsDevice>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByU_P, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the push notifications devices where userId = &#63; and platform = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 */
	@Override
	public void removeByU_P(long userId, String platform) {
		for (PushNotificationsDevice pushNotificationsDevice :
				findByU_P(
					userId, platform, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(pushNotificationsDevice);
		}
	}

	/**
	 * Returns the number of push notifications devices where userId = &#63; and platform = &#63;.
	 *
	 * @param userId the user ID
	 * @param platform the platform
	 * @return the number of matching push notifications devices
	 */
	@Override
	public int countByU_P(long userId, String platform) {
		platform = Objects.toString(platform, "");

		FinderPath finderPath = _finderPathCountByU_P;

		Object[] finderArgs = new Object[] {userId, platform};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_PUSHNOTIFICATIONSDEVICE_WHERE);

			sb.append(_FINDER_COLUMN_U_P_USERID_2);

			boolean bindPlatform = false;

			if (platform.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_P_PLATFORM_3);
			}
			else {
				bindPlatform = true;

				sb.append(_FINDER_COLUMN_U_P_PLATFORM_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindPlatform) {
					queryPos.add(platform);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of push notifications devices where userId = any &#63; and platform = &#63;.
	 *
	 * @param userIds the user IDs
	 * @param platform the platform
	 * @return the number of matching push notifications devices
	 */
	@Override
	public int countByU_P(long[] userIds, String platform) {
		if (userIds == null) {
			userIds = new long[0];
		}
		else if (userIds.length > 1) {
			userIds = ArrayUtil.sortedUnique(userIds);
		}

		platform = Objects.toString(platform, "");

		Object[] finderArgs = new Object[] {
			StringUtil.merge(userIds), platform
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByU_P, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_PUSHNOTIFICATIONSDEVICE_WHERE);

			if (userIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_U_P_USERID_7);

				sb.append(StringUtil.merge(userIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindPlatform = false;

			if (platform.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_P_PLATFORM_3);
			}
			else {
				bindPlatform = true;

				sb.append(_FINDER_COLUMN_U_P_PLATFORM_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindPlatform) {
					queryPos.add(platform);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByU_P, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_U_P_USERID_2 =
		"pushNotificationsDevice.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_P_USERID_7 =
		"pushNotificationsDevice.userId IN (";

	private static final String _FINDER_COLUMN_U_P_PLATFORM_2 =
		"pushNotificationsDevice.platform = ?";

	private static final String _FINDER_COLUMN_U_P_PLATFORM_3 =
		"(pushNotificationsDevice.platform IS NULL OR pushNotificationsDevice.platform = '')";

	public PushNotificationsDevicePersistenceImpl() {
		setModelClass(PushNotificationsDevice.class);

		setModelImplClass(PushNotificationsDeviceImpl.class);
		setModelPKClass(long.class);

		setTable(PushNotificationsDeviceTable.INSTANCE);
	}

	/**
	 * Caches the push notifications device in the entity cache if it is enabled.
	 *
	 * @param pushNotificationsDevice the push notifications device
	 */
	@Override
	public void cacheResult(PushNotificationsDevice pushNotificationsDevice) {
		entityCache.putResult(
			PushNotificationsDeviceImpl.class,
			pushNotificationsDevice.getPrimaryKey(), pushNotificationsDevice);

		finderCache.putResult(
			_finderPathFetchByToken,
			new Object[] {pushNotificationsDevice.getToken()},
			pushNotificationsDevice);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the push notifications devices in the entity cache if it is enabled.
	 *
	 * @param pushNotificationsDevices the push notifications devices
	 */
	@Override
	public void cacheResult(
		List<PushNotificationsDevice> pushNotificationsDevices) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (pushNotificationsDevices.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (PushNotificationsDevice pushNotificationsDevice :
				pushNotificationsDevices) {

			if (entityCache.getResult(
					PushNotificationsDeviceImpl.class,
					pushNotificationsDevice.getPrimaryKey()) == null) {

				cacheResult(pushNotificationsDevice);
			}
		}
	}

	/**
	 * Clears the cache for all push notifications devices.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(PushNotificationsDeviceImpl.class);

		finderCache.clearCache(PushNotificationsDeviceImpl.class);
	}

	/**
	 * Clears the cache for the push notifications device.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PushNotificationsDevice pushNotificationsDevice) {
		entityCache.removeResult(
			PushNotificationsDeviceImpl.class, pushNotificationsDevice);
	}

	@Override
	public void clearCache(
		List<PushNotificationsDevice> pushNotificationsDevices) {

		for (PushNotificationsDevice pushNotificationsDevice :
				pushNotificationsDevices) {

			entityCache.removeResult(
				PushNotificationsDeviceImpl.class, pushNotificationsDevice);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(PushNotificationsDeviceImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				PushNotificationsDeviceImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		PushNotificationsDeviceModelImpl pushNotificationsDeviceModelImpl) {

		Object[] args = new Object[] {
			pushNotificationsDeviceModelImpl.getToken()
		};

		finderCache.putResult(_finderPathCountByToken, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByToken, args, pushNotificationsDeviceModelImpl);
	}

	/**
	 * Creates a new push notifications device with the primary key. Does not add the push notifications device to the database.
	 *
	 * @param pushNotificationsDeviceId the primary key for the new push notifications device
	 * @return the new push notifications device
	 */
	@Override
	public PushNotificationsDevice create(long pushNotificationsDeviceId) {
		PushNotificationsDevice pushNotificationsDevice =
			new PushNotificationsDeviceImpl();

		pushNotificationsDevice.setNew(true);
		pushNotificationsDevice.setPrimaryKey(pushNotificationsDeviceId);

		pushNotificationsDevice.setCompanyId(CompanyThreadLocal.getCompanyId());

		return pushNotificationsDevice;
	}

	/**
	 * Removes the push notifications device with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param pushNotificationsDeviceId the primary key of the push notifications device
	 * @return the push notifications device that was removed
	 * @throws NoSuchDeviceException if a push notifications device with the primary key could not be found
	 */
	@Override
	public PushNotificationsDevice remove(long pushNotificationsDeviceId)
		throws NoSuchDeviceException {

		return remove((Serializable)pushNotificationsDeviceId);
	}

	/**
	 * Removes the push notifications device with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the push notifications device
	 * @return the push notifications device that was removed
	 * @throws NoSuchDeviceException if a push notifications device with the primary key could not be found
	 */
	@Override
	public PushNotificationsDevice remove(Serializable primaryKey)
		throws NoSuchDeviceException {

		Session session = null;

		try {
			session = openSession();

			PushNotificationsDevice pushNotificationsDevice =
				(PushNotificationsDevice)session.get(
					PushNotificationsDeviceImpl.class, primaryKey);

			if (pushNotificationsDevice == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDeviceException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(pushNotificationsDevice);
		}
		catch (NoSuchDeviceException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected PushNotificationsDevice removeImpl(
		PushNotificationsDevice pushNotificationsDevice) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(pushNotificationsDevice)) {
				pushNotificationsDevice = (PushNotificationsDevice)session.get(
					PushNotificationsDeviceImpl.class,
					pushNotificationsDevice.getPrimaryKeyObj());
			}

			if (pushNotificationsDevice != null) {
				session.delete(pushNotificationsDevice);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (pushNotificationsDevice != null) {
			clearCache(pushNotificationsDevice);
		}

		return pushNotificationsDevice;
	}

	@Override
	public PushNotificationsDevice updateImpl(
		PushNotificationsDevice pushNotificationsDevice) {

		boolean isNew = pushNotificationsDevice.isNew();

		if (!(pushNotificationsDevice instanceof
				PushNotificationsDeviceModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(pushNotificationsDevice.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					pushNotificationsDevice);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in pushNotificationsDevice proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PushNotificationsDevice implementation " +
					pushNotificationsDevice.getClass());
		}

		PushNotificationsDeviceModelImpl pushNotificationsDeviceModelImpl =
			(PushNotificationsDeviceModelImpl)pushNotificationsDevice;

		if (isNew && (pushNotificationsDevice.getCreateDate() == null)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				pushNotificationsDevice.setCreateDate(date);
			}
			else {
				pushNotificationsDevice.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(pushNotificationsDevice);
			}
			else {
				pushNotificationsDevice =
					(PushNotificationsDevice)session.merge(
						pushNotificationsDevice);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			PushNotificationsDeviceImpl.class, pushNotificationsDeviceModelImpl,
			false, true);

		cacheUniqueFindersCache(pushNotificationsDeviceModelImpl);

		if (isNew) {
			pushNotificationsDevice.setNew(false);
		}

		pushNotificationsDevice.resetOriginalValues();

		return pushNotificationsDevice;
	}

	/**
	 * Returns the push notifications device with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the push notifications device
	 * @return the push notifications device
	 * @throws NoSuchDeviceException if a push notifications device with the primary key could not be found
	 */
	@Override
	public PushNotificationsDevice findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDeviceException {

		PushNotificationsDevice pushNotificationsDevice = fetchByPrimaryKey(
			primaryKey);

		if (pushNotificationsDevice == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDeviceException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return pushNotificationsDevice;
	}

	/**
	 * Returns the push notifications device with the primary key or throws a <code>NoSuchDeviceException</code> if it could not be found.
	 *
	 * @param pushNotificationsDeviceId the primary key of the push notifications device
	 * @return the push notifications device
	 * @throws NoSuchDeviceException if a push notifications device with the primary key could not be found
	 */
	@Override
	public PushNotificationsDevice findByPrimaryKey(
			long pushNotificationsDeviceId)
		throws NoSuchDeviceException {

		return findByPrimaryKey((Serializable)pushNotificationsDeviceId);
	}

	/**
	 * Returns the push notifications device with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param pushNotificationsDeviceId the primary key of the push notifications device
	 * @return the push notifications device, or <code>null</code> if a push notifications device with the primary key could not be found
	 */
	@Override
	public PushNotificationsDevice fetchByPrimaryKey(
		long pushNotificationsDeviceId) {

		return fetchByPrimaryKey((Serializable)pushNotificationsDeviceId);
	}

	/**
	 * Returns all the push notifications devices.
	 *
	 * @return the push notifications devices
	 */
	@Override
	public List<PushNotificationsDevice> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the push notifications devices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PushNotificationsDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of push notifications devices
	 * @param end the upper bound of the range of push notifications devices (not inclusive)
	 * @return the range of push notifications devices
	 */
	@Override
	public List<PushNotificationsDevice> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the push notifications devices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PushNotificationsDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of push notifications devices
	 * @param end the upper bound of the range of push notifications devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of push notifications devices
	 */
	@Override
	public List<PushNotificationsDevice> findAll(
		int start, int end,
		OrderByComparator<PushNotificationsDevice> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the push notifications devices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PushNotificationsDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of push notifications devices
	 * @param end the upper bound of the range of push notifications devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of push notifications devices
	 */
	@Override
	public List<PushNotificationsDevice> findAll(
		int start, int end,
		OrderByComparator<PushNotificationsDevice> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<PushNotificationsDevice> list = null;

		if (useFinderCache) {
			list = (List<PushNotificationsDevice>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_PUSHNOTIFICATIONSDEVICE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_PUSHNOTIFICATIONSDEVICE;

				sql = sql.concat(
					PushNotificationsDeviceModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<PushNotificationsDevice>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the push notifications devices from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PushNotificationsDevice pushNotificationsDevice : findAll()) {
			remove(pushNotificationsDevice);
		}
	}

	/**
	 * Returns the number of push notifications devices.
	 *
	 * @return the number of push notifications devices
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_PUSHNOTIFICATIONSDEVICE);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "pushNotificationsDeviceId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_PUSHNOTIFICATIONSDEVICE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return PushNotificationsDeviceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the push notifications device persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByToken = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByToken",
			new String[] {String.class.getName()}, new String[] {"token"},
			true);

		_finderPathCountByToken = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByToken",
			new String[] {String.class.getName()}, new String[] {"token"},
			false);

		_finderPathWithPaginationFindByU_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"userId", "platform"}, true);

		_finderPathWithoutPaginationFindByU_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_P",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"userId", "platform"}, true);

		_finderPathCountByU_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_P",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"userId", "platform"}, false);

		_finderPathWithPaginationCountByU_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByU_P",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"userId", "platform"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(PushNotificationsDeviceImpl.class.getName());
	}

	@Override
	@Reference(
		target = PushNotificationsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = PushNotificationsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = PushNotificationsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_PUSHNOTIFICATIONSDEVICE =
		"SELECT pushNotificationsDevice FROM PushNotificationsDevice pushNotificationsDevice";

	private static final String _SQL_SELECT_PUSHNOTIFICATIONSDEVICE_WHERE =
		"SELECT pushNotificationsDevice FROM PushNotificationsDevice pushNotificationsDevice WHERE ";

	private static final String _SQL_COUNT_PUSHNOTIFICATIONSDEVICE =
		"SELECT COUNT(pushNotificationsDevice) FROM PushNotificationsDevice pushNotificationsDevice";

	private static final String _SQL_COUNT_PUSHNOTIFICATIONSDEVICE_WHERE =
		"SELECT COUNT(pushNotificationsDevice) FROM PushNotificationsDevice pushNotificationsDevice WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"pushNotificationsDevice.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No PushNotificationsDevice exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PushNotificationsDevice exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PushNotificationsDevicePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PushNotificationsDeviceModelArgumentsResolver
		_pushNotificationsDeviceModelArgumentsResolver;

}