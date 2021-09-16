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

import com.liferay.dynamic.data.lists.exception.NoSuchRecordException;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordTable;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordImpl;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordPersistence;
import com.liferay.dynamic.data.lists.service.persistence.impl.constants.DDLPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
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
 * The persistence implementation for the ddl record service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {DDLRecordPersistence.class, BasePersistence.class})
public class DDLRecordPersistenceImpl
	extends BasePersistenceImpl<DDLRecord> implements DDLRecordPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DDLRecordUtil</code> to access the ddl record persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DDLRecordImpl.class.getName();

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
	 * Returns all the ddl records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

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

		List<DDLRecord> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDLRecord>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecord ddlRecord : list) {
					if (!uuid.equals(ddlRecord.getUuid())) {
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

			sb.append(_SQL_SELECT_DDLRECORD_WHERE);

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
				sb.append(DDLRecordModelImpl.ORDER_BY_JPQL);
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

				list = (List<DDLRecord>)QueryUtil.list(
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
	 * Returns the first ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByUuid_First(
			String uuid, OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByUuid_First(uuid, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchRecordException(sb.toString());
	}

	/**
	 * Returns the first ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByUuid_First(
		String uuid, OrderByComparator<DDLRecord> orderByComparator) {

		List<DDLRecord> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByUuid_Last(
			String uuid, OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByUuid_Last(uuid, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchRecordException(sb.toString());
	}

	/**
	 * Returns the last ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByUuid_Last(
		String uuid, OrderByComparator<DDLRecord> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DDLRecord> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where uuid = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord[] findByUuid_PrevAndNext(
			long recordId, String uuid,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		uuid = Objects.toString(uuid, "");

		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, ddlRecord, uuid, orderByComparator, true);

			array[1] = ddlRecord;

			array[2] = getByUuid_PrevAndNext(
				session, ddlRecord, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDLRecord getByUuid_PrevAndNext(
		Session session, DDLRecord ddlRecord, String uuid,
		OrderByComparator<DDLRecord> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_DDLRECORD_WHERE);

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
			sb.append(DDLRecordModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(ddlRecord)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDLRecord> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddl records where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DDLRecord ddlRecord :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of ddl records where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DDLRECORD_WHERE);

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
		"ddlRecord.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(ddlRecord.uuid IS NULL OR ddlRecord.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the ddl record where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchRecordException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByUUID_G(String uuid, long groupId)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByUUID_G(uuid, groupId);

		if (ddlRecord == null) {
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

			throw new NoSuchRecordException(sb.toString());
		}

		return ddlRecord;
	}

	/**
	 * Returns the ddl record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the ddl record where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof DDLRecord) {
			DDLRecord ddlRecord = (DDLRecord)result;

			if (!Objects.equals(uuid, ddlRecord.getUuid()) ||
				(groupId != ddlRecord.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DDLRECORD_WHERE);

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

				List<DDLRecord> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					DDLRecord ddlRecord = list.get(0);

					result = ddlRecord;

					cacheResult(ddlRecord);
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
			return (DDLRecord)result;
		}
	}

	/**
	 * Removes the ddl record where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the ddl record that was removed
	 */
	@Override
	public DDLRecord removeByUUID_G(String uuid, long groupId)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = findByUUID_G(uuid, groupId);

		return remove(ddlRecord);
	}

	/**
	 * Returns the number of ddl records where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G;

			finderArgs = new Object[] {uuid, groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDLRECORD_WHERE);

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
		"ddlRecord.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(ddlRecord.uuid IS NULL OR ddlRecord.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"ddlRecord.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

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

		List<DDLRecord> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDLRecord>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecord ddlRecord : list) {
					if (!uuid.equals(ddlRecord.getUuid()) ||
						(companyId != ddlRecord.getCompanyId())) {

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

			sb.append(_SQL_SELECT_DDLRECORD_WHERE);

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
				sb.append(DDLRecordModelImpl.ORDER_BY_JPQL);
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

				list = (List<DDLRecord>)QueryUtil.list(
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
	 * Returns the first ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchRecordException(sb.toString());
	}

	/**
	 * Returns the first ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DDLRecord> orderByComparator) {

		List<DDLRecord> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchRecordException(sb.toString());
	}

	/**
	 * Returns the last ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DDLRecord> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DDLRecord> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord[] findByUuid_C_PrevAndNext(
			long recordId, String uuid, long companyId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		uuid = Objects.toString(uuid, "");

		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, ddlRecord, uuid, companyId, orderByComparator, true);

			array[1] = ddlRecord;

			array[2] = getByUuid_C_PrevAndNext(
				session, ddlRecord, uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDLRecord getByUuid_C_PrevAndNext(
		Session session, DDLRecord ddlRecord, String uuid, long companyId,
		OrderByComparator<DDLRecord> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_DDLRECORD_WHERE);

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
			sb.append(DDLRecordModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(ddlRecord)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDLRecord> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddl records where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DDLRecord ddlRecord :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of ddl records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDLRECORD_WHERE);

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
		"ddlRecord.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(ddlRecord.uuid IS NULL OR ddlRecord.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"ddlRecord.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the ddl records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ddl records
	 */
	@Override
	public List<DDLRecord> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<DDLRecord> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDLRecord>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecord ddlRecord : list) {
					if (companyId != ddlRecord.getCompanyId()) {
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

			sb.append(_SQL_SELECT_DDLRECORD_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDLRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<DDLRecord>)QueryUtil.list(
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
	 * Returns the first ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByCompanyId_First(
			long companyId, OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchRecordException(sb.toString());
	}

	/**
	 * Returns the first ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByCompanyId_First(
		long companyId, OrderByComparator<DDLRecord> orderByComparator) {

		List<DDLRecord> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByCompanyId_Last(
			long companyId, OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchRecordException(sb.toString());
	}

	/**
	 * Returns the last ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByCompanyId_Last(
		long companyId, OrderByComparator<DDLRecord> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<DDLRecord> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where companyId = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord[] findByCompanyId_PrevAndNext(
			long recordId, long companyId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, ddlRecord, companyId, orderByComparator, true);

			array[1] = ddlRecord;

			array[2] = getByCompanyId_PrevAndNext(
				session, ddlRecord, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDLRecord getByCompanyId_PrevAndNext(
		Session session, DDLRecord ddlRecord, long companyId,
		OrderByComparator<DDLRecord> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_DDLRECORD_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			sb.append(DDLRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ddlRecord)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDLRecord> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddl records where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (DDLRecord ddlRecord :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of ddl records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByCompanyId(long companyId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCompanyId;

			finderArgs = new Object[] {companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DDLRECORD_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"ddlRecord.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByRecordSetId;
	private FinderPath _finderPathWithoutPaginationFindByRecordSetId;
	private FinderPath _finderPathCountByRecordSetId;

	/**
	 * Returns all the ddl records where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @return the matching ddl records
	 */
	@Override
	public List<DDLRecord> findByRecordSetId(long recordSetId) {
		return findByRecordSetId(
			recordSetId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl records where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end) {

		return findByRecordSetId(recordSetId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return findByRecordSetId(
			recordSetId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByRecordSetId(
		long recordSetId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByRecordSetId;
				finderArgs = new Object[] {recordSetId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByRecordSetId;
			finderArgs = new Object[] {
				recordSetId, start, end, orderByComparator
			};
		}

		List<DDLRecord> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDLRecord>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecord ddlRecord : list) {
					if (recordSetId != ddlRecord.getRecordSetId()) {
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

			sb.append(_SQL_SELECT_DDLRECORD_WHERE);

			sb.append(_FINDER_COLUMN_RECORDSETID_RECORDSETID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDLRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(recordSetId);

				list = (List<DDLRecord>)QueryUtil.list(
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
	 * Returns the first ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByRecordSetId_First(
			long recordSetId, OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByRecordSetId_First(
			recordSetId, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("recordSetId=");
		sb.append(recordSetId);

		sb.append("}");

		throw new NoSuchRecordException(sb.toString());
	}

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByRecordSetId_First(
		long recordSetId, OrderByComparator<DDLRecord> orderByComparator) {

		List<DDLRecord> list = findByRecordSetId(
			recordSetId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByRecordSetId_Last(
			long recordSetId, OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByRecordSetId_Last(
			recordSetId, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("recordSetId=");
		sb.append(recordSetId);

		sb.append("}");

		throw new NoSuchRecordException(sb.toString());
	}

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByRecordSetId_Last(
		long recordSetId, OrderByComparator<DDLRecord> orderByComparator) {

		int count = countByRecordSetId(recordSetId);

		if (count == 0) {
			return null;
		}

		List<DDLRecord> list = findByRecordSetId(
			recordSetId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord[] findByRecordSetId_PrevAndNext(
			long recordId, long recordSetId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByRecordSetId_PrevAndNext(
				session, ddlRecord, recordSetId, orderByComparator, true);

			array[1] = ddlRecord;

			array[2] = getByRecordSetId_PrevAndNext(
				session, ddlRecord, recordSetId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDLRecord getByRecordSetId_PrevAndNext(
		Session session, DDLRecord ddlRecord, long recordSetId,
		OrderByComparator<DDLRecord> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_DDLRECORD_WHERE);

		sb.append(_FINDER_COLUMN_RECORDSETID_RECORDSETID_2);

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
			sb.append(DDLRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(recordSetId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ddlRecord)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDLRecord> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddl records where recordSetId = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 */
	@Override
	public void removeByRecordSetId(long recordSetId) {
		for (DDLRecord ddlRecord :
				findByRecordSetId(
					recordSetId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of ddl records where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByRecordSetId(long recordSetId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByRecordSetId;

			finderArgs = new Object[] {recordSetId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DDLRECORD_WHERE);

			sb.append(_FINDER_COLUMN_RECORDSETID_RECORDSETID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(recordSetId);

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

	private static final String _FINDER_COLUMN_RECORDSETID_RECORDSETID_2 =
		"ddlRecord.recordSetId = ?";

	private FinderPath _finderPathWithPaginationFindByR_U;
	private FinderPath _finderPathWithoutPaginationFindByR_U;
	private FinderPath _finderPathCountByR_U;

	/**
	 * Returns all the ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @return the matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_U(long recordSetId, long userId) {
		return findByR_U(
			recordSetId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end) {

		return findByR_U(recordSetId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return findByR_U(
			recordSetId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_U(
		long recordSetId, long userId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByR_U;
				finderArgs = new Object[] {recordSetId, userId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByR_U;
			finderArgs = new Object[] {
				recordSetId, userId, start, end, orderByComparator
			};
		}

		List<DDLRecord> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDLRecord>)finderCache.getResult(
				finderPath, finderArgs);

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
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_DDLRECORD_WHERE);

			sb.append(_FINDER_COLUMN_R_U_RECORDSETID_2);

			sb.append(_FINDER_COLUMN_R_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDLRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(recordSetId);

				queryPos.add(userId);

				list = (List<DDLRecord>)QueryUtil.list(
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
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByR_U_First(
			long recordSetId, long userId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByR_U_First(
			recordSetId, userId, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("recordSetId=");
		sb.append(recordSetId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchRecordException(sb.toString());
	}

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByR_U_First(
		long recordSetId, long userId,
		OrderByComparator<DDLRecord> orderByComparator) {

		List<DDLRecord> list = findByR_U(
			recordSetId, userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByR_U_Last(
			long recordSetId, long userId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByR_U_Last(
			recordSetId, userId, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("recordSetId=");
		sb.append(recordSetId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchRecordException(sb.toString());
	}

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByR_U_Last(
		long recordSetId, long userId,
		OrderByComparator<DDLRecord> orderByComparator) {

		int count = countByR_U(recordSetId, userId);

		if (count == 0) {
			return null;
		}

		List<DDLRecord> list = findByR_U(
			recordSetId, userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord[] findByR_U_PrevAndNext(
			long recordId, long recordSetId, long userId,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByR_U_PrevAndNext(
				session, ddlRecord, recordSetId, userId, orderByComparator,
				true);

			array[1] = ddlRecord;

			array[2] = getByR_U_PrevAndNext(
				session, ddlRecord, recordSetId, userId, orderByComparator,
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

	protected DDLRecord getByR_U_PrevAndNext(
		Session session, DDLRecord ddlRecord, long recordSetId, long userId,
		OrderByComparator<DDLRecord> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_DDLRECORD_WHERE);

		sb.append(_FINDER_COLUMN_R_U_RECORDSETID_2);

		sb.append(_FINDER_COLUMN_R_U_USERID_2);

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
			sb.append(DDLRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(recordSetId);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ddlRecord)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDLRecord> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddl records where recordSetId = &#63; and userId = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByR_U(long recordSetId, long userId) {
		for (DDLRecord ddlRecord :
				findByR_U(
					recordSetId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of ddl records where recordSetId = &#63; and userId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param userId the user ID
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByR_U(long recordSetId, long userId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByR_U;

			finderArgs = new Object[] {recordSetId, userId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDLRECORD_WHERE);

			sb.append(_FINDER_COLUMN_R_U_RECORDSETID_2);

			sb.append(_FINDER_COLUMN_R_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(recordSetId);

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

	private static final String _FINDER_COLUMN_R_U_RECORDSETID_2 =
		"ddlRecord.recordSetId = ? AND ";

	private static final String _FINDER_COLUMN_R_U_USERID_2 =
		"ddlRecord.userId = ?";

	private FinderPath _finderPathWithPaginationFindByR_R;
	private FinderPath _finderPathWithoutPaginationFindByR_R;
	private FinderPath _finderPathCountByR_R;

	/**
	 * Returns all the ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @return the matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion) {

		return findByR_R(
			recordSetId, recordSetVersion, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion, int start, int end) {

		return findByR_R(recordSetId, recordSetVersion, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return findByR_R(
			recordSetId, recordSetVersion, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByR_R(
		long recordSetId, String recordSetVersion, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		recordSetVersion = Objects.toString(recordSetVersion, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByR_R;
				finderArgs = new Object[] {recordSetId, recordSetVersion};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByR_R;
			finderArgs = new Object[] {
				recordSetId, recordSetVersion, start, end, orderByComparator
			};
		}

		List<DDLRecord> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDLRecord>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecord ddlRecord : list) {
					if ((recordSetId != ddlRecord.getRecordSetId()) ||
						!recordSetVersion.equals(
							ddlRecord.getRecordSetVersion())) {

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

			sb.append(_SQL_SELECT_DDLRECORD_WHERE);

			sb.append(_FINDER_COLUMN_R_R_RECORDSETID_2);

			boolean bindRecordSetVersion = false;

			if (recordSetVersion.isEmpty()) {
				sb.append(_FINDER_COLUMN_R_R_RECORDSETVERSION_3);
			}
			else {
				bindRecordSetVersion = true;

				sb.append(_FINDER_COLUMN_R_R_RECORDSETVERSION_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDLRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(recordSetId);

				if (bindRecordSetVersion) {
					queryPos.add(recordSetVersion);
				}

				list = (List<DDLRecord>)QueryUtil.list(
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
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByR_R_First(
			long recordSetId, String recordSetVersion,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByR_R_First(
			recordSetId, recordSetVersion, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("recordSetId=");
		sb.append(recordSetId);

		sb.append(", recordSetVersion=");
		sb.append(recordSetVersion);

		sb.append("}");

		throw new NoSuchRecordException(sb.toString());
	}

	/**
	 * Returns the first ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByR_R_First(
		long recordSetId, String recordSetVersion,
		OrderByComparator<DDLRecord> orderByComparator) {

		List<DDLRecord> list = findByR_R(
			recordSetId, recordSetVersion, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByR_R_Last(
			long recordSetId, String recordSetVersion,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByR_R_Last(
			recordSetId, recordSetVersion, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("recordSetId=");
		sb.append(recordSetId);

		sb.append(", recordSetVersion=");
		sb.append(recordSetVersion);

		sb.append("}");

		throw new NoSuchRecordException(sb.toString());
	}

	/**
	 * Returns the last ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByR_R_Last(
		long recordSetId, String recordSetVersion,
		OrderByComparator<DDLRecord> orderByComparator) {

		int count = countByR_R(recordSetId, recordSetVersion);

		if (count == 0) {
			return null;
		}

		List<DDLRecord> list = findByR_R(
			recordSetId, recordSetVersion, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord[] findByR_R_PrevAndNext(
			long recordId, long recordSetId, String recordSetVersion,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		recordSetVersion = Objects.toString(recordSetVersion, "");

		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByR_R_PrevAndNext(
				session, ddlRecord, recordSetId, recordSetVersion,
				orderByComparator, true);

			array[1] = ddlRecord;

			array[2] = getByR_R_PrevAndNext(
				session, ddlRecord, recordSetId, recordSetVersion,
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

	protected DDLRecord getByR_R_PrevAndNext(
		Session session, DDLRecord ddlRecord, long recordSetId,
		String recordSetVersion, OrderByComparator<DDLRecord> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_DDLRECORD_WHERE);

		sb.append(_FINDER_COLUMN_R_R_RECORDSETID_2);

		boolean bindRecordSetVersion = false;

		if (recordSetVersion.isEmpty()) {
			sb.append(_FINDER_COLUMN_R_R_RECORDSETVERSION_3);
		}
		else {
			bindRecordSetVersion = true;

			sb.append(_FINDER_COLUMN_R_R_RECORDSETVERSION_2);
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
			sb.append(DDLRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(recordSetId);

		if (bindRecordSetVersion) {
			queryPos.add(recordSetVersion);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ddlRecord)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDLRecord> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddl records where recordSetId = &#63; and recordSetVersion = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 */
	@Override
	public void removeByR_R(long recordSetId, String recordSetVersion) {
		for (DDLRecord ddlRecord :
				findByR_R(
					recordSetId, recordSetVersion, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of ddl records where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByR_R(long recordSetId, String recordSetVersion) {
		recordSetVersion = Objects.toString(recordSetVersion, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByR_R;

			finderArgs = new Object[] {recordSetId, recordSetVersion};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDLRECORD_WHERE);

			sb.append(_FINDER_COLUMN_R_R_RECORDSETID_2);

			boolean bindRecordSetVersion = false;

			if (recordSetVersion.isEmpty()) {
				sb.append(_FINDER_COLUMN_R_R_RECORDSETVERSION_3);
			}
			else {
				bindRecordSetVersion = true;

				sb.append(_FINDER_COLUMN_R_R_RECORDSETVERSION_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(recordSetId);

				if (bindRecordSetVersion) {
					queryPos.add(recordSetVersion);
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

	private static final String _FINDER_COLUMN_R_R_RECORDSETID_2 =
		"ddlRecord.recordSetId = ? AND ";

	private static final String _FINDER_COLUMN_R_R_RECORDSETVERSION_2 =
		"ddlRecord.recordSetVersion = ?";

	private static final String _FINDER_COLUMN_R_R_RECORDSETVERSION_3 =
		"(ddlRecord.recordSetVersion IS NULL OR ddlRecord.recordSetVersion = '')";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the ddl records where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @return the matching ddl records
	 */
	@Override
	public List<DDLRecord> findByC_C(String className, long classPK) {
		return findByC_C(
			className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl records where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByC_C(
		String className, long classPK, int start, int end) {

		return findByC_C(className, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByC_C(
		String className, long classPK, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return findByC_C(
			className, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl records
	 */
	@Override
	public List<DDLRecord> findByC_C(
		String className, long classPK, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		className = Objects.toString(className, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {className, classPK};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				className, classPK, start, end, orderByComparator
			};
		}

		List<DDLRecord> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDLRecord>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecord ddlRecord : list) {
					if (!className.equals(ddlRecord.getClassName()) ||
						(classPK != ddlRecord.getClassPK())) {

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

			sb.append(_SQL_SELECT_DDLRECORD_WHERE);

			boolean bindClassName = false;

			if (className.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_CLASSNAME_3);
			}
			else {
				bindClassName = true;

				sb.append(_FINDER_COLUMN_C_C_CLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDLRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindClassName) {
					queryPos.add(className);
				}

				queryPos.add(classPK);

				list = (List<DDLRecord>)QueryUtil.list(
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
	 * Returns the first ddl record in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByC_C_First(
			String className, long classPK,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByC_C_First(
			className, classPK, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("className=");
		sb.append(className);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchRecordException(sb.toString());
	}

	/**
	 * Returns the first ddl record in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByC_C_First(
		String className, long classPK,
		OrderByComparator<DDLRecord> orderByComparator) {

		List<DDLRecord> list = findByC_C(
			className, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddl record in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record
	 * @throws NoSuchRecordException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord findByC_C_Last(
			String className, long classPK,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByC_C_Last(
			className, classPK, orderByComparator);

		if (ddlRecord != null) {
			return ddlRecord;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("className=");
		sb.append(className);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchRecordException(sb.toString());
	}

	/**
	 * Returns the last ddl record in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchByC_C_Last(
		String className, long classPK,
		OrderByComparator<DDLRecord> orderByComparator) {

		int count = countByC_C(className, classPK);

		if (count == 0) {
			return null;
		}

		List<DDLRecord> list = findByC_C(
			className, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddl records before and after the current ddl record in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param recordId the primary key of the current ddl record
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord[] findByC_C_PrevAndNext(
			long recordId, String className, long classPK,
			OrderByComparator<DDLRecord> orderByComparator)
		throws NoSuchRecordException {

		className = Objects.toString(className, "");

		DDLRecord ddlRecord = findByPrimaryKey(recordId);

		Session session = null;

		try {
			session = openSession();

			DDLRecord[] array = new DDLRecordImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, ddlRecord, className, classPK, orderByComparator,
				true);

			array[1] = ddlRecord;

			array[2] = getByC_C_PrevAndNext(
				session, ddlRecord, className, classPK, orderByComparator,
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

	protected DDLRecord getByC_C_PrevAndNext(
		Session session, DDLRecord ddlRecord, String className, long classPK,
		OrderByComparator<DDLRecord> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_DDLRECORD_WHERE);

		boolean bindClassName = false;

		if (className.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_C_CLASSNAME_3);
		}
		else {
			bindClassName = true;

			sb.append(_FINDER_COLUMN_C_C_CLASSNAME_2);
		}

		sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			sb.append(DDLRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindClassName) {
			queryPos.add(className);
		}

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ddlRecord)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDLRecord> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddl records where className = &#63; and classPK = &#63; from the database.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(String className, long classPK) {
		for (DDLRecord ddlRecord :
				findByC_C(
					className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of ddl records where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @return the number of matching ddl records
	 */
	@Override
	public int countByC_C(String className, long classPK) {
		className = Objects.toString(className, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C;

			finderArgs = new Object[] {className, classPK};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDLRECORD_WHERE);

			boolean bindClassName = false;

			if (className.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_CLASSNAME_3);
			}
			else {
				bindClassName = true;

				sb.append(_FINDER_COLUMN_C_C_CLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindClassName) {
					queryPos.add(className);
				}

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAME_2 =
		"ddlRecord.className = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSNAME_3 =
		"(ddlRecord.className IS NULL OR ddlRecord.className = '') AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"ddlRecord.classPK = ?";

	public DDLRecordPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DDLRecord.class);

		setModelImplClass(DDLRecordImpl.class);
		setModelPKClass(long.class);

		setTable(DDLRecordTable.INSTANCE);
	}

	/**
	 * Caches the ddl record in the entity cache if it is enabled.
	 *
	 * @param ddlRecord the ddl record
	 */
	@Override
	public void cacheResult(DDLRecord ddlRecord) {
		if (ddlRecord.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			DDLRecordImpl.class, ddlRecord.getPrimaryKey(), ddlRecord);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {ddlRecord.getUuid(), ddlRecord.getGroupId()},
			ddlRecord);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the ddl records in the entity cache if it is enabled.
	 *
	 * @param ddlRecords the ddl records
	 */
	@Override
	public void cacheResult(List<DDLRecord> ddlRecords) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ddlRecords.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (DDLRecord ddlRecord : ddlRecords) {
			if (ddlRecord.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					DDLRecordImpl.class, ddlRecord.getPrimaryKey()) == null) {

				cacheResult(ddlRecord);
			}
		}
	}

	/**
	 * Clears the cache for all ddl records.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDLRecordImpl.class);

		finderCache.clearCache(DDLRecordImpl.class);
	}

	/**
	 * Clears the cache for the ddl record.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDLRecord ddlRecord) {
		entityCache.removeResult(DDLRecordImpl.class, ddlRecord);
	}

	@Override
	public void clearCache(List<DDLRecord> ddlRecords) {
		for (DDLRecord ddlRecord : ddlRecords) {
			entityCache.removeResult(DDLRecordImpl.class, ddlRecord);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(DDLRecordImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(DDLRecordImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DDLRecordModelImpl ddlRecordModelImpl) {

		Object[] args = new Object[] {
			ddlRecordModelImpl.getUuid(), ddlRecordModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, ddlRecordModelImpl);
	}

	/**
	 * Creates a new ddl record with the primary key. Does not add the ddl record to the database.
	 *
	 * @param recordId the primary key for the new ddl record
	 * @return the new ddl record
	 */
	@Override
	public DDLRecord create(long recordId) {
		DDLRecord ddlRecord = new DDLRecordImpl();

		ddlRecord.setNew(true);
		ddlRecord.setPrimaryKey(recordId);

		String uuid = PortalUUIDUtil.generate();

		ddlRecord.setUuid(uuid);

		ddlRecord.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ddlRecord;
	}

	/**
	 * Removes the ddl record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record that was removed
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord remove(long recordId) throws NoSuchRecordException {
		return remove((Serializable)recordId);
	}

	/**
	 * Removes the ddl record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ddl record
	 * @return the ddl record that was removed
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord remove(Serializable primaryKey)
		throws NoSuchRecordException {

		Session session = null;

		try {
			session = openSession();

			DDLRecord ddlRecord = (DDLRecord)session.get(
				DDLRecordImpl.class, primaryKey);

			if (ddlRecord == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRecordException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ddlRecord);
		}
		catch (NoSuchRecordException noSuchEntityException) {
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
	protected DDLRecord removeImpl(DDLRecord ddlRecord) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddlRecord)) {
				ddlRecord = (DDLRecord)session.get(
					DDLRecordImpl.class, ddlRecord.getPrimaryKeyObj());
			}

			if ((ddlRecord != null) &&
				ctPersistenceHelper.isRemove(ddlRecord)) {

				session.delete(ddlRecord);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
		boolean isNew = ddlRecord.isNew();

		if (!(ddlRecord instanceof DDLRecordModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ddlRecord.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ddlRecord);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ddlRecord proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DDLRecord implementation " +
					ddlRecord.getClass());
		}

		DDLRecordModelImpl ddlRecordModelImpl = (DDLRecordModelImpl)ddlRecord;

		if (Validator.isNull(ddlRecord.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddlRecord.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (ddlRecord.getCreateDate() == null)) {
			if (serviceContext == null) {
				ddlRecord.setCreateDate(date);
			}
			else {
				ddlRecord.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!ddlRecordModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ddlRecord.setModifiedDate(date);
			}
			else {
				ddlRecord.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(ddlRecord)) {
				if (!isNew) {
					session.evict(
						DDLRecordImpl.class, ddlRecord.getPrimaryKeyObj());
				}

				session.save(ddlRecord);
			}
			else {
				ddlRecord = (DDLRecord)session.merge(ddlRecord);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddlRecord.getCtCollectionId() != 0) {
			if (isNew) {
				ddlRecord.setNew(false);
			}

			ddlRecord.resetOriginalValues();

			return ddlRecord;
		}

		entityCache.putResult(
			DDLRecordImpl.class, ddlRecordModelImpl, false, true);

		cacheUniqueFindersCache(ddlRecordModelImpl);

		if (isNew) {
			ddlRecord.setNew(false);
		}

		ddlRecord.resetOriginalValues();

		return ddlRecord;
	}

	/**
	 * Returns the ddl record with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddl record
	 * @return the ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRecordException {

		DDLRecord ddlRecord = fetchByPrimaryKey(primaryKey);

		if (ddlRecord == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRecordException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ddlRecord;
	}

	/**
	 * Returns the ddl record with the primary key or throws a <code>NoSuchRecordException</code> if it could not be found.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record
	 * @throws NoSuchRecordException if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord findByPrimaryKey(long recordId)
		throws NoSuchRecordException {

		return findByPrimaryKey((Serializable)recordId);
	}

	/**
	 * Returns the ddl record with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddl record
	 * @return the ddl record, or <code>null</code> if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(DDLRecord.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		DDLRecord ddlRecord = null;

		Session session = null;

		try {
			session = openSession();

			ddlRecord = (DDLRecord)session.get(DDLRecordImpl.class, primaryKey);

			if (ddlRecord != null) {
				cacheResult(ddlRecord);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return ddlRecord;
	}

	/**
	 * Returns the ddl record with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record, or <code>null</code> if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord fetchByPrimaryKey(long recordId) {
		return fetchByPrimaryKey((Serializable)recordId);
	}

	@Override
	public Map<Serializable, DDLRecord> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(DDLRecord.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDLRecord> map =
			new HashMap<Serializable, DDLRecord>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDLRecord ddlRecord = fetchByPrimaryKey(primaryKey);

			if (ddlRecord != null) {
				map.put(primaryKey, ddlRecord);
			}

			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

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

			for (DDLRecord ddlRecord : (List<DDLRecord>)query.list()) {
				map.put(ddlRecord.getPrimaryKeyObj(), ddlRecord);

				cacheResult(ddlRecord);
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
	 * Returns all the ddl records.
	 *
	 * @return the ddl records
	 */
	@Override
	public List<DDLRecord> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of ddl records
	 */
	@Override
	public List<DDLRecord> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddl records
	 */
	@Override
	public List<DDLRecord> findAll(
		int start, int end, OrderByComparator<DDLRecord> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddl records
	 */
	@Override
	public List<DDLRecord> findAll(
		int start, int end, OrderByComparator<DDLRecord> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

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

		List<DDLRecord> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDLRecord>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DDLRECORD);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DDLRECORD;

				sql = sql.concat(DDLRecordModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DDLRecord>)QueryUtil.list(
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
	 * Removes all the ddl records from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDLRecord ddlRecord : findAll()) {
			remove(ddlRecord);
		}
	}

	/**
	 * Returns the number of ddl records.
	 *
	 * @return the number of ddl records
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDLRecord.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_DDLRECORD);

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
		return "recordId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DDLRECORD;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return DDLRecordModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DDLRecord";
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
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("versionUserId");
		ctStrictColumnNames.add("versionUserName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("DDMStorageId");
		ctStrictColumnNames.add("recordSetId");
		ctStrictColumnNames.add("recordSetVersion");
		ctStrictColumnNames.add("className");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("version");
		ctStrictColumnNames.add("displayIndex");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("recordId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});
	}

	/**
	 * Initializes the ddl record persistence.
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

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindByRecordSetId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRecordSetId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"recordSetId"}, true);

		_finderPathWithoutPaginationFindByRecordSetId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRecordSetId",
			new String[] {Long.class.getName()}, new String[] {"recordSetId"},
			true);

		_finderPathCountByRecordSetId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRecordSetId",
			new String[] {Long.class.getName()}, new String[] {"recordSetId"},
			false);

		_finderPathWithPaginationFindByR_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"recordSetId", "userId"}, true);

		_finderPathWithoutPaginationFindByR_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"recordSetId", "userId"}, true);

		_finderPathCountByR_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"recordSetId", "userId"}, false);

		_finderPathWithPaginationFindByR_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"recordSetId", "recordSetVersion"}, true);

		_finderPathWithoutPaginationFindByR_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_R",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"recordSetId", "recordSetVersion"}, true);

		_finderPathCountByR_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_R",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"recordSetId", "recordSetVersion"}, false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"className", "classPK"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"className", "classPK"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"className", "classPK"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DDLRecordImpl.class.getName());
	}

	@Override
	@Reference(
		target = DDLPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DDLPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DDLPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_DDLRECORD =
		"SELECT ddlRecord FROM DDLRecord ddlRecord";

	private static final String _SQL_SELECT_DDLRECORD_WHERE =
		"SELECT ddlRecord FROM DDLRecord ddlRecord WHERE ";

	private static final String _SQL_COUNT_DDLRECORD =
		"SELECT COUNT(ddlRecord) FROM DDLRecord ddlRecord";

	private static final String _SQL_COUNT_DDLRECORD_WHERE =
		"SELECT COUNT(ddlRecord) FROM DDLRecord ddlRecord WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ddlRecord.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DDLRecord exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DDLRecord exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private DDLRecordModelArgumentsResolver _ddlRecordModelArgumentsResolver;

}