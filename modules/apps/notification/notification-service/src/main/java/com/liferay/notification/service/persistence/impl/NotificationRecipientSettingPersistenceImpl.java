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

package com.liferay.notification.service.persistence.impl;

import com.liferay.notification.exception.NoSuchNotificationRecipientSettingException;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationRecipientSettingTable;
import com.liferay.notification.model.impl.NotificationRecipientSettingImpl;
import com.liferay.notification.model.impl.NotificationRecipientSettingModelImpl;
import com.liferay.notification.service.persistence.NotificationRecipientSettingPersistence;
import com.liferay.notification.service.persistence.NotificationRecipientSettingUtil;
import com.liferay.notification.service.persistence.impl.constants.NotificationPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
 * The persistence implementation for the notification recipient setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @generated
 */
@Component(service = NotificationRecipientSettingPersistence.class)
public class NotificationRecipientSettingPersistenceImpl
	extends BasePersistenceImpl<NotificationRecipientSetting>
	implements NotificationRecipientSettingPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>NotificationRecipientSettingUtil</code> to access the notification recipient setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		NotificationRecipientSettingImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the notification recipient settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notification recipient settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @return the range of matching notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<NotificationRecipientSetting> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<NotificationRecipientSetting> list = null;

		if (useFinderCache) {
			list = (List<NotificationRecipientSetting>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (NotificationRecipientSetting notificationRecipientSetting :
						list) {

					if (!uuid.equals(notificationRecipientSetting.getUuid())) {
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

			sb.append(_SQL_SELECT_NOTIFICATIONRECIPIENTSETTING_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(NotificationRecipientSettingModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<NotificationRecipientSetting>)QueryUtil.list(
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
	 * Returns the first notification recipient setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	@Override
	public NotificationRecipientSetting findByUuid_First(
			String uuid,
			OrderByComparator<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException {

		NotificationRecipientSetting notificationRecipientSetting =
			fetchByUuid_First(uuid, orderByComparator);

		if (notificationRecipientSetting != null) {
			return notificationRecipientSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchNotificationRecipientSettingException(sb.toString());
	}

	/**
	 * Returns the first notification recipient setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	@Override
	public NotificationRecipientSetting fetchByUuid_First(
		String uuid,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		List<NotificationRecipientSetting> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last notification recipient setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	@Override
	public NotificationRecipientSetting findByUuid_Last(
			String uuid,
			OrderByComparator<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException {

		NotificationRecipientSetting notificationRecipientSetting =
			fetchByUuid_Last(uuid, orderByComparator);

		if (notificationRecipientSetting != null) {
			return notificationRecipientSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchNotificationRecipientSettingException(sb.toString());
	}

	/**
	 * Returns the last notification recipient setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	@Override
	public NotificationRecipientSetting fetchByUuid_Last(
		String uuid,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<NotificationRecipientSetting> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the notification recipient settings before and after the current notification recipient setting in the ordered set where uuid = &#63;.
	 *
	 * @param notificationRecipientSettingId the primary key of the current notification recipient setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationRecipientSetting[] findByUuid_PrevAndNext(
			long notificationRecipientSettingId, String uuid,
			OrderByComparator<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException {

		uuid = Objects.toString(uuid, "");

		NotificationRecipientSetting notificationRecipientSetting =
			findByPrimaryKey(notificationRecipientSettingId);

		Session session = null;

		try {
			session = openSession();

			NotificationRecipientSetting[] array =
				new NotificationRecipientSettingImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, notificationRecipientSetting, uuid, orderByComparator,
				true);

			array[1] = notificationRecipientSetting;

			array[2] = getByUuid_PrevAndNext(
				session, notificationRecipientSetting, uuid, orderByComparator,
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

	protected NotificationRecipientSetting getByUuid_PrevAndNext(
		Session session,
		NotificationRecipientSetting notificationRecipientSetting, String uuid,
		OrderByComparator<NotificationRecipientSetting> orderByComparator,
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

		sb.append(_SQL_SELECT_NOTIFICATIONRECIPIENTSETTING_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
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
			sb.append(NotificationRecipientSettingModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						notificationRecipientSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<NotificationRecipientSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the notification recipient settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (NotificationRecipientSetting notificationRecipientSetting :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(notificationRecipientSetting);
		}
	}

	/**
	 * Returns the number of notification recipient settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching notification recipient settings
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_NOTIFICATIONRECIPIENTSETTING_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"notificationRecipientSetting.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(notificationRecipientSetting.uuid IS NULL OR notificationRecipientSetting.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the notification recipient settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notification recipient settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @return the range of matching notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<NotificationRecipientSetting> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<NotificationRecipientSetting> list = null;

		if (useFinderCache) {
			list = (List<NotificationRecipientSetting>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (NotificationRecipientSetting notificationRecipientSetting :
						list) {

					if (!uuid.equals(notificationRecipientSetting.getUuid()) ||
						(companyId !=
							notificationRecipientSetting.getCompanyId())) {

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

			sb.append(_SQL_SELECT_NOTIFICATIONRECIPIENTSETTING_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(NotificationRecipientSettingModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<NotificationRecipientSetting>)QueryUtil.list(
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
	 * Returns the first notification recipient setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	@Override
	public NotificationRecipientSetting findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException {

		NotificationRecipientSetting notificationRecipientSetting =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (notificationRecipientSetting != null) {
			return notificationRecipientSetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchNotificationRecipientSettingException(sb.toString());
	}

	/**
	 * Returns the first notification recipient setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	@Override
	public NotificationRecipientSetting fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		List<NotificationRecipientSetting> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last notification recipient setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	@Override
	public NotificationRecipientSetting findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException {

		NotificationRecipientSetting notificationRecipientSetting =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (notificationRecipientSetting != null) {
			return notificationRecipientSetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchNotificationRecipientSettingException(sb.toString());
	}

	/**
	 * Returns the last notification recipient setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	@Override
	public NotificationRecipientSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<NotificationRecipientSetting> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the notification recipient settings before and after the current notification recipient setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param notificationRecipientSettingId the primary key of the current notification recipient setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationRecipientSetting[] findByUuid_C_PrevAndNext(
			long notificationRecipientSettingId, String uuid, long companyId,
			OrderByComparator<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException {

		uuid = Objects.toString(uuid, "");

		NotificationRecipientSetting notificationRecipientSetting =
			findByPrimaryKey(notificationRecipientSettingId);

		Session session = null;

		try {
			session = openSession();

			NotificationRecipientSetting[] array =
				new NotificationRecipientSettingImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, notificationRecipientSetting, uuid, companyId,
				orderByComparator, true);

			array[1] = notificationRecipientSetting;

			array[2] = getByUuid_C_PrevAndNext(
				session, notificationRecipientSetting, uuid, companyId,
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

	protected NotificationRecipientSetting getByUuid_C_PrevAndNext(
		Session session,
		NotificationRecipientSetting notificationRecipientSetting, String uuid,
		long companyId,
		OrderByComparator<NotificationRecipientSetting> orderByComparator,
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

		sb.append(_SQL_SELECT_NOTIFICATIONRECIPIENTSETTING_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			sb.append(NotificationRecipientSettingModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						notificationRecipientSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<NotificationRecipientSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the notification recipient settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (NotificationRecipientSetting notificationRecipientSetting :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(notificationRecipientSetting);
		}
	}

	/**
	 * Returns the number of notification recipient settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching notification recipient settings
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_NOTIFICATIONRECIPIENTSETTING_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"notificationRecipientSetting.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(notificationRecipientSetting.uuid IS NULL OR notificationRecipientSetting.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"notificationRecipientSetting.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByNotificationRecipientId;
	private FinderPath
		_finderPathWithoutPaginationFindByNotificationRecipientId;
	private FinderPath _finderPathCountByNotificationRecipientId;

	/**
	 * Returns all the notification recipient settings where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @return the matching notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findByNotificationRecipientId(
		long notificationRecipientId) {

		return findByNotificationRecipientId(
			notificationRecipientId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the notification recipient settings where notificationRecipientId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @return the range of matching notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findByNotificationRecipientId(
		long notificationRecipientId, int start, int end) {

		return findByNotificationRecipientId(
			notificationRecipientId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings where notificationRecipientId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findByNotificationRecipientId(
		long notificationRecipientId, int start, int end,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		return findByNotificationRecipientId(
			notificationRecipientId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings where notificationRecipientId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findByNotificationRecipientId(
		long notificationRecipientId, int start, int end,
		OrderByComparator<NotificationRecipientSetting> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByNotificationRecipientId;
				finderArgs = new Object[] {notificationRecipientId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByNotificationRecipientId;
			finderArgs = new Object[] {
				notificationRecipientId, start, end, orderByComparator
			};
		}

		List<NotificationRecipientSetting> list = null;

		if (useFinderCache) {
			list = (List<NotificationRecipientSetting>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (NotificationRecipientSetting notificationRecipientSetting :
						list) {

					if (notificationRecipientId !=
							notificationRecipientSetting.
								getNotificationRecipientId()) {

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

			sb.append(_SQL_SELECT_NOTIFICATIONRECIPIENTSETTING_WHERE);

			sb.append(
				_FINDER_COLUMN_NOTIFICATIONRECIPIENTID_NOTIFICATIONRECIPIENTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(NotificationRecipientSettingModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationRecipientId);

				list = (List<NotificationRecipientSetting>)QueryUtil.list(
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
	 * Returns the first notification recipient setting in the ordered set where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	@Override
	public NotificationRecipientSetting findByNotificationRecipientId_First(
			long notificationRecipientId,
			OrderByComparator<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException {

		NotificationRecipientSetting notificationRecipientSetting =
			fetchByNotificationRecipientId_First(
				notificationRecipientId, orderByComparator);

		if (notificationRecipientSetting != null) {
			return notificationRecipientSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("notificationRecipientId=");
		sb.append(notificationRecipientId);

		sb.append("}");

		throw new NoSuchNotificationRecipientSettingException(sb.toString());
	}

	/**
	 * Returns the first notification recipient setting in the ordered set where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	@Override
	public NotificationRecipientSetting fetchByNotificationRecipientId_First(
		long notificationRecipientId,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		List<NotificationRecipientSetting> list = findByNotificationRecipientId(
			notificationRecipientId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last notification recipient setting in the ordered set where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	@Override
	public NotificationRecipientSetting findByNotificationRecipientId_Last(
			long notificationRecipientId,
			OrderByComparator<NotificationRecipientSetting> orderByComparator)
		throws NoSuchNotificationRecipientSettingException {

		NotificationRecipientSetting notificationRecipientSetting =
			fetchByNotificationRecipientId_Last(
				notificationRecipientId, orderByComparator);

		if (notificationRecipientSetting != null) {
			return notificationRecipientSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("notificationRecipientId=");
		sb.append(notificationRecipientId);

		sb.append("}");

		throw new NoSuchNotificationRecipientSettingException(sb.toString());
	}

	/**
	 * Returns the last notification recipient setting in the ordered set where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	@Override
	public NotificationRecipientSetting fetchByNotificationRecipientId_Last(
		long notificationRecipientId,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		int count = countByNotificationRecipientId(notificationRecipientId);

		if (count == 0) {
			return null;
		}

		List<NotificationRecipientSetting> list = findByNotificationRecipientId(
			notificationRecipientId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the notification recipient settings before and after the current notification recipient setting in the ordered set where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientSettingId the primary key of the current notification recipient setting
	 * @param notificationRecipientId the notification recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationRecipientSetting[]
			findByNotificationRecipientId_PrevAndNext(
				long notificationRecipientSettingId,
				long notificationRecipientId,
				OrderByComparator<NotificationRecipientSetting>
					orderByComparator)
		throws NoSuchNotificationRecipientSettingException {

		NotificationRecipientSetting notificationRecipientSetting =
			findByPrimaryKey(notificationRecipientSettingId);

		Session session = null;

		try {
			session = openSession();

			NotificationRecipientSetting[] array =
				new NotificationRecipientSettingImpl[3];

			array[0] = getByNotificationRecipientId_PrevAndNext(
				session, notificationRecipientSetting, notificationRecipientId,
				orderByComparator, true);

			array[1] = notificationRecipientSetting;

			array[2] = getByNotificationRecipientId_PrevAndNext(
				session, notificationRecipientSetting, notificationRecipientId,
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

	protected NotificationRecipientSetting
		getByNotificationRecipientId_PrevAndNext(
			Session session,
			NotificationRecipientSetting notificationRecipientSetting,
			long notificationRecipientId,
			OrderByComparator<NotificationRecipientSetting> orderByComparator,
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

		sb.append(_SQL_SELECT_NOTIFICATIONRECIPIENTSETTING_WHERE);

		sb.append(
			_FINDER_COLUMN_NOTIFICATIONRECIPIENTID_NOTIFICATIONRECIPIENTID_2);

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
			sb.append(NotificationRecipientSettingModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(notificationRecipientId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						notificationRecipientSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<NotificationRecipientSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the notification recipient settings where notificationRecipientId = &#63; from the database.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 */
	@Override
	public void removeByNotificationRecipientId(long notificationRecipientId) {
		for (NotificationRecipientSetting notificationRecipientSetting :
				findByNotificationRecipientId(
					notificationRecipientId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(notificationRecipientSetting);
		}
	}

	/**
	 * Returns the number of notification recipient settings where notificationRecipientId = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @return the number of matching notification recipient settings
	 */
	@Override
	public int countByNotificationRecipientId(long notificationRecipientId) {
		FinderPath finderPath = _finderPathCountByNotificationRecipientId;

		Object[] finderArgs = new Object[] {notificationRecipientId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_NOTIFICATIONRECIPIENTSETTING_WHERE);

			sb.append(
				_FINDER_COLUMN_NOTIFICATIONRECIPIENTID_NOTIFICATIONRECIPIENTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationRecipientId);

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

	private static final String
		_FINDER_COLUMN_NOTIFICATIONRECIPIENTID_NOTIFICATIONRECIPIENTID_2 =
			"notificationRecipientSetting.notificationRecipientId = ?";

	private FinderPath _finderPathFetchByNRI_N;
	private FinderPath _finderPathCountByNRI_N;

	/**
	 * Returns the notification recipient setting where notificationRecipientId = &#63; and name = &#63; or throws a <code>NoSuchNotificationRecipientSettingException</code> if it could not be found.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param name the name
	 * @return the matching notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a matching notification recipient setting could not be found
	 */
	@Override
	public NotificationRecipientSetting findByNRI_N(
			long notificationRecipientId, String name)
		throws NoSuchNotificationRecipientSettingException {

		NotificationRecipientSetting notificationRecipientSetting =
			fetchByNRI_N(notificationRecipientId, name);

		if (notificationRecipientSetting == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("notificationRecipientId=");
			sb.append(notificationRecipientId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchNotificationRecipientSettingException(
				sb.toString());
		}

		return notificationRecipientSetting;
	}

	/**
	 * Returns the notification recipient setting where notificationRecipientId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param name the name
	 * @return the matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	@Override
	public NotificationRecipientSetting fetchByNRI_N(
		long notificationRecipientId, String name) {

		return fetchByNRI_N(notificationRecipientId, name, true);
	}

	/**
	 * Returns the notification recipient setting where notificationRecipientId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification recipient setting, or <code>null</code> if a matching notification recipient setting could not be found
	 */
	@Override
	public NotificationRecipientSetting fetchByNRI_N(
		long notificationRecipientId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {notificationRecipientId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByNRI_N, finderArgs, this);
		}

		if (result instanceof NotificationRecipientSetting) {
			NotificationRecipientSetting notificationRecipientSetting =
				(NotificationRecipientSetting)result;

			if ((notificationRecipientId !=
					notificationRecipientSetting.
						getNotificationRecipientId()) ||
				!Objects.equals(name, notificationRecipientSetting.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_NOTIFICATIONRECIPIENTSETTING_WHERE);

			sb.append(_FINDER_COLUMN_NRI_N_NOTIFICATIONRECIPIENTID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NRI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NRI_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationRecipientId);

				if (bindName) {
					queryPos.add(name);
				}

				List<NotificationRecipientSetting> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByNRI_N, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									notificationRecipientId, name
								};
							}

							_log.warn(
								"NotificationRecipientSettingPersistenceImpl.fetchByNRI_N(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					NotificationRecipientSetting notificationRecipientSetting =
						list.get(0);

					result = notificationRecipientSetting;

					cacheResult(notificationRecipientSetting);
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
			return (NotificationRecipientSetting)result;
		}
	}

	/**
	 * Removes the notification recipient setting where notificationRecipientId = &#63; and name = &#63; from the database.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param name the name
	 * @return the notification recipient setting that was removed
	 */
	@Override
	public NotificationRecipientSetting removeByNRI_N(
			long notificationRecipientId, String name)
		throws NoSuchNotificationRecipientSettingException {

		NotificationRecipientSetting notificationRecipientSetting = findByNRI_N(
			notificationRecipientId, name);

		return remove(notificationRecipientSetting);
	}

	/**
	 * Returns the number of notification recipient settings where notificationRecipientId = &#63; and name = &#63;.
	 *
	 * @param notificationRecipientId the notification recipient ID
	 * @param name the name
	 * @return the number of matching notification recipient settings
	 */
	@Override
	public int countByNRI_N(long notificationRecipientId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByNRI_N;

		Object[] finderArgs = new Object[] {notificationRecipientId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_NOTIFICATIONRECIPIENTSETTING_WHERE);

			sb.append(_FINDER_COLUMN_NRI_N_NOTIFICATIONRECIPIENTID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NRI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NRI_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationRecipientId);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_NRI_N_NOTIFICATIONRECIPIENTID_2 =
		"notificationRecipientSetting.notificationRecipientId = ? AND ";

	private static final String _FINDER_COLUMN_NRI_N_NAME_2 =
		"notificationRecipientSetting.name = ?";

	private static final String _FINDER_COLUMN_NRI_N_NAME_3 =
		"(notificationRecipientSetting.name IS NULL OR notificationRecipientSetting.name = '')";

	public NotificationRecipientSettingPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(NotificationRecipientSetting.class);

		setModelImplClass(NotificationRecipientSettingImpl.class);
		setModelPKClass(long.class);

		setTable(NotificationRecipientSettingTable.INSTANCE);
	}

	/**
	 * Caches the notification recipient setting in the entity cache if it is enabled.
	 *
	 * @param notificationRecipientSetting the notification recipient setting
	 */
	@Override
	public void cacheResult(
		NotificationRecipientSetting notificationRecipientSetting) {

		entityCache.putResult(
			NotificationRecipientSettingImpl.class,
			notificationRecipientSetting.getPrimaryKey(),
			notificationRecipientSetting);

		finderCache.putResult(
			_finderPathFetchByNRI_N,
			new Object[] {
				notificationRecipientSetting.getNotificationRecipientId(),
				notificationRecipientSetting.getName()
			},
			notificationRecipientSetting);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the notification recipient settings in the entity cache if it is enabled.
	 *
	 * @param notificationRecipientSettings the notification recipient settings
	 */
	@Override
	public void cacheResult(
		List<NotificationRecipientSetting> notificationRecipientSettings) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (notificationRecipientSettings.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (NotificationRecipientSetting notificationRecipientSetting :
				notificationRecipientSettings) {

			if (entityCache.getResult(
					NotificationRecipientSettingImpl.class,
					notificationRecipientSetting.getPrimaryKey()) == null) {

				cacheResult(notificationRecipientSetting);
			}
		}
	}

	/**
	 * Clears the cache for all notification recipient settings.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(NotificationRecipientSettingImpl.class);

		finderCache.clearCache(NotificationRecipientSettingImpl.class);
	}

	/**
	 * Clears the cache for the notification recipient setting.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		NotificationRecipientSetting notificationRecipientSetting) {

		entityCache.removeResult(
			NotificationRecipientSettingImpl.class,
			notificationRecipientSetting);
	}

	@Override
	public void clearCache(
		List<NotificationRecipientSetting> notificationRecipientSettings) {

		for (NotificationRecipientSetting notificationRecipientSetting :
				notificationRecipientSettings) {

			entityCache.removeResult(
				NotificationRecipientSettingImpl.class,
				notificationRecipientSetting);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(NotificationRecipientSettingImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				NotificationRecipientSettingImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		NotificationRecipientSettingModelImpl
			notificationRecipientSettingModelImpl) {

		Object[] args = new Object[] {
			notificationRecipientSettingModelImpl.getNotificationRecipientId(),
			notificationRecipientSettingModelImpl.getName()
		};

		finderCache.putResult(_finderPathCountByNRI_N, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByNRI_N, args,
			notificationRecipientSettingModelImpl);
	}

	/**
	 * Creates a new notification recipient setting with the primary key. Does not add the notification recipient setting to the database.
	 *
	 * @param notificationRecipientSettingId the primary key for the new notification recipient setting
	 * @return the new notification recipient setting
	 */
	@Override
	public NotificationRecipientSetting create(
		long notificationRecipientSettingId) {

		NotificationRecipientSetting notificationRecipientSetting =
			new NotificationRecipientSettingImpl();

		notificationRecipientSetting.setNew(true);
		notificationRecipientSetting.setPrimaryKey(
			notificationRecipientSettingId);

		String uuid = _portalUUID.generate();

		notificationRecipientSetting.setUuid(uuid);

		notificationRecipientSetting.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return notificationRecipientSetting;
	}

	/**
	 * Removes the notification recipient setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationRecipientSettingId the primary key of the notification recipient setting
	 * @return the notification recipient setting that was removed
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationRecipientSetting remove(
			long notificationRecipientSettingId)
		throws NoSuchNotificationRecipientSettingException {

		return remove((Serializable)notificationRecipientSettingId);
	}

	/**
	 * Removes the notification recipient setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the notification recipient setting
	 * @return the notification recipient setting that was removed
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationRecipientSetting remove(Serializable primaryKey)
		throws NoSuchNotificationRecipientSettingException {

		Session session = null;

		try {
			session = openSession();

			NotificationRecipientSetting notificationRecipientSetting =
				(NotificationRecipientSetting)session.get(
					NotificationRecipientSettingImpl.class, primaryKey);

			if (notificationRecipientSetting == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationRecipientSettingException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(notificationRecipientSetting);
		}
		catch (NoSuchNotificationRecipientSettingException
					noSuchEntityException) {

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
	protected NotificationRecipientSetting removeImpl(
		NotificationRecipientSetting notificationRecipientSetting) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(notificationRecipientSetting)) {
				notificationRecipientSetting =
					(NotificationRecipientSetting)session.get(
						NotificationRecipientSettingImpl.class,
						notificationRecipientSetting.getPrimaryKeyObj());
			}

			if (notificationRecipientSetting != null) {
				session.delete(notificationRecipientSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (notificationRecipientSetting != null) {
			clearCache(notificationRecipientSetting);
		}

		return notificationRecipientSetting;
	}

	@Override
	public NotificationRecipientSetting updateImpl(
		NotificationRecipientSetting notificationRecipientSetting) {

		boolean isNew = notificationRecipientSetting.isNew();

		if (!(notificationRecipientSetting instanceof
				NotificationRecipientSettingModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					notificationRecipientSetting.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					notificationRecipientSetting);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in notificationRecipientSetting proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom NotificationRecipientSetting implementation " +
					notificationRecipientSetting.getClass());
		}

		NotificationRecipientSettingModelImpl
			notificationRecipientSettingModelImpl =
				(NotificationRecipientSettingModelImpl)
					notificationRecipientSetting;

		if (Validator.isNull(notificationRecipientSetting.getUuid())) {
			String uuid = _portalUUID.generate();

			notificationRecipientSetting.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (notificationRecipientSetting.getCreateDate() == null)) {
			if (serviceContext == null) {
				notificationRecipientSetting.setCreateDate(date);
			}
			else {
				notificationRecipientSetting.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!notificationRecipientSettingModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				notificationRecipientSetting.setModifiedDate(date);
			}
			else {
				notificationRecipientSetting.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(notificationRecipientSetting);
			}
			else {
				notificationRecipientSetting =
					(NotificationRecipientSetting)session.merge(
						notificationRecipientSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			NotificationRecipientSettingImpl.class,
			notificationRecipientSettingModelImpl, false, true);

		cacheUniqueFindersCache(notificationRecipientSettingModelImpl);

		if (isNew) {
			notificationRecipientSetting.setNew(false);
		}

		notificationRecipientSetting.resetOriginalValues();

		return notificationRecipientSetting;
	}

	/**
	 * Returns the notification recipient setting with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the notification recipient setting
	 * @return the notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationRecipientSetting findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchNotificationRecipientSettingException {

		NotificationRecipientSetting notificationRecipientSetting =
			fetchByPrimaryKey(primaryKey);

		if (notificationRecipientSetting == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationRecipientSettingException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return notificationRecipientSetting;
	}

	/**
	 * Returns the notification recipient setting with the primary key or throws a <code>NoSuchNotificationRecipientSettingException</code> if it could not be found.
	 *
	 * @param notificationRecipientSettingId the primary key of the notification recipient setting
	 * @return the notification recipient setting
	 * @throws NoSuchNotificationRecipientSettingException if a notification recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationRecipientSetting findByPrimaryKey(
			long notificationRecipientSettingId)
		throws NoSuchNotificationRecipientSettingException {

		return findByPrimaryKey((Serializable)notificationRecipientSettingId);
	}

	/**
	 * Returns the notification recipient setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationRecipientSettingId the primary key of the notification recipient setting
	 * @return the notification recipient setting, or <code>null</code> if a notification recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationRecipientSetting fetchByPrimaryKey(
		long notificationRecipientSettingId) {

		return fetchByPrimaryKey((Serializable)notificationRecipientSettingId);
	}

	/**
	 * Returns all the notification recipient settings.
	 *
	 * @return the notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notification recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @return the range of notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findAll(
		int start, int end,
		OrderByComparator<NotificationRecipientSetting> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notification recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification recipient settings
	 * @param end the upper bound of the range of notification recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification recipient settings
	 */
	@Override
	public List<NotificationRecipientSetting> findAll(
		int start, int end,
		OrderByComparator<NotificationRecipientSetting> orderByComparator,
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

		List<NotificationRecipientSetting> list = null;

		if (useFinderCache) {
			list = (List<NotificationRecipientSetting>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_NOTIFICATIONRECIPIENTSETTING);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_NOTIFICATIONRECIPIENTSETTING;

				sql = sql.concat(
					NotificationRecipientSettingModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<NotificationRecipientSetting>)QueryUtil.list(
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
	 * Removes all the notification recipient settings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (NotificationRecipientSetting notificationRecipientSetting :
				findAll()) {

			remove(notificationRecipientSetting);
		}
	}

	/**
	 * Returns the number of notification recipient settings.
	 *
	 * @return the number of notification recipient settings
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_NOTIFICATIONRECIPIENTSETTING);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "notificationRecipientSettingId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_NOTIFICATIONRECIPIENTSETTING;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return NotificationRecipientSettingModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the notification recipient setting persistence.
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

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByNotificationRecipientId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByNotificationRecipientId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"notificationRecipientId"}, true);

		_finderPathWithoutPaginationFindByNotificationRecipientId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByNotificationRecipientId",
				new String[] {Long.class.getName()},
				new String[] {"notificationRecipientId"}, true);

		_finderPathCountByNotificationRecipientId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByNotificationRecipientId",
			new String[] {Long.class.getName()},
			new String[] {"notificationRecipientId"}, false);

		_finderPathFetchByNRI_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByNRI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"notificationRecipientId", "name"}, true);

		_finderPathCountByNRI_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByNRI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"notificationRecipientId", "name"}, false);

		NotificationRecipientSettingUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		NotificationRecipientSettingUtil.setPersistence(null);

		entityCache.removeCache(
			NotificationRecipientSettingImpl.class.getName());
	}

	@Override
	@Reference(
		target = NotificationPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = NotificationPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = NotificationPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_NOTIFICATIONRECIPIENTSETTING =
		"SELECT notificationRecipientSetting FROM NotificationRecipientSetting notificationRecipientSetting";

	private static final String _SQL_SELECT_NOTIFICATIONRECIPIENTSETTING_WHERE =
		"SELECT notificationRecipientSetting FROM NotificationRecipientSetting notificationRecipientSetting WHERE ";

	private static final String _SQL_COUNT_NOTIFICATIONRECIPIENTSETTING =
		"SELECT COUNT(notificationRecipientSetting) FROM NotificationRecipientSetting notificationRecipientSetting";

	private static final String _SQL_COUNT_NOTIFICATIONRECIPIENTSETTING_WHERE =
		"SELECT COUNT(notificationRecipientSetting) FROM NotificationRecipientSetting notificationRecipientSetting WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"notificationRecipientSetting.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No NotificationRecipientSetting exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No NotificationRecipientSetting exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationRecipientSettingPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

}