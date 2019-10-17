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

package com.liferay.info.service.persistence.impl;

import com.liferay.info.exception.NoSuchItemUsageException;
import com.liferay.info.model.InfoItemUsage;
import com.liferay.info.model.impl.InfoItemUsageImpl;
import com.liferay.info.model.impl.InfoItemUsageModelImpl;
import com.liferay.info.service.persistence.InfoItemUsagePersistence;
import com.liferay.info.service.persistence.impl.constants.InfoPersistenceConstants;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

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
 * The persistence implementation for the info item usage service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = InfoItemUsagePersistence.class)
public class InfoItemUsagePersistenceImpl
	extends BasePersistenceImpl<InfoItemUsage>
	implements InfoItemUsagePersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>InfoItemUsageUtil</code> to access the info item usage persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		InfoItemUsageImpl.class.getName();

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
	 * Returns all the info item usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the info item usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the info item usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the info item usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator,
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

		List<InfoItemUsage> list = null;

		if (useFinderCache) {
			list = (List<InfoItemUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (InfoItemUsage infoItemUsage : list) {
					if (!uuid.equals(infoItemUsage.getUuid())) {
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

			query.append(_SQL_SELECT_INFOITEMUSAGE_WHERE);

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
				query.append(InfoItemUsageModelImpl.ORDER_BY_JPQL);
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

				list = (List<InfoItemUsage>)QueryUtil.list(
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
	 * Returns the first info item usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage findByUuid_First(
			String uuid, OrderByComparator<InfoItemUsage> orderByComparator)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = fetchByUuid_First(
			uuid, orderByComparator);

		if (infoItemUsage != null) {
			return infoItemUsage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchItemUsageException(msg.toString());
	}

	/**
	 * Returns the first info item usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage fetchByUuid_First(
		String uuid, OrderByComparator<InfoItemUsage> orderByComparator) {

		List<InfoItemUsage> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last info item usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage findByUuid_Last(
			String uuid, OrderByComparator<InfoItemUsage> orderByComparator)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = fetchByUuid_Last(uuid, orderByComparator);

		if (infoItemUsage != null) {
			return infoItemUsage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchItemUsageException(msg.toString());
	}

	/**
	 * Returns the last info item usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage fetchByUuid_Last(
		String uuid, OrderByComparator<InfoItemUsage> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<InfoItemUsage> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the info item usages before and after the current info item usage in the ordered set where uuid = &#63;.
	 *
	 * @param infoItemUsageId the primary key of the current info item usage
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	@Override
	public InfoItemUsage[] findByUuid_PrevAndNext(
			long infoItemUsageId, String uuid,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws NoSuchItemUsageException {

		uuid = Objects.toString(uuid, "");

		InfoItemUsage infoItemUsage = findByPrimaryKey(infoItemUsageId);

		Session session = null;

		try {
			session = openSession();

			InfoItemUsage[] array = new InfoItemUsageImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, infoItemUsage, uuid, orderByComparator, true);

			array[1] = infoItemUsage;

			array[2] = getByUuid_PrevAndNext(
				session, infoItemUsage, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected InfoItemUsage getByUuid_PrevAndNext(
		Session session, InfoItemUsage infoItemUsage, String uuid,
		OrderByComparator<InfoItemUsage> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_INFOITEMUSAGE_WHERE);

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
			query.append(InfoItemUsageModelImpl.ORDER_BY_JPQL);
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
						infoItemUsage)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<InfoItemUsage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the info item usages where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (InfoItemUsage infoItemUsage :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(infoItemUsage);
		}
	}

	/**
	 * Returns the number of info item usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching info item usages
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_INFOITEMUSAGE_WHERE);

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
		"infoItemUsage.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(infoItemUsage.uuid IS NULL OR infoItemUsage.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the info item usage where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchItemUsageException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage findByUUID_G(String uuid, long groupId)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = fetchByUUID_G(uuid, groupId);

		if (infoItemUsage == null) {
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

			throw new NoSuchItemUsageException(msg.toString());
		}

		return infoItemUsage;
	}

	/**
	 * Returns the info item usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the info item usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage fetchByUUID_G(
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

		if (result instanceof InfoItemUsage) {
			InfoItemUsage infoItemUsage = (InfoItemUsage)result;

			if (!Objects.equals(uuid, infoItemUsage.getUuid()) ||
				(groupId != infoItemUsage.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_INFOITEMUSAGE_WHERE);

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

				List<InfoItemUsage> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					InfoItemUsage infoItemUsage = list.get(0);

					result = infoItemUsage;

					cacheResult(infoItemUsage);
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
			return (InfoItemUsage)result;
		}
	}

	/**
	 * Removes the info item usage where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the info item usage that was removed
	 */
	@Override
	public InfoItemUsage removeByUUID_G(String uuid, long groupId)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = findByUUID_G(uuid, groupId);

		return remove(infoItemUsage);
	}

	/**
	 * Returns the number of info item usages where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching info item usages
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_INFOITEMUSAGE_WHERE);

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
		"infoItemUsage.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(infoItemUsage.uuid IS NULL OR infoItemUsage.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"infoItemUsage.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByPlid;
	private FinderPath _finderPathWithoutPaginationFindByPlid;
	private FinderPath _finderPathCountByPlid;

	/**
	 * Returns all the info item usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByPlid(long plid) {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the info item usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByPlid(long plid, int start, int end) {
		return findByPlid(plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the info item usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByPlid(
		long plid, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return findByPlid(plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the info item usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByPlid(
		long plid, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByPlid;
				finderArgs = new Object[] {plid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByPlid;
			finderArgs = new Object[] {plid, start, end, orderByComparator};
		}

		List<InfoItemUsage> list = null;

		if (useFinderCache) {
			list = (List<InfoItemUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (InfoItemUsage infoItemUsage : list) {
					if (plid != infoItemUsage.getPlid()) {
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

			query.append(_SQL_SELECT_INFOITEMUSAGE_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(InfoItemUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				list = (List<InfoItemUsage>)QueryUtil.list(
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
	 * Returns the first info item usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage findByPlid_First(
			long plid, OrderByComparator<InfoItemUsage> orderByComparator)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = fetchByPlid_First(
			plid, orderByComparator);

		if (infoItemUsage != null) {
			return infoItemUsage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchItemUsageException(msg.toString());
	}

	/**
	 * Returns the first info item usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage fetchByPlid_First(
		long plid, OrderByComparator<InfoItemUsage> orderByComparator) {

		List<InfoItemUsage> list = findByPlid(plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last info item usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage findByPlid_Last(
			long plid, OrderByComparator<InfoItemUsage> orderByComparator)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = fetchByPlid_Last(plid, orderByComparator);

		if (infoItemUsage != null) {
			return infoItemUsage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchItemUsageException(msg.toString());
	}

	/**
	 * Returns the last info item usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage fetchByPlid_Last(
		long plid, OrderByComparator<InfoItemUsage> orderByComparator) {

		int count = countByPlid(plid);

		if (count == 0) {
			return null;
		}

		List<InfoItemUsage> list = findByPlid(
			plid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the info item usages before and after the current info item usage in the ordered set where plid = &#63;.
	 *
	 * @param infoItemUsageId the primary key of the current info item usage
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	@Override
	public InfoItemUsage[] findByPlid_PrevAndNext(
			long infoItemUsageId, long plid,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = findByPrimaryKey(infoItemUsageId);

		Session session = null;

		try {
			session = openSession();

			InfoItemUsage[] array = new InfoItemUsageImpl[3];

			array[0] = getByPlid_PrevAndNext(
				session, infoItemUsage, plid, orderByComparator, true);

			array[1] = infoItemUsage;

			array[2] = getByPlid_PrevAndNext(
				session, infoItemUsage, plid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected InfoItemUsage getByPlid_PrevAndNext(
		Session session, InfoItemUsage infoItemUsage, long plid,
		OrderByComparator<InfoItemUsage> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_INFOITEMUSAGE_WHERE);

		query.append(_FINDER_COLUMN_PLID_PLID_2);

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
			query.append(InfoItemUsageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						infoItemUsage)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<InfoItemUsage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the info item usages where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	@Override
	public void removeByPlid(long plid) {
		for (InfoItemUsage infoItemUsage :
				findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(infoItemUsage);
		}
	}

	/**
	 * Returns the number of info item usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching info item usages
	 */
	@Override
	public int countByPlid(long plid) {
		FinderPath finderPath = _finderPathCountByPlid;

		Object[] finderArgs = new Object[] {plid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_INFOITEMUSAGE_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

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

	private static final String _FINDER_COLUMN_PLID_PLID_2 =
		"infoItemUsage.plid = ? AND infoItemUsage.containerKey IS NOT NULL";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the info item usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByC_C(long classNameId, long classPK) {
		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the info item usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the info item usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the info item usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator,
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

		List<InfoItemUsage> list = null;

		if (useFinderCache) {
			list = (List<InfoItemUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (InfoItemUsage infoItemUsage : list) {
					if ((classNameId != infoItemUsage.getClassNameId()) ||
						(classPK != infoItemUsage.getClassPK())) {

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

			query.append(_SQL_SELECT_INFOITEMUSAGE_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(InfoItemUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<InfoItemUsage>)QueryUtil.list(
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
	 * Returns the first info item usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (infoItemUsage != null) {
			return infoItemUsage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchItemUsageException(msg.toString());
	}

	/**
	 * Returns the first info item usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		List<InfoItemUsage> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last info item usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (infoItemUsage != null) {
			return infoItemUsage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchItemUsageException(msg.toString());
	}

	/**
	 * Returns the last info item usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<InfoItemUsage> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the info item usages before and after the current info item usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param infoItemUsageId the primary key of the current info item usage
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	@Override
	public InfoItemUsage[] findByC_C_PrevAndNext(
			long infoItemUsageId, long classNameId, long classPK,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = findByPrimaryKey(infoItemUsageId);

		Session session = null;

		try {
			session = openSession();

			InfoItemUsage[] array = new InfoItemUsageImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, infoItemUsage, classNameId, classPK, orderByComparator,
				true);

			array[1] = infoItemUsage;

			array[2] = getByC_C_PrevAndNext(
				session, infoItemUsage, classNameId, classPK, orderByComparator,
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

	protected InfoItemUsage getByC_C_PrevAndNext(
		Session session, InfoItemUsage infoItemUsage, long classNameId,
		long classPK, OrderByComparator<InfoItemUsage> orderByComparator,
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

		query.append(_SQL_SELECT_INFOITEMUSAGE_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			query.append(InfoItemUsageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						infoItemUsage)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<InfoItemUsage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the info item usages where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (InfoItemUsage infoItemUsage :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(infoItemUsage);
		}
	}

	/**
	 * Returns the number of info item usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching info item usages
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_INFOITEMUSAGE_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"infoItemUsage.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"infoItemUsage.classPK = ? AND infoItemUsage.containerKey IS NOT NULL";

	private FinderPath _finderPathWithPaginationFindByC_C_T;
	private FinderPath _finderPathWithoutPaginationFindByC_C_T;
	private FinderPath _finderPathCountByC_C_T;

	/**
	 * Returns all the info item usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByC_C_T(
		long classNameId, long classPK, int type) {

		return findByC_C_T(
			classNameId, classPK, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the info item usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end) {

		return findByC_C_T(classNameId, classPK, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the info item usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return findByC_C_T(
			classNameId, classPK, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the info item usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C_T;
				finderArgs = new Object[] {classNameId, classPK, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C_T;
			finderArgs = new Object[] {
				classNameId, classPK, type, start, end, orderByComparator
			};
		}

		List<InfoItemUsage> list = null;

		if (useFinderCache) {
			list = (List<InfoItemUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (InfoItemUsage infoItemUsage : list) {
					if ((classNameId != infoItemUsage.getClassNameId()) ||
						(classPK != infoItemUsage.getClassPK()) ||
						(type != infoItemUsage.getType())) {

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

			query.append(_SQL_SELECT_INFOITEMUSAGE_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(InfoItemUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				list = (List<InfoItemUsage>)QueryUtil.list(
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
	 * Returns the first info item usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage findByC_C_T_First(
			long classNameId, long classPK, int type,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = fetchByC_C_T_First(
			classNameId, classPK, type, orderByComparator);

		if (infoItemUsage != null) {
			return infoItemUsage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchItemUsageException(msg.toString());
	}

	/**
	 * Returns the first info item usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage fetchByC_C_T_First(
		long classNameId, long classPK, int type,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		List<InfoItemUsage> list = findByC_C_T(
			classNameId, classPK, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last info item usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage findByC_C_T_Last(
			long classNameId, long classPK, int type,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = fetchByC_C_T_Last(
			classNameId, classPK, type, orderByComparator);

		if (infoItemUsage != null) {
			return infoItemUsage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchItemUsageException(msg.toString());
	}

	/**
	 * Returns the last info item usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage fetchByC_C_T_Last(
		long classNameId, long classPK, int type,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		int count = countByC_C_T(classNameId, classPK, type);

		if (count == 0) {
			return null;
		}

		List<InfoItemUsage> list = findByC_C_T(
			classNameId, classPK, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the info item usages before and after the current info item usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param infoItemUsageId the primary key of the current info item usage
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	@Override
	public InfoItemUsage[] findByC_C_T_PrevAndNext(
			long infoItemUsageId, long classNameId, long classPK, int type,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = findByPrimaryKey(infoItemUsageId);

		Session session = null;

		try {
			session = openSession();

			InfoItemUsage[] array = new InfoItemUsageImpl[3];

			array[0] = getByC_C_T_PrevAndNext(
				session, infoItemUsage, classNameId, classPK, type,
				orderByComparator, true);

			array[1] = infoItemUsage;

			array[2] = getByC_C_T_PrevAndNext(
				session, infoItemUsage, classNameId, classPK, type,
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

	protected InfoItemUsage getByC_C_T_PrevAndNext(
		Session session, InfoItemUsage infoItemUsage, long classNameId,
		long classPK, int type,
		OrderByComparator<InfoItemUsage> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_INFOITEMUSAGE_WHERE);

		query.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

		query.append(_FINDER_COLUMN_C_C_T_TYPE_2);

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
			query.append(InfoItemUsageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						infoItemUsage)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<InfoItemUsage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the info item usages where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	@Override
	public void removeByC_C_T(long classNameId, long classPK, int type) {
		for (InfoItemUsage infoItemUsage :
				findByC_C_T(
					classNameId, classPK, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(infoItemUsage);
		}
	}

	/**
	 * Returns the number of info item usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching info item usages
	 */
	@Override
	public int countByC_C_T(long classNameId, long classPK, int type) {
		FinderPath finderPath = _finderPathCountByC_C_T;

		Object[] finderArgs = new Object[] {classNameId, classPK, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_INFOITEMUSAGE_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_C_C_T_CLASSNAMEID_2 =
		"infoItemUsage.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_CLASSPK_2 =
		"infoItemUsage.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_TYPE_2 =
		"infoItemUsage.type = ? AND infoItemUsage.containerKey IS NOT NULL";

	private FinderPath _finderPathWithPaginationFindByCK_CT_P;
	private FinderPath _finderPathWithoutPaginationFindByCK_CT_P;
	private FinderPath _finderPathCountByCK_CT_P;

	/**
	 * Returns all the info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid) {

		return findByCK_CT_P(
			containerKey, containerType, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start,
		int end) {

		return findByCK_CT_P(
			containerKey, containerType, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return findByCK_CT_P(
			containerKey, containerType, plid, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching info item usages
	 */
	@Override
	public List<InfoItemUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator,
		boolean useFinderCache) {

		containerKey = Objects.toString(containerKey, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCK_CT_P;
				finderArgs = new Object[] {containerKey, containerType, plid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCK_CT_P;
			finderArgs = new Object[] {
				containerKey, containerType, plid, start, end, orderByComparator
			};
		}

		List<InfoItemUsage> list = null;

		if (useFinderCache) {
			list = (List<InfoItemUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (InfoItemUsage infoItemUsage : list) {
					if (!containerKey.equals(infoItemUsage.getContainerKey()) ||
						(containerType != infoItemUsage.getContainerType()) ||
						(plid != infoItemUsage.getPlid())) {

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

			query.append(_SQL_SELECT_INFOITEMUSAGE_WHERE);

			boolean bindContainerKey = false;

			if (containerKey.isEmpty()) {
				query.append(_FINDER_COLUMN_CK_CT_P_CONTAINERKEY_3);
			}
			else {
				bindContainerKey = true;

				query.append(_FINDER_COLUMN_CK_CT_P_CONTAINERKEY_2);
			}

			query.append(_FINDER_COLUMN_CK_CT_P_CONTAINERTYPE_2);

			query.append(_FINDER_COLUMN_CK_CT_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(InfoItemUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindContainerKey) {
					qPos.add(containerKey);
				}

				qPos.add(containerType);

				qPos.add(plid);

				list = (List<InfoItemUsage>)QueryUtil.list(
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
	 * Returns the first info item usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage findByCK_CT_P_First(
			String containerKey, long containerType, long plid,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = fetchByCK_CT_P_First(
			containerKey, containerType, plid, orderByComparator);

		if (infoItemUsage != null) {
			return infoItemUsage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("containerKey=");
		msg.append(containerKey);

		msg.append(", containerType=");
		msg.append(containerType);

		msg.append(", plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchItemUsageException(msg.toString());
	}

	/**
	 * Returns the first info item usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage fetchByCK_CT_P_First(
		String containerKey, long containerType, long plid,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		List<InfoItemUsage> list = findByCK_CT_P(
			containerKey, containerType, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last info item usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage findByCK_CT_P_Last(
			String containerKey, long containerType, long plid,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = fetchByCK_CT_P_Last(
			containerKey, containerType, plid, orderByComparator);

		if (infoItemUsage != null) {
			return infoItemUsage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("containerKey=");
		msg.append(containerKey);

		msg.append(", containerType=");
		msg.append(containerType);

		msg.append(", plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchItemUsageException(msg.toString());
	}

	/**
	 * Returns the last info item usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage fetchByCK_CT_P_Last(
		String containerKey, long containerType, long plid,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		int count = countByCK_CT_P(containerKey, containerType, plid);

		if (count == 0) {
			return null;
		}

		List<InfoItemUsage> list = findByCK_CT_P(
			containerKey, containerType, plid, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the info item usages before and after the current info item usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param infoItemUsageId the primary key of the current info item usage
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	@Override
	public InfoItemUsage[] findByCK_CT_P_PrevAndNext(
			long infoItemUsageId, String containerKey, long containerType,
			long plid, OrderByComparator<InfoItemUsage> orderByComparator)
		throws NoSuchItemUsageException {

		containerKey = Objects.toString(containerKey, "");

		InfoItemUsage infoItemUsage = findByPrimaryKey(infoItemUsageId);

		Session session = null;

		try {
			session = openSession();

			InfoItemUsage[] array = new InfoItemUsageImpl[3];

			array[0] = getByCK_CT_P_PrevAndNext(
				session, infoItemUsage, containerKey, containerType, plid,
				orderByComparator, true);

			array[1] = infoItemUsage;

			array[2] = getByCK_CT_P_PrevAndNext(
				session, infoItemUsage, containerKey, containerType, plid,
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

	protected InfoItemUsage getByCK_CT_P_PrevAndNext(
		Session session, InfoItemUsage infoItemUsage, String containerKey,
		long containerType, long plid,
		OrderByComparator<InfoItemUsage> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_INFOITEMUSAGE_WHERE);

		boolean bindContainerKey = false;

		if (containerKey.isEmpty()) {
			query.append(_FINDER_COLUMN_CK_CT_P_CONTAINERKEY_3);
		}
		else {
			bindContainerKey = true;

			query.append(_FINDER_COLUMN_CK_CT_P_CONTAINERKEY_2);
		}

		query.append(_FINDER_COLUMN_CK_CT_P_CONTAINERTYPE_2);

		query.append(_FINDER_COLUMN_CK_CT_P_PLID_2);

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
			query.append(InfoItemUsageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindContainerKey) {
			qPos.add(containerKey);
		}

		qPos.add(containerType);

		qPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						infoItemUsage)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<InfoItemUsage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63; from the database.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 */
	@Override
	public void removeByCK_CT_P(
		String containerKey, long containerType, long plid) {

		for (InfoItemUsage infoItemUsage :
				findByCK_CT_P(
					containerKey, containerType, plid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(infoItemUsage);
		}
	}

	/**
	 * Returns the number of info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the number of matching info item usages
	 */
	@Override
	public int countByCK_CT_P(
		String containerKey, long containerType, long plid) {

		containerKey = Objects.toString(containerKey, "");

		FinderPath finderPath = _finderPathCountByCK_CT_P;

		Object[] finderArgs = new Object[] {containerKey, containerType, plid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_INFOITEMUSAGE_WHERE);

			boolean bindContainerKey = false;

			if (containerKey.isEmpty()) {
				query.append(_FINDER_COLUMN_CK_CT_P_CONTAINERKEY_3);
			}
			else {
				bindContainerKey = true;

				query.append(_FINDER_COLUMN_CK_CT_P_CONTAINERKEY_2);
			}

			query.append(_FINDER_COLUMN_CK_CT_P_CONTAINERTYPE_2);

			query.append(_FINDER_COLUMN_CK_CT_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindContainerKey) {
					qPos.add(containerKey);
				}

				qPos.add(containerType);

				qPos.add(plid);

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

	private static final String _FINDER_COLUMN_CK_CT_P_CONTAINERKEY_2 =
		"infoItemUsage.containerKey = ? AND ";

	private static final String _FINDER_COLUMN_CK_CT_P_CONTAINERKEY_3 =
		"(infoItemUsage.containerKey IS NULL OR infoItemUsage.containerKey = '') AND ";

	private static final String _FINDER_COLUMN_CK_CT_P_CONTAINERTYPE_2 =
		"infoItemUsage.containerType = ? AND ";

	private static final String _FINDER_COLUMN_CK_CT_P_PLID_2 =
		"infoItemUsage.plid = ? AND infoItemUsage.containerKey IS NOT NULL";

	private FinderPath _finderPathFetchByC_C_CK_CT_P;
	private FinderPath _finderPathCountByC_C_CK_CT_P;

	/**
	 * Returns the info item usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; or throws a <code>NoSuchItemUsageException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage findByC_C_CK_CT_P(
			long classNameId, long classPK, String containerKey,
			long containerType, long plid)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = fetchByC_C_CK_CT_P(
			classNameId, classPK, containerKey, containerType, plid);

		if (infoItemUsage == null) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", containerKey=");
			msg.append(containerKey);

			msg.append(", containerType=");
			msg.append(containerType);

			msg.append(", plid=");
			msg.append(plid);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchItemUsageException(msg.toString());
		}

		return infoItemUsage;
	}

	/**
	 * Returns the info item usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage fetchByC_C_CK_CT_P(
		long classNameId, long classPK, String containerKey, long containerType,
		long plid) {

		return fetchByC_C_CK_CT_P(
			classNameId, classPK, containerKey, containerType, plid, true);
	}

	/**
	 * Returns the info item usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	@Override
	public InfoItemUsage fetchByC_C_CK_CT_P(
		long classNameId, long classPK, String containerKey, long containerType,
		long plid, boolean useFinderCache) {

		containerKey = Objects.toString(containerKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				classNameId, classPK, containerKey, containerType, plid
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C_CK_CT_P, finderArgs, this);
		}

		if (result instanceof InfoItemUsage) {
			InfoItemUsage infoItemUsage = (InfoItemUsage)result;

			if ((classNameId != infoItemUsage.getClassNameId()) ||
				(classPK != infoItemUsage.getClassPK()) ||
				!Objects.equals(
					containerKey, infoItemUsage.getContainerKey()) ||
				(containerType != infoItemUsage.getContainerType()) ||
				(plid != infoItemUsage.getPlid())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_INFOITEMUSAGE_WHERE);

			query.append(_FINDER_COLUMN_C_C_CK_CT_P_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CK_CT_P_CLASSPK_2);

			boolean bindContainerKey = false;

			if (containerKey.isEmpty()) {
				query.append(_FINDER_COLUMN_C_C_CK_CT_P_CONTAINERKEY_3);
			}
			else {
				bindContainerKey = true;

				query.append(_FINDER_COLUMN_C_C_CK_CT_P_CONTAINERKEY_2);
			}

			query.append(_FINDER_COLUMN_C_C_CK_CT_P_CONTAINERTYPE_2);

			query.append(_FINDER_COLUMN_C_C_CK_CT_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindContainerKey) {
					qPos.add(containerKey);
				}

				qPos.add(containerType);

				qPos.add(plid);

				List<InfoItemUsage> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_CK_CT_P, finderArgs, list);
					}
				}
				else {
					InfoItemUsage infoItemUsage = list.get(0);

					result = infoItemUsage;

					cacheResult(infoItemUsage);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByC_C_CK_CT_P, finderArgs);
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
			return (InfoItemUsage)result;
		}
	}

	/**
	 * Removes the info item usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the info item usage that was removed
	 */
	@Override
	public InfoItemUsage removeByC_C_CK_CT_P(
			long classNameId, long classPK, String containerKey,
			long containerType, long plid)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = findByC_C_CK_CT_P(
			classNameId, classPK, containerKey, containerType, plid);

		return remove(infoItemUsage);
	}

	/**
	 * Returns the number of info item usages where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the number of matching info item usages
	 */
	@Override
	public int countByC_C_CK_CT_P(
		long classNameId, long classPK, String containerKey, long containerType,
		long plid) {

		containerKey = Objects.toString(containerKey, "");

		FinderPath finderPath = _finderPathCountByC_C_CK_CT_P;

		Object[] finderArgs = new Object[] {
			classNameId, classPK, containerKey, containerType, plid
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_INFOITEMUSAGE_WHERE);

			query.append(_FINDER_COLUMN_C_C_CK_CT_P_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CK_CT_P_CLASSPK_2);

			boolean bindContainerKey = false;

			if (containerKey.isEmpty()) {
				query.append(_FINDER_COLUMN_C_C_CK_CT_P_CONTAINERKEY_3);
			}
			else {
				bindContainerKey = true;

				query.append(_FINDER_COLUMN_C_C_CK_CT_P_CONTAINERKEY_2);
			}

			query.append(_FINDER_COLUMN_C_C_CK_CT_P_CONTAINERTYPE_2);

			query.append(_FINDER_COLUMN_C_C_CK_CT_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindContainerKey) {
					qPos.add(containerKey);
				}

				qPos.add(containerType);

				qPos.add(plid);

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

	private static final String _FINDER_COLUMN_C_C_CK_CT_P_CLASSNAMEID_2 =
		"infoItemUsage.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CK_CT_P_CLASSPK_2 =
		"infoItemUsage.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CK_CT_P_CONTAINERKEY_2 =
		"infoItemUsage.containerKey = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CK_CT_P_CONTAINERKEY_3 =
		"(infoItemUsage.containerKey IS NULL OR infoItemUsage.containerKey = '') AND ";

	private static final String _FINDER_COLUMN_C_C_CK_CT_P_CONTAINERTYPE_2 =
		"infoItemUsage.containerType = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CK_CT_P_PLID_2 =
		"infoItemUsage.plid = ?";

	public InfoItemUsagePersistenceImpl() {
		setModelClass(InfoItemUsage.class);

		setModelImplClass(InfoItemUsageImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the info item usage in the entity cache if it is enabled.
	 *
	 * @param infoItemUsage the info item usage
	 */
	@Override
	public void cacheResult(InfoItemUsage infoItemUsage) {
		entityCache.putResult(
			entityCacheEnabled, InfoItemUsageImpl.class,
			infoItemUsage.getPrimaryKey(), infoItemUsage);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {infoItemUsage.getUuid(), infoItemUsage.getGroupId()},
			infoItemUsage);

		finderCache.putResult(
			_finderPathFetchByC_C_CK_CT_P,
			new Object[] {
				infoItemUsage.getClassNameId(), infoItemUsage.getClassPK(),
				infoItemUsage.getContainerKey(),
				infoItemUsage.getContainerType(), infoItemUsage.getPlid()
			},
			infoItemUsage);

		infoItemUsage.resetOriginalValues();
	}

	/**
	 * Caches the info item usages in the entity cache if it is enabled.
	 *
	 * @param infoItemUsages the info item usages
	 */
	@Override
	public void cacheResult(List<InfoItemUsage> infoItemUsages) {
		for (InfoItemUsage infoItemUsage : infoItemUsages) {
			if (entityCache.getResult(
					entityCacheEnabled, InfoItemUsageImpl.class,
					infoItemUsage.getPrimaryKey()) == null) {

				cacheResult(infoItemUsage);
			}
			else {
				infoItemUsage.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all info item usages.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(InfoItemUsageImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the info item usage.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(InfoItemUsage infoItemUsage) {
		entityCache.removeResult(
			entityCacheEnabled, InfoItemUsageImpl.class,
			infoItemUsage.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((InfoItemUsageModelImpl)infoItemUsage, true);
	}

	@Override
	public void clearCache(List<InfoItemUsage> infoItemUsages) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (InfoItemUsage infoItemUsage : infoItemUsages) {
			entityCache.removeResult(
				entityCacheEnabled, InfoItemUsageImpl.class,
				infoItemUsage.getPrimaryKey());

			clearUniqueFindersCache(
				(InfoItemUsageModelImpl)infoItemUsage, true);
		}
	}

	protected void cacheUniqueFindersCache(
		InfoItemUsageModelImpl infoItemUsageModelImpl) {

		Object[] args = new Object[] {
			infoItemUsageModelImpl.getUuid(),
			infoItemUsageModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, infoItemUsageModelImpl, false);

		args = new Object[] {
			infoItemUsageModelImpl.getClassNameId(),
			infoItemUsageModelImpl.getClassPK(),
			infoItemUsageModelImpl.getContainerKey(),
			infoItemUsageModelImpl.getContainerType(),
			infoItemUsageModelImpl.getPlid()
		};

		finderCache.putResult(
			_finderPathCountByC_C_CK_CT_P, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_C_CK_CT_P, args, infoItemUsageModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		InfoItemUsageModelImpl infoItemUsageModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				infoItemUsageModelImpl.getUuid(),
				infoItemUsageModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((infoItemUsageModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				infoItemUsageModelImpl.getOriginalUuid(),
				infoItemUsageModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				infoItemUsageModelImpl.getClassNameId(),
				infoItemUsageModelImpl.getClassPK(),
				infoItemUsageModelImpl.getContainerKey(),
				infoItemUsageModelImpl.getContainerType(),
				infoItemUsageModelImpl.getPlid()
			};

			finderCache.removeResult(_finderPathCountByC_C_CK_CT_P, args);
			finderCache.removeResult(_finderPathFetchByC_C_CK_CT_P, args);
		}

		if ((infoItemUsageModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C_CK_CT_P.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				infoItemUsageModelImpl.getOriginalClassNameId(),
				infoItemUsageModelImpl.getOriginalClassPK(),
				infoItemUsageModelImpl.getOriginalContainerKey(),
				infoItemUsageModelImpl.getOriginalContainerType(),
				infoItemUsageModelImpl.getOriginalPlid()
			};

			finderCache.removeResult(_finderPathCountByC_C_CK_CT_P, args);
			finderCache.removeResult(_finderPathFetchByC_C_CK_CT_P, args);
		}
	}

	/**
	 * Creates a new info item usage with the primary key. Does not add the info item usage to the database.
	 *
	 * @param infoItemUsageId the primary key for the new info item usage
	 * @return the new info item usage
	 */
	@Override
	public InfoItemUsage create(long infoItemUsageId) {
		InfoItemUsage infoItemUsage = new InfoItemUsageImpl();

		infoItemUsage.setNew(true);
		infoItemUsage.setPrimaryKey(infoItemUsageId);

		String uuid = PortalUUIDUtil.generate();

		infoItemUsage.setUuid(uuid);

		return infoItemUsage;
	}

	/**
	 * Removes the info item usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param infoItemUsageId the primary key of the info item usage
	 * @return the info item usage that was removed
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	@Override
	public InfoItemUsage remove(long infoItemUsageId)
		throws NoSuchItemUsageException {

		return remove((Serializable)infoItemUsageId);
	}

	/**
	 * Removes the info item usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the info item usage
	 * @return the info item usage that was removed
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	@Override
	public InfoItemUsage remove(Serializable primaryKey)
		throws NoSuchItemUsageException {

		Session session = null;

		try {
			session = openSession();

			InfoItemUsage infoItemUsage = (InfoItemUsage)session.get(
				InfoItemUsageImpl.class, primaryKey);

			if (infoItemUsage == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchItemUsageException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(infoItemUsage);
		}
		catch (NoSuchItemUsageException nsee) {
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
	protected InfoItemUsage removeImpl(InfoItemUsage infoItemUsage) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(infoItemUsage)) {
				infoItemUsage = (InfoItemUsage)session.get(
					InfoItemUsageImpl.class, infoItemUsage.getPrimaryKeyObj());
			}

			if (infoItemUsage != null) {
				session.delete(infoItemUsage);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (infoItemUsage != null) {
			clearCache(infoItemUsage);
		}

		return infoItemUsage;
	}

	@Override
	public InfoItemUsage updateImpl(InfoItemUsage infoItemUsage) {
		boolean isNew = infoItemUsage.isNew();

		if (!(infoItemUsage instanceof InfoItemUsageModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(infoItemUsage.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					infoItemUsage);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in infoItemUsage proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom InfoItemUsage implementation " +
					infoItemUsage.getClass());
		}

		InfoItemUsageModelImpl infoItemUsageModelImpl =
			(InfoItemUsageModelImpl)infoItemUsage;

		if (Validator.isNull(infoItemUsage.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			infoItemUsage.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (infoItemUsage.getCreateDate() == null)) {
			if (serviceContext == null) {
				infoItemUsage.setCreateDate(now);
			}
			else {
				infoItemUsage.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!infoItemUsageModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				infoItemUsage.setModifiedDate(now);
			}
			else {
				infoItemUsage.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (infoItemUsage.isNew()) {
				session.save(infoItemUsage);

				infoItemUsage.setNew(false);
			}
			else {
				infoItemUsage = (InfoItemUsage)session.merge(infoItemUsage);
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
			Object[] args = new Object[] {infoItemUsageModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {infoItemUsageModelImpl.getPlid()};

			finderCache.removeResult(_finderPathCountByPlid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByPlid, args);

			args = new Object[] {
				infoItemUsageModelImpl.getClassNameId(),
				infoItemUsageModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			args = new Object[] {
				infoItemUsageModelImpl.getClassNameId(),
				infoItemUsageModelImpl.getClassPK(),
				infoItemUsageModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByC_C_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_C_T, args);

			args = new Object[] {
				infoItemUsageModelImpl.getContainerKey(),
				infoItemUsageModelImpl.getContainerType(),
				infoItemUsageModelImpl.getPlid()
			};

			finderCache.removeResult(_finderPathCountByCK_CT_P, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCK_CT_P, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((infoItemUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					infoItemUsageModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {infoItemUsageModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((infoItemUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByPlid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					infoItemUsageModelImpl.getOriginalPlid()
				};

				finderCache.removeResult(_finderPathCountByPlid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByPlid, args);

				args = new Object[] {infoItemUsageModelImpl.getPlid()};

				finderCache.removeResult(_finderPathCountByPlid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByPlid, args);
			}

			if ((infoItemUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					infoItemUsageModelImpl.getOriginalClassNameId(),
					infoItemUsageModelImpl.getOriginalClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					infoItemUsageModelImpl.getClassNameId(),
					infoItemUsageModelImpl.getClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}

			if ((infoItemUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					infoItemUsageModelImpl.getOriginalClassNameId(),
					infoItemUsageModelImpl.getOriginalClassPK(),
					infoItemUsageModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByC_C_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C_T, args);

				args = new Object[] {
					infoItemUsageModelImpl.getClassNameId(),
					infoItemUsageModelImpl.getClassPK(),
					infoItemUsageModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByC_C_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C_T, args);
			}

			if ((infoItemUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCK_CT_P.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					infoItemUsageModelImpl.getOriginalContainerKey(),
					infoItemUsageModelImpl.getOriginalContainerType(),
					infoItemUsageModelImpl.getOriginalPlid()
				};

				finderCache.removeResult(_finderPathCountByCK_CT_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCK_CT_P, args);

				args = new Object[] {
					infoItemUsageModelImpl.getContainerKey(),
					infoItemUsageModelImpl.getContainerType(),
					infoItemUsageModelImpl.getPlid()
				};

				finderCache.removeResult(_finderPathCountByCK_CT_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCK_CT_P, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, InfoItemUsageImpl.class,
			infoItemUsage.getPrimaryKey(), infoItemUsage, false);

		clearUniqueFindersCache(infoItemUsageModelImpl, false);
		cacheUniqueFindersCache(infoItemUsageModelImpl);

		infoItemUsage.resetOriginalValues();

		return infoItemUsage;
	}

	/**
	 * Returns the info item usage with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the info item usage
	 * @return the info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	@Override
	public InfoItemUsage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchItemUsageException {

		InfoItemUsage infoItemUsage = fetchByPrimaryKey(primaryKey);

		if (infoItemUsage == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchItemUsageException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return infoItemUsage;
	}

	/**
	 * Returns the info item usage with the primary key or throws a <code>NoSuchItemUsageException</code> if it could not be found.
	 *
	 * @param infoItemUsageId the primary key of the info item usage
	 * @return the info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	@Override
	public InfoItemUsage findByPrimaryKey(long infoItemUsageId)
		throws NoSuchItemUsageException {

		return findByPrimaryKey((Serializable)infoItemUsageId);
	}

	/**
	 * Returns the info item usage with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param infoItemUsageId the primary key of the info item usage
	 * @return the info item usage, or <code>null</code> if a info item usage with the primary key could not be found
	 */
	@Override
	public InfoItemUsage fetchByPrimaryKey(long infoItemUsageId) {
		return fetchByPrimaryKey((Serializable)infoItemUsageId);
	}

	/**
	 * Returns all the info item usages.
	 *
	 * @return the info item usages
	 */
	@Override
	public List<InfoItemUsage> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the info item usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of info item usages
	 */
	@Override
	public List<InfoItemUsage> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the info item usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of info item usages
	 */
	@Override
	public List<InfoItemUsage> findAll(
		int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the info item usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of info item usages
	 */
	@Override
	public List<InfoItemUsage> findAll(
		int start, int end, OrderByComparator<InfoItemUsage> orderByComparator,
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

		List<InfoItemUsage> list = null;

		if (useFinderCache) {
			list = (List<InfoItemUsage>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_INFOITEMUSAGE);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_INFOITEMUSAGE;

				sql = sql.concat(InfoItemUsageModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<InfoItemUsage>)QueryUtil.list(
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
	 * Removes all the info item usages from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (InfoItemUsage infoItemUsage : findAll()) {
			remove(infoItemUsage);
		}
	}

	/**
	 * Returns the number of info item usages.
	 *
	 * @return the number of info item usages
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_INFOITEMUSAGE);

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
		return "infoItemUsageId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_INFOITEMUSAGE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return InfoItemUsageModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the info item usage persistence.
	 */
	@Activate
	public void activate() {
		InfoItemUsageModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		InfoItemUsageModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, InfoItemUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, InfoItemUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, InfoItemUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, InfoItemUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			InfoItemUsageModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, InfoItemUsageImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			InfoItemUsageModelImpl.UUID_COLUMN_BITMASK |
			InfoItemUsageModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByPlid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, InfoItemUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPlid",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByPlid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, InfoItemUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPlid",
			new String[] {Long.class.getName()},
			InfoItemUsageModelImpl.PLID_COLUMN_BITMASK);

		_finderPathCountByPlid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPlid",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, InfoItemUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, InfoItemUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			InfoItemUsageModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			InfoItemUsageModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_C_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, InfoItemUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, InfoItemUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			InfoItemUsageModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			InfoItemUsageModelImpl.CLASSPK_COLUMN_BITMASK |
			InfoItemUsageModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByC_C_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByCK_CT_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, InfoItemUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCK_CT_P",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCK_CT_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, InfoItemUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCK_CT_P",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			InfoItemUsageModelImpl.CONTAINERKEY_COLUMN_BITMASK |
			InfoItemUsageModelImpl.CONTAINERTYPE_COLUMN_BITMASK |
			InfoItemUsageModelImpl.PLID_COLUMN_BITMASK);

		_finderPathCountByCK_CT_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCK_CT_P",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			});

		_finderPathFetchByC_C_CK_CT_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, InfoItemUsageImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_CK_CT_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			InfoItemUsageModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			InfoItemUsageModelImpl.CLASSPK_COLUMN_BITMASK |
			InfoItemUsageModelImpl.CONTAINERKEY_COLUMN_BITMASK |
			InfoItemUsageModelImpl.CONTAINERTYPE_COLUMN_BITMASK |
			InfoItemUsageModelImpl.PLID_COLUMN_BITMASK);

		_finderPathCountByC_C_CK_CT_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_CK_CT_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(InfoItemUsageImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = InfoPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.info.model.InfoItemUsage"),
			true);
	}

	@Override
	@Reference(
		target = InfoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = InfoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_INFOITEMUSAGE =
		"SELECT infoItemUsage FROM InfoItemUsage infoItemUsage";

	private static final String _SQL_SELECT_INFOITEMUSAGE_WHERE =
		"SELECT infoItemUsage FROM InfoItemUsage infoItemUsage WHERE ";

	private static final String _SQL_COUNT_INFOITEMUSAGE =
		"SELECT COUNT(infoItemUsage) FROM InfoItemUsage infoItemUsage";

	private static final String _SQL_COUNT_INFOITEMUSAGE_WHERE =
		"SELECT COUNT(infoItemUsage) FROM InfoItemUsage infoItemUsage WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "infoItemUsage.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No InfoItemUsage exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No InfoItemUsage exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		InfoItemUsagePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	static {
		try {
			Class.forName(InfoPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}