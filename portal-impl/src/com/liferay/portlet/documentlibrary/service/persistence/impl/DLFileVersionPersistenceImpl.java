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

package com.liferay.portlet.documentlibrary.service.persistence.impl;

import com.liferay.document.library.kernel.exception.NoSuchFileVersionException;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.persistence.DLFileVersionPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
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
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the document library file version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileVersionPersistenceImpl
	extends BasePersistenceImpl<DLFileVersion>
	implements DLFileVersionPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DLFileVersionUtil</code> to access the document library file version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DLFileVersionImpl.class.getName();

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
	 * Returns all the document library file versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
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

		List<DLFileVersion> list = null;

		if (useFinderCache) {
			list = (List<DLFileVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if (!uuid.equals(dlFileVersion.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
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

				list = (List<DLFileVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByUuid_First(
			String uuid, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByUuid_First(
			uuid, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUuid_First(
		String uuid, OrderByComparator<DLFileVersion> orderByComparator) {

		List<DLFileVersion> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByUuid_Last(
			String uuid, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByUuid_Last(uuid, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUuid_Last(
		String uuid, OrderByComparator<DLFileVersion> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByUuid_PrevAndNext(
			long fileVersionId, String uuid,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		uuid = Objects.toString(uuid, "");

		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, dlFileVersion, uuid, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByUuid_PrevAndNext(
				session, dlFileVersion, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByUuid_PrevAndNext(
		Session session, DLFileVersion dlFileVersion, String uuid,
		OrderByComparator<DLFileVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
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
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dlFileVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DLFileVersion dlFileVersion :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"dlFileVersion.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(dlFileVersion.uuid IS NULL OR dlFileVersion.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the document library file version where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchFileVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByUUID_G(String uuid, long groupId)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByUUID_G(uuid, groupId);

		if (dlFileVersion == null) {
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

			throw new NoSuchFileVersionException(msg.toString());
		}

		return dlFileVersion;
	}

	/**
	 * Returns the document library file version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the document library file version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof DLFileVersion) {
			DLFileVersion dlFileVersion = (DLFileVersion)result;

			if (!Objects.equals(uuid, dlFileVersion.getUuid()) ||
				(groupId != dlFileVersion.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

				List<DLFileVersion> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					DLFileVersion dlFileVersion = list.get(0);

					result = dlFileVersion;

					cacheResult(dlFileVersion);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByUUID_G, finderArgs);
				}

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
			return (DLFileVersion)result;
		}
	}

	/**
	 * Removes the document library file version where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the document library file version that was removed
	 */
	@Override
	public DLFileVersion removeByUUID_G(String uuid, long groupId)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = findByUUID_G(uuid, groupId);

		return remove(dlFileVersion);
	}

	/**
	 * Returns the number of document library file versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"dlFileVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(dlFileVersion.uuid IS NULL OR dlFileVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"dlFileVersion.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
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

		List<DLFileVersion> list = null;

		if (useFinderCache) {
			list = (List<DLFileVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if (!uuid.equals(dlFileVersion.getUuid()) ||
						(companyId != dlFileVersion.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
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

				list = (List<DLFileVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DLFileVersion> orderByComparator) {

		List<DLFileVersion> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DLFileVersion> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByUuid_C_PrevAndNext(
			long fileVersionId, String uuid, long companyId,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		uuid = Objects.toString(uuid, "");

		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, dlFileVersion, uuid, companyId, orderByComparator,
				true);

			array[1] = dlFileVersion;

			array[2] = getByUuid_C_PrevAndNext(
				session, dlFileVersion, uuid, companyId, orderByComparator,
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

	protected DLFileVersion getByUuid_C_PrevAndNext(
		Session session, DLFileVersion dlFileVersion, String uuid,
		long companyId, OrderByComparator<DLFileVersion> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
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
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dlFileVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DLFileVersion dlFileVersion :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"dlFileVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(dlFileVersion.uuid IS NULL OR dlFileVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"dlFileVersion.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the document library file versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<DLFileVersion> list = null;

		if (useFinderCache) {
			list = (List<DLFileVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if (companyId != dlFileVersion.getCompanyId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<DLFileVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first document library file version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByCompanyId_First(
			long companyId, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByCompanyId_First(
		long companyId, OrderByComparator<DLFileVersion> orderByComparator) {

		List<DLFileVersion> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByCompanyId_Last(
			long companyId, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByCompanyId_Last(
		long companyId, OrderByComparator<DLFileVersion> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where companyId = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByCompanyId_PrevAndNext(
			long fileVersionId, long companyId,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, dlFileVersion, companyId, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByCompanyId_PrevAndNext(
				session, dlFileVersion, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByCompanyId_PrevAndNext(
		Session session, DLFileVersion dlFileVersion, long companyId,
		OrderByComparator<DLFileVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dlFileVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (DLFileVersion dlFileVersion :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"dlFileVersion.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByFileEntryId;
	private FinderPath _finderPathWithoutPaginationFindByFileEntryId;
	private FinderPath _finderPathCountByFileEntryId;

	/**
	 * Returns all the document library file versions where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByFileEntryId(long fileEntryId) {
		return findByFileEntryId(
			fileEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByFileEntryId(
		long fileEntryId, int start, int end) {

		return findByFileEntryId(fileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByFileEntryId(
			fileEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByFileEntryId;
				finderArgs = new Object[] {fileEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByFileEntryId;
			finderArgs = new Object[] {
				fileEntryId, start, end, orderByComparator
			};
		}

		List<DLFileVersion> list = null;

		if (useFinderCache) {
			list = (List<DLFileVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if (fileEntryId != dlFileVersion.getFileEntryId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				list = (List<DLFileVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByFileEntryId_First(
			long fileEntryId,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByFileEntryId_First(
			fileEntryId, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByFileEntryId_First(
		long fileEntryId, OrderByComparator<DLFileVersion> orderByComparator) {

		List<DLFileVersion> list = findByFileEntryId(
			fileEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByFileEntryId_Last(
			long fileEntryId,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByFileEntryId_Last(
			fileEntryId, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByFileEntryId_Last(
		long fileEntryId, OrderByComparator<DLFileVersion> orderByComparator) {

		int count = countByFileEntryId(fileEntryId);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByFileEntryId(
			fileEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByFileEntryId_PrevAndNext(
			long fileVersionId, long fileEntryId,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByFileEntryId_PrevAndNext(
				session, dlFileVersion, fileEntryId, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByFileEntryId_PrevAndNext(
				session, dlFileVersion, fileEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByFileEntryId_PrevAndNext(
		Session session, DLFileVersion dlFileVersion, long fileEntryId,
		OrderByComparator<DLFileVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dlFileVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 */
	@Override
	public void removeByFileEntryId(long fileEntryId) {
		for (DLFileVersion dlFileVersion :
				findByFileEntryId(
					fileEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByFileEntryId(long fileEntryId) {
		FinderPath finderPath = _finderPathCountByFileEntryId;

		Object[] finderArgs = new Object[] {fileEntryId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2 =
		"dlFileVersion.fileEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByMimeType;
	private FinderPath _finderPathWithoutPaginationFindByMimeType;
	private FinderPath _finderPathCountByMimeType;

	/**
	 * Returns all the document library file versions where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByMimeType(String mimeType) {
		return findByMimeType(
			mimeType, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByMimeType(
		String mimeType, int start, int end) {

		return findByMimeType(mimeType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByMimeType(
		String mimeType, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByMimeType(mimeType, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByMimeType(
		String mimeType, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		mimeType = Objects.toString(mimeType, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByMimeType;
				finderArgs = new Object[] {mimeType};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByMimeType;
			finderArgs = new Object[] {mimeType, start, end, orderByComparator};
		}

		List<DLFileVersion> list = null;

		if (useFinderCache) {
			list = (List<DLFileVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if (!mimeType.equals(dlFileVersion.getMimeType())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			boolean bindMimeType = false;

			if (mimeType.isEmpty()) {
				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_3);
			}
			else {
				bindMimeType = true;

				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindMimeType) {
					qPos.add(mimeType);
				}

				list = (List<DLFileVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first document library file version in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByMimeType_First(
			String mimeType, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByMimeType_First(
			mimeType, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("mimeType=");
		msg.append(mimeType);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByMimeType_First(
		String mimeType, OrderByComparator<DLFileVersion> orderByComparator) {

		List<DLFileVersion> list = findByMimeType(
			mimeType, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByMimeType_Last(
			String mimeType, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByMimeType_Last(
			mimeType, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("mimeType=");
		msg.append(mimeType);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByMimeType_Last(
		String mimeType, OrderByComparator<DLFileVersion> orderByComparator) {

		int count = countByMimeType(mimeType);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByMimeType(
			mimeType, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where mimeType = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByMimeType_PrevAndNext(
			long fileVersionId, String mimeType,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		mimeType = Objects.toString(mimeType, "");

		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByMimeType_PrevAndNext(
				session, dlFileVersion, mimeType, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByMimeType_PrevAndNext(
				session, dlFileVersion, mimeType, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByMimeType_PrevAndNext(
		Session session, DLFileVersion dlFileVersion, String mimeType,
		OrderByComparator<DLFileVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		boolean bindMimeType = false;

		if (mimeType.isEmpty()) {
			query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_3);
		}
		else {
			bindMimeType = true;

			query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindMimeType) {
			qPos.add(mimeType);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dlFileVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where mimeType = &#63; from the database.
	 *
	 * @param mimeType the mime type
	 */
	@Override
	public void removeByMimeType(String mimeType) {
		for (DLFileVersion dlFileVersion :
				findByMimeType(
					mimeType, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByMimeType(String mimeType) {
		mimeType = Objects.toString(mimeType, "");

		FinderPath finderPath = _finderPathCountByMimeType;

		Object[] finderArgs = new Object[] {mimeType};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			boolean bindMimeType = false;

			if (mimeType.isEmpty()) {
				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_3);
			}
			else {
				bindMimeType = true;

				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindMimeType) {
					qPos.add(mimeType);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_MIMETYPE_MIMETYPE_2 =
		"dlFileVersion.mimeType = ?";

	private static final String _FINDER_COLUMN_MIMETYPE_MIMETYPE_3 =
		"(dlFileVersion.mimeType IS NULL OR dlFileVersion.mimeType = '')";

	private FinderPath _finderPathWithPaginationFindByC_NotS;
	private FinderPath _finderPathWithPaginationCountByC_NotS;

	/**
	 * Returns all the document library file versions where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_NotS(long companyId, int status) {
		return findByC_NotS(
			companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_NotS(
		long companyId, int status, int start, int end) {

		return findByC_NotS(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_NotS(
		long companyId, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByC_NotS(
			companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_NotS(
		long companyId, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_NotS;
		finderArgs = new Object[] {
			companyId, status, start, end, orderByComparator
		};

		List<DLFileVersion> list = null;

		if (useFinderCache) {
			list = (List<DLFileVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if ((companyId != dlFileVersion.getCompanyId()) ||
						(status == dlFileVersion.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_NOTS_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				list = (List<DLFileVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByC_NotS_First(
			long companyId, int status,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByC_NotS_First(
			companyId, status, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status!=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByC_NotS_First(
		long companyId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {

		List<DLFileVersion> list = findByC_NotS(
			companyId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByC_NotS_Last(
			long companyId, int status,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByC_NotS_Last(
			companyId, status, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status!=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByC_NotS_Last(
		long companyId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {

		int count = countByC_NotS(companyId, status);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByC_NotS(
			companyId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByC_NotS_PrevAndNext(
			long fileVersionId, long companyId, int status,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByC_NotS_PrevAndNext(
				session, dlFileVersion, companyId, status, orderByComparator,
				true);

			array[1] = dlFileVersion;

			array[2] = getByC_NotS_PrevAndNext(
				session, dlFileVersion, companyId, status, orderByComparator,
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

	protected DLFileVersion getByC_NotS_PrevAndNext(
		Session session, DLFileVersion dlFileVersion, long companyId,
		int status, OrderByComparator<DLFileVersion> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_C_NOTS_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_NOTS_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dlFileVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_NotS(long companyId, int status) {
		for (DLFileVersion dlFileVersion :
				findByC_NotS(
					companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByC_NotS(long companyId, int status) {
		FinderPath finderPath = _finderPathWithPaginationCountByC_NotS;

		Object[] finderArgs = new Object[] {companyId, status};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_NOTS_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_NOTS_COMPANYID_2 =
		"dlFileVersion.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_NOTS_STATUS_2 =
		"dlFileVersion.status != ?";

	private FinderPath _finderPathFetchByF_V;
	private FinderPath _finderPathCountByF_V;

	/**
	 * Returns the document library file version where fileEntryId = &#63; and version = &#63; or throws a <code>NoSuchFileVersionException</code> if it could not be found.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByF_V(long fileEntryId, String version)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByF_V(fileEntryId, version);

		if (dlFileVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("fileEntryId=");
			msg.append(fileEntryId);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFileVersionException(msg.toString());
		}

		return dlFileVersion;
	}

	/**
	 * Returns the document library file version where fileEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByF_V(long fileEntryId, String version) {
		return fetchByF_V(fileEntryId, version, true);
	}

	/**
	 * Returns the document library file version where fileEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByF_V(
		long fileEntryId, String version, boolean useFinderCache) {

		version = Objects.toString(version, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {fileEntryId, version};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByF_V, finderArgs, this);
		}

		if (result instanceof DLFileVersion) {
			DLFileVersion dlFileVersion = (DLFileVersion)result;

			if ((fileEntryId != dlFileVersion.getFileEntryId()) ||
				!Objects.equals(version, dlFileVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_V_FILEENTRYID_2);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				query.append(_FINDER_COLUMN_F_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_F_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				if (bindVersion) {
					qPos.add(version);
				}

				List<DLFileVersion> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByF_V, finderArgs, list);
					}
				}
				else {
					DLFileVersion dlFileVersion = list.get(0);

					result = dlFileVersion;

					cacheResult(dlFileVersion);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByF_V, finderArgs);
				}

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
			return (DLFileVersion)result;
		}
	}

	/**
	 * Removes the document library file version where fileEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the document library file version that was removed
	 */
	@Override
	public DLFileVersion removeByF_V(long fileEntryId, String version)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = findByF_V(fileEntryId, version);

		return remove(dlFileVersion);
	}

	/**
	 * Returns the number of document library file versions where fileEntryId = &#63; and version = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByF_V(long fileEntryId, String version) {
		version = Objects.toString(version, "");

		FinderPath finderPath = _finderPathCountByF_V;

		Object[] finderArgs = new Object[] {fileEntryId, version};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_V_FILEENTRYID_2);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				query.append(_FINDER_COLUMN_F_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_F_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				if (bindVersion) {
					qPos.add(version);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_F_V_FILEENTRYID_2 =
		"dlFileVersion.fileEntryId = ? AND ";

	private static final String _FINDER_COLUMN_F_V_VERSION_2 =
		"dlFileVersion.version = ?";

	private static final String _FINDER_COLUMN_F_V_VERSION_3 =
		"(dlFileVersion.version IS NULL OR dlFileVersion.version = '')";

	private FinderPath _finderPathWithPaginationFindByF_S;
	private FinderPath _finderPathWithoutPaginationFindByF_S;
	private FinderPath _finderPathCountByF_S;

	/**
	 * Returns all the document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(long fileEntryId, int status) {
		return findByF_S(
			fileEntryId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(
		long fileEntryId, int status, int start, int end) {

		return findByF_S(fileEntryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(
		long fileEntryId, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByF_S(
			fileEntryId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(
		long fileEntryId, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByF_S;
				finderArgs = new Object[] {fileEntryId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByF_S;
			finderArgs = new Object[] {
				fileEntryId, status, start, end, orderByComparator
			};
		}

		List<DLFileVersion> list = null;

		if (useFinderCache) {
			list = (List<DLFileVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if ((fileEntryId != dlFileVersion.getFileEntryId()) ||
						(status != dlFileVersion.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_S_FILEENTRYID_2);

			query.append(_FINDER_COLUMN_F_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				qPos.add(status);

				list = (List<DLFileVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByF_S_First(
			long fileEntryId, int status,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByF_S_First(
			fileEntryId, status, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByF_S_First(
		long fileEntryId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {

		List<DLFileVersion> list = findByF_S(
			fileEntryId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByF_S_Last(
			long fileEntryId, int status,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByF_S_Last(
			fileEntryId, status, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByF_S_Last(
		long fileEntryId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {

		int count = countByF_S(fileEntryId, status);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByF_S(
			fileEntryId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByF_S_PrevAndNext(
			long fileVersionId, long fileEntryId, int status,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByF_S_PrevAndNext(
				session, dlFileVersion, fileEntryId, status, orderByComparator,
				true);

			array[1] = dlFileVersion;

			array[2] = getByF_S_PrevAndNext(
				session, dlFileVersion, fileEntryId, status, orderByComparator,
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

	protected DLFileVersion getByF_S_PrevAndNext(
		Session session, DLFileVersion dlFileVersion, long fileEntryId,
		int status, OrderByComparator<DLFileVersion> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_F_S_FILEENTRYID_2);

		query.append(_FINDER_COLUMN_F_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileEntryId);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dlFileVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where fileEntryId = &#63; and status = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 */
	@Override
	public void removeByF_S(long fileEntryId, int status) {
		for (DLFileVersion dlFileVersion :
				findByF_S(
					fileEntryId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByF_S(long fileEntryId, int status) {
		FinderPath finderPath = _finderPathCountByF_S;

		Object[] finderArgs = new Object[] {fileEntryId, status};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_S_FILEENTRYID_2);

			query.append(_FINDER_COLUMN_F_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_F_S_FILEENTRYID_2 =
		"dlFileVersion.fileEntryId = ? AND ";

	private static final String _FINDER_COLUMN_F_S_STATUS_2 =
		"dlFileVersion.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_F_S;
	private FinderPath _finderPathWithoutPaginationFindByG_F_S;
	private FinderPath _finderPathCountByG_F_S;

	/**
	 * Returns all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_S(
		long groupId, long folderId, int status) {

		return findByG_F_S(
			groupId, folderId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_S(
		long groupId, long folderId, int status, int start, int end) {

		return findByG_F_S(groupId, folderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findByG_F_S(
			groupId, folderId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_F_S;
				finderArgs = new Object[] {groupId, folderId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_F_S;
			finderArgs = new Object[] {
				groupId, folderId, status, start, end, orderByComparator
			};
		}

		List<DLFileVersion> list = null;

		if (useFinderCache) {
			list = (List<DLFileVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if ((groupId != dlFileVersion.getGroupId()) ||
						(folderId != dlFileVersion.getFolderId()) ||
						(status != dlFileVersion.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(status);

				list = (List<DLFileVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByG_F_S_First(
			long groupId, long folderId, int status,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByG_F_S_First(
			groupId, folderId, status, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByG_F_S_First(
		long groupId, long folderId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {

		List<DLFileVersion> list = findByG_F_S(
			groupId, folderId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByG_F_S_Last(
			long groupId, long folderId, int status,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByG_F_S_Last(
			groupId, folderId, status, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByG_F_S_Last(
		long groupId, long folderId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {

		int count = countByG_F_S(groupId, folderId, status);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByG_F_S(
			groupId, folderId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByG_F_S_PrevAndNext(
			long fileVersionId, long groupId, long folderId, int status,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByG_F_S_PrevAndNext(
				session, dlFileVersion, groupId, folderId, status,
				orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByG_F_S_PrevAndNext(
				session, dlFileVersion, groupId, folderId, status,
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

	protected DLFileVersion getByG_F_S_PrevAndNext(
		Session session, DLFileVersion dlFileVersion, long groupId,
		long folderId, int status,
		OrderByComparator<DLFileVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dlFileVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 */
	@Override
	public void removeByG_F_S(long groupId, long folderId, int status) {
		for (DLFileVersion dlFileVersion :
				findByG_F_S(
					groupId, folderId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByG_F_S(long groupId, long folderId, int status) {
		FinderPath finderPath = _finderPathCountByG_F_S;

		Object[] finderArgs = new Object[] {groupId, folderId, status};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_F_S_GROUPID_2 =
		"dlFileVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_S_FOLDERID_2 =
		"dlFileVersion.folderId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_S_STATUS_2 =
		"dlFileVersion.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_F_T_V;
	private FinderPath _finderPathWithoutPaginationFindByG_F_T_V;
	private FinderPath _finderPathCountByG_F_T_V;

	/**
	 * Returns all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_T_V(
		long groupId, long folderId, String title, String version) {

		return findByG_F_T_V(
			groupId, folderId, title, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_T_V(
		long groupId, long folderId, String title, String version, int start,
		int end) {

		return findByG_F_T_V(
			groupId, folderId, title, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_T_V(
		long groupId, long folderId, String title, String version, int start,
		int end, OrderByComparator<DLFileVersion> orderByComparator) {

		return findByG_F_T_V(
			groupId, folderId, title, version, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_T_V(
		long groupId, long folderId, String title, String version, int start,
		int end, OrderByComparator<DLFileVersion> orderByComparator,
		boolean useFinderCache) {

		title = Objects.toString(title, "");
		version = Objects.toString(version, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_F_T_V;
				finderArgs = new Object[] {groupId, folderId, title, version};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_F_T_V;
			finderArgs = new Object[] {
				groupId, folderId, title, version, start, end, orderByComparator
			};
		}

		List<DLFileVersion> list = null;

		if (useFinderCache) {
			list = (List<DLFileVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if ((groupId != dlFileVersion.getGroupId()) ||
						(folderId != dlFileVersion.getFolderId()) ||
						!title.equals(dlFileVersion.getTitle()) ||
						!version.equals(dlFileVersion.getVersion())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_T_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_T_V_FOLDERID_2);

			boolean bindTitle = false;

			if (title.isEmpty()) {
				query.append(_FINDER_COLUMN_G_F_T_V_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_G_F_T_V_TITLE_2);
			}

			boolean bindVersion = false;

			if (version.isEmpty()) {
				query.append(_FINDER_COLUMN_G_F_T_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_G_F_T_V_VERSION_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (bindTitle) {
					qPos.add(title);
				}

				if (bindVersion) {
					qPos.add(version);
				}

				list = (List<DLFileVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByG_F_T_V_First(
			long groupId, long folderId, String title, String version,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByG_F_T_V_First(
			groupId, folderId, title, version, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", title=");
		msg.append(title);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByG_F_T_V_First(
		long groupId, long folderId, String title, String version,
		OrderByComparator<DLFileVersion> orderByComparator) {

		List<DLFileVersion> list = findByG_F_T_V(
			groupId, folderId, title, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByG_F_T_V_Last(
			long groupId, long folderId, String title, String version,
			OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByG_F_T_V_Last(
			groupId, folderId, title, version, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", title=");
		msg.append(title);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByG_F_T_V_Last(
		long groupId, long folderId, String title, String version,
		OrderByComparator<DLFileVersion> orderByComparator) {

		int count = countByG_F_T_V(groupId, folderId, title, version);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByG_F_T_V(
			groupId, folderId, title, version, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByG_F_T_V_PrevAndNext(
			long fileVersionId, long groupId, long folderId, String title,
			String version, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {

		title = Objects.toString(title, "");
		version = Objects.toString(version, "");

		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByG_F_T_V_PrevAndNext(
				session, dlFileVersion, groupId, folderId, title, version,
				orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByG_F_T_V_PrevAndNext(
				session, dlFileVersion, groupId, folderId, title, version,
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

	protected DLFileVersion getByG_F_T_V_PrevAndNext(
		Session session, DLFileVersion dlFileVersion, long groupId,
		long folderId, String title, String version,
		OrderByComparator<DLFileVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_F_T_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_T_V_FOLDERID_2);

		boolean bindTitle = false;

		if (title.isEmpty()) {
			query.append(_FINDER_COLUMN_G_F_T_V_TITLE_3);
		}
		else {
			bindTitle = true;

			query.append(_FINDER_COLUMN_G_F_T_V_TITLE_2);
		}

		boolean bindVersion = false;

		if (version.isEmpty()) {
			query.append(_FINDER_COLUMN_G_F_T_V_VERSION_3);
		}
		else {
			bindVersion = true;

			query.append(_FINDER_COLUMN_G_F_T_V_VERSION_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (bindTitle) {
			qPos.add(title);
		}

		if (bindVersion) {
			qPos.add(version);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dlFileVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 */
	@Override
	public void removeByG_F_T_V(
		long groupId, long folderId, String title, String version) {

		for (DLFileVersion dlFileVersion :
				findByG_F_T_V(
					groupId, folderId, title, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByG_F_T_V(
		long groupId, long folderId, String title, String version) {

		title = Objects.toString(title, "");
		version = Objects.toString(version, "");

		FinderPath finderPath = _finderPathCountByG_F_T_V;

		Object[] finderArgs = new Object[] {groupId, folderId, title, version};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_T_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_T_V_FOLDERID_2);

			boolean bindTitle = false;

			if (title.isEmpty()) {
				query.append(_FINDER_COLUMN_G_F_T_V_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_G_F_T_V_TITLE_2);
			}

			boolean bindVersion = false;

			if (version.isEmpty()) {
				query.append(_FINDER_COLUMN_G_F_T_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_G_F_T_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (bindTitle) {
					qPos.add(title);
				}

				if (bindVersion) {
					qPos.add(version);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_F_T_V_GROUPID_2 =
		"dlFileVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_T_V_FOLDERID_2 =
		"dlFileVersion.folderId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_T_V_TITLE_2 =
		"dlFileVersion.title = ? AND ";

	private static final String _FINDER_COLUMN_G_F_T_V_TITLE_3 =
		"(dlFileVersion.title IS NULL OR dlFileVersion.title = '') AND ";

	private static final String _FINDER_COLUMN_G_F_T_V_VERSION_2 =
		"dlFileVersion.version = ?";

	private static final String _FINDER_COLUMN_G_F_T_V_VERSION_3 =
		"(dlFileVersion.version IS NULL OR dlFileVersion.version = '')";

	public DLFileVersionPersistenceImpl() {
		setModelClass(DLFileVersion.class);

		setModelImplClass(DLFileVersionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("size", "size_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the document library file version in the entity cache if it is enabled.
	 *
	 * @param dlFileVersion the document library file version
	 */
	@Override
	public void cacheResult(DLFileVersion dlFileVersion) {
		EntityCacheUtil.putResult(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey(),
			dlFileVersion);

		FinderCacheUtil.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {dlFileVersion.getUuid(), dlFileVersion.getGroupId()},
			dlFileVersion);

		FinderCacheUtil.putResult(
			_finderPathFetchByF_V,
			new Object[] {
				dlFileVersion.getFileEntryId(), dlFileVersion.getVersion()
			},
			dlFileVersion);

		dlFileVersion.resetOriginalValues();
	}

	/**
	 * Caches the document library file versions in the entity cache if it is enabled.
	 *
	 * @param dlFileVersions the document library file versions
	 */
	@Override
	public void cacheResult(List<DLFileVersion> dlFileVersions) {
		for (DLFileVersion dlFileVersion : dlFileVersions) {
			if (EntityCacheUtil.getResult(
					DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
					DLFileVersionImpl.class, dlFileVersion.getPrimaryKey()) ==
						null) {

				cacheResult(dlFileVersion);
			}
			else {
				dlFileVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all document library file versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(DLFileVersionImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the document library file version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFileVersion dlFileVersion) {
		EntityCacheUtil.removeResult(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DLFileVersionModelImpl)dlFileVersion, true);
	}

	@Override
	public void clearCache(List<DLFileVersion> dlFileVersions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			EntityCacheUtil.removeResult(
				DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
				DLFileVersionImpl.class, dlFileVersion.getPrimaryKey());

			clearUniqueFindersCache(
				(DLFileVersionModelImpl)dlFileVersion, true);
		}
	}

	protected void cacheUniqueFindersCache(
		DLFileVersionModelImpl dlFileVersionModelImpl) {

		Object[] args = new Object[] {
			dlFileVersionModelImpl.getUuid(),
			dlFileVersionModelImpl.getGroupId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByUUID_G, args, dlFileVersionModelImpl, false);

		args = new Object[] {
			dlFileVersionModelImpl.getFileEntryId(),
			dlFileVersionModelImpl.getVersion()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByF_V, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByF_V, args, dlFileVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DLFileVersionModelImpl dlFileVersionModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				dlFileVersionModelImpl.getUuid(),
				dlFileVersionModelImpl.getGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUUID_G, args);
			FinderCacheUtil.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((dlFileVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				dlFileVersionModelImpl.getOriginalUuid(),
				dlFileVersionModelImpl.getOriginalGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUUID_G, args);
			FinderCacheUtil.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				dlFileVersionModelImpl.getFileEntryId(),
				dlFileVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByF_V, args);
			FinderCacheUtil.removeResult(_finderPathFetchByF_V, args);
		}

		if ((dlFileVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByF_V.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				dlFileVersionModelImpl.getOriginalFileEntryId(),
				dlFileVersionModelImpl.getOriginalVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByF_V, args);
			FinderCacheUtil.removeResult(_finderPathFetchByF_V, args);
		}
	}

	/**
	 * Creates a new document library file version with the primary key. Does not add the document library file version to the database.
	 *
	 * @param fileVersionId the primary key for the new document library file version
	 * @return the new document library file version
	 */
	@Override
	public DLFileVersion create(long fileVersionId) {
		DLFileVersion dlFileVersion = new DLFileVersionImpl();

		dlFileVersion.setNew(true);
		dlFileVersion.setPrimaryKey(fileVersionId);

		String uuid = PortalUUIDUtil.generate();

		dlFileVersion.setUuid(uuid);

		dlFileVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return dlFileVersion;
	}

	/**
	 * Removes the document library file version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileVersionId the primary key of the document library file version
	 * @return the document library file version that was removed
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion remove(long fileVersionId)
		throws NoSuchFileVersionException {

		return remove((Serializable)fileVersionId);
	}

	/**
	 * Removes the document library file version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library file version
	 * @return the document library file version that was removed
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion remove(Serializable primaryKey)
		throws NoSuchFileVersionException {

		Session session = null;

		try {
			session = openSession();

			DLFileVersion dlFileVersion = (DLFileVersion)session.get(
				DLFileVersionImpl.class, primaryKey);

			if (dlFileVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(dlFileVersion);
		}
		catch (NoSuchFileVersionException nsee) {
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
	protected DLFileVersion removeImpl(DLFileVersion dlFileVersion) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlFileVersion)) {
				dlFileVersion = (DLFileVersion)session.get(
					DLFileVersionImpl.class, dlFileVersion.getPrimaryKeyObj());
			}

			if (dlFileVersion != null) {
				session.delete(dlFileVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlFileVersion != null) {
			clearCache(dlFileVersion);
		}

		return dlFileVersion;
	}

	@Override
	public DLFileVersion updateImpl(DLFileVersion dlFileVersion) {
		boolean isNew = dlFileVersion.isNew();

		if (!(dlFileVersion instanceof DLFileVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(dlFileVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					dlFileVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in dlFileVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DLFileVersion implementation " +
					dlFileVersion.getClass());
		}

		DLFileVersionModelImpl dlFileVersionModelImpl =
			(DLFileVersionModelImpl)dlFileVersion;

		if (Validator.isNull(dlFileVersion.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileVersion.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (dlFileVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				dlFileVersion.setCreateDate(now);
			}
			else {
				dlFileVersion.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!dlFileVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				dlFileVersion.setModifiedDate(now);
			}
			else {
				dlFileVersion.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (dlFileVersion.isNew()) {
				session.save(dlFileVersion);

				dlFileVersion.setNew(false);
			}
			else {
				dlFileVersion = (DLFileVersion)session.merge(dlFileVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!DLFileVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {dlFileVersionModelImpl.getUuid()};

			FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				dlFileVersionModelImpl.getUuid(),
				dlFileVersionModelImpl.getCompanyId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {dlFileVersionModelImpl.getCompanyId()};

			FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {dlFileVersionModelImpl.getFileEntryId()};

			FinderCacheUtil.removeResult(_finderPathCountByFileEntryId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByFileEntryId, args);

			args = new Object[] {dlFileVersionModelImpl.getMimeType()};

			FinderCacheUtil.removeResult(_finderPathCountByMimeType, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByMimeType, args);

			args = new Object[] {
				dlFileVersionModelImpl.getFileEntryId(),
				dlFileVersionModelImpl.getStatus()
			};

			FinderCacheUtil.removeResult(_finderPathCountByF_S, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByF_S, args);

			args = new Object[] {
				dlFileVersionModelImpl.getGroupId(),
				dlFileVersionModelImpl.getFolderId(),
				dlFileVersionModelImpl.getStatus()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_F_S, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_F_S, args);

			args = new Object[] {
				dlFileVersionModelImpl.getGroupId(),
				dlFileVersionModelImpl.getFolderId(),
				dlFileVersionModelImpl.getTitle(),
				dlFileVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_F_T_V, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_F_T_V, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((dlFileVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					dlFileVersionModelImpl.getOriginalUuid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {dlFileVersionModelImpl.getUuid()};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					dlFileVersionModelImpl.getOriginalUuid(),
					dlFileVersionModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					dlFileVersionModelImpl.getUuid(),
					dlFileVersionModelImpl.getCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dlFileVersionModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {dlFileVersionModelImpl.getCompanyId()};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFileEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dlFileVersionModelImpl.getOriginalFileEntryId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByFileEntryId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByFileEntryId, args);

				args = new Object[] {dlFileVersionModelImpl.getFileEntryId()};

				FinderCacheUtil.removeResult(
					_finderPathCountByFileEntryId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByFileEntryId, args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByMimeType.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dlFileVersionModelImpl.getOriginalMimeType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByMimeType, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByMimeType, args);

				args = new Object[] {dlFileVersionModelImpl.getMimeType()};

				FinderCacheUtil.removeResult(_finderPathCountByMimeType, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByMimeType, args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByF_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					dlFileVersionModelImpl.getOriginalFileEntryId(),
					dlFileVersionModelImpl.getOriginalStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByF_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByF_S, args);

				args = new Object[] {
					dlFileVersionModelImpl.getFileEntryId(),
					dlFileVersionModelImpl.getStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByF_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByF_S, args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_F_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					dlFileVersionModelImpl.getOriginalGroupId(),
					dlFileVersionModelImpl.getOriginalFolderId(),
					dlFileVersionModelImpl.getOriginalStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_F_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_F_S, args);

				args = new Object[] {
					dlFileVersionModelImpl.getGroupId(),
					dlFileVersionModelImpl.getFolderId(),
					dlFileVersionModelImpl.getStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_F_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_F_S, args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_F_T_V.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dlFileVersionModelImpl.getOriginalGroupId(),
					dlFileVersionModelImpl.getOriginalFolderId(),
					dlFileVersionModelImpl.getOriginalTitle(),
					dlFileVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_F_T_V, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_F_T_V, args);

				args = new Object[] {
					dlFileVersionModelImpl.getGroupId(),
					dlFileVersionModelImpl.getFolderId(),
					dlFileVersionModelImpl.getTitle(),
					dlFileVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_F_T_V, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_F_T_V, args);
			}
		}

		EntityCacheUtil.putResult(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey(),
			dlFileVersion, false);

		clearUniqueFindersCache(dlFileVersionModelImpl, false);
		cacheUniqueFindersCache(dlFileVersionModelImpl);

		dlFileVersion.resetOriginalValues();

		return dlFileVersion;
	}

	/**
	 * Returns the document library file version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file version
	 * @return the document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFileVersionException {

		DLFileVersion dlFileVersion = fetchByPrimaryKey(primaryKey);

		if (dlFileVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFileVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return dlFileVersion;
	}

	/**
	 * Returns the document library file version with the primary key or throws a <code>NoSuchFileVersionException</code> if it could not be found.
	 *
	 * @param fileVersionId the primary key of the document library file version
	 * @return the document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion findByPrimaryKey(long fileVersionId)
		throws NoSuchFileVersionException {

		return findByPrimaryKey((Serializable)fileVersionId);
	}

	/**
	 * Returns the document library file version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileVersionId the primary key of the document library file version
	 * @return the document library file version, or <code>null</code> if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion fetchByPrimaryKey(long fileVersionId) {
		return fetchByPrimaryKey((Serializable)fileVersionId);
	}

	/**
	 * Returns all the document library file versions.
	 *
	 * @return the document library file versions
	 */
	@Override
	public List<DLFileVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of document library file versions
	 */
	@Override
	public List<DLFileVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file versions
	 */
	@Override
	public List<DLFileVersion> findAll(
		int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of document library file versions
	 */
	@Override
	public List<DLFileVersion> findAll(
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator,
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

		List<DLFileVersion> list = null;

		if (useFinderCache) {
			list = (List<DLFileVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DLFILEVERSION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILEVERSION;

				sql = sql.concat(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<DLFileVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the document library file versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLFileVersion dlFileVersion : findAll()) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions.
	 *
	 * @return the number of document library file versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLFILEVERSION);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

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
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "fileVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DLFILEVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DLFileVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the document library file version persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			DLFileVersionModelImpl.UUID_COLUMN_BITMASK |
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			DLFileVersionModelImpl.UUID_COLUMN_BITMASK |
			DLFileVersionModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			DLFileVersionModelImpl.UUID_COLUMN_BITMASK |
			DLFileVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] {Long.class.getName()},
			DLFileVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByFileEntryId = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByFileEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByFileEntryId = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByFileEntryId", new String[] {Long.class.getName()},
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByFileEntryId = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileEntryId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByMimeType = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByMimeType",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByMimeType = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByMimeType", new String[] {String.class.getName()},
			DLFileVersionModelImpl.MIMETYPE_COLUMN_BITMASK |
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByMimeType = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByMimeType",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByC_NotS = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_NotS",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByC_NotS = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_NotS",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathFetchByF_V = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByF_V",
			new String[] {Long.class.getName(), String.class.getName()},
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByF_V = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_V",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByF_S = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByF_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByF_S = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByF_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.STATUS_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByF_S = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_S",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByG_F_S = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_F_S = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			DLFileVersionModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileVersionModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileVersionModelImpl.STATUS_COLUMN_BITMASK |
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByG_F_S = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_F_T_V = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F_T_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_F_T_V = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_F_T_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			DLFileVersionModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileVersionModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileVersionModelImpl.TITLE_COLUMN_BITMASK |
			DLFileVersionModelImpl.VERSION_COLUMN_BITMASK |
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByG_F_T_V = new FinderPath(
			DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_T_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DLFileVersionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_DLFILEVERSION =
		"SELECT dlFileVersion FROM DLFileVersion dlFileVersion";

	private static final String _SQL_SELECT_DLFILEVERSION_WHERE =
		"SELECT dlFileVersion FROM DLFileVersion dlFileVersion WHERE ";

	private static final String _SQL_COUNT_DLFILEVERSION =
		"SELECT COUNT(dlFileVersion) FROM DLFileVersion dlFileVersion";

	private static final String _SQL_COUNT_DLFILEVERSION_WHERE =
		"SELECT COUNT(dlFileVersion) FROM DLFileVersion dlFileVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DLFileVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DLFileVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileVersionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "size"});

}