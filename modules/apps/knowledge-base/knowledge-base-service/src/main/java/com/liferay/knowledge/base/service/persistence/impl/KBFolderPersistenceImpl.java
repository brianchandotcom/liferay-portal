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

package com.liferay.knowledge.base.service.persistence.impl;

import com.liferay.knowledge.base.exception.NoSuchFolderException;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.model.impl.KBFolderImpl;
import com.liferay.knowledge.base.model.impl.KBFolderModelImpl;
import com.liferay.knowledge.base.service.persistence.KBFolderPersistence;
import com.liferay.knowledge.base.service.persistence.impl.constants.KBPersistenceConstants;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
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
 * The persistence implementation for the kb folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = KBFolderPersistence.class)
public class KBFolderPersistenceImpl
	extends BasePersistenceImpl<KBFolder> implements KBFolderPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>KBFolderUtil</code> to access the kb folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		KBFolderImpl.class.getName();

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
	 * Returns all the kb folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching kb folders
	 */
	@Override
	public List<KBFolder> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kb folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @return the range of matching kb folders
	 */
	@Override
	public List<KBFolder> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kb folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kb folders
	 */
	@Override
	public List<KBFolder> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<KBFolder> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kb folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kb folders
	 */
	@Override
	public List<KBFolder> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<KBFolder> orderByComparator, boolean useFinderCache) {

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

		List<KBFolder> list = null;

		if (useFinderCache) {
			list = (List<KBFolder>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KBFolder kbFolder : list) {
					if (!uuid.equals(kbFolder.getUuid())) {
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

			query.append(_SQL_SELECT_KBFOLDER_WHERE);

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
				query.append(KBFolderModelImpl.ORDER_BY_JPQL);
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

				list = (List<KBFolder>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first kb folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kb folder
	 * @throws NoSuchFolderException if a matching kb folder could not be found
	 */
	@Override
	public KBFolder findByUuid_First(
			String uuid, OrderByComparator<KBFolder> orderByComparator)
		throws NoSuchFolderException {

		KBFolder kbFolder = fetchByUuid_First(uuid, orderByComparator);

		if (kbFolder != null) {
			return kbFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first kb folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kb folder, or <code>null</code> if a matching kb folder could not be found
	 */
	@Override
	public KBFolder fetchByUuid_First(
		String uuid, OrderByComparator<KBFolder> orderByComparator) {

		List<KBFolder> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kb folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kb folder
	 * @throws NoSuchFolderException if a matching kb folder could not be found
	 */
	@Override
	public KBFolder findByUuid_Last(
			String uuid, OrderByComparator<KBFolder> orderByComparator)
		throws NoSuchFolderException {

		KBFolder kbFolder = fetchByUuid_Last(uuid, orderByComparator);

		if (kbFolder != null) {
			return kbFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last kb folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kb folder, or <code>null</code> if a matching kb folder could not be found
	 */
	@Override
	public KBFolder fetchByUuid_Last(
		String uuid, OrderByComparator<KBFolder> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<KBFolder> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kb folders before and after the current kb folder in the ordered set where uuid = &#63;.
	 *
	 * @param kbFolderId the primary key of the current kb folder
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kb folder
	 * @throws NoSuchFolderException if a kb folder with the primary key could not be found
	 */
	@Override
	public KBFolder[] findByUuid_PrevAndNext(
			long kbFolderId, String uuid,
			OrderByComparator<KBFolder> orderByComparator)
		throws NoSuchFolderException {

		uuid = Objects.toString(uuid, "");

		KBFolder kbFolder = findByPrimaryKey(kbFolderId);

		Session session = null;

		try {
			session = openSession();

			KBFolder[] array = new KBFolderImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, kbFolder, uuid, orderByComparator, true);

			array[1] = kbFolder;

			array[2] = getByUuid_PrevAndNext(
				session, kbFolder, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected KBFolder getByUuid_PrevAndNext(
		Session session, KBFolder kbFolder, String uuid,
		OrderByComparator<KBFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_KBFOLDER_WHERE);

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
			query.append(KBFolderModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(kbFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<KBFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kb folders where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (KBFolder kbFolder :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kbFolder);
		}
	}

	/**
	 * Returns the number of kb folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching kb folders
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_KBFOLDER_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"kbFolder.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(kbFolder.uuid IS NULL OR kbFolder.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the kb folder where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching kb folder
	 * @throws NoSuchFolderException if a matching kb folder could not be found
	 */
	@Override
	public KBFolder findByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException {

		KBFolder kbFolder = fetchByUUID_G(uuid, groupId);

		if (kbFolder == null) {
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

		return kbFolder;
	}

	/**
	 * Returns the kb folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching kb folder, or <code>null</code> if a matching kb folder could not be found
	 */
	@Override
	public KBFolder fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the kb folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kb folder, or <code>null</code> if a matching kb folder could not be found
	 */
	@Override
	public KBFolder fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof KBFolder) {
			KBFolder kbFolder = (KBFolder)result;

			if (!Objects.equals(uuid, kbFolder.getUuid()) ||
				(groupId != kbFolder.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_KBFOLDER_WHERE);

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

				List<KBFolder> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					KBFolder kbFolder = list.get(0);

					result = kbFolder;

					cacheResult(kbFolder);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
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
			return (KBFolder)result;
		}
	}

	/**
	 * Removes the kb folder where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the kb folder that was removed
	 */
	@Override
	public KBFolder removeByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException {

		KBFolder kbFolder = findByUUID_G(uuid, groupId);

		return remove(kbFolder);
	}

	/**
	 * Returns the number of kb folders where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching kb folders
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_KBFOLDER_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"kbFolder.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(kbFolder.uuid IS NULL OR kbFolder.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"kbFolder.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the kb folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching kb folders
	 */
	@Override
	public List<KBFolder> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kb folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @return the range of matching kb folders
	 */
	@Override
	public List<KBFolder> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kb folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kb folders
	 */
	@Override
	public List<KBFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<KBFolder> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kb folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kb folders
	 */
	@Override
	public List<KBFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<KBFolder> orderByComparator, boolean useFinderCache) {

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

		List<KBFolder> list = null;

		if (useFinderCache) {
			list = (List<KBFolder>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KBFolder kbFolder : list) {
					if (!uuid.equals(kbFolder.getUuid()) ||
						(companyId != kbFolder.getCompanyId())) {

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

			query.append(_SQL_SELECT_KBFOLDER_WHERE);

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
				query.append(KBFolderModelImpl.ORDER_BY_JPQL);
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

				list = (List<KBFolder>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first kb folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kb folder
	 * @throws NoSuchFolderException if a matching kb folder could not be found
	 */
	@Override
	public KBFolder findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<KBFolder> orderByComparator)
		throws NoSuchFolderException {

		KBFolder kbFolder = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (kbFolder != null) {
			return kbFolder;
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
	 * Returns the first kb folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kb folder, or <code>null</code> if a matching kb folder could not be found
	 */
	@Override
	public KBFolder fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<KBFolder> orderByComparator) {

		List<KBFolder> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kb folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kb folder
	 * @throws NoSuchFolderException if a matching kb folder could not be found
	 */
	@Override
	public KBFolder findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<KBFolder> orderByComparator)
		throws NoSuchFolderException {

		KBFolder kbFolder = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (kbFolder != null) {
			return kbFolder;
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
	 * Returns the last kb folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kb folder, or <code>null</code> if a matching kb folder could not be found
	 */
	@Override
	public KBFolder fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<KBFolder> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<KBFolder> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kb folders before and after the current kb folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param kbFolderId the primary key of the current kb folder
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kb folder
	 * @throws NoSuchFolderException if a kb folder with the primary key could not be found
	 */
	@Override
	public KBFolder[] findByUuid_C_PrevAndNext(
			long kbFolderId, String uuid, long companyId,
			OrderByComparator<KBFolder> orderByComparator)
		throws NoSuchFolderException {

		uuid = Objects.toString(uuid, "");

		KBFolder kbFolder = findByPrimaryKey(kbFolderId);

		Session session = null;

		try {
			session = openSession();

			KBFolder[] array = new KBFolderImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, kbFolder, uuid, companyId, orderByComparator, true);

			array[1] = kbFolder;

			array[2] = getByUuid_C_PrevAndNext(
				session, kbFolder, uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected KBFolder getByUuid_C_PrevAndNext(
		Session session, KBFolder kbFolder, String uuid, long companyId,
		OrderByComparator<KBFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_KBFOLDER_WHERE);

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
			query.append(KBFolderModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(kbFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<KBFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kb folders where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (KBFolder kbFolder :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kbFolder);
		}
	}

	/**
	 * Returns the number of kb folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching kb folders
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_KBFOLDER_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"kbFolder.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(kbFolder.uuid IS NULL OR kbFolder.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"kbFolder.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByG_P;
	private FinderPath _finderPathWithoutPaginationFindByG_P;
	private FinderPath _finderPathCountByG_P;

	/**
	 * Returns all the kb folders where groupId = &#63; and parentKBFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @return the matching kb folders
	 */
	@Override
	public List<KBFolder> findByG_P(long groupId, long parentKBFolderId) {
		return findByG_P(
			groupId, parentKBFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kb folders where groupId = &#63; and parentKBFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @return the range of matching kb folders
	 */
	@Override
	public List<KBFolder> findByG_P(
		long groupId, long parentKBFolderId, int start, int end) {

		return findByG_P(groupId, parentKBFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kb folders where groupId = &#63; and parentKBFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kb folders
	 */
	@Override
	public List<KBFolder> findByG_P(
		long groupId, long parentKBFolderId, int start, int end,
		OrderByComparator<KBFolder> orderByComparator) {

		return findByG_P(
			groupId, parentKBFolderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kb folders where groupId = &#63; and parentKBFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kb folders
	 */
	@Override
	public List<KBFolder> findByG_P(
		long groupId, long parentKBFolderId, int start, int end,
		OrderByComparator<KBFolder> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_P;
				finderArgs = new Object[] {groupId, parentKBFolderId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_P;
			finderArgs = new Object[] {
				groupId, parentKBFolderId, start, end, orderByComparator
			};
		}

		List<KBFolder> list = null;

		if (useFinderCache) {
			list = (List<KBFolder>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KBFolder kbFolder : list) {
					if ((groupId != kbFolder.getGroupId()) ||
						(parentKBFolderId != kbFolder.getParentKBFolderId())) {

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

			query.append(_SQL_SELECT_KBFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTKBFOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(KBFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentKBFolderId);

				list = (List<KBFolder>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first kb folder in the ordered set where groupId = &#63; and parentKBFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kb folder
	 * @throws NoSuchFolderException if a matching kb folder could not be found
	 */
	@Override
	public KBFolder findByG_P_First(
			long groupId, long parentKBFolderId,
			OrderByComparator<KBFolder> orderByComparator)
		throws NoSuchFolderException {

		KBFolder kbFolder = fetchByG_P_First(
			groupId, parentKBFolderId, orderByComparator);

		if (kbFolder != null) {
			return kbFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentKBFolderId=");
		msg.append(parentKBFolderId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first kb folder in the ordered set where groupId = &#63; and parentKBFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kb folder, or <code>null</code> if a matching kb folder could not be found
	 */
	@Override
	public KBFolder fetchByG_P_First(
		long groupId, long parentKBFolderId,
		OrderByComparator<KBFolder> orderByComparator) {

		List<KBFolder> list = findByG_P(
			groupId, parentKBFolderId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kb folder in the ordered set where groupId = &#63; and parentKBFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kb folder
	 * @throws NoSuchFolderException if a matching kb folder could not be found
	 */
	@Override
	public KBFolder findByG_P_Last(
			long groupId, long parentKBFolderId,
			OrderByComparator<KBFolder> orderByComparator)
		throws NoSuchFolderException {

		KBFolder kbFolder = fetchByG_P_Last(
			groupId, parentKBFolderId, orderByComparator);

		if (kbFolder != null) {
			return kbFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentKBFolderId=");
		msg.append(parentKBFolderId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last kb folder in the ordered set where groupId = &#63; and parentKBFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kb folder, or <code>null</code> if a matching kb folder could not be found
	 */
	@Override
	public KBFolder fetchByG_P_Last(
		long groupId, long parentKBFolderId,
		OrderByComparator<KBFolder> orderByComparator) {

		int count = countByG_P(groupId, parentKBFolderId);

		if (count == 0) {
			return null;
		}

		List<KBFolder> list = findByG_P(
			groupId, parentKBFolderId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kb folders before and after the current kb folder in the ordered set where groupId = &#63; and parentKBFolderId = &#63;.
	 *
	 * @param kbFolderId the primary key of the current kb folder
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kb folder
	 * @throws NoSuchFolderException if a kb folder with the primary key could not be found
	 */
	@Override
	public KBFolder[] findByG_P_PrevAndNext(
			long kbFolderId, long groupId, long parentKBFolderId,
			OrderByComparator<KBFolder> orderByComparator)
		throws NoSuchFolderException {

		KBFolder kbFolder = findByPrimaryKey(kbFolderId);

		Session session = null;

		try {
			session = openSession();

			KBFolder[] array = new KBFolderImpl[3];

			array[0] = getByG_P_PrevAndNext(
				session, kbFolder, groupId, parentKBFolderId, orderByComparator,
				true);

			array[1] = kbFolder;

			array[2] = getByG_P_PrevAndNext(
				session, kbFolder, groupId, parentKBFolderId, orderByComparator,
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

	protected KBFolder getByG_P_PrevAndNext(
		Session session, KBFolder kbFolder, long groupId, long parentKBFolderId,
		OrderByComparator<KBFolder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_KBFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTKBFOLDERID_2);

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
			query.append(KBFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentKBFolderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(kbFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<KBFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the kb folders that the user has permission to view where groupId = &#63; and parentKBFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @return the matching kb folders that the user has permission to view
	 */
	@Override
	public List<KBFolder> filterFindByG_P(long groupId, long parentKBFolderId) {
		return filterFindByG_P(
			groupId, parentKBFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kb folders that the user has permission to view where groupId = &#63; and parentKBFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @return the range of matching kb folders that the user has permission to view
	 */
	@Override
	public List<KBFolder> filterFindByG_P(
		long groupId, long parentKBFolderId, int start, int end) {

		return filterFindByG_P(groupId, parentKBFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kb folders that the user has permissions to view where groupId = &#63; and parentKBFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kb folders that the user has permission to view
	 */
	@Override
	public List<KBFolder> filterFindByG_P(
		long groupId, long parentKBFolderId, int start, int end,
		OrderByComparator<KBFolder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P(
				groupId, parentKBFolderId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_KBFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_KBFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTKBFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_KBFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(KBFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(KBFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), KBFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, KBFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, KBFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentKBFolderId);

			return (List<KBFolder>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the kb folders before and after the current kb folder in the ordered set of kb folders that the user has permission to view where groupId = &#63; and parentKBFolderId = &#63;.
	 *
	 * @param kbFolderId the primary key of the current kb folder
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kb folder
	 * @throws NoSuchFolderException if a kb folder with the primary key could not be found
	 */
	@Override
	public KBFolder[] filterFindByG_P_PrevAndNext(
			long kbFolderId, long groupId, long parentKBFolderId,
			OrderByComparator<KBFolder> orderByComparator)
		throws NoSuchFolderException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_PrevAndNext(
				kbFolderId, groupId, parentKBFolderId, orderByComparator);
		}

		KBFolder kbFolder = findByPrimaryKey(kbFolderId);

		Session session = null;

		try {
			session = openSession();

			KBFolder[] array = new KBFolderImpl[3];

			array[0] = filterGetByG_P_PrevAndNext(
				session, kbFolder, groupId, parentKBFolderId, orderByComparator,
				true);

			array[1] = kbFolder;

			array[2] = filterGetByG_P_PrevAndNext(
				session, kbFolder, groupId, parentKBFolderId, orderByComparator,
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

	protected KBFolder filterGetByG_P_PrevAndNext(
		Session session, KBFolder kbFolder, long groupId, long parentKBFolderId,
		OrderByComparator<KBFolder> orderByComparator, boolean previous) {

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
			query.append(_FILTER_SQL_SELECT_KBFOLDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_KBFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTKBFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_KBFOLDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(KBFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(KBFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), KBFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, KBFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, KBFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentKBFolderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(kbFolder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<KBFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kb folders where groupId = &#63; and parentKBFolderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 */
	@Override
	public void removeByG_P(long groupId, long parentKBFolderId) {
		for (KBFolder kbFolder :
				findByG_P(
					groupId, parentKBFolderId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kbFolder);
		}
	}

	/**
	 * Returns the number of kb folders where groupId = &#63; and parentKBFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @return the number of matching kb folders
	 */
	@Override
	public int countByG_P(long groupId, long parentKBFolderId) {
		FinderPath finderPath = _finderPathCountByG_P;

		Object[] finderArgs = new Object[] {groupId, parentKBFolderId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_KBFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTKBFOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentKBFolderId);

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

	/**
	 * Returns the number of kb folders that the user has permission to view where groupId = &#63; and parentKBFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @return the number of matching kb folders that the user has permission to view
	 */
	@Override
	public int filterCountByG_P(long groupId, long parentKBFolderId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P(groupId, parentKBFolderId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_KBFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTKBFOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), KBFolder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentKBFolderId);

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
		"kbFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_PARENTKBFOLDERID_2 =
		"kbFolder.parentKBFolderId = ?";

	private FinderPath _finderPathFetchByG_P_N;
	private FinderPath _finderPathCountByG_P_N;

	/**
	 * Returns the kb folder where groupId = &#63; and parentKBFolderId = &#63; and name = &#63; or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param name the name
	 * @return the matching kb folder
	 * @throws NoSuchFolderException if a matching kb folder could not be found
	 */
	@Override
	public KBFolder findByG_P_N(
			long groupId, long parentKBFolderId, String name)
		throws NoSuchFolderException {

		KBFolder kbFolder = fetchByG_P_N(groupId, parentKBFolderId, name);

		if (kbFolder == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", parentKBFolderId=");
			msg.append(parentKBFolderId);

			msg.append(", name=");
			msg.append(name);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return kbFolder;
	}

	/**
	 * Returns the kb folder where groupId = &#63; and parentKBFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param name the name
	 * @return the matching kb folder, or <code>null</code> if a matching kb folder could not be found
	 */
	@Override
	public KBFolder fetchByG_P_N(
		long groupId, long parentKBFolderId, String name) {

		return fetchByG_P_N(groupId, parentKBFolderId, name, true);
	}

	/**
	 * Returns the kb folder where groupId = &#63; and parentKBFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kb folder, or <code>null</code> if a matching kb folder could not be found
	 */
	@Override
	public KBFolder fetchByG_P_N(
		long groupId, long parentKBFolderId, String name,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, parentKBFolderId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_P_N, finderArgs, this);
		}

		if (result instanceof KBFolder) {
			KBFolder kbFolder = (KBFolder)result;

			if ((groupId != kbFolder.getGroupId()) ||
				(parentKBFolderId != kbFolder.getParentKBFolderId()) ||
				!Objects.equals(name, kbFolder.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_KBFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_N_PARENTKBFOLDERID_2);

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

				qPos.add(parentKBFolderId);

				if (bindName) {
					qPos.add(name);
				}

				List<KBFolder> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_P_N, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									groupId, parentKBFolderId, name
								};
							}

							_log.warn(
								"KBFolderPersistenceImpl.fetchByG_P_N(long, long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					KBFolder kbFolder = list.get(0);

					result = kbFolder;

					cacheResult(kbFolder);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
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
			return (KBFolder)result;
		}
	}

	/**
	 * Removes the kb folder where groupId = &#63; and parentKBFolderId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param name the name
	 * @return the kb folder that was removed
	 */
	@Override
	public KBFolder removeByG_P_N(
			long groupId, long parentKBFolderId, String name)
		throws NoSuchFolderException {

		KBFolder kbFolder = findByG_P_N(groupId, parentKBFolderId, name);

		return remove(kbFolder);
	}

	/**
	 * Returns the number of kb folders where groupId = &#63; and parentKBFolderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param name the name
	 * @return the number of matching kb folders
	 */
	@Override
	public int countByG_P_N(long groupId, long parentKBFolderId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByG_P_N;

		Object[] finderArgs = new Object[] {groupId, parentKBFolderId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_KBFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_N_PARENTKBFOLDERID_2);

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

				qPos.add(parentKBFolderId);

				if (bindName) {
					qPos.add(name);
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

	private static final String _FINDER_COLUMN_G_P_N_GROUPID_2 =
		"kbFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_N_PARENTKBFOLDERID_2 =
		"kbFolder.parentKBFolderId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_N_NAME_2 =
		"kbFolder.name = ?";

	private static final String _FINDER_COLUMN_G_P_N_NAME_3 =
		"(kbFolder.name IS NULL OR kbFolder.name = '')";

	private FinderPath _finderPathFetchByG_P_UT;
	private FinderPath _finderPathCountByG_P_UT;

	/**
	 * Returns the kb folder where groupId = &#63; and parentKBFolderId = &#63; and urlTitle = &#63; or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param urlTitle the url title
	 * @return the matching kb folder
	 * @throws NoSuchFolderException if a matching kb folder could not be found
	 */
	@Override
	public KBFolder findByG_P_UT(
			long groupId, long parentKBFolderId, String urlTitle)
		throws NoSuchFolderException {

		KBFolder kbFolder = fetchByG_P_UT(groupId, parentKBFolderId, urlTitle);

		if (kbFolder == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", parentKBFolderId=");
			msg.append(parentKBFolderId);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return kbFolder;
	}

	/**
	 * Returns the kb folder where groupId = &#63; and parentKBFolderId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param urlTitle the url title
	 * @return the matching kb folder, or <code>null</code> if a matching kb folder could not be found
	 */
	@Override
	public KBFolder fetchByG_P_UT(
		long groupId, long parentKBFolderId, String urlTitle) {

		return fetchByG_P_UT(groupId, parentKBFolderId, urlTitle, true);
	}

	/**
	 * Returns the kb folder where groupId = &#63; and parentKBFolderId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param urlTitle the url title
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kb folder, or <code>null</code> if a matching kb folder could not be found
	 */
	@Override
	public KBFolder fetchByG_P_UT(
		long groupId, long parentKBFolderId, String urlTitle,
		boolean useFinderCache) {

		urlTitle = Objects.toString(urlTitle, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, parentKBFolderId, urlTitle};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_P_UT, finderArgs, this);
		}

		if (result instanceof KBFolder) {
			KBFolder kbFolder = (KBFolder)result;

			if ((groupId != kbFolder.getGroupId()) ||
				(parentKBFolderId != kbFolder.getParentKBFolderId()) ||
				!Objects.equals(urlTitle, kbFolder.getUrlTitle())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_KBFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_UT_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_UT_PARENTKBFOLDERID_2);

			boolean bindUrlTitle = false;

			if (urlTitle.isEmpty()) {
				query.append(_FINDER_COLUMN_G_P_UT_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_P_UT_URLTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentKBFolderId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				List<KBFolder> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_P_UT, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									groupId, parentKBFolderId, urlTitle
								};
							}

							_log.warn(
								"KBFolderPersistenceImpl.fetchByG_P_UT(long, long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					KBFolder kbFolder = list.get(0);

					result = kbFolder;

					cacheResult(kbFolder);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByG_P_UT, finderArgs);
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
			return (KBFolder)result;
		}
	}

	/**
	 * Removes the kb folder where groupId = &#63; and parentKBFolderId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param urlTitle the url title
	 * @return the kb folder that was removed
	 */
	@Override
	public KBFolder removeByG_P_UT(
			long groupId, long parentKBFolderId, String urlTitle)
		throws NoSuchFolderException {

		KBFolder kbFolder = findByG_P_UT(groupId, parentKBFolderId, urlTitle);

		return remove(kbFolder);
	}

	/**
	 * Returns the number of kb folders where groupId = &#63; and parentKBFolderId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentKBFolderId the parent kb folder ID
	 * @param urlTitle the url title
	 * @return the number of matching kb folders
	 */
	@Override
	public int countByG_P_UT(
		long groupId, long parentKBFolderId, String urlTitle) {

		urlTitle = Objects.toString(urlTitle, "");

		FinderPath finderPath = _finderPathCountByG_P_UT;

		Object[] finderArgs = new Object[] {
			groupId, parentKBFolderId, urlTitle
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_KBFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_UT_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_UT_PARENTKBFOLDERID_2);

			boolean bindUrlTitle = false;

			if (urlTitle.isEmpty()) {
				query.append(_FINDER_COLUMN_G_P_UT_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_P_UT_URLTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentKBFolderId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
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

	private static final String _FINDER_COLUMN_G_P_UT_GROUPID_2 =
		"kbFolder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_UT_PARENTKBFOLDERID_2 =
		"kbFolder.parentKBFolderId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_UT_URLTITLE_2 =
		"kbFolder.urlTitle = ?";

	private static final String _FINDER_COLUMN_G_P_UT_URLTITLE_3 =
		"(kbFolder.urlTitle IS NULL OR kbFolder.urlTitle = '')";

	public KBFolderPersistenceImpl() {
		setModelClass(KBFolder.class);

		setModelImplClass(KBFolderImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the kb folder in the entity cache if it is enabled.
	 *
	 * @param kbFolder the kb folder
	 */
	@Override
	public void cacheResult(KBFolder kbFolder) {
		entityCache.putResult(
			entityCacheEnabled, KBFolderImpl.class, kbFolder.getPrimaryKey(),
			kbFolder);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {kbFolder.getUuid(), kbFolder.getGroupId()}, kbFolder);

		finderCache.putResult(
			_finderPathFetchByG_P_N,
			new Object[] {
				kbFolder.getGroupId(), kbFolder.getParentKBFolderId(),
				kbFolder.getName()
			},
			kbFolder);

		finderCache.putResult(
			_finderPathFetchByG_P_UT,
			new Object[] {
				kbFolder.getGroupId(), kbFolder.getParentKBFolderId(),
				kbFolder.getUrlTitle()
			},
			kbFolder);

		kbFolder.resetOriginalValues();
	}

	/**
	 * Caches the kb folders in the entity cache if it is enabled.
	 *
	 * @param kbFolders the kb folders
	 */
	@Override
	public void cacheResult(List<KBFolder> kbFolders) {
		for (KBFolder kbFolder : kbFolders) {
			if (entityCache.getResult(
					entityCacheEnabled, KBFolderImpl.class,
					kbFolder.getPrimaryKey()) == null) {

				cacheResult(kbFolder);
			}
			else {
				kbFolder.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all kb folders.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KBFolderImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the kb folder.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KBFolder kbFolder) {
		entityCache.removeResult(
			entityCacheEnabled, KBFolderImpl.class, kbFolder.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((KBFolderModelImpl)kbFolder, true);
	}

	@Override
	public void clearCache(List<KBFolder> kbFolders) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (KBFolder kbFolder : kbFolders) {
			entityCache.removeResult(
				entityCacheEnabled, KBFolderImpl.class,
				kbFolder.getPrimaryKey());

			clearUniqueFindersCache((KBFolderModelImpl)kbFolder, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, KBFolderImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		KBFolderModelImpl kbFolderModelImpl) {

		Object[] args = new Object[] {
			kbFolderModelImpl.getUuid(), kbFolderModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, kbFolderModelImpl, false);

		args = new Object[] {
			kbFolderModelImpl.getGroupId(),
			kbFolderModelImpl.getParentKBFolderId(), kbFolderModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByG_P_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_P_N, args, kbFolderModelImpl, false);

		args = new Object[] {
			kbFolderModelImpl.getGroupId(),
			kbFolderModelImpl.getParentKBFolderId(),
			kbFolderModelImpl.getUrlTitle()
		};

		finderCache.putResult(
			_finderPathCountByG_P_UT, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_P_UT, args, kbFolderModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		KBFolderModelImpl kbFolderModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				kbFolderModelImpl.getUuid(), kbFolderModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((kbFolderModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				kbFolderModelImpl.getOriginalUuid(),
				kbFolderModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				kbFolderModelImpl.getGroupId(),
				kbFolderModelImpl.getParentKBFolderId(),
				kbFolderModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByG_P_N, args);
			finderCache.removeResult(_finderPathFetchByG_P_N, args);
		}

		if ((kbFolderModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_P_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				kbFolderModelImpl.getOriginalGroupId(),
				kbFolderModelImpl.getOriginalParentKBFolderId(),
				kbFolderModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByG_P_N, args);
			finderCache.removeResult(_finderPathFetchByG_P_N, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				kbFolderModelImpl.getGroupId(),
				kbFolderModelImpl.getParentKBFolderId(),
				kbFolderModelImpl.getUrlTitle()
			};

			finderCache.removeResult(_finderPathCountByG_P_UT, args);
			finderCache.removeResult(_finderPathFetchByG_P_UT, args);
		}

		if ((kbFolderModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_P_UT.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				kbFolderModelImpl.getOriginalGroupId(),
				kbFolderModelImpl.getOriginalParentKBFolderId(),
				kbFolderModelImpl.getOriginalUrlTitle()
			};

			finderCache.removeResult(_finderPathCountByG_P_UT, args);
			finderCache.removeResult(_finderPathFetchByG_P_UT, args);
		}
	}

	/**
	 * Creates a new kb folder with the primary key. Does not add the kb folder to the database.
	 *
	 * @param kbFolderId the primary key for the new kb folder
	 * @return the new kb folder
	 */
	@Override
	public KBFolder create(long kbFolderId) {
		KBFolder kbFolder = new KBFolderImpl();

		kbFolder.setNew(true);
		kbFolder.setPrimaryKey(kbFolderId);

		String uuid = PortalUUIDUtil.generate();

		kbFolder.setUuid(uuid);

		kbFolder.setCompanyId(CompanyThreadLocal.getCompanyId());

		return kbFolder;
	}

	/**
	 * Removes the kb folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbFolderId the primary key of the kb folder
	 * @return the kb folder that was removed
	 * @throws NoSuchFolderException if a kb folder with the primary key could not be found
	 */
	@Override
	public KBFolder remove(long kbFolderId) throws NoSuchFolderException {
		return remove((Serializable)kbFolderId);
	}

	/**
	 * Removes the kb folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kb folder
	 * @return the kb folder that was removed
	 * @throws NoSuchFolderException if a kb folder with the primary key could not be found
	 */
	@Override
	public KBFolder remove(Serializable primaryKey)
		throws NoSuchFolderException {

		Session session = null;

		try {
			session = openSession();

			KBFolder kbFolder = (KBFolder)session.get(
				KBFolderImpl.class, primaryKey);

			if (kbFolder == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFolderException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(kbFolder);
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
	protected KBFolder removeImpl(KBFolder kbFolder) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kbFolder)) {
				kbFolder = (KBFolder)session.get(
					KBFolderImpl.class, kbFolder.getPrimaryKeyObj());
			}

			if (kbFolder != null) {
				session.delete(kbFolder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (kbFolder != null) {
			clearCache(kbFolder);
		}

		return kbFolder;
	}

	@Override
	public KBFolder updateImpl(KBFolder kbFolder) {
		boolean isNew = kbFolder.isNew();

		if (!(kbFolder instanceof KBFolderModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(kbFolder.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(kbFolder);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in kbFolder proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom KBFolder implementation " +
					kbFolder.getClass());
		}

		KBFolderModelImpl kbFolderModelImpl = (KBFolderModelImpl)kbFolder;

		if (Validator.isNull(kbFolder.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			kbFolder.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (kbFolder.getCreateDate() == null)) {
			if (serviceContext == null) {
				kbFolder.setCreateDate(now);
			}
			else {
				kbFolder.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!kbFolderModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kbFolder.setModifiedDate(now);
			}
			else {
				kbFolder.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (kbFolder.isNew()) {
				session.save(kbFolder);

				kbFolder.setNew(false);
			}
			else {
				kbFolder = (KBFolder)session.merge(kbFolder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {kbFolderModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				kbFolderModelImpl.getUuid(), kbFolderModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				kbFolderModelImpl.getGroupId(),
				kbFolderModelImpl.getParentKBFolderId()
			};

			finderCache.removeResult(_finderPathCountByG_P, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_P, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((kbFolderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					kbFolderModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {kbFolderModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((kbFolderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					kbFolderModelImpl.getOriginalUuid(),
					kbFolderModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					kbFolderModelImpl.getUuid(),
					kbFolderModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((kbFolderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					kbFolderModelImpl.getOriginalGroupId(),
					kbFolderModelImpl.getOriginalParentKBFolderId()
				};

				finderCache.removeResult(_finderPathCountByG_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_P, args);

				args = new Object[] {
					kbFolderModelImpl.getGroupId(),
					kbFolderModelImpl.getParentKBFolderId()
				};

				finderCache.removeResult(_finderPathCountByG_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_P, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, KBFolderImpl.class, kbFolder.getPrimaryKey(),
			kbFolder, false);

		clearUniqueFindersCache(kbFolderModelImpl, false);
		cacheUniqueFindersCache(kbFolderModelImpl);

		kbFolder.resetOriginalValues();

		return kbFolder;
	}

	/**
	 * Returns the kb folder with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kb folder
	 * @return the kb folder
	 * @throws NoSuchFolderException if a kb folder with the primary key could not be found
	 */
	@Override
	public KBFolder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFolderException {

		KBFolder kbFolder = fetchByPrimaryKey(primaryKey);

		if (kbFolder == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFolderException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return kbFolder;
	}

	/**
	 * Returns the kb folder with the primary key or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param kbFolderId the primary key of the kb folder
	 * @return the kb folder
	 * @throws NoSuchFolderException if a kb folder with the primary key could not be found
	 */
	@Override
	public KBFolder findByPrimaryKey(long kbFolderId)
		throws NoSuchFolderException {

		return findByPrimaryKey((Serializable)kbFolderId);
	}

	/**
	 * Returns the kb folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kbFolderId the primary key of the kb folder
	 * @return the kb folder, or <code>null</code> if a kb folder with the primary key could not be found
	 */
	@Override
	public KBFolder fetchByPrimaryKey(long kbFolderId) {
		return fetchByPrimaryKey((Serializable)kbFolderId);
	}

	/**
	 * Returns all the kb folders.
	 *
	 * @return the kb folders
	 */
	@Override
	public List<KBFolder> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kb folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @return the range of kb folders
	 */
	@Override
	public List<KBFolder> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kb folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kb folders
	 */
	@Override
	public List<KBFolder> findAll(
		int start, int end, OrderByComparator<KBFolder> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kb folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kb folders
	 */
	@Override
	public List<KBFolder> findAll(
		int start, int end, OrderByComparator<KBFolder> orderByComparator,
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

		List<KBFolder> list = null;

		if (useFinderCache) {
			list = (List<KBFolder>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_KBFOLDER);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_KBFOLDER;

				sql = sql.concat(KBFolderModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<KBFolder>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Removes all the kb folders from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KBFolder kbFolder : findAll()) {
			remove(kbFolder);
		}
	}

	/**
	 * Returns the number of kb folders.
	 *
	 * @return the number of kb folders
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_KBFOLDER);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
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
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "kbFolderId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_KBFOLDER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return KBFolderModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kb folder persistence.
	 */
	@Activate
	public void activate() {
		KBFolderModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		KBFolderModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, KBFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, KBFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, KBFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, KBFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			KBFolderModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, KBFolderImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			KBFolderModelImpl.UUID_COLUMN_BITMASK |
			KBFolderModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, KBFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, KBFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			KBFolderModelImpl.UUID_COLUMN_BITMASK |
			KBFolderModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByG_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, KBFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, KBFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			KBFolderModelImpl.GROUPID_COLUMN_BITMASK |
			KBFolderModelImpl.PARENTKBFOLDERID_COLUMN_BITMASK);

		_finderPathCountByG_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByG_P_N = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, KBFolderImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			KBFolderModelImpl.GROUPID_COLUMN_BITMASK |
			KBFolderModelImpl.PARENTKBFOLDERID_COLUMN_BITMASK |
			KBFolderModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_P_N = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

		_finderPathFetchByG_P_UT = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, KBFolderImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_P_UT",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			KBFolderModelImpl.GROUPID_COLUMN_BITMASK |
			KBFolderModelImpl.PARENTKBFOLDERID_COLUMN_BITMASK |
			KBFolderModelImpl.URLTITLE_COLUMN_BITMASK);

		_finderPathCountByG_P_UT = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_UT",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(KBFolderImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = KBPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.knowledge.base.model.KBFolder"),
			true);
	}

	@Override
	@Reference(
		target = KBPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = KBPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_KBFOLDER =
		"SELECT kbFolder FROM KBFolder kbFolder";

	private static final String _SQL_SELECT_KBFOLDER_WHERE =
		"SELECT kbFolder FROM KBFolder kbFolder WHERE ";

	private static final String _SQL_COUNT_KBFOLDER =
		"SELECT COUNT(kbFolder) FROM KBFolder kbFolder";

	private static final String _SQL_COUNT_KBFOLDER_WHERE =
		"SELECT COUNT(kbFolder) FROM KBFolder kbFolder WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"kbFolder.kbFolderId";

	private static final String _FILTER_SQL_SELECT_KBFOLDER_WHERE =
		"SELECT DISTINCT {kbFolder.*} FROM KBFolder kbFolder WHERE ";

	private static final String
		_FILTER_SQL_SELECT_KBFOLDER_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {KBFolder.*} FROM (SELECT DISTINCT kbFolder.kbFolderId FROM KBFolder kbFolder WHERE ";

	private static final String
		_FILTER_SQL_SELECT_KBFOLDER_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN KBFolder ON TEMP_TABLE.kbFolderId = KBFolder.kbFolderId";

	private static final String _FILTER_SQL_COUNT_KBFOLDER_WHERE =
		"SELECT COUNT(DISTINCT kbFolder.kbFolderId) AS COUNT_VALUE FROM KBFolder kbFolder WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "kbFolder";

	private static final String _FILTER_ENTITY_TABLE = "KBFolder";

	private static final String _ORDER_BY_ENTITY_ALIAS = "kbFolder.";

	private static final String _ORDER_BY_ENTITY_TABLE = "KBFolder.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No KBFolder exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No KBFolder exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		KBFolderPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(KBPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}