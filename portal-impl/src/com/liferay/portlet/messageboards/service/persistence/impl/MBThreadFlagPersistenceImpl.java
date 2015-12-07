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

package com.liferay.portlet.messageboards.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
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

import com.liferay.portlet.messageboards.NoSuchThreadFlagException;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.model.impl.MBThreadFlagImpl;
import com.liferay.portlet.messageboards.model.impl.MBThreadFlagModelImpl;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFlagPersistence;

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
 * The persistence implementation for the message boards thread flag service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadFlagPersistence
 * @see com.liferay.portlet.messageboards.service.persistence.MBThreadFlagUtil
 * @generated
 */
@ProviderType
public class MBThreadFlagPersistenceImpl extends BasePersistenceImpl<MBThreadFlag>
	implements MBThreadFlagPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MBThreadFlagUtil} to access the message boards thread flag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MBThreadFlagImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			MBThreadFlagModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUuid(String uuid, int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUuid(String uuid, int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator,
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

		List<MBThreadFlag> list = null;

		if (retrieveFromCache) {
			list = (List<MBThreadFlag>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThreadFlag mbThreadFlag : list) {
					if (!Validator.equals(uuid, mbThreadFlag.getUuid())) {
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

			query.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

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
				query.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
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
					list = (List<MBThreadFlag>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThreadFlag>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread flag in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag
	 * @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag findByUuid_First(String uuid,
		OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {
		MBThreadFlag mbThreadFlag = fetchByUuid_First(uuid, orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadFlagException(msg.toString());
	}

	/**
	 * Returns the first message boards thread flag in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByUuid_First(String uuid,
		OrderByComparator<MBThreadFlag> orderByComparator) {
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
	public MBThreadFlag findByUuid_Last(String uuid,
		OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {
		MBThreadFlag mbThreadFlag = fetchByUuid_Last(uuid, orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadFlagException(msg.toString());
	}

	/**
	 * Returns the last message boards thread flag in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByUuid_Last(String uuid,
		OrderByComparator<MBThreadFlag> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<MBThreadFlag> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

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
	public MBThreadFlag[] findByUuid_PrevAndNext(long threadFlagId,
		String uuid, OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {
		MBThreadFlag mbThreadFlag = findByPrimaryKey(threadFlagId);

		Session session = null;

		try {
			session = openSession();

			MBThreadFlag[] array = new MBThreadFlagImpl[3];

			array[0] = getByUuid_PrevAndNext(session, mbThreadFlag, uuid,
					orderByComparator, true);

			array[1] = mbThreadFlag;

			array[2] = getByUuid_PrevAndNext(session, mbThreadFlag, uuid,
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

	protected MBThreadFlag getByUuid_PrevAndNext(Session session,
		MBThreadFlag mbThreadFlag, String uuid,
		OrderByComparator<MBThreadFlag> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

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
			query.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbThreadFlag);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThreadFlag> list = q.list();

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
		for (MBThreadFlag mbThreadFlag : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBTHREADFLAG_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "mbThreadFlag.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "mbThreadFlag.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(mbThreadFlag.uuid IS NULL OR mbThreadFlag.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			MBThreadFlagModelImpl.UUID_COLUMN_BITMASK |
			MBThreadFlagModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the message boards thread flag where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchThreadFlagException} if it could not be found.
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

			throw new NoSuchThreadFlagException(msg.toString());
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
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof MBThreadFlag) {
			MBThreadFlag mbThreadFlag = (MBThreadFlag)result;

			if (!Validator.equals(uuid, mbThreadFlag.getUuid()) ||
					(groupId != mbThreadFlag.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

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

				List<MBThreadFlag> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					MBThreadFlag mbThreadFlag = list.get(0);

					result = mbThreadFlag;

					cacheResult(mbThreadFlag);

					if ((mbThreadFlag.getUuid() == null) ||
							!mbThreadFlag.getUuid().equals(uuid) ||
							(mbThreadFlag.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, mbThreadFlag);
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBTHREADFLAG_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "mbThreadFlag.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "mbThreadFlag.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(mbThreadFlag.uuid IS NULL OR mbThreadFlag.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "mbThreadFlag.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			MBThreadFlagModelImpl.UUID_COLUMN_BITMASK |
			MBThreadFlagModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the message boards thread flags where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards thread flags where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @return the range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<MBThreadFlag> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<MBThreadFlag> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<MBThreadFlag> orderByComparator,
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

		List<MBThreadFlag> list = null;

		if (retrieveFromCache) {
			list = (List<MBThreadFlag>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThreadFlag mbThreadFlag : list) {
					if (!Validator.equals(uuid, mbThreadFlag.getUuid()) ||
							(companyId != mbThreadFlag.getCompanyId())) {
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

			query.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

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
				query.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
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
					list = (List<MBThreadFlag>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThreadFlag>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread flag in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag
	 * @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {
		MBThreadFlag mbThreadFlag = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadFlagException(msg.toString());
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
	public MBThreadFlag fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<MBThreadFlag> orderByComparator) {
		List<MBThreadFlag> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

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
	public MBThreadFlag findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {
		MBThreadFlag mbThreadFlag = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadFlagException(msg.toString());
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
	public MBThreadFlag fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<MBThreadFlag> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<MBThreadFlag> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

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
	public MBThreadFlag[] findByUuid_C_PrevAndNext(long threadFlagId,
		String uuid, long companyId,
		OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {
		MBThreadFlag mbThreadFlag = findByPrimaryKey(threadFlagId);

		Session session = null;

		try {
			session = openSession();

			MBThreadFlag[] array = new MBThreadFlagImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, mbThreadFlag, uuid,
					companyId, orderByComparator, true);

			array[1] = mbThreadFlag;

			array[2] = getByUuid_C_PrevAndNext(session, mbThreadFlag, uuid,
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

	protected MBThreadFlag getByUuid_C_PrevAndNext(Session session,
		MBThreadFlag mbThreadFlag, String uuid, long companyId,
		OrderByComparator<MBThreadFlag> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

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
			query.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbThreadFlag);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThreadFlag> list = q.list();

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
		for (MBThreadFlag mbThreadFlag : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBTHREADFLAG_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "mbThreadFlag.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "mbThreadFlag.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(mbThreadFlag.uuid IS NULL OR mbThreadFlag.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "mbThreadFlag.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			MBThreadFlagModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUserId(long userId, int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator) {
		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByUserId(long userId, int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<MBThreadFlag> list = null;

		if (retrieveFromCache) {
			list = (List<MBThreadFlag>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThreadFlag mbThreadFlag : list) {
					if ((userId != mbThreadFlag.getUserId())) {
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

			query.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<MBThreadFlag>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThreadFlag>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread flag in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag
	 * @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag findByUserId_First(long userId,
		OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {
		MBThreadFlag mbThreadFlag = fetchByUserId_First(userId,
				orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadFlagException(msg.toString());
	}

	/**
	 * Returns the first message boards thread flag in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByUserId_First(long userId,
		OrderByComparator<MBThreadFlag> orderByComparator) {
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
	public MBThreadFlag findByUserId_Last(long userId,
		OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {
		MBThreadFlag mbThreadFlag = fetchByUserId_Last(userId, orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadFlagException(msg.toString());
	}

	/**
	 * Returns the last message boards thread flag in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByUserId_Last(long userId,
		OrderByComparator<MBThreadFlag> orderByComparator) {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<MBThreadFlag> list = findByUserId(userId, count - 1, count,
				orderByComparator);

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
	public MBThreadFlag[] findByUserId_PrevAndNext(long threadFlagId,
		long userId, OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {
		MBThreadFlag mbThreadFlag = findByPrimaryKey(threadFlagId);

		Session session = null;

		try {
			session = openSession();

			MBThreadFlag[] array = new MBThreadFlagImpl[3];

			array[0] = getByUserId_PrevAndNext(session, mbThreadFlag, userId,
					orderByComparator, true);

			array[1] = mbThreadFlag;

			array[2] = getByUserId_PrevAndNext(session, mbThreadFlag, userId,
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

	protected MBThreadFlag getByUserId_PrevAndNext(Session session,
		MBThreadFlag mbThreadFlag, long userId,
		OrderByComparator<MBThreadFlag> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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
			query.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThreadFlag);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThreadFlag> list = q.list();

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
		for (MBThreadFlag mbThreadFlag : findByUserId(userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBTHREADFLAG_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "mbThreadFlag.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_THREADID = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByThreadId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADID =
		new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByThreadId",
			new String[] { Long.class.getName() },
			MBThreadFlagModelImpl.THREADID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_THREADID = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByThreadId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the message boards thread flags where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByThreadId(long threadId) {
		return findByThreadId(threadId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards thread flags where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @return the range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByThreadId(long threadId, int start, int end) {
		return findByThreadId(threadId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByThreadId(long threadId, int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator) {
		return findByThreadId(threadId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findByThreadId(long threadId, int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADID;
			finderArgs = new Object[] { threadId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_THREADID;
			finderArgs = new Object[] { threadId, start, end, orderByComparator };
		}

		List<MBThreadFlag> list = null;

		if (retrieveFromCache) {
			list = (List<MBThreadFlag>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThreadFlag mbThreadFlag : list) {
					if ((threadId != mbThreadFlag.getThreadId())) {
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

			query.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

			query.append(_FINDER_COLUMN_THREADID_THREADID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				if (!pagination) {
					list = (List<MBThreadFlag>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThreadFlag>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread flag in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag
	 * @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag findByThreadId_First(long threadId,
		OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {
		MBThreadFlag mbThreadFlag = fetchByThreadId_First(threadId,
				orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadFlagException(msg.toString());
	}

	/**
	 * Returns the first message boards thread flag in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByThreadId_First(long threadId,
		OrderByComparator<MBThreadFlag> orderByComparator) {
		List<MBThreadFlag> list = findByThreadId(threadId, 0, 1,
				orderByComparator);

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
	public MBThreadFlag findByThreadId_Last(long threadId,
		OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {
		MBThreadFlag mbThreadFlag = fetchByThreadId_Last(threadId,
				orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadFlagException(msg.toString());
	}

	/**
	 * Returns the last message boards thread flag in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByThreadId_Last(long threadId,
		OrderByComparator<MBThreadFlag> orderByComparator) {
		int count = countByThreadId(threadId);

		if (count == 0) {
			return null;
		}

		List<MBThreadFlag> list = findByThreadId(threadId, count - 1, count,
				orderByComparator);

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
	public MBThreadFlag[] findByThreadId_PrevAndNext(long threadFlagId,
		long threadId, OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException {
		MBThreadFlag mbThreadFlag = findByPrimaryKey(threadFlagId);

		Session session = null;

		try {
			session = openSession();

			MBThreadFlag[] array = new MBThreadFlagImpl[3];

			array[0] = getByThreadId_PrevAndNext(session, mbThreadFlag,
					threadId, orderByComparator, true);

			array[1] = mbThreadFlag;

			array[2] = getByThreadId_PrevAndNext(session, mbThreadFlag,
					threadId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThreadFlag getByThreadId_PrevAndNext(Session session,
		MBThreadFlag mbThreadFlag, long threadId,
		OrderByComparator<MBThreadFlag> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

		query.append(_FINDER_COLUMN_THREADID_THREADID_2);

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
			query.append(MBThreadFlagModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(threadId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThreadFlag);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThreadFlag> list = q.list();

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
		for (MBThreadFlag mbThreadFlag : findByThreadId(threadId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_THREADID;

		Object[] finderArgs = new Object[] { threadId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBTHREADFLAG_WHERE);

			query.append(_FINDER_COLUMN_THREADID_THREADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

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

	private static final String _FINDER_COLUMN_THREADID_THREADID_2 = "mbThreadFlag.threadId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_U_T = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_T",
			new String[] { Long.class.getName(), Long.class.getName() },
			MBThreadFlagModelImpl.USERID_COLUMN_BITMASK |
			MBThreadFlagModelImpl.THREADID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_T = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_T",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the message boards thread flag where userId = &#63; and threadId = &#63; or throws a {@link NoSuchThreadFlagException} if it could not be found.
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
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", threadId=");
			msg.append(threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchThreadFlagException(msg.toString());
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
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public MBThreadFlag fetchByU_T(long userId, long threadId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { userId, threadId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_U_T,
					finderArgs, this);
		}

		if (result instanceof MBThreadFlag) {
			MBThreadFlag mbThreadFlag = (MBThreadFlag)result;

			if ((userId != mbThreadFlag.getUserId()) ||
					(threadId != mbThreadFlag.getThreadId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

			query.append(_FINDER_COLUMN_U_T_USERID_2);

			query.append(_FINDER_COLUMN_U_T_THREADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(threadId);

				List<MBThreadFlag> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_U_T, finderArgs,
						list);
				}
				else {
					MBThreadFlag mbThreadFlag = list.get(0);

					result = mbThreadFlag;

					cacheResult(mbThreadFlag);

					if ((mbThreadFlag.getUserId() != userId) ||
							(mbThreadFlag.getThreadId() != threadId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_U_T,
							finderArgs, mbThreadFlag);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_U_T, finderArgs);

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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_T;

		Object[] finderArgs = new Object[] { userId, threadId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBTHREADFLAG_WHERE);

			query.append(_FINDER_COLUMN_U_T_USERID_2);

			query.append(_FINDER_COLUMN_U_T_THREADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(threadId);

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

	private static final String _FINDER_COLUMN_U_T_USERID_2 = "mbThreadFlag.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_T_THREADID_2 = "mbThreadFlag.threadId = ?";

	public MBThreadFlagPersistenceImpl() {
		setModelClass(MBThreadFlag.class);
	}

	/**
	 * Caches the message boards thread flag in the entity cache if it is enabled.
	 *
	 * @param mbThreadFlag the message boards thread flag
	 */
	@Override
	public void cacheResult(MBThreadFlag mbThreadFlag) {
		entityCache.putResult(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKey(), mbThreadFlag);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { mbThreadFlag.getUuid(), mbThreadFlag.getGroupId() },
			mbThreadFlag);

		finderCache.putResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] { mbThreadFlag.getUserId(), mbThreadFlag.getThreadId() },
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
			if (entityCache.getResult(
						MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
						MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKey()) == null) {
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
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
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
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MBThreadFlag mbThreadFlag) {
		entityCache.removeResult(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((MBThreadFlagModelImpl)mbThreadFlag);
	}

	@Override
	public void clearCache(List<MBThreadFlag> mbThreadFlags) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MBThreadFlag mbThreadFlag : mbThreadFlags) {
			entityCache.removeResult(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
				MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKey());

			clearUniqueFindersCache((MBThreadFlagModelImpl)mbThreadFlag);
		}
	}

	protected void cacheUniqueFindersCache(
		MBThreadFlagModelImpl mbThreadFlagModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					mbThreadFlagModelImpl.getUuid(),
					mbThreadFlagModelImpl.getGroupId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				mbThreadFlagModelImpl);

			args = new Object[] {
					mbThreadFlagModelImpl.getUserId(),
					mbThreadFlagModelImpl.getThreadId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_U_T, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_U_T, args,
				mbThreadFlagModelImpl);
		}
		else {
			if ((mbThreadFlagModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadFlagModelImpl.getUuid(),
						mbThreadFlagModelImpl.getGroupId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					mbThreadFlagModelImpl);
			}

			if ((mbThreadFlagModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_U_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadFlagModelImpl.getUserId(),
						mbThreadFlagModelImpl.getThreadId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_U_T, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_U_T, args,
					mbThreadFlagModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		MBThreadFlagModelImpl mbThreadFlagModelImpl) {
		Object[] args = new Object[] {
				mbThreadFlagModelImpl.getUuid(),
				mbThreadFlagModelImpl.getGroupId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((mbThreadFlagModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					mbThreadFlagModelImpl.getOriginalUuid(),
					mbThreadFlagModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		args = new Object[] {
				mbThreadFlagModelImpl.getUserId(),
				mbThreadFlagModelImpl.getThreadId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_U_T, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_U_T, args);

		if ((mbThreadFlagModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_U_T.getColumnBitmask()) != 0) {
			args = new Object[] {
					mbThreadFlagModelImpl.getOriginalUserId(),
					mbThreadFlagModelImpl.getOriginalThreadId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_U_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_U_T, args);
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

			MBThreadFlag mbThreadFlag = (MBThreadFlag)session.get(MBThreadFlagImpl.class,
					primaryKey);

			if (mbThreadFlag == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchThreadFlagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(mbThreadFlag);
		}
		catch (NoSuchThreadFlagException nsee) {
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
	protected MBThreadFlag removeImpl(MBThreadFlag mbThreadFlag) {
		mbThreadFlag = toUnwrappedModel(mbThreadFlag);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mbThreadFlag)) {
				mbThreadFlag = (MBThreadFlag)session.get(MBThreadFlagImpl.class,
						mbThreadFlag.getPrimaryKeyObj());
			}

			if (mbThreadFlag != null) {
				session.delete(mbThreadFlag);
			}
		}
		catch (Exception e) {
			throw processException(e);
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
		mbThreadFlag = toUnwrappedModel(mbThreadFlag);

		boolean isNew = mbThreadFlag.isNew();

		MBThreadFlagModelImpl mbThreadFlagModelImpl = (MBThreadFlagModelImpl)mbThreadFlag;

		if (Validator.isNull(mbThreadFlag.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mbThreadFlag.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

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
				mbThreadFlag.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (mbThreadFlag.isNew()) {
				session.save(mbThreadFlag);

				mbThreadFlag.setNew(false);
			}
			else {
				mbThreadFlag = (MBThreadFlag)session.merge(mbThreadFlag);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !MBThreadFlagModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((mbThreadFlagModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadFlagModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { mbThreadFlagModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((mbThreadFlagModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadFlagModelImpl.getOriginalUuid(),
						mbThreadFlagModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						mbThreadFlagModelImpl.getUuid(),
						mbThreadFlagModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((mbThreadFlagModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadFlagModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { mbThreadFlagModelImpl.getUserId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((mbThreadFlagModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadFlagModelImpl.getOriginalThreadId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_THREADID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADID,
					args);

				args = new Object[] { mbThreadFlagModelImpl.getThreadId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_THREADID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADID,
					args);
			}
		}

		entityCache.putResult(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKey(), mbThreadFlag,
			false);

		clearUniqueFindersCache(mbThreadFlagModelImpl);
		cacheUniqueFindersCache(mbThreadFlagModelImpl, isNew);

		mbThreadFlag.resetOriginalValues();

		return mbThreadFlag;
	}

	protected MBThreadFlag toUnwrappedModel(MBThreadFlag mbThreadFlag) {
		if (mbThreadFlag instanceof MBThreadFlagImpl) {
			return mbThreadFlag;
		}

		MBThreadFlagImpl mbThreadFlagImpl = new MBThreadFlagImpl();

		mbThreadFlagImpl.setNew(mbThreadFlag.isNew());
		mbThreadFlagImpl.setPrimaryKey(mbThreadFlag.getPrimaryKey());

		mbThreadFlagImpl.setUuid(mbThreadFlag.getUuid());
		mbThreadFlagImpl.setThreadFlagId(mbThreadFlag.getThreadFlagId());
		mbThreadFlagImpl.setGroupId(mbThreadFlag.getGroupId());
		mbThreadFlagImpl.setCompanyId(mbThreadFlag.getCompanyId());
		mbThreadFlagImpl.setUserId(mbThreadFlag.getUserId());
		mbThreadFlagImpl.setUserName(mbThreadFlag.getUserName());
		mbThreadFlagImpl.setCreateDate(mbThreadFlag.getCreateDate());
		mbThreadFlagImpl.setModifiedDate(mbThreadFlag.getModifiedDate());
		mbThreadFlagImpl.setThreadId(mbThreadFlag.getThreadId());
		mbThreadFlagImpl.setLastPublishDate(mbThreadFlag.getLastPublishDate());

		return mbThreadFlagImpl;
	}

	/**
	 * Returns the message boards thread flag with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
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
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchThreadFlagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return mbThreadFlag;
	}

	/**
	 * Returns the message boards thread flag with the primary key or throws a {@link NoSuchThreadFlagException} if it could not be found.
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
		MBThreadFlag mbThreadFlag = (MBThreadFlag)entityCache.getResult(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
				MBThreadFlagImpl.class, primaryKey);

		if (mbThreadFlag == _nullMBThreadFlag) {
			return null;
		}

		if (mbThreadFlag == null) {
			Session session = null;

			try {
				session = openSession();

				mbThreadFlag = (MBThreadFlag)session.get(MBThreadFlagImpl.class,
						primaryKey);

				if (mbThreadFlag != null) {
					cacheResult(mbThreadFlag);
				}
				else {
					entityCache.putResult(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
						MBThreadFlagImpl.class, primaryKey, _nullMBThreadFlag);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
					MBThreadFlagImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
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
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, MBThreadFlag> map = new HashMap<Serializable, MBThreadFlag>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			MBThreadFlag mbThreadFlag = fetchByPrimaryKey(primaryKey);

			if (mbThreadFlag != null) {
				map.put(primaryKey, mbThreadFlag);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			MBThreadFlag mbThreadFlag = (MBThreadFlag)entityCache.getResult(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
					MBThreadFlagImpl.class, primaryKey);

			if (mbThreadFlag == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, mbThreadFlag);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_MBTHREADFLAG_WHERE_PKS_IN);

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

			for (MBThreadFlag mbThreadFlag : (List<MBThreadFlag>)q.list()) {
				map.put(mbThreadFlag.getPrimaryKeyObj(), mbThreadFlag);

				cacheResult(mbThreadFlag);

				uncachedPrimaryKeys.remove(mbThreadFlag.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
					MBThreadFlagImpl.class, primaryKey, _nullMBThreadFlag);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findAll(int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of message boards thread flags
	 */
	@Override
	public List<MBThreadFlag> findAll(int start, int end,
		OrderByComparator<MBThreadFlag> orderByComparator,
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

		List<MBThreadFlag> list = null;

		if (retrieveFromCache) {
			list = (List<MBThreadFlag>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MBTHREADFLAG);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MBTHREADFLAG;

				if (pagination) {
					sql = sql.concat(MBThreadFlagModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<MBThreadFlag>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThreadFlag>)QueryUtil.list(q, getDialect(),
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
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MBTHREADFLAG);

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
		return MBThreadFlagModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the message boards thread flag persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(MBThreadFlagImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_MBTHREADFLAG = "SELECT mbThreadFlag FROM MBThreadFlag mbThreadFlag";
	private static final String _SQL_SELECT_MBTHREADFLAG_WHERE_PKS_IN = "SELECT mbThreadFlag FROM MBThreadFlag mbThreadFlag WHERE threadFlagId IN (";
	private static final String _SQL_SELECT_MBTHREADFLAG_WHERE = "SELECT mbThreadFlag FROM MBThreadFlag mbThreadFlag WHERE ";
	private static final String _SQL_COUNT_MBTHREADFLAG = "SELECT COUNT(mbThreadFlag) FROM MBThreadFlag mbThreadFlag";
	private static final String _SQL_COUNT_MBTHREADFLAG_WHERE = "SELECT COUNT(mbThreadFlag) FROM MBThreadFlag mbThreadFlag WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mbThreadFlag.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MBThreadFlag exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MBThreadFlag exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(MBThreadFlagPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static final MBThreadFlag _nullMBThreadFlag = new MBThreadFlagImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<MBThreadFlag> toCacheModel() {
				return _nullMBThreadFlagCacheModel;
			}
		};

	private static final CacheModel<MBThreadFlag> _nullMBThreadFlagCacheModel = new CacheModel<MBThreadFlag>() {
			@Override
			public MBThreadFlag toEntityModel() {
				return _nullMBThreadFlag;
			}
		};
}