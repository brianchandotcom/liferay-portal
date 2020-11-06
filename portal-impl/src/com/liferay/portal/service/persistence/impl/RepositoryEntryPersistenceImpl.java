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

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.model.RepositoryEntryTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.RepositoryEntryPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.impl.RepositoryEntryImpl;
import com.liferay.portal.model.impl.RepositoryEntryModelImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The persistence implementation for the repository entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RepositoryEntryPersistenceImpl
	extends BasePersistenceImpl<RepositoryEntry>
	implements RepositoryEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RepositoryEntryUtil</code> to access the repository entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RepositoryEntryImpl.class.getName();

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
	 * Returns all the repository entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching repository entries
	 */
	@Override
	public List<RepositoryEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the repository entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @return the range of matching repository entries
	 */
	@Override
	public List<RepositoryEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the repository entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching repository entries
	 */
	@Override
	public List<RepositoryEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RepositoryEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the repository entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching repository entries
	 */
	@Override
	public List<RepositoryEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RepositoryEntry> orderByComparator,
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

		List<RepositoryEntry> list = null;

		if (useFinderCache) {
			list = (List<RepositoryEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (RepositoryEntry repositoryEntry : list) {
					if (!uuid.equals(repositoryEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

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
				sb.append(RepositoryEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<RepositoryEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository entry
	 * @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry findByUuid_First(
			String uuid, OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException {

		RepositoryEntry repositoryEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (repositoryEntry != null) {
			return repositoryEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchRepositoryEntryException(sb.toString());
	}

	/**
	 * Returns the first repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry fetchByUuid_First(
		String uuid, OrderByComparator<RepositoryEntry> orderByComparator) {

		List<RepositoryEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository entry
	 * @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry findByUuid_Last(
			String uuid, OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException {

		RepositoryEntry repositoryEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (repositoryEntry != null) {
			return repositoryEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchRepositoryEntryException(sb.toString());
	}

	/**
	 * Returns the last repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry fetchByUuid_Last(
		String uuid, OrderByComparator<RepositoryEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<RepositoryEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the repository entries before and after the current repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param repositoryEntryId the primary key of the current repository entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next repository entry
	 * @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	 */
	@Override
	public RepositoryEntry[] findByUuid_PrevAndNext(
			long repositoryEntryId, String uuid,
			OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException {

		uuid = Objects.toString(uuid, "");

		RepositoryEntry repositoryEntry = findByPrimaryKey(repositoryEntryId);

		Session session = null;

		try {
			session = openSession();

			RepositoryEntry[] array = new RepositoryEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, repositoryEntry, uuid, orderByComparator, true);

			array[1] = repositoryEntry;

			array[2] = getByUuid_PrevAndNext(
				session, repositoryEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected RepositoryEntry getByUuid_PrevAndNext(
		Session session, RepositoryEntry repositoryEntry, String uuid,
		OrderByComparator<RepositoryEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

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
			sb.append(RepositoryEntryModelImpl.ORDER_BY_JPQL);
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
						repositoryEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RepositoryEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the repository entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (RepositoryEntry repositoryEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(repositoryEntry);
		}
	}

	/**
	 * Returns the number of repository entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching repository entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REPOSITORYENTRY_WHERE);

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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		"repositoryEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(repositoryEntry.uuid IS NULL OR repositoryEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the repository entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchRepositoryEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching repository entry
	 * @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchRepositoryEntryException {

		RepositoryEntry repositoryEntry = fetchByUUID_G(uuid, groupId);

		if (repositoryEntry == null) {
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

			throw new NoSuchRepositoryEntryException(sb.toString());
		}

		return repositoryEntry;
	}

	/**
	 * Returns the repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof RepositoryEntry) {
			RepositoryEntry repositoryEntry = (RepositoryEntry)result;

			if (!Objects.equals(uuid, repositoryEntry.getUuid()) ||
				(groupId != repositoryEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

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

				List<RepositoryEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					RepositoryEntry repositoryEntry = list.get(0);

					result = repositoryEntry;

					cacheResult(repositoryEntry);
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
			return (RepositoryEntry)result;
		}
	}

	/**
	 * Removes the repository entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the repository entry that was removed
	 */
	@Override
	public RepositoryEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchRepositoryEntryException {

		RepositoryEntry repositoryEntry = findByUUID_G(uuid, groupId);

		return remove(repositoryEntry);
	}

	/**
	 * Returns the number of repository entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching repository entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REPOSITORYENTRY_WHERE);

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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		"repositoryEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(repositoryEntry.uuid IS NULL OR repositoryEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"repositoryEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching repository entries
	 */
	@Override
	public List<RepositoryEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @return the range of matching repository entries
	 */
	@Override
	public List<RepositoryEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching repository entries
	 */
	@Override
	public List<RepositoryEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RepositoryEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching repository entries
	 */
	@Override
	public List<RepositoryEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RepositoryEntry> orderByComparator,
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

		List<RepositoryEntry> list = null;

		if (useFinderCache) {
			list = (List<RepositoryEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (RepositoryEntry repositoryEntry : list) {
					if (!uuid.equals(repositoryEntry.getUuid()) ||
						(companyId != repositoryEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

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
				sb.append(RepositoryEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<RepositoryEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository entry
	 * @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException {

		RepositoryEntry repositoryEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (repositoryEntry != null) {
			return repositoryEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchRepositoryEntryException(sb.toString());
	}

	/**
	 * Returns the first repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<RepositoryEntry> orderByComparator) {

		List<RepositoryEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository entry
	 * @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException {

		RepositoryEntry repositoryEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (repositoryEntry != null) {
			return repositoryEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchRepositoryEntryException(sb.toString());
	}

	/**
	 * Returns the last repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<RepositoryEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<RepositoryEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the repository entries before and after the current repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param repositoryEntryId the primary key of the current repository entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next repository entry
	 * @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	 */
	@Override
	public RepositoryEntry[] findByUuid_C_PrevAndNext(
			long repositoryEntryId, String uuid, long companyId,
			OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException {

		uuid = Objects.toString(uuid, "");

		RepositoryEntry repositoryEntry = findByPrimaryKey(repositoryEntryId);

		Session session = null;

		try {
			session = openSession();

			RepositoryEntry[] array = new RepositoryEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, repositoryEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = repositoryEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, repositoryEntry, uuid, companyId, orderByComparator,
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

	protected RepositoryEntry getByUuid_C_PrevAndNext(
		Session session, RepositoryEntry repositoryEntry, String uuid,
		long companyId, OrderByComparator<RepositoryEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

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
			sb.append(RepositoryEntryModelImpl.ORDER_BY_JPQL);
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
						repositoryEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RepositoryEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the repository entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (RepositoryEntry repositoryEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(repositoryEntry);
		}
	}

	/**
	 * Returns the number of repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching repository entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REPOSITORYENTRY_WHERE);

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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		"repositoryEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(repositoryEntry.uuid IS NULL OR repositoryEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"repositoryEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByRepositoryId;
	private FinderPath _finderPathWithoutPaginationFindByRepositoryId;
	private FinderPath _finderPathCountByRepositoryId;

	/**
	 * Returns all the repository entries where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @return the matching repository entries
	 */
	@Override
	public List<RepositoryEntry> findByRepositoryId(long repositoryId) {
		return findByRepositoryId(
			repositoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the repository entries where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @return the range of matching repository entries
	 */
	@Override
	public List<RepositoryEntry> findByRepositoryId(
		long repositoryId, int start, int end) {

		return findByRepositoryId(repositoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the repository entries where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching repository entries
	 */
	@Override
	public List<RepositoryEntry> findByRepositoryId(
		long repositoryId, int start, int end,
		OrderByComparator<RepositoryEntry> orderByComparator) {

		return findByRepositoryId(
			repositoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the repository entries where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching repository entries
	 */
	@Override
	public List<RepositoryEntry> findByRepositoryId(
		long repositoryId, int start, int end,
		OrderByComparator<RepositoryEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByRepositoryId;
				finderArgs = new Object[] {repositoryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByRepositoryId;
			finderArgs = new Object[] {
				repositoryId, start, end, orderByComparator
			};
		}

		List<RepositoryEntry> list = null;

		if (useFinderCache) {
			list = (List<RepositoryEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (RepositoryEntry repositoryEntry : list) {
					if (repositoryId != repositoryEntry.getRepositoryId()) {
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

			sb.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

			sb.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RepositoryEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

				list = (List<RepositoryEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first repository entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository entry
	 * @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry findByRepositoryId_First(
			long repositoryId,
			OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException {

		RepositoryEntry repositoryEntry = fetchByRepositoryId_First(
			repositoryId, orderByComparator);

		if (repositoryEntry != null) {
			return repositoryEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("repositoryId=");
		sb.append(repositoryId);

		sb.append("}");

		throw new NoSuchRepositoryEntryException(sb.toString());
	}

	/**
	 * Returns the first repository entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry fetchByRepositoryId_First(
		long repositoryId,
		OrderByComparator<RepositoryEntry> orderByComparator) {

		List<RepositoryEntry> list = findByRepositoryId(
			repositoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last repository entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository entry
	 * @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry findByRepositoryId_Last(
			long repositoryId,
			OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException {

		RepositoryEntry repositoryEntry = fetchByRepositoryId_Last(
			repositoryId, orderByComparator);

		if (repositoryEntry != null) {
			return repositoryEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("repositoryId=");
		sb.append(repositoryId);

		sb.append("}");

		throw new NoSuchRepositoryEntryException(sb.toString());
	}

	/**
	 * Returns the last repository entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry fetchByRepositoryId_Last(
		long repositoryId,
		OrderByComparator<RepositoryEntry> orderByComparator) {

		int count = countByRepositoryId(repositoryId);

		if (count == 0) {
			return null;
		}

		List<RepositoryEntry> list = findByRepositoryId(
			repositoryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the repository entries before and after the current repository entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryEntryId the primary key of the current repository entry
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next repository entry
	 * @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	 */
	@Override
	public RepositoryEntry[] findByRepositoryId_PrevAndNext(
			long repositoryEntryId, long repositoryId,
			OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException {

		RepositoryEntry repositoryEntry = findByPrimaryKey(repositoryEntryId);

		Session session = null;

		try {
			session = openSession();

			RepositoryEntry[] array = new RepositoryEntryImpl[3];

			array[0] = getByRepositoryId_PrevAndNext(
				session, repositoryEntry, repositoryId, orderByComparator,
				true);

			array[1] = repositoryEntry;

			array[2] = getByRepositoryId_PrevAndNext(
				session, repositoryEntry, repositoryId, orderByComparator,
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

	protected RepositoryEntry getByRepositoryId_PrevAndNext(
		Session session, RepositoryEntry repositoryEntry, long repositoryId,
		OrderByComparator<RepositoryEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

		sb.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

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
			sb.append(RepositoryEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(repositoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						repositoryEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RepositoryEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the repository entries where repositoryId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 */
	@Override
	public void removeByRepositoryId(long repositoryId) {
		for (RepositoryEntry repositoryEntry :
				findByRepositoryId(
					repositoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(repositoryEntry);
		}
	}

	/**
	 * Returns the number of repository entries where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @return the number of matching repository entries
	 */
	@Override
	public int countByRepositoryId(long repositoryId) {
		FinderPath finderPath = _finderPathCountByRepositoryId;

		Object[] finderArgs = new Object[] {repositoryId};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REPOSITORYENTRY_WHERE);

			sb.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2 =
		"repositoryEntry.repositoryId = ?";

	private FinderPath _finderPathFetchByR_M;
	private FinderPath _finderPathCountByR_M;

	/**
	 * Returns the repository entry where repositoryId = &#63; and mappedId = &#63; or throws a <code>NoSuchRepositoryEntryException</code> if it could not be found.
	 *
	 * @param repositoryId the repository ID
	 * @param mappedId the mapped ID
	 * @return the matching repository entry
	 * @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry findByR_M(long repositoryId, String mappedId)
		throws NoSuchRepositoryEntryException {

		RepositoryEntry repositoryEntry = fetchByR_M(repositoryId, mappedId);

		if (repositoryEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("repositoryId=");
			sb.append(repositoryId);

			sb.append(", mappedId=");
			sb.append(mappedId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchRepositoryEntryException(sb.toString());
		}

		return repositoryEntry;
	}

	/**
	 * Returns the repository entry where repositoryId = &#63; and mappedId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param repositoryId the repository ID
	 * @param mappedId the mapped ID
	 * @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry fetchByR_M(long repositoryId, String mappedId) {
		return fetchByR_M(repositoryId, mappedId, true);
	}

	/**
	 * Returns the repository entry where repositoryId = &#63; and mappedId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param repositoryId the repository ID
	 * @param mappedId the mapped ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 */
	@Override
	public RepositoryEntry fetchByR_M(
		long repositoryId, String mappedId, boolean useFinderCache) {

		mappedId = Objects.toString(mappedId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {repositoryId, mappedId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByR_M, finderArgs);
		}

		if (result instanceof RepositoryEntry) {
			RepositoryEntry repositoryEntry = (RepositoryEntry)result;

			if ((repositoryId != repositoryEntry.getRepositoryId()) ||
				!Objects.equals(mappedId, repositoryEntry.getMappedId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

			sb.append(_FINDER_COLUMN_R_M_REPOSITORYID_2);

			boolean bindMappedId = false;

			if (mappedId.isEmpty()) {
				sb.append(_FINDER_COLUMN_R_M_MAPPEDID_3);
			}
			else {
				bindMappedId = true;

				sb.append(_FINDER_COLUMN_R_M_MAPPEDID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

				if (bindMappedId) {
					queryPos.add(mappedId);
				}

				List<RepositoryEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByR_M, finderArgs, list);
					}
				}
				else {
					RepositoryEntry repositoryEntry = list.get(0);

					result = repositoryEntry;

					cacheResult(repositoryEntry);
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
			return (RepositoryEntry)result;
		}
	}

	/**
	 * Removes the repository entry where repositoryId = &#63; and mappedId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param mappedId the mapped ID
	 * @return the repository entry that was removed
	 */
	@Override
	public RepositoryEntry removeByR_M(long repositoryId, String mappedId)
		throws NoSuchRepositoryEntryException {

		RepositoryEntry repositoryEntry = findByR_M(repositoryId, mappedId);

		return remove(repositoryEntry);
	}

	/**
	 * Returns the number of repository entries where repositoryId = &#63; and mappedId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param mappedId the mapped ID
	 * @return the number of matching repository entries
	 */
	@Override
	public int countByR_M(long repositoryId, String mappedId) {
		mappedId = Objects.toString(mappedId, "");

		FinderPath finderPath = _finderPathCountByR_M;

		Object[] finderArgs = new Object[] {repositoryId, mappedId};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REPOSITORYENTRY_WHERE);

			sb.append(_FINDER_COLUMN_R_M_REPOSITORYID_2);

			boolean bindMappedId = false;

			if (mappedId.isEmpty()) {
				sb.append(_FINDER_COLUMN_R_M_MAPPEDID_3);
			}
			else {
				bindMappedId = true;

				sb.append(_FINDER_COLUMN_R_M_MAPPEDID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

				if (bindMappedId) {
					queryPos.add(mappedId);
				}

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_R_M_REPOSITORYID_2 =
		"repositoryEntry.repositoryId = ? AND ";

	private static final String _FINDER_COLUMN_R_M_MAPPEDID_2 =
		"repositoryEntry.mappedId = ?";

	private static final String _FINDER_COLUMN_R_M_MAPPEDID_3 =
		"(repositoryEntry.mappedId IS NULL OR repositoryEntry.mappedId = '')";

	public RepositoryEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(RepositoryEntry.class);

		setModelImplClass(RepositoryEntryImpl.class);
		setModelPKClass(long.class);

		setTable(RepositoryEntryTable.INSTANCE);
	}

	/**
	 * Caches the repository entry in the entity cache if it is enabled.
	 *
	 * @param repositoryEntry the repository entry
	 */
	@Override
	public void cacheResult(RepositoryEntry repositoryEntry) {
		EntityCacheUtil.putResult(
			RepositoryEntryImpl.class, repositoryEntry.getPrimaryKey(),
			repositoryEntry);

		FinderCacheUtil.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				repositoryEntry.getUuid(), repositoryEntry.getGroupId()
			},
			repositoryEntry);

		FinderCacheUtil.putResult(
			_finderPathFetchByR_M,
			new Object[] {
				repositoryEntry.getRepositoryId(), repositoryEntry.getMappedId()
			},
			repositoryEntry);
	}

	/**
	 * Caches the repository entries in the entity cache if it is enabled.
	 *
	 * @param repositoryEntries the repository entries
	 */
	@Override
	public void cacheResult(List<RepositoryEntry> repositoryEntries) {
		for (RepositoryEntry repositoryEntry : repositoryEntries) {
			if (EntityCacheUtil.getResult(
					RepositoryEntryImpl.class,
					repositoryEntry.getPrimaryKey()) == null) {

				cacheResult(repositoryEntry);
			}
		}
	}

	/**
	 * Clears the cache for all repository entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(RepositoryEntryImpl.class);

		FinderCacheUtil.clearCache(RepositoryEntryImpl.class);
	}

	/**
	 * Clears the cache for the repository entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RepositoryEntry repositoryEntry) {
		EntityCacheUtil.removeResult(
			RepositoryEntryImpl.class, repositoryEntry);
	}

	@Override
	public void clearCache(List<RepositoryEntry> repositoryEntries) {
		for (RepositoryEntry repositoryEntry : repositoryEntries) {
			EntityCacheUtil.removeResult(
				RepositoryEntryImpl.class, repositoryEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(RepositoryEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(RepositoryEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		RepositoryEntryModelImpl repositoryEntryModelImpl) {

		Object[] args = new Object[] {
			repositoryEntryModelImpl.getUuid(),
			repositoryEntryModelImpl.getGroupId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByUUID_G, args, repositoryEntryModelImpl);

		args = new Object[] {
			repositoryEntryModelImpl.getRepositoryId(),
			repositoryEntryModelImpl.getMappedId()
		};

		FinderCacheUtil.putResult(_finderPathCountByR_M, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByR_M, args, repositoryEntryModelImpl);
	}

	/**
	 * Creates a new repository entry with the primary key. Does not add the repository entry to the database.
	 *
	 * @param repositoryEntryId the primary key for the new repository entry
	 * @return the new repository entry
	 */
	@Override
	public RepositoryEntry create(long repositoryEntryId) {
		RepositoryEntry repositoryEntry = new RepositoryEntryImpl();

		repositoryEntry.setNew(true);
		repositoryEntry.setPrimaryKey(repositoryEntryId);

		String uuid = PortalUUIDUtil.generate();

		repositoryEntry.setUuid(uuid);

		repositoryEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return repositoryEntry;
	}

	/**
	 * Removes the repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param repositoryEntryId the primary key of the repository entry
	 * @return the repository entry that was removed
	 * @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	 */
	@Override
	public RepositoryEntry remove(long repositoryEntryId)
		throws NoSuchRepositoryEntryException {

		return remove((Serializable)repositoryEntryId);
	}

	/**
	 * Removes the repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the repository entry
	 * @return the repository entry that was removed
	 * @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	 */
	@Override
	public RepositoryEntry remove(Serializable primaryKey)
		throws NoSuchRepositoryEntryException {

		Session session = null;

		try {
			session = openSession();

			RepositoryEntry repositoryEntry = (RepositoryEntry)session.get(
				RepositoryEntryImpl.class, primaryKey);

			if (repositoryEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRepositoryEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(repositoryEntry);
		}
		catch (NoSuchRepositoryEntryException noSuchEntityException) {
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
	protected RepositoryEntry removeImpl(RepositoryEntry repositoryEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(repositoryEntry)) {
				repositoryEntry = (RepositoryEntry)session.get(
					RepositoryEntryImpl.class,
					repositoryEntry.getPrimaryKeyObj());
			}

			if (repositoryEntry != null) {
				session.delete(repositoryEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (repositoryEntry != null) {
			clearCache(repositoryEntry);
		}

		return repositoryEntry;
	}

	@Override
	public RepositoryEntry updateImpl(RepositoryEntry repositoryEntry) {
		boolean isNew = repositoryEntry.isNew();

		if (!(repositoryEntry instanceof RepositoryEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(repositoryEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					repositoryEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in repositoryEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RepositoryEntry implementation " +
					repositoryEntry.getClass());
		}

		RepositoryEntryModelImpl repositoryEntryModelImpl =
			(RepositoryEntryModelImpl)repositoryEntry;

		if (Validator.isNull(repositoryEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			repositoryEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (repositoryEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				repositoryEntry.setCreateDate(now);
			}
			else {
				repositoryEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!repositoryEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				repositoryEntry.setModifiedDate(now);
			}
			else {
				repositoryEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(repositoryEntry);
			}
			else {
				repositoryEntry = (RepositoryEntry)session.merge(
					repositoryEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			RepositoryEntryImpl.class, repositoryEntryModelImpl, false, true);

		cacheUniqueFindersCache(repositoryEntryModelImpl);

		if (isNew) {
			repositoryEntry.setNew(false);
		}

		repositoryEntry.resetOriginalValues();

		return repositoryEntry;
	}

	/**
	 * Returns the repository entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the repository entry
	 * @return the repository entry
	 * @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	 */
	@Override
	public RepositoryEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRepositoryEntryException {

		RepositoryEntry repositoryEntry = fetchByPrimaryKey(primaryKey);

		if (repositoryEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRepositoryEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return repositoryEntry;
	}

	/**
	 * Returns the repository entry with the primary key or throws a <code>NoSuchRepositoryEntryException</code> if it could not be found.
	 *
	 * @param repositoryEntryId the primary key of the repository entry
	 * @return the repository entry
	 * @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	 */
	@Override
	public RepositoryEntry findByPrimaryKey(long repositoryEntryId)
		throws NoSuchRepositoryEntryException {

		return findByPrimaryKey((Serializable)repositoryEntryId);
	}

	/**
	 * Returns the repository entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param repositoryEntryId the primary key of the repository entry
	 * @return the repository entry, or <code>null</code> if a repository entry with the primary key could not be found
	 */
	@Override
	public RepositoryEntry fetchByPrimaryKey(long repositoryEntryId) {
		return fetchByPrimaryKey((Serializable)repositoryEntryId);
	}

	/**
	 * Returns all the repository entries.
	 *
	 * @return the repository entries
	 */
	@Override
	public List<RepositoryEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @return the range of repository entries
	 */
	@Override
	public List<RepositoryEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of repository entries
	 */
	@Override
	public List<RepositoryEntry> findAll(
		int start, int end,
		OrderByComparator<RepositoryEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of repository entries
	 */
	@Override
	public List<RepositoryEntry> findAll(
		int start, int end,
		OrderByComparator<RepositoryEntry> orderByComparator,
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

		List<RepositoryEntry> list = null;

		if (useFinderCache) {
			list = (List<RepositoryEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_REPOSITORYENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_REPOSITORYENTRY;

				sql = sql.concat(RepositoryEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RepositoryEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the repository entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RepositoryEntry repositoryEntry : findAll()) {
			remove(repositoryEntry);
		}
	}

	/**
	 * Returns the number of repository entries.
	 *
	 * @return the number of repository entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_REPOSITORYENTRY);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "repositoryEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REPOSITORYENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RepositoryEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the repository entry persistence.
	 */
	public void afterPropertiesSet() {
		Registry registry = RegistryUtil.getRegistry();

		_argumentsResolverServiceRegistration = registry.registerService(
			ArgumentsResolver.class,
			new RepositoryEntryModelArgumentsResolver());

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

		_finderPathWithPaginationFindByRepositoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRepositoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"repositoryId"}, true);

		_finderPathWithoutPaginationFindByRepositoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRepositoryId",
			new String[] {Long.class.getName()}, new String[] {"repositoryId"},
			true);

		_finderPathCountByRepositoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRepositoryId",
			new String[] {Long.class.getName()}, new String[] {"repositoryId"},
			false);

		_finderPathFetchByR_M = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByR_M",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"repositoryId", "mappedId"}, true);

		_finderPathCountByR_M = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_M",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"repositoryId", "mappedId"}, false);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(RepositoryEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private static final String _SQL_SELECT_REPOSITORYENTRY =
		"SELECT repositoryEntry FROM RepositoryEntry repositoryEntry";

	private static final String _SQL_SELECT_REPOSITORYENTRY_WHERE =
		"SELECT repositoryEntry FROM RepositoryEntry repositoryEntry WHERE ";

	private static final String _SQL_COUNT_REPOSITORYENTRY =
		"SELECT COUNT(repositoryEntry) FROM RepositoryEntry repositoryEntry";

	private static final String _SQL_COUNT_REPOSITORYENTRY_WHERE =
		"SELECT COUNT(repositoryEntry) FROM RepositoryEntry repositoryEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "repositoryEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RepositoryEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RepositoryEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RepositoryEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class RepositoryEntryModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			RepositoryEntryModelImpl repositoryEntryModelImpl =
				(RepositoryEntryModelImpl)baseModel;

			long columnBitmask = repositoryEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					repositoryEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						repositoryEntryModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					repositoryEntryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return RepositoryEntryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return RepositoryEntryTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			RepositoryEntryModelImpl repositoryEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						repositoryEntryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = repositoryEntryModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}