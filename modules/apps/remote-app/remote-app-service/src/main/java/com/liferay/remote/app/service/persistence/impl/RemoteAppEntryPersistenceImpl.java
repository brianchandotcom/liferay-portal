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

package com.liferay.remote.app.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.remote.app.exception.NoSuchEntryException;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.model.RemoteAppEntryTable;
import com.liferay.remote.app.model.impl.RemoteAppEntryImpl;
import com.liferay.remote.app.model.impl.RemoteAppEntryModelImpl;
import com.liferay.remote.app.service.persistence.RemoteAppEntryPersistence;
import com.liferay.remote.app.service.persistence.impl.constants.RemoteAppPersistenceConstants;

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
 * The persistence implementation for the remote app entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = RemoteAppEntryPersistence.class)
public class RemoteAppEntryPersistenceImpl
	extends BasePersistenceImpl<RemoteAppEntry>
	implements RemoteAppEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RemoteAppEntryUtil</code> to access the remote app entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RemoteAppEntryImpl.class.getName();

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
	 * Returns all the remote app entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching remote app entries
	 */
	@Override
	public List<RemoteAppEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the remote app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @return the range of matching remote app entries
	 */
	@Override
	public List<RemoteAppEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the remote app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote app entries
	 */
	@Override
	public List<RemoteAppEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RemoteAppEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the remote app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote app entries
	 */
	@Override
	public List<RemoteAppEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RemoteAppEntry> orderByComparator,
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

		List<RemoteAppEntry> list = null;

		if (useFinderCache) {
			list = (List<RemoteAppEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RemoteAppEntry remoteAppEntry : list) {
					if (!uuid.equals(remoteAppEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_REMOTEAPPENTRY_WHERE);

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
				sb.append(RemoteAppEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<RemoteAppEntry>)QueryUtil.list(
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
	 * Returns the first remote app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry
	 * @throws NoSuchEntryException if a matching remote app entry could not be found
	 */
	@Override
	public RemoteAppEntry findByUuid_First(
			String uuid, OrderByComparator<RemoteAppEntry> orderByComparator)
		throws NoSuchEntryException {

		RemoteAppEntry remoteAppEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (remoteAppEntry != null) {
			return remoteAppEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first remote app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	@Override
	public RemoteAppEntry fetchByUuid_First(
		String uuid, OrderByComparator<RemoteAppEntry> orderByComparator) {

		List<RemoteAppEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last remote app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry
	 * @throws NoSuchEntryException if a matching remote app entry could not be found
	 */
	@Override
	public RemoteAppEntry findByUuid_Last(
			String uuid, OrderByComparator<RemoteAppEntry> orderByComparator)
		throws NoSuchEntryException {

		RemoteAppEntry remoteAppEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (remoteAppEntry != null) {
			return remoteAppEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last remote app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	@Override
	public RemoteAppEntry fetchByUuid_Last(
		String uuid, OrderByComparator<RemoteAppEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<RemoteAppEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the remote app entries before and after the current remote app entry in the ordered set where uuid = &#63;.
	 *
	 * @param remoteAppEntryId the primary key of the current remote app entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote app entry
	 * @throws NoSuchEntryException if a remote app entry with the primary key could not be found
	 */
	@Override
	public RemoteAppEntry[] findByUuid_PrevAndNext(
			long remoteAppEntryId, String uuid,
			OrderByComparator<RemoteAppEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		RemoteAppEntry remoteAppEntry = findByPrimaryKey(remoteAppEntryId);

		Session session = null;

		try {
			session = openSession();

			RemoteAppEntry[] array = new RemoteAppEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, remoteAppEntry, uuid, orderByComparator, true);

			array[1] = remoteAppEntry;

			array[2] = getByUuid_PrevAndNext(
				session, remoteAppEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected RemoteAppEntry getByUuid_PrevAndNext(
		Session session, RemoteAppEntry remoteAppEntry, String uuid,
		OrderByComparator<RemoteAppEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_REMOTEAPPENTRY_WHERE);

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
			sb.append(RemoteAppEntryModelImpl.ORDER_BY_JPQL);
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
						remoteAppEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RemoteAppEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the remote app entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (RemoteAppEntry remoteAppEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(remoteAppEntry);
		}
	}

	/**
	 * Returns the number of remote app entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching remote app entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REMOTEAPPENTRY_WHERE);

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
		"remoteAppEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(remoteAppEntry.uuid IS NULL OR remoteAppEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the remote app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching remote app entries
	 */
	@Override
	public List<RemoteAppEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the remote app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @return the range of matching remote app entries
	 */
	@Override
	public List<RemoteAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the remote app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote app entries
	 */
	@Override
	public List<RemoteAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RemoteAppEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the remote app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote app entries
	 */
	@Override
	public List<RemoteAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RemoteAppEntry> orderByComparator,
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

		List<RemoteAppEntry> list = null;

		if (useFinderCache) {
			list = (List<RemoteAppEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RemoteAppEntry remoteAppEntry : list) {
					if (!uuid.equals(remoteAppEntry.getUuid()) ||
						(companyId != remoteAppEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_REMOTEAPPENTRY_WHERE);

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
				sb.append(RemoteAppEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<RemoteAppEntry>)QueryUtil.list(
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
	 * Returns the first remote app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry
	 * @throws NoSuchEntryException if a matching remote app entry could not be found
	 */
	@Override
	public RemoteAppEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<RemoteAppEntry> orderByComparator)
		throws NoSuchEntryException {

		RemoteAppEntry remoteAppEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (remoteAppEntry != null) {
			return remoteAppEntry;
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
	 * Returns the first remote app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	@Override
	public RemoteAppEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<RemoteAppEntry> orderByComparator) {

		List<RemoteAppEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last remote app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry
	 * @throws NoSuchEntryException if a matching remote app entry could not be found
	 */
	@Override
	public RemoteAppEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<RemoteAppEntry> orderByComparator)
		throws NoSuchEntryException {

		RemoteAppEntry remoteAppEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (remoteAppEntry != null) {
			return remoteAppEntry;
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
	 * Returns the last remote app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	@Override
	public RemoteAppEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<RemoteAppEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<RemoteAppEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the remote app entries before and after the current remote app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param remoteAppEntryId the primary key of the current remote app entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote app entry
	 * @throws NoSuchEntryException if a remote app entry with the primary key could not be found
	 */
	@Override
	public RemoteAppEntry[] findByUuid_C_PrevAndNext(
			long remoteAppEntryId, String uuid, long companyId,
			OrderByComparator<RemoteAppEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		RemoteAppEntry remoteAppEntry = findByPrimaryKey(remoteAppEntryId);

		Session session = null;

		try {
			session = openSession();

			RemoteAppEntry[] array = new RemoteAppEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, remoteAppEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = remoteAppEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, remoteAppEntry, uuid, companyId, orderByComparator,
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

	protected RemoteAppEntry getByUuid_C_PrevAndNext(
		Session session, RemoteAppEntry remoteAppEntry, String uuid,
		long companyId, OrderByComparator<RemoteAppEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_REMOTEAPPENTRY_WHERE);

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
			sb.append(RemoteAppEntryModelImpl.ORDER_BY_JPQL);
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
						remoteAppEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RemoteAppEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the remote app entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (RemoteAppEntry remoteAppEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(remoteAppEntry);
		}
	}

	/**
	 * Returns the number of remote app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching remote app entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REMOTEAPPENTRY_WHERE);

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
		"remoteAppEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(remoteAppEntry.uuid IS NULL OR remoteAppEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"remoteAppEntry.companyId = ?";

	private FinderPath _finderPathFetchByC_U;
	private FinderPath _finderPathCountByC_U;

	/**
	 * Returns the remote app entry where companyId = &#63; and url = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote app entry
	 * @throws NoSuchEntryException if a matching remote app entry could not be found
	 */
	@Override
	public RemoteAppEntry findByC_U(long companyId, String url)
		throws NoSuchEntryException {

		RemoteAppEntry remoteAppEntry = fetchByC_U(companyId, url);

		if (remoteAppEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", url=");
			sb.append(url);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return remoteAppEntry;
	}

	/**
	 * Returns the remote app entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	@Override
	public RemoteAppEntry fetchByC_U(long companyId, String url) {
		return fetchByC_U(companyId, url, true);
	}

	/**
	 * Returns the remote app entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	@Override
	public RemoteAppEntry fetchByC_U(
		long companyId, String url, boolean useFinderCache) {

		url = Objects.toString(url, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, url};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_U, finderArgs, this);
		}

		if (result instanceof RemoteAppEntry) {
			RemoteAppEntry remoteAppEntry = (RemoteAppEntry)result;

			if ((companyId != remoteAppEntry.getCompanyId()) ||
				!Objects.equals(url, remoteAppEntry.getUrl())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_REMOTEAPPENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			boolean bindUrl = false;

			if (url.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_U_URL_3);
			}
			else {
				bindUrl = true;

				sb.append(_FINDER_COLUMN_C_U_URL_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindUrl) {
					queryPos.add(url);
				}

				List<RemoteAppEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_U, finderArgs, list);
					}
				}
				else {
					RemoteAppEntry remoteAppEntry = list.get(0);

					result = remoteAppEntry;

					cacheResult(remoteAppEntry);
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
			return (RemoteAppEntry)result;
		}
	}

	/**
	 * Removes the remote app entry where companyId = &#63; and url = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the remote app entry that was removed
	 */
	@Override
	public RemoteAppEntry removeByC_U(long companyId, String url)
		throws NoSuchEntryException {

		RemoteAppEntry remoteAppEntry = findByC_U(companyId, url);

		return remove(remoteAppEntry);
	}

	/**
	 * Returns the number of remote app entries where companyId = &#63; and url = &#63;.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the number of matching remote app entries
	 */
	@Override
	public int countByC_U(long companyId, String url) {
		url = Objects.toString(url, "");

		FinderPath finderPath = _finderPathCountByC_U;

		Object[] finderArgs = new Object[] {companyId, url};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REMOTEAPPENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			boolean bindUrl = false;

			if (url.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_U_URL_3);
			}
			else {
				bindUrl = true;

				sb.append(_FINDER_COLUMN_C_U_URL_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindUrl) {
					queryPos.add(url);
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

	private static final String _FINDER_COLUMN_C_U_COMPANYID_2 =
		"remoteAppEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_U_URL_2 =
		"remoteAppEntry.url = ?";

	private static final String _FINDER_COLUMN_C_U_URL_3 =
		"(remoteAppEntry.url IS NULL OR remoteAppEntry.url = '')";

	public RemoteAppEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(RemoteAppEntry.class);

		setModelImplClass(RemoteAppEntryImpl.class);
		setModelPKClass(long.class);

		setTable(RemoteAppEntryTable.INSTANCE);
	}

	/**
	 * Caches the remote app entry in the entity cache if it is enabled.
	 *
	 * @param remoteAppEntry the remote app entry
	 */
	@Override
	public void cacheResult(RemoteAppEntry remoteAppEntry) {
		entityCache.putResult(
			RemoteAppEntryImpl.class, remoteAppEntry.getPrimaryKey(),
			remoteAppEntry);

		finderCache.putResult(
			_finderPathFetchByC_U,
			new Object[] {
				remoteAppEntry.getCompanyId(), remoteAppEntry.getUrl()
			},
			remoteAppEntry);

		remoteAppEntry.resetOriginalValues();
	}

	/**
	 * Caches the remote app entries in the entity cache if it is enabled.
	 *
	 * @param remoteAppEntries the remote app entries
	 */
	@Override
	public void cacheResult(List<RemoteAppEntry> remoteAppEntries) {
		for (RemoteAppEntry remoteAppEntry : remoteAppEntries) {
			if (entityCache.getResult(
					RemoteAppEntryImpl.class, remoteAppEntry.getPrimaryKey()) ==
						null) {

				cacheResult(remoteAppEntry);
			}
			else {
				remoteAppEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all remote app entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RemoteAppEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the remote app entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RemoteAppEntry remoteAppEntry) {
		entityCache.removeResult(
			RemoteAppEntryImpl.class, remoteAppEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((RemoteAppEntryModelImpl)remoteAppEntry, true);
	}

	@Override
	public void clearCache(List<RemoteAppEntry> remoteAppEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RemoteAppEntry remoteAppEntry : remoteAppEntries) {
			entityCache.removeResult(
				RemoteAppEntryImpl.class, remoteAppEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(RemoteAppEntryModelImpl)remoteAppEntry, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(RemoteAppEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		RemoteAppEntryModelImpl remoteAppEntryModelImpl) {

		Object[] args = new Object[] {
			remoteAppEntryModelImpl.getCompanyId(),
			remoteAppEntryModelImpl.getUrl()
		};

		finderCache.putResult(
			_finderPathCountByC_U, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_U, args, remoteAppEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		RemoteAppEntryModelImpl remoteAppEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				remoteAppEntryModelImpl.getCompanyId(),
				remoteAppEntryModelImpl.getUrl()
			};

			finderCache.removeResult(_finderPathCountByC_U, args);
			finderCache.removeResult(_finderPathFetchByC_U, args);
		}

		if ((remoteAppEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_U.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				remoteAppEntryModelImpl.getOriginalCompanyId(),
				remoteAppEntryModelImpl.getOriginalUrl()
			};

			finderCache.removeResult(_finderPathCountByC_U, args);
			finderCache.removeResult(_finderPathFetchByC_U, args);
		}
	}

	/**
	 * Creates a new remote app entry with the primary key. Does not add the remote app entry to the database.
	 *
	 * @param remoteAppEntryId the primary key for the new remote app entry
	 * @return the new remote app entry
	 */
	@Override
	public RemoteAppEntry create(long remoteAppEntryId) {
		RemoteAppEntry remoteAppEntry = new RemoteAppEntryImpl();

		remoteAppEntry.setNew(true);
		remoteAppEntry.setPrimaryKey(remoteAppEntryId);

		String uuid = PortalUUIDUtil.generate();

		remoteAppEntry.setUuid(uuid);

		remoteAppEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return remoteAppEntry;
	}

	/**
	 * Removes the remote app entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteAppEntryId the primary key of the remote app entry
	 * @return the remote app entry that was removed
	 * @throws NoSuchEntryException if a remote app entry with the primary key could not be found
	 */
	@Override
	public RemoteAppEntry remove(long remoteAppEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)remoteAppEntryId);
	}

	/**
	 * Removes the remote app entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the remote app entry
	 * @return the remote app entry that was removed
	 * @throws NoSuchEntryException if a remote app entry with the primary key could not be found
	 */
	@Override
	public RemoteAppEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			RemoteAppEntry remoteAppEntry = (RemoteAppEntry)session.get(
				RemoteAppEntryImpl.class, primaryKey);

			if (remoteAppEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(remoteAppEntry);
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
	protected RemoteAppEntry removeImpl(RemoteAppEntry remoteAppEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(remoteAppEntry)) {
				remoteAppEntry = (RemoteAppEntry)session.get(
					RemoteAppEntryImpl.class,
					remoteAppEntry.getPrimaryKeyObj());
			}

			if (remoteAppEntry != null) {
				session.delete(remoteAppEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (remoteAppEntry != null) {
			clearCache(remoteAppEntry);
		}

		return remoteAppEntry;
	}

	@Override
	public RemoteAppEntry updateImpl(RemoteAppEntry remoteAppEntry) {
		boolean isNew = remoteAppEntry.isNew();

		if (!(remoteAppEntry instanceof RemoteAppEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(remoteAppEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					remoteAppEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in remoteAppEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RemoteAppEntry implementation " +
					remoteAppEntry.getClass());
		}

		RemoteAppEntryModelImpl remoteAppEntryModelImpl =
			(RemoteAppEntryModelImpl)remoteAppEntry;

		if (Validator.isNull(remoteAppEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			remoteAppEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (remoteAppEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				remoteAppEntry.setCreateDate(now);
			}
			else {
				remoteAppEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!remoteAppEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				remoteAppEntry.setModifiedDate(now);
			}
			else {
				remoteAppEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (remoteAppEntry.isNew()) {
				session.save(remoteAppEntry);

				remoteAppEntry.setNew(false);
			}
			else {
				remoteAppEntry = (RemoteAppEntry)session.merge(remoteAppEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {remoteAppEntryModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				remoteAppEntryModelImpl.getUuid(),
				remoteAppEntryModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((remoteAppEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					remoteAppEntryModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {remoteAppEntryModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((remoteAppEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					remoteAppEntryModelImpl.getOriginalUuid(),
					remoteAppEntryModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					remoteAppEntryModelImpl.getUuid(),
					remoteAppEntryModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}
		}

		entityCache.putResult(
			RemoteAppEntryImpl.class, remoteAppEntry.getPrimaryKey(),
			remoteAppEntry, false);

		clearUniqueFindersCache(remoteAppEntryModelImpl, false);
		cacheUniqueFindersCache(remoteAppEntryModelImpl);

		remoteAppEntry.resetOriginalValues();

		return remoteAppEntry;
	}

	/**
	 * Returns the remote app entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the remote app entry
	 * @return the remote app entry
	 * @throws NoSuchEntryException if a remote app entry with the primary key could not be found
	 */
	@Override
	public RemoteAppEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		RemoteAppEntry remoteAppEntry = fetchByPrimaryKey(primaryKey);

		if (remoteAppEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return remoteAppEntry;
	}

	/**
	 * Returns the remote app entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param remoteAppEntryId the primary key of the remote app entry
	 * @return the remote app entry
	 * @throws NoSuchEntryException if a remote app entry with the primary key could not be found
	 */
	@Override
	public RemoteAppEntry findByPrimaryKey(long remoteAppEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)remoteAppEntryId);
	}

	/**
	 * Returns the remote app entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param remoteAppEntryId the primary key of the remote app entry
	 * @return the remote app entry, or <code>null</code> if a remote app entry with the primary key could not be found
	 */
	@Override
	public RemoteAppEntry fetchByPrimaryKey(long remoteAppEntryId) {
		return fetchByPrimaryKey((Serializable)remoteAppEntryId);
	}

	/**
	 * Returns all the remote app entries.
	 *
	 * @return the remote app entries
	 */
	@Override
	public List<RemoteAppEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the remote app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @return the range of remote app entries
	 */
	@Override
	public List<RemoteAppEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the remote app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of remote app entries
	 */
	@Override
	public List<RemoteAppEntry> findAll(
		int start, int end,
		OrderByComparator<RemoteAppEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the remote app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of remote app entries
	 */
	@Override
	public List<RemoteAppEntry> findAll(
		int start, int end, OrderByComparator<RemoteAppEntry> orderByComparator,
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

		List<RemoteAppEntry> list = null;

		if (useFinderCache) {
			list = (List<RemoteAppEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_REMOTEAPPENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_REMOTEAPPENTRY;

				sql = sql.concat(RemoteAppEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RemoteAppEntry>)QueryUtil.list(
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
	 * Removes all the remote app entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RemoteAppEntry remoteAppEntry : findAll()) {
			remove(remoteAppEntry);
		}
	}

	/**
	 * Returns the number of remote app entries.
	 *
	 * @return the number of remote app entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_REMOTEAPPENTRY);

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
		return "remoteAppEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REMOTEAPPENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RemoteAppEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the remote app entry persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			RemoteAppEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			RemoteAppEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			RemoteAppEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			RemoteAppEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			RemoteAppEntryModelImpl.UUID_COLUMN_BITMASK |
			RemoteAppEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			RemoteAppEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			RemoteAppEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			RemoteAppEntryModelImpl.UUID_COLUMN_BITMASK |
			RemoteAppEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			RemoteAppEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathFetchByC_U = new FinderPath(
			RemoteAppEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_U",
			new String[] {Long.class.getName(), String.class.getName()},
			RemoteAppEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			RemoteAppEntryModelImpl.URL_COLUMN_BITMASK);

		_finderPathCountByC_U = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(RemoteAppEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = RemoteAppPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = RemoteAppPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = RemoteAppPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_REMOTEAPPENTRY =
		"SELECT remoteAppEntry FROM RemoteAppEntry remoteAppEntry";

	private static final String _SQL_SELECT_REMOTEAPPENTRY_WHERE =
		"SELECT remoteAppEntry FROM RemoteAppEntry remoteAppEntry WHERE ";

	private static final String _SQL_COUNT_REMOTEAPPENTRY =
		"SELECT COUNT(remoteAppEntry) FROM RemoteAppEntry remoteAppEntry";

	private static final String _SQL_COUNT_REMOTEAPPENTRY_WHERE =
		"SELECT COUNT(remoteAppEntry) FROM RemoteAppEntry remoteAppEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "remoteAppEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RemoteAppEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RemoteAppEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteAppEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(RemoteAppPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}