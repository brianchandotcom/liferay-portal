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

package com.liferay.dynamic.data.lists.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.exception.NoSuchRecordException;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordImpl;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordPersistence;

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
 * The persistence implementation for the d d l record service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordPersistence
 * @see com.liferay.dynamic.data.lists.service.persistence.DDLRecordUtil
 * @generated
 */
@ProviderType
public class DDLRecordPersistenceImpl extends BasePersistenceImpl<DDLRecord>
	implements DDLRecordPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDLRecordUtil} to access the d d l record persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDLRecordImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DDLRecordModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the d d l records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching d d l records
	 */
	@Override
	public List<DDLRecord> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d l records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @return the range of matching d d l records
	 */
	@Override
	public List<DDLRecord> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d l records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d l records
	 */
	@Override
	public List<DDLRecord> findByUuid(String uuid, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d l records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d l records
	 */
	@Override
	public List<DDLRecord> findByUuid(String uuid, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
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

		List<DDLRecord> list = null;

		if (retrieveFromCache) {
			list = (List<DDLRecord>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecord ddlRecord : list) {
					if (!Validator.equals(uuid, ddlRecord.getUuid())) {
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

			query.append(_SQL_SELECT_DDLRECORD_WHERE);

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
				query.append(DDLRecordModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDLRecord>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDLRecord>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d l record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord findByUuid_First(String uuid,
		OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = fetchByUuid_First(uuid, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the first d d l record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord fetchByUuid_First(String uuid,
		OrderByComparator<DDLRecord> orderByComparator) {
		List<DDLRecord> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d l record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord findByUuid_Last(String uuid,
		OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = fetchByUuid_Last(uuid, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the last d d l record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord fetchByUuid_Last(String uuid,
		OrderByComparator<DDLRecord> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DDLRecord> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d l records before and after the current d d l record in the ordered set where uuid = &#63;.
	 *
	 * @param recordId the primary key of the current d d l record
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a d d l record with the primary key could not be found
	 */
	@Override
	public DDLRecord[] findByUuid_PrevAndNext(long recordId, String uuid,
		OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ddlRecord, uuid,
					orderByComparator, true);

			array[1] = ddlRecord;

			array[2] = getByUuid_PrevAndNext(session, ddlRecord, uuid,
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

	protected DDLRecord getByUuid_PrevAndNext(Session session,
		DDLRecord ddlRecord, String uuid,
		OrderByComparator<DDLRecord> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDLRECORD_WHERE);

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
			query.append(DDLRecordModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(ddlRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d l records where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DDLRecord ddlRecord : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of d d l records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching d d l records
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDLRECORD_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ddlRecord.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ddlRecord.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ddlRecord.uuid IS NULL OR ddlRecord.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DDLRecordModelImpl.UUID_COLUMN_BITMASK |
			DDLRecordModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the d d l record where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.dynamic.data.lists.NoSuchRecordException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord findByUUID_G(String uuid, long groupId)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = fetchByUUID_G(uuid, groupId);

		if (ddlRecord == null) {
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

			throw new NoSuchRecordException(msg.toString());
		}

		return ddlRecord;
	}

	/**
	 * Returns the d d l record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the d d l record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DDLRecord) {
			DDLRecord ddlRecord = (DDLRecord)result;

			if (!Validator.equals(uuid, ddlRecord.getUuid()) ||
					(groupId != ddlRecord.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DDLRECORD_WHERE);

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

				List<DDLRecord> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					DDLRecord ddlRecord = list.get(0);

					result = ddlRecord;

					cacheResult(ddlRecord);

					if ((ddlRecord.getUuid() == null) ||
							!ddlRecord.getUuid().equals(uuid) ||
							(ddlRecord.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, ddlRecord);
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
			return (DDLRecord)result;
		}
	}

	/**
	 * Removes the d d l record where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the d d l record that was removed
	 */
	@Override
	public DDLRecord removeByUUID_G(String uuid, long groupId)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = findByUUID_G(uuid, groupId);

		return remove(ddlRecord);
	}

	/**
	 * Returns the number of d d l records where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching d d l records
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDLRECORD_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "ddlRecord.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "ddlRecord.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(ddlRecord.uuid IS NULL OR ddlRecord.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "ddlRecord.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DDLRecordModelImpl.UUID_COLUMN_BITMASK |
			DDLRecordModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the d d l records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching d d l records
	 */
	@Override
	public List<DDLRecord> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d l records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @return the range of matching d d l records
	 */
	@Override
	public List<DDLRecord> findByUuid_C(String uuid, long companyId, int start,
		int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d l records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d l records
	 */
	@Override
	public List<DDLRecord> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<DDLRecord> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d l records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d l records
	 */
	@Override
	public List<DDLRecord> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<DDLRecord> orderByComparator,
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

		List<DDLRecord> list = null;

		if (retrieveFromCache) {
			list = (List<DDLRecord>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecord ddlRecord : list) {
					if (!Validator.equals(uuid, ddlRecord.getUuid()) ||
							(companyId != ddlRecord.getCompanyId())) {
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

			query.append(_SQL_SELECT_DDLRECORD_WHERE);

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
				query.append(DDLRecordModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDLRecord>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDLRecord>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the first d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DDLRecord> orderByComparator) {
		List<DDLRecord> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the last d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DDLRecord> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DDLRecord> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d l records before and after the current d d l record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param recordId the primary key of the current d d l record
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a d d l record with the primary key could not be found
	 */
	@Override
	public DDLRecord[] findByUuid_C_PrevAndNext(long recordId, String uuid,
		long companyId, OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, ddlRecord, uuid,
					companyId, orderByComparator, true);

			array[1] = ddlRecord;

			array[2] = getByUuid_C_PrevAndNext(session, ddlRecord, uuid,
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

	protected DDLRecord getByUuid_C_PrevAndNext(Session session,
		DDLRecord ddlRecord, String uuid, long companyId,
		OrderByComparator<DDLRecord> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDLRECORD_WHERE);

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
			query.append(DDLRecordModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(ddlRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d l records where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DDLRecord ddlRecord : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of d d l records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching d d l records
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDLRECORD_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "ddlRecord.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "ddlRecord.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(ddlRecord.uuid IS NULL OR ddlRecord.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "ddlRecord.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			DDLRecordModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d l records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching d d l records
	 */
	@Override
	public List<DDLRecord> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the d d l records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @return the range of matching d d l records
	 */
	@Override
	public List<DDLRecord> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d l records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d l records
	 */
	@Override
	public List<DDLRecord> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d l records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d l records
	 */
	@Override
	public List<DDLRecord> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<DDLRecord> list = null;

		if (retrieveFromCache) {
			list = (List<DDLRecord>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecord ddlRecord : list) {
					if ((companyId != ddlRecord.getCompanyId())) {
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

			query.append(_SQL_SELECT_DDLRECORD_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDLRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<DDLRecord>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDLRecord>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d l record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord findByCompanyId_First(long companyId,
		OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the first d d l record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord fetchByCompanyId_First(long companyId,
		OrderByComparator<DDLRecord> orderByComparator) {
		List<DDLRecord> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d l record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord findByCompanyId_Last(long companyId,
		OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = fetchByCompanyId_Last(companyId, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the last d d l record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord fetchByCompanyId_Last(long companyId,
		OrderByComparator<DDLRecord> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<DDLRecord> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d l records before and after the current d d l record in the ordered set where companyId = &#63;.
	 *
	 * @param recordId the primary key of the current d d l record
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a d d l record with the primary key could not be found
	 */
	@Override
	public DDLRecord[] findByCompanyId_PrevAndNext(long recordId,
		long companyId, OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, ddlRecord,
					companyId, orderByComparator, true);

			array[1] = ddlRecord;

			array[2] = getByCompanyId_PrevAndNext(session, ddlRecord,
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

	protected DDLRecord getByCompanyId_PrevAndNext(Session session,
		DDLRecord ddlRecord, long companyId,
		OrderByComparator<DDLRecord> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDLRECORD_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(DDLRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddlRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d l records where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (DDLRecord ddlRecord : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of d d l records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching d d l records
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDLRECORD_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "ddlRecord.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_RECORDSETID =
		new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRecordSetId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDSETID =
		new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRecordSetId",
			new String[] { Long.class.getName() },
			DDLRecordModelImpl.RECORDSETID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_RECORDSETID = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRecordSetId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d l records where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @return the matching d d l records
	 */
	@Override
	public List<DDLRecord> findByRecordSetId(long recordSetId) {
		return findByRecordSetId(recordSetId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d l records where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @return the range of matching d d l records
	 */
	@Override
	public List<DDLRecord> findByRecordSetId(long recordSetId, int start,
		int end) {
		return findByRecordSetId(recordSetId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d l records where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d l records
	 */
	@Override
	public List<DDLRecord> findByRecordSetId(long recordSetId, int start,
		int end, OrderByComparator<DDLRecord> orderByComparator) {
		return findByRecordSetId(recordSetId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the d d l records where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d l records
	 */
	@Override
	public List<DDLRecord> findByRecordSetId(long recordSetId, int start,
		int end, OrderByComparator<DDLRecord> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDSETID;
			finderArgs = new Object[] { recordSetId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_RECORDSETID;
			finderArgs = new Object[] { recordSetId, start, end, orderByComparator };
		}

		List<DDLRecord> list = null;

		if (retrieveFromCache) {
			list = (List<DDLRecord>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecord ddlRecord : list) {
					if ((recordSetId != ddlRecord.getRecordSetId())) {
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

			query.append(_SQL_SELECT_DDLRECORD_WHERE);

			query.append(_FINDER_COLUMN_RECORDSETID_RECORDSETID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDLRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordSetId);

				if (!pagination) {
					list = (List<DDLRecord>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDLRecord>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d l record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord findByRecordSetId_First(long recordSetId,
		OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = fetchByRecordSetId_First(recordSetId,
				orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordSetId=");
		msg.append(recordSetId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the first d d l record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord fetchByRecordSetId_First(long recordSetId,
		OrderByComparator<DDLRecord> orderByComparator) {
		List<DDLRecord> list = findByRecordSetId(recordSetId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d l record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord findByRecordSetId_Last(long recordSetId,
		OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = fetchByRecordSetId_Last(recordSetId,
				orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordSetId=");
		msg.append(recordSetId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the last d d l record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord fetchByRecordSetId_Last(long recordSetId,
		OrderByComparator<DDLRecord> orderByComparator) {
		int count = countByRecordSetId(recordSetId);

		if (count == 0) {
			return null;
		}

		List<DDLRecord> list = findByRecordSetId(recordSetId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d l records before and after the current d d l record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordId the primary key of the current d d l record
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a d d l record with the primary key could not be found
	 */
	@Override
	public DDLRecord[] findByRecordSetId_PrevAndNext(long recordId,
		long recordSetId, OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByRecordSetId_PrevAndNext(session, ddlRecord,
					recordSetId, orderByComparator, true);

			array[1] = ddlRecord;

			array[2] = getByRecordSetId_PrevAndNext(session, ddlRecord,
					recordSetId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDLRecord getByRecordSetId_PrevAndNext(Session session,
		DDLRecord ddlRecord, long recordSetId,
		OrderByComparator<DDLRecord> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDLRECORD_WHERE);

		query.append(_FINDER_COLUMN_RECORDSETID_RECORDSETID_2);

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
			query.append(DDLRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(recordSetId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddlRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d l records where recordSetId = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 */
	@Override
	public void removeByRecordSetId(long recordSetId) {
		for (DDLRecord ddlRecord : findByRecordSetId(recordSetId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of d d l records where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @return the number of matching d d l records
	 */
	@Override
	public int countByRecordSetId(long recordSetId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_RECORDSETID;

		Object[] finderArgs = new Object[] { recordSetId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDLRECORD_WHERE);

			query.append(_FINDER_COLUMN_RECORDSETID_RECORDSETID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordSetId);

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

	private static final String _FINDER_COLUMN_RECORDSETID_RECORDSETID_2 = "ddlRecord.recordSetId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_R_U = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_U = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, DDLRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			DDLRecordModelImpl.RECORDSETID_COLUMN_BITMASK |
			DDLRecordModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_U = new FinderPath(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_U",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the d d l records where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @return the matching d d l records
	 */
	@Override
	public List<DDLRecord> findByR_U(long recordSetId, long userId) {
		return findByR_U(recordSetId, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d l records where recordSetId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @return the range of matching d d l records
	 */
	@Override
	public List<DDLRecord> findByR_U(long recordSetId, long userId, int start,
		int end) {
		return findByR_U(recordSetId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d l records where recordSetId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d l records
	 */
	@Override
	public List<DDLRecord> findByR_U(long recordSetId, long userId, int start,
		int end, OrderByComparator<DDLRecord> orderByComparator) {
		return findByR_U(recordSetId, userId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the d d l records where recordSetId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d l records
	 */
	@Override
	public List<DDLRecord> findByR_U(long recordSetId, long userId, int start,
		int end, OrderByComparator<DDLRecord> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_U;
			finderArgs = new Object[] { recordSetId, userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_R_U;
			finderArgs = new Object[] {
					recordSetId, userId,
					
					start, end, orderByComparator
				};
		}

		List<DDLRecord> list = null;

		if (retrieveFromCache) {
			list = (List<DDLRecord>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecord ddlRecord : list) {
					if ((recordSetId != ddlRecord.getRecordSetId()) ||
							(userId != ddlRecord.getUserId())) {
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

			query.append(_SQL_SELECT_DDLRECORD_WHERE);

			query.append(_FINDER_COLUMN_R_U_RECORDSETID_2);

			query.append(_FINDER_COLUMN_R_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDLRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordSetId);

				qPos.add(userId);

				if (!pagination) {
					list = (List<DDLRecord>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDLRecord>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord findByR_U_First(long recordSetId, long userId,
		OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = fetchByR_U_First(recordSetId, userId,
				orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordSetId=");
		msg.append(recordSetId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the first d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord fetchByR_U_First(long recordSetId, long userId,
		OrderByComparator<DDLRecord> orderByComparator) {
		List<DDLRecord> list = findByR_U(recordSetId, userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord findByR_U_Last(long recordSetId, long userId,
		OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = fetchByR_U_Last(recordSetId, userId,
				orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordSetId=");
		msg.append(recordSetId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordException(msg.toString());
	}

	/**
	 * Returns the last d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d l record, or <code>null</code> if a matching d d l record could not be found
	 */
	@Override
	public DDLRecord fetchByR_U_Last(long recordSetId, long userId,
		OrderByComparator<DDLRecord> orderByComparator) {
		int count = countByR_U(recordSetId, userId);

		if (count == 0) {
			return null;
		}

		List<DDLRecord> list = findByR_U(recordSetId, userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d l records before and after the current d d l record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordId the primary key of the current d d l record
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a d d l record with the primary key could not be found
	 */
	@Override
	public DDLRecord[] findByR_U_PrevAndNext(long recordId, long recordSetId,
		long userId, OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByR_U_PrevAndNext(session, ddlRecord, recordSetId,
					userId, orderByComparator, true);

			array[1] = ddlRecord;

			array[2] = getByR_U_PrevAndNext(session, ddlRecord, recordSetId,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDLRecord getByR_U_PrevAndNext(Session session,
		DDLRecord ddlRecord, long recordSetId, long userId,
		OrderByComparator<DDLRecord> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDLRECORD_WHERE);

		query.append(_FINDER_COLUMN_R_U_RECORDSETID_2);

		query.append(_FINDER_COLUMN_R_U_USERID_2);

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
			query.append(DDLRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(recordSetId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddlRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d l records where recordSetId = &#63; and userId = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByR_U(long recordSetId, long userId) {
		for (DDLRecord ddlRecord : findByR_U(recordSetId, userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of d d l records where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @return the number of matching d d l records
	 */
	@Override
	public int countByR_U(long recordSetId, long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_R_U;

		Object[] finderArgs = new Object[] { recordSetId, userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDLRECORD_WHERE);

			query.append(_FINDER_COLUMN_R_U_RECORDSETID_2);

			query.append(_FINDER_COLUMN_R_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordSetId);

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

	private static final String _FINDER_COLUMN_R_U_RECORDSETID_2 = "ddlRecord.recordSetId = ? AND ";
	private static final String _FINDER_COLUMN_R_U_USERID_2 = "ddlRecord.userId = ?";

	public DDLRecordPersistenceImpl() {
		setModelClass(DDLRecord.class);
	}

	/**
	 * Caches the d d l record in the entity cache if it is enabled.
	 *
	 * @param ddlRecord the d d l record
	 */
	@Override
	public void cacheResult(DDLRecord ddlRecord) {
		entityCache.putResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordImpl.class, ddlRecord.getPrimaryKey(), ddlRecord);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { ddlRecord.getUuid(), ddlRecord.getGroupId() },
			ddlRecord);

		ddlRecord.resetOriginalValues();
	}

	/**
	 * Caches the d d l records in the entity cache if it is enabled.
	 *
	 * @param ddlRecords the d d l records
	 */
	@Override
	public void cacheResult(List<DDLRecord> ddlRecords) {
		for (DDLRecord ddlRecord : ddlRecords) {
			if (entityCache.getResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
						DDLRecordImpl.class, ddlRecord.getPrimaryKey()) == null) {
				cacheResult(ddlRecord);
			}
			else {
				ddlRecord.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d d l records.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDLRecordImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d d l record.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDLRecord ddlRecord) {
		entityCache.removeResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordImpl.class, ddlRecord.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DDLRecordModelImpl)ddlRecord);
	}

	@Override
	public void clearCache(List<DDLRecord> ddlRecords) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDLRecord ddlRecord : ddlRecords) {
			entityCache.removeResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
				DDLRecordImpl.class, ddlRecord.getPrimaryKey());

			clearUniqueFindersCache((DDLRecordModelImpl)ddlRecord);
		}
	}

	protected void cacheUniqueFindersCache(
		DDLRecordModelImpl ddlRecordModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					ddlRecordModelImpl.getUuid(),
					ddlRecordModelImpl.getGroupId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				ddlRecordModelImpl);
		}
		else {
			if ((ddlRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddlRecordModelImpl.getUuid(),
						ddlRecordModelImpl.getGroupId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					ddlRecordModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		DDLRecordModelImpl ddlRecordModelImpl) {
		Object[] args = new Object[] {
				ddlRecordModelImpl.getUuid(), ddlRecordModelImpl.getGroupId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((ddlRecordModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					ddlRecordModelImpl.getOriginalUuid(),
					ddlRecordModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new d d l record with the primary key. Does not add the d d l record to the database.
	 *
	 * @param recordId the primary key for the new d d l record
	 * @return the new d d l record
	 */
	@Override
	public DDLRecord create(long recordId) {
		DDLRecord ddlRecord = new DDLRecordImpl();

		ddlRecord.setNew(true);
		ddlRecord.setPrimaryKey(recordId);

		String uuid = PortalUUIDUtil.generate();

		ddlRecord.setUuid(uuid);

		ddlRecord.setCompanyId(companyProvider.getCompanyId());

		return ddlRecord;
	}

	/**
	 * Removes the d d l record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordId the primary key of the d d l record
	 * @return the d d l record that was removed
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a d d l record with the primary key could not be found
	 */
	@Override
	public DDLRecord remove(long recordId) throws NoSuchRecordException {
		return remove((Serializable)recordId);
	}

	/**
	 * Removes the d d l record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d l record
	 * @return the d d l record that was removed
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a d d l record with the primary key could not be found
	 */
	@Override
	public DDLRecord remove(Serializable primaryKey)
		throws NoSuchRecordException {
		Session session = null;

		try {
			session = openSession();

			DDLRecord ddlRecord = (DDLRecord)session.get(DDLRecordImpl.class,
					primaryKey);

			if (ddlRecord == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRecordException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddlRecord);
		}
		catch (NoSuchRecordException nsee) {
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
	protected DDLRecord removeImpl(DDLRecord ddlRecord) {
		ddlRecord = toUnwrappedModel(ddlRecord);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddlRecord)) {
				ddlRecord = (DDLRecord)session.get(DDLRecordImpl.class,
						ddlRecord.getPrimaryKeyObj());
			}

			if (ddlRecord != null) {
				session.delete(ddlRecord);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddlRecord != null) {
			clearCache(ddlRecord);
		}

		return ddlRecord;
	}

	@Override
	public DDLRecord updateImpl(DDLRecord ddlRecord) {
		ddlRecord = toUnwrappedModel(ddlRecord);

		boolean isNew = ddlRecord.isNew();

		DDLRecordModelImpl ddlRecordModelImpl = (DDLRecordModelImpl)ddlRecord;

		if (Validator.isNull(ddlRecord.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddlRecord.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ddlRecord.getCreateDate() == null)) {
			if (serviceContext == null) {
				ddlRecord.setCreateDate(now);
			}
			else {
				ddlRecord.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!ddlRecordModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ddlRecord.setModifiedDate(now);
			}
			else {
				ddlRecord.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ddlRecord.isNew()) {
				session.save(ddlRecord);

				ddlRecord.setNew(false);
			}
			else {
				ddlRecord = (DDLRecord)session.merge(ddlRecord);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DDLRecordModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ddlRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddlRecordModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { ddlRecordModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((ddlRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddlRecordModelImpl.getOriginalUuid(),
						ddlRecordModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						ddlRecordModelImpl.getUuid(),
						ddlRecordModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((ddlRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddlRecordModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { ddlRecordModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((ddlRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDSETID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddlRecordModelImpl.getOriginalRecordSetId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RECORDSETID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDSETID,
					args);

				args = new Object[] { ddlRecordModelImpl.getRecordSetId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RECORDSETID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDSETID,
					args);
			}

			if ((ddlRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddlRecordModelImpl.getOriginalRecordSetId(),
						ddlRecordModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_U, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_U,
					args);

				args = new Object[] {
						ddlRecordModelImpl.getRecordSetId(),
						ddlRecordModelImpl.getUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_U, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_U,
					args);
			}
		}

		entityCache.putResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordImpl.class, ddlRecord.getPrimaryKey(), ddlRecord, false);

		clearUniqueFindersCache(ddlRecordModelImpl);
		cacheUniqueFindersCache(ddlRecordModelImpl, isNew);

		ddlRecord.resetOriginalValues();

		return ddlRecord;
	}

	protected DDLRecord toUnwrappedModel(DDLRecord ddlRecord) {
		if (ddlRecord instanceof DDLRecordImpl) {
			return ddlRecord;
		}

		DDLRecordImpl ddlRecordImpl = new DDLRecordImpl();

		ddlRecordImpl.setNew(ddlRecord.isNew());
		ddlRecordImpl.setPrimaryKey(ddlRecord.getPrimaryKey());

		ddlRecordImpl.setUuid(ddlRecord.getUuid());
		ddlRecordImpl.setRecordId(ddlRecord.getRecordId());
		ddlRecordImpl.setGroupId(ddlRecord.getGroupId());
		ddlRecordImpl.setCompanyId(ddlRecord.getCompanyId());
		ddlRecordImpl.setUserId(ddlRecord.getUserId());
		ddlRecordImpl.setUserName(ddlRecord.getUserName());
		ddlRecordImpl.setVersionUserId(ddlRecord.getVersionUserId());
		ddlRecordImpl.setVersionUserName(ddlRecord.getVersionUserName());
		ddlRecordImpl.setCreateDate(ddlRecord.getCreateDate());
		ddlRecordImpl.setModifiedDate(ddlRecord.getModifiedDate());
		ddlRecordImpl.setDDMStorageId(ddlRecord.getDDMStorageId());
		ddlRecordImpl.setRecordSetId(ddlRecord.getRecordSetId());
		ddlRecordImpl.setVersion(ddlRecord.getVersion());
		ddlRecordImpl.setDisplayIndex(ddlRecord.getDisplayIndex());
		ddlRecordImpl.setLastPublishDate(ddlRecord.getLastPublishDate());

		return ddlRecordImpl;
	}

	/**
	 * Returns the d d l record with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d l record
	 * @return the d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a d d l record with the primary key could not be found
	 */
	@Override
	public DDLRecord findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRecordException {
		DDLRecord ddlRecord = fetchByPrimaryKey(primaryKey);

		if (ddlRecord == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRecordException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ddlRecord;
	}

	/**
	 * Returns the d d l record with the primary key or throws a {@link com.liferay.dynamic.data.lists.NoSuchRecordException} if it could not be found.
	 *
	 * @param recordId the primary key of the d d l record
	 * @return the d d l record
	 * @throws com.liferay.dynamic.data.lists.NoSuchRecordException if a d d l record with the primary key could not be found
	 */
	@Override
	public DDLRecord findByPrimaryKey(long recordId)
		throws NoSuchRecordException {
		return findByPrimaryKey((Serializable)recordId);
	}

	/**
	 * Returns the d d l record with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d l record
	 * @return the d d l record, or <code>null</code> if a d d l record with the primary key could not be found
	 */
	@Override
	public DDLRecord fetchByPrimaryKey(Serializable primaryKey) {
		DDLRecord ddlRecord = (DDLRecord)entityCache.getResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
				DDLRecordImpl.class, primaryKey);

		if (ddlRecord == _nullDDLRecord) {
			return null;
		}

		if (ddlRecord == null) {
			Session session = null;

			try {
				session = openSession();

				ddlRecord = (DDLRecord)session.get(DDLRecordImpl.class,
						primaryKey);

				if (ddlRecord != null) {
					cacheResult(ddlRecord);
				}
				else {
					entityCache.putResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
						DDLRecordImpl.class, primaryKey, _nullDDLRecord);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
					DDLRecordImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ddlRecord;
	}

	/**
	 * Returns the d d l record with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param recordId the primary key of the d d l record
	 * @return the d d l record, or <code>null</code> if a d d l record with the primary key could not be found
	 */
	@Override
	public DDLRecord fetchByPrimaryKey(long recordId) {
		return fetchByPrimaryKey((Serializable)recordId);
	}

	@Override
	public Map<Serializable, DDLRecord> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDLRecord> map = new HashMap<Serializable, DDLRecord>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDLRecord ddlRecord = fetchByPrimaryKey(primaryKey);

			if (ddlRecord != null) {
				map.put(primaryKey, ddlRecord);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DDLRecord ddlRecord = (DDLRecord)entityCache.getResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
					DDLRecordImpl.class, primaryKey);

			if (ddlRecord == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, ddlRecord);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DDLRECORD_WHERE_PKS_IN);

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

			for (DDLRecord ddlRecord : (List<DDLRecord>)q.list()) {
				map.put(ddlRecord.getPrimaryKeyObj(), ddlRecord);

				cacheResult(ddlRecord);

				uncachedPrimaryKeys.remove(ddlRecord.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DDLRecordModelImpl.ENTITY_CACHE_ENABLED,
					DDLRecordImpl.class, primaryKey, _nullDDLRecord);
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
	 * Returns all the d d l records.
	 *
	 * @return the d d l records
	 */
	@Override
	public List<DDLRecord> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d l records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @return the range of d d l records
	 */
	@Override
	public List<DDLRecord> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d l records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d l records
	 */
	@Override
	public List<DDLRecord> findAll(int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d l records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d l records
	 * @param end the upper bound of the range of d d l records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of d d l records
	 */
	@Override
	public List<DDLRecord> findAll(int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
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

		List<DDLRecord> list = null;

		if (retrieveFromCache) {
			list = (List<DDLRecord>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDLRECORD);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDLRECORD;

				if (pagination) {
					sql = sql.concat(DDLRecordModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDLRecord>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDLRecord>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the d d l records from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDLRecord ddlRecord : findAll()) {
			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of d d l records.
	 *
	 * @return the number of d d l records
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDLRECORD);

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
		return DDLRecordModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the d d l record persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DDLRecordImpl.class.getName());
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
	private static final String _SQL_SELECT_DDLRECORD = "SELECT ddlRecord FROM DDLRecord ddlRecord";
	private static final String _SQL_SELECT_DDLRECORD_WHERE_PKS_IN = "SELECT ddlRecord FROM DDLRecord ddlRecord WHERE recordId IN (";
	private static final String _SQL_SELECT_DDLRECORD_WHERE = "SELECT ddlRecord FROM DDLRecord ddlRecord WHERE ";
	private static final String _SQL_COUNT_DDLRECORD = "SELECT COUNT(ddlRecord) FROM DDLRecord ddlRecord";
	private static final String _SQL_COUNT_DDLRECORD_WHERE = "SELECT COUNT(ddlRecord) FROM DDLRecord ddlRecord WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddlRecord.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDLRecord exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDLRecord exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DDLRecordPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static final DDLRecord _nullDDLRecord = new DDLRecordImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DDLRecord> toCacheModel() {
				return _nullDDLRecordCacheModel;
			}
		};

	private static final CacheModel<DDLRecord> _nullDDLRecordCacheModel = new CacheModel<DDLRecord>() {
			@Override
			public DDLRecord toEntityModel() {
				return _nullDDLRecord;
			}
		};
}