/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchUserNotificationDeliveryException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationDelivery;
import com.liferay.portal.kernel.model.UserNotificationDeliveryTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.UserNotificationDeliveryPersistence;
import com.liferay.portal.kernel.service.persistence.UserNotificationDeliveryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.UserNotificationDeliveryImpl;
import com.liferay.portal.model.impl.UserNotificationDeliveryModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the user notification delivery service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserNotificationDeliveryPersistenceImpl
	extends BasePersistenceImpl<UserNotificationDelivery>
	implements UserNotificationDeliveryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>UserNotificationDeliveryUtil</code> to access the user notification delivery persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		UserNotificationDeliveryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the user notification deliveries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching user notification deliveries
	 */
	@Override
	public List<UserNotificationDelivery> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification deliveries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user notification deliveries
	 * @param end the upper bound of the range of user notification deliveries (not inclusive)
	 * @return the range of matching user notification deliveries
	 */
	@Override
	public List<UserNotificationDelivery> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification deliveries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user notification deliveries
	 * @param end the upper bound of the range of user notification deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification deliveries
	 */
	@Override
	public List<UserNotificationDelivery> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserNotificationDelivery> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification deliveries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user notification deliveries
	 * @param end the upper bound of the range of user notification deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification deliveries
	 */
	@Override
	public List<UserNotificationDelivery> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserNotificationDelivery> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<UserNotificationDelivery> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationDelivery>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationDelivery userNotificationDelivery : list) {
					if (userId != userNotificationDelivery.getUserId()) {
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
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_USERNOTIFICATIONDELIVERY_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserNotificationDeliveryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<UserNotificationDelivery>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first user notification delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification delivery
	 * @throws NoSuchUserNotificationDeliveryException if a matching user notification delivery could not be found
	 */
	@Override
	public UserNotificationDelivery findByUserId_First(
			long userId,
			OrderByComparator<UserNotificationDelivery> orderByComparator)
		throws NoSuchUserNotificationDeliveryException {

		UserNotificationDelivery userNotificationDelivery = fetchByUserId_First(
			userId, orderByComparator);

		if (userNotificationDelivery != null) {
			return userNotificationDelivery;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchUserNotificationDeliveryException(sb.toString());
	}

	/**
	 * Returns the first user notification delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	 */
	@Override
	public UserNotificationDelivery fetchByUserId_First(
		long userId,
		OrderByComparator<UserNotificationDelivery> orderByComparator) {

		List<UserNotificationDelivery> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification delivery
	 * @throws NoSuchUserNotificationDeliveryException if a matching user notification delivery could not be found
	 */
	@Override
	public UserNotificationDelivery findByUserId_Last(
			long userId,
			OrderByComparator<UserNotificationDelivery> orderByComparator)
		throws NoSuchUserNotificationDeliveryException {

		UserNotificationDelivery userNotificationDelivery = fetchByUserId_Last(
			userId, orderByComparator);

		if (userNotificationDelivery != null) {
			return userNotificationDelivery;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchUserNotificationDeliveryException(sb.toString());
	}

	/**
	 * Returns the last user notification delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	 */
	@Override
	public UserNotificationDelivery fetchByUserId_Last(
		long userId,
		OrderByComparator<UserNotificationDelivery> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<UserNotificationDelivery> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification deliveries before and after the current user notification delivery in the ordered set where userId = &#63;.
	 *
	 * @param userNotificationDeliveryId the primary key of the current user notification delivery
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification delivery
	 * @throws NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	 */
	@Override
	public UserNotificationDelivery[] findByUserId_PrevAndNext(
			long userNotificationDeliveryId, long userId,
			OrderByComparator<UserNotificationDelivery> orderByComparator)
		throws NoSuchUserNotificationDeliveryException {

		UserNotificationDelivery userNotificationDelivery = findByPrimaryKey(
			userNotificationDeliveryId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationDelivery[] array =
				new UserNotificationDeliveryImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, userNotificationDelivery, userId, orderByComparator,
				true);

			array[1] = userNotificationDelivery;

			array[2] = getByUserId_PrevAndNext(
				session, userNotificationDelivery, userId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationDelivery getByUserId_PrevAndNext(
		Session session, UserNotificationDelivery userNotificationDelivery,
		long userId,
		OrderByComparator<UserNotificationDelivery> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USERNOTIFICATIONDELIVERY_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

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
			sb.append(UserNotificationDeliveryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationDelivery)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationDelivery> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification deliveries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (UserNotificationDelivery userNotificationDelivery :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userNotificationDelivery);
		}
	}

	/**
	 * Returns the number of user notification deliveries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching user notification deliveries
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USERNOTIFICATIONDELIVERY_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"userNotificationDelivery.userId = ?";

	private FinderPath _finderPathFetchByU_P_C_N_D;
	private FinderPath _finderPathCountByU_P_C_N_D;

	/**
	 * Returns the user notification delivery where userId = &#63; and portletId = &#63; and classNameId = &#63; and notificationType = &#63; and deliveryType = &#63; or throws a <code>NoSuchUserNotificationDeliveryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param notificationType the notification type
	 * @param deliveryType the delivery type
	 * @return the matching user notification delivery
	 * @throws NoSuchUserNotificationDeliveryException if a matching user notification delivery could not be found
	 */
	@Override
	public UserNotificationDelivery findByU_P_C_N_D(
			long userId, String portletId, long classNameId,
			int notificationType, int deliveryType)
		throws NoSuchUserNotificationDeliveryException {

		UserNotificationDelivery userNotificationDelivery = fetchByU_P_C_N_D(
			userId, portletId, classNameId, notificationType, deliveryType);

		if (userNotificationDelivery == null) {
			StringBundler sb = new StringBundler(12);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", portletId=");
			sb.append(portletId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", notificationType=");
			sb.append(notificationType);

			sb.append(", deliveryType=");
			sb.append(deliveryType);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchUserNotificationDeliveryException(sb.toString());
		}

		return userNotificationDelivery;
	}

	/**
	 * Returns the user notification delivery where userId = &#63; and portletId = &#63; and classNameId = &#63; and notificationType = &#63; and deliveryType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param notificationType the notification type
	 * @param deliveryType the delivery type
	 * @return the matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	 */
	@Override
	public UserNotificationDelivery fetchByU_P_C_N_D(
		long userId, String portletId, long classNameId, int notificationType,
		int deliveryType) {

		return fetchByU_P_C_N_D(
			userId, portletId, classNameId, notificationType, deliveryType,
			true);
	}

	/**
	 * Returns the user notification delivery where userId = &#63; and portletId = &#63; and classNameId = &#63; and notificationType = &#63; and deliveryType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param notificationType the notification type
	 * @param deliveryType the delivery type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user notification delivery, or <code>null</code> if a matching user notification delivery could not be found
	 */
	@Override
	public UserNotificationDelivery fetchByU_P_C_N_D(
		long userId, String portletId, long classNameId, int notificationType,
		int deliveryType, boolean useFinderCache) {

		portletId = Objects.toString(portletId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				userId, portletId, classNameId, notificationType, deliveryType
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByU_P_C_N_D, finderArgs, this);
		}

		if (result instanceof UserNotificationDelivery) {
			UserNotificationDelivery userNotificationDelivery =
				(UserNotificationDelivery)result;

			if ((userId != userNotificationDelivery.getUserId()) ||
				!Objects.equals(
					portletId, userNotificationDelivery.getPortletId()) ||
				(classNameId != userNotificationDelivery.getClassNameId()) ||
				(notificationType !=
					userNotificationDelivery.getNotificationType()) ||
				(deliveryType != userNotificationDelivery.getDeliveryType())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(7);

			sb.append(_SQL_SELECT_USERNOTIFICATIONDELIVERY_WHERE);

			sb.append(_FINDER_COLUMN_U_P_C_N_D_USERID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_P_C_N_D_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_U_P_C_N_D_PORTLETID_2);
			}

			sb.append(_FINDER_COLUMN_U_P_C_N_D_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_U_P_C_N_D_NOTIFICATIONTYPE_2);

			sb.append(_FINDER_COLUMN_U_P_C_N_D_DELIVERYTYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				queryPos.add(classNameId);

				queryPos.add(notificationType);

				queryPos.add(deliveryType);

				List<UserNotificationDelivery> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByU_P_C_N_D, finderArgs, list);
					}
				}
				else {
					UserNotificationDelivery userNotificationDelivery =
						list.get(0);

					result = userNotificationDelivery;

					cacheResult(userNotificationDelivery);
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
			return (UserNotificationDelivery)result;
		}
	}

	/**
	 * Removes the user notification delivery where userId = &#63; and portletId = &#63; and classNameId = &#63; and notificationType = &#63; and deliveryType = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param notificationType the notification type
	 * @param deliveryType the delivery type
	 * @return the user notification delivery that was removed
	 */
	@Override
	public UserNotificationDelivery removeByU_P_C_N_D(
			long userId, String portletId, long classNameId,
			int notificationType, int deliveryType)
		throws NoSuchUserNotificationDeliveryException {

		UserNotificationDelivery userNotificationDelivery = findByU_P_C_N_D(
			userId, portletId, classNameId, notificationType, deliveryType);

		return remove(userNotificationDelivery);
	}

	/**
	 * Returns the number of user notification deliveries where userId = &#63; and portletId = &#63; and classNameId = &#63; and notificationType = &#63; and deliveryType = &#63;.
	 *
	 * @param userId the user ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param notificationType the notification type
	 * @param deliveryType the delivery type
	 * @return the number of matching user notification deliveries
	 */
	@Override
	public int countByU_P_C_N_D(
		long userId, String portletId, long classNameId, int notificationType,
		int deliveryType) {

		portletId = Objects.toString(portletId, "");

		FinderPath finderPath = _finderPathCountByU_P_C_N_D;

		Object[] finderArgs = new Object[] {
			userId, portletId, classNameId, notificationType, deliveryType
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_USERNOTIFICATIONDELIVERY_WHERE);

			sb.append(_FINDER_COLUMN_U_P_C_N_D_USERID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_P_C_N_D_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_U_P_C_N_D_PORTLETID_2);
			}

			sb.append(_FINDER_COLUMN_U_P_C_N_D_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_U_P_C_N_D_NOTIFICATIONTYPE_2);

			sb.append(_FINDER_COLUMN_U_P_C_N_D_DELIVERYTYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				queryPos.add(classNameId);

				queryPos.add(notificationType);

				queryPos.add(deliveryType);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_U_P_C_N_D_USERID_2 =
		"userNotificationDelivery.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_P_C_N_D_PORTLETID_2 =
		"userNotificationDelivery.portletId = ? AND ";

	private static final String _FINDER_COLUMN_U_P_C_N_D_PORTLETID_3 =
		"(userNotificationDelivery.portletId IS NULL OR userNotificationDelivery.portletId = '') AND ";

	private static final String _FINDER_COLUMN_U_P_C_N_D_CLASSNAMEID_2 =
		"userNotificationDelivery.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_U_P_C_N_D_NOTIFICATIONTYPE_2 =
		"userNotificationDelivery.notificationType = ? AND ";

	private static final String _FINDER_COLUMN_U_P_C_N_D_DELIVERYTYPE_2 =
		"userNotificationDelivery.deliveryType = ?";

	public UserNotificationDeliveryPersistenceImpl() {
		setModelClass(UserNotificationDelivery.class);

		setModelImplClass(UserNotificationDeliveryImpl.class);
		setModelPKClass(long.class);

		setTable(UserNotificationDeliveryTable.INSTANCE);
	}

	/**
	 * Caches the user notification delivery in the entity cache if it is enabled.
	 *
	 * @param userNotificationDelivery the user notification delivery
	 */
	@Override
	public void cacheResult(UserNotificationDelivery userNotificationDelivery) {
		EntityCacheUtil.putResult(
			UserNotificationDeliveryImpl.class,
			userNotificationDelivery.getPrimaryKey(), userNotificationDelivery);

		FinderCacheUtil.putResult(
			_finderPathFetchByU_P_C_N_D,
			new Object[] {
				userNotificationDelivery.getUserId(),
				userNotificationDelivery.getPortletId(),
				userNotificationDelivery.getClassNameId(),
				userNotificationDelivery.getNotificationType(),
				userNotificationDelivery.getDeliveryType()
			},
			userNotificationDelivery);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the user notification deliveries in the entity cache if it is enabled.
	 *
	 * @param userNotificationDeliveries the user notification deliveries
	 */
	@Override
	public void cacheResult(
		List<UserNotificationDelivery> userNotificationDeliveries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (userNotificationDeliveries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (UserNotificationDelivery userNotificationDelivery :
				userNotificationDeliveries) {

			if (EntityCacheUtil.getResult(
					UserNotificationDeliveryImpl.class,
					userNotificationDelivery.getPrimaryKey()) == null) {

				cacheResult(userNotificationDelivery);
			}
		}
	}

	/**
	 * Clears the cache for all user notification deliveries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(UserNotificationDeliveryImpl.class);

		FinderCacheUtil.clearCache(UserNotificationDeliveryImpl.class);
	}

	/**
	 * Clears the cache for the user notification delivery.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserNotificationDelivery userNotificationDelivery) {
		EntityCacheUtil.removeResult(
			UserNotificationDeliveryImpl.class, userNotificationDelivery);
	}

	@Override
	public void clearCache(
		List<UserNotificationDelivery> userNotificationDeliveries) {

		for (UserNotificationDelivery userNotificationDelivery :
				userNotificationDeliveries) {

			EntityCacheUtil.removeResult(
				UserNotificationDeliveryImpl.class, userNotificationDelivery);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(UserNotificationDeliveryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				UserNotificationDeliveryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		UserNotificationDeliveryModelImpl userNotificationDeliveryModelImpl) {

		Object[] args = new Object[] {
			userNotificationDeliveryModelImpl.getUserId(),
			userNotificationDeliveryModelImpl.getPortletId(),
			userNotificationDeliveryModelImpl.getClassNameId(),
			userNotificationDeliveryModelImpl.getNotificationType(),
			userNotificationDeliveryModelImpl.getDeliveryType()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByU_P_C_N_D, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByU_P_C_N_D, args,
			userNotificationDeliveryModelImpl);
	}

	/**
	 * Creates a new user notification delivery with the primary key. Does not add the user notification delivery to the database.
	 *
	 * @param userNotificationDeliveryId the primary key for the new user notification delivery
	 * @return the new user notification delivery
	 */
	@Override
	public UserNotificationDelivery create(long userNotificationDeliveryId) {
		UserNotificationDelivery userNotificationDelivery =
			new UserNotificationDeliveryImpl();

		userNotificationDelivery.setNew(true);
		userNotificationDelivery.setPrimaryKey(userNotificationDeliveryId);

		userNotificationDelivery.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return userNotificationDelivery;
	}

	/**
	 * Removes the user notification delivery with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userNotificationDeliveryId the primary key of the user notification delivery
	 * @return the user notification delivery that was removed
	 * @throws NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	 */
	@Override
	public UserNotificationDelivery remove(long userNotificationDeliveryId)
		throws NoSuchUserNotificationDeliveryException {

		return remove((Serializable)userNotificationDeliveryId);
	}

	/**
	 * Removes the user notification delivery with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user notification delivery
	 * @return the user notification delivery that was removed
	 * @throws NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	 */
	@Override
	public UserNotificationDelivery remove(Serializable primaryKey)
		throws NoSuchUserNotificationDeliveryException {

		Session session = null;

		try {
			session = openSession();

			UserNotificationDelivery userNotificationDelivery =
				(UserNotificationDelivery)session.get(
					UserNotificationDeliveryImpl.class, primaryKey);

			if (userNotificationDelivery == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserNotificationDeliveryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(userNotificationDelivery);
		}
		catch (NoSuchUserNotificationDeliveryException noSuchEntityException) {
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
	protected UserNotificationDelivery removeImpl(
		UserNotificationDelivery userNotificationDelivery) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(userNotificationDelivery)) {
				userNotificationDelivery =
					(UserNotificationDelivery)session.get(
						UserNotificationDeliveryImpl.class,
						userNotificationDelivery.getPrimaryKeyObj());
			}

			if (userNotificationDelivery != null) {
				session.delete(userNotificationDelivery);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (userNotificationDelivery != null) {
			clearCache(userNotificationDelivery);
		}

		return userNotificationDelivery;
	}

	@Override
	public UserNotificationDelivery updateImpl(
		UserNotificationDelivery userNotificationDelivery) {

		boolean isNew = userNotificationDelivery.isNew();

		if (!(userNotificationDelivery instanceof
				UserNotificationDeliveryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(userNotificationDelivery.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					userNotificationDelivery);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in userNotificationDelivery proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom UserNotificationDelivery implementation " +
					userNotificationDelivery.getClass());
		}

		UserNotificationDeliveryModelImpl userNotificationDeliveryModelImpl =
			(UserNotificationDeliveryModelImpl)userNotificationDelivery;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(userNotificationDelivery);
			}
			else {
				userNotificationDelivery =
					(UserNotificationDelivery)session.merge(
						userNotificationDelivery);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			UserNotificationDeliveryImpl.class,
			userNotificationDeliveryModelImpl, false, true);

		cacheUniqueFindersCache(userNotificationDeliveryModelImpl);

		if (isNew) {
			userNotificationDelivery.setNew(false);
		}

		userNotificationDelivery.resetOriginalValues();

		return userNotificationDelivery;
	}

	/**
	 * Returns the user notification delivery with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user notification delivery
	 * @return the user notification delivery
	 * @throws NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	 */
	@Override
	public UserNotificationDelivery findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserNotificationDeliveryException {

		UserNotificationDelivery userNotificationDelivery = fetchByPrimaryKey(
			primaryKey);

		if (userNotificationDelivery == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserNotificationDeliveryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return userNotificationDelivery;
	}

	/**
	 * Returns the user notification delivery with the primary key or throws a <code>NoSuchUserNotificationDeliveryException</code> if it could not be found.
	 *
	 * @param userNotificationDeliveryId the primary key of the user notification delivery
	 * @return the user notification delivery
	 * @throws NoSuchUserNotificationDeliveryException if a user notification delivery with the primary key could not be found
	 */
	@Override
	public UserNotificationDelivery findByPrimaryKey(
			long userNotificationDeliveryId)
		throws NoSuchUserNotificationDeliveryException {

		return findByPrimaryKey((Serializable)userNotificationDeliveryId);
	}

	/**
	 * Returns the user notification delivery with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userNotificationDeliveryId the primary key of the user notification delivery
	 * @return the user notification delivery, or <code>null</code> if a user notification delivery with the primary key could not be found
	 */
	@Override
	public UserNotificationDelivery fetchByPrimaryKey(
		long userNotificationDeliveryId) {

		return fetchByPrimaryKey((Serializable)userNotificationDeliveryId);
	}

	/**
	 * Returns all the user notification deliveries.
	 *
	 * @return the user notification deliveries
	 */
	@Override
	public List<UserNotificationDelivery> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification deliveries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user notification deliveries
	 * @param end the upper bound of the range of user notification deliveries (not inclusive)
	 * @return the range of user notification deliveries
	 */
	@Override
	public List<UserNotificationDelivery> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification deliveries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user notification deliveries
	 * @param end the upper bound of the range of user notification deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user notification deliveries
	 */
	@Override
	public List<UserNotificationDelivery> findAll(
		int start, int end,
		OrderByComparator<UserNotificationDelivery> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification deliveries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationDeliveryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user notification deliveries
	 * @param end the upper bound of the range of user notification deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of user notification deliveries
	 */
	@Override
	public List<UserNotificationDelivery> findAll(
		int start, int end,
		OrderByComparator<UserNotificationDelivery> orderByComparator,
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

		List<UserNotificationDelivery> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationDelivery>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_USERNOTIFICATIONDELIVERY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_USERNOTIFICATIONDELIVERY;

				sql = sql.concat(
					UserNotificationDeliveryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<UserNotificationDelivery>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the user notification deliveries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (UserNotificationDelivery userNotificationDelivery : findAll()) {
			remove(userNotificationDelivery);
		}
	}

	/**
	 * Returns the number of user notification deliveries.
	 *
	 * @return the number of user notification deliveries
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_USERNOTIFICATIONDELIVERY);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "userNotificationDeliveryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_USERNOTIFICATIONDELIVERY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return UserNotificationDeliveryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the user notification delivery persistence.
	 */
	public void afterPropertiesSet() {
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

		_finderPathWithPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userId"}, true);

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);

		_finderPathFetchByU_P_C_N_D = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByU_P_C_N_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			new String[] {
				"userId", "portletId", "classNameId", "notificationType",
				"deliveryType"
			},
			true);

		_finderPathCountByU_P_C_N_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_P_C_N_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			new String[] {
				"userId", "portletId", "classNameId", "notificationType",
				"deliveryType"
			},
			false);

		UserNotificationDeliveryUtil.setPersistence(this);
	}

	public void destroy() {
		UserNotificationDeliveryUtil.setPersistence(null);

		EntityCacheUtil.removeCache(
			UserNotificationDeliveryImpl.class.getName());
	}

	private static final String _SQL_SELECT_USERNOTIFICATIONDELIVERY =
		"SELECT userNotificationDelivery FROM UserNotificationDelivery userNotificationDelivery";

	private static final String _SQL_SELECT_USERNOTIFICATIONDELIVERY_WHERE =
		"SELECT userNotificationDelivery FROM UserNotificationDelivery userNotificationDelivery WHERE ";

	private static final String _SQL_COUNT_USERNOTIFICATIONDELIVERY =
		"SELECT COUNT(userNotificationDelivery) FROM UserNotificationDelivery userNotificationDelivery";

	private static final String _SQL_COUNT_USERNOTIFICATIONDELIVERY_WHERE =
		"SELECT COUNT(userNotificationDelivery) FROM UserNotificationDelivery userNotificationDelivery WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"userNotificationDelivery.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No UserNotificationDelivery exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No UserNotificationDelivery exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		UserNotificationDeliveryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}