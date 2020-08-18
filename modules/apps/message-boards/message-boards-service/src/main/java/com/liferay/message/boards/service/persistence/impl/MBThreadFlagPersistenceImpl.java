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

import com.liferay.message.boards.exception.NoSuchThreadFlagException;
import com.liferay.message.boards.model.MBThreadFlag;
import com.liferay.message.boards.model.MBThreadFlagTable;
import com.liferay.message.boards.model.impl.MBThreadFlagImpl;
import com.liferay.message.boards.model.impl.MBThreadFlagModelImpl;
import com.liferay.message.boards.service.persistence.MBThreadFlagPersistence;
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
 * The persistence implementation for the message boards thread flag service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = MBThreadFlagPersistence.class)
public class MBThreadFlagPersistenceImpl
	extends BasePersistenceImpl<MBThreadFlag>
	implements MBThreadFlagPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MBThreadFlagUtil</code> to access the message boards thread flag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MBThreadFlagImpl.class.getName();

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
	 * Returns all the message boards thread flags where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards thread flags where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @return the range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThreadFlag.class);

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

		List<MBThreadFlag> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThreadFlag>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThreadFlag mbThreadFlag : list) {
					if (!uuid.equals(mbThreadFlag.getUuid())) {
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

			sb.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

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
				sb.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
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

				list = (List<MBThreadFlag>)QueryUtil.list(
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
	 * Returns the first message boards thread flag in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag
	 * @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag findByUuid_First(
			String uuid, OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {

		MBThreadFlag mbThreadFlag = fetchByUuid_First(uuid, orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchThreadFlagException(sb.toString());
	}

	/**
	 * Returns the first message boards thread flag in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByUuid_First(
		String uuid, OrderByComparator<MBThreadFlag> orderByComparator) {

		List<MBThreadFlag> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread flag in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread flag
	 * @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag findByUuid_Last(
			String uuid, OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {

		MBThreadFlag mbThreadFlag = fetchByUuid_Last(uuid, orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchThreadFlagException(sb.toString());
	}

	/**
	 * Returns the last message boards thread flag in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByUuid_Last(
		String uuid, OrderByComparator<MBThreadFlag> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<MBThreadFlag> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards thread flags before and after the current message boards thread flag in the ordered set where uuid = &#63;.
	 *
	 * @param threadFlagId the primary key of the current message boards thread flag
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread flag
	 * @throws NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	 */
	@Override
	public MBThreadFlag[] findByUuid_PrevAndNext(
			long threadFlagId, String uuid,
			OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {

		uuid = Objects.toString(uuid, "");

		MBThreadFlag mbThreadFlag = findByPrimaryKey(threadFlagId);

		Session session = null;

		try {
			session = openSession();

			MBThreadFlag[] array = new MBThreadFlagImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, mbThreadFlag, uuid, orderByComparator, true);

			array[1] = mbThreadFlag;

			array[2] = getByUuid_PrevAndNext(
				session, mbThreadFlag, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThreadFlag getByUuid_PrevAndNext(
		Session session, MBThreadFlag mbThreadFlag, String uuid,
		OrderByComparator<MBThreadFlag> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

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
			sb.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(mbThreadFlag)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThreadFlag> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards thread flags where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (MBThreadFlag mbThreadFlag :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mbThreadFlag);
		}
	}

	/**
	 * Returns the number of message boards thread flags where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching message boards thread flags
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThreadFlag.class);

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

			sb.append(_SQL_COUNT_MBTHREADFLAG_WHERE);

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
		"mbThreadFlag.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(mbThreadFlag.uuid IS NULL OR mbThreadFlag.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the message boards thread flag where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchThreadFlagException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards thread flag
	 * @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag findByUUID_G(String uuid, long groupId)
		throws NoSuchThreadFlagException {

		MBThreadFlag mbThreadFlag = fetchByUUID_G(uuid, groupId);

		if (mbThreadFlag == null) {
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

			throw new NoSuchThreadFlagException(sb.toString());
		}

		return mbThreadFlag;
	}

	/**
	 * Returns the message boards thread flag where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the message boards thread flag where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThreadFlag.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof MBThreadFlag) {
			MBThreadFlag mbThreadFlag = (MBThreadFlag)result;

			if (!Objects.equals(uuid, mbThreadFlag.getUuid()) ||
				(groupId != mbThreadFlag.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

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

				List<MBThreadFlag> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					MBThreadFlag mbThreadFlag = list.get(0);

					result = mbThreadFlag;

					cacheResult(mbThreadFlag);
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
			return (MBThreadFlag)result;
		}
	}

	/**
	 * Removes the message boards thread flag where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the message boards thread flag that was removed
	 */
	@Override
	public MBThreadFlag removeByUUID_G(String uuid, long groupId)
		throws NoSuchThreadFlagException {

		MBThreadFlag mbThreadFlag = findByUUID_G(uuid, groupId);

		return remove(mbThreadFlag);
	}

	/**
	 * Returns the number of message boards thread flags where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching message boards thread flags
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThreadFlag.class);

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

			sb.append(_SQL_COUNT_MBTHREADFLAG_WHERE);

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
		"mbThreadFlag.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(mbThreadFlag.uuid IS NULL OR mbThreadFlag.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"mbThreadFlag.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the message boards thread flags where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards thread flags where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @return the range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThreadFlag.class);

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

		List<MBThreadFlag> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThreadFlag>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThreadFlag mbThreadFlag : list) {
					if (!uuid.equals(mbThreadFlag.getUuid()) ||
						(companyId != mbThreadFlag.getCompanyId())) {

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

			sb.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

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
				sb.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
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

				list = (List<MBThreadFlag>)QueryUtil.list(
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
	 * Returns the first message boards thread flag in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag
	 * @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {

		MBThreadFlag mbThreadFlag = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchThreadFlagException(sb.toString());
	}

	/**
	 * Returns the first message boards thread flag in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<MBThreadFlag> orderByComparator) {

		List<MBThreadFlag> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread flag in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread flag
	 * @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {

		MBThreadFlag mbThreadFlag = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchThreadFlagException(sb.toString());
	}

	/**
	 * Returns the last message boards thread flag in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<MBThreadFlag> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<MBThreadFlag> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards thread flags before and after the current message boards thread flag in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param threadFlagId the primary key of the current message boards thread flag
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread flag
	 * @throws NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	 */
	@Override
	public MBThreadFlag[] findByUuid_C_PrevAndNext(
			long threadFlagId, String uuid, long companyId,
			OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {

		uuid = Objects.toString(uuid, "");

		MBThreadFlag mbThreadFlag = findByPrimaryKey(threadFlagId);

		Session session = null;

		try {
			session = openSession();

			MBThreadFlag[] array = new MBThreadFlagImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, mbThreadFlag, uuid, companyId, orderByComparator,
				true);

			array[1] = mbThreadFlag;

			array[2] = getByUuid_C_PrevAndNext(
				session, mbThreadFlag, uuid, companyId, orderByComparator,
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

	protected MBThreadFlag getByUuid_C_PrevAndNext(
		Session session, MBThreadFlag mbThreadFlag, String uuid, long companyId,
		OrderByComparator<MBThreadFlag> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

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
			sb.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(mbThreadFlag)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThreadFlag> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards thread flags where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (MBThreadFlag mbThreadFlag :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(mbThreadFlag);
		}
	}

	/**
	 * Returns the number of message boards thread flags where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching message boards thread flags
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThreadFlag.class);

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

			sb.append(_SQL_COUNT_MBTHREADFLAG_WHERE);

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
		"mbThreadFlag.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(mbThreadFlag.uuid IS NULL OR mbThreadFlag.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"mbThreadFlag.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the message boards thread flags where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards thread flags where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @return the range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUserId(
		long userId, int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUserId(
		long userId, int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThreadFlag.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<MBThreadFlag> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThreadFlag>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThreadFlag mbThreadFlag : list) {
					if (userId != mbThreadFlag.getUserId()) {
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

			sb.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<MBThreadFlag>)QueryUtil.list(
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
	 * Returns the first message boards thread flag in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag
	 * @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag findByUserId_First(
			long userId, OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {

		MBThreadFlag mbThreadFlag = fetchByUserId_First(
			userId, orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchThreadFlagException(sb.toString());
	}

	/**
	 * Returns the first message boards thread flag in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByUserId_First(
		long userId, OrderByComparator<MBThreadFlag> orderByComparator) {

		List<MBThreadFlag> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread flag in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread flag
	 * @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag findByUserId_Last(
			long userId, OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {

		MBThreadFlag mbThreadFlag = fetchByUserId_Last(
			userId, orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchThreadFlagException(sb.toString());
	}

	/**
	 * Returns the last message boards thread flag in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByUserId_Last(
		long userId, OrderByComparator<MBThreadFlag> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<MBThreadFlag> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards thread flags before and after the current message boards thread flag in the ordered set where userId = &#63;.
	 *
	 * @param threadFlagId the primary key of the current message boards thread flag
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread flag
	 * @throws NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	 */
	@Override
	public MBThreadFlag[] findByUserId_PrevAndNext(
			long threadFlagId, long userId,
			OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {

		MBThreadFlag mbThreadFlag = findByPrimaryKey(threadFlagId);

		Session session = null;

		try {
			session = openSession();

			MBThreadFlag[] array = new MBThreadFlagImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, mbThreadFlag, userId, orderByComparator, true);

			array[1] = mbThreadFlag;

			array[2] = getByUserId_PrevAndNext(
				session, mbThreadFlag, userId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThreadFlag getByUserId_PrevAndNext(
		Session session, MBThreadFlag mbThreadFlag, long userId,
		OrderByComparator<MBThreadFlag> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

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
			sb.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThreadFlag)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThreadFlag> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards thread flags where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (MBThreadFlag mbThreadFlag :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mbThreadFlag);
		}
	}

	/**
	 * Returns the number of message boards thread flags where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching message boards thread flags
	 */
	@Override
	public int countByUserId(long userId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThreadFlag.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUserId;

			finderArgs = new Object[] {userId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MBTHREADFLAG_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"mbThreadFlag.userId = ?";

	private FinderPath _finderPathWithPaginationFindByThreadId;
	private FinderPath _finderPathWithoutPaginationFindByThreadId;
	private FinderPath _finderPathCountByThreadId;

	/**
	 * Returns all the message boards thread flags where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByThreadId(long threadId) {
		return findByThreadId(
			threadId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards thread flags where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @return the range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByThreadId(
		long threadId, int start, int end) {

		return findByThreadId(threadId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByThreadId(
		long threadId, int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator) {

		return findByThreadId(threadId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByThreadId(
		long threadId, int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThreadFlag.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByThreadId;
				finderArgs = new Object[] {threadId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByThreadId;
			finderArgs = new Object[] {threadId, start, end, orderByComparator};
		}

		List<MBThreadFlag> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThreadFlag>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThreadFlag mbThreadFlag : list) {
					if (threadId != mbThreadFlag.getThreadId()) {
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

			sb.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

			sb.append(_FINDER_COLUMN_THREADID_THREADID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(threadId);

				list = (List<MBThreadFlag>)QueryUtil.list(
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
	 * Returns the first message boards thread flag in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag
	 * @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag findByThreadId_First(
			long threadId, OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {

		MBThreadFlag mbThreadFlag = fetchByThreadId_First(
			threadId, orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("threadId=");
		sb.append(threadId);

		sb.append("}");

		throw new NoSuchThreadFlagException(sb.toString());
	}

	/**
	 * Returns the first message boards thread flag in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByThreadId_First(
		long threadId, OrderByComparator<MBThreadFlag> orderByComparator) {

		List<MBThreadFlag> list = findByThreadId(
			threadId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread flag in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread flag
	 * @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag findByThreadId_Last(
			long threadId, OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {

		MBThreadFlag mbThreadFlag = fetchByThreadId_Last(
			threadId, orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("threadId=");
		sb.append(threadId);

		sb.append("}");

		throw new NoSuchThreadFlagException(sb.toString());
	}

	/**
	 * Returns the last message boards thread flag in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByThreadId_Last(
		long threadId, OrderByComparator<MBThreadFlag> orderByComparator) {

		int count = countByThreadId(threadId);

		if (count == 0) {
			return null;
		}

		List<MBThreadFlag> list = findByThreadId(
			threadId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards thread flags before and after the current message boards thread flag in the ordered set where threadId = &#63;.
	 *
	 * @param threadFlagId the primary key of the current message boards thread flag
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread flag
	 * @throws NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	 */
	@Override
	public MBThreadFlag[] findByThreadId_PrevAndNext(
			long threadFlagId, long threadId,
			OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {

		MBThreadFlag mbThreadFlag = findByPrimaryKey(threadFlagId);

		Session session = null;

		try {
			session = openSession();

			MBThreadFlag[] array = new MBThreadFlagImpl[3];

			array[0] = getByThreadId_PrevAndNext(
				session, mbThreadFlag, threadId, orderByComparator, true);

			array[1] = mbThreadFlag;

			array[2] = getByThreadId_PrevAndNext(
				session, mbThreadFlag, threadId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThreadFlag getByThreadId_PrevAndNext(
		Session session, MBThreadFlag mbThreadFlag, long threadId,
		OrderByComparator<MBThreadFlag> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

		sb.append(_FINDER_COLUMN_THREADID_THREADID_2);

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
			sb.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(threadId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbThreadFlag)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBThreadFlag> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards thread flags where threadId = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 */
	@Override
	public void removeByThreadId(long threadId) {
		for (MBThreadFlag mbThreadFlag :
				findByThreadId(
					threadId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mbThreadFlag);
		}
	}

	/**
	 * Returns the number of message boards thread flags where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the number of matching message boards thread flags
	 */
	@Override
	public int countByThreadId(long threadId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThreadFlag.class);

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

			sb.append(_SQL_COUNT_MBTHREADFLAG_WHERE);

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
		"mbThreadFlag.threadId = ?";

	private FinderPath _finderPathFetchByU_T;
	private FinderPath _finderPathCountByU_T;

	/**
	 * Returns the message boards thread flag where userId = &#63; and threadId = &#63; or throws a <code>NoSuchThreadFlagException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the matching message boards thread flag
	 * @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag findByU_T(long userId, long threadId)
		throws NoSuchThreadFlagException {

		MBThreadFlag mbThreadFlag = fetchByU_T(userId, threadId);

		if (mbThreadFlag == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", threadId=");
			sb.append(threadId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchThreadFlagException(sb.toString());
		}

		return mbThreadFlag;
	}

	/**
	 * Returns the message boards thread flag where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByU_T(long userId, long threadId) {
		return fetchByU_T(userId, threadId, true);
	}

	/**
	 * Returns the message boards thread flag where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByU_T(
		long userId, long threadId, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThreadFlag.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {userId, threadId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByU_T, finderArgs, this);
		}

		if (result instanceof MBThreadFlag) {
			MBThreadFlag mbThreadFlag = (MBThreadFlag)result;

			if ((userId != mbThreadFlag.getUserId()) ||
				(threadId != mbThreadFlag.getThreadId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

			sb.append(_FINDER_COLUMN_U_T_USERID_2);

			sb.append(_FINDER_COLUMN_U_T_THREADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(threadId);

				List<MBThreadFlag> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByU_T, finderArgs, list);
					}
				}
				else {
					MBThreadFlag mbThreadFlag = list.get(0);

					result = mbThreadFlag;

					cacheResult(mbThreadFlag);
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
			return (MBThreadFlag)result;
		}
	}

	/**
	 * Removes the message boards thread flag where userId = &#63; and threadId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the message boards thread flag that was removed
	 */
	@Override
	public MBThreadFlag removeByU_T(long userId, long threadId)
		throws NoSuchThreadFlagException {

		MBThreadFlag mbThreadFlag = findByU_T(userId, threadId);

		return remove(mbThreadFlag);
	}

	/**
	 * Returns the number of message boards thread flags where userId = &#63; and threadId = &#63;.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the number of matching message boards thread flags
	 */
	@Override
	public int countByU_T(long userId, long threadId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThreadFlag.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByU_T;

			finderArgs = new Object[] {userId, threadId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MBTHREADFLAG_WHERE);

			sb.append(_FINDER_COLUMN_U_T_USERID_2);

			sb.append(_FINDER_COLUMN_U_T_THREADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_U_T_USERID_2 =
		"mbThreadFlag.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_T_THREADID_2 =
		"mbThreadFlag.threadId = ?";

	public MBThreadFlagPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(MBThreadFlag.class);

		setModelImplClass(MBThreadFlagImpl.class);
		setModelPKClass(long.class);

		setTable(MBThreadFlagTable.INSTANCE);
	}

	/**
	 * Caches the message boards thread flag in the entity cache if it is enabled.
	 *
	 * @param mbThreadFlag the message boards thread flag
	 */
	@Override
	public void cacheResult(MBThreadFlag mbThreadFlag) {
		if (mbThreadFlag.getCtCollectionId() != 0) {
			mbThreadFlag.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKey(), mbThreadFlag);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {mbThreadFlag.getUuid(), mbThreadFlag.getGroupId()},
			mbThreadFlag);

		finderCache.putResult(
			_finderPathFetchByU_T,
			new Object[] {mbThreadFlag.getUserId(), mbThreadFlag.getThreadId()},
			mbThreadFlag);

		mbThreadFlag.resetOriginalValues();
	}

	/**
	 * Caches the message boards thread flags in the entity cache if it is enabled.
	 *
	 * @param mbThreadFlags the message boards thread flags
	 */
	@Override
	public void cacheResult(List<MBThreadFlag> mbThreadFlags) {
		for (MBThreadFlag mbThreadFlag : mbThreadFlags) {
			if (mbThreadFlag.getCtCollectionId() != 0) {
				mbThreadFlag.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKey()) ==
						null) {

				cacheResult(mbThreadFlag);
			}
			else {
				mbThreadFlag.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all message boards thread flags.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MBThreadFlagImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the message boards thread flag.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MBThreadFlag mbThreadFlag) {
		entityCache.removeResult(
			MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((MBThreadFlagModelImpl)mbThreadFlag, true);
	}

	@Override
	public void clearCache(List<MBThreadFlag> mbThreadFlags) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MBThreadFlag mbThreadFlag : mbThreadFlags) {
			entityCache.removeResult(
				MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKey());

			clearUniqueFindersCache((MBThreadFlagModelImpl)mbThreadFlag, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(MBThreadFlagImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		MBThreadFlagModelImpl mbThreadFlagModelImpl) {

		Object[] args = new Object[] {
			mbThreadFlagModelImpl.getUuid(), mbThreadFlagModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, mbThreadFlagModelImpl, false);

		args = new Object[] {
			mbThreadFlagModelImpl.getUserId(),
			mbThreadFlagModelImpl.getThreadId()
		};

		finderCache.putResult(
			_finderPathCountByU_T, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByU_T, args, mbThreadFlagModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		MBThreadFlagModelImpl mbThreadFlagModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				mbThreadFlagModelImpl.getUuid(),
				mbThreadFlagModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((mbThreadFlagModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				mbThreadFlagModelImpl.getOriginalUuid(),
				mbThreadFlagModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				mbThreadFlagModelImpl.getUserId(),
				mbThreadFlagModelImpl.getThreadId()
			};

			finderCache.removeResult(_finderPathCountByU_T, args);
			finderCache.removeResult(_finderPathFetchByU_T, args);
		}

		if ((mbThreadFlagModelImpl.getColumnBitmask() &
			 _finderPathFetchByU_T.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				mbThreadFlagModelImpl.getOriginalUserId(),
				mbThreadFlagModelImpl.getOriginalThreadId()
			};

			finderCache.removeResult(_finderPathCountByU_T, args);
			finderCache.removeResult(_finderPathFetchByU_T, args);
		}
	}

	/**
	 * Creates a new message boards thread flag with the primary key. Does not add the message boards thread flag to the database.
	 *
	 * @param threadFlagId the primary key for the new message boards thread flag
	 * @return the new message boards thread flag
	 */
	@Override
	public MBThreadFlag create(long threadFlagId) {
		MBThreadFlag mbThreadFlag = new MBThreadFlagImpl();

		mbThreadFlag.setNew(true);
		mbThreadFlag.setPrimaryKey(threadFlagId);

		String uuid = PortalUUIDUtil.generate();

		mbThreadFlag.setUuid(uuid);

		mbThreadFlag.setCompanyId(CompanyThreadLocal.getCompanyId());

		return mbThreadFlag;
	}

	/**
	 * Removes the message boards thread flag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param threadFlagId the primary key of the message boards thread flag
	 * @return the message boards thread flag that was removed
	 * @throws NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	 */
	@Override
	public MBThreadFlag remove(long threadFlagId)
		throws NoSuchThreadFlagException {

		return remove((Serializable)threadFlagId);
	}

	/**
	 * Removes the message boards thread flag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message boards thread flag
	 * @return the message boards thread flag that was removed
	 * @throws NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	 */
	@Override
	public MBThreadFlag remove(Serializable primaryKey)
		throws NoSuchThreadFlagException {

		Session session = null;

		try {
			session = openSession();

			MBThreadFlag mbThreadFlag = (MBThreadFlag)session.get(
				MBThreadFlagImpl.class, primaryKey);

			if (mbThreadFlag == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchThreadFlagException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(mbThreadFlag);
		}
		catch (NoSuchThreadFlagException noSuchEntityException) {
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
	protected MBThreadFlag removeImpl(MBThreadFlag mbThreadFlag) {
		if (!ctPersistenceHelper.isRemove(mbThreadFlag)) {
			return mbThreadFlag;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mbThreadFlag)) {
				mbThreadFlag = (MBThreadFlag)session.get(
					MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKeyObj());
			}

			if (mbThreadFlag != null) {
				session.delete(mbThreadFlag);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mbThreadFlag != null) {
			clearCache(mbThreadFlag);
		}

		return mbThreadFlag;
	}

	@Override
	public MBThreadFlag updateImpl(MBThreadFlag mbThreadFlag) {
		boolean isNew = mbThreadFlag.isNew();

		if (!(mbThreadFlag instanceof MBThreadFlagModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(mbThreadFlag.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					mbThreadFlag);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in mbThreadFlag proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MBThreadFlag implementation " +
					mbThreadFlag.getClass());
		}

		MBThreadFlagModelImpl mbThreadFlagModelImpl =
			(MBThreadFlagModelImpl)mbThreadFlag;

		if (Validator.isNull(mbThreadFlag.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mbThreadFlag.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (mbThreadFlag.getCreateDate() == null)) {
			if (serviceContext == null) {
				mbThreadFlag.setCreateDate(now);
			}
			else {
				mbThreadFlag.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!mbThreadFlagModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				mbThreadFlag.setModifiedDate(now);
			}
			else {
				mbThreadFlag.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(mbThreadFlag)) {
				if (!isNew) {
					session.evict(
						MBThreadFlagImpl.class,
						mbThreadFlag.getPrimaryKeyObj());
				}

				session.save(mbThreadFlag);

				mbThreadFlag.setNew(false);
			}
			else {
				mbThreadFlag = (MBThreadFlag)session.merge(mbThreadFlag);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mbThreadFlag.getCtCollectionId() != 0) {
			mbThreadFlag.resetOriginalValues();

			return mbThreadFlag;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {mbThreadFlagModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				mbThreadFlagModelImpl.getUuid(),
				mbThreadFlagModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {mbThreadFlagModelImpl.getUserId()};

			finderCache.removeResult(_finderPathCountByUserId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserId, args);

			args = new Object[] {mbThreadFlagModelImpl.getThreadId()};

			finderCache.removeResult(_finderPathCountByThreadId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByThreadId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((mbThreadFlagModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbThreadFlagModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {mbThreadFlagModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((mbThreadFlagModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbThreadFlagModelImpl.getOriginalUuid(),
					mbThreadFlagModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					mbThreadFlagModelImpl.getUuid(),
					mbThreadFlagModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((mbThreadFlagModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbThreadFlagModelImpl.getOriginalUserId()
				};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);

				args = new Object[] {mbThreadFlagModelImpl.getUserId()};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);
			}

			if ((mbThreadFlagModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByThreadId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					mbThreadFlagModelImpl.getOriginalThreadId()
				};

				finderCache.removeResult(_finderPathCountByThreadId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByThreadId, args);

				args = new Object[] {mbThreadFlagModelImpl.getThreadId()};

				finderCache.removeResult(_finderPathCountByThreadId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByThreadId, args);
			}
		}

		entityCache.putResult(
			MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKey(), mbThreadFlag,
			false);

		clearUniqueFindersCache(mbThreadFlagModelImpl, false);
		cacheUniqueFindersCache(mbThreadFlagModelImpl);

		mbThreadFlag.resetOriginalValues();

		return mbThreadFlag;
	}

	/**
	 * Returns the message boards thread flag with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards thread flag
	 * @return the message boards thread flag
	 * @throws NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	 */
	@Override
	public MBThreadFlag findByPrimaryKey(Serializable primaryKey)
		throws NoSuchThreadFlagException {

		MBThreadFlag mbThreadFlag = fetchByPrimaryKey(primaryKey);

		if (mbThreadFlag == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchThreadFlagException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return mbThreadFlag;
	}

	/**
	 * Returns the message boards thread flag with the primary key or throws a <code>NoSuchThreadFlagException</code> if it could not be found.
	 *
	 * @param threadFlagId the primary key of the message boards thread flag
	 * @return the message boards thread flag
	 * @throws NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	 */
	@Override
	public MBThreadFlag findByPrimaryKey(long threadFlagId)
		throws NoSuchThreadFlagException {

		return findByPrimaryKey((Serializable)threadFlagId);
	}

	/**
	 * Returns the message boards thread flag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards thread flag
	 * @return the message boards thread flag, or <code>null</code> if a message boards thread flag with the primary key could not be found
	 */
	@Override
	public MBThreadFlag fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(MBThreadFlag.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		MBThreadFlag mbThreadFlag = null;

		Session session = null;

		try {
			session = openSession();

			mbThreadFlag = (MBThreadFlag)session.get(
				MBThreadFlagImpl.class, primaryKey);

			if (mbThreadFlag != null) {
				cacheResult(mbThreadFlag);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return mbThreadFlag;
	}

	/**
	 * Returns the message boards thread flag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param threadFlagId the primary key of the message boards thread flag
	 * @return the message boards thread flag, or <code>null</code> if a message boards thread flag with the primary key could not be found
	 */
	@Override
	public MBThreadFlag fetchByPrimaryKey(long threadFlagId) {
		return fetchByPrimaryKey((Serializable)threadFlagId);
	}

	@Override
	public Map<Serializable, MBThreadFlag> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(MBThreadFlag.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, MBThreadFlag> map =
			new HashMap<Serializable, MBThreadFlag>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			MBThreadFlag mbThreadFlag = fetchByPrimaryKey(primaryKey);

			if (mbThreadFlag != null) {
				map.put(primaryKey, mbThreadFlag);
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

			for (MBThreadFlag mbThreadFlag : (List<MBThreadFlag>)query.list()) {
				map.put(mbThreadFlag.getPrimaryKeyObj(), mbThreadFlag);

				cacheResult(mbThreadFlag);
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
	 * Returns all the message boards thread flags.
	 *
	 * @return the message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards thread flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @return the range of message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findAll(
		int start, int end, OrderByComparator<MBThreadFlag> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findAll(
		int start, int end, OrderByComparator<MBThreadFlag> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThreadFlag.class);

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

		List<MBThreadFlag> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBThreadFlag>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MBTHREADFLAG);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MBTHREADFLAG;

				sql = sql.concat(MBThreadFlagModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<MBThreadFlag>)QueryUtil.list(
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
	 * Removes all the message boards thread flags from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MBThreadFlag mbThreadFlag : findAll()) {
			remove(mbThreadFlag);
		}
	}

	/**
	 * Returns the number of message boards thread flags.
	 *
	 * @return the number of message boards thread flags
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBThreadFlag.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_MBTHREADFLAG);

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
		return "threadFlagId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MBTHREADFLAG;
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
		return MBThreadFlagModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "MBThreadFlag";
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
		ctStrictColumnNames.add("threadId");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("threadFlagId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(new String[] {"userId", "threadId"});
	}

	/**
	 * Initializes the message boards thread flag persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			MBThreadFlagImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			MBThreadFlagImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			MBThreadFlagImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			MBThreadFlagImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			MBThreadFlagModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			MBThreadFlagImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			MBThreadFlagModelImpl.UUID_COLUMN_BITMASK |
			MBThreadFlagModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			MBThreadFlagImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			MBThreadFlagImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			MBThreadFlagModelImpl.UUID_COLUMN_BITMASK |
			MBThreadFlagModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUserId = new FinderPath(
			MBThreadFlagImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			MBThreadFlagImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUserId", new String[] {Long.class.getName()},
			MBThreadFlagModelImpl.USERID_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByThreadId = new FinderPath(
			MBThreadFlagImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByThreadId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByThreadId = new FinderPath(
			MBThreadFlagImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByThreadId", new String[] {Long.class.getName()},
			MBThreadFlagModelImpl.THREADID_COLUMN_BITMASK);

		_finderPathCountByThreadId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByThreadId", new String[] {Long.class.getName()});

		_finderPathFetchByU_T = new FinderPath(
			MBThreadFlagImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByU_T",
			new String[] {Long.class.getName(), Long.class.getName()},
			MBThreadFlagModelImpl.USERID_COLUMN_BITMASK |
			MBThreadFlagModelImpl.THREADID_COLUMN_BITMASK);

		_finderPathCountByU_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_T",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(MBThreadFlagImpl.class.getName());
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

	private static final String _SQL_SELECT_MBTHREADFLAG =
		"SELECT mbThreadFlag FROM MBThreadFlag mbThreadFlag";

	private static final String _SQL_SELECT_MBTHREADFLAG_WHERE =
		"SELECT mbThreadFlag FROM MBThreadFlag mbThreadFlag WHERE ";

	private static final String _SQL_COUNT_MBTHREADFLAG =
		"SELECT COUNT(mbThreadFlag) FROM MBThreadFlag mbThreadFlag";

	private static final String _SQL_COUNT_MBTHREADFLAG_WHERE =
		"SELECT COUNT(mbThreadFlag) FROM MBThreadFlag mbThreadFlag WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "mbThreadFlag.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MBThreadFlag exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No MBThreadFlag exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MBThreadFlagPersistenceImpl.class);

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