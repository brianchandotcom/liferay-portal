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

import aQute.bnd.annotation.ProviderType;

import com.liferay.mobile.device.rules.exception.NoSuchActionException;
import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.mobile.device.rules.model.impl.MDRActionImpl;
import com.liferay.mobile.device.rules.model.impl.MDRActionModelImpl;
import com.liferay.mobile.device.rules.service.persistence.MDRActionPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the m d r action service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRActionPersistence
 * @see com.liferay.mobile.device.rules.service.persistence.MDRActionUtil
 * @generated
 */
@ProviderType
public class MDRActionPersistenceImpl extends BasePersistenceImpl<MDRAction>
	implements MDRActionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MDRActionUtil} to access the m d r action persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MDRActionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionModelImpl.FINDER_CACHE_ENABLED, MDRActionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionModelImpl.FINDER_CACHE_ENABLED, MDRActionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionModelImpl.FINDER_CACHE_ENABLED, MDRActionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionModelImpl.FINDER_CACHE_ENABLED, MDRActionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			MDRActionModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the m d r actions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching m d r actions
	 */
	@Override
	public List<MDRAction> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r actions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of m d r actions
	 * @param end the upper bound of the range of m d r actions (not inclusive)
	 * @return the range of matching m d r actions
	 */
	@Override
	public List<MDRAction> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r actions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of m d r actions
	 * @param end the upper bound of the range of m d r actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching m d r actions
	 */
	@Override
	public List<MDRAction> findByUuid(String uuid, int start, int end,
		OrderByComparator<MDRAction> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the m d r actions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of m d r actions
	 * @param end the upper bound of the range of m d r actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching m d r actions
	 */
	@Override
	public List<MDRAction> findByUuid(String uuid, int start, int end,
		OrderByComparator<MDRAction> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<MDRAction> list = null;

		if (retrieveFromCache) {
			list = (List<MDRAction>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MDRAction mdrAction : list) {
					if (!Validator.equals(uuid, mdrAction.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_MDRACTION_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MDRActionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<MDRAction>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MDRAction>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first m d r action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r action
	 * @throws com.liferay.mobile.device.rules.NoSuchActionException if a matching m d r action could not be found
	 */
	@Override
	public MDRAction findByUuid_First(String uuid,
		OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {
		MDRAction mdrAction = fetchByUuid_First(uuid, orderByComparator);

		if (mdrAction != null) {
			return mdrAction;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActionException(msg.toString());
	}

	/**
	 * Returns the first m d r action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r action, or <code>null</code> if a matching m d r action could not be found
	 */
	@Override
	public MDRAction fetchByUuid_First(String uuid,
		OrderByComparator<MDRAction> orderByComparator) {
		List<MDRAction> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last m d r action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r action
	 * @throws com.liferay.mobile.device.rules.NoSuchActionException if a matching m d r action could not be found
	 */
	@Override
	public MDRAction findByUuid_Last(String uuid,
		OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {
		MDRAction mdrAction = fetchByUuid_Last(uuid, orderByComparator);

		if (mdrAction != null) {
			return mdrAction;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActionException(msg.toString());
	}

	/**
	 * Returns the last m d r action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r action, or <code>null</code> if a matching m d r action could not be found
	 */
	@Override
	public MDRAction fetchByUuid_Last(String uuid,
		OrderByComparator<MDRAction> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<MDRAction> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the m d r actions before and after the current m d r action in the ordered set where uuid = &#63;.
	 *
	 * @param actionId the primary key of the current m d r action
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next m d r action
	 * @throws com.liferay.mobile.device.rules.NoSuchActionException if a m d r action with the primary key could not be found
	 */
	@Override
	public MDRAction[] findByUuid_PrevAndNext(long actionId, String uuid,
		OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {
		MDRAction mdrAction = findByPrimaryKey(actionId);

		Session session = null;

		try {
			session = openSession();

			MDRAction[] array = new MDRActionImpl[3];

			array[0] = getByUuid_PrevAndNext(session, mdrAction, uuid,
					orderByComparator, true);

			array[1] = mdrAction;

			array[2] = getByUuid_PrevAndNext(session, mdrAction, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MDRAction getByUuid_PrevAndNext(Session session,
		MDRAction mdrAction, String uuid,
		OrderByComparator<MDRAction> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MDRACTION_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(MDRActionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mdrAction);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MDRAction> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the m d r actions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (MDRAction mdrAction : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(mdrAction);
		}
	}

	/**
	 * Returns the number of m d r actions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching m d r actions
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MDRACTION_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "mdrAction.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "mdrAction.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(mdrAction.uuid IS NULL OR mdrAction.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionModelImpl.FINDER_CACHE_ENABLED, MDRActionImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			MDRActionModelImpl.UUID_COLUMN_BITMASK |
			MDRActionModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the m d r action where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.mobile.device.rules.NoSuchActionException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching m d r action
	 * @throws com.liferay.mobile.device.rules.NoSuchActionException if a matching m d r action could not be found
	 */
	@Override
	public MDRAction findByUUID_G(String uuid, long groupId)
		throws NoSuchActionException {
		MDRAction mdrAction = fetchByUUID_G(uuid, groupId);

		if (mdrAction == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchActionException(msg.toString());
		}

		return mdrAction;
	}

	/**
	 * Returns the m d r action where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching m d r action, or <code>null</code> if a matching m d r action could not be found
	 */
	@Override
	public MDRAction fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the m d r action where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching m d r action, or <code>null</code> if a matching m d r action could not be found
	 */
	@Override
	public MDRAction fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof MDRAction) {
			MDRAction mdrAction = (MDRAction)result;

			if (!Validator.equals(uuid, mdrAction.getUuid()) ||
					(groupId != mdrAction.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MDRACTION_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<MDRAction> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					MDRAction mdrAction = list.get(0);

					result = mdrAction;

					cacheResult(mdrAction);

					if ((mdrAction.getUuid() == null) ||
							!mdrAction.getUuid().equals(uuid) ||
							(mdrAction.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, mdrAction);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

				throw processException(e);
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
	 * Removes the m d r action where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the m d r action that was removed
	 */
	@Override
	public MDRAction removeByUUID_G(String uuid, long groupId)
		throws NoSuchActionException {
		MDRAction mdrAction = findByUUID_G(uuid, groupId);

		return remove(mdrAction);
	}

	/**
	 * Returns the number of m d r actions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching m d r actions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MDRACTION_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "mdrAction.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "mdrAction.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(mdrAction.uuid IS NULL OR mdrAction.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "mdrAction.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionModelImpl.FINDER_CACHE_ENABLED, MDRActionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionModelImpl.FINDER_CACHE_ENABLED, MDRActionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			MDRActionModelImpl.UUID_COLUMN_BITMASK |
			MDRActionModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the m d r actions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching m d r actions
	 */
	@Override
	public List<MDRAction> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r actions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of m d r actions
	 * @param end the upper bound of the range of m d r actions (not inclusive)
	 * @return the range of matching m d r actions
	 */
	@Override
	public List<MDRAction> findByUuid_C(String uuid, long companyId, int start,
		int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r actions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of m d r actions
	 * @param end the upper bound of the range of m d r actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching m d r actions
	 */
	@Override
	public List<MDRAction> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<MDRAction> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the m d r actions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of m d r actions
	 * @param end the upper bound of the range of m d r actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching m d r actions
	 */
	@Override
	public List<MDRAction> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<MDRAction> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<MDRAction> list = null;

		if (retrieveFromCache) {
			list = (List<MDRAction>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MDRAction mdrAction : list) {
					if (!Validator.equals(uuid, mdrAction.getUuid()) ||
							(companyId != mdrAction.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_MDRACTION_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MDRActionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<MDRAction>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MDRAction>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first m d r action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r action
	 * @throws com.liferay.mobile.device.rules.NoSuchActionException if a matching m d r action could not be found
	 */
	@Override
	public MDRAction findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {
		MDRAction mdrAction = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (mdrAction != null) {
			return mdrAction;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActionException(msg.toString());
	}

	/**
	 * Returns the first m d r action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r action, or <code>null</code> if a matching m d r action could not be found
	 */
	@Override
	public MDRAction fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<MDRAction> orderByComparator) {
		List<MDRAction> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last m d r action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r action
	 * @throws com.liferay.mobile.device.rules.NoSuchActionException if a matching m d r action could not be found
	 */
	@Override
	public MDRAction findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {
		MDRAction mdrAction = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (mdrAction != null) {
			return mdrAction;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActionException(msg.toString());
	}

	/**
	 * Returns the last m d r action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r action, or <code>null</code> if a matching m d r action could not be found
	 */
	@Override
	public MDRAction fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<MDRAction> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<MDRAction> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the m d r actions before and after the current m d r action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param actionId the primary key of the current m d r action
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next m d r action
	 * @throws com.liferay.mobile.device.rules.NoSuchActionException if a m d r action with the primary key could not be found
	 */
	@Override
	public MDRAction[] findByUuid_C_PrevAndNext(long actionId, String uuid,
		long companyId, OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {
		MDRAction mdrAction = findByPrimaryKey(actionId);

		Session session = null;

		try {
			session = openSession();

			MDRAction[] array = new MDRActionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, mdrAction, uuid,
					companyId, orderByComparator, true);

			array[1] = mdrAction;

			array[2] = getByUuid_C_PrevAndNext(session, mdrAction, uuid,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MDRAction getByUuid_C_PrevAndNext(Session session,
		MDRAction mdrAction, String uuid, long companyId,
		OrderByComparator<MDRAction> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MDRACTION_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(MDRActionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mdrAction);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MDRAction> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the m d r actions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (MDRAction mdrAction : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(mdrAction);
		}
	}

	/**
	 * Returns the number of m d r actions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching m d r actions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MDRACTION_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "mdrAction.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "mdrAction.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(mdrAction.uuid IS NULL OR mdrAction.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "mdrAction.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_RULEGROUPINSTANCEID =
		new FinderPath(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionModelImpl.FINDER_CACHE_ENABLED, MDRActionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByRuleGroupInstanceId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RULEGROUPINSTANCEID =
		new FinderPath(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionModelImpl.FINDER_CACHE_ENABLED, MDRActionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByRuleGroupInstanceId", new String[] { Long.class.getName() },
			MDRActionModelImpl.RULEGROUPINSTANCEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_RULEGROUPINSTANCEID = new FinderPath(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByRuleGroupInstanceId", new String[] { Long.class.getName() });

	/**
	 * Returns all the m d r actions where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @return the matching m d r actions
	 */
	@Override
	public List<MDRAction> findByRuleGroupInstanceId(long ruleGroupInstanceId) {
		return findByRuleGroupInstanceId(ruleGroupInstanceId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r actions where ruleGroupInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param start the lower bound of the range of m d r actions
	 * @param end the upper bound of the range of m d r actions (not inclusive)
	 * @return the range of matching m d r actions
	 */
	@Override
	public List<MDRAction> findByRuleGroupInstanceId(long ruleGroupInstanceId,
		int start, int end) {
		return findByRuleGroupInstanceId(ruleGroupInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r actions where ruleGroupInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param start the lower bound of the range of m d r actions
	 * @param end the upper bound of the range of m d r actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching m d r actions
	 */
	@Override
	public List<MDRAction> findByRuleGroupInstanceId(long ruleGroupInstanceId,
		int start, int end, OrderByComparator<MDRAction> orderByComparator) {
		return findByRuleGroupInstanceId(ruleGroupInstanceId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the m d r actions where ruleGroupInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param start the lower bound of the range of m d r actions
	 * @param end the upper bound of the range of m d r actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching m d r actions
	 */
	@Override
	public List<MDRAction> findByRuleGroupInstanceId(long ruleGroupInstanceId,
		int start, int end, OrderByComparator<MDRAction> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RULEGROUPINSTANCEID;
			finderArgs = new Object[] { ruleGroupInstanceId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_RULEGROUPINSTANCEID;
			finderArgs = new Object[] {
					ruleGroupInstanceId,
					
					start, end, orderByComparator
				};
		}

		List<MDRAction> list = null;

		if (retrieveFromCache) {
			list = (List<MDRAction>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MDRAction mdrAction : list) {
					if ((ruleGroupInstanceId != mdrAction.getRuleGroupInstanceId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_MDRACTION_WHERE);

			query.append(_FINDER_COLUMN_RULEGROUPINSTANCEID_RULEGROUPINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MDRActionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ruleGroupInstanceId);

				if (!pagination) {
					list = (List<MDRAction>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MDRAction>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r action
	 * @throws com.liferay.mobile.device.rules.NoSuchActionException if a matching m d r action could not be found
	 */
	@Override
	public MDRAction findByRuleGroupInstanceId_First(long ruleGroupInstanceId,
		OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {
		MDRAction mdrAction = fetchByRuleGroupInstanceId_First(ruleGroupInstanceId,
				orderByComparator);

		if (mdrAction != null) {
			return mdrAction;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ruleGroupInstanceId=");
		msg.append(ruleGroupInstanceId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActionException(msg.toString());
	}

	/**
	 * Returns the first m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r action, or <code>null</code> if a matching m d r action could not be found
	 */
	@Override
	public MDRAction fetchByRuleGroupInstanceId_First(
		long ruleGroupInstanceId, OrderByComparator<MDRAction> orderByComparator) {
		List<MDRAction> list = findByRuleGroupInstanceId(ruleGroupInstanceId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r action
	 * @throws com.liferay.mobile.device.rules.NoSuchActionException if a matching m d r action could not be found
	 */
	@Override
	public MDRAction findByRuleGroupInstanceId_Last(long ruleGroupInstanceId,
		OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {
		MDRAction mdrAction = fetchByRuleGroupInstanceId_Last(ruleGroupInstanceId,
				orderByComparator);

		if (mdrAction != null) {
			return mdrAction;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ruleGroupInstanceId=");
		msg.append(ruleGroupInstanceId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActionException(msg.toString());
	}

	/**
	 * Returns the last m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r action, or <code>null</code> if a matching m d r action could not be found
	 */
	@Override
	public MDRAction fetchByRuleGroupInstanceId_Last(long ruleGroupInstanceId,
		OrderByComparator<MDRAction> orderByComparator) {
		int count = countByRuleGroupInstanceId(ruleGroupInstanceId);

		if (count == 0) {
			return null;
		}

		List<MDRAction> list = findByRuleGroupInstanceId(ruleGroupInstanceId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the m d r actions before and after the current m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param actionId the primary key of the current m d r action
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next m d r action
	 * @throws com.liferay.mobile.device.rules.NoSuchActionException if a m d r action with the primary key could not be found
	 */
	@Override
	public MDRAction[] findByRuleGroupInstanceId_PrevAndNext(long actionId,
		long ruleGroupInstanceId, OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException {
		MDRAction mdrAction = findByPrimaryKey(actionId);

		Session session = null;

		try {
			session = openSession();

			MDRAction[] array = new MDRActionImpl[3];

			array[0] = getByRuleGroupInstanceId_PrevAndNext(session, mdrAction,
					ruleGroupInstanceId, orderByComparator, true);

			array[1] = mdrAction;

			array[2] = getByRuleGroupInstanceId_PrevAndNext(session, mdrAction,
					ruleGroupInstanceId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MDRAction getByRuleGroupInstanceId_PrevAndNext(Session session,
		MDRAction mdrAction, long ruleGroupInstanceId,
		OrderByComparator<MDRAction> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MDRACTION_WHERE);

		query.append(_FINDER_COLUMN_RULEGROUPINSTANCEID_RULEGROUPINSTANCEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(MDRActionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ruleGroupInstanceId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mdrAction);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MDRAction> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the m d r actions where ruleGroupInstanceId = &#63; from the database.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 */
	@Override
	public void removeByRuleGroupInstanceId(long ruleGroupInstanceId) {
		for (MDRAction mdrAction : findByRuleGroupInstanceId(
				ruleGroupInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(mdrAction);
		}
	}

	/**
	 * Returns the number of m d r actions where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @return the number of matching m d r actions
	 */
	@Override
	public int countByRuleGroupInstanceId(long ruleGroupInstanceId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_RULEGROUPINSTANCEID;

		Object[] finderArgs = new Object[] { ruleGroupInstanceId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MDRACTION_WHERE);

			query.append(_FINDER_COLUMN_RULEGROUPINSTANCEID_RULEGROUPINSTANCEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ruleGroupInstanceId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_RULEGROUPINSTANCEID_RULEGROUPINSTANCEID_2 =
		"mdrAction.ruleGroupInstanceId = ?";

	public MDRActionPersistenceImpl() {
		setModelClass(MDRAction.class);
	}

	/**
	 * Caches the m d r action in the entity cache if it is enabled.
	 *
	 * @param mdrAction the m d r action
	 */
	@Override
	public void cacheResult(MDRAction mdrAction) {
		entityCache.putResult(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionImpl.class, mdrAction.getPrimaryKey(), mdrAction);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { mdrAction.getUuid(), mdrAction.getGroupId() },
			mdrAction);

		mdrAction.resetOriginalValues();
	}

	/**
	 * Caches the m d r actions in the entity cache if it is enabled.
	 *
	 * @param mdrActions the m d r actions
	 */
	@Override
	public void cacheResult(List<MDRAction> mdrActions) {
		for (MDRAction mdrAction : mdrActions) {
			if (entityCache.getResult(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
						MDRActionImpl.class, mdrAction.getPrimaryKey()) == null) {
				cacheResult(mdrAction);
			}
			else {
				mdrAction.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all m d r actions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MDRActionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the m d r action.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MDRAction mdrAction) {
		entityCache.removeResult(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionImpl.class, mdrAction.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((MDRActionModelImpl)mdrAction);
	}

	@Override
	public void clearCache(List<MDRAction> mdrActions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MDRAction mdrAction : mdrActions) {
			entityCache.removeResult(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
				MDRActionImpl.class, mdrAction.getPrimaryKey());

			clearUniqueFindersCache((MDRActionModelImpl)mdrAction);
		}
	}

	protected void cacheUniqueFindersCache(
		MDRActionModelImpl mdrActionModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					mdrActionModelImpl.getUuid(),
					mdrActionModelImpl.getGroupId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				mdrActionModelImpl);
		}
		else {
			if ((mdrActionModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mdrActionModelImpl.getUuid(),
						mdrActionModelImpl.getGroupId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					mdrActionModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		MDRActionModelImpl mdrActionModelImpl) {
		Object[] args = new Object[] {
				mdrActionModelImpl.getUuid(), mdrActionModelImpl.getGroupId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((mdrActionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					mdrActionModelImpl.getOriginalUuid(),
					mdrActionModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new m d r action with the primary key. Does not add the m d r action to the database.
	 *
	 * @param actionId the primary key for the new m d r action
	 * @return the new m d r action
	 */
	@Override
	public MDRAction create(long actionId) {
		MDRAction mdrAction = new MDRActionImpl();

		mdrAction.setNew(true);
		mdrAction.setPrimaryKey(actionId);

		String uuid = PortalUUIDUtil.generate();

		mdrAction.setUuid(uuid);

		mdrAction.setCompanyId(companyProvider.getCompanyId());

		return mdrAction;
	}

	/**
	 * Removes the m d r action with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param actionId the primary key of the m d r action
	 * @return the m d r action that was removed
	 * @throws com.liferay.mobile.device.rules.NoSuchActionException if a m d r action with the primary key could not be found
	 */
	@Override
	public MDRAction remove(long actionId) throws NoSuchActionException {
		return remove((Serializable)actionId);
	}

	/**
	 * Removes the m d r action with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the m d r action
	 * @return the m d r action that was removed
	 * @throws com.liferay.mobile.device.rules.NoSuchActionException if a m d r action with the primary key could not be found
	 */
	@Override
	public MDRAction remove(Serializable primaryKey)
		throws NoSuchActionException {
		Session session = null;

		try {
			session = openSession();

			MDRAction mdrAction = (MDRAction)session.get(MDRActionImpl.class,
					primaryKey);

			if (mdrAction == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(mdrAction);
		}
		catch (NoSuchActionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected MDRAction removeImpl(MDRAction mdrAction) {
		mdrAction = toUnwrappedModel(mdrAction);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mdrAction)) {
				mdrAction = (MDRAction)session.get(MDRActionImpl.class,
						mdrAction.getPrimaryKeyObj());
			}

			if (mdrAction != null) {
				session.delete(mdrAction);
			}
		}
		catch (Exception e) {
			throw processException(e);
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
		mdrAction = toUnwrappedModel(mdrAction);

		boolean isNew = mdrAction.isNew();

		MDRActionModelImpl mdrActionModelImpl = (MDRActionModelImpl)mdrAction;

		if (Validator.isNull(mdrAction.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mdrAction.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (mdrAction.getCreateDate() == null)) {
			if (serviceContext == null) {
				mdrAction.setCreateDate(now);
			}
			else {
				mdrAction.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!mdrActionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				mdrAction.setModifiedDate(now);
			}
			else {
				mdrAction.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (mdrAction.isNew()) {
				session.save(mdrAction);

				mdrAction.setNew(false);
			}
			else {
				mdrAction = (MDRAction)session.merge(mdrAction);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !MDRActionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((mdrActionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mdrActionModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { mdrActionModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((mdrActionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mdrActionModelImpl.getOriginalUuid(),
						mdrActionModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						mdrActionModelImpl.getUuid(),
						mdrActionModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((mdrActionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RULEGROUPINSTANCEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mdrActionModelImpl.getOriginalRuleGroupInstanceId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RULEGROUPINSTANCEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RULEGROUPINSTANCEID,
					args);

				args = new Object[] { mdrActionModelImpl.getRuleGroupInstanceId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RULEGROUPINSTANCEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RULEGROUPINSTANCEID,
					args);
			}
		}

		entityCache.putResult(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
			MDRActionImpl.class, mdrAction.getPrimaryKey(), mdrAction, false);

		clearUniqueFindersCache(mdrActionModelImpl);
		cacheUniqueFindersCache(mdrActionModelImpl, isNew);

		mdrAction.resetOriginalValues();

		return mdrAction;
	}

	protected MDRAction toUnwrappedModel(MDRAction mdrAction) {
		if (mdrAction instanceof MDRActionImpl) {
			return mdrAction;
		}

		MDRActionImpl mdrActionImpl = new MDRActionImpl();

		mdrActionImpl.setNew(mdrAction.isNew());
		mdrActionImpl.setPrimaryKey(mdrAction.getPrimaryKey());

		mdrActionImpl.setUuid(mdrAction.getUuid());
		mdrActionImpl.setActionId(mdrAction.getActionId());
		mdrActionImpl.setGroupId(mdrAction.getGroupId());
		mdrActionImpl.setCompanyId(mdrAction.getCompanyId());
		mdrActionImpl.setUserId(mdrAction.getUserId());
		mdrActionImpl.setUserName(mdrAction.getUserName());
		mdrActionImpl.setCreateDate(mdrAction.getCreateDate());
		mdrActionImpl.setModifiedDate(mdrAction.getModifiedDate());
		mdrActionImpl.setClassNameId(mdrAction.getClassNameId());
		mdrActionImpl.setClassPK(mdrAction.getClassPK());
		mdrActionImpl.setRuleGroupInstanceId(mdrAction.getRuleGroupInstanceId());
		mdrActionImpl.setName(mdrAction.getName());
		mdrActionImpl.setDescription(mdrAction.getDescription());
		mdrActionImpl.setType(mdrAction.getType());
		mdrActionImpl.setTypeSettings(mdrAction.getTypeSettings());
		mdrActionImpl.setLastPublishDate(mdrAction.getLastPublishDate());

		return mdrActionImpl;
	}

	/**
	 * Returns the m d r action with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the m d r action
	 * @return the m d r action
	 * @throws com.liferay.mobile.device.rules.NoSuchActionException if a m d r action with the primary key could not be found
	 */
	@Override
	public MDRAction findByPrimaryKey(Serializable primaryKey)
		throws NoSuchActionException {
		MDRAction mdrAction = fetchByPrimaryKey(primaryKey);

		if (mdrAction == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchActionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return mdrAction;
	}

	/**
	 * Returns the m d r action with the primary key or throws a {@link com.liferay.mobile.device.rules.NoSuchActionException} if it could not be found.
	 *
	 * @param actionId the primary key of the m d r action
	 * @return the m d r action
	 * @throws com.liferay.mobile.device.rules.NoSuchActionException if a m d r action with the primary key could not be found
	 */
	@Override
	public MDRAction findByPrimaryKey(long actionId)
		throws NoSuchActionException {
		return findByPrimaryKey((Serializable)actionId);
	}

	/**
	 * Returns the m d r action with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the m d r action
	 * @return the m d r action, or <code>null</code> if a m d r action with the primary key could not be found
	 */
	@Override
	public MDRAction fetchByPrimaryKey(Serializable primaryKey) {
		MDRAction mdrAction = (MDRAction)entityCache.getResult(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
				MDRActionImpl.class, primaryKey);

		if (mdrAction == _nullMDRAction) {
			return null;
		}

		if (mdrAction == null) {
			Session session = null;

			try {
				session = openSession();

				mdrAction = (MDRAction)session.get(MDRActionImpl.class,
						primaryKey);

				if (mdrAction != null) {
					cacheResult(mdrAction);
				}
				else {
					entityCache.putResult(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
						MDRActionImpl.class, primaryKey, _nullMDRAction);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
					MDRActionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return mdrAction;
	}

	/**
	 * Returns the m d r action with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param actionId the primary key of the m d r action
	 * @return the m d r action, or <code>null</code> if a m d r action with the primary key could not be found
	 */
	@Override
	public MDRAction fetchByPrimaryKey(long actionId) {
		return fetchByPrimaryKey((Serializable)actionId);
	}

	@Override
	public Map<Serializable, MDRAction> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, MDRAction> map = new HashMap<Serializable, MDRAction>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			MDRAction mdrAction = fetchByPrimaryKey(primaryKey);

			if (mdrAction != null) {
				map.put(primaryKey, mdrAction);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			MDRAction mdrAction = (MDRAction)entityCache.getResult(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
					MDRActionImpl.class, primaryKey);

			if (mdrAction == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, mdrAction);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_MDRACTION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (MDRAction mdrAction : (List<MDRAction>)q.list()) {
				map.put(mdrAction.getPrimaryKeyObj(), mdrAction);

				cacheResult(mdrAction);

				uncachedPrimaryKeys.remove(mdrAction.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(MDRActionModelImpl.ENTITY_CACHE_ENABLED,
					MDRActionImpl.class, primaryKey, _nullMDRAction);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the m d r actions.
	 *
	 * @return the m d r actions
	 */
	@Override
	public List<MDRAction> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of m d r actions
	 * @param end the upper bound of the range of m d r actions (not inclusive)
	 * @return the range of m d r actions
	 */
	@Override
	public List<MDRAction> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of m d r actions
	 * @param end the upper bound of the range of m d r actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of m d r actions
	 */
	@Override
	public List<MDRAction> findAll(int start, int end,
		OrderByComparator<MDRAction> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the m d r actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of m d r actions
	 * @param end the upper bound of the range of m d r actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of m d r actions
	 */
	@Override
	public List<MDRAction> findAll(int start, int end,
		OrderByComparator<MDRAction> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<MDRAction> list = null;

		if (retrieveFromCache) {
			list = (List<MDRAction>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MDRACTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MDRACTION;

				if (pagination) {
					sql = sql.concat(MDRActionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<MDRAction>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MDRAction>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the m d r actions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MDRAction mdrAction : findAll()) {
			remove(mdrAction);
		}
	}

	/**
	 * Returns the number of m d r actions.
	 *
	 * @return the number of m d r actions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MDRACTION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
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
	protected Map<String, Integer> getTableColumnsMap() {
		return MDRActionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the m d r action persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(MDRActionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_MDRACTION = "SELECT mdrAction FROM MDRAction mdrAction";
	private static final String _SQL_SELECT_MDRACTION_WHERE_PKS_IN = "SELECT mdrAction FROM MDRAction mdrAction WHERE actionId IN (";
	private static final String _SQL_SELECT_MDRACTION_WHERE = "SELECT mdrAction FROM MDRAction mdrAction WHERE ";
	private static final String _SQL_COUNT_MDRACTION = "SELECT COUNT(mdrAction) FROM MDRAction mdrAction";
	private static final String _SQL_COUNT_MDRACTION_WHERE = "SELECT COUNT(mdrAction) FROM MDRAction mdrAction WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mdrAction.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MDRAction exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MDRAction exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(MDRActionPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "type"
			});
	private static final MDRAction _nullMDRAction = new MDRActionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<MDRAction> toCacheModel() {
				return _nullMDRActionCacheModel;
			}
		};

	private static final CacheModel<MDRAction> _nullMDRActionCacheModel = new CacheModel<MDRAction>() {
			@Override
			public MDRAction toEntityModel() {
				return _nullMDRAction;
			}
		};
}