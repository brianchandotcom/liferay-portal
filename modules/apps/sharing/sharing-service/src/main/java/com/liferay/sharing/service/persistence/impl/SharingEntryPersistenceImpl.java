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

package com.liferay.sharing.service.persistence.impl;

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
import com.liferay.sharing.exception.NoSuchEntryException;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.model.SharingEntryTable;
import com.liferay.sharing.model.impl.SharingEntryImpl;
import com.liferay.sharing.model.impl.SharingEntryModelImpl;
import com.liferay.sharing.service.persistence.SharingEntryPersistence;
import com.liferay.sharing.service.persistence.impl.constants.SharingPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

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
 * The persistence implementation for the sharing entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {SharingEntryPersistence.class, BasePersistence.class})
public class SharingEntryPersistenceImpl
	extends BasePersistenceImpl<SharingEntry>
	implements SharingEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SharingEntryUtil</code> to access the sharing entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SharingEntryImpl.class.getName();

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
	 * Returns all the sharing entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
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

		List<SharingEntry> list = null;

		if (useFinderCache) {
			list = (List<SharingEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if (!uuid.equals(sharingEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

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
				sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<SharingEntry>)QueryUtil.list(
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
	 * Returns the first sharing entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByUuid_First(
			String uuid, OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByUuid_First(uuid, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByUuid_First(
		String uuid, OrderByComparator<SharingEntry> orderByComparator) {

		List<SharingEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByUuid_Last(
			String uuid, OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByUuid_Last(
		String uuid, OrderByComparator<SharingEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where uuid = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByUuid_PrevAndNext(
			long sharingEntryId, String uuid,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, sharingEntry, uuid, orderByComparator, true);

			array[1] = sharingEntry;

			array[2] = getByUuid_PrevAndNext(
				session, sharingEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SharingEntry getByUuid_PrevAndNext(
		Session session, SharingEntry sharingEntry, String uuid,
		OrderByComparator<SharingEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

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
			sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(sharingEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SharingEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (SharingEntry sharingEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SHARINGENTRY_WHERE);

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
		"sharingEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(sharingEntry.uuid IS NULL OR sharingEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the sharing entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByUUID_G(uuid, groupId);

		if (sharingEntry == null) {
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

		return sharingEntry;
	}

	/**
	 * Returns the sharing entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the sharing entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByUUID_G(
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

		if (result instanceof SharingEntry) {
			SharingEntry sharingEntry = (SharingEntry)result;

			if (!Objects.equals(uuid, sharingEntry.getUuid()) ||
				(groupId != sharingEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

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

				List<SharingEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					SharingEntry sharingEntry = list.get(0);

					result = sharingEntry;

					cacheResult(sharingEntry);
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
			return (SharingEntry)result;
		}
	}

	/**
	 * Removes the sharing entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the sharing entry that was removed
	 */
	@Override
	public SharingEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = findByUUID_G(uuid, groupId);

		return remove(sharingEntry);
	}

	/**
	 * Returns the number of sharing entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SHARINGENTRY_WHERE);

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
		"sharingEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(sharingEntry.uuid IS NULL OR sharingEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"sharingEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the sharing entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
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

		List<SharingEntry> list = null;

		if (useFinderCache) {
			list = (List<SharingEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if (!uuid.equals(sharingEntry.getUuid()) ||
						(companyId != sharingEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

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
				sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<SharingEntry>)QueryUtil.list(
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
	 * Returns the first sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
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
	 * Returns the first sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SharingEntry> orderByComparator) {

		List<SharingEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
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
	 * Returns the last sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SharingEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByUuid_C_PrevAndNext(
			long sharingEntryId, String uuid, long companyId,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, sharingEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = sharingEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, sharingEntry, uuid, companyId, orderByComparator,
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

	protected SharingEntry getByUuid_C_PrevAndNext(
		Session session, SharingEntry sharingEntry, String uuid, long companyId,
		OrderByComparator<SharingEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

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
			sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(sharingEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SharingEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (SharingEntry sharingEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SHARINGENTRY_WHERE);

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
		"sharingEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(sharingEntry.uuid IS NULL OR sharingEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"sharingEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the sharing entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<SharingEntry> list = null;

		if (useFinderCache) {
			list = (List<SharingEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if (groupId != sharingEntry.getGroupId()) {
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

			sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<SharingEntry>)QueryUtil.list(
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
	 * Returns the first sharing entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByGroupId_First(
			long groupId, OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByGroupId_First(
		long groupId, OrderByComparator<SharingEntry> orderByComparator) {

		List<SharingEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByGroupId_Last(
			long groupId, OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<SharingEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where groupId = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByGroupId_PrevAndNext(
			long sharingEntryId, long groupId,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, sharingEntry, groupId, orderByComparator, true);

			array[1] = sharingEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, sharingEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SharingEntry getByGroupId_PrevAndNext(
		Session session, SharingEntry sharingEntry, long groupId,
		OrderByComparator<SharingEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

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
			sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(sharingEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SharingEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SharingEntry sharingEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"sharingEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the sharing entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<SharingEntry> list = null;

		if (useFinderCache) {
			list = (List<SharingEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if (userId != sharingEntry.getUserId()) {
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

			sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<SharingEntry>)QueryUtil.list(
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
	 * Returns the first sharing entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByUserId_First(
			long userId, OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByUserId_First(
			userId, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByUserId_First(
		long userId, OrderByComparator<SharingEntry> orderByComparator) {

		List<SharingEntry> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByUserId_Last(
			long userId, OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByUserId_Last(
			userId, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByUserId_Last(
		long userId, OrderByComparator<SharingEntry> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where userId = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByUserId_PrevAndNext(
			long sharingEntryId, long userId,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, sharingEntry, userId, orderByComparator, true);

			array[1] = sharingEntry;

			array[2] = getByUserId_PrevAndNext(
				session, sharingEntry, userId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SharingEntry getByUserId_PrevAndNext(
		Session session, SharingEntry sharingEntry, long userId,
		OrderByComparator<SharingEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

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
			sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(sharingEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SharingEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (SharingEntry sharingEntry :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"sharingEntry.userId = ?";

	private FinderPath _finderPathWithPaginationFindByToUserId;
	private FinderPath _finderPathWithoutPaginationFindByToUserId;
	private FinderPath _finderPathCountByToUserId;

	/**
	 * Returns all the sharing entries where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByToUserId(long toUserId) {
		return findByToUserId(
			toUserId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where toUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByToUserId(
		long toUserId, int start, int end) {

		return findByToUserId(toUserId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where toUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByToUserId(
		long toUserId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {

		return findByToUserId(toUserId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where toUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByToUserId(
		long toUserId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByToUserId;
				finderArgs = new Object[] {toUserId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByToUserId;
			finderArgs = new Object[] {toUserId, start, end, orderByComparator};
		}

		List<SharingEntry> list = null;

		if (useFinderCache) {
			list = (List<SharingEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if (toUserId != sharingEntry.getToUserId()) {
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

			sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_TOUSERID_TOUSERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(toUserId);

				list = (List<SharingEntry>)QueryUtil.list(
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
	 * Returns the first sharing entry in the ordered set where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByToUserId_First(
			long toUserId, OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByToUserId_First(
			toUserId, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("toUserId=");
		sb.append(toUserId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByToUserId_First(
		long toUserId, OrderByComparator<SharingEntry> orderByComparator) {

		List<SharingEntry> list = findByToUserId(
			toUserId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByToUserId_Last(
			long toUserId, OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByToUserId_Last(
			toUserId, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("toUserId=");
		sb.append(toUserId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByToUserId_Last(
		long toUserId, OrderByComparator<SharingEntry> orderByComparator) {

		int count = countByToUserId(toUserId);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByToUserId(
			toUserId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where toUserId = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param toUserId the to user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByToUserId_PrevAndNext(
			long sharingEntryId, long toUserId,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByToUserId_PrevAndNext(
				session, sharingEntry, toUserId, orderByComparator, true);

			array[1] = sharingEntry;

			array[2] = getByToUserId_PrevAndNext(
				session, sharingEntry, toUserId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SharingEntry getByToUserId_PrevAndNext(
		Session session, SharingEntry sharingEntry, long toUserId,
		OrderByComparator<SharingEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

		sb.append(_FINDER_COLUMN_TOUSERID_TOUSERID_2);

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
			sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(toUserId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(sharingEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SharingEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where toUserId = &#63; from the database.
	 *
	 * @param toUserId the to user ID
	 */
	@Override
	public void removeByToUserId(long toUserId) {
		for (SharingEntry sharingEntry :
				findByToUserId(
					toUserId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByToUserId(long toUserId) {
		FinderPath finderPath = _finderPathCountByToUserId;

		Object[] finderArgs = new Object[] {toUserId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_TOUSERID_TOUSERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(toUserId);

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

	private static final String _FINDER_COLUMN_TOUSERID_TOUSERID_2 =
		"sharingEntry.toUserId = ?";

	private FinderPath _finderPathWithPaginationFindByLtExpirationDate;
	private FinderPath _finderPathWithPaginationCountByLtExpirationDate;

	/**
	 * Returns all the sharing entries where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByLtExpirationDate(Date expirationDate) {
		return findByLtExpirationDate(
			expirationDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByLtExpirationDate(
		Date expirationDate, int start, int end) {

		return findByLtExpirationDate(expirationDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByLtExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {

		return findByLtExpirationDate(
			expirationDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByLtExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtExpirationDate;
		finderArgs = new Object[] {
			_getTime(expirationDate), start, end, orderByComparator
		};

		List<SharingEntry> list = null;

		if (useFinderCache) {
			list = (List<SharingEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if (expirationDate.getTime() <=
							sharingEntry.getExpirationDate(
							).getTime()) {

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

			sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
				}

				list = (List<SharingEntry>)QueryUtil.list(
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
	 * Returns the first sharing entry in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByLtExpirationDate_First(
			Date expirationDate,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByLtExpirationDate_First(
			expirationDate, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate<");
		sb.append(expirationDate);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByLtExpirationDate_First(
		Date expirationDate,
		OrderByComparator<SharingEntry> orderByComparator) {

		List<SharingEntry> list = findByLtExpirationDate(
			expirationDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByLtExpirationDate_Last(
			Date expirationDate,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByLtExpirationDate_Last(
			expirationDate, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate<");
		sb.append(expirationDate);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByLtExpirationDate_Last(
		Date expirationDate,
		OrderByComparator<SharingEntry> orderByComparator) {

		int count = countByLtExpirationDate(expirationDate);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByLtExpirationDate(
			expirationDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByLtExpirationDate_PrevAndNext(
			long sharingEntryId, Date expirationDate,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByLtExpirationDate_PrevAndNext(
				session, sharingEntry, expirationDate, orderByComparator, true);

			array[1] = sharingEntry;

			array[2] = getByLtExpirationDate_PrevAndNext(
				session, sharingEntry, expirationDate, orderByComparator,
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

	protected SharingEntry getByLtExpirationDate_PrevAndNext(
		Session session, SharingEntry sharingEntry, Date expirationDate,
		OrderByComparator<SharingEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

		boolean bindExpirationDate = false;

		if (expirationDate == null) {
			sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1);
		}
		else {
			bindExpirationDate = true;

			sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2);
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
			sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindExpirationDate) {
			queryPos.add(new Timestamp(expirationDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(sharingEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SharingEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where expirationDate &lt; &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 */
	@Override
	public void removeByLtExpirationDate(Date expirationDate) {
		for (SharingEntry sharingEntry :
				findByLtExpirationDate(
					expirationDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByLtExpirationDate(Date expirationDate) {
		FinderPath finderPath =
			_finderPathWithPaginationCountByLtExpirationDate;

		Object[] finderArgs = new Object[] {_getTime(expirationDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SHARINGENTRY_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
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

	private static final String
		_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1 =
			"sharingEntry.expirationDate IS NULL";

	private static final String
		_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2 =
			"sharingEntry.expirationDate < ?";

	private FinderPath _finderPathWithPaginationFindByU_C;
	private FinderPath _finderPathWithoutPaginationFindByU_C;
	private FinderPath _finderPathCountByU_C;

	/**
	 * Returns all the sharing entries where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByU_C(long userId, long classNameId) {
		return findByU_C(
			userId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByU_C(
		long userId, long classNameId, int start, int end) {

		return findByU_C(userId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByU_C(
		long userId, long classNameId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {

		return findByU_C(
			userId, classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByU_C(
		long userId, long classNameId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_C;
				finderArgs = new Object[] {userId, classNameId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_C;
			finderArgs = new Object[] {
				userId, classNameId, start, end, orderByComparator
			};
		}

		List<SharingEntry> list = null;

		if (useFinderCache) {
			list = (List<SharingEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if ((userId != sharingEntry.getUserId()) ||
						(classNameId != sharingEntry.getClassNameId())) {

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

			sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_C_USERID_2);

			sb.append(_FINDER_COLUMN_U_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(classNameId);

				list = (List<SharingEntry>)QueryUtil.list(
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
	 * Returns the first sharing entry in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByU_C_First(
			long userId, long classNameId,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByU_C_First(
			userId, classNameId, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByU_C_First(
		long userId, long classNameId,
		OrderByComparator<SharingEntry> orderByComparator) {

		List<SharingEntry> list = findByU_C(
			userId, classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByU_C_Last(
			long userId, long classNameId,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByU_C_Last(
			userId, classNameId, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByU_C_Last(
		long userId, long classNameId,
		OrderByComparator<SharingEntry> orderByComparator) {

		int count = countByU_C(userId, classNameId);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByU_C(
			userId, classNameId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByU_C_PrevAndNext(
			long sharingEntryId, long userId, long classNameId,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByU_C_PrevAndNext(
				session, sharingEntry, userId, classNameId, orderByComparator,
				true);

			array[1] = sharingEntry;

			array[2] = getByU_C_PrevAndNext(
				session, sharingEntry, userId, classNameId, orderByComparator,
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

	protected SharingEntry getByU_C_PrevAndNext(
		Session session, SharingEntry sharingEntry, long userId,
		long classNameId, OrderByComparator<SharingEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

		sb.append(_FINDER_COLUMN_U_C_USERID_2);

		sb.append(_FINDER_COLUMN_U_C_CLASSNAMEID_2);

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
			sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		queryPos.add(classNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(sharingEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SharingEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where userId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByU_C(long userId, long classNameId) {
		for (SharingEntry sharingEntry :
				findByU_C(
					userId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByU_C(long userId, long classNameId) {
		FinderPath finderPath = _finderPathCountByU_C;

		Object[] finderArgs = new Object[] {userId, classNameId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_C_USERID_2);

			sb.append(_FINDER_COLUMN_U_C_CLASSNAMEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_U_C_USERID_2 =
		"sharingEntry.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_C_CLASSNAMEID_2 =
		"sharingEntry.classNameId = ?";

	private FinderPath _finderPathWithPaginationFindByTU_C;
	private FinderPath _finderPathWithoutPaginationFindByTU_C;
	private FinderPath _finderPathCountByTU_C;

	/**
	 * Returns all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByTU_C(long toUserId, long classNameId) {
		return findByTU_C(
			toUserId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByTU_C(
		long toUserId, long classNameId, int start, int end) {

		return findByTU_C(toUserId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByTU_C(
		long toUserId, long classNameId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {

		return findByTU_C(
			toUserId, classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByTU_C(
		long toUserId, long classNameId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByTU_C;
				finderArgs = new Object[] {toUserId, classNameId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByTU_C;
			finderArgs = new Object[] {
				toUserId, classNameId, start, end, orderByComparator
			};
		}

		List<SharingEntry> list = null;

		if (useFinderCache) {
			list = (List<SharingEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if ((toUserId != sharingEntry.getToUserId()) ||
						(classNameId != sharingEntry.getClassNameId())) {

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

			sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_TU_C_TOUSERID_2);

			sb.append(_FINDER_COLUMN_TU_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(toUserId);

				queryPos.add(classNameId);

				list = (List<SharingEntry>)QueryUtil.list(
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
	 * Returns the first sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByTU_C_First(
			long toUserId, long classNameId,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByTU_C_First(
			toUserId, classNameId, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("toUserId=");
		sb.append(toUserId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByTU_C_First(
		long toUserId, long classNameId,
		OrderByComparator<SharingEntry> orderByComparator) {

		List<SharingEntry> list = findByTU_C(
			toUserId, classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByTU_C_Last(
			long toUserId, long classNameId,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByTU_C_Last(
			toUserId, classNameId, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("toUserId=");
		sb.append(toUserId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByTU_C_Last(
		long toUserId, long classNameId,
		OrderByComparator<SharingEntry> orderByComparator) {

		int count = countByTU_C(toUserId, classNameId);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByTU_C(
			toUserId, classNameId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByTU_C_PrevAndNext(
			long sharingEntryId, long toUserId, long classNameId,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByTU_C_PrevAndNext(
				session, sharingEntry, toUserId, classNameId, orderByComparator,
				true);

			array[1] = sharingEntry;

			array[2] = getByTU_C_PrevAndNext(
				session, sharingEntry, toUserId, classNameId, orderByComparator,
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

	protected SharingEntry getByTU_C_PrevAndNext(
		Session session, SharingEntry sharingEntry, long toUserId,
		long classNameId, OrderByComparator<SharingEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

		sb.append(_FINDER_COLUMN_TU_C_TOUSERID_2);

		sb.append(_FINDER_COLUMN_TU_C_CLASSNAMEID_2);

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
			sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(toUserId);

		queryPos.add(classNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(sharingEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SharingEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where toUserId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByTU_C(long toUserId, long classNameId) {
		for (SharingEntry sharingEntry :
				findByTU_C(
					toUserId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByTU_C(long toUserId, long classNameId) {
		FinderPath finderPath = _finderPathCountByTU_C;

		Object[] finderArgs = new Object[] {toUserId, classNameId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_TU_C_TOUSERID_2);

			sb.append(_FINDER_COLUMN_TU_C_CLASSNAMEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(toUserId);

				queryPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_TU_C_TOUSERID_2 =
		"sharingEntry.toUserId = ? AND ";

	private static final String _FINDER_COLUMN_TU_C_CLASSNAMEID_2 =
		"sharingEntry.classNameId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByC_C(long classNameId, long classPK) {
		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<SharingEntry> list = null;

		if (useFinderCache) {
			list = (List<SharingEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if ((classNameId != sharingEntry.getClassNameId()) ||
						(classPK != sharingEntry.getClassPK())) {

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

			sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<SharingEntry>)QueryUtil.list(
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
	 * Returns the first sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator) {

		List<SharingEntry> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByC_C_PrevAndNext(
			long sharingEntryId, long classNameId, long classPK,
			OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, sharingEntry, classNameId, classPK, orderByComparator,
				true);

			array[1] = sharingEntry;

			array[2] = getByC_C_PrevAndNext(
				session, sharingEntry, classNameId, classPK, orderByComparator,
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

	protected SharingEntry getByC_C_PrevAndNext(
		Session session, SharingEntry sharingEntry, long classNameId,
		long classPK, OrderByComparator<SharingEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

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
			sb.append(SharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(sharingEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SharingEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (SharingEntry sharingEntry :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"sharingEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"sharingEntry.classPK = ?";

	private FinderPath _finderPathFetchByTU_C_C;
	private FinderPath _finderPathCountByTU_C_C;

	/**
	 * Returns the sharing entry where toUserId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByTU_C_C(
			long toUserId, long classNameId, long classPK)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByTU_C_C(
			toUserId, classNameId, classPK);

		if (sharingEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("toUserId=");
			sb.append(toUserId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return sharingEntry;
	}

	/**
	 * Returns the sharing entry where toUserId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByTU_C_C(
		long toUserId, long classNameId, long classPK) {

		return fetchByTU_C_C(toUserId, classNameId, classPK, true);
	}

	/**
	 * Returns the sharing entry where toUserId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByTU_C_C(
		long toUserId, long classNameId, long classPK, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {toUserId, classNameId, classPK};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByTU_C_C, finderArgs);
		}

		if (result instanceof SharingEntry) {
			SharingEntry sharingEntry = (SharingEntry)result;

			if ((toUserId != sharingEntry.getToUserId()) ||
				(classNameId != sharingEntry.getClassNameId()) ||
				(classPK != sharingEntry.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_SHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_TU_C_C_TOUSERID_2);

			sb.append(_FINDER_COLUMN_TU_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_TU_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(toUserId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				List<SharingEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByTU_C_C, finderArgs, list);
					}
				}
				else {
					SharingEntry sharingEntry = list.get(0);

					result = sharingEntry;

					cacheResult(sharingEntry);
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
			return (SharingEntry)result;
		}
	}

	/**
	 * Removes the sharing entry where toUserId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the sharing entry that was removed
	 */
	@Override
	public SharingEntry removeByTU_C_C(
			long toUserId, long classNameId, long classPK)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = findByTU_C_C(
			toUserId, classNameId, classPK);

		return remove(sharingEntry);
	}

	/**
	 * Returns the number of sharing entries where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByTU_C_C(long toUserId, long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByTU_C_C;

		Object[] finderArgs = new Object[] {toUserId, classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_SHARINGENTRY_WHERE);

			sb.append(_FINDER_COLUMN_TU_C_C_TOUSERID_2);

			sb.append(_FINDER_COLUMN_TU_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_TU_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(toUserId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_TU_C_C_TOUSERID_2 =
		"sharingEntry.toUserId = ? AND ";

	private static final String _FINDER_COLUMN_TU_C_C_CLASSNAMEID_2 =
		"sharingEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_TU_C_C_CLASSPK_2 =
		"sharingEntry.classPK = ?";

	public SharingEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SharingEntry.class);

		setModelImplClass(SharingEntryImpl.class);
		setModelPKClass(long.class);

		setTable(SharingEntryTable.INSTANCE);
	}

	/**
	 * Caches the sharing entry in the entity cache if it is enabled.
	 *
	 * @param sharingEntry the sharing entry
	 */
	@Override
	public void cacheResult(SharingEntry sharingEntry) {
		entityCache.putResult(
			SharingEntryImpl.class, sharingEntry.getPrimaryKey(), sharingEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {sharingEntry.getUuid(), sharingEntry.getGroupId()},
			sharingEntry);

		finderCache.putResult(
			_finderPathFetchByTU_C_C,
			new Object[] {
				sharingEntry.getToUserId(), sharingEntry.getClassNameId(),
				sharingEntry.getClassPK()
			},
			sharingEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the sharing entries in the entity cache if it is enabled.
	 *
	 * @param sharingEntries the sharing entries
	 */
	@Override
	public void cacheResult(List<SharingEntry> sharingEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (sharingEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (SharingEntry sharingEntry : sharingEntries) {
			if (entityCache.getResult(
					SharingEntryImpl.class, sharingEntry.getPrimaryKey()) ==
						null) {

				cacheResult(sharingEntry);
			}
		}
	}

	/**
	 * Clears the cache for all sharing entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SharingEntryImpl.class);

		finderCache.clearCache(SharingEntryImpl.class);
	}

	/**
	 * Clears the cache for the sharing entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SharingEntry sharingEntry) {
		entityCache.removeResult(SharingEntryImpl.class, sharingEntry);
	}

	@Override
	public void clearCache(List<SharingEntry> sharingEntries) {
		for (SharingEntry sharingEntry : sharingEntries) {
			entityCache.removeResult(SharingEntryImpl.class, sharingEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(SharingEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(SharingEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SharingEntryModelImpl sharingEntryModelImpl) {

		Object[] args = new Object[] {
			sharingEntryModelImpl.getUuid(), sharingEntryModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, sharingEntryModelImpl);

		args = new Object[] {
			sharingEntryModelImpl.getToUserId(),
			sharingEntryModelImpl.getClassNameId(),
			sharingEntryModelImpl.getClassPK()
		};

		finderCache.putResult(_finderPathCountByTU_C_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByTU_C_C, args, sharingEntryModelImpl);
	}

	/**
	 * Creates a new sharing entry with the primary key. Does not add the sharing entry to the database.
	 *
	 * @param sharingEntryId the primary key for the new sharing entry
	 * @return the new sharing entry
	 */
	@Override
	public SharingEntry create(long sharingEntryId) {
		SharingEntry sharingEntry = new SharingEntryImpl();

		sharingEntry.setNew(true);
		sharingEntry.setPrimaryKey(sharingEntryId);

		String uuid = PortalUUIDUtil.generate();

		sharingEntry.setUuid(uuid);

		sharingEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return sharingEntry;
	}

	/**
	 * Removes the sharing entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sharingEntryId the primary key of the sharing entry
	 * @return the sharing entry that was removed
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry remove(long sharingEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)sharingEntryId);
	}

	/**
	 * Removes the sharing entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the sharing entry
	 * @return the sharing entry that was removed
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			SharingEntry sharingEntry = (SharingEntry)session.get(
				SharingEntryImpl.class, primaryKey);

			if (sharingEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(sharingEntry);
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
	protected SharingEntry removeImpl(SharingEntry sharingEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(sharingEntry)) {
				sharingEntry = (SharingEntry)session.get(
					SharingEntryImpl.class, sharingEntry.getPrimaryKeyObj());
			}

			if (sharingEntry != null) {
				session.delete(sharingEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (sharingEntry != null) {
			clearCache(sharingEntry);
		}

		return sharingEntry;
	}

	@Override
	public SharingEntry updateImpl(SharingEntry sharingEntry) {
		boolean isNew = sharingEntry.isNew();

		if (!(sharingEntry instanceof SharingEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(sharingEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					sharingEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in sharingEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SharingEntry implementation " +
					sharingEntry.getClass());
		}

		SharingEntryModelImpl sharingEntryModelImpl =
			(SharingEntryModelImpl)sharingEntry;

		if (Validator.isNull(sharingEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			sharingEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (sharingEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				sharingEntry.setCreateDate(date);
			}
			else {
				sharingEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!sharingEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				sharingEntry.setModifiedDate(date);
			}
			else {
				sharingEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(sharingEntry);
			}
			else {
				sharingEntry = (SharingEntry)session.merge(sharingEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			SharingEntryImpl.class, sharingEntryModelImpl, false, true);

		cacheUniqueFindersCache(sharingEntryModelImpl);

		if (isNew) {
			sharingEntry.setNew(false);
		}

		sharingEntry.resetOriginalValues();

		return sharingEntry;
	}

	/**
	 * Returns the sharing entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the sharing entry
	 * @return the sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		SharingEntry sharingEntry = fetchByPrimaryKey(primaryKey);

		if (sharingEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return sharingEntry;
	}

	/**
	 * Returns the sharing entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param sharingEntryId the primary key of the sharing entry
	 * @return the sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry findByPrimaryKey(long sharingEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)sharingEntryId);
	}

	/**
	 * Returns the sharing entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sharingEntryId the primary key of the sharing entry
	 * @return the sharing entry, or <code>null</code> if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry fetchByPrimaryKey(long sharingEntryId) {
		return fetchByPrimaryKey((Serializable)sharingEntryId);
	}

	/**
	 * Returns all the sharing entries.
	 *
	 * @return the sharing entries
	 */
	@Override
	public List<SharingEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of sharing entries
	 */
	@Override
	public List<SharingEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sharing entries
	 */
	@Override
	public List<SharingEntry> findAll(
		int start, int end, OrderByComparator<SharingEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sharing entries
	 */
	@Override
	public List<SharingEntry> findAll(
		int start, int end, OrderByComparator<SharingEntry> orderByComparator,
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

		List<SharingEntry> list = null;

		if (useFinderCache) {
			list = (List<SharingEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SHARINGENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SHARINGENTRY;

				sql = sql.concat(SharingEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SharingEntry>)QueryUtil.list(
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
	 * Removes all the sharing entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SharingEntry sharingEntry : findAll()) {
			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries.
	 *
	 * @return the number of sharing entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_SHARINGENTRY);

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
		return "sharingEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SHARINGENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SharingEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the sharing entry persistence.
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

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathWithPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userId"}, true);

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);

		_finderPathWithPaginationFindByToUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByToUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"toUserId"}, true);

		_finderPathWithoutPaginationFindByToUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByToUserId",
			new String[] {Long.class.getName()}, new String[] {"toUserId"},
			true);

		_finderPathCountByToUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByToUserId",
			new String[] {Long.class.getName()}, new String[] {"toUserId"},
			false);

		_finderPathWithPaginationFindByLtExpirationDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtExpirationDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"expirationDate"}, true);

		_finderPathWithPaginationCountByLtExpirationDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtExpirationDate",
			new String[] {Date.class.getName()},
			new String[] {"expirationDate"}, false);

		_finderPathWithPaginationFindByU_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"userId", "classNameId"}, true);

		_finderPathWithoutPaginationFindByU_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"userId", "classNameId"}, true);

		_finderPathCountByU_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"userId", "classNameId"}, false);

		_finderPathWithPaginationFindByTU_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByTU_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"toUserId", "classNameId"}, true);

		_finderPathWithoutPaginationFindByTU_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTU_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"toUserId", "classNameId"}, true);

		_finderPathCountByTU_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTU_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"toUserId", "classNameId"}, false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);

		_finderPathFetchByTU_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByTU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"toUserId", "classNameId", "classPK"}, true);

		_finderPathCountByTU_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"toUserId", "classNameId", "classPK"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SharingEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = SharingPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SharingPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SharingPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_SHARINGENTRY =
		"SELECT sharingEntry FROM SharingEntry sharingEntry";

	private static final String _SQL_SELECT_SHARINGENTRY_WHERE =
		"SELECT sharingEntry FROM SharingEntry sharingEntry WHERE ";

	private static final String _SQL_COUNT_SHARINGENTRY =
		"SELECT COUNT(sharingEntry) FROM SharingEntry sharingEntry";

	private static final String _SQL_COUNT_SHARINGENTRY_WHERE =
		"SELECT COUNT(sharingEntry) FROM SharingEntry sharingEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "sharingEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SharingEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SharingEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SharingEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private SharingEntryModelArgumentsResolver
		_sharingEntryModelArgumentsResolver;

}