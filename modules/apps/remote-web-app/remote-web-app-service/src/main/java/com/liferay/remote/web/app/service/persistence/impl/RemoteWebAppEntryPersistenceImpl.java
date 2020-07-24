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

package com.liferay.remote.web.app.service.persistence.impl;

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
import com.liferay.remote.web.app.exception.NoSuchEntryException;
import com.liferay.remote.web.app.model.RemoteWebAppEntry;
import com.liferay.remote.web.app.model.RemoteWebAppEntryTable;
import com.liferay.remote.web.app.model.impl.RemoteWebAppEntryImpl;
import com.liferay.remote.web.app.model.impl.RemoteWebAppEntryModelImpl;
import com.liferay.remote.web.app.service.persistence.RemoteWebAppEntryPersistence;
import com.liferay.remote.web.app.service.persistence.impl.constants.RemoteWebAppPersistenceConstants;

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
 * The persistence implementation for the remote web app entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = RemoteWebAppEntryPersistence.class)
public class RemoteWebAppEntryPersistenceImpl
	extends BasePersistenceImpl<RemoteWebAppEntry>
	implements RemoteWebAppEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RemoteWebAppEntryUtil</code> to access the remote web app entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RemoteWebAppEntryImpl.class.getName();

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
	 * Returns all the remote web app entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching remote web app entries
	 */
	@Override
	public List<RemoteWebAppEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the remote web app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @return the range of matching remote web app entries
	 */
	@Override
	public List<RemoteWebAppEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the remote web app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote web app entries
	 */
	@Override
	public List<RemoteWebAppEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RemoteWebAppEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the remote web app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote web app entries
	 */
	@Override
	public List<RemoteWebAppEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RemoteWebAppEntry> orderByComparator,
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

		List<RemoteWebAppEntry> list = null;

		if (useFinderCache) {
			list = (List<RemoteWebAppEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RemoteWebAppEntry remoteWebAppEntry : list) {
					if (!uuid.equals(remoteWebAppEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_REMOTEWEBAPPENTRY_WHERE);

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
				sb.append(RemoteWebAppEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<RemoteWebAppEntry>)QueryUtil.list(
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
	 * Returns the first remote web app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web app entry
	 * @throws NoSuchEntryException if a matching remote web app entry could not be found
	 */
	@Override
	public RemoteWebAppEntry findByUuid_First(
			String uuid, OrderByComparator<RemoteWebAppEntry> orderByComparator)
		throws NoSuchEntryException {

		RemoteWebAppEntry remoteWebAppEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (remoteWebAppEntry != null) {
			return remoteWebAppEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first remote web app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	@Override
	public RemoteWebAppEntry fetchByUuid_First(
		String uuid, OrderByComparator<RemoteWebAppEntry> orderByComparator) {

		List<RemoteWebAppEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last remote web app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web app entry
	 * @throws NoSuchEntryException if a matching remote web app entry could not be found
	 */
	@Override
	public RemoteWebAppEntry findByUuid_Last(
			String uuid, OrderByComparator<RemoteWebAppEntry> orderByComparator)
		throws NoSuchEntryException {

		RemoteWebAppEntry remoteWebAppEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (remoteWebAppEntry != null) {
			return remoteWebAppEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last remote web app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	@Override
	public RemoteWebAppEntry fetchByUuid_Last(
		String uuid, OrderByComparator<RemoteWebAppEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<RemoteWebAppEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the remote web app entries before and after the current remote web app entry in the ordered set where uuid = &#63;.
	 *
	 * @param entryId the primary key of the current remote web app entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote web app entry
	 * @throws NoSuchEntryException if a remote web app entry with the primary key could not be found
	 */
	@Override
	public RemoteWebAppEntry[] findByUuid_PrevAndNext(
			long entryId, String uuid,
			OrderByComparator<RemoteWebAppEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		RemoteWebAppEntry remoteWebAppEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			RemoteWebAppEntry[] array = new RemoteWebAppEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, remoteWebAppEntry, uuid, orderByComparator, true);

			array[1] = remoteWebAppEntry;

			array[2] = getByUuid_PrevAndNext(
				session, remoteWebAppEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected RemoteWebAppEntry getByUuid_PrevAndNext(
		Session session, RemoteWebAppEntry remoteWebAppEntry, String uuid,
		OrderByComparator<RemoteWebAppEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_REMOTEWEBAPPENTRY_WHERE);

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
			sb.append(RemoteWebAppEntryModelImpl.ORDER_BY_JPQL);
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
						remoteWebAppEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RemoteWebAppEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the remote web app entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (RemoteWebAppEntry remoteWebAppEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(remoteWebAppEntry);
		}
	}

	/**
	 * Returns the number of remote web app entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching remote web app entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REMOTEWEBAPPENTRY_WHERE);

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
		"remoteWebAppEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(remoteWebAppEntry.uuid IS NULL OR remoteWebAppEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the remote web app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching remote web app entries
	 */
	@Override
	public List<RemoteWebAppEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the remote web app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @return the range of matching remote web app entries
	 */
	@Override
	public List<RemoteWebAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the remote web app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote web app entries
	 */
	@Override
	public List<RemoteWebAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RemoteWebAppEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the remote web app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote web app entries
	 */
	@Override
	public List<RemoteWebAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RemoteWebAppEntry> orderByComparator,
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

		List<RemoteWebAppEntry> list = null;

		if (useFinderCache) {
			list = (List<RemoteWebAppEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RemoteWebAppEntry remoteWebAppEntry : list) {
					if (!uuid.equals(remoteWebAppEntry.getUuid()) ||
						(companyId != remoteWebAppEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_REMOTEWEBAPPENTRY_WHERE);

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
				sb.append(RemoteWebAppEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<RemoteWebAppEntry>)QueryUtil.list(
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
	 * Returns the first remote web app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web app entry
	 * @throws NoSuchEntryException if a matching remote web app entry could not be found
	 */
	@Override
	public RemoteWebAppEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<RemoteWebAppEntry> orderByComparator)
		throws NoSuchEntryException {

		RemoteWebAppEntry remoteWebAppEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (remoteWebAppEntry != null) {
			return remoteWebAppEntry;
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
	 * Returns the first remote web app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	@Override
	public RemoteWebAppEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<RemoteWebAppEntry> orderByComparator) {

		List<RemoteWebAppEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last remote web app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web app entry
	 * @throws NoSuchEntryException if a matching remote web app entry could not be found
	 */
	@Override
	public RemoteWebAppEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<RemoteWebAppEntry> orderByComparator)
		throws NoSuchEntryException {

		RemoteWebAppEntry remoteWebAppEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (remoteWebAppEntry != null) {
			return remoteWebAppEntry;
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
	 * Returns the last remote web app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	@Override
	public RemoteWebAppEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<RemoteWebAppEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<RemoteWebAppEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the remote web app entries before and after the current remote web app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param entryId the primary key of the current remote web app entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote web app entry
	 * @throws NoSuchEntryException if a remote web app entry with the primary key could not be found
	 */
	@Override
	public RemoteWebAppEntry[] findByUuid_C_PrevAndNext(
			long entryId, String uuid, long companyId,
			OrderByComparator<RemoteWebAppEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		RemoteWebAppEntry remoteWebAppEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			RemoteWebAppEntry[] array = new RemoteWebAppEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, remoteWebAppEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = remoteWebAppEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, remoteWebAppEntry, uuid, companyId, orderByComparator,
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

	protected RemoteWebAppEntry getByUuid_C_PrevAndNext(
		Session session, RemoteWebAppEntry remoteWebAppEntry, String uuid,
		long companyId, OrderByComparator<RemoteWebAppEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_REMOTEWEBAPPENTRY_WHERE);

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
			sb.append(RemoteWebAppEntryModelImpl.ORDER_BY_JPQL);
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
						remoteWebAppEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RemoteWebAppEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the remote web app entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (RemoteWebAppEntry remoteWebAppEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(remoteWebAppEntry);
		}
	}

	/**
	 * Returns the number of remote web app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching remote web app entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REMOTEWEBAPPENTRY_WHERE);

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
		"remoteWebAppEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(remoteWebAppEntry.uuid IS NULL OR remoteWebAppEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"remoteWebAppEntry.companyId = ?";

	private FinderPath _finderPathFetchByC_U;
	private FinderPath _finderPathCountByC_U;

	/**
	 * Returns the remote web app entry where companyId = &#63; and url = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote web app entry
	 * @throws NoSuchEntryException if a matching remote web app entry could not be found
	 */
	@Override
	public RemoteWebAppEntry findByC_U(long companyId, String url)
		throws NoSuchEntryException {

		RemoteWebAppEntry remoteWebAppEntry = fetchByC_U(companyId, url);

		if (remoteWebAppEntry == null) {
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

		return remoteWebAppEntry;
	}

	/**
	 * Returns the remote web app entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	@Override
	public RemoteWebAppEntry fetchByC_U(long companyId, String url) {
		return fetchByC_U(companyId, url, true);
	}

	/**
	 * Returns the remote web app entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	@Override
	public RemoteWebAppEntry fetchByC_U(
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

		if (result instanceof RemoteWebAppEntry) {
			RemoteWebAppEntry remoteWebAppEntry = (RemoteWebAppEntry)result;

			if ((companyId != remoteWebAppEntry.getCompanyId()) ||
				!Objects.equals(url, remoteWebAppEntry.getUrl())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_REMOTEWEBAPPENTRY_WHERE);

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

				List<RemoteWebAppEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_U, finderArgs, list);
					}
				}
				else {
					RemoteWebAppEntry remoteWebAppEntry = list.get(0);

					result = remoteWebAppEntry;

					cacheResult(remoteWebAppEntry);
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
			return (RemoteWebAppEntry)result;
		}
	}

	/**
	 * Removes the remote web app entry where companyId = &#63; and url = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the remote web app entry that was removed
	 */
	@Override
	public RemoteWebAppEntry removeByC_U(long companyId, String url)
		throws NoSuchEntryException {

		RemoteWebAppEntry remoteWebAppEntry = findByC_U(companyId, url);

		return remove(remoteWebAppEntry);
	}

	/**
	 * Returns the number of remote web app entries where companyId = &#63; and url = &#63;.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the number of matching remote web app entries
	 */
	@Override
	public int countByC_U(long companyId, String url) {
		url = Objects.toString(url, "");

		FinderPath finderPath = _finderPathCountByC_U;

		Object[] finderArgs = new Object[] {companyId, url};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REMOTEWEBAPPENTRY_WHERE);

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
		"remoteWebAppEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_U_URL_2 =
		"remoteWebAppEntry.url = ?";

	private static final String _FINDER_COLUMN_C_U_URL_3 =
		"(remoteWebAppEntry.url IS NULL OR remoteWebAppEntry.url = '')";

	public RemoteWebAppEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(RemoteWebAppEntry.class);

		setModelImplClass(RemoteWebAppEntryImpl.class);
		setModelPKClass(long.class);

		setTable(RemoteWebAppEntryTable.INSTANCE);
	}

	/**
	 * Caches the remote web app entry in the entity cache if it is enabled.
	 *
	 * @param remoteWebAppEntry the remote web app entry
	 */
	@Override
	public void cacheResult(RemoteWebAppEntry remoteWebAppEntry) {
		entityCache.putResult(
			RemoteWebAppEntryImpl.class, remoteWebAppEntry.getPrimaryKey(),
			remoteWebAppEntry);

		finderCache.putResult(
			_finderPathFetchByC_U,
			new Object[] {
				remoteWebAppEntry.getCompanyId(), remoteWebAppEntry.getUrl()
			},
			remoteWebAppEntry);

		remoteWebAppEntry.resetOriginalValues();
	}

	/**
	 * Caches the remote web app entries in the entity cache if it is enabled.
	 *
	 * @param remoteWebAppEntries the remote web app entries
	 */
	@Override
	public void cacheResult(List<RemoteWebAppEntry> remoteWebAppEntries) {
		for (RemoteWebAppEntry remoteWebAppEntry : remoteWebAppEntries) {
			if (entityCache.getResult(
					RemoteWebAppEntryImpl.class,
					remoteWebAppEntry.getPrimaryKey()) == null) {

				cacheResult(remoteWebAppEntry);
			}
			else {
				remoteWebAppEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all remote web app entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RemoteWebAppEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the remote web app entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RemoteWebAppEntry remoteWebAppEntry) {
		entityCache.removeResult(
			RemoteWebAppEntryImpl.class, remoteWebAppEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(RemoteWebAppEntryModelImpl)remoteWebAppEntry, true);
	}

	@Override
	public void clearCache(List<RemoteWebAppEntry> remoteWebAppEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RemoteWebAppEntry remoteWebAppEntry : remoteWebAppEntries) {
			entityCache.removeResult(
				RemoteWebAppEntryImpl.class, remoteWebAppEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(RemoteWebAppEntryModelImpl)remoteWebAppEntry, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(RemoteWebAppEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		RemoteWebAppEntryModelImpl remoteWebAppEntryModelImpl) {

		Object[] args = new Object[] {
			remoteWebAppEntryModelImpl.getCompanyId(),
			remoteWebAppEntryModelImpl.getUrl()
		};

		finderCache.putResult(
			_finderPathCountByC_U, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_U, args, remoteWebAppEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		RemoteWebAppEntryModelImpl remoteWebAppEntryModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				remoteWebAppEntryModelImpl.getCompanyId(),
				remoteWebAppEntryModelImpl.getUrl()
			};

			finderCache.removeResult(_finderPathCountByC_U, args);
			finderCache.removeResult(_finderPathFetchByC_U, args);
		}

		if ((remoteWebAppEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_U.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				remoteWebAppEntryModelImpl.getOriginalCompanyId(),
				remoteWebAppEntryModelImpl.getOriginalUrl()
			};

			finderCache.removeResult(_finderPathCountByC_U, args);
			finderCache.removeResult(_finderPathFetchByC_U, args);
		}
	}

	/**
	 * Creates a new remote web app entry with the primary key. Does not add the remote web app entry to the database.
	 *
	 * @param entryId the primary key for the new remote web app entry
	 * @return the new remote web app entry
	 */
	@Override
	public RemoteWebAppEntry create(long entryId) {
		RemoteWebAppEntry remoteWebAppEntry = new RemoteWebAppEntryImpl();

		remoteWebAppEntry.setNew(true);
		remoteWebAppEntry.setPrimaryKey(entryId);

		String uuid = PortalUUIDUtil.generate();

		remoteWebAppEntry.setUuid(uuid);

		remoteWebAppEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return remoteWebAppEntry;
	}

	/**
	 * Removes the remote web app entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the remote web app entry
	 * @return the remote web app entry that was removed
	 * @throws NoSuchEntryException if a remote web app entry with the primary key could not be found
	 */
	@Override
	public RemoteWebAppEntry remove(long entryId) throws NoSuchEntryException {
		return remove((Serializable)entryId);
	}

	/**
	 * Removes the remote web app entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the remote web app entry
	 * @return the remote web app entry that was removed
	 * @throws NoSuchEntryException if a remote web app entry with the primary key could not be found
	 */
	@Override
	public RemoteWebAppEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			RemoteWebAppEntry remoteWebAppEntry =
				(RemoteWebAppEntry)session.get(
					RemoteWebAppEntryImpl.class, primaryKey);

			if (remoteWebAppEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(remoteWebAppEntry);
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
	protected RemoteWebAppEntry removeImpl(
		RemoteWebAppEntry remoteWebAppEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(remoteWebAppEntry)) {
				remoteWebAppEntry = (RemoteWebAppEntry)session.get(
					RemoteWebAppEntryImpl.class,
					remoteWebAppEntry.getPrimaryKeyObj());
			}

			if (remoteWebAppEntry != null) {
				session.delete(remoteWebAppEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (remoteWebAppEntry != null) {
			clearCache(remoteWebAppEntry);
		}

		return remoteWebAppEntry;
	}

	@Override
	public RemoteWebAppEntry updateImpl(RemoteWebAppEntry remoteWebAppEntry) {
		boolean isNew = remoteWebAppEntry.isNew();

		if (!(remoteWebAppEntry instanceof RemoteWebAppEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(remoteWebAppEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					remoteWebAppEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in remoteWebAppEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RemoteWebAppEntry implementation " +
					remoteWebAppEntry.getClass());
		}

		RemoteWebAppEntryModelImpl remoteWebAppEntryModelImpl =
			(RemoteWebAppEntryModelImpl)remoteWebAppEntry;

		if (Validator.isNull(remoteWebAppEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			remoteWebAppEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (remoteWebAppEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				remoteWebAppEntry.setCreateDate(now);
			}
			else {
				remoteWebAppEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!remoteWebAppEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				remoteWebAppEntry.setModifiedDate(now);
			}
			else {
				remoteWebAppEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (remoteWebAppEntry.isNew()) {
				session.save(remoteWebAppEntry);

				remoteWebAppEntry.setNew(false);
			}
			else {
				remoteWebAppEntry = (RemoteWebAppEntry)session.merge(
					remoteWebAppEntry);
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
			Object[] args = new Object[] {remoteWebAppEntryModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				remoteWebAppEntryModelImpl.getUuid(),
				remoteWebAppEntryModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((remoteWebAppEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					remoteWebAppEntryModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {remoteWebAppEntryModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((remoteWebAppEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					remoteWebAppEntryModelImpl.getOriginalUuid(),
					remoteWebAppEntryModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					remoteWebAppEntryModelImpl.getUuid(),
					remoteWebAppEntryModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}
		}

		entityCache.putResult(
			RemoteWebAppEntryImpl.class, remoteWebAppEntry.getPrimaryKey(),
			remoteWebAppEntry, false);

		clearUniqueFindersCache(remoteWebAppEntryModelImpl, false);
		cacheUniqueFindersCache(remoteWebAppEntryModelImpl);

		remoteWebAppEntry.resetOriginalValues();

		return remoteWebAppEntry;
	}

	/**
	 * Returns the remote web app entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the remote web app entry
	 * @return the remote web app entry
	 * @throws NoSuchEntryException if a remote web app entry with the primary key could not be found
	 */
	@Override
	public RemoteWebAppEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		RemoteWebAppEntry remoteWebAppEntry = fetchByPrimaryKey(primaryKey);

		if (remoteWebAppEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return remoteWebAppEntry;
	}

	/**
	 * Returns the remote web app entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the remote web app entry
	 * @return the remote web app entry
	 * @throws NoSuchEntryException if a remote web app entry with the primary key could not be found
	 */
	@Override
	public RemoteWebAppEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns the remote web app entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the remote web app entry
	 * @return the remote web app entry, or <code>null</code> if a remote web app entry with the primary key could not be found
	 */
	@Override
	public RemoteWebAppEntry fetchByPrimaryKey(long entryId) {
		return fetchByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns all the remote web app entries.
	 *
	 * @return the remote web app entries
	 */
	@Override
	public List<RemoteWebAppEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the remote web app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @return the range of remote web app entries
	 */
	@Override
	public List<RemoteWebAppEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the remote web app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of remote web app entries
	 */
	@Override
	public List<RemoteWebAppEntry> findAll(
		int start, int end,
		OrderByComparator<RemoteWebAppEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the remote web app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of remote web app entries
	 */
	@Override
	public List<RemoteWebAppEntry> findAll(
		int start, int end,
		OrderByComparator<RemoteWebAppEntry> orderByComparator,
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

		List<RemoteWebAppEntry> list = null;

		if (useFinderCache) {
			list = (List<RemoteWebAppEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_REMOTEWEBAPPENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_REMOTEWEBAPPENTRY;

				sql = sql.concat(RemoteWebAppEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RemoteWebAppEntry>)QueryUtil.list(
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
	 * Removes all the remote web app entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RemoteWebAppEntry remoteWebAppEntry : findAll()) {
			remove(remoteWebAppEntry);
		}
	}

	/**
	 * Returns the number of remote web app entries.
	 *
	 * @return the number of remote web app entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_REMOTEWEBAPPENTRY);

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
		return "entryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REMOTEWEBAPPENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RemoteWebAppEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the remote web app entry persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			RemoteWebAppEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			RemoteWebAppEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			RemoteWebAppEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			RemoteWebAppEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			RemoteWebAppEntryModelImpl.UUID_COLUMN_BITMASK |
			RemoteWebAppEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			RemoteWebAppEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			RemoteWebAppEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			RemoteWebAppEntryModelImpl.UUID_COLUMN_BITMASK |
			RemoteWebAppEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			RemoteWebAppEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathFetchByC_U = new FinderPath(
			RemoteWebAppEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_U",
			new String[] {Long.class.getName(), String.class.getName()},
			RemoteWebAppEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			RemoteWebAppEntryModelImpl.URL_COLUMN_BITMASK);

		_finderPathCountByC_U = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(RemoteWebAppEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = RemoteWebAppPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = RemoteWebAppPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = RemoteWebAppPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_REMOTEWEBAPPENTRY =
		"SELECT remoteWebAppEntry FROM RemoteWebAppEntry remoteWebAppEntry";

	private static final String _SQL_SELECT_REMOTEWEBAPPENTRY_WHERE =
		"SELECT remoteWebAppEntry FROM RemoteWebAppEntry remoteWebAppEntry WHERE ";

	private static final String _SQL_COUNT_REMOTEWEBAPPENTRY =
		"SELECT COUNT(remoteWebAppEntry) FROM RemoteWebAppEntry remoteWebAppEntry";

	private static final String _SQL_COUNT_REMOTEWEBAPPENTRY_WHERE =
		"SELECT COUNT(remoteWebAppEntry) FROM RemoteWebAppEntry remoteWebAppEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "remoteWebAppEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RemoteWebAppEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RemoteWebAppEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteWebAppEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(RemoteWebAppPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}