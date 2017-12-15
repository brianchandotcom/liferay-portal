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

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceRecordException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceRecordPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the ddm form instance record service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordPersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceRecordUtil
 * @generated
 */
@ProviderType
public class DDMFormInstanceRecordPersistenceImpl extends BasePersistenceImpl<DDMFormInstanceRecord>
	implements DDMFormInstanceRecordPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMFormInstanceRecordUtil} to access the ddm form instance record persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMFormInstanceRecordImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DDMFormInstanceRecordModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the ddm form instance records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByUuid(String uuid, int start,
		int end, OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByUuid(String uuid, int start,
		int end, OrderByComparator<DDMFormInstanceRecord> orderByComparator,
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

		List<DDMFormInstanceRecord> list = null;

		if (retrieveFromCache) {
			list = (List<DDMFormInstanceRecord>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecord ddmFormInstanceRecord : list) {
					if (!Objects.equals(uuid, ddmFormInstanceRecord.getUuid())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
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
				query.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDMFormInstanceRecord>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMFormInstanceRecord>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first ddm form instance record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByUuid_First(String uuid,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByUuid_First(uuid,
				orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchFormInstanceRecordException(msg.toString());
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByUuid_First(String uuid,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		List<DDMFormInstanceRecord> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByUuid_Last(String uuid,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByUuid_Last(uuid,
				orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchFormInstanceRecordException(msg.toString());
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByUuid_Last(String uuid,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceRecord> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm form instance records before and after the current ddm form instance record in the ordered set where uuid = &#63;.
	 *
	 * @param formInstanceRecordId the primary key of the current ddm form instance record
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord[] findByUuid_PrevAndNext(
		long formInstanceRecordId, String uuid,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = findByPrimaryKey(formInstanceRecordId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecord[] array = new DDMFormInstanceRecordImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ddmFormInstanceRecord,
					uuid, orderByComparator, true);

			array[1] = ddmFormInstanceRecord;

			array[2] = getByUuid_PrevAndNext(session, ddmFormInstanceRecord,
					uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMFormInstanceRecord getByUuid_PrevAndNext(Session session,
		DDMFormInstanceRecord ddmFormInstanceRecord, String uuid,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals("")) {
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
			query.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmFormInstanceRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMFormInstanceRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm form instance records where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DDMFormInstanceRecord ddmFormInstanceRecord : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmFormInstanceRecord);
		}
	}

	/**
	 * Returns the number of ddm form instance records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching ddm form instance records
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMFORMINSTANCERECORD_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ddmFormInstanceRecord.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ddmFormInstanceRecord.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ddmFormInstanceRecord.uuid IS NULL OR ddmFormInstanceRecord.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMFormInstanceRecordModelImpl.UUID_COLUMN_BITMASK |
			DDMFormInstanceRecordModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the ddm form instance record where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFormInstanceRecordException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByUUID_G(String uuid, long groupId)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByUUID_G(uuid,
				groupId);

		if (ddmFormInstanceRecord == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFormInstanceRecordException(msg.toString());
		}

		return ddmFormInstanceRecord;
	}

	/**
	 * Returns the ddm form instance record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the ddm form instance record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DDMFormInstanceRecord) {
			DDMFormInstanceRecord ddmFormInstanceRecord = (DDMFormInstanceRecord)result;

			if (!Objects.equals(uuid, ddmFormInstanceRecord.getUuid()) ||
					(groupId != ddmFormInstanceRecord.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
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

				List<DDMFormInstanceRecord> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					DDMFormInstanceRecord ddmFormInstanceRecord = list.get(0);

					result = ddmFormInstanceRecord;

					cacheResult(ddmFormInstanceRecord);

					if ((ddmFormInstanceRecord.getUuid() == null) ||
							!ddmFormInstanceRecord.getUuid().equals(uuid) ||
							(ddmFormInstanceRecord.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, ddmFormInstanceRecord);
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
			return (DDMFormInstanceRecord)result;
		}
	}

	/**
	 * Removes the ddm form instance record where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the ddm form instance record that was removed
	 */
	@Override
	public DDMFormInstanceRecord removeByUUID_G(String uuid, long groupId)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = findByUUID_G(uuid, groupId);

		return remove(ddmFormInstanceRecord);
	}

	/**
	 * Returns the number of ddm form instance records where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching ddm form instance records
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMFORMINSTANCERECORD_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "ddmFormInstanceRecord.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "ddmFormInstanceRecord.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(ddmFormInstanceRecord.uuid IS NULL OR ddmFormInstanceRecord.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "ddmFormInstanceRecord.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMFormInstanceRecordModelImpl.UUID_COLUMN_BITMASK |
			DDMFormInstanceRecordModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the ddm form instance records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
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

		List<DDMFormInstanceRecord> list = null;

		if (retrieveFromCache) {
			list = (List<DDMFormInstanceRecord>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecord ddmFormInstanceRecord : list) {
					if (!Objects.equals(uuid, ddmFormInstanceRecord.getUuid()) ||
							(companyId != ddmFormInstanceRecord.getCompanyId())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
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
				query.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDMFormInstanceRecord>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMFormInstanceRecord>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first ddm form instance record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFormInstanceRecordException(msg.toString());
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		List<DDMFormInstanceRecord> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFormInstanceRecordException(msg.toString());
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceRecord> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm form instance records before and after the current ddm form instance record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param formInstanceRecordId the primary key of the current ddm form instance record
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord[] findByUuid_C_PrevAndNext(
		long formInstanceRecordId, String uuid, long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = findByPrimaryKey(formInstanceRecordId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecord[] array = new DDMFormInstanceRecordImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, ddmFormInstanceRecord,
					uuid, companyId, orderByComparator, true);

			array[1] = ddmFormInstanceRecord;

			array[2] = getByUuid_C_PrevAndNext(session, ddmFormInstanceRecord,
					uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMFormInstanceRecord getByUuid_C_PrevAndNext(Session session,
		DDMFormInstanceRecord ddmFormInstanceRecord, String uuid,
		long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals("")) {
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
			query.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmFormInstanceRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMFormInstanceRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm form instance records where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DDMFormInstanceRecord ddmFormInstanceRecord : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmFormInstanceRecord);
		}
	}

	/**
	 * Returns the number of ddm form instance records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching ddm form instance records
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMFORMINSTANCERECORD_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "ddmFormInstanceRecord.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "ddmFormInstanceRecord.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(ddmFormInstanceRecord.uuid IS NULL OR ddmFormInstanceRecord.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "ddmFormInstanceRecord.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			DDMFormInstanceRecordModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the ddm form instance records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the ddm form instance records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByCompanyId(long companyId,
		int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
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

		List<DDMFormInstanceRecord> list = null;

		if (retrieveFromCache) {
			list = (List<DDMFormInstanceRecord>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecord ddmFormInstanceRecord : list) {
					if ((companyId != ddmFormInstanceRecord.getCompanyId())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<DDMFormInstanceRecord>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMFormInstanceRecord>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first ddm form instance record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByCompanyId_First(long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFormInstanceRecordException(msg.toString());
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByCompanyId_First(long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		List<DDMFormInstanceRecord> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByCompanyId_Last(long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFormInstanceRecordException(msg.toString());
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByCompanyId_Last(long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceRecord> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm form instance records before and after the current ddm form instance record in the ordered set where companyId = &#63;.
	 *
	 * @param formInstanceRecordId the primary key of the current ddm form instance record
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord[] findByCompanyId_PrevAndNext(
		long formInstanceRecordId, long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = findByPrimaryKey(formInstanceRecordId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecord[] array = new DDMFormInstanceRecordImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					ddmFormInstanceRecord, companyId, orderByComparator, true);

			array[1] = ddmFormInstanceRecord;

			array[2] = getByCompanyId_PrevAndNext(session,
					ddmFormInstanceRecord, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMFormInstanceRecord getByCompanyId_PrevAndNext(
		Session session, DDMFormInstanceRecord ddmFormInstanceRecord,
		long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

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
			query.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmFormInstanceRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMFormInstanceRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm form instance records where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (DDMFormInstanceRecord ddmFormInstanceRecord : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmFormInstanceRecord);
		}
	}

	/**
	 * Returns the number of ddm form instance records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ddm form instance records
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMFORMINSTANCERECORD_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "ddmFormInstanceRecord.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FORMINSTANCEID =
		new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFormInstanceId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMINSTANCEID =
		new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFormInstanceId",
			new String[] { Long.class.getName() },
			DDMFormInstanceRecordModelImpl.FORMINSTANCEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FORMINSTANCEID = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFormInstanceId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the ddm form instance records where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @return the matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByFormInstanceId(long formInstanceId) {
		return findByFormInstanceId(formInstanceId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance records where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByFormInstanceId(
		long formInstanceId, int start, int end) {
		return findByFormInstanceId(formInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByFormInstanceId(
		long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		return findByFormInstanceId(formInstanceId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByFormInstanceId(
		long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMINSTANCEID;
			finderArgs = new Object[] { formInstanceId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_FORMINSTANCEID;
			finderArgs = new Object[] {
					formInstanceId,
					
					start, end, orderByComparator
				};
		}

		List<DDMFormInstanceRecord> list = null;

		if (retrieveFromCache) {
			list = (List<DDMFormInstanceRecord>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecord ddmFormInstanceRecord : list) {
					if ((formInstanceId != ddmFormInstanceRecord.getFormInstanceId())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

			query.append(_FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(formInstanceId);

				if (!pagination) {
					list = (List<DDMFormInstanceRecord>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMFormInstanceRecord>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first ddm form instance record in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByFormInstanceId_First(
		long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByFormInstanceId_First(formInstanceId,
				orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("formInstanceId=");
		msg.append(formInstanceId);

		msg.append("}");

		throw new NoSuchFormInstanceRecordException(msg.toString());
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByFormInstanceId_First(
		long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		List<DDMFormInstanceRecord> list = findByFormInstanceId(formInstanceId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByFormInstanceId_Last(
		long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByFormInstanceId_Last(formInstanceId,
				orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("formInstanceId=");
		msg.append(formInstanceId);

		msg.append("}");

		throw new NoSuchFormInstanceRecordException(msg.toString());
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByFormInstanceId_Last(
		long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		int count = countByFormInstanceId(formInstanceId);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceRecord> list = findByFormInstanceId(formInstanceId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm form instance records before and after the current ddm form instance record in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceRecordId the primary key of the current ddm form instance record
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord[] findByFormInstanceId_PrevAndNext(
		long formInstanceRecordId, long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = findByPrimaryKey(formInstanceRecordId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecord[] array = new DDMFormInstanceRecordImpl[3];

			array[0] = getByFormInstanceId_PrevAndNext(session,
					ddmFormInstanceRecord, formInstanceId, orderByComparator,
					true);

			array[1] = ddmFormInstanceRecord;

			array[2] = getByFormInstanceId_PrevAndNext(session,
					ddmFormInstanceRecord, formInstanceId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMFormInstanceRecord getByFormInstanceId_PrevAndNext(
		Session session, DDMFormInstanceRecord ddmFormInstanceRecord,
		long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

		query.append(_FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2);

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
			query.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(formInstanceId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmFormInstanceRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMFormInstanceRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm form instance records where formInstanceId = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 */
	@Override
	public void removeByFormInstanceId(long formInstanceId) {
		for (DDMFormInstanceRecord ddmFormInstanceRecord : findByFormInstanceId(
				formInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmFormInstanceRecord);
		}
	}

	/**
	 * Returns the number of ddm form instance records where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @return the number of matching ddm form instance records
	 */
	@Override
	public int countByFormInstanceId(long formInstanceId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FORMINSTANCEID;

		Object[] finderArgs = new Object[] { formInstanceId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMFORMINSTANCERECORD_WHERE);

			query.append(_FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(formInstanceId);

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

	private static final String _FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2 = "ddmFormInstanceRecord.formInstanceId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_F = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_F = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			DDMFormInstanceRecordModelImpl.USERID_COLUMN_BITMASK |
			DDMFormInstanceRecordModelImpl.FORMINSTANCEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_F = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_F",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the ddm form instance records where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @return the matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByU_F(long userId,
		long formInstanceId) {
		return findByU_F(userId, formInstanceId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance records where userId = &#63; and formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByU_F(long userId,
		long formInstanceId, int start, int end) {
		return findByU_F(userId, formInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where userId = &#63; and formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByU_F(long userId,
		long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		return findByU_F(userId, formInstanceId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where userId = &#63; and formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByU_F(long userId,
		long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_F;
			finderArgs = new Object[] { userId, formInstanceId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_F;
			finderArgs = new Object[] {
					userId, formInstanceId,
					
					start, end, orderByComparator
				};
		}

		List<DDMFormInstanceRecord> list = null;

		if (retrieveFromCache) {
			list = (List<DDMFormInstanceRecord>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecord ddmFormInstanceRecord : list) {
					if ((userId != ddmFormInstanceRecord.getUserId()) ||
							(formInstanceId != ddmFormInstanceRecord.getFormInstanceId())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

			query.append(_FINDER_COLUMN_U_F_USERID_2);

			query.append(_FINDER_COLUMN_U_F_FORMINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(formInstanceId);

				if (!pagination) {
					list = (List<DDMFormInstanceRecord>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMFormInstanceRecord>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first ddm form instance record in the ordered set where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByU_F_First(long userId,
		long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByU_F_First(userId,
				formInstanceId, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", formInstanceId=");
		msg.append(formInstanceId);

		msg.append("}");

		throw new NoSuchFormInstanceRecordException(msg.toString());
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByU_F_First(long userId,
		long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		List<DDMFormInstanceRecord> list = findByU_F(userId, formInstanceId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByU_F_Last(long userId,
		long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByU_F_Last(userId,
				formInstanceId, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", formInstanceId=");
		msg.append(formInstanceId);

		msg.append("}");

		throw new NoSuchFormInstanceRecordException(msg.toString());
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByU_F_Last(long userId,
		long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		int count = countByU_F(userId, formInstanceId);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceRecord> list = findByU_F(userId, formInstanceId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm form instance records before and after the current ddm form instance record in the ordered set where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param formInstanceRecordId the primary key of the current ddm form instance record
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord[] findByU_F_PrevAndNext(
		long formInstanceRecordId, long userId, long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = findByPrimaryKey(formInstanceRecordId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecord[] array = new DDMFormInstanceRecordImpl[3];

			array[0] = getByU_F_PrevAndNext(session, ddmFormInstanceRecord,
					userId, formInstanceId, orderByComparator, true);

			array[1] = ddmFormInstanceRecord;

			array[2] = getByU_F_PrevAndNext(session, ddmFormInstanceRecord,
					userId, formInstanceId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMFormInstanceRecord getByU_F_PrevAndNext(Session session,
		DDMFormInstanceRecord ddmFormInstanceRecord, long userId,
		long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

		query.append(_FINDER_COLUMN_U_F_USERID_2);

		query.append(_FINDER_COLUMN_U_F_FORMINSTANCEID_2);

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
			query.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(formInstanceId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmFormInstanceRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMFormInstanceRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm form instance records where userId = &#63; and formInstanceId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 */
	@Override
	public void removeByU_F(long userId, long formInstanceId) {
		for (DDMFormInstanceRecord ddmFormInstanceRecord : findByU_F(userId,
				formInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmFormInstanceRecord);
		}
	}

	/**
	 * Returns the number of ddm form instance records where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @return the number of matching ddm form instance records
	 */
	@Override
	public int countByU_F(long userId, long formInstanceId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_F;

		Object[] finderArgs = new Object[] { userId, formInstanceId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMFORMINSTANCERECORD_WHERE);

			query.append(_FINDER_COLUMN_U_F_USERID_2);

			query.append(_FINDER_COLUMN_U_F_FORMINSTANCEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(formInstanceId);

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

	private static final String _FINDER_COLUMN_U_F_USERID_2 = "ddmFormInstanceRecord.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_F_FORMINSTANCEID_2 = "ddmFormInstanceRecord.formInstanceId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_F_F = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByF_F",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_F = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByF_F",
			new String[] { Long.class.getName(), String.class.getName() },
			DDMFormInstanceRecordModelImpl.FORMINSTANCEID_COLUMN_BITMASK |
			DDMFormInstanceRecordModelImpl.FORMINSTANCEVERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_F_F = new FinderPath(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_F",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @return the matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByF_F(long formInstanceId,
		String formInstanceVersion) {
		return findByF_F(formInstanceId, formInstanceVersion,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByF_F(long formInstanceId,
		String formInstanceVersion, int start, int end) {
		return findByF_F(formInstanceId, formInstanceVersion, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByF_F(long formInstanceId,
		String formInstanceVersion, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		return findByF_F(formInstanceId, formInstanceVersion, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByF_F(long formInstanceId,
		String formInstanceVersion, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_F;
			finderArgs = new Object[] { formInstanceId, formInstanceVersion };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_F_F;
			finderArgs = new Object[] {
					formInstanceId, formInstanceVersion,
					
					start, end, orderByComparator
				};
		}

		List<DDMFormInstanceRecord> list = null;

		if (retrieveFromCache) {
			list = (List<DDMFormInstanceRecord>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecord ddmFormInstanceRecord : list) {
					if ((formInstanceId != ddmFormInstanceRecord.getFormInstanceId()) ||
							!Objects.equals(formInstanceVersion,
								ddmFormInstanceRecord.getFormInstanceVersion())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

			query.append(_FINDER_COLUMN_F_F_FORMINSTANCEID_2);

			boolean bindFormInstanceVersion = false;

			if (formInstanceVersion == null) {
				query.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_1);
			}
			else if (formInstanceVersion.equals("")) {
				query.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_3);
			}
			else {
				bindFormInstanceVersion = true;

				query.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(formInstanceId);

				if (bindFormInstanceVersion) {
					qPos.add(formInstanceVersion);
				}

				if (!pagination) {
					list = (List<DDMFormInstanceRecord>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMFormInstanceRecord>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first ddm form instance record in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByF_F_First(long formInstanceId,
		String formInstanceVersion,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByF_F_First(formInstanceId,
				formInstanceVersion, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("formInstanceId=");
		msg.append(formInstanceId);

		msg.append(", formInstanceVersion=");
		msg.append(formInstanceVersion);

		msg.append("}");

		throw new NoSuchFormInstanceRecordException(msg.toString());
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByF_F_First(long formInstanceId,
		String formInstanceVersion,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		List<DDMFormInstanceRecord> list = findByF_F(formInstanceId,
				formInstanceVersion, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByF_F_Last(long formInstanceId,
		String formInstanceVersion,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByF_F_Last(formInstanceId,
				formInstanceVersion, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("formInstanceId=");
		msg.append(formInstanceId);

		msg.append(", formInstanceVersion=");
		msg.append(formInstanceVersion);

		msg.append("}");

		throw new NoSuchFormInstanceRecordException(msg.toString());
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByF_F_Last(long formInstanceId,
		String formInstanceVersion,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		int count = countByF_F(formInstanceId, formInstanceVersion);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceRecord> list = findByF_F(formInstanceId,
				formInstanceVersion, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm form instance records before and after the current ddm form instance record in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceRecordId the primary key of the current ddm form instance record
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord[] findByF_F_PrevAndNext(
		long formInstanceRecordId, long formInstanceId,
		String formInstanceVersion,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = findByPrimaryKey(formInstanceRecordId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecord[] array = new DDMFormInstanceRecordImpl[3];

			array[0] = getByF_F_PrevAndNext(session, ddmFormInstanceRecord,
					formInstanceId, formInstanceVersion, orderByComparator, true);

			array[1] = ddmFormInstanceRecord;

			array[2] = getByF_F_PrevAndNext(session, ddmFormInstanceRecord,
					formInstanceId, formInstanceVersion, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMFormInstanceRecord getByF_F_PrevAndNext(Session session,
		DDMFormInstanceRecord ddmFormInstanceRecord, long formInstanceId,
		String formInstanceVersion,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

		query.append(_FINDER_COLUMN_F_F_FORMINSTANCEID_2);

		boolean bindFormInstanceVersion = false;

		if (formInstanceVersion == null) {
			query.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_1);
		}
		else if (formInstanceVersion.equals("")) {
			query.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_3);
		}
		else {
			bindFormInstanceVersion = true;

			query.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_2);
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
			query.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(formInstanceId);

		if (bindFormInstanceVersion) {
			qPos.add(formInstanceVersion);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmFormInstanceRecord);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMFormInstanceRecord> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 */
	@Override
	public void removeByF_F(long formInstanceId, String formInstanceVersion) {
		for (DDMFormInstanceRecord ddmFormInstanceRecord : findByF_F(
				formInstanceId, formInstanceVersion, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(ddmFormInstanceRecord);
		}
	}

	/**
	 * Returns the number of ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @return the number of matching ddm form instance records
	 */
	@Override
	public int countByF_F(long formInstanceId, String formInstanceVersion) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_F_F;

		Object[] finderArgs = new Object[] { formInstanceId, formInstanceVersion };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMFORMINSTANCERECORD_WHERE);

			query.append(_FINDER_COLUMN_F_F_FORMINSTANCEID_2);

			boolean bindFormInstanceVersion = false;

			if (formInstanceVersion == null) {
				query.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_1);
			}
			else if (formInstanceVersion.equals("")) {
				query.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_3);
			}
			else {
				bindFormInstanceVersion = true;

				query.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(formInstanceId);

				if (bindFormInstanceVersion) {
					qPos.add(formInstanceVersion);
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

	private static final String _FINDER_COLUMN_F_F_FORMINSTANCEID_2 = "ddmFormInstanceRecord.formInstanceId = ? AND ";
	private static final String _FINDER_COLUMN_F_F_FORMINSTANCEVERSION_1 = "ddmFormInstanceRecord.formInstanceVersion IS NULL";
	private static final String _FINDER_COLUMN_F_F_FORMINSTANCEVERSION_2 = "ddmFormInstanceRecord.formInstanceVersion = ?";
	private static final String _FINDER_COLUMN_F_F_FORMINSTANCEVERSION_3 = "(ddmFormInstanceRecord.formInstanceVersion IS NULL OR ddmFormInstanceRecord.formInstanceVersion = '')";

	public DDMFormInstanceRecordPersistenceImpl() {
		setModelClass(DDMFormInstanceRecord.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the ddm form instance record in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceRecord the ddm form instance record
	 */
	@Override
	public void cacheResult(DDMFormInstanceRecord ddmFormInstanceRecord) {
		entityCache.putResult(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			ddmFormInstanceRecord.getPrimaryKey(), ddmFormInstanceRecord);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				ddmFormInstanceRecord.getUuid(),
				ddmFormInstanceRecord.getGroupId()
			}, ddmFormInstanceRecord);

		ddmFormInstanceRecord.resetOriginalValues();
	}

	/**
	 * Caches the ddm form instance records in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceRecords the ddm form instance records
	 */
	@Override
	public void cacheResult(List<DDMFormInstanceRecord> ddmFormInstanceRecords) {
		for (DDMFormInstanceRecord ddmFormInstanceRecord : ddmFormInstanceRecords) {
			if (entityCache.getResult(
						DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
						DDMFormInstanceRecordImpl.class,
						ddmFormInstanceRecord.getPrimaryKey()) == null) {
				cacheResult(ddmFormInstanceRecord);
			}
			else {
				ddmFormInstanceRecord.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ddm form instance records.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMFormInstanceRecordImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ddm form instance record.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMFormInstanceRecord ddmFormInstanceRecord) {
		entityCache.removeResult(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			ddmFormInstanceRecord.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DDMFormInstanceRecordModelImpl)ddmFormInstanceRecord,
			true);
	}

	@Override
	public void clearCache(List<DDMFormInstanceRecord> ddmFormInstanceRecords) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMFormInstanceRecord ddmFormInstanceRecord : ddmFormInstanceRecords) {
			entityCache.removeResult(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
				DDMFormInstanceRecordImpl.class,
				ddmFormInstanceRecord.getPrimaryKey());

			clearUniqueFindersCache((DDMFormInstanceRecordModelImpl)ddmFormInstanceRecord,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMFormInstanceRecordModelImpl ddmFormInstanceRecordModelImpl) {
		Object[] args = new Object[] {
				ddmFormInstanceRecordModelImpl.getUuid(),
				ddmFormInstanceRecordModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			ddmFormInstanceRecordModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DDMFormInstanceRecordModelImpl ddmFormInstanceRecordModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					ddmFormInstanceRecordModelImpl.getUuid(),
					ddmFormInstanceRecordModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((ddmFormInstanceRecordModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					ddmFormInstanceRecordModelImpl.getOriginalUuid(),
					ddmFormInstanceRecordModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new ddm form instance record with the primary key. Does not add the ddm form instance record to the database.
	 *
	 * @param formInstanceRecordId the primary key for the new ddm form instance record
	 * @return the new ddm form instance record
	 */
	@Override
	public DDMFormInstanceRecord create(long formInstanceRecordId) {
		DDMFormInstanceRecord ddmFormInstanceRecord = new DDMFormInstanceRecordImpl();

		ddmFormInstanceRecord.setNew(true);
		ddmFormInstanceRecord.setPrimaryKey(formInstanceRecordId);

		String uuid = PortalUUIDUtil.generate();

		ddmFormInstanceRecord.setUuid(uuid);

		ddmFormInstanceRecord.setCompanyId(companyProvider.getCompanyId());

		return ddmFormInstanceRecord;
	}

	/**
	 * Removes the ddm form instance record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formInstanceRecordId the primary key of the ddm form instance record
	 * @return the ddm form instance record that was removed
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord remove(long formInstanceRecordId)
		throws NoSuchFormInstanceRecordException {
		return remove((Serializable)formInstanceRecordId);
	}

	/**
	 * Removes the ddm form instance record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ddm form instance record
	 * @return the ddm form instance record that was removed
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord remove(Serializable primaryKey)
		throws NoSuchFormInstanceRecordException {
		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecord ddmFormInstanceRecord = (DDMFormInstanceRecord)session.get(DDMFormInstanceRecordImpl.class,
					primaryKey);

			if (ddmFormInstanceRecord == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFormInstanceRecordException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmFormInstanceRecord);
		}
		catch (NoSuchFormInstanceRecordException nsee) {
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
	protected DDMFormInstanceRecord removeImpl(
		DDMFormInstanceRecord ddmFormInstanceRecord) {
		ddmFormInstanceRecord = toUnwrappedModel(ddmFormInstanceRecord);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmFormInstanceRecord)) {
				ddmFormInstanceRecord = (DDMFormInstanceRecord)session.get(DDMFormInstanceRecordImpl.class,
						ddmFormInstanceRecord.getPrimaryKeyObj());
			}

			if (ddmFormInstanceRecord != null) {
				session.delete(ddmFormInstanceRecord);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmFormInstanceRecord != null) {
			clearCache(ddmFormInstanceRecord);
		}

		return ddmFormInstanceRecord;
	}

	@Override
	public DDMFormInstanceRecord updateImpl(
		DDMFormInstanceRecord ddmFormInstanceRecord) {
		ddmFormInstanceRecord = toUnwrappedModel(ddmFormInstanceRecord);

		boolean isNew = ddmFormInstanceRecord.isNew();

		DDMFormInstanceRecordModelImpl ddmFormInstanceRecordModelImpl = (DDMFormInstanceRecordModelImpl)ddmFormInstanceRecord;

		if (Validator.isNull(ddmFormInstanceRecord.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddmFormInstanceRecord.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ddmFormInstanceRecord.getCreateDate() == null)) {
			if (serviceContext == null) {
				ddmFormInstanceRecord.setCreateDate(now);
			}
			else {
				ddmFormInstanceRecord.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!ddmFormInstanceRecordModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ddmFormInstanceRecord.setModifiedDate(now);
			}
			else {
				ddmFormInstanceRecord.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ddmFormInstanceRecord.isNew()) {
				session.save(ddmFormInstanceRecord);

				ddmFormInstanceRecord.setNew(false);
			}
			else {
				ddmFormInstanceRecord = (DDMFormInstanceRecord)session.merge(ddmFormInstanceRecord);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!DDMFormInstanceRecordModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					ddmFormInstanceRecordModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					ddmFormInstanceRecordModelImpl.getUuid(),
					ddmFormInstanceRecordModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { ddmFormInstanceRecordModelImpl.getCompanyId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] {
					ddmFormInstanceRecordModelImpl.getFormInstanceId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_FORMINSTANCEID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMINSTANCEID,
				args);

			args = new Object[] {
					ddmFormInstanceRecordModelImpl.getUserId(),
					ddmFormInstanceRecordModelImpl.getFormInstanceId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_U_F, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_F,
				args);

			args = new Object[] {
					ddmFormInstanceRecordModelImpl.getFormInstanceId(),
					ddmFormInstanceRecordModelImpl.getFormInstanceVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_F_F, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_F,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((ddmFormInstanceRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmFormInstanceRecordModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { ddmFormInstanceRecordModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((ddmFormInstanceRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmFormInstanceRecordModelImpl.getOriginalUuid(),
						ddmFormInstanceRecordModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						ddmFormInstanceRecordModelImpl.getUuid(),
						ddmFormInstanceRecordModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((ddmFormInstanceRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmFormInstanceRecordModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						ddmFormInstanceRecordModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((ddmFormInstanceRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMINSTANCEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmFormInstanceRecordModelImpl.getOriginalFormInstanceId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FORMINSTANCEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMINSTANCEID,
					args);

				args = new Object[] {
						ddmFormInstanceRecordModelImpl.getFormInstanceId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FORMINSTANCEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMINSTANCEID,
					args);
			}

			if ((ddmFormInstanceRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmFormInstanceRecordModelImpl.getOriginalUserId(),
						ddmFormInstanceRecordModelImpl.getOriginalFormInstanceId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_F,
					args);

				args = new Object[] {
						ddmFormInstanceRecordModelImpl.getUserId(),
						ddmFormInstanceRecordModelImpl.getFormInstanceId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_F,
					args);
			}

			if ((ddmFormInstanceRecordModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmFormInstanceRecordModelImpl.getOriginalFormInstanceId(),
						ddmFormInstanceRecordModelImpl.getOriginalFormInstanceVersion()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_F_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_F,
					args);

				args = new Object[] {
						ddmFormInstanceRecordModelImpl.getFormInstanceId(),
						ddmFormInstanceRecordModelImpl.getFormInstanceVersion()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_F_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_F,
					args);
			}
		}

		entityCache.putResult(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceRecordImpl.class,
			ddmFormInstanceRecord.getPrimaryKey(), ddmFormInstanceRecord, false);

		clearUniqueFindersCache(ddmFormInstanceRecordModelImpl, false);
		cacheUniqueFindersCache(ddmFormInstanceRecordModelImpl);

		ddmFormInstanceRecord.resetOriginalValues();

		return ddmFormInstanceRecord;
	}

	protected DDMFormInstanceRecord toUnwrappedModel(
		DDMFormInstanceRecord ddmFormInstanceRecord) {
		if (ddmFormInstanceRecord instanceof DDMFormInstanceRecordImpl) {
			return ddmFormInstanceRecord;
		}

		DDMFormInstanceRecordImpl ddmFormInstanceRecordImpl = new DDMFormInstanceRecordImpl();

		ddmFormInstanceRecordImpl.setNew(ddmFormInstanceRecord.isNew());
		ddmFormInstanceRecordImpl.setPrimaryKey(ddmFormInstanceRecord.getPrimaryKey());

		ddmFormInstanceRecordImpl.setUuid(ddmFormInstanceRecord.getUuid());
		ddmFormInstanceRecordImpl.setFormInstanceRecordId(ddmFormInstanceRecord.getFormInstanceRecordId());
		ddmFormInstanceRecordImpl.setGroupId(ddmFormInstanceRecord.getGroupId());
		ddmFormInstanceRecordImpl.setCompanyId(ddmFormInstanceRecord.getCompanyId());
		ddmFormInstanceRecordImpl.setUserId(ddmFormInstanceRecord.getUserId());
		ddmFormInstanceRecordImpl.setUserName(ddmFormInstanceRecord.getUserName());
		ddmFormInstanceRecordImpl.setVersionUserId(ddmFormInstanceRecord.getVersionUserId());
		ddmFormInstanceRecordImpl.setVersionUserName(ddmFormInstanceRecord.getVersionUserName());
		ddmFormInstanceRecordImpl.setCreateDate(ddmFormInstanceRecord.getCreateDate());
		ddmFormInstanceRecordImpl.setModifiedDate(ddmFormInstanceRecord.getModifiedDate());
		ddmFormInstanceRecordImpl.setFormInstanceId(ddmFormInstanceRecord.getFormInstanceId());
		ddmFormInstanceRecordImpl.setFormInstanceVersion(ddmFormInstanceRecord.getFormInstanceVersion());
		ddmFormInstanceRecordImpl.setStorageId(ddmFormInstanceRecord.getStorageId());
		ddmFormInstanceRecordImpl.setVersion(ddmFormInstanceRecord.getVersion());
		ddmFormInstanceRecordImpl.setLastPublishDate(ddmFormInstanceRecord.getLastPublishDate());

		return ddmFormInstanceRecordImpl;
	}

	/**
	 * Returns the ddm form instance record with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm form instance record
	 * @return the ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFormInstanceRecordException {
		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByPrimaryKey(primaryKey);

		if (ddmFormInstanceRecord == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFormInstanceRecordException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ddmFormInstanceRecord;
	}

	/**
	 * Returns the ddm form instance record with the primary key or throws a {@link NoSuchFormInstanceRecordException} if it could not be found.
	 *
	 * @param formInstanceRecordId the primary key of the ddm form instance record
	 * @return the ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByPrimaryKey(long formInstanceRecordId)
		throws NoSuchFormInstanceRecordException {
		return findByPrimaryKey((Serializable)formInstanceRecordId);
	}

	/**
	 * Returns the ddm form instance record with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm form instance record
	 * @return the ddm form instance record, or <code>null</code> if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
				DDMFormInstanceRecordImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		DDMFormInstanceRecord ddmFormInstanceRecord = (DDMFormInstanceRecord)serializable;

		if (ddmFormInstanceRecord == null) {
			Session session = null;

			try {
				session = openSession();

				ddmFormInstanceRecord = (DDMFormInstanceRecord)session.get(DDMFormInstanceRecordImpl.class,
						primaryKey);

				if (ddmFormInstanceRecord != null) {
					cacheResult(ddmFormInstanceRecord);
				}
				else {
					entityCache.putResult(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
						DDMFormInstanceRecordImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
					DDMFormInstanceRecordImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ddmFormInstanceRecord;
	}

	/**
	 * Returns the ddm form instance record with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formInstanceRecordId the primary key of the ddm form instance record
	 * @return the ddm form instance record, or <code>null</code> if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByPrimaryKey(long formInstanceRecordId) {
		return fetchByPrimaryKey((Serializable)formInstanceRecordId);
	}

	@Override
	public Map<Serializable, DDMFormInstanceRecord> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMFormInstanceRecord> map = new HashMap<Serializable, DDMFormInstanceRecord>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMFormInstanceRecord ddmFormInstanceRecord = fetchByPrimaryKey(primaryKey);

			if (ddmFormInstanceRecord != null) {
				map.put(primaryKey, ddmFormInstanceRecord);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
					DDMFormInstanceRecordImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (DDMFormInstanceRecord)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (DDMFormInstanceRecord ddmFormInstanceRecord : (List<DDMFormInstanceRecord>)q.list()) {
				map.put(ddmFormInstanceRecord.getPrimaryKeyObj(),
					ddmFormInstanceRecord);

				cacheResult(ddmFormInstanceRecord);

				uncachedPrimaryKeys.remove(ddmFormInstanceRecord.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DDMFormInstanceRecordModelImpl.ENTITY_CACHE_ENABLED,
					DDMFormInstanceRecordImpl.class, primaryKey, nullModel);
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
	 * Returns all the ddm form instance records.
	 *
	 * @return the ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findAll(int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findAll(int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
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

		List<DDMFormInstanceRecord> list = null;

		if (retrieveFromCache) {
			list = (List<DDMFormInstanceRecord>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DDMFORMINSTANCERECORD);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMFORMINSTANCERECORD;

				if (pagination) {
					sql = sql.concat(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMFormInstanceRecord>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMFormInstanceRecord>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the ddm form instance records from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMFormInstanceRecord ddmFormInstanceRecord : findAll()) {
			remove(ddmFormInstanceRecord);
		}
	}

	/**
	 * Returns the number of ddm form instance records.
	 *
	 * @return the number of ddm form instance records
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMFORMINSTANCERECORD);

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
		return DDMFormInstanceRecordModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ddm form instance record persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DDMFormInstanceRecordImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_DDMFORMINSTANCERECORD = "SELECT ddmFormInstanceRecord FROM DDMFormInstanceRecord ddmFormInstanceRecord";
	private static final String _SQL_SELECT_DDMFORMINSTANCERECORD_WHERE_PKS_IN = "SELECT ddmFormInstanceRecord FROM DDMFormInstanceRecord ddmFormInstanceRecord WHERE formInstanceRecordId IN (";
	private static final String _SQL_SELECT_DDMFORMINSTANCERECORD_WHERE = "SELECT ddmFormInstanceRecord FROM DDMFormInstanceRecord ddmFormInstanceRecord WHERE ";
	private static final String _SQL_COUNT_DDMFORMINSTANCERECORD = "SELECT COUNT(ddmFormInstanceRecord) FROM DDMFormInstanceRecord ddmFormInstanceRecord";
	private static final String _SQL_COUNT_DDMFORMINSTANCERECORD_WHERE = "SELECT COUNT(ddmFormInstanceRecord) FROM DDMFormInstanceRecord ddmFormInstanceRecord WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmFormInstanceRecord.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMFormInstanceRecord exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMFormInstanceRecord exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DDMFormInstanceRecordPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}