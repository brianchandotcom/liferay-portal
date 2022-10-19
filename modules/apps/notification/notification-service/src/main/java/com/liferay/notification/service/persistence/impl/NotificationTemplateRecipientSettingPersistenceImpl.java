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

import com.liferay.notification.exception.NoSuchNotificationTemplateRecipientSettingException;
import com.liferay.notification.model.NotificationTemplateRecipientSetting;
import com.liferay.notification.model.NotificationTemplateRecipientSettingTable;
import com.liferay.notification.model.impl.NotificationTemplateRecipientSettingImpl;
import com.liferay.notification.model.impl.NotificationTemplateRecipientSettingModelImpl;
import com.liferay.notification.service.persistence.NotificationTemplateRecipientSettingPersistence;
import com.liferay.notification.service.persistence.NotificationTemplateRecipientSettingUtil;
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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.Field;
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
 * The persistence implementation for the notification template recipient setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @generated
 */
@Component(
	service = {
		NotificationTemplateRecipientSettingPersistence.class,
		BasePersistence.class
	}
)
public class NotificationTemplateRecipientSettingPersistenceImpl
	extends BasePersistenceImpl<NotificationTemplateRecipientSetting>
	implements NotificationTemplateRecipientSettingPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>NotificationTemplateRecipientSettingUtil</code> to access the notification template recipient setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		NotificationTemplateRecipientSettingImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath
		_finderPathWithPaginationFindByNotificationTemplateRecipientId;
	private FinderPath
		_finderPathWithoutPaginationFindByNotificationTemplateRecipientId;
	private FinderPath _finderPathCountByNotificationTemplateRecipientId;

	/**
	 * Returns all the notification template recipient settings where notificationTemplateRecipientId = &#63;.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @return the matching notification template recipient settings
	 */
	@Override
	public List<NotificationTemplateRecipientSetting>
		findByNotificationTemplateRecipientId(
			long notificationTemplateRecipientId) {

		return findByNotificationTemplateRecipientId(
			notificationTemplateRecipientId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notification template recipient settings where notificationTemplateRecipientId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param start the lower bound of the range of notification template recipient settings
	 * @param end the upper bound of the range of notification template recipient settings (not inclusive)
	 * @return the range of matching notification template recipient settings
	 */
	@Override
	public List<NotificationTemplateRecipientSetting>
		findByNotificationTemplateRecipientId(
			long notificationTemplateRecipientId, int start, int end) {

		return findByNotificationTemplateRecipientId(
			notificationTemplateRecipientId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification template recipient settings where notificationTemplateRecipientId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param start the lower bound of the range of notification template recipient settings
	 * @param end the upper bound of the range of notification template recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notification template recipient settings
	 */
	@Override
	public List<NotificationTemplateRecipientSetting>
		findByNotificationTemplateRecipientId(
			long notificationTemplateRecipientId, int start, int end,
			OrderByComparator<NotificationTemplateRecipientSetting>
				orderByComparator) {

		return findByNotificationTemplateRecipientId(
			notificationTemplateRecipientId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the notification template recipient settings where notificationTemplateRecipientId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param start the lower bound of the range of notification template recipient settings
	 * @param end the upper bound of the range of notification template recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notification template recipient settings
	 */
	@Override
	public List<NotificationTemplateRecipientSetting>
		findByNotificationTemplateRecipientId(
			long notificationTemplateRecipientId, int start, int end,
			OrderByComparator<NotificationTemplateRecipientSetting>
				orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByNotificationTemplateRecipientId;
				finderArgs = new Object[] {notificationTemplateRecipientId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByNotificationTemplateRecipientId;
			finderArgs = new Object[] {
				notificationTemplateRecipientId, start, end, orderByComparator
			};
		}

		List<NotificationTemplateRecipientSetting> list = null;

		if (useFinderCache) {
			list =
				(List<NotificationTemplateRecipientSetting>)
					finderCache.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (NotificationTemplateRecipientSetting
						notificationTemplateRecipientSetting : list) {

					if (notificationTemplateRecipientId !=
							notificationTemplateRecipientSetting.
								getNotificationTemplateRecipientId()) {

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

			sb.append(_SQL_SELECT_NOTIFICATIONTEMPLATERECIPIENTSETTING_WHERE);

			sb.append(
				_FINDER_COLUMN_NOTIFICATIONTEMPLATERECIPIENTID_NOTIFICATIONTEMPLATERECIPIENTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					NotificationTemplateRecipientSettingModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationTemplateRecipientId);

				list =
					(List<NotificationTemplateRecipientSetting>)QueryUtil.list(
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
	 * Returns the first notification template recipient setting in the ordered set where notificationTemplateRecipientId = &#63;.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template recipient setting
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a matching notification template recipient setting could not be found
	 */
	@Override
	public NotificationTemplateRecipientSetting
			findByNotificationTemplateRecipientId_First(
				long notificationTemplateRecipientId,
				OrderByComparator<NotificationTemplateRecipientSetting>
					orderByComparator)
		throws NoSuchNotificationTemplateRecipientSettingException {

		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting =
				fetchByNotificationTemplateRecipientId_First(
					notificationTemplateRecipientId, orderByComparator);

		if (notificationTemplateRecipientSetting != null) {
			return notificationTemplateRecipientSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("notificationTemplateRecipientId=");
		sb.append(notificationTemplateRecipientId);

		sb.append("}");

		throw new NoSuchNotificationTemplateRecipientSettingException(
			sb.toString());
	}

	/**
	 * Returns the first notification template recipient setting in the ordered set where notificationTemplateRecipientId = &#63;.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification template recipient setting, or <code>null</code> if a matching notification template recipient setting could not be found
	 */
	@Override
	public NotificationTemplateRecipientSetting
		fetchByNotificationTemplateRecipientId_First(
			long notificationTemplateRecipientId,
			OrderByComparator<NotificationTemplateRecipientSetting>
				orderByComparator) {

		List<NotificationTemplateRecipientSetting> list =
			findByNotificationTemplateRecipientId(
				notificationTemplateRecipientId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last notification template recipient setting in the ordered set where notificationTemplateRecipientId = &#63;.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template recipient setting
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a matching notification template recipient setting could not be found
	 */
	@Override
	public NotificationTemplateRecipientSetting
			findByNotificationTemplateRecipientId_Last(
				long notificationTemplateRecipientId,
				OrderByComparator<NotificationTemplateRecipientSetting>
					orderByComparator)
		throws NoSuchNotificationTemplateRecipientSettingException {

		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting =
				fetchByNotificationTemplateRecipientId_Last(
					notificationTemplateRecipientId, orderByComparator);

		if (notificationTemplateRecipientSetting != null) {
			return notificationTemplateRecipientSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("notificationTemplateRecipientId=");
		sb.append(notificationTemplateRecipientId);

		sb.append("}");

		throw new NoSuchNotificationTemplateRecipientSettingException(
			sb.toString());
	}

	/**
	 * Returns the last notification template recipient setting in the ordered set where notificationTemplateRecipientId = &#63;.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification template recipient setting, or <code>null</code> if a matching notification template recipient setting could not be found
	 */
	@Override
	public NotificationTemplateRecipientSetting
		fetchByNotificationTemplateRecipientId_Last(
			long notificationTemplateRecipientId,
			OrderByComparator<NotificationTemplateRecipientSetting>
				orderByComparator) {

		int count = countByNotificationTemplateRecipientId(
			notificationTemplateRecipientId);

		if (count == 0) {
			return null;
		}

		List<NotificationTemplateRecipientSetting> list =
			findByNotificationTemplateRecipientId(
				notificationTemplateRecipientId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the notification template recipient settings before and after the current notification template recipient setting in the ordered set where notificationTemplateRecipientId = &#63;.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key of the current notification template recipient setting
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification template recipient setting
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a notification template recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationTemplateRecipientSetting[]
			findByNotificationTemplateRecipientId_PrevAndNext(
				long notificationTemplateRecipientSettingId,
				long notificationTemplateRecipientId,
				OrderByComparator<NotificationTemplateRecipientSetting>
					orderByComparator)
		throws NoSuchNotificationTemplateRecipientSettingException {

		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting = findByPrimaryKey(
				notificationTemplateRecipientSettingId);

		Session session = null;

		try {
			session = openSession();

			NotificationTemplateRecipientSetting[] array =
				new NotificationTemplateRecipientSettingImpl[3];

			array[0] = getByNotificationTemplateRecipientId_PrevAndNext(
				session, notificationTemplateRecipientSetting,
				notificationTemplateRecipientId, orderByComparator, true);

			array[1] = notificationTemplateRecipientSetting;

			array[2] = getByNotificationTemplateRecipientId_PrevAndNext(
				session, notificationTemplateRecipientSetting,
				notificationTemplateRecipientId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected NotificationTemplateRecipientSetting
		getByNotificationTemplateRecipientId_PrevAndNext(
			Session session,
			NotificationTemplateRecipientSetting
				notificationTemplateRecipientSetting,
			long notificationTemplateRecipientId,
			OrderByComparator<NotificationTemplateRecipientSetting>
				orderByComparator,
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

		sb.append(_SQL_SELECT_NOTIFICATIONTEMPLATERECIPIENTSETTING_WHERE);

		sb.append(
			_FINDER_COLUMN_NOTIFICATIONTEMPLATERECIPIENTID_NOTIFICATIONTEMPLATERECIPIENTID_2);

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
			sb.append(
				NotificationTemplateRecipientSettingModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(notificationTemplateRecipientId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						notificationTemplateRecipientSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<NotificationTemplateRecipientSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the notification template recipient settings where notificationTemplateRecipientId = &#63; from the database.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 */
	@Override
	public void removeByNotificationTemplateRecipientId(
		long notificationTemplateRecipientId) {

		for (NotificationTemplateRecipientSetting
				notificationTemplateRecipientSetting :
					findByNotificationTemplateRecipientId(
						notificationTemplateRecipientId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(notificationTemplateRecipientSetting);
		}
	}

	/**
	 * Returns the number of notification template recipient settings where notificationTemplateRecipientId = &#63;.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @return the number of matching notification template recipient settings
	 */
	@Override
	public int countByNotificationTemplateRecipientId(
		long notificationTemplateRecipientId) {

		FinderPath finderPath =
			_finderPathCountByNotificationTemplateRecipientId;

		Object[] finderArgs = new Object[] {notificationTemplateRecipientId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_NOTIFICATIONTEMPLATERECIPIENTSETTING_WHERE);

			sb.append(
				_FINDER_COLUMN_NOTIFICATIONTEMPLATERECIPIENTID_NOTIFICATIONTEMPLATERECIPIENTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationTemplateRecipientId);

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
		_FINDER_COLUMN_NOTIFICATIONTEMPLATERECIPIENTID_NOTIFICATIONTEMPLATERECIPIENTID_2 =
			"notificationTemplateRecipientSetting.notificationTemplateRecipientId = ?";

	private FinderPath _finderPathFetchByN_NTRI;
	private FinderPath _finderPathCountByN_NTRI;

	/**
	 * Returns the notification template recipient setting where notificationTemplateRecipientId = &#63; and name = &#63; or throws a <code>NoSuchNotificationTemplateRecipientSettingException</code> if it could not be found.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param name the name
	 * @return the matching notification template recipient setting
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a matching notification template recipient setting could not be found
	 */
	@Override
	public NotificationTemplateRecipientSetting findByN_NTRI(
			long notificationTemplateRecipientId, String name)
		throws NoSuchNotificationTemplateRecipientSettingException {

		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting = fetchByN_NTRI(
				notificationTemplateRecipientId, name);

		if (notificationTemplateRecipientSetting == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("notificationTemplateRecipientId=");
			sb.append(notificationTemplateRecipientId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchNotificationTemplateRecipientSettingException(
				sb.toString());
		}

		return notificationTemplateRecipientSetting;
	}

	/**
	 * Returns the notification template recipient setting where notificationTemplateRecipientId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param name the name
	 * @return the matching notification template recipient setting, or <code>null</code> if a matching notification template recipient setting could not be found
	 */
	@Override
	public NotificationTemplateRecipientSetting fetchByN_NTRI(
		long notificationTemplateRecipientId, String name) {

		return fetchByN_NTRI(notificationTemplateRecipientId, name, true);
	}

	/**
	 * Returns the notification template recipient setting where notificationTemplateRecipientId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching notification template recipient setting, or <code>null</code> if a matching notification template recipient setting could not be found
	 */
	@Override
	public NotificationTemplateRecipientSetting fetchByN_NTRI(
		long notificationTemplateRecipientId, String name,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {notificationTemplateRecipientId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByN_NTRI, finderArgs, this);
		}

		if (result instanceof NotificationTemplateRecipientSetting) {
			NotificationTemplateRecipientSetting
				notificationTemplateRecipientSetting =
					(NotificationTemplateRecipientSetting)result;

			if ((notificationTemplateRecipientId !=
					notificationTemplateRecipientSetting.
						getNotificationTemplateRecipientId()) ||
				!Objects.equals(
					name, notificationTemplateRecipientSetting.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_NOTIFICATIONTEMPLATERECIPIENTSETTING_WHERE);

			sb.append(_FINDER_COLUMN_N_NTRI_NOTIFICATIONTEMPLATERECIPIENTID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_NTRI_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_N_NTRI_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationTemplateRecipientId);

				if (bindName) {
					queryPos.add(name);
				}

				List<NotificationTemplateRecipientSetting> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByN_NTRI, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									notificationTemplateRecipientId, name
								};
							}

							_log.warn(
								"NotificationTemplateRecipientSettingPersistenceImpl.fetchByN_NTRI(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					NotificationTemplateRecipientSetting
						notificationTemplateRecipientSetting = list.get(0);

					result = notificationTemplateRecipientSetting;

					cacheResult(notificationTemplateRecipientSetting);
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
			return (NotificationTemplateRecipientSetting)result;
		}
	}

	/**
	 * Removes the notification template recipient setting where notificationTemplateRecipientId = &#63; and name = &#63; from the database.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param name the name
	 * @return the notification template recipient setting that was removed
	 */
	@Override
	public NotificationTemplateRecipientSetting removeByN_NTRI(
			long notificationTemplateRecipientId, String name)
		throws NoSuchNotificationTemplateRecipientSettingException {

		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting = findByN_NTRI(
				notificationTemplateRecipientId, name);

		return remove(notificationTemplateRecipientSetting);
	}

	/**
	 * Returns the number of notification template recipient settings where notificationTemplateRecipientId = &#63; and name = &#63;.
	 *
	 * @param notificationTemplateRecipientId the notification template recipient ID
	 * @param name the name
	 * @return the number of matching notification template recipient settings
	 */
	@Override
	public int countByN_NTRI(
		long notificationTemplateRecipientId, String name) {

		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByN_NTRI;

		Object[] finderArgs = new Object[] {
			notificationTemplateRecipientId, name
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_NOTIFICATIONTEMPLATERECIPIENTSETTING_WHERE);

			sb.append(_FINDER_COLUMN_N_NTRI_NOTIFICATIONTEMPLATERECIPIENTID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_NTRI_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_N_NTRI_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationTemplateRecipientId);

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

	private static final String
		_FINDER_COLUMN_N_NTRI_NOTIFICATIONTEMPLATERECIPIENTID_2 =
			"notificationTemplateRecipientSetting.notificationTemplateRecipientId = ? AND ";

	private static final String _FINDER_COLUMN_N_NTRI_NAME_2 =
		"notificationTemplateRecipientSetting.name = ?";

	private static final String _FINDER_COLUMN_N_NTRI_NAME_3 =
		"(notificationTemplateRecipientSetting.name IS NULL OR notificationTemplateRecipientSetting.name = '')";

	public NotificationTemplateRecipientSettingPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"notificationTemplateRecipientSettingId",
			"NTemplateRecipientSettingId");
		dbColumnNames.put(
			"notificationTemplateRecipientId", "NTemplateRecipientId");

		setDBColumnNames(dbColumnNames);

		setModelClass(NotificationTemplateRecipientSetting.class);

		setModelImplClass(NotificationTemplateRecipientSettingImpl.class);
		setModelPKClass(long.class);

		setTable(NotificationTemplateRecipientSettingTable.INSTANCE);
	}

	/**
	 * Caches the notification template recipient setting in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateRecipientSetting the notification template recipient setting
	 */
	@Override
	public void cacheResult(
		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting) {

		entityCache.putResult(
			NotificationTemplateRecipientSettingImpl.class,
			notificationTemplateRecipientSetting.getPrimaryKey(),
			notificationTemplateRecipientSetting);

		finderCache.putResult(
			_finderPathFetchByN_NTRI,
			new Object[] {
				notificationTemplateRecipientSetting.
					getNotificationTemplateRecipientId(),
				notificationTemplateRecipientSetting.getName()
			},
			notificationTemplateRecipientSetting);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the notification template recipient settings in the entity cache if it is enabled.
	 *
	 * @param notificationTemplateRecipientSettings the notification template recipient settings
	 */
	@Override
	public void cacheResult(
		List<NotificationTemplateRecipientSetting>
			notificationTemplateRecipientSettings) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (notificationTemplateRecipientSettings.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (NotificationTemplateRecipientSetting
				notificationTemplateRecipientSetting :
					notificationTemplateRecipientSettings) {

			if (entityCache.getResult(
					NotificationTemplateRecipientSettingImpl.class,
					notificationTemplateRecipientSetting.getPrimaryKey()) ==
						null) {

				cacheResult(notificationTemplateRecipientSetting);
			}
		}
	}

	/**
	 * Clears the cache for all notification template recipient settings.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(NotificationTemplateRecipientSettingImpl.class);

		finderCache.clearCache(NotificationTemplateRecipientSettingImpl.class);
	}

	/**
	 * Clears the cache for the notification template recipient setting.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting) {

		entityCache.removeResult(
			NotificationTemplateRecipientSettingImpl.class,
			notificationTemplateRecipientSetting);
	}

	@Override
	public void clearCache(
		List<NotificationTemplateRecipientSetting>
			notificationTemplateRecipientSettings) {

		for (NotificationTemplateRecipientSetting
				notificationTemplateRecipientSetting :
					notificationTemplateRecipientSettings) {

			entityCache.removeResult(
				NotificationTemplateRecipientSettingImpl.class,
				notificationTemplateRecipientSetting);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(NotificationTemplateRecipientSettingImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				NotificationTemplateRecipientSettingImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		NotificationTemplateRecipientSettingModelImpl
			notificationTemplateRecipientSettingModelImpl) {

		Object[] args = new Object[] {
			notificationTemplateRecipientSettingModelImpl.
				getNotificationTemplateRecipientId(),
			notificationTemplateRecipientSettingModelImpl.getName()
		};

		finderCache.putResult(_finderPathCountByN_NTRI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByN_NTRI, args,
			notificationTemplateRecipientSettingModelImpl);
	}

	/**
	 * Creates a new notification template recipient setting with the primary key. Does not add the notification template recipient setting to the database.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key for the new notification template recipient setting
	 * @return the new notification template recipient setting
	 */
	@Override
	public NotificationTemplateRecipientSetting create(
		long notificationTemplateRecipientSettingId) {

		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting =
				new NotificationTemplateRecipientSettingImpl();

		notificationTemplateRecipientSetting.setNew(true);
		notificationTemplateRecipientSetting.setPrimaryKey(
			notificationTemplateRecipientSettingId);

		notificationTemplateRecipientSetting.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return notificationTemplateRecipientSetting;
	}

	/**
	 * Removes the notification template recipient setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key of the notification template recipient setting
	 * @return the notification template recipient setting that was removed
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a notification template recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationTemplateRecipientSetting remove(
			long notificationTemplateRecipientSettingId)
		throws NoSuchNotificationTemplateRecipientSettingException {

		return remove((Serializable)notificationTemplateRecipientSettingId);
	}

	/**
	 * Removes the notification template recipient setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the notification template recipient setting
	 * @return the notification template recipient setting that was removed
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a notification template recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationTemplateRecipientSetting remove(Serializable primaryKey)
		throws NoSuchNotificationTemplateRecipientSettingException {

		Session session = null;

		try {
			session = openSession();

			NotificationTemplateRecipientSetting
				notificationTemplateRecipientSetting =
					(NotificationTemplateRecipientSetting)session.get(
						NotificationTemplateRecipientSettingImpl.class,
						primaryKey);

			if (notificationTemplateRecipientSetting == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationTemplateRecipientSettingException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(notificationTemplateRecipientSetting);
		}
		catch (NoSuchNotificationTemplateRecipientSettingException
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
	protected NotificationTemplateRecipientSetting removeImpl(
		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(notificationTemplateRecipientSetting)) {
				notificationTemplateRecipientSetting =
					(NotificationTemplateRecipientSetting)session.get(
						NotificationTemplateRecipientSettingImpl.class,
						notificationTemplateRecipientSetting.
							getPrimaryKeyObj());
			}

			if (notificationTemplateRecipientSetting != null) {
				session.delete(notificationTemplateRecipientSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (notificationTemplateRecipientSetting != null) {
			clearCache(notificationTemplateRecipientSetting);
		}

		return notificationTemplateRecipientSetting;
	}

	@Override
	public NotificationTemplateRecipientSetting updateImpl(
		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting) {

		boolean isNew = notificationTemplateRecipientSetting.isNew();

		if (!(notificationTemplateRecipientSetting instanceof
				NotificationTemplateRecipientSettingModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					notificationTemplateRecipientSetting.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					notificationTemplateRecipientSetting);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in notificationTemplateRecipientSetting proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom NotificationTemplateRecipientSetting implementation " +
					notificationTemplateRecipientSetting.getClass());
		}

		NotificationTemplateRecipientSettingModelImpl
			notificationTemplateRecipientSettingModelImpl =
				(NotificationTemplateRecipientSettingModelImpl)
					notificationTemplateRecipientSetting;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(notificationTemplateRecipientSetting.getCreateDate() == null)) {

			if (serviceContext == null) {
				notificationTemplateRecipientSetting.setCreateDate(date);
			}
			else {
				notificationTemplateRecipientSetting.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!notificationTemplateRecipientSettingModelImpl.
				hasSetModifiedDate()) {

			if (serviceContext == null) {
				notificationTemplateRecipientSetting.setModifiedDate(date);
			}
			else {
				notificationTemplateRecipientSetting.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(notificationTemplateRecipientSetting);
			}
			else {
				notificationTemplateRecipientSetting =
					(NotificationTemplateRecipientSetting)session.merge(
						notificationTemplateRecipientSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			NotificationTemplateRecipientSettingImpl.class,
			notificationTemplateRecipientSettingModelImpl, false, true);

		cacheUniqueFindersCache(notificationTemplateRecipientSettingModelImpl);

		if (isNew) {
			notificationTemplateRecipientSetting.setNew(false);
		}

		notificationTemplateRecipientSetting.resetOriginalValues();

		return notificationTemplateRecipientSetting;
	}

	/**
	 * Returns the notification template recipient setting with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the notification template recipient setting
	 * @return the notification template recipient setting
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a notification template recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationTemplateRecipientSetting findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchNotificationTemplateRecipientSettingException {

		NotificationTemplateRecipientSetting
			notificationTemplateRecipientSetting = fetchByPrimaryKey(
				primaryKey);

		if (notificationTemplateRecipientSetting == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationTemplateRecipientSettingException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return notificationTemplateRecipientSetting;
	}

	/**
	 * Returns the notification template recipient setting with the primary key or throws a <code>NoSuchNotificationTemplateRecipientSettingException</code> if it could not be found.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key of the notification template recipient setting
	 * @return the notification template recipient setting
	 * @throws NoSuchNotificationTemplateRecipientSettingException if a notification template recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationTemplateRecipientSetting findByPrimaryKey(
			long notificationTemplateRecipientSettingId)
		throws NoSuchNotificationTemplateRecipientSettingException {

		return findByPrimaryKey(
			(Serializable)notificationTemplateRecipientSettingId);
	}

	/**
	 * Returns the notification template recipient setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationTemplateRecipientSettingId the primary key of the notification template recipient setting
	 * @return the notification template recipient setting, or <code>null</code> if a notification template recipient setting with the primary key could not be found
	 */
	@Override
	public NotificationTemplateRecipientSetting fetchByPrimaryKey(
		long notificationTemplateRecipientSettingId) {

		return fetchByPrimaryKey(
			(Serializable)notificationTemplateRecipientSettingId);
	}

	/**
	 * Returns all the notification template recipient settings.
	 *
	 * @return the notification template recipient settings
	 */
	@Override
	public List<NotificationTemplateRecipientSetting> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notification template recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template recipient settings
	 * @param end the upper bound of the range of notification template recipient settings (not inclusive)
	 * @return the range of notification template recipient settings
	 */
	@Override
	public List<NotificationTemplateRecipientSetting> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the notification template recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template recipient settings
	 * @param end the upper bound of the range of notification template recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notification template recipient settings
	 */
	@Override
	public List<NotificationTemplateRecipientSetting> findAll(
		int start, int end,
		OrderByComparator<NotificationTemplateRecipientSetting>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notification template recipient settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationTemplateRecipientSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification template recipient settings
	 * @param end the upper bound of the range of notification template recipient settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notification template recipient settings
	 */
	@Override
	public List<NotificationTemplateRecipientSetting> findAll(
		int start, int end,
		OrderByComparator<NotificationTemplateRecipientSetting>
			orderByComparator,
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

		List<NotificationTemplateRecipientSetting> list = null;

		if (useFinderCache) {
			list =
				(List<NotificationTemplateRecipientSetting>)
					finderCache.getResult(finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_NOTIFICATIONTEMPLATERECIPIENTSETTING);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_NOTIFICATIONTEMPLATERECIPIENTSETTING;

				sql = sql.concat(
					NotificationTemplateRecipientSettingModelImpl.
						ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list =
					(List<NotificationTemplateRecipientSetting>)QueryUtil.list(
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
	 * Removes all the notification template recipient settings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (NotificationTemplateRecipientSetting
				notificationTemplateRecipientSetting : findAll()) {

			remove(notificationTemplateRecipientSetting);
		}
	}

	/**
	 * Returns the number of notification template recipient settings.
	 *
	 * @return the number of notification template recipient settings
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
					_SQL_COUNT_NOTIFICATIONTEMPLATERECIPIENTSETTING);

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
		return "NTemplateRecipientSettingId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_NOTIFICATIONTEMPLATERECIPIENTSETTING;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return NotificationTemplateRecipientSettingModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the notification template recipient setting persistence.
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

		_finderPathWithPaginationFindByNotificationTemplateRecipientId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByNotificationTemplateRecipientId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"NTemplateRecipientId"}, true);

		_finderPathWithoutPaginationFindByNotificationTemplateRecipientId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByNotificationTemplateRecipientId",
				new String[] {Long.class.getName()},
				new String[] {"NTemplateRecipientId"}, true);

		_finderPathCountByNotificationTemplateRecipientId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByNotificationTemplateRecipientId",
			new String[] {Long.class.getName()},
			new String[] {"NTemplateRecipientId"}, false);

		_finderPathFetchByN_NTRI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByN_NTRI",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"NTemplateRecipientId", "name"}, true);

		_finderPathCountByN_NTRI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_NTRI",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"NTemplateRecipientId", "name"}, false);

		_setNotificationTemplateRecipientSettingUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setNotificationTemplateRecipientSettingUtilPersistence(null);

		entityCache.removeCache(
			NotificationTemplateRecipientSettingImpl.class.getName());
	}

	private void _setNotificationTemplateRecipientSettingUtilPersistence(
		NotificationTemplateRecipientSettingPersistence
			notificationTemplateRecipientSettingPersistence) {

		try {
			Field field =
				NotificationTemplateRecipientSettingUtil.class.getDeclaredField(
					"_persistence");

			field.setAccessible(true);

			field.set(null, notificationTemplateRecipientSettingPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
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

	private static final String
		_SQL_SELECT_NOTIFICATIONTEMPLATERECIPIENTSETTING =
			"SELECT notificationTemplateRecipientSetting FROM NotificationTemplateRecipientSetting notificationTemplateRecipientSetting";

	private static final String
		_SQL_SELECT_NOTIFICATIONTEMPLATERECIPIENTSETTING_WHERE =
			"SELECT notificationTemplateRecipientSetting FROM NotificationTemplateRecipientSetting notificationTemplateRecipientSetting WHERE ";

	private static final String
		_SQL_COUNT_NOTIFICATIONTEMPLATERECIPIENTSETTING =
			"SELECT COUNT(notificationTemplateRecipientSetting) FROM NotificationTemplateRecipientSetting notificationTemplateRecipientSetting";

	private static final String
		_SQL_COUNT_NOTIFICATIONTEMPLATERECIPIENTSETTING_WHERE =
			"SELECT COUNT(notificationTemplateRecipientSetting) FROM NotificationTemplateRecipientSetting notificationTemplateRecipientSetting WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"notificationTemplateRecipientSetting.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No NotificationTemplateRecipientSetting exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No NotificationTemplateRecipientSetting exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationTemplateRecipientSettingPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {
			"notificationTemplateRecipientSettingId",
			"notificationTemplateRecipientId"
		});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}