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

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryTypePersistence;
import com.liferay.document.library.kernel.service.persistence.DLFolderPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the document library folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFolderPersistenceImpl
	extends BasePersistenceImpl<DLFolder> implements DLFolderPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DLFolderUtil</code> to access the document library folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DLFolderImpl.class.getName();

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
	 * Returns all the document library folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching document library folders
	 */
	@Override
	public List<DLFolder> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DLFolder> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DLFolder> orderByComparator, boolean useFinderCache) {

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

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFolder dlFolder : list) {
					if (!uuid.equals(dlFolder.getUuid())) {
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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
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

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Returns the first document library folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByUuid_First(
			String uuid, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByUuid_First(uuid, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByUuid_First(
		String uuid, OrderByComparator<DLFolder> orderByComparator) {

		List<DLFolder> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByUuid_Last(
			String uuid, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByUuid_Last(uuid, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByUuid_Last(
		String uuid, OrderByComparator<DLFolder> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DLFolder> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where uuid = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] findByUuid_PrevAndNext(
			long folderId, String uuid,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		uuid = Objects.toString(uuid, "");

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, dlFolder, uuid, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByUuid_PrevAndNext(
				session, dlFolder, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder getByUuid_PrevAndNext(
		Session session, DLFolder dlFolder, String uuid,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library folders where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DLFolder dlFolder :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching document library folders
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

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

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
		"dlFolder.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(dlFolder.uuid IS NULL OR dlFolder.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the document library folder where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByUUID_G(uuid, groupId);

		if (dlFolder == null) {
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

			throw new NoSuchFolderException(msg.toString());
		}

		return dlFolder;
	}

	/**
	 * Returns the document library folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the document library folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByUUID_G(
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

		if (result instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)result;

			if (!Objects.equals(uuid, dlFolder.getUuid()) ||
				(groupId != dlFolder.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

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

				List<DLFolder> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					DLFolder dlFolder = list.get(0);

					result = dlFolder;

					cacheResult(dlFolder);
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
			return (DLFolder)result;
		}
	}

	/**
	 * Removes the document library folder where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the document library folder that was removed
	 */
	@Override
	public DLFolder removeByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException {

		DLFolder dlFolder = findByUUID_G(uuid, groupId);

		return remove(dlFolder);
	}

	/**
	 * Returns the number of document library folders where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching document library folders
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

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

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
		"dlFolder.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(dlFolder.uuid IS NULL OR dlFolder.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"dlFolder.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the document library folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching document library folders
	 */
	@Override
	public List<DLFolder> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator, boolean useFinderCache) {

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

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFolder dlFolder : list) {
					if (!uuid.equals(dlFolder.getUuid()) ||
						(companyId != dlFolder.getCompanyId())) {

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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

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
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
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

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Returns the first document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DLFolder> orderByComparator) {

		List<DLFolder> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DLFolder> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DLFolder> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] findByUuid_C_PrevAndNext(
			long folderId, String uuid, long companyId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		uuid = Objects.toString(uuid, "");

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, dlFolder, uuid, companyId, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByUuid_C_PrevAndNext(
				session, dlFolder, uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder getByUuid_C_PrevAndNext(
		Session session, DLFolder dlFolder, String uuid, long companyId,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library folders where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DLFolder dlFolder :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching document library folders
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

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

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
		"dlFolder.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(dlFolder.uuid IS NULL OR dlFolder.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"dlFolder.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the document library folders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching document library folders
	 */
	@Override
	public List<DLFolder> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator, boolean useFinderCache) {

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

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFolder dlFolder : list) {
					if (groupId != dlFolder.getGroupId()) {
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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Returns the first document library folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByGroupId_First(
			long groupId, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByGroupId_First(groupId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByGroupId_First(
		long groupId, OrderByComparator<DLFolder> orderByComparator) {

		List<DLFolder> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByGroupId_Last(
			long groupId, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByGroupId_Last(groupId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByGroupId_Last(
		long groupId, OrderByComparator<DLFolder> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<DLFolder> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] findByGroupId_PrevAndNext(
			long folderId, long groupId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, dlFolder, groupId, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByGroupId_PrevAndNext(
				session, dlFolder, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder getByGroupId_PrevAndNext(
		Session session, DLFolder dlFolder, long groupId,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<DLFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] filterFindByGroupId_PrevAndNext(
			long folderId, long groupId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(
				folderId, groupId, orderByComparator);
		}

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(
				session, dlFolder, groupId, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = filterGetByGroupId_PrevAndNext(
				session, dlFolder, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder filterGetByGroupId_PrevAndNext(
		Session session, DLFolder dlFolder, long groupId,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library folders where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (DLFolder dlFolder :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching document library folders
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	/**
	 * Returns the number of document library folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching document library folders that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"dlFolder.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the document library folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching document library folders
	 */
	@Override
	public List<DLFolder> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator, boolean useFinderCache) {

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

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFolder dlFolder : list) {
					if (companyId != dlFolder.getCompanyId()) {
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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Returns the first document library folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByCompanyId_First(
			long companyId, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByCompanyId_First(
		long companyId, OrderByComparator<DLFolder> orderByComparator) {

		List<DLFolder> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByCompanyId_Last(
			long companyId, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByCompanyId_Last(companyId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByCompanyId_Last(
		long companyId, OrderByComparator<DLFolder> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<DLFolder> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where companyId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] findByCompanyId_PrevAndNext(
			long folderId, long companyId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, dlFolder, companyId, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByCompanyId_PrevAndNext(
				session, dlFolder, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder getByCompanyId_PrevAndNext(
		Session session, DLFolder dlFolder, long companyId,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library folders where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (DLFolder dlFolder :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching document library folders
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

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
		"dlFolder.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByRepositoryId;
	private FinderPath _finderPathWithoutPaginationFindByRepositoryId;
	private FinderPath _finderPathCountByRepositoryId;

	/**
	 * Returns all the document library folders where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @return the matching document library folders
	 */
	@Override
	public List<DLFolder> findByRepositoryId(long repositoryId) {
		return findByRepositoryId(
			repositoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByRepositoryId(
		long repositoryId, int start, int end) {

		return findByRepositoryId(repositoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByRepositoryId(
		long repositoryId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator) {

		return findByRepositoryId(
			repositoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library folders where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByRepositoryId(
		long repositoryId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator, boolean useFinderCache) {

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

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFolder dlFolder : list) {
					if (repositoryId != dlFolder.getRepositoryId()) {
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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Returns the first document library folder in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByRepositoryId_First(
			long repositoryId, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByRepositoryId_First(
			repositoryId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("repositoryId=");
		msg.append(repositoryId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByRepositoryId_First(
		long repositoryId, OrderByComparator<DLFolder> orderByComparator) {

		List<DLFolder> list = findByRepositoryId(
			repositoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByRepositoryId_Last(
			long repositoryId, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByRepositoryId_Last(
			repositoryId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("repositoryId=");
		msg.append(repositoryId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByRepositoryId_Last(
		long repositoryId, OrderByComparator<DLFolder> orderByComparator) {

		int count = countByRepositoryId(repositoryId);

		if (count == 0) {
			return null;
		}

		List<DLFolder> list = findByRepositoryId(
			repositoryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where repositoryId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] findByRepositoryId_PrevAndNext(
			long folderId, long repositoryId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByRepositoryId_PrevAndNext(
				session, dlFolder, repositoryId, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByRepositoryId_PrevAndNext(
				session, dlFolder, repositoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder getByRepositoryId_PrevAndNext(
		Session session, DLFolder dlFolder, long repositoryId,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(repositoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library folders where repositoryId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 */
	@Override
	public void removeByRepositoryId(long repositoryId) {
		for (DLFolder dlFolder :
				findByRepositoryId(
					repositoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @return the number of matching document library folders
	 */
	@Override
	public int countByRepositoryId(long repositoryId) {
		FinderPath finderPath = _finderPathCountByRepositoryId;

		Object[] finderArgs = new Object[] {repositoryId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

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

	private static final String _FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2 =
		"dlFolder.repositoryId = ?";

	private FinderPath _finderPathWithPaginationFindByG_P;
	private FinderPath _finderPathWithoutPaginationFindByG_P;
	private FinderPath _finderPathCountByG_P;

	/**
	 * Returns all the document library folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_P(long groupId, long parentFolderId) {
		return findByG_P(
			groupId, parentFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end) {

		return findByG_P(groupId, parentFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator) {

		return findByG_P(
			groupId, parentFolderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_P;
				finderArgs = new Object[] {groupId, parentFolderId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_P;
			finderArgs = new Object[] {
				groupId, parentFolderId, start, end, orderByComparator
			};
		}

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFolder dlFolder : list) {
					if ((groupId != dlFolder.getGroupId()) ||
						(parentFolderId != dlFolder.getParentFolderId())) {

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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByG_P_First(
			long groupId, long parentFolderId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByG_P_First(
			groupId, parentFolderId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByG_P_First(
		long groupId, long parentFolderId,
		OrderByComparator<DLFolder> orderByComparator) {

		List<DLFolder> list = findByG_P(
			groupId, parentFolderId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByG_P_Last(
			long groupId, long parentFolderId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByG_P_Last(
			groupId, parentFolderId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByG_P_Last(
		long groupId, long parentFolderId,
		OrderByComparator<DLFolder> orderByComparator) {

		int count = countByG_P(groupId, parentFolderId);

		if (count == 0) {
			return null;
		}

		List<DLFolder> list = findByG_P(
			groupId, parentFolderId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] findByG_P_PrevAndNext(
			long folderId, long groupId, long parentFolderId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByG_P_PrevAndNext(
				session, dlFolder, groupId, parentFolderId, orderByComparator,
				true);

			array[1] = dlFolder;

			array[2] = getByG_P_PrevAndNext(
				session, dlFolder, groupId, parentFolderId, orderByComparator,
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

	protected DLFolder getByG_P_PrevAndNext(
		Session session, DLFolder dlFolder, long groupId, long parentFolderId,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentFolderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_P(long groupId, long parentFolderId) {
		return filterFindByG_P(
			groupId, parentFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end) {

		return filterFindByG_P(groupId, parentFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P(
				groupId, parentFolderId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

			return (List<DLFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] filterFindByG_P_PrevAndNext(
			long folderId, long groupId, long parentFolderId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_PrevAndNext(
				folderId, groupId, parentFolderId, orderByComparator);
		}

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = filterGetByG_P_PrevAndNext(
				session, dlFolder, groupId, parentFolderId, orderByComparator,
				true);

			array[1] = dlFolder;

			array[2] = filterGetByG_P_PrevAndNext(
				session, dlFolder, groupId, parentFolderId, orderByComparator,
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

	protected DLFolder filterGetByG_P_PrevAndNext(
		Session session, DLFolder dlFolder, long groupId, long parentFolderId,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentFolderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library folders where groupId = &#63; and parentFolderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 */
	@Override
	public void removeByG_P(long groupId, long parentFolderId) {
		for (DLFolder dlFolder :
				findByG_P(
					groupId, parentFolderId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching document library folders
	 */
	@Override
	public int countByG_P(long groupId, long parentFolderId) {
		FinderPath finderPath = _finderPathCountByG_P;

		Object[] finderArgs = new Object[] {groupId, parentFolderId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

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

	/**
	 * Returns the number of document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching document library folders that the user has permission to view
	 */
	@Override
	public int filterCountByG_P(long groupId, long parentFolderId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P(groupId, parentFolderId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_P_GROUPID_2 =
		"dlFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_PARENTFOLDERID_2 =
		"dlFolder.parentFolderId = ?";

	private FinderPath _finderPathWithPaginationFindByC_NotS;
	private FinderPath _finderPathWithPaginationCountByC_NotS;

	/**
	 * Returns all the document library folders where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching document library folders
	 */
	@Override
	public List<DLFolder> findByC_NotS(long companyId, int status) {
		return findByC_NotS(
			companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByC_NotS(
		long companyId, int status, int start, int end) {

		return findByC_NotS(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByC_NotS(
		long companyId, int status, int start, int end,
		OrderByComparator<DLFolder> orderByComparator) {

		return findByC_NotS(
			companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library folders where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByC_NotS(
		long companyId, int status, int start, int end,
		OrderByComparator<DLFolder> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_NotS;
		finderArgs = new Object[] {
			companyId, status, start, end, orderByComparator
		};

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFolder dlFolder : list) {
					if ((companyId != dlFolder.getCompanyId()) ||
						(status == dlFolder.getStatus())) {

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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_C_NOTS_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Returns the first document library folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByC_NotS_First(
			long companyId, int status,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByC_NotS_First(
			companyId, status, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status!=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByC_NotS_First(
		long companyId, int status,
		OrderByComparator<DLFolder> orderByComparator) {

		List<DLFolder> list = findByC_NotS(
			companyId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByC_NotS_Last(
			long companyId, int status,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByC_NotS_Last(
			companyId, status, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status!=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByC_NotS_Last(
		long companyId, int status,
		OrderByComparator<DLFolder> orderByComparator) {

		int count = countByC_NotS(companyId, status);

		if (count == 0) {
			return null;
		}

		List<DLFolder> list = findByC_NotS(
			companyId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] findByC_NotS_PrevAndNext(
			long folderId, long companyId, int status,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByC_NotS_PrevAndNext(
				session, dlFolder, companyId, status, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByC_NotS_PrevAndNext(
				session, dlFolder, companyId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder getByC_NotS_PrevAndNext(
		Session session, DLFolder dlFolder, long companyId, int status,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library folders where companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_NotS(long companyId, int status) {
		for (DLFolder dlFolder :
				findByC_NotS(
					companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching document library folders
	 */
	@Override
	public int countByC_NotS(long companyId, int status) {
		FinderPath finderPath = _finderPathWithPaginationCountByC_NotS;

		Object[] finderArgs = new Object[] {companyId, status};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

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
		"dlFolder.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_NOTS_STATUS_2 =
		"dlFolder.status != ?";

	private FinderPath _finderPathFetchByR_M;
	private FinderPath _finderPathCountByR_M;

	/**
	 * Returns the document library folder where repositoryId = &#63; and mountPoint = &#63; or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param repositoryId the repository ID
	 * @param mountPoint the mount point
	 * @return the matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByR_M(long repositoryId, boolean mountPoint)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByR_M(repositoryId, mountPoint);

		if (dlFolder == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("repositoryId=");
			msg.append(repositoryId);

			msg.append(", mountPoint=");
			msg.append(mountPoint);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return dlFolder;
	}

	/**
	 * Returns the document library folder where repositoryId = &#63; and mountPoint = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param repositoryId the repository ID
	 * @param mountPoint the mount point
	 * @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByR_M(long repositoryId, boolean mountPoint) {
		return fetchByR_M(repositoryId, mountPoint, true);
	}

	/**
	 * Returns the document library folder where repositoryId = &#63; and mountPoint = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param repositoryId the repository ID
	 * @param mountPoint the mount point
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByR_M(
		long repositoryId, boolean mountPoint, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {repositoryId, mountPoint};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByR_M, finderArgs, this);
		}

		if (result instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)result;

			if ((repositoryId != dlFolder.getRepositoryId()) ||
				(mountPoint != dlFolder.isMountPoint())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_R_M_REPOSITORYID_2);

			query.append(_FINDER_COLUMN_R_M_MOUNTPOINT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				qPos.add(mountPoint);

				List<DLFolder> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByR_M, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									repositoryId, mountPoint
								};
							}

							_log.warn(
								"DLFolderPersistenceImpl.fetchByR_M(long, boolean, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					DLFolder dlFolder = list.get(0);

					result = dlFolder;

					cacheResult(dlFolder);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByR_M, finderArgs);
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
			return (DLFolder)result;
		}
	}

	/**
	 * Removes the document library folder where repositoryId = &#63; and mountPoint = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param mountPoint the mount point
	 * @return the document library folder that was removed
	 */
	@Override
	public DLFolder removeByR_M(long repositoryId, boolean mountPoint)
		throws NoSuchFolderException {

		DLFolder dlFolder = findByR_M(repositoryId, mountPoint);

		return remove(dlFolder);
	}

	/**
	 * Returns the number of document library folders where repositoryId = &#63; and mountPoint = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param mountPoint the mount point
	 * @return the number of matching document library folders
	 */
	@Override
	public int countByR_M(long repositoryId, boolean mountPoint) {
		FinderPath finderPath = _finderPathCountByR_M;

		Object[] finderArgs = new Object[] {repositoryId, mountPoint};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_R_M_REPOSITORYID_2);

			query.append(_FINDER_COLUMN_R_M_MOUNTPOINT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				qPos.add(mountPoint);

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

	private static final String _FINDER_COLUMN_R_M_REPOSITORYID_2 =
		"dlFolder.repositoryId = ? AND ";

	private static final String _FINDER_COLUMN_R_M_MOUNTPOINT_2 =
		"dlFolder.mountPoint = ?";

	private FinderPath _finderPathWithPaginationFindByR_P;
	private FinderPath _finderPathWithoutPaginationFindByR_P;
	private FinderPath _finderPathCountByR_P;

	/**
	 * Returns all the document library folders where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching document library folders
	 */
	@Override
	public List<DLFolder> findByR_P(long repositoryId, long parentFolderId) {
		return findByR_P(
			repositoryId, parentFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library folders where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByR_P(
		long repositoryId, long parentFolderId, int start, int end) {

		return findByR_P(repositoryId, parentFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByR_P(
		long repositoryId, long parentFolderId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator) {

		return findByR_P(
			repositoryId, parentFolderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library folders where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByR_P(
		long repositoryId, long parentFolderId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByR_P;
				finderArgs = new Object[] {repositoryId, parentFolderId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByR_P;
			finderArgs = new Object[] {
				repositoryId, parentFolderId, start, end, orderByComparator
			};
		}

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFolder dlFolder : list) {
					if ((repositoryId != dlFolder.getRepositoryId()) ||
						(parentFolderId != dlFolder.getParentFolderId())) {

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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_R_P_REPOSITORYID_2);

			query.append(_FINDER_COLUMN_R_P_PARENTFOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				qPos.add(parentFolderId);

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Returns the first document library folder in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByR_P_First(
			long repositoryId, long parentFolderId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByR_P_First(
			repositoryId, parentFolderId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("repositoryId=");
		msg.append(repositoryId);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByR_P_First(
		long repositoryId, long parentFolderId,
		OrderByComparator<DLFolder> orderByComparator) {

		List<DLFolder> list = findByR_P(
			repositoryId, parentFolderId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByR_P_Last(
			long repositoryId, long parentFolderId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByR_P_Last(
			repositoryId, parentFolderId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("repositoryId=");
		msg.append(repositoryId);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByR_P_Last(
		long repositoryId, long parentFolderId,
		OrderByComparator<DLFolder> orderByComparator) {

		int count = countByR_P(repositoryId, parentFolderId);

		if (count == 0) {
			return null;
		}

		List<DLFolder> list = findByR_P(
			repositoryId, parentFolderId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] findByR_P_PrevAndNext(
			long folderId, long repositoryId, long parentFolderId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByR_P_PrevAndNext(
				session, dlFolder, repositoryId, parentFolderId,
				orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByR_P_PrevAndNext(
				session, dlFolder, repositoryId, parentFolderId,
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

	protected DLFolder getByR_P_PrevAndNext(
		Session session, DLFolder dlFolder, long repositoryId,
		long parentFolderId, OrderByComparator<DLFolder> orderByComparator,
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

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_R_P_REPOSITORYID_2);

		query.append(_FINDER_COLUMN_R_P_PARENTFOLDERID_2);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(repositoryId);

		qPos.add(parentFolderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library folders where repositoryId = &#63; and parentFolderId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 */
	@Override
	public void removeByR_P(long repositoryId, long parentFolderId) {
		for (DLFolder dlFolder :
				findByR_P(
					repositoryId, parentFolderId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching document library folders
	 */
	@Override
	public int countByR_P(long repositoryId, long parentFolderId) {
		FinderPath finderPath = _finderPathCountByR_P;

		Object[] finderArgs = new Object[] {repositoryId, parentFolderId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_R_P_REPOSITORYID_2);

			query.append(_FINDER_COLUMN_R_P_PARENTFOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				qPos.add(parentFolderId);

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

	private static final String _FINDER_COLUMN_R_P_REPOSITORYID_2 =
		"dlFolder.repositoryId = ? AND ";

	private static final String _FINDER_COLUMN_R_P_PARENTFOLDERID_2 =
		"dlFolder.parentFolderId = ?";

	private FinderPath _finderPathWithPaginationFindByP_N;
	private FinderPath _finderPathWithoutPaginationFindByP_N;
	private FinderPath _finderPathCountByP_N;

	/**
	 * Returns all the document library folders where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the matching document library folders
	 */
	@Override
	public List<DLFolder> findByP_N(long parentFolderId, String name) {
		return findByP_N(
			parentFolderId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where parentFolderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByP_N(
		long parentFolderId, String name, int start, int end) {

		return findByP_N(parentFolderId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where parentFolderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByP_N(
		long parentFolderId, String name, int start, int end,
		OrderByComparator<DLFolder> orderByComparator) {

		return findByP_N(
			parentFolderId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library folders where parentFolderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByP_N(
		long parentFolderId, String name, int start, int end,
		OrderByComparator<DLFolder> orderByComparator, boolean useFinderCache) {

		name = Objects.toString(name, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByP_N;
				finderArgs = new Object[] {parentFolderId, name};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByP_N;
			finderArgs = new Object[] {
				parentFolderId, name, start, end, orderByComparator
			};
		}

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFolder dlFolder : list) {
					if ((parentFolderId != dlFolder.getParentFolderId()) ||
						!name.equals(dlFolder.getName())) {

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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_P_N_PARENTFOLDERID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_P_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_P_N_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentFolderId);

				if (bindName) {
					qPos.add(name);
				}

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Returns the first document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByP_N_First(
			long parentFolderId, String name,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByP_N_First(
			parentFolderId, name, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByP_N_First(
		long parentFolderId, String name,
		OrderByComparator<DLFolder> orderByComparator) {

		List<DLFolder> list = findByP_N(
			parentFolderId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByP_N_Last(
			long parentFolderId, String name,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByP_N_Last(
			parentFolderId, name, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByP_N_Last(
		long parentFolderId, String name,
		OrderByComparator<DLFolder> orderByComparator) {

		int count = countByP_N(parentFolderId, name);

		if (count == 0) {
			return null;
		}

		List<DLFolder> list = findByP_N(
			parentFolderId, name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] findByP_N_PrevAndNext(
			long folderId, long parentFolderId, String name,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		name = Objects.toString(name, "");

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByP_N_PrevAndNext(
				session, dlFolder, parentFolderId, name, orderByComparator,
				true);

			array[1] = dlFolder;

			array[2] = getByP_N_PrevAndNext(
				session, dlFolder, parentFolderId, name, orderByComparator,
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

	protected DLFolder getByP_N_PrevAndNext(
		Session session, DLFolder dlFolder, long parentFolderId, String name,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_P_N_PARENTFOLDERID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			query.append(_FINDER_COLUMN_P_N_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_P_N_NAME_2);
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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(parentFolderId);

		if (bindName) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library folders where parentFolderId = &#63; and name = &#63; from the database.
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 */
	@Override
	public void removeByP_N(long parentFolderId, String name) {
		for (DLFolder dlFolder :
				findByP_N(
					parentFolderId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where parentFolderId = &#63; and name = &#63;.
	 *
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the number of matching document library folders
	 */
	@Override
	public int countByP_N(long parentFolderId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByP_N;

		Object[] finderArgs = new Object[] {parentFolderId, name};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_P_N_PARENTFOLDERID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_P_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_P_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentFolderId);

				if (bindName) {
					qPos.add(name);
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

	private static final String _FINDER_COLUMN_P_N_PARENTFOLDERID_2 =
		"dlFolder.parentFolderId = ? AND ";

	private static final String _FINDER_COLUMN_P_N_NAME_2 = "dlFolder.name = ?";

	private static final String _FINDER_COLUMN_P_N_NAME_3 =
		"(dlFolder.name IS NULL OR dlFolder.name = '')";

	private FinderPath _finderPathWithPaginationFindByG_M_P;
	private FinderPath _finderPathWithoutPaginationFindByG_M_P;
	private FinderPath _finderPathCountByG_M_P;

	/**
	 * Returns all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @return the matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_P(
		long groupId, boolean mountPoint, long parentFolderId) {

		return findByG_M_P(
			groupId, mountPoint, parentFolderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_P(
		long groupId, boolean mountPoint, long parentFolderId, int start,
		int end) {

		return findByG_M_P(
			groupId, mountPoint, parentFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_P(
		long groupId, boolean mountPoint, long parentFolderId, int start,
		int end, OrderByComparator<DLFolder> orderByComparator) {

		return findByG_M_P(
			groupId, mountPoint, parentFolderId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_P(
		long groupId, boolean mountPoint, long parentFolderId, int start,
		int end, OrderByComparator<DLFolder> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_M_P;
				finderArgs = new Object[] {groupId, mountPoint, parentFolderId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_M_P;
			finderArgs = new Object[] {
				groupId, mountPoint, parentFolderId, start, end,
				orderByComparator
			};
		}

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFolder dlFolder : list) {
					if ((groupId != dlFolder.getGroupId()) ||
						(mountPoint != dlFolder.isMountPoint()) ||
						(parentFolderId != dlFolder.getParentFolderId())) {

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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_M_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_P_MOUNTPOINT_2);

			query.append(_FINDER_COLUMN_G_M_P_PARENTFOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(mountPoint);

				qPos.add(parentFolderId);

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByG_M_P_First(
			long groupId, boolean mountPoint, long parentFolderId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByG_M_P_First(
			groupId, mountPoint, parentFolderId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", mountPoint=");
		msg.append(mountPoint);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByG_M_P_First(
		long groupId, boolean mountPoint, long parentFolderId,
		OrderByComparator<DLFolder> orderByComparator) {

		List<DLFolder> list = findByG_M_P(
			groupId, mountPoint, parentFolderId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByG_M_P_Last(
			long groupId, boolean mountPoint, long parentFolderId,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByG_M_P_Last(
			groupId, mountPoint, parentFolderId, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", mountPoint=");
		msg.append(mountPoint);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByG_M_P_Last(
		long groupId, boolean mountPoint, long parentFolderId,
		OrderByComparator<DLFolder> orderByComparator) {

		int count = countByG_M_P(groupId, mountPoint, parentFolderId);

		if (count == 0) {
			return null;
		}

		List<DLFolder> list = findByG_M_P(
			groupId, mountPoint, parentFolderId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] findByG_M_P_PrevAndNext(
			long folderId, long groupId, boolean mountPoint,
			long parentFolderId, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByG_M_P_PrevAndNext(
				session, dlFolder, groupId, mountPoint, parentFolderId,
				orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByG_M_P_PrevAndNext(
				session, dlFolder, groupId, mountPoint, parentFolderId,
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

	protected DLFolder getByG_M_P_PrevAndNext(
		Session session, DLFolder dlFolder, long groupId, boolean mountPoint,
		long parentFolderId, OrderByComparator<DLFolder> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_M_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_PARENTFOLDERID_2);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(mountPoint);

		qPos.add(parentFolderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @return the matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_M_P(
		long groupId, boolean mountPoint, long parentFolderId) {

		return filterFindByG_M_P(
			groupId, mountPoint, parentFolderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_M_P(
		long groupId, boolean mountPoint, long parentFolderId, int start,
		int end) {

		return filterFindByG_M_P(
			groupId, mountPoint, parentFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_M_P(
		long groupId, boolean mountPoint, long parentFolderId, int start,
		int end, OrderByComparator<DLFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_M_P(
				groupId, mountPoint, parentFolderId, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_PARENTFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(mountPoint);

			qPos.add(parentFolderId);

			return (List<DLFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] filterFindByG_M_P_PrevAndNext(
			long folderId, long groupId, boolean mountPoint,
			long parentFolderId, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_M_P_PrevAndNext(
				folderId, groupId, mountPoint, parentFolderId,
				orderByComparator);
		}

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = filterGetByG_M_P_PrevAndNext(
				session, dlFolder, groupId, mountPoint, parentFolderId,
				orderByComparator, true);

			array[1] = dlFolder;

			array[2] = filterGetByG_M_P_PrevAndNext(
				session, dlFolder, groupId, mountPoint, parentFolderId,
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

	protected DLFolder filterGetByG_M_P_PrevAndNext(
		Session session, DLFolder dlFolder, long groupId, boolean mountPoint,
		long parentFolderId, OrderByComparator<DLFolder> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_PARENTFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(mountPoint);

		qPos.add(parentFolderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 */
	@Override
	public void removeByG_M_P(
		long groupId, boolean mountPoint, long parentFolderId) {

		for (DLFolder dlFolder :
				findByG_M_P(
					groupId, mountPoint, parentFolderId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching document library folders
	 */
	@Override
	public int countByG_M_P(
		long groupId, boolean mountPoint, long parentFolderId) {

		FinderPath finderPath = _finderPathCountByG_M_P;

		Object[] finderArgs = new Object[] {
			groupId, mountPoint, parentFolderId
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_M_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_P_MOUNTPOINT_2);

			query.append(_FINDER_COLUMN_G_M_P_PARENTFOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(mountPoint);

				qPos.add(parentFolderId);

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

	/**
	 * Returns the number of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching document library folders that the user has permission to view
	 */
	@Override
	public int filterCountByG_M_P(
		long groupId, boolean mountPoint, long parentFolderId) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_M_P(groupId, mountPoint, parentFolderId);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_M_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_PARENTFOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(mountPoint);

			qPos.add(parentFolderId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_M_P_GROUPID_2 =
		"dlFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_M_P_MOUNTPOINT_2 =
		"dlFolder.mountPoint = ? AND ";

	private static final String _FINDER_COLUMN_G_M_P_PARENTFOLDERID_2 =
		"dlFolder.parentFolderId = ?";

	private FinderPath _finderPathFetchByG_P_N;
	private FinderPath _finderPathCountByG_P_N;

	/**
	 * Returns the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByG_P_N(long groupId, long parentFolderId, String name)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByG_P_N(groupId, parentFolderId, name);

		if (dlFolder == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", parentFolderId=");
			msg.append(parentFolderId);

			msg.append(", name=");
			msg.append(name);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return dlFolder;
	}

	/**
	 * Returns the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByG_P_N(
		long groupId, long parentFolderId, String name) {

		return fetchByG_P_N(groupId, parentFolderId, name, true);
	}

	/**
	 * Returns the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByG_P_N(
		long groupId, long parentFolderId, String name,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, parentFolderId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByG_P_N, finderArgs, this);
		}

		if (result instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)result;

			if ((groupId != dlFolder.getGroupId()) ||
				(parentFolderId != dlFolder.getParentFolderId()) ||
				!Objects.equals(name, dlFolder.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_N_PARENTFOLDERID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_G_P_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_P_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				if (bindName) {
					qPos.add(name);
				}

				List<DLFolder> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByG_P_N, finderArgs, list);
					}
				}
				else {
					DLFolder dlFolder = list.get(0);

					result = dlFolder;

					cacheResult(dlFolder);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByG_P_N, finderArgs);
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
			return (DLFolder)result;
		}
	}

	/**
	 * Removes the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the document library folder that was removed
	 */
	@Override
	public DLFolder removeByG_P_N(
			long groupId, long parentFolderId, String name)
		throws NoSuchFolderException {

		DLFolder dlFolder = findByG_P_N(groupId, parentFolderId, name);

		return remove(dlFolder);
	}

	/**
	 * Returns the number of document library folders where groupId = &#63; and parentFolderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the number of matching document library folders
	 */
	@Override
	public int countByG_P_N(long groupId, long parentFolderId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByG_P_N;

		Object[] finderArgs = new Object[] {groupId, parentFolderId, name};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_N_PARENTFOLDERID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_G_P_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_P_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				if (bindName) {
					qPos.add(name);
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

	private static final String _FINDER_COLUMN_G_P_N_GROUPID_2 =
		"dlFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_N_PARENTFOLDERID_2 =
		"dlFolder.parentFolderId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_N_NAME_2 =
		"dlFolder.name = ?";

	private static final String _FINDER_COLUMN_G_P_N_NAME_3 =
		"(dlFolder.name IS NULL OR dlFolder.name = '')";

	private FinderPath _finderPathWithPaginationFindByF_C_P_NotS;
	private FinderPath _finderPathWithPaginationCountByF_C_P_NotS;

	/**
	 * Returns all the document library folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the matching document library folders
	 */
	@Override
	public List<DLFolder> findByF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status) {

		return findByF_C_P_NotS(
			folderId, companyId, parentFolderId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status,
		int start, int end) {

		return findByF_C_P_NotS(
			folderId, companyId, parentFolderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status,
		int start, int end, OrderByComparator<DLFolder> orderByComparator) {

		return findByF_C_P_NotS(
			folderId, companyId, parentFolderId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status,
		int start, int end, OrderByComparator<DLFolder> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByF_C_P_NotS;
		finderArgs = new Object[] {
			folderId, companyId, parentFolderId, status, start, end,
			orderByComparator
		};

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFolder dlFolder : list) {
					if ((folderId >= dlFolder.getFolderId()) ||
						(companyId != dlFolder.getCompanyId()) ||
						(parentFolderId != dlFolder.getParentFolderId()) ||
						(status == dlFolder.getStatus())) {

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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_F_C_P_NOTS_FOLDERID_2);

			query.append(_FINDER_COLUMN_F_C_P_NOTS_COMPANYID_2);

			query.append(_FINDER_COLUMN_F_C_P_NOTS_PARENTFOLDERID_2);

			query.append(_FINDER_COLUMN_F_C_P_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				qPos.add(companyId);

				qPos.add(parentFolderId);

				qPos.add(status);

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Returns the first document library folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByF_C_P_NotS_First(
			long folderId, long companyId, long parentFolderId, int status,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByF_C_P_NotS_First(
			folderId, companyId, parentFolderId, status, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("folderId>");
		msg.append(folderId);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", status!=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByF_C_P_NotS_First(
		long folderId, long companyId, long parentFolderId, int status,
		OrderByComparator<DLFolder> orderByComparator) {

		List<DLFolder> list = findByF_C_P_NotS(
			folderId, companyId, parentFolderId, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByF_C_P_NotS_Last(
			long folderId, long companyId, long parentFolderId, int status,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByF_C_P_NotS_Last(
			folderId, companyId, parentFolderId, status, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("folderId>");
		msg.append(folderId);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", status!=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByF_C_P_NotS_Last(
		long folderId, long companyId, long parentFolderId, int status,
		OrderByComparator<DLFolder> orderByComparator) {

		int count = countByF_C_P_NotS(
			folderId, companyId, parentFolderId, status);

		if (count == 0) {
			return null;
		}

		List<DLFolder> list = findByF_C_P_NotS(
			folderId, companyId, parentFolderId, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the document library folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 */
	@Override
	public void removeByF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status) {

		for (DLFolder dlFolder :
				findByF_C_P_NotS(
					folderId, companyId, parentFolderId, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the number of matching document library folders
	 */
	@Override
	public int countByF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status) {

		FinderPath finderPath = _finderPathWithPaginationCountByF_C_P_NotS;

		Object[] finderArgs = new Object[] {
			folderId, companyId, parentFolderId, status
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_F_C_P_NOTS_FOLDERID_2);

			query.append(_FINDER_COLUMN_F_C_P_NOTS_COMPANYID_2);

			query.append(_FINDER_COLUMN_F_C_P_NOTS_PARENTFOLDERID_2);

			query.append(_FINDER_COLUMN_F_C_P_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				qPos.add(companyId);

				qPos.add(parentFolderId);

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

	private static final String _FINDER_COLUMN_F_C_P_NOTS_FOLDERID_2 =
		"dlFolder.folderId > ? AND ";

	private static final String _FINDER_COLUMN_F_C_P_NOTS_COMPANYID_2 =
		"dlFolder.companyId = ? AND ";

	private static final String _FINDER_COLUMN_F_C_P_NOTS_PARENTFOLDERID_2 =
		"dlFolder.parentFolderId = ? AND ";

	private static final String _FINDER_COLUMN_F_C_P_NOTS_STATUS_2 =
		"dlFolder.status != ?";

	private FinderPath _finderPathWithPaginationFindByG_M_P_H;
	private FinderPath _finderPathWithoutPaginationFindByG_M_P_H;
	private FinderPath _finderPathCountByG_M_P_H;

	/**
	 * Returns all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @return the matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden) {

		return findByG_M_P_H(
			groupId, mountPoint, parentFolderId, hidden, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int start, int end) {

		return findByG_M_P_H(
			groupId, mountPoint, parentFolderId, hidden, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int start, int end, OrderByComparator<DLFolder> orderByComparator) {

		return findByG_M_P_H(
			groupId, mountPoint, parentFolderId, hidden, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int start, int end, OrderByComparator<DLFolder> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_M_P_H;
				finderArgs = new Object[] {
					groupId, mountPoint, parentFolderId, hidden
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_M_P_H;
			finderArgs = new Object[] {
				groupId, mountPoint, parentFolderId, hidden, start, end,
				orderByComparator
			};
		}

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFolder dlFolder : list) {
					if ((groupId != dlFolder.getGroupId()) ||
						(mountPoint != dlFolder.isMountPoint()) ||
						(parentFolderId != dlFolder.getParentFolderId()) ||
						(hidden != dlFolder.isHidden())) {

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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_M_P_H_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_P_H_MOUNTPOINT_2);

			query.append(_FINDER_COLUMN_G_M_P_H_PARENTFOLDERID_2);

			query.append(_FINDER_COLUMN_G_M_P_H_HIDDEN_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(mountPoint);

				qPos.add(parentFolderId);

				qPos.add(hidden);

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByG_M_P_H_First(
			long groupId, boolean mountPoint, long parentFolderId,
			boolean hidden, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByG_M_P_H_First(
			groupId, mountPoint, parentFolderId, hidden, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", mountPoint=");
		msg.append(mountPoint);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", hidden=");
		msg.append(hidden);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByG_M_P_H_First(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		OrderByComparator<DLFolder> orderByComparator) {

		List<DLFolder> list = findByG_M_P_H(
			groupId, mountPoint, parentFolderId, hidden, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByG_M_P_H_Last(
			long groupId, boolean mountPoint, long parentFolderId,
			boolean hidden, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByG_M_P_H_Last(
			groupId, mountPoint, parentFolderId, hidden, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", mountPoint=");
		msg.append(mountPoint);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", hidden=");
		msg.append(hidden);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByG_M_P_H_Last(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		OrderByComparator<DLFolder> orderByComparator) {

		int count = countByG_M_P_H(groupId, mountPoint, parentFolderId, hidden);

		if (count == 0) {
			return null;
		}

		List<DLFolder> list = findByG_M_P_H(
			groupId, mountPoint, parentFolderId, hidden, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] findByG_M_P_H_PrevAndNext(
			long folderId, long groupId, boolean mountPoint,
			long parentFolderId, boolean hidden,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByG_M_P_H_PrevAndNext(
				session, dlFolder, groupId, mountPoint, parentFolderId, hidden,
				orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByG_M_P_H_PrevAndNext(
				session, dlFolder, groupId, mountPoint, parentFolderId, hidden,
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

	protected DLFolder getByG_M_P_H_PrevAndNext(
		Session session, DLFolder dlFolder, long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_M_P_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_H_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_HIDDEN_2);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(mountPoint);

		qPos.add(parentFolderId);

		qPos.add(hidden);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @return the matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden) {

		return filterFindByG_M_P_H(
			groupId, mountPoint, parentFolderId, hidden, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int start, int end) {

		return filterFindByG_M_P_H(
			groupId, mountPoint, parentFolderId, hidden, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int start, int end, OrderByComparator<DLFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_M_P_H(
				groupId, mountPoint, parentFolderId, hidden, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_P_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_H_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_HIDDEN_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(mountPoint);

			qPos.add(parentFolderId);

			qPos.add(hidden);

			return (List<DLFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] filterFindByG_M_P_H_PrevAndNext(
			long folderId, long groupId, boolean mountPoint,
			long parentFolderId, boolean hidden,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_M_P_H_PrevAndNext(
				folderId, groupId, mountPoint, parentFolderId, hidden,
				orderByComparator);
		}

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = filterGetByG_M_P_H_PrevAndNext(
				session, dlFolder, groupId, mountPoint, parentFolderId, hidden,
				orderByComparator, true);

			array[1] = dlFolder;

			array[2] = filterGetByG_M_P_H_PrevAndNext(
				session, dlFolder, groupId, mountPoint, parentFolderId, hidden,
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

	protected DLFolder filterGetByG_M_P_H_PrevAndNext(
		Session session, DLFolder dlFolder, long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_P_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_H_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_HIDDEN_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(mountPoint);

		qPos.add(parentFolderId);

		qPos.add(hidden);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 */
	@Override
	public void removeByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden) {

		for (DLFolder dlFolder :
				findByG_M_P_H(
					groupId, mountPoint, parentFolderId, hidden,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @return the number of matching document library folders
	 */
	@Override
	public int countByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden) {

		FinderPath finderPath = _finderPathCountByG_M_P_H;

		Object[] finderArgs = new Object[] {
			groupId, mountPoint, parentFolderId, hidden
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_M_P_H_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_P_H_MOUNTPOINT_2);

			query.append(_FINDER_COLUMN_G_M_P_H_PARENTFOLDERID_2);

			query.append(_FINDER_COLUMN_G_M_P_H_HIDDEN_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(mountPoint);

				qPos.add(parentFolderId);

				qPos.add(hidden);

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

	/**
	 * Returns the number of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @return the number of matching document library folders that the user has permission to view
	 */
	@Override
	public int filterCountByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_M_P_H(groupId, mountPoint, parentFolderId, hidden);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_M_P_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_H_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_HIDDEN_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(mountPoint);

			qPos.add(parentFolderId);

			qPos.add(hidden);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_M_P_H_GROUPID_2 =
		"dlFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_M_P_H_MOUNTPOINT_2 =
		"dlFolder.mountPoint = ? AND ";

	private static final String _FINDER_COLUMN_G_M_P_H_PARENTFOLDERID_2 =
		"dlFolder.parentFolderId = ? AND ";

	private static final String _FINDER_COLUMN_G_M_P_H_HIDDEN_2 =
		"dlFolder.hidden = ?";

	private static final String _FINDER_COLUMN_G_M_P_H_HIDDEN_2_SQL =
		"dlFolder.hidden_ = ?";

	private FinderPath _finderPathWithPaginationFindByG_M_T_H;
	private FinderPath _finderPathWithPaginationCountByG_M_T_H;

	/**
	 * Returns all the document library folders where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 * @return the matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_T_H(
		long groupId, boolean mountPoint, String treePath, boolean hidden) {

		return findByG_M_T_H(
			groupId, mountPoint, treePath, hidden, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_T_H(
		long groupId, boolean mountPoint, String treePath, boolean hidden,
		int start, int end) {

		return findByG_M_T_H(
			groupId, mountPoint, treePath, hidden, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_T_H(
		long groupId, boolean mountPoint, String treePath, boolean hidden,
		int start, int end, OrderByComparator<DLFolder> orderByComparator) {

		return findByG_M_T_H(
			groupId, mountPoint, treePath, hidden, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_T_H(
		long groupId, boolean mountPoint, String treePath, boolean hidden,
		int start, int end, OrderByComparator<DLFolder> orderByComparator,
		boolean useFinderCache) {

		treePath = Objects.toString(treePath, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_M_T_H;
		finderArgs = new Object[] {
			groupId, mountPoint, treePath, hidden, start, end, orderByComparator
		};

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFolder dlFolder : list) {
					if ((groupId != dlFolder.getGroupId()) ||
						(mountPoint != dlFolder.isMountPoint()) ||
						!StringUtil.wildcardMatches(
							dlFolder.getTreePath(), treePath, '_', '%', '\\',
							true) ||
						(hidden != dlFolder.isHidden())) {

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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_M_T_H_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_T_H_MOUNTPOINT_2);

			boolean bindTreePath = false;

			if (treePath.isEmpty()) {
				query.append(_FINDER_COLUMN_G_M_T_H_TREEPATH_3);
			}
			else {
				bindTreePath = true;

				query.append(_FINDER_COLUMN_G_M_T_H_TREEPATH_2);
			}

			query.append(_FINDER_COLUMN_G_M_T_H_HIDDEN_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(mountPoint);

				if (bindTreePath) {
					qPos.add(treePath);
				}

				qPos.add(hidden);

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByG_M_T_H_First(
			long groupId, boolean mountPoint, String treePath, boolean hidden,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByG_M_T_H_First(
			groupId, mountPoint, treePath, hidden, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", mountPoint=");
		msg.append(mountPoint);

		msg.append(", treePathLIKE");
		msg.append(treePath);

		msg.append(", hidden=");
		msg.append(hidden);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByG_M_T_H_First(
		long groupId, boolean mountPoint, String treePath, boolean hidden,
		OrderByComparator<DLFolder> orderByComparator) {

		List<DLFolder> list = findByG_M_T_H(
			groupId, mountPoint, treePath, hidden, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByG_M_T_H_Last(
			long groupId, boolean mountPoint, String treePath, boolean hidden,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByG_M_T_H_Last(
			groupId, mountPoint, treePath, hidden, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", mountPoint=");
		msg.append(mountPoint);

		msg.append(", treePathLIKE");
		msg.append(treePath);

		msg.append(", hidden=");
		msg.append(hidden);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByG_M_T_H_Last(
		long groupId, boolean mountPoint, String treePath, boolean hidden,
		OrderByComparator<DLFolder> orderByComparator) {

		int count = countByG_M_T_H(groupId, mountPoint, treePath, hidden);

		if (count == 0) {
			return null;
		}

		List<DLFolder> list = findByG_M_T_H(
			groupId, mountPoint, treePath, hidden, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] findByG_M_T_H_PrevAndNext(
			long folderId, long groupId, boolean mountPoint, String treePath,
			boolean hidden, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		treePath = Objects.toString(treePath, "");

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByG_M_T_H_PrevAndNext(
				session, dlFolder, groupId, mountPoint, treePath, hidden,
				orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByG_M_T_H_PrevAndNext(
				session, dlFolder, groupId, mountPoint, treePath, hidden,
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

	protected DLFolder getByG_M_T_H_PrevAndNext(
		Session session, DLFolder dlFolder, long groupId, boolean mountPoint,
		String treePath, boolean hidden,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_M_T_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_T_H_MOUNTPOINT_2);

		boolean bindTreePath = false;

		if (treePath.isEmpty()) {
			query.append(_FINDER_COLUMN_G_M_T_H_TREEPATH_3);
		}
		else {
			bindTreePath = true;

			query.append(_FINDER_COLUMN_G_M_T_H_TREEPATH_2);
		}

		query.append(_FINDER_COLUMN_G_M_T_H_HIDDEN_2);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(mountPoint);

		if (bindTreePath) {
			qPos.add(treePath);
		}

		qPos.add(hidden);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 * @return the matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_M_T_H(
		long groupId, boolean mountPoint, String treePath, boolean hidden) {

		return filterFindByG_M_T_H(
			groupId, mountPoint, treePath, hidden, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_M_T_H(
		long groupId, boolean mountPoint, String treePath, boolean hidden,
		int start, int end) {

		return filterFindByG_M_T_H(
			groupId, mountPoint, treePath, hidden, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_M_T_H(
		long groupId, boolean mountPoint, String treePath, boolean hidden,
		int start, int end, OrderByComparator<DLFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_M_T_H(
				groupId, mountPoint, treePath, hidden, start, end,
				orderByComparator);
		}

		treePath = Objects.toString(treePath, "");

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_T_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_T_H_MOUNTPOINT_2);

		boolean bindTreePath = false;

		if (treePath.isEmpty()) {
			query.append(_FINDER_COLUMN_G_M_T_H_TREEPATH_3);
		}
		else {
			bindTreePath = true;

			query.append(_FINDER_COLUMN_G_M_T_H_TREEPATH_2);
		}

		query.append(_FINDER_COLUMN_G_M_T_H_HIDDEN_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(mountPoint);

			if (bindTreePath) {
				qPos.add(treePath);
			}

			qPos.add(hidden);

			return (List<DLFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] filterFindByG_M_T_H_PrevAndNext(
			long folderId, long groupId, boolean mountPoint, String treePath,
			boolean hidden, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_M_T_H_PrevAndNext(
				folderId, groupId, mountPoint, treePath, hidden,
				orderByComparator);
		}

		treePath = Objects.toString(treePath, "");

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = filterGetByG_M_T_H_PrevAndNext(
				session, dlFolder, groupId, mountPoint, treePath, hidden,
				orderByComparator, true);

			array[1] = dlFolder;

			array[2] = filterGetByG_M_T_H_PrevAndNext(
				session, dlFolder, groupId, mountPoint, treePath, hidden,
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

	protected DLFolder filterGetByG_M_T_H_PrevAndNext(
		Session session, DLFolder dlFolder, long groupId, boolean mountPoint,
		String treePath, boolean hidden,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_T_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_T_H_MOUNTPOINT_2);

		boolean bindTreePath = false;

		if (treePath.isEmpty()) {
			query.append(_FINDER_COLUMN_G_M_T_H_TREEPATH_3);
		}
		else {
			bindTreePath = true;

			query.append(_FINDER_COLUMN_G_M_T_H_TREEPATH_2);
		}

		query.append(_FINDER_COLUMN_G_M_T_H_HIDDEN_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(mountPoint);

		if (bindTreePath) {
			qPos.add(treePath);
		}

		qPos.add(hidden);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library folders where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 */
	@Override
	public void removeByG_M_T_H(
		long groupId, boolean mountPoint, String treePath, boolean hidden) {

		for (DLFolder dlFolder :
				findByG_M_T_H(
					groupId, mountPoint, treePath, hidden, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 * @return the number of matching document library folders
	 */
	@Override
	public int countByG_M_T_H(
		long groupId, boolean mountPoint, String treePath, boolean hidden) {

		treePath = Objects.toString(treePath, "");

		FinderPath finderPath = _finderPathWithPaginationCountByG_M_T_H;

		Object[] finderArgs = new Object[] {
			groupId, mountPoint, treePath, hidden
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_M_T_H_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_T_H_MOUNTPOINT_2);

			boolean bindTreePath = false;

			if (treePath.isEmpty()) {
				query.append(_FINDER_COLUMN_G_M_T_H_TREEPATH_3);
			}
			else {
				bindTreePath = true;

				query.append(_FINDER_COLUMN_G_M_T_H_TREEPATH_2);
			}

			query.append(_FINDER_COLUMN_G_M_T_H_HIDDEN_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(mountPoint);

				if (bindTreePath) {
					qPos.add(treePath);
				}

				qPos.add(hidden);

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

	/**
	 * Returns the number of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param treePath the tree path
	 * @param hidden the hidden
	 * @return the number of matching document library folders that the user has permission to view
	 */
	@Override
	public int filterCountByG_M_T_H(
		long groupId, boolean mountPoint, String treePath, boolean hidden) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_M_T_H(groupId, mountPoint, treePath, hidden);
		}

		treePath = Objects.toString(treePath, "");

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_M_T_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_T_H_MOUNTPOINT_2);

		boolean bindTreePath = false;

		if (treePath.isEmpty()) {
			query.append(_FINDER_COLUMN_G_M_T_H_TREEPATH_3);
		}
		else {
			bindTreePath = true;

			query.append(_FINDER_COLUMN_G_M_T_H_TREEPATH_2);
		}

		query.append(_FINDER_COLUMN_G_M_T_H_HIDDEN_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(mountPoint);

			if (bindTreePath) {
				qPos.add(treePath);
			}

			qPos.add(hidden);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_M_T_H_GROUPID_2 =
		"dlFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_M_T_H_MOUNTPOINT_2 =
		"dlFolder.mountPoint = ? AND ";

	private static final String _FINDER_COLUMN_G_M_T_H_TREEPATH_2 =
		"dlFolder.treePath LIKE ? AND ";

	private static final String _FINDER_COLUMN_G_M_T_H_TREEPATH_3 =
		"(dlFolder.treePath IS NULL OR dlFolder.treePath LIKE '') AND ";

	private static final String _FINDER_COLUMN_G_M_T_H_HIDDEN_2 =
		"dlFolder.hidden = ?";

	private static final String _FINDER_COLUMN_G_M_T_H_HIDDEN_2_SQL =
		"dlFolder.hidden_ = ?";

	private FinderPath _finderPathWithPaginationFindByG_P_H_S;
	private FinderPath _finderPathWithoutPaginationFindByG_P_H_S;
	private FinderPath _finderPathCountByG_P_H_S;

	/**
	 * Returns all the document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @return the matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status) {

		return findByG_P_H_S(
			groupId, parentFolderId, hidden, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status,
		int start, int end) {

		return findByG_P_H_S(
			groupId, parentFolderId, hidden, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status,
		int start, int end, OrderByComparator<DLFolder> orderByComparator) {

		return findByG_P_H_S(
			groupId, parentFolderId, hidden, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status,
		int start, int end, OrderByComparator<DLFolder> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_P_H_S;
				finderArgs = new Object[] {
					groupId, parentFolderId, hidden, status
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_P_H_S;
			finderArgs = new Object[] {
				groupId, parentFolderId, hidden, status, start, end,
				orderByComparator
			};
		}

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFolder dlFolder : list) {
					if ((groupId != dlFolder.getGroupId()) ||
						(parentFolderId != dlFolder.getParentFolderId()) ||
						(hidden != dlFolder.isHidden()) ||
						(status != dlFolder.getStatus())) {

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

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_H_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_H_S_PARENTFOLDERID_2);

			query.append(_FINDER_COLUMN_G_P_H_S_HIDDEN_2);

			query.append(_FINDER_COLUMN_G_P_H_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				qPos.add(hidden);

				qPos.add(status);

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByG_P_H_S_First(
			long groupId, long parentFolderId, boolean hidden, int status,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByG_P_H_S_First(
			groupId, parentFolderId, hidden, status, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", hidden=");
		msg.append(hidden);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByG_P_H_S_First(
		long groupId, long parentFolderId, boolean hidden, int status,
		OrderByComparator<DLFolder> orderByComparator) {

		List<DLFolder> list = findByG_P_H_S(
			groupId, parentFolderId, hidden, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByG_P_H_S_Last(
			long groupId, long parentFolderId, boolean hidden, int status,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByG_P_H_S_Last(
			groupId, parentFolderId, hidden, status, orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", hidden=");
		msg.append(hidden);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByG_P_H_S_Last(
		long groupId, long parentFolderId, boolean hidden, int status,
		OrderByComparator<DLFolder> orderByComparator) {

		int count = countByG_P_H_S(groupId, parentFolderId, hidden, status);

		if (count == 0) {
			return null;
		}

		List<DLFolder> list = findByG_P_H_S(
			groupId, parentFolderId, hidden, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] findByG_P_H_S_PrevAndNext(
			long folderId, long groupId, long parentFolderId, boolean hidden,
			int status, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByG_P_H_S_PrevAndNext(
				session, dlFolder, groupId, parentFolderId, hidden, status,
				orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByG_P_H_S_PrevAndNext(
				session, dlFolder, groupId, parentFolderId, hidden, status,
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

	protected DLFolder getByG_P_H_S_PrevAndNext(
		Session session, DLFolder dlFolder, long groupId, long parentFolderId,
		boolean hidden, int status,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_H_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_H_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_P_H_S_HIDDEN_2);

		query.append(_FINDER_COLUMN_G_P_H_S_STATUS_2);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentFolderId);

		qPos.add(hidden);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @return the matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status) {

		return filterFindByG_P_H_S(
			groupId, parentFolderId, hidden, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status,
		int start, int end) {

		return filterFindByG_P_H_S(
			groupId, parentFolderId, hidden, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status,
		int start, int end, OrderByComparator<DLFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_H_S(
				groupId, parentFolderId, hidden, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_H_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_H_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_P_H_S_HIDDEN_2_SQL);

		query.append(_FINDER_COLUMN_G_P_H_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

			qPos.add(hidden);

			qPos.add(status);

			return (List<DLFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] filterFindByG_P_H_S_PrevAndNext(
			long folderId, long groupId, long parentFolderId, boolean hidden,
			int status, OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_H_S_PrevAndNext(
				folderId, groupId, parentFolderId, hidden, status,
				orderByComparator);
		}

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = filterGetByG_P_H_S_PrevAndNext(
				session, dlFolder, groupId, parentFolderId, hidden, status,
				orderByComparator, true);

			array[1] = dlFolder;

			array[2] = filterGetByG_P_H_S_PrevAndNext(
				session, dlFolder, groupId, parentFolderId, hidden, status,
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

	protected DLFolder filterGetByG_P_H_S_PrevAndNext(
		Session session, DLFolder dlFolder, long groupId, long parentFolderId,
		boolean hidden, int status,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_H_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_H_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_P_H_S_HIDDEN_2_SQL);

		query.append(_FINDER_COLUMN_G_P_H_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentFolderId);

		qPos.add(hidden);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 */
	@Override
	public void removeByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status) {

		for (DLFolder dlFolder :
				findByG_P_H_S(
					groupId, parentFolderId, hidden, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @return the number of matching document library folders
	 */
	@Override
	public int countByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status) {

		FinderPath finderPath = _finderPathCountByG_P_H_S;

		Object[] finderArgs = new Object[] {
			groupId, parentFolderId, hidden, status
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_H_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_H_S_PARENTFOLDERID_2);

			query.append(_FINDER_COLUMN_G_P_H_S_HIDDEN_2);

			query.append(_FINDER_COLUMN_G_P_H_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				qPos.add(hidden);

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

	/**
	 * Returns the number of document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @return the number of matching document library folders that the user has permission to view
	 */
	@Override
	public int filterCountByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P_H_S(groupId, parentFolderId, hidden, status);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_H_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_H_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_P_H_S_HIDDEN_2_SQL);

		query.append(_FINDER_COLUMN_G_P_H_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

			qPos.add(hidden);

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_P_H_S_GROUPID_2 =
		"dlFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_H_S_PARENTFOLDERID_2 =
		"dlFolder.parentFolderId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_H_S_HIDDEN_2 =
		"dlFolder.hidden = ? AND ";

	private static final String _FINDER_COLUMN_G_P_H_S_HIDDEN_2_SQL =
		"dlFolder.hidden_ = ? AND ";

	private static final String _FINDER_COLUMN_G_P_H_S_STATUS_2 =
		"dlFolder.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_M_P_H_S;
	private FinderPath _finderPathWithoutPaginationFindByG_M_P_H_S;
	private FinderPath _finderPathCountByG_M_P_H_S;

	/**
	 * Returns all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @return the matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status) {

		return findByG_M_P_H_S(
			groupId, mountPoint, parentFolderId, hidden, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status, int start, int end) {

		return findByG_M_P_H_S(
			groupId, mountPoint, parentFolderId, hidden, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status, int start, int end,
		OrderByComparator<DLFolder> orderByComparator) {

		return findByG_M_P_H_S(
			groupId, mountPoint, parentFolderId, hidden, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library folders
	 */
	@Override
	public List<DLFolder> findByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status, int start, int end,
		OrderByComparator<DLFolder> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_M_P_H_S;
				finderArgs = new Object[] {
					groupId, mountPoint, parentFolderId, hidden, status
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_M_P_H_S;
			finderArgs = new Object[] {
				groupId, mountPoint, parentFolderId, hidden, status, start, end,
				orderByComparator
			};
		}

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFolder dlFolder : list) {
					if ((groupId != dlFolder.getGroupId()) ||
						(mountPoint != dlFolder.isMountPoint()) ||
						(parentFolderId != dlFolder.getParentFolderId()) ||
						(hidden != dlFolder.isHidden()) ||
						(status != dlFolder.getStatus())) {

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
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_M_P_H_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_P_H_S_MOUNTPOINT_2);

			query.append(_FINDER_COLUMN_G_M_P_H_S_PARENTFOLDERID_2);

			query.append(_FINDER_COLUMN_G_M_P_H_S_HIDDEN_2);

			query.append(_FINDER_COLUMN_G_M_P_H_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(mountPoint);

				qPos.add(parentFolderId);

				qPos.add(hidden);

				qPos.add(status);

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByG_M_P_H_S_First(
			long groupId, boolean mountPoint, long parentFolderId,
			boolean hidden, int status,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByG_M_P_H_S_First(
			groupId, mountPoint, parentFolderId, hidden, status,
			orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", mountPoint=");
		msg.append(mountPoint);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", hidden=");
		msg.append(hidden);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByG_M_P_H_S_First(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status, OrderByComparator<DLFolder> orderByComparator) {

		List<DLFolder> list = findByG_M_P_H_S(
			groupId, mountPoint, parentFolderId, hidden, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder
	 * @throws NoSuchFolderException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder findByG_M_P_H_S_Last(
			long groupId, boolean mountPoint, long parentFolderId,
			boolean hidden, int status,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByG_M_P_H_S_Last(
			groupId, mountPoint, parentFolderId, hidden, status,
			orderByComparator);

		if (dlFolder != null) {
			return dlFolder;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", mountPoint=");
		msg.append(mountPoint);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(", hidden=");
		msg.append(hidden);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchByG_M_P_H_S_Last(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status, OrderByComparator<DLFolder> orderByComparator) {

		int count = countByG_M_P_H_S(
			groupId, mountPoint, parentFolderId, hidden, status);

		if (count == 0) {
			return null;
		}

		List<DLFolder> list = findByG_M_P_H_S(
			groupId, mountPoint, parentFolderId, hidden, status, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] findByG_M_P_H_S_PrevAndNext(
			long folderId, long groupId, boolean mountPoint,
			long parentFolderId, boolean hidden, int status,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = getByG_M_P_H_S_PrevAndNext(
				session, dlFolder, groupId, mountPoint, parentFolderId, hidden,
				status, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = getByG_M_P_H_S_PrevAndNext(
				session, dlFolder, groupId, mountPoint, parentFolderId, hidden,
				status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder getByG_M_P_H_S_PrevAndNext(
		Session session, DLFolder dlFolder, long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int status,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		query.append(_SQL_SELECT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_M_P_H_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_S_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_H_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_S_HIDDEN_2);

		query.append(_FINDER_COLUMN_G_M_P_H_S_STATUS_2);

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
			query.append(DLFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(mountPoint);

		qPos.add(parentFolderId);

		qPos.add(hidden);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @return the matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status) {

		return filterFindByG_M_P_H_S(
			groupId, mountPoint, parentFolderId, hidden, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status, int start, int end) {

		return filterFindByG_M_P_H_S(
			groupId, mountPoint, parentFolderId, hidden, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library folders that the user has permission to view
	 */
	@Override
	public List<DLFolder> filterFindByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status, int start, int end,
		OrderByComparator<DLFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_M_P_H_S(
				groupId, mountPoint, parentFolderId, hidden, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(8);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_P_H_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_S_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_H_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_S_HIDDEN_2_SQL);

		query.append(_FINDER_COLUMN_G_M_P_H_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(mountPoint);

			qPos.add(parentFolderId);

			qPos.add(hidden);

			qPos.add(status);

			return (List<DLFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param folderId the primary key of the current document library folder
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder[] filterFindByG_M_P_H_S_PrevAndNext(
			long folderId, long groupId, boolean mountPoint,
			long parentFolderId, boolean hidden, int status,
			OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_M_P_H_S_PrevAndNext(
				folderId, groupId, mountPoint, parentFolderId, hidden, status,
				orderByComparator);
		}

		DLFolder dlFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			DLFolder[] array = new DLFolderImpl[3];

			array[0] = filterGetByG_M_P_H_S_PrevAndNext(
				session, dlFolder, groupId, mountPoint, parentFolderId, hidden,
				status, orderByComparator, true);

			array[1] = dlFolder;

			array[2] = filterGetByG_M_P_H_S_PrevAndNext(
				session, dlFolder, groupId, mountPoint, parentFolderId, hidden,
				status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFolder filterGetByG_M_P_H_S_PrevAndNext(
		Session session, DLFolder dlFolder, long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int status,
		OrderByComparator<DLFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				9 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(8);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_P_H_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_S_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_H_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_S_HIDDEN_2_SQL);

		query.append(_FINDER_COLUMN_G_M_P_H_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(mountPoint);

		qPos.add(parentFolderId);

		qPos.add(hidden);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 */
	@Override
	public void removeByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status) {

		for (DLFolder dlFolder :
				findByG_M_P_H_S(
					groupId, mountPoint, parentFolderId, hidden, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @return the number of matching document library folders
	 */
	@Override
	public int countByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status) {

		FinderPath finderPath = _finderPathCountByG_M_P_H_S;

		Object[] finderArgs = new Object[] {
			groupId, mountPoint, parentFolderId, hidden, status
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_DLFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_M_P_H_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_P_H_S_MOUNTPOINT_2);

			query.append(_FINDER_COLUMN_G_M_P_H_S_PARENTFOLDERID_2);

			query.append(_FINDER_COLUMN_G_M_P_H_S_HIDDEN_2);

			query.append(_FINDER_COLUMN_G_M_P_H_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(mountPoint);

				qPos.add(parentFolderId);

				qPos.add(hidden);

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

	/**
	 * Returns the number of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param mountPoint the mount point
	 * @param parentFolderId the parent folder ID
	 * @param hidden the hidden
	 * @param status the status
	 * @return the number of matching document library folders that the user has permission to view
	 */
	@Override
	public int filterCountByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_M_P_H_S(
				groupId, mountPoint, parentFolderId, hidden, status);
		}

		StringBundler query = new StringBundler(6);

		query.append(_FILTER_SQL_COUNT_DLFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_M_P_H_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_S_MOUNTPOINT_2);

		query.append(_FINDER_COLUMN_G_M_P_H_S_PARENTFOLDERID_2);

		query.append(_FINDER_COLUMN_G_M_P_H_S_HIDDEN_2_SQL);

		query.append(_FINDER_COLUMN_G_M_P_H_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), DLFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(mountPoint);

			qPos.add(parentFolderId);

			qPos.add(hidden);

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_M_P_H_S_GROUPID_2 =
		"dlFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_M_P_H_S_MOUNTPOINT_2 =
		"dlFolder.mountPoint = ? AND ";

	private static final String _FINDER_COLUMN_G_M_P_H_S_PARENTFOLDERID_2 =
		"dlFolder.parentFolderId = ? AND ";

	private static final String _FINDER_COLUMN_G_M_P_H_S_HIDDEN_2 =
		"dlFolder.hidden = ? AND ";

	private static final String _FINDER_COLUMN_G_M_P_H_S_HIDDEN_2_SQL =
		"dlFolder.hidden_ = ? AND ";

	private static final String _FINDER_COLUMN_G_M_P_H_S_STATUS_2 =
		"dlFolder.status = ?";

	public DLFolderPersistenceImpl() {
		setModelClass(DLFolder.class);

		setModelImplClass(DLFolderImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(DLFolderModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("hidden", "hidden_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the document library folder in the entity cache if it is enabled.
	 *
	 * @param dlFolder the document library folder
	 */
	@Override
	public void cacheResult(DLFolder dlFolder) {
		EntityCacheUtil.putResult(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED, DLFolderImpl.class,
			dlFolder.getPrimaryKey(), dlFolder);

		FinderCacheUtil.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {dlFolder.getUuid(), dlFolder.getGroupId()}, dlFolder);

		FinderCacheUtil.putResult(
			_finderPathFetchByR_M,
			new Object[] {dlFolder.getRepositoryId(), dlFolder.isMountPoint()},
			dlFolder);

		FinderCacheUtil.putResult(
			_finderPathFetchByG_P_N,
			new Object[] {
				dlFolder.getGroupId(), dlFolder.getParentFolderId(),
				dlFolder.getName()
			},
			dlFolder);

		dlFolder.resetOriginalValues();
	}

	/**
	 * Caches the document library folders in the entity cache if it is enabled.
	 *
	 * @param dlFolders the document library folders
	 */
	@Override
	public void cacheResult(List<DLFolder> dlFolders) {
		for (DLFolder dlFolder : dlFolders) {
			if (EntityCacheUtil.getResult(
					DLFolderModelImpl.ENTITY_CACHE_ENABLED, DLFolderImpl.class,
					dlFolder.getPrimaryKey()) == null) {

				cacheResult(dlFolder);
			}
			else {
				dlFolder.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all document library folders.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(DLFolderImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the document library folder.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFolder dlFolder) {
		EntityCacheUtil.removeResult(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED, DLFolderImpl.class,
			dlFolder.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DLFolderModelImpl)dlFolder, true);
	}

	@Override
	public void clearCache(List<DLFolder> dlFolders) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFolder dlFolder : dlFolders) {
			EntityCacheUtil.removeResult(
				DLFolderModelImpl.ENTITY_CACHE_ENABLED, DLFolderImpl.class,
				dlFolder.getPrimaryKey());

			clearUniqueFindersCache((DLFolderModelImpl)dlFolder, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				DLFolderModelImpl.ENTITY_CACHE_ENABLED, DLFolderImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DLFolderModelImpl dlFolderModelImpl) {

		Object[] args = new Object[] {
			dlFolderModelImpl.getUuid(), dlFolderModelImpl.getGroupId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByUUID_G, args, dlFolderModelImpl, false);

		args = new Object[] {
			dlFolderModelImpl.getRepositoryId(),
			dlFolderModelImpl.isMountPoint()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByR_M, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByR_M, args, dlFolderModelImpl, false);

		args = new Object[] {
			dlFolderModelImpl.getGroupId(),
			dlFolderModelImpl.getParentFolderId(), dlFolderModelImpl.getName()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByG_P_N, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByG_P_N, args, dlFolderModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DLFolderModelImpl dlFolderModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				dlFolderModelImpl.getUuid(), dlFolderModelImpl.getGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUUID_G, args);
			FinderCacheUtil.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((dlFolderModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				dlFolderModelImpl.getOriginalUuid(),
				dlFolderModelImpl.getOriginalGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUUID_G, args);
			FinderCacheUtil.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				dlFolderModelImpl.getRepositoryId(),
				dlFolderModelImpl.isMountPoint()
			};

			FinderCacheUtil.removeResult(_finderPathCountByR_M, args);
			FinderCacheUtil.removeResult(_finderPathFetchByR_M, args);
		}

		if ((dlFolderModelImpl.getColumnBitmask() &
			 _finderPathFetchByR_M.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				dlFolderModelImpl.getOriginalRepositoryId(),
				dlFolderModelImpl.getOriginalMountPoint()
			};

			FinderCacheUtil.removeResult(_finderPathCountByR_M, args);
			FinderCacheUtil.removeResult(_finderPathFetchByR_M, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				dlFolderModelImpl.getGroupId(),
				dlFolderModelImpl.getParentFolderId(),
				dlFolderModelImpl.getName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_N, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_N, args);
		}

		if ((dlFolderModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_P_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				dlFolderModelImpl.getOriginalGroupId(),
				dlFolderModelImpl.getOriginalParentFolderId(),
				dlFolderModelImpl.getOriginalName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_N, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_N, args);
		}
	}

	/**
	 * Creates a new document library folder with the primary key. Does not add the document library folder to the database.
	 *
	 * @param folderId the primary key for the new document library folder
	 * @return the new document library folder
	 */
	@Override
	public DLFolder create(long folderId) {
		DLFolder dlFolder = new DLFolderImpl();

		dlFolder.setNew(true);
		dlFolder.setPrimaryKey(folderId);

		String uuid = PortalUUIDUtil.generate();

		dlFolder.setUuid(uuid);

		dlFolder.setCompanyId(CompanyThreadLocal.getCompanyId());

		return dlFolder;
	}

	/**
	 * Removes the document library folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param folderId the primary key of the document library folder
	 * @return the document library folder that was removed
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder remove(long folderId) throws NoSuchFolderException {
		return remove((Serializable)folderId);
	}

	/**
	 * Removes the document library folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library folder
	 * @return the document library folder that was removed
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder remove(Serializable primaryKey)
		throws NoSuchFolderException {

		Session session = null;

		try {
			session = openSession();

			DLFolder dlFolder = (DLFolder)session.get(
				DLFolderImpl.class, primaryKey);

			if (dlFolder == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFolderException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(dlFolder);
		}
		catch (NoSuchFolderException nsee) {
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
	protected DLFolder removeImpl(DLFolder dlFolder) {
		dlFolderToDLFileEntryTypeTableMapper.deleteLeftPrimaryKeyTableMappings(
			dlFolder.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlFolder)) {
				dlFolder = (DLFolder)session.get(
					DLFolderImpl.class, dlFolder.getPrimaryKeyObj());
			}

			if (dlFolder != null) {
				session.delete(dlFolder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlFolder != null) {
			clearCache(dlFolder);
		}

		return dlFolder;
	}

	@Override
	public DLFolder updateImpl(DLFolder dlFolder) {
		boolean isNew = dlFolder.isNew();

		if (!(dlFolder instanceof DLFolderModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(dlFolder.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(dlFolder);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in dlFolder proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DLFolder implementation " +
					dlFolder.getClass());
		}

		DLFolderModelImpl dlFolderModelImpl = (DLFolderModelImpl)dlFolder;

		if (Validator.isNull(dlFolder.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFolder.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (dlFolder.getCreateDate() == null)) {
			if (serviceContext == null) {
				dlFolder.setCreateDate(now);
			}
			else {
				dlFolder.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!dlFolderModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				dlFolder.setModifiedDate(now);
			}
			else {
				dlFolder.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (dlFolder.isNew()) {
				session.save(dlFolder);

				dlFolder.setNew(false);
			}
			else {
				dlFolder = (DLFolder)session.merge(dlFolder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!DLFolderModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {dlFolderModelImpl.getUuid()};

			FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				dlFolderModelImpl.getUuid(), dlFolderModelImpl.getCompanyId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {dlFolderModelImpl.getGroupId()};

			FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {dlFolderModelImpl.getCompanyId()};

			FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {dlFolderModelImpl.getRepositoryId()};

			FinderCacheUtil.removeResult(_finderPathCountByRepositoryId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByRepositoryId, args);

			args = new Object[] {
				dlFolderModelImpl.getGroupId(),
				dlFolderModelImpl.getParentFolderId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P, args);

			args = new Object[] {
				dlFolderModelImpl.getRepositoryId(),
				dlFolderModelImpl.getParentFolderId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByR_P, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByR_P, args);

			args = new Object[] {
				dlFolderModelImpl.getParentFolderId(),
				dlFolderModelImpl.getName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByP_N, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByP_N, args);

			args = new Object[] {
				dlFolderModelImpl.getGroupId(),
				dlFolderModelImpl.isMountPoint(),
				dlFolderModelImpl.getParentFolderId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_M_P, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_M_P, args);

			args = new Object[] {
				dlFolderModelImpl.getGroupId(),
				dlFolderModelImpl.isMountPoint(),
				dlFolderModelImpl.getParentFolderId(),
				dlFolderModelImpl.isHidden()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_M_P_H, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_M_P_H, args);

			args = new Object[] {
				dlFolderModelImpl.getGroupId(),
				dlFolderModelImpl.getParentFolderId(),
				dlFolderModelImpl.isHidden(), dlFolderModelImpl.getStatus()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_H_S, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P_H_S, args);

			args = new Object[] {
				dlFolderModelImpl.getGroupId(),
				dlFolderModelImpl.isMountPoint(),
				dlFolderModelImpl.getParentFolderId(),
				dlFolderModelImpl.isHidden(), dlFolderModelImpl.getStatus()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_M_P_H_S, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_M_P_H_S, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((dlFolderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					dlFolderModelImpl.getOriginalUuid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {dlFolderModelImpl.getUuid()};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					dlFolderModelImpl.getOriginalUuid(),
					dlFolderModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					dlFolderModelImpl.getUuid(),
					dlFolderModelImpl.getCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dlFolderModelImpl.getOriginalGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {dlFolderModelImpl.getGroupId()};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dlFolderModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {dlFolderModelImpl.getCompanyId()};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByRepositoryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dlFolderModelImpl.getOriginalRepositoryId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByRepositoryId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByRepositoryId, args);

				args = new Object[] {dlFolderModelImpl.getRepositoryId()};

				FinderCacheUtil.removeResult(
					_finderPathCountByRepositoryId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByRepositoryId, args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					dlFolderModelImpl.getOriginalGroupId(),
					dlFolderModelImpl.getOriginalParentFolderId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P, args);

				args = new Object[] {
					dlFolderModelImpl.getGroupId(),
					dlFolderModelImpl.getParentFolderId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P, args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByR_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					dlFolderModelImpl.getOriginalRepositoryId(),
					dlFolderModelImpl.getOriginalParentFolderId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByR_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByR_P, args);

				args = new Object[] {
					dlFolderModelImpl.getRepositoryId(),
					dlFolderModelImpl.getParentFolderId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByR_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByR_P, args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByP_N.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					dlFolderModelImpl.getOriginalParentFolderId(),
					dlFolderModelImpl.getOriginalName()
				};

				FinderCacheUtil.removeResult(_finderPathCountByP_N, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByP_N, args);

				args = new Object[] {
					dlFolderModelImpl.getParentFolderId(),
					dlFolderModelImpl.getName()
				};

				FinderCacheUtil.removeResult(_finderPathCountByP_N, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByP_N, args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_M_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					dlFolderModelImpl.getOriginalGroupId(),
					dlFolderModelImpl.getOriginalMountPoint(),
					dlFolderModelImpl.getOriginalParentFolderId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_M_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_M_P, args);

				args = new Object[] {
					dlFolderModelImpl.getGroupId(),
					dlFolderModelImpl.isMountPoint(),
					dlFolderModelImpl.getParentFolderId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_M_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_M_P, args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_M_P_H.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dlFolderModelImpl.getOriginalGroupId(),
					dlFolderModelImpl.getOriginalMountPoint(),
					dlFolderModelImpl.getOriginalParentFolderId(),
					dlFolderModelImpl.getOriginalHidden()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_M_P_H, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_M_P_H, args);

				args = new Object[] {
					dlFolderModelImpl.getGroupId(),
					dlFolderModelImpl.isMountPoint(),
					dlFolderModelImpl.getParentFolderId(),
					dlFolderModelImpl.isHidden()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_M_P_H, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_M_P_H, args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_H_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dlFolderModelImpl.getOriginalGroupId(),
					dlFolderModelImpl.getOriginalParentFolderId(),
					dlFolderModelImpl.getOriginalHidden(),
					dlFolderModelImpl.getOriginalStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_H_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_H_S, args);

				args = new Object[] {
					dlFolderModelImpl.getGroupId(),
					dlFolderModelImpl.getParentFolderId(),
					dlFolderModelImpl.isHidden(), dlFolderModelImpl.getStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_H_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_H_S, args);
			}

			if ((dlFolderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_M_P_H_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dlFolderModelImpl.getOriginalGroupId(),
					dlFolderModelImpl.getOriginalMountPoint(),
					dlFolderModelImpl.getOriginalParentFolderId(),
					dlFolderModelImpl.getOriginalHidden(),
					dlFolderModelImpl.getOriginalStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_M_P_H_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_M_P_H_S, args);

				args = new Object[] {
					dlFolderModelImpl.getGroupId(),
					dlFolderModelImpl.isMountPoint(),
					dlFolderModelImpl.getParentFolderId(),
					dlFolderModelImpl.isHidden(), dlFolderModelImpl.getStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_M_P_H_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_M_P_H_S, args);
			}
		}

		EntityCacheUtil.putResult(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED, DLFolderImpl.class,
			dlFolder.getPrimaryKey(), dlFolder, false);

		clearUniqueFindersCache(dlFolderModelImpl, false);
		cacheUniqueFindersCache(dlFolderModelImpl);

		dlFolder.resetOriginalValues();

		return dlFolder;
	}

	/**
	 * Returns the document library folder with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library folder
	 * @return the document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFolderException {

		DLFolder dlFolder = fetchByPrimaryKey(primaryKey);

		if (dlFolder == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFolderException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return dlFolder;
	}

	/**
	 * Returns the document library folder with the primary key or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param folderId the primary key of the document library folder
	 * @return the document library folder
	 * @throws NoSuchFolderException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder findByPrimaryKey(long folderId)
		throws NoSuchFolderException {

		return findByPrimaryKey((Serializable)folderId);
	}

	/**
	 * Returns the document library folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param folderId the primary key of the document library folder
	 * @return the document library folder, or <code>null</code> if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder fetchByPrimaryKey(long folderId) {
		return fetchByPrimaryKey((Serializable)folderId);
	}

	/**
	 * Returns all the document library folders.
	 *
	 * @return the document library folders
	 */
	@Override
	public List<DLFolder> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of document library folders
	 */
	@Override
	public List<DLFolder> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library folders
	 */
	@Override
	public List<DLFolder> findAll(
		int start, int end, OrderByComparator<DLFolder> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of document library folders
	 */
	@Override
	public List<DLFolder> findAll(
		int start, int end, OrderByComparator<DLFolder> orderByComparator,
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

		List<DLFolder> list = null;

		if (useFinderCache) {
			list = (List<DLFolder>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DLFOLDER);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFOLDER;

				sql = sql.concat(DLFolderModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<DLFolder>)QueryUtil.list(
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
	 * Removes all the document library folders from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLFolder dlFolder : findAll()) {
			remove(dlFolder);
		}
	}

	/**
	 * Returns the number of document library folders.
	 *
	 * @return the number of document library folders
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLFOLDER);

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

	/**
	 * Returns the primaryKeys of document library file entry types associated with the document library folder.
	 *
	 * @param pk the primary key of the document library folder
	 * @return long[] of the primaryKeys of document library file entry types associated with the document library folder
	 */
	@Override
	public long[] getDLFileEntryTypePrimaryKeys(long pk) {
		long[] pks = dlFolderToDLFileEntryTypeTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the document library file entry types associated with the document library folder.
	 *
	 * @param pk the primary key of the document library folder
	 * @return the document library file entry types associated with the document library folder
	 */
	@Override
	public List<com.liferay.document.library.kernel.model.DLFileEntryType>
		getDLFileEntryTypes(long pk) {

		return getDLFileEntryTypes(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the document library file entry types associated with the document library folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the document library folder
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of document library file entry types associated with the document library folder
	 */
	@Override
	public List<com.liferay.document.library.kernel.model.DLFileEntryType>
		getDLFileEntryTypes(long pk, int start, int end) {

		return getDLFileEntryTypes(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry types associated with the document library folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFolderModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the document library folder
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file entry types associated with the document library folder
	 */
	@Override
	public List<com.liferay.document.library.kernel.model.DLFileEntryType>
		getDLFileEntryTypes(
			long pk, int start, int end,
			OrderByComparator
				<com.liferay.document.library.kernel.model.DLFileEntryType>
					orderByComparator) {

		return dlFolderToDLFileEntryTypeTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of document library file entry types associated with the document library folder.
	 *
	 * @param pk the primary key of the document library folder
	 * @return the number of document library file entry types associated with the document library folder
	 */
	@Override
	public int getDLFileEntryTypesSize(long pk) {
		long[] pks = dlFolderToDLFileEntryTypeTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the document library file entry type is associated with the document library folder.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypePK the primary key of the document library file entry type
	 * @return <code>true</code> if the document library file entry type is associated with the document library folder; <code>false</code> otherwise
	 */
	@Override
	public boolean containsDLFileEntryType(long pk, long dlFileEntryTypePK) {
		return dlFolderToDLFileEntryTypeTableMapper.containsTableMapping(
			pk, dlFileEntryTypePK);
	}

	/**
	 * Returns <code>true</code> if the document library folder has any document library file entry types associated with it.
	 *
	 * @param pk the primary key of the document library folder to check for associations with document library file entry types
	 * @return <code>true</code> if the document library folder has any document library file entry types associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsDLFileEntryTypes(long pk) {
		if (getDLFileEntryTypesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypePK the primary key of the document library file entry type
	 */
	@Override
	public void addDLFileEntryType(long pk, long dlFileEntryTypePK) {
		DLFolder dlFolder = fetchByPrimaryKey(pk);

		if (dlFolder == null) {
			dlFolderToDLFileEntryTypeTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, dlFileEntryTypePK);
		}
		else {
			dlFolderToDLFileEntryTypeTableMapper.addTableMapping(
				dlFolder.getCompanyId(), pk, dlFileEntryTypePK);
		}
	}

	/**
	 * Adds an association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryType the document library file entry type
	 */
	@Override
	public void addDLFileEntryType(
		long pk,
		com.liferay.document.library.kernel.model.DLFileEntryType
			dlFileEntryType) {

		DLFolder dlFolder = fetchByPrimaryKey(pk);

		if (dlFolder == null) {
			dlFolderToDLFileEntryTypeTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				dlFileEntryType.getPrimaryKey());
		}
		else {
			dlFolderToDLFileEntryTypeTableMapper.addTableMapping(
				dlFolder.getCompanyId(), pk, dlFileEntryType.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypePKs the primary keys of the document library file entry types
	 */
	@Override
	public void addDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs) {
		long companyId = 0;

		DLFolder dlFolder = fetchByPrimaryKey(pk);

		if (dlFolder == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = dlFolder.getCompanyId();
		}

		dlFolderToDLFileEntryTypeTableMapper.addTableMappings(
			companyId, pk, dlFileEntryTypePKs);
	}

	/**
	 * Adds an association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypes the document library file entry types
	 */
	@Override
	public void addDLFileEntryTypes(
		long pk,
		List<com.liferay.document.library.kernel.model.DLFileEntryType>
			dlFileEntryTypes) {

		addDLFileEntryTypes(
			pk,
			ListUtil.toLongArray(
				dlFileEntryTypes,
				com.liferay.document.library.kernel.model.DLFileEntryType.
					FILE_ENTRY_TYPE_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the document library folder and its document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder to clear the associated document library file entry types from
	 */
	@Override
	public void clearDLFileEntryTypes(long pk) {
		dlFolderToDLFileEntryTypeTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypePK the primary key of the document library file entry type
	 */
	@Override
	public void removeDLFileEntryType(long pk, long dlFileEntryTypePK) {
		dlFolderToDLFileEntryTypeTableMapper.deleteTableMapping(
			pk, dlFileEntryTypePK);
	}

	/**
	 * Removes the association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryType the document library file entry type
	 */
	@Override
	public void removeDLFileEntryType(
		long pk,
		com.liferay.document.library.kernel.model.DLFileEntryType
			dlFileEntryType) {

		dlFolderToDLFileEntryTypeTableMapper.deleteTableMapping(
			pk, dlFileEntryType.getPrimaryKey());
	}

	/**
	 * Removes the association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypePKs the primary keys of the document library file entry types
	 */
	@Override
	public void removeDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs) {
		dlFolderToDLFileEntryTypeTableMapper.deleteTableMappings(
			pk, dlFileEntryTypePKs);
	}

	/**
	 * Removes the association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypes the document library file entry types
	 */
	@Override
	public void removeDLFileEntryTypes(
		long pk,
		List<com.liferay.document.library.kernel.model.DLFileEntryType>
			dlFileEntryTypes) {

		removeDLFileEntryTypes(
			pk,
			ListUtil.toLongArray(
				dlFileEntryTypes,
				com.liferay.document.library.kernel.model.DLFileEntryType.
					FILE_ENTRY_TYPE_ID_ACCESSOR));
	}

	/**
	 * Sets the document library file entry types associated with the document library folder, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypePKs the primary keys of the document library file entry types to be associated with the document library folder
	 */
	@Override
	public void setDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs) {
		Set<Long> newDLFileEntryTypePKsSet = SetUtil.fromArray(
			dlFileEntryTypePKs);
		Set<Long> oldDLFileEntryTypePKsSet = SetUtil.fromArray(
			dlFolderToDLFileEntryTypeTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeDLFileEntryTypePKsSet = new HashSet<Long>(
			oldDLFileEntryTypePKsSet);

		removeDLFileEntryTypePKsSet.removeAll(newDLFileEntryTypePKsSet);

		dlFolderToDLFileEntryTypeTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeDLFileEntryTypePKsSet));

		newDLFileEntryTypePKsSet.removeAll(oldDLFileEntryTypePKsSet);

		long companyId = 0;

		DLFolder dlFolder = fetchByPrimaryKey(pk);

		if (dlFolder == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = dlFolder.getCompanyId();
		}

		dlFolderToDLFileEntryTypeTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newDLFileEntryTypePKsSet));
	}

	/**
	 * Sets the document library file entry types associated with the document library folder, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library folder
	 * @param dlFileEntryTypes the document library file entry types to be associated with the document library folder
	 */
	@Override
	public void setDLFileEntryTypes(
		long pk,
		List<com.liferay.document.library.kernel.model.DLFileEntryType>
			dlFileEntryTypes) {

		try {
			long[] dlFileEntryTypePKs = new long[dlFileEntryTypes.size()];

			for (int i = 0; i < dlFileEntryTypes.size(); i++) {
				com.liferay.document.library.kernel.model.DLFileEntryType
					dlFileEntryType = dlFileEntryTypes.get(i);

				dlFileEntryTypePKs[i] = dlFileEntryType.getPrimaryKey();
			}

			setDLFileEntryTypes(pk, dlFileEntryTypePKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
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
		return "folderId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DLFOLDER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DLFolderModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the document library folder persistence.
	 */
	public void afterPropertiesSet() {
		dlFolderToDLFileEntryTypeTableMapper =
			TableMapperFactory.getTableMapper(
				"DLFileEntryTypes_DLFolders", "companyId", "folderId",
				"fileEntryTypeId", this, dlFileEntryTypePersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			DLFolderModelImpl.UUID_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			DLFolderModelImpl.UUID_COLUMN_BITMASK |
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			DLFolderModelImpl.UUID_COLUMN_BITMASK |
			DLFolderModelImpl.COMPANYID_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			DLFolderModelImpl.COMPANYID_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByRepositoryId = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRepositoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByRepositoryId = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRepositoryId",
			new String[] {Long.class.getName()},
			DLFolderModelImpl.REPOSITORYID_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByRepositoryId = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRepositoryId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByG_P = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_P = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_NotS = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_NotS",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByC_NotS = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_NotS",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathFetchByR_M = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByR_M",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			DLFolderModelImpl.REPOSITORYID_COLUMN_BITMASK |
			DLFolderModelImpl.MOUNTPOINT_COLUMN_BITMASK);

		_finderPathCountByR_M = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_M",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByR_P = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByR_P = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			DLFolderModelImpl.REPOSITORYID_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByR_P = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_P",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByP_N = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByP_N = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP_N",
			new String[] {Long.class.getName(), String.class.getName()},
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByP_N = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_N",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_M_P = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_M_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_M_P = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_M_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK |
			DLFolderModelImpl.MOUNTPOINT_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_M_P = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_M_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});

		_finderPathFetchByG_P_N = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_P_N = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByF_C_P_NotS = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByF_C_P_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByF_C_P_NotS = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByF_C_P_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_M_P_H = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_M_P_H",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_M_P_H = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_M_P_H",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK |
			DLFolderModelImpl.MOUNTPOINT_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.HIDDEN_COLUMN_BITMASK |
			DLFolderModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_M_P_H = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_M_P_H",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			});

		_finderPathWithPaginationFindByG_M_T_H = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_M_T_H",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_M_T_H = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_M_T_H",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Boolean.class.getName()
			});

		_finderPathWithPaginationFindByG_P_H_S = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_H_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_H_S = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_H_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			},
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.HIDDEN_COLUMN_BITMASK |
			DLFolderModelImpl.STATUS_COLUMN_BITMASK |
			DLFolderModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_P_H_S = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_H_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_M_P_H_S = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_M_P_H_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_M_P_H_S = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, DLFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_M_P_H_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			DLFolderModelImpl.GROUPID_COLUMN_BITMASK |
			DLFolderModelImpl.MOUNTPOINT_COLUMN_BITMASK |
			DLFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			DLFolderModelImpl.HIDDEN_COLUMN_BITMASK |
			DLFolderModelImpl.STATUS_COLUMN_BITMASK |
			DLFolderModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_M_P_H_S = new FinderPath(
			DLFolderModelImpl.ENTITY_CACHE_ENABLED,
			DLFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_M_P_H_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DLFolderImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("DLFileEntryTypes_DLFolders");
	}

	@BeanReference(type = DLFileEntryTypePersistence.class)
	protected DLFileEntryTypePersistence dlFileEntryTypePersistence;

	protected TableMapper
		<DLFolder, com.liferay.document.library.kernel.model.DLFileEntryType>
			dlFolderToDLFileEntryTypeTableMapper;

	private static final String _SQL_SELECT_DLFOLDER =
		"SELECT dlFolder FROM DLFolder dlFolder";

	private static final String _SQL_SELECT_DLFOLDER_WHERE =
		"SELECT dlFolder FROM DLFolder dlFolder WHERE ";

	private static final String _SQL_COUNT_DLFOLDER =
		"SELECT COUNT(dlFolder) FROM DLFolder dlFolder";

	private static final String _SQL_COUNT_DLFOLDER_WHERE =
		"SELECT COUNT(dlFolder) FROM DLFolder dlFolder WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"dlFolder.folderId";

	private static final String _FILTER_SQL_SELECT_DLFOLDER_WHERE =
		"SELECT DISTINCT {dlFolder.*} FROM DLFolder dlFolder WHERE ";

	private static final String
		_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {DLFolder.*} FROM (SELECT DISTINCT dlFolder.folderId FROM DLFolder dlFolder WHERE ";

	private static final String
		_FILTER_SQL_SELECT_DLFOLDER_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN DLFolder ON TEMP_TABLE.folderId = DLFolder.folderId";

	private static final String _FILTER_SQL_COUNT_DLFOLDER_WHERE =
		"SELECT COUNT(DISTINCT dlFolder.folderId) AS COUNT_VALUE FROM DLFolder dlFolder WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "dlFolder";

	private static final String _FILTER_ENTITY_TABLE = "DLFolder";

	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFolder.";

	private static final String _ORDER_BY_ENTITY_TABLE = "DLFolder.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DLFolder exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DLFolder exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DLFolderPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "hidden"});

}