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

package com.liferay.layout.service.persistence.impl;

import com.liferay.layout.exception.NoSuchClassedModelUsageException;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.model.impl.LayoutClassedModelUsageImpl;
import com.liferay.layout.model.impl.LayoutClassedModelUsageModelImpl;
import com.liferay.layout.service.persistence.LayoutClassedModelUsagePersistence;
import com.liferay.layout.service.persistence.impl.constants.LayoutPersistenceConstants;
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
 * The persistence implementation for the layout classed model usage service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = LayoutClassedModelUsagePersistence.class)
public class LayoutClassedModelUsagePersistenceImpl
	extends BasePersistenceImpl<LayoutClassedModelUsage>
	implements LayoutClassedModelUsagePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutClassedModelUsageUtil</code> to access the layout classed model usage persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LayoutClassedModelUsageImpl.class.getName();

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
	 * Returns all the layout classed model usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout classed model usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout classed model usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout classed model usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator,
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

		List<LayoutClassedModelUsage> list = null;

		if (useFinderCache) {
			list = (List<LayoutClassedModelUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutClassedModelUsage layoutClassedModelUsage : list) {
					if (!uuid.equals(layoutClassedModelUsage.getUuid())) {
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

			query.append(_SQL_SELECT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
				query.append(LayoutClassedModelUsageModelImpl.ORDER_BY_JPQL);
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

				list = (List<LayoutClassedModelUsage>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout classed model usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByUuid_First(
			String uuid,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = fetchByUuid_First(
			uuid, orderByComparator);

		if (layoutClassedModelUsage != null) {
			return layoutClassedModelUsage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchClassedModelUsageException(msg.toString());
	}

	/**
	 * Returns the first layout classed model usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByUuid_First(
		String uuid,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		List<LayoutClassedModelUsage> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout classed model usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByUuid_Last(
			String uuid,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = fetchByUuid_Last(
			uuid, orderByComparator);

		if (layoutClassedModelUsage != null) {
			return layoutClassedModelUsage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchClassedModelUsageException(msg.toString());
	}

	/**
	 * Returns the last layout classed model usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByUuid_Last(
		String uuid,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LayoutClassedModelUsage> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout classed model usages before and after the current layout classed model usage in the ordered set where uuid = &#63;.
	 *
	 * @param layoutClassedModelUsageId the primary key of the current layout classed model usage
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	@Override
	public LayoutClassedModelUsage[] findByUuid_PrevAndNext(
			long layoutClassedModelUsageId, String uuid,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		uuid = Objects.toString(uuid, "");

		LayoutClassedModelUsage layoutClassedModelUsage = findByPrimaryKey(
			layoutClassedModelUsageId);

		Session session = null;

		try {
			session = openSession();

			LayoutClassedModelUsage[] array =
				new LayoutClassedModelUsageImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, layoutClassedModelUsage, uuid, orderByComparator,
				true);

			array[1] = layoutClassedModelUsage;

			array[2] = getByUuid_PrevAndNext(
				session, layoutClassedModelUsage, uuid, orderByComparator,
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

	protected LayoutClassedModelUsage getByUuid_PrevAndNext(
		Session session, LayoutClassedModelUsage layoutClassedModelUsage,
		String uuid,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
			query.append(LayoutClassedModelUsageModelImpl.ORDER_BY_JPQL);
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
						layoutClassedModelUsage)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutClassedModelUsage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout classed model usages where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LayoutClassedModelUsage layoutClassedModelUsage :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutClassedModelUsage);
		}
	}

	/**
	 * Returns the number of layout classed model usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout classed model usages
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"layoutClassedModelUsage.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(layoutClassedModelUsage.uuid IS NULL OR layoutClassedModelUsage.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the layout classed model usage where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchClassedModelUsageException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByUUID_G(String uuid, long groupId)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = fetchByUUID_G(
			uuid, groupId);

		if (layoutClassedModelUsage == null) {
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

			throw new NoSuchClassedModelUsageException(msg.toString());
		}

		return layoutClassedModelUsage;
	}

	/**
	 * Returns the layout classed model usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the layout classed model usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByUUID_G(
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

		if (result instanceof LayoutClassedModelUsage) {
			LayoutClassedModelUsage layoutClassedModelUsage =
				(LayoutClassedModelUsage)result;

			if (!Objects.equals(uuid, layoutClassedModelUsage.getUuid()) ||
				(groupId != layoutClassedModelUsage.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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

				List<LayoutClassedModelUsage> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					LayoutClassedModelUsage layoutClassedModelUsage = list.get(
						0);

					result = layoutClassedModelUsage;

					cacheResult(layoutClassedModelUsage);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByUUID_G, finderArgs);
				}

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
			return (LayoutClassedModelUsage)result;
		}
	}

	/**
	 * Removes the layout classed model usage where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout classed model usage that was removed
	 */
	@Override
	public LayoutClassedModelUsage removeByUUID_G(String uuid, long groupId)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = findByUUID_G(
			uuid, groupId);

		return remove(layoutClassedModelUsage);
	}

	/**
	 * Returns the number of layout classed model usages where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout classed model usages
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"layoutClassedModelUsage.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(layoutClassedModelUsage.uuid IS NULL OR layoutClassedModelUsage.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"layoutClassedModelUsage.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the layout classed model usages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout classed model usages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout classed model usages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout classed model usages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator,
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

		List<LayoutClassedModelUsage> list = null;

		if (useFinderCache) {
			list = (List<LayoutClassedModelUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutClassedModelUsage layoutClassedModelUsage : list) {
					if (!uuid.equals(layoutClassedModelUsage.getUuid()) ||
						(companyId != layoutClassedModelUsage.getCompanyId())) {

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

			query.append(_SQL_SELECT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
				query.append(LayoutClassedModelUsageModelImpl.ORDER_BY_JPQL);
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

				list = (List<LayoutClassedModelUsage>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout classed model usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (layoutClassedModelUsage != null) {
			return layoutClassedModelUsage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchClassedModelUsageException(msg.toString());
	}

	/**
	 * Returns the first layout classed model usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		List<LayoutClassedModelUsage> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout classed model usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (layoutClassedModelUsage != null) {
			return layoutClassedModelUsage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchClassedModelUsageException(msg.toString());
	}

	/**
	 * Returns the last layout classed model usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<LayoutClassedModelUsage> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout classed model usages before and after the current layout classed model usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutClassedModelUsageId the primary key of the current layout classed model usage
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	@Override
	public LayoutClassedModelUsage[] findByUuid_C_PrevAndNext(
			long layoutClassedModelUsageId, String uuid, long companyId,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		uuid = Objects.toString(uuid, "");

		LayoutClassedModelUsage layoutClassedModelUsage = findByPrimaryKey(
			layoutClassedModelUsageId);

		Session session = null;

		try {
			session = openSession();

			LayoutClassedModelUsage[] array =
				new LayoutClassedModelUsageImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, layoutClassedModelUsage, uuid, companyId,
				orderByComparator, true);

			array[1] = layoutClassedModelUsage;

			array[2] = getByUuid_C_PrevAndNext(
				session, layoutClassedModelUsage, uuid, companyId,
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

	protected LayoutClassedModelUsage getByUuid_C_PrevAndNext(
		Session session, LayoutClassedModelUsage layoutClassedModelUsage,
		String uuid, long companyId,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
			query.append(LayoutClassedModelUsageModelImpl.ORDER_BY_JPQL);
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
						layoutClassedModelUsage)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutClassedModelUsage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout classed model usages where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LayoutClassedModelUsage layoutClassedModelUsage :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutClassedModelUsage);
		}
	}

	/**
	 * Returns the number of layout classed model usages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout classed model usages
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"layoutClassedModelUsage.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(layoutClassedModelUsage.uuid IS NULL OR layoutClassedModelUsage.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"layoutClassedModelUsage.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByPlid;
	private FinderPath _finderPathWithoutPaginationFindByPlid;
	private FinderPath _finderPathCountByPlid;

	/**
	 * Returns all the layout classed model usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByPlid(long plid) {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout classed model usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByPlid(
		long plid, int start, int end) {

		return findByPlid(plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout classed model usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		return findByPlid(plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout classed model usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator,
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

		List<LayoutClassedModelUsage> list = null;

		if (useFinderCache) {
			list = (List<LayoutClassedModelUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutClassedModelUsage layoutClassedModelUsage : list) {
					if (plid != layoutClassedModelUsage.getPlid()) {
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

			query.append(_SQL_SELECT_LAYOUTCLASSEDMODELUSAGE_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutClassedModelUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				list = (List<LayoutClassedModelUsage>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout classed model usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByPlid_First(
			long plid,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = fetchByPlid_First(
			plid, orderByComparator);

		if (layoutClassedModelUsage != null) {
			return layoutClassedModelUsage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchClassedModelUsageException(msg.toString());
	}

	/**
	 * Returns the first layout classed model usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByPlid_First(
		long plid,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		List<LayoutClassedModelUsage> list = findByPlid(
			plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout classed model usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByPlid_Last(
			long plid,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = fetchByPlid_Last(
			plid, orderByComparator);

		if (layoutClassedModelUsage != null) {
			return layoutClassedModelUsage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchClassedModelUsageException(msg.toString());
	}

	/**
	 * Returns the last layout classed model usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByPlid_Last(
		long plid,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		int count = countByPlid(plid);

		if (count == 0) {
			return null;
		}

		List<LayoutClassedModelUsage> list = findByPlid(
			plid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout classed model usages before and after the current layout classed model usage in the ordered set where plid = &#63;.
	 *
	 * @param layoutClassedModelUsageId the primary key of the current layout classed model usage
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	@Override
	public LayoutClassedModelUsage[] findByPlid_PrevAndNext(
			long layoutClassedModelUsageId, long plid,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = findByPrimaryKey(
			layoutClassedModelUsageId);

		Session session = null;

		try {
			session = openSession();

			LayoutClassedModelUsage[] array =
				new LayoutClassedModelUsageImpl[3];

			array[0] = getByPlid_PrevAndNext(
				session, layoutClassedModelUsage, plid, orderByComparator,
				true);

			array[1] = layoutClassedModelUsage;

			array[2] = getByPlid_PrevAndNext(
				session, layoutClassedModelUsage, plid, orderByComparator,
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

	protected LayoutClassedModelUsage getByPlid_PrevAndNext(
		Session session, LayoutClassedModelUsage layoutClassedModelUsage,
		long plid, OrderByComparator<LayoutClassedModelUsage> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
			query.append(LayoutClassedModelUsageModelImpl.ORDER_BY_JPQL);
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
						layoutClassedModelUsage)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutClassedModelUsage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout classed model usages where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	@Override
	public void removeByPlid(long plid) {
		for (LayoutClassedModelUsage layoutClassedModelUsage :
				findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutClassedModelUsage);
		}
	}

	/**
	 * Returns the number of layout classed model usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout classed model usages
	 */
	@Override
	public int countByPlid(long plid) {
		FinderPath finderPath = _finderPathCountByPlid;

		Object[] finderArgs = new Object[] {plid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_PLID_PLID_2 =
		"layoutClassedModelUsage.plid = ? AND layoutClassedModelUsage.containerKey IS NOT NULL";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the layout classed model usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByC_C(
		long classNameId, long classPK) {

		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout classed model usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout classed model usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout classed model usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator,
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

		List<LayoutClassedModelUsage> list = null;

		if (useFinderCache) {
			list = (List<LayoutClassedModelUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutClassedModelUsage layoutClassedModelUsage : list) {
					if ((classNameId !=
							layoutClassedModelUsage.getClassNameId()) ||
						(classPK != layoutClassedModelUsage.getClassPK())) {

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

			query.append(_SQL_SELECT_LAYOUTCLASSEDMODELUSAGE_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutClassedModelUsageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<LayoutClassedModelUsage>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (layoutClassedModelUsage != null) {
			return layoutClassedModelUsage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchClassedModelUsageException(msg.toString());
	}

	/**
	 * Returns the first layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		List<LayoutClassedModelUsage> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (layoutClassedModelUsage != null) {
			return layoutClassedModelUsage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchClassedModelUsageException(msg.toString());
	}

	/**
	 * Returns the last layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<LayoutClassedModelUsage> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout classed model usages before and after the current layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param layoutClassedModelUsageId the primary key of the current layout classed model usage
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	@Override
	public LayoutClassedModelUsage[] findByC_C_PrevAndNext(
			long layoutClassedModelUsageId, long classNameId, long classPK,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = findByPrimaryKey(
			layoutClassedModelUsageId);

		Session session = null;

		try {
			session = openSession();

			LayoutClassedModelUsage[] array =
				new LayoutClassedModelUsageImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, layoutClassedModelUsage, classNameId, classPK,
				orderByComparator, true);

			array[1] = layoutClassedModelUsage;

			array[2] = getByC_C_PrevAndNext(
				session, layoutClassedModelUsage, classNameId, classPK,
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

	protected LayoutClassedModelUsage getByC_C_PrevAndNext(
		Session session, LayoutClassedModelUsage layoutClassedModelUsage,
		long classNameId, long classPK,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
			query.append(LayoutClassedModelUsageModelImpl.ORDER_BY_JPQL);
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
						layoutClassedModelUsage)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutClassedModelUsage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout classed model usages where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (LayoutClassedModelUsage layoutClassedModelUsage :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutClassedModelUsage);
		}
	}

	/**
	 * Returns the number of layout classed model usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching layout classed model usages
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"layoutClassedModelUsage.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"layoutClassedModelUsage.classPK = ? AND layoutClassedModelUsage.containerKey IS NOT NULL";

	private FinderPath _finderPathWithPaginationFindByC_C_T;
	private FinderPath _finderPathWithoutPaginationFindByC_C_T;
	private FinderPath _finderPathCountByC_C_T;

	/**
	 * Returns all the layout classed model usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByC_C_T(
		long classNameId, long classPK, int type) {

		return findByC_C_T(
			classNameId, classPK, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout classed model usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end) {

		return findByC_C_T(classNameId, classPK, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout classed model usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		return findByC_C_T(
			classNameId, classPK, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout classed model usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator,
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

		List<LayoutClassedModelUsage> list = null;

		if (useFinderCache) {
			list = (List<LayoutClassedModelUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutClassedModelUsage layoutClassedModelUsage : list) {
					if ((classNameId !=
							layoutClassedModelUsage.getClassNameId()) ||
						(classPK != layoutClassedModelUsage.getClassPK()) ||
						(type != layoutClassedModelUsage.getType())) {

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

			query.append(_SQL_SELECT_LAYOUTCLASSEDMODELUSAGE_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutClassedModelUsageModelImpl.ORDER_BY_JPQL);
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

				list = (List<LayoutClassedModelUsage>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByC_C_T_First(
			long classNameId, long classPK, int type,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = fetchByC_C_T_First(
			classNameId, classPK, type, orderByComparator);

		if (layoutClassedModelUsage != null) {
			return layoutClassedModelUsage;
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

		throw new NoSuchClassedModelUsageException(msg.toString());
	}

	/**
	 * Returns the first layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByC_C_T_First(
		long classNameId, long classPK, int type,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		List<LayoutClassedModelUsage> list = findByC_C_T(
			classNameId, classPK, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByC_C_T_Last(
			long classNameId, long classPK, int type,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = fetchByC_C_T_Last(
			classNameId, classPK, type, orderByComparator);

		if (layoutClassedModelUsage != null) {
			return layoutClassedModelUsage;
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

		throw new NoSuchClassedModelUsageException(msg.toString());
	}

	/**
	 * Returns the last layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByC_C_T_Last(
		long classNameId, long classPK, int type,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		int count = countByC_C_T(classNameId, classPK, type);

		if (count == 0) {
			return null;
		}

		List<LayoutClassedModelUsage> list = findByC_C_T(
			classNameId, classPK, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout classed model usages before and after the current layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param layoutClassedModelUsageId the primary key of the current layout classed model usage
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	@Override
	public LayoutClassedModelUsage[] findByC_C_T_PrevAndNext(
			long layoutClassedModelUsageId, long classNameId, long classPK,
			int type,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = findByPrimaryKey(
			layoutClassedModelUsageId);

		Session session = null;

		try {
			session = openSession();

			LayoutClassedModelUsage[] array =
				new LayoutClassedModelUsageImpl[3];

			array[0] = getByC_C_T_PrevAndNext(
				session, layoutClassedModelUsage, classNameId, classPK, type,
				orderByComparator, true);

			array[1] = layoutClassedModelUsage;

			array[2] = getByC_C_T_PrevAndNext(
				session, layoutClassedModelUsage, classNameId, classPK, type,
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

	protected LayoutClassedModelUsage getByC_C_T_PrevAndNext(
		Session session, LayoutClassedModelUsage layoutClassedModelUsage,
		long classNameId, long classPK, int type,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
			query.append(LayoutClassedModelUsageModelImpl.ORDER_BY_JPQL);
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
						layoutClassedModelUsage)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutClassedModelUsage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout classed model usages where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	@Override
	public void removeByC_C_T(long classNameId, long classPK, int type) {
		for (LayoutClassedModelUsage layoutClassedModelUsage :
				findByC_C_T(
					classNameId, classPK, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutClassedModelUsage);
		}
	}

	/**
	 * Returns the number of layout classed model usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching layout classed model usages
	 */
	@Override
	public int countByC_C_T(long classNameId, long classPK, int type) {
		FinderPath finderPath = _finderPathCountByC_C_T;

		Object[] finderArgs = new Object[] {classNameId, classPK, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_T_CLASSNAMEID_2 =
		"layoutClassedModelUsage.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_CLASSPK_2 =
		"layoutClassedModelUsage.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_TYPE_2 =
		"layoutClassedModelUsage.type = ? AND layoutClassedModelUsage.containerKey IS NOT NULL";

	private FinderPath _finderPathWithPaginationFindByCK_CT_P;
	private FinderPath _finderPathWithoutPaginationFindByCK_CT_P;
	private FinderPath _finderPathCountByCK_CT_P;

	/**
	 * Returns all the layout classed model usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid) {

		return findByCK_CT_P(
			containerKey, containerType, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout classed model usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start,
		int end) {

		return findByCK_CT_P(
			containerKey, containerType, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout classed model usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		return findByCK_CT_P(
			containerKey, containerType, plid, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the layout classed model usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator,
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

		List<LayoutClassedModelUsage> list = null;

		if (useFinderCache) {
			list = (List<LayoutClassedModelUsage>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutClassedModelUsage layoutClassedModelUsage : list) {
					if (!containerKey.equals(
							layoutClassedModelUsage.getContainerKey()) ||
						(containerType !=
							layoutClassedModelUsage.getContainerType()) ||
						(plid != layoutClassedModelUsage.getPlid())) {

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

			query.append(_SQL_SELECT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
				query.append(LayoutClassedModelUsageModelImpl.ORDER_BY_JPQL);
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

				list = (List<LayoutClassedModelUsage>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout classed model usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByCK_CT_P_First(
			String containerKey, long containerType, long plid,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = fetchByCK_CT_P_First(
			containerKey, containerType, plid, orderByComparator);

		if (layoutClassedModelUsage != null) {
			return layoutClassedModelUsage;
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

		throw new NoSuchClassedModelUsageException(msg.toString());
	}

	/**
	 * Returns the first layout classed model usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByCK_CT_P_First(
		String containerKey, long containerType, long plid,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		List<LayoutClassedModelUsage> list = findByCK_CT_P(
			containerKey, containerType, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout classed model usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByCK_CT_P_Last(
			String containerKey, long containerType, long plid,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = fetchByCK_CT_P_Last(
			containerKey, containerType, plid, orderByComparator);

		if (layoutClassedModelUsage != null) {
			return layoutClassedModelUsage;
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

		throw new NoSuchClassedModelUsageException(msg.toString());
	}

	/**
	 * Returns the last layout classed model usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByCK_CT_P_Last(
		String containerKey, long containerType, long plid,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		int count = countByCK_CT_P(containerKey, containerType, plid);

		if (count == 0) {
			return null;
		}

		List<LayoutClassedModelUsage> list = findByCK_CT_P(
			containerKey, containerType, plid, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout classed model usages before and after the current layout classed model usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param layoutClassedModelUsageId the primary key of the current layout classed model usage
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	@Override
	public LayoutClassedModelUsage[] findByCK_CT_P_PrevAndNext(
			long layoutClassedModelUsageId, String containerKey,
			long containerType, long plid,
			OrderByComparator<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException {

		containerKey = Objects.toString(containerKey, "");

		LayoutClassedModelUsage layoutClassedModelUsage = findByPrimaryKey(
			layoutClassedModelUsageId);

		Session session = null;

		try {
			session = openSession();

			LayoutClassedModelUsage[] array =
				new LayoutClassedModelUsageImpl[3];

			array[0] = getByCK_CT_P_PrevAndNext(
				session, layoutClassedModelUsage, containerKey, containerType,
				plid, orderByComparator, true);

			array[1] = layoutClassedModelUsage;

			array[2] = getByCK_CT_P_PrevAndNext(
				session, layoutClassedModelUsage, containerKey, containerType,
				plid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutClassedModelUsage getByCK_CT_P_PrevAndNext(
		Session session, LayoutClassedModelUsage layoutClassedModelUsage,
		String containerKey, long containerType, long plid,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
			query.append(LayoutClassedModelUsageModelImpl.ORDER_BY_JPQL);
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
						layoutClassedModelUsage)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutClassedModelUsage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout classed model usages where containerKey = &#63; and containerType = &#63; and plid = &#63; from the database.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 */
	@Override
	public void removeByCK_CT_P(
		String containerKey, long containerType, long plid) {

		for (LayoutClassedModelUsage layoutClassedModelUsage :
				findByCK_CT_P(
					containerKey, containerType, plid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutClassedModelUsage);
		}
	}

	/**
	 * Returns the number of layout classed model usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the number of matching layout classed model usages
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

			query.append(_SQL_COUNT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CK_CT_P_CONTAINERKEY_2 =
		"layoutClassedModelUsage.containerKey = ? AND ";

	private static final String _FINDER_COLUMN_CK_CT_P_CONTAINERKEY_3 =
		"(layoutClassedModelUsage.containerKey IS NULL OR layoutClassedModelUsage.containerKey = '') AND ";

	private static final String _FINDER_COLUMN_CK_CT_P_CONTAINERTYPE_2 =
		"layoutClassedModelUsage.containerType = ? AND ";

	private static final String _FINDER_COLUMN_CK_CT_P_PLID_2 =
		"layoutClassedModelUsage.plid = ? AND layoutClassedModelUsage.containerKey IS NOT NULL";

	private FinderPath _finderPathFetchByC_C_CK_CT_P;
	private FinderPath _finderPathCountByC_C_CK_CT_P;

	/**
	 * Returns the layout classed model usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; or throws a <code>NoSuchClassedModelUsageException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByC_C_CK_CT_P(
			long classNameId, long classPK, String containerKey,
			long containerType, long plid)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = fetchByC_C_CK_CT_P(
			classNameId, classPK, containerKey, containerType, plid);

		if (layoutClassedModelUsage == null) {
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

			throw new NoSuchClassedModelUsageException(msg.toString());
		}

		return layoutClassedModelUsage;
	}

	/**
	 * Returns the layout classed model usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByC_C_CK_CT_P(
		long classNameId, long classPK, String containerKey, long containerType,
		long plid) {

		return fetchByC_C_CK_CT_P(
			classNameId, classPK, containerKey, containerType, plid, true);
	}

	/**
	 * Returns the layout classed model usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByC_C_CK_CT_P(
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

		if (result instanceof LayoutClassedModelUsage) {
			LayoutClassedModelUsage layoutClassedModelUsage =
				(LayoutClassedModelUsage)result;

			if ((classNameId != layoutClassedModelUsage.getClassNameId()) ||
				(classPK != layoutClassedModelUsage.getClassPK()) ||
				!Objects.equals(
					containerKey, layoutClassedModelUsage.getContainerKey()) ||
				(containerType != layoutClassedModelUsage.getContainerType()) ||
				(plid != layoutClassedModelUsage.getPlid())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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

				List<LayoutClassedModelUsage> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_CK_CT_P, finderArgs, list);
					}
				}
				else {
					LayoutClassedModelUsage layoutClassedModelUsage = list.get(
						0);

					result = layoutClassedModelUsage;

					cacheResult(layoutClassedModelUsage);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByC_C_CK_CT_P, finderArgs);
				}

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
			return (LayoutClassedModelUsage)result;
		}
	}

	/**
	 * Removes the layout classed model usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the layout classed model usage that was removed
	 */
	@Override
	public LayoutClassedModelUsage removeByC_C_CK_CT_P(
			long classNameId, long classPK, String containerKey,
			long containerType, long plid)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = findByC_C_CK_CT_P(
			classNameId, classPK, containerKey, containerType, plid);

		return remove(layoutClassedModelUsage);
	}

	/**
	 * Returns the number of layout classed model usages where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the number of matching layout classed model usages
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

			query.append(_SQL_COUNT_LAYOUTCLASSEDMODELUSAGE_WHERE);

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
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_CK_CT_P_CLASSNAMEID_2 =
		"layoutClassedModelUsage.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CK_CT_P_CLASSPK_2 =
		"layoutClassedModelUsage.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CK_CT_P_CONTAINERKEY_2 =
		"layoutClassedModelUsage.containerKey = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CK_CT_P_CONTAINERKEY_3 =
		"(layoutClassedModelUsage.containerKey IS NULL OR layoutClassedModelUsage.containerKey = '') AND ";

	private static final String _FINDER_COLUMN_C_C_CK_CT_P_CONTAINERTYPE_2 =
		"layoutClassedModelUsage.containerType = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CK_CT_P_PLID_2 =
		"layoutClassedModelUsage.plid = ?";

	public LayoutClassedModelUsagePersistenceImpl() {
		setModelClass(LayoutClassedModelUsage.class);

		setModelImplClass(LayoutClassedModelUsageImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the layout classed model usage in the entity cache if it is enabled.
	 *
	 * @param layoutClassedModelUsage the layout classed model usage
	 */
	@Override
	public void cacheResult(LayoutClassedModelUsage layoutClassedModelUsage) {
		entityCache.putResult(
			entityCacheEnabled, LayoutClassedModelUsageImpl.class,
			layoutClassedModelUsage.getPrimaryKey(), layoutClassedModelUsage);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				layoutClassedModelUsage.getUuid(),
				layoutClassedModelUsage.getGroupId()
			},
			layoutClassedModelUsage);

		finderCache.putResult(
			_finderPathFetchByC_C_CK_CT_P,
			new Object[] {
				layoutClassedModelUsage.getClassNameId(),
				layoutClassedModelUsage.getClassPK(),
				layoutClassedModelUsage.getContainerKey(),
				layoutClassedModelUsage.getContainerType(),
				layoutClassedModelUsage.getPlid()
			},
			layoutClassedModelUsage);

		layoutClassedModelUsage.resetOriginalValues();
	}

	/**
	 * Caches the layout classed model usages in the entity cache if it is enabled.
	 *
	 * @param layoutClassedModelUsages the layout classed model usages
	 */
	@Override
	public void cacheResult(
		List<LayoutClassedModelUsage> layoutClassedModelUsages) {

		for (LayoutClassedModelUsage layoutClassedModelUsage :
				layoutClassedModelUsages) {

			if (entityCache.getResult(
					entityCacheEnabled, LayoutClassedModelUsageImpl.class,
					layoutClassedModelUsage.getPrimaryKey()) == null) {

				cacheResult(layoutClassedModelUsage);
			}
			else {
				layoutClassedModelUsage.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout classed model usages.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutClassedModelUsageImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout classed model usage.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutClassedModelUsage layoutClassedModelUsage) {
		entityCache.removeResult(
			entityCacheEnabled, LayoutClassedModelUsageImpl.class,
			layoutClassedModelUsage.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(LayoutClassedModelUsageModelImpl)layoutClassedModelUsage, true);
	}

	@Override
	public void clearCache(
		List<LayoutClassedModelUsage> layoutClassedModelUsages) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutClassedModelUsage layoutClassedModelUsage :
				layoutClassedModelUsages) {

			entityCache.removeResult(
				entityCacheEnabled, LayoutClassedModelUsageImpl.class,
				layoutClassedModelUsage.getPrimaryKey());

			clearUniqueFindersCache(
				(LayoutClassedModelUsageModelImpl)layoutClassedModelUsage,
				true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, LayoutClassedModelUsageImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutClassedModelUsageModelImpl layoutClassedModelUsageModelImpl) {

		Object[] args = new Object[] {
			layoutClassedModelUsageModelImpl.getUuid(),
			layoutClassedModelUsageModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, layoutClassedModelUsageModelImpl,
			false);

		args = new Object[] {
			layoutClassedModelUsageModelImpl.getClassNameId(),
			layoutClassedModelUsageModelImpl.getClassPK(),
			layoutClassedModelUsageModelImpl.getContainerKey(),
			layoutClassedModelUsageModelImpl.getContainerType(),
			layoutClassedModelUsageModelImpl.getPlid()
		};

		finderCache.putResult(
			_finderPathCountByC_C_CK_CT_P, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_C_CK_CT_P, args,
			layoutClassedModelUsageModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LayoutClassedModelUsageModelImpl layoutClassedModelUsageModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutClassedModelUsageModelImpl.getUuid(),
				layoutClassedModelUsageModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((layoutClassedModelUsageModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutClassedModelUsageModelImpl.getOriginalUuid(),
				layoutClassedModelUsageModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutClassedModelUsageModelImpl.getClassNameId(),
				layoutClassedModelUsageModelImpl.getClassPK(),
				layoutClassedModelUsageModelImpl.getContainerKey(),
				layoutClassedModelUsageModelImpl.getContainerType(),
				layoutClassedModelUsageModelImpl.getPlid()
			};

			finderCache.removeResult(_finderPathCountByC_C_CK_CT_P, args);
			finderCache.removeResult(_finderPathFetchByC_C_CK_CT_P, args);
		}

		if ((layoutClassedModelUsageModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C_CK_CT_P.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutClassedModelUsageModelImpl.getOriginalClassNameId(),
				layoutClassedModelUsageModelImpl.getOriginalClassPK(),
				layoutClassedModelUsageModelImpl.getOriginalContainerKey(),
				layoutClassedModelUsageModelImpl.getOriginalContainerType(),
				layoutClassedModelUsageModelImpl.getOriginalPlid()
			};

			finderCache.removeResult(_finderPathCountByC_C_CK_CT_P, args);
			finderCache.removeResult(_finderPathFetchByC_C_CK_CT_P, args);
		}
	}

	/**
	 * Creates a new layout classed model usage with the primary key. Does not add the layout classed model usage to the database.
	 *
	 * @param layoutClassedModelUsageId the primary key for the new layout classed model usage
	 * @return the new layout classed model usage
	 */
	@Override
	public LayoutClassedModelUsage create(long layoutClassedModelUsageId) {
		LayoutClassedModelUsage layoutClassedModelUsage =
			new LayoutClassedModelUsageImpl();

		layoutClassedModelUsage.setNew(true);
		layoutClassedModelUsage.setPrimaryKey(layoutClassedModelUsageId);

		String uuid = PortalUUIDUtil.generate();

		layoutClassedModelUsage.setUuid(uuid);

		layoutClassedModelUsage.setCompanyId(CompanyThreadLocal.getCompanyId());

		return layoutClassedModelUsage;
	}

	/**
	 * Removes the layout classed model usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutClassedModelUsageId the primary key of the layout classed model usage
	 * @return the layout classed model usage that was removed
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	@Override
	public LayoutClassedModelUsage remove(long layoutClassedModelUsageId)
		throws NoSuchClassedModelUsageException {

		return remove((Serializable)layoutClassedModelUsageId);
	}

	/**
	 * Removes the layout classed model usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout classed model usage
	 * @return the layout classed model usage that was removed
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	@Override
	public LayoutClassedModelUsage remove(Serializable primaryKey)
		throws NoSuchClassedModelUsageException {

		Session session = null;

		try {
			session = openSession();

			LayoutClassedModelUsage layoutClassedModelUsage =
				(LayoutClassedModelUsage)session.get(
					LayoutClassedModelUsageImpl.class, primaryKey);

			if (layoutClassedModelUsage == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchClassedModelUsageException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(layoutClassedModelUsage);
		}
		catch (NoSuchClassedModelUsageException noSuchEntityException) {
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
	protected LayoutClassedModelUsage removeImpl(
		LayoutClassedModelUsage layoutClassedModelUsage) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutClassedModelUsage)) {
				layoutClassedModelUsage = (LayoutClassedModelUsage)session.get(
					LayoutClassedModelUsageImpl.class,
					layoutClassedModelUsage.getPrimaryKeyObj());
			}

			if (layoutClassedModelUsage != null) {
				session.delete(layoutClassedModelUsage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (layoutClassedModelUsage != null) {
			clearCache(layoutClassedModelUsage);
		}

		return layoutClassedModelUsage;
	}

	@Override
	public LayoutClassedModelUsage updateImpl(
		LayoutClassedModelUsage layoutClassedModelUsage) {

		boolean isNew = layoutClassedModelUsage.isNew();

		if (!(layoutClassedModelUsage instanceof
				LayoutClassedModelUsageModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutClassedModelUsage.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					layoutClassedModelUsage);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutClassedModelUsage proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutClassedModelUsage implementation " +
					layoutClassedModelUsage.getClass());
		}

		LayoutClassedModelUsageModelImpl layoutClassedModelUsageModelImpl =
			(LayoutClassedModelUsageModelImpl)layoutClassedModelUsage;

		if (Validator.isNull(layoutClassedModelUsage.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			layoutClassedModelUsage.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutClassedModelUsage.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutClassedModelUsage.setCreateDate(now);
			}
			else {
				layoutClassedModelUsage.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!layoutClassedModelUsageModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutClassedModelUsage.setModifiedDate(now);
			}
			else {
				layoutClassedModelUsage.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutClassedModelUsage.isNew()) {
				session.save(layoutClassedModelUsage);

				layoutClassedModelUsage.setNew(false);
			}
			else {
				layoutClassedModelUsage =
					(LayoutClassedModelUsage)session.merge(
						layoutClassedModelUsage);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				layoutClassedModelUsageModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				layoutClassedModelUsageModelImpl.getUuid(),
				layoutClassedModelUsageModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {layoutClassedModelUsageModelImpl.getPlid()};

			finderCache.removeResult(_finderPathCountByPlid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByPlid, args);

			args = new Object[] {
				layoutClassedModelUsageModelImpl.getClassNameId(),
				layoutClassedModelUsageModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			args = new Object[] {
				layoutClassedModelUsageModelImpl.getClassNameId(),
				layoutClassedModelUsageModelImpl.getClassPK(),
				layoutClassedModelUsageModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByC_C_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_C_T, args);

			args = new Object[] {
				layoutClassedModelUsageModelImpl.getContainerKey(),
				layoutClassedModelUsageModelImpl.getContainerType(),
				layoutClassedModelUsageModelImpl.getPlid()
			};

			finderCache.removeResult(_finderPathCountByCK_CT_P, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCK_CT_P, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((layoutClassedModelUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutClassedModelUsageModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {
					layoutClassedModelUsageModelImpl.getUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((layoutClassedModelUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutClassedModelUsageModelImpl.getOriginalUuid(),
					layoutClassedModelUsageModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					layoutClassedModelUsageModelImpl.getUuid(),
					layoutClassedModelUsageModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((layoutClassedModelUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByPlid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutClassedModelUsageModelImpl.getOriginalPlid()
				};

				finderCache.removeResult(_finderPathCountByPlid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByPlid, args);

				args = new Object[] {
					layoutClassedModelUsageModelImpl.getPlid()
				};

				finderCache.removeResult(_finderPathCountByPlid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByPlid, args);
			}

			if ((layoutClassedModelUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutClassedModelUsageModelImpl.getOriginalClassNameId(),
					layoutClassedModelUsageModelImpl.getOriginalClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					layoutClassedModelUsageModelImpl.getClassNameId(),
					layoutClassedModelUsageModelImpl.getClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}

			if ((layoutClassedModelUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutClassedModelUsageModelImpl.getOriginalClassNameId(),
					layoutClassedModelUsageModelImpl.getOriginalClassPK(),
					layoutClassedModelUsageModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByC_C_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C_T, args);

				args = new Object[] {
					layoutClassedModelUsageModelImpl.getClassNameId(),
					layoutClassedModelUsageModelImpl.getClassPK(),
					layoutClassedModelUsageModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByC_C_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C_T, args);
			}

			if ((layoutClassedModelUsageModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCK_CT_P.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutClassedModelUsageModelImpl.getOriginalContainerKey(),
					layoutClassedModelUsageModelImpl.getOriginalContainerType(),
					layoutClassedModelUsageModelImpl.getOriginalPlid()
				};

				finderCache.removeResult(_finderPathCountByCK_CT_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCK_CT_P, args);

				args = new Object[] {
					layoutClassedModelUsageModelImpl.getContainerKey(),
					layoutClassedModelUsageModelImpl.getContainerType(),
					layoutClassedModelUsageModelImpl.getPlid()
				};

				finderCache.removeResult(_finderPathCountByCK_CT_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCK_CT_P, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, LayoutClassedModelUsageImpl.class,
			layoutClassedModelUsage.getPrimaryKey(), layoutClassedModelUsage,
			false);

		clearUniqueFindersCache(layoutClassedModelUsageModelImpl, false);
		cacheUniqueFindersCache(layoutClassedModelUsageModelImpl);

		layoutClassedModelUsage.resetOriginalValues();

		return layoutClassedModelUsage;
	}

	/**
	 * Returns the layout classed model usage with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout classed model usage
	 * @return the layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchClassedModelUsageException {

		LayoutClassedModelUsage layoutClassedModelUsage = fetchByPrimaryKey(
			primaryKey);

		if (layoutClassedModelUsage == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchClassedModelUsageException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return layoutClassedModelUsage;
	}

	/**
	 * Returns the layout classed model usage with the primary key or throws a <code>NoSuchClassedModelUsageException</code> if it could not be found.
	 *
	 * @param layoutClassedModelUsageId the primary key of the layout classed model usage
	 * @return the layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	@Override
	public LayoutClassedModelUsage findByPrimaryKey(
			long layoutClassedModelUsageId)
		throws NoSuchClassedModelUsageException {

		return findByPrimaryKey((Serializable)layoutClassedModelUsageId);
	}

	/**
	 * Returns the layout classed model usage with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutClassedModelUsageId the primary key of the layout classed model usage
	 * @return the layout classed model usage, or <code>null</code> if a layout classed model usage with the primary key could not be found
	 */
	@Override
	public LayoutClassedModelUsage fetchByPrimaryKey(
		long layoutClassedModelUsageId) {

		return fetchByPrimaryKey((Serializable)layoutClassedModelUsageId);
	}

	/**
	 * Returns all the layout classed model usages.
	 *
	 * @return the layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout classed model usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout classed model usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findAll(
		int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout classed model usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout classed model usages
	 */
	@Override
	public List<LayoutClassedModelUsage> findAll(
		int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator,
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

		List<LayoutClassedModelUsage> list = null;

		if (useFinderCache) {
			list = (List<LayoutClassedModelUsage>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTCLASSEDMODELUSAGE);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTCLASSEDMODELUSAGE;

				sql = sql.concat(
					LayoutClassedModelUsageModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<LayoutClassedModelUsage>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the layout classed model usages from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutClassedModelUsage layoutClassedModelUsage : findAll()) {
			remove(layoutClassedModelUsage);
		}
	}

	/**
	 * Returns the number of layout classed model usages.
	 *
	 * @return the number of layout classed model usages
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
					_SQL_COUNT_LAYOUTCLASSEDMODELUSAGE);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

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
		return "layoutClassedModelUsageId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTCLASSEDMODELUSAGE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutClassedModelUsageModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout classed model usage persistence.
	 */
	@Activate
	public void activate() {
		LayoutClassedModelUsageModelImpl.setEntityCacheEnabled(
			entityCacheEnabled);
		LayoutClassedModelUsageModelImpl.setFinderCacheEnabled(
			finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			LayoutClassedModelUsageModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			LayoutClassedModelUsageModelImpl.UUID_COLUMN_BITMASK |
			LayoutClassedModelUsageModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			LayoutClassedModelUsageModelImpl.UUID_COLUMN_BITMASK |
			LayoutClassedModelUsageModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByPlid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPlid",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByPlid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPlid",
			new String[] {Long.class.getName()},
			LayoutClassedModelUsageModelImpl.PLID_COLUMN_BITMASK);

		_finderPathCountByPlid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPlid",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			LayoutClassedModelUsageModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			LayoutClassedModelUsageModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_C_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			LayoutClassedModelUsageModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			LayoutClassedModelUsageModelImpl.CLASSPK_COLUMN_BITMASK |
			LayoutClassedModelUsageModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByC_C_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByCK_CT_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCK_CT_P",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCK_CT_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCK_CT_P",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			LayoutClassedModelUsageModelImpl.CONTAINERKEY_COLUMN_BITMASK |
			LayoutClassedModelUsageModelImpl.CONTAINERTYPE_COLUMN_BITMASK |
			LayoutClassedModelUsageModelImpl.PLID_COLUMN_BITMASK);

		_finderPathCountByCK_CT_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCK_CT_P",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			});

		_finderPathFetchByC_C_CK_CT_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutClassedModelUsageImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_C_CK_CT_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			LayoutClassedModelUsageModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			LayoutClassedModelUsageModelImpl.CLASSPK_COLUMN_BITMASK |
			LayoutClassedModelUsageModelImpl.CONTAINERKEY_COLUMN_BITMASK |
			LayoutClassedModelUsageModelImpl.CONTAINERTYPE_COLUMN_BITMASK |
			LayoutClassedModelUsageModelImpl.PLID_COLUMN_BITMASK);

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
		entityCache.removeCache(LayoutClassedModelUsageImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = LayoutPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.layout.model.LayoutClassedModelUsage"),
			true);
	}

	@Override
	@Reference(
		target = LayoutPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = LayoutPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_LAYOUTCLASSEDMODELUSAGE =
		"SELECT layoutClassedModelUsage FROM LayoutClassedModelUsage layoutClassedModelUsage";

	private static final String _SQL_SELECT_LAYOUTCLASSEDMODELUSAGE_WHERE =
		"SELECT layoutClassedModelUsage FROM LayoutClassedModelUsage layoutClassedModelUsage WHERE ";

	private static final String _SQL_COUNT_LAYOUTCLASSEDMODELUSAGE =
		"SELECT COUNT(layoutClassedModelUsage) FROM LayoutClassedModelUsage layoutClassedModelUsage";

	private static final String _SQL_COUNT_LAYOUTCLASSEDMODELUSAGE_WHERE =
		"SELECT COUNT(layoutClassedModelUsage) FROM LayoutClassedModelUsage layoutClassedModelUsage WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"layoutClassedModelUsage.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LayoutClassedModelUsage exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LayoutClassedModelUsage exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutClassedModelUsagePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	static {
		try {
			Class.forName(LayoutPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}