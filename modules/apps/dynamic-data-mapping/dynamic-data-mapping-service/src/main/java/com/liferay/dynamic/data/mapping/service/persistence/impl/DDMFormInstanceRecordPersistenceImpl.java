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

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceRecordException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordTable;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceRecordPersistence;
import com.liferay.dynamic.data.mapping.service.persistence.impl.constants.DDMPersistenceConstants;
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
 * The persistence implementation for the ddm form instance record service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DDMFormInstanceRecordPersistence.class)
public class DDMFormInstanceRecordPersistenceImpl
	extends BasePersistenceImpl<DDMFormInstanceRecord>
	implements DDMFormInstanceRecordPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DDMFormInstanceRecordUtil</code> to access the ddm form instance record persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DDMFormInstanceRecordImpl.class.getName();

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

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

		List<DDMFormInstanceRecord> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFormInstanceRecord>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecord ddmFormInstanceRecord : list) {
					if (!uuid.equals(ddmFormInstanceRecord.getUuid())) {
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

			sb.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

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
				sb.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
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

				list = (List<DDMFormInstanceRecord>)QueryUtil.list(
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
	 * Returns the first ddm form instance record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByUuid_First(
			String uuid,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {

		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByUuid_First(
			uuid, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchFormInstanceRecordException(sb.toString());
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByUuid_First(
		String uuid,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		List<DDMFormInstanceRecord> list = findByUuid(
			uuid, 0, 1, orderByComparator);

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
	public DDMFormInstanceRecord findByUuid_Last(
			String uuid,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {

		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByUuid_Last(
			uuid, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchFormInstanceRecordException(sb.toString());
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByUuid_Last(
		String uuid,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceRecord> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

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

		uuid = Objects.toString(uuid, "");

		DDMFormInstanceRecord ddmFormInstanceRecord = findByPrimaryKey(
			formInstanceRecordId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecord[] array = new DDMFormInstanceRecordImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, ddmFormInstanceRecord, uuid, orderByComparator, true);

			array[1] = ddmFormInstanceRecord;

			array[2] = getByUuid_PrevAndNext(
				session, ddmFormInstanceRecord, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMFormInstanceRecord getByUuid_PrevAndNext(
		Session session, DDMFormInstanceRecord ddmFormInstanceRecord,
		String uuid, OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

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
			sb.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(
						ddmFormInstanceRecord)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMFormInstanceRecord> list = query.list();

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
		for (DDMFormInstanceRecord ddmFormInstanceRecord :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

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
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

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

			sb.append(_SQL_COUNT_DDMFORMINSTANCERECORD_WHERE);

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
		"ddmFormInstanceRecord.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(ddmFormInstanceRecord.uuid IS NULL OR ddmFormInstanceRecord.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the ddm form instance record where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchFormInstanceRecordException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByUUID_G(String uuid, long groupId)
		throws NoSuchFormInstanceRecordException {

		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByUUID_G(
			uuid, groupId);

		if (ddmFormInstanceRecord == null) {
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

			throw new NoSuchFormInstanceRecordException(sb.toString());
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
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof DDMFormInstanceRecord) {
			DDMFormInstanceRecord ddmFormInstanceRecord =
				(DDMFormInstanceRecord)result;

			if (!Objects.equals(uuid, ddmFormInstanceRecord.getUuid()) ||
				(groupId != ddmFormInstanceRecord.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

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

				List<DDMFormInstanceRecord> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					DDMFormInstanceRecord ddmFormInstanceRecord = list.get(0);

					result = ddmFormInstanceRecord;

					cacheResult(ddmFormInstanceRecord);
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

		DDMFormInstanceRecord ddmFormInstanceRecord = findByUUID_G(
			uuid, groupId);

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
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

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

			sb.append(_SQL_COUNT_DDMFORMINSTANCERECORD_WHERE);

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
		"ddmFormInstanceRecord.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(ddmFormInstanceRecord.uuid IS NULL OR ddmFormInstanceRecord.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"ddmFormInstanceRecord.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the ddm form instance records where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
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
	public List<DDMFormInstanceRecord> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

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

		List<DDMFormInstanceRecord> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFormInstanceRecord>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecord ddmFormInstanceRecord : list) {
					if (!uuid.equals(ddmFormInstanceRecord.getUuid()) ||
						(companyId != ddmFormInstanceRecord.getCompanyId())) {

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

			sb.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

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
				sb.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
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

				list = (List<DDMFormInstanceRecord>)QueryUtil.list(
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
	 * Returns the first ddm form instance record in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {

		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFormInstanceRecordException(sb.toString());
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
	public DDMFormInstanceRecord fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		List<DDMFormInstanceRecord> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

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
	public DDMFormInstanceRecord findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {

		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFormInstanceRecordException(sb.toString());
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
	public DDMFormInstanceRecord fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceRecord> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

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

		uuid = Objects.toString(uuid, "");

		DDMFormInstanceRecord ddmFormInstanceRecord = findByPrimaryKey(
			formInstanceRecordId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecord[] array = new DDMFormInstanceRecordImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, ddmFormInstanceRecord, uuid, companyId,
				orderByComparator, true);

			array[1] = ddmFormInstanceRecord;

			array[2] = getByUuid_C_PrevAndNext(
				session, ddmFormInstanceRecord, uuid, companyId,
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

	protected DDMFormInstanceRecord getByUuid_C_PrevAndNext(
		Session session, DDMFormInstanceRecord ddmFormInstanceRecord,
		String uuid, long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

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
			sb.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(
						ddmFormInstanceRecord)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMFormInstanceRecord> list = query.list();

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
		for (DDMFormInstanceRecord ddmFormInstanceRecord :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

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

			sb.append(_SQL_COUNT_DDMFORMINSTANCERECORD_WHERE);

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
		"ddmFormInstanceRecord.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(ddmFormInstanceRecord.uuid IS NULL OR ddmFormInstanceRecord.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"ddmFormInstanceRecord.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the ddm form instance records where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

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

		List<DDMFormInstanceRecord> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFormInstanceRecord>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecord ddmFormInstanceRecord : list) {
					if (companyId != ddmFormInstanceRecord.getCompanyId()) {
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

			sb.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<DDMFormInstanceRecord>)QueryUtil.list(
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
	 * Returns the first ddm form instance record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByCompanyId_First(
			long companyId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {

		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFormInstanceRecordException(sb.toString());
	}

	/**
	 * Returns the first ddm form instance record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByCompanyId_First(
		long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		List<DDMFormInstanceRecord> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

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
	public DDMFormInstanceRecord findByCompanyId_Last(
			long companyId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {

		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFormInstanceRecordException(sb.toString());
	}

	/**
	 * Returns the last ddm form instance record in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance record, or <code>null</code> if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceRecord> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

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

		DDMFormInstanceRecord ddmFormInstanceRecord = findByPrimaryKey(
			formInstanceRecordId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecord[] array = new DDMFormInstanceRecordImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, ddmFormInstanceRecord, companyId, orderByComparator,
				true);

			array[1] = ddmFormInstanceRecord;

			array[2] = getByCompanyId_PrevAndNext(
				session, ddmFormInstanceRecord, companyId, orderByComparator,
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

	protected DDMFormInstanceRecord getByCompanyId_PrevAndNext(
		Session session, DDMFormInstanceRecord ddmFormInstanceRecord,
		long companyId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

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
			sb.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmFormInstanceRecord)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMFormInstanceRecord> list = query.list();

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
		for (DDMFormInstanceRecord ddmFormInstanceRecord :
				findByCompanyId(
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
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCompanyId;

			finderArgs = new Object[] {companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DDMFORMINSTANCERECORD_WHERE);

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
		"ddmFormInstanceRecord.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByFormInstanceId;
	private FinderPath _finderPathWithoutPaginationFindByFormInstanceId;
	private FinderPath _finderPathCountByFormInstanceId;

	/**
	 * Returns all the ddm form instance records where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @return the matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByFormInstanceId(
		long formInstanceId) {

		return findByFormInstanceId(
			formInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance records where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
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

		return findByFormInstanceId(
			formInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByFormInstanceId(
		long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByFormInstanceId;
				finderArgs = new Object[] {formInstanceId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByFormInstanceId;
			finderArgs = new Object[] {
				formInstanceId, start, end, orderByComparator
			};
		}

		List<DDMFormInstanceRecord> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFormInstanceRecord>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecord ddmFormInstanceRecord : list) {
					if (formInstanceId !=
							ddmFormInstanceRecord.getFormInstanceId()) {

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

			sb.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

			sb.append(_FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceId);

				list = (List<DDMFormInstanceRecord>)QueryUtil.list(
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

		DDMFormInstanceRecord ddmFormInstanceRecord =
			fetchByFormInstanceId_First(formInstanceId, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("formInstanceId=");
		sb.append(formInstanceId);

		sb.append("}");

		throw new NoSuchFormInstanceRecordException(sb.toString());
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

		List<DDMFormInstanceRecord> list = findByFormInstanceId(
			formInstanceId, 0, 1, orderByComparator);

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

		DDMFormInstanceRecord ddmFormInstanceRecord =
			fetchByFormInstanceId_Last(formInstanceId, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("formInstanceId=");
		sb.append(formInstanceId);

		sb.append("}");

		throw new NoSuchFormInstanceRecordException(sb.toString());
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

		List<DDMFormInstanceRecord> list = findByFormInstanceId(
			formInstanceId, count - 1, count, orderByComparator);

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

		DDMFormInstanceRecord ddmFormInstanceRecord = findByPrimaryKey(
			formInstanceRecordId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecord[] array = new DDMFormInstanceRecordImpl[3];

			array[0] = getByFormInstanceId_PrevAndNext(
				session, ddmFormInstanceRecord, formInstanceId,
				orderByComparator, true);

			array[1] = ddmFormInstanceRecord;

			array[2] = getByFormInstanceId_PrevAndNext(
				session, ddmFormInstanceRecord, formInstanceId,
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

	protected DDMFormInstanceRecord getByFormInstanceId_PrevAndNext(
		Session session, DDMFormInstanceRecord ddmFormInstanceRecord,
		long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

		sb.append(_FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2);

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
			sb.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(formInstanceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmFormInstanceRecord)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMFormInstanceRecord> list = query.list();

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
		for (DDMFormInstanceRecord ddmFormInstanceRecord :
				findByFormInstanceId(
					formInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFormInstanceId;

			finderArgs = new Object[] {formInstanceId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DDMFORMINSTANCERECORD_WHERE);

			sb.append(_FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceId);

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

	private static final String _FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2 =
		"ddmFormInstanceRecord.formInstanceId = ?";

	private FinderPath _finderPathWithPaginationFindByU_F;
	private FinderPath _finderPathWithoutPaginationFindByU_F;
	private FinderPath _finderPathCountByU_F;

	/**
	 * Returns all the ddm form instance records where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @return the matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByU_F(
		long userId, long formInstanceId) {

		return findByU_F(
			userId, formInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance records where userId = &#63; and formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByU_F(
		long userId, long formInstanceId, int start, int end) {

		return findByU_F(userId, formInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where userId = &#63; and formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
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
	public List<DDMFormInstanceRecord> findByU_F(
		long userId, long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return findByU_F(
			userId, formInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where userId = &#63; and formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByU_F(
		long userId, long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByU_F;
				finderArgs = new Object[] {userId, formInstanceId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByU_F;
			finderArgs = new Object[] {
				userId, formInstanceId, start, end, orderByComparator
			};
		}

		List<DDMFormInstanceRecord> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFormInstanceRecord>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecord ddmFormInstanceRecord : list) {
					if ((userId != ddmFormInstanceRecord.getUserId()) ||
						(formInstanceId !=
							ddmFormInstanceRecord.getFormInstanceId())) {

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

			sb.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

			sb.append(_FINDER_COLUMN_U_F_USERID_2);

			sb.append(_FINDER_COLUMN_U_F_FORMINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(formInstanceId);

				list = (List<DDMFormInstanceRecord>)QueryUtil.list(
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
	 * Returns the first ddm form instance record in the ordered set where userId = &#63; and formInstanceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByU_F_First(
			long userId, long formInstanceId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {

		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByU_F_First(
			userId, formInstanceId, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", formInstanceId=");
		sb.append(formInstanceId);

		sb.append("}");

		throw new NoSuchFormInstanceRecordException(sb.toString());
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
	public DDMFormInstanceRecord fetchByU_F_First(
		long userId, long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		List<DDMFormInstanceRecord> list = findByU_F(
			userId, formInstanceId, 0, 1, orderByComparator);

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
	public DDMFormInstanceRecord findByU_F_Last(
			long userId, long formInstanceId,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {

		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByU_F_Last(
			userId, formInstanceId, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", formInstanceId=");
		sb.append(formInstanceId);

		sb.append("}");

		throw new NoSuchFormInstanceRecordException(sb.toString());
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
	public DDMFormInstanceRecord fetchByU_F_Last(
		long userId, long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		int count = countByU_F(userId, formInstanceId);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceRecord> list = findByU_F(
			userId, formInstanceId, count - 1, count, orderByComparator);

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

		DDMFormInstanceRecord ddmFormInstanceRecord = findByPrimaryKey(
			formInstanceRecordId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecord[] array = new DDMFormInstanceRecordImpl[3];

			array[0] = getByU_F_PrevAndNext(
				session, ddmFormInstanceRecord, userId, formInstanceId,
				orderByComparator, true);

			array[1] = ddmFormInstanceRecord;

			array[2] = getByU_F_PrevAndNext(
				session, ddmFormInstanceRecord, userId, formInstanceId,
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

	protected DDMFormInstanceRecord getByU_F_PrevAndNext(
		Session session, DDMFormInstanceRecord ddmFormInstanceRecord,
		long userId, long formInstanceId,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

		sb.append(_FINDER_COLUMN_U_F_USERID_2);

		sb.append(_FINDER_COLUMN_U_F_FORMINSTANCEID_2);

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
			sb.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		queryPos.add(formInstanceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmFormInstanceRecord)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMFormInstanceRecord> list = query.list();

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
		for (DDMFormInstanceRecord ddmFormInstanceRecord :
				findByU_F(
					userId, formInstanceId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

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
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByU_F;

			finderArgs = new Object[] {userId, formInstanceId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDMFORMINSTANCERECORD_WHERE);

			sb.append(_FINDER_COLUMN_U_F_USERID_2);

			sb.append(_FINDER_COLUMN_U_F_FORMINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(formInstanceId);

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

	private static final String _FINDER_COLUMN_U_F_USERID_2 =
		"ddmFormInstanceRecord.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_F_FORMINSTANCEID_2 =
		"ddmFormInstanceRecord.formInstanceId = ?";

	private FinderPath _finderPathWithPaginationFindByF_F;
	private FinderPath _finderPathWithoutPaginationFindByF_F;
	private FinderPath _finderPathCountByF_F;

	/**
	 * Returns all the ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @return the matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByF_F(
		long formInstanceId, String formInstanceVersion) {

		return findByF_F(
			formInstanceId, formInstanceVersion, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @return the range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByF_F(
		long formInstanceId, String formInstanceVersion, int start, int end) {

		return findByF_F(formInstanceId, formInstanceVersion, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
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
	public List<DDMFormInstanceRecord> findByF_F(
		long formInstanceId, String formInstanceVersion, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return findByF_F(
			formInstanceId, formInstanceVersion, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findByF_F(
		long formInstanceId, String formInstanceVersion, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean useFinderCache) {

		formInstanceVersion = Objects.toString(formInstanceVersion, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByF_F;
				finderArgs = new Object[] {formInstanceId, formInstanceVersion};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByF_F;
			finderArgs = new Object[] {
				formInstanceId, formInstanceVersion, start, end,
				orderByComparator
			};
		}

		List<DDMFormInstanceRecord> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFormInstanceRecord>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceRecord ddmFormInstanceRecord : list) {
					if ((formInstanceId !=
							ddmFormInstanceRecord.getFormInstanceId()) ||
						!formInstanceVersion.equals(
							ddmFormInstanceRecord.getFormInstanceVersion())) {

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

			sb.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

			sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEID_2);

			boolean bindFormInstanceVersion = false;

			if (formInstanceVersion.isEmpty()) {
				sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_3);
			}
			else {
				bindFormInstanceVersion = true;

				sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceId);

				if (bindFormInstanceVersion) {
					queryPos.add(formInstanceVersion);
				}

				list = (List<DDMFormInstanceRecord>)QueryUtil.list(
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
	 * Returns the first ddm form instance record in the ordered set where formInstanceId = &#63; and formInstanceVersion = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param formInstanceVersion the form instance version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a matching ddm form instance record could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByF_F_First(
			long formInstanceId, String formInstanceVersion,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {

		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByF_F_First(
			formInstanceId, formInstanceVersion, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("formInstanceId=");
		sb.append(formInstanceId);

		sb.append(", formInstanceVersion=");
		sb.append(formInstanceVersion);

		sb.append("}");

		throw new NoSuchFormInstanceRecordException(sb.toString());
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
	public DDMFormInstanceRecord fetchByF_F_First(
		long formInstanceId, String formInstanceVersion,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		List<DDMFormInstanceRecord> list = findByF_F(
			formInstanceId, formInstanceVersion, 0, 1, orderByComparator);

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
	public DDMFormInstanceRecord findByF_F_Last(
			long formInstanceId, String formInstanceVersion,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws NoSuchFormInstanceRecordException {

		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByF_F_Last(
			formInstanceId, formInstanceVersion, orderByComparator);

		if (ddmFormInstanceRecord != null) {
			return ddmFormInstanceRecord;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("formInstanceId=");
		sb.append(formInstanceId);

		sb.append(", formInstanceVersion=");
		sb.append(formInstanceVersion);

		sb.append("}");

		throw new NoSuchFormInstanceRecordException(sb.toString());
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
	public DDMFormInstanceRecord fetchByF_F_Last(
		long formInstanceId, String formInstanceVersion,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		int count = countByF_F(formInstanceId, formInstanceVersion);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceRecord> list = findByF_F(
			formInstanceId, formInstanceVersion, count - 1, count,
			orderByComparator);

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

		formInstanceVersion = Objects.toString(formInstanceVersion, "");

		DDMFormInstanceRecord ddmFormInstanceRecord = findByPrimaryKey(
			formInstanceRecordId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceRecord[] array = new DDMFormInstanceRecordImpl[3];

			array[0] = getByF_F_PrevAndNext(
				session, ddmFormInstanceRecord, formInstanceId,
				formInstanceVersion, orderByComparator, true);

			array[1] = ddmFormInstanceRecord;

			array[2] = getByF_F_PrevAndNext(
				session, ddmFormInstanceRecord, formInstanceId,
				formInstanceVersion, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMFormInstanceRecord getByF_F_PrevAndNext(
		Session session, DDMFormInstanceRecord ddmFormInstanceRecord,
		long formInstanceId, String formInstanceVersion,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMFORMINSTANCERECORD_WHERE);

		sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEID_2);

		boolean bindFormInstanceVersion = false;

		if (formInstanceVersion.isEmpty()) {
			sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_3);
		}
		else {
			bindFormInstanceVersion = true;

			sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_2);
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
			sb.append(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(formInstanceId);

		if (bindFormInstanceVersion) {
			queryPos.add(formInstanceVersion);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmFormInstanceRecord)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMFormInstanceRecord> list = query.list();

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
		for (DDMFormInstanceRecord ddmFormInstanceRecord :
				findByF_F(
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
		formInstanceVersion = Objects.toString(formInstanceVersion, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByF_F;

			finderArgs = new Object[] {formInstanceId, formInstanceVersion};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDMFORMINSTANCERECORD_WHERE);

			sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEID_2);

			boolean bindFormInstanceVersion = false;

			if (formInstanceVersion.isEmpty()) {
				sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_3);
			}
			else {
				bindFormInstanceVersion = true;

				sb.append(_FINDER_COLUMN_F_F_FORMINSTANCEVERSION_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(formInstanceId);

				if (bindFormInstanceVersion) {
					queryPos.add(formInstanceVersion);
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

	private static final String _FINDER_COLUMN_F_F_FORMINSTANCEID_2 =
		"ddmFormInstanceRecord.formInstanceId = ? AND ";

	private static final String _FINDER_COLUMN_F_F_FORMINSTANCEVERSION_2 =
		"ddmFormInstanceRecord.formInstanceVersion = ?";

	private static final String _FINDER_COLUMN_F_F_FORMINSTANCEVERSION_3 =
		"(ddmFormInstanceRecord.formInstanceVersion IS NULL OR ddmFormInstanceRecord.formInstanceVersion = '')";

	public DDMFormInstanceRecordPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DDMFormInstanceRecord.class);

		setModelImplClass(DDMFormInstanceRecordImpl.class);
		setModelPKClass(long.class);

		setTable(DDMFormInstanceRecordTable.INSTANCE);
	}

	/**
	 * Caches the ddm form instance record in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceRecord the ddm form instance record
	 */
	@Override
	public void cacheResult(DDMFormInstanceRecord ddmFormInstanceRecord) {
		if (ddmFormInstanceRecord.getCtCollectionId() != 0) {
			ddmFormInstanceRecord.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			DDMFormInstanceRecordImpl.class,
			ddmFormInstanceRecord.getPrimaryKey(), ddmFormInstanceRecord);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				ddmFormInstanceRecord.getUuid(),
				ddmFormInstanceRecord.getGroupId()
			},
			ddmFormInstanceRecord);

		ddmFormInstanceRecord.resetOriginalValues();
	}

	/**
	 * Caches the ddm form instance records in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceRecords the ddm form instance records
	 */
	@Override
	public void cacheResult(
		List<DDMFormInstanceRecord> ddmFormInstanceRecords) {

		for (DDMFormInstanceRecord ddmFormInstanceRecord :
				ddmFormInstanceRecords) {

			if (ddmFormInstanceRecord.getCtCollectionId() != 0) {
				ddmFormInstanceRecord.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
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
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
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
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMFormInstanceRecord ddmFormInstanceRecord) {
		entityCache.removeResult(
			DDMFormInstanceRecordImpl.class,
			ddmFormInstanceRecord.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(DDMFormInstanceRecordModelImpl)ddmFormInstanceRecord, true);
	}

	@Override
	public void clearCache(List<DDMFormInstanceRecord> ddmFormInstanceRecords) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMFormInstanceRecord ddmFormInstanceRecord :
				ddmFormInstanceRecords) {

			entityCache.removeResult(
				DDMFormInstanceRecordImpl.class,
				ddmFormInstanceRecord.getPrimaryKey());

			clearUniqueFindersCache(
				(DDMFormInstanceRecordModelImpl)ddmFormInstanceRecord, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				DDMFormInstanceRecordImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMFormInstanceRecordModelImpl ddmFormInstanceRecordModelImpl) {

		Object[] args = new Object[] {
			ddmFormInstanceRecordModelImpl.getUuid(),
			ddmFormInstanceRecordModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, ddmFormInstanceRecordModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		DDMFormInstanceRecordModelImpl ddmFormInstanceRecordModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				ddmFormInstanceRecordModelImpl.getUuid(),
				ddmFormInstanceRecordModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((ddmFormInstanceRecordModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ddmFormInstanceRecordModelImpl.getOriginalUuid(),
				ddmFormInstanceRecordModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
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
		DDMFormInstanceRecord ddmFormInstanceRecord =
			new DDMFormInstanceRecordImpl();

		ddmFormInstanceRecord.setNew(true);
		ddmFormInstanceRecord.setPrimaryKey(formInstanceRecordId);

		String uuid = PortalUUIDUtil.generate();

		ddmFormInstanceRecord.setUuid(uuid);

		ddmFormInstanceRecord.setCompanyId(CompanyThreadLocal.getCompanyId());

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

			DDMFormInstanceRecord ddmFormInstanceRecord =
				(DDMFormInstanceRecord)session.get(
					DDMFormInstanceRecordImpl.class, primaryKey);

			if (ddmFormInstanceRecord == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFormInstanceRecordException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ddmFormInstanceRecord);
		}
		catch (NoSuchFormInstanceRecordException noSuchEntityException) {
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
	protected DDMFormInstanceRecord removeImpl(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		if (!ctPersistenceHelper.isRemove(ddmFormInstanceRecord)) {
			return ddmFormInstanceRecord;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmFormInstanceRecord)) {
				ddmFormInstanceRecord = (DDMFormInstanceRecord)session.get(
					DDMFormInstanceRecordImpl.class,
					ddmFormInstanceRecord.getPrimaryKeyObj());
			}

			if (ddmFormInstanceRecord != null) {
				session.delete(ddmFormInstanceRecord);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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

		boolean isNew = ddmFormInstanceRecord.isNew();

		if (!(ddmFormInstanceRecord instanceof
				DDMFormInstanceRecordModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ddmFormInstanceRecord.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ddmFormInstanceRecord);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ddmFormInstanceRecord proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DDMFormInstanceRecord implementation " +
					ddmFormInstanceRecord.getClass());
		}

		DDMFormInstanceRecordModelImpl ddmFormInstanceRecordModelImpl =
			(DDMFormInstanceRecordModelImpl)ddmFormInstanceRecord;

		if (Validator.isNull(ddmFormInstanceRecord.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddmFormInstanceRecord.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ddmFormInstanceRecord.getCreateDate() == null)) {
			if (serviceContext == null) {
				ddmFormInstanceRecord.setCreateDate(now);
			}
			else {
				ddmFormInstanceRecord.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!ddmFormInstanceRecordModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ddmFormInstanceRecord.setModifiedDate(now);
			}
			else {
				ddmFormInstanceRecord.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(ddmFormInstanceRecord)) {
				if (!isNew) {
					session.evict(
						DDMFormInstanceRecordImpl.class,
						ddmFormInstanceRecord.getPrimaryKeyObj());
				}

				session.save(ddmFormInstanceRecord);

				ddmFormInstanceRecord.setNew(false);
			}
			else {
				ddmFormInstanceRecord = (DDMFormInstanceRecord)session.merge(
					ddmFormInstanceRecord);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddmFormInstanceRecord.getCtCollectionId() != 0) {
			ddmFormInstanceRecord.resetOriginalValues();

			return ddmFormInstanceRecord;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				ddmFormInstanceRecordModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				ddmFormInstanceRecordModelImpl.getUuid(),
				ddmFormInstanceRecordModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {ddmFormInstanceRecordModelImpl.getCompanyId()};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {
				ddmFormInstanceRecordModelImpl.getFormInstanceId()
			};

			finderCache.removeResult(_finderPathCountByFormInstanceId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByFormInstanceId, args);

			args = new Object[] {
				ddmFormInstanceRecordModelImpl.getUserId(),
				ddmFormInstanceRecordModelImpl.getFormInstanceId()
			};

			finderCache.removeResult(_finderPathCountByU_F, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByU_F, args);

			args = new Object[] {
				ddmFormInstanceRecordModelImpl.getFormInstanceId(),
				ddmFormInstanceRecordModelImpl.getFormInstanceVersion()
			};

			finderCache.removeResult(_finderPathCountByF_F, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByF_F, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ddmFormInstanceRecordModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ddmFormInstanceRecordModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {ddmFormInstanceRecordModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((ddmFormInstanceRecordModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ddmFormInstanceRecordModelImpl.getOriginalUuid(),
					ddmFormInstanceRecordModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					ddmFormInstanceRecordModelImpl.getUuid(),
					ddmFormInstanceRecordModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((ddmFormInstanceRecordModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ddmFormInstanceRecordModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {
					ddmFormInstanceRecordModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((ddmFormInstanceRecordModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFormInstanceId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ddmFormInstanceRecordModelImpl.getOriginalFormInstanceId()
				};

				finderCache.removeResult(
					_finderPathCountByFormInstanceId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFormInstanceId, args);

				args = new Object[] {
					ddmFormInstanceRecordModelImpl.getFormInstanceId()
				};

				finderCache.removeResult(
					_finderPathCountByFormInstanceId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFormInstanceId, args);
			}

			if ((ddmFormInstanceRecordModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_F.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ddmFormInstanceRecordModelImpl.getOriginalUserId(),
					ddmFormInstanceRecordModelImpl.getOriginalFormInstanceId()
				};

				finderCache.removeResult(_finderPathCountByU_F, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_F, args);

				args = new Object[] {
					ddmFormInstanceRecordModelImpl.getUserId(),
					ddmFormInstanceRecordModelImpl.getFormInstanceId()
				};

				finderCache.removeResult(_finderPathCountByU_F, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_F, args);
			}

			if ((ddmFormInstanceRecordModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByF_F.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ddmFormInstanceRecordModelImpl.getOriginalFormInstanceId(),
					ddmFormInstanceRecordModelImpl.
						getOriginalFormInstanceVersion()
				};

				finderCache.removeResult(_finderPathCountByF_F, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByF_F, args);

				args = new Object[] {
					ddmFormInstanceRecordModelImpl.getFormInstanceId(),
					ddmFormInstanceRecordModelImpl.getFormInstanceVersion()
				};

				finderCache.removeResult(_finderPathCountByF_F, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByF_F, args);
			}
		}

		entityCache.putResult(
			DDMFormInstanceRecordImpl.class,
			ddmFormInstanceRecord.getPrimaryKey(), ddmFormInstanceRecord,
			false);

		clearUniqueFindersCache(ddmFormInstanceRecordModelImpl, false);
		cacheUniqueFindersCache(ddmFormInstanceRecordModelImpl);

		ddmFormInstanceRecord.resetOriginalValues();

		return ddmFormInstanceRecord;
	}

	/**
	 * Returns the ddm form instance record with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm form instance record
	 * @return the ddm form instance record
	 * @throws NoSuchFormInstanceRecordException if a ddm form instance record with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceRecord findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFormInstanceRecordException {

		DDMFormInstanceRecord ddmFormInstanceRecord = fetchByPrimaryKey(
			primaryKey);

		if (ddmFormInstanceRecord == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFormInstanceRecordException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ddmFormInstanceRecord;
	}

	/**
	 * Returns the ddm form instance record with the primary key or throws a <code>NoSuchFormInstanceRecordException</code> if it could not be found.
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
		if (ctPersistenceHelper.isProductionMode(DDMFormInstanceRecord.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		DDMFormInstanceRecord ddmFormInstanceRecord = null;

		Session session = null;

		try {
			session = openSession();

			ddmFormInstanceRecord = (DDMFormInstanceRecord)session.get(
				DDMFormInstanceRecordImpl.class, primaryKey);

			if (ddmFormInstanceRecord != null) {
				cacheResult(ddmFormInstanceRecord);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
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

		if (ctPersistenceHelper.isProductionMode(DDMFormInstanceRecord.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMFormInstanceRecord> map =
			new HashMap<Serializable, DDMFormInstanceRecord>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMFormInstanceRecord ddmFormInstanceRecord = fetchByPrimaryKey(
				primaryKey);

			if (ddmFormInstanceRecord != null) {
				map.put(primaryKey, ddmFormInstanceRecord);
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

			for (DDMFormInstanceRecord ddmFormInstanceRecord :
					(List<DDMFormInstanceRecord>)query.list()) {

				map.put(
					ddmFormInstanceRecord.getPrimaryKeyObj(),
					ddmFormInstanceRecord);

				cacheResult(ddmFormInstanceRecord);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findAll(
		int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance records
	 * @param end the upper bound of the range of ddm form instance records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm form instance records
	 */
	@Override
	public List<DDMFormInstanceRecord> findAll(
		int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

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

		List<DDMFormInstanceRecord> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMFormInstanceRecord>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DDMFORMINSTANCERECORD);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DDMFORMINSTANCERECORD;

				sql = sql.concat(DDMFormInstanceRecordModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DDMFormInstanceRecord>)QueryUtil.list(
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
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMFormInstanceRecord.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_DDMFORMINSTANCERECORD);

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
		return "formInstanceRecordId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DDMFORMINSTANCERECORD;
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
		return DDMFormInstanceRecordModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DDMFormInstanceRecord";
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
		ctStrictColumnNames.add("versionUserId");
		ctStrictColumnNames.add("versionUserName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("formInstanceId");
		ctStrictColumnNames.add("formInstanceVersion");
		ctStrictColumnNames.add("storageId");
		ctStrictColumnNames.add("version");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("formInstanceRecordId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});
	}

	/**
	 * Initializes the ddm form instance record persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			DDMFormInstanceRecordModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			DDMFormInstanceRecordImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			DDMFormInstanceRecordModelImpl.UUID_COLUMN_BITMASK |
			DDMFormInstanceRecordModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			DDMFormInstanceRecordModelImpl.UUID_COLUMN_BITMASK |
			DDMFormInstanceRecordModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			DDMFormInstanceRecordModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByFormInstanceId = new FinderPath(
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFormInstanceId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByFormInstanceId = new FinderPath(
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFormInstanceId",
			new String[] {Long.class.getName()},
			DDMFormInstanceRecordModelImpl.FORMINSTANCEID_COLUMN_BITMASK);

		_finderPathCountByFormInstanceId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFormInstanceId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByU_F = new FinderPath(
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_F = new FinderPath(
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			DDMFormInstanceRecordModelImpl.USERID_COLUMN_BITMASK |
			DDMFormInstanceRecordModelImpl.FORMINSTANCEID_COLUMN_BITMASK);

		_finderPathCountByU_F = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_F",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByF_F = new FinderPath(
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByF_F",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByF_F = new FinderPath(
			DDMFormInstanceRecordImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByF_F",
			new String[] {Long.class.getName(), String.class.getName()},
			DDMFormInstanceRecordModelImpl.FORMINSTANCEID_COLUMN_BITMASK |
			DDMFormInstanceRecordModelImpl.FORMINSTANCEVERSION_COLUMN_BITMASK);

		_finderPathCountByF_F = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_F",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DDMFormInstanceRecordImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = DDMPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DDMPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DDMPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_DDMFORMINSTANCERECORD =
		"SELECT ddmFormInstanceRecord FROM DDMFormInstanceRecord ddmFormInstanceRecord";

	private static final String _SQL_SELECT_DDMFORMINSTANCERECORD_WHERE =
		"SELECT ddmFormInstanceRecord FROM DDMFormInstanceRecord ddmFormInstanceRecord WHERE ";

	private static final String _SQL_COUNT_DDMFORMINSTANCERECORD =
		"SELECT COUNT(ddmFormInstanceRecord) FROM DDMFormInstanceRecord ddmFormInstanceRecord";

	private static final String _SQL_COUNT_DDMFORMINSTANCERECORD_WHERE =
		"SELECT COUNT(ddmFormInstanceRecord) FROM DDMFormInstanceRecord ddmFormInstanceRecord WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"ddmFormInstanceRecord.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DDMFormInstanceRecord exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DDMFormInstanceRecord exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(DDMPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}