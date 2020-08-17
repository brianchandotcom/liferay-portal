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

package com.liferay.message.boards.service.persistence.impl;

import com.liferay.message.boards.exception.NoSuchDiscussionException;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.model.MBDiscussionTable;
import com.liferay.message.boards.model.impl.MBDiscussionImpl;
import com.liferay.message.boards.model.impl.MBDiscussionModelImpl;
import com.liferay.message.boards.service.persistence.MBDiscussionPersistence;
import com.liferay.message.boards.service.persistence.impl.constants.MBPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
 * The persistence implementation for the message boards discussion service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = MBDiscussionPersistence.class)
public class MBDiscussionPersistenceImpl
	extends BasePersistenceImpl<MBDiscussion>
	implements MBDiscussionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MBDiscussionUtil</code> to access the message boards discussion persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MBDiscussionImpl.class.getName();

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
	 * Returns all the message boards discussions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards discussions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @return the range of matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards discussions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MBDiscussion> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards discussions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MBDiscussion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBDiscussion.class);

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

		List<MBDiscussion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBDiscussion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBDiscussion mbDiscussion : list) {
					if (!uuid.equals(mbDiscussion.getUuid())) {
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

			sb.append(_SQL_SELECT_MBDISCUSSION_WHERE);

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
				sb.append(MBDiscussionModelImpl.ORDER_BY_JPQL);
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

				list = (List<MBDiscussion>)QueryUtil.list(
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
	 * Returns the first message boards discussion in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByUuid_First(
			String uuid, OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByUuid_First(uuid, orderByComparator);

		if (mbDiscussion != null) {
			return mbDiscussion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchDiscussionException(sb.toString());
	}

	/**
	 * Returns the first message boards discussion in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByUuid_First(
		String uuid, OrderByComparator<MBDiscussion> orderByComparator) {

		List<MBDiscussion> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards discussion in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByUuid_Last(
			String uuid, OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByUuid_Last(uuid, orderByComparator);

		if (mbDiscussion != null) {
			return mbDiscussion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchDiscussionException(sb.toString());
	}

	/**
	 * Returns the last message boards discussion in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByUuid_Last(
		String uuid, OrderByComparator<MBDiscussion> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<MBDiscussion> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards discussions before and after the current message boards discussion in the ordered set where uuid = &#63;.
	 *
	 * @param discussionId the primary key of the current message boards discussion
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards discussion
	 * @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion[] findByUuid_PrevAndNext(
			long discussionId, String uuid,
			OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException {

		uuid = Objects.toString(uuid, "");

		MBDiscussion mbDiscussion = findByPrimaryKey(discussionId);

		Session session = null;

		try {
			session = openSession();

			MBDiscussion[] array = new MBDiscussionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, mbDiscussion, uuid, orderByComparator, true);

			array[1] = mbDiscussion;

			array[2] = getByUuid_PrevAndNext(
				session, mbDiscussion, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBDiscussion getByUuid_PrevAndNext(
		Session session, MBDiscussion mbDiscussion, String uuid,
		OrderByComparator<MBDiscussion> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MBDISCUSSION_WHERE);

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
			sb.append(MBDiscussionModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(mbDiscussion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBDiscussion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards discussions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (MBDiscussion mbDiscussion :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mbDiscussion);
		}
	}

	/**
	 * Returns the number of message boards discussions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching message boards discussions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBDiscussion.class);

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

			sb.append(_SQL_COUNT_MBDISCUSSION_WHERE);

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
		"mbDiscussion.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(mbDiscussion.uuid IS NULL OR mbDiscussion.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the message boards discussion where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchDiscussionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByUUID_G(String uuid, long groupId)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByUUID_G(uuid, groupId);

		if (mbDiscussion == null) {
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

			throw new NoSuchDiscussionException(sb.toString());
		}

		return mbDiscussion;
	}

	/**
	 * Returns the message boards discussion where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the message boards discussion where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBDiscussion.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof MBDiscussion) {
			MBDiscussion mbDiscussion = (MBDiscussion)result;

			if (!Objects.equals(uuid, mbDiscussion.getUuid()) ||
				(groupId != mbDiscussion.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_MBDISCUSSION_WHERE);

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

				List<MBDiscussion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					MBDiscussion mbDiscussion = list.get(0);

					result = mbDiscussion;

					cacheResult(mbDiscussion);
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
			return (MBDiscussion)result;
		}
	}

	/**
	 * Removes the message boards discussion where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the message boards discussion that was removed
	 */
	@Override
	public MBDiscussion removeByUUID_G(String uuid, long groupId)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = findByUUID_G(uuid, groupId);

		return remove(mbDiscussion);
	}

	/**
	 * Returns the number of message boards discussions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching message boards discussions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBDiscussion.class);

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

			sb.append(_SQL_COUNT_MBDISCUSSION_WHERE);

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
		"mbDiscussion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(mbDiscussion.uuid IS NULL OR mbDiscussion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"mbDiscussion.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the message boards discussions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards discussions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @return the range of matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards discussions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBDiscussion> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards discussions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBDiscussion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBDiscussion.class);

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

		List<MBDiscussion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBDiscussion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBDiscussion mbDiscussion : list) {
					if (!uuid.equals(mbDiscussion.getUuid()) ||
						(companyId != mbDiscussion.getCompanyId())) {

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

			sb.append(_SQL_SELECT_MBDISCUSSION_WHERE);

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
				sb.append(MBDiscussionModelImpl.ORDER_BY_JPQL);
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

				list = (List<MBDiscussion>)QueryUtil.list(
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
	 * Returns the first message boards discussion in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (mbDiscussion != null) {
			return mbDiscussion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDiscussionException(sb.toString());
	}

	/**
	 * Returns the first message boards discussion in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<MBDiscussion> orderByComparator) {

		List<MBDiscussion> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards discussion in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (mbDiscussion != null) {
			return mbDiscussion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDiscussionException(sb.toString());
	}

	/**
	 * Returns the last message boards discussion in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<MBDiscussion> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<MBDiscussion> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards discussions before and after the current message boards discussion in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param discussionId the primary key of the current message boards discussion
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards discussion
	 * @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion[] findByUuid_C_PrevAndNext(
			long discussionId, String uuid, long companyId,
			OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException {

		uuid = Objects.toString(uuid, "");

		MBDiscussion mbDiscussion = findByPrimaryKey(discussionId);

		Session session = null;

		try {
			session = openSession();

			MBDiscussion[] array = new MBDiscussionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, mbDiscussion, uuid, companyId, orderByComparator,
				true);

			array[1] = mbDiscussion;

			array[2] = getByUuid_C_PrevAndNext(
				session, mbDiscussion, uuid, companyId, orderByComparator,
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

	protected MBDiscussion getByUuid_C_PrevAndNext(
		Session session, MBDiscussion mbDiscussion, String uuid, long companyId,
		OrderByComparator<MBDiscussion> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_MBDISCUSSION_WHERE);

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
			sb.append(MBDiscussionModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(mbDiscussion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBDiscussion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards discussions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (MBDiscussion mbDiscussion :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(mbDiscussion);
		}
	}

	/**
	 * Returns the number of message boards discussions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching message boards discussions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBDiscussion.class);

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

			sb.append(_SQL_COUNT_MBDISCUSSION_WHERE);

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
		"mbDiscussion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(mbDiscussion.uuid IS NULL OR mbDiscussion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"mbDiscussion.companyId = ?";

	private FinderPath _finderPathFetchByThreadId;
	private FinderPath _finderPathCountByThreadId;

	/**
	 * Returns the message boards discussion where threadId = &#63; or throws a <code>NoSuchDiscussionException</code> if it could not be found.
	 *
	 * @param threadId the thread ID
	 * @return the matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByThreadId(long threadId)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByThreadId(threadId);

		if (mbDiscussion == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("threadId=");
			sb.append(threadId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDiscussionException(sb.toString());
		}

		return mbDiscussion;
	}

	/**
	 * Returns the message boards discussion where threadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param threadId the thread ID
	 * @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByThreadId(long threadId) {
		return fetchByThreadId(threadId, true);
	}

	/**
	 * Returns the message boards discussion where threadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param threadId the thread ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByThreadId(long threadId, boolean useFinderCache) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBDiscussion.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {threadId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByThreadId, finderArgs, this);
		}

		if (result instanceof MBDiscussion) {
			MBDiscussion mbDiscussion = (MBDiscussion)result;

			if (threadId != mbDiscussion.getThreadId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_MBDISCUSSION_WHERE);

			sb.append(_FINDER_COLUMN_THREADID_THREADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(threadId);

				List<MBDiscussion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByThreadId, finderArgs, list);
					}
				}
				else {
					MBDiscussion mbDiscussion = list.get(0);

					result = mbDiscussion;

					cacheResult(mbDiscussion);
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
			return (MBDiscussion)result;
		}
	}

	/**
	 * Removes the message boards discussion where threadId = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @return the message boards discussion that was removed
	 */
	@Override
	public MBDiscussion removeByThreadId(long threadId)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = findByThreadId(threadId);

		return remove(mbDiscussion);
	}

	/**
	 * Returns the number of message boards discussions where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the number of matching message boards discussions
	 */
	@Override
	public int countByThreadId(long threadId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBDiscussion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByThreadId;

			finderArgs = new Object[] {threadId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MBDISCUSSION_WHERE);

			sb.append(_FINDER_COLUMN_THREADID_THREADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(threadId);

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

	private static final String _FINDER_COLUMN_THREADID_THREADID_2 =
		"mbDiscussion.threadId = ?";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the message boards discussion where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchDiscussionException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByC_C(long classNameId, long classPK)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByC_C(classNameId, classPK);

		if (mbDiscussion == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDiscussionException(sb.toString());
		}

		return mbDiscussion;
	}

	/**
	 * Returns the message boards discussion where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByC_C(long classNameId, long classPK) {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the message boards discussion where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBDiscussion.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {classNameId, classPK};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByC_C, finderArgs, this);
		}

		if (result instanceof MBDiscussion) {
			MBDiscussion mbDiscussion = (MBDiscussion)result;

			if ((classNameId != mbDiscussion.getClassNameId()) ||
				(classPK != mbDiscussion.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_MBDISCUSSION_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				List<MBDiscussion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					MBDiscussion mbDiscussion = list.get(0);

					result = mbDiscussion;

					cacheResult(mbDiscussion);
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
			return (MBDiscussion)result;
		}
	}

	/**
	 * Removes the message boards discussion where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the message boards discussion that was removed
	 */
	@Override
	public MBDiscussion removeByC_C(long classNameId, long classPK)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = findByC_C(classNameId, classPK);

		return remove(mbDiscussion);
	}

	/**
	 * Returns the number of message boards discussions where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching message boards discussions
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBDiscussion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C;

			finderArgs = new Object[] {classNameId, classPK};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MBDISCUSSION_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"mbDiscussion.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"mbDiscussion.classPK = ?";

	public MBDiscussionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(MBDiscussion.class);

		setModelImplClass(MBDiscussionImpl.class);
		setModelPKClass(long.class);

		setTable(MBDiscussionTable.INSTANCE);
	}

	/**
	 * Caches the message boards discussion in the entity cache if it is enabled.
	 *
	 * @param mbDiscussion the message boards discussion
	 */
	@Override
	public void cacheResult(MBDiscussion mbDiscussion) {
		if (mbDiscussion.getCtCollectionId() != 0) {
			mbDiscussion.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			MBDiscussionImpl.class, mbDiscussion.getPrimaryKey(), mbDiscussion);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {mbDiscussion.getUuid(), mbDiscussion.getGroupId()},
			mbDiscussion);

		finderCache.putResult(
			_finderPathFetchByThreadId,
			new Object[] {mbDiscussion.getThreadId()}, mbDiscussion);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				mbDiscussion.getClassNameId(), mbDiscussion.getClassPK()
			},
			mbDiscussion);

		mbDiscussion.resetOriginalValues();
	}

	/**
	 * Caches the message boards discussions in the entity cache if it is enabled.
	 *
	 * @param mbDiscussions the message boards discussions
	 */
	@Override
	public void cacheResult(List<MBDiscussion> mbDiscussions) {
		for (MBDiscussion mbDiscussion : mbDiscussions) {
			if (mbDiscussion.getCtCollectionId() != 0) {
				mbDiscussion.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					MBDiscussionImpl.class, mbDiscussion.getPrimaryKey()) ==
						null) {

				cacheResult(mbDiscussion);
			}
			else {
				mbDiscussion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all message boards discussions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MBDiscussionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the message boards discussion.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MBDiscussion mbDiscussion) {
		entityCache.removeResult(
			MBDiscussionImpl.class, mbDiscussion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((MBDiscussionModelImpl)mbDiscussion, true);
	}

	@Override
	public void clearCache(List<MBDiscussion> mbDiscussions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MBDiscussion mbDiscussion : mbDiscussions) {
			entityCache.removeResult(
				MBDiscussionImpl.class, mbDiscussion.getPrimaryKey());

			clearUniqueFindersCache((MBDiscussionModelImpl)mbDiscussion, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(MBDiscussionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		MBDiscussionModelImpl mbDiscussionModelImpl) {

		Object[] args = new Object[] {
			mbDiscussionModelImpl.getUuid(), mbDiscussionModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, mbDiscussionModelImpl, false);

		args = new Object[] {mbDiscussionModelImpl.getThreadId()};

		finderCache.putResult(
			_finderPathCountByThreadId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByThreadId, args, mbDiscussionModelImpl, false);

		args = new Object[] {
			mbDiscussionModelImpl.getClassNameId(),
			mbDiscussionModelImpl.getClassPK()
		};

		finderCache.putResult(
			_finderPathCountByC_C, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_C, args, mbDiscussionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		MBDiscussionModelImpl mbDiscussionModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				mbDiscussionModelImpl.getUuid(),
				mbDiscussionModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((mbDiscussionModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				mbDiscussionModelImpl.getOriginalUuid(),
				mbDiscussionModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {mbDiscussionModelImpl.getThreadId()};

			finderCache.removeResult(_finderPathCountByThreadId, args);
			finderCache.removeResult(_finderPathFetchByThreadId, args);
		}

		if ((mbDiscussionModelImpl.getColumnBitmask() &
			 _finderPathFetchByThreadId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				mbDiscussionModelImpl.getOriginalThreadId()
			};

			finderCache.removeResult(_finderPathCountByThreadId, args);
			finderCache.removeResult(_finderPathFetchByThreadId, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				mbDiscussionModelImpl.getClassNameId(),
				mbDiscussionModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}

		if ((mbDiscussionModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				mbDiscussionModelImpl.getOriginalClassNameId(),
				mbDiscussionModelImpl.getOriginalClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}
	}

	/**
	 * Creates a new message boards discussion with the primary key. Does not add the message boards discussion to the database.
	 *
	 * @param discussionId the primary key for the new message boards discussion
	 * @return the new message boards discussion
	 */
	@Override
	public MBDiscussion create(long discussionId) {
		MBDiscussion mbDiscussion = new MBDiscussionImpl();

		mbDiscussion.setNew(true);
		mbDiscussion.setPrimaryKey(discussionId);

		String uuid = PortalUUIDUtil.generate();

		mbDiscussion.setUuid(uuid);

		mbDiscussion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return mbDiscussion;
	}

	/**
	 * Removes the message boards discussion with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param discussionId the primary key of the message boards discussion
	 * @return the message boards discussion that was removed
	 * @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion remove(long discussionId)
		throws NoSuchDiscussionException {

		return remove((Serializable)discussionId);
	}

	/**
	 * Removes the message boards discussion with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message boards discussion
	 * @return the message boards discussion that was removed
	 * @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion remove(Serializable primaryKey)
		throws NoSuchDiscussionException {

		Session session = null;

		try {
			session = openSession();

			MBDiscussion mbDiscussion = (MBDiscussion)session.get(
				MBDiscussionImpl.class, primaryKey);

			if (mbDiscussion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDiscussionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(mbDiscussion);
		}
		catch (NoSuchDiscussionException noSuchEntityException) {
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
	protected MBDiscussion removeImpl(MBDiscussion mbDiscussion) {
		if (!ctPersistenceHelper.isRemove(mbDiscussion)) {
			return mbDiscussion;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mbDiscussion)) {
				mbDiscussion = (MBDiscussion)session.get(
					MBDiscussionImpl.class, mbDiscussion.getPrimaryKeyObj());
			}

			if (mbDiscussion != null) {
				session.delete(mbDiscussion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mbDiscussion != null) {
			clearCache(mbDiscussion);
		}

		return mbDiscussion;
	}

	@Override
	public MBDiscussion updateImpl(MBDiscussion mbDiscussion) {
		boolean isNew = mbDiscussion.isNew();

		if (!(mbDiscussion instanceof MBDiscussionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(mbDiscussion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					mbDiscussion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in mbDiscussion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MBDiscussion implementation " +
					mbDiscussion.getClass());
		}

		MBDiscussionModelImpl mbDiscussionModelImpl =
			(MBDiscussionModelImpl)mbDiscussion;

		if (Validator.isNull(mbDiscussion.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mbDiscussion.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (mbDiscussion.getCreateDate() == null)) {
			if (serviceContext == null) {
				mbDiscussion.setCreateDate(now);
			}
			else {
				mbDiscussion.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!mbDiscussionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				mbDiscussion.setModifiedDate(now);
			}
			else {
				mbDiscussion.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(mbDiscussion)) {
				if (!isNew) {
					session.evict(
						MBDiscussionImpl.class,
						mbDiscussion.getPrimaryKeyObj());
				}

				session.save(mbDiscussion);

				mbDiscussion.setNew(false);
			}
			else {
				mbDiscussion = (MBDiscussion)session.merge(mbDiscussion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mbDiscussion.getCtCollectionId() != 0) {
			mbDiscussion.resetOriginalValues();

			return mbDiscussion;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {mbDiscussionModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				mbDiscussionModelImpl.getUuid(),
				mbDiscussionModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((mbDiscussionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbDiscussionModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {mbDiscussionModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((mbDiscussionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbDiscussionModelImpl.getOriginalUuid(),
					mbDiscussionModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					mbDiscussionModelImpl.getUuid(),
					mbDiscussionModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}
		}

		entityCache.putResult(
			MBDiscussionImpl.class, mbDiscussion.getPrimaryKey(), mbDiscussion,
			false);

		clearUniqueFindersCache(mbDiscussionModelImpl, false);
		cacheUniqueFindersCache(mbDiscussionModelImpl);

		mbDiscussion.resetOriginalValues();

		return mbDiscussion;
	}

	/**
	 * Returns the message boards discussion with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards discussion
	 * @return the message boards discussion
	 * @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByPrimaryKey(primaryKey);

		if (mbDiscussion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDiscussionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return mbDiscussion;
	}

	/**
	 * Returns the message boards discussion with the primary key or throws a <code>NoSuchDiscussionException</code> if it could not be found.
	 *
	 * @param discussionId the primary key of the message boards discussion
	 * @return the message boards discussion
	 * @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion findByPrimaryKey(long discussionId)
		throws NoSuchDiscussionException {

		return findByPrimaryKey((Serializable)discussionId);
	}

	/**
	 * Returns the message boards discussion with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards discussion
	 * @return the message boards discussion, or <code>null</code> if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(MBDiscussion.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		MBDiscussion mbDiscussion = null;

		Session session = null;

		try {
			session = openSession();

			mbDiscussion = (MBDiscussion)session.get(
				MBDiscussionImpl.class, primaryKey);

			if (mbDiscussion != null) {
				cacheResult(mbDiscussion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return mbDiscussion;
	}

	/**
	 * Returns the message boards discussion with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param discussionId the primary key of the message boards discussion
	 * @return the message boards discussion, or <code>null</code> if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion fetchByPrimaryKey(long discussionId) {
		return fetchByPrimaryKey((Serializable)discussionId);
	}

	@Override
	public Map<Serializable, MBDiscussion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(MBDiscussion.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, MBDiscussion> map =
			new HashMap<Serializable, MBDiscussion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			MBDiscussion mbDiscussion = fetchByPrimaryKey(primaryKey);

			if (mbDiscussion != null) {
				map.put(primaryKey, mbDiscussion);
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

			for (MBDiscussion mbDiscussion : (List<MBDiscussion>)query.list()) {
				map.put(mbDiscussion.getPrimaryKeyObj(), mbDiscussion);

				cacheResult(mbDiscussion);
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
	 * Returns all the message boards discussions.
	 *
	 * @return the message boards discussions
	 */
	@Override
	public List<MBDiscussion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards discussions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @return the range of message boards discussions
	 */
	@Override
	public List<MBDiscussion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards discussions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards discussions
	 */
	@Override
	public List<MBDiscussion> findAll(
		int start, int end, OrderByComparator<MBDiscussion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards discussions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of message boards discussions
	 */
	@Override
	public List<MBDiscussion> findAll(
		int start, int end, OrderByComparator<MBDiscussion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBDiscussion.class);

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

		List<MBDiscussion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBDiscussion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MBDISCUSSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MBDISCUSSION;

				sql = sql.concat(MBDiscussionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<MBDiscussion>)QueryUtil.list(
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
	 * Removes all the message boards discussions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MBDiscussion mbDiscussion : findAll()) {
			remove(mbDiscussion);
		}
	}

	/**
	 * Returns the number of message boards discussions.
	 *
	 * @return the number of message boards discussions
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBDiscussion.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_MBDISCUSSION);

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
		return "discussionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MBDISCUSSION;
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
		return MBDiscussionModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "MBDiscussion";
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
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("threadId");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("discussionId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(new String[] {"threadId"});

		_uniqueIndexColumnNames.add(new String[] {"classNameId", "classPK"});
	}

	/**
	 * Initializes the message boards discussion persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			MBDiscussionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			MBDiscussionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			MBDiscussionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			MBDiscussionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			MBDiscussionModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			MBDiscussionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			MBDiscussionModelImpl.UUID_COLUMN_BITMASK |
			MBDiscussionModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			MBDiscussionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			MBDiscussionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			MBDiscussionModelImpl.UUID_COLUMN_BITMASK |
			MBDiscussionModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathFetchByThreadId = new FinderPath(
			MBDiscussionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByThreadId",
			new String[] {Long.class.getName()},
			MBDiscussionModelImpl.THREADID_COLUMN_BITMASK);

		_finderPathCountByThreadId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByThreadId", new String[] {Long.class.getName()});

		_finderPathFetchByC_C = new FinderPath(
			MBDiscussionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			MBDiscussionModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			MBDiscussionModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(MBDiscussionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = MBPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = MBPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = MBPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_MBDISCUSSION =
		"SELECT mbDiscussion FROM MBDiscussion mbDiscussion";

	private static final String _SQL_SELECT_MBDISCUSSION_WHERE =
		"SELECT mbDiscussion FROM MBDiscussion mbDiscussion WHERE ";

	private static final String _SQL_COUNT_MBDISCUSSION =
		"SELECT COUNT(mbDiscussion) FROM MBDiscussion mbDiscussion";

	private static final String _SQL_COUNT_MBDISCUSSION_WHERE =
		"SELECT COUNT(mbDiscussion) FROM MBDiscussion mbDiscussion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "mbDiscussion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MBDiscussion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No MBDiscussion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MBDiscussionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(MBPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}