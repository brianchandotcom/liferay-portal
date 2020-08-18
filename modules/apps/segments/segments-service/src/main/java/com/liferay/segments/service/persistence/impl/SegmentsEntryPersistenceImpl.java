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

package com.liferay.segments.service.persistence.impl;

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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.segments.exception.NoSuchEntryException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryTable;
import com.liferay.segments.model.impl.SegmentsEntryImpl;
import com.liferay.segments.model.impl.SegmentsEntryModelImpl;
import com.liferay.segments.service.persistence.SegmentsEntryPersistence;
import com.liferay.segments.service.persistence.impl.constants.SegmentsPersistenceConstants;

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
 * The persistence implementation for the segments entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @generated
 */
@Component(service = SegmentsEntryPersistence.class)
public class SegmentsEntryPersistenceImpl
	extends BasePersistenceImpl<SegmentsEntry>
	implements SegmentsEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SegmentsEntryUtil</code> to access the segments entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SegmentsEntryImpl.class.getName();

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
	 * Returns all the segments entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

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

		List<SegmentsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if (!uuid.equals(segmentsEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

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
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns the first segments entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByUuid_First(
			String uuid, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByUuid_First(
		String uuid, OrderByComparator<SegmentsEntry> orderByComparator) {

		List<SegmentsEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByUuid_Last(
			String uuid, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last segments entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByUuid_Last(
		String uuid, OrderByComparator<SegmentsEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entries before and after the current segments entry in the ordered set where uuid = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] findByUuid_PrevAndNext(
			long segmentsEntryId, String uuid,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, segmentsEntry, uuid, orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = getByUuid_PrevAndNext(
				session, segmentsEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntry getByUuid_PrevAndNext(
		Session session, SegmentsEntry segmentsEntry, String uuid,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

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
			sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
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
						segmentsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (SegmentsEntry segmentsEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

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

			sb.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

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
		"segmentsEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(segmentsEntry.uuid IS NULL OR segmentsEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the segments entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByUUID_G(uuid, groupId);

		if (segmentsEntry == null) {
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

			throw new NoSuchEntryException(sb.toString());
		}

		return segmentsEntry;
	}

	/**
	 * Returns the segments entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the segments entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof SegmentsEntry) {
			SegmentsEntry segmentsEntry = (SegmentsEntry)result;

			if (!Objects.equals(uuid, segmentsEntry.getUuid()) ||
				(groupId != segmentsEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

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

				List<SegmentsEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					SegmentsEntry segmentsEntry = list.get(0);

					result = segmentsEntry;

					cacheResult(segmentsEntry);
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
			return (SegmentsEntry)result;
		}
	}

	/**
	 * Removes the segments entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the segments entry that was removed
	 */
	@Override
	public SegmentsEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = findByUUID_G(uuid, groupId);

		return remove(segmentsEntry);
	}

	/**
	 * Returns the number of segments entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

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

			sb.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

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
		"segmentsEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(segmentsEntry.uuid IS NULL OR segmentsEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"segmentsEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the segments entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

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

		List<SegmentsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if (!uuid.equals(segmentsEntry.getUuid()) ||
						(companyId != segmentsEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

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
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns the first segments entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		List<SegmentsEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last segments entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entries before and after the current segments entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] findByUuid_C_PrevAndNext(
			long segmentsEntryId, String uuid, long companyId,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, segmentsEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = segmentsEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, segmentsEntry, uuid, companyId, orderByComparator,
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

	protected SegmentsEntry getByUuid_C_PrevAndNext(
		Session session, SegmentsEntry segmentsEntry, String uuid,
		long companyId, OrderByComparator<SegmentsEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

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
			sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
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
						segmentsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (SegmentsEntry segmentsEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

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

			sb.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

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
		"segmentsEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(segmentsEntry.uuid IS NULL OR segmentsEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"segmentsEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private FinderPath _finderPathWithPaginationCountByGroupId;

	/**
	 * Returns all the segments entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

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

		List<SegmentsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if (groupId != segmentsEntry.getGroupId()) {
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

			sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns the first segments entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByGroupId_First(
			long groupId, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByGroupId_First(
		long groupId, OrderByComparator<SegmentsEntry> orderByComparator) {

		List<SegmentsEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByGroupId_Last(
			long groupId, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last segments entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<SegmentsEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entries before and after the current segments entry in the ordered set where groupId = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] findByGroupId_PrevAndNext(
			long segmentsEntryId, long groupId,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, segmentsEntry, groupId, orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, segmentsEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntry getByGroupId_PrevAndNext(
		Session session, SegmentsEntry segmentsEntry, long groupId,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

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
			sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns the segments entries before and after the current segments entry in the ordered set of segments entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] filterFindByGroupId_PrevAndNext(
			long segmentsEntryId, long groupId,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(
				segmentsEntryId, groupId, orderByComparator);
		}

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(
				session, segmentsEntry, groupId, orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = filterGetByGroupId_PrevAndNext(
				session, segmentsEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntry filterGetByGroupId_PrevAndNext(
		Session session, SegmentsEntry segmentsEntry, long groupId,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {

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
			sb.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByGroupId(long[] groupIds) {
		return filterFindByGroupId(
			groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByGroupId(
		long[] groupIds, int start, int end) {

		return filterFindByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByGroupId(groupIds, start, end, orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
			}

			return (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns all the segments entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(long[] groupIds) {
		return findByGroupId(
			groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(
		long[] groupIds, int start, int end) {

		return findByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByGroupId(groupIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (groupIds.length == 1) {
			return findByGroupId(groupIds[0], start, end, orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {StringUtil.merge(groupIds)};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), start, end, orderByComparator
			};
		}

		List<SegmentsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByGroupId, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if (!ArrayUtil.contains(
							groupIds, segmentsEntry.getGroupId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

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
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SegmentsEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByGroupId, finderArgs,
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
	 * Removes all the segments entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SegmentsEntry segmentsEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

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

			sb.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

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
	 * Returns the number of segments entries where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByGroupId(long[] groupIds) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {StringUtil.merge(groupIds)};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByGroupId, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

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

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByGroupId, finderArgs,
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
	 * Returns the number of segments entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
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

	/**
	 * Returns the number of segments entries that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long[] groupIds) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByGroupId(groupIds);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE);

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

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
		"segmentsEntry.groupId = ?";

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_7 =
		"segmentsEntry.groupId IN (";

	private FinderPath _finderPathWithPaginationFindBySource;
	private FinderPath _finderPathWithoutPaginationFindBySource;
	private FinderPath _finderPathCountBySource;

	/**
	 * Returns all the segments entries where source = &#63;.
	 *
	 * @param source the source
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySource(String source) {
		return findBySource(source, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySource(String source, int start, int end) {
		return findBySource(source, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySource(
		String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findBySource(source, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where source = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param source the source
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findBySource(
		String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		source = Objects.toString(source, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindBySource;
				finderArgs = new Object[] {source};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindBySource;
			finderArgs = new Object[] {source, start, end, orderByComparator};
		}

		List<SegmentsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if (!source.equals(segmentsEntry.getSource())) {
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

			sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			boolean bindSource = false;

			if (source.isEmpty()) {
				sb.append(_FINDER_COLUMN_SOURCE_SOURCE_3);
			}
			else {
				bindSource = true;

				sb.append(_FINDER_COLUMN_SOURCE_SOURCE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSource) {
					queryPos.add(source);
				}

				list = (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns the first segments entry in the ordered set where source = &#63;.
	 *
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findBySource_First(
			String source, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchBySource_First(
			source, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("source=");
		sb.append(source);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where source = &#63;.
	 *
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchBySource_First(
		String source, OrderByComparator<SegmentsEntry> orderByComparator) {

		List<SegmentsEntry> list = findBySource(
			source, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry in the ordered set where source = &#63;.
	 *
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findBySource_Last(
			String source, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchBySource_Last(
			source, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("source=");
		sb.append(source);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last segments entry in the ordered set where source = &#63;.
	 *
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchBySource_Last(
		String source, OrderByComparator<SegmentsEntry> orderByComparator) {

		int count = countBySource(source);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntry> list = findBySource(
			source, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entries before and after the current segments entry in the ordered set where source = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param source the source
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] findBySource_PrevAndNext(
			long segmentsEntryId, String source,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		source = Objects.toString(source, "");

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = getBySource_PrevAndNext(
				session, segmentsEntry, source, orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = getBySource_PrevAndNext(
				session, segmentsEntry, source, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntry getBySource_PrevAndNext(
		Session session, SegmentsEntry segmentsEntry, String source,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

		boolean bindSource = false;

		if (source.isEmpty()) {
			sb.append(_FINDER_COLUMN_SOURCE_SOURCE_3);
		}
		else {
			bindSource = true;

			sb.append(_FINDER_COLUMN_SOURCE_SOURCE_2);
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
			sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindSource) {
			queryPos.add(source);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entries where source = &#63; from the database.
	 *
	 * @param source the source
	 */
	@Override
	public void removeBySource(String source) {
		for (SegmentsEntry segmentsEntry :
				findBySource(
					source, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries where source = &#63;.
	 *
	 * @param source the source
	 * @return the number of matching segments entries
	 */
	@Override
	public int countBySource(String source) {
		source = Objects.toString(source, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountBySource;

			finderArgs = new Object[] {source};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			boolean bindSource = false;

			if (source.isEmpty()) {
				sb.append(_FINDER_COLUMN_SOURCE_SOURCE_3);
			}
			else {
				bindSource = true;

				sb.append(_FINDER_COLUMN_SOURCE_SOURCE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSource) {
					queryPos.add(source);
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

	private static final String _FINDER_COLUMN_SOURCE_SOURCE_2 =
		"segmentsEntry.source = ?";

	private static final String _FINDER_COLUMN_SOURCE_SOURCE_3 =
		"(segmentsEntry.source IS NULL OR segmentsEntry.source = '')";

	private FinderPath _finderPathWithPaginationFindByType;
	private FinderPath _finderPathWithoutPaginationFindByType;
	private FinderPath _finderPathCountByType;

	/**
	 * Returns all the segments entries where type = &#63;.
	 *
	 * @param type the type
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByType(String type) {
		return findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByType(String type, int start, int end) {
		return findByType(type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByType(
		String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByType(type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByType(
		String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByType;
				finderArgs = new Object[] {type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByType;
			finderArgs = new Object[] {type, start, end, orderByComparator};
		}

		List<SegmentsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if (!type.equals(segmentsEntry.getType())) {
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

			sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_TYPE_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_TYPE_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns the first segments entry in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByType_First(
			String type, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByType_First(
			type, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByType_First(
		String type, OrderByComparator<SegmentsEntry> orderByComparator) {

		List<SegmentsEntry> list = findByType(type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByType_Last(
			String type, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByType_Last(type, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last segments entry in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByType_Last(
		String type, OrderByComparator<SegmentsEntry> orderByComparator) {

		int count = countByType(type);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntry> list = findByType(
			type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entries before and after the current segments entry in the ordered set where type = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] findByType_PrevAndNext(
			long segmentsEntryId, String type,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		type = Objects.toString(type, "");

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = getByType_PrevAndNext(
				session, segmentsEntry, type, orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = getByType_PrevAndNext(
				session, segmentsEntry, type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntry getByType_PrevAndNext(
		Session session, SegmentsEntry segmentsEntry, String type,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_TYPE_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_TYPE_TYPE_2);
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
			sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entries where type = &#63; from the database.
	 *
	 * @param type the type
	 */
	@Override
	public void removeByType(String type) {
		for (SegmentsEntry segmentsEntry :
				findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries where type = &#63;.
	 *
	 * @param type the type
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByType(String type) {
		type = Objects.toString(type, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByType;

			finderArgs = new Object[] {type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_TYPE_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_TYPE_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindType) {
					queryPos.add(type);
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

	private static final String _FINDER_COLUMN_TYPE_TYPE_2 =
		"segmentsEntry.type = ?";

	private static final String _FINDER_COLUMN_TYPE_TYPE_3 =
		"(segmentsEntry.type IS NULL OR segmentsEntry.type = '')";

	private FinderPath _finderPathFetchByG_S;
	private FinderPath _finderPathCountByG_S;

	/**
	 * Returns the segments entry where groupId = &#63; and segmentsEntryKey = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryKey the segments entry key
	 * @return the matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_S(long groupId, String segmentsEntryKey)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByG_S(groupId, segmentsEntryKey);

		if (segmentsEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", segmentsEntryKey=");
			sb.append(segmentsEntryKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return segmentsEntry;
	}

	/**
	 * Returns the segments entry where groupId = &#63; and segmentsEntryKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryKey the segments entry key
	 * @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_S(long groupId, String segmentsEntryKey) {
		return fetchByG_S(groupId, segmentsEntryKey, true);
	}

	/**
	 * Returns the segments entry where groupId = &#63; and segmentsEntryKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryKey the segments entry key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_S(
		long groupId, String segmentsEntryKey, boolean useFinderCache) {

		segmentsEntryKey = Objects.toString(segmentsEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, segmentsEntryKey};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_S, finderArgs, this);
		}

		if (result instanceof SegmentsEntry) {
			SegmentsEntry segmentsEntry = (SegmentsEntry)result;

			if ((groupId != segmentsEntry.getGroupId()) ||
				!Objects.equals(
					segmentsEntryKey, segmentsEntry.getSegmentsEntryKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			boolean bindSegmentsEntryKey = false;

			if (segmentsEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_S_SEGMENTSENTRYKEY_3);
			}
			else {
				bindSegmentsEntryKey = true;

				sb.append(_FINDER_COLUMN_G_S_SEGMENTSENTRYKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindSegmentsEntryKey) {
					queryPos.add(segmentsEntryKey);
				}

				List<SegmentsEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_S, finderArgs, list);
					}
				}
				else {
					SegmentsEntry segmentsEntry = list.get(0);

					result = segmentsEntry;

					cacheResult(segmentsEntry);
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
			return (SegmentsEntry)result;
		}
	}

	/**
	 * Removes the segments entry where groupId = &#63; and segmentsEntryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryKey the segments entry key
	 * @return the segments entry that was removed
	 */
	@Override
	public SegmentsEntry removeByG_S(long groupId, String segmentsEntryKey)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = findByG_S(groupId, segmentsEntryKey);

		return remove(segmentsEntry);
	}

	/**
	 * Returns the number of segments entries where groupId = &#63; and segmentsEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsEntryKey the segments entry key
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_S(long groupId, String segmentsEntryKey) {
		segmentsEntryKey = Objects.toString(segmentsEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_S;

			finderArgs = new Object[] {groupId, segmentsEntryKey};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			boolean bindSegmentsEntryKey = false;

			if (segmentsEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_S_SEGMENTSENTRYKEY_3);
			}
			else {
				bindSegmentsEntryKey = true;

				sb.append(_FINDER_COLUMN_G_S_SEGMENTSENTRYKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindSegmentsEntryKey) {
					queryPos.add(segmentsEntryKey);
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

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 =
		"segmentsEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_SEGMENTSENTRYKEY_2 =
		"segmentsEntry.segmentsEntryKey = ?";

	private static final String _FINDER_COLUMN_G_S_SEGMENTSENTRYKEY_3 =
		"(segmentsEntry.segmentsEntryKey IS NULL OR segmentsEntry.segmentsEntryKey = '')";

	private FinderPath _finderPathWithPaginationFindByG_A;
	private FinderPath _finderPathWithoutPaginationFindByG_A;
	private FinderPath _finderPathCountByG_A;
	private FinderPath _finderPathWithPaginationCountByG_A;

	/**
	 * Returns all the segments entries where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(long groupId, boolean active) {
		return findByG_A(
			groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(
		long groupId, boolean active, int start, int end) {

		return findByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByG_A(groupId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_A;
				finderArgs = new Object[] {groupId, active};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_A;
			finderArgs = new Object[] {
				groupId, active, start, end, orderByComparator
			};
		}

		List<SegmentsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if ((groupId != segmentsEntry.getGroupId()) ||
						(active != segmentsEntry.isActive())) {

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

			sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(active);

				list = (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_A_First(
			long groupId, boolean active,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByG_A_First(
			groupId, active, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_A_First(
		long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		List<SegmentsEntry> list = findByG_A(
			groupId, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_A_Last(
			long groupId, boolean active,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByG_A_Last(
			groupId, active, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_A_Last(
		long groupId, boolean active,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		int count = countByG_A(groupId, active);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntry> list = findByG_A(
			groupId, active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entries before and after the current segments entry in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] findByG_A_PrevAndNext(
			long segmentsEntryId, long groupId, boolean active,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = getByG_A_PrevAndNext(
				session, segmentsEntry, groupId, active, orderByComparator,
				true);

			array[1] = segmentsEntry;

			array[2] = getByG_A_PrevAndNext(
				session, segmentsEntry, groupId, active, orderByComparator,
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

	protected SegmentsEntry getByG_A_PrevAndNext(
		Session session, SegmentsEntry segmentsEntry, long groupId,
		boolean active, OrderByComparator<SegmentsEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_ACTIVE_2);

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
			sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A(long groupId, boolean active) {
		return filterFindByG_A(
			groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A(
		long groupId, boolean active, int start, int end) {

		return filterFindByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A(groupId, active, start, end, orderByComparator);
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
			sb.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(active);

			return (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns the segments entries before and after the current segments entry in the ordered set of segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] filterFindByG_A_PrevAndNext(
			long segmentsEntryId, long groupId, boolean active,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_PrevAndNext(
				segmentsEntryId, groupId, active, orderByComparator);
		}

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = filterGetByG_A_PrevAndNext(
				session, segmentsEntry, groupId, active, orderByComparator,
				true);

			array[1] = segmentsEntry;

			array[2] = filterGetByG_A_PrevAndNext(
				session, segmentsEntry, groupId, active, orderByComparator,
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

	protected SegmentsEntry filterGetByG_A_PrevAndNext(
		Session session, SegmentsEntry segmentsEntry, long groupId,
		boolean active, OrderByComparator<SegmentsEntry> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A(
		long[] groupIds, boolean active) {

		return filterFindByG_A(
			groupIds, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A(
		long[] groupIds, boolean active, int start, int end) {

		return filterFindByG_A(groupIds, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A(
		long[] groupIds, boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByG_A(groupIds, active, start, end, orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_A_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(active);

			return (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns all the segments entries where groupId = any &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(long[] groupIds, boolean active) {
		return findByG_A(
			groupIds, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = any &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(
		long[] groupIds, boolean active, int start, int end) {

		return findByG_A(groupIds, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = any &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(
		long[] groupIds, boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByG_A(groupIds, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A(
		long[] groupIds, boolean active, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (groupIds.length == 1) {
			return findByG_A(
				groupIds[0], active, start, end, orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {StringUtil.merge(groupIds), active};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), active, start, end,
				orderByComparator
			};
		}

		List<SegmentsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByG_A, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if (!ArrayUtil.contains(
							groupIds, segmentsEntry.getGroupId()) ||
						(active != segmentsEntry.isActive())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_A_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(active);

				list = (List<SegmentsEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_A, finderArgs, list);
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
	 * Removes all the segments entries where groupId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 */
	@Override
	public void removeByG_A(long groupId, boolean active) {
		for (SegmentsEntry segmentsEntry :
				findByG_A(
					groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_A(long groupId, boolean active) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_A;

			finderArgs = new Object[] {groupId, active};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(active);

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
	 * Returns the number of segments entries where groupId = any &#63; and active = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_A(long[] groupIds, boolean active) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {StringUtil.merge(groupIds), active};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_A, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_A_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(active);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByG_A, finderArgs, count);
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
	 * Returns the number of segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_A(long groupId, boolean active) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A(groupId, active);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(active);

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
	 * Returns the number of segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_A(long[] groupIds, boolean active) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByG_A(groupIds, active);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE);

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_A_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(active);

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

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 =
		"segmentsEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_A_GROUPID_7 =
		"segmentsEntry.groupId IN (";

	private static final String _FINDER_COLUMN_G_A_ACTIVE_2 =
		"segmentsEntry.active = ?";

	private static final String _FINDER_COLUMN_G_A_ACTIVE_2_SQL =
		"segmentsEntry.active_ = ?";

	private FinderPath _finderPathWithPaginationFindByA_T;
	private FinderPath _finderPathWithoutPaginationFindByA_T;
	private FinderPath _finderPathCountByA_T;

	/**
	 * Returns all the segments entries where active = &#63; and type = &#63;.
	 *
	 * @param active the active
	 * @param type the type
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByA_T(boolean active, String type) {
		return findByA_T(
			active, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByA_T(
		boolean active, String type, int start, int end) {

		return findByA_T(active, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByA_T(
		boolean active, String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByA_T(active, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByA_T(
		boolean active, String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByA_T;
				finderArgs = new Object[] {active, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByA_T;
			finderArgs = new Object[] {
				active, type, start, end, orderByComparator
			};
		}

		List<SegmentsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if ((active != segmentsEntry.isActive()) ||
						!type.equals(segmentsEntry.getType())) {

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

			sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_A_T_ACTIVE_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_A_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_A_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(active);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns the first segments entry in the ordered set where active = &#63; and type = &#63;.
	 *
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByA_T_First(
			boolean active, String type,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByA_T_First(
			active, type, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("active=");
		sb.append(active);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where active = &#63; and type = &#63;.
	 *
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByA_T_First(
		boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		List<SegmentsEntry> list = findByA_T(
			active, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry in the ordered set where active = &#63; and type = &#63;.
	 *
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByA_T_Last(
			boolean active, String type,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByA_T_Last(
			active, type, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("active=");
		sb.append(active);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last segments entry in the ordered set where active = &#63; and type = &#63;.
	 *
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByA_T_Last(
		boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		int count = countByA_T(active, type);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntry> list = findByA_T(
			active, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entries before and after the current segments entry in the ordered set where active = &#63; and type = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] findByA_T_PrevAndNext(
			long segmentsEntryId, boolean active, String type,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		type = Objects.toString(type, "");

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = getByA_T_PrevAndNext(
				session, segmentsEntry, active, type, orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = getByA_T_PrevAndNext(
				session, segmentsEntry, active, type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntry getByA_T_PrevAndNext(
		Session session, SegmentsEntry segmentsEntry, boolean active,
		String type, OrderByComparator<SegmentsEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_A_T_ACTIVE_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_A_T_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_A_T_TYPE_2);
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
			sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(active);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entries where active = &#63; and type = &#63; from the database.
	 *
	 * @param active the active
	 * @param type the type
	 */
	@Override
	public void removeByA_T(boolean active, String type) {
		for (SegmentsEntry segmentsEntry :
				findByA_T(
					active, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries where active = &#63; and type = &#63;.
	 *
	 * @param active the active
	 * @param type the type
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByA_T(boolean active, String type) {
		type = Objects.toString(type, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByA_T;

			finderArgs = new Object[] {active, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_A_T_ACTIVE_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_A_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_A_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(active);

				if (bindType) {
					queryPos.add(type);
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

	private static final String _FINDER_COLUMN_A_T_ACTIVE_2 =
		"segmentsEntry.active = ? AND ";

	private static final String _FINDER_COLUMN_A_T_TYPE_2 =
		"segmentsEntry.type = ?";

	private static final String _FINDER_COLUMN_A_T_TYPE_3 =
		"(segmentsEntry.type IS NULL OR segmentsEntry.type = '')";

	private FinderPath _finderPathWithPaginationFindByG_A_T;
	private FinderPath _finderPathWithoutPaginationFindByG_A_T;
	private FinderPath _finderPathCountByG_A_T;
	private FinderPath _finderPathWithPaginationCountByG_A_T;

	/**
	 * Returns all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_T(
		long groupId, boolean active, String type) {

		return findByG_A_T(
			groupId, active, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_T(
		long groupId, boolean active, String type, int start, int end) {

		return findByG_A_T(groupId, active, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_T(
		long groupId, boolean active, String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByG_A_T(
			groupId, active, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_T(
		long groupId, boolean active, String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_A_T;
				finderArgs = new Object[] {groupId, active, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_A_T;
			finderArgs = new Object[] {
				groupId, active, type, start, end, orderByComparator
			};
		}

		List<SegmentsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if ((groupId != segmentsEntry.getGroupId()) ||
						(active != segmentsEntry.isActive()) ||
						!type.equals(segmentsEntry.getType())) {

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

			sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_A_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_A_T_ACTIVE_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_A_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_A_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(active);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_A_T_First(
			long groupId, boolean active, String type,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByG_A_T_First(
			groupId, active, type, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_A_T_First(
		long groupId, boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		List<SegmentsEntry> list = findByG_A_T(
			groupId, active, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_A_T_Last(
			long groupId, boolean active, String type,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByG_A_T_Last(
			groupId, active, type, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_A_T_Last(
		long groupId, boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		int count = countByG_A_T(groupId, active, type);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntry> list = findByG_A_T(
			groupId, active, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entries before and after the current segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] findByG_A_T_PrevAndNext(
			long segmentsEntryId, long groupId, boolean active, String type,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		type = Objects.toString(type, "");

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = getByG_A_T_PrevAndNext(
				session, segmentsEntry, groupId, active, type,
				orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = getByG_A_T_PrevAndNext(
				session, segmentsEntry, groupId, active, type,
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

	protected SegmentsEntry getByG_A_T_PrevAndNext(
		Session session, SegmentsEntry segmentsEntry, long groupId,
		boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_A_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_T_ACTIVE_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_T_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_G_A_T_TYPE_2);
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
			sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(active);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_T(
		long groupId, boolean active, String type) {

		return filterFindByG_A_T(
			groupId, active, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_T(
		long groupId, boolean active, String type, int start, int end) {

		return filterFindByG_A_T(groupId, active, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_T(
		long groupId, boolean active, String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_T(
				groupId, active, type, start, end, orderByComparator);
		}

		type = Objects.toString(type, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_A_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_T_ACTIVE_2_SQL);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_G_A_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(active);

			if (bindType) {
				queryPos.add(type);
			}

			return (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns the segments entries before and after the current segments entry in the ordered set of segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] filterFindByG_A_T_PrevAndNext(
			long segmentsEntryId, long groupId, boolean active, String type,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_T_PrevAndNext(
				segmentsEntryId, groupId, active, type, orderByComparator);
		}

		type = Objects.toString(type, "");

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = filterGetByG_A_T_PrevAndNext(
				session, segmentsEntry, groupId, active, type,
				orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = filterGetByG_A_T_PrevAndNext(
				session, segmentsEntry, groupId, active, type,
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

	protected SegmentsEntry filterGetByG_A_T_PrevAndNext(
		Session session, SegmentsEntry segmentsEntry, long groupId,
		boolean active, String type,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {

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
			sb.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_A_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_T_ACTIVE_2_SQL);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_G_A_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(active);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param type the type
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_T(
		long[] groupIds, boolean active, String type) {

		return filterFindByG_A_T(
			groupIds, active, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_T(
		long[] groupIds, boolean active, String type, int start, int end) {

		return filterFindByG_A_T(groupIds, active, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_T(
		long[] groupIds, boolean active, String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByG_A_T(
				groupIds, active, type, start, end, orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		type = Objects.toString(type, "");

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_A_T_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_A_T_ACTIVE_2_SQL);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_G_A_T_TYPE_2_SQL);
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(active);

			if (bindType) {
				queryPos.add(type);
			}

			return (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns all the segments entries where groupId = any &#63; and active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param type the type
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_T(
		long[] groupIds, boolean active, String type) {

		return findByG_A_T(
			groupIds, active, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = any &#63; and active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_T(
		long[] groupIds, boolean active, String type, int start, int end) {

		return findByG_A_T(groupIds, active, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = any &#63; and active = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_T(
		long[] groupIds, boolean active, String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByG_A_T(
			groupIds, active, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and type = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_T(
		long[] groupIds, boolean active, String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		type = Objects.toString(type, "");

		if (groupIds.length == 1) {
			return findByG_A_T(
				groupIds[0], active, type, start, end, orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), active, type
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), active, type, start, end,
				orderByComparator
			};
		}

		List<SegmentsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByG_A_T, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if (!ArrayUtil.contains(
							groupIds, segmentsEntry.getGroupId()) ||
						(active != segmentsEntry.isActive()) ||
						!type.equals(segmentsEntry.getType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_A_T_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_A_T_ACTIVE_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_A_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_A_T_TYPE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(active);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<SegmentsEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_A_T, finderArgs, list);
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
	 * Removes all the segments entries where groupId = &#63; and active = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 */
	@Override
	public void removeByG_A_T(long groupId, boolean active, String type) {
		for (SegmentsEntry segmentsEntry :
				findByG_A_T(
					groupId, active, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_A_T(long groupId, boolean active, String type) {
		type = Objects.toString(type, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_A_T;

			finderArgs = new Object[] {groupId, active, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_A_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_A_T_ACTIVE_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_A_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_A_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(active);

				if (bindType) {
					queryPos.add(type);
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
	 * Returns the number of segments entries where groupId = any &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param type the type
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_A_T(long[] groupIds, boolean active, String type) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		type = Objects.toString(type, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), active, type
			};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_A_T, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_A_T_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_A_T_ACTIVE_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_A_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_A_T_TYPE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(active);

				if (bindType) {
					queryPos.add(type);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByG_A_T, finderArgs,
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
	 * Returns the number of segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_A_T(long groupId, boolean active, String type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A_T(groupId, active, type);
		}

		type = Objects.toString(type, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_A_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_T_ACTIVE_2_SQL);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_G_A_T_TYPE_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(active);

			if (bindType) {
				queryPos.add(type);
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

	/**
	 * Returns the number of segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and type = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param type the type
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_A_T(
		long[] groupIds, boolean active, String type) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByG_A_T(groupIds, active, type);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		type = Objects.toString(type, "");

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE);

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_A_T_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_A_T_ACTIVE_2_SQL);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_G_A_T_TYPE_2_SQL);
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(active);

			if (bindType) {
				queryPos.add(type);
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

	private static final String _FINDER_COLUMN_G_A_T_GROUPID_2 =
		"segmentsEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_A_T_GROUPID_7 =
		"segmentsEntry.groupId IN (";

	private static final String _FINDER_COLUMN_G_A_T_ACTIVE_2 =
		"segmentsEntry.active = ? AND ";

	private static final String _FINDER_COLUMN_G_A_T_ACTIVE_2_SQL =
		"segmentsEntry.active_ = ? AND ";

	private static final String _FINDER_COLUMN_G_A_T_TYPE_2 =
		"segmentsEntry.type = ?";

	private static final String _FINDER_COLUMN_G_A_T_TYPE_3 =
		"(segmentsEntry.type IS NULL OR segmentsEntry.type = '')";

	private static final String _FINDER_COLUMN_G_A_T_TYPE_2_SQL =
		"segmentsEntry.type_ = ?";

	private static final String _FINDER_COLUMN_G_A_T_TYPE_3_SQL =
		"(segmentsEntry.type_ IS NULL OR segmentsEntry.type_ = '')";

	private FinderPath _finderPathWithPaginationFindByG_A_S_T;
	private FinderPath _finderPathWithoutPaginationFindByG_A_S_T;
	private FinderPath _finderPathCountByG_A_S_T;
	private FinderPath _finderPathWithPaginationCountByG_A_S_T;

	/**
	 * Returns all the segments entries where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_S_T(
		long groupId, boolean active, String source, String type) {

		return findByG_A_S_T(
			groupId, active, source, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_S_T(
		long groupId, boolean active, String source, String type, int start,
		int end) {

		return findByG_A_S_T(groupId, active, source, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_S_T(
		long groupId, boolean active, String source, String type, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByG_A_S_T(
			groupId, active, source, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_S_T(
		long groupId, boolean active, String source, String type, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		source = Objects.toString(source, "");
		type = Objects.toString(type, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_A_S_T;
				finderArgs = new Object[] {groupId, active, source, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_A_S_T;
			finderArgs = new Object[] {
				groupId, active, source, type, start, end, orderByComparator
			};
		}

		List<SegmentsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if ((groupId != segmentsEntry.getGroupId()) ||
						(active != segmentsEntry.isActive()) ||
						!source.equals(segmentsEntry.getSource()) ||
						!type.equals(segmentsEntry.getType())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_A_S_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_A_S_T_ACTIVE_2);

			boolean bindSource = false;

			if (source.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_3);
			}
			else {
				bindSource = true;

				sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_2);
			}

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(active);

				if (bindSource) {
					queryPos.add(source);
				}

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_A_S_T_First(
			long groupId, boolean active, String source, String type,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByG_A_S_T_First(
			groupId, active, source, type, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", source=");
		sb.append(source);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_A_S_T_First(
		long groupId, boolean active, String source, String type,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		List<SegmentsEntry> list = findByG_A_S_T(
			groupId, active, source, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry
	 * @throws NoSuchEntryException if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry findByG_A_S_T_Last(
			long groupId, boolean active, String source, String type,
			OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByG_A_S_T_Last(
			groupId, active, source, type, orderByComparator);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", source=");
		sb.append(source);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	@Override
	public SegmentsEntry fetchByG_A_S_T_Last(
		long groupId, boolean active, String source, String type,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		int count = countByG_A_S_T(groupId, active, source, type);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntry> list = findByG_A_S_T(
			groupId, active, source, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entries before and after the current segments entry in the ordered set where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] findByG_A_S_T_PrevAndNext(
			long segmentsEntryId, long groupId, boolean active, String source,
			String type, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		source = Objects.toString(source, "");
		type = Objects.toString(type, "");

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = getByG_A_S_T_PrevAndNext(
				session, segmentsEntry, groupId, active, source, type,
				orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = getByG_A_S_T_PrevAndNext(
				session, segmentsEntry, groupId, active, source, type,
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

	protected SegmentsEntry getByG_A_S_T_PrevAndNext(
		Session session, SegmentsEntry segmentsEntry, long groupId,
		boolean active, String source, String type,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_A_S_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_S_T_ACTIVE_2);

		boolean bindSource = false;

		if (source.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_3);
		}
		else {
			bindSource = true;

			sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_2);
		}

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_2);
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
			sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(active);

		if (bindSource) {
			queryPos.add(source);
		}

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_S_T(
		long groupId, boolean active, String source, String type) {

		return filterFindByG_A_S_T(
			groupId, active, source, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_S_T(
		long groupId, boolean active, String source, String type, int start,
		int end) {

		return filterFindByG_A_S_T(
			groupId, active, source, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_S_T(
		long groupId, boolean active, String source, String type, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_S_T(
				groupId, active, source, type, start, end, orderByComparator);
		}

		source = Objects.toString(source, "");
		type = Objects.toString(type, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_A_S_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_S_T_ACTIVE_2_SQL);

		boolean bindSource = false;

		if (source.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_3);
		}
		else {
			bindSource = true;

			sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_2);
		}

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(active);

			if (bindSource) {
				queryPos.add(source);
			}

			if (bindType) {
				queryPos.add(type);
			}

			return (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns the segments entries before and after the current segments entry in the ordered set of segments entries that the user has permission to view where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * @param segmentsEntryId the primary key of the current segments entry
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry[] filterFindByG_A_S_T_PrevAndNext(
			long segmentsEntryId, long groupId, boolean active, String source,
			String type, OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_S_T_PrevAndNext(
				segmentsEntryId, groupId, active, source, type,
				orderByComparator);
		}

		source = Objects.toString(source, "");
		type = Objects.toString(type, "");

		SegmentsEntry segmentsEntry = findByPrimaryKey(segmentsEntryId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry[] array = new SegmentsEntryImpl[3];

			array[0] = filterGetByG_A_S_T_PrevAndNext(
				session, segmentsEntry, groupId, active, source, type,
				orderByComparator, true);

			array[1] = segmentsEntry;

			array[2] = filterGetByG_A_S_T_PrevAndNext(
				session, segmentsEntry, groupId, active, source, type,
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

	protected SegmentsEntry filterGetByG_A_S_T_PrevAndNext(
		Session session, SegmentsEntry segmentsEntry, long groupId,
		boolean active, String source, String type,
		OrderByComparator<SegmentsEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_A_S_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_S_T_ACTIVE_2_SQL);

		boolean bindSource = false;

		if (source.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_3);
		}
		else {
			bindSource = true;

			sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_2);
		}

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(active);

		if (bindSource) {
			queryPos.add(source);
		}

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @return the matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_S_T(
		long[] groupIds, boolean active, String source, String type) {

		return filterFindByG_A_S_T(
			groupIds, active, source, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_S_T(
		long[] groupIds, boolean active, String source, String type, int start,
		int end) {

		return filterFindByG_A_S_T(
			groupIds, active, source, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries that the user has permission to view
	 */
	@Override
	public List<SegmentsEntry> filterFindByG_A_S_T(
		long[] groupIds, boolean active, String source, String type, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByG_A_S_T(
				groupIds, active, source, type, start, end, orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		source = Objects.toString(source, "");
		type = Objects.toString(type, "");

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_A_S_T_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_A_S_T_ACTIVE_2_SQL);

		boolean bindSource = false;

		if (source.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_3);
		}
		else {
			bindSource = true;

			sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_2);
		}

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_2_SQL);
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SegmentsEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SegmentsEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(active);

			if (bindSource) {
				queryPos.add(source);
			}

			if (bindType) {
				queryPos.add(type);
			}

			return (List<SegmentsEntry>)QueryUtil.list(
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
	 * Returns all the segments entries where groupId = any &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @return the matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_S_T(
		long[] groupIds, boolean active, String source, String type) {

		return findByG_A_S_T(
			groupIds, active, source, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries where groupId = any &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_S_T(
		long[] groupIds, boolean active, String source, String type, int start,
		int end) {

		return findByG_A_S_T(groupIds, active, source, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = any &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_S_T(
		long[] groupIds, boolean active, String source, String type, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator) {

		return findByG_A_S_T(
			groupIds, active, source, type, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entries
	 */
	@Override
	public List<SegmentsEntry> findByG_A_S_T(
		long[] groupIds, boolean active, String source, String type, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		source = Objects.toString(source, "");
		type = Objects.toString(type, "");

		if (groupIds.length == 1) {
			return findByG_A_S_T(
				groupIds[0], active, source, type, start, end,
				orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), active, source, type
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), active, source, type, start, end,
				orderByComparator
			};
		}

		List<SegmentsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByG_A_S_T, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntry segmentsEntry : list) {
					if (!ArrayUtil.contains(
							groupIds, segmentsEntry.getGroupId()) ||
						(active != segmentsEntry.isActive()) ||
						!source.equals(segmentsEntry.getSource()) ||
						!type.equals(segmentsEntry.getType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_SEGMENTSENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_A_S_T_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_A_S_T_ACTIVE_2);

			boolean bindSource = false;

			if (source.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_3);
			}
			else {
				bindSource = true;

				sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_2);
			}

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(active);

				if (bindSource) {
					queryPos.add(source);
				}

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<SegmentsEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_A_S_T, finderArgs,
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
	 * Removes all the segments entries where groupId = &#63; and active = &#63; and source = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 */
	@Override
	public void removeByG_A_S_T(
		long groupId, boolean active, String source, String type) {

		for (SegmentsEntry segmentsEntry :
				findByG_A_S_T(
					groupId, active, source, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_A_S_T(
		long groupId, boolean active, String source, String type) {

		source = Objects.toString(source, "");
		type = Objects.toString(type, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_A_S_T;

			finderArgs = new Object[] {groupId, active, source, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_A_S_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_A_S_T_ACTIVE_2);

			boolean bindSource = false;

			if (source.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_3);
			}
			else {
				bindSource = true;

				sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_2);
			}

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(active);

				if (bindSource) {
					queryPos.add(source);
				}

				if (bindType) {
					queryPos.add(type);
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
	 * Returns the number of segments entries where groupId = any &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @return the number of matching segments entries
	 */
	@Override
	public int countByG_A_S_T(
		long[] groupIds, boolean active, String source, String type) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		source = Objects.toString(source, "");
		type = Objects.toString(type, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), active, source, type
			};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_A_S_T, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_SEGMENTSENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_A_S_T_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_A_S_T_ACTIVE_2);

			boolean bindSource = false;

			if (source.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_3);
			}
			else {
				bindSource = true;

				sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_2);
			}

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(active);

				if (bindSource) {
					queryPos.add(source);
				}

				if (bindType) {
					queryPos.add(type);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByG_A_S_T, finderArgs,
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
	 * Returns the number of segments entries that the user has permission to view where groupId = &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_A_S_T(
		long groupId, boolean active, String source, String type) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A_S_T(groupId, active, source, type);
		}

		source = Objects.toString(source, "");
		type = Objects.toString(type, "");

		StringBundler sb = new StringBundler(5);

		sb.append(_FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_A_S_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_S_T_ACTIVE_2_SQL);

		boolean bindSource = false;

		if (source.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_3);
		}
		else {
			bindSource = true;

			sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_2);
		}

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(active);

			if (bindSource) {
				queryPos.add(source);
			}

			if (bindType) {
				queryPos.add(type);
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

	/**
	 * Returns the number of segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and source = &#63; and type = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param active the active
	 * @param source the source
	 * @param type the type
	 * @return the number of matching segments entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_A_S_T(
		long[] groupIds, boolean active, String source, String type) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByG_A_S_T(groupIds, active, source, type);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		source = Objects.toString(source, "");
		type = Objects.toString(type, "");

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE);

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_A_S_T_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		sb.append(_FINDER_COLUMN_G_A_S_T_ACTIVE_2_SQL);

		boolean bindSource = false;

		if (source.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_3);
		}
		else {
			bindSource = true;

			sb.append(_FINDER_COLUMN_G_A_S_T_SOURCE_2);
		}

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_G_A_S_T_TYPE_2_SQL);
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(active);

			if (bindSource) {
				queryPos.add(source);
			}

			if (bindType) {
				queryPos.add(type);
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

	private static final String _FINDER_COLUMN_G_A_S_T_GROUPID_2 =
		"segmentsEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_A_S_T_GROUPID_7 =
		"segmentsEntry.groupId IN (";

	private static final String _FINDER_COLUMN_G_A_S_T_ACTIVE_2 =
		"segmentsEntry.active = ? AND ";

	private static final String _FINDER_COLUMN_G_A_S_T_ACTIVE_2_SQL =
		"segmentsEntry.active_ = ? AND ";

	private static final String _FINDER_COLUMN_G_A_S_T_SOURCE_2 =
		"segmentsEntry.source = ? AND ";

	private static final String _FINDER_COLUMN_G_A_S_T_SOURCE_3 =
		"(segmentsEntry.source IS NULL OR segmentsEntry.source = '') AND ";

	private static final String _FINDER_COLUMN_G_A_S_T_TYPE_2 =
		"segmentsEntry.type = ?";

	private static final String _FINDER_COLUMN_G_A_S_T_TYPE_3 =
		"(segmentsEntry.type IS NULL OR segmentsEntry.type = '')";

	private static final String _FINDER_COLUMN_G_A_S_T_TYPE_2_SQL =
		"segmentsEntry.type_ = ?";

	private static final String _FINDER_COLUMN_G_A_S_T_TYPE_3_SQL =
		"(segmentsEntry.type_ IS NULL OR segmentsEntry.type_ = '')";

	public SegmentsEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("active", "active_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SegmentsEntry.class);

		setModelImplClass(SegmentsEntryImpl.class);
		setModelPKClass(long.class);

		setTable(SegmentsEntryTable.INSTANCE);
	}

	/**
	 * Caches the segments entry in the entity cache if it is enabled.
	 *
	 * @param segmentsEntry the segments entry
	 */
	@Override
	public void cacheResult(SegmentsEntry segmentsEntry) {
		if (segmentsEntry.getCtCollectionId() != 0) {
			segmentsEntry.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			SegmentsEntryImpl.class, segmentsEntry.getPrimaryKey(),
			segmentsEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {segmentsEntry.getUuid(), segmentsEntry.getGroupId()},
			segmentsEntry);

		finderCache.putResult(
			_finderPathFetchByG_S,
			new Object[] {
				segmentsEntry.getGroupId(), segmentsEntry.getSegmentsEntryKey()
			},
			segmentsEntry);

		segmentsEntry.resetOriginalValues();
	}

	/**
	 * Caches the segments entries in the entity cache if it is enabled.
	 *
	 * @param segmentsEntries the segments entries
	 */
	@Override
	public void cacheResult(List<SegmentsEntry> segmentsEntries) {
		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			if (segmentsEntry.getCtCollectionId() != 0) {
				segmentsEntry.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					SegmentsEntryImpl.class, segmentsEntry.getPrimaryKey()) ==
						null) {

				cacheResult(segmentsEntry);
			}
			else {
				segmentsEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all segments entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SegmentsEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the segments entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SegmentsEntry segmentsEntry) {
		entityCache.removeResult(
			SegmentsEntryImpl.class, segmentsEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SegmentsEntryModelImpl)segmentsEntry, true);
	}

	@Override
	public void clearCache(List<SegmentsEntry> segmentsEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			entityCache.removeResult(
				SegmentsEntryImpl.class, segmentsEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(SegmentsEntryModelImpl)segmentsEntry, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(SegmentsEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SegmentsEntryModelImpl segmentsEntryModelImpl) {

		Object[] args = new Object[] {
			segmentsEntryModelImpl.getUuid(),
			segmentsEntryModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, segmentsEntryModelImpl, false);

		args = new Object[] {
			segmentsEntryModelImpl.getGroupId(),
			segmentsEntryModelImpl.getSegmentsEntryKey()
		};

		finderCache.putResult(
			_finderPathCountByG_S, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_S, args, segmentsEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SegmentsEntryModelImpl segmentsEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				segmentsEntryModelImpl.getUuid(),
				segmentsEntryModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((segmentsEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				segmentsEntryModelImpl.getOriginalUuid(),
				segmentsEntryModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				segmentsEntryModelImpl.getGroupId(),
				segmentsEntryModelImpl.getSegmentsEntryKey()
			};

			finderCache.removeResult(_finderPathCountByG_S, args);
			finderCache.removeResult(_finderPathFetchByG_S, args);
		}

		if ((segmentsEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_S.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				segmentsEntryModelImpl.getOriginalGroupId(),
				segmentsEntryModelImpl.getOriginalSegmentsEntryKey()
			};

			finderCache.removeResult(_finderPathCountByG_S, args);
			finderCache.removeResult(_finderPathFetchByG_S, args);
		}
	}

	/**
	 * Creates a new segments entry with the primary key. Does not add the segments entry to the database.
	 *
	 * @param segmentsEntryId the primary key for the new segments entry
	 * @return the new segments entry
	 */
	@Override
	public SegmentsEntry create(long segmentsEntryId) {
		SegmentsEntry segmentsEntry = new SegmentsEntryImpl();

		segmentsEntry.setNew(true);
		segmentsEntry.setPrimaryKey(segmentsEntryId);

		String uuid = PortalUUIDUtil.generate();

		segmentsEntry.setUuid(uuid);

		segmentsEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return segmentsEntry;
	}

	/**
	 * Removes the segments entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryId the primary key of the segments entry
	 * @return the segments entry that was removed
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry remove(long segmentsEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)segmentsEntryId);
	}

	/**
	 * Removes the segments entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the segments entry
	 * @return the segments entry that was removed
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			SegmentsEntry segmentsEntry = (SegmentsEntry)session.get(
				SegmentsEntryImpl.class, primaryKey);

			if (segmentsEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(segmentsEntry);
		}
		catch (NoSuchEntryException noSuchEntityException) {
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
	protected SegmentsEntry removeImpl(SegmentsEntry segmentsEntry) {
		if (!ctPersistenceHelper.isRemove(segmentsEntry)) {
			return segmentsEntry;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(segmentsEntry)) {
				segmentsEntry = (SegmentsEntry)session.get(
					SegmentsEntryImpl.class, segmentsEntry.getPrimaryKeyObj());
			}

			if (segmentsEntry != null) {
				session.delete(segmentsEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (segmentsEntry != null) {
			clearCache(segmentsEntry);
		}

		return segmentsEntry;
	}

	@Override
	public SegmentsEntry updateImpl(SegmentsEntry segmentsEntry) {
		boolean isNew = segmentsEntry.isNew();

		if (!(segmentsEntry instanceof SegmentsEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(segmentsEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					segmentsEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in segmentsEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SegmentsEntry implementation " +
					segmentsEntry.getClass());
		}

		SegmentsEntryModelImpl segmentsEntryModelImpl =
			(SegmentsEntryModelImpl)segmentsEntry;

		if (Validator.isNull(segmentsEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			segmentsEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (segmentsEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				segmentsEntry.setCreateDate(now);
			}
			else {
				segmentsEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!segmentsEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				segmentsEntry.setModifiedDate(now);
			}
			else {
				segmentsEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(segmentsEntry)) {
				if (!isNew) {
					session.evict(
						SegmentsEntryImpl.class,
						segmentsEntry.getPrimaryKeyObj());
				}

				session.save(segmentsEntry);

				segmentsEntry.setNew(false);
			}
			else {
				segmentsEntry = (SegmentsEntry)session.merge(segmentsEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (segmentsEntry.getCtCollectionId() != 0) {
			segmentsEntry.resetOriginalValues();

			return segmentsEntry;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {segmentsEntryModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				segmentsEntryModelImpl.getUuid(),
				segmentsEntryModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {segmentsEntryModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {segmentsEntryModelImpl.getSource()};

			finderCache.removeResult(_finderPathCountBySource, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindBySource, args);

			args = new Object[] {segmentsEntryModelImpl.getType()};

			finderCache.removeResult(_finderPathCountByType, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByType, args);

			args = new Object[] {
				segmentsEntryModelImpl.getGroupId(),
				segmentsEntryModelImpl.isActive()
			};

			finderCache.removeResult(_finderPathCountByG_A, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_A, args);

			args = new Object[] {
				segmentsEntryModelImpl.isActive(),
				segmentsEntryModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByA_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByA_T, args);

			args = new Object[] {
				segmentsEntryModelImpl.getGroupId(),
				segmentsEntryModelImpl.isActive(),
				segmentsEntryModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByG_A_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_A_T, args);

			args = new Object[] {
				segmentsEntryModelImpl.getGroupId(),
				segmentsEntryModelImpl.isActive(),
				segmentsEntryModelImpl.getSource(),
				segmentsEntryModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByG_A_S_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_A_S_T, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((segmentsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					segmentsEntryModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {segmentsEntryModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((segmentsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					segmentsEntryModelImpl.getOriginalUuid(),
					segmentsEntryModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					segmentsEntryModelImpl.getUuid(),
					segmentsEntryModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((segmentsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					segmentsEntryModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {segmentsEntryModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((segmentsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindBySource.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					segmentsEntryModelImpl.getOriginalSource()
				};

				finderCache.removeResult(_finderPathCountBySource, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySource, args);

				args = new Object[] {segmentsEntryModelImpl.getSource()};

				finderCache.removeResult(_finderPathCountBySource, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySource, args);
			}

			if ((segmentsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByType.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					segmentsEntryModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByType, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByType, args);

				args = new Object[] {segmentsEntryModelImpl.getType()};

				finderCache.removeResult(_finderPathCountByType, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByType, args);
			}

			if ((segmentsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_A.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					segmentsEntryModelImpl.getOriginalGroupId(),
					segmentsEntryModelImpl.getOriginalActive()
				};

				finderCache.removeResult(_finderPathCountByG_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_A, args);

				args = new Object[] {
					segmentsEntryModelImpl.getGroupId(),
					segmentsEntryModelImpl.isActive()
				};

				finderCache.removeResult(_finderPathCountByG_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_A, args);
			}

			if ((segmentsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByA_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					segmentsEntryModelImpl.getOriginalActive(),
					segmentsEntryModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByA_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByA_T, args);

				args = new Object[] {
					segmentsEntryModelImpl.isActive(),
					segmentsEntryModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByA_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByA_T, args);
			}

			if ((segmentsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_A_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					segmentsEntryModelImpl.getOriginalGroupId(),
					segmentsEntryModelImpl.getOriginalActive(),
					segmentsEntryModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByG_A_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_A_T, args);

				args = new Object[] {
					segmentsEntryModelImpl.getGroupId(),
					segmentsEntryModelImpl.isActive(),
					segmentsEntryModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByG_A_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_A_T, args);
			}

			if ((segmentsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_A_S_T.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					segmentsEntryModelImpl.getOriginalGroupId(),
					segmentsEntryModelImpl.getOriginalActive(),
					segmentsEntryModelImpl.getOriginalSource(),
					segmentsEntryModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByG_A_S_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_A_S_T, args);

				args = new Object[] {
					segmentsEntryModelImpl.getGroupId(),
					segmentsEntryModelImpl.isActive(),
					segmentsEntryModelImpl.getSource(),
					segmentsEntryModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByG_A_S_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_A_S_T, args);
			}
		}

		entityCache.putResult(
			SegmentsEntryImpl.class, segmentsEntry.getPrimaryKey(),
			segmentsEntry, false);

		clearUniqueFindersCache(segmentsEntryModelImpl, false);
		cacheUniqueFindersCache(segmentsEntryModelImpl);

		segmentsEntry.resetOriginalValues();

		return segmentsEntry;
	}

	/**
	 * Returns the segments entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the segments entry
	 * @return the segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		SegmentsEntry segmentsEntry = fetchByPrimaryKey(primaryKey);

		if (segmentsEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return segmentsEntry;
	}

	/**
	 * Returns the segments entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param segmentsEntryId the primary key of the segments entry
	 * @return the segments entry
	 * @throws NoSuchEntryException if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry findByPrimaryKey(long segmentsEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)segmentsEntryId);
	}

	/**
	 * Returns the segments entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the segments entry
	 * @return the segments entry, or <code>null</code> if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(SegmentsEntry.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		SegmentsEntry segmentsEntry = null;

		Session session = null;

		try {
			session = openSession();

			segmentsEntry = (SegmentsEntry)session.get(
				SegmentsEntryImpl.class, primaryKey);

			if (segmentsEntry != null) {
				cacheResult(segmentsEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return segmentsEntry;
	}

	/**
	 * Returns the segments entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param segmentsEntryId the primary key of the segments entry
	 * @return the segments entry, or <code>null</code> if a segments entry with the primary key could not be found
	 */
	@Override
	public SegmentsEntry fetchByPrimaryKey(long segmentsEntryId) {
		return fetchByPrimaryKey((Serializable)segmentsEntryId);
	}

	@Override
	public Map<Serializable, SegmentsEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(SegmentsEntry.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SegmentsEntry> map =
			new HashMap<Serializable, SegmentsEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SegmentsEntry segmentsEntry = fetchByPrimaryKey(primaryKey);

			if (segmentsEntry != null) {
				map.put(primaryKey, segmentsEntry);
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

			for (SegmentsEntry segmentsEntry :
					(List<SegmentsEntry>)query.list()) {

				map.put(segmentsEntry.getPrimaryKeyObj(), segmentsEntry);

				cacheResult(segmentsEntry);
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
	 * Returns all the segments entries.
	 *
	 * @return the segments entries
	 */
	@Override
	public List<SegmentsEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of segments entries
	 */
	@Override
	public List<SegmentsEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of segments entries
	 */
	@Override
	public List<SegmentsEntry> findAll(
		int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of segments entries
	 */
	@Override
	public List<SegmentsEntry> findAll(
		int start, int end, OrderByComparator<SegmentsEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

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

		List<SegmentsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SEGMENTSENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SEGMENTSENTRY;

				sql = sql.concat(SegmentsEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SegmentsEntry>)QueryUtil.list(
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
	 * Removes all the segments entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SegmentsEntry segmentsEntry : findAll()) {
			remove(segmentsEntry);
		}
	}

	/**
	 * Returns the number of segments entries.
	 *
	 * @return the number of segments entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsEntry.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_SEGMENTSENTRY);

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
		return "segmentsEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SEGMENTSENTRY;
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
		return SegmentsEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "SegmentsEntry";
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
		ctStrictColumnNames.add("segmentsEntryKey");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("description");
		ctStrictColumnNames.add("active_");
		ctStrictColumnNames.add("criteria");
		ctStrictColumnNames.add("source");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("segmentsEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "segmentsEntryKey"});
	}

	/**
	 * Initializes the segments entry persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			SegmentsEntryModelImpl.UUID_COLUMN_BITMASK |
			SegmentsEntryModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			SegmentsEntryModelImpl.UUID_COLUMN_BITMASK |
			SegmentsEntryModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			SegmentsEntryModelImpl.UUID_COLUMN_BITMASK |
			SegmentsEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			SegmentsEntryModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] {Long.class.getName()},
			SegmentsEntryModelImpl.GROUPID_COLUMN_BITMASK |
			SegmentsEntryModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindBySource = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBySource",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindBySource = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBySource", new String[] {String.class.getName()},
			SegmentsEntryModelImpl.SOURCE_COLUMN_BITMASK |
			SegmentsEntryModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountBySource = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySource", new String[] {String.class.getName()});

		_finderPathWithPaginationFindByType = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByType",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByType = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByType", new String[] {String.class.getName()},
			SegmentsEntryModelImpl.TYPE_COLUMN_BITMASK |
			SegmentsEntryModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByType = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByType", new String[] {String.class.getName()});

		_finderPathFetchByG_S = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_S",
			new String[] {Long.class.getName(), String.class.getName()},
			SegmentsEntryModelImpl.GROUPID_COLUMN_BITMASK |
			SegmentsEntryModelImpl.SEGMENTSENTRYKEY_COLUMN_BITMASK);

		_finderPathCountByG_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_A = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_A = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			SegmentsEntryModelImpl.GROUPID_COLUMN_BITMASK |
			SegmentsEntryModelImpl.ACTIVE_COLUMN_BITMASK |
			SegmentsEntryModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByG_A = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationCountByG_A = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_A",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByA_T = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByA_T",
			new String[] {
				Boolean.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByA_T = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByA_T",
			new String[] {Boolean.class.getName(), String.class.getName()},
			SegmentsEntryModelImpl.ACTIVE_COLUMN_BITMASK |
			SegmentsEntryModelImpl.TYPE_COLUMN_BITMASK |
			SegmentsEntryModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByA_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_T",
			new String[] {Boolean.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_A_T = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_A_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_A_T = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_A_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			SegmentsEntryModelImpl.GROUPID_COLUMN_BITMASK |
			SegmentsEntryModelImpl.ACTIVE_COLUMN_BITMASK |
			SegmentsEntryModelImpl.TYPE_COLUMN_BITMASK |
			SegmentsEntryModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByG_A_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_A_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationCountByG_A_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_A_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByG_A_S_T = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_A_S_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_A_S_T = new FinderPath(
			SegmentsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_A_S_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), String.class.getName()
			},
			SegmentsEntryModelImpl.GROUPID_COLUMN_BITMASK |
			SegmentsEntryModelImpl.ACTIVE_COLUMN_BITMASK |
			SegmentsEntryModelImpl.SOURCE_COLUMN_BITMASK |
			SegmentsEntryModelImpl.TYPE_COLUMN_BITMASK |
			SegmentsEntryModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByG_A_S_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_A_S_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), String.class.getName()
			});

		_finderPathWithPaginationCountByG_A_S_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_A_S_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), String.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SegmentsEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = SegmentsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SegmentsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SegmentsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_SEGMENTSENTRY =
		"SELECT segmentsEntry FROM SegmentsEntry segmentsEntry";

	private static final String _SQL_SELECT_SEGMENTSENTRY_WHERE =
		"SELECT segmentsEntry FROM SegmentsEntry segmentsEntry WHERE ";

	private static final String _SQL_COUNT_SEGMENTSENTRY =
		"SELECT COUNT(segmentsEntry) FROM SegmentsEntry segmentsEntry";

	private static final String _SQL_COUNT_SEGMENTSENTRY_WHERE =
		"SELECT COUNT(segmentsEntry) FROM SegmentsEntry segmentsEntry WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"segmentsEntry.segmentsEntryId";

	private static final String _FILTER_SQL_SELECT_SEGMENTSENTRY_WHERE =
		"SELECT DISTINCT {segmentsEntry.*} FROM SegmentsEntry segmentsEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {SegmentsEntry.*} FROM (SELECT DISTINCT segmentsEntry.segmentsEntryId FROM SegmentsEntry segmentsEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_SEGMENTSENTRY_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN SegmentsEntry ON TEMP_TABLE.segmentsEntryId = SegmentsEntry.segmentsEntryId";

	private static final String _FILTER_SQL_COUNT_SEGMENTSENTRY_WHERE =
		"SELECT COUNT(DISTINCT segmentsEntry.segmentsEntryId) AS COUNT_VALUE FROM SegmentsEntry segmentsEntry WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "segmentsEntry";

	private static final String _FILTER_ENTITY_TABLE = "SegmentsEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS = "segmentsEntry.";

	private static final String _ORDER_BY_ENTITY_TABLE = "SegmentsEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SegmentsEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SegmentsEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "active", "type"});

	static {
		try {
			Class.forName(SegmentsPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}