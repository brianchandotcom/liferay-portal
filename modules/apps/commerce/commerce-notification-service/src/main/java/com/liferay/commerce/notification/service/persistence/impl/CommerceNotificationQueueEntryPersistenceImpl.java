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

package com.liferay.commerce.notification.service.persistence.impl;

import com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException;
import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
import com.liferay.commerce.notification.model.CommerceNotificationQueueEntryTable;
import com.liferay.commerce.notification.model.impl.CommerceNotificationQueueEntryImpl;
import com.liferay.commerce.notification.model.impl.CommerceNotificationQueueEntryModelImpl;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationQueueEntryPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
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
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce notification queue entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceNotificationQueueEntryPersistenceImpl
	extends BasePersistenceImpl<CommerceNotificationQueueEntry>
	implements CommerceNotificationQueueEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceNotificationQueueEntryUtil</code> to access the commerce notification queue entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceNotificationQueueEntryImpl.class.getName();

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
	 * Returns all the commerce notification queue entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
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

		List<CommerceNotificationQueueEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceNotificationQueueEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationQueueEntry
						commerceNotificationQueueEntry : list) {

					if (groupId !=
							commerceNotificationQueueEntry.getGroupId()) {

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

			sb.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(
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
	 * Returns the first commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByGroupId_First(
			long groupId,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			fetchByGroupId_First(groupId, orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchNotificationQueueEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		List<CommerceNotificationQueueEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByGroupId_Last(
			long groupId,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			fetchByGroupId_Last(groupId, orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchNotificationQueueEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationQueueEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry[] findByGroupId_PrevAndNext(
			long commerceNotificationQueueEntryId, long groupId,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			findByPrimaryKey(commerceNotificationQueueEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationQueueEntry[] array =
				new CommerceNotificationQueueEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, commerceNotificationQueueEntry, groupId,
				orderByComparator, true);

			array[1] = commerceNotificationQueueEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, commerceNotificationQueueEntry, groupId,
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

	protected CommerceNotificationQueueEntry getByGroupId_PrevAndNext(
		Session session,
		CommerceNotificationQueueEntry commerceNotificationQueueEntry,
		long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

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
			sb.append(CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
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
						commerceNotificationQueueEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceNotificationQueueEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification queue entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceNotificationQueueEntry);
		}
	}

	/**
	 * Returns the number of commerce notification queue entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce notification queue entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

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
		"commerceNotificationQueueEntry.groupId = ?";

	private FinderPath
		_finderPathWithPaginationFindByCommerceNotificationTemplateId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceNotificationTemplateId;
	private FinderPath _finderPathCountByCommerceNotificationTemplateId;

	/**
	 * Returns all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId) {

		return findByCommerceNotificationTemplateId(
			commerceNotificationTemplateId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end) {

		return findByCommerceNotificationTemplateId(
			commerceNotificationTemplateId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end,
			OrderByComparator<CommerceNotificationQueueEntry>
				orderByComparator) {

		return findByCommerceNotificationTemplateId(
			commerceNotificationTemplateId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceNotificationTemplateId;
				finderArgs = new Object[] {commerceNotificationTemplateId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommerceNotificationTemplateId;
			finderArgs = new Object[] {
				commerceNotificationTemplateId, start, end, orderByComparator
			};
		}

		List<CommerceNotificationQueueEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceNotificationQueueEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationQueueEntry
						commerceNotificationQueueEntry : list) {

					if (commerceNotificationTemplateId !=
							commerceNotificationQueueEntry.
								getCommerceNotificationTemplateId()) {

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

			sb.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCENOTIFICATIONTEMPLATEID_COMMERCENOTIFICATIONTEMPLATEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceNotificationTemplateId);

				list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(
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
	 * Returns the first commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry
			findByCommerceNotificationTemplateId_First(
				long commerceNotificationTemplateId,
				OrderByComparator<CommerceNotificationQueueEntry>
					orderByComparator)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			fetchByCommerceNotificationTemplateId_First(
				commerceNotificationTemplateId, orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceNotificationTemplateId=");
		sb.append(commerceNotificationTemplateId);

		sb.append("}");

		throw new NoSuchNotificationQueueEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry
		fetchByCommerceNotificationTemplateId_First(
			long commerceNotificationTemplateId,
			OrderByComparator<CommerceNotificationQueueEntry>
				orderByComparator) {

		List<CommerceNotificationQueueEntry> list =
			findByCommerceNotificationTemplateId(
				commerceNotificationTemplateId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry
			findByCommerceNotificationTemplateId_Last(
				long commerceNotificationTemplateId,
				OrderByComparator<CommerceNotificationQueueEntry>
					orderByComparator)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			fetchByCommerceNotificationTemplateId_Last(
				commerceNotificationTemplateId, orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceNotificationTemplateId=");
		sb.append(commerceNotificationTemplateId);

		sb.append("}");

		throw new NoSuchNotificationQueueEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry
		fetchByCommerceNotificationTemplateId_Last(
			long commerceNotificationTemplateId,
			OrderByComparator<CommerceNotificationQueueEntry>
				orderByComparator) {

		int count = countByCommerceNotificationTemplateId(
			commerceNotificationTemplateId);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationQueueEntry> list =
			findByCommerceNotificationTemplateId(
				commerceNotificationTemplateId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry[]
			findByCommerceNotificationTemplateId_PrevAndNext(
				long commerceNotificationQueueEntryId,
				long commerceNotificationTemplateId,
				OrderByComparator<CommerceNotificationQueueEntry>
					orderByComparator)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			findByPrimaryKey(commerceNotificationQueueEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationQueueEntry[] array =
				new CommerceNotificationQueueEntryImpl[3];

			array[0] = getByCommerceNotificationTemplateId_PrevAndNext(
				session, commerceNotificationQueueEntry,
				commerceNotificationTemplateId, orderByComparator, true);

			array[1] = commerceNotificationQueueEntry;

			array[2] = getByCommerceNotificationTemplateId_PrevAndNext(
				session, commerceNotificationQueueEntry,
				commerceNotificationTemplateId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceNotificationQueueEntry
		getByCommerceNotificationTemplateId_PrevAndNext(
			Session session,
			CommerceNotificationQueueEntry commerceNotificationQueueEntry,
			long commerceNotificationTemplateId,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCENOTIFICATIONTEMPLATEID_COMMERCENOTIFICATIONTEMPLATEID_2);

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
			sb.append(CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceNotificationTemplateId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceNotificationQueueEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceNotificationQueueEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification queue entries where commerceNotificationTemplateId = &#63; from the database.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 */
	@Override
	public void removeByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {

		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry :
				findByCommerceNotificationTemplateId(
					commerceNotificationTemplateId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceNotificationQueueEntry);
		}
	}

	/**
	 * Returns the number of commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the number of matching commerce notification queue entries
	 */
	@Override
	public int countByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {

		FinderPath finderPath =
			_finderPathCountByCommerceNotificationTemplateId;

		Object[] finderArgs = new Object[] {commerceNotificationTemplateId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCENOTIFICATIONTEMPLATEID_COMMERCENOTIFICATIONTEMPLATEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceNotificationTemplateId);

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
		_FINDER_COLUMN_COMMERCENOTIFICATIONTEMPLATEID_COMMERCENOTIFICATIONTEMPLATEID_2 =
			"commerceNotificationQueueEntry.commerceNotificationTemplateId = ?";

	private FinderPath _finderPathWithPaginationFindBySent;
	private FinderPath _finderPathWithoutPaginationFindBySent;
	private FinderPath _finderPathCountBySent;

	/**
	 * Returns all the commerce notification queue entries where sent = &#63;.
	 *
	 * @param sent the sent
	 * @return the matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findBySent(boolean sent) {
		return findBySent(sent, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findBySent(
		boolean sent, int start, int end) {

		return findBySent(sent, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findBySent(
		boolean sent, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return findBySent(sent, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findBySent(
		boolean sent, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
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

		List<CommerceNotificationQueueEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceNotificationQueueEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationQueueEntry
						commerceNotificationQueueEntry : list) {

					if (sent != commerceNotificationQueueEntry.isSent()) {
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

			sb.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_SENT_SENT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(sent);

				list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(
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
	 * Returns the first commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findBySent_First(
			boolean sent,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			fetchBySent_First(sent, orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sent=");
		sb.append(sent);

		sb.append("}");

		throw new NoSuchNotificationQueueEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchBySent_First(
		boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		List<CommerceNotificationQueueEntry> list = findBySent(
			sent, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findBySent_Last(
			boolean sent,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			fetchBySent_Last(sent, orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sent=");
		sb.append(sent);

		sb.append("}");

		throw new NoSuchNotificationQueueEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchBySent_Last(
		boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		int count = countBySent(sent);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationQueueEntry> list = findBySent(
			sent, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry[] findBySent_PrevAndNext(
			long commerceNotificationQueueEntryId, boolean sent,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			findByPrimaryKey(commerceNotificationQueueEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationQueueEntry[] array =
				new CommerceNotificationQueueEntryImpl[3];

			array[0] = getBySent_PrevAndNext(
				session, commerceNotificationQueueEntry, sent,
				orderByComparator, true);

			array[1] = commerceNotificationQueueEntry;

			array[2] = getBySent_PrevAndNext(
				session, commerceNotificationQueueEntry, sent,
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

	protected CommerceNotificationQueueEntry getBySent_PrevAndNext(
		Session session,
		CommerceNotificationQueueEntry commerceNotificationQueueEntry,
		boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

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
			sb.append(CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
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
						commerceNotificationQueueEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceNotificationQueueEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification queue entries where sent = &#63; from the database.
	 *
	 * @param sent the sent
	 */
	@Override
	public void removeBySent(boolean sent) {
		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry :
				findBySent(sent, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceNotificationQueueEntry);
		}
	}

	/**
	 * Returns the number of commerce notification queue entries where sent = &#63;.
	 *
	 * @param sent the sent
	 * @return the number of matching commerce notification queue entries
	 */
	@Override
	public int countBySent(boolean sent) {
		FinderPath finderPath = _finderPathCountBySent;

		Object[] finderArgs = new Object[] {sent};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

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
		"commerceNotificationQueueEntry.sent = ?";

	private FinderPath _finderPathWithPaginationFindByLtSentDate;
	private FinderPath _finderPathWithPaginationCountByLtSentDate;

	/**
	 * Returns all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByLtSentDate(
		Date sentDate) {

		return findByLtSentDate(
			sentDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end) {

		return findByLtSentDate(sentDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return findByLtSentDate(sentDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtSentDate;
		finderArgs = new Object[] {
			_getTime(sentDate), start, end, orderByComparator
		};

		List<CommerceNotificationQueueEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceNotificationQueueEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationQueueEntry
						commerceNotificationQueueEntry : list) {

					if (sentDate.getTime() <=
							commerceNotificationQueueEntry.getSentDate(
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

			sb.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

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
				sb.append(
					CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(
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
	 * Returns the first commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByLtSentDate_First(
			Date sentDate,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			fetchByLtSentDate_First(sentDate, orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sentDate<");
		sb.append(sentDate);

		sb.append("}");

		throw new NoSuchNotificationQueueEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchByLtSentDate_First(
		Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		List<CommerceNotificationQueueEntry> list = findByLtSentDate(
			sentDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByLtSentDate_Last(
			Date sentDate,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			fetchByLtSentDate_Last(sentDate, orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sentDate<");
		sb.append(sentDate);

		sb.append("}");

		throw new NoSuchNotificationQueueEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchByLtSentDate_Last(
		Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		int count = countByLtSentDate(sentDate);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationQueueEntry> list = findByLtSentDate(
			sentDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry[] findByLtSentDate_PrevAndNext(
			long commerceNotificationQueueEntryId, Date sentDate,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			findByPrimaryKey(commerceNotificationQueueEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationQueueEntry[] array =
				new CommerceNotificationQueueEntryImpl[3];

			array[0] = getByLtSentDate_PrevAndNext(
				session, commerceNotificationQueueEntry, sentDate,
				orderByComparator, true);

			array[1] = commerceNotificationQueueEntry;

			array[2] = getByLtSentDate_PrevAndNext(
				session, commerceNotificationQueueEntry, sentDate,
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

	protected CommerceNotificationQueueEntry getByLtSentDate_PrevAndNext(
		Session session,
		CommerceNotificationQueueEntry commerceNotificationQueueEntry,
		Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

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
			sb.append(CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
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
						commerceNotificationQueueEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceNotificationQueueEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification queue entries where sentDate &lt; &#63; from the database.
	 *
	 * @param sentDate the sent date
	 */
	@Override
	public void removeByLtSentDate(Date sentDate) {
		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry :
				findByLtSentDate(
					sentDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceNotificationQueueEntry);
		}
	}

	/**
	 * Returns the number of commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the number of matching commerce notification queue entries
	 */
	@Override
	public int countByLtSentDate(Date sentDate) {
		FinderPath finderPath = _finderPathWithPaginationCountByLtSentDate;

		Object[] finderArgs = new Object[] {_getTime(sentDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

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
		"commerceNotificationQueueEntry.sentDate IS NULL";

	private static final String _FINDER_COLUMN_LTSENTDATE_SENTDATE_2 =
		"commerceNotificationQueueEntry.sentDate < ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C_S;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C_S;
	private FinderPath _finderPathCountByG_C_C_S;

	/**
	 * Returns all the commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @return the matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent) {

		return findByG_C_C_S(
			groupId, classNameId, classPK, sent, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end) {

		return findByG_C_C_S(
			groupId, classNameId, classPK, sent, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return findByG_C_C_S(
			groupId, classNameId, classPK, sent, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
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

		List<CommerceNotificationQueueEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceNotificationQueueEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationQueueEntry
						commerceNotificationQueueEntry : list) {

					if ((groupId !=
							commerceNotificationQueueEntry.getGroupId()) ||
						(classNameId !=
							commerceNotificationQueueEntry.getClassNameId()) ||
						(classPK !=
							commerceNotificationQueueEntry.getClassPK()) ||
						(sent != commerceNotificationQueueEntry.isSent())) {

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

			sb.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_S_CLASSPK_2);

			sb.append(_FINDER_COLUMN_G_C_C_S_SENT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(
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
	 * Returns the first commerce notification queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByG_C_C_S_First(
			long groupId, long classNameId, long classPK, boolean sent,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			fetchByG_C_C_S_First(
				groupId, classNameId, classPK, sent, orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
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

		throw new NoSuchNotificationQueueEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchByG_C_C_S_First(
		long groupId, long classNameId, long classPK, boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		List<CommerceNotificationQueueEntry> list = findByG_C_C_S(
			groupId, classNameId, classPK, sent, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByG_C_C_S_Last(
			long groupId, long classNameId, long classPK, boolean sent,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			fetchByG_C_C_S_Last(
				groupId, classNameId, classPK, sent, orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
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

		throw new NoSuchNotificationQueueEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchByG_C_C_S_Last(
		long groupId, long classNameId, long classPK, boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		int count = countByG_C_C_S(groupId, classNameId, classPK, sent);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationQueueEntry> list = findByG_C_C_S(
			groupId, classNameId, classPK, sent, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry[] findByG_C_C_S_PrevAndNext(
			long commerceNotificationQueueEntryId, long groupId,
			long classNameId, long classPK, boolean sent,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			findByPrimaryKey(commerceNotificationQueueEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationQueueEntry[] array =
				new CommerceNotificationQueueEntryImpl[3];

			array[0] = getByG_C_C_S_PrevAndNext(
				session, commerceNotificationQueueEntry, groupId, classNameId,
				classPK, sent, orderByComparator, true);

			array[1] = commerceNotificationQueueEntry;

			array[2] = getByG_C_C_S_PrevAndNext(
				session, commerceNotificationQueueEntry, groupId, classNameId,
				classPK, sent, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceNotificationQueueEntry getByG_C_C_S_PrevAndNext(
		Session session,
		CommerceNotificationQueueEntry commerceNotificationQueueEntry,
		long groupId, long classNameId, long classPK, boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

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
			sb.append(CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
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
						commerceNotificationQueueEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceNotificationQueueEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 */
	@Override
	public void removeByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent) {

		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry :
				findByG_C_C_S(
					groupId, classNameId, classPK, sent, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceNotificationQueueEntry);
		}
	}

	/**
	 * Returns the number of commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @return the number of matching commerce notification queue entries
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

			sb.append(_SQL_COUNT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

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
		"commerceNotificationQueueEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_S_CLASSNAMEID_2 =
		"commerceNotificationQueueEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_S_CLASSPK_2 =
		"commerceNotificationQueueEntry.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_S_SENT_2 =
		"commerceNotificationQueueEntry.sent = ?";

	public CommerceNotificationQueueEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceNotificationQueueEntryId", "CNotificationQueueEntryId");
		dbColumnNames.put("from", "from_");
		dbColumnNames.put("to", "to_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceNotificationQueueEntry.class);

		setModelImplClass(CommerceNotificationQueueEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceNotificationQueueEntryTable.INSTANCE);
	}

	/**
	 * Caches the commerce notification queue entry in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationQueueEntry the commerce notification queue entry
	 */
	@Override
	public void cacheResult(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {

		entityCache.putResult(
			CommerceNotificationQueueEntryImpl.class,
			commerceNotificationQueueEntry.getPrimaryKey(),
			commerceNotificationQueueEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce notification queue entries in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationQueueEntries the commerce notification queue entries
	 */
	@Override
	public void cacheResult(
		List<CommerceNotificationQueueEntry> commerceNotificationQueueEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceNotificationQueueEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry :
				commerceNotificationQueueEntries) {

			if (entityCache.getResult(
					CommerceNotificationQueueEntryImpl.class,
					commerceNotificationQueueEntry.getPrimaryKey()) == null) {

				cacheResult(commerceNotificationQueueEntry);
			}
		}
	}

	/**
	 * Clears the cache for all commerce notification queue entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceNotificationQueueEntryImpl.class);

		finderCache.clearCache(CommerceNotificationQueueEntryImpl.class);
	}

	/**
	 * Clears the cache for the commerce notification queue entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {

		entityCache.removeResult(
			CommerceNotificationQueueEntryImpl.class,
			commerceNotificationQueueEntry);
	}

	@Override
	public void clearCache(
		List<CommerceNotificationQueueEntry> commerceNotificationQueueEntries) {

		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry :
				commerceNotificationQueueEntries) {

			entityCache.removeResult(
				CommerceNotificationQueueEntryImpl.class,
				commerceNotificationQueueEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceNotificationQueueEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceNotificationQueueEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commerce notification queue entry with the primary key. Does not add the commerce notification queue entry to the database.
	 *
	 * @param commerceNotificationQueueEntryId the primary key for the new commerce notification queue entry
	 * @return the new commerce notification queue entry
	 */
	@Override
	public CommerceNotificationQueueEntry create(
		long commerceNotificationQueueEntryId) {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			new CommerceNotificationQueueEntryImpl();

		commerceNotificationQueueEntry.setNew(true);
		commerceNotificationQueueEntry.setPrimaryKey(
			commerceNotificationQueueEntryId);

		commerceNotificationQueueEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceNotificationQueueEntry;
	}

	/**
	 * Removes the commerce notification queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry that was removed
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry remove(
			long commerceNotificationQueueEntryId)
		throws NoSuchNotificationQueueEntryException {

		return remove((Serializable)commerceNotificationQueueEntryId);
	}

	/**
	 * Removes the commerce notification queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry that was removed
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry remove(Serializable primaryKey)
		throws NoSuchNotificationQueueEntryException {

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationQueueEntry commerceNotificationQueueEntry =
				(CommerceNotificationQueueEntry)session.get(
					CommerceNotificationQueueEntryImpl.class, primaryKey);

			if (commerceNotificationQueueEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationQueueEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceNotificationQueueEntry);
		}
		catch (NoSuchNotificationQueueEntryException noSuchEntityException) {
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
	protected CommerceNotificationQueueEntry removeImpl(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceNotificationQueueEntry)) {
				commerceNotificationQueueEntry =
					(CommerceNotificationQueueEntry)session.get(
						CommerceNotificationQueueEntryImpl.class,
						commerceNotificationQueueEntry.getPrimaryKeyObj());
			}

			if (commerceNotificationQueueEntry != null) {
				session.delete(commerceNotificationQueueEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceNotificationQueueEntry != null) {
			clearCache(commerceNotificationQueueEntry);
		}

		return commerceNotificationQueueEntry;
	}

	@Override
	public CommerceNotificationQueueEntry updateImpl(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {

		boolean isNew = commerceNotificationQueueEntry.isNew();

		if (!(commerceNotificationQueueEntry instanceof
				CommerceNotificationQueueEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceNotificationQueueEntry.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceNotificationQueueEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceNotificationQueueEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceNotificationQueueEntry implementation " +
					commerceNotificationQueueEntry.getClass());
		}

		CommerceNotificationQueueEntryModelImpl
			commerceNotificationQueueEntryModelImpl =
				(CommerceNotificationQueueEntryModelImpl)
					commerceNotificationQueueEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceNotificationQueueEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceNotificationQueueEntry.setCreateDate(date);
			}
			else {
				commerceNotificationQueueEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceNotificationQueueEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceNotificationQueueEntry.setModifiedDate(date);
			}
			else {
				commerceNotificationQueueEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceNotificationQueueEntry);
			}
			else {
				commerceNotificationQueueEntry =
					(CommerceNotificationQueueEntry)session.merge(
						commerceNotificationQueueEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceNotificationQueueEntryImpl.class,
			commerceNotificationQueueEntryModelImpl, false, true);

		if (isNew) {
			commerceNotificationQueueEntry.setNew(false);
		}

		commerceNotificationQueueEntry.resetOriginalValues();

		return commerceNotificationQueueEntry;
	}

	/**
	 * Returns the commerce notification queue entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchNotificationQueueEntryException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			fetchByPrimaryKey(primaryKey);

		if (commerceNotificationQueueEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationQueueEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceNotificationQueueEntry;
	}

	/**
	 * Returns the commerce notification queue entry with the primary key or throws a <code>NoSuchNotificationQueueEntryException</code> if it could not be found.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByPrimaryKey(
			long commerceNotificationQueueEntryId)
		throws NoSuchNotificationQueueEntryException {

		return findByPrimaryKey((Serializable)commerceNotificationQueueEntryId);
	}

	/**
	 * Returns the commerce notification queue entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry, or <code>null</code> if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchByPrimaryKey(
		long commerceNotificationQueueEntryId) {

		return fetchByPrimaryKey(
			(Serializable)commerceNotificationQueueEntryId);
	}

	/**
	 * Returns all the commerce notification queue entries.
	 *
	 * @return the commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
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

		List<CommerceNotificationQueueEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceNotificationQueueEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY;

				sql = sql.concat(
					CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(
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
	 * Removes all the commerce notification queue entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry :
				findAll()) {

			remove(commerceNotificationQueueEntry);
		}
	}

	/**
	 * Returns the number of commerce notification queue entries.
	 *
	 * @return the number of commerce notification queue entries
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
					_SQL_COUNT_COMMERCENOTIFICATIONQUEUEENTRY);

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
		return "CNotificationQueueEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceNotificationQueueEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce notification queue entry persistence.
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

		_finderPathWithPaginationFindByCommerceNotificationTemplateId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceNotificationTemplateId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceNotificationTemplateId"}, true);

		_finderPathWithoutPaginationFindByCommerceNotificationTemplateId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceNotificationTemplateId",
				new String[] {Long.class.getName()},
				new String[] {"commerceNotificationTemplateId"}, true);

		_finderPathCountByCommerceNotificationTemplateId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceNotificationTemplateId",
			new String[] {Long.class.getName()},
			new String[] {"commerceNotificationTemplateId"}, false);

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
	}

	public void destroy() {
		entityCache.removeCache(
			CommerceNotificationQueueEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY =
		"SELECT commerceNotificationQueueEntry FROM CommerceNotificationQueueEntry commerceNotificationQueueEntry";

	private static final String
		_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE =
			"SELECT commerceNotificationQueueEntry FROM CommerceNotificationQueueEntry commerceNotificationQueueEntry WHERE ";

	private static final String _SQL_COUNT_COMMERCENOTIFICATIONQUEUEENTRY =
		"SELECT COUNT(commerceNotificationQueueEntry) FROM CommerceNotificationQueueEntry commerceNotificationQueueEntry";

	private static final String
		_SQL_COUNT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE =
			"SELECT COUNT(commerceNotificationQueueEntry) FROM CommerceNotificationQueueEntry commerceNotificationQueueEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceNotificationQueueEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceNotificationQueueEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceNotificationQueueEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceNotificationQueueEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"commerceNotificationQueueEntryId", "from", "to"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}