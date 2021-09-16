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

package com.liferay.mobile.device.rules.service.persistence.impl;

import com.liferay.mobile.device.rules.exception.NoSuchActionException;
import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.mobile.device.rules.model.MDRActionTable;
import com.liferay.mobile.device.rules.model.impl.MDRActionImpl;
import com.liferay.mobile.device.rules.model.impl.MDRActionModelImpl;
import com.liferay.mobile.device.rules.service.persistence.MDRActionPersistence;
import com.liferay.mobile.device.rules.service.persistence.impl.constants.MDRPersistenceConstants;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the mdr action service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @generated
 */
@Component(service = {MDRActionPersistence.class, BasePersistence.class})
public class MDRActionPersistenceImpl
	extends BasePersistenceImpl<MDRAction> implements MDRActionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MDRActionUtil</code> to access the mdr action persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MDRActionImpl.class.getName();

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
	 * Returns all the mdr actions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching mdr actions
	 */
	@Override
	public List<MDRAction> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mdr actions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @return the range of matching mdr actions
	 */
	@Override
	public List<MDRAction> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the mdr actions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr actions
	 */
	@Override
	public List<MDRAction> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MDRAction> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mdr actions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr actions
	 */
	@Override
	public List<MDRAction> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MDRAction> orderByComparator,
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

		List<MDRAction> list = null;

		if (useFinderCache) {
			list = (List<MDRAction>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (MDRAction mdrAction : list) {
					if (!uuid.equals(mdrAction.getUuid())) {
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

			sb.append(_SQL_SELECT_MDRACTION_WHERE);

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
				sb.append(MDRActionModelImpl.ORDER_BY_JPQL);
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

				list = (List<MDRAction>)QueryUtil.list(
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
	 * Returns the first mdr action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	@Override
	public MDRAction findByUuid_First(
			String uuid, OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {

		MDRAction mdrAction = fetchByUuid_First(uuid, orderByComparator);

		if (mdrAction != null) {
			return mdrAction;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchActionException(sb.toString());
	}

	/**
	 * Returns the first mdr action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	@Override
	public MDRAction fetchByUuid_First(
		String uuid, OrderByComparator<MDRAction> orderByComparator) {

		List<MDRAction> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last mdr action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	@Override
	public MDRAction findByUuid_Last(
			String uuid, OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {

		MDRAction mdrAction = fetchByUuid_Last(uuid, orderByComparator);

		if (mdrAction != null) {
			return mdrAction;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchActionException(sb.toString());
	}

	/**
	 * Returns the last mdr action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	@Override
	public MDRAction fetchByUuid_Last(
		String uuid, OrderByComparator<MDRAction> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<MDRAction> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the mdr actions before and after the current mdr action in the ordered set where uuid = &#63;.
	 *
	 * @param actionId the primary key of the current mdr action
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr action
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	@Override
	public MDRAction[] findByUuid_PrevAndNext(
			long actionId, String uuid,
			OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {

		uuid = Objects.toString(uuid, "");

		MDRAction mdrAction = findByPrimaryKey(actionId);

		Session session = null;

		try {
			session = openSession();

			MDRAction[] array = new MDRActionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, mdrAction, uuid, orderByComparator, true);

			array[1] = mdrAction;

			array[2] = getByUuid_PrevAndNext(
				session, mdrAction, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MDRAction getByUuid_PrevAndNext(
		Session session, MDRAction mdrAction, String uuid,
		OrderByComparator<MDRAction> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MDRACTION_WHERE);

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
			sb.append(MDRActionModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(mdrAction)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MDRAction> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the mdr actions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (MDRAction mdrAction :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mdrAction);
		}
	}

	/**
	 * Returns the number of mdr actions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching mdr actions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MDRACTION_WHERE);

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
		"mdrAction.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(mdrAction.uuid IS NULL OR mdrAction.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the mdr action where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchActionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	@Override
	public MDRAction findByUUID_G(String uuid, long groupId)
		throws NoSuchActionException {

		MDRAction mdrAction = fetchByUUID_G(uuid, groupId);

		if (mdrAction == null) {
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

			throw new NoSuchActionException(sb.toString());
		}

		return mdrAction;
	}

	/**
	 * Returns the mdr action where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	@Override
	public MDRAction fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the mdr action where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	@Override
	public MDRAction fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof MDRAction) {
			MDRAction mdrAction = (MDRAction)result;

			if (!Objects.equals(uuid, mdrAction.getUuid()) ||
				(groupId != mdrAction.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_MDRACTION_WHERE);

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

				List<MDRAction> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					MDRAction mdrAction = list.get(0);

					result = mdrAction;

					cacheResult(mdrAction);
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
			return (MDRAction)result;
		}
	}

	/**
	 * Removes the mdr action where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the mdr action that was removed
	 */
	@Override
	public MDRAction removeByUUID_G(String uuid, long groupId)
		throws NoSuchActionException {

		MDRAction mdrAction = findByUUID_G(uuid, groupId);

		return remove(mdrAction);
	}

	/**
	 * Returns the number of mdr actions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching mdr actions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MDRACTION_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"mdrAction.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(mdrAction.uuid IS NULL OR mdrAction.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"mdrAction.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the mdr actions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching mdr actions
	 */
	@Override
	public List<MDRAction> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mdr actions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @return the range of matching mdr actions
	 */
	@Override
	public List<MDRAction> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the mdr actions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr actions
	 */
	@Override
	public List<MDRAction> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MDRAction> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mdr actions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr actions
	 */
	@Override
	public List<MDRAction> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MDRAction> orderByComparator,
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

		List<MDRAction> list = null;

		if (useFinderCache) {
			list = (List<MDRAction>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (MDRAction mdrAction : list) {
					if (!uuid.equals(mdrAction.getUuid()) ||
						(companyId != mdrAction.getCompanyId())) {

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

			sb.append(_SQL_SELECT_MDRACTION_WHERE);

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
				sb.append(MDRActionModelImpl.ORDER_BY_JPQL);
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

				list = (List<MDRAction>)QueryUtil.list(
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
	 * Returns the first mdr action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	@Override
	public MDRAction findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {

		MDRAction mdrAction = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (mdrAction != null) {
			return mdrAction;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchActionException(sb.toString());
	}

	/**
	 * Returns the first mdr action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	@Override
	public MDRAction fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<MDRAction> orderByComparator) {

		List<MDRAction> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last mdr action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	@Override
	public MDRAction findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {

		MDRAction mdrAction = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (mdrAction != null) {
			return mdrAction;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchActionException(sb.toString());
	}

	/**
	 * Returns the last mdr action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	@Override
	public MDRAction fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<MDRAction> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<MDRAction> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the mdr actions before and after the current mdr action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param actionId the primary key of the current mdr action
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr action
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	@Override
	public MDRAction[] findByUuid_C_PrevAndNext(
			long actionId, String uuid, long companyId,
			OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {

		uuid = Objects.toString(uuid, "");

		MDRAction mdrAction = findByPrimaryKey(actionId);

		Session session = null;

		try {
			session = openSession();

			MDRAction[] array = new MDRActionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, mdrAction, uuid, companyId, orderByComparator, true);

			array[1] = mdrAction;

			array[2] = getByUuid_C_PrevAndNext(
				session, mdrAction, uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MDRAction getByUuid_C_PrevAndNext(
		Session session, MDRAction mdrAction, String uuid, long companyId,
		OrderByComparator<MDRAction> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_MDRACTION_WHERE);

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
			sb.append(MDRActionModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(mdrAction)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MDRAction> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the mdr actions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (MDRAction mdrAction :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(mdrAction);
		}
	}

	/**
	 * Returns the number of mdr actions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching mdr actions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MDRACTION_WHERE);

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
		"mdrAction.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(mdrAction.uuid IS NULL OR mdrAction.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"mdrAction.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByRuleGroupInstanceId;
	private FinderPath _finderPathWithoutPaginationFindByRuleGroupInstanceId;
	private FinderPath _finderPathCountByRuleGroupInstanceId;

	/**
	 * Returns all the mdr actions where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @return the matching mdr actions
	 */
	@Override
	public List<MDRAction> findByRuleGroupInstanceId(long ruleGroupInstanceId) {
		return findByRuleGroupInstanceId(
			ruleGroupInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mdr actions where ruleGroupInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @return the range of matching mdr actions
	 */
	@Override
	public List<MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end) {

		return findByRuleGroupInstanceId(ruleGroupInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the mdr actions where ruleGroupInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr actions
	 */
	@Override
	public List<MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end,
		OrderByComparator<MDRAction> orderByComparator) {

		return findByRuleGroupInstanceId(
			ruleGroupInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mdr actions where ruleGroupInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr actions
	 */
	@Override
	public List<MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end,
		OrderByComparator<MDRAction> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByRuleGroupInstanceId;
				finderArgs = new Object[] {ruleGroupInstanceId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByRuleGroupInstanceId;
			finderArgs = new Object[] {
				ruleGroupInstanceId, start, end, orderByComparator
			};
		}

		List<MDRAction> list = null;

		if (useFinderCache) {
			list = (List<MDRAction>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (MDRAction mdrAction : list) {
					if (ruleGroupInstanceId !=
							mdrAction.getRuleGroupInstanceId()) {

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

			sb.append(_SQL_SELECT_MDRACTION_WHERE);

			sb.append(_FINDER_COLUMN_RULEGROUPINSTANCEID_RULEGROUPINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MDRActionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ruleGroupInstanceId);

				list = (List<MDRAction>)QueryUtil.list(
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
	 * Returns the first mdr action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	@Override
	public MDRAction findByRuleGroupInstanceId_First(
			long ruleGroupInstanceId,
			OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {

		MDRAction mdrAction = fetchByRuleGroupInstanceId_First(
			ruleGroupInstanceId, orderByComparator);

		if (mdrAction != null) {
			return mdrAction;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ruleGroupInstanceId=");
		sb.append(ruleGroupInstanceId);

		sb.append("}");

		throw new NoSuchActionException(sb.toString());
	}

	/**
	 * Returns the first mdr action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	@Override
	public MDRAction fetchByRuleGroupInstanceId_First(
		long ruleGroupInstanceId,
		OrderByComparator<MDRAction> orderByComparator) {

		List<MDRAction> list = findByRuleGroupInstanceId(
			ruleGroupInstanceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last mdr action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	@Override
	public MDRAction findByRuleGroupInstanceId_Last(
			long ruleGroupInstanceId,
			OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {

		MDRAction mdrAction = fetchByRuleGroupInstanceId_Last(
			ruleGroupInstanceId, orderByComparator);

		if (mdrAction != null) {
			return mdrAction;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ruleGroupInstanceId=");
		sb.append(ruleGroupInstanceId);

		sb.append("}");

		throw new NoSuchActionException(sb.toString());
	}

	/**
	 * Returns the last mdr action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	@Override
	public MDRAction fetchByRuleGroupInstanceId_Last(
		long ruleGroupInstanceId,
		OrderByComparator<MDRAction> orderByComparator) {

		int count = countByRuleGroupInstanceId(ruleGroupInstanceId);

		if (count == 0) {
			return null;
		}

		List<MDRAction> list = findByRuleGroupInstanceId(
			ruleGroupInstanceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the mdr actions before and after the current mdr action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param actionId the primary key of the current mdr action
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr action
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	@Override
	public MDRAction[] findByRuleGroupInstanceId_PrevAndNext(
			long actionId, long ruleGroupInstanceId,
			OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {

		MDRAction mdrAction = findByPrimaryKey(actionId);

		Session session = null;

		try {
			session = openSession();

			MDRAction[] array = new MDRActionImpl[3];

			array[0] = getByRuleGroupInstanceId_PrevAndNext(
				session, mdrAction, ruleGroupInstanceId, orderByComparator,
				true);

			array[1] = mdrAction;

			array[2] = getByRuleGroupInstanceId_PrevAndNext(
				session, mdrAction, ruleGroupInstanceId, orderByComparator,
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

	protected MDRAction getByRuleGroupInstanceId_PrevAndNext(
		Session session, MDRAction mdrAction, long ruleGroupInstanceId,
		OrderByComparator<MDRAction> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MDRACTION_WHERE);

		sb.append(_FINDER_COLUMN_RULEGROUPINSTANCEID_RULEGROUPINSTANCEID_2);

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
			sb.append(MDRActionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ruleGroupInstanceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mdrAction)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MDRAction> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the mdr actions where ruleGroupInstanceId = &#63; from the database.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 */
	@Override
	public void removeByRuleGroupInstanceId(long ruleGroupInstanceId) {
		for (MDRAction mdrAction :
				findByRuleGroupInstanceId(
					ruleGroupInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(mdrAction);
		}
	}

	/**
	 * Returns the number of mdr actions where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @return the number of matching mdr actions
	 */
	@Override
	public int countByRuleGroupInstanceId(long ruleGroupInstanceId) {
		FinderPath finderPath = _finderPathCountByRuleGroupInstanceId;

		Object[] finderArgs = new Object[] {ruleGroupInstanceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MDRACTION_WHERE);

			sb.append(_FINDER_COLUMN_RULEGROUPINSTANCEID_RULEGROUPINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ruleGroupInstanceId);

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
		_FINDER_COLUMN_RULEGROUPINSTANCEID_RULEGROUPINSTANCEID_2 =
			"mdrAction.ruleGroupInstanceId = ?";

	public MDRActionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(MDRAction.class);

		setModelImplClass(MDRActionImpl.class);
		setModelPKClass(long.class);

		setTable(MDRActionTable.INSTANCE);
	}

	/**
	 * Caches the mdr action in the entity cache if it is enabled.
	 *
	 * @param mdrAction the mdr action
	 */
	@Override
	public void cacheResult(MDRAction mdrAction) {
		entityCache.putResult(
			MDRActionImpl.class, mdrAction.getPrimaryKey(), mdrAction);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {mdrAction.getUuid(), mdrAction.getGroupId()},
			mdrAction);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the mdr actions in the entity cache if it is enabled.
	 *
	 * @param mdrActions the mdr actions
	 */
	@Override
	public void cacheResult(List<MDRAction> mdrActions) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (mdrActions.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (MDRAction mdrAction : mdrActions) {
			if (entityCache.getResult(
					MDRActionImpl.class, mdrAction.getPrimaryKey()) == null) {

				cacheResult(mdrAction);
			}
		}
	}

	/**
	 * Clears the cache for all mdr actions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MDRActionImpl.class);

		finderCache.clearCache(MDRActionImpl.class);
	}

	/**
	 * Clears the cache for the mdr action.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MDRAction mdrAction) {
		entityCache.removeResult(MDRActionImpl.class, mdrAction);
	}

	@Override
	public void clearCache(List<MDRAction> mdrActions) {
		for (MDRAction mdrAction : mdrActions) {
			entityCache.removeResult(MDRActionImpl.class, mdrAction);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(MDRActionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(MDRActionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		MDRActionModelImpl mdrActionModelImpl) {

		Object[] args = new Object[] {
			mdrActionModelImpl.getUuid(), mdrActionModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, mdrActionModelImpl);
	}

	/**
	 * Creates a new mdr action with the primary key. Does not add the mdr action to the database.
	 *
	 * @param actionId the primary key for the new mdr action
	 * @return the new mdr action
	 */
	@Override
	public MDRAction create(long actionId) {
		MDRAction mdrAction = new MDRActionImpl();

		mdrAction.setNew(true);
		mdrAction.setPrimaryKey(actionId);

		String uuid = PortalUUIDUtil.generate();

		mdrAction.setUuid(uuid);

		mdrAction.setCompanyId(CompanyThreadLocal.getCompanyId());

		return mdrAction;
	}

	/**
	 * Removes the mdr action with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param actionId the primary key of the mdr action
	 * @return the mdr action that was removed
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	@Override
	public MDRAction remove(long actionId) throws NoSuchActionException {
		return remove((Serializable)actionId);
	}

	/**
	 * Removes the mdr action with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the mdr action
	 * @return the mdr action that was removed
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	@Override
	public MDRAction remove(Serializable primaryKey)
		throws NoSuchActionException {

		Session session = null;

		try {
			session = openSession();

			MDRAction mdrAction = (MDRAction)session.get(
				MDRActionImpl.class, primaryKey);

			if (mdrAction == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(mdrAction);
		}
		catch (NoSuchActionException noSuchEntityException) {
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
	protected MDRAction removeImpl(MDRAction mdrAction) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mdrAction)) {
				mdrAction = (MDRAction)session.get(
					MDRActionImpl.class, mdrAction.getPrimaryKeyObj());
			}

			if (mdrAction != null) {
				session.delete(mdrAction);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mdrAction != null) {
			clearCache(mdrAction);
		}

		return mdrAction;
	}

	@Override
	public MDRAction updateImpl(MDRAction mdrAction) {
		boolean isNew = mdrAction.isNew();

		if (!(mdrAction instanceof MDRActionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(mdrAction.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(mdrAction);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in mdrAction proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MDRAction implementation " +
					mdrAction.getClass());
		}

		MDRActionModelImpl mdrActionModelImpl = (MDRActionModelImpl)mdrAction;

		if (Validator.isNull(mdrAction.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mdrAction.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (mdrAction.getCreateDate() == null)) {
			if (serviceContext == null) {
				mdrAction.setCreateDate(date);
			}
			else {
				mdrAction.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!mdrActionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				mdrAction.setModifiedDate(date);
			}
			else {
				mdrAction.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(mdrAction);
			}
			else {
				mdrAction = (MDRAction)session.merge(mdrAction);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			MDRActionImpl.class, mdrActionModelImpl, false, true);

		cacheUniqueFindersCache(mdrActionModelImpl);

		if (isNew) {
			mdrAction.setNew(false);
		}

		mdrAction.resetOriginalValues();

		return mdrAction;
	}

	/**
	 * Returns the mdr action with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the mdr action
	 * @return the mdr action
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	@Override
	public MDRAction findByPrimaryKey(Serializable primaryKey)
		throws NoSuchActionException {

		MDRAction mdrAction = fetchByPrimaryKey(primaryKey);

		if (mdrAction == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchActionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return mdrAction;
	}

	/**
	 * Returns the mdr action with the primary key or throws a <code>NoSuchActionException</code> if it could not be found.
	 *
	 * @param actionId the primary key of the mdr action
	 * @return the mdr action
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	@Override
	public MDRAction findByPrimaryKey(long actionId)
		throws NoSuchActionException {

		return findByPrimaryKey((Serializable)actionId);
	}

	/**
	 * Returns the mdr action with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param actionId the primary key of the mdr action
	 * @return the mdr action, or <code>null</code> if a mdr action with the primary key could not be found
	 */
	@Override
	public MDRAction fetchByPrimaryKey(long actionId) {
		return fetchByPrimaryKey((Serializable)actionId);
	}

	/**
	 * Returns all the mdr actions.
	 *
	 * @return the mdr actions
	 */
	@Override
	public List<MDRAction> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mdr actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @return the range of mdr actions
	 */
	@Override
	public List<MDRAction> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the mdr actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mdr actions
	 */
	@Override
	public List<MDRAction> findAll(
		int start, int end, OrderByComparator<MDRAction> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mdr actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mdr actions
	 */
	@Override
	public List<MDRAction> findAll(
		int start, int end, OrderByComparator<MDRAction> orderByComparator,
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

		List<MDRAction> list = null;

		if (useFinderCache) {
			list = (List<MDRAction>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MDRACTION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MDRACTION;

				sql = sql.concat(MDRActionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<MDRAction>)QueryUtil.list(
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
	 * Removes all the mdr actions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MDRAction mdrAction : findAll()) {
			remove(mdrAction);
		}
	}

	/**
	 * Returns the number of mdr actions.
	 *
	 * @return the number of mdr actions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_MDRACTION);

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
		return "actionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MDRACTION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return MDRActionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the mdr action persistence.
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

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

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

		_finderPathWithPaginationFindByRuleGroupInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRuleGroupInstanceId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ruleGroupInstanceId"}, true);

		_finderPathWithoutPaginationFindByRuleGroupInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByRuleGroupInstanceId", new String[] {Long.class.getName()},
			new String[] {"ruleGroupInstanceId"}, true);

		_finderPathCountByRuleGroupInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByRuleGroupInstanceId", new String[] {Long.class.getName()},
			new String[] {"ruleGroupInstanceId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(MDRActionImpl.class.getName());
	}

	@Override
	@Reference(
		target = MDRPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = MDRPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = MDRPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_MDRACTION =
		"SELECT mdrAction FROM MDRAction mdrAction";

	private static final String _SQL_SELECT_MDRACTION_WHERE =
		"SELECT mdrAction FROM MDRAction mdrAction WHERE ";

	private static final String _SQL_COUNT_MDRACTION =
		"SELECT COUNT(mdrAction) FROM MDRAction mdrAction";

	private static final String _SQL_COUNT_MDRACTION_WHERE =
		"SELECT COUNT(mdrAction) FROM MDRAction mdrAction WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "mdrAction.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MDRAction exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No MDRAction exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MDRActionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private MDRActionModelArgumentsResolver _mdrActionModelArgumentsResolver;

}