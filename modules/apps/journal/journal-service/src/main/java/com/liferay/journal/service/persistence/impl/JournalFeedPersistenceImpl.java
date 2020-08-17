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

package com.liferay.journal.service.persistence.impl;

import com.liferay.journal.exception.NoSuchFeedException;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.model.JournalFeedTable;
import com.liferay.journal.model.impl.JournalFeedImpl;
import com.liferay.journal.model.impl.JournalFeedModelImpl;
import com.liferay.journal.service.persistence.JournalFeedPersistence;
import com.liferay.journal.service.persistence.impl.constants.JournalPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
 * The persistence implementation for the journal feed service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = JournalFeedPersistence.class)
public class JournalFeedPersistenceImpl
	extends BasePersistenceImpl<JournalFeed> implements JournalFeedPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>JournalFeedUtil</code> to access the journal feed persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		JournalFeedImpl.class.getName();

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
	 * Returns all the journal feeds where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching journal feeds
	 */
	@Override
	public List<JournalFeed> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal feeds where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFeedModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @return the range of matching journal feeds
	 */
	@Override
	public List<JournalFeed> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal feeds where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFeedModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal feeds
	 */
	@Override
	public List<JournalFeed> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<JournalFeed> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal feeds where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFeedModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal feeds
	 */
	@Override
	public List<JournalFeed> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<JournalFeed> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalFeed.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<JournalFeed> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JournalFeed>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (JournalFeed journalFeed : list) {
					if (!uuid.equals(journalFeed.getUuid())) {
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

			sb.append(_SQL_SELECT_JOURNALFEED_WHERE);

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
				sb.append(JournalFeedModelImpl.ORDER_BY_JPQL);
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

				list = (List<JournalFeed>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first journal feed in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal feed
	 * @throws NoSuchFeedException if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed findByUuid_First(
			String uuid, OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException {

		JournalFeed journalFeed = fetchByUuid_First(uuid, orderByComparator);

		if (journalFeed != null) {
			return journalFeed;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchFeedException(sb.toString());
	}

	/**
	 * Returns the first journal feed in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed fetchByUuid_First(
		String uuid, OrderByComparator<JournalFeed> orderByComparator) {

		List<JournalFeed> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal feed in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal feed
	 * @throws NoSuchFeedException if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed findByUuid_Last(
			String uuid, OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException {

		JournalFeed journalFeed = fetchByUuid_Last(uuid, orderByComparator);

		if (journalFeed != null) {
			return journalFeed;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchFeedException(sb.toString());
	}

	/**
	 * Returns the last journal feed in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed fetchByUuid_Last(
		String uuid, OrderByComparator<JournalFeed> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<JournalFeed> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal feeds before and after the current journal feed in the ordered set where uuid = &#63;.
	 *
	 * @param id the primary key of the current journal feed
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal feed
	 * @throws NoSuchFeedException if a journal feed with the primary key could not be found
	 */
	@Override
	public JournalFeed[] findByUuid_PrevAndNext(
			long id, String uuid,
			OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException {

		uuid = Objects.toString(uuid, "");

		JournalFeed journalFeed = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalFeed[] array = new JournalFeedImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, journalFeed, uuid, orderByComparator, true);

			array[1] = journalFeed;

			array[2] = getByUuid_PrevAndNext(
				session, journalFeed, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalFeed getByUuid_PrevAndNext(
		Session session, JournalFeed journalFeed, String uuid,
		OrderByComparator<JournalFeed> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_JOURNALFEED_WHERE);

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
			sb.append(JournalFeedModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(journalFeed)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JournalFeed> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal feeds where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (JournalFeed journalFeed :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(journalFeed);
		}
	}

	/**
	 * Returns the number of journal feeds where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching journal feeds
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalFeed.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_JOURNALFEED_WHERE);

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

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		"journalFeed.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(journalFeed.uuid IS NULL OR journalFeed.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the journal feed where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchFeedException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal feed
	 * @throws NoSuchFeedException if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed findByUUID_G(String uuid, long groupId)
		throws NoSuchFeedException {

		JournalFeed journalFeed = fetchByUUID_G(uuid, groupId);

		if (journalFeed == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFeedException(sb.toString());
		}

		return journalFeed;
	}

	/**
	 * Returns the journal feed where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the journal feed where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalFeed.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof JournalFeed) {
			JournalFeed journalFeed = (JournalFeed)result;

			if (!Objects.equals(uuid, journalFeed.getUuid()) ||
				(groupId != journalFeed.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_JOURNALFEED_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				List<JournalFeed> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					JournalFeed journalFeed = list.get(0);

					result = journalFeed;

					cacheResult(journalFeed);
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
			return (JournalFeed)result;
		}
	}

	/**
	 * Removes the journal feed where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the journal feed that was removed
	 */
	@Override
	public JournalFeed removeByUUID_G(String uuid, long groupId)
		throws NoSuchFeedException {

		JournalFeed journalFeed = findByUUID_G(uuid, groupId);

		return remove(journalFeed);
	}

	/**
	 * Returns the number of journal feeds where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching journal feeds
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalFeed.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G;

			finderArgs = new Object[] {uuid, groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_JOURNALFEED_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"journalFeed.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(journalFeed.uuid IS NULL OR journalFeed.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"journalFeed.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the journal feeds where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching journal feeds
	 */
	@Override
	public List<JournalFeed> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal feeds where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFeedModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @return the range of matching journal feeds
	 */
	@Override
	public List<JournalFeed> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal feeds where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFeedModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal feeds
	 */
	@Override
	public List<JournalFeed> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<JournalFeed> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal feeds where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFeedModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal feeds
	 */
	@Override
	public List<JournalFeed> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<JournalFeed> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalFeed.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<JournalFeed> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JournalFeed>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (JournalFeed journalFeed : list) {
					if (!uuid.equals(journalFeed.getUuid()) ||
						(companyId != journalFeed.getCompanyId())) {

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

			sb.append(_SQL_SELECT_JOURNALFEED_WHERE);

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
				sb.append(JournalFeedModelImpl.ORDER_BY_JPQL);
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

				list = (List<JournalFeed>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first journal feed in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal feed
	 * @throws NoSuchFeedException if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException {

		JournalFeed journalFeed = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (journalFeed != null) {
			return journalFeed;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFeedException(sb.toString());
	}

	/**
	 * Returns the first journal feed in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<JournalFeed> orderByComparator) {

		List<JournalFeed> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal feed in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal feed
	 * @throws NoSuchFeedException if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException {

		JournalFeed journalFeed = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (journalFeed != null) {
			return journalFeed;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFeedException(sb.toString());
	}

	/**
	 * Returns the last journal feed in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<JournalFeed> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<JournalFeed> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal feeds before and after the current journal feed in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param id the primary key of the current journal feed
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal feed
	 * @throws NoSuchFeedException if a journal feed with the primary key could not be found
	 */
	@Override
	public JournalFeed[] findByUuid_C_PrevAndNext(
			long id, String uuid, long companyId,
			OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException {

		uuid = Objects.toString(uuid, "");

		JournalFeed journalFeed = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalFeed[] array = new JournalFeedImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, journalFeed, uuid, companyId, orderByComparator, true);

			array[1] = journalFeed;

			array[2] = getByUuid_C_PrevAndNext(
				session, journalFeed, uuid, companyId, orderByComparator,
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

	protected JournalFeed getByUuid_C_PrevAndNext(
		Session session, JournalFeed journalFeed, String uuid, long companyId,
		OrderByComparator<JournalFeed> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_JOURNALFEED_WHERE);

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
			sb.append(JournalFeedModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(journalFeed)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JournalFeed> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal feeds where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (JournalFeed journalFeed :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(journalFeed);
		}
	}

	/**
	 * Returns the number of journal feeds where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching journal feeds
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalFeed.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_JOURNALFEED_WHERE);

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

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		"journalFeed.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(journalFeed.uuid IS NULL OR journalFeed.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"journalFeed.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the journal feeds where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal feeds
	 */
	@Override
	public List<JournalFeed> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal feeds where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFeedModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @return the range of matching journal feeds
	 */
	@Override
	public List<JournalFeed> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal feeds where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFeedModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal feeds
	 */
	@Override
	public List<JournalFeed> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<JournalFeed> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal feeds where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFeedModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal feeds
	 */
	@Override
	public List<JournalFeed> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<JournalFeed> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalFeed.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<JournalFeed> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JournalFeed>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (JournalFeed journalFeed : list) {
					if (groupId != journalFeed.getGroupId()) {
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

			sb.append(_SQL_SELECT_JOURNALFEED_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(JournalFeedModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<JournalFeed>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first journal feed in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal feed
	 * @throws NoSuchFeedException if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed findByGroupId_First(
			long groupId, OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException {

		JournalFeed journalFeed = fetchByGroupId_First(
			groupId, orderByComparator);

		if (journalFeed != null) {
			return journalFeed;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFeedException(sb.toString());
	}

	/**
	 * Returns the first journal feed in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed fetchByGroupId_First(
		long groupId, OrderByComparator<JournalFeed> orderByComparator) {

		List<JournalFeed> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal feed in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal feed
	 * @throws NoSuchFeedException if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed findByGroupId_Last(
			long groupId, OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException {

		JournalFeed journalFeed = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (journalFeed != null) {
			return journalFeed;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFeedException(sb.toString());
	}

	/**
	 * Returns the last journal feed in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed fetchByGroupId_Last(
		long groupId, OrderByComparator<JournalFeed> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<JournalFeed> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal feeds before and after the current journal feed in the ordered set where groupId = &#63;.
	 *
	 * @param id the primary key of the current journal feed
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal feed
	 * @throws NoSuchFeedException if a journal feed with the primary key could not be found
	 */
	@Override
	public JournalFeed[] findByGroupId_PrevAndNext(
			long id, long groupId,
			OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException {

		JournalFeed journalFeed = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalFeed[] array = new JournalFeedImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, journalFeed, groupId, orderByComparator, true);

			array[1] = journalFeed;

			array[2] = getByGroupId_PrevAndNext(
				session, journalFeed, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalFeed getByGroupId_PrevAndNext(
		Session session, JournalFeed journalFeed, long groupId,
		OrderByComparator<JournalFeed> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_JOURNALFEED_WHERE);

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
			sb.append(JournalFeedModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(journalFeed)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JournalFeed> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal feeds that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal feeds that the user has permission to view
	 */
	@Override
	public List<JournalFeed> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal feeds that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFeedModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @return the range of matching journal feeds that the user has permission to view
	 */
	@Override
	public List<JournalFeed> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal feeds that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFeedModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal feeds that the user has permission to view
	 */
	@Override
	public List<JournalFeed> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<JournalFeed> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALFEED_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALFEED_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALFEED_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalFeedModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(JournalFeedModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalFeed.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, JournalFeedImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, JournalFeedImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<JournalFeed>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal feeds before and after the current journal feed in the ordered set of journal feeds that the user has permission to view where groupId = &#63;.
	 *
	 * @param id the primary key of the current journal feed
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal feed
	 * @throws NoSuchFeedException if a journal feed with the primary key could not be found
	 */
	@Override
	public JournalFeed[] filterFindByGroupId_PrevAndNext(
			long id, long groupId,
			OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(id, groupId, orderByComparator);
		}

		JournalFeed journalFeed = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalFeed[] array = new JournalFeedImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(
				session, journalFeed, groupId, orderByComparator, true);

			array[1] = journalFeed;

			array[2] = filterGetByGroupId_PrevAndNext(
				session, journalFeed, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalFeed filterGetByGroupId_PrevAndNext(
		Session session, JournalFeed journalFeed, long groupId,
		OrderByComparator<JournalFeed> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_JOURNALFEED_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALFEED_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_JOURNALFEED_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(JournalFeedModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(JournalFeedModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalFeed.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, JournalFeedImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, JournalFeedImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(journalFeed)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JournalFeed> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal feeds where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (JournalFeed journalFeed :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(journalFeed);
		}
	}

	/**
	 * Returns the number of journal feeds where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal feeds
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalFeed.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId;

			finderArgs = new Object[] {groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_JOURNALFEED_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
	 * Returns the number of journal feeds that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal feeds that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_JOURNALFEED_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), JournalFeed.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"journalFeed.groupId = ?";

	private FinderPath _finderPathFetchByG_F;
	private FinderPath _finderPathCountByG_F;

	/**
	 * Returns the journal feed where groupId = &#63; and feedId = &#63; or throws a <code>NoSuchFeedException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param feedId the feed ID
	 * @return the matching journal feed
	 * @throws NoSuchFeedException if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed findByG_F(long groupId, String feedId)
		throws NoSuchFeedException {

		JournalFeed journalFeed = fetchByG_F(groupId, feedId);

		if (journalFeed == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", feedId=");
			sb.append(feedId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFeedException(sb.toString());
		}

		return journalFeed;
	}

	/**
	 * Returns the journal feed where groupId = &#63; and feedId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param feedId the feed ID
	 * @return the matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed fetchByG_F(long groupId, String feedId) {
		return fetchByG_F(groupId, feedId, true);
	}

	/**
	 * Returns the journal feed where groupId = &#63; and feedId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param feedId the feed ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 */
	@Override
	public JournalFeed fetchByG_F(
		long groupId, String feedId, boolean useFinderCache) {

		feedId = Objects.toString(feedId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalFeed.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, feedId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_F, finderArgs, this);
		}

		if (result instanceof JournalFeed) {
			JournalFeed journalFeed = (JournalFeed)result;

			if ((groupId != journalFeed.getGroupId()) ||
				!Objects.equals(feedId, journalFeed.getFeedId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_JOURNALFEED_WHERE);

			sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

			boolean bindFeedId = false;

			if (feedId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_F_FEEDID_3);
			}
			else {
				bindFeedId = true;

				sb.append(_FINDER_COLUMN_G_F_FEEDID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindFeedId) {
					queryPos.add(feedId);
				}

				List<JournalFeed> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_F, finderArgs, list);
					}
				}
				else {
					JournalFeed journalFeed = list.get(0);

					result = journalFeed;

					cacheResult(journalFeed);
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
			return (JournalFeed)result;
		}
	}

	/**
	 * Removes the journal feed where groupId = &#63; and feedId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param feedId the feed ID
	 * @return the journal feed that was removed
	 */
	@Override
	public JournalFeed removeByG_F(long groupId, String feedId)
		throws NoSuchFeedException {

		JournalFeed journalFeed = findByG_F(groupId, feedId);

		return remove(journalFeed);
	}

	/**
	 * Returns the number of journal feeds where groupId = &#63; and feedId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param feedId the feed ID
	 * @return the number of matching journal feeds
	 */
	@Override
	public int countByG_F(long groupId, String feedId) {
		feedId = Objects.toString(feedId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalFeed.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_F;

			finderArgs = new Object[] {groupId, feedId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_JOURNALFEED_WHERE);

			sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

			boolean bindFeedId = false;

			if (feedId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_F_FEEDID_3);
			}
			else {
				bindFeedId = true;

				sb.append(_FINDER_COLUMN_G_F_FEEDID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindFeedId) {
					queryPos.add(feedId);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_F_GROUPID_2 =
		"journalFeed.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_FEEDID_2 =
		"journalFeed.feedId = ?";

	private static final String _FINDER_COLUMN_G_F_FEEDID_3 =
		"(journalFeed.feedId IS NULL OR journalFeed.feedId = '')";

	public JournalFeedPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("id", "id_");

		setDBColumnNames(dbColumnNames);

		setModelClass(JournalFeed.class);

		setModelImplClass(JournalFeedImpl.class);
		setModelPKClass(long.class);

		setTable(JournalFeedTable.INSTANCE);
	}

	/**
	 * Caches the journal feed in the entity cache if it is enabled.
	 *
	 * @param journalFeed the journal feed
	 */
	@Override
	public void cacheResult(JournalFeed journalFeed) {
		if (journalFeed.getCtCollectionId() != 0) {
			journalFeed.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			JournalFeedImpl.class, journalFeed.getPrimaryKey(), journalFeed);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {journalFeed.getUuid(), journalFeed.getGroupId()},
			journalFeed);

		finderCache.putResult(
			_finderPathFetchByG_F,
			new Object[] {journalFeed.getGroupId(), journalFeed.getFeedId()},
			journalFeed);

		journalFeed.resetOriginalValues();
	}

	/**
	 * Caches the journal feeds in the entity cache if it is enabled.
	 *
	 * @param journalFeeds the journal feeds
	 */
	@Override
	public void cacheResult(List<JournalFeed> journalFeeds) {
		for (JournalFeed journalFeed : journalFeeds) {
			if (journalFeed.getCtCollectionId() != 0) {
				journalFeed.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					JournalFeedImpl.class, journalFeed.getPrimaryKey()) ==
						null) {

				cacheResult(journalFeed);
			}
			else {
				journalFeed.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all journal feeds.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(JournalFeedImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the journal feed.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(JournalFeed journalFeed) {
		entityCache.removeResult(
			JournalFeedImpl.class, journalFeed.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((JournalFeedModelImpl)journalFeed, true);
	}

	@Override
	public void clearCache(List<JournalFeed> journalFeeds) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (JournalFeed journalFeed : journalFeeds) {
			entityCache.removeResult(
				JournalFeedImpl.class, journalFeed.getPrimaryKey());

			clearUniqueFindersCache((JournalFeedModelImpl)journalFeed, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(JournalFeedImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		JournalFeedModelImpl journalFeedModelImpl) {

		Object[] args = new Object[] {
			journalFeedModelImpl.getUuid(), journalFeedModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, journalFeedModelImpl, false);

		args = new Object[] {
			journalFeedModelImpl.getGroupId(), journalFeedModelImpl.getFeedId()
		};

		finderCache.putResult(
			_finderPathCountByG_F, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_F, args, journalFeedModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		JournalFeedModelImpl journalFeedModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				journalFeedModelImpl.getUuid(),
				journalFeedModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((journalFeedModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				journalFeedModelImpl.getOriginalUuid(),
				journalFeedModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				journalFeedModelImpl.getGroupId(),
				journalFeedModelImpl.getFeedId()
			};

			finderCache.removeResult(_finderPathCountByG_F, args);
			finderCache.removeResult(_finderPathFetchByG_F, args);
		}

		if ((journalFeedModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_F.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				journalFeedModelImpl.getOriginalGroupId(),
				journalFeedModelImpl.getOriginalFeedId()
			};

			finderCache.removeResult(_finderPathCountByG_F, args);
			finderCache.removeResult(_finderPathFetchByG_F, args);
		}
	}

	/**
	 * Creates a new journal feed with the primary key. Does not add the journal feed to the database.
	 *
	 * @param id the primary key for the new journal feed
	 * @return the new journal feed
	 */
	@Override
	public JournalFeed create(long id) {
		JournalFeed journalFeed = new JournalFeedImpl();

		journalFeed.setNew(true);
		journalFeed.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalFeed.setUuid(uuid);

		journalFeed.setCompanyId(CompanyThreadLocal.getCompanyId());

		return journalFeed;
	}

	/**
	 * Removes the journal feed with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the journal feed
	 * @return the journal feed that was removed
	 * @throws NoSuchFeedException if a journal feed with the primary key could not be found
	 */
	@Override
	public JournalFeed remove(long id) throws NoSuchFeedException {
		return remove((Serializable)id);
	}

	/**
	 * Removes the journal feed with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the journal feed
	 * @return the journal feed that was removed
	 * @throws NoSuchFeedException if a journal feed with the primary key could not be found
	 */
	@Override
	public JournalFeed remove(Serializable primaryKey)
		throws NoSuchFeedException {

		Session session = null;

		try {
			session = openSession();

			JournalFeed journalFeed = (JournalFeed)session.get(
				JournalFeedImpl.class, primaryKey);

			if (journalFeed == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFeedException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(journalFeed);
		}
		catch (NoSuchFeedException noSuchEntityException) {
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
	protected JournalFeed removeImpl(JournalFeed journalFeed) {
		if (!ctPersistenceHelper.isRemove(journalFeed)) {
			return journalFeed;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(journalFeed)) {
				journalFeed = (JournalFeed)session.get(
					JournalFeedImpl.class, journalFeed.getPrimaryKeyObj());
			}

			if (journalFeed != null) {
				session.delete(journalFeed);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (journalFeed != null) {
			clearCache(journalFeed);
		}

		return journalFeed;
	}

	@Override
	public JournalFeed updateImpl(JournalFeed journalFeed) {
		boolean isNew = journalFeed.isNew();

		if (!(journalFeed instanceof JournalFeedModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(journalFeed.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(journalFeed);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in journalFeed proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom JournalFeed implementation " +
					journalFeed.getClass());
		}

		JournalFeedModelImpl journalFeedModelImpl =
			(JournalFeedModelImpl)journalFeed;

		if (Validator.isNull(journalFeed.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalFeed.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (journalFeed.getCreateDate() == null)) {
			if (serviceContext == null) {
				journalFeed.setCreateDate(now);
			}
			else {
				journalFeed.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!journalFeedModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				journalFeed.setModifiedDate(now);
			}
			else {
				journalFeed.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(journalFeed)) {
				if (!isNew) {
					session.evict(
						JournalFeedImpl.class, journalFeed.getPrimaryKeyObj());
				}

				session.save(journalFeed);

				journalFeed.setNew(false);
			}
			else {
				journalFeed = (JournalFeed)session.merge(journalFeed);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (journalFeed.getCtCollectionId() != 0) {
			journalFeed.resetOriginalValues();

			return journalFeed;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {journalFeedModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				journalFeedModelImpl.getUuid(),
				journalFeedModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {journalFeedModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((journalFeedModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					journalFeedModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {journalFeedModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((journalFeedModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					journalFeedModelImpl.getOriginalUuid(),
					journalFeedModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					journalFeedModelImpl.getUuid(),
					journalFeedModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((journalFeedModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					journalFeedModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {journalFeedModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}
		}

		entityCache.putResult(
			JournalFeedImpl.class, journalFeed.getPrimaryKey(), journalFeed,
			false);

		clearUniqueFindersCache(journalFeedModelImpl, false);
		cacheUniqueFindersCache(journalFeedModelImpl);

		journalFeed.resetOriginalValues();

		return journalFeed;
	}

	/**
	 * Returns the journal feed with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal feed
	 * @return the journal feed
	 * @throws NoSuchFeedException if a journal feed with the primary key could not be found
	 */
	@Override
	public JournalFeed findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFeedException {

		JournalFeed journalFeed = fetchByPrimaryKey(primaryKey);

		if (journalFeed == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFeedException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return journalFeed;
	}

	/**
	 * Returns the journal feed with the primary key or throws a <code>NoSuchFeedException</code> if it could not be found.
	 *
	 * @param id the primary key of the journal feed
	 * @return the journal feed
	 * @throws NoSuchFeedException if a journal feed with the primary key could not be found
	 */
	@Override
	public JournalFeed findByPrimaryKey(long id) throws NoSuchFeedException {
		return findByPrimaryKey((Serializable)id);
	}

	/**
	 * Returns the journal feed with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal feed
	 * @return the journal feed, or <code>null</code> if a journal feed with the primary key could not be found
	 */
	@Override
	public JournalFeed fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(JournalFeed.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		JournalFeed journalFeed = null;

		Session session = null;

		try {
			session = openSession();

			journalFeed = (JournalFeed)session.get(
				JournalFeedImpl.class, primaryKey);

			if (journalFeed != null) {
				cacheResult(journalFeed);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return journalFeed;
	}

	/**
	 * Returns the journal feed with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the journal feed
	 * @return the journal feed, or <code>null</code> if a journal feed with the primary key could not be found
	 */
	@Override
	public JournalFeed fetchByPrimaryKey(long id) {
		return fetchByPrimaryKey((Serializable)id);
	}

	@Override
	public Map<Serializable, JournalFeed> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(JournalFeed.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, JournalFeed> map =
			new HashMap<Serializable, JournalFeed>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			JournalFeed journalFeed = fetchByPrimaryKey(primaryKey);

			if (journalFeed != null) {
				map.put(primaryKey, journalFeed);
			}

			return map;
		}

		StringBundler sb = new StringBundler(primaryKeys.size() * 2 + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (JournalFeed journalFeed : (List<JournalFeed>)query.list()) {
				map.put(journalFeed.getPrimaryKeyObj(), journalFeed);

				cacheResult(journalFeed);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the journal feeds.
	 *
	 * @return the journal feeds
	 */
	@Override
	public List<JournalFeed> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal feeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFeedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @return the range of journal feeds
	 */
	@Override
	public List<JournalFeed> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal feeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFeedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal feeds
	 */
	@Override
	public List<JournalFeed> findAll(
		int start, int end, OrderByComparator<JournalFeed> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal feeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalFeedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of journal feeds
	 */
	@Override
	public List<JournalFeed> findAll(
		int start, int end, OrderByComparator<JournalFeed> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalFeed.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<JournalFeed> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JournalFeed>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_JOURNALFEED);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_JOURNALFEED;

				sql = sql.concat(JournalFeedModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<JournalFeed>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Removes all the journal feeds from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (JournalFeed journalFeed : findAll()) {
			remove(journalFeed);
		}
	}

	/**
	 * Returns the number of journal feeds.
	 *
	 * @return the number of journal feeds
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalFeed.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_JOURNALFEED);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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
		return "id_";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_JOURNALFEED;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.get(ctColumnResolutionType);
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return JournalFeedModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "JournalFeed";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("feedId");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("description");
		ctStrictColumnNames.add("DDMStructureKey");
		ctStrictColumnNames.add("DDMTemplateKey");
		ctStrictColumnNames.add("DDMRendererTemplateKey");
		ctStrictColumnNames.add("delta");
		ctStrictColumnNames.add("orderByCol");
		ctStrictColumnNames.add("orderByType");
		ctStrictColumnNames.add("targetLayoutFriendlyUrl");
		ctStrictColumnNames.add("targetPortletId");
		ctStrictColumnNames.add("contentField");
		ctStrictColumnNames.add("feedFormat");
		ctStrictColumnNames.add("feedVersion");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("id_"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(new String[] {"groupId", "feedId"});
	}

	/**
	 * Initializes the journal feed persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			JournalFeedImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			JournalFeedImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			JournalFeedImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			JournalFeedImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			JournalFeedModelImpl.UUID_COLUMN_BITMASK |
			JournalFeedModelImpl.FEEDID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			JournalFeedImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			JournalFeedModelImpl.UUID_COLUMN_BITMASK |
			JournalFeedModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			JournalFeedImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			JournalFeedImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			JournalFeedModelImpl.UUID_COLUMN_BITMASK |
			JournalFeedModelImpl.COMPANYID_COLUMN_BITMASK |
			JournalFeedModelImpl.FEEDID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			JournalFeedImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			JournalFeedImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] {Long.class.getName()},
			JournalFeedModelImpl.GROUPID_COLUMN_BITMASK |
			JournalFeedModelImpl.FEEDID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathFetchByG_F = new FinderPath(
			JournalFeedImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_F",
			new String[] {Long.class.getName(), String.class.getName()},
			JournalFeedModelImpl.GROUPID_COLUMN_BITMASK |
			JournalFeedModelImpl.FEEDID_COLUMN_BITMASK);

		_finderPathCountByG_F = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(JournalFeedImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = JournalPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = JournalPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = JournalPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_JOURNALFEED =
		"SELECT journalFeed FROM JournalFeed journalFeed";

	private static final String _SQL_SELECT_JOURNALFEED_WHERE =
		"SELECT journalFeed FROM JournalFeed journalFeed WHERE ";

	private static final String _SQL_COUNT_JOURNALFEED =
		"SELECT COUNT(journalFeed) FROM JournalFeed journalFeed";

	private static final String _SQL_COUNT_JOURNALFEED_WHERE =
		"SELECT COUNT(journalFeed) FROM JournalFeed journalFeed WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"journalFeed.id_";

	private static final String _FILTER_SQL_SELECT_JOURNALFEED_WHERE =
		"SELECT DISTINCT {journalFeed.*} FROM JournalFeed journalFeed WHERE ";

	private static final String
		_FILTER_SQL_SELECT_JOURNALFEED_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {JournalFeed.*} FROM (SELECT DISTINCT journalFeed.id_ FROM JournalFeed journalFeed WHERE ";

	private static final String
		_FILTER_SQL_SELECT_JOURNALFEED_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN JournalFeed ON TEMP_TABLE.id_ = JournalFeed.id_";

	private static final String _FILTER_SQL_COUNT_JOURNALFEED_WHERE =
		"SELECT COUNT(DISTINCT journalFeed.id_) AS COUNT_VALUE FROM JournalFeed journalFeed WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "journalFeed";

	private static final String _FILTER_ENTITY_TABLE = "JournalFeed";

	private static final String _ORDER_BY_ENTITY_ALIAS = "journalFeed.";

	private static final String _ORDER_BY_ENTITY_TABLE = "JournalFeed.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No JournalFeed exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No JournalFeed exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		JournalFeedPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "id"});

	static {
		try {
			Class.forName(JournalPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}