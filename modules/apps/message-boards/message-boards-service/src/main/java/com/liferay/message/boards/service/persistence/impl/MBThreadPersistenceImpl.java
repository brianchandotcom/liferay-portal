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

import com.liferay.message.boards.exception.NoSuchThreadException;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.model.MBThreadTable;
import com.liferay.message.boards.model.impl.MBThreadImpl;
import com.liferay.message.boards.model.impl.MBThreadModelImpl;
import com.liferay.message.boards.service.persistence.MBThreadPersistence;
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
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

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
 * The persistence implementation for the message boards thread service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = MBThreadPersistence.class)
public class MBThreadPersistenceImpl
	extends BasePersistenceImpl<MBThread> implements MBThreadPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MBThreadUtil</code> to access the message boards thread persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MBThreadImpl.class.getName();

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
	 * Returns all the message boards threads where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

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

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if (!uuid.equals(mbThread.getUuid())) {
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

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
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

				list = (List<MBThread>)QueryUtil.list(
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
	 * Returns the first message boards thread in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByUuid_First(
			String uuid, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByUuid_First(uuid, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByUuid_First(
		String uuid, OrderByComparator<MBThread> orderByComparator) {

		List<MBThread> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByUuid_Last(
			String uuid, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByUuid_Last(uuid, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByUuid_Last(
		String uuid, OrderByComparator<MBThread> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where uuid = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByUuid_PrevAndNext(
			long threadId, String uuid,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		uuid = Objects.toString(uuid, "");

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, mbThread, uuid, orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByUuid_PrevAndNext(
				session, mbThread, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread getByUuid_PrevAndNext(
		Session session, MBThread mbThread, String uuid,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MBTHREAD_WHERE);

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
			sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (MBThread mbThread :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

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

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

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
		"mbThread.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(mbThread.uuid IS NULL OR mbThread.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the message boards thread where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchThreadException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByUUID_G(String uuid, long groupId)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByUUID_G(uuid, groupId);

		if (mbThread == null) {
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

			throw new NoSuchThreadException(sb.toString());
		}

		return mbThread;
	}

	/**
	 * Returns the message boards thread where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the message boards thread where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof MBThread) {
			MBThread mbThread = (MBThread)result;

			if (!Objects.equals(uuid, mbThread.getUuid()) ||
				(groupId != mbThread.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

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

				List<MBThread> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					MBThread mbThread = list.get(0);

					result = mbThread;

					cacheResult(mbThread);
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
			return (MBThread)result;
		}
	}

	/**
	 * Removes the message boards thread where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the message boards thread that was removed
	 */
	@Override
	public MBThread removeByUUID_G(String uuid, long groupId)
		throws NoSuchThreadException {

		MBThread mbThread = findByUUID_G(uuid, groupId);

		return remove(mbThread);
	}

	/**
	 * Returns the number of message boards threads where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

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

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

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
		"mbThread.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(mbThread.uuid IS NULL OR mbThread.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"mbThread.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the message boards threads where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

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

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if (!uuid.equals(mbThread.getUuid()) ||
						(companyId != mbThread.getCompanyId())) {

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

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
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

				list = (List<MBThread>)QueryUtil.list(
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
	 * Returns the first message boards thread in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<MBThread> orderByComparator) {

		List<MBThread> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<MBThread> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByUuid_C_PrevAndNext(
			long threadId, String uuid, long companyId,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		uuid = Objects.toString(uuid, "");

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, mbThread, uuid, companyId, orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByUuid_C_PrevAndNext(
				session, mbThread, uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread getByUuid_C_PrevAndNext(
		Session session, MBThread mbThread, String uuid, long companyId,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_MBTHREAD_WHERE);

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
			sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (MBThread mbThread :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

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

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

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
		"mbThread.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(mbThread.uuid IS NULL OR mbThread.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"mbThread.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the message boards threads where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

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

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if (groupId != mbThread.getGroupId()) {
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

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<MBThread>)QueryUtil.list(
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByGroupId_First(
			long groupId, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByGroupId_First(groupId, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByGroupId_First(
		long groupId, OrderByComparator<MBThread> orderByComparator) {

		List<MBThread> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByGroupId_Last(
			long groupId, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByGroupId_Last(groupId, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByGroupId_Last(
		long groupId, OrderByComparator<MBThread> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByGroupId_PrevAndNext(
			long threadId, long groupId,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, mbThread, groupId, orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByGroupId_PrevAndNext(
				session, mbThread, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread getByGroupId_PrevAndNext(
		Session session, MBThread mbThread, long groupId,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MBTHREAD_WHERE);

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
			sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<MBThread>)QueryUtil.list(
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
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByGroupId_PrevAndNext(
			long threadId, long groupId,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(
				threadId, groupId, orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(
				session, mbThread, groupId, orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByGroupId_PrevAndNext(
				session, mbThread, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread filterGetByGroupId_PrevAndNext(
		Session session, MBThread mbThread, long groupId,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {

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
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (MBThread mbThread :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

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

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
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
		"mbThread.groupId = ? AND mbThread.categoryId != -1";

	private FinderPath _finderPathFetchByRootMessageId;
	private FinderPath _finderPathCountByRootMessageId;

	/**
	 * Returns the message boards thread where rootMessageId = &#63; or throws a <code>NoSuchThreadException</code> if it could not be found.
	 *
	 * @param rootMessageId the root message ID
	 * @return the matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByRootMessageId(long rootMessageId)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByRootMessageId(rootMessageId);

		if (mbThread == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("rootMessageId=");
			sb.append(rootMessageId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchThreadException(sb.toString());
		}

		return mbThread;
	}

	/**
	 * Returns the message boards thread where rootMessageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param rootMessageId the root message ID
	 * @return the matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByRootMessageId(long rootMessageId) {
		return fetchByRootMessageId(rootMessageId, true);
	}

	/**
	 * Returns the message boards thread where rootMessageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param rootMessageId the root message ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByRootMessageId(
		long rootMessageId, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {rootMessageId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByRootMessageId, finderArgs, this);
		}

		if (result instanceof MBThread) {
			MBThread mbThread = (MBThread)result;

			if (rootMessageId != mbThread.getRootMessageId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_ROOTMESSAGEID_ROOTMESSAGEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(rootMessageId);

				List<MBThread> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByRootMessageId, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!productionMode || !useFinderCache) {
								finderArgs = new Object[] {rootMessageId};
							}

							_log.warn(
								"MBThreadPersistenceImpl.fetchByRootMessageId(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					MBThread mbThread = list.get(0);

					result = mbThread;

					cacheResult(mbThread);
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
			return (MBThread)result;
		}
	}

	/**
	 * Removes the message boards thread where rootMessageId = &#63; from the database.
	 *
	 * @param rootMessageId the root message ID
	 * @return the message boards thread that was removed
	 */
	@Override
	public MBThread removeByRootMessageId(long rootMessageId)
		throws NoSuchThreadException {

		MBThread mbThread = findByRootMessageId(rootMessageId);

		return remove(mbThread);
	}

	/**
	 * Returns the number of message boards threads where rootMessageId = &#63;.
	 *
	 * @param rootMessageId the root message ID
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByRootMessageId(long rootMessageId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByRootMessageId;

			finderArgs = new Object[] {rootMessageId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_ROOTMESSAGEID_ROOTMESSAGEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(rootMessageId);

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

	private static final String _FINDER_COLUMN_ROOTMESSAGEID_ROOTMESSAGEID_2 =
		"mbThread.rootMessageId = ?";

	private FinderPath _finderPathWithPaginationFindByG_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C;
	private FinderPath _finderPathCountByG_C;
	private FinderPath _finderPathWithPaginationCountByG_C;

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(long groupId, long categoryId) {
		return findByG_C(
			groupId, categoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(
		long groupId, long categoryId, int start, int end) {

		return findByG_C(groupId, categoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(
		long groupId, long categoryId, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByG_C(
			groupId, categoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(
		long groupId, long categoryId, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C;
				finderArgs = new Object[] {groupId, categoryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C;
			finderArgs = new Object[] {
				groupId, categoryId, start, end, orderByComparator
			};
		}

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
						(categoryId != mbThread.getCategoryId())) {

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

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(categoryId);

				list = (List<MBThread>)QueryUtil.list(
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_First(
			long groupId, long categoryId,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_C_First(
			groupId, categoryId, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", categoryId=");
		sb.append(categoryId);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_First(
		long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator) {

		List<MBThread> list = findByG_C(
			groupId, categoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_Last(
			long groupId, long categoryId,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_C_Last(
			groupId, categoryId, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", categoryId=");
		sb.append(categoryId);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_Last(
		long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator) {

		int count = countByG_C(groupId, categoryId);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_C(
			groupId, categoryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_C_PrevAndNext(
			long threadId, long groupId, long categoryId,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_C_PrevAndNext(
				session, mbThread, groupId, categoryId, orderByComparator,
				true);

			array[1] = mbThread;

			array[2] = getByG_C_PrevAndNext(
				session, mbThread, groupId, categoryId, orderByComparator,
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

	protected MBThread getByG_C_PrevAndNext(
		Session session, MBThread mbThread, long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

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
			sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(categoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C(long groupId, long categoryId) {
		return filterFindByG_C(
			groupId, categoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C(
		long groupId, long categoryId, int start, int end) {

		return filterFindByG_C(groupId, categoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C(
		long groupId, long categoryId, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C(
				groupId, categoryId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(categoryId);

			return (List<MBThread>)QueryUtil.list(
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
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_C_PrevAndNext(
			long threadId, long groupId, long categoryId,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_PrevAndNext(
				threadId, groupId, categoryId, orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_C_PrevAndNext(
				session, mbThread, groupId, categoryId, orderByComparator,
				true);

			array[1] = mbThread;

			array[2] = filterGetByG_C_PrevAndNext(
				session, mbThread, groupId, categoryId, orderByComparator,
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

	protected MBThread filterGetByG_C_PrevAndNext(
		Session session, MBThread mbThread, long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(categoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C(long groupId, long[] categoryIds) {
		return filterFindByG_C(
			groupId, categoryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C(
		long groupId, long[] categoryIds, int start, int end) {

		return filterFindByG_C(groupId, categoryIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C(
		long groupId, long[] categoryIds, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C(
				groupId, categoryIds, start, end, orderByComparator);
		}

		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.sortedUnique(categoryIds);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

		if (categoryIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_C_CATEGORYID_7);

			sb.append(StringUtil.merge(categoryIds));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<MBThread>)QueryUtil.list(
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
	 * Returns all the message boards threads where groupId = &#63; and categoryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(long groupId, long[] categoryIds) {
		return findByG_C(
			groupId, categoryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(
		long groupId, long[] categoryIds, int start, int end) {

		return findByG_C(groupId, categoryIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(
		long groupId, long[] categoryIds, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByG_C(
			groupId, categoryIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(
		long groupId, long[] categoryIds, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.sortedUnique(categoryIds);
		}

		if (categoryIds.length == 1) {
			return findByG_C(
				groupId, categoryIds[0], start, end, orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					groupId, StringUtil.merge(categoryIds)
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				groupId, StringUtil.merge(categoryIds), start, end,
				orderByComparator
			};
		}

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				_finderPathWithPaginationFindByG_C, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
						!ArrayUtil.contains(
							categoryIds, mbThread.getCategoryId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

			if (categoryIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_C_CATEGORYID_7);

				sb.append(StringUtil.merge(categoryIds));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<MBThread>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_C, finderArgs, list);
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
	 * Removes all the message boards threads where groupId = &#63; and categoryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 */
	@Override
	public void removeByG_C(long groupId, long categoryId) {
		for (MBThread mbThread :
				findByG_C(
					groupId, categoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_C(long groupId, long categoryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C;

			finderArgs = new Object[] {groupId, categoryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(categoryId);

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
	 * Returns the number of message boards threads where groupId = &#63; and categoryId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_C(long groupId, long[] categoryIds) {
		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.sortedUnique(categoryIds);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {groupId, StringUtil.merge(categoryIds)};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_C, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

			if (categoryIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_C_CATEGORYID_7);

				sb.append(StringUtil.merge(categoryIds));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByG_C, finderArgs, count);
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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_C(long groupId, long categoryId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C(groupId, categoryId);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(categoryId);

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

	/**
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_C(long groupId, long[] categoryIds) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C(groupId, categoryIds);
		}

		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.sortedUnique(categoryIds);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

		if (categoryIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_C_CATEGORYID_7);

			sb.append(StringUtil.merge(categoryIds));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
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

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 =
		"mbThread.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_CATEGORYID_2 =
		"mbThread.categoryId = ?";

	private static final String _FINDER_COLUMN_G_C_CATEGORYID_7 =
		"mbThread.categoryId IN (";

	private FinderPath _finderPathWithPaginationFindByG_NotC;
	private FinderPath _finderPathWithPaginationCountByG_NotC;

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC(long groupId, long categoryId) {
		return findByG_NotC(
			groupId, categoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC(
		long groupId, long categoryId, int start, int end) {

		return findByG_NotC(groupId, categoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC(
		long groupId, long categoryId, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByG_NotC(
			groupId, categoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC(
		long groupId, long categoryId, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_NotC;
		finderArgs = new Object[] {
			groupId, categoryId, start, end, orderByComparator
		};

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
						(categoryId == mbThread.getCategoryId())) {

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

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_NOTC_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_NOTC_CATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(categoryId);

				list = (List<MBThread>)QueryUtil.list(
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_NotC_First(
			long groupId, long categoryId,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_NotC_First(
			groupId, categoryId, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", categoryId!=");
		sb.append(categoryId);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_NotC_First(
		long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator) {

		List<MBThread> list = findByG_NotC(
			groupId, categoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_NotC_Last(
			long groupId, long categoryId,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_NotC_Last(
			groupId, categoryId, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", categoryId!=");
		sb.append(categoryId);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_NotC_Last(
		long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator) {

		int count = countByG_NotC(groupId, categoryId);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_NotC(
			groupId, categoryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_NotC_PrevAndNext(
			long threadId, long groupId, long categoryId,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_NotC_PrevAndNext(
				session, mbThread, groupId, categoryId, orderByComparator,
				true);

			array[1] = mbThread;

			array[2] = getByG_NotC_PrevAndNext(
				session, mbThread, groupId, categoryId, orderByComparator,
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

	protected MBThread getByG_NotC_PrevAndNext(
		Session session, MBThread mbThread, long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_NOTC_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_CATEGORYID_2);

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
			sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(categoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC(long groupId, long categoryId) {
		return filterFindByG_NotC(
			groupId, categoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC(
		long groupId, long categoryId, int start, int end) {

		return filterFindByG_NotC(groupId, categoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC(
		long groupId, long categoryId, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_NotC(
				groupId, categoryId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_NOTC_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_CATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(categoryId);

			return (List<MBThread>)QueryUtil.list(
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
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_NotC_PrevAndNext(
			long threadId, long groupId, long categoryId,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_NotC_PrevAndNext(
				threadId, groupId, categoryId, orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_NotC_PrevAndNext(
				session, mbThread, groupId, categoryId, orderByComparator,
				true);

			array[1] = mbThread;

			array[2] = filterGetByG_NotC_PrevAndNext(
				session, mbThread, groupId, categoryId, orderByComparator,
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

	protected MBThread filterGetByG_NotC_PrevAndNext(
		Session session, MBThread mbThread, long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_NOTC_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_CATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(categoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where groupId = &#63; and categoryId &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 */
	@Override
	public void removeByG_NotC(long groupId, long categoryId) {
		for (MBThread mbThread :
				findByG_NotC(
					groupId, categoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_NotC(long groupId, long categoryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_NotC;

			finderArgs = new Object[] {groupId, categoryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_NOTC_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_NOTC_CATEGORYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(categoryId);

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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_NotC(long groupId, long categoryId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_NotC(groupId, categoryId);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_NOTC_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_CATEGORYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(categoryId);

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

	private static final String _FINDER_COLUMN_G_NOTC_GROUPID_2 =
		"mbThread.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_NOTC_CATEGORYID_2 =
		"mbThread.categoryId != ?";

	private FinderPath _finderPathWithPaginationFindByG_S;
	private FinderPath _finderPathWithoutPaginationFindByG_S;
	private FinderPath _finderPathCountByG_S;

	/**
	 * Returns all the message boards threads where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_S(long groupId, int status) {
		return findByG_S(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_S(
		long groupId, int status, int start, int end) {

		return findByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByG_S(groupId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_S;
				finderArgs = new Object[] {groupId, status};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_S;
			finderArgs = new Object[] {
				groupId, status, start, end, orderByComparator
			};
		}

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
						(status != mbThread.getStatus())) {

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

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(status);

				list = (List<MBThread>)QueryUtil.list(
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_S_First(
			long groupId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_S_First(
			groupId, status, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_S_First(
		long groupId, int status,
		OrderByComparator<MBThread> orderByComparator) {

		List<MBThread> list = findByG_S(
			groupId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_S_Last(
			long groupId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_S_Last(groupId, status, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_S_Last(
		long groupId, int status,
		OrderByComparator<MBThread> orderByComparator) {

		int count = countByG_S(groupId, status);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_S(
			groupId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_S_PrevAndNext(
			long threadId, long groupId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_S_PrevAndNext(
				session, mbThread, groupId, status, orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByG_S_PrevAndNext(
				session, mbThread, groupId, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread getByG_S_PrevAndNext(
		Session session, MBThread mbThread, long groupId, int status,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_S_STATUS_2);

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
			sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_S(long groupId, int status) {
		return filterFindByG_S(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_S(
		long groupId, int status, int start, int end) {

		return filterFindByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S(groupId, status, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

			return (List<MBThread>)QueryUtil.list(
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
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_S_PrevAndNext(
			long threadId, long groupId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S_PrevAndNext(
				threadId, groupId, status, orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_S_PrevAndNext(
				session, mbThread, groupId, status, orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByG_S_PrevAndNext(
				session, mbThread, groupId, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread filterGetByG_S_PrevAndNext(
		Session session, MBThread mbThread, long groupId, int status,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	@Override
	public void removeByG_S(long groupId, int status) {
		for (MBThread mbThread :
				findByG_S(
					groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_S(long groupId, int status) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_S;

			finderArgs = new Object[] {groupId, status};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(status);

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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_S(long groupId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_S(groupId, status);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 =
		"mbThread.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_STATUS_2 =
		"mbThread.status = ? AND mbThread.categoryId != -1";

	private FinderPath _finderPathWithPaginationFindByC_P;
	private FinderPath _finderPathWithoutPaginationFindByC_P;
	private FinderPath _finderPathCountByC_P;

	/**
	 * Returns all the message boards threads where categoryId = &#63; and priority = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByC_P(long categoryId, double priority) {
		return findByC_P(
			categoryId, priority, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where categoryId = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByC_P(
		long categoryId, double priority, int start, int end) {

		return findByC_P(categoryId, priority, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where categoryId = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByC_P(
		long categoryId, double priority, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByC_P(
			categoryId, priority, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where categoryId = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByC_P(
		long categoryId, double priority, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_P;
				finderArgs = new Object[] {categoryId, priority};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_P;
			finderArgs = new Object[] {
				categoryId, priority, start, end, orderByComparator
			};
		}

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((categoryId != mbThread.getCategoryId()) ||
						(priority != mbThread.getPriority())) {

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

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_C_P_CATEGORYID_2);

			sb.append(_FINDER_COLUMN_C_P_PRIORITY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(categoryId);

				queryPos.add(priority);

				list = (List<MBThread>)QueryUtil.list(
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
	 * Returns the first message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByC_P_First(
			long categoryId, double priority,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByC_P_First(
			categoryId, priority, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("categoryId=");
		sb.append(categoryId);

		sb.append(", priority=");
		sb.append(priority);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByC_P_First(
		long categoryId, double priority,
		OrderByComparator<MBThread> orderByComparator) {

		List<MBThread> list = findByC_P(
			categoryId, priority, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByC_P_Last(
			long categoryId, double priority,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByC_P_Last(
			categoryId, priority, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("categoryId=");
		sb.append(categoryId);

		sb.append(", priority=");
		sb.append(priority);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByC_P_Last(
		long categoryId, double priority,
		OrderByComparator<MBThread> orderByComparator) {

		int count = countByC_P(categoryId, priority);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByC_P(
			categoryId, priority, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByC_P_PrevAndNext(
			long threadId, long categoryId, double priority,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByC_P_PrevAndNext(
				session, mbThread, categoryId, priority, orderByComparator,
				true);

			array[1] = mbThread;

			array[2] = getByC_P_PrevAndNext(
				session, mbThread, categoryId, priority, orderByComparator,
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

	protected MBThread getByC_P_PrevAndNext(
		Session session, MBThread mbThread, long categoryId, double priority,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_C_P_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_C_P_PRIORITY_2);

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
			sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(categoryId);

		queryPos.add(priority);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where categoryId = &#63; and priority = &#63; from the database.
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 */
	@Override
	public void removeByC_P(long categoryId, double priority) {
		for (MBThread mbThread :
				findByC_P(
					categoryId, priority, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where categoryId = &#63; and priority = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByC_P(long categoryId, double priority) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_P;

			finderArgs = new Object[] {categoryId, priority};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_C_P_CATEGORYID_2);

			sb.append(_FINDER_COLUMN_C_P_PRIORITY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(categoryId);

				queryPos.add(priority);

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

	private static final String _FINDER_COLUMN_C_P_CATEGORYID_2 =
		"mbThread.categoryId = ? AND ";

	private static final String _FINDER_COLUMN_C_P_PRIORITY_2 =
		"mbThread.priority = ?";

	private FinderPath _finderPathWithPaginationFindByL_P;
	private FinderPath _finderPathWithoutPaginationFindByL_P;
	private FinderPath _finderPathCountByL_P;

	/**
	 * Returns all the message boards threads where lastPostDate = &#63; and priority = &#63;.
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByL_P(Date lastPostDate, double priority) {
		return findByL_P(
			lastPostDate, priority, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where lastPostDate = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByL_P(
		Date lastPostDate, double priority, int start, int end) {

		return findByL_P(lastPostDate, priority, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where lastPostDate = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByL_P(
		Date lastPostDate, double priority, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByL_P(
			lastPostDate, priority, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where lastPostDate = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByL_P(
		Date lastPostDate, double priority, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByL_P;
				finderArgs = new Object[] {_getTime(lastPostDate), priority};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByL_P;
			finderArgs = new Object[] {
				_getTime(lastPostDate), priority, start, end, orderByComparator
			};
		}

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if (!Objects.equals(
							lastPostDate, mbThread.getLastPostDate()) ||
						(priority != mbThread.getPriority())) {

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

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

			boolean bindLastPostDate = false;

			if (lastPostDate == null) {
				sb.append(_FINDER_COLUMN_L_P_LASTPOSTDATE_1);
			}
			else {
				bindLastPostDate = true;

				sb.append(_FINDER_COLUMN_L_P_LASTPOSTDATE_2);
			}

			sb.append(_FINDER_COLUMN_L_P_PRIORITY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindLastPostDate) {
					queryPos.add(new Timestamp(lastPostDate.getTime()));
				}

				queryPos.add(priority);

				list = (List<MBThread>)QueryUtil.list(
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
	 * Returns the first message boards thread in the ordered set where lastPostDate = &#63; and priority = &#63;.
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByL_P_First(
			Date lastPostDate, double priority,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByL_P_First(
			lastPostDate, priority, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("lastPostDate=");
		sb.append(lastPostDate);

		sb.append(", priority=");
		sb.append(priority);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where lastPostDate = &#63; and priority = &#63;.
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByL_P_First(
		Date lastPostDate, double priority,
		OrderByComparator<MBThread> orderByComparator) {

		List<MBThread> list = findByL_P(
			lastPostDate, priority, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where lastPostDate = &#63; and priority = &#63;.
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByL_P_Last(
			Date lastPostDate, double priority,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByL_P_Last(
			lastPostDate, priority, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("lastPostDate=");
		sb.append(lastPostDate);

		sb.append(", priority=");
		sb.append(priority);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where lastPostDate = &#63; and priority = &#63;.
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByL_P_Last(
		Date lastPostDate, double priority,
		OrderByComparator<MBThread> orderByComparator) {

		int count = countByL_P(lastPostDate, priority);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByL_P(
			lastPostDate, priority, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where lastPostDate = &#63; and priority = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByL_P_PrevAndNext(
			long threadId, Date lastPostDate, double priority,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByL_P_PrevAndNext(
				session, mbThread, lastPostDate, priority, orderByComparator,
				true);

			array[1] = mbThread;

			array[2] = getByL_P_PrevAndNext(
				session, mbThread, lastPostDate, priority, orderByComparator,
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

	protected MBThread getByL_P_PrevAndNext(
		Session session, MBThread mbThread, Date lastPostDate, double priority,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_MBTHREAD_WHERE);

		boolean bindLastPostDate = false;

		if (lastPostDate == null) {
			sb.append(_FINDER_COLUMN_L_P_LASTPOSTDATE_1);
		}
		else {
			bindLastPostDate = true;

			sb.append(_FINDER_COLUMN_L_P_LASTPOSTDATE_2);
		}

		sb.append(_FINDER_COLUMN_L_P_PRIORITY_2);

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
			sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindLastPostDate) {
			queryPos.add(new Timestamp(lastPostDate.getTime()));
		}

		queryPos.add(priority);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where lastPostDate = &#63; and priority = &#63; from the database.
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 */
	@Override
	public void removeByL_P(Date lastPostDate, double priority) {
		for (MBThread mbThread :
				findByL_P(
					lastPostDate, priority, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where lastPostDate = &#63; and priority = &#63;.
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByL_P(Date lastPostDate, double priority) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByL_P;

			finderArgs = new Object[] {_getTime(lastPostDate), priority};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

			boolean bindLastPostDate = false;

			if (lastPostDate == null) {
				sb.append(_FINDER_COLUMN_L_P_LASTPOSTDATE_1);
			}
			else {
				bindLastPostDate = true;

				sb.append(_FINDER_COLUMN_L_P_LASTPOSTDATE_2);
			}

			sb.append(_FINDER_COLUMN_L_P_PRIORITY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindLastPostDate) {
					queryPos.add(new Timestamp(lastPostDate.getTime()));
				}

				queryPos.add(priority);

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

	private static final String _FINDER_COLUMN_L_P_LASTPOSTDATE_1 =
		"mbThread.lastPostDate IS NULL AND ";

	private static final String _FINDER_COLUMN_L_P_LASTPOSTDATE_2 =
		"mbThread.lastPostDate = ? AND ";

	private static final String _FINDER_COLUMN_L_P_PRIORITY_2 =
		"mbThread.priority = ? AND mbThread.categoryId != -1";

	private FinderPath _finderPathWithPaginationFindByG_C_L;
	private FinderPath _finderPathWithoutPaginationFindByG_C_L;
	private FinderPath _finderPathCountByG_C_L;

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_L(
		long groupId, long categoryId, Date lastPostDate) {

		return findByG_C_L(
			groupId, categoryId, lastPostDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_L(
		long groupId, long categoryId, Date lastPostDate, int start, int end) {

		return findByG_C_L(groupId, categoryId, lastPostDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_L(
		long groupId, long categoryId, Date lastPostDate, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByG_C_L(
			groupId, categoryId, lastPostDate, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_L(
		long groupId, long categoryId, Date lastPostDate, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_L;
				finderArgs = new Object[] {
					groupId, categoryId, _getTime(lastPostDate)
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_L;
			finderArgs = new Object[] {
				groupId, categoryId, _getTime(lastPostDate), start, end,
				orderByComparator
			};
		}

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
						(categoryId != mbThread.getCategoryId()) ||
						!Objects.equals(
							lastPostDate, mbThread.getLastPostDate())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_L_CATEGORYID_2);

			boolean bindLastPostDate = false;

			if (lastPostDate == null) {
				sb.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_1);
			}
			else {
				bindLastPostDate = true;

				sb.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(categoryId);

				if (bindLastPostDate) {
					queryPos.add(new Timestamp(lastPostDate.getTime()));
				}

				list = (List<MBThread>)QueryUtil.list(
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_L_First(
			long groupId, long categoryId, Date lastPostDate,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_C_L_First(
			groupId, categoryId, lastPostDate, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", categoryId=");
		sb.append(categoryId);

		sb.append(", lastPostDate=");
		sb.append(lastPostDate);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_L_First(
		long groupId, long categoryId, Date lastPostDate,
		OrderByComparator<MBThread> orderByComparator) {

		List<MBThread> list = findByG_C_L(
			groupId, categoryId, lastPostDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_L_Last(
			long groupId, long categoryId, Date lastPostDate,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_C_L_Last(
			groupId, categoryId, lastPostDate, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", categoryId=");
		sb.append(categoryId);

		sb.append(", lastPostDate=");
		sb.append(lastPostDate);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_L_Last(
		long groupId, long categoryId, Date lastPostDate,
		OrderByComparator<MBThread> orderByComparator) {

		int count = countByG_C_L(groupId, categoryId, lastPostDate);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_C_L(
			groupId, categoryId, lastPostDate, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_C_L_PrevAndNext(
			long threadId, long groupId, long categoryId, Date lastPostDate,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_C_L_PrevAndNext(
				session, mbThread, groupId, categoryId, lastPostDate,
				orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByG_C_L_PrevAndNext(
				session, mbThread, groupId, categoryId, lastPostDate,
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

	protected MBThread getByG_C_L_PrevAndNext(
		Session session, MBThread mbThread, long groupId, long categoryId,
		Date lastPostDate, OrderByComparator<MBThread> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_L_CATEGORYID_2);

		boolean bindLastPostDate = false;

		if (lastPostDate == null) {
			sb.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_1);
		}
		else {
			bindLastPostDate = true;

			sb.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_2);
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
			sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(categoryId);

		if (bindLastPostDate) {
			queryPos.add(new Timestamp(lastPostDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_L(
		long groupId, long categoryId, Date lastPostDate) {

		return filterFindByG_C_L(
			groupId, categoryId, lastPostDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_L(
		long groupId, long categoryId, Date lastPostDate, int start, int end) {

		return filterFindByG_C_L(
			groupId, categoryId, lastPostDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_L(
		long groupId, long categoryId, Date lastPostDate, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_L(
				groupId, categoryId, lastPostDate, start, end,
				orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_L_CATEGORYID_2);

		boolean bindLastPostDate = false;

		if (lastPostDate == null) {
			sb.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_1);
		}
		else {
			bindLastPostDate = true;

			sb.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(categoryId);

			if (bindLastPostDate) {
				queryPos.add(new Timestamp(lastPostDate.getTime()));
			}

			return (List<MBThread>)QueryUtil.list(
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
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_C_L_PrevAndNext(
			long threadId, long groupId, long categoryId, Date lastPostDate,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_L_PrevAndNext(
				threadId, groupId, categoryId, lastPostDate, orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_C_L_PrevAndNext(
				session, mbThread, groupId, categoryId, lastPostDate,
				orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByG_C_L_PrevAndNext(
				session, mbThread, groupId, categoryId, lastPostDate,
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

	protected MBThread filterGetByG_C_L_PrevAndNext(
		Session session, MBThread mbThread, long groupId, long categoryId,
		Date lastPostDate, OrderByComparator<MBThread> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_L_CATEGORYID_2);

		boolean bindLastPostDate = false;

		if (lastPostDate == null) {
			sb.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_1);
		}
		else {
			bindLastPostDate = true;

			sb.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(categoryId);

		if (bindLastPostDate) {
			queryPos.add(new Timestamp(lastPostDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 */
	@Override
	public void removeByG_C_L(
		long groupId, long categoryId, Date lastPostDate) {

		for (MBThread mbThread :
				findByG_C_L(
					groupId, categoryId, lastPostDate, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_C_L(long groupId, long categoryId, Date lastPostDate) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_L;

			finderArgs = new Object[] {
				groupId, categoryId, _getTime(lastPostDate)
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_L_CATEGORYID_2);

			boolean bindLastPostDate = false;

			if (lastPostDate == null) {
				sb.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_1);
			}
			else {
				bindLastPostDate = true;

				sb.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(categoryId);

				if (bindLastPostDate) {
					queryPos.add(new Timestamp(lastPostDate.getTime()));
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

	/**
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_L(
		long groupId, long categoryId, Date lastPostDate) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_L(groupId, categoryId, lastPostDate);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_L_CATEGORYID_2);

		boolean bindLastPostDate = false;

		if (lastPostDate == null) {
			sb.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_1);
		}
		else {
			bindLastPostDate = true;

			sb.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(categoryId);

			if (bindLastPostDate) {
				queryPos.add(new Timestamp(lastPostDate.getTime()));
			}

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

	private static final String _FINDER_COLUMN_G_C_L_GROUPID_2 =
		"mbThread.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_L_CATEGORYID_2 =
		"mbThread.categoryId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_L_LASTPOSTDATE_1 =
		"mbThread.lastPostDate IS NULL";

	private static final String _FINDER_COLUMN_G_C_L_LASTPOSTDATE_2 =
		"mbThread.lastPostDate = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_S;
	private FinderPath _finderPathWithoutPaginationFindByG_C_S;
	private FinderPath _finderPathCountByG_C_S;
	private FinderPath _finderPathWithPaginationCountByG_C_S;

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(
		long groupId, long categoryId, int status) {

		return findByG_C_S(
			groupId, categoryId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end) {

		return findByG_C_S(groupId, categoryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByG_C_S(
			groupId, categoryId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_S;
				finderArgs = new Object[] {groupId, categoryId, status};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_S;
			finderArgs = new Object[] {
				groupId, categoryId, status, start, end, orderByComparator
			};
		}

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
						(categoryId != mbThread.getCategoryId()) ||
						(status != mbThread.getStatus())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

			sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(categoryId);

				queryPos.add(status);

				list = (List<MBThread>)QueryUtil.list(
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_S_First(
			long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_C_S_First(
			groupId, categoryId, status, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", categoryId=");
		sb.append(categoryId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_S_First(
		long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator) {

		List<MBThread> list = findByG_C_S(
			groupId, categoryId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_S_Last(
			long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_C_S_Last(
			groupId, categoryId, status, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", categoryId=");
		sb.append(categoryId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_S_Last(
		long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator) {

		int count = countByG_C_S(groupId, categoryId, status);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_C_S(
			groupId, categoryId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_C_S_PrevAndNext(
			long threadId, long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_C_S_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
				orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByG_C_S_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
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

	protected MBThread getByG_C_S_PrevAndNext(
		Session session, MBThread mbThread, long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

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
			sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(categoryId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_S(
		long groupId, long categoryId, int status) {

		return filterFindByG_C_S(
			groupId, categoryId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_S(
		long groupId, long categoryId, int status, int start, int end) {

		return filterFindByG_C_S(groupId, categoryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_S(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_S(
				groupId, categoryId, status, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(categoryId);

			queryPos.add(status);

			return (List<MBThread>)QueryUtil.list(
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
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_C_S_PrevAndNext(
			long threadId, long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_S_PrevAndNext(
				threadId, groupId, categoryId, status, orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_C_S_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
				orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByG_C_S_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
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

	protected MBThread filterGetByG_C_S_PrevAndNext(
		Session session, MBThread mbThread, long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(categoryId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_S(
		long groupId, long[] categoryIds, int status) {

		return filterFindByG_C_S(
			groupId, categoryIds, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_S(
		long groupId, long[] categoryIds, int status, int start, int end) {

		return filterFindByG_C_S(
			groupId, categoryIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_S(
		long groupId, long[] categoryIds, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_S(
				groupId, categoryIds, status, start, end, orderByComparator);
		}

		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.sortedUnique(categoryIds);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		if (categoryIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_C_S_CATEGORYID_7);

			sb.append(StringUtil.merge(categoryIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

			return (List<MBThread>)QueryUtil.list(
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
	 * Returns all the message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(
		long groupId, long[] categoryIds, int status) {

		return findByG_C_S(
			groupId, categoryIds, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(
		long groupId, long[] categoryIds, int status, int start, int end) {

		return findByG_C_S(groupId, categoryIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(
		long groupId, long[] categoryIds, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByG_C_S(
			groupId, categoryIds, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(
		long groupId, long[] categoryIds, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.sortedUnique(categoryIds);
		}

		if (categoryIds.length == 1) {
			return findByG_C_S(
				groupId, categoryIds[0], status, start, end, orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					groupId, StringUtil.merge(categoryIds), status
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				groupId, StringUtil.merge(categoryIds), status, start, end,
				orderByComparator
			};
		}

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				_finderPathWithPaginationFindByG_C_S, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
						!ArrayUtil.contains(
							categoryIds, mbThread.getCategoryId()) ||
						(status != mbThread.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

			if (categoryIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_C_S_CATEGORYID_7);

				sb.append(StringUtil.merge(categoryIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(status);

				list = (List<MBThread>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_C_S, finderArgs, list);
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
	 * Removes all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 */
	@Override
	public void removeByG_C_S(long groupId, long categoryId, int status) {
		for (MBThread mbThread :
				findByG_C_S(
					groupId, categoryId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_C_S(long groupId, long categoryId, int status) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_S;

			finderArgs = new Object[] {groupId, categoryId, status};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

			sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(categoryId);

				queryPos.add(status);

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
	 * Returns the number of message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_C_S(long groupId, long[] categoryIds, int status) {
		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.sortedUnique(categoryIds);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {
				groupId, StringUtil.merge(categoryIds), status
			};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_C_S, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

			if (categoryIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_C_S_CATEGORYID_7);

				sb.append(StringUtil.merge(categoryIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByG_C_S, finderArgs,
						count);
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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_S(long groupId, long categoryId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_S(groupId, categoryId, status);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(categoryId);

			queryPos.add(status);

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

	/**
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_S(
		long groupId, long[] categoryIds, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_S(groupId, categoryIds, status);
		}

		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.sortedUnique(categoryIds);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		if (categoryIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_C_S_CATEGORYID_7);

			sb.append(StringUtil.merge(categoryIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_C_S_STATUS_2);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_C_S_GROUPID_2 =
		"mbThread.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_S_CATEGORYID_2 =
		"mbThread.categoryId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_S_CATEGORYID_7 =
		"mbThread.categoryId IN (";

	private static final String _FINDER_COLUMN_G_C_S_STATUS_2 =
		"mbThread.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_NotS;
	private FinderPath _finderPathWithPaginationCountByG_C_NotS;

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(
		long groupId, long categoryId, int status) {

		return findByG_C_NotS(
			groupId, categoryId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(
		long groupId, long categoryId, int status, int start, int end) {

		return findByG_C_NotS(groupId, categoryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByG_C_NotS(
			groupId, categoryId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_C_NotS;
		finderArgs = new Object[] {
			groupId, categoryId, status, start, end, orderByComparator
		};

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
						(categoryId != mbThread.getCategoryId()) ||
						(status == mbThread.getStatus())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_2);

			sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(categoryId);

				queryPos.add(status);

				list = (List<MBThread>)QueryUtil.list(
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_NotS_First(
			long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_C_NotS_First(
			groupId, categoryId, status, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", categoryId=");
		sb.append(categoryId);

		sb.append(", status!=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_NotS_First(
		long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator) {

		List<MBThread> list = findByG_C_NotS(
			groupId, categoryId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_NotS_Last(
			long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_C_NotS_Last(
			groupId, categoryId, status, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", categoryId=");
		sb.append(categoryId);

		sb.append(", status!=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_NotS_Last(
		long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator) {

		int count = countByG_C_NotS(groupId, categoryId, status);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_C_NotS(
			groupId, categoryId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_C_NotS_PrevAndNext(
			long threadId, long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_C_NotS_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
				orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByG_C_NotS_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
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

	protected MBThread getByG_C_NotS_PrevAndNext(
		Session session, MBThread mbThread, long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

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
			sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(categoryId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_NotS(
		long groupId, long categoryId, int status) {

		return filterFindByG_C_NotS(
			groupId, categoryId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_NotS(
		long groupId, long categoryId, int status, int start, int end) {

		return filterFindByG_C_NotS(
			groupId, categoryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_NotS(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_NotS(
				groupId, categoryId, status, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(categoryId);

			queryPos.add(status);

			return (List<MBThread>)QueryUtil.list(
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
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_C_NotS_PrevAndNext(
			long threadId, long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_NotS_PrevAndNext(
				threadId, groupId, categoryId, status, orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_C_NotS_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
				orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByG_C_NotS_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
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

	protected MBThread filterGetByG_C_NotS_PrevAndNext(
		Session session, MBThread mbThread, long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(categoryId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_NotS(
		long groupId, long[] categoryIds, int status) {

		return filterFindByG_C_NotS(
			groupId, categoryIds, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_NotS(
		long groupId, long[] categoryIds, int status, int start, int end) {

		return filterFindByG_C_NotS(
			groupId, categoryIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_NotS(
		long groupId, long[] categoryIds, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_NotS(
				groupId, categoryIds, status, start, end, orderByComparator);
		}

		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.sortedUnique(categoryIds);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

		if (categoryIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_7);

			sb.append(StringUtil.merge(categoryIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

			return (List<MBThread>)QueryUtil.list(
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
	 * Returns all the message boards threads where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(
		long groupId, long[] categoryIds, int status) {

		return findByG_C_NotS(
			groupId, categoryIds, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(
		long groupId, long[] categoryIds, int status, int start, int end) {

		return findByG_C_NotS(groupId, categoryIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(
		long groupId, long[] categoryIds, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByG_C_NotS(
			groupId, categoryIds, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(
		long groupId, long[] categoryIds, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.sortedUnique(categoryIds);
		}

		if (categoryIds.length == 1) {
			return findByG_C_NotS(
				groupId, categoryIds[0], status, start, end, orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					groupId, StringUtil.merge(categoryIds), status
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				groupId, StringUtil.merge(categoryIds), status, start, end,
				orderByComparator
			};
		}

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				_finderPathWithPaginationFindByG_C_NotS, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
						!ArrayUtil.contains(
							categoryIds, mbThread.getCategoryId()) ||
						(status == mbThread.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

			if (categoryIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_7);

				sb.append(StringUtil.merge(categoryIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(status);

				list = (List<MBThread>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_C_NotS, finderArgs,
						list);
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
	 * Removes all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 */
	@Override
	public void removeByG_C_NotS(long groupId, long categoryId, int status) {
		for (MBThread mbThread :
				findByG_C_NotS(
					groupId, categoryId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_C_NotS(long groupId, long categoryId, int status) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_C_NotS;

			finderArgs = new Object[] {groupId, categoryId, status};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_2);

			sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(categoryId);

				queryPos.add(status);

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
	 * Returns the number of message boards threads where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_C_NotS(long groupId, long[] categoryIds, int status) {
		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.sortedUnique(categoryIds);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {
				groupId, StringUtil.merge(categoryIds), status
			};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_C_NotS, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

			if (categoryIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_7);

				sb.append(StringUtil.merge(categoryIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByG_C_NotS, finderArgs,
						count);
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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_NotS(
		long groupId, long categoryId, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_NotS(groupId, categoryId, status);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(categoryId);

			queryPos.add(status);

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

	/**
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_NotS(
		long groupId, long[] categoryIds, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_NotS(groupId, categoryIds, status);
		}

		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.sortedUnique(categoryIds);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

		if (categoryIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_7);

			sb.append(StringUtil.merge(categoryIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_C_NOTS_GROUPID_2 =
		"mbThread.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_NOTS_CATEGORYID_2 =
		"mbThread.categoryId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_NOTS_CATEGORYID_7 =
		"mbThread.categoryId IN (";

	private static final String _FINDER_COLUMN_G_C_NOTS_STATUS_2 =
		"mbThread.status != ?";

	private FinderPath _finderPathWithPaginationFindByG_NotC_S;
	private FinderPath _finderPathWithPaginationCountByG_NotC_S;

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_S(
		long groupId, long categoryId, int status) {

		return findByG_NotC_S(
			groupId, categoryId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_S(
		long groupId, long categoryId, int status, int start, int end) {

		return findByG_NotC_S(groupId, categoryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_S(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByG_NotC_S(
			groupId, categoryId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_S(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_NotC_S;
		finderArgs = new Object[] {
			groupId, categoryId, status, start, end, orderByComparator
		};

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
						(categoryId == mbThread.getCategoryId()) ||
						(status != mbThread.getStatus())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_NOTC_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_NOTC_S_CATEGORYID_2);

			sb.append(_FINDER_COLUMN_G_NOTC_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(categoryId);

				queryPos.add(status);

				list = (List<MBThread>)QueryUtil.list(
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_NotC_S_First(
			long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_NotC_S_First(
			groupId, categoryId, status, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", categoryId!=");
		sb.append(categoryId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_NotC_S_First(
		long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator) {

		List<MBThread> list = findByG_NotC_S(
			groupId, categoryId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_NotC_S_Last(
			long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_NotC_S_Last(
			groupId, categoryId, status, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", categoryId!=");
		sb.append(categoryId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_NotC_S_Last(
		long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator) {

		int count = countByG_NotC_S(groupId, categoryId, status);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_NotC_S(
			groupId, categoryId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_NotC_S_PrevAndNext(
			long threadId, long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_NotC_S_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
				orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByG_NotC_S_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
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

	protected MBThread getByG_NotC_S_PrevAndNext(
		Session session, MBThread mbThread, long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_NOTC_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_S_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_S_STATUS_2);

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
			sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(categoryId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC_S(
		long groupId, long categoryId, int status) {

		return filterFindByG_NotC_S(
			groupId, categoryId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC_S(
		long groupId, long categoryId, int status, int start, int end) {

		return filterFindByG_NotC_S(
			groupId, categoryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC_S(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_NotC_S(
				groupId, categoryId, status, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_NOTC_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_S_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(categoryId);

			queryPos.add(status);

			return (List<MBThread>)QueryUtil.list(
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
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_NotC_S_PrevAndNext(
			long threadId, long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_NotC_S_PrevAndNext(
				threadId, groupId, categoryId, status, orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_NotC_S_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
				orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByG_NotC_S_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
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

	protected MBThread filterGetByG_NotC_S_PrevAndNext(
		Session session, MBThread mbThread, long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_NOTC_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_S_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(categoryId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 */
	@Override
	public void removeByG_NotC_S(long groupId, long categoryId, int status) {
		for (MBThread mbThread :
				findByG_NotC_S(
					groupId, categoryId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_NotC_S(long groupId, long categoryId, int status) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_NotC_S;

			finderArgs = new Object[] {groupId, categoryId, status};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_NOTC_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_NOTC_S_CATEGORYID_2);

			sb.append(_FINDER_COLUMN_G_NOTC_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(categoryId);

				queryPos.add(status);

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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_NotC_S(
		long groupId, long categoryId, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_NotC_S(groupId, categoryId, status);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_NOTC_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_S_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(categoryId);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_NOTC_S_GROUPID_2 =
		"mbThread.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_NOTC_S_CATEGORYID_2 =
		"mbThread.categoryId != ? AND ";

	private static final String _FINDER_COLUMN_G_NOTC_S_STATUS_2 =
		"mbThread.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_NotC_NotS;
	private FinderPath _finderPathWithPaginationCountByG_NotC_NotS;

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_NotS(
		long groupId, long categoryId, int status) {

		return findByG_NotC_NotS(
			groupId, categoryId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_NotS(
		long groupId, long categoryId, int status, int start, int end) {

		return findByG_NotC_NotS(groupId, categoryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_NotS(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		return findByG_NotC_NotS(
			groupId, categoryId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_NotS(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_NotC_NotS;
		finderArgs = new Object[] {
			groupId, categoryId, status, start, end, orderByComparator
		};

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
						(categoryId == mbThread.getCategoryId()) ||
						(status == mbThread.getStatus())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_NOTC_NOTS_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_NOTC_NOTS_CATEGORYID_2);

			sb.append(_FINDER_COLUMN_G_NOTC_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(categoryId);

				queryPos.add(status);

				list = (List<MBThread>)QueryUtil.list(
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_NotC_NotS_First(
			long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_NotC_NotS_First(
			groupId, categoryId, status, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", categoryId!=");
		sb.append(categoryId);

		sb.append(", status!=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_NotC_NotS_First(
		long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator) {

		List<MBThread> list = findByG_NotC_NotS(
			groupId, categoryId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_NotC_NotS_Last(
			long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByG_NotC_NotS_Last(
			groupId, categoryId, status, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", categoryId!=");
		sb.append(categoryId);

		sb.append(", status!=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchThreadException(sb.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_NotC_NotS_Last(
		long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator) {

		int count = countByG_NotC_NotS(groupId, categoryId, status);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_NotC_NotS(
			groupId, categoryId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_NotC_NotS_PrevAndNext(
			long threadId, long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_NotC_NotS_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
				orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByG_NotC_NotS_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
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

	protected MBThread getByG_NotC_NotS_PrevAndNext(
		Session session, MBThread mbThread, long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_NOTC_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_NOTS_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_NOTS_STATUS_2);

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
			sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(categoryId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC_NotS(
		long groupId, long categoryId, int status) {

		return filterFindByG_NotC_NotS(
			groupId, categoryId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC_NotS(
		long groupId, long categoryId, int status, int start, int end) {

		return filterFindByG_NotC_NotS(
			groupId, categoryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC_NotS(
		long groupId, long categoryId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_NotC_NotS(
				groupId, categoryId, status, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_NOTC_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_NOTS_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(categoryId);

			queryPos.add(status);

			return (List<MBThread>)QueryUtil.list(
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
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_NotC_NotS_PrevAndNext(
			long threadId, long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_NotC_NotS_PrevAndNext(
				threadId, groupId, categoryId, status, orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_NotC_NotS_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
				orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByG_NotC_NotS_PrevAndNext(
				session, mbThread, groupId, categoryId, status,
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

	protected MBThread filterGetByG_NotC_NotS_PrevAndNext(
		Session session, MBThread mbThread, long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_NOTC_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_NOTS_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(categoryId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThread)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThread> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 */
	@Override
	public void removeByG_NotC_NotS(long groupId, long categoryId, int status) {
		for (MBThread mbThread :
				findByG_NotC_NotS(
					groupId, categoryId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_NotC_NotS(long groupId, long categoryId, int status) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_NotC_NotS;

			finderArgs = new Object[] {groupId, categoryId, status};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_MBTHREAD_WHERE);

			sb.append(_FINDER_COLUMN_G_NOTC_NOTS_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_NOTC_NOTS_CATEGORYID_2);

			sb.append(_FINDER_COLUMN_G_NOTC_NOTS_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(categoryId);

				queryPos.add(status);

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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_NotC_NotS(
		long groupId, long categoryId, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_NotC_NotS(groupId, categoryId, status);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		sb.append(_FINDER_COLUMN_G_NOTC_NOTS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_NOTS_CATEGORYID_2);

		sb.append(_FINDER_COLUMN_G_NOTC_NOTS_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), MBThread.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(categoryId);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_NOTC_NOTS_GROUPID_2 =
		"mbThread.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_NOTC_NOTS_CATEGORYID_2 =
		"mbThread.categoryId != ? AND ";

	private static final String _FINDER_COLUMN_G_NOTC_NOTS_STATUS_2 =
		"mbThread.status != ?";

	public MBThreadPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(MBThread.class);

		setModelImplClass(MBThreadImpl.class);
		setModelPKClass(long.class);

		setTable(MBThreadTable.INSTANCE);
	}

	/**
	 * Caches the message boards thread in the entity cache if it is enabled.
	 *
	 * @param mbThread the message boards thread
	 */
	@Override
	public void cacheResult(MBThread mbThread) {
		if (mbThread.getCtCollectionId() != 0) {
			mbThread.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			MBThreadImpl.class, mbThread.getPrimaryKey(), mbThread);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {mbThread.getUuid(), mbThread.getGroupId()}, mbThread);

		finderCache.putResult(
			_finderPathFetchByRootMessageId,
			new Object[] {mbThread.getRootMessageId()}, mbThread);

		mbThread.resetOriginalValues();
	}

	/**
	 * Caches the message boards threads in the entity cache if it is enabled.
	 *
	 * @param mbThreads the message boards threads
	 */
	@Override
	public void cacheResult(List<MBThread> mbThreads) {
		for (MBThread mbThread : mbThreads) {
			if (mbThread.getCtCollectionId() != 0) {
				mbThread.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					MBThreadImpl.class, mbThread.getPrimaryKey()) == null) {

				cacheResult(mbThread);
			}
			else {
				mbThread.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all message boards threads.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MBThreadImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the message boards thread.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MBThread mbThread) {
		entityCache.removeResult(MBThreadImpl.class, mbThread.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((MBThreadModelImpl)mbThread, true);
	}

	@Override
	public void clearCache(List<MBThread> mbThreads) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MBThread mbThread : mbThreads) {
			entityCache.removeResult(
				MBThreadImpl.class, mbThread.getPrimaryKey());

			clearUniqueFindersCache((MBThreadModelImpl)mbThread, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(MBThreadImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		MBThreadModelImpl mbThreadModelImpl) {

		Object[] args = new Object[] {
			mbThreadModelImpl.getUuid(), mbThreadModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, mbThreadModelImpl, false);

		args = new Object[] {mbThreadModelImpl.getRootMessageId()};

		finderCache.putResult(
			_finderPathCountByRootMessageId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByRootMessageId, args, mbThreadModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		MBThreadModelImpl mbThreadModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				mbThreadModelImpl.getUuid(), mbThreadModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((mbThreadModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				mbThreadModelImpl.getOriginalUuid(),
				mbThreadModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {mbThreadModelImpl.getRootMessageId()};

			finderCache.removeResult(_finderPathCountByRootMessageId, args);
			finderCache.removeResult(_finderPathFetchByRootMessageId, args);
		}

		if ((mbThreadModelImpl.getColumnBitmask() &
			 _finderPathFetchByRootMessageId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				mbThreadModelImpl.getOriginalRootMessageId()
			};

			finderCache.removeResult(_finderPathCountByRootMessageId, args);
			finderCache.removeResult(_finderPathFetchByRootMessageId, args);
		}
	}

	/**
	 * Creates a new message boards thread with the primary key. Does not add the message boards thread to the database.
	 *
	 * @param threadId the primary key for the new message boards thread
	 * @return the new message boards thread
	 */
	@Override
	public MBThread create(long threadId) {
		MBThread mbThread = new MBThreadImpl();

		mbThread.setNew(true);
		mbThread.setPrimaryKey(threadId);

		String uuid = PortalUUIDUtil.generate();

		mbThread.setUuid(uuid);

		mbThread.setCompanyId(CompanyThreadLocal.getCompanyId());

		return mbThread;
	}

	/**
	 * Removes the message boards thread with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param threadId the primary key of the message boards thread
	 * @return the message boards thread that was removed
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread remove(long threadId) throws NoSuchThreadException {
		return remove((Serializable)threadId);
	}

	/**
	 * Removes the message boards thread with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message boards thread
	 * @return the message boards thread that was removed
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread remove(Serializable primaryKey)
		throws NoSuchThreadException {

		Session session = null;

		try {
			session = openSession();

			MBThread mbThread = (MBThread)session.get(
				MBThreadImpl.class, primaryKey);

			if (mbThread == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchThreadException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(mbThread);
		}
		catch (NoSuchThreadException noSuchEntityException) {
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
	protected MBThread removeImpl(MBThread mbThread) {
		if (!ctPersistenceHelper.isRemove(mbThread)) {
			return mbThread;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mbThread)) {
				mbThread = (MBThread)session.get(
					MBThreadImpl.class, mbThread.getPrimaryKeyObj());
			}

			if (mbThread != null) {
				session.delete(mbThread);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mbThread != null) {
			clearCache(mbThread);
		}

		return mbThread;
	}

	@Override
	public MBThread updateImpl(MBThread mbThread) {
		boolean isNew = mbThread.isNew();

		if (!(mbThread instanceof MBThreadModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(mbThread.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(mbThread);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in mbThread proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MBThread implementation " +
					mbThread.getClass());
		}

		MBThreadModelImpl mbThreadModelImpl = (MBThreadModelImpl)mbThread;

		if (Validator.isNull(mbThread.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mbThread.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (mbThread.getCreateDate() == null)) {
			if (serviceContext == null) {
				mbThread.setCreateDate(now);
			}
			else {
				mbThread.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!mbThreadModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				mbThread.setModifiedDate(now);
			}
			else {
				mbThread.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		long userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

		if (userId > 0) {
			long companyId = mbThread.getCompanyId();

			long groupId = mbThread.getGroupId();

			long threadId = 0;

			if (!isNew) {
				threadId = mbThread.getPrimaryKey();
			}

			try {
				mbThread.setTitle(
					SanitizerUtil.sanitize(
						companyId, groupId, userId, MBThread.class.getName(),
						threadId, ContentTypes.TEXT_PLAIN, Sanitizer.MODE_ALL,
						mbThread.getTitle(), null));
			}
			catch (SanitizerException sanitizerException) {
				throw new SystemException(sanitizerException);
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(mbThread)) {
				if (!isNew) {
					session.evict(
						MBThreadImpl.class, mbThread.getPrimaryKeyObj());
				}

				session.save(mbThread);

				mbThread.setNew(false);
			}
			else {
				mbThread = (MBThread)session.merge(mbThread);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mbThread.getCtCollectionId() != 0) {
			mbThread.resetOriginalValues();

			return mbThread;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {mbThreadModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				mbThreadModelImpl.getUuid(), mbThreadModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {mbThreadModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				mbThreadModelImpl.getGroupId(),
				mbThreadModelImpl.getCategoryId()
			};

			finderCache.removeResult(_finderPathCountByG_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C, args);

			args = new Object[] {
				mbThreadModelImpl.getGroupId(), mbThreadModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByG_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_S, args);

			args = new Object[] {
				mbThreadModelImpl.getCategoryId(),
				mbThreadModelImpl.getPriority()
			};

			finderCache.removeResult(_finderPathCountByC_P, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_P, args);

			args = new Object[] {
				mbThreadModelImpl.getLastPostDate(),
				mbThreadModelImpl.getPriority()
			};

			finderCache.removeResult(_finderPathCountByL_P, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByL_P, args);

			args = new Object[] {
				mbThreadModelImpl.getGroupId(),
				mbThreadModelImpl.getCategoryId(),
				mbThreadModelImpl.getLastPostDate()
			};

			finderCache.removeResult(_finderPathCountByG_C_L, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_L, args);

			args = new Object[] {
				mbThreadModelImpl.getGroupId(),
				mbThreadModelImpl.getCategoryId(), mbThreadModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByG_C_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_S, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((mbThreadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbThreadModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {mbThreadModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbThreadModelImpl.getOriginalUuid(),
					mbThreadModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					mbThreadModelImpl.getUuid(),
					mbThreadModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					mbThreadModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {mbThreadModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbThreadModelImpl.getOriginalGroupId(),
					mbThreadModelImpl.getOriginalCategoryId()
				};

				finderCache.removeResult(_finderPathCountByG_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C, args);

				args = new Object[] {
					mbThreadModelImpl.getGroupId(),
					mbThreadModelImpl.getCategoryId()
				};

				finderCache.removeResult(_finderPathCountByG_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C, args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbThreadModelImpl.getOriginalGroupId(),
					mbThreadModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByG_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_S, args);

				args = new Object[] {
					mbThreadModelImpl.getGroupId(),
					mbThreadModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByG_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_S, args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbThreadModelImpl.getOriginalCategoryId(),
					mbThreadModelImpl.getOriginalPriority()
				};

				finderCache.removeResult(_finderPathCountByC_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_P, args);

				args = new Object[] {
					mbThreadModelImpl.getCategoryId(),
					mbThreadModelImpl.getPriority()
				};

				finderCache.removeResult(_finderPathCountByC_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_P, args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByL_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbThreadModelImpl.getOriginalLastPostDate(),
					mbThreadModelImpl.getOriginalPriority()
				};

				finderCache.removeResult(_finderPathCountByL_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByL_P, args);

				args = new Object[] {
					mbThreadModelImpl.getLastPostDate(),
					mbThreadModelImpl.getPriority()
				};

				finderCache.removeResult(_finderPathCountByL_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByL_P, args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_L.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbThreadModelImpl.getOriginalGroupId(),
					mbThreadModelImpl.getOriginalCategoryId(),
					mbThreadModelImpl.getOriginalLastPostDate()
				};

				finderCache.removeResult(_finderPathCountByG_C_L, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_L, args);

				args = new Object[] {
					mbThreadModelImpl.getGroupId(),
					mbThreadModelImpl.getCategoryId(),
					mbThreadModelImpl.getLastPostDate()
				};

				finderCache.removeResult(_finderPathCountByG_C_L, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_L, args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbThreadModelImpl.getOriginalGroupId(),
					mbThreadModelImpl.getOriginalCategoryId(),
					mbThreadModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByG_C_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_S, args);

				args = new Object[] {
					mbThreadModelImpl.getGroupId(),
					mbThreadModelImpl.getCategoryId(),
					mbThreadModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByG_C_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_S, args);
			}
		}

		entityCache.putResult(
			MBThreadImpl.class, mbThread.getPrimaryKey(), mbThread, false);

		clearUniqueFindersCache(mbThreadModelImpl, false);
		cacheUniqueFindersCache(mbThreadModelImpl);

		mbThread.resetOriginalValues();

		return mbThread;
	}

	/**
	 * Returns the message boards thread with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards thread
	 * @return the message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread findByPrimaryKey(Serializable primaryKey)
		throws NoSuchThreadException {

		MBThread mbThread = fetchByPrimaryKey(primaryKey);

		if (mbThread == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchThreadException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return mbThread;
	}

	/**
	 * Returns the message boards thread with the primary key or throws a <code>NoSuchThreadException</code> if it could not be found.
	 *
	 * @param threadId the primary key of the message boards thread
	 * @return the message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread findByPrimaryKey(long threadId)
		throws NoSuchThreadException {

		return findByPrimaryKey((Serializable)threadId);
	}

	/**
	 * Returns the message boards thread with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards thread
	 * @return the message boards thread, or <code>null</code> if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(MBThread.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		MBThread mbThread = null;

		Session session = null;

		try {
			session = openSession();

			mbThread = (MBThread)session.get(MBThreadImpl.class, primaryKey);

			if (mbThread != null) {
				cacheResult(mbThread);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return mbThread;
	}

	/**
	 * Returns the message boards thread with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param threadId the primary key of the message boards thread
	 * @return the message boards thread, or <code>null</code> if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread fetchByPrimaryKey(long threadId) {
		return fetchByPrimaryKey((Serializable)threadId);
	}

	@Override
	public Map<Serializable, MBThread> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(MBThread.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, MBThread> map = new HashMap<Serializable, MBThread>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			MBThread mbThread = fetchByPrimaryKey(primaryKey);

			if (mbThread != null) {
				map.put(primaryKey, mbThread);
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

			for (MBThread mbThread : (List<MBThread>)query.list()) {
				map.put(mbThread.getPrimaryKeyObj(), mbThread);

				cacheResult(mbThread);
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
	 * Returns all the message boards threads.
	 *
	 * @return the message boards threads
	 */
	@Override
	public List<MBThread> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of message boards threads
	 */
	@Override
	public List<MBThread> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards threads
	 */
	@Override
	public List<MBThread> findAll(
		int start, int end, OrderByComparator<MBThread> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of message boards threads
	 */
	@Override
	public List<MBThread> findAll(
		int start, int end, OrderByComparator<MBThread> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

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

		List<MBThread> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThread>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MBTHREAD);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MBTHREAD;

				sql = sql.concat(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<MBThread>)QueryUtil.list(
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
	 * Removes all the message boards threads from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MBThread mbThread : findAll()) {
			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads.
	 *
	 * @return the number of message boards threads
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThread.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_MBTHREAD);

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
		return "threadId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MBTHREAD;
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
		return MBThreadModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "MBThread";
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
		ctStrictColumnNames.add("categoryId");
		ctStrictColumnNames.add("rootMessageId");
		ctStrictColumnNames.add("rootMessageUserId");
		ctStrictColumnNames.add("title");
		ctStrictColumnNames.add("lastPostByUserId");
		ctStrictColumnNames.add("lastPostDate");
		ctStrictColumnNames.add("priority");
		ctStrictColumnNames.add("question");
		ctStrictColumnNames.add("lastPublishDate");
		ctStrictColumnNames.add("status");
		ctStrictColumnNames.add("statusByUserId");
		ctStrictColumnNames.add("statusByUserName");
		ctStrictColumnNames.add("statusDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("threadId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});
	}

	/**
	 * Initializes the message boards thread persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			MBThreadModelImpl.UUID_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			MBThreadModelImpl.UUID_COLUMN_BITMASK |
			MBThreadModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			MBThreadModelImpl.UUID_COLUMN_BITMASK |
			MBThreadModelImpl.COMPANYID_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] {Long.class.getName()},
			MBThreadModelImpl.GROUPID_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathFetchByRootMessageId = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByRootMessageId", new String[] {Long.class.getName()},
			MBThreadModelImpl.ROOTMESSAGEID_COLUMN_BITMASK);

		_finderPathCountByRootMessageId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByRootMessageId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByG_C = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			MBThreadModelImpl.GROUPID_COLUMN_BITMASK |
			MBThreadModelImpl.CATEGORYID_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK);

		_finderPathCountByG_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationCountByG_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByG_NotC = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_NotC",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_NotC = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_NotC",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByG_S = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_S = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			MBThreadModelImpl.GROUPID_COLUMN_BITMASK |
			MBThreadModelImpl.STATUS_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK);

		_finderPathCountByG_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByC_P = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_P",
			new String[] {
				Long.class.getName(), Double.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_P = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_P",
			new String[] {Long.class.getName(), Double.class.getName()},
			MBThreadModelImpl.CATEGORYID_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK);

		_finderPathCountByC_P = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P",
			new String[] {Long.class.getName(), Double.class.getName()});

		_finderPathWithPaginationFindByL_P = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByL_P",
			new String[] {
				Date.class.getName(), Double.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByL_P = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByL_P",
			new String[] {Date.class.getName(), Double.class.getName()},
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByL_P = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_P",
			new String[] {Date.class.getName(), Double.class.getName()});

		_finderPathWithPaginationFindByG_C_L = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_L = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			},
			MBThreadModelImpl.GROUPID_COLUMN_BITMASK |
			MBThreadModelImpl.CATEGORYID_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByG_C_L = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			});

		_finderPathWithPaginationFindByG_C_S = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_S = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			MBThreadModelImpl.GROUPID_COLUMN_BITMASK |
			MBThreadModelImpl.CATEGORYID_COLUMN_BITMASK |
			MBThreadModelImpl.STATUS_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK);

		_finderPathCountByG_C_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationCountByG_C_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_C_NotS = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_C_NotS = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_C_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_NotC_S = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_NotC_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_NotC_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_NotC_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_NotC_NotS = new FinderPath(
			MBThreadImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_NotC_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_NotC_NotS = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_NotC_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(MBThreadImpl.class.getName());
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

	private Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_MBTHREAD =
		"SELECT mbThread FROM MBThread mbThread";

	private static final String _SQL_SELECT_MBTHREAD_WHERE =
		"SELECT mbThread FROM MBThread mbThread WHERE ";

	private static final String _SQL_COUNT_MBTHREAD =
		"SELECT COUNT(mbThread) FROM MBThread mbThread";

	private static final String _SQL_COUNT_MBTHREAD_WHERE =
		"SELECT COUNT(mbThread) FROM MBThread mbThread WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"mbThread.threadId";

	private static final String _FILTER_SQL_SELECT_MBTHREAD_WHERE =
		"SELECT DISTINCT {mbThread.*} FROM MBThread mbThread WHERE ";

	private static final String
		_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {MBThread.*} FROM (SELECT DISTINCT mbThread.threadId FROM MBThread mbThread WHERE ";

	private static final String
		_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN MBThread ON TEMP_TABLE.threadId = MBThread.threadId";

	private static final String _FILTER_SQL_COUNT_MBTHREAD_WHERE =
		"SELECT COUNT(DISTINCT mbThread.threadId) AS COUNT_VALUE FROM MBThread mbThread WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "mbThread";

	private static final String _FILTER_ENTITY_TABLE = "MBThread";

	private static final String _ORDER_BY_ENTITY_ALIAS = "mbThread.";

	private static final String _ORDER_BY_ENTITY_TABLE = "MBThread.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MBThread exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No MBThread exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MBThreadPersistenceImpl.class);

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