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

package com.liferay.notifications.admin.service.persistence.impl;

import com.liferay.notifications.admin.exception.NoSuchNotificationsQueueEntryException;
import com.liferay.notifications.admin.model.NotificationsQueueEntry;
import com.liferay.notifications.admin.model.NotificationsQueueEntryTable;
import com.liferay.notifications.admin.model.impl.NotificationsQueueEntryImpl;
import com.liferay.notifications.admin.model.impl.NotificationsQueueEntryModelImpl;
import com.liferay.notifications.admin.service.persistence.NotificationsQueueEntryPersistence;
import com.liferay.notifications.admin.service.persistence.NotificationsQueueEntryUtil;
import com.liferay.notifications.admin.service.persistence.impl.constants.NotificationsPersistenceConstants;
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

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the notifications queue entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @generated
 */
@Component(
	service = {NotificationsQueueEntryPersistence.class, BasePersistence.class}
)
public class NotificationsQueueEntryPersistenceImpl
	extends BasePersistenceImpl<NotificationsQueueEntry>
	implements NotificationsQueueEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>NotificationsQueueEntryUtil</code> to access the notifications queue entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		NotificationsQueueEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the notifications queue entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notifications queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @return the range of matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<NotificationsQueueEntry> list = null;

		if (useFinderCache) {
			list = (List<NotificationsQueueEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (NotificationsQueueEntry notificationsQueueEntry : list) {
					if (groupId != notificationsQueueEntry.getGroupId()) {
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

			sb.append(_SQL_SELECT_NOTIFICATIONSQUEUEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(NotificationsQueueEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<NotificationsQueueEntry>)QueryUtil.list(
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
	 * Returns the first notifications queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry findByGroupId_First(
			long groupId,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (notificationsQueueEntry != null) {
			return notificationsQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchNotificationsQueueEntryException(sb.toString());
	}

	/**
	 * Returns the first notifications queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		List<NotificationsQueueEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry findByGroupId_Last(
			long groupId,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (notificationsQueueEntry != null) {
			return notificationsQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchNotificationsQueueEntryException(sb.toString());
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry fetchByGroupId_Last(
		long groupId,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<NotificationsQueueEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the notifications queue entries before and after the current notifications queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param notificationsQueueEntryId the primary key of the current notifications queue entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	@Override
	public NotificationsQueueEntry[] findByGroupId_PrevAndNext(
			long notificationsQueueEntryId, long groupId,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry = findByPrimaryKey(
			notificationsQueueEntryId);

		Session session = null;

		try {
			session = openSession();

			NotificationsQueueEntry[] array =
				new NotificationsQueueEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, notificationsQueueEntry, groupId, orderByComparator,
				true);

			array[1] = notificationsQueueEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, notificationsQueueEntry, groupId, orderByComparator,
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

	protected NotificationsQueueEntry getByGroupId_PrevAndNext(
		Session session, NotificationsQueueEntry notificationsQueueEntry,
		long groupId,
		OrderByComparator<NotificationsQueueEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_NOTIFICATIONSQUEUEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			sb.append(NotificationsQueueEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						notificationsQueueEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<NotificationsQueueEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the notifications queue entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (NotificationsQueueEntry notificationsQueueEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(notificationsQueueEntry);
		}
	}

	/**
	 * Returns the number of notifications queue entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching notifications queue entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_NOTIFICATIONSQUEUEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"notificationsQueueEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByNotificationsTemplateId;
	private FinderPath
		_finderPathWithoutPaginationFindByNotificationsTemplateId;
	private FinderPath _finderPathCountByNotificationsTemplateId;

	/**
	 * Returns all the notifications queue entries where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @return the matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByNotificationsTemplateId(
		long notificationsTemplateId) {

		return findByNotificationsTemplateId(
			notificationsTemplateId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the notifications queue entries where notificationsTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @return the range of matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByNotificationsTemplateId(
		long notificationsTemplateId, int start, int end) {

		return findByNotificationsTemplateId(
			notificationsTemplateId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where notificationsTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByNotificationsTemplateId(
		long notificationsTemplateId, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return findByNotificationsTemplateId(
			notificationsTemplateId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where notificationsTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByNotificationsTemplateId(
		long notificationsTemplateId, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByNotificationsTemplateId;
				finderArgs = new Object[] {notificationsTemplateId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByNotificationsTemplateId;
			finderArgs = new Object[] {
				notificationsTemplateId, start, end, orderByComparator
			};
		}

		List<NotificationsQueueEntry> list = null;

		if (useFinderCache) {
			list = (List<NotificationsQueueEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (NotificationsQueueEntry notificationsQueueEntry : list) {
					if (notificationsTemplateId !=
							notificationsQueueEntry.
								getNotificationsTemplateId()) {

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

			sb.append(_SQL_SELECT_NOTIFICATIONSQUEUEENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_NOTIFICATIONSTEMPLATEID_NOTIFICATIONSTEMPLATEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(NotificationsQueueEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationsTemplateId);

				list = (List<NotificationsQueueEntry>)QueryUtil.list(
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
	 * Returns the first notifications queue entry in the ordered set where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry findByNotificationsTemplateId_First(
			long notificationsTemplateId,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry =
			fetchByNotificationsTemplateId_First(
				notificationsTemplateId, orderByComparator);

		if (notificationsQueueEntry != null) {
			return notificationsQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("notificationsTemplateId=");
		sb.append(notificationsTemplateId);

		sb.append("}");

		throw new NoSuchNotificationsQueueEntryException(sb.toString());
	}

	/**
	 * Returns the first notifications queue entry in the ordered set where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry fetchByNotificationsTemplateId_First(
		long notificationsTemplateId,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		List<NotificationsQueueEntry> list = findByNotificationsTemplateId(
			notificationsTemplateId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry findByNotificationsTemplateId_Last(
			long notificationsTemplateId,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry =
			fetchByNotificationsTemplateId_Last(
				notificationsTemplateId, orderByComparator);

		if (notificationsQueueEntry != null) {
			return notificationsQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("notificationsTemplateId=");
		sb.append(notificationsTemplateId);

		sb.append("}");

		throw new NoSuchNotificationsQueueEntryException(sb.toString());
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry fetchByNotificationsTemplateId_Last(
		long notificationsTemplateId,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		int count = countByNotificationsTemplateId(notificationsTemplateId);

		if (count == 0) {
			return null;
		}

		List<NotificationsQueueEntry> list = findByNotificationsTemplateId(
			notificationsTemplateId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the notifications queue entries before and after the current notifications queue entry in the ordered set where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsQueueEntryId the primary key of the current notifications queue entry
	 * @param notificationsTemplateId the notifications template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	@Override
	public NotificationsQueueEntry[] findByNotificationsTemplateId_PrevAndNext(
			long notificationsQueueEntryId, long notificationsTemplateId,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry = findByPrimaryKey(
			notificationsQueueEntryId);

		Session session = null;

		try {
			session = openSession();

			NotificationsQueueEntry[] array =
				new NotificationsQueueEntryImpl[3];

			array[0] = getByNotificationsTemplateId_PrevAndNext(
				session, notificationsQueueEntry, notificationsTemplateId,
				orderByComparator, true);

			array[1] = notificationsQueueEntry;

			array[2] = getByNotificationsTemplateId_PrevAndNext(
				session, notificationsQueueEntry, notificationsTemplateId,
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

	protected NotificationsQueueEntry getByNotificationsTemplateId_PrevAndNext(
		Session session, NotificationsQueueEntry notificationsQueueEntry,
		long notificationsTemplateId,
		OrderByComparator<NotificationsQueueEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_NOTIFICATIONSQUEUEENTRY_WHERE);

		sb.append(
			_FINDER_COLUMN_NOTIFICATIONSTEMPLATEID_NOTIFICATIONSTEMPLATEID_2);

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
			sb.append(NotificationsQueueEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(notificationsTemplateId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						notificationsQueueEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<NotificationsQueueEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the notifications queue entries where notificationsTemplateId = &#63; from the database.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 */
	@Override
	public void removeByNotificationsTemplateId(long notificationsTemplateId) {
		for (NotificationsQueueEntry notificationsQueueEntry :
				findByNotificationsTemplateId(
					notificationsTemplateId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(notificationsQueueEntry);
		}
	}

	/**
	 * Returns the number of notifications queue entries where notificationsTemplateId = &#63;.
	 *
	 * @param notificationsTemplateId the notifications template ID
	 * @return the number of matching notifications queue entries
	 */
	@Override
	public int countByNotificationsTemplateId(long notificationsTemplateId) {
		FinderPath finderPath = _finderPathCountByNotificationsTemplateId;

		Object[] finderArgs = new Object[] {notificationsTemplateId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_NOTIFICATIONSQUEUEENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_NOTIFICATIONSTEMPLATEID_NOTIFICATIONSTEMPLATEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(notificationsTemplateId);

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
		_FINDER_COLUMN_NOTIFICATIONSTEMPLATEID_NOTIFICATIONSTEMPLATEID_2 =
			"notificationsQueueEntry.notificationsTemplateId = ?";

	private FinderPath _finderPathWithPaginationFindBySent;
	private FinderPath _finderPathWithoutPaginationFindBySent;
	private FinderPath _finderPathCountBySent;

	/**
	 * Returns all the notifications queue entries where sent = &#63;.
	 *
	 * @param sent the sent
	 * @return the matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findBySent(boolean sent) {
		return findBySent(sent, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notifications queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @return the range of matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findBySent(
		boolean sent, int start, int end) {

		return findBySent(sent, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findBySent(
		boolean sent, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return findBySent(sent, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findBySent(
		boolean sent, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindBySent;
				finderArgs = new Object[] {sent};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindBySent;
			finderArgs = new Object[] {sent, start, end, orderByComparator};
		}

		List<NotificationsQueueEntry> list = null;

		if (useFinderCache) {
			list = (List<NotificationsQueueEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (NotificationsQueueEntry notificationsQueueEntry : list) {
					if (sent != notificationsQueueEntry.isSent()) {
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

			sb.append(_SQL_SELECT_NOTIFICATIONSQUEUEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_SENT_SENT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(NotificationsQueueEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(sent);

				list = (List<NotificationsQueueEntry>)QueryUtil.list(
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
	 * Returns the first notifications queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry findBySent_First(
			boolean sent,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry = fetchBySent_First(
			sent, orderByComparator);

		if (notificationsQueueEntry != null) {
			return notificationsQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sent=");
		sb.append(sent);

		sb.append("}");

		throw new NoSuchNotificationsQueueEntryException(sb.toString());
	}

	/**
	 * Returns the first notifications queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry fetchBySent_First(
		boolean sent,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		List<NotificationsQueueEntry> list = findBySent(
			sent, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry findBySent_Last(
			boolean sent,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry = fetchBySent_Last(
			sent, orderByComparator);

		if (notificationsQueueEntry != null) {
			return notificationsQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sent=");
		sb.append(sent);

		sb.append("}");

		throw new NoSuchNotificationsQueueEntryException(sb.toString());
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry fetchBySent_Last(
		boolean sent,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		int count = countBySent(sent);

		if (count == 0) {
			return null;
		}

		List<NotificationsQueueEntry> list = findBySent(
			sent, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the notifications queue entries before and after the current notifications queue entry in the ordered set where sent = &#63;.
	 *
	 * @param notificationsQueueEntryId the primary key of the current notifications queue entry
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	@Override
	public NotificationsQueueEntry[] findBySent_PrevAndNext(
			long notificationsQueueEntryId, boolean sent,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry = findByPrimaryKey(
			notificationsQueueEntryId);

		Session session = null;

		try {
			session = openSession();

			NotificationsQueueEntry[] array =
				new NotificationsQueueEntryImpl[3];

			array[0] = getBySent_PrevAndNext(
				session, notificationsQueueEntry, sent, orderByComparator,
				true);

			array[1] = notificationsQueueEntry;

			array[2] = getBySent_PrevAndNext(
				session, notificationsQueueEntry, sent, orderByComparator,
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

	protected NotificationsQueueEntry getBySent_PrevAndNext(
		Session session, NotificationsQueueEntry notificationsQueueEntry,
		boolean sent,
		OrderByComparator<NotificationsQueueEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_NOTIFICATIONSQUEUEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_SENT_SENT_2);

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
			sb.append(NotificationsQueueEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(sent);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						notificationsQueueEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<NotificationsQueueEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the notifications queue entries where sent = &#63; from the database.
	 *
	 * @param sent the sent
	 */
	@Override
	public void removeBySent(boolean sent) {
		for (NotificationsQueueEntry notificationsQueueEntry :
				findBySent(sent, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(notificationsQueueEntry);
		}
	}

	/**
	 * Returns the number of notifications queue entries where sent = &#63;.
	 *
	 * @param sent the sent
	 * @return the number of matching notifications queue entries
	 */
	@Override
	public int countBySent(boolean sent) {
		FinderPath finderPath = _finderPathCountBySent;

		Object[] finderArgs = new Object[] {sent};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_NOTIFICATIONSQUEUEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_SENT_SENT_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(sent);

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

	private static final String _FINDER_COLUMN_SENT_SENT_2 =
		"notificationsQueueEntry.sent = ?";

	private FinderPath _finderPathWithPaginationFindByLtSentDate;
	private FinderPath _finderPathWithPaginationCountByLtSentDate;

	/**
	 * Returns all the notifications queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByLtSentDate(Date sentDate) {
		return findByLtSentDate(
			sentDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notifications queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @return the range of matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end) {

		return findByLtSentDate(sentDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return findByLtSentDate(sentDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtSentDate;
		finderArgs = new Object[] {
			_getTime(sentDate), start, end, orderByComparator
		};

		List<NotificationsQueueEntry> list = null;

		if (useFinderCache) {
			list = (List<NotificationsQueueEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (NotificationsQueueEntry notificationsQueueEntry : list) {
					if (sentDate.getTime() <=
							notificationsQueueEntry.getSentDate(
							).getTime()) {

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

			sb.append(_SQL_SELECT_NOTIFICATIONSQUEUEENTRY_WHERE);

			boolean bindSentDate = false;

			if (sentDate == null) {
				sb.append(_FINDER_COLUMN_LTSENTDATE_SENTDATE_1);
			}
			else {
				bindSentDate = true;

				sb.append(_FINDER_COLUMN_LTSENTDATE_SENTDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(NotificationsQueueEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSentDate) {
					queryPos.add(new Timestamp(sentDate.getTime()));
				}

				list = (List<NotificationsQueueEntry>)QueryUtil.list(
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
	 * Returns the first notifications queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry findByLtSentDate_First(
			Date sentDate,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry =
			fetchByLtSentDate_First(sentDate, orderByComparator);

		if (notificationsQueueEntry != null) {
			return notificationsQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sentDate<");
		sb.append(sentDate);

		sb.append("}");

		throw new NoSuchNotificationsQueueEntryException(sb.toString());
	}

	/**
	 * Returns the first notifications queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry fetchByLtSentDate_First(
		Date sentDate,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		List<NotificationsQueueEntry> list = findByLtSentDate(
			sentDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry findByLtSentDate_Last(
			Date sentDate,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry =
			fetchByLtSentDate_Last(sentDate, orderByComparator);

		if (notificationsQueueEntry != null) {
			return notificationsQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sentDate<");
		sb.append(sentDate);

		sb.append("}");

		throw new NoSuchNotificationsQueueEntryException(sb.toString());
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry fetchByLtSentDate_Last(
		Date sentDate,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		int count = countByLtSentDate(sentDate);

		if (count == 0) {
			return null;
		}

		List<NotificationsQueueEntry> list = findByLtSentDate(
			sentDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the notifications queue entries before and after the current notifications queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param notificationsQueueEntryId the primary key of the current notifications queue entry
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	@Override
	public NotificationsQueueEntry[] findByLtSentDate_PrevAndNext(
			long notificationsQueueEntryId, Date sentDate,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry = findByPrimaryKey(
			notificationsQueueEntryId);

		Session session = null;

		try {
			session = openSession();

			NotificationsQueueEntry[] array =
				new NotificationsQueueEntryImpl[3];

			array[0] = getByLtSentDate_PrevAndNext(
				session, notificationsQueueEntry, sentDate, orderByComparator,
				true);

			array[1] = notificationsQueueEntry;

			array[2] = getByLtSentDate_PrevAndNext(
				session, notificationsQueueEntry, sentDate, orderByComparator,
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

	protected NotificationsQueueEntry getByLtSentDate_PrevAndNext(
		Session session, NotificationsQueueEntry notificationsQueueEntry,
		Date sentDate,
		OrderByComparator<NotificationsQueueEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_NOTIFICATIONSQUEUEENTRY_WHERE);

		boolean bindSentDate = false;

		if (sentDate == null) {
			sb.append(_FINDER_COLUMN_LTSENTDATE_SENTDATE_1);
		}
		else {
			bindSentDate = true;

			sb.append(_FINDER_COLUMN_LTSENTDATE_SENTDATE_2);
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
			sb.append(NotificationsQueueEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindSentDate) {
			queryPos.add(new Timestamp(sentDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						notificationsQueueEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<NotificationsQueueEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the notifications queue entries where sentDate &lt; &#63; from the database.
	 *
	 * @param sentDate the sent date
	 */
	@Override
	public void removeByLtSentDate(Date sentDate) {
		for (NotificationsQueueEntry notificationsQueueEntry :
				findByLtSentDate(
					sentDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(notificationsQueueEntry);
		}
	}

	/**
	 * Returns the number of notifications queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the number of matching notifications queue entries
	 */
	@Override
	public int countByLtSentDate(Date sentDate) {
		FinderPath finderPath = _finderPathWithPaginationCountByLtSentDate;

		Object[] finderArgs = new Object[] {_getTime(sentDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_NOTIFICATIONSQUEUEENTRY_WHERE);

			boolean bindSentDate = false;

			if (sentDate == null) {
				sb.append(_FINDER_COLUMN_LTSENTDATE_SENTDATE_1);
			}
			else {
				bindSentDate = true;

				sb.append(_FINDER_COLUMN_LTSENTDATE_SENTDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSentDate) {
					queryPos.add(new Timestamp(sentDate.getTime()));
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

	private static final String _FINDER_COLUMN_LTSENTDATE_SENTDATE_1 =
		"notificationsQueueEntry.sentDate IS NULL";

	private static final String _FINDER_COLUMN_LTSENTDATE_SENTDATE_2 =
		"notificationsQueueEntry.sentDate < ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C_S;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C_S;
	private FinderPath _finderPathCountByG_C_C_S;

	/**
	 * Returns all the notifications queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @return the matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent) {

		return findByG_C_C_S(
			groupId, classNameId, classPK, sent, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notifications queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @return the range of matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end) {

		return findByG_C_C_S(
			groupId, classNameId, classPK, sent, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end, OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return findByG_C_C_S(
			groupId, classNameId, classPK, sent, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end, OrderByComparator<NotificationsQueueEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C_S;
				finderArgs = new Object[] {groupId, classNameId, classPK, sent};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C_C_S;
			finderArgs = new Object[] {
				groupId, classNameId, classPK, sent, start, end,
				orderByComparator
			};
		}

		List<NotificationsQueueEntry> list = null;

		if (useFinderCache) {
			list = (List<NotificationsQueueEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (NotificationsQueueEntry notificationsQueueEntry : list) {
					if ((groupId != notificationsQueueEntry.getGroupId()) ||
						(classNameId !=
							notificationsQueueEntry.getClassNameId()) ||
						(classPK != notificationsQueueEntry.getClassPK()) ||
						(sent != notificationsQueueEntry.isSent())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_NOTIFICATIONSQUEUEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_S_CLASSPK_2);

			sb.append(_FINDER_COLUMN_G_C_C_S_SENT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(NotificationsQueueEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(sent);

				list = (List<NotificationsQueueEntry>)QueryUtil.list(
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
	 * Returns the first notifications queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry findByG_C_C_S_First(
			long groupId, long classNameId, long classPK, boolean sent,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry = fetchByG_C_C_S_First(
			groupId, classNameId, classPK, sent, orderByComparator);

		if (notificationsQueueEntry != null) {
			return notificationsQueueEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", sent=");
		sb.append(sent);

		sb.append("}");

		throw new NoSuchNotificationsQueueEntryException(sb.toString());
	}

	/**
	 * Returns the first notifications queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry fetchByG_C_C_S_First(
		long groupId, long classNameId, long classPK, boolean sent,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		List<NotificationsQueueEntry> list = findByG_C_C_S(
			groupId, classNameId, classPK, sent, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry findByG_C_C_S_Last(
			long groupId, long classNameId, long classPK, boolean sent,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry = fetchByG_C_C_S_Last(
			groupId, classNameId, classPK, sent, orderByComparator);

		if (notificationsQueueEntry != null) {
			return notificationsQueueEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", sent=");
		sb.append(sent);

		sb.append("}");

		throw new NoSuchNotificationsQueueEntryException(sb.toString());
	}

	/**
	 * Returns the last notifications queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications queue entry, or <code>null</code> if a matching notifications queue entry could not be found
	 */
	@Override
	public NotificationsQueueEntry fetchByG_C_C_S_Last(
		long groupId, long classNameId, long classPK, boolean sent,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		int count = countByG_C_C_S(groupId, classNameId, classPK, sent);

		if (count == 0) {
			return null;
		}

		List<NotificationsQueueEntry> list = findByG_C_C_S(
			groupId, classNameId, classPK, sent, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the notifications queue entries before and after the current notifications queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param notificationsQueueEntryId the primary key of the current notifications queue entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	@Override
	public NotificationsQueueEntry[] findByG_C_C_S_PrevAndNext(
			long notificationsQueueEntryId, long groupId, long classNameId,
			long classPK, boolean sent,
			OrderByComparator<NotificationsQueueEntry> orderByComparator)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry = findByPrimaryKey(
			notificationsQueueEntryId);

		Session session = null;

		try {
			session = openSession();

			NotificationsQueueEntry[] array =
				new NotificationsQueueEntryImpl[3];

			array[0] = getByG_C_C_S_PrevAndNext(
				session, notificationsQueueEntry, groupId, classNameId, classPK,
				sent, orderByComparator, true);

			array[1] = notificationsQueueEntry;

			array[2] = getByG_C_C_S_PrevAndNext(
				session, notificationsQueueEntry, groupId, classNameId, classPK,
				sent, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected NotificationsQueueEntry getByG_C_C_S_PrevAndNext(
		Session session, NotificationsQueueEntry notificationsQueueEntry,
		long groupId, long classNameId, long classPK, boolean sent,
		OrderByComparator<NotificationsQueueEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_NOTIFICATIONSQUEUEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_S_CLASSPK_2);

		sb.append(_FINDER_COLUMN_G_C_C_S_SENT_2);

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
			sb.append(NotificationsQueueEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(sent);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						notificationsQueueEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<NotificationsQueueEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the notifications queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 */
	@Override
	public void removeByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent) {

		for (NotificationsQueueEntry notificationsQueueEntry :
				findByG_C_C_S(
					groupId, classNameId, classPK, sent, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(notificationsQueueEntry);
		}
	}

	/**
	 * Returns the number of notifications queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @return the number of matching notifications queue entries
	 */
	@Override
	public int countByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent) {

		FinderPath finderPath = _finderPathCountByG_C_C_S;

		Object[] finderArgs = new Object[] {
			groupId, classNameId, classPK, sent
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_NOTIFICATIONSQUEUEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_S_CLASSPK_2);

			sb.append(_FINDER_COLUMN_G_C_C_S_SENT_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(sent);

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

	private static final String _FINDER_COLUMN_G_C_C_S_GROUPID_2 =
		"notificationsQueueEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_S_CLASSNAMEID_2 =
		"notificationsQueueEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_S_CLASSPK_2 =
		"notificationsQueueEntry.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_S_SENT_2 =
		"notificationsQueueEntry.sent = ?";

	public NotificationsQueueEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("from", "from_");
		dbColumnNames.put("to", "to_");

		setDBColumnNames(dbColumnNames);

		setModelClass(NotificationsQueueEntry.class);

		setModelImplClass(NotificationsQueueEntryImpl.class);
		setModelPKClass(long.class);

		setTable(NotificationsQueueEntryTable.INSTANCE);
	}

	/**
	 * Caches the notifications queue entry in the entity cache if it is enabled.
	 *
	 * @param notificationsQueueEntry the notifications queue entry
	 */
	@Override
	public void cacheResult(NotificationsQueueEntry notificationsQueueEntry) {
		entityCache.putResult(
			NotificationsQueueEntryImpl.class,
			notificationsQueueEntry.getPrimaryKey(), notificationsQueueEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the notifications queue entries in the entity cache if it is enabled.
	 *
	 * @param notificationsQueueEntries the notifications queue entries
	 */
	@Override
	public void cacheResult(
		List<NotificationsQueueEntry> notificationsQueueEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (notificationsQueueEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (NotificationsQueueEntry notificationsQueueEntry :
				notificationsQueueEntries) {

			if (entityCache.getResult(
					NotificationsQueueEntryImpl.class,
					notificationsQueueEntry.getPrimaryKey()) == null) {

				cacheResult(notificationsQueueEntry);
			}
		}
	}

	/**
	 * Clears the cache for all notifications queue entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(NotificationsQueueEntryImpl.class);

		finderCache.clearCache(NotificationsQueueEntryImpl.class);
	}

	/**
	 * Clears the cache for the notifications queue entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(NotificationsQueueEntry notificationsQueueEntry) {
		entityCache.removeResult(
			NotificationsQueueEntryImpl.class, notificationsQueueEntry);
	}

	@Override
	public void clearCache(
		List<NotificationsQueueEntry> notificationsQueueEntries) {

		for (NotificationsQueueEntry notificationsQueueEntry :
				notificationsQueueEntries) {

			entityCache.removeResult(
				NotificationsQueueEntryImpl.class, notificationsQueueEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(NotificationsQueueEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				NotificationsQueueEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new notifications queue entry with the primary key. Does not add the notifications queue entry to the database.
	 *
	 * @param notificationsQueueEntryId the primary key for the new notifications queue entry
	 * @return the new notifications queue entry
	 */
	@Override
	public NotificationsQueueEntry create(long notificationsQueueEntryId) {
		NotificationsQueueEntry notificationsQueueEntry =
			new NotificationsQueueEntryImpl();

		notificationsQueueEntry.setNew(true);
		notificationsQueueEntry.setPrimaryKey(notificationsQueueEntryId);

		notificationsQueueEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return notificationsQueueEntry;
	}

	/**
	 * Removes the notifications queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationsQueueEntryId the primary key of the notifications queue entry
	 * @return the notifications queue entry that was removed
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	@Override
	public NotificationsQueueEntry remove(long notificationsQueueEntryId)
		throws NoSuchNotificationsQueueEntryException {

		return remove((Serializable)notificationsQueueEntryId);
	}

	/**
	 * Removes the notifications queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the notifications queue entry
	 * @return the notifications queue entry that was removed
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	@Override
	public NotificationsQueueEntry remove(Serializable primaryKey)
		throws NoSuchNotificationsQueueEntryException {

		Session session = null;

		try {
			session = openSession();

			NotificationsQueueEntry notificationsQueueEntry =
				(NotificationsQueueEntry)session.get(
					NotificationsQueueEntryImpl.class, primaryKey);

			if (notificationsQueueEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationsQueueEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(notificationsQueueEntry);
		}
		catch (NoSuchNotificationsQueueEntryException noSuchEntityException) {
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
	protected NotificationsQueueEntry removeImpl(
		NotificationsQueueEntry notificationsQueueEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(notificationsQueueEntry)) {
				notificationsQueueEntry = (NotificationsQueueEntry)session.get(
					NotificationsQueueEntryImpl.class,
					notificationsQueueEntry.getPrimaryKeyObj());
			}

			if (notificationsQueueEntry != null) {
				session.delete(notificationsQueueEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (notificationsQueueEntry != null) {
			clearCache(notificationsQueueEntry);
		}

		return notificationsQueueEntry;
	}

	@Override
	public NotificationsQueueEntry updateImpl(
		NotificationsQueueEntry notificationsQueueEntry) {

		boolean isNew = notificationsQueueEntry.isNew();

		if (!(notificationsQueueEntry instanceof
				NotificationsQueueEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(notificationsQueueEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					notificationsQueueEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in notificationsQueueEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom NotificationsQueueEntry implementation " +
					notificationsQueueEntry.getClass());
		}

		NotificationsQueueEntryModelImpl notificationsQueueEntryModelImpl =
			(NotificationsQueueEntryModelImpl)notificationsQueueEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (notificationsQueueEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				notificationsQueueEntry.setCreateDate(date);
			}
			else {
				notificationsQueueEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!notificationsQueueEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				notificationsQueueEntry.setModifiedDate(date);
			}
			else {
				notificationsQueueEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(notificationsQueueEntry);
			}
			else {
				notificationsQueueEntry =
					(NotificationsQueueEntry)session.merge(
						notificationsQueueEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			NotificationsQueueEntryImpl.class, notificationsQueueEntryModelImpl,
			false, true);

		if (isNew) {
			notificationsQueueEntry.setNew(false);
		}

		notificationsQueueEntry.resetOriginalValues();

		return notificationsQueueEntry;
	}

	/**
	 * Returns the notifications queue entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the notifications queue entry
	 * @return the notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	@Override
	public NotificationsQueueEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchNotificationsQueueEntryException {

		NotificationsQueueEntry notificationsQueueEntry = fetchByPrimaryKey(
			primaryKey);

		if (notificationsQueueEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationsQueueEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return notificationsQueueEntry;
	}

	/**
	 * Returns the notifications queue entry with the primary key or throws a <code>NoSuchNotificationsQueueEntryException</code> if it could not be found.
	 *
	 * @param notificationsQueueEntryId the primary key of the notifications queue entry
	 * @return the notifications queue entry
	 * @throws NoSuchNotificationsQueueEntryException if a notifications queue entry with the primary key could not be found
	 */
	@Override
	public NotificationsQueueEntry findByPrimaryKey(
			long notificationsQueueEntryId)
		throws NoSuchNotificationsQueueEntryException {

		return findByPrimaryKey((Serializable)notificationsQueueEntryId);
	}

	/**
	 * Returns the notifications queue entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationsQueueEntryId the primary key of the notifications queue entry
	 * @return the notifications queue entry, or <code>null</code> if a notifications queue entry with the primary key could not be found
	 */
	@Override
	public NotificationsQueueEntry fetchByPrimaryKey(
		long notificationsQueueEntryId) {

		return fetchByPrimaryKey((Serializable)notificationsQueueEntryId);
	}

	/**
	 * Returns all the notifications queue entries.
	 *
	 * @return the notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notifications queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @return the range of notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findAll(
		int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notifications queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications queue entries
	 * @param end the upper bound of the range of notifications queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notifications queue entries
	 */
	@Override
	public List<NotificationsQueueEntry> findAll(
		int start, int end,
		OrderByComparator<NotificationsQueueEntry> orderByComparator,
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

		List<NotificationsQueueEntry> list = null;

		if (useFinderCache) {
			list = (List<NotificationsQueueEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_NOTIFICATIONSQUEUEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_NOTIFICATIONSQUEUEENTRY;

				sql = sql.concat(
					NotificationsQueueEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<NotificationsQueueEntry>)QueryUtil.list(
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
	 * Removes all the notifications queue entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (NotificationsQueueEntry notificationsQueueEntry : findAll()) {
			remove(notificationsQueueEntry);
		}
	}

	/**
	 * Returns the number of notifications queue entries.
	 *
	 * @return the number of notifications queue entries
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
					_SQL_COUNT_NOTIFICATIONSQUEUEENTRY);

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
		return "notificationsQueueEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_NOTIFICATIONSQUEUEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return NotificationsQueueEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the notifications queue entry persistence.
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

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathWithPaginationFindByNotificationsTemplateId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByNotificationsTemplateId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"notificationsTemplateId"}, true);

		_finderPathWithoutPaginationFindByNotificationsTemplateId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByNotificationsTemplateId",
				new String[] {Long.class.getName()},
				new String[] {"notificationsTemplateId"}, true);

		_finderPathCountByNotificationsTemplateId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByNotificationsTemplateId",
			new String[] {Long.class.getName()},
			new String[] {"notificationsTemplateId"}, false);

		_finderPathWithPaginationFindBySent = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySent",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"sent"}, true);

		_finderPathWithoutPaginationFindBySent = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySent",
			new String[] {Boolean.class.getName()}, new String[] {"sent"},
			true);

		_finderPathCountBySent = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySent",
			new String[] {Boolean.class.getName()}, new String[] {"sent"},
			false);

		_finderPathWithPaginationFindByLtSentDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtSentDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"sentDate"}, true);

		_finderPathWithPaginationCountByLtSentDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtSentDate",
			new String[] {Date.class.getName()}, new String[] {"sentDate"},
			false);

		_finderPathWithPaginationFindByG_C_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK", "sent"}, true);

		_finderPathWithoutPaginationFindByG_C_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK", "sent"}, true);

		_finderPathCountByG_C_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK", "sent"}, false);

		_setNotificationsQueueEntryUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setNotificationsQueueEntryUtilPersistence(null);

		entityCache.removeCache(NotificationsQueueEntryImpl.class.getName());
	}

	private void _setNotificationsQueueEntryUtilPersistence(
		NotificationsQueueEntryPersistence notificationsQueueEntryPersistence) {

		try {
			Field field = NotificationsQueueEntryUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, notificationsQueueEntryPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = NotificationsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = NotificationsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = NotificationsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_NOTIFICATIONSQUEUEENTRY =
		"SELECT notificationsQueueEntry FROM NotificationsQueueEntry notificationsQueueEntry";

	private static final String _SQL_SELECT_NOTIFICATIONSQUEUEENTRY_WHERE =
		"SELECT notificationsQueueEntry FROM NotificationsQueueEntry notificationsQueueEntry WHERE ";

	private static final String _SQL_COUNT_NOTIFICATIONSQUEUEENTRY =
		"SELECT COUNT(notificationsQueueEntry) FROM NotificationsQueueEntry notificationsQueueEntry";

	private static final String _SQL_COUNT_NOTIFICATIONSQUEUEENTRY_WHERE =
		"SELECT COUNT(notificationsQueueEntry) FROM NotificationsQueueEntry notificationsQueueEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"notificationsQueueEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No NotificationsQueueEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No NotificationsQueueEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationsQueueEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"from", "to"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private NotificationsQueueEntryModelArgumentsResolver
		_notificationsQueueEntryModelArgumentsResolver;

}